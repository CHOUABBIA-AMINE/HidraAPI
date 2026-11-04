/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcFoundationIdentityLifecycleAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Atomically updates protected Identity owner rows with current authority, version checks and immutable audit.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.in.FoundationIdentityLifecycleUseCase.*;
import dz.sh.hidra.modules.identity.application.port.out.FoundationIdentityLifecyclePersistencePort;
import dz.sh.hidra.modules.identity.domain.service.IdentityLifecyclePolicy;
import dz.sh.hidra.modules.identity.domain.value.GrantStatus;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.NoSuchElementException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Human administration is infrequent. Its fixed Identity owner-table lock provides a
 * simple serial boundary against grant and user writers, including last-admin races.
 * No independent transaction, dynamic SQL identifier or business authority shortcut.
 */
@Repository
public class JdbcFoundationIdentityLifecycleAdapter implements FoundationIdentityLifecyclePersistencePort {
    private static final String USER = "public.hidra_identity_user";
    private static final String CREDENTIAL = "public.hidra_identity_local_credential";
    private static final String SESSION = "public.hidra_identity_login_session";
    private static final String USER_ROLE = "public.hidra_identity_user_role_grant";
    private static final String USER_PERMISSION = "public.hidra_identity_user_permission_grant";
    private static final String ROLE_PERMISSION = "public.hidra_identity_role_permission_grant";

    private final JdbcTemplate jdbc;
    public JdbcFoundationIdentityLifecycleAdapter(JdbcTemplate jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
    }

    @Override
    public UserState readUser(String id, String actorId) {
        readAuthorize(actorId);
        var results = jdbc.query("SELECT id,status,updated_at FROM " + USER + " WHERE id=?",
                (rs,n) -> new UserState(rs.getString(1),rs.getString(2),rs.getTimestamp(3).toInstant()),id);
        if (results.isEmpty()) throw new NoSuchElementException("Unknown Identity user.");
        if (results.size() != 1) throw new IllegalStateException("Corrupt Identity user cardinality.");
        return results.getFirst();
    }

    @Override
    public CredentialState readCredential(String id, String actorId) {
        readAuthorize(actorId);
        var users = jdbc.queryForList("SELECT id FROM " + USER + " WHERE id=?",id);
        if (users.isEmpty()) throw new NoSuchElementException("Unknown Identity user.");
        var results = jdbc.query("SELECT security_version,password_changed_at FROM " + CREDENTIAL + " WHERE user_id=?",
                (rs,n) -> new CredentialState(id,rs.getLong(1),
                        rs.getTimestamp(2)==null?null:rs.getTimestamp(2).toInstant()),id);
        if (results.isEmpty()) return new CredentialState(id,0,null);
        if (results.size() != 1) throw new IllegalStateException("Corrupt LOCAL credential cardinality.");
        return results.getFirst();
    }

    private void readAuthorize(String actorId) {
        if (!isCurrentGlobalAdministrator(actorId))
            throw new org.springframework.security.access.AccessDeniedException("Current administrator grant required.");
    }

    @Override
    public UserState changeUser(String userId, Instant expected, boolean activate, String reason, String actorId) {
        lockAndAuthorize(actorId);
        Map<String,Object> before = user(userId);
        String previous = (String) before.get("status");
        UserStatus target = activate ? UserStatus.ACTIVE : UserStatus.DISABLED;
        IdentityLifecyclePolicy.transition(UserStatus.valueOf(previous), target);
        IdentityLifecyclePolicy.expected(expected, instant(before.get("updated_at")));
        if (activate) {
            Integer activeCredentials = jdbc.queryForObject(
                    "SELECT count(*) FROM " + CREDENTIAL
                    + " WHERE user_id=? AND credential_status='ACTIVE' AND password_hash IS NOT NULL"
                    + " AND length(password_hash)>=40", Integer.class, userId);
            if (activeCredentials == null || activeCredentials != 1)
                throw new IllegalStateException("An ACTIVE LOCAL credential is required.");
        } else {
            preventLastAdministrator(userId);
        }
        var current = jdbc.queryForObject(
                "UPDATE " + USER
                + " SET status=?,updated_at=GREATEST(clock_timestamp(),updated_at+interval '1 microsecond'),"
                + (activate ? "activated_at=clock_timestamp()" : "disabled_at=clock_timestamp()")
                + " WHERE id=? AND status=? RETURNING id,status,updated_at",
                (rs,n) -> new UserState(rs.getString(1),rs.getString(2),rs.getTimestamp(3).toInstant()),
                target.name(),userId,previous);
        if (current == null) throw new IllegalStateException("Lifecycle update failed.");
        if (!activate) revokeSessions(userId);
        audit(actorId,"USER",userId,activate ? "ACTIVATE" : "DEACTIVATE",reason);
        return current;
    }

