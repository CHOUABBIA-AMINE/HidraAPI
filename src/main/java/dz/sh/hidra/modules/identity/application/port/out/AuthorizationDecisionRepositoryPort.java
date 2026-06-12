/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationDecisionRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Outbound repository port for AuthorizationDecision.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.AuthorizationDecision;

import java.util.Optional;

/**
 * Outbound repository port for AuthorizationDecision.
 */
public interface AuthorizationDecisionRepositoryPort {

    AuthorizationDecision save(AuthorizationDecision model);

    Optional<AuthorizationDecision> findById(String id);
}
