/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationReconciliationIssue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Single reconciliation discrepancy.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Single reconciliation discrepancy.
     *
         * @param id id
     * @param reconciliationRunId reconciliationRunId
     * @param issueType issueType
     * @param externalObjectType externalObjectType
     * @param externalObjectId externalObjectId
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param targetId targetId
     * @param fieldPath fieldPath
     * @param hidraValueSnapshot hidraValueSnapshot
     * @param externalValueSnapshot externalValueSnapshot
     * @param severity severity
     * @param status status
     * @param resolutionComment resolutionComment
     * @param createdAt createdAt
     * @param resolvedAt resolvedAt
     */
    public record IntegrationReconciliationIssue(
            String id,
        String reconciliationRunId,
        String issueType,
        String externalObjectType,
        String externalObjectId,
        String targetModule,
        String targetTypeCode,
        String targetId,
        String fieldPath,
        String hidraValueSnapshot,
        String externalValueSnapshot,
        IntegrationSeverity severity,
        ReconciliationIssueStatus status,
        String resolutionComment,
        Instant createdAt,
        Instant resolvedAt
    ) {

        public IntegrationReconciliationIssue {
        id = normalize(id);
        reconciliationRunId = normalize(reconciliationRunId);
        issueType = normalize(issueType);
        externalObjectType = normalize(externalObjectType);
        externalObjectId = normalize(externalObjectId);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        targetId = normalize(targetId);
        fieldPath = normalize(fieldPath);
        hidraValueSnapshot = normalize(hidraValueSnapshot);
        externalValueSnapshot = normalize(externalValueSnapshot);
        resolutionComment = normalize(resolutionComment);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
