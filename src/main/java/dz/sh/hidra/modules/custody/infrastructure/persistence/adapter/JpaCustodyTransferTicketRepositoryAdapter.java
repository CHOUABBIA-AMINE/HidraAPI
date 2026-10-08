/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyTransferTicketRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyTransferTicket.
 */
@Component
public class JpaCustodyTransferTicketRepositoryAdapter implements CustodyTransferTicketRepositoryPort {

    private final CustodyTransferTicketJpaRepository repository;
    private final CustodyTransferTicketReferenceValidation validation;

    public JpaCustodyTransferTicketRepositoryAdapter(CustodyTransferTicketJpaRepository repository, CustodyTransferTicketReferenceValidation validation) {
        this.repository = Objects.requireNonNull(repository, "CustodyTransferTicketJpaRepository must not be null.");
        this.validation = Objects.requireNonNull(validation);
    }

    @Override
    @Transactional
    public CustodyTransferTicket save(CustodyTransferTicket model) {
        Objects.requireNonNull(model);
        var previous=repository.findByIdForUpdate(model.id()).map(CustodyPersistenceMapper::toDomain).orElse(null);
        validation.validate(model,previous);
        return CustodyPersistenceMapper.toDomain(repository.saveAndFlush(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyTransferTicket> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
