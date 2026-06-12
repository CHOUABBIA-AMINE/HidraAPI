/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HazardReportJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for HazardReport.
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
     * Database-backed JPA entity for HazardReport.
     */
    @Entity
    @Table(name = "hidra_hse_hazard_report")
    public class HazardReportJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_number", nullable = false, length = 80)
    private String reportNumber;

    @Column(name = "hazard_type_id", nullable = false, length = 80)
    private String hazardTypeId;

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

    @Column(name = "target_code_snapshot", nullable = true, length = 160)
    private String targetCodeSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "initial_severity", nullable = true, length = 40)
    private HseImpactSeverity initialSeverity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ReportStatus status;

    @Column(name = "reported_by_actor_id", nullable = true, length = 80)
    private String reportedByActorId;

    @Column(name = "reported_at", nullable = false)
    private Instant reportedAt;

    @Column(name = "linked_hse_case_id", nullable = true, length = 80)
    private String linkedHseCaseId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected HazardReportJpaEntity() {
            // Required by JPA.
        }

        public HazardReportJpaEntity(
                String id,
            String reportNumber,
            String hazardTypeId,
            String title,
            String description,
            String targetModule,
            String targetTypeCode,
            String targetId,
            String targetCodeSnapshot,
            HseImpactSeverity initialSeverity,
            ReportStatus status,
            String reportedByActorId,
            Instant reportedAt,
            String linkedHseCaseId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.reportNumber = reportNumber;
        this.hazardTypeId = hazardTypeId;
        this.title = title;
        this.description = description;
        this.targetModule = targetModule;
        this.targetTypeCode = targetTypeCode;
        this.targetId = targetId;
        this.targetCodeSnapshot = targetCodeSnapshot;
        this.initialSeverity = initialSeverity;
        this.status = status;
        this.reportedByActorId = reportedByActorId;
        this.reportedAt = reportedAt;
        this.linkedHseCaseId = linkedHseCaseId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String reportNumber() {
        return reportNumber;
    }


    public String hazardTypeId() {
        return hazardTypeId;
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


    public String targetCodeSnapshot() {
        return targetCodeSnapshot;
    }


    public HseImpactSeverity initialSeverity() {
        return initialSeverity;
    }


    public ReportStatus status() {
        return status;
    }


    public String reportedByActorId() {
        return reportedByActorId;
    }


    public Instant reportedAt() {
        return reportedAt;
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
