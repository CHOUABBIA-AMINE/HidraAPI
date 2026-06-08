/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowEscalationStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Workflow escalation lifecycle status enum.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Workflow escalation lifecycle status enum.
 *
 * <p>Architecture rule:
 * This is a technical workflow lifecycle enum. Business taxonomy values such as workflow type,
 * reason, priority, target type, escalation reason, and delegation reason remain catalog-backed.
 */
public enum WorkflowEscalationStatus implements ValueObject {
    PENDING,
    TRIGGERED,
    RESOLVED,
    CANCELLED;
}
