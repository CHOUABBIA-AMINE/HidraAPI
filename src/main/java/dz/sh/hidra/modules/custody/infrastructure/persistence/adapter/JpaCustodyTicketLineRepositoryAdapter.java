/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyTicketLineRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyTicketLine.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyTicketLineRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyTicketLine;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyTicketLineJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyTicketLine.
 */
@Component
public class JpaCustodyTicketLineRepositoryAdapter implements CustodyTicketLineRepositoryPort {

    private final CustodyTicketLineJpaRepository repository;

    public JpaCustodyTicketLineRepositoryAdapter(CustodyTicketLineJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyTicketLineJpaRepository must not be null.");
    }

    @Override
    public CustodyTicketLine save(CustodyTicketLine model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyTicketLine> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
