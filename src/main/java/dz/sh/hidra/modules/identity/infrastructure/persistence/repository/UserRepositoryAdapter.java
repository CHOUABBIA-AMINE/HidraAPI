/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.repository
 *
 * @Description : Persistence adapter implementing the identity UserRepository port.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.repository;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.port.out.UserRepository;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.Username;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Persistence adapter implementing the identity UserRepository port.
 *
 * <p>Business role: persists and loads identity user aggregates for application use
 * cases.</p>
 *
 * <p>Architecture role: infrastructure adapter from the application outbound
 * {@link UserRepository} port to Spring Data JPA.</p>
 *
 * <p>Validation responsibility: rejects null dependencies and arguments and delegates
 * domain rebuilding to {@link IdentityPersistenceMapper}.</p>
 *
 * <p>Usage: inject as the application {@link UserRepository} implementation.</p>
 */
@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final IdentityPersistenceMapper mapper;

    public UserRepositoryAdapter(
            UserJpaRepository userJpaRepository,
            IdentityPersistenceMapper mapper
    ) {
        this.userJpaRepository = requireNonNull(userJpaRepository, "UserJpaRepository");
        this.mapper = requireNonNull(mapper, "IdentityPersistenceMapper");
    }

    @Override
    public User save(User user) {
        User requiredUser = requireNonNull(user, "User");
        return mapper.toUserDomain(userJpaRepository.save(mapper.toUserEntity(requiredUser)));
    }

    @Override
    public Optional<User> findById(UserId userId) {
        UserId requiredUserId = requireNonNull(userId, "UserId");
        return userJpaRepository.findById(requiredUserId.value())
                .map(mapper::toUserDomain);
    }

    @Override
    public Optional<User> findByUsername(Username username) {
        Username requiredUsername = requireNonNull(username, "Username");
        return userJpaRepository.findByUsername(requiredUsername.value())
                .map(mapper::toUserDomain);
    }

    @Override
    public Optional<User> findByEmailAddress(EmailAddress emailAddress) {
        EmailAddress requiredEmailAddress = requireNonNull(emailAddress, "EmailAddress");
        return userJpaRepository.findByEmailAddress(requiredEmailAddress.value())
                .map(mapper::toUserDomain);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll().stream()
                .map(mapper::toUserDomain)
                .toList();
    }

    @Override
    public boolean existsByUsername(Username username) {
        Username requiredUsername = requireNonNull(username, "Username");
        return userJpaRepository.existsByUsername(requiredUsername.value());
    }

    @Override
    public boolean existsByEmailAddress(EmailAddress emailAddress) {
        EmailAddress requiredEmailAddress = requireNonNull(emailAddress, "EmailAddress");
        return userJpaRepository.existsByEmailAddress(requiredEmailAddress.value());
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
