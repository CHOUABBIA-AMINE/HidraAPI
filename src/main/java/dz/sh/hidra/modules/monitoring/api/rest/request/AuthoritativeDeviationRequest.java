/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoritativeDeviationRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.request
 *
 * @Description : Identifies the exact evidence and requested evaluation instant.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;

@Schema(name = "MonitoringAuthoritativeDeviationRequest")
public record AuthoritativeDeviationRequest(
        @NotBlank @Size(max = 80) String targetId,
        @NotBlank @Size(max = 80) String trustedReadingId,
        @NotNull Instant evaluatedAt) { }
