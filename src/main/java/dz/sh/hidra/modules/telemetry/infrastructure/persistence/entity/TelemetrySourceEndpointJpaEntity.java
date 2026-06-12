/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceEndpointJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TelemetrySourceEndpoint.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for TelemetrySourceEndpoint.
     */
    @Entity
    @Table(name = "hidra_telemetry_source_endpoint")
    public class TelemetrySourceEndpointJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "source_id", nullable = false, length = 80)
    private String sourceId;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "endpoint_role", nullable = false, length = 80)
    private EndpointRole endpointRole;

    @Column(name = "protocol_id", nullable = false, length = 80)
    private String protocolId;

    @Column(name = "endpoint_uri", nullable = true, columnDefinition = "text")
    private String endpointUri;

    @Column(name = "host", nullable = true, length = 160)
    private String host;

    @Column(name = "port", nullable = true)
    private Integer port;

    @Column(name = "path_or_topic", nullable = true, length = 500)
    private String pathOrTopic;

    @Column(name = "polling_interval_seconds", nullable = true)
    private Integer pollingIntervalSeconds;

    @Column(name = "timeout_seconds", nullable = true)
    private Integer timeoutSeconds;

    @Column(name = "credential_reference", nullable = true, length = 80)
    private String credentialReference;

    @Column(name = "connection_options_json", nullable = true, columnDefinition = "jsonb")
    private String connectionOptionsJson;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected TelemetrySourceEndpointJpaEntity() {
            // Required by JPA.
        }

        public TelemetrySourceEndpointJpaEntity(
                String id,
            String sourceId,
            String code,
            EndpointRole endpointRole,
            String protocolId,
            String endpointUri,
            String host,
            Integer port,
            String pathOrTopic,
            Integer pollingIntervalSeconds,
            Integer timeoutSeconds,
            String credentialReference,
            String connectionOptionsJson,
            boolean active,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.sourceId = sourceId;
        this.code = code;
        this.endpointRole = endpointRole;
        this.protocolId = protocolId;
        this.endpointUri = endpointUri;
        this.host = host;
        this.port = port;
        this.pathOrTopic = pathOrTopic;
        this.pollingIntervalSeconds = pollingIntervalSeconds;
        this.timeoutSeconds = timeoutSeconds;
        this.credentialReference = credentialReference;
        this.connectionOptionsJson = connectionOptionsJson;
        this.active = active;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String sourceId() {
        return sourceId;
    }


    public String code() {
        return code;
    }


    public EndpointRole endpointRole() {
        return endpointRole;
    }


    public String protocolId() {
        return protocolId;
    }


    public String endpointUri() {
        return endpointUri;
    }


    public String host() {
        return host;
    }


    public Integer port() {
        return port;
    }


    public String pathOrTopic() {
        return pathOrTopic;
    }


    public Integer pollingIntervalSeconds() {
        return pollingIntervalSeconds;
    }


    public Integer timeoutSeconds() {
        return timeoutSeconds;
    }


    public String credentialReference() {
        return credentialReference;
    }


    public String connectionOptionsJson() {
        return connectionOptionsJson;
    }


    public boolean active() {
        return active;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
