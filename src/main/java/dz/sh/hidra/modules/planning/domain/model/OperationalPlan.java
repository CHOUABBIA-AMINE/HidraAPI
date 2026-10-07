/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlan
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : Main plan/program header for a topology/product scope.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.value.*;
import java.time.Instant;

    /**
     * Main plan/program header for a topology/product scope.
     *
         * @param id id
     * @param periodId periodId
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param planTypeId planTypeId
     * @param productTypeId productTypeId
     * @param topologyScopeType topologyScopeType
     * @param topologyScopeId topologyScopeId
     * @param topologyScopeCode topologyScopeCode
     * @param topologyScopeNameSnapshot topologyScopeNameSnapshot
     * @param responsibleOrganizationUnitId responsibleOrganizationUnitId
     * @param status status
     * @param currentRevisionId currentRevisionId
     * @param approvedRevisionId approvedRevisionId
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record OperationalPlan(
            String id,
        String periodId,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String planTypeId,
        String productTypeId,
        String topologyScopeType,
        String topologyScopeId,
        String topologyScopeCode,
        String topologyScopeNameSnapshot,
        String responsibleOrganizationUnitId,
        OperationalPlanStatus status,
        String currentRevisionId,
        String approvedRevisionId,
        String createdByActorId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public OperationalPlan {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPlanningValueException("OperationalPlan id must not be blank.");
        }
        // HRA-051 required: periodId
        if (periodId == null || periodId.isBlank()) {
            throw new InvalidPlanningValueException("OperationalPlan period id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidPlanningValueException("OperationalPlan code must not be blank.");
        }
        // HRA-051 required: planTypeId
        if (planTypeId == null || planTypeId.isBlank()) {
            throw new InvalidPlanningValueException("OperationalPlan plan type id must not be blank.");
        }
        // HRA-051 required: topologyScopeId
        if (topologyScopeId == null || topologyScopeId.isBlank()) {
            throw new InvalidPlanningValueException("OperationalPlan topology scope id must not be blank.");
        }
        // HRA-051 required: topologyScopeCode
        if (topologyScopeCode == null || topologyScopeCode.isBlank()) {
            throw new InvalidPlanningValueException("OperationalPlan topology scope code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPlanningValueException("OperationalPlan status must not be null.");
        }
        // HRA-051 required: createdByActorId
        if (createdByActorId == null || createdByActorId.isBlank()) {
            throw new InvalidPlanningValueException("OperationalPlan created by actor id must not be blank.");
        }

        if (nameFr == null || nameFr.isBlank()) {
            throw new InvalidPlanningValueException("OperationalPlan French name must not be blank.");
        }
        if (topologyScopeType == null || topologyScopeType.isBlank()) {
            throw new InvalidPlanningValueException("OperationalPlan topology scope type must not be blank.");
        }
        id = normalize(id);
        periodId = normalize(periodId);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        planTypeId = normalize(planTypeId);
        productTypeId = normalize(productTypeId);
        topologyScopeType = normalize(topologyScopeType);
        topologyScopeId = normalize(topologyScopeId);
        topologyScopeCode = normalize(topologyScopeCode);
        topologyScopeNameSnapshot = normalize(topologyScopeNameSnapshot);
        responsibleOrganizationUnitId = normalize(responsibleOrganizationUnitId);
        currentRevisionId = normalize(currentRevisionId);
        approvedRevisionId = normalize(approvedRevisionId);
        createdByActorId = normalize(createdByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
