/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningNominationCommandRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 *
 * @Type        : Class
 * @Layer       : REST Adapter
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.request
 *
 * @Description : Defines explicit request payloads for nomination authoring and disposition.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public final class PlanningNominationCommandRequest {
    private PlanningNominationCommandRequest() { }

    @Schema(name = "PlanningNominationCreateRequest")
    public record Create(
            @NotNull UUID commandId,
            @NotNull Instant expectedRevisionUpdatedAt,
            @NotBlank @Size(max = 80) String code,
            @NotBlank @Size(max = 80) String nominationTypeId,
            @NotBlank @Size(max = 80) String productTypeId,
            @NotNull @Positive @Digits(integer = 12, fraction = 6) BigDecimal quantity,
            @NotBlank @Size(max = 80) String quantityUnitId,
            @Positive @Digits(integer = 12, fraction = 6) BigDecimal rate,
            @Size(min = 1, max = 80) String rateUnitId,
            @NotBlank @Size(max = 80) String destinationFacilityId,
            @NotNull Instant periodStart,
            @NotNull Instant periodEnd) { }

    public enum Decision { CONFIRM, REJECT }

    @Schema(name = "PlanningNominationDispositionRequest")
    public record Disposition(
            @NotNull UUID commandId,
            @NotNull @PositiveOrZero Long expectedVersion,
            @NotNull Decision decision,
            @NotBlank @Size(max = 500) String reason) { }
}
