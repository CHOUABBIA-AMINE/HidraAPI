/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyScopeEvidenceQueryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Verifies scope eligibility transport and missing physical source boundaries.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationTopologyScopeEvidencePort;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationTopologyScopeEvidencePort.ScopeEvidence;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.topology.application.contract.simulation.SimulationTopologyScopeContract;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopologyScopeEvidenceQueryAdapterTest {
    @Test
    void preservesAllLegalFlagCombinationsAndRequestEchoWithoutPhysicalInput() {
        var stub = new Stub();
        var query = new TopologyScopeEvidenceQueryAdapter(stub);
        for (boolean[] flags : new boolean[][]{{false, false, false}, {true, false, false},
                {true, true, false}, {true, true, true}}) {
            stub.result = new SimulationTopologyScopeContract.ScopeResolution(flags[0], flags[1], flags[2]);
            var actual = query.resolve(" PIPELINE ", " scope-1 ").orElseThrow();
            assertEquals(new ScopeEvidence("PIPELINE", "scope-1", flags[0], flags[1], flags[2]), actual);
            assertEquals(SimulationTopologyScopeEvidencePort.PhysicalInputStatus.MISSING_VERSIONED_TOPOLOGY_SOURCE,
                    actual.physicalInputStatus());
            assertEquals("PIPELINE", stub.type);
            assertEquals("scope-1", stub.id);
        }
        assertEquals(4, stub.calls);
    }

    @Test
    void rejectsEveryIncoherentFlagCombination() {
        var stub = new Stub();
        var query = new TopologyScopeEvidenceQueryAdapter(stub);
        for (boolean[] flags : new boolean[][]{{false, false, true}, {false, true, false},
                {false, true, true}, {true, false, true}}) {
            stub.result = new SimulationTopologyScopeContract.ScopeResolution(flags[0], flags[1], flags[2]);
            assertThrows(InvalidSimulationValueException.class, () -> query.resolve("PIPELINE", "scope-1"));
            assertThrows(InvalidSimulationValueException.class,
                    () -> new ScopeEvidence("PIPELINE", "scope-1", flags[0], flags[1], flags[2]));
        }
    }

    @Test
    void projectionRequiresBothIdsAndNormalizesItsRequestEcho() {
        for (String invalid : new String[]{null, "", " ", "\t\n"}) {
            assertThrows(InvalidSimulationValueException.class,
                    () -> new ScopeEvidence(invalid, "scope-1", false, false, false));
            assertThrows(InvalidSimulationValueException.class,
                    () -> new ScopeEvidence("PIPELINE", invalid, false, false, false));
        }
        assertEquals(new ScopeEvidence("PIPELINE", "scope-1", true, true, false),
                new ScopeEvidence(" PIPELINE ", " scope-1 ", true, true, false));
    }

    @Test
    void invalidRequestsDoNotCallOwner() {
        var stub = new Stub();
        var query = new TopologyScopeEvidenceQueryAdapter(stub);
        for (String invalid : new String[]{null, "", " ", "\t\n"}) {
            assertTrue(query.resolve(invalid, "scope-1").isEmpty());
            assertTrue(query.resolve("PIPELINE", invalid).isEmpty());
        }
        assertEquals(0, stub.calls);
    }

    @Test
    void unsupportedTypesArePreservedAsOwnerResultsRatherThanHidden() {
        var stub = new Stub();
        var query = new TopologyScopeEvidenceQueryAdapter(stub);
        for (String type : new String[]{"SEGMENT_GROUP", "FACILITY_NETWORK", "UNKNOWN_SCOPE"}) {
            var actual = query.resolve(type, "scope-1").orElseThrow();
            assertEquals(new ScopeEvidence(type, "scope-1", false, false, false), actual);
            assertEquals(type, stub.type);
        }
    }

    @Test
    void rejectsNullOwnerResultAndPropagatesOwnerFailure() {
        var stub = new Stub();
        stub.result = null;
        var query = new TopologyScopeEvidenceQueryAdapter(stub);
        assertThrows(NullPointerException.class, () -> query.resolve("PIPELINE", "scope-1"));
        stub.failure = new IllegalStateException("owner unavailable");
        var failure = assertThrows(IllegalStateException.class, () -> query.resolve("PIPELINE", "scope-1"));
        assertSame(stub.failure, failure);
    }

    @Test
    void rejectsNullOwnerDependency() {
        assertThrows(NullPointerException.class, () -> new TopologyScopeEvidenceQueryAdapter(null));
    }

    @Test
    void replacementPreservesEarlierProjectionAndEquality() {
        var stub = new Stub();
        stub.result = SimulationTopologyScopeContract.ScopeResolution.resolved(true);
        var query = new TopologyScopeEvidenceQueryAdapter(stub);
        var original = query.resolve("PIPELINE", "scope-1").orElseThrow();
        var equal = query.resolve("PIPELINE", "scope-1").orElseThrow();
        assertEquals(original, equal);
        assertEquals(original.hashCode(), equal.hashCode());
        stub.result = SimulationTopologyScopeContract.ScopeResolution.missing();
        var replacement = query.resolve("PIPELINE", "scope-1").orElseThrow();
        assertTrue(original.exists());
        assertTrue(original.eligible());
        assertFalse(replacement.exists());
        assertNotEquals(original, replacement);
    }

    private static final class Stub implements SimulationTopologyScopeContract {
        private ScopeResolution result = ScopeResolution.unsupported();
        private RuntimeException failure;
        private String type;
        private String id;
        private int calls;

        @Override
        public ScopeResolution resolve(String scopeType, String scopeId) {
            calls++;
            type = scopeType;
            id = scopeId;
            if (failure != null) throw failure;
            return result;
        }
    }
}
