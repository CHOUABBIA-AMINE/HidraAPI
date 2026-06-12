/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyTransferPointRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyTransferPoint.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyTransferPointRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyTransferPoint;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyTransferPointJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyTransferPoint.
 */
@Component
public class JpaCustodyTransferPointRepositoryAdapter implements CustodyTransferPointRepositoryPort {

    private final CustodyTransferPointJpaRepository repository;

    public JpaCustodyTransferPointRepositoryAdapter(CustodyTransferPointJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyTransferPointJpaRepository must not be null.");
    }

    @Override
    public CustodyTransferPoint save(CustodyTransferPoint model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyTransferPoint> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
