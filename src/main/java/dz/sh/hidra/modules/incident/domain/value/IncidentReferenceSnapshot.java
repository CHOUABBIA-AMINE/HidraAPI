/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentReferenceSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.value
 *
 * @Description : Neutral incident reference snapshot.
 *
 */
package dz.sh.hidra.modules.incident.domain.value;

/**
 * Neutral incident reference snapshot.
 *
 * @param incidentId incident identifier
 * @param incidentNumber incident number snapshot
 * @param titleSnapshot incident title snapshot
 * @param statusSnapshot incident status snapshot
 */
public record IncidentReferenceSnapshot(
        String incidentId,
        String incidentNumber,
        String titleSnapshot,
        String statusSnapshot
) {
}
