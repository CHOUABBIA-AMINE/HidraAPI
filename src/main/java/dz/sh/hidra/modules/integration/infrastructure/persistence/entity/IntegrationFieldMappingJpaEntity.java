/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationFieldMappingJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationFieldMapping.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for IntegrationFieldMapping.
     */
    @Entity
    @Table(name = "hidra_integration_field_mapping")
    public class IntegrationFieldMappingJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "mapping_profile_id", nullable = false, length = 80)
    private String mappingProfileId;

    @Column(name = "source_path", nullable = false, length = 500)
    private String sourcePath;

    @Column(name = "target_path", nullable = false, length = 500)
    private String targetPath;

    @Column(name = "data_type", nullable = false, length = 50)
    private String dataType;

    @Column(name = "required", nullable = false)
    private boolean required;

    @Column(name = "default_value", nullable = true, length = 1000)
    private String defaultValue;

    @Column(name = "unit_code", nullable = true, length = 80)
    private String unitCode;

    @Column(name = "transformation_rule_id", nullable = true, length = 80)
    private String transformationRuleId;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    @Column(name = "active", nullable = false)
    private boolean active;

        protected IntegrationFieldMappingJpaEntity() {
            // Required by JPA.
        }

        public IntegrationFieldMappingJpaEntity(
                String id,
            String mappingProfileId,
            String sourcePath,
            String targetPath,
            String dataType,
            boolean required,
            String defaultValue,
            String unitCode,
            String transformationRuleId,
            int displayOrder,
            boolean active
        ) {
            this.id = id;
        this.mappingProfileId = mappingProfileId;
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
        this.dataType = dataType;
        this.required = required;
        this.defaultValue = defaultValue;
        this.unitCode = unitCode;
        this.transformationRuleId = transformationRuleId;
        this.displayOrder = displayOrder;
        this.active = active;
        }


    public String id() {
        return id;
    }


    public String mappingProfileId() {
        return mappingProfileId;
    }


    public String sourcePath() {
        return sourcePath;
    }


    public String targetPath() {
        return targetPath;
    }


    public String dataType() {
        return dataType;
    }


    public boolean required() {
        return required;
    }


    public String defaultValue() {
        return defaultValue;
    }


    public String unitCode() {
        return unitCode;
    }


    public String transformationRuleId() {
        return transformationRuleId;
    }


    public int displayOrder() {
        return displayOrder;
    }


    public boolean active() {
        return active;
    }

    }
