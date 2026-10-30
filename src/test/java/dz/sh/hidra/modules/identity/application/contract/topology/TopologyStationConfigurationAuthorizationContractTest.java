/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyStationConfigurationAuthorizationContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.topology
 *
 * @Description : Verifies descriptor invariants without claiming native Identity or database authority.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.topology;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyStationConfigurationAuthorizationContract.*;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TopologyStationConfigurationAuthorizationContractTest {
    private static final Instant AT = Instant.parse("2026-10-30T00:00:00Z");
    private static Request request(Operation operation, List<String> units) {
        return new Request(operation, "company", "facility",
            operation == Operation.SAVE || operation == Operation.READ_REVISION ? "revision" : null, units);
    }
    private static Contribution contribution(Request request, List<ResourceDecision> decisions) {
        return new Contribution(request, decisions, "binding", "session", "user", AT, AT.plusSeconds(1), 1, 1);
    }
    private static List<ResourceDecision> permits(Request request) {
        return request.requirements().stream().map(r -> new ResourceDecision(r, Decision.PERMIT)).toList();
    }

    @Test void exactOperationPermissionsRemainSeparateFromPhysicalUnitRead() {
        var codes = Map.of(Operation.SAVE, "save", Operation.READ_CURRENT, "read",
            Operation.READ_REVISION, "read", Operation.LIST_HISTORY, "history");
        for (var operation : Operation.values()) {
            assertEquals("topology:installed-configuration:" + codes.get(operation),
                TopologyStationConfigurationAuthorizationContract.permissionCode(operation, "STATION_INSTALLED_CONFIGURATION"));
            assertEquals("topology:installed-unit:read",
                TopologyStationConfigurationAuthorizationContract.permissionCode(operation, "INSTALLED_UNIT"));
            assertThrows(IllegalArgumentException.class,
                () -> TopologyStationConfigurationAuthorizationContract.permissionCode(operation, "STATION_GRAPH_REVISION"));
            assertThrows(IllegalArgumentException.class,
                () -> TopologyStationConfigurationAuthorizationContract.permissionCode(operation, "INSTALLED_UNIT_INSTALLATION"));
        }
    }
    @Test void revisionIdentityIsRequiredOnlyForSaveAndExactRevisionRead() {
        for (var operation : Operation.values()) {
            assertNotNull(request(operation, List.of()));
            if (operation == Operation.SAVE || operation == Operation.READ_REVISION)
                assertThrows(IllegalArgumentException.class,
                    () -> new Request(operation, "company", "facility", null, List.of()));
            else assertThrows(IllegalArgumentException.class,
                () -> new Request(operation, "company", "facility", "revision", List.of()));
        }
        assertThrows(NullPointerException.class, () -> new Request(null, "company", "facility", null, List.of()));
    }
    @Test void everyOperationRequiresBothCompanyAndFacilityConfigurationDecisions() {
        for (var operation : Operation.values()) {
            var requirements = request(operation, List.of()).requirements();
            assertEquals(List.of(
                new Requirement("STATION_INSTALLED_CONFIGURATION", "facility", Scope.COMPANY, "company"),
                new Requirement("STATION_INSTALLED_CONFIGURATION", "facility", Scope.FACILITY, "facility")), requirements);
        }
        var same = new Request(Operation.READ_CURRENT, "same", "same", null, List.of("same"));
        assertEquals(3, same.requirements().size()); // Identical text never collapses scopes or resource types.
    }
    @Test void unitReferencesAreCanonicalUniqueAndDoNotAliasConfigurationResources() {
        var a = request(Operation.SAVE, List.of("z", "facility", "a"));
        var b = request(Operation.SAVE, List.of("a", "z", "facility"));
        assertEquals(a, b);
        assertEquals(a.requirements(), b.requirements());
        assertEquals(List.of("a", "facility", "z"), a.referencedUnitIds());
        assertEquals(5, a.requirements().size());
        assertEquals(new Requirement("INSTALLED_UNIT", "a", Scope.COMPANY, "company"), a.requirements().get(0));
        assertThrows(IllegalArgumentException.class, () -> request(Operation.SAVE, List.of("same", "same")));
        assertThrows(IllegalArgumentException.class,
            () -> new Requirement("INSTALLED_UNIT", "unit", Scope.FACILITY, "facility"));
        assertThrows(IllegalArgumentException.class,
            () -> new Requirement("STATION_GRAPH_REVISION", "revision", Scope.COMPANY, "company"));
    }
    @Test void maximumReferencedUnitsAreAcceptedAndOneMoreIsRejected() {
        var units = new ArrayList<String>();
        for (int i = 0; i < 128; i++) units.add("unit-" + i);
        assertEquals(130, request(Operation.SAVE, units).requirements().size());
        units.add("unit-128");
        assertThrows(IllegalArgumentException.class, () -> request(Operation.SAVE, units));
        assertThrows(IllegalArgumentException.class, () -> request(Operation.SAVE, null));
    }
    @Test void malformedReferencesAreRejectedWithoutAuthorityTargetNormalization() {
        var invalid = Arrays.asList(null, "", " ", " padded", "padded ", "x".repeat(81),
            "line\nbreak", "nul" + (char) 0, "bad" + (char) 0xd800);
        for (String bad : invalid) {
            assertThrows(IllegalArgumentException.class,
                () -> new Request(Operation.SAVE, bad, "facility", "revision", List.of()));
            assertThrows(IllegalArgumentException.class,
                () -> new Request(Operation.SAVE, "company", bad, "revision", List.of()));
            assertThrows(IllegalArgumentException.class,
                () -> new Request(Operation.SAVE, "company", "facility", bad, List.of()));
            assertThrows(IllegalArgumentException.class,
                () -> request(Operation.SAVE, Collections.singletonList(bad)));
            assertThrows(IllegalArgumentException.class,
                () -> new Requirement("INSTALLED_UNIT", "unit", Scope.COMPANY, bad));
        }
        assertEquals(1, request(Operation.SAVE, List.of("𐀀".repeat(80))).referencedUnitIds().size());
    }
    @Test void descriptorAndContributionListsCannotBeChangedAfterConstruction() {
        var units = new ArrayList<>(List.of("unit"));
        var request = request(Operation.SAVE, units);
        units.clear();
        assertEquals(List.of("unit"), request.referencedUnitIds());
        assertThrows(UnsupportedOperationException.class, () -> request.referencedUnitIds().clear());
        assertThrows(UnsupportedOperationException.class, () -> request.requirements().clear());
        var decisions = new ArrayList<>(permits(request));
        var result = contribution(request, decisions);
        decisions.clear();
        assertEquals(3, result.decisions().size());
        assertThrows(UnsupportedOperationException.class, () -> result.decisions().clear());
    }
    @Test void exactDecisionMembershipRejectsMissingDuplicateExtraAndReorderedResources() {
        var request = request(Operation.SAVE, List.of("unit"));
        var exact = permits(request);
        assertEquals(Decision.PERMIT, contribution(request, exact).overallDecision());
        assertThrows(IllegalArgumentException.class, () -> contribution(request, exact.subList(0, 2)));
        var reversed = new ArrayList<>(exact); Collections.reverse(reversed);
        assertThrows(IllegalArgumentException.class, () -> contribution(request, reversed));
        var duplicate = new ArrayList<>(exact); duplicate.set(1, duplicate.get(0));
        assertThrows(IllegalArgumentException.class, () -> contribution(request, duplicate));
        var extra = new ArrayList<>(exact); extra.add(exact.get(0));
        assertThrows(IllegalArgumentException.class, () -> contribution(request, extra));
        var foreign = permits(new Request(Operation.SAVE, "other-company", "facility", "revision", List.of("unit")));
        assertThrows(IllegalArgumentException.class, () -> contribution(request, foreign));
    }
    @Test void denyDominatesIndeterminateRegardlessOfResourceOrder() {
        var request = request(Operation.SAVE, List.of("unit"));
        for (int i = 0; i < request.requirements().size(); i++) {
            var decisions = new ArrayList<>(permits(request));
            decisions.set(i, new ResourceDecision(request.requirements().get(i), Decision.INDETERMINATE));
            assertEquals(Decision.INDETERMINATE, contribution(request, decisions).overallDecision());
            int other = (i + 1) % decisions.size();
            decisions.set(other, new ResourceDecision(request.requirements().get(other), Decision.DENY));
            assertEquals(Decision.DENY, contribution(request, decisions).overallDecision());
        }
    }
    @Test void physicalAndTemporalContributionBoundsCannotBeInventedOrEmpty() {
        var request = request(Operation.READ_CURRENT, List.of());
        var decisions = permits(request);
        assertThrows(IllegalArgumentException.class,
            () -> new Contribution(request, decisions, "b", "s", "u", AT, AT, 1, 1));
        assertThrows(IllegalArgumentException.class,
            () -> new Contribution(request, decisions, "b", "s", "u", AT, AT.minusSeconds(1), 1, 1));
        for (int pid : new int[]{0, -1})
            assertThrows(IllegalArgumentException.class,
                () -> new Contribution(request, decisions, "b", "s", "u", AT, AT.plusSeconds(1), pid, 1));
        for (long xid : new long[]{0, -1})
            assertThrows(IllegalArgumentException.class,
                () -> new Contribution(request, decisions, "b", "s", "u", AT, AT.plusSeconds(1), 1, xid));
        assertThrows(IllegalArgumentException.class,
            () -> new Contribution(request, decisions, " ", "s", "u", AT, AT.plusSeconds(1), 1, 1));
        assertThrows(IllegalArgumentException.class,
            () -> new Contribution(request, decisions, "b", " ", "u", AT, AT.plusSeconds(1), 1, 1));
        assertThrows(IllegalArgumentException.class,
            () -> new Contribution(request, decisions, "b", "s", " ", AT, AT.plusSeconds(1), 1, 1));
    }
    @Test void constructibleScalarsNeverImplementOpaqueNativeHandleTypes() {
        var request = request(Operation.READ_CURRENT, List.of("unit"));
        var result = contribution(request, permits(request));
        for (Object value : List.of(request, result, request.requirements().get(0))) {
            assertFalse(Prepared.class.isInstance(value));
            assertFalse(Admission.class.isInstance(value));
        }
        assertTrue(TopologyStationConfigurationAuthorizationContract.class.isInterface());
    }
    @Test void defaultDiagnosticsDoNotExposeActorOrRequestedResourceReferences() {
        var request = request(Operation.SAVE, List.of("private-unit-value"));
        var result = contribution(request, permits(request));
        for (String text : List.of(request.toString(), result.toString(), request.requirements().get(0).toString()))
            for (String secret : List.of("private-unit-value", "company", "facility", "revision", "binding", "session", "user"))
                assertFalse(text.contains(secret), text);
    }
}
