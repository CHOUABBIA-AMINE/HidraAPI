/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionProfileJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for LeakDetectionProfile.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity;

import dz.sh.hidra.modules.leakdetection.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for LeakDetectionProfile.
     */
    @Entity
    @Table(name = "hidra_leak_detection_profile")
    public class LeakDetectionProfileJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "topology_asset_type", nullable = false, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = false, length = 160)
    private String topologyAssetCode;

    @Column(name = "topology_asset_name_snapshot", nullable = true, length = 500)
    private String topologyAssetNameSnapshot;

    @Column(name = "method_id", nullable = false, length = 80)
    private String methodId;

    @Column(name = "configuration_json", nullable = true, columnDefinition = "jsonb")
    private String configurationJson;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private LeakDetectionProfileStatus status;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected LeakDetectionProfileJpaEntity() {
            // Required by JPA.
        }

        public LeakDetectionProfileJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            String topologyAssetNameSnapshot,
            String methodId,
            String configurationJson,
            LeakDetectionProfileStatus status,
            String createdByActorId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.topologyAssetNameSnapshot = topologyAssetNameSnapshot;
        this.methodId = methodId;
        this.configurationJson = configurationJson;
        this.status = status;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
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


    public String topologyAssetType() {
        return topologyAssetType;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCode() {
        return topologyAssetCode;
    }


    public String topologyAssetNameSnapshot() {
        return topologyAssetNameSnapshot;
    }


    public String methodId() {
        return methodId;
    }


    public String configurationJson() {
        return configurationJson;
    }


    public LeakDetectionProfileStatus status() {
        return status;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
