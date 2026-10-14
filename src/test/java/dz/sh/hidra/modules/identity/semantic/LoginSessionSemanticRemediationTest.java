/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LoginSessionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.application.service.AuthenticationSessionLifecycleApplicationService;
import dz.sh.hidra.modules.identity.domain.model.*;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;

class LoginSessionSemanticRemediationTest {
    @Test
    void externalAuthenticationCarriesExactIdentityAndProtocol() {
        var sessions = mock(LoginSessionRepositoryPort.class);
        when(sessions.save(any())).thenAnswer(x -> x.getArgument(0));
        var events = mock(AuthenticationEventRepositoryPort.class);
        var service =
                new AuthenticationSessionLifecycleApplicationService(
                        sessions, events, mock(IdentityProviderRepositoryPort.class));
        var principal =
                new HidraPrincipal(
                        "u",
                        "alice",
                        null,
                        ProviderType.OIDC,
                        "provider",
                        Set.of(),
                        Set.of(),
                        "external-42");
        var session =
                service.startSession(
                        principal, Instant.now().plusSeconds(300), null, null, "correlation");
        assertThat(session.externalIdentityId()).isEqualTo("external-42");
        assertThat(session.sessionType()).isEqualTo(AuthenticationProtocol.OIDC);
        assertThat(session.endedAt()).isNull();
        assertThat(IdentityPersistenceMapper.toDomain(IdentityPersistenceMapper.toEntity(session)))
                .isEqualTo(session);
    }

    @Test
    void terminationPreservesActivityAndIsIdempotent() {
        var sessions = mock(LoginSessionRepositoryPort.class);
        when(sessions.save(any())).thenAnswer(x -> x.getArgument(0));
        var service =
                new AuthenticationSessionLifecycleApplicationService(
                        sessions,
                        mock(AuthenticationEventRepositoryPort.class),
                        mock(IdentityProviderRepositoryPort.class));
        var principal =
                new HidraPrincipal(
                        "u", "alice", null, ProviderType.LOCAL, null, Set.of(), Set.of());
        var active =
                service.startSession(principal, Instant.now().plusSeconds(300), null, null, null);
        when(sessions.findById(active.id())).thenReturn(Optional.of(active));
        var ended = service.logoutSession(active.id());
        assertThat(ended.lastSeenAt()).isEqualTo(active.lastSeenAt());
        assertThat(ended.endedAt()).isNotNull();
        assertThat(ended.sessionType()).isEqualTo(AuthenticationProtocol.LOCAL);
        when(sessions.findById(active.id())).thenReturn(Optional.of(ended));
        assertThat(service.revokeSession(active.id())).isSameAs(ended);
        assertThat(service.touchSession(active.id(), Instant.now())).isSameAs(ended);
    }

    @Test
    void expiryRecordsAnEndWithoutChangingLastActivity() {
        var sessions = mock(LoginSessionRepositoryPort.class);
        when(sessions.save(any())).thenAnswer(x -> x.getArgument(0));
        var service =
                new AuthenticationSessionLifecycleApplicationService(
                        sessions,
                        mock(AuthenticationEventRepositoryPort.class),
                        mock(IdentityProviderRepositoryPort.class));
        var principal =
                new HidraPrincipal(
                        "u", "alice", null, ProviderType.LOCAL, null, Set.of(), Set.of());
        var active =
                service.startSession(principal, Instant.now().plusSeconds(300), null, null, null);
        when(sessions.findById(active.id())).thenReturn(Optional.of(active));
        var expired = service.touchSession(active.id(), active.expiresAt());
        assertThat(expired.status()).isEqualTo(LoginSessionStatus.EXPIRED);
        assertThat(expired.endedAt()).isEqualTo(active.expiresAt());
        assertThat(expired.lastSeenAt()).isEqualTo(active.lastSeenAt());
    }

    @Test
    void sessionPersistenceFlushesBeforeReturningTheSavedModel() {
        var repository =
                mock(
                        dz.sh.hidra.modules.identity.infrastructure.persistence.repository
                                .LoginSessionJpaRepository.class);
        when(repository.saveAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));
        var adapter =
                new dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
                        .JpaLoginSessionRepositoryAdapter(repository);
        var now = Instant.parse("2026-10-14T08:00:00Z");
        var model =
                new LoginSession(
                        "session",
                        "user",
                        null,
                        null,
                        now,
                        now,
                        now.plusSeconds(60),
                        null,
                        null,
                        LoginSessionStatus.ACTIVE,
                        null,
                        AuthenticationProtocol.LOCAL,
                        null);
        assertThat(adapter.save(model)).isEqualTo(model);
        verify(repository).saveAndFlush(any());
        verify(repository, never()).save(any());
    }

    @Test
    void readOnlySessionQueriesDoNotAcquireMutationLocks() {
        var repository =
                mock(
                        dz.sh.hidra.modules.identity.infrastructure.persistence.repository
                                .LoginSessionJpaRepository.class);
        var entityManager = mock(jakarta.persistence.EntityManager.class);
        var adapter =
                new dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
                        .JpaLoginSessionRepositoryAdapter(repository);
        org.springframework.test.util.ReflectionTestUtils.setField(
                adapter, "entityManager", entityManager);
        when(repository.findById("missing")).thenReturn(Optional.empty());
        org.springframework.transaction.support.TransactionSynchronizationManager
                .setActualTransactionActive(true);
        org.springframework.transaction.support.TransactionSynchronizationManager
                .setCurrentTransactionReadOnly(true);
        try {
            assertThat(adapter.findById("missing")).isEmpty();
            verify(repository).findById("missing");
            verifyNoInteractions(entityManager);
        } finally {
            org.springframework.transaction.support.TransactionSynchronizationManager.clear();
        }
    }
}
