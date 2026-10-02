/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityRecommendationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrityRecommendation.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integrity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IntegrityRecommendation.
     */
    @Entity
    @Table(name = "hidra_integrity_recommendation")
    public class IntegrityRecommendationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "recommendation_number", nullable = false, length = 80)
    private String recommendationNumber;

    @Column(name = "source_assessment_id", nullable = true, length = 80)
    private String sourceAssessmentId;

    @Column(name = "source_defect_id", nullable = true, length = 80)
    private String sourceDefectId;

    @Column(name = "recommendation_type_id", nullable = false, length = 80)
    private String recommendationTypeId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private RecommendationStatus status;

    @Column(name = "priority_id", nullable = true, length = 80)
    private String priorityId;

    @Column(name = "target_module", nullable = true, length = 80)
    private String targetModule;

    @Column(name = "target_reference_id", nullable = true, length = 80)
    private String targetReferenceId;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "due_at", nullable = true)
    private Instant dueAt;

    @Column(name = "closed_at", nullable = true)
    private Instant closedAt;

        protected IntegrityRecommendationJpaEntity() {
            // Required by JPA.
        }

        public IntegrityRecommendationJpaEntity(
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
            this.id = id;
        this.recommendationNumber = recommendationNumber;
        this.sourceAssessmentId = sourceAssessmentId;
        this.sourceDefectId = sourceDefectId;
        this.recommendationTypeId = recommendationTypeId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priorityId = priorityId;
        this.targetModule = targetModule;
        this.targetReferenceId = targetReferenceId;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        this.dueAt = dueAt;
        this.closedAt = closedAt;
        }


    public String id() {
        return id;
    }


    public String recommendationNumber() {
        return recommendationNumber;
    }


    public String sourceAssessmentId() {
        return sourceAssessmentId;
    }


    public String sourceDefectId() {
        return sourceDefectId;
    }


    public String recommendationTypeId() {
        return recommendationTypeId;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public RecommendationStatus status() {
        return status;
    }


    public String priorityId() {
        return priorityId;
    }


    public String targetModule() {
        return targetModule;
    }


    public String targetReferenceId() {
        return targetReferenceId;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant dueAt() {
        return dueAt;
    }


    public Instant closedAt() {
        return closedAt;
    }

    }
