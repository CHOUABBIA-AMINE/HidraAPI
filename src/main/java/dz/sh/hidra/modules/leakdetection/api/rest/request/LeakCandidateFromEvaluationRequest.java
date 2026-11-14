/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakCandidateFromEvaluationRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Record
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.request
 * @Description : T13 governed source-linked suspected leak candidate delivery.
 */
package dz.sh.hidra.modules.leakdetection.api.rest.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;

public record LeakCandidateFromEvaluationRequest(
        @NotBlank String evaluationId,
        @NotBlank String profileId,
        @NotNull @DecimalMin("0.0") @DecimalMax("1.0") @Digits(integer = 1, fraction = 6)
        BigDecimal declaredConfidence,
        @NotBlank String reason) { }
