/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetTechnicalAttributeValueJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetTechnicalAttributeValue.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AssetTechnicalAttributeValue.
     */
    @Entity
    @Table(name = "hidra_asset_technical_attribute_value")
    public class AssetTechnicalAttributeValueJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "maintainable_asset_id", nullable = false, length = 80)
    private String maintainableAssetId;

    @Column(name = "attribute_definition_id", nullable = false, length = 80)
    private String attributeDefinitionId;

    @Column(name = "text_value", nullable = true, columnDefinition = "text")
    private String textValue;

    @Column(name = "numeric_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal numericValue;

    @Column(name = "boolean_value", nullable = false)
    private boolean booleanValue;

    @Column(name = "date_value", nullable = true)
    private Instant dateValue;

    @Column(name = "catalog_value_id", nullable = true, length = 80)
    private String catalogValueId;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Column(name = "effective_from", nullable = true)
    private Instant effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AssetTechnicalAttributeValueJpaEntity() {
            // Required by JPA.
        }

        public AssetTechnicalAttributeValueJpaEntity(
                String id,
            String maintainableAssetId,
            String attributeDefinitionId,
            String textValue,
            BigDecimal numericValue,
            boolean booleanValue,
            Instant dateValue,
            String catalogValueId,
            String unitId,
            Instant effectiveFrom,
            Instant effectiveTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.maintainableAssetId = maintainableAssetId;
        this.attributeDefinitionId = attributeDefinitionId;
        this.textValue = textValue;
        this.numericValue = numericValue;
        this.booleanValue = booleanValue;
        this.dateValue = dateValue;
        this.catalogValueId = catalogValueId;
        this.unitId = unitId;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String maintainableAssetId() {
        return maintainableAssetId;
    }


    public String attributeDefinitionId() {
        return attributeDefinitionId;
    }


    public String textValue() {
        return textValue;
    }


    public BigDecimal numericValue() {
        return numericValue;
    }


    public boolean booleanValue() {
        return booleanValue;
    }


    public Instant dateValue() {
        return dateValue;
    }


    public String catalogValueId() {
        return catalogValueId;
    }


    public String unitId() {
        return unitId;
    }


    public Instant effectiveFrom() {
        return effectiveFrom;
    }


    public Instant effectiveTo() {
        return effectiveTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