    @Override
    public CredentialState setCredential(String userId, String hash, long expectedVersion,
            String reason, String actorId) {
        lockAndAuthorize(actorId);
        Map<String,Object> state = user(userId);
        String status = (String) state.get("status");
        if (!"REGISTERED".equals(status) && !"ACTIVE".equals(status))
            throw new IllegalStateException("Only registered or active LOCAL identities may receive credentials.");
        if (!"HUMAN".equals(state.get("user_type")))
            throw new IllegalStateException("Only human LOCAL identities may receive credentials.");
        if (hash == null || !hash.startsWith("$2") || hash.length() > 255)
            throw new IllegalArgumentException("A valid adaptive password hash is required.");

        List<Map<String,Object>> rows = jdbc.queryForList(
                "SELECT id,security_version FROM " + CREDENTIAL + " WHERE user_id=? FOR UPDATE",userId);
        if (rows.size() > 1) throw new IllegalStateException("Non-unique LOCAL credential.");
        if (rows.isEmpty()) {
            if (expectedVersion != 0) throw new IllegalStateException("Stale credential generation.");
            jdbc.update("INSERT INTO " + CREDENTIAL
                    + "(id,user_id,password_hash,credential_status,password_changed_at,created_at,updated_at)"
                    + " VALUES (?,?,?,'ACTIVE',clock_timestamp(),clock_timestamp(),clock_timestamp())",
                    UUID.randomUUID().toString(),userId,hash);
        } else {
            if (((Number)rows.getFirst().get("security_version")).longValue() != expectedVersion)
                throw new IllegalStateException("Stale credential generation.");
            int changed = jdbc.update("UPDATE " + CREDENTIAL
                    + " SET password_hash=?,credential_status='ACTIVE',password_changed_at=clock_timestamp(),"
                    + "updated_at=clock_timestamp() WHERE id=? AND security_version=?",
                    hash,rows.getFirst().get("id"),expectedVersion);
            if (changed != 1) throw new IllegalStateException("Stale credential generation.");
        }
        revokeSessions(userId);
        var current = jdbc.queryForObject(
                "SELECT security_version,password_changed_at FROM " + CREDENTIAL + " WHERE user_id=?",
                (rs,n) -> new CredentialState(userId,rs.getLong(1),rs.getTimestamp(2).toInstant()),userId);
        if (current == null) throw new IllegalStateException("Credential update failed.");
        audit(actorId,"USER",userId,"CREDENTIAL_RESET",reason);
        return current;
    }

    @Override
    public GrantState revokeGrant(GrantKind kind, String grantId, Instant expectedCreated,
            String reason, String actorId) {
        lockAndAuthorize(actorId);
        String table = switch (Objects.requireNonNull(kind)) {
            case USER_ROLE -> USER_ROLE;
            case USER_PERMISSION -> USER_PERMISSION;
            case ROLE_PERMISSION -> ROLE_PERMISSION;
        };
        List<Map<String,Object>> rows = jdbc.queryForList(
                "SELECT id,status,created_at FROM " + table + " WHERE id=? FOR UPDATE",grantId);
        if (rows.isEmpty()) throw new NoSuchElementException("Unknown Identity grant.");
        if (rows.size() != 1) throw new IllegalStateException("Corrupt Identity grant.");
        var row = rows.getFirst();
        IdentityLifecyclePolicy.revoke(GrantStatus.valueOf((String) row.get("status")));
        // ACTIVE -> REVOKED is itself a locked one-way CAS.
        if (expectedCreated != null)
            IdentityLifecyclePolicy.expected(expectedCreated,instant(row.get("created_at")));
        if (kind == GrantKind.USER_ROLE) {
            var role = jdbc.queryForList(
                    "SELECT g.user_id,r.code,g.scope_type,g.scope_reference_id"
                    + " FROM " + USER_ROLE + " g JOIN public.hidra_identity_role r ON r.id=g.role_id"
                    + " WHERE g.id=?",grantId);
            if (role.size() != 1) throw new IllegalStateException("Corrupt Identity role reference.");
            var value = role.getFirst();
            if ("HIDRA_ADMIN".equals(value.get("code")) && "GLOBAL".equals(value.get("scope_type"))
                    && value.get("scope_reference_id") == null)
                preventLastAdministrator((String)value.get("user_id"));
        }
        int changed = jdbc.update("UPDATE " + table
                + " SET status='REVOKED',revoked_at=clock_timestamp(),revoked_reason=?"
                + " WHERE id=? AND status='ACTIVE'",reason,grantId);
        if (changed != 1) throw new IllegalStateException("Grant revocation conflict.");
        Instant at = jdbc.queryForObject("SELECT revoked_at FROM " + table + " WHERE id=?",
                (rs,n) -> rs.getTimestamp(1).toInstant(),grantId);
        audit(actorId,kind.name(),grantId,"REVOKE",reason);
        return new GrantState(grantId,"REVOKED",at);
    }

