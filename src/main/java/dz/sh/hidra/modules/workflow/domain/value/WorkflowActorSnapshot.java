/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActorSnapshot
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Actor snapshot for workflow evidence.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

/**
 * Actor snapshot for workflow evidence.
 *
 * @param actorId actor identifier
 * @param usernameSnapshot username snapshot
 * @param displayNameSnapshot display name snapshot
 * @param roleCodeSnapshot role code snapshot
 */
public record WorkflowActorSnapshot(
        String actorId,
        String usernameSnapshot,
        String displayNameSnapshot,
        String roleCodeSnapshot
) {
}
