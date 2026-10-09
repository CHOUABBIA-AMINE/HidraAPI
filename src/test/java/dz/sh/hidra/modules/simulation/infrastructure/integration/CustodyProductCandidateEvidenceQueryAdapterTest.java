/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyProductCandidateEvidenceQueryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Verifies catalogue evidence transport without Simulation product approval.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationProductCandidateEvidencePort;
import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationProductCandidateContract;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustodyProductCandidateEvidenceQueryAdapterTest {
    private static final Instant CREATED = Instant.parse("2026-10-01T08:00:00Z");
    private static final Instant UPDATED = Instant.parse("2026-10-02T09:00:00Z");

    @Test
    void mapsEveryDistinctFieldAndNormalizesOnlyRequestedIdentity() {
        var stub = new Stub();
        stub.result = Optional.of(fixture("candidate-1", " unknown-catalog ", " raw-code ", true, CREATED, UPDATED));
        var actual = new CustodyProductCandidateEvidenceQueryAdapter(stub).resolve("  candidate-1  ").orElseThrow();
        assertEquals(new SimulationProductCandidateEvidencePort.CandidateEvidence("candidate-1", " unknown-catalog ", " raw-code ", true, CREATED, UPDATED), actual);
        assertEquals("candidate-1", stub.request);
        assertEquals(1, stub.calls);
    }

    @Test
    void invalidRequestsDoNotCallOwner() {
        var stub = new Stub();
        var query = new CustodyProductCandidateEvidenceQueryAdapter(stub);
        for (String id : new String[]{null, "", " ", "\t\n"}) assertTrue(query.resolve(id).isEmpty());
        assertEquals(0, stub.calls);
    }

    @Test
    void missingAndMismatchedIdentitiesRemainAbsent() {
        var stub = new Stub();
        var query = new CustodyProductCandidateEvidenceQueryAdapter(stub);
        assertTrue(query.resolve("candidate-1").isEmpty());
        for (String id : new String[]{"other", null, " candidate-1 "}) {
            stub.result = Optional.of(fixture(id, "catalog", "code", true, CREATED, UPDATED));
            assertTrue(query.resolve("candidate-1").isEmpty());
        }
        assertEquals(4, stub.calls);
    }

    @Test
    void preservesActivityAndUnknownCatalogueWithoutGasClassification() {
        var stub = new Stub();
        var query = new CustodyProductCandidateEvidenceQueryAdapter(stub);
        for (boolean active : new boolean[]{false, true}) {
            stub.result = Optional.of(fixture("candidate-1", "unknown-catalog", "GAS_LABEL_IS_NOT_APPROVAL", active, CREATED, UPDATED));
            var actual = query.resolve("candidate-1").orElseThrow();
            assertEquals(new SimulationProductCandidateEvidencePort.CandidateEvidence("candidate-1", "unknown-catalog", "GAS_LABEL_IS_NOT_APPROVAL", active, CREATED, UPDATED), actual);
            assertEquals(SimulationProductCandidateEvidencePort.EligibilityStatus.UNASSESSED, actual.simulationEligibility());
            assertEquals(SimulationProductCandidateEvidencePort.PhysicalInputStatus.MISSING_VERSIONED_FLUID_SOURCE, actual.physicalInputStatus());
        }
    }

    @Test
    void missingRawMetadataNeverBecomesGeneratedDefaults() {
        var stub = new Stub();
        stub.result = Optional.of(fixture("candidate-1", null, null, false, null, null));
        var actual = new CustodyProductCandidateEvidenceQueryAdapter(stub).resolve("candidate-1").orElseThrow();
        assertEquals(new SimulationProductCandidateEvidencePort.CandidateEvidence("candidate-1", null, null, false, null, null), actual);
    }

    @Test
    void propagatesLookupFailuresAndRejectsNullDependency() {
        assertThrows(NullPointerException.class, () -> new CustodyProductCandidateEvidenceQueryAdapter(null));
        var stub = new Stub();
        stub.failure = new IllegalStateException("owner unavailable");
        var failure = assertThrows(IllegalStateException.class, () -> new CustodyProductCandidateEvidenceQueryAdapter(stub).resolve("candidate-1"));
        assertSame(stub.failure, failure);
    }

    @Test
    void replacementAndEqualEvidenceLeaveOriginalUnchanged() {
        var stub = new Stub();
        var query = new CustodyProductCandidateEvidenceQueryAdapter(stub);
        stub.result = Optional.of(fixture("candidate-1", "catalog", "first", false, CREATED, UPDATED));
        var original = query.resolve("candidate-1").orElseThrow();
        var equal = query.resolve("candidate-1").orElseThrow();
        assertEquals(original, equal);
        assertEquals(original.hashCode(), equal.hashCode());
        stub.result = Optional.of(fixture("candidate-1", "catalog", "second", true, CREATED, null));
        var replacement = query.resolve("candidate-1").orElseThrow();
        assertEquals("first", original.code());
        assertFalse(original.active());
        assertEquals(UPDATED, original.updatedAt());
        assertNotEquals(original, replacement);
    }

    private static SimulationProductCandidateContract.Candidate fixture(String id, String catalog, String code, boolean active,
            Instant created, Instant updated) {
        return new SimulationProductCandidateContract.Candidate(id, catalog, code, active, created, updated);
    }

    private static final class Stub implements SimulationProductCandidateContract {
        private Optional<Candidate> result = Optional.empty();
        private RuntimeException failure;
        private int calls;
        private String request;

        @Override
        public Optional<Candidate> resolve(String id) {
            calls++;
            request = id;
            if (failure != null) throw failure;
            return result;
        }
    }
}
