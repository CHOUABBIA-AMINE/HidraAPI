/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowPageResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response DTO for paginated workflow responses.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * REST response DTO for paginated workflow responses.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the workflow API. It must not contain business behavior,
 * persistence mapping, application orchestration, telemetry implementation imports, topology
 * implementation imports, identity implementation imports, organization implementation imports,
 * planning, monitoring, incidents, audit implementation, integration, analytics, reporting, or
 * notification behavior.
 */
public record WorkflowPageResponse<T>(
        @Schema(description = "Page items")
        List<T> items,
        @Schema(description = "Page number", example = "0")
        Integer page,
        @Schema(description = "Page size", example = "20")
        Integer size,
        @Schema(description = "Total element count", example = "125")
        Long totalElements,
        @Schema(description = "Total page count", example = "7")
        Integer totalPages) {
    public WorkflowPageResponse {
        items = items == null ? List.of() : List.copyOf(items);
    }

    public static <T> WorkflowPageResponse<T> empty(Integer page, Integer size) {
        return new WorkflowPageResponse<>(List.of(), page, size, 0L, 0);
    }

}
