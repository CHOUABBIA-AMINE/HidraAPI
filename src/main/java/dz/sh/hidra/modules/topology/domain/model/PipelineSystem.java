/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystem
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Logical transportation system grouping pipelines and facilities.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
public record PipelineSystem(
        String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        PipelineSystemType systemType,
        TopologyStatus status,
        String description,
        Instant commissionedAt,
        Instant retiredAt,
        Instant createdAt,
        Instant updatedAt
) {
    public PipelineSystem {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        description = normalize(description);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
