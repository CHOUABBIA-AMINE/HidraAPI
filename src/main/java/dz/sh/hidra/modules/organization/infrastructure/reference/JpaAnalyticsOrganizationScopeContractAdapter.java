/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsOrganizationScopeContractAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.reference
 *
 * @Description : Organization-owned persistence adapter for Analytics scope existence lookup.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.reference;

import dz.sh.hidra.modules.organization.application.contract.analytics.AnalyticsOrganizationScopeContract;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationUnitJpaRepository;
import java.util.Locale;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 * Organization-owned Analytics scope resolver.
 */
@Component
public class JpaAnalyticsOrganizationScopeContractAdapter implements AnalyticsOrganizationScopeContract {

    private final OrganizationUnitJpaRepository organizationUnitRepository;

    public JpaAnalyticsOrganizationScopeContractAdapter(
            OrganizationUnitJpaRepository organizationUnitRepository
    ) {
        this.organizationUnitRepository = Objects.requireNonNull(
                organizationUnitRepository,
                "OrganizationUnitJpaRepository must not be null."
        );
    }

    @Override
    public Resolution resolve(String scopeType, String scopeId) {
        if (scopeType == null || scopeType.isBlank()) {
            return Resolution.unsupported();
        }
        String type = scopeType.trim().toUpperCase(Locale.ROOT);
        if (!"ORGANIZATION_UNIT".equals(type)) {
            return Resolution.unsupported();
        }
        if (scopeId == null || scopeId.isBlank()) {
            return Resolution.supported(false);
        }
        return Resolution.supported(organizationUnitRepository.existsById(scopeId.trim()));
    }
}
