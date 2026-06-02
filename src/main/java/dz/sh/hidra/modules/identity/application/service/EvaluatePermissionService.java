/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EvaluatePermissionService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Application service evaluating identity permissions.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.application.port.out.UserRepository;
import dz.sh.hidra.modules.identity.application.query.CheckPermissionQuery;
import dz.sh.hidra.modules.identity.domain.exception.IdentityDomainException;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.service.PermissionEvaluationDomainService;

import java.util.List;

/**
 * Application service evaluating identity permissions.
 *
 * <p>Business role: determines whether a user has a requested business permission
 * through assigned roles.</p>
 *
 * <p>Architecture role: implements the evaluate-permission inbound port by loading the
 * user and roles and delegating evaluation rules to the domain service. It does not own
 * Spring Security authorization plumbing.</p>
 *
 * <p>Validation responsibility: rejects null dependencies and queries and fails when the
 * user does not exist.</p>
 *
 * <p>Usage: called by future API adapters through {@link EvaluatePermissionUseCase}.</p>
 */
public final class EvaluatePermissionService implements EvaluatePermissionUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionEvaluationDomainService permissionEvaluationDomainService;

    public EvaluatePermissionService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PermissionEvaluationDomainService permissionEvaluationDomainService
    ) {
        this.userRepository = requireNonNull(userRepository, "UserRepository");
        this.roleRepository = requireNonNull(roleRepository, "RoleRepository");
        this.permissionEvaluationDomainService = requireNonNull(
                permissionEvaluationDomainService,
                "PermissionEvaluationDomainService"
        );
    }

    @Override
    public PermissionDecisionDto evaluatePermission(CheckPermissionQuery query) {
        CheckPermissionQuery requiredQuery = requireNonNull(query, "CheckPermissionQuery");

        User user = userRepository.findById(requiredQuery.userId())
                .orElseThrow(() -> new IdentityDomainException("User not found: " + requiredQuery.userId().value() + "."));

        List<Role> assignedRoles = roleRepository.findAll().stream()
                .filter(role -> user.hasRole(role.id()))
                .toList();

        boolean granted = permissionEvaluationDomainService.hasPermission(
                user,
                requiredQuery.permissionCode(),
                assignedRoles
        );

        if (granted) {
            return PermissionDecisionDto.granted(user.id(), requiredQuery.permissionCode());
        }

        return PermissionDecisionDto.denied(user.id(), requiredQuery.permissionCode(), "Permission denied.");
    }

private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
        throw new InvalidValueObjectException(fieldName + " must not be null.");
    }
    return value;
}
}
