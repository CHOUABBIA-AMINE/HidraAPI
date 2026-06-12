/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskReview
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Periodic review of a risk assessment or register item.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import dz.sh.hidra.modules.risk.domain.value.*;
import java.time.Instant;

    /**
     * Periodic review of a risk assessment or register item.
     *
         * @param id id
     * @param riskAssessmentId riskAssessmentId
     * @param reviewTypeId reviewTypeId
     * @param reviewStatus reviewStatus
     * @param reviewDueDate reviewDueDate
     * @param reviewedAt reviewedAt
     * @param reviewedByActorId reviewedByActorId
     * @param reviewedByDisplayNameSnapshot reviewedByDisplayNameSnapshot
     * @param reviewFinding reviewFinding
     * @param ratingChanged ratingChanged
     * @param previousRatingId previousRatingId
     * @param newRatingId newRatingId
     * @param nextReviewDueDate nextReviewDueDate
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskReview(
            String id,
        String riskAssessmentId,
        String reviewTypeId,
        RiskReviewStatus reviewStatus,
        Instant reviewDueDate,
        Instant reviewedAt,
        String reviewedByActorId,
        String reviewedByDisplayNameSnapshot,
        String reviewFinding,
        boolean ratingChanged,
        String previousRatingId,
        String newRatingId,
        Instant nextReviewDueDate,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskReview {
        id = normalize(id);
        riskAssessmentId = normalize(riskAssessmentId);
        reviewTypeId = normalize(reviewTypeId);
        reviewedByActorId = normalize(reviewedByActorId);
        reviewedByDisplayNameSnapshot = normalize(reviewedByDisplayNameSnapshot);
        reviewFinding = normalize(reviewFinding);
        previousRatingId = normalize(previousRatingId);
        newRatingId = normalize(newRatingId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
