/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTransferLifecycleRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-26
 * @Type        : Class
 * @Layer       : REST Adapter
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.request
 * @Description : Validated software-only custody lifecycle request DTOs.
 */
package dz.sh.hidra.modules.custody.api.rest.request;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
public final class CustodyTransferLifecycleRequests {
    private CustodyTransferLifecycleRequests(){}
    public record Reconcile(@NotNull UUID commandId,
        @DecimalMin("0.0") BigDecimal ticketQuantity){}
    public record Finalize(@NotNull UUID commandId,@Min(1) long expectedVersion,
        @NotBlank String workflowInstanceId,@NotBlank String workflowTaskId,
        @NotBlank String workflowActionId){}
    public record Correct(@NotNull UUID commandId,@Min(1) long expectedVersion,
        @NotNull @DecimalMin("0.0") BigDecimal revisedQuantity,
        @NotBlank String reason,@NotBlank String evidenceReference){}
    public record Disposition(@NotNull UUID commandId,@NotBlank String disposition,
        @NotBlank String reason,@NotBlank String evidenceReference){}

    public record OpenPeriod(@NotNull UUID commandId,@NotBlank @Size(max=80) String agreementId,
        @NotBlank @Size(max=80) String periodCode,@NotNull Instant periodStart,@NotNull Instant periodEnd){}
}
