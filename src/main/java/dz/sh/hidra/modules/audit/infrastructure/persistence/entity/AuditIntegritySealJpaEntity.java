/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditIntegritySealJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuditIntegritySeal.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.entity;

import dz.sh.hidra.modules.audit.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AuditIntegritySeal.
     */
    @Entity
    @Table(name = "hidra_audit_integrity_seal")
    public class AuditIntegritySealJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "seal_type_id", nullable = false, length = 80)
    private String sealTypeId;

    @Column(name = "audit_event_id", nullable = true, length = 80)
    private String auditEventId;

    @Column(name = "from_recorded_at", nullable = true)
    private Instant fromRecordedAt;

    @Column(name = "to_recorded_at", nullable = true)
    private Instant toRecordedAt;

    @Column(name = "event_count", nullable = false)
    private Integer eventCount;

    @Column(name = "hash_algorithm", nullable = false, length = 80)
    private String hashAlgorithm;

    @Column(name = "root_hash", nullable = false, length = 256)
    private String rootHash;

    @Column(name = "previous_seal_hash", nullable = true, length = 256)
    private String previousSealHash;

    @Column(name = "sealed_by_actor_id", nullable = true, length = 120)
    private String sealedByActorId;

    @Column(name = "sealed_at", nullable = false)
    private Instant sealedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 40)
    private AuditSealVerificationStatus verificationStatus;

    @Column(name = "verified_at", nullable = true)
    private Instant verifiedAt;

        protected AuditIntegritySealJpaEntity() {
            // Required by JPA.
        }

        public AuditIntegritySealJpaEntity(
                String id,
            String sealTypeId,
            String auditEventId,
            Instant fromRecordedAt,
            Instant toRecordedAt,
            Integer eventCount,
            String hashAlgorithm,
            String rootHash,
            String previousSealHash,
            String sealedByActorId,
            Instant sealedAt,
            AuditSealVerificationStatus verificationStatus,
            Instant verifiedAt
        ) {
            this.id = id;
        this.sealTypeId = sealTypeId;
        this.auditEventId = auditEventId;
        this.fromRecordedAt = fromRecordedAt;
        this.toRecordedAt = toRecordedAt;
        this.eventCount = eventCount;
        this.hashAlgorithm = hashAlgorithm;
        this.rootHash = rootHash;
        this.previousSealHash = previousSealHash;
        this.sealedByActorId = sealedByActorId;
        this.sealedAt = sealedAt;
        this.verificationStatus = verificationStatus;
        this.verifiedAt = verifiedAt;
        }


    public String id() {
        return id;
    }


    public String sealTypeId() {
        return sealTypeId;
    }


    public String auditEventId() {
        return auditEventId;
    }


    public Instant fromRecordedAt() {
        return fromRecordedAt;
    }


    public Instant toRecordedAt() {
        return toRecordedAt;
    }


    public Integer eventCount() {
        return eventCount;
    }


    public String hashAlgorithm() {
        return hashAlgorithm;
    }


    public String rootHash() {
        return rootHash;
    }


    public String previousSealHash() {
        return previousSealHash;
    }


    public String sealedByActorId() {
        return sealedByActorId;
    }


    public Instant sealedAt() {
        return sealedAt;
    }


    public AuditSealVerificationStatus verificationStatus() {
        return verificationStatus;
    }


    public Instant verifiedAt() {
        return verifiedAt;
    }

    }
