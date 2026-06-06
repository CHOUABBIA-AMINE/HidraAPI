/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO representing a pipeline with trilingual labels.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Represents pipeline data returned by topology use cases.
 *
 * <p>Business role:
 * This DTO describes a physical pipeline belonging to a pipeline system.
 *
 * <p>Architecture role:
 * This application DTO is framework-independent and exposes pipeline business labels as first-class
 * Arabic, French, and English fields.
 */
public record PipelineDto(
        String pipelineId,
        String pipelineSystemId,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String descriptionAr,
        String descriptionFr,
        String descriptionEn,
        String productType,
        BigDecimal nominalDiameterInches,
        BigDecimal designLengthKm,
        String status,
        Instant createdAt,
        Instant updatedAt) {
}
