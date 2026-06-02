/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Outbound repository port for identity users.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.Username;

import java.util.List;
import java.util.Optional;

/**
 * Outbound repository port for identity users.
 *
 * <p>Business role: provides application services with user lookup and persistence
 * operations for registration, lifecycle, role assignment, and permission evaluation use
 * cases.</p>
 *
 * <p>Architecture role: application outbound port implemented later by infrastructure
 * persistence adapters. It does not depend on Spring Data, JPA, SQL, platform security
 * plumbing, or organization structures.</p>
 *
 * <p>Validation responsibility: callers and implementations should use validated user
 * identifiers, usernames, and email addresses.</p>
 *
 * <p>Usage: depend on this port from user application services.</p>
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findById(UserId userId);

    Optional<User> findByUsername(Username username);

    Optional<User> findByEmailAddress(EmailAddress emailAddress);

    List<User> findAll();

    boolean existsByUsername(Username username);

    boolean existsByEmailAddress(EmailAddress emailAddress);
}
