/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStepJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WorkflowStep.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for WorkflowStep.
     */
    @Entity
    @Table(name = "hidra_workflow_step")
    public class WorkflowStepJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "definition_id", nullable = false, length = 80)
    private String definitionId;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "step_order", nullable = false)
    private int stepOrder;

    @Column(name = "mandatory", nullable = false)
    private boolean mandatory;

    @Column(name = "step_type_id", nullable = true, length = 80)
    private String stepTypeId;

    @Column(name = "default_assignment_rule_id", nullable = true, length = 80)
    private String defaultAssignmentRuleId;

    @Column(name = "sla_policy_id", nullable = true, length = 80)
    private String slaPolicyId;

    @Column(name = "allow_claim", nullable = false)
    private boolean allowClaim;

    @Column(name = "allow_delegation", nullable = false)
    private boolean allowDelegation;

    @Column(name = "allow_escalation", nullable = false)
    private boolean allowEscalation;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected WorkflowStepJpaEntity() {
            // Required by JPA.
        }

        public WorkflowStepJpaEntity(
                String id,
            String definitionId,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            int stepOrder,
            boolean mandatory,
            String stepTypeId,
            String defaultAssignmentRuleId,
            String slaPolicyId,
            boolean allowClaim,
            boolean allowDelegation,
            boolean allowEscalation,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.definitionId = definitionId;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.stepOrder = stepOrder;
        this.mandatory = mandatory;
        this.stepTypeId = stepTypeId;
        this.defaultAssignmentRuleId = defaultAssignmentRuleId;
        this.slaPolicyId = slaPolicyId;
        this.allowClaim = allowClaim;
        this.allowDelegation = allowDelegation;
        this.allowEscalation = allowEscalation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String definitionId() {
        return definitionId;
    }


    public String code() {
        return code;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameFr() {
        return nameFr;
    }


    public String nameEn() {
        return nameEn;
    }


    public int stepOrder() {
        return stepOrder;
    }


    public boolean mandatory() {
        return mandatory;
    }


    public String stepTypeId() {
        return stepTypeId;
    }


    public String defaultAssignmentRuleId() {
        return defaultAssignmentRuleId;
    }


    public String slaPolicyId() {
        return slaPolicyId;
    }


    public boolean allowClaim() {
        return allowClaim;
    }


    public boolean allowDelegation() {
        return allowDelegation;
    }


    public boolean allowEscalation() {
        return allowEscalation;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
