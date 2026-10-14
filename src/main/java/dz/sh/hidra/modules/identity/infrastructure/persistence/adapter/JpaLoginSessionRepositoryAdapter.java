/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLoginSessionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for LoginSession.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.LoginSessionRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.LoginSession;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.LoginSessionJpaRepository;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for LoginSession.
 */
@Component
public class JpaLoginSessionRepositoryAdapter implements LoginSessionRepositoryPort {

    private final LoginSessionJpaRepository repository;
    @jakarta.persistence.PersistenceContext private jakarta.persistence.EntityManager entityManager;

    public JpaLoginSessionRepositoryAdapter(LoginSessionJpaRepository repository) {
        this.repository =
                Objects.requireNonNull(repository, "LoginSessionJpaRepository must not be null.");
    }

    @Override
    public LoginSession save(LoginSession model) {
        return IdentityPersistenceMapper.toDomain(
                repository.saveAndFlush(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LoginSession> findById(String id) {
        if (entityManager != null
                && org.springframework.transaction.support.TransactionSynchronizationManager
                        .isActualTransactionActive()
                && !org.springframework.transaction.support.TransactionSynchronizationManager
                        .isCurrentTransactionReadOnly()) {
            var row =
                    entityManager.find(
                            dz.sh.hidra.modules.identity.infrastructure.persistence.entity
                                    .LoginSessionJpaEntity.class,
                            id,
                            jakarta.persistence.LockModeType.PESSIMISTIC_WRITE);
            if (row == null) return Optional.empty();
            entityManager.refresh(row, jakarta.persistence.LockModeType.PESSIMISTIC_WRITE);
            return Optional.of(IdentityPersistenceMapper.toDomain(row));
        }
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
