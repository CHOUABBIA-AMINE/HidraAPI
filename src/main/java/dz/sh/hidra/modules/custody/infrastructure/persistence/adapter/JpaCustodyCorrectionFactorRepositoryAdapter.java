/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyCorrectionFactorRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyCorrectionFactor.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyCorrectionFactorRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyCorrectionFactor;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyCorrectionFactorJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyCorrectionFactor.
 */
@Component
public class JpaCustodyCorrectionFactorRepositoryAdapter implements CustodyCorrectionFactorRepositoryPort {

    private final CustodyCorrectionFactorJpaRepository repository;

    public JpaCustodyCorrectionFactorRepositoryAdapter(CustodyCorrectionFactorJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyCorrectionFactorJpaRepository must not be null.");
    }

    @Override
    public CustodyCorrectionFactor save(CustodyCorrectionFactor model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyCorrectionFactor> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
