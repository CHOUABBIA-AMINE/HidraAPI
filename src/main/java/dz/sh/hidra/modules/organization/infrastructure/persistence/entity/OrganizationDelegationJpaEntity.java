/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationDelegationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for OrganizationDelegation.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for OrganizationDelegation.
     */
    @Entity
    @Table(name = "hidra_org_delegation")
    public class OrganizationDelegationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "delegator_employee_id", nullable = false, length = 80)
    private String delegatorEmployeeId;

    @Column(name = "delegate_employee_id", nullable = false, length = 80)
    private String delegateEmployeeId;

    @Column(name = "responsibility_assignment_id", nullable = true, length = 80)
    private String responsibilityAssignmentId;

    @Column(name = "reason", nullable = true, columnDefinition = "text")
    private String reason;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private DelegationStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "revoked_at", nullable = true)
    private Instant revokedAt;

        protected OrganizationDelegationJpaEntity() {
            // Required by JPA.
        }

        public OrganizationDelegationJpaEntity(
                String id,
            String delegatorEmployeeId,
            String delegateEmployeeId,
            String responsibilityAssignmentId,
            String reason,
            Instant validFrom,
            Instant validTo,
            DelegationStatus status,
            Instant createdAt,
            Instant revokedAt
        ) {
            this.id = id;
        this.delegatorEmployeeId = delegatorEmployeeId;
        this.delegateEmployeeId = delegateEmployeeId;
        this.responsibilityAssignmentId = responsibilityAssignmentId;
        this.reason = reason;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
        }


    public String id() {
        return id;
    }


    public String delegatorEmployeeId() {
        return delegatorEmployeeId;
    }


    public String delegateEmployeeId() {
        return delegateEmployeeId;
    }


    public String responsibilityAssignmentId() {
        return responsibilityAssignmentId;
    }


    public String reason() {
        return reason;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public DelegationStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant revokedAt() {
        return revokedAt;
    }

    }
