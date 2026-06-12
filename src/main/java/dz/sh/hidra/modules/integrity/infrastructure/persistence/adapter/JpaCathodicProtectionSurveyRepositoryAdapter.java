/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCathodicProtectionSurveyRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CathodicProtectionSurvey.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.CathodicProtectionSurveyRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.CathodicProtectionSurvey;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.CathodicProtectionSurveyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CathodicProtectionSurvey.
 */
@Component
public class JpaCathodicProtectionSurveyRepositoryAdapter implements CathodicProtectionSurveyRepositoryPort {

    private final CathodicProtectionSurveyJpaRepository repository;

    public JpaCathodicProtectionSurveyRepositoryAdapter(CathodicProtectionSurveyJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CathodicProtectionSurveyJpaRepository must not be null.");
    }

    @Override
    public CathodicProtectionSurvey save(CathodicProtectionSurvey model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CathodicProtectionSurvey> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
