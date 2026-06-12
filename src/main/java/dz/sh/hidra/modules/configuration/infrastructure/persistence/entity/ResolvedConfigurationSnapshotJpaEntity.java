/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResolvedConfigurationSnapshotJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ResolvedConfigurationSnapshot.
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
     * Database-backed JPA entity for ResolvedConfigurationSnapshot.
     */
    @Entity
    @Table(name = "hidra_configuration_resolved_snapshot")
    public class ResolvedConfigurationSnapshotJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "snapshot_number", nullable = false, length = 120)
    private String snapshotNumber;

    @Column(name = "profile_id", nullable = true, length = 80)
    private String profileId;

    @Column(name = "namespace_id", nullable = true, length = 80)
    private String namespaceId;

    @Column(name = "target_module", nullable = true, length = 80)
    private String targetModule;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = true, length = 40)
    private ConfigurationScopeType scopeType;

    @Column(name = "scope_id", nullable = true, length = 120)
    private String scopeId;

    @Column(name = "environment", nullable = false, length = 40)
    private String environment;

    @Column(name = "resolved_values_json", nullable = false, columnDefinition = "jsonb")
    private String resolvedValuesJson;

    @Column(name = "hash_value", nullable = false, length = 256)
    private String hashValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ResolvedSnapshotStatus status;

    @Column(name = "resolved_at", nullable = false)
    private Instant resolvedAt;

    @Column(name = "expires_at", nullable = true)
    private Instant expiresAt;

        protected ResolvedConfigurationSnapshotJpaEntity() {
            // Required by JPA.
        }

        public ResolvedConfigurationSnapshotJpaEntity(
                String id,
            String snapshotNumber,
            String profileId,
            String namespaceId,
            String targetModule,
            ConfigurationScopeType scopeType,
            String scopeId,
            String environment,
            String resolvedValuesJson,
            String hashValue,
            ResolvedSnapshotStatus status,
            Instant resolvedAt,
            Instant expiresAt
        ) {
            this.id = id;
        this.snapshotNumber = snapshotNumber;
        this.profileId = profileId;
        this.namespaceId = namespaceId;
        this.targetModule = targetModule;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.environment = environment;
        this.resolvedValuesJson = resolvedValuesJson;
        this.hashValue = hashValue;
        this.status = status;
        this.resolvedAt = resolvedAt;
        this.expiresAt = expiresAt;
        }


    public String id() {
        return id;
    }


    public String snapshotNumber() {
        return snapshotNumber;
    }


    public String profileId() {
        return profileId;
    }


    public String namespaceId() {
        return namespaceId;
    }


    public String targetModule() {
        return targetModule;
    }


    public ConfigurationScopeType scopeType() {
        return scopeType;
    }


    public String scopeId() {
        return scopeId;
    }


    public String environment() {
        return environment;
    }


    public String resolvedValuesJson() {
        return resolvedValuesJson;
    }


    public String hashValue() {
        return hashValue;
    }


    public ResolvedSnapshotStatus status() {
        return status;
    }


    public Instant resolvedAt() {
        return resolvedAt;
    }


    public Instant expiresAt() {
        return expiresAt;
    }

    }
