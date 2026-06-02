/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserRepositoryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Persistence Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Tests identity user persistence adapter.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.Username;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests identity user persistence adapter.
 *
 * <p>Business role: verifies that identity users can be persisted and loaded through the
 * application {@code UserRepository} outbound port implementation.</p>
 *
 * <p>Architecture role: persistence adapter test using Spring Data JPA test support. It
 * exercises the infrastructure adapter and mapper without importing REST controllers,
 * platform security plumbing, or organization modules.</p>
 *
 * <p>Validation responsibility: covers save/load by identifier, lookup by username,
 * lookup by email address, uniqueness checks, and domain status round-trip.</p>
 *
 * <p>Usage: executed by the identity persistence test suite.</p>
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import({IdentityPersistenceMapper.class, UserRepositoryAdapter.class})
class UserRepositoryAdapterTest {

    @Autowired
    private UserRepositoryAdapter userRepositoryAdapter;

    @Test
    void saveShouldPersistAndLoadUserById() {
        User user = registeredUser();
        user.activate();

        User savedUser = userRepositoryAdapter.save(user);

        Optional<User> foundUser = userRepositoryAdapter.findById(savedUser.id());

        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.id(), foundUser.orElseThrow().id());
        assertEquals(Username.of("abir.medjerab"), foundUser.orElseThrow().username());
        assertEquals(EmailAddress.of("abir.medjerab@sonatrach.dz"), foundUser.orElseThrow().emailAddress());
        assertEquals(UserStatus.ACTIVE, foundUser.orElseThrow().status());
    }

    @Test
    void findByUsernameAndEmailShouldReturnPersistedUser() {
        User savedUser = userRepositoryAdapter.save(registeredUser());

        Optional<User> byUsername = userRepositoryAdapter.findByUsername(Username.of("abir.medjerab"));
        Optional<User> byEmail = userRepositoryAdapter.findByEmailAddress(
                EmailAddress.of("abir.medjerab@sonatrach.dz")
        );

        assertTrue(byUsername.isPresent());
        assertTrue(byEmail.isPresent());
        assertEquals(savedUser.id(), byUsername.orElseThrow().id());
        assertEquals(savedUser.id(), byEmail.orElseThrow().id());
    }

    @Test
    void existsChecksShouldReflectPersistedUser() {
        userRepositoryAdapter.save(registeredUser());

        assertTrue(userRepositoryAdapter.existsByUsername(Username.of("abir.medjerab")));
        assertTrue(userRepositoryAdapter.existsByEmailAddress(EmailAddress.of("abir.medjerab@sonatrach.dz")));
    }

    private static User registeredUser() {
        return User.register(
                UserId.of("user-1"),
                Username.of("abir.medjerab"),
                EmailAddress.of("abir.medjerab@sonatrach.dz")
        );
    }
}
