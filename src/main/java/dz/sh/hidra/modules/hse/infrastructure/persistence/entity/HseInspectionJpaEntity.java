/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseInspectionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for HseInspection.
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
     * Database-backed JPA entity for HseInspection.
     */
    @Entity
    @Table(name = "hidra_hse_inspection")
    public class HseInspectionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "inspection_number", nullable = false, length = 80)
    private String inspectionNumber;

    @Column(name = "inspection_type_id", nullable = false, length = 80)
    private String inspectionTypeId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "target_module", nullable = true, length = 80)
    private String targetModule;

    @Column(name = "target_type_code", nullable = true, length = 80)
    private String targetTypeCode;

    @Column(name = "target_id", nullable = true, length = 80)
    private String targetId;

    @Column(name = "inspector_actor_id", nullable = true, length = 80)
    private String inspectorActorId;

    @Column(name = "planned_at", nullable = true)
    private Instant plannedAt;

    @Column(name = "started_at", nullable = true)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private InspectionStatus status;

    @Column(name = "finding_summary", nullable = true, columnDefinition = "text")
    private String findingSummary;

    @Column(name = "linked_hse_case_id", nullable = true, length = 80)
    private String linkedHseCaseId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected HseInspectionJpaEntity() {
            // Required by JPA.
        }

        public HseInspectionJpaEntity(
                String id,
            String inspectionNumber,
            String inspectionTypeId,
            String title,
            String targetModule,
            String targetTypeCode,
            String targetId,
            String inspectorActorId,
            Instant plannedAt,
            Instant startedAt,
            Instant completedAt,
            InspectionStatus status,
            String findingSummary,
            String linkedHseCaseId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.inspectionNumber = inspectionNumber;
        this.inspectionTypeId = inspectionTypeId;
        this.title = title;
        this.targetModule = targetModule;
        this.targetTypeCode = targetTypeCode;
        this.targetId = targetId;
        this.inspectorActorId = inspectorActorId;
        this.plannedAt = plannedAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.status = status;
        this.findingSummary = findingSummary;
        this.linkedHseCaseId = linkedHseCaseId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String inspectionNumber() {
        return inspectionNumber;
    }


    public String inspectionTypeId() {
        return inspectionTypeId;
    }


    public String title() {
        return title;
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


    public String inspectorActorId() {
        return inspectorActorId;
    }


    public Instant plannedAt() {
        return plannedAt;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public InspectionStatus status() {
        return status;
    }


    public String findingSummary() {
        return findingSummary;
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
