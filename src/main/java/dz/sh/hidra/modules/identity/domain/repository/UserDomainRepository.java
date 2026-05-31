/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserDomainRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.repository
 *
 * @Description : Domain repository contract for identity users.
 *
 */
package dz.sh.hidra.modules.identity.domain.repository;

import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.Username;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for users.
 *
 * <p>Business role: provides user lookup and persistence boundaries required by
 * registration, lifecycle, role assignment, and permission evaluation use cases.</p>
 *
 * <p>Architecture role: domain-facing repository contract. Infrastructure adapters may
 * implement it later, but this interface remains free of Spring Data, JPA, SQL, and
 * platform security details.</p>
 *
 * <p>Validation responsibility: callers must pass validated {@link UserId},
 * {@link Username}, and {@link EmailAddress} values. Implementations should preserve
 * aggregate invariants when storing and loading users.</p>
 *
 * <p>Usage: depend on this contract from identity application services when users must
 * be saved, loaded, or checked for uniqueness.</p>
 */
public interface UserDomainRepository {

    User save(User user);

    Optional<User> findById(UserId userId);

    Optional<User> findByUsername(Username username);

    Optional<User> findByEmailAddress(EmailAddress emailAddress);

    List<User> findAll();

    boolean existsByUsername(Username username);

    boolean existsByEmailAddress(EmailAddress emailAddress);
}
