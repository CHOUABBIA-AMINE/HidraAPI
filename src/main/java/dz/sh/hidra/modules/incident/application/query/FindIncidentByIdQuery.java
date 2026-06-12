/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindIncidentByIdQuery
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.query
 *
 * @Description : Query to find incident by ID.
 *
 */
package dz.sh.hidra.modules.incident.application.query;

public record FindIncidentByIdQuery(String incidentId) {
}
