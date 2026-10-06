/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationDelegationGrantSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Enforces the admitted Batch 5 AuthorizationDelegationGrantSemanticRemediationTest contract.
 *
 */
package dz.sh.hidra.modules.identity.semantic;

import dz.sh.hidra.modules.identity.domain.model.AuthorizationDelegationGrant;
import dz.sh.hidra.modules.identity.domain.value.DelegationStatus;
import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JpaAuthorizationDelegationGrantRepositoryAdapter;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.*;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorizationDelegationGrantSemanticRemediationTest {
    private final Instant from = Instant.parse("2026-10-06T12:00:00Z");
    private AuthorizationDelegationGrant grant(String permission, String role, Instant to, String reason) {
        return new AuthorizationDelegationGrant("d", "u", "v", permission, role, null, null,
                from, to, DelegationStatus.ACTIVE, from, null, reason);
    }
    @Test void requiresReasonAndBoundedValidity() {
        assertThatThrownBy(() -> grant(null,null,null,"reason")).isInstanceOf(InvalidIdentityValueException.class);
        assertThatThrownBy(() -> grant(null,null,from," ")).isInstanceOf(InvalidIdentityValueException.class);
        assertThatThrownBy(() -> grant(null,null,from.minusSeconds(1),"reason")).isInstanceOf(InvalidIdentityValueException.class);
        assertThat(grant(null,null,from," reason ").reason()).isEqualTo("reason");
        assertThat(DelegationStatus.values()).extracting(Enum::name).containsExactly("ACTIVE","REVOKED","EXPIRED");
    }
    @Test void optionalReferencesAreValidatedWithoutExclusiveSelectionRule() {
        var repository = mock(AuthorizationDelegationGrantJpaRepository.class);
        var roles = mock(RoleJpaRepository.class);
        var permissions = mock(PermissionJpaRepository.class);
        var adapter = new JpaAuthorizationDelegationGrantRepositoryAdapter(repository,roles,permissions);
        assertThatThrownBy(() -> adapter.save(grant(null,"missing",from,"reason"))).isInstanceOf(InvalidIdentityValueException.class);
        assertThatThrownBy(() -> adapter.save(grant("missing",null,from,"reason"))).isInstanceOf(InvalidIdentityValueException.class);
        verifyNoInteractions(repository);
    }
}
