/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskRegister
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Governed container for risks within a scope.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
import dz.sh.hidra.modules.risk.domain.value.*;
import java.time.Instant;

    /**
     * Governed container for risks within a scope.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param description description
     * @param registerTypeId registerTypeId
     * @param ownerOrganizationUnitId ownerOrganizationUnitId
     * @param ownerOrganizationUnitNameSnapshot ownerOrganizationUnitNameSnapshot
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param scopeCodeSnapshot scopeCodeSnapshot
     * @param scopeLabelSnapshot scopeLabelSnapshot
     * @param status status
     * @param reviewFrequencyId reviewFrequencyId
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param createdByActorId createdByActorId
     * @param createdByDisplayNameSnapshot createdByDisplayNameSnapshot
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskRegister(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String description,
        String registerTypeId,
        String ownerOrganizationUnitId,
        String ownerOrganizationUnitNameSnapshot,
        String scopeType,
        String scopeId,
        String scopeCodeSnapshot,
        String scopeLabelSnapshot,
        RiskRegisterStatus status,
        String reviewFrequencyId,
        Instant effectiveFrom,
        Instant effectiveTo,
        String createdByActorId,
        String createdByDisplayNameSnapshot,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskRegister {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidRiskValueException("RiskRegister id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidRiskValueException("RiskRegister code must not be blank.");
        }
        // HRA-051 required: registerTypeId
        if (registerTypeId == null || registerTypeId.isBlank()) {
            throw new InvalidRiskValueException("RiskRegister register type id must not be blank.");
        }
        // HMR-049 required: typed scope pair
        if (scopeType == null || scopeType.isBlank()) {
            throw new InvalidRiskValueException("RiskRegister scope type must not be blank.");
        }
        // HRA-051 required: scopeId
        if (scopeId == null || scopeId.isBlank()) {
            throw new InvalidRiskValueException("RiskRegister scope id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidRiskValueException("RiskRegister status must not be null.");
        }
        // HRA-051 order: effectiveFrom <= effectiveTo
        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new InvalidRiskValueException("RiskRegister effective to must not be before effective from.");
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        description = normalize(description);
        registerTypeId = normalize(registerTypeId);
        ownerOrganizationUnitId = normalize(ownerOrganizationUnitId);
        ownerOrganizationUnitNameSnapshot = normalize(ownerOrganizationUnitNameSnapshot);
        scopeType = normalize(scopeType);
        scopeId = normalize(scopeId);
        scopeCodeSnapshot = normalize(scopeCodeSnapshot);
        scopeLabelSnapshot = normalize(scopeLabelSnapshot);
        reviewFrequencyId = normalize(reviewFrequencyId);
        createdByActorId = normalize(createdByActorId);
        createdByDisplayNameSnapshot = normalize(createdByDisplayNameSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
