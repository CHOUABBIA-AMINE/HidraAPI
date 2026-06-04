/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.repository
 *
 * @Description : Persistence adapter implementing the position repository port.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.modules.organization.application.port.out.PositionRepository;
import dz.sh.hidra.modules.organization.domain.model.Position;
import dz.sh.hidra.modules.organization.domain.value.PositionCode;
import dz.sh.hidra.modules.organization.domain.value.PositionId;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;

/**
 * Persistence adapter implementing the position repository port.
 *
 * <p>Business role:
 * Persists and retrieves operational positions/functions.
 *
 * <p>Architecture role:
 * This class adapts the application outbound PositionRepository port to Spring Data JPA without
 * exposing persistence entities outside infrastructure.
 *
 * <p>Validation:
 * Domain validation happens before persistence. Database constraints protect position code
 * uniqueness.
 *
 * <p>Usage:
 * Wire this adapter later in organization configuration.
 */
public final class PositionRepositoryAdapter implements PositionRepository {

    private final PositionJpaRepository positionJpaRepository;
    private final OrganizationPersistenceMapper mapper;

    public PositionRepositoryAdapter(
            PositionJpaRepository positionJpaRepository,
            OrganizationPersistenceMapper mapper) {

        this.positionJpaRepository = Objects.requireNonNull(positionJpaRepository, "Position JPA repository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Organization persistence mapper must not be null.");
    }

    @Override
    public Position save(Position position) {
        return mapper.toDomain(positionJpaRepository.save(mapper.toEntity(position)));
    }

    @Override
    public Optional<Position> findById(PositionId id) {
        Objects.requireNonNull(id, "Position id must not be null.");
        return positionJpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<Position> findByCode(PositionCode code) {
        Objects.requireNonNull(code, "Position code must not be null.");
        return positionJpaRepository.findByCode(code.value()).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCode(PositionCode code) {
        Objects.requireNonNull(code, "Position code must not be null.");
        return positionJpaRepository.existsByCode(code.value());
    }

    @Override
    public List<Position> findActivePositions() {
        return positionJpaRepository.findByActiveTrue().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
