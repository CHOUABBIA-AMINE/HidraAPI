/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentitySynchronizationRecordJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IdentitySynchronizationRecord.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.identity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IdentitySynchronizationRecord.
     */
    @Entity
    @Table(name = "hidra_identity_synchronization_record")
    public class IdentitySynchronizationRecordJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "job_id", nullable = false, length = 80)
    private String jobId;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_type", nullable = false, length = 80)
    private SyncRecordType recordType;

    @Column(name = "external_reference", nullable = false, columnDefinition = "text")
    private String externalReference;

    @Column(name = "local_reference_id", nullable = true, length = 120)
    private String localReferenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation", nullable = false, length = 80)
    private SyncRecordOperation operation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private SyncRecordStatus status;

    @Column(name = "message", nullable = true, columnDefinition = "text")
    private String message;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

        protected IdentitySynchronizationRecordJpaEntity() {
            // Required by JPA.
        }

        public IdentitySynchronizationRecordJpaEntity(
                String id,
            String jobId,
            SyncRecordType recordType,
            String externalReference,
            String localReferenceId,
            SyncRecordOperation operation,
            SyncRecordStatus status,
            String message,
            Instant occurredAt
        ) {
            this.id = id;
        this.jobId = jobId;
        this.recordType = recordType;
        this.externalReference = externalReference;
        this.localReferenceId = localReferenceId;
        this.operation = operation;
        this.status = status;
        this.message = message;
        this.occurredAt = occurredAt;
        }


    public String id() {
        return id;
    }


    public String jobId() {
        return jobId;
    }


    public SyncRecordType recordType() {
        return recordType;
    }


    public String externalReference() {
        return externalReference;
    }


    public String localReferenceId() {
        return localReferenceId;
    }


    public SyncRecordOperation operation() {
        return operation;
    }


    public SyncRecordStatus status() {
        return status;
    }


    public String message() {
        return message;
    }


    public Instant occurredAt() {
        return occurredAt;
    }

    }
