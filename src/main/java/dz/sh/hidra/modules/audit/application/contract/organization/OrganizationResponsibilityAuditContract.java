/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationResponsibilityAuditContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.contract.organization
 *
 * @Description : Narrow Audit evidence contract exported to Organization responsibility orchestration.
 *
 */
package dz.sh.hidra.modules.audit.application.contract.organization;

import java.time.Instant;

/**
 * Appends catalog-backed audit evidence for Organization responsibility operations.
 */
public interface OrganizationResponsibilityAuditContract {

    String append(Event event);

    enum Operation {
        CREATE,
        UPDATE,
        READ
    }

    record Event(
            String eventTypeCode,
            String eventCategoryCode,
            String sourceComponent,
            String actionCode,
            String actorId,
            String actorUsername,
            String actorDisplayName,
            String targetType,
            String targetId,
            Operation operation,
            String decisionCode,
            String reasonText,
            String workflowInstanceId,
            String workflowTaskId,
            String workflowActionId,
            String requestId,
            String correlationId,
            Instant occurredAt
    ) { }
}
