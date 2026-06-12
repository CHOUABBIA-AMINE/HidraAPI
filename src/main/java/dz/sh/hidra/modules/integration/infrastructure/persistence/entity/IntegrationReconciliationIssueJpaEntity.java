/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationReconciliationIssueJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationReconciliationIssue.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integration.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IntegrationReconciliationIssue.
     */
    @Entity
    @Table(name = "hidra_integration_reconciliation_issue")
    public class IntegrationReconciliationIssueJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "reconciliation_run_id", nullable = false, length = 80)
    private String reconciliationRunId;

    @Column(name = "issue_type", nullable = false, length = 80)
    private String issueType;

    @Column(name = "external_object_type", nullable = true, length = 120)
    private String externalObjectType;

    @Column(name = "external_object_id", nullable = true, length = 255)
    private String externalObjectId;

    @Column(name = "target_module", nullable = true, length = 80)
    private String targetModule;

    @Column(name = "target_type_code", nullable = true, length = 120)
    private String targetTypeCode;

    @Column(name = "target_id", nullable = true, length = 120)
    private String targetId;

    @Column(name = "field_path", nullable = true, length = 500)
    private String fieldPath;

    @Column(name = "hidra_value_snapshot", nullable = true, length = 1000)
    private String hidraValueSnapshot;

    @Column(name = "external_value_snapshot", nullable = true, length = 1000)
    private String externalValueSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 40)
    private IntegrationSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ReconciliationIssueStatus status;

    @Column(name = "resolution_comment", nullable = true, length = 2000)
    private String resolutionComment;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "resolved_at", nullable = true)
    private Instant resolvedAt;

        protected IntegrationReconciliationIssueJpaEntity() {
            // Required by JPA.
        }

        public IntegrationReconciliationIssueJpaEntity(
                String id,
            String reconciliationRunId,
            String issueType,
            String externalObjectType,
            String externalObjectId,
            String targetModule,
            String targetTypeCode,
            String targetId,
            String fieldPath,
            String hidraValueSnapshot,
            String externalValueSnapshot,
            IntegrationSeverity severity,
            ReconciliationIssueStatus status,
            String resolutionComment,
            Instant createdAt,
            Instant resolvedAt
        ) {
            this.id = id;
        this.reconciliationRunId = reconciliationRunId;
        this.issueType = issueType;
        this.externalObjectType = externalObjectType;
        this.externalObjectId = externalObjectId;
        this.targetModule = targetModule;
        this.targetTypeCode = targetTypeCode;
        this.targetId = targetId;
        this.fieldPath = fieldPath;
        this.hidraValueSnapshot = hidraValueSnapshot;
        this.externalValueSnapshot = externalValueSnapshot;
        this.severity = severity;
        this.status = status;
        this.resolutionComment = resolutionComment;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
        }


    public String id() {
        return id;
    }


    public String reconciliationRunId() {
        return reconciliationRunId;
    }


    public String issueType() {
        return issueType;
    }


    public String externalObjectType() {
        return externalObjectType;
    }


    public String externalObjectId() {
        return externalObjectId;
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


    public String fieldPath() {
        return fieldPath;
    }


    public String hidraValueSnapshot() {
        return hidraValueSnapshot;
    }


    public String externalValueSnapshot() {
        return externalValueSnapshot;
    }


    public IntegrationSeverity severity() {
        return severity;
    }


    public ReconciliationIssueStatus status() {
        return status;
    }


    public String resolutionComment() {
        return resolutionComment;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant resolvedAt() {
        return resolvedAt;
    }

    }
