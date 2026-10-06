/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Enforces the admitted Batch 5 UserSemanticRemediationTest contract.
 *
 */
package dz.sh.hidra.modules.identity.semantic;

import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JpaUserRepositoryAdapter;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository;
import dz.sh.hidra.modules.organization.application.contract.identity.IdentityEmployeeReferenceContract;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserSemanticRemediationTest {
    private User user(String username, String employee) {
        return new User("u", username, null, null, UserType.HUMAN, UserStatus.REGISTERED,
                employee, null, 0, null, Instant.now(), null, null, null, Instant.now());
    }
    @Test void rejectsBlankUsernameBeforePersistence() {
        for (String username : new String[]{null, "", "  "}) {
            assertThatThrownBy(() -> user(username, null)).isInstanceOf(InvalidIdentityValueException.class);
        }
        assertThat(user(" alice ", null).username()).isEqualTo("alice");
    }
    @Test void rejectsUnknownEmployeeThroughOwnerContract() {
        var repository = mock(UserJpaRepository.class);
        var employees = mock(IdentityEmployeeReferenceContract.class);
        var adapter = new JpaUserRepositoryAdapter(repository, employees);
        assertThatThrownBy(() -> adapter.save(user("alice", "missing")))
                .isInstanceOf(InvalidIdentityValueException.class);
        verify(employees).exists("missing");
        verifyNoInteractions(repository);
    }
}
