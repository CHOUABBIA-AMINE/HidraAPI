/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationInboundRecordJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationInboundRecord.
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
     * Database-backed JPA entity for IntegrationInboundRecord.
     */
    @Entity
    @Table(name = "hidra_integration_inbound_record")
    public class IntegrationInboundRecordJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "exchange_message_id", nullable = false, length = 80)
    private String exchangeMessageId;

    @Column(name = "job_run_id", nullable = true, length = 80)
    private String jobRunId;

    @Column(name = "record_sequence", nullable = false)
    private long recordSequence;

    @Column(name = "mapping_profile_id", nullable = true, length = 80)
    private String mappingProfileId;

    @Column(name = "target_module", nullable = false, length = 80)
    private String targetModule;

    @Column(name = "target_type_code", nullable = false, length = 120)
    private String targetTypeCode;

    @Column(name = "target_id", nullable = true, length = 120)
    private String targetId;

    @Column(name = "target_code_snapshot", nullable = true, length = 120)
    private String targetCodeSnapshot;

    @Column(name = "mapped_payload", nullable = true, columnDefinition = "jsonb")
    private String mappedPayload;

    @Enumerated(EnumType.STRING)
    @Column(name = "validation_status", nullable = false, length = 40)
    private ValidationStatus validationStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "submission_status", nullable = false, length = 40)
    private SubmissionStatus submissionStatus;

    @Column(name = "target_response_code", nullable = true, length = 120)
    private String targetResponseCode;

    @Column(name = "target_response_message", nullable = true, length = 2000)
    private String targetResponseMessage;

    @Column(name = "error_code", nullable = true, length = 120)
    private String errorCode;

    @Column(name = "error_message", nullable = true, length = 2000)
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "submitted_at", nullable = true)
    private Instant submittedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

        protected IntegrationInboundRecordJpaEntity() {
            // Required by JPA.
        }

        public IntegrationInboundRecordJpaEntity(
                String id,
            String exchangeMessageId,
            String jobRunId,
            long recordSequence,
            String mappingProfileId,
            String targetModule,
            String targetTypeCode,
            String targetId,
            String targetCodeSnapshot,
            String mappedPayload,
            ValidationStatus validationStatus,
            SubmissionStatus submissionStatus,
            String targetResponseCode,
            String targetResponseMessage,
            String errorCode,
            String errorMessage,
            Instant createdAt,
            Instant submittedAt,
            Instant completedAt
        ) {
            this.id = id;
        this.exchangeMessageId = exchangeMessageId;
        this.jobRunId = jobRunId;
        this.recordSequence = recordSequence;
        this.mappingProfileId = mappingProfileId;
        this.targetModule = targetModule;
        this.targetTypeCode = targetTypeCode;
        this.targetId = targetId;
        this.targetCodeSnapshot = targetCodeSnapshot;
        this.mappedPayload = mappedPayload;
        this.validationStatus = validationStatus;
        this.submissionStatus = submissionStatus;
        this.targetResponseCode = targetResponseCode;
        this.targetResponseMessage = targetResponseMessage;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
        this.submittedAt = submittedAt;
        this.completedAt = completedAt;
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


    public long recordSequence() {
        return recordSequence;
    }


    public String mappingProfileId() {
        return mappingProfileId;
    }


    public String targetModule() {
        return targetModule;
    }


    public String targetTypeCode() {
        return targetTypeCode;
    }


    public String targetId() {
        return targetId;
    }


    public String targetCodeSnapshot() {
        return targetCodeSnapshot;
    }


    public String mappedPayload() {
        return mappedPayload;
    }


    public ValidationStatus validationStatus() {
        return validationStatus;
    }


    public SubmissionStatus submissionStatus() {
        return submissionStatus;
    }


    public String targetResponseCode() {
        return targetResponseCode;
    }


    public String targetResponseMessage() {
        return targetResponseMessage;
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


    public Instant submittedAt() {
        return submittedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }

    }
