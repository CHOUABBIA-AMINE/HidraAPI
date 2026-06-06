/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Deprecated compatibility wrapper for organization unit type catalog references.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Deprecated compatibility wrapper for organization unit type catalog references.
 *
 * <p>Business role:
 * Keeps older call sites and tests source-compatible while organization unit type is migrated from a
 * Java enum to catalog-backed multilingual reference data.
 *
 * <p>Architecture role:
 * This is no longer an enum. New code must use {@link OrganizationUnitTypeReference} directly.
 *
 * @deprecated use {@link OrganizationUnitTypeReference}
 */
@Deprecated(forRemoval = false)
public final class OrganizationUnitType implements ValueObject {

    public static final OrganizationUnitType COMPANY = new OrganizationUnitType(OrganizationUnitTypeReference.COMPANY);
    public static final OrganizationUnitType DIVISION = new OrganizationUnitType(OrganizationUnitTypeReference.DIVISION);
    public static final OrganizationUnitType DIRECTION = new OrganizationUnitType(OrganizationUnitTypeReference.DIRECTION);
    public static final OrganizationUnitType DEPARTMENT = new OrganizationUnitType(OrganizationUnitTypeReference.DEPARTMENT);
    public static final OrganizationUnitType REGION = new OrganizationUnitType(OrganizationUnitTypeReference.REGION);
    public static final OrganizationUnitType AREA = new OrganizationUnitType(OrganizationUnitTypeReference.AREA);
    public static final OrganizationUnitType DISTRICT = new OrganizationUnitType(OrganizationUnitTypeReference.DISTRICT);
    public static final OrganizationUnitType STATION = new OrganizationUnitType(OrganizationUnitTypeReference.STATION);
    public static final OrganizationUnitType TEAM = new OrganizationUnitType(OrganizationUnitTypeReference.TEAM);
    public static final OrganizationUnitType PROJECT_TEAM = new OrganizationUnitType(OrganizationUnitTypeReference.PROJECT_TEAM);
    public static final OrganizationUnitType OTHER = new OrganizationUnitType(OrganizationUnitTypeReference.OTHER);

    private final OrganizationUnitTypeReference reference;

    private OrganizationUnitType(OrganizationUnitTypeReference reference) {
        this.reference = Objects.requireNonNull(reference, "Organization unit type reference must not be null.");
    }

    public static OrganizationUnitType valueOf(String code) {
        return new OrganizationUnitType(OrganizationUnitTypeReference.ofCode(code));
    }

    public static OrganizationUnitType[] values() {
        return new OrganizationUnitType[] {
                COMPANY,
                DIVISION,
                DIRECTION,
                DEPARTMENT,
                REGION,
                AREA,
                DISTRICT,
                STATION,
                TEAM,
                PROJECT_TEAM,
                OTHER
        };
    }

    public OrganizationUnitTypeReference toReference() {
        return reference;
    }

    public String name() {
        return reference.name();
    }

    public boolean isStationOrganizationUnit() {
        return reference.isStationOrganizationUnit();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OrganizationUnitType that)) {
            return false;
        }
        return reference.equals(that.reference);
    }

    @Override
    public int hashCode() {
        return reference.hashCode();
    }

    @Override
    public String toString() {
        return name();
    }
}
