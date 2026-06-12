/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportAccessPolicyJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportAccessPolicy.
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
     * Database-backed JPA entity for ReportAccessPolicy.
     */
    @Entity
    @Table(name = "hidra_reporting_access_policy")
    public class ReportAccessPolicyJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_definition_id", nullable = false, length = 80)
    private String reportDefinitionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = false, length = 40)
    private ReportAccessScopeType scopeType;

    @Column(name = "scope_reference_id", nullable = true, length = 120)
    private String scopeReferenceId;

    @Column(name = "permission_code", nullable = false, length = 120)
    private String permissionCode;

    @Column(name = "restricted", nullable = false)
    private boolean restricted;

    @Column(name = "mask_sensitive_values", nullable = false)
    private boolean maskSensitiveValues;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ReportAccessPolicyJpaEntity() {
            // Required by JPA.
        }

        public ReportAccessPolicyJpaEntity(
                String id,
            String reportDefinitionId,
            ReportAccessScopeType scopeType,
            String scopeReferenceId,
            String permissionCode,
            boolean restricted,
            boolean maskSensitiveValues,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.reportDefinitionId = reportDefinitionId;
        this.scopeType = scopeType;
        this.scopeReferenceId = scopeReferenceId;
        this.permissionCode = permissionCode;
        this.restricted = restricted;
        this.maskSensitiveValues = maskSensitiveValues;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String reportDefinitionId() {
        return reportDefinitionId;
    }


    public ReportAccessScopeType scopeType() {
        return scopeType;
    }


    public String scopeReferenceId() {
        return scopeReferenceId;
    }


    public String permissionCode() {
        return permissionCode;
    }


    public boolean restricted() {
        return restricted;
    }


    public boolean maskSensitiveValues() {
        return maskSensitiveValues;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
