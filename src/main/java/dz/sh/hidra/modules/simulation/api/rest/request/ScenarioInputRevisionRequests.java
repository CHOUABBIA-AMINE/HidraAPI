/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScenarioInputRevisionRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.request
 * @Description : Typed bounded write DTOs for engine-neutral scenario inputs.
 */
package dz.sh.hidra.modules.simulation.api.rest.request;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public final class ScenarioInputRevisionRequests {
    private ScenarioInputRevisionRequests() {}
    public record Boundary(@NotBlank String slot,@NotNull BigDecimal value,@NotBlank String unit) {}
    public record Create(@NotNull UUID baselineReceiptId,@NotBlank String expectedManifestDigest,
                         @NotBlank String name,@NotBlank String mode,
                         @Valid @NotEmpty List<@Valid Boundary> boundaries,
                         Integer durationSeconds,Integer stepSeconds,@NotBlank String requestKey) {}
    public record Append(@Positive long expectedRevision,@NotNull UUID baselineReceiptId,
                         @NotBlank String expectedManifestDigest,@NotBlank String name,@NotBlank String mode,
                         @Valid @NotEmpty List<@Valid Boundary> boundaries,
                         Integer durationSeconds,Integer stepSeconds,@NotBlank String requestKey) {}
}
