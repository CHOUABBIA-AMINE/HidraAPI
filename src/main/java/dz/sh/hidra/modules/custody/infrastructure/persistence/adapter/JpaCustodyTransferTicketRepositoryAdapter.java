/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyTransferTicketRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyTransferTicket.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyTransferTicketRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyTransferTicket;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyTransferTicketJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyTransferTicket.
 */
@Component
public class JpaCustodyTransferTicketRepositoryAdapter implements CustodyTransferTicketRepositoryPort {

    private final CustodyTransferTicketJpaRepository repository;

    public JpaCustodyTransferTicketRepositoryAdapter(CustodyTransferTicketJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyTransferTicketJpaRepository must not be null.");
    }

    @Override
    public CustodyTransferTicket save(CustodyTransferTicket model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyTransferTicket> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
