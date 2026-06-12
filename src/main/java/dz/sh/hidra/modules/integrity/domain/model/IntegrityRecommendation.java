/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityRecommendation
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Recommendation for remediation or monitoring.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;

    /**
     * Recommendation for remediation or monitoring.
     *
         * @param id id
     * @param recommendationNumber recommendationNumber
     * @param sourceAssessmentId sourceAssessmentId
     * @param sourceDefectId sourceDefectId
     * @param recommendationTypeId recommendationTypeId
     * @param title title
     * @param description description
     * @param status status
     * @param priorityId priorityId
     * @param targetModule targetModule
     * @param targetReferenceId targetReferenceId
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     * @param dueAt dueAt
     * @param closedAt closedAt
     */
    public record IntegrityRecommendation(
            String id,
        String recommendationNumber,
        String sourceAssessmentId,
        String sourceDefectId,
        String recommendationTypeId,
        String title,
        String description,
        RecommendationStatus status,
        String priorityId,
        String targetModule,
        String targetReferenceId,
        String createdByActorId,
        Instant createdAt,
        Instant dueAt,
        Instant closedAt
    ) {

        public IntegrityRecommendation {
        id = normalize(id);
        recommendationNumber = normalize(recommendationNumber);
        sourceAssessmentId = normalize(sourceAssessmentId);
        sourceDefectId = normalize(sourceDefectId);
        recommendationTypeId = normalize(recommendationTypeId);
        title = normalize(title);
        description = normalize(description);
        priorityId = normalize(priorityId);
        targetModule = normalize(targetModule);
        targetReferenceId = normalize(targetReferenceId);
        createdByActorId = normalize(createdByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
