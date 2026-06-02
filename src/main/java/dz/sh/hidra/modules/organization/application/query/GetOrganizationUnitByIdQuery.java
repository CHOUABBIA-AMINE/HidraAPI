/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetOrganizationUnitByIdQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.query
 *
 * @Description : Application query for retrieving an organization unit by identifier.
 *
 */
package dz.sh.hidra.modules.organization.application.query;

import java.util.Objects;

import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;

/**
 * Carries input required to retrieve a single organization unit by identifier.
 *
 * <p>Business role:
 * This query requests one organization unit, including possible station-as-organization-unit
 * structures that represent people and responsibility.
 *
 * <p>Architecture role:
 * This is an application query consumed by organization read use cases. It must not depend on API,
 * persistence, identity, topology, platform, Spring, or JPA code.
 *
 * <p>Validation:
 * Organization unit id is mandatory.
 *
 * <p>Usage:
 * Use this query from inbound ports or API mappers when a client asks for one organization unit.
 *
 * @param organizationUnitId organization unit identifier
 */
public record GetOrganizationUnitByIdQuery(OrganizationUnitId organizationUnitId) implements Query {

    public GetOrganizationUnitByIdQuery {
        Objects.requireNonNull(organizationUnitId, "Organization unit id must not be null.");
    }
}
