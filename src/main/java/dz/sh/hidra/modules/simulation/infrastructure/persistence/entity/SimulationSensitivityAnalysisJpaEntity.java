/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationSensitivityAnalysisJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationSensitivityAnalysis.
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
     * Database-backed JPA entity for SimulationSensitivityAnalysis.
     */
    @Entity
    @Table(name = "hidra_simulation_sensitivity_analysis")
    public class SimulationSensitivityAnalysisJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "scenario_id", nullable = false, length = 80)
    private String scenarioId;

    @Column(name = "base_run_id", nullable = false, length = 80)
    private String baseRunId;

    @Column(name = "parameter_code", nullable = false, length = 120)
    private String parameterCode;

    @Column(name = "parameter_range_text", nullable = false, length = 1000)
    private String parameterRangeText;

    @Column(name = "result_metric_code", nullable = false, length = 120)
    private String resultMetricCode;

    @Column(name = "sensitivity_score", nullable = true, precision = 18, scale = 6)
    private BigDecimal sensitivityScore;

    @Column(name = "summary_text", nullable = true, length = 2000)
    private String summaryText;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationSensitivityAnalysisJpaEntity() {
            // Required by JPA.
        }

        public SimulationSensitivityAnalysisJpaEntity(
                String id,
            String scenarioId,
            String baseRunId,
            String parameterCode,
            String parameterRangeText,
            String resultMetricCode,
            BigDecimal sensitivityScore,
            String summaryText,
            Instant createdAt
        ) {
            this.id = id;
        this.scenarioId = scenarioId;
        this.baseRunId = baseRunId;
        this.parameterCode = parameterCode;
        this.parameterRangeText = parameterRangeText;
        this.resultMetricCode = resultMetricCode;
        this.sensitivityScore = sensitivityScore;
        this.summaryText = summaryText;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String scenarioId() {
        return scenarioId;
    }


    public String baseRunId() {
        return baseRunId;
    }


    public String parameterCode() {
        return parameterCode;
    }


    public String parameterRangeText() {
        return parameterRangeText;
    }


    public String resultMetricCode() {
        return resultMetricCode;
    }


    public BigDecimal sensitivityScore() {
        return sensitivityScore;
    }


    public String summaryText() {
        return summaryText;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
