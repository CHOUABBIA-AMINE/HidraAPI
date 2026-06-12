/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnit
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Internal organization unit such as company, division, region, area, station-as-organization-unit, or team.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;

    /**
     * Internal organization unit such as company, division, region, area, station-as-organization-unit, or team.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param unitTypeId unitTypeId
     * @param parentUnitId parentUnitId
     * @param status status
     * @param operationalScopeType operationalScopeType
     * @param operationalScopeId operationalScopeId
     * @param operationalScopeCode operationalScopeCode
     * @param operationalScopeName operationalScopeName
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
        String operationalScopeType,
        String operationalScopeId,
        String operationalScopeCode,
        String operationalScopeName,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public OrganizationUnit {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        unitTypeId = normalize(unitTypeId);
        parentUnitId = normalize(parentUnitId);
        operationalScopeType = normalize(operationalScopeType);
        operationalScopeId = normalize(operationalScopeId);
        operationalScopeCode = normalize(operationalScopeCode);
        operationalScopeName = normalize(operationalScopeName);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
