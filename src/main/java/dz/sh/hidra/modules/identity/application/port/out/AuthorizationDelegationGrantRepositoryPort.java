/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationDelegationGrantRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Repository port for AuthorizationDelegationGrant.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.AuthorizationDelegationGrant;

import java.util.Optional;

/**
 * Repository port for AuthorizationDelegationGrant.
 */
public interface AuthorizationDelegationGrantRepositoryPort {

    AuthorizationDelegationGrant save(AuthorizationDelegationGrant model);

    Optional<AuthorizationDelegationGrant> findById(String id);
}
