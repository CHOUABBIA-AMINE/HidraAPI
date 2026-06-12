/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationContactPointJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for OrganizationContactPoint.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for OrganizationContactPoint.
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

    @Column(name = "target_type", nullable = false, length = 80)
    private String targetType;

    @Column(name = "target_id", nullable = false, length = 80)
    private String targetId;

    @Column(name = "label", nullable = true, length = 255)
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


    public String id() {
        return id;
    }


    public ContactPointType contactPointType() {
        return contactPointType;
    }


    public String targetType() {
        return targetType;
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
