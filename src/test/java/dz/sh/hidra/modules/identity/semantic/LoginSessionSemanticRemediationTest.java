/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LoginSessionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Enforces the admitted Batch 5 LoginSessionSemanticRemediationTest contract.
 *
 */
package dz.sh.hidra.modules.identity.semantic;

import dz.sh.hidra.modules.identity.domain.model.*;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.application.service.AuthenticationSessionLifecycleApplicationService;
import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginSessionSemanticRemediationTest {
    @Test void externalAuthenticationCarriesExactIdentityAndProtocol() {
        var sessions = mock(LoginSessionRepositoryPort.class);
        when(sessions.save(any())).thenAnswer(x -> x.getArgument(0));
        var events = mock(AuthenticationEventRepositoryPort.class);
        var service = new AuthenticationSessionLifecycleApplicationService(sessions, events, mock(IdentityProviderRepositoryPort.class));
        var principal = new HidraPrincipal("u","alice",null,ProviderType.OIDC,"provider",Set.of(),Set.of(),"external-42");
        var session = service.startSession(principal,Instant.now().plusSeconds(300),null,null,"correlation");
        assertThat(session.externalIdentityId()).isEqualTo("external-42");
        assertThat(session.sessionType()).isEqualTo(AuthenticationProtocol.OIDC);
        assertThat(session.endedAt()).isNull();
        assertThat(IdentityPersistenceMapper.toDomain(IdentityPersistenceMapper.toEntity(session))).isEqualTo(session);
    }
    @Test void terminationPreservesActivityAndIsIdempotent() {
        var sessions = mock(LoginSessionRepositoryPort.class);
        when(sessions.save(any())).thenAnswer(x -> x.getArgument(0));
        var service = new AuthenticationSessionLifecycleApplicationService(sessions, mock(AuthenticationEventRepositoryPort.class), mock(IdentityProviderRepositoryPort.class));
        var principal = new HidraPrincipal("u","alice",null,ProviderType.LOCAL,null,Set.of(),Set.of());
        var active = service.startSession(principal,Instant.now().plusSeconds(300),null,null,null);
        when(sessions.findById(active.id())).thenReturn(Optional.of(active));
        var ended = service.logoutSession(active.id());
        assertThat(ended.lastSeenAt()).isEqualTo(active.lastSeenAt());
        assertThat(ended.endedAt()).isNotNull();
        assertThat(ended.sessionType()).isEqualTo(AuthenticationProtocol.LOCAL);
        when(sessions.findById(active.id())).thenReturn(Optional.of(ended));
        assertThat(service.revokeSession(active.id())).isSameAs(ended);
        assertThat(service.touchSession(active.id(),Instant.now())).isSameAs(ended);
    }
    @Test void expiryRecordsAnEndWithoutChangingLastActivity() {
        var sessions = mock(LoginSessionRepositoryPort.class);
        when(sessions.save(any())).thenAnswer(x -> x.getArgument(0));
        var service = new AuthenticationSessionLifecycleApplicationService(sessions, mock(AuthenticationEventRepositoryPort.class), mock(IdentityProviderRepositoryPort.class));
        var principal = new HidraPrincipal("u","alice",null,ProviderType.LOCAL,null,Set.of(),Set.of());
        var active = service.startSession(principal,Instant.now().plusSeconds(300),null,null,null);
        when(sessions.findById(active.id())).thenReturn(Optional.of(active));
        var expired = service.touchSession(active.id(),active.expiresAt());
        assertThat(expired.status()).isEqualTo(LoginSessionStatus.EXPIRED);
        assertThat(expired.endedAt()).isEqualTo(active.expiresAt());
        assertThat(expired.lastSeenAt()).isEqualTo(active.lastSeenAt());
    }
}
