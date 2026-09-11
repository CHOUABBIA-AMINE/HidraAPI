/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAdministrationCommands
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.command
 *
 * @Description : Declares framework-neutral commands for identity administration mutations.
 *
 */
package dz.sh.hidra.modules.identity.application.command;

import java.time.Instant;

/**
 * Namespace for identity administration command records.
 */
public final class IdentityAdministrationCommands {

    private IdentityAdministrationCommands() {
    }

    public record CreateRole(
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String description,
            String roleType,
            String status
    ) { }

    public record CreatePermission(
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String description,
            String permissionDomain,
            String resourceType,
            String action,
            boolean sensitive,
            String status
    ) { }

    public record GrantRoleToUser(
            String userId,
            String roleId,
            String scopeType,
            String scopeReferenceId,
            String scopeCodeSnapshot,
            String reason,
            String approvedByWorkflowId,
            Instant validFrom,
            Instant validTo
    ) { }

    public record GrantPermissionToRole(
            String roleId,
            String permissionId,
            String effect,
            String conditionExpression,
            Instant validFrom,
            Instant validTo
    ) { }

    public record GrantPermissionToUser(
            String userId,
            String permissionId,
            String effect,
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
