/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmergencyDrillJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for EmergencyDrill.
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
     * Database-backed JPA entity for EmergencyDrill.
     */
    @Entity
    @Table(name = "hidra_hse_emergency_drill")
    public class EmergencyDrillJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "drill_number", nullable = false, length = 80)
    private String drillNumber;

    @Column(name = "drill_type_id", nullable = false, length = 80)
    private String drillTypeId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "target_module", nullable = true, length = 80)
    private String targetModule;

    @Column(name = "target_type_code", nullable = true, length = 80)
    private String targetTypeCode;

    @Column(name = "target_id", nullable = true, length = 80)
    private String targetId;

    @Column(name = "planned_at", nullable = true)
    private Instant plannedAt;

    @Column(name = "executed_at", nullable = true)
    private Instant executedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private DrillStatus status;

    @Column(name = "participants_count", nullable = true)
    private Integer participantsCount;

    @Column(name = "evaluation_summary", nullable = true, columnDefinition = "text")
    private String evaluationSummary;

    @Column(name = "linked_hse_case_id", nullable = true, length = 80)
    private String linkedHseCaseId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected EmergencyDrillJpaEntity() {
            // Required by JPA.
        }

        public EmergencyDrillJpaEntity(
                String id,
            String drillNumber,
            String drillTypeId,
            String title,
            String targetModule,
            String targetTypeCode,
            String targetId,
            Instant plannedAt,
            Instant executedAt,
            DrillStatus status,
            Integer participantsCount,
            String evaluationSummary,
            String linkedHseCaseId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.drillNumber = drillNumber;
        this.drillTypeId = drillTypeId;
        this.title = title;
        this.targetModule = targetModule;
        this.targetTypeCode = targetTypeCode;
        this.targetId = targetId;
        this.plannedAt = plannedAt;
        this.executedAt = executedAt;
        this.status = status;
        this.participantsCount = participantsCount;
        this.evaluationSummary = evaluationSummary;
        this.linkedHseCaseId = linkedHseCaseId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String drillNumber() {
        return drillNumber;
    }


    public String drillTypeId() {
        return drillTypeId;
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


    public Instant plannedAt() {
        return plannedAt;
    }


    public Instant executedAt() {
        return executedAt;
    }


    public DrillStatus status() {
        return status;
    }


    public Integer participantsCount() {
        return participantsCount;
    }


    public String evaluationSummary() {
        return evaluationSummary;
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