    private Map<String,Object> user(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("User id is required.");
        var rows = jdbc.queryForList("SELECT id,status,user_type,updated_at FROM " + USER + " WHERE id=? FOR UPDATE",id);
        if (rows.isEmpty()) throw new NoSuchElementException("Unknown Identity user.");
        if (rows.size() != 1) throw new IllegalStateException("Corrupt Identity user cardinality.");
        return rows.getFirst();
    }

    private void lockAndAuthorize(String actorId) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly())
            throw new IllegalStateException("Writable lifecycle owner transaction required.");
        // Serialize all user/grant administration mutations before sampling authority.
        jdbc.execute("LOCK TABLE " + USER + ",public.hidra_identity_role," + USER_ROLE + ","
                + USER_PERMISSION + "," + ROLE_PERMISSION + " IN SHARE ROW EXCLUSIVE MODE");
        if (!isCurrentGlobalAdministrator(actorId))
            throw new org.springframework.security.access.AccessDeniedException("Current administrator grant required.");
    }

    private boolean isCurrentGlobalAdministrator(String actor) {
        if (actor == null || actor.isBlank()) return false;
        Boolean present = jdbc.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM " + USER + " u"
                + " JOIN " + USER_ROLE + " g ON g.user_id=u.id"
                + " JOIN public.hidra_identity_role r ON r.id=g.role_id"
                + " WHERE u.id=? AND u.status='ACTIVE' AND u.user_type='HUMAN'"
                + " AND (u.locked_until IS NULL OR u.locked_until<=clock_timestamp())"
                + " AND r.code='HIDRA_ADMIN' AND r.status='ACTIVE' AND r.role_type='ADMIN'"
                + " AND g.status='ACTIVE' AND g.scope_type='GLOBAL' AND g.scope_reference_id IS NULL"
                + " AND g.revoked_at IS NULL AND g.valid_from<=clock_timestamp()"
                + " AND (g.valid_to IS NULL OR g.valid_to>clock_timestamp()))",Boolean.class,actor);
        return Boolean.TRUE.equals(present);
    }

    private void preventLastAdministrator(String userId) {
        if (!isCurrentGlobalAdministrator(userId)) return;
        Integer other = jdbc.queryForObject(
                "SELECT count(DISTINCT u.id) FROM " + USER + " u"
                + " JOIN " + USER_ROLE + " g ON g.user_id=u.id"
                + " JOIN public.hidra_identity_role r ON r.id=g.role_id"
                + " WHERE u.id<>? AND u.status='ACTIVE' AND u.user_type='HUMAN'"
                + " AND (u.locked_until IS NULL OR u.locked_until<=clock_timestamp())"
                + " AND r.code='HIDRA_ADMIN' AND r.role_type='ADMIN' AND r.status='ACTIVE'"
                + " AND g.status='ACTIVE' AND g.scope_type='GLOBAL' AND g.scope_reference_id IS NULL"
                + " AND g.revoked_at IS NULL AND g.valid_from<=clock_timestamp()"
                + " AND (g.valid_to IS NULL OR g.valid_to>clock_timestamp())",Integer.class,userId);
        if (other == null || other == 0)
            throw new IllegalStateException("The last usable administrator must be preserved.");
    }

    private void revokeSessions(String userId) {
        jdbc.update("UPDATE " + SESSION
                + " SET status='REVOKED',ended_at=clock_timestamp()"
                + " WHERE user_id=? AND status='ACTIVE'",userId);
    }

    private void audit(String actorId,String targetType,String targetId,String action,String reason) {
        jdbc.update("INSERT INTO public.hidra_identity_lifecycle_event"
                + "(id,actor_id,target_type,target_id,action,reason,occurred_at)"
                + " VALUES (?,?,?,?,?,?,clock_timestamp())",
                UUID.randomUUID().toString(),actorId,targetType,targetId,action,reason);
    }

    private static Instant instant(Object value) {
        return value instanceof Timestamp t ? t.toInstant() : null;
    }
}
