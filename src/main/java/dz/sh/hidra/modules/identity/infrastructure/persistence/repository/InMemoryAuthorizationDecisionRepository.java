/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InMemoryAuthorizationDecisionRepository
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.repository
 *
 * @Description : In-memory repository adapter for authorization decisions.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.repository;

import dz.sh.hidra.modules.identity.application.port.out.AuthorizationDecisionRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.AuthorizationDecision;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory repository adapter for authorization decisions.
 */
public class InMemoryAuthorizationDecisionRepository implements AuthorizationDecisionRepositoryPort {

    private final Map<String, AuthorizationDecision> store = new LinkedHashMap<>();

    @Override
    public AuthorizationDecision save(AuthorizationDecision model) {
        store.put(model.id(), model);
        return model;
    }

    @Override
    public Optional<AuthorizationDecision> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}
