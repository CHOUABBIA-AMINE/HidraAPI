/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationCandidateChangeJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationCandidateChange.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for SimulationCandidateChange.
     */
    @Entity
    @Table(name = "hidra_simulation_candidate_change")
    public class SimulationCandidateChangeJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "candidate_id", nullable = false, length = 80)
    private String candidateId;

    @Column(name = "change_type_id", nullable = false, length = 80)
    private String changeTypeId;

    @Column(name = "target_type", nullable = false, length = 80)
    private String targetType;

    @Column(name = "target_id", nullable = false, length = 120)
    private String targetId;

    @Column(name = "before_value", nullable = true, length = 1000)
    private String beforeValue;

    @Column(name = "after_value", nullable = false, length = 1000)
    private String afterValue;

    @Column(name = "unit_code", nullable = true, length = 40)
    private String unitCode;

    @Column(name = "requires_topology_change", nullable = false)
    private boolean requiresTopologyChange;

    @Column(name = "requires_operational_procedure", nullable = false)
    private boolean requiresOperationalProcedure;

    @Column(name = "safety_critical", nullable = false)
    private boolean safetyCritical;

    @Column(name = "explanation", nullable = true, length = 2000)
    private String explanation;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationCandidateChangeJpaEntity() {
            // Required by JPA.
        }

        public SimulationCandidateChangeJpaEntity(
                String id,
            String candidateId,
            String changeTypeId,
            String targetType,
            String targetId,
            String beforeValue,
            String afterValue,
            String unitCode,
            boolean requiresTopologyChange,
            boolean requiresOperationalProcedure,
            boolean safetyCritical,
            String explanation,
            Instant createdAt
        ) {
            this.id = id;
        this.candidateId = candidateId;
        this.changeTypeId = changeTypeId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.beforeValue = beforeValue;
        this.afterValue = afterValue;
        this.unitCode = unitCode;
        this.requiresTopologyChange = requiresTopologyChange;
        this.requiresOperationalProcedure = requiresOperationalProcedure;
        this.safetyCritical = safetyCritical;
        this.explanation = explanation;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String candidateId() {
        return candidateId;
    }


    public String changeTypeId() {
        return changeTypeId;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public String beforeValue() {
        return beforeValue;
    }


    public String afterValue() {
        return afterValue;
    }


    public String unitCode() {
        return unitCode;
    }


    public boolean requiresTopologyChange() {
        return requiresTopologyChange;
    }


    public boolean requiresOperationalProcedure() {
        return requiresOperationalProcedure;
    }


    public boolean safetyCritical() {
        return safetyCritical;
    }


    public String explanation() {
        return explanation;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
