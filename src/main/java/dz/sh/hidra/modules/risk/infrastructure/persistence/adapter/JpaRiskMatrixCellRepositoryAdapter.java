/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskMatrixCellRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed RiskMatrixCell adapter with coordinate and catalog-family validation.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskMatrixCellRepositoryPort;
import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
import dz.sh.hidra.modules.risk.domain.model.RiskMatrixCell;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskMatrixCellJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Persists only matrix cells whose coordinate and controlled-level semantics are valid.
 */
@Component
public class JpaRiskMatrixCellRepositoryAdapter implements RiskMatrixCellRepositoryPort {

    private static final String LIKELIHOOD_CATALOG = "RISK_LIKELIHOOD_LEVEL";
    private static final String CONSEQUENCE_CATALOG = "RISK_CONSEQUENCE_LEVEL";

    private final RiskMatrixCellJpaRepository repository;

    public JpaRiskMatrixCellRepositoryAdapter(RiskMatrixCellJpaRepository repository) {
        this.repository = Objects.requireNonNull(
                repository,
                "RiskMatrixCellJpaRepository must not be null."
        );
    }

    @Override
    public RiskMatrixCell save(RiskMatrixCell model) {
        Objects.requireNonNull(model, "RiskMatrixCell must not be null.");

        if (!repository.existsCatalogEntryInFamily(
                model.likelihoodLevelId(),
                LIKELIHOOD_CATALOG
        )) {
            throw new InvalidRiskValueException(
                    "RiskMatrixCell likelihood level must resolve to "
                            + LIKELIHOOD_CATALOG + ": " + model.likelihoodLevelId()
            );
        }

        if (!repository.existsCatalogEntryInFamily(
                model.consequenceLevelId(),
                CONSEQUENCE_CATALOG
        )) {
            throw new InvalidRiskValueException(
                    "RiskMatrixCell consequence level must resolve to "
                            + CONSEQUENCE_CATALOG + ": " + model.consequenceLevelId()
            );
        }

        if (repository.existsOtherAtCoordinate(
                model.id(),
                model.riskMatrixId(),
                model.likelihoodLevelId(),
                model.consequenceLevelId()
        )) {
            throw new InvalidRiskValueException(
                    "RiskMatrixCell coordinate must be unique per matrix, likelihood and consequence."
            );
        }

        return RiskPersistenceMapper.toDomain(
                repository.save(RiskPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<RiskMatrixCell> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
