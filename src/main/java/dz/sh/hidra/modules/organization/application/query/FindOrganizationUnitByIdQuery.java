/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindOrganizationUnitByIdQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.query
 *
 * @Description : Query to find an organization unit by ID.
 *
 */
package dz.sh.hidra.modules.organization.application.query;

/**
 * Query to find an organization unit by ID.
 *
 * @param organizationUnitId organization unit identifier
 */
public record FindOrganizationUnitByIdQuery(String organizationUnitId) {
}
