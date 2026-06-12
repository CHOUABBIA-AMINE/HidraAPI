/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemFacility
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Association between system and facility.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
public record PipelineSystemFacility(
        String id,
        String pipelineSystemId,
        String facilityId,
        String relationshipCode,
        Instant validFrom,
        Instant validTo,
        TopologyStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public PipelineSystemFacility {
        id = normalize(id);
        pipelineSystemId = normalize(pipelineSystemId);
        facilityId = normalize(facilityId);
        relationshipCode = normalize(relationshipCode);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
