/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOrganizationDelegationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for OrganizationDelegation.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.OrganizationDelegationRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OrganizationDelegation;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationDelegationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for OrganizationDelegation.
 */
@Component
public class JpaOrganizationDelegationRepositoryAdapter implements OrganizationDelegationRepositoryPort {

    private final OrganizationDelegationJpaRepository repository;

    public JpaOrganizationDelegationRepositoryAdapter(OrganizationDelegationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "OrganizationDelegationJpaRepository must not be null.");
    }

    @Override
    public OrganizationDelegation save(OrganizationDelegation model) {
        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<OrganizationDelegation> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }
}
