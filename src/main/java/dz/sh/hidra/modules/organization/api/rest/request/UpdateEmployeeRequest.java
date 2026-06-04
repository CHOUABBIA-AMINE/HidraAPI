/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UpdateEmployeeRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : REST request body for updating an organization employee.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request body used to update employee descriptive information.
 *
 * <p>Business role:
 * Updates organization employee information only. Identity credentials, roles, permissions, and
 * topology assets are outside this request.
 *
 * <p>Architecture role:
 * This is a REST input contract mapped to an application command.
 *
 * <p>Validation:
 * Full name is required. Email is optional.
 *
 * @param fullName new employee full name
 * @param email optional professional email
 */
@Schema(name = "UpdateEmployeeRequest", description = "Request body for updating employee descriptive information.")
public record UpdateEmployeeRequest(
        @Schema(description = "Employee full name.", example = "Abir MEDJERAB", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 3, max = 160)
        String fullName,

        @Schema(description = "Optional professional email.", example = "abir.medjerab@sh.dz")
        @Email
        @Size(max = 120)
        String email) {
}
