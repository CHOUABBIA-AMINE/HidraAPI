/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskReviewJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskReview.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.entity;

import dz.sh.hidra.modules.risk.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for RiskReview.
     */
    @Entity
    @Table(name = "hidra_risk_review")
    public class RiskReviewJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_assessment_id", nullable = false, length = 80)
    private String riskAssessmentId;

    @Column(name = "review_type_id", nullable = false, length = 80)
    private String reviewTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_status", nullable = false, length = 40)
    private RiskReviewStatus reviewStatus;

    @Column(name = "review_due_date", nullable = false)
    private Instant reviewDueDate;

    @Column(name = "reviewed_at", nullable = true)
    private Instant reviewedAt;

    @Column(name = "reviewed_by_actor_id", nullable = true, length = 80)
    private String reviewedByActorId;

    @Column(name = "reviewed_by_display_name_snapshot", nullable = true, length = 255)
    private String reviewedByDisplayNameSnapshot;

    @Column(name = "review_finding", nullable = true, columnDefinition = "text")
    private String reviewFinding;

    @Column(name = "rating_changed", nullable = false)
    private boolean ratingChanged;

    @Column(name = "previous_rating_id", nullable = true, length = 80)
    private String previousRatingId;

    @Column(name = "new_rating_id", nullable = true, length = 80)
    private String newRatingId;

    @Column(name = "next_review_due_date", nullable = true)
    private Instant nextReviewDueDate;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected RiskReviewJpaEntity() {
            // Required by JPA.
        }

        public RiskReviewJpaEntity(
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
            this.id = id;
        this.riskAssessmentId = riskAssessmentId;
        this.reviewTypeId = reviewTypeId;
        this.reviewStatus = reviewStatus;
        this.reviewDueDate = reviewDueDate;
        this.reviewedAt = reviewedAt;
        this.reviewedByActorId = reviewedByActorId;
        this.reviewedByDisplayNameSnapshot = reviewedByDisplayNameSnapshot;
        this.reviewFinding = reviewFinding;
        this.ratingChanged = ratingChanged;
        this.previousRatingId = previousRatingId;
        this.newRatingId = newRatingId;
        this.nextReviewDueDate = nextReviewDueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String riskAssessmentId() {
        return riskAssessmentId;
    }


    public String reviewTypeId() {
        return reviewTypeId;
    }


    public RiskReviewStatus reviewStatus() {
        return reviewStatus;
    }


    public Instant reviewDueDate() {
        return reviewDueDate;
    }


    public Instant reviewedAt() {
        return reviewedAt;
    }


    public String reviewedByActorId() {
        return reviewedByActorId;
    }


    public String reviewedByDisplayNameSnapshot() {
        return reviewedByDisplayNameSnapshot;
    }


    public String reviewFinding() {
        return reviewFinding;
    }


    public boolean ratingChanged() {
        return ratingChanged;
    }


    public String previousRatingId() {
        return previousRatingId;
    }


    public String newRatingId() {
        return newRatingId;
    }


    public Instant nextReviewDueDate() {
        return nextReviewDueDate;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
