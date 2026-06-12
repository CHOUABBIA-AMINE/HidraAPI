/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDefinitionVersionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ConfigurationDefinitionVersion.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for ConfigurationDefinitionVersion.
     */
    @Entity
    @Table(name = "hidra_configuration_definition_version")
    public class ConfigurationDefinitionVersionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "definition_id", nullable = false, length = 80)
    private String definitionId;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Column(name = "schema_json", nullable = true, columnDefinition = "jsonb")
    private String schemaJson;

    @Column(name = "default_value", nullable = true, length = 2000)
    private String defaultValue;

    @Column(name = "validation_summary", nullable = true, length = 1000)
    private String validationSummary;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "active", nullable = false)
    private boolean active;

        protected ConfigurationDefinitionVersionJpaEntity() {
            // Required by JPA.
        }

        public ConfigurationDefinitionVersionJpaEntity(
                String id,
            String definitionId,
            int versionNumber,
            String schemaJson,
            String defaultValue,
            String validationSummary,
            String createdByActorId,
            Instant createdAt,
            boolean active
        ) {
            this.id = id;
        this.definitionId = definitionId;
        this.versionNumber = versionNumber;
        this.schemaJson = schemaJson;
        this.defaultValue = defaultValue;
        this.validationSummary = validationSummary;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        this.active = active;
        }


    public String id() {
        return id;
    }


    public String definitionId() {
        return definitionId;
    }


    public int versionNumber() {
        return versionNumber;
    }


    public String schemaJson() {
        return schemaJson;
    }


    public String defaultValue() {
        return defaultValue;
    }


    public String validationSummary() {
        return validationSummary;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public boolean active() {
        return active;
    }

    }
