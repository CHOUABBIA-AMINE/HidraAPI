/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SafetyObservationJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SafetyObservation.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.entity;

import dz.sh.hidra.modules.hse.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for SafetyObservation.
     */
    @Entity
    @Table(name = "hidra_hse_safety_observation")
    public class SafetyObservationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "observation_number", nullable = false, length = 80)
    private String observationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "observation_type", nullable = false, length = 80)
    private ObservationType observationType;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "target_module", nullable = true, length = 80)
    private String targetModule;

    @Column(name = "target_type_code", nullable = true, length = 80)
    private String targetTypeCode;

    @Column(name = "target_id", nullable = true, length = 80)
    private String targetId;

    @Column(name = "observed_by_actor_id", nullable = true, length = 80)
    private String observedByActorId;

    @Column(name = "observed_at", nullable = false)
    private Instant observedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ReportStatus status;

    @Column(name = "linked_hse_case_id", nullable = true, length = 80)
    private String linkedHseCaseId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected SafetyObservationJpaEntity() {
            // Required by JPA.
        }

        public SafetyObservationJpaEntity(
                String id,
            String observationNumber,
            ObservationType observationType,
            String title,
            String description,
            String targetModule,
            String targetTypeCode,
            String targetId,
            String observedByActorId,
            Instant observedAt,
            ReportStatus status,
            String linkedHseCaseId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.observationNumber = observationNumber;
        this.observationType = observationType;
        this.title = title;
        this.description = description;
        this.targetModule = targetModule;
        this.targetTypeCode = targetTypeCode;
        this.targetId = targetId;
        this.observedByActorId = observedByActorId;
        this.observedAt = observedAt;
        this.status = status;
        this.linkedHseCaseId = linkedHseCaseId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String observationNumber() {
        return observationNumber;
    }


    public ObservationType observationType() {
        return observationType;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String targetModule() {
        return targetModule;
    }


    public String targetTypeCode() {
        return targetTypeCode;
    }


    public String targetId() {
        return targetId;
    }


    public String observedByActorId() {
        return observedByActorId;
    }


    public Instant observedAt() {
        return observedAt;
    }


    public ReportStatus status() {
        return status;
    }


    public String linkedHseCaseId() {
        return linkedHseCaseId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
