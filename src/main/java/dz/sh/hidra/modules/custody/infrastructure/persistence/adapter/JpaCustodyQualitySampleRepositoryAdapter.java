/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyQualitySampleRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyQualitySample.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyQualitySampleRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyQualitySample;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyQualitySampleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyQualitySample.
 */
@Component
public class JpaCustodyQualitySampleRepositoryAdapter implements CustodyQualitySampleRepositoryPort {

    private final CustodyQualitySampleJpaRepository repository;

    public JpaCustodyQualitySampleRepositoryAdapter(CustodyQualitySampleJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyQualitySampleJpaRepository must not be null.");
    }

    @Override
    public CustodyQualitySample save(CustodyQualitySample model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyQualitySample> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
