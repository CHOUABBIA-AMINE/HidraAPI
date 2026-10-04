/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOrganizationUnitRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.adapter
 *
 * @Description : Database-backed OrganizationUnit adapter with hierarchy/type policy validation.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationUnitJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationUnitJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Persists OrganizationUnit only when changed classification and hierarchy are valid.
 */
@Component
public class JpaOrganizationUnitRepositoryAdapter implements OrganizationUnitRepositoryPort {

    private final OrganizationUnitJpaRepository repository;

    public JpaOrganizationUnitRepositoryAdapter(
            OrganizationUnitJpaRepository repository
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "OrganizationUnitJpaRepository must not be null."
        );
    }

    @Override
    public OrganizationUnit save(OrganizationUnit model) {
        Objects.requireNonNull(model, "OrganizationUnit must not be null.");

        Optional<OrganizationUnitJpaEntity> existing = repository.findById(model.id());
        boolean typeChanged = existing
                .map(entity -> !entity.unitTypeId().equals(model.unitTypeId()))
                .orElse(true);

        if (typeChanged && !repository.existsActiveUnitType(model.unitTypeId())) {
            throw new InvalidOrganizationValueException(
                    "Organization unit type is inactive and cannot be selected: "
                            + model.unitTypeId()
            );
        }

        if (model.parentUnitId() != null
                && repository.wouldCreateHierarchyCycle(
                        model.id(),
                        model.parentUnitId()
                )) {
            throw new InvalidOrganizationValueException(
                    "Organization unit hierarchy must not contain cycles."
            );
        }

        return OrganizationPersistenceMapper.toDomain(
                repository.save(OrganizationPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<OrganizationUnit> findById(String id) {
        return repository.findById(id).map(OrganizationPersistenceMapper::toDomain);
    }
}
