package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.RefreshTokenRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.RefreshTokenFamily;
import dz.sh.hidra.modules.identity.domain.service.RefreshTokenRotationPolicy;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Single writable READ_COMMITTED owner transaction. Always lock the family before the token
 * row and native session. A successful rotation retires its previous bound bearer.
 */
@Component
public final class JdbcRefreshTokenRepositoryAdapter implements RefreshTokenRepositoryPort {
    private static final String F = "public.hidra_identity_refresh_family";
    private static final String T = "public.hidra_identity_refresh_token";
    private static final String S = "public.hidra_identity_login_session";
    private final JdbcTemplate jdbc;
    public JdbcRefreshTokenRepositoryAdapter(JdbcTemplate jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
    }
    private void owner() {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || !"read committed".equals(jdbc.queryForObject("SHOW transaction_isolation",String.class)))
            throw new IllegalStateException("Refresh storage requires writable READ COMMITTED owner.");
    }
    @Override
    public void create(RefreshTokenFamily f, byte[] digest, String bindingId, String issuer,
                       String jti, String sessionId) {
        owner();
        jdbc.update("INSERT INTO "+F+" (id,user_id,provider_id,credential_id,"
                        +"provider_generation,credential_generation,rights_digest,created_at,deadline)"
                        +" VALUES (?,?,?,?,?,?,?,?,?)",f.id(),f.userId(),f.providerId(),
                f.credentialId(),f.providerGeneration(),f.credentialGeneration(),f.rightsDigest(),
                Timestamp.from(f.createdAt()),Timestamp.from(f.deadline()));
        jdbc.update("INSERT INTO "+T+" (family_id,generation,digest,binding_id,native_issuer,"
                        +"native_jti,session_id,issued_at) VALUES (?,0,?,?,?,?,?,?)",
                f.id(),digest.clone(),bindingId,issuer,jti,sessionId,Timestamp.from(f.createdAt()));
    }

    private record Locked(RefreshTokenFamily family,boolean revoked) {}
    private Locked lock(String familyId) {
        var rows=jdbc.query("SELECT user_id,provider_id,credential_id,provider_generation,"
                        +"credential_generation,rights_digest,created_at,deadline,revoked_at"
                        +" FROM "+F+" WHERE id=? FOR UPDATE",
                (r,n)->new Locked(new RefreshTokenFamily(familyId,r.getString(1),r.getString(2),
                        r.getLong(4),r.getString(3),r.getLong(5),r.getBytes(6),
                        r.getTimestamp(7).toInstant(),r.getTimestamp(8).toInstant()),
                        r.getTimestamp(9)!=null),familyId);
        return rows.size()==1?rows.getFirst():null;
    }
    private record Row(String bindingId,String issuer,String jti,String sessionId,long generation,
                       Instant consumedAt,String sessionStatus,Instant endedAt) {}

    @Override
    public Consumed consume(String familyId,byte[] digest,Instant at) {
        owner();
        var family=lock(familyId);
        if(family==null||family.revoked()) throw invalid();
        var rows=jdbc.query("SELECT t.binding_id,t.native_issuer,t.native_jti,t.session_id,"
                        +"t.generation,t.consumed_at,s.status,s.ended_at FROM "+T+" t"
                        +" JOIN "+S+" s ON s.id=t.session_id"
                        +" WHERE t.family_id=? AND t.digest=?",
                (r,n)->new Row(r.getString(1),r.getString(2),r.getString(3),r.getString(4),
                        r.getLong(5),r.getTimestamp(6)==null?null:r.getTimestamp(6).toInstant(),
                        r.getString(7),r.getTimestamp(8)==null?null:r.getTimestamp(8).toInstant()),
                familyId,digest.clone());
        if(rows.size()!=1) throw invalid();
        Row row=rows.getFirst();
        if(row.consumedAt()!=null) {
            revokeFamilyLocked(familyId,at);
            throw new RefreshTokenRotationPolicy.Replay();
        }
        // Evaluate expiry after acquiring the family row lock. Before-lock timestamps can
        // stale across another transaction that rotates and publishes a newer session.
        Instant afterLock=jdbc.queryForObject("SELECT clock_timestamp()",Timestamp.class).toInstant();
        if(!afterLock.isBefore(family.family().deadline())
                || !"ACTIVE".equals(row.sessionStatus()) || row.endedAt()!=null) {
            revokeFamilyLocked(familyId,at);
            throw new RefreshTokenRotationPolicy.Rejected();
        }
        if(jdbc.update("UPDATE "+T+" SET consumed_at=? WHERE family_id=?"
                        +" AND generation=? AND digest=? AND consumed_at IS NULL",
                Timestamp.from(at),familyId,row.generation(),digest.clone())!=1)
            throw new IllegalStateException("Refresh row arbitration failed.");
        // A refreshed token never leaves the older access JWT valid.
        if(jdbc.update("UPDATE "+S+" SET status='REVOKED',ended_at=?"
                        +" WHERE id=? AND status='ACTIVE' AND ended_at IS NULL",
                Timestamp.from(at),row.sessionId())!=1)
            throw new IllegalStateException("Refresh session termination failed.");
        return new Consumed(family.family(),row.bindingId(),row.issuer(),row.jti(),
                row.sessionId(),row.generation());
    }

    @Override
    public void attachRotation(String familyId,long previousGeneration,byte[] nextDigest,
                               String bindingId,String issuer,String jti,String newSession,
                               Instant at) {
        owner();
        if(previousGeneration<0||nextDigest==null||nextDigest.length!=32)
            throw new IllegalArgumentException("Invalid rotation generation.");
        // Family lock is held from consume() until the current owner transaction commits.
        jdbc.update("INSERT INTO "+T+" (family_id,generation,digest,binding_id,native_issuer,"
                        +"native_jti,session_id,issued_at) VALUES (?,?,?,?,?,?,?,?)",
                familyId,previousGeneration+1,nextDigest.clone(),bindingId,issuer,jti,
                newSession,Timestamp.from(at));
    }

    @Override
    public void revokeFamily(String familyId,Instant at) {
        owner();
        var f=lock(familyId);
        if(f!=null&&!f.revoked()) revokeFamilyLocked(familyId,at);
    }
    private void revokeFamilyLocked(String familyId,Instant at) {
        // PostgreSQL samples clock_timestamp() after the family lock wait: the
        // termination must never predate any newly committed descendant session.
        jdbc.update("UPDATE "+F+" SET revoked_at=clock_timestamp()"
                +" WHERE id=? AND revoked_at IS NULL",familyId);
        jdbc.update("UPDATE "+S+" s SET status='REVOKED', ended_at=clock_timestamp()"
                        +" WHERE status='ACTIVE' AND ended_at IS NULL"
                        +" AND EXISTS (SELECT 1 FROM "+T+" t"
                        +" WHERE t.family_id=? AND t.session_id=s.id)",familyId);
    }
    @Override
    public void logout(String familyId,byte[] digest,Instant at) {
        owner();
        var f=lock(familyId);
        if(f==null||f.revoked()) return;
        Boolean known=jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM "+T+
                " WHERE family_id=? AND digest=?)",Boolean.class,familyId,digest.clone());
        if(Boolean.TRUE.equals(known)) revokeFamilyLocked(familyId,at);
    }
    private static BadCredentialsException invalid() {
        return new BadCredentialsException("Invalid refresh credentials.");
    }
}
