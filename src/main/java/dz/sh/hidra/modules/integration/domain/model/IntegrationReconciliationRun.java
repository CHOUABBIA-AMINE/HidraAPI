/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationReconciliationRun
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Reconciliation run comparing Hidra and external records.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Reconciliation run comparing Hidra and external records.
     *
         * @param id id
     * @param externalSystemId externalSystemId
     * @param jobDefinitionId jobDefinitionId
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param reconciliationPeriodStart reconciliationPeriodStart
     * @param reconciliationPeriodEnd reconciliationPeriodEnd
     * @param status status
     * @param hidraCount hidraCount
     * @param externalCount externalCount
     * @param matchedCount matchedCount
     * @param missingInHidraCount missingInHidraCount
     * @param missingExternallyCount missingExternallyCount
     * @param mismatchCount mismatchCount
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param createdAt createdAt
     */
    public record IntegrationReconciliationRun(
            String id,
        String externalSystemId,
        String jobDefinitionId,
        String targetModule,
        String targetTypeCode,
        Instant reconciliationPeriodStart,
        Instant reconciliationPeriodEnd,
        ReconciliationRunStatus status,
        long hidraCount,
        long externalCount,
        long matchedCount,
        long missingInHidraCount,
        long missingExternallyCount,
        long mismatchCount,
        Instant startedAt,
        Instant completedAt,
        Instant createdAt
    ) {

        public IntegrationReconciliationRun {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationReconciliationRun id must not be blank.");
        }
        // HRA-051 required: externalSystemId
        if (externalSystemId == null || externalSystemId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationReconciliationRun external system id must not be blank.");
        }
        // HRA-051 required: targetTypeCode
        if (targetTypeCode == null || targetTypeCode.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationReconciliationRun target type code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIntegrationValueException("IntegrationReconciliationRun status must not be null.");
        }
        // HRA-051 required: startedAt
        if (startedAt == null) {
            throw new InvalidIntegrationValueException("IntegrationReconciliationRun started at must not be null.");
        }

        id = normalize(id);
        externalSystemId = normalize(externalSystemId);
        jobDefinitionId = normalize(jobDefinitionId);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
