/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalObjectReferenceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ExternalObjectReference.
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
     * Database-backed JPA entity for ExternalObjectReference.
     */
    @Entity
    @Table(name = "hidra_integration_external_object_reference")
    public class ExternalObjectReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "external_system_id", nullable = false, length = 80)
    private String externalSystemId;

    @Column(name = "external_object_type", nullable = false, length = 120)
    private String externalObjectType;

    @Column(name = "external_object_id", nullable = false, length = 255)
    private String externalObjectId;

    @Column(name = "external_object_code", nullable = true, length = 255)
    private String externalObjectCode;

    @Column(name = "target_module", nullable = false, length = 80)
    private String targetModule;

    @Column(name = "target_type_code", nullable = false, length = 120)
    private String targetTypeCode;

    @Column(name = "target_id", nullable = false, length = 120)
    private String targetId;

    @Column(name = "target_code_snapshot", nullable = true, length = 120)
    private String targetCodeSnapshot;

    @Column(name = "target_label_snapshot", nullable = true, length = 240)
    private String targetLabelSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "confidence_level", nullable = false, length = 40)
    private ExternalObjectConfidence confidenceLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ExternalObjectReferenceStatus status;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ExternalObjectReferenceJpaEntity() {
            // Required by JPA.
        }

        public ExternalObjectReferenceJpaEntity(
                String id,
            String externalSystemId,
            String externalObjectType,
            String externalObjectId,
            String externalObjectCode,
            String targetModule,
            String targetTypeCode,
            String targetId,
            String targetCodeSnapshot,
            String targetLabelSnapshot,
            ExternalObjectConfidence confidenceLevel,
            ExternalObjectReferenceStatus status,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.externalSystemId = externalSystemId;
        this.externalObjectType = externalObjectType;
        this.externalObjectId = externalObjectId;
        this.externalObjectCode = externalObjectCode;
        this.targetModule = targetModule;
        this.targetTypeCode = targetTypeCode;
        this.targetId = targetId;
        this.targetCodeSnapshot = targetCodeSnapshot;
        this.targetLabelSnapshot = targetLabelSnapshot;
        this.confidenceLevel = confidenceLevel;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String externalSystemId() {
        return externalSystemId;
    }


    public String externalObjectType() {
        return externalObjectType;
    }


    public String externalObjectId() {
        return externalObjectId;
    }


    public String externalObjectCode() {
        return externalObjectCode;
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


    public ExternalObjectConfidence confidenceLevel() {
        return confidenceLevel;
    }


    public ExternalObjectReferenceStatus status() {
        return status;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
