/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingAccessAuthorizationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Adapts Identity permission evaluation to Reporting's neutral access contract.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.reporting.ReportingAccessAuthorizationContract;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.util.Locale;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public final class ReportingAccessAuthorizationQueryService
        implements ReportingAccessAuthorizationContract {

    private final EvaluatePermissionUseCase evaluatePermissionUseCase;

    public ReportingAccessAuthorizationQueryService(EvaluatePermissionUseCase evaluatePermissionUseCase) {
        this.evaluatePermissionUseCase = Objects.requireNonNull(
                evaluatePermissionUseCase,
                "EvaluatePermissionUseCase must not be null."
        );
    }

    @Override
    public boolean permitted(AccessRequest request) {
        if (request == null
                || request.actorId() == null
                || request.actorId().isBlank()
                || request.permissionCode() == null
                || request.permissionCode().isBlank()
                || request.reportDefinitionId() == null
                || request.reportDefinitionId().isBlank()) {
            return false;
        }

        AuthorizationScope scope = toScope(request.scopeType(), request.scopeReferenceId());
        return evaluatePermissionUseCase.evaluate(
                new EvaluatePermissionQuery(
                        request.actorId().trim(),
                        request.permissionCode().trim(),
                        "REPORT_DEFINITION",
                        request.reportDefinitionId().trim(),
                        scope
                )
        ).permitted();
    }

    private static AuthorizationScope toScope(String scopeType, String scopeReferenceId) {
        if (scopeType == null || scopeType.isBlank() || "GLOBAL".equalsIgnoreCase(scopeType)) {
            return AuthorizationScope.global();
        }
        ScopeType identityScope = switch (scopeType.trim().toUpperCase(Locale.ROOT)) {
            case "ORGANIZATION_UNIT" -> ScopeType.ORGANIZATION_UNIT;
            default -> ScopeType.CUSTOM;
        };
        return new AuthorizationScope(identityScope, scopeReferenceId, null);
    }
}
