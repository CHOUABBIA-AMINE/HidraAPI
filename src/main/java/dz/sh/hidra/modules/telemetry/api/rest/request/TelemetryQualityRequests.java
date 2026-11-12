/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQualityRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : Explicit company-scoped trusted-observation command DTOs.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

import jakarta.validation.constraints.NotBlank;
public final class TelemetryQualityRequests {
    private TelemetryQualityRequests() { }
    public record Command(@NotBlank String companyPartyId) { }
}
