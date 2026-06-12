/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationProfileEntryJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ConfigurationProfileEntry.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for ConfigurationProfileEntry.
     */
    @Entity
    @Table(name = "hidra_configuration_profile_entry")
    public class ConfigurationProfileEntryJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "profile_id", nullable = false, length = 80)
    private String profileId;

    @Column(name = "definition_id", nullable = false, length = 80)
    private String definitionId;

    @Column(name = "configuration_value_id", nullable = true, length = 80)
    private String configurationValueId;

    @Column(name = "scoped_override_id", nullable = true, length = 80)
    private String scopedOverrideId;

    @Column(name = "priority_order", nullable = false)
    private int priorityOrder;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ConfigurationProfileEntryJpaEntity() {
            // Required by JPA.
        }

        public ConfigurationProfileEntryJpaEntity(
                String id,
            String profileId,
            String definitionId,
            String configurationValueId,
            String scopedOverrideId,
            int priorityOrder,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.profileId = profileId;
        this.definitionId = definitionId;
        this.configurationValueId = configurationValueId;
        this.scopedOverrideId = scopedOverrideId;
        this.priorityOrder = priorityOrder;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String profileId() {
        return profileId;
    }


    public String definitionId() {
        return definitionId;
    }


    public String configurationValueId() {
        return configurationValueId;
    }


    public String scopedOverrideId() {
        return scopedOverrideId;
    }


    public int priorityOrder() {
        return priorityOrder;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
