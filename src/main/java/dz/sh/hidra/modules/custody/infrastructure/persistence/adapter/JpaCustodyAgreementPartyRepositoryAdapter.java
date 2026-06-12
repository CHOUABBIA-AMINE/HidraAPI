/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyAgreementPartyRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyAgreementParty.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyAgreementPartyRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyAgreementParty;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyAgreementPartyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyAgreementParty.
 */
@Component
public class JpaCustodyAgreementPartyRepositoryAdapter implements CustodyAgreementPartyRepositoryPort {

    private final CustodyAgreementPartyJpaRepository repository;

    public JpaCustodyAgreementPartyRepositoryAdapter(CustodyAgreementPartyJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyAgreementPartyJpaRepository must not be null.");
    }

    @Override
    public CustodyAgreementParty save(CustodyAgreementParty model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyAgreementParty> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
