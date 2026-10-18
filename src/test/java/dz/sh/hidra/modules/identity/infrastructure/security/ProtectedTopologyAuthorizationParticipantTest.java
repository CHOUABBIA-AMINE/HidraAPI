/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedTopologyAuthorizationParticipantTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Checks paired contract and mocked invocation mechanics without asserting database provenance.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyOperationAuthenticationContract.Evidence;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedAuthorizationContract.*;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.*;
import dz.sh.hidra.modules.identity.domain.policy.*;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader.PairedAuthorizationEvidence;

import org.junit.jupiter.api.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

/** Mocked mechanical checks only; no assertion that these fixtures establish genuine provenance. */
class ProtectedTopologyAuthorizationParticipantTest {
    private final ProtectedOperationIdentityParticipant identity =
            mock(ProtectedOperationIdentityParticipant.class);
    private final JdbcProtectedAuthorizationEvidenceReader reader =
            mock(JdbcProtectedAuthorizationEvidenceReader.class);
    private final ProtectedTopologyAuthorizationParticipant adapter =
            new ProtectedTopologyAuthorizationParticipant(identity, reader);
    private final ProtectedOperationIdentityParticipant.Prepared original =
            mock(ProtectedOperationIdentityParticipant.Prepared.class);
    private final ProtectedOperationIdentityParticipant.QualifiedClaimPair pair =
            mock(ProtectedOperationIdentityParticipant.QualifiedClaimPair.class);
    private final Request request = new Request(Operation.AUTHOR, "company", "facility");
    private final Instant at = Instant.parse("2026-10-18T00:00:00Z");
    private org.springframework.security.core.Authentication actual;

    @BeforeEach
    void setup() {
        actual = UsernamePasswordAuthenticationToken.authenticated("mock-actor", "", List.of());
        SecurityContextHolder.getContext().setAuthentication(actual);
        when(identity.prepare()).thenReturn(original);
        when(identity.qualifyPair(original, request)).thenReturn(pair);
        when(reader.loadQualifiedPair(pair, request)).thenReturn(graphs());
    }

    @AfterEach
    void clear() {
        SecurityContextHolder.clearContext();
    }

    private PairedAuthorizationEvidence graphs() {
        String id = UUID.randomUUID().toString();
        var bound =
                new ProtectedOperationIdentityParticipant.Contribution(
                        new Evidence(
                                id,
                                id,
                                "actual-user",
                                "provider",
                                "LOCAL",
                                1,
                                null,
                                null,
                                "credential",
                                1L,
                                "hidra-api",
                                id,
                                at,
                                at.plusSeconds(60),
                                null),
                        at.plusSeconds(30),
                        123,
                        456);
        var company =
                new AuthorizationEvaluationRequest(
                        "actual-user",
                        "topology:station-revision:author",
                        "STATION_GRAPH_REVISION",
                        "facility",
                        new AuthorizationScope(ScopeType.CUSTOM, "topology-company:company", null));
        var facility =
                new AuthorizationEvaluationRequest(
                        "actual-user",
                        company.permissionCode(),
                        company.resourceType(),
                        company.resourceReferenceId(),
                        new AuthorizationScope(ScopeType.FACILITY, "facility", null));
        var empty = new AuthorizationEvidence(null, List.of(), List.of(), Map.of());
        return new PairedAuthorizationEvidence(company, empty, facility, empty, bound);
    }

