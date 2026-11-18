/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Api
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.request
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.api.rest.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class ScopedReportRequests {
    private ScopedReportRequests() {}
    public record T25SubmitReport(@NotBlank @Size(max=80) String requestKey,
            @NotBlank @Size(max=80) String definitionId,@NotBlank @Size(max=80) String templateVersionId,
            @NotBlank @Size(max=80) String metricRunId) {}
    public record T25RenderReport(@NotBlank @Size(max=80) String requestKey,
            @NotBlank @Size(max=80) String workflowInstanceId,@NotBlank @Size(max=80) String workflowActionId) {}
    public record T25SealReport(@NotBlank @Size(max=80) String requestKey) {}
}
