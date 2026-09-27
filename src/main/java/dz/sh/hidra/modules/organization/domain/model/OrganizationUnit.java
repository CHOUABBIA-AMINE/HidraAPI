/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnit
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Internal organization unit independent from operational-scope responsibility.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.domain.value.OrganizationCode;

import java.time.Instant;

/**
 * Internal organization unit such as company, division, region, area,
 * station-as-organization-unit, or team.
 *
 * <p>Operational responsibility is deliberately not part of the unit identity.
 * It is modeled separately through responsibility assignments.</p>
 *
 * @param id id
 * @param code code
 * @param nameAr nameAr
 * @param nameFr nameFr
 * @param nameEn nameEn
 * @param unitTypeId unitTypeId
 * @param parentUnitId parentUnitId
 * @param status status
 * @param validFrom validFrom
 * @param validTo validTo
 * @param createdAt createdAt
 * @param updatedAt updatedAt
 */
public record OrganizationUnit(
        String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String unitTypeId,
        String parentUnitId,
        OrganizationUnitStatus status,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
) {

    public OrganizationUnit {
        id = normalize(id);
        code = OrganizationCode.of(code).value();
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        unitTypeId = normalize(unitTypeId);
        parentUnitId = normalize(parentUnitId);
    }

    /**
     * Transitional constructor kept only so the still-legacy persistence mapper can
     * read rows containing the four historical scope columns while that schema is
     * being retired. The supplied scope values are deliberately discarded.
     */
    @Deprecated(forRemoval = true)
    public OrganizationUnit(
            String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String unitTypeId,
            String parentUnitId,
            OrganizationUnitStatus status,
            String operationalScopeType,
            String operationalScopeId,
            String operationalScopeCode,
            String operationalScopeName,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
    ) {
        this(
                id,
                code,
                nameAr,
                nameFr,
                nameEn,
                unitTypeId,
                parentUnitId,
                status,
                validFrom,
                validTo,
                createdAt,
                updatedAt
        );
    }

    /**
     * Transitional persistence compatibility only. Operational scope is no longer
     * canonical state of OrganizationUnit.
     */
    @Deprecated(forRemoval = true)
    public String operationalScopeType() {
        return null;
    }

    /**
     * Transitional persistence compatibility only.
     */
    @Deprecated(forRemoval = true)
    public String operationalScopeId() {
        return null;
    }

    /**
     * Transitional persistence compatibility only.
     */
    @Deprecated(forRemoval = true)
    public String operationalScopeCode() {
        return null;
    }

    /**
     * Transitional persistence compatibility only.
     */
    @Deprecated(forRemoval = true)
    public String operationalScopeName() {
        return null;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
