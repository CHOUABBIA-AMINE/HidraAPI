/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationPolicyRuleRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Repository port for AuthorizationPolicyRule.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.AuthorizationPolicyRule;

import java.util.Optional;

/**
 * Repository port for AuthorizationPolicyRule.
 */
public interface AuthorizationPolicyRuleRepositoryPort {

    AuthorizationPolicyRule save(AuthorizationPolicyRule model);

    Optional<AuthorizationPolicyRule> findById(String id);
}
