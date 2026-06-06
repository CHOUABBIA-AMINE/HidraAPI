/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitTypeCatalog
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Organization unit type catalog entry with multilingual translations.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitTypeReference;

/**
 * Organization unit type catalog entry with multilingual translations.
 *
 * <p>Business role:
 * Represents configurable and multilingual organization unit type reference data.
 *
 * <p>Architecture role:
 * This aggregate is owned by the organization module and replaces enum-based organization unit type
 * modeling without importing identity, topology, REST, JPA, Spring, or platform infrastructure.
 *
 * @param reference stable catalog reference
 * @param status catalog entry status
 * @param sortOrder display order
 * @param systemDefined true when protected default reference data
 * @param translations localized labels and descriptions
 * @param createdAt creation instant
 * @param updatedAt update instant
 */
public record OrganizationUnitTypeCatalog(
        OrganizationUnitTypeReference reference,
        String status,
        int sortOrder,
        boolean systemDefined,
        List<OrganizationUnitTypeTranslation> translations,
        Instant createdAt,
        Instant updatedAt) implements AggregateRoot<OrganizationUnitTypeReference> {

    public OrganizationUnitTypeCatalog {
        Objects.requireNonNull(reference, "Organization unit type reference must not be null.");
        status = normalizeStatus(status);
        translations = translations == null ? List.of() : List.copyOf(translations);
        Objects.requireNonNull(createdAt, "Organization unit type createdAt must not be null.");
        Objects.requireNonNull(updatedAt, "Organization unit type updatedAt must not be null.");
        if (updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("Organization unit type updatedAt must not be before createdAt.");
        }
    }

    @Override
    public OrganizationUnitTypeReference id() {
        return reference;
    }

    public boolean active() {
        return "ACTIVE".equals(status);
    }

    private static String normalizeStatus(String value) {
        if (value == null || value.isBlank()) {
            return "ACTIVE";
        }
        return value.trim().toUpperCase(java.util.Locale.ROOT);
    }
}
