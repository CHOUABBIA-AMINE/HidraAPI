/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Pipeline
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Physical or logical pipeline within a system.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;
public record Pipeline(
        String id,
        String pipelineSystemId,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        PipelineType pipelineType,
        BigDecimal nominalDiameter,
        String diameterUnitCode,
        BigDecimal designPressure,
        String pressureUnitCode,
        BigDecimal totalLengthKm,
        TopologyStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public Pipeline {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("Pipeline id must not be blank.");
        }
        // HRA-051 required: pipelineSystemId
        if (pipelineSystemId == null || pipelineSystemId.isBlank()) {
            throw new InvalidTopologyValueException("Pipeline pipeline system id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidTopologyValueException("Pipeline code must not be blank.");
        }
        // HRA-051 required: pipelineType
        if (pipelineType == null) {
            throw new InvalidTopologyValueException("Pipeline pipeline type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTopologyValueException("Pipeline status must not be null.");
        }

        id = normalize(id);
        pipelineSystemId = normalize(pipelineSystemId);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        diameterUnitCode = normalize(diameterUnitCode);
        pressureUnitCode = normalize(pressureUnitCode);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
