/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationMappingProfileRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationMappingProfile.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationMappingProfileRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationMappingProfile;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationMappingProfileJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationMappingProfile.
 */
@Component
public class JpaIntegrationMappingProfileRepositoryAdapter implements IntegrationMappingProfileRepositoryPort {

    private final IntegrationMappingProfileJpaRepository repository;

    public JpaIntegrationMappingProfileRepositoryAdapter(IntegrationMappingProfileJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationMappingProfileJpaRepository must not be null.");
    }

    @Override
    public IntegrationMappingProfile save(IntegrationMappingProfile model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationMappingProfile> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
