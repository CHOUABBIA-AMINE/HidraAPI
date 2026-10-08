/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaHseCaseRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for HseCase.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.HseCaseRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HseCase;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HseCaseJpaRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class JpaHseCaseRepositoryAdapter implements HseCaseRepositoryPort {

    private final HseCaseJpaRepository repository;
    private final HseCaseReferenceValidation references;

    public JpaHseCaseRepositoryAdapter(HseCaseJpaRepository repository, HseCaseReferenceValidation references) {
        this.repository = Objects.requireNonNull(repository, "HseCaseJpaRepository must not be null.");
        this.references = Objects.requireNonNull(references);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public HseCase save(HseCase model) {
        Objects.requireNonNull(model);
        HseCase old=repository.findByIdForUpdate(model.id()).map(HsePersistenceMapper::toDomain).orElse(null);
        if(model.status()==dz.sh.hidra.modules.hse.domain.value.HseCaseStatus.CLOSED
                && (old==null || old.status()!=model.status()))
            throw new IllegalArgumentException("CLOSED requires the coordinated closure operation.");
        if(old!=null && old.status()==dz.sh.hidra.modules.hse.domain.value.HseCaseStatus.CLOSED
                && (model.status()!=old.status() || !Objects.equals(model.closedAt(),old.closedAt())))
            throw new IllegalArgumentException("Recorded closed lifecycle cannot be changed.");
        if(model.status()!=dz.sh.hidra.modules.hse.domain.value.HseCaseStatus.CLOSED && model.closedAt()!=null)
            throw new IllegalArgumentException("Nonclosed case cannot carry closure time.");
        references.validate(model,old);
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<HseCase> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }

    @Override
    public Optional<HseCase> findByIdForUpdate(String id) {
        return repository.findByIdForUpdate(id).map(HsePersistenceMapper::toDomain);
    }

    @Override
    public List<HseCase> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt")))
                .stream().map(HsePersistenceMapper::toDomain).toList();
    }

    @Override
    public long count() {
        return repository.count();
    }
}
