/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeQueryApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Resolves registered operational scopes with current owner-controlled display attributes.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.port.in.OperationalScopeQueryUseCase;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public final class OperationalScopeQueryApplicationService implements OperationalScopeQueryUseCase {

    private final OperationalScopeRegistryRepositoryPort scopes;
    private final OperationalScopeTargetResolverPort targets;

    public OperationalScopeQueryApplicationService(
            OperationalScopeRegistryRepositoryPort scopes,
            OperationalScopeTargetResolverPort targets
    ) {
        this.scopes = Objects.requireNonNull(scopes, "Operational scope repository must not be null.");
        this.targets = Objects.requireNonNull(targets, "Operational scope target resolver must not be null.");
    }

    @Override
    public ScopeView scope(Long scopeId) {
        if (scopeId == null || scopeId <= 0) {
            throw new IllegalArgumentException("Operational scope registry ID must be positive.");
        }

        OperationalScope scope = scopes.findById(scopeId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Unknown operational scope registry ID: " + scopeId
                ));

        if (scope.type() == OperationalScopeType.GLOBAL) {
            return new ScopeView(scope.id(), scope.type(), null, null, null, true);
        }

        if (!targets.supports(scope.type())) {
            throw new IllegalStateException(
                    "No authoritative owner resolver for scope type: " + scope.type()
            );
        }

        OperationalScopeTargetResolverPort.ResolvedTarget target =
                targets.resolve(scope.type(), scope.targetId())
                        .orElseThrow(() -> new NoSuchElementException(
                                "Operational scope owner target no longer exists: " + scope.targetId()
                        ));

        if (target.type() != scope.type() || !Objects.equals(target.targetId(), scope.targetId())) {
            throw new IllegalStateException("Operational scope owner resolver returned mismatched identity.");
        }

        return new ScopeView(
                scope.id(),
                scope.type(),
                scope.targetId(),
                target.code(),
                target.name(),
                target.assignable()
        );
    }
}
