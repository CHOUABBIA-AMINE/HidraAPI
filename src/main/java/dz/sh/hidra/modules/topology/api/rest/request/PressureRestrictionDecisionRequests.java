/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PressureRestrictionDecisionRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : Defines validated API payloads for pressure restriction submission and publication.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class PressureRestrictionDecisionRequests {
    private PressureRestrictionDecisionRequests() { }

    @Schema(name = "PressureRestrictionSubmission")
    public record Submission(@NotNull UUID requestId, @NotBlank String companyPartyId, String sectionId,
                             @NotBlank String conditionCode, @NotNull UUID baselineReceiptId,
                             @NotNull @PositiveOrZero Long expectedVersion, @NotNull @Valid Change change) { }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "kind")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = Initial.class, name = "INITIAL"),
        @JsonSubTypes.Type(value = Replacement.class, name = "REPLACEMENT"),
        @JsonSubTypes.Type(value = Withdrawal.class, name = "WITHDRAWAL")
    })
    @Schema(name = "PressureRestrictionChange")
    public sealed interface Change permits Initial, Replacement, Withdrawal { }

    @Schema(name = "PressureRestrictionInitialChange")
    public record Initial(@NotNull @Valid Pressure pressure) implements Change { }

    @Schema(name = "PressureRestrictionReplacementChange")
    public record Replacement(@NotNull UUID predecessorId, @NotNull @Valid Pressure pressure) implements Change { }

    @Schema(name = "PressureRestrictionWithdrawalChange")
    public record Withdrawal(@NotNull UUID predecessorId, @NotNull Instant effectiveAt) implements Change { }

    public enum Basis { GAUGE, ABSOLUTE }

    @Schema(name = "PressureRestrictionPressureInput")
    public record Pressure(@NotNull BigDecimal upperPressure, @NotBlank String unit, @NotNull Basis basis,
                           @NotNull Instant effectiveFrom, Instant effectiveUntil) { }

    @Schema(name = "PressureRestrictionPublicationCommand")
    public record Publication(@NotBlank String companyPartyId, String sectionId, @NotNull @PositiveOrZero Long expectedVersion,
                              @NotNull @Size(min = 1, max = 2) List<@NotNull @Valid WorkflowReference> decisions) { }

    @Schema(name = "PressureRestrictionWorkflowReference")
    public record WorkflowReference(@NotNull UUID candidateId, @NotBlank @Size(max = 80) String instanceId,
                                    @NotBlank @Size(max = 80) String taskId, @NotBlank @Size(max = 80) String actionId) { }
}
