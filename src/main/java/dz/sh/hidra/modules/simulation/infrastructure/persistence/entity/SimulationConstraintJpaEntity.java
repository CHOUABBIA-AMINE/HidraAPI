/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationConstraintJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationConstraint.
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
     * Database-backed JPA entity for SimulationConstraint.
     */
    @Entity
    @Table(name = "hidra_simulation_constraint")
    public class SimulationConstraintJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "scenario_id", nullable = false, length = 80)
    private String scenarioId;

    @Column(name = "constraint_type_id", nullable = false, length = 80)
    private String constraintTypeId;

    @Column(name = "target_type", nullable = true, length = 80)
    private String targetType;

    @Column(name = "target_id", nullable = true, length = 120)
    private String targetId;

    @Column(name = "expression_text", nullable = false, length = 2000)
    private String expressionText;

    @Column(name = "limit_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal limitValue;

    @Column(name = "unit_code", nullable = true, length = 40)
    private String unitCode;

    @Column(name = "severity_id", nullable = false, length = 80)
    private String severityId;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationConstraintJpaEntity() {
            // Required by JPA.
        }

        public SimulationConstraintJpaEntity(
                String id,
            String scenarioId,
            String constraintTypeId,
            String targetType,
            String targetId,
            String expressionText,
            BigDecimal limitValue,
            String unitCode,
            String severityId,
            boolean active,
            Instant createdAt
        ) {
            this.id = id;
        this.scenarioId = scenarioId;
        this.constraintTypeId = constraintTypeId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.expressionText = expressionText;
        this.limitValue = limitValue;
        this.unitCode = unitCode;
        this.severityId = severityId;
        this.active = active;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String scenarioId() {
        return scenarioId;
    }


    public String constraintTypeId() {
        return constraintTypeId;
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


    public BigDecimal limitValue() {
        return limitValue;
    }


    public String unitCode() {
        return unitCode;
    }


    public String severityId() {
        return severityId;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
