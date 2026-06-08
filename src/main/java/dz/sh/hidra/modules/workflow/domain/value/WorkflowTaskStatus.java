/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Workflow task lifecycle status enum.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Workflow task lifecycle status enum.
 *
 * <p>Architecture rule:
 * This is a technical workflow lifecycle enum. Business taxonomy values such as workflow type,
 * reason, priority, target type, escalation reason, and delegation reason remain catalog-backed.
 */
public enum WorkflowTaskStatus implements ValueObject {
    OPEN,
    CLAIMED,
    IN_REVIEW,
    APPROVED,
    REJECTED,
    RETURNED,
    DELEGATED,
    ESCALATED,
    CANCELLED,
    EXPIRED;

    public boolean isClosed() {
        return this == APPROVED
                || this == REJECTED
                || this == CANCELLED
                || this == EXPIRED;
    }

}
