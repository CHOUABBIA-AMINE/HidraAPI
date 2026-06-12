/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentReferenceSnapshot
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.value
 *
 * @Description : Neutral incident reference snapshot for HSE.
 *
 */
package dz.sh.hidra.modules.hse.domain.value;

/**
 * Neutral incident reference snapshot for HSE.
 *
 * @param incidentReferenceId incident identifier
 * @param incidentCodeSnapshot incident code snapshot
 * @param incidentTitleSnapshot incident title snapshot
 */
public record IncidentReferenceSnapshot(
        String incidentReferenceId,
        String incidentCodeSnapshot,
        String incidentTitleSnapshot
) {
}
