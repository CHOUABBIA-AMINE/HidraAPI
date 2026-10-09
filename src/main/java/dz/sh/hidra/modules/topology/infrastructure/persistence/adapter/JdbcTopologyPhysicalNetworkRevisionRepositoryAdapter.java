/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Persists append-only physical revisions and verifies every exact-identity read.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.TopologyPhysicalNetworkRevisionRepositoryPort;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter implements TopologyPhysicalNetworkRevisionRepositoryPort {
    private final JdbcTemplate jdbc;
    private final TopologyPhysicalNetworkRevisionCodec codec = new TopologyPhysicalNetworkRevisionCodec();

    public JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter(JdbcTemplate jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc, "Topology JDBC template");
    }

    @Override
    @Transactional
    public TopologyPhysicalNetworkRevision append(TopologyPhysicalNetworkRevision revision) {
        if (revision == null) {
            throw invalid("Physical revision must be supplied.");
        }
        byte[] payload = codec.encode(revision);
        String digest = codec.sha256(payload);
        jdbc.update("""
                INSERT INTO hidra_topology_physical_network_revision
                    (source_id,revision_id,payload_format,canonical_payload,sha256)
                VALUES (?,?,?,?,?) ON CONFLICT (source_id,revision_id) DO NOTHING
                """, revision.sourceId(), revision.revisionId(), TopologyPhysicalNetworkRevisionCodec.FORMAT,
                payload, digest);
        var stored = findStored(revision.sourceId(), revision.revisionId())
                .orElseThrow(() -> invalid("Appended physical revision is unavailable."));
        if (!stored.payloadFormat().equals(TopologyPhysicalNetworkRevisionCodec.FORMAT)
                || !stored.sha256().equals(digest) || !Arrays.equals(codec.encode(stored.revision()), payload)) {
            throw invalid("Conflicting physical revision content for the same source/revision identity.");
        }
        return stored.revision();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TopologyPhysicalNetworkRevision> find(String sourceId, String revisionId) {
        return findStored(sourceId, revisionId).map(StoredRevision::revision);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StoredRevision> findStored(String sourceId, String revisionId) {
        String source = identity(sourceId);
        String revision = identity(revisionId);
        var rows = jdbc.query("""
                SELECT source_id,revision_id,payload_format,canonical_payload,sha256
                FROM hidra_topology_physical_network_revision WHERE source_id=? AND revision_id=?
                """, (row, number) -> {
                    String format = row.getString("payload_format");
                    byte[] payload = row.getBytes("canonical_payload");
                    String digest = row.getString("sha256");
                    if (!TopologyPhysicalNetworkRevisionCodec.FORMAT.equals(format)
                            || payload == null || !codec.sha256(payload).equals(digest)) {
                        throw invalid("Stored physical revision integrity check failed.");
                    }
                    var value = codec.decode(payload);
                    if (!source.equals(value.sourceId()) || !revision.equals(value.revisionId())
                            || !source.equals(row.getString("source_id")) || !revision.equals(row.getString("revision_id"))) {
                        throw invalid("Stored physical revision payload identity does not match its row key.");
                    }
                    return new StoredRevision(value, format, digest);
                }, source, revision);
        return rows.stream().findFirst();
    }

    private static String identity(String value) {
        if (value == null || value.isBlank() || value.trim().isEmpty()) {
            throw invalid("Physical revision lookup identity must not be blank.");
        }
        return value.trim();
    }

    private static InvalidTopologyValueException invalid(String message) {
        return new InvalidTopologyValueException(message);
    }
}
