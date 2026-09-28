/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskMatrix
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidRiskValueException("RiskMatrix id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidRiskValueException("RiskMatrix code must not be blank.");
        }
        // HRA-051 required: matrixTypeId
        if (matrixTypeId == null || matrixTypeId.isBlank()) {
            throw new InvalidRiskValueException("RiskMatrix matrix type id must not be blank.");
        }
        // HRA-051 required: version
        if (version == null || version.isBlank()) {
            throw new InvalidRiskValueException("RiskMatrix version must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidRiskValueException("RiskMatrix status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidRiskValueException("RiskMatrix valid to must not be before valid from.");
        }

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
