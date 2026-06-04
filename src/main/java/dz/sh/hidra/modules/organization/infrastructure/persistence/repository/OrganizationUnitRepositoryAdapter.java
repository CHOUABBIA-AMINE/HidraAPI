/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.repository
 *
 * @Description : Persistence adapter implementing the organization unit repository port.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepository;
import dz.sh.hidra.modules.organization.domain.model.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;

/**
 * Persistence adapter implementing the organization unit repository port.
 *
 * <p>Business role:
 * Persists and retrieves organization units, including station-as-organization-unit structures.
 *
 * <p>Architecture role:
 * This class adapts the application outbound OrganizationUnitRepository port to Spring Data JPA
 * without exposing persistence entities outside infrastructure.
 *
 * <p>Validation:
 * Domain validation happens before persistence. Operational scope values remain neutral and do not
 * import topology.
 *
 * <p>Usage:
 * Wire this adapter later in organization configuration.
 */
public final class OrganizationUnitRepositoryAdapter implements OrganizationUnitRepository {

    private final OrganizationUnitJpaRepository organizationUnitJpaRepository;
    private final OrganizationPersistenceMapper mapper;

    public OrganizationUnitRepositoryAdapter(
            OrganizationUnitJpaRepository organizationUnitJpaRepository,
            OrganizationPersistenceMapper mapper) {

        this.organizationUnitJpaRepository = Objects.requireNonNull(
                organizationUnitJpaRepository,
                "Organization unit JPA repository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Organization persistence mapper must not be null.");
    }

    @Override
    public OrganizationUnit save(OrganizationUnit organizationUnit) {
        return mapper.toDomain(organizationUnitJpaRepository.save(mapper.toEntity(organizationUnit)));
    }

    @Override
    public Optional<OrganizationUnit> findById(OrganizationUnitId id) {
        Objects.requireNonNull(id, "Organization unit id must not be null.");
        return organizationUnitJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<OrganizationUnit> findByCode(OrganizationUnitCode code) {
        Objects.requireNonNull(code, "Organization unit code must not be null.");
        return organizationUnitJpaRepository.findByCode(code.value()).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCode(OrganizationUnitCode code) {
        Objects.requireNonNull(code, "Organization unit code must not be null.");
        return organizationUnitJpaRepository.existsByCode(code.value());
    }

    @Override
    public List<OrganizationUnit> findChildrenOf(OrganizationUnitId parentId) {
        Objects.requireNonNull(parentId, "Parent organization unit id must not be null.");
        return organizationUnitJpaRepository.findByParentId(parentId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<OrganizationUnit> findByType(OrganizationUnitType type) {
        Objects.requireNonNull(type, "Organization unit type must not be null.");
        return organizationUnitJpaRepository.findByType(type.name()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<OrganizationUnit> findByOperationalScope(OperationalScopeReference operationalScopeReference) {
        Objects.requireNonNull(operationalScopeReference, "Operational scope reference must not be null.");
        return organizationUnitJpaRepository.findByOperationalScopeTypeAndOperationalScopeCode(
                        operationalScopeReference.scopeType().name(),
                        operationalScopeReference.scopeCode())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
