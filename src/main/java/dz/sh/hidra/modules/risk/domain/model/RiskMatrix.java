/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskMatrix
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Scoring method for likelihood/consequence mapping.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import dz.sh.hidra.modules.risk.domain.value.*;
import java.time.Instant;

    /**
     * Scoring method for likelihood/consequence mapping.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param description description
     * @param matrixTypeId matrixTypeId
     * @param version version
     * @param status status
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdByActorId createdByActorId
     * @param approvedByActorId approvedByActorId
     * @param approvedAt approvedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskMatrix(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String description,
        String matrixTypeId,
        String version,
        RiskMatrixStatus status,
        Instant validFrom,
        Instant validTo,
        String createdByActorId,
        String approvedByActorId,
        Instant approvedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskMatrix {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        description = normalize(description);
        matrixTypeId = normalize(matrixTypeId);
        version = normalize(version);
        createdByActorId = normalize(createdByActorId);
        approvedByActorId = normalize(approvedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
