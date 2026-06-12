/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAdministrativeLocalityRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for AdministrativeLocality.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.AdministrativeLocalityRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.AdministrativeLocality;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.AdministrativeLocalityJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for AdministrativeLocality.
 */
@Component
public class JpaAdministrativeLocalityRepositoryAdapter implements AdministrativeLocalityRepositoryPort {

    private final AdministrativeLocalityJpaRepository repository;

    public JpaAdministrativeLocalityRepositoryAdapter(AdministrativeLocalityJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AdministrativeLocalityJpaRepository must not be null.");
    }

    @Override
    public AdministrativeLocality save(AdministrativeLocality model) {
        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<AdministrativeLocality> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }
}
