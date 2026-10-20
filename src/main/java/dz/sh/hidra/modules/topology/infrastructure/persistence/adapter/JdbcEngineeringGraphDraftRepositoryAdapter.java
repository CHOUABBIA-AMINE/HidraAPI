/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcEngineeringGraphDraftRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Stores immutable graph snapshots within the existing protected engineering-draft transaction.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.EngineeringGraphDraftRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * A narrow persistence adapter, not an independently authorized aggregate.
 * The existing draft service/coordinator owns authorization, version CAS, publication role,
 * the provisional SQL permit, 34 physical relation fences, and final materialized guard.
 */
@Repository
public final class JdbcEngineeringGraphDraftRepositoryAdapter implements EngineeringGraphDraftRepositoryPort {
    // A single, exact DML template must also be pinned independently by the coordinator.
    public static final String INSERT_SQL = "INSERT INTO public.hidra_topology_engineering_graph_snapshot "
        + "(draft_id,version,payload_format,canonical_payload,sha256,transaction_id) VALUES (?,?,?,?,?,?)";
    public static final String SELECT_AS_OF_SQL = "SELECT version,payload_format,canonical_payload,sha256 "
        + "FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=? AND version<=? "
        + "ORDER BY version DESC LIMIT 1";
    private final JdbcTemplate jdbc;
    private final ProtectedTransactionExecutor executor;
    private final EngineeringGraphDraftCodec codec = new EngineeringGraphDraftCodec();

    public JdbcEngineeringGraphDraftRepositoryAdapter(JdbcTemplate jdbc, ProtectedTransactionExecutor executor) {
        this.jdbc = Objects.requireNonNull(jdbc);
        this.executor = Objects.requireNonNull(executor);
    }

    private void requireProtectedOwner(boolean requireObservedAdmission) {
        // Inward call from the one reviewed application aggregate, not an ad-hoc JDBC writer.
        if (!StackWalker.getInstance().walk(frames -> frames.anyMatch(frame -> frame.getClassName().equals(
                "dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationService")))
            || !TransactionSynchronizationManager.isActualTransactionActive()
            || TransactionSynchronizationManager.isCurrentTransactionReadOnly())
            throw new IllegalStateException("Protected graph draft owner required");
        // A READ must observe sealed admission; an INSERT runs in the narrower
        // executor-issued provisional phase, where requireAdmission() is intentionally invalid.
        // The protected data source independently rejects SQL outside its exact allowlist.
        if (requireObservedAdmission) executor.requireAdmission();
    }

    @Override
    public Snapshot asOf(UUID draftId, long requestedVersion) {
        requireProtectedOwner(true);
        Objects.requireNonNull(draftId);
        if (requestedVersion < 1) throw new IllegalArgumentException("Positive as-of version required");
        var snapshots = jdbc.query(SELECT_AS_OF_SQL, (rs, row) -> {
            long version = rs.getLong("version");
            String format = rs.getString("payload_format");
            byte[] payload = rs.getBytes("canonical_payload");
            String sha = rs.getString("sha256");
            if (!EngineeringGraphDraftCodec.FORMAT.equals(format)
                || !codec.sha256(payload).equals(sha)
                || !Arrays.equals(payload, codec.encode(codec.decode(payload))))
                throw new IllegalStateException("Invalid immutable graph evidence");
            return new Snapshot(version, sha, codec.decode(payload));
        }, draftId, requestedVersion);
        return snapshots.isEmpty() ? null : snapshots.get(0);
    }

    @Override
    public Snapshot append(UUID draftId, long newVersion, EngineeringGraphDraft graph, long transactionId) {
        requireProtectedOwner(false);
        Objects.requireNonNull(draftId);
        Objects.requireNonNull(graph);
        if (newVersion < 2 || transactionId < 1)
            throw new IllegalArgumentException("Invalid version or physical transaction");
        byte[] bytes = codec.encode(graph);
        String sha = codec.sha256(bytes);
        if (jdbc.update(INSERT_SQL, draftId, newVersion, EngineeringGraphDraftCodec.FORMAT,
                bytes, sha, transactionId) != 1)
            throw new IllegalStateException("Graph snapshot insertion was not unique");
        return new Snapshot(newVersion, sha, graph);
    }
}
