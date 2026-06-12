/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologySnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
        id = normalize(id);
        snapshotCode = normalize(snapshotCode);
        snapshotPayload = normalize(snapshotPayload);
        approvedByWorkflowId = normalize(approvedByWorkflowId);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
