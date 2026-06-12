/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SubjectSecurityAttributeJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SubjectSecurityAttribute.
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
     * Database-backed JPA entity for SubjectSecurityAttribute.
     */
    @Entity
    @Table(name = "hidra_identity_subject_security_attribute")
    public class SubjectSecurityAttributeJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_type", nullable = false, length = 80)
    private SubjectAttributeOwnerType subjectType;

    @Column(name = "subject_id", nullable = false, length = 80)
    private String subjectId;

    @Column(name = "attribute_definition_id", nullable = false, length = 80)
    private String attributeDefinitionId;

    @Column(name = "attribute_value", nullable = false, columnDefinition = "text")
    private String attributeValue;

    @Column(name = "source_provider_id", nullable = true, length = 120)
    private String sourceProviderId;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PermissionStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected SubjectSecurityAttributeJpaEntity() {
            // Required by JPA.
        }

        public SubjectSecurityAttributeJpaEntity(
                String id,
            SubjectAttributeOwnerType subjectType,
            String subjectId,
            String attributeDefinitionId,
            String attributeValue,
            String sourceProviderId,
            Instant validFrom,
            Instant validTo,
            PermissionStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.subjectType = subjectType;
        this.subjectId = subjectId;
        this.attributeDefinitionId = attributeDefinitionId;
        this.attributeValue = attributeValue;
        this.sourceProviderId = sourceProviderId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public SubjectAttributeOwnerType subjectType() {
        return subjectType;
    }


    public String subjectId() {
        return subjectId;
    }


    public String attributeDefinitionId() {
        return attributeDefinitionId;
    }


    public String attributeValue() {
        return attributeValue;
    }


    public String sourceProviderId() {
        return sourceProviderId;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public PermissionStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
