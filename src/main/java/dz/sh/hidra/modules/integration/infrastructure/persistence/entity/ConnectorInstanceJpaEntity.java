/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConnectorInstanceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ConnectorInstance.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integration.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ConnectorInstance.
     */
    @Entity
    @Table(name = "hidra_integration_connector_instance")
    public class ConnectorInstanceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "external_system_id", nullable = false, length = 80)
    private String externalSystemId;

    @Column(name = "endpoint_id", nullable = false, length = 80)
    private String endpointId;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "connector_type_id", nullable = false, length = 80)
    private String connectorTypeId;

    @Column(name = "connector_implementation", nullable = false, length = 255)
    private String connectorImplementation;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false, length = 30)
    private IntegrationDirection direction;

    @Column(name = "configuration_json", nullable = true, columnDefinition = "jsonb")
    private String configurationJson;

    @Column(name = "max_concurrency", nullable = true)
    private Integer maxConcurrency;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "health_status", nullable = true, length = 40)
    private ConnectorHealthStatus healthStatus;

    @Column(name = "last_health_check_at", nullable = true)
    private Instant lastHealthCheckAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ConnectorInstanceJpaEntity() {
            // Required by JPA.
        }

        public ConnectorInstanceJpaEntity(
                String id,
            String externalSystemId,
            String endpointId,
            String code,
            String connectorTypeId,
            String connectorImplementation,
            IntegrationDirection direction,
            String configurationJson,
            Integer maxConcurrency,
            boolean active,
            ConnectorHealthStatus healthStatus,
            Instant lastHealthCheckAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.externalSystemId = externalSystemId;
        this.endpointId = endpointId;
        this.code = code;
        this.connectorTypeId = connectorTypeId;
        this.connectorImplementation = connectorImplementation;
        this.direction = direction;
        this.configurationJson = configurationJson;
        this.maxConcurrency = maxConcurrency;
        this.active = active;
        this.healthStatus = healthStatus;
        this.lastHealthCheckAt = lastHealthCheckAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String externalSystemId() {
        return externalSystemId;
    }


    public String endpointId() {
        return endpointId;
    }


    public String code() {
        return code;
    }


    public String connectorTypeId() {
        return connectorTypeId;
    }


    public String connectorImplementation() {
        return connectorImplementation;
    }


    public IntegrationDirection direction() {
        return direction;
    }


    public String configurationJson() {
        return configurationJson;
    }


    public Integer maxConcurrency() {
        return maxConcurrency;
    }


    public boolean active() {
        return active;
    }


    public ConnectorHealthStatus healthStatus() {
        return healthStatus;
    }


    public Instant lastHealthCheckAt() {
        return lastHealthCheckAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
