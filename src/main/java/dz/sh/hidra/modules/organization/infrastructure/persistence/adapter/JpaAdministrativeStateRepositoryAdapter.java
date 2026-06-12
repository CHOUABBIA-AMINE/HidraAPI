/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAdministrativeStateRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AdministrativeState.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.AdministrativeStateRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.AdministrativeState;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.AdministrativeStateJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AdministrativeState.
 */
@Component
public class JpaAdministrativeStateRepositoryAdapter implements AdministrativeStateRepositoryPort {

    private final AdministrativeStateJpaRepository repository;

    public JpaAdministrativeStateRepositoryAdapter(AdministrativeStateJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AdministrativeStateJpaRepository must not be null.");
    }

    @Override
    public AdministrativeState save(AdministrativeState model) {
        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<AdministrativeState> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }
}
