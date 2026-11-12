/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCommissioningRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : Typed scoped Telemetry commissioning commands.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
public final class TelemetryCommissioningRequests {
    private TelemetryCommissioningRequests() { }
    public record Enroll(@NotNull UUID requestId,@NotBlank String companyPartyId,
           @NotBlank String assetType,@NotBlank String assetId,
           String assetCode,@NotBlank String sourceCode,@NotBlank String sourceNameFr,
           @NotBlank String sourceTypeId,@NotBlank String protocolId,
           @NotBlank String deviceCode,@NotBlank String deviceNameFr,@NotBlank String deviceTypeId,
           @NotBlank String pointCode,@NotBlank String pointNameFr,
           @NotBlank String pointTypeId,@NotBlank String signalTypeId,
           String unitId,@NotBlank String bindingRoleId,@NotBlank String externalTagName,
           @NotNull Instant validFrom,Instant validUntil) { }
    public record Rebind(@NotNull UUID requestId,@NotBlank String companyPartyId,
           @NotBlank String assetType,@NotBlank String assetId,String assetCode,
           @NotBlank String bindingRoleId,@NotBlank String externalTagName,
           @NotNull Instant validFrom,Instant validUntil) { }
}
