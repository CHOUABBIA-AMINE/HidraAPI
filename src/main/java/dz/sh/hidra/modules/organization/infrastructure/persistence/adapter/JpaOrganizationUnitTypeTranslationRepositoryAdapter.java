/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOrganizationUnitTypeTranslationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for OrganizationUnitTypeTranslation.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitTypeTranslationRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnitTypeTranslation;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationUnitTypeTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for OrganizationUnitTypeTranslation.
 */
@Component
public class JpaOrganizationUnitTypeTranslationRepositoryAdapter implements OrganizationUnitTypeTranslationRepositoryPort {

    private final OrganizationUnitTypeTranslationJpaRepository repository;

    public JpaOrganizationUnitTypeTranslationRepositoryAdapter(OrganizationUnitTypeTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "OrganizationUnitTypeTranslationJpaRepository must not be null.");
    }

    @Override
    public OrganizationUnitTypeTranslation save(OrganizationUnitTypeTranslation model) {
        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<OrganizationUnitTypeTranslation> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }
}
