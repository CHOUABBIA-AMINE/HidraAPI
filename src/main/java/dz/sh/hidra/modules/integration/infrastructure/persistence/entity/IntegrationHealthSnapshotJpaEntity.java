/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationHealthSnapshotJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationHealthSnapshot.
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
     * Database-backed JPA entity for IntegrationHealthSnapshot.
     */
    @Entity
    @Table(name = "hidra_integration_health_snapshot")
    public class IntegrationHealthSnapshotJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "external_system_id", nullable = false, length = 80)
    private String externalSystemId;

    @Column(name = "endpoint_id", nullable = true, length = 80)
    private String endpointId;

    @Column(name = "connector_instance_id", nullable = true, length = 80)
    private String connectorInstanceId;

    @Column(name = "job_definition_id", nullable = true, length = 80)
    private String jobDefinitionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "health_status", nullable = false, length = 40)
    private ConnectorHealthStatus healthStatus;

    @Column(name = "latency_ms", nullable = true)
    private Long latencyMs;

    @Column(name = "last_success_at", nullable = true)
    private Instant lastSuccessAt;

    @Column(name = "last_failure_at", nullable = true)
    private Instant lastFailureAt;

    @Column(name = "error_code", nullable = true, length = 120)
    private String errorCode;

    @Column(name = "error_message", nullable = true, length = 2000)
    private String errorMessage;

    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt;

        protected IntegrationHealthSnapshotJpaEntity() {
            // Required by JPA.
        }

        public IntegrationHealthSnapshotJpaEntity(
                String id,
            String externalSystemId,
            String endpointId,
            String connectorInstanceId,
            String jobDefinitionId,
            ConnectorHealthStatus healthStatus,
            Long latencyMs,
            Instant lastSuccessAt,
            Instant lastFailureAt,
            String errorCode,
            String errorMessage,
            Instant capturedAt
        ) {
            this.id = id;
        this.externalSystemId = externalSystemId;
        this.endpointId = endpointId;
        this.connectorInstanceId = connectorInstanceId;
        this.jobDefinitionId = jobDefinitionId;
        this.healthStatus = healthStatus;
        this.latencyMs = latencyMs;
        this.lastSuccessAt = lastSuccessAt;
        this.lastFailureAt = lastFailureAt;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.capturedAt = capturedAt;
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


    public String connectorInstanceId() {
        return connectorInstanceId;
    }


    public String jobDefinitionId() {
        return jobDefinitionId;
    }


    public ConnectorHealthStatus healthStatus() {
        return healthStatus;
    }


    public Long latencyMs() {
        return latencyMs;
    }


    public Instant lastSuccessAt() {
        return lastSuccessAt;
    }


    public Instant lastFailureAt() {
        return lastFailureAt;
    }


    public String errorCode() {
        return errorCode;
    }


    public String errorMessage() {
        return errorMessage;
    }


    public Instant capturedAt() {
        return capturedAt;
    }

    }
