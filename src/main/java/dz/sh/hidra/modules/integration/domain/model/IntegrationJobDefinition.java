/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobDefinition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Repeatable import, export, sync, replay, reconciliation, or health-check job.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Repeatable import, export, sync, replay, reconciliation, or health-check job.
     *
         * @param id id
     * @param code code
     * @param nameFr nameFr
     * @param nameAr nameAr
     * @param nameEn nameEn
     * @param connectorInstanceId connectorInstanceId
     * @param mappingProfileId mappingProfileId
     * @param jobTypeId jobTypeId
     * @param direction direction
     * @param targetModule targetModule
     * @param scheduleExpression scheduleExpression
     * @param manualRunAllowed manualRunAllowed
     * @param retryPolicyId retryPolicyId
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record IntegrationJobDefinition(
            String id,
        String code,
        String nameFr,
        String nameAr,
        String nameEn,
        String connectorInstanceId,
        String mappingProfileId,
        String jobTypeId,
        IntegrationDirection direction,
        String targetModule,
        String scheduleExpression,
        boolean manualRunAllowed,
        String retryPolicyId,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public IntegrationJobDefinition {
        id = normalize(id);
        code = normalize(code);
        nameFr = normalize(nameFr);
        nameAr = normalize(nameAr);
        nameEn = normalize(nameEn);
        connectorInstanceId = normalize(connectorInstanceId);
        mappingProfileId = normalize(mappingProfileId);
        jobTypeId = normalize(jobTypeId);
        targetModule = normalize(targetModule);
        scheduleExpression = normalize(scheduleExpression);
        retryPolicyId = normalize(retryPolicyId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
