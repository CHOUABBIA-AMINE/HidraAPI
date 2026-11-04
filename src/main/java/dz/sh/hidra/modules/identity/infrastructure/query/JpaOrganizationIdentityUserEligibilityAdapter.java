/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOrganizationIdentityUserEligibilityAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.query
 *
 * @Description : Resolve Identity-owned user lifecycle without exposing a private query use case to Organization.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.query;

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationIdentityUserEligibility;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public final class JpaOrganizationIdentityUserEligibilityAdapter
        implements OrganizationIdentityUserEligibility {
    private final IdentityAdministrationQueryUseCase users;

    public JpaOrganizationIdentityUserEligibilityAdapter(IdentityAdministrationQueryUseCase users) {
        this.users = Objects.requireNonNull(users, "Identity user query must not be null.");
    }

    @Override
    public Eligibility resolve(String userId) {
        if (userId == null || userId.isBlank() || !userId.equals(userId.trim())) {
            throw new IllegalArgumentException("Exact Identity user ID is required.");
        }
        try {
            var user = users.user(userId);
            if (user == null || !userId.equals(user.id())) {
                return Eligibility.NOT_FOUND;
            }
            return "ACTIVE".equals(user.status()) ? Eligibility.ACTIVE : Eligibility.INACTIVE;
        } catch (NoSuchElementException missing) {
            return Eligibility.NOT_FOUND;
        }
    }
}
