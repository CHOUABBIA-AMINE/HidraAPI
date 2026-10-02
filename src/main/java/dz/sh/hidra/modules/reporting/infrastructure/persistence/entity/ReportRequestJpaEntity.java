/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRequestJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportRequest.
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
     * Database-backed JPA entity for ReportRequest.
     */
    @Entity
    @Table(name = "hidra_reporting_request")
    public class ReportRequestJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_definition_id", nullable = false, length = 80)
    private String reportDefinitionId;

    @Column(name = "requested_by_actor_id", nullable = false, length = 80)
    private String requestedByActorId;

    @Column(name = "requested_by_username_snapshot", nullable = true, length = 120)
    private String requestedByUsernameSnapshot;

    @Column(name = "requested_by_display_name_snapshot", nullable = true, length = 160)
    private String requestedByDisplayNameSnapshot;

    @Column(name = "requested_by_role_code_snapshot", nullable = true, length = 120)
    private String requestedByRoleCodeSnapshot;

    @Column(name = "organization_unit_id", nullable = true, length = 80)
    private String organizationUnitId;

    @Column(name = "organization_unit_name_snapshot", nullable = true, length = 160)
    private String organizationUnitNameSnapshot;

    @Column(name = "requested_at", nullable = false)
    private Instant requestedAt;

    @Column(name = "purpose", nullable = true, length = 1000)
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ReportRequestStatus status;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "workflow_reference_id", nullable = true, length = 120)
    private String workflowReferenceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ReportRequestJpaEntity() {
            // Required by JPA.
        }

        public ReportRequestJpaEntity(
                String id,
            String reportDefinitionId,
            String requestedByActorId,
            String requestedByUsernameSnapshot,
            String requestedByDisplayNameSnapshot,
            String requestedByRoleCodeSnapshot,
            String organizationUnitId,
            String organizationUnitNameSnapshot,
            Instant requestedAt,
            String purpose,
            ReportRequestStatus status,
            String correlationId,
            String workflowReferenceId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.reportDefinitionId = reportDefinitionId;
        this.requestedByActorId = requestedByActorId;
        this.requestedByUsernameSnapshot = requestedByUsernameSnapshot;
        this.requestedByDisplayNameSnapshot = requestedByDisplayNameSnapshot;
        this.requestedByRoleCodeSnapshot = requestedByRoleCodeSnapshot;
        this.organizationUnitId = organizationUnitId;
        this.organizationUnitNameSnapshot = organizationUnitNameSnapshot;
        this.requestedAt = requestedAt;
        this.purpose = purpose;
        this.status = status;
        this.correlationId = correlationId;
        this.workflowReferenceId = workflowReferenceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String reportDefinitionId() {
        return reportDefinitionId;
    }


    public String requestedByActorId() {
        return requestedByActorId;
    }


    public String requestedByUsernameSnapshot() {
        return requestedByUsernameSnapshot;
    }


    public String requestedByDisplayNameSnapshot() {
        return requestedByDisplayNameSnapshot;
    }


    public String requestedByRoleCodeSnapshot() {
        return requestedByRoleCodeSnapshot;
    }


    public String organizationUnitId() {
        return organizationUnitId;
    }


    public String organizationUnitNameSnapshot() {
        return organizationUnitNameSnapshot;
    }


    public Instant requestedAt() {
        return requestedAt;
    }


    public String purpose() {
        return purpose;
    }


    public ReportRequestStatus status() {
        return status;
    }


    public String correlationId() {
        return correlationId;
    }


    public String workflowReferenceId() {
        return workflowReferenceId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
