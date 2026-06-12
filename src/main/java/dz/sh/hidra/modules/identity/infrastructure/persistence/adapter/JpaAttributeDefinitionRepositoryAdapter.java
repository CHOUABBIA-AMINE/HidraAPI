/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAttributeDefinitionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for AttributeDefinition.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.AttributeDefinitionRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.AttributeDefinition;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.AttributeDefinitionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AttributeDefinition.
 */
@Component
public class JpaAttributeDefinitionRepositoryAdapter implements AttributeDefinitionRepositoryPort {

    private final AttributeDefinitionJpaRepository repository;

    public JpaAttributeDefinitionRepositoryAdapter(AttributeDefinitionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AttributeDefinitionJpaRepository must not be null.");
    }

    @Override
    public AttributeDefinition save(AttributeDefinition model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<AttributeDefinition> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
