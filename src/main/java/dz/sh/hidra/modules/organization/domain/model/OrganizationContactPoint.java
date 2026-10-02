/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationContactPoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Operational contact information owned by a typed Organization target.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetReference;
import dz.sh.hidra.modules.organization.domain.value.ContactPointType;

import java.time.Instant;

/**
 * Represents one operational contact channel for an employee or organization unit.
 *
 * <p>Business role: records phone, mobile, email, radio, office, or emergency contact
 * information against an Organization-owned target. For employees, this model is the
 * canonical source for operational EMAIL, MOBILE and PHONE contact data; direct contact
 * fields on Employee are compatibility-only until consumer/data cutover is complete.</p>
 *
 * <p>Architecture role: canonical target state is a typed
 * {@link ContactPointTargetReference}; the model does not import identity, topology,
 * party, or other external bounded-context models.</p>
 *
 * <p>Validation: ID, contact-point type, target, and contact value are mandatory.
 * Target existence is validated at the application boundary because it requires
 * Organization repositories. Lifecycle-specific rules are intentionally not invented
 * by this correction.</p>
 *
 * <p>Usage: use {@link ContactPointTargetReference} with target type EMPLOYEE for
 * employee communication channels and ORGANIZATION_UNIT for unit channels. Persistence
 * stores the governed target enum name in the existing target_type VARCHAR column.
 * Deprecated textual bridges remain only for migration compatibility.</p>
 *
 * @param id contact-point identifier
 * @param contactPointType operational contact channel type
 * @param target typed Organization-owned contact target
 * @param label optional operator/business label
 * @param value contact value such as phone number, email address, or radio call sign
 * @param primaryContact whether this is the target's primary contact
 * @param emergencyContact whether this channel is intended for emergency contact
 * @param active whether the contact point is active
 * @param createdAt creation timestamp
 * @param updatedAt update timestamp
 */
public record OrganizationContactPoint(
        String id,
        ContactPointType contactPointType,
        ContactPointTargetReference target,
        String label,
        String value,
        boolean primaryContact,
        boolean emergencyContact,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {

    public OrganizationContactPoint {
        id = normalize(id);
        label = normalize(label);
        value = normalize(value);

        if (id == null) {
            throw new InvalidOrganizationValueException(
                    "Organization contact-point ID is required."
            );
        }
        if (contactPointType == null) {
            throw new InvalidOrganizationValueException(
                    "Contact-point type is required."
            );
        }
        if (target == null) {
            throw new InvalidOrganizationValueException(
                    "Contact-point target is required."
            );
        }
        if (value == null) {
            throw new InvalidOrganizationValueException(
                    "Contact-point value is required."
            );
        }
    }

    /**
     * Transitional constructor for callers that still provide textual target type/id state.
     */
    @Deprecated(forRemoval = true)
    public OrganizationContactPoint(
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
                ContactPointTargetReference.from(targetType, targetId),
                label,
                value,
                primaryContact,
                emergencyContact,
                active,
                createdAt,
                updatedAt
        );
    }

    /**
     * Transitional textual target-type accessor.
     *
     * @return canonical target type name
     */
    @Deprecated(forRemoval = true)
    public String targetType() {
        return target.type().name();
    }

    /**
     * Transitional target identifier accessor.
     *
     * @return canonical target ID
     */
    @Deprecated(forRemoval = true)
    public String targetId() {
        return target.targetId();
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
