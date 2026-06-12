/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Pipeline
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
