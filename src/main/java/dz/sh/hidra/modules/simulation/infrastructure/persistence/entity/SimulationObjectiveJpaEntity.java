/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationObjectiveJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationObjective.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for SimulationObjective.
     */
    @Entity
    @Table(name = "hidra_simulation_objective")
    public class SimulationObjectiveJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "scenario_id", nullable = false, length = 80)
    private String scenarioId;

    @Column(name = "objective_type_id", nullable = false, length = 80)
    private String objectiveTypeId;

    @Column(name = "weight", nullable = false, precision = 18, scale = 6)
    private BigDecimal weight;

    @Column(name = "priority_order", nullable = false)
    private int priorityOrder;

    @Column(name = "target_type", nullable = true, length = 80)
    private String targetType;

    @Column(name = "target_id", nullable = true, length = 120)
    private String targetId;

    @Column(name = "expression_text", nullable = true, length = 2000)
    private String expressionText;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationObjectiveJpaEntity() {
            // Required by JPA.
        }

        public SimulationObjectiveJpaEntity(
                String id,
            String scenarioId,
            String objectiveTypeId,
            BigDecimal weight,
            int priorityOrder,
            String targetType,
            String targetId,
            String expressionText,
            Instant createdAt
        ) {
            this.id = id;
        this.scenarioId = scenarioId;
        this.objectiveTypeId = objectiveTypeId;
        this.weight = weight;
        this.priorityOrder = priorityOrder;
        this.targetType = targetType;
        this.targetId = targetId;
        this.expressionText = expressionText;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String scenarioId() {
        return scenarioId;
    }


    public String objectiveTypeId() {
        return objectiveTypeId;
    }


    public BigDecimal weight() {
        return weight;
    }


    public int priorityOrder() {
        return priorityOrder;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public String expressionText() {
        return expressionText;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
