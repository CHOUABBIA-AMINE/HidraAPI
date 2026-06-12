/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDefinitionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ConfigurationDefinition.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.entity;

import dz.sh.hidra.modules.configuration.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ConfigurationDefinition.
     */
    @Entity
    @Table(name = "hidra_configuration_definition")
    public class ConfigurationDefinitionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "namespace_id", nullable = false, length = 80)
    private String namespaceId;

    @Column(name = "key", nullable = false, length = 160)
    private String key;

    @Column(name = "display_name_fr", nullable = false, length = 160)
    private String displayNameFr;

    @Column(name = "display_name_ar", nullable = true, length = 160)
    private String displayNameAr;

    @Column(name = "display_name_en", nullable = true, length = 160)
    private String displayNameEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type", nullable = false, length = 40)
    private ConfigurationValueType valueType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sensitivity", nullable = false, length = 40)
    private ConfigurationSensitivity sensitivity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ConfigurationDefinitionStatus status;

    @Column(name = "scoped", nullable = false)
    private boolean scoped;

    @Column(name = "requires_approval", nullable = false)
    private boolean requiresApproval;

    @Column(name = "default_value", nullable = true, length = 2000)
    private String defaultValue;

    @Column(name = "description", nullable = true, length = 1000)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ConfigurationDefinitionJpaEntity() {
            // Required by JPA.
        }

        public ConfigurationDefinitionJpaEntity(
                String id,
            String namespaceId,
            String key,
            String displayNameFr,
            String displayNameAr,
            String displayNameEn,
            ConfigurationValueType valueType,
            ConfigurationSensitivity sensitivity,
            ConfigurationDefinitionStatus status,
            boolean scoped,
            boolean requiresApproval,
            String defaultValue,
            String description,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.namespaceId = namespaceId;
        this.key = key;
        this.displayNameFr = displayNameFr;
        this.displayNameAr = displayNameAr;
        this.displayNameEn = displayNameEn;
        this.valueType = valueType;
        this.sensitivity = sensitivity;
        this.status = status;
        this.scoped = scoped;
        this.requiresApproval = requiresApproval;
        this.defaultValue = defaultValue;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String namespaceId() {
        return namespaceId;
    }


    public String key() {
        return key;
    }


    public String displayNameFr() {
        return displayNameFr;
    }


    public String displayNameAr() {
        return displayNameAr;
    }


    public String displayNameEn() {
        return displayNameEn;
    }


    public ConfigurationValueType valueType() {
        return valueType;
    }


    public ConfigurationSensitivity sensitivity() {
        return sensitivity;
    }


    public ConfigurationDefinitionStatus status() {
        return status;
    }


    public boolean scoped() {
        return scoped;
    }


    public boolean requiresApproval() {
        return requiresApproval;
    }


    public String defaultValue() {
        return defaultValue;
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
