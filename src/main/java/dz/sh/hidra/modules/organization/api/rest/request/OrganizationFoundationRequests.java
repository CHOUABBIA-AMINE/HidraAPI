/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationFoundationRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Coordinates the bounded Organization foundation contract.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import java.time.Instant;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
public final class OrganizationFoundationRequests {
    private OrganizationFoundationRequests() {}
    @Schema(name="OrganizationFoundationActivationRequest")
    public record Activation(@NotNull Instant expectedUpdatedAt,@NotBlank @Size(max=2000) String reason) {}
}
