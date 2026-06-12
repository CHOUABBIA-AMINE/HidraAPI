/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationDeadLetterRecordJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationDeadLetterRecord.
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
     * Database-backed JPA entity for IntegrationDeadLetterRecord.
     */
    @Entity
    @Table(name = "hidra_integration_dead_letter_record")
    public class IntegrationDeadLetterRecordJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "external_system_id", nullable = false, length = 80)
    private String externalSystemId;

    @Column(name = "job_run_id", nullable = true, length = 80)
    private String jobRunId;

    @Column(name = "exchange_message_id", nullable = true, length = 80)
    private String exchangeMessageId;

    @Column(name = "inbound_record_id", nullable = true, length = 80)
    private String inboundRecordId;

    @Column(name = "outbound_record_id", nullable = true, length = 80)
    private String outboundRecordId;

    @Column(name = "target_module", nullable = true, length = 80)
    private String targetModule;

    @Column(name = "failure_stage", nullable = false, length = 80)
    private String failureStage;

    @Column(name = "reason_code", nullable = false, length = 120)
    private String reasonCode;

    @Column(name = "reason_message", nullable = false, length = 2000)
    private String reasonMessage;

    @Column(name = "payload_hash", nullable = true, length = 128)
    private String payloadHash;

    @Column(name = "sanitized_payload", nullable = true, columnDefinition = "jsonb")
    private String sanitizedPayload;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private DeadLetterStatus status;

    @Column(name = "resolved_by_actor_id", nullable = true, length = 80)
    private String resolvedByActorId;

    @Column(name = "resolved_at", nullable = true)
    private Instant resolvedAt;

    @Column(name = "resolution_comment", nullable = true, length = 2000)
    private String resolutionComment;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IntegrationDeadLetterRecordJpaEntity() {
            // Required by JPA.
        }

        public IntegrationDeadLetterRecordJpaEntity(
                String id,
            String externalSystemId,
            String jobRunId,
            String exchangeMessageId,
            String inboundRecordId,
            String outboundRecordId,
            String targetModule,
            String failureStage,
            String reasonCode,
            String reasonMessage,
            String payloadHash,
            String sanitizedPayload,
            DeadLetterStatus status,
            String resolvedByActorId,
            Instant resolvedAt,
            String resolutionComment,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.externalSystemId = externalSystemId;
        this.jobRunId = jobRunId;
        this.exchangeMessageId = exchangeMessageId;
        this.inboundRecordId = inboundRecordId;
        this.outboundRecordId = outboundRecordId;
        this.targetModule = targetModule;
        this.failureStage = failureStage;
        this.reasonCode = reasonCode;
        this.reasonMessage = reasonMessage;
        this.payloadHash = payloadHash;
        this.sanitizedPayload = sanitizedPayload;
        this.status = status;
        this.resolvedByActorId = resolvedByActorId;
        this.resolvedAt = resolvedAt;
        this.resolutionComment = resolutionComment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String externalSystemId() {
        return externalSystemId;
    }


    public String jobRunId() {
        return jobRunId;
    }


    public String exchangeMessageId() {
        return exchangeMessageId;
    }


    public String inboundRecordId() {
        return inboundRecordId;
    }


    public String outboundRecordId() {
        return outboundRecordId;
    }


    public String targetModule() {
        return targetModule;
    }


    public String failureStage() {
        return failureStage;
    }


    public String reasonCode() {
        return reasonCode;
    }


    public String reasonMessage() {
        return reasonMessage;
    }


    public String payloadHash() {
        return payloadHash;
    }


    public String sanitizedPayload() {
        return sanitizedPayload;
    }


    public DeadLetterStatus status() {
        return status;
    }


    public String resolvedByActorId() {
        return resolvedByActorId;
    }


    public Instant resolvedAt() {
        return resolvedAt;
    }


    public String resolutionComment() {
        return resolutionComment;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
