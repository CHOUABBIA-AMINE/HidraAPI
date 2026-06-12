/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuthorizationPolicyRuleRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for AuthorizationPolicyRule.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.AuthorizationPolicyRuleRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.AuthorizationPolicyRule;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.AuthorizationPolicyRuleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AuthorizationPolicyRule.
 */
@Component
public class JpaAuthorizationPolicyRuleRepositoryAdapter implements AuthorizationPolicyRuleRepositoryPort {

    private final AuthorizationPolicyRuleJpaRepository repository;

    public JpaAuthorizationPolicyRuleRepositoryAdapter(AuthorizationPolicyRuleJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AuthorizationPolicyRuleJpaRepository must not be null.");
    }

    @Override
    public AuthorizationPolicyRule save(AuthorizationPolicyRule model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AuthorizationPolicyRule> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
