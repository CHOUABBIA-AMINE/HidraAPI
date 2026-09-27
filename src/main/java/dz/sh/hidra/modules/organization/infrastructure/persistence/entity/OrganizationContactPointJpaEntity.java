/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationContactPointJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for governed Organization contact-point targets.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetType;
import dz.sh.hidra.modules.organization.domain.value.ContactPointType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

/**
 * Persists operational contact data while governing the owning Organization target type.
 *
 * <p>Business role: stores an employee- or organization-unit-owned operational contact
 * channel.</p>
 *
 * <p>Architecture role: persistence representation of OrganizationContactPoint.
 * target_type remains VARCHAR and uses {@link EnumType#STRING}.</p>
 *
 * <p>Validation: JPA mapping constrains new runtime writes to the governed enum names.
 * Database preflight/CHECK/FK enforcement is deliberately deferred to ORG-046.</p>
 *
 * <p>Usage: infrastructure only; domain/application code must use typed target
 * references instead of this entity.</p>
 */
@Entity
@Table(name = "hidra_org_contact_point")
public class OrganizationContactPointJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_point_type", nullable = false, length = 80)
    private ContactPointType contactPointType;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 80)
    private ContactPointTargetType targetType;

    @Column(name = "target_id", nullable = false, length = 80)
    private String targetId;

    @Column(name = "label", length = 255)
    private String label;

    @Column(name = "value", nullable = false, length = 255)
    private String value;

    @Column(name = "primary_contact", nullable = false)
    private boolean primaryContact;

    @Column(name = "emergency_contact", nullable = false)
    private boolean emergencyContact;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected OrganizationContactPointJpaEntity() {
        // Required by JPA.
    }

    public OrganizationContactPointJpaEntity(
            String id,
            ContactPointType contactPointType,
            ContactPointTargetType targetType,
            String targetId,
            String label,
            String value,
            boolean primaryContact,
            boolean emergencyContact,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.contactPointType = contactPointType;
        this.targetType = targetType;
        this.targetId = targetId;
        this.label = label;
        this.value = value;
        this.primaryContact = primaryContact;
        this.emergencyContact = emergencyContact;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Transitional constructor for legacy persistence callers with textual target types.
     */
    @Deprecated(forRemoval = true)
    public OrganizationContactPointJpaEntity(
            String id,
            ContactPointType contactPointType,
            String targetType,
            String targetId,
            String label,
            String value,
            boolean primaryContact,
            boolean emergencyContact,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {
        this(
                id,
                contactPointType,
                ContactPointTargetType.from(targetType),
                targetId,
                label,
                value,
                primaryContact,
                emergencyContact,
                active,
                createdAt,
                updatedAt
        );
    }

    public String id() {
        return id;
    }

    public ContactPointType contactPointType() {
        return contactPointType;
    }

    public ContactPointTargetType contactTargetType() {
        return targetType;
    }

    @Deprecated(forRemoval = true)
    public String targetType() {
        return targetType.name();
    }

    public String targetId() {
        return targetId;
    }

    public String label() {
        return label;
    }

    public String value() {
        return value;
    }

    public boolean primaryContact() {
        return primaryContact;
    }

    public boolean emergencyContact() {
        return emergencyContact;
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
