/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitTypeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Stable reference to an organization unit type catalog entry.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import java.util.Locale;
import java.util.Map;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Stable reference to an organization unit type catalog entry.
 *
 * <p>Business role:
 * Represents the language-neutral catalog identity of an organization unit type such as COMPANY,
 * REGION, STATION, TEAM, or PROJECT_TEAM.
 *
 * <p>Architecture role:
 * This value object replaces the old enum-based organization unit type model. It carries the catalog
 * id used by persistence and the stable code used by business APIs without depending on REST, JPA,
 * Spring, identity, topology, or platform code.
 *
 * <p>Validation:
 * The id and code are mandatory. Known seed codes use deterministic ids that match the organization
 * unit type catalog migration. Unknown codes are allowed only as stable custom catalog references.
 *
 * <p>Usage:
 * Use this reference in organization domain, application commands, queries, DTOs, and persistence
 * mapping whenever an organization unit type is needed.
 *
 * @param id catalog entry identifier
 * @param code stable language-neutral catalog code
 */
public record OrganizationUnitTypeReference(String id, String code) implements ValueObject {

    private static final String PREFIX = "organization-out-";

    private static final Map<String, String> SEEDED_IDS = Map.ofEntries(
            Map.entry("COMPANY", PREFIX + "company"),
            Map.entry("DIVISION", PREFIX + "division"),
            Map.entry("DIRECTION", PREFIX + "direction"),
            Map.entry("DEPARTMENT", PREFIX + "department"),
            Map.entry("REGION", PREFIX + "region"),
            Map.entry("AREA", PREFIX + "area"),
            Map.entry("DISTRICT", PREFIX + "district"),
            Map.entry("STATION", PREFIX + "station"),
            Map.entry("TEAM", PREFIX + "team"),
            Map.entry("PROJECT_TEAM", PREFIX + "project-team"),
            Map.entry("OTHER", PREFIX + "other"));

    private static final Map<String, String> CODES_BY_ID = Map.ofEntries(
            Map.entry(PREFIX + "company", "COMPANY"),
            Map.entry(PREFIX + "division", "DIVISION"),
            Map.entry(PREFIX + "direction", "DIRECTION"),
            Map.entry(PREFIX + "department", "DEPARTMENT"),
            Map.entry(PREFIX + "region", "REGION"),
            Map.entry(PREFIX + "area", "AREA"),
            Map.entry(PREFIX + "district", "DISTRICT"),
            Map.entry(PREFIX + "station", "STATION"),
            Map.entry(PREFIX + "team", "TEAM"),
            Map.entry(PREFIX + "project-team", "PROJECT_TEAM"),
            Map.entry(PREFIX + "other", "OTHER"));

    public static final OrganizationUnitTypeReference COMPANY = ofCode("COMPANY");
    public static final OrganizationUnitTypeReference DIVISION = ofCode("DIVISION");
    public static final OrganizationUnitTypeReference DIRECTION = ofCode("DIRECTION");
    public static final OrganizationUnitTypeReference DEPARTMENT = ofCode("DEPARTMENT");
    public static final OrganizationUnitTypeReference REGION = ofCode("REGION");
    public static final OrganizationUnitTypeReference AREA = ofCode("AREA");
    public static final OrganizationUnitTypeReference DISTRICT = ofCode("DISTRICT");
    public static final OrganizationUnitTypeReference STATION = ofCode("STATION");
    public static final OrganizationUnitTypeReference TEAM = ofCode("TEAM");
    public static final OrganizationUnitTypeReference PROJECT_TEAM = ofCode("PROJECT_TEAM");
    public static final OrganizationUnitTypeReference OTHER = ofCode("OTHER");

    public OrganizationUnitTypeReference {
        id = normalizeId(id);
        code = normalizeCode(code);
    }

    public static OrganizationUnitTypeReference of(String id, String code) {
        return new OrganizationUnitTypeReference(id, code);
    }

    public static OrganizationUnitTypeReference ofCode(String code) {
        String normalizedCode = normalizeCode(code);
        return new OrganizationUnitTypeReference(SEEDED_IDS.getOrDefault(normalizedCode, toCustomId(normalizedCode)), normalizedCode);
    }

    public static OrganizationUnitTypeReference ofId(String id) {
        String normalizedId = normalizeId(id);
        return new OrganizationUnitTypeReference(normalizedId, CODES_BY_ID.getOrDefault(normalizedId, toCustomCode(normalizedId)));
    }

    public String name() {
        return code;
    }

    public boolean is(String expectedCode) {
        return code.equals(normalizeCode(expectedCode));
    }

    public boolean isStationOrganizationUnit() {
        return is("STATION");
    }

    private static String normalizeId(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("Organization unit type id must not be blank.");
        }
        if (value.trim().length() > 80) {
            throw new InvalidValueObjectException("Organization unit type id must not exceed 80 characters.");
        }
        return value.trim();
    }

    private static String normalizeCode(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("Organization unit type code must not be blank.");
        }
        String normalized = value.trim().toUpperCase(Locale.ROOT).replace('-', '_').replace(' ', '_');
        if (normalized.length() > 80) {
            throw new InvalidValueObjectException("Organization unit type code must not exceed 80 characters.");
        }
        if (!normalized.matches("[A-Z][A-Z0-9_]*")) {
            throw new InvalidValueObjectException("Organization unit type code has invalid format.");
        }
        return normalized;
    }

    private static String toCustomId(String code) {
        return PREFIX + code.toLowerCase(Locale.ROOT).replace('_', '-');
    }

    private static String toCustomCode(String id) {
        String suffix = id.startsWith(PREFIX) ? id.substring(PREFIX.length()) : id;
        return suffix.toUpperCase(Locale.ROOT).replace('-', '_');
    }
}
