/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExecuteWorkflowTransitionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.request
 *
 * @Description : Client-supplied evidence for an authoritative workflow transition.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.request;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record ExecuteWorkflowTransitionRequest(
        @NotNull Instant expectedTaskUpdatedAt,
        String reasonId,
        String decisionNote,
        String commentText,
        String correlationId
) { }
