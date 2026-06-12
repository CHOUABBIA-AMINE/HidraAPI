/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyGraphProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.projection
 *
 * @Description : Graph projection for topology visualization.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.projection;

import dz.sh.hidra.modules.topology.domain.value.ProjectionType;
import java.time.Instant;
public record TopologyGraphProjection(String projectionCode, ProjectionType projectionType, String snapshotId, String graphPayload, Instant generatedAt) { }
