/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityEffectivePermissionSourceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Adapts identity effective grants to the platform permission-resolution extension point.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.platform.security.HidraEffectivePermissionSource;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * Publishes Identity-owned effective permission meaning to technical security plumbing.
 */
@Component
public final class IdentityEffectivePermissionSourceAdapter implements HidraEffectivePermissionSource {

    private final IdentityAdministrationQueryUseCase queryUseCase;

    public IdentityEffectivePermissionSourceAdapter(IdentityAdministrationQueryUseCase queryUseCase) {
        this.queryUseCase = Objects.requireNonNull(queryUseCase, "Identity administration query use case must not be null.");
    }

    @Override
    public Set<String> resolve(String principalName) {
        IdentityAdministrationQueryUseCase.PrincipalView principal = queryUseCase.principal(principalName, List.of());
        return Set.copyOf(new LinkedHashSet<>(principal.effectivePermissions()));
    }
}
