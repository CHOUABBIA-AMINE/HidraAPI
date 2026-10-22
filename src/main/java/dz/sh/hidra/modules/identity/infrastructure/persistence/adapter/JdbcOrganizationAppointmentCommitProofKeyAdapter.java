/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcOrganizationAppointmentCommitProofKeyAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-22
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Authenticates transaction-bound appointment commit evidence within Identity.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.infrastructure.configuration.OrganizationAppointmentCommitProofProperties;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** Metadata only: the database never discloses private key bytes or a MAC. */
@Repository
public class JdbcOrganizationAppointmentCommitProofKeyAdapter {
    private final JdbcTemplate jdbc;
    private final OrganizationAppointmentCommitProofProperties properties;
    public JdbcOrganizationAppointmentCommitProofKeyAdapter(JdbcTemplate jdbc,
            OrganizationAppointmentCommitProofProperties properties) {
        this.jdbc = Objects.requireNonNull(jdbc); this.properties = Objects.requireNonNull(properties);
    }
    public record Context(String keyId, long revision, UUID audience, String publisher,
            int backendPid, long transactionId) {
        public Context {
            if (keyId == null || revision <= 0 || audience == null || publisher == null
                    || backendPid <= 0 || transactionId <= 0) throw denied();
        }
        @Override public String toString() { return "CommitProofKeyContext[opaque]"; }
    }
    public Context acquire() { return metadata(true); }
    public void requireCurrent(Context retained) {
        if (retained == null || !retained.equals(metadata(false))) throw denied();
    }
    private Context metadata(boolean acquire) {
        if (!properties.isEnabled() || !TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly() || jdbc.getDataSource() == null
                || !(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder holder)
                || holder.isRollbackOnly()) throw denied();
        try {
            var rows = jdbc.query("SELECT key_id,key_revision,audience,publisher,backend_pid,transaction_id "
                    + "FROM public.hidra_identity_org_commit_key_context(?)",
                    (r, n) -> new Context(r.getString(1), r.getLong(2), r.getObject(3, UUID.class),
                            r.getString(4), r.getInt(5), r.getLong(6)), acquire);
            if (rows.size() != 1) throw denied();
            var context = rows.get(0);
            if (!context.keyId().equals(properties.getKeyId()) || context.revision() != properties.getKeyRevision())
                throw denied();
            return context;
        } catch (RuntimeException failure) { throw denied(); }
    }
    public static List<ProtectedRelationFence> requiredRelationFences() {
        return List.of(
                new ProtectedRelationFence("public.hidra_identity_org_commit_key_event", ProtectedRelationFence.Mode.ACCESS_SHARE),
                new ProtectedRelationFence("public.hidra_identity_org_commit_key_head", ProtectedRelationFence.Mode.SHARE),
                new ProtectedRelationFence("public.hidra_identity_org_commit_proof_use", ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
    }
    private static AccessDeniedException denied() { return new AccessDeniedException("Appointment commit proof key unavailable"); }
}

