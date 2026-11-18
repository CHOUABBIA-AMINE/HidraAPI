/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaLifecycleRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.request
 * @Description : Validated signed HSE lifecycle commands carrying no caller-authoritative actor.
 */
package dz.sh.hidra.modules.hse.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
public final class HseCapaLifecycleRequest {
    private HseCapaLifecycleRequest(){}
    public record Enroll(@NotBlank String requestId,@NotBlank String incidentId,
            @NotBlank String caseNumber,@NotBlank String title,String description,
            @NotBlank String caseTypeId,@NotBlank String severityId){}
    public record Propose(@NotBlank String requestId,@PositiveOrZero long expectedCaseVersion,
            @NotBlank String actionNumber,
            @NotBlank String actionTypeId,@NotBlank String title,String description,
            @NotBlank String ownerActorId){}
    public record ScopedHseCapaAction(@NotBlank String requestId,@PositiveOrZero long expectedVersion,
            @NotBlank String reason,String referenceId,String supportingId,String finding){}
}
