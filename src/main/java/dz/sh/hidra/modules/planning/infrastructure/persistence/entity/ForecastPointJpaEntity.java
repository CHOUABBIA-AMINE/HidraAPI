/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ForecastPointJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ForecastPoint.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for ForecastPoint.
     */
    @Entity
    @Table(name = "hidra_planning_forecast_point")
    public class ForecastPointJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "forecast_series_id", nullable = false, length = 80)
    private String forecastSeriesId;

    @Column(name = "forecast_at", nullable = false)
    private Instant forecastAt;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @Column(name = "value", nullable = false, precision = 18, scale = 6)
    private BigDecimal value;

    @Column(name = "unit_id", nullable = false, length = 80)
    private String unitId;

    @Column(name = "confidence_level", nullable = true, precision = 10, scale = 4)
    private BigDecimal confidenceLevel;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected ForecastPointJpaEntity() {
            // Required by JPA.
        }

        public ForecastPointJpaEntity(
                String id,
            String forecastSeriesId,
            Instant forecastAt,
            Instant validFrom,
            Instant validTo,
            BigDecimal value,
            String unitId,
            BigDecimal confidenceLevel,
            Instant createdAt
        ) {
            this.id = id;
        this.forecastSeriesId = forecastSeriesId;
        this.forecastAt = forecastAt;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.value = value;
        this.unitId = unitId;
        this.confidenceLevel = confidenceLevel;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String forecastSeriesId() {
        return forecastSeriesId;
    }


    public Instant forecastAt() {
        return forecastAt;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public BigDecimal value() {
        return value;
    }


    public String unitId() {
        return unitId;
    }


    public BigDecimal confidenceLevel() {
        return confidenceLevel;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
