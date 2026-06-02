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
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserRepositoryAdapter;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests identity user persistence adapter.
 *
 * <p>Business role: verifies that identity users can be saved and loaded through the
 * application {@code UserRepository} outbound port implementation.</p>
 *
 * <p>Architecture role: persistence adapter test using plain JUnit and Mockito. It avoids
 * Spring Boot test slices so the test does not require {@code spring-boot-test-autoconfigure}
 * on the classpath.</p>
 *
 * <p>Validation responsibility: covers save mapping, lookup by identifier, lookup by
 * username, lookup by email address, and existence checks.</p>
 *
 * <p>Usage: executed by the identity persistence test suite.</p>
 */
class UserRepositoryAdapterTest {

    private final UserJpaRepository userJpaRepository = mock(UserJpaRepository.class);
    private final IdentityPersistenceMapper mapper = new IdentityPersistenceMapper();
    private final UserRepositoryAdapter adapter = new UserRepositoryAdapter(userJpaRepository, mapper);

    @Test
    void saveShouldMapDomainToJpaAndBack() {
        User user = registeredUser();
        user.activate();

        when(userJpaRepository.save(any(UserJpaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = adapter.save(user);

        assertEquals(user.id(), savedUser.id());
        assertEquals(UserStatus.ACTIVE, savedUser.status());
        verify(userJpaRepository).save(any(UserJpaEntity.class));
    }

    @Test
    void findByIdShouldMapJpaEntityToDomain() {
        User user = registeredUser();
        UserJpaEntity entity = mapper.toUserEntity(user);

        when(userJpaRepository.findById("user-1")).thenReturn(Optional.of(entity));

        Optional<User> foundUser = adapter.findById(UserId.of("user-1"));

        assertTrue(foundUser.isPresent());
        assertEquals(user.id(), foundUser.orElseThrow().id());
        assertEquals(user.username(), foundUser.orElseThrow().username());
        assertEquals(user.emailAddress(), foundUser.orElseThrow().emailAddress());
    }

    @Test
    void findByUsernameAndEmailShouldDelegateToJpaRepository() {
        User user = registeredUser();
        UserJpaEntity entity = mapper.toUserEntity(user);

        when(userJpaRepository.findByUsername("abir.medjerab")).thenReturn(Optional.of(entity));
        when(userJpaRepository.findByEmailAddress("abir.medjerab@sonatrach.dz")).thenReturn(Optional.of(entity));

        assertTrue(adapter.findByUsername(Username.of("abir.medjerab")).isPresent());
        assertTrue(adapter.findByEmailAddress(EmailAddress.of("abir.medjerab@sonatrach.dz")).isPresent());
    }

    @Test
    void existsChecksShouldDelegateToJpaRepository() {
        when(userJpaRepository.existsByUsername("abir.medjerab")).thenReturn(true);
        when(userJpaRepository.existsByEmailAddress("abir.medjerab@sonatrach.dz")).thenReturn(true);

        assertTrue(adapter.existsByUsername(Username.of("abir.medjerab")));
        assertTrue(adapter.existsByEmailAddress(EmailAddress.of("abir.medjerab@sonatrach.dz")));
    }

    private static User registeredUser() {
        return User.register(
                UserId.of("user-1"),
                Username.of("abir.medjerab"),
                EmailAddress.of("abir.medjerab@sonatrach.dz")
        );
    }
}
