/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateEmployeeRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : REST request body for creating an organization employee.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request body used to create a real operational employee.
 *
 * <p>Business role:
 * Creates an employee in the organization module. It does not create an identity user, role,
 * permission, password, token, or login account.
 *
 * <p>Architecture role:
 * This is a REST input contract. Controllers map it to an application command through
 * OrganizationRestMapper.
 *
 * <p>Validation:
 * Employee number and full name are required. Email and identity user reference are optional.
 *
 * @param employeeNumber unique employee business number
 * @param fullName employee full name
 * @param email optional professional email
 * @param identityUserReference optional neutral identity user reference
 */
@Schema(name = "CreateEmployeeRequest", description = "Request body for creating an organization employee.")
public record CreateEmployeeRequest(
        @Schema(description = "Unique employee business number.", example = "EMP-000123", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 2, max = 40)
        String employeeNumber,

        @Schema(description = "Employee full name.", example = "Abir MEDJERAB", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 3, max = 160)
        String fullName,

        @Schema(description = "Optional professional email.", example = "abir.medjerab@sh.dz")
        @Email
        @Size(max = 120)
        String email,

        @Schema(description = "Optional neutral identity user reference.", example = "usr_550e8400-e29b-41d4-a716-446655440000")
        @Size(max = 120)
        String identityUserReference) {
}