    @Test
    void eightOperationsHaveExactOwnerCodes() {
        String[] suffixes = {
            "author",
            "publish",
            "read-current",
            "read-history",
            "list-history",
            "withdraw",
            "reactivate",
            "transfer"
        };
        for (int i = 0; i < Operation.values().length; i++)
            assertThat(TopologyProtectedAuthorizationContract.permissionCode(Operation.values()[i]))
                    .isEqualTo("topology:station-revision:" + suffixes[i]);
        assertThatThrownBy(() -> TopologyProtectedAuthorizationContract.permissionCode(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void allNineDecisionPairsRequireTwoPermits() {
        Decision[][] expected = {
            {Decision.PERMIT, Decision.DENY, Decision.INDETERMINATE},
            {Decision.DENY, Decision.DENY, Decision.DENY},
            {Decision.INDETERMINATE, Decision.DENY, Decision.INDETERMINATE}
        };
        for (var a : Decision.values())
            for (var b : Decision.values())
                assertThat(TopologyProtectedAuthorizationContract.combine(a, b))
                        .isEqualTo(expected[a.ordinal()][b.ordinal()]);
        assertThatThrownBy(
                        () -> TopologyProtectedAuthorizationContract.combine(null, Decision.PERMIT))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void adapterUsesOneQualifiedPairAndExportsOnlyItsActualScalarIdentity() {
        var result = adapter.contribute(adapter.prepare(), request);
        assertThat(result.userId()).isEqualTo("actual-user");
        assertThat(result.sampledAt()).isEqualTo(at);
        assertThat(result.validUntil()).isEqualTo(at.plusSeconds(30));
        assertThat(result.backendPid()).isEqualTo(123);
        assertThat(result.transactionId()).isEqualTo(456);
        assertThat(result.combinedDecision()).isEqualTo(Decision.DENY);
        verify(identity, times(1)).qualifyPair(original, request);
        verify(reader, times(1)).loadQualifiedPair(pair, request);
        verify(identity, times(1)).requireCurrentPair(pair, request);
        verifyNoMoreInteractions(reader);
    }

    @Test
    void foreignMarkerAndNullCannotReachTheOwner() {
        assertThatThrownBy(() -> adapter.contribute(new Prepared() {}, request))
                .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> adapter.contribute(null, request))
                .isInstanceOf(RuntimeException.class);
        verify(identity, never()).qualifyPair(any(ProtectedOperationIdentityParticipant.Prepared.class), any());
    }

    @Test
    void anotherParticipantCannotConsumeOrReturnTheOpaqueEntry() {
        var p = adapter.prepare();
        var other = new ProtectedTopologyAuthorizationParticipant(identity, reader);
        assertThatThrownBy(() -> other.contribute(p, request)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> adapter.contribute(p, request))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void entryIsConsumedOnSuccessfulOrFailedAttempt() {
        var p = adapter.prepare();
        adapter.contribute(p, request);
        assertThatThrownBy(() -> adapter.contribute(p, request))
                .isInstanceOf(RuntimeException.class);
        var failed = adapter.prepare();
        when(reader.loadQualifiedPair(pair, request))
                .thenThrow(new IllegalStateException("synthetic"));
        assertThatThrownBy(() -> adapter.contribute(failed, request))
                .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> adapter.contribute(failed, request))
                .isInstanceOf(RuntimeException.class);
        verify(identity, times(2)).qualifyPair(original, request);
    }

    @Test
    void changedAuthenticationCannotBorrowPreparation() {
        var p = adapter.prepare();
        SecurityContextHolder.clearContext();
        assertThatThrownBy(() -> adapter.contribute(p, request))
                .isInstanceOf(RuntimeException.class);
        verify(identity, never()).qualifyPair(any(ProtectedOperationIdentityParticipant.Prepared.class), any());
    }

    @Test
    void sameAuthenticationOnAnotherThreadCannotBorrowPreparation() throws Exception {
        var p = adapter.prepare();
        try (var pool = Executors.newSingleThreadExecutor()) {
            pool.submit(
                            () -> {
                                SecurityContextHolder.getContext().setAuthentication(actual);
                                try {
                                    assertThatThrownBy(() -> adapter.contribute(p, request))
                                            .isInstanceOf(RuntimeException.class);
                                } finally {
                                    SecurityContextHolder.clearContext();
                                }
                            })
                    .get(2, TimeUnit.SECONDS);
        }
        assertThatThrownBy(() -> adapter.contribute(p, request))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void missingRequestConsumesTheAttempt() {
        var p = adapter.prepare();
        assertThatThrownBy(() -> adapter.contribute(p, null)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> adapter.contribute(p, request))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void graphContextMutationPreventsScalarExport() {
        var p = adapter.prepare();
        when(reader.loadQualifiedPair(pair, request))
                .thenAnswer(
                        i -> {
                            SecurityContextHolder.clearContext();
                            return graphs();
                        });
        assertThatThrownBy(() -> adapter.contribute(p, request))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void preparationCannotReturnWithMissingOrChangedActor() {
        SecurityContextHolder.clearContext();
        assertThatThrownBy(adapter::prepare).isInstanceOf(RuntimeException.class);
        SecurityContextHolder.getContext().setAuthentication(actual);
        when(identity.prepare())
                .thenAnswer(
                        i -> {
                            SecurityContextHolder.clearContext();
                            return original;
                        });
        assertThatThrownBy(adapter::prepare).isInstanceOf(RuntimeException.class);
    }

    @Test
    void scalarConstructorRefusesIncoherentDecisionCodeTimeOrIdentity() {
        var valid = adapter.contribute(adapter.prepare(), request);
        for (String code : List.of("*", "topology:station-revision:publish"))
            assertThatThrownBy(
                            () ->
                                    new Contribution(
                                            valid.bindingId(),
                                            valid.sessionId(),
                                            valid.userId(),
                                            request.operation(),
                                            code,
                                            "company",
                                            "facility",
                                            Decision.PERMIT,
                                            Decision.DENY,
                                            Decision.PERMIT,
                                            at,
                                            at.plusSeconds(1),
                                            1,
                                            1))
                    .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                new Contribution(
                                        "",
                                        "s",
                                        "u",
                                        Operation.AUTHOR,
                                        "topology:station-revision:author",
                                        "c",
                                        "f",
                                        Decision.PERMIT,
                                        Decision.PERMIT,
                                        Decision.PERMIT,
                                        at,
                                        at,
                                        1,
                                        1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(valid.toString())
                .doesNotContain("actual-user", valid.bindingId(), valid.sessionId());
    }

    @Test void integratedAdmissionDefersTheQualifiedPairUntilCollection() {
        var admitted=mock(ProtectedOperationIdentityParticipant.Admitted.class);
        when(identity.admit(original)).thenReturn(admitted);when(identity.qualifyPair(admitted,request)).thenReturn(pair);
        org.springframework.transaction.support.TransactionSynchronizationManager.setActualTransactionActive(true);
        org.springframework.transaction.support.TransactionSynchronizationManager.initSynchronization();
        try {
            var a=adapter.acquire(adapter.prepare(),request);
            verify(identity,never()).qualifyPair(admitted,request);verifyNoInteractions(reader);
            var c=adapter.contribute(a);assertThat(c.sampledAt()).isEqualTo(at);
            assertThatThrownBy(()->adapter.contribute(a)).isInstanceOf(RuntimeException.class);
        } finally {org.springframework.transaction.support.TransactionSynchronizationManager.clearSynchronization();org.springframework.transaction.support.TransactionSynchronizationManager.setActualTransactionActive(false);}
    }

}
