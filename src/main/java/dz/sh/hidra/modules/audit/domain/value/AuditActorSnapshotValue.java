/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditActorSnapshotValue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.value
 *
 * @Description : Actor evidence snapshot.
 *
 */
package dz.sh.hidra.modules.audit.domain.value;

/**
 * Actor evidence snapshot.
 *
 * @param actorId actor identifier
 * @param actorType actor type
 * @param displayNameSnapshot display-name snapshot
 * @param usernameSnapshot username snapshot
 */
public record AuditActorSnapshotValue(
        String actorId,
        String actorType,
        String displayNameSnapshot,
        String usernameSnapshot
) {
}
