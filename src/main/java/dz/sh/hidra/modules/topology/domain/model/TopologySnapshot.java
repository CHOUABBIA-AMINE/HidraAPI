/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologySnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Versioned network snapshot.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
public record TopologySnapshot(
        String id,
        String snapshotCode,
        int versionNumber,
        SnapshotStatus status,
        String snapshotPayload,
        String approvedByWorkflowId,
        Instant capturedAt,
        Instant approvedAt,
        Instant createdAt
) {
    public TopologySnapshot {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("TopologySnapshot id must not be blank.");
        }
        // HRA-051 required: snapshotCode
        if (snapshotCode == null || snapshotCode.isBlank()) {
            throw new InvalidTopologyValueException("TopologySnapshot snapshot code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTopologyValueException("TopologySnapshot status must not be null.");
        }
        // HRA-051 required: capturedAt
        if (capturedAt == null) {
            throw new InvalidTopologyValueException("TopologySnapshot captured at must not be null.");
        }

        id = normalize(id);
        snapshotCode = normalize(snapshotCode);
        snapshotPayload = normalize(snapshotPayload);
        approvedByWorkflowId = normalize(approvedByWorkflowId);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
