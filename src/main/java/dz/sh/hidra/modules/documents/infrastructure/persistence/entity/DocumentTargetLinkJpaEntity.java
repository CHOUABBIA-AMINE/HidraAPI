/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentTargetLinkJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for DocumentTargetLink.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for DocumentTargetLink.
     */
    @Entity
    @Table(name = "hidra_documents_target_link")
    public class DocumentTargetLinkJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "document_id", nullable = false, length = 80)
    private String documentId;

    @Column(name = "document_version_id", nullable = true, length = 80)
    private String documentVersionId;

    @Column(name = "target_module", nullable = false, length = 80)
    private String targetModule;

    @Column(name = "target_type_code", nullable = false, length = 80)
    private String targetTypeCode;

    @Column(name = "target_id", nullable = false, length = 120)
    private String targetId;

    @Column(name = "target_code_snapshot", nullable = true, length = 120)
    private String targetCodeSnapshot;

    @Column(name = "target_label_snapshot", nullable = true, length = 240)
    private String targetLabelSnapshot;

    @Column(name = "link_role_id", nullable = false, length = 80)
    private String linkRoleId;

    @Column(name = "primary_link", nullable = false)
    private boolean primaryLink;

    @Column(name = "linked_by_actor_id", nullable = false, length = 80)
    private String linkedByActorId;

    @Column(name = "linked_at", nullable = false)
    private Instant linkedAt;

    @Column(name = "unlinked_at", nullable = true)
    private Instant unlinkedAt;

    @Column(name = "active", nullable = false)
    private boolean active;

        protected DocumentTargetLinkJpaEntity() {
            // Required by JPA.
        }

        public DocumentTargetLinkJpaEntity(
                String id,
            String documentId,
            String documentVersionId,
            String targetModule,
            String targetTypeCode,
            String targetId,
            String targetCodeSnapshot,
            String targetLabelSnapshot,
            String linkRoleId,
            boolean primaryLink,
            String linkedByActorId,
            Instant linkedAt,
            Instant unlinkedAt,
            boolean active
        ) {
            this.id = id;
        this.documentId = documentId;
        this.documentVersionId = documentVersionId;
        this.targetModule = targetModule;
        this.targetTypeCode = targetTypeCode;
        this.targetId = targetId;
        this.targetCodeSnapshot = targetCodeSnapshot;
        this.targetLabelSnapshot = targetLabelSnapshot;
        this.linkRoleId = linkRoleId;
        this.primaryLink = primaryLink;
        this.linkedByActorId = linkedByActorId;
        this.linkedAt = linkedAt;
        this.unlinkedAt = unlinkedAt;
        this.active = active;
        }


    public String id() {
        return id;
    }


    public String documentId() {
        return documentId;
    }


    public String documentVersionId() {
        return documentVersionId;
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


    public String targetLabelSnapshot() {
        return targetLabelSnapshot;
    }


    public String linkRoleId() {
        return linkRoleId;
    }


    public boolean primaryLink() {
        return primaryLink;
    }


    public String linkedByActorId() {
        return linkedByActorId;
    }


    public Instant linkedAt() {
        return linkedAt;
    }


    public Instant unlinkedAt() {
        return unlinkedAt;
    }


    public boolean active() {
        return active;
    }

    }
