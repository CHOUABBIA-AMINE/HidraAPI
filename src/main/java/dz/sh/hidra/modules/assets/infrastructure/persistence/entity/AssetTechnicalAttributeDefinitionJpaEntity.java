/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetTechnicalAttributeDefinitionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetTechnicalAttributeDefinition.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import dz.sh.hidra.modules.assets.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AssetTechnicalAttributeDefinition.
     */
    @Entity
    @Table(name = "hidra_asset_technical_attribute_definition")
    public class AssetTechnicalAttributeDefinitionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "asset_type_id", nullable = false, length = 80)
    private String assetTypeId;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name", nullable = false, length = 160)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false, length = 40)
    private TechnicalAttributeDataType dataType;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Column(name = "required", nullable = false)
    private boolean required;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AssetTechnicalAttributeDefinitionJpaEntity() {
            // Required by JPA.
        }

        public AssetTechnicalAttributeDefinitionJpaEntity(
                String id,
            String assetTypeId,
            String code,
            String name,
            TechnicalAttributeDataType dataType,
            String unitId,
            boolean required,
            boolean active,
            int sortOrder,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.assetTypeId = assetTypeId;
        this.code = code;
        this.name = name;
        this.dataType = dataType;
        this.unitId = unitId;
        this.required = required;
        this.active = active;
        this.sortOrder = sortOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String assetTypeId() {
        return assetTypeId;
    }


    public String code() {
        return code;
    }


    public String name() {
        return name;
    }


    public TechnicalAttributeDataType dataType() {
        return dataType;
    }


    public String unitId() {
        return unitId;
    }


    public boolean required() {
        return required;
    }


    public boolean active() {
        return active;
    }


    public int sortOrder() {
        return sortOrder;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
