/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDistributionRecordJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportDistributionRecord.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.entity;

import dz.sh.hidra.modules.reporting.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ReportDistributionRecord.
     */
    @Entity
    @Table(name = "hidra_reporting_distribution_record")
    public class ReportDistributionRecordJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_publication_id", nullable = false, length = 80)
    private String reportPublicationId;

    @Column(name = "report_output_artifact_id", nullable = false, length = 80)
    private String reportOutputArtifactId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 40)
    private ReportDistributionTargetType targetType;

    @Column(name = "target_reference", nullable = false, length = 255)
    private String targetReference;

    @Column(name = "notification_request_id", nullable = true, length = 120)
    private String notificationRequestId;

    @Column(name = "integration_outbound_record_id", nullable = true, length = 120)
    private String integrationOutboundRecordId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ReportDistributionStatus status;

    @Column(name = "requested_at", nullable = false)
    private Instant requestedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "failure_reason", nullable = true, length = 2000)
    private String failureReason;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ReportDistributionRecordJpaEntity() {
            // Required by JPA.
        }

        public ReportDistributionRecordJpaEntity(
                String id,
            String reportPublicationId,
            String reportOutputArtifactId,
            ReportDistributionTargetType targetType,
            String targetReference,
            String notificationRequestId,
            String integrationOutboundRecordId,
            ReportDistributionStatus status,
            Instant requestedAt,
            Instant completedAt,
            String failureReason,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.reportPublicationId = reportPublicationId;
        this.reportOutputArtifactId = reportOutputArtifactId;
        this.targetType = targetType;
        this.targetReference = targetReference;
        this.notificationRequestId = notificationRequestId;
        this.integrationOutboundRecordId = integrationOutboundRecordId;
        this.status = status;
        this.requestedAt = requestedAt;
        this.completedAt = completedAt;
        this.failureReason = failureReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String reportPublicationId() {
        return reportPublicationId;
    }


    public String reportOutputArtifactId() {
        return reportOutputArtifactId;
    }


    public ReportDistributionTargetType targetType() {
        return targetType;
    }


    public String targetReference() {
        return targetReference;
    }


    public String notificationRequestId() {
        return notificationRequestId;
    }


    public String integrationOutboundRecordId() {
        return integrationOutboundRecordId;
    }


    public ReportDistributionStatus status() {
        return status;
    }


    public Instant requestedAt() {
        return requestedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String failureReason() {
        return failureReason;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
