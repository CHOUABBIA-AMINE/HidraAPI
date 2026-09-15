/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLocalCredentialRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for Identity-owned LOCAL credentials.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.LocalCredentialRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.LocalCredential;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.LocalCredentialJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.LocalCredentialJpaRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Persists LOCAL credential hashes through the Identity application port.
 */
@Component
public final class JpaLocalCredentialRepositoryAdapter implements LocalCredentialRepositoryPort {

    private final LocalCredentialJpaRepository repository;

    public JpaLocalCredentialRepositoryAdapter(LocalCredentialJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "LocalCredentialJpaRepository must not be null.");
    }

    @Override
    public LocalCredential save(LocalCredential credential) {
        Objects.requireNonNull(credential, "Local credential must not be null.");
        return toDomain(repository.save(toEntity(credential)));
    }

    @Override
    public Optional<LocalCredential> findById(String id) {
        return repository.findById(id).map(JpaLocalCredentialRepositoryAdapter::toDomain);
    }

    @Override
    public Optional<LocalCredential> findByUserId(String userId) {
        return repository.findByUserId(userId).map(JpaLocalCredentialRepositoryAdapter::toDomain);
    }

    private static LocalCredentialJpaEntity toEntity(LocalCredential credential) {
        return new LocalCredentialJpaEntity(
                credential.id(),
                credential.userId(),
                credential.passwordHash(),
                credential.credentialStatus(),
                credential.passwordChangedAt(),
                credential.createdAt(),
                credential.updatedAt()
        );
    }

    private static LocalCredential toDomain(LocalCredentialJpaEntity entity) {
        return new LocalCredential(
                entity.id(),
                entity.userId(),
                entity.passwordHash(),
                entity.credentialStatus(),
                entity.passwordChangedAt(),
                entity.createdAt(),
                entity.updatedAt()
        );
    }
}
