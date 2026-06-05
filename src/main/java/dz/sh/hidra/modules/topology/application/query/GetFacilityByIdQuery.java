/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetFacilityByIdQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.query
 *
 * @Description : Application query for retrieving a physical facility.
 *
 */
package dz.sh.hidra.modules.topology.application.query;

import java.util.Objects;

import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;

/**
 * Carries input required to retrieve a physical facility.
 *
 * <p>Business role:
 * This query retrieves one topology resource by identifier.
 *
 * <p>Architecture role:
 * This is an application query. It must not depend on API, persistence, Spring, JPA, identity,
 * organization implementation, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * Identifier is mandatory.
 *
 * <p>Usage:
 * Use this query from get use cases.
 *
 * @param id facility id
 */
public record GetFacilityByIdQuery(FacilityId id) implements Query {

    public GetFacilityByIdQuery {
        Objects.requireNonNull(id, "Facility id must not be null.");
    }
}
