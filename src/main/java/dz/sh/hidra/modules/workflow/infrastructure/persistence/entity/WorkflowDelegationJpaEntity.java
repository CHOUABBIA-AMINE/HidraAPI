/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDelegationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WorkflowDelegation.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import dz.sh.hidra.modules.workflow.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for WorkflowDelegation.
     */
    @Entity
    @Table(name = "hidra_workflow_delegation")
    public class WorkflowDelegationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "task_id", nullable = false, length = 80)
    private String taskId;

    @Column(name = "from_actor_id", nullable = false, length = 80)
    private String fromActorId;

    @Column(name = "from_actor_username_snapshot", nullable = true, length = 120)
    private String fromActorUsernameSnapshot;

    @Column(name = "from_actor_display_name_snapshot", nullable = false, length = 160)
    private String fromActorDisplayNameSnapshot;

    @Column(name = "from_actor_role_code_snapshot", nullable = true, length = 80)
    private String fromActorRoleCodeSnapshot;

    @Column(name = "to_actor_id", nullable = true, length = 80)
    private String toActorId;

    @Column(name = "to_actor_username_snapshot", nullable = true, length = 120)
    private String toActorUsernameSnapshot;

    @Column(name = "to_actor_display_name_snapshot", nullable = true, length = 160)
    private String toActorDisplayNameSnapshot;

    @Column(name = "to_actor_role_code_snapshot", nullable = true, length = 80)
    private String toActorRoleCodeSnapshot;

    @Column(name = "to_organization_unit_id", nullable = true, length = 80)
    private String toOrganizationUnitId;

    @Column(name = "to_organization_unit_name_snapshot", nullable = true, length = 160)
    private String toOrganizationUnitNameSnapshot;

    @Column(name = "to_organization_role_code_snapshot", nullable = true, length = 80)
    private String toOrganizationRoleCodeSnapshot;

    @Column(name = "reason_id", nullable = false, length = 80)
    private String reasonId;

    @Enumerated(EnumType.STRING)
    @Column(name = "delegation_status", nullable = false, length = 40)
    private WorkflowDelegationStatus delegationStatus;

    @Column(name = "delegated_at", nullable = false)
    private Instant delegatedAt;

    @Column(name = "accepted_at", nullable = true)
    private Instant acceptedAt;

    @Column(name = "valid_until", nullable = true)
    private Instant validUntil;

    @Column(name = "delegation_depth", nullable = true)
    private Integer delegationDepth;

        protected WorkflowDelegationJpaEntity() {
            // Required by JPA.
        }

        public WorkflowDelegationJpaEntity(
                String id,
            String taskId,
            String fromActorId,
            String fromActorUsernameSnapshot,
            String fromActorDisplayNameSnapshot,
            String fromActorRoleCodeSnapshot,
            String toActorId,
            String toActorUsernameSnapshot,
            String toActorDisplayNameSnapshot,
            String toActorRoleCodeSnapshot,
            String toOrganizationUnitId,
            String toOrganizationUnitNameSnapshot,
            String toOrganizationRoleCodeSnapshot,
            String reasonId,
            WorkflowDelegationStatus delegationStatus,
            Instant delegatedAt,
            Instant acceptedAt,
            Instant validUntil,
            Integer delegationDepth
        ) {
            this.id = id;
        this.taskId = taskId;
        this.fromActorId = fromActorId;
        this.fromActorUsernameSnapshot = fromActorUsernameSnapshot;
        this.fromActorDisplayNameSnapshot = fromActorDisplayNameSnapshot;
        this.fromActorRoleCodeSnapshot = fromActorRoleCodeSnapshot;
        this.toActorId = toActorId;
        this.toActorUsernameSnapshot = toActorUsernameSnapshot;
        this.toActorDisplayNameSnapshot = toActorDisplayNameSnapshot;
        this.toActorRoleCodeSnapshot = toActorRoleCodeSnapshot;
        this.toOrganizationUnitId = toOrganizationUnitId;
        this.toOrganizationUnitNameSnapshot = toOrganizationUnitNameSnapshot;
        this.toOrganizationRoleCodeSnapshot = toOrganizationRoleCodeSnapshot;
        this.reasonId = reasonId;
        this.delegationStatus = delegationStatus;
        this.delegatedAt = delegatedAt;
        this.acceptedAt = acceptedAt;
        this.validUntil = validUntil;
        this.delegationDepth = delegationDepth;
        }


    public String id() {
        return id;
    }


    public String taskId() {
        return taskId;
    }


    public String fromActorId() {
        return fromActorId;
    }


    public String fromActorUsernameSnapshot() {
        return fromActorUsernameSnapshot;
    }


    public String fromActorDisplayNameSnapshot() {
        return fromActorDisplayNameSnapshot;
    }


    public String fromActorRoleCodeSnapshot() {
        return fromActorRoleCodeSnapshot;
    }


    public String toActorId() {
        return toActorId;
    }


    public String toActorUsernameSnapshot() {
        return toActorUsernameSnapshot;
    }


    public String toActorDisplayNameSnapshot() {
        return toActorDisplayNameSnapshot;
    }


    public String toActorRoleCodeSnapshot() {
        return toActorRoleCodeSnapshot;
    }


    public String toOrganizationUnitId() {
        return toOrganizationUnitId;
    }


    public String toOrganizationUnitNameSnapshot() {
        return toOrganizationUnitNameSnapshot;
    }


    public String toOrganizationRoleCodeSnapshot() {
        return toOrganizationRoleCodeSnapshot;
    }


    public String reasonId() {
        return reasonId;
    }


    public WorkflowDelegationStatus delegationStatus() {
        return delegationStatus;
    }


    public Instant delegatedAt() {
        return delegatedAt;
    }


    public Instant acceptedAt() {
        return acceptedAt;
    }


    public Instant validUntil() {
        return validUntil;
    }


    public Integer delegationDepth() {
        return delegationDepth;
    }

    }
