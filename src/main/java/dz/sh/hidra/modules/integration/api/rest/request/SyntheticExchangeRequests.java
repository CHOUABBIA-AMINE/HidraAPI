/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : SyntheticExchangeRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.request
 * @Description : T26 bounded synthetic exchange REST contract.
 */
package dz.sh.hidra.modules.integration.api.rest.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

public final class SyntheticExchangeRequests {
    private SyntheticExchangeRequests() {}
    public record T26SubmitExchange(@NotBlank @Size(max=80) String requestKey,
            @NotBlank @Size(max=80) String mappingProfileId,
            @NotBlank @Size(max=80) String schemaVersionId,
            @NotBlank @Size(max=160) String titleFr) {}
    public record T26ReplayExchange(@NotBlank @Size(max=80) String requestKey,
            @NotNull @Min(1) @Max(2) Integer expectedAttempt,
            @NotBlank @Size(max=500) String reason) {}
}
