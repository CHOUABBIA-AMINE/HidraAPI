/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAdministrationQueryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.in
 *
 * @Description : Defines dedicated identity administration and current-principal read contracts.
 *
 */
package dz.sh.hidra.modules.identity.application.port.in;

import java.util.List;

/**
 * Read-only identity administration contract.
 */
public interface IdentityAdministrationQueryUseCase {

    Page<UserView> users(String query, int page, int size);

    UserView user(String id);

    Page<RoleView> roles(String query, int page, int size);

    Page<PermissionView> permissions(String query, int page, int size);

    PrincipalView principal(String authenticationName, List<String> authenticationAuthorities);

    record Page<T>(List<T> content, int page, int size, long totalElements, int totalPages, boolean hasNext) { }

    record UserView(
            String id,
            String username,
            String emailAddress,
            String displayName,
            String userType,
            String status,
            String employeeReferenceId
    ) { }

    record RoleView(
            String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String description,
            String roleType,
            String status
    ) { }

    record PermissionView(
            String id,
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

    record PrincipalView(
            String authenticationName,
            String authenticationType,
            String userId,
            String username,
            String displayName,
            String employeeReferenceId,
            List<String> authenticationAuthorities,
            List<String> effectivePermissions
    ) { }
}
