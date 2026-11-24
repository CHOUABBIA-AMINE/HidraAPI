/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegionalOperationalScopeResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-24
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.response
 *
 * @Description : Minimal current employee operational scope coordinates qualified for one company.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.response;

/** No employee identifiers, assignment history, foreign scope metadata or independent grant. */
public record RegionalOperationalScopeResponse(long id, String type, String targetId) { }
