/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationPolicyRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Repository port for AuthorizationPolicy.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.AuthorizationPolicy;

import java.util.Optional;

/**
 * Repository port for AuthorizationPolicy.
 */
public interface AuthorizationPolicyRepositoryPort {

    AuthorizationPolicy save(AuthorizationPolicy model);

    Optional<AuthorizationPolicy> findById(String id);
}
