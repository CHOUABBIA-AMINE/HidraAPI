/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalEndpointJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ExternalEndpoint.
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
     * Database-backed JPA entity for ExternalEndpoint.
     */
    @Entity
    @Table(name = "hidra_integration_external_endpoint")
    public class ExternalEndpointJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "external_system_id", nullable = false, length = 80)
    private String externalSystemId;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "endpoint_type_id", nullable = false, length = 80)
    private String endpointTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false, length = 30)
    private IntegrationDirection direction;

    @Column(name = "endpoint_uri", nullable = true, length = 1000)
    private String endpointUri;

    @Column(name = "host", nullable = true, length = 255)
    private String host;

    @Column(name = "port", nullable = true)
    private Integer port;

    @Column(name = "path_or_topic", nullable = true, length = 500)
    private String pathOrTopic;

    @Column(name = "protocol_id", nullable = false, length = 80)
    private String protocolId;

    @Column(name = "polling_interval_seconds", nullable = true)
    private Integer pollingIntervalSeconds;

    @Column(name = "timeout_seconds", nullable = true)
    private Integer timeoutSeconds;

    @Column(name = "credential_reference", nullable = true, length = 255)
    private String credentialReference;

    @Column(name = "tls_required", nullable = false)
    private boolean tlsRequired;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ExternalEndpointJpaEntity() {
            // Required by JPA.
        }

        public ExternalEndpointJpaEntity(
                String id,
            String externalSystemId,
            String code,
            String endpointTypeId,
            IntegrationDirection direction,
            String endpointUri,
            String host,
            Integer port,
            String pathOrTopic,
            String protocolId,
            Integer pollingIntervalSeconds,
            Integer timeoutSeconds,
            String credentialReference,
            boolean tlsRequired,
            boolean active,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.externalSystemId = externalSystemId;
        this.code = code;
        this.endpointTypeId = endpointTypeId;
        this.direction = direction;
        this.endpointUri = endpointUri;
        this.host = host;
        this.port = port;
        this.pathOrTopic = pathOrTopic;
        this.protocolId = protocolId;
        this.pollingIntervalSeconds = pollingIntervalSeconds;
        this.timeoutSeconds = timeoutSeconds;
        this.credentialReference = credentialReference;
        this.tlsRequired = tlsRequired;
        this.active = active;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String externalSystemId() {
        return externalSystemId;
    }


    public String code() {
        return code;
    }


    public String endpointTypeId() {
        return endpointTypeId;
    }


    public IntegrationDirection direction() {
        return direction;
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


    public String protocolId() {
        return protocolId;
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


    public boolean tlsRequired() {
        return tlsRequired;
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
