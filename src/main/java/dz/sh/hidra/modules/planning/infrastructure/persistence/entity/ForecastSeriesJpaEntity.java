/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ForecastSeriesJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ForecastSeries.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.entity;

import dz.sh.hidra.modules.planning.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ForecastSeries.
     */
    @Entity
    @Table(name = "hidra_planning_forecast_series")
    public class ForecastSeriesJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "period_id", nullable = false, length = 80)
    private String periodId;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "forecast_type_id", nullable = false, length = 80)
    private String forecastTypeId;

    @Column(name = "topology_asset_type", nullable = true, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = true, length = 80)
    private String topologyAssetId;

    @Column(name = "product_type_id", nullable = true, length = 80)
    private String productTypeId;

    @Column(name = "source_module", nullable = true, length = 160)
    private String sourceModule;

    @Column(name = "source_reference_id", nullable = true, length = 80)
    private String sourceReferenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ForecastSeriesStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ForecastSeriesJpaEntity() {
            // Required by JPA.
        }

        public ForecastSeriesJpaEntity(
                String id,
            String periodId,
            String code,
            String forecastTypeId,
            String topologyAssetType,
            String topologyAssetId,
            String productTypeId,
            String sourceModule,
            String sourceReferenceId,
            ForecastSeriesStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.periodId = periodId;
        this.code = code;
        this.forecastTypeId = forecastTypeId;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.productTypeId = productTypeId;
        this.sourceModule = sourceModule;
        this.sourceReferenceId = sourceReferenceId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String periodId() {
        return periodId;
    }


    public String code() {
        return code;
    }


    public String forecastTypeId() {
        return forecastTypeId;
    }


    public String topologyAssetType() {
        return topologyAssetType;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String productTypeId() {
        return productTypeId;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public String sourceReferenceId() {
        return sourceReferenceId;
    }


    public ForecastSeriesStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
