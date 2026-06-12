/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationOutboundRecordJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationOutboundRecord.
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
     * Database-backed JPA entity for IntegrationOutboundRecord.
     */
    @Entity
    @Table(name = "hidra_integration_outbound_record")
    public class IntegrationOutboundRecordJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "exchange_message_id", nullable = true, length = 80)
    private String exchangeMessageId;

    @Column(name = "job_run_id", nullable = true, length = 80)
    private String jobRunId;

    @Column(name = "source_module", nullable = false, length = 80)
    private String sourceModule;

    @Column(name = "source_type_code", nullable = false, length = 120)
    private String sourceTypeCode;

    @Column(name = "source_id", nullable = false, length = 120)
    private String sourceId;

    @Column(name = "source_code_snapshot", nullable = true, length = 120)
    private String sourceCodeSnapshot;

    @Column(name = "source_label_snapshot", nullable = true, length = 240)
    private String sourceLabelSnapshot;

    @Column(name = "mapping_profile_id", nullable = true, length = 80)
    private String mappingProfileId;

    @Column(name = "outbound_payload", nullable = true, columnDefinition = "jsonb")
    private String outboundPayload;

    @Column(name = "external_system_id", nullable = false, length = 80)
    private String externalSystemId;

    @Column(name = "external_object_id", nullable = true, length = 255)
    private String externalObjectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private OutboundRecordStatus status;

    @Column(name = "error_code", nullable = true, length = 120)
    private String errorCode;

    @Column(name = "error_message", nullable = true, length = 2000)
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "sent_at", nullable = true)
    private Instant sentAt;

    @Column(name = "acknowledged_at", nullable = true)
    private Instant acknowledgedAt;

        protected IntegrationOutboundRecordJpaEntity() {
            // Required by JPA.
        }

        public IntegrationOutboundRecordJpaEntity(
                String id,
            String exchangeMessageId,
            String jobRunId,
            String sourceModule,
            String sourceTypeCode,
            String sourceId,
            String sourceCodeSnapshot,
            String sourceLabelSnapshot,
            String mappingProfileId,
            String outboundPayload,
            String externalSystemId,
            String externalObjectId,
            OutboundRecordStatus status,
            String errorCode,
            String errorMessage,
            Instant createdAt,
            Instant sentAt,
            Instant acknowledgedAt
        ) {
            this.id = id;
        this.exchangeMessageId = exchangeMessageId;
        this.jobRunId = jobRunId;
        this.sourceModule = sourceModule;
        this.sourceTypeCode = sourceTypeCode;
        this.sourceId = sourceId;
        this.sourceCodeSnapshot = sourceCodeSnapshot;
        this.sourceLabelSnapshot = sourceLabelSnapshot;
        this.mappingProfileId = mappingProfileId;
        this.outboundPayload = outboundPayload;
        this.externalSystemId = externalSystemId;
        this.externalObjectId = externalObjectId;
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
        this.sentAt = sentAt;
        this.acknowledgedAt = acknowledgedAt;
        }


    public String id() {
        return id;
    }


    public String exchangeMessageId() {
        return exchangeMessageId;
    }


    public String jobRunId() {
        return jobRunId;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public String sourceTypeCode() {
        return sourceTypeCode;
    }


    public String sourceId() {
        return sourceId;
    }


    public String sourceCodeSnapshot() {
        return sourceCodeSnapshot;
    }


    public String sourceLabelSnapshot() {
        return sourceLabelSnapshot;
    }


    public String mappingProfileId() {
        return mappingProfileId;
    }


    public String outboundPayload() {
        return outboundPayload;
    }


    public String externalSystemId() {
        return externalSystemId;
    }


    public String externalObjectId() {
        return externalObjectId;
    }


    public OutboundRecordStatus status() {
        return status;
    }


    public String errorCode() {
        return errorCode;
    }


    public String errorMessage() {
        return errorMessage;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant sentAt() {
        return sentAt;
    }


    public Instant acknowledgedAt() {
        return acknowledgedAt;
    }

    }
