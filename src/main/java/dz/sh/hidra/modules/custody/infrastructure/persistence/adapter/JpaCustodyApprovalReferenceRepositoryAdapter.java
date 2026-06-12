/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyApprovalReferenceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyApprovalReference.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyApprovalReferenceRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyApprovalReference;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyApprovalReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyApprovalReference.
 */
@Component
public class JpaCustodyApprovalReferenceRepositoryAdapter implements CustodyApprovalReferenceRepositoryPort {

    private final CustodyApprovalReferenceJpaRepository repository;

    public JpaCustodyApprovalReferenceRepositoryAdapter(CustodyApprovalReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyApprovalReferenceJpaRepository must not be null.");
    }

    @Override
    public CustodyApprovalReference save(CustodyApprovalReference model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyApprovalReference> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
