/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricDefinitionVersionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MetricDefinitionVersion.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for MetricDefinitionVersion.
     */
    @Entity
    @Table(name = "hidra_analytics_metric_definition_version")
    public class MetricDefinitionVersionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "metric_definition_id", nullable = false, length = 80)
    private String metricDefinitionId;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Column(name = "formula_expression", nullable = false, length = 2000)
    private String formulaExpression;

    @Column(name = "calculation_description", nullable = true, length = 1000)
    private String calculationDescription;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected MetricDefinitionVersionJpaEntity() {
            // Required by JPA.
        }

        public MetricDefinitionVersionJpaEntity(
                String id,
            String metricDefinitionId,
            int versionNumber,
            String formulaExpression,
            String calculationDescription,
            Instant validFrom,
            Instant validTo,
            String createdByActorId,
            Instant createdAt
        ) {
            this.id = id;
        this.metricDefinitionId = metricDefinitionId;
        this.versionNumber = versionNumber;
        this.formulaExpression = formulaExpression;
        this.calculationDescription = calculationDescription;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String metricDefinitionId() {
        return metricDefinitionId;
    }


    public int versionNumber() {
        return versionNumber;
    }


    public String formulaExpression() {
        return formulaExpression;
    }


    public String calculationDescription() {
        return calculationDescription;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
