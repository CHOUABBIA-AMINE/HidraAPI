/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAdministrationRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.request
 *
 * @Description : Declares validated REST request contracts for identity administration mutations.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

/**
 * Namespace for identity administration REST request records.
 */
public final class IdentityAdministrationRequests {

    private IdentityAdministrationRequests() {
    }

    public record CreateRole(
            @NotBlank String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String description,
            @NotBlank String roleType,
            @NotBlank String status
    ) { }

    public record CreatePermission(
            @NotBlank String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String description,
            @NotBlank String permissionDomain,
            @NotBlank String resourceType,
            @NotBlank String action,
            boolean sensitive,
            @NotBlank String status
    ) { }

    public record GrantRoleToUser(
            @NotBlank String userId,
            @NotBlank String roleId,
            String scopeType,
            String scopeReferenceId,
            String scopeCodeSnapshot,
            String reason,
            String approvedByWorkflowId,
            Instant validFrom,
            Instant validTo
    ) { }

    public record GrantPermissionToRole(
            @NotBlank String roleId,
            @NotBlank String permissionId,
            @NotBlank String effect,
            String conditionExpression,
            Instant validFrom,
            Instant validTo
    ) { }

    public record GrantPermissionToUser(
            @NotBlank String userId,
            @NotBlank String permissionId,
            @NotBlank String effect,
            String scopeType,
            String scopeReferenceId,
            String scopeCodeSnapshot,
            String reason,
            String approvedByWorkflowId,
            boolean emergencyAccess,
            Instant validFrom,
            Instant validTo
    ) { }
}
