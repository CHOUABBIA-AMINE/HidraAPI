/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyScopedAccessQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Verifies trusted actor binding and paired scoped Topology permission decisions.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import static org.junit.jupiter.api.Assertions.*;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyCurrentActorContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Operation;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Request;
import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.PermissionStatus;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class TopologyScopedAccessQueryServiceTest {

    private static final Instant NOW = Instant.parse("2026-10-11T12:00:00Z");
    private static final Request REQUEST = new Request(Operation.PUBLISH, "company-a", "facility-a");
    private static final PermissionDecisionDto PERMIT = result(true, AuthorizationDecisionValue.PERMIT);

    @ParameterizedTest
    @MethodSource("operations")
    void everyOperationUsesOwnerValidPermissionAndExactPairedScopes(Operation operation, String code) {
        var queries = new ArrayList<EvaluatePermissionQuery>();
        var actorCalls = new AtomicInteger();
        var service = new TopologyScopedAccessQueryService(() -> {
            actorCalls.incrementAndGet(); return actor("trusted-user");
        }, query -> { queries.add(query); return PERMIT; });
        assertTrue(service.permitted(new Request(operation, " company-a ", " facility-a ")));
        assertEquals(1, actorCalls.get());
        assertEquals(2, queries.size());
        for (var query : queries) {
            assertEquals("trusted-user", query.userId());
            assertEquals(code, query.permissionCode());
            assertEquals("STATION_GRAPH_REVISION", query.resourceType());
            assertEquals("facility-a", query.resourceReferenceId());
            assertNotNull(query.scope());
            assertNotEquals(ScopeType.GLOBAL, query.scope().scopeType());
            assertNull(query.scope().scopeCodeSnapshot());
        }
        assertEquals(ScopeType.CUSTOM, queries.get(0).scope().scopeType());
        assertEquals("topology-company:company-a", queries.get(0).scope().scopeReferenceId());
        assertEquals(ScopeType.FACILITY, queries.get(1).scope().scopeType());
        assertEquals("facility-a", queries.get(1).scope().scopeReferenceId());
        String action = code.substring(code.lastIndexOf(':') + 1);
        assertEquals(code, new Permission("synthetic-permission", code, null, null, null, null,
                "topology", "STATION_GRAPH_REVISION", action, false, PermissionStatus.ACTIVE, NOW, NOW).code());
    }

    static Stream<Arguments> operations() {
        return Stream.of(Arguments.of(Operation.AUTHOR, "topology:station-revision:author"),
                Arguments.of(Operation.PUBLISH, "topology:station-revision:publish"),
                Arguments.of(Operation.READ_CURRENT, "topology:station-revision:read-current"),
                Arguments.of(Operation.READ_HISTORY, "topology:station-revision:read-history"),
                Arguments.of(Operation.LIST_HISTORY, "topology:station-revision:list-history"),
                Arguments.of(Operation.WITHDRAW, "topology:station-revision:withdraw"),
                Arguments.of(Operation.REACTIVATE, "topology:station-revision:reactivate"),
                Arguments.of(Operation.TRANSFER, "topology:station-revision:transfer"));
    }

    @Test
    void nullRequestAndMissingActorNeverEvaluatePermissions() {
        var calls = new AtomicInteger();
        var service = new TopologyScopedAccessQueryService(() -> {
            calls.incrementAndGet(); return Optional.empty();
        }, query -> { fail("Evaluator must not be called"); return null; });
        assertFalse(service.permitted(null));
        assertEquals(0, calls.get());
        assertFalse(service.permitted(REQUEST));
        assertEquals(1, calls.get());
    }

    @ParameterizedTest
    @MethodSource("nonPermits")
    void companyNonPermitShortCircuitsFacility(PermissionDecisionDto decision) {
        var queries = new ArrayList<EvaluatePermissionQuery>();
        var service = new TopologyScopedAccessQueryService(() -> actor("trusted-user"), query -> {
            queries.add(query); return decision;
        });
        assertFalse(service.permitted(REQUEST));
        assertEquals(1, queries.size());
        assertEquals(ScopeType.CUSTOM, queries.getFirst().scope().scopeType());
    }

    @ParameterizedTest
    @MethodSource("nonPermits")
    void facilityNonPermitCannotBeOverriddenByCompanyPermit(PermissionDecisionDto decision) {
        var calls = new AtomicInteger();
        var service = new TopologyScopedAccessQueryService(() -> actor("trusted-user"), query ->
                calls.incrementAndGet() == 1 ? PERMIT : decision);
        assertFalse(service.permitted(REQUEST));
        assertEquals(2, calls.get());
    }

    static Stream<Arguments> nonPermits() {
        return Stream.of(Arguments.of((Object) null),
                Arguments.of(result(false, AuthorizationDecisionValue.PERMIT)),
                Arguments.of(result(false, AuthorizationDecisionValue.DENY)),
                Arguments.of(result(false, AuthorizationDecisionValue.INDETERMINATE)),
                Arguments.of(result(true, AuthorizationDecisionValue.DENY)),
                Arguments.of(result(true, AuthorizationDecisionValue.INDETERMINATE)),
                Arguments.of(result(true, null)), Arguments.of(result(false, null)));
    }

    @Test
    void eachInvocationCapturesOneFreshActorForBothQueriesWithoutCaching() {
        var calls = new AtomicInteger();
        var queries = new ArrayList<EvaluatePermissionQuery>();
        var service = new TopologyScopedAccessQueryService(() -> actor("user-" + calls.incrementAndGet()),
                query -> { queries.add(query); return PERMIT; });
        assertTrue(service.permitted(REQUEST));
        assertTrue(service.permitted(new Request(Operation.READ_HISTORY, "company-b", "facility-b")));
        assertEquals(2, calls.get());
        assertEquals(List.of("user-1", "user-1", "user-2", "user-2"),
                queries.stream().map(EvaluatePermissionQuery::userId).toList());
        assertEquals("topology-company:company-b", queries.get(2).scope().scopeReferenceId());
        assertEquals("facility-b", queries.get(3).scope().scopeReferenceId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"actor", "company", "facility"})
    void dependencyFailurePropagatesWithoutAResult(String phase) {
        var failure = new IllegalStateException("synthetic " + phase + " failure");
        var service = new TopologyScopedAccessQueryService(() -> {
            if (phase.equals("actor")) { throw failure; }
            return actor("trusted-user");
        }, query -> {
            if ((phase.equals("company") && query.scope().scopeType() == ScopeType.CUSTOM)
                    || (phase.equals("facility") && query.scope().scopeType() == ScopeType.FACILITY)) {
                throw failure;
            }
            return PERMIT;
        });
        assertSame(failure, assertThrows(IllegalStateException.class, () -> service.permitted(REQUEST)));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t\n"})
    void malformedReferencesNeverBecomeDefaultScopes(String value) {
        assertThrows(IllegalArgumentException.class, () -> new Request(Operation.PUBLISH, value, "facility-a"));
        assertThrows(IllegalArgumentException.class, () -> new Request(Operation.PUBLISH, "company-a", value));
    }

    @Test
    void publicRequestContainsOnlyOperationAndNeutralReferences() {
        assertThrows(IllegalArgumentException.class, () -> new Request(null, "company-a", "facility-a"));
        assertEquals(List.of("operation", "companyPartyId", "facilityId"),
                Arrays.stream(Request.class.getRecordComponents()).map(c -> c.getName()).toList());
        assertEquals(List.of(Operation.class, String.class, String.class),
                Arrays.stream(Request.class.getRecordComponents()).map(c -> c.getType()).toList());
        assertEquals(8, Operation.values().length);
        assertEquals(1, TopologyScopedAccessContract.class.getDeclaredMethods().length);
        assertEquals(boolean.class, TopologyScopedAccessContract.class.getDeclaredMethods()[0].getReturnType());
        assertEquals("company-a", new Request(Operation.PUBLISH, " company-a ", " facility-a ").companyPartyId());
        assertThrows(NullPointerException.class, () -> new TopologyScopedAccessQueryService(null, query -> PERMIT));
        assertThrows(NullPointerException.class, () -> new TopologyScopedAccessQueryService(Optional::empty, null));
    }

    private static Optional<TopologyCurrentActorContract.Actor> actor(String id) {
        return Optional.of(new TopologyCurrentActorContract.Actor(id));
    }

    private static PermissionDecisionDto result(boolean permitted, AuthorizationDecisionValue decision) {
        return new PermissionDecisionDto(permitted, decision, "synthetic-reason", "Synthetic decision", NOW);
    }
}
