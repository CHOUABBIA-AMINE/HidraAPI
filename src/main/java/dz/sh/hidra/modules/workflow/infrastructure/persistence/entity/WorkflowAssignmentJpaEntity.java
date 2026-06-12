/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAssignmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WorkflowAssignment.
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
     * Database-backed JPA entity for WorkflowAssignment.
     */
    @Entity
    @Table(name = "hidra_workflow_assignment")
    public class WorkflowAssignmentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "task_id", nullable = false, length = 80)
    private String taskId;

    @Column(name = "actor_id", nullable = true, length = 80)
    private String actorId;

    @Column(name = "actor_username_snapshot", nullable = true, length = 120)
    private String actorUsernameSnapshot;

    @Column(name = "actor_display_name_snapshot", nullable = true, length = 160)
    private String actorDisplayNameSnapshot;

    @Column(name = "role_code_snapshot", nullable = true, length = 80)
    private String roleCodeSnapshot;

    @Column(name = "organization_unit_id", nullable = true, length = 80)
    private String organizationUnitId;

    @Column(name = "organization_unit_name_snapshot", nullable = true, length = 160)
    private String organizationUnitNameSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private WorkflowAssignmentStatus status;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected WorkflowAssignmentJpaEntity() {
            // Required by JPA.
        }

        public WorkflowAssignmentJpaEntity(
                String id,
            String taskId,
            String actorId,
            String actorUsernameSnapshot,
            String actorDisplayNameSnapshot,
            String roleCodeSnapshot,
            String organizationUnitId,
            String organizationUnitNameSnapshot,
            WorkflowAssignmentStatus status,
            Instant assignedAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.taskId = taskId;
        this.actorId = actorId;
        this.actorUsernameSnapshot = actorUsernameSnapshot;
        this.actorDisplayNameSnapshot = actorDisplayNameSnapshot;
        this.roleCodeSnapshot = roleCodeSnapshot;
        this.organizationUnitId = organizationUnitId;
        this.organizationUnitNameSnapshot = organizationUnitNameSnapshot;
        this.status = status;
        this.assignedAt = assignedAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String taskId() {
        return taskId;
    }


    public String actorId() {
        return actorId;
    }


    public String actorUsernameSnapshot() {
        return actorUsernameSnapshot;
    }


    public String actorDisplayNameSnapshot() {
        return actorDisplayNameSnapshot;
    }


    public String roleCodeSnapshot() {
        return roleCodeSnapshot;
    }


    public String organizationUnitId() {
        return organizationUnitId;
    }


    public String organizationUnitNameSnapshot() {
        return organizationUnitNameSnapshot;
    }


    public WorkflowAssignmentStatus status() {
        return status;
    }


    public Instant assignedAt() {
        return assignedAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
