/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationScenarioAssumptionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationScenarioAssumption.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.entity;

import dz.sh.hidra.modules.simulation.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for SimulationScenarioAssumption.
     */
    @Entity
    @Table(name = "hidra_simulation_scenario_assumption")
    public class SimulationScenarioAssumptionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "scenario_id", nullable = false, length = 80)
    private String scenarioId;

    @Column(name = "assumption_type_id", nullable = false, length = 80)
    private String assumptionTypeId;

    @Column(name = "target_type", nullable = true, length = 80)
    private String targetType;

    @Column(name = "target_id", nullable = true, length = 120)
    private String targetId;

    @Column(name = "parameter_code", nullable = false, length = 120)
    private String parameterCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type", nullable = false, length = 40)
    private SimulationValueType valueType;

    @Column(name = "value_text", nullable = false, length = 2000)
    private String valueText;

    @Column(name = "unit_code", nullable = true, length = 40)
    private String unitCode;

    @Column(name = "confidence_level_id", nullable = true, length = 80)
    private String confidenceLevelId;

    @Column(name = "source_note", nullable = true, length = 1000)
    private String sourceNote;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationScenarioAssumptionJpaEntity() {
            // Required by JPA.
        }

        public SimulationScenarioAssumptionJpaEntity(
                String id,
            String scenarioId,
            String assumptionTypeId,
            String targetType,
            String targetId,
            String parameterCode,
            SimulationValueType valueType,
            String valueText,
            String unitCode,
            String confidenceLevelId,
            String sourceNote,
            Instant createdAt
        ) {
            this.id = id;
        this.scenarioId = scenarioId;
        this.assumptionTypeId = assumptionTypeId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.parameterCode = parameterCode;
        this.valueType = valueType;
        this.valueText = valueText;
        this.unitCode = unitCode;
        this.confidenceLevelId = confidenceLevelId;
        this.sourceNote = sourceNote;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String scenarioId() {
        return scenarioId;
    }


    public String assumptionTypeId() {
        return assumptionTypeId;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public String parameterCode() {
        return parameterCode;
    }


    public SimulationValueType valueType() {
        return valueType;
    }


    public String valueText() {
        return valueText;
    }


    public String unitCode() {
        return unitCode;
    }


    public String confidenceLevelId() {
        return confidenceLevelId;
    }


    public String sourceNote() {
        return sourceNote;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
