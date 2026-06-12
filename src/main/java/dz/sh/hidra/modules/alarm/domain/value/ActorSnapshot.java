/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ActorSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.value
 *
 * @Description : Actor snapshot captured by alarm lifecycle records.
 *
 */
package dz.sh.hidra.modules.alarm.domain.value;

/**
 * Actor snapshot captured by alarm lifecycle records.
 *
 * @param actorId actor identifier
 * @param actorDisplayName actor display name snapshot
 * @param actorType actor type snapshot
 */
public record ActorSnapshot(
        String actorId,
        String actorDisplayName,
        String actorType
) {
}
