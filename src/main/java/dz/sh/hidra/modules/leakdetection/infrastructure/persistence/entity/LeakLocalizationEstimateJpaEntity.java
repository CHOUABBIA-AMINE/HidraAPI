/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakLocalizationEstimateJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for LeakLocalizationEstimate.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for LeakLocalizationEstimate.
     */
    @Entity
    @Table(name = "hidra_leak_detection_localization_estimate")
    public class LeakLocalizationEstimateJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "candidate_id", nullable = false, length = 80)
    private String candidateId;

    @Column(name = "topology_asset_type", nullable = false, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = false, length = 160)
    private String topologyAssetCode;

    @Column(name = "estimated_kilometer_point", nullable = true, precision = 14, scale = 4)
    private BigDecimal estimatedKilometerPoint;

    @Column(name = "uncertainty_radius_meters", nullable = true, precision = 14, scale = 4)
    private BigDecimal uncertaintyRadiusMeters;

    @Column(name = "latitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "confidence_score", nullable = false, precision = 10, scale = 6)
    private BigDecimal confidenceScore;

    @Column(name = "method_id", nullable = false, length = 80)
    private String methodId;

    @Column(name = "estimated_at", nullable = false)
    private Instant estimatedAt;

    @Column(name = "notes", nullable = true, columnDefinition = "text")
    private String notes;

        protected LeakLocalizationEstimateJpaEntity() {
            // Required by JPA.
        }

        public LeakLocalizationEstimateJpaEntity(
                String id,
            String candidateId,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            BigDecimal estimatedKilometerPoint,
            BigDecimal uncertaintyRadiusMeters,
            BigDecimal latitude,
            BigDecimal longitude,
            BigDecimal confidenceScore,
            String methodId,
            Instant estimatedAt,
            String notes
        ) {
            this.id = id;
        this.candidateId = candidateId;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.estimatedKilometerPoint = estimatedKilometerPoint;
        this.uncertaintyRadiusMeters = uncertaintyRadiusMeters;
        this.latitude = latitude;
        this.longitude = longitude;
        this.confidenceScore = confidenceScore;
        this.methodId = methodId;
        this.estimatedAt = estimatedAt;
        this.notes = notes;
        }


    public String id() {
        return id;
    }


    public String candidateId() {
        return candidateId;
    }


    public String topologyAssetType() {
        return topologyAssetType;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCode() {
        return topologyAssetCode;
    }


    public BigDecimal estimatedKilometerPoint() {
        return estimatedKilometerPoint;
    }


    public BigDecimal uncertaintyRadiusMeters() {
        return uncertaintyRadiusMeters;
    }


    public BigDecimal latitude() {
        return latitude;
    }


    public BigDecimal longitude() {
        return longitude;
    }


    public BigDecimal confidenceScore() {
        return confidenceScore;
    }


    public String methodId() {
        return methodId;
    }


    public Instant estimatedAt() {
        return estimatedAt;
    }


    public String notes() {
        return notes;
    }

    }
