/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationValueJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ConfigurationValue.
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
     * Database-backed JPA entity for ConfigurationValue.
     */
    @Entity
    @Table(name = "hidra_configuration_value")
    public class ConfigurationValueJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "definition_id", nullable = false, length = 80)
    private String definitionId;

    @Column(name = "definition_version_id", nullable = true, length = 80)
    private String definitionVersionId;

    @Column(name = "environment", nullable = false, length = 40)
    private String environment;

    @Column(name = "raw_value", nullable = true, length = 4000)
    private String rawValue;

    @Column(name = "json_value", nullable = true, columnDefinition = "jsonb")
    private String jsonValue;

    @Column(name = "secret_reference", nullable = true, length = 255)
    private String secretReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ConfigurationValueStatus status;

    @Column(name = "effective_from", nullable = true)
    private Instant effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ConfigurationValueJpaEntity() {
            // Required by JPA.
        }

        public ConfigurationValueJpaEntity(
                String id,
            String definitionId,
            String definitionVersionId,
            String environment,
            String rawValue,
            String jsonValue,
            String secretReference,
            ConfigurationValueStatus status,
            Instant effectiveFrom,
            Instant effectiveTo,
            String createdByActorId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.definitionId = definitionId;
        this.definitionVersionId = definitionVersionId;
        this.environment = environment;
        this.rawValue = rawValue;
        this.jsonValue = jsonValue;
        this.secretReference = secretReference;
        this.status = status;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String definitionId() {
        return definitionId;
    }


    public String definitionVersionId() {
        return definitionVersionId;
    }


    public String environment() {
        return environment;
    }


    public String rawValue() {
        return rawValue;
    }


    public String jsonValue() {
        return jsonValue;
    }


    public String secretReference() {
        return secretReference;
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
