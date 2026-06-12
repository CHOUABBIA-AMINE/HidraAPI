/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskCatalogTranslation;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskCatalogTranslation.
 */
@Component
public class JpaRiskCatalogTranslationRepositoryAdapter implements RiskCatalogTranslationRepositoryPort {

    private final RiskCatalogTranslationJpaRepository repository;

    public JpaRiskCatalogTranslationRepositoryAdapter(RiskCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public RiskCatalogTranslation save(RiskCatalogTranslation model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskCatalogTranslation> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
