/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationEvidenceLinkJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationEvidenceLink.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for NotificationEvidenceLink.
     */
    @Entity
    @Table(name = "hidra_notification_evidence_link")
    public class NotificationEvidenceLinkJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "notification_request_id", nullable = true, length = 80)
    private String notificationRequestId;

    @Column(name = "message_id", nullable = true, length = 80)
    private String messageId;

    @Column(name = "evidence_type", nullable = false, length = 120)
    private String evidenceType;

    @Column(name = "reference_module", nullable = false, length = 80)
    private String referenceModule;

    @Column(name = "reference_type", nullable = false, length = 120)
    private String referenceType;

    @Column(name = "reference_id", nullable = false, length = 120)
    private String referenceId;

    @Column(name = "reference_code_snapshot", nullable = true, length = 120)
    private String referenceCodeSnapshot;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected NotificationEvidenceLinkJpaEntity() {
            // Required by JPA.
        }

        public NotificationEvidenceLinkJpaEntity(
                String id,
            String notificationRequestId,
            String messageId,
            String evidenceType,
            String referenceModule,
            String referenceType,
            String referenceId,
            String referenceCodeSnapshot,
            Instant createdAt
        ) {
            this.id = id;
        this.notificationRequestId = notificationRequestId;
        this.messageId = messageId;
        this.evidenceType = evidenceType;
        this.referenceModule = referenceModule;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.referenceCodeSnapshot = referenceCodeSnapshot;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String notificationRequestId() {
        return notificationRequestId;
    }


    public String messageId() {
        return messageId;
    }


    public String evidenceType() {
        return evidenceType;
    }


    public String referenceModule() {
        return referenceModule;
    }


    public String referenceType() {
        return referenceType;
    }


    public String referenceId() {
        return referenceId;
    }


    public String referenceCodeSnapshot() {
        return referenceCodeSnapshot;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
