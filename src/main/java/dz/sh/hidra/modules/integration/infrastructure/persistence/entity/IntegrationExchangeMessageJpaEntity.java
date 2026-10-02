/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationExchangeMessageJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationExchangeMessage.
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
     * Database-backed JPA entity for IntegrationExchangeMessage.
     */
    @Entity
    @Table(name = "hidra_integration_exchange_message")
    public class IntegrationExchangeMessageJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "job_run_id", nullable = true, length = 80)
    private String jobRunId;

    @Column(name = "external_system_id", nullable = false, length = 80)
    private String externalSystemId;

    @Column(name = "endpoint_id", nullable = true, length = 80)
    private String endpointId;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false, length = 30)
    private IntegrationDirection direction;

    @Column(name = "message_type_id", nullable = false, length = 80)
    private String messageTypeId;

    @Column(name = "external_message_id", nullable = true, length = 255)
    private String externalMessageId;

    @Column(name = "payload_format_id", nullable = false, length = 80)
    private String payloadFormatId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payload_storage_mode", nullable = false, length = 40)
    private PayloadStorageMode payloadStorageMode;

    @Column(name = "payload_sanitized", nullable = true, columnDefinition = "jsonb")
    private String payloadSanitized;

    @Column(name = "payload_reference", nullable = true, length = 1000)
    private String payloadReference;

    @Column(name = "payload_hash", nullable = false, length = 128)
    private String payloadHash;

    @Column(name = "content_length_bytes", nullable = true)
    private Long contentLengthBytes;

    @Column(name = "received_or_sent_at", nullable = false)
    private Instant receivedOrSentAt;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ExchangeMessageStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected IntegrationExchangeMessageJpaEntity() {
            // Required by JPA.
        }

        public IntegrationExchangeMessageJpaEntity(
                String id,
            String jobRunId,
            String externalSystemId,
            String endpointId,
            IntegrationDirection direction,
            String messageTypeId,
            String externalMessageId,
            String payloadFormatId,
            PayloadStorageMode payloadStorageMode,
            String payloadSanitized,
            String payloadReference,
            String payloadHash,
            Long contentLengthBytes,
            Instant receivedOrSentAt,
            String correlationId,
            ExchangeMessageStatus status,
            Instant createdAt
        ) {
            this.id = id;
        this.jobRunId = jobRunId;
        this.externalSystemId = externalSystemId;
        this.endpointId = endpointId;
        this.direction = direction;
        this.messageTypeId = messageTypeId;
        this.externalMessageId = externalMessageId;
        this.payloadFormatId = payloadFormatId;
        this.payloadStorageMode = payloadStorageMode;
        this.payloadSanitized = payloadSanitized;
        this.payloadReference = payloadReference;
        this.payloadHash = payloadHash;
        this.contentLengthBytes = contentLengthBytes;
        this.receivedOrSentAt = receivedOrSentAt;
        this.correlationId = correlationId;
        this.status = status;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String jobRunId() {
        return jobRunId;
    }


    public String externalSystemId() {
        return externalSystemId;
    }


    public String endpointId() {
        return endpointId;
    }


    public IntegrationDirection direction() {
        return direction;
    }


    public String messageTypeId() {
        return messageTypeId;
    }


    public String externalMessageId() {
        return externalMessageId;
    }


    public String payloadFormatId() {
        return payloadFormatId;
    }


    public PayloadStorageMode payloadStorageMode() {
        return payloadStorageMode;
    }


    public String payloadSanitized() {
        return payloadSanitized;
    }


    public String payloadReference() {
        return payloadReference;
    }


    public String payloadHash() {
        return payloadHash;
    }


    public Long contentLengthBytes() {
        return contentLengthBytes;
    }


    public Instant receivedOrSentAt() {
        return receivedOrSentAt;
    }


    public String correlationId() {
        return correlationId;
    }


    public ExchangeMessageStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
