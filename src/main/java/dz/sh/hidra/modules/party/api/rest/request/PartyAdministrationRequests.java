/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyAdministrationRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.api.rest.request
 *
 * @Description : Defines explicit Party lifecycle and metadata request DTOs.
 *
 */
package dz.sh.hidra.modules.party.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
public final class PartyAdministrationRequests {
    private PartyAdministrationRequests() { }
    @io.swagger.v3.oas.annotations.media.Schema(name="PartyAdministrationTransition")
    public record Transition(@NotNull Instant expectedUpdatedAt,@NotBlank String reason) { }
    @io.swagger.v3.oas.annotations.media.Schema(name="PartyAdministrationMetadata")
    public record Metadata(@NotNull Instant expectedUpdatedAt,@NotBlank String legalName,String tradeName,String shortName,@NotBlank String reason) { }
}
