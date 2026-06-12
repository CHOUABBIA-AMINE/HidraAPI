/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTargetReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Neutral workflow target reference.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

/**
 * Neutral workflow target reference.
 *
 * @param targetModule target module name
 * @param targetTypeId target type catalog identifier
 * @param targetId target object identifier
 * @param targetCodeSnapshot target code snapshot
 * @param targetLabelSnapshot target label snapshot
 */
public record WorkflowTargetReference(
        String targetModule,
        String targetTypeId,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot
) {
}
