/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Pipeline system summary DTO with wire-safe classification catalog fields.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import dz.sh.hidra.modules.topology.domain.value.PipelineSystemType;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

public record PipelineSystemSummaryDto(
        String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String systemTypeId,
        String systemTypeCode,
        String systemTypeNameAr,
        String systemTypeNameFr,
        String systemTypeNameEn,
        TopologyStatus status
) {

    public PipelineSystemSummaryDto(
            String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            PipelineSystemType systemType,
            TopologyStatus status
    ) {
        this(
                id,
                code,
                nameAr,
                nameFr,
                nameEn,
                systemType.id(),
                systemType.code(),
                systemType.nameAr(),
                systemType.nameFr(),
                systemType.nameEn(),
                status
        );
    }
}
