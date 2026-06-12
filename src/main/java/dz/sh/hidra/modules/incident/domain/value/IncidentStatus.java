/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.value
 *
 * @Description : Defines IncidentStatus values.
 *
 */
package dz.sh.hidra.modules.incident.domain.value;

/**
 * Defines IncidentStatus values.
 */
public enum IncidentStatus {
    DRAFT, OPEN, TRIAGED, ASSIGNED, IN_PROGRESS, CONTAINED, RESOLVED, CLOSED, REOPENED, ESCALATED, MERGED, CANCELLED
}
