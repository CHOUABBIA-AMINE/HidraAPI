/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedConfigurationOverrideJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ScopedConfigurationOverride.
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
     * Database-backed JPA entity for ScopedConfigurationOverride.
     */
    @Entity
    @Table(name = "hidra_configuration_scoped_override")
    public class ScopedConfigurationOverrideJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "configuration_value_id", nullable = false, length = 80)
    private String configurationValueId;

    @Column(name = "definition_id", nullable = false, length = 80)
    private String definitionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = false, length = 40)
    private ConfigurationScopeType scopeType;

    @Column(name = "scope_id", nullable = true, length = 120)
    private String scopeId;

    @Column(name = "module_name", nullable = true, length = 80)
    private String moduleName;

    @Column(name = "organization_unit_id", nullable = true, length = 80)
    private String organizationUnitId;

    @Column(name = "override_value", nullable = true, length = 4000)
    private String overrideValue;

    @Column(name = "override_json_value", nullable = true, columnDefinition = "jsonb")
    private String overrideJsonValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ConfigurationValueStatus status;

    @Column(name = "effective_from", nullable = true)
    private Instant effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ScopedConfigurationOverrideJpaEntity() {
            // Required by JPA.
        }

        public ScopedConfigurationOverrideJpaEntity(
                String id,
            String configurationValueId,
            String definitionId,
            ConfigurationScopeType scopeType,
            String scopeId,
            String moduleName,
            String organizationUnitId,
            String overrideValue,
            String overrideJsonValue,
            ConfigurationValueStatus status,
            Instant effectiveFrom,
            Instant effectiveTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.configurationValueId = configurationValueId;
        this.definitionId = definitionId;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.moduleName = moduleName;
        this.organizationUnitId = organizationUnitId;
        this.overrideValue = overrideValue;
        this.overrideJsonValue = overrideJsonValue;
        this.status = status;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String configurationValueId() {
        return configurationValueId;
    }


    public String definitionId() {
        return definitionId;
    }


    public ConfigurationScopeType scopeType() {
        return scopeType;
    }


    public String scopeId() {
        return scopeId;
    }


    public String moduleName() {
        return moduleName;
    }


    public String organizationUnitId() {
        return organizationUnitId;
    }


    public String overrideValue() {
        return overrideValue;
    }


    public String overrideJsonValue() {
        return overrideJsonValue;
    }


    public ConfigurationValueStatus status() {
        return status;
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
