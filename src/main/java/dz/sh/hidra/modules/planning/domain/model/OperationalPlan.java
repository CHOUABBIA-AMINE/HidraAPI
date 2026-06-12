/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlan
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
