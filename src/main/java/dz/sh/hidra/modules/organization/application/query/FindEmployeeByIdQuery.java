/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindEmployeeByIdQuery
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.query
 *
 * @Description : Query to find an employee by ID.
 *
 */
package dz.sh.hidra.modules.organization.application.query;

/**
 * Query to find an employee by ID.
 *
 * @param employeeId employee identifier
 */
public record FindEmployeeByIdQuery(String employeeId) {
}
