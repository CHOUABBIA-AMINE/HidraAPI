/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ContactPointTargetReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Typed reference to an Organization-owned employee or organization-unit contact target.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;

/**
 * Identifies the Organization-owned subject to which an operational contact point belongs.
 *
 * <p>Business role: binds a governed target category to the native identifier of the
 * employee or organization unit whose phone, email, radio, office, or emergency
 * contact data is being recorded.</p>
 *
 * <p>Architecture role: preserves a typed Organization-only reference without
 * importing the referenced aggregate into the contact-point model.</p>
 *
 * <p>Validation: target type and native target identifier are mandatory. Existence is
 * deliberately checked by the application validator because it requires repositories.</p>
 *
 * <p>Usage: this is the canonical target state of {@code OrganizationContactPoint}.
 * Textual type/id pairs are accepted only through {@link #from(String, String)} for
 * compatibility.</p>
 *
 * @param type governed Organization contact target category
 * @param targetId native identifier of the referenced Organization subject
 */
public record ContactPointTargetReference(
        ContactPointTargetType type,
        String targetId
) {

    public ContactPointTargetReference {
        if (type == null) {
            throw new InvalidOrganizationValueException(
                    "Contact-point target type is required."
            );
        }
        if (targetId == null || targetId.isBlank()) {
            throw new InvalidOrganizationValueException(
                    "Contact-point target ID is required."
            );
        }
        targetId = targetId.trim();
    }

    /**
     * Builds a typed target reference from legacy textual state.
     *
     * @param type textual target discriminator
     * @param targetId native target identifier
     * @return validated typed target reference
     */
    public static ContactPointTargetReference from(String type, String targetId) {
        return new ContactPointTargetReference(ContactPointTargetType.from(type), targetId);
    }
}
