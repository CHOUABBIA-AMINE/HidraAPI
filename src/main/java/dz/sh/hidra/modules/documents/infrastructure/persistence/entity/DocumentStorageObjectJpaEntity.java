/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentStorageObjectJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for DocumentStorageObject.
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
     * Database-backed JPA entity for DocumentStorageObject.
     */
    @Entity
    @Table(name = "hidra_documents_storage_object")
    public class DocumentStorageObjectJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "storage_provider_id", nullable = false, length = 80)
    private String storageProviderId;

    @Column(name = "bucket_or_container", nullable = true, length = 160)
    private String bucketOrContainer;

    @Column(name = "object_key", nullable = false, length = 500)
    private String objectKey;

    @Column(name = "object_uri", nullable = true, length = 1000)
    private String objectUri;

    @Column(name = "encrypted", nullable = false)
    private boolean encrypted;

    @Column(name = "encryption_key_reference", nullable = true, length = 160)
    private String encryptionKeyReference;

    @Column(name = "content_length_bytes", nullable = false)
    private long contentLengthBytes;

    @Column(name = "content_type", nullable = false, length = 120)
    private String contentType;

    @Column(name = "checksum_algorithm", nullable = false, length = 40)
    private String checksumAlgorithm;

    @Column(name = "checksum_value", nullable = false, length = 160)
    private String checksumValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_status", nullable = false, length = 40)
    private DocumentStorageStatus storageStatus;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "verified_at", nullable = true)
    private Instant verifiedAt;

        protected DocumentStorageObjectJpaEntity() {
            // Required by JPA.
        }

        public DocumentStorageObjectJpaEntity(
                String id,
            String storageProviderId,
            String bucketOrContainer,
            String objectKey,
            String objectUri,
            boolean encrypted,
            String encryptionKeyReference,
            long contentLengthBytes,
            String contentType,
            String checksumAlgorithm,
            String checksumValue,
            DocumentStorageStatus storageStatus,
            Instant createdAt,
            Instant verifiedAt
        ) {
            this.id = id;
        this.storageProviderId = storageProviderId;
        this.bucketOrContainer = bucketOrContainer;
        this.objectKey = objectKey;
        this.objectUri = objectUri;
        this.encrypted = encrypted;
        this.encryptionKeyReference = encryptionKeyReference;
        this.contentLengthBytes = contentLengthBytes;
        this.contentType = contentType;
        this.checksumAlgorithm = checksumAlgorithm;
        this.checksumValue = checksumValue;
        this.storageStatus = storageStatus;
        this.createdAt = createdAt;
        this.verifiedAt = verifiedAt;
        }


    public String id() {
        return id;
    }


    public String storageProviderId() {
        return storageProviderId;
    }


    public String bucketOrContainer() {
        return bucketOrContainer;
    }


    public String objectKey() {
        return objectKey;
    }


    public String objectUri() {
        return objectUri;
    }


    public boolean encrypted() {
        return encrypted;
    }


    public String encryptionKeyReference() {
        return encryptionKeyReference;
    }


    public long contentLengthBytes() {
        return contentLengthBytes;
    }


    public String contentType() {
        return contentType;
    }


    public String checksumAlgorithm() {
        return checksumAlgorithm;
    }


    public String checksumValue() {
        return checksumValue;
    }


    public DocumentStorageStatus storageStatus() {
        return storageStatus;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant verifiedAt() {
        return verifiedAt;
    }

    }
