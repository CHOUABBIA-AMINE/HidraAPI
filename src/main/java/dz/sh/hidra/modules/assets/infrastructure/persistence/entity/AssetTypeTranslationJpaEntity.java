/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetTypeTranslationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetTypeTranslation.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AssetTypeTranslation.
     */
    @Entity
    @Table(name = "hidra_asset_type_translation")
    public class AssetTypeTranslationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "asset_type_id", nullable = false, length = 80)
    private String assetTypeId;

    @Column(name = "locale", nullable = false, length = 10)
    private String locale;

    @Column(name = "name", nullable = false, length = 160)
    private String name;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AssetTypeTranslationJpaEntity() {
            // Required by JPA.
        }

        public AssetTypeTranslationJpaEntity(
                String id,
            String assetTypeId,
            String locale,
            String name,
            String description,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.assetTypeId = assetTypeId;
        this.locale = locale;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String assetTypeId() {
        return assetTypeId;
    }


    public String locale() {
        return locale;
    }


    public String name() {
        return name;
    }


    public String description() {
        return description;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
