/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityWorkOrderRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.request
 * @Description : T18 governed Integrity and Assets maintenance handoff boundary.
 */
package dz.sh.hidra.modules.assets.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
public final class IntegrityWorkOrderRequest {
    private IntegrityWorkOrderRequest() {}
    public record Create(@NotBlank @Size(max=80) String commandId,
            @NotBlank @Size(max=80) String approvalId,
            @NotBlank @Size(max=80) String maintainableAssetId,
            @NotBlank @Size(max=80) String workOrderNumber,
            @NotBlank @Size(max=80) String workOrderTypeId,
            @Size(max=80) String priorityId,
            @NotBlank @Size(max=255) String title,
            @Size(max=1000) String description) { }
    public record Assign(@NotBlank @Size(max=80) String commandId,
            @NotNull Long expectedVersion,@NotBlank @Size(max=80) String assigneeId,
            @NotBlank @Size(max=1000) String reason) { }
    public record Start(@NotBlank @Size(max=80) String commandId,
            @NotNull Long expectedVersion,@NotBlank @Size(max=1000) String reason) { }
    public record Execute(@NotBlank @Size(max=80) String commandId,
            @NotNull Long expectedVersion,@NotBlank String result,
            @NotBlank @Size(max=1000) String summary,@NotNull Instant occurredAt) { }
    public record Verify(@NotBlank @Size(max=80) String commandId,
            @NotNull Long expectedVersion,@NotBlank @Size(max=80) String completionId,
            @NotBlank String verdict,@NotBlank @Size(max=1000) String evidenceSummary) { }
}
