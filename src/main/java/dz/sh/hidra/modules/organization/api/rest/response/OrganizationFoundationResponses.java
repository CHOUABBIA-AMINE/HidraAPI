/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationFoundationResponses
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
package dz.sh.hidra.modules.organization.api.rest.response;

import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;
public final class OrganizationFoundationResponses {
    private OrganizationFoundationResponses() {}
    @Schema(name="OrganizationFoundationEmployeeState")
    public record EmployeeState(String id,String status,Instant hiredAt,Instant updatedAt) {}
    @Schema(name="OrganizationFoundationChoice")
    public record Choice(String id,String code,String nameAr,String nameFr,String nameEn) {}
    @Schema(name="OrganizationFoundationActivationEvent")
    public record ActivationEvent(String id,String employeeId,String actorId,String reason,Instant previousUpdatedAt,Instant updatedAt,Instant occurredAt) {}
}
