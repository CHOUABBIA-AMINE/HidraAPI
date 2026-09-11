package dz.sh.hidra.modules.identity.application.port.in;

import java.time.Instant;
import java.util.List;

public interface IdentityAdministrationQueryUseCase {
    Page<UserView> users(String query, int page, int size);
    UserView user(String id);
    Page<RoleView> roles(String query, int page, int size);
    Page<PermissionView> permissions(String query, int page, int size);
    PrincipalView principal(String authenticationName, List<String> authenticationAuthorities);
    RoleView createRole(CreateRoleCommand command);
    PermissionView createPermission(CreatePermissionCommand command);
    String grantRoleToUser(UserRoleGrantCommand command);
    String grantPermissionToRole(RolePermissionGrantCommand command);
    String grantPermissionToUser(UserPermissionGrantCommand command);

    record Page<T>(List<T> content, int page, int size, long totalElements, int totalPages, boolean hasNext) { }
    record UserView(String id, String username, String emailAddress, String displayName, String userType, String status, String employeeReferenceId) { }
    record RoleView(String id, String code, String nameAr, String nameFr, String nameEn, String description, String roleType, String status) { }
    record PermissionView(String id, String code, String nameAr, String nameFr, String nameEn, String description, String permissionDomain, String resourceType, String action, boolean sensitive, String status) { }
    record PrincipalView(String authenticationName, String authenticationType, String userId, String username, String displayName, String employeeReferenceId, List<String> authenticationAuthorities, List<String> effectivePermissions) { }
    record CreateRoleCommand(String code, String nameAr, String nameFr, String nameEn, String description, String roleType, String status) { }
    record CreatePermissionCommand(String code, String nameAr, String nameFr, String nameEn, String description, String permissionDomain, String resourceType, String action, boolean sensitive, String status) { }
    record UserRoleGrantCommand(String userId, String roleId, String scopeType, String scopeReferenceId, String scopeCodeSnapshot, String reason, String approvedByWorkflowId, Instant validFrom, Instant validTo) { }
    record RolePermissionGrantCommand(String roleId, String permissionId, String effect, String conditionExpression, Instant validFrom, Instant validTo) { }
    record UserPermissionGrantCommand(String userId, String permissionId, String effect, String scopeType, String scopeReferenceId, String scopeCodeSnapshot, String reason, String approvedByWorkflowId, boolean emergencyAccess, Instant validFrom, Instant validTo) { }
}
