/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationExternalReferenceJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ConfigurationExternalReference.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for ConfigurationExternalReference.
     */
    @Entity
    @Table(name = "hidra_configuration_external_reference")
    public class ConfigurationExternalReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "target_type", nullable = false, length = 120)
    private String targetType;

    @Column(name = "target_id", nullable = false, length = 120)
    private String targetId;

    @Column(name = "target_code_snapshot", nullable = true, length = 120)
    private String targetCodeSnapshot;

    @Column(name = "target_label_snapshot", nullable = true, length = 240)
    private String targetLabelSnapshot;

    @Column(name = "reference_module", nullable = true, length = 80)
    private String referenceModule;

    @Column(name = "reference_type", nullable = false, length = 120)
    private String referenceType;

    @Column(name = "reference_id", nullable = false, length = 120)
    private String referenceId;

    @Column(name = "reference_code_snapshot", nullable = true, length = 120)
    private String referenceCodeSnapshot;

    @Column(name = "reference_label_snapshot", nullable = true, length = 240)
    private String referenceLabelSnapshot;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ConfigurationExternalReferenceJpaEntity() {
            // Required by JPA.
        }

        public ConfigurationExternalReferenceJpaEntity(
                String id,
            String targetType,
            String targetId,
            String targetCodeSnapshot,
            String targetLabelSnapshot,
            String referenceModule,
            String referenceType,
            String referenceId,
            String referenceCodeSnapshot,
            String referenceLabelSnapshot,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.targetType = targetType;
        this.targetId = targetId;
        this.targetCodeSnapshot = targetCodeSnapshot;
        this.targetLabelSnapshot = targetLabelSnapshot;
        this.referenceModule = referenceModule;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.referenceCodeSnapshot = referenceCodeSnapshot;
        this.referenceLabelSnapshot = referenceLabelSnapshot;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String targetType() {
        return targetType;
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


    public String referenceLabelSnapshot() {
        return referenceLabelSnapshot;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
