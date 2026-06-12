/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentAccessGrantJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for DocumentAccessGrant.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.entity;

import dz.sh.hidra.modules.documents.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for DocumentAccessGrant.
     */
    @Entity
    @Table(name = "hidra_documents_access_grant")
    public class DocumentAccessGrantJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "document_id", nullable = false, length = 80)
    private String documentId;

    @Column(name = "document_version_id", nullable = true, length = 80)
    private String documentVersionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "principal_type", nullable = false, length = 40)
    private DocumentPrincipalType principalType;

    @Column(name = "principal_id", nullable = false, length = 120)
    private String principalId;

    @Column(name = "principal_label_snapshot", nullable = true, length = 160)
    private String principalLabelSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_level", nullable = false, length = 40)
    private DocumentAccessLevel accessLevel;

    @Column(name = "granted_by_actor_id", nullable = false, length = 80)
    private String grantedByActorId;

    @Column(name = "granted_at", nullable = false)
    private Instant grantedAt;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "revoked_at", nullable = true)
    private Instant revokedAt;

    @Column(name = "active", nullable = false)
    private boolean active;

        protected DocumentAccessGrantJpaEntity() {
            // Required by JPA.
        }

        public DocumentAccessGrantJpaEntity(
                String id,
            String documentId,
            String documentVersionId,
            DocumentPrincipalType principalType,
            String principalId,
            String principalLabelSnapshot,
            DocumentAccessLevel accessLevel,
            String grantedByActorId,
            Instant grantedAt,
            Instant validFrom,
            Instant validTo,
            Instant revokedAt,
            boolean active
        ) {
            this.id = id;
        this.documentId = documentId;
        this.documentVersionId = documentVersionId;
        this.principalType = principalType;
        this.principalId = principalId;
        this.principalLabelSnapshot = principalLabelSnapshot;
        this.accessLevel = accessLevel;
        this.grantedByActorId = grantedByActorId;
        this.grantedAt = grantedAt;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.revokedAt = revokedAt;
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


    public DocumentPrincipalType principalType() {
        return principalType;
    }


    public String principalId() {
        return principalId;
    }


    public String principalLabelSnapshot() {
        return principalLabelSnapshot;
    }


    public DocumentAccessLevel accessLevel() {
        return accessLevel;
    }


    public String grantedByActorId() {
        return grantedByActorId;
    }


    public Instant grantedAt() {
        return grantedAt;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public Instant revokedAt() {
        return revokedAt;
    }


    public boolean active() {
        return active;
    }

    }
