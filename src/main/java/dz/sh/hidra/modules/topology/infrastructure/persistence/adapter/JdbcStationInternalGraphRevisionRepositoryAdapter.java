/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcStationInternalGraphRevisionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Appends immutable station graph bytes and verifies every exact-identity read.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.StationInternalGraphRevisionRepositoryPort;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** Owner-private storage; no publication, company authorization or predecessor enforcement. */
@Repository
public class JdbcStationInternalGraphRevisionRepositoryAdapter implements StationInternalGraphRevisionRepositoryPort {
    private final JdbcTemplate jdbc;
    private final StationInternalGraphRevisionCodec codec = new StationInternalGraphRevisionCodec();

    public JdbcStationInternalGraphRevisionRepositoryAdapter(JdbcTemplate jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc, "Topology JDBC template");
    }

    @Override
    @Transactional
    public StationInternalGraphRevision append(StationInternalGraphRevision revision) {
        byte[] payload = codec.encode(revision);
        String digest = codec.sha256(payload);
        jdbc.update("""
                INSERT INTO hidra_topology_station_graph_revision
                    (facility_id,revision_id,payload_format,canonical_payload,sha256)
                VALUES (?,?,?,?,?) ON CONFLICT (facility_id,revision_id) DO NOTHING
                """, revision.facilityId(), revision.revisionId(), StationInternalGraphRevisionCodec.FORMAT,
                payload, digest);
        var stored = findStored(revision.facilityId(), revision.revisionId())
                .orElseThrow(() -> invalid("Appended station graph revision is unavailable."));
        if (!stored.payloadFormat().equals(StationInternalGraphRevisionCodec.FORMAT)
                || !stored.sha256().equals(digest) || !Arrays.equals(codec.encode(stored.revision()), payload)) {
            throw invalid("Conflicting station graph content for the same facility/revision identity.");
        }
        return stored.revision();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StationInternalGraphRevision> find(String facilityId, String revisionId) {
        return findStored(facilityId, revisionId).map(StoredRevision::revision);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StoredRevision> findStored(String facilityId, String revisionId) {
        String facility = identity(facilityId);
        String revision = identity(revisionId);
        var rows = jdbc.query("""
                SELECT facility_id,revision_id,payload_format,canonical_payload,sha256
                FROM hidra_topology_station_graph_revision WHERE facility_id=? AND revision_id=?
                """, (row, number) -> {
                    String format = row.getString("payload_format");
                    byte[] payload = row.getBytes("canonical_payload");
                    String digest = row.getString("sha256");
                    if (!StationInternalGraphRevisionCodec.FORMAT.equals(format) || payload == null
                            || payload.length == 0 || !codec.sha256(payload).equals(digest)) {
                        throw invalid("Stored station graph revision integrity check failed.");
                    }
                    var value = codec.decode(payload);
                    if (!facility.equals(value.facilityId()) || !revision.equals(value.revisionId())
                            || !facility.equals(row.getString("facility_id"))
                            || !revision.equals(row.getString("revision_id"))) {
                        throw invalid("Stored station graph payload identity does not match its row key.");
                    }
                    return new StoredRevision(value, format, digest);
                }, facility, revision);
        return rows.stream().findFirst();
    }

    private static String identity(String value) {
        if (value == null || value.isBlank() || value.trim().isEmpty()) {
            throw invalid("Station graph lookup identity must not be blank.");
        }
        return value.trim();
    }

    private static InvalidTopologyValueException invalid(String message) {
        return new InvalidTopologyValueException(message);
    }
}
