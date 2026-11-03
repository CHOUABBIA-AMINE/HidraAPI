/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityLifecycleRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.request
 *
 * @Description : Defines validated non-secret Identity lifecycle mutation inputs.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.time.Instant;

public final class IdentityLifecycleRequests {
    private IdentityLifecycleRequests() { }
    public record Transition(@NotNull Instant expectedUpdatedAt, @NotBlank String reason) { }
    public record Credential(@NotBlank String password, @Min(0) long expectedSecurityVersion, @NotBlank String reason) { }
    public record Revoke(Instant expectedCreatedAt, @NotBlank String reason) { }
}
