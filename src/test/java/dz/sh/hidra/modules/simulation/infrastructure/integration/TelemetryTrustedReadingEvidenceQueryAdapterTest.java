/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTrustedReadingEvidenceQueryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Verifies exact raw evidence transport and fail-closed identity lookup.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationTrustedReadingEvidencePort;
import dz.sh.hidra.modules.telemetry.application.contract.simulation.SimulationTrustedReadingContract;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelemetryTrustedReadingEvidenceQueryAdapterTest {

    private static final Instant SOURCE = Instant.parse("2026-10-01T10:00:00Z");
    private static final Instant TRUSTED = Instant.parse("2026-10-01T10:00:07Z");

    @Test
    void copiesEveryDistinctEvidenceFieldWithoutConversion() {
        var stub = new Stub();
        stub.result = Optional.of(fixture("evidence-1", new BigDecimal("-12.3400"), "raw-text", false, "HIGH", false));
        var actual = new TelemetryTrustedReadingEvidenceQueryAdapter(stub).resolve("  evidence-1  ").orElseThrow();
        var expected = new SimulationTrustedReadingEvidencePort.ReadingEvidence(
                "evidence-1", "original-reading", "point-2", new BigDecimal("-12.3400"), "raw-text", false,
                "raw-unit", "quality-code", "HIGH", SOURCE, TRUSTED, "assessment-3",
                "asset-type", "asset-4", "asset-code", "snapshot-5", "batch-6");
        assertEquals(expected, actual);
        assertEquals("evidence-1", stub.request);
        assertEquals(1, stub.calls);
    }

    @Test
    void invalidIdsDoNotCallOwner() {
        var stub = new Stub();
        var query = new TelemetryTrustedReadingEvidenceQueryAdapter(stub);
        for (String id : new String[]{null, "", " ", "\t\n"}) assertTrue(query.resolve(id).isEmpty());
        assertEquals(0, stub.calls);
    }

    @Test
    void absenceAndWrongIdentityRemainAbsent() {
        var stub = new Stub();
        var query = new TelemetryTrustedReadingEvidenceQueryAdapter(stub);
        assertTrue(query.resolve("evidence-1").isEmpty());
        stub.result = Optional.of(fixture("other", BigDecimal.ONE, null, null, "HIGH", false));
        assertTrue(query.resolve("evidence-1").isEmpty());
        assertEquals(2, stub.calls);
    }

    @Test
    void transportsEveryTrustLevelWithoutReclassification() {
        var stub = new Stub();
        var query = new TelemetryTrustedReadingEvidenceQueryAdapter(stub);
        for (String trust : new String[]{"UNTRUSTED", "LOW", "MEDIUM", "HIGH", "CERTIFIED"}) {
            stub.result = Optional.of(fixture("evidence-1", BigDecimal.ZERO, null, null, trust, false));
            assertEquals(trust, query.resolve("evidence-1").orElseThrow().trustLevel());
        }
    }

    @Test
    void preservesRawValueAlternativesAndMissingOptionalMetadata() {
        var stub = new Stub();
        var query = new TelemetryTrustedReadingEvidenceQueryAdapter(stub);
        for (BigDecimal numeric : new BigDecimal[]{null, BigDecimal.ZERO, new BigDecimal("-1.25")}) {
            for (String text : new String[]{null, "text-value"}) {
                for (Boolean flag : new Boolean[]{null, false, true}) {
                    stub.result = Optional.of(fixture("evidence-1", numeric, text, flag, "HIGH", true));
                    var actual = query.resolve("evidence-1").orElseThrow();
                    assertEquals(new SimulationTrustedReadingEvidencePort.ReadingEvidence("evidence-1", "original-reading", "point-2",
                            numeric, text, flag, null, "quality-code", "HIGH", SOURCE, TRUSTED,
                            "assessment-3", null, null, null, null, null), actual);
                }
            }
        }
    }

    @Test
    void propagatesFailuresAndRejectsNullDependency() {
        assertThrows(NullPointerException.class, () -> new TelemetryTrustedReadingEvidenceQueryAdapter(null));
        var stub = new Stub();
        stub.failure = new IllegalStateException("owner unavailable");
        var thrown = assertThrows(IllegalStateException.class,
                () -> new TelemetryTrustedReadingEvidenceQueryAdapter(stub).resolve("evidence-1"));
        assertSame(stub.failure, thrown);
    }

    @Test
    void replacementEvidenceDoesNotModifyEarlierProjection() {
        var stub = new Stub();
        var query = new TelemetryTrustedReadingEvidenceQueryAdapter(stub);
        stub.result = Optional.of(fixture("evidence-1", BigDecimal.ONE, null, null, "HIGH", false));
        var original = query.resolve("evidence-1").orElseThrow();
        stub.result = Optional.of(fixture("evidence-1", BigDecimal.TEN, null, null, "HIGH", false));
        var replacement = query.resolve("evidence-1").orElseThrow();
        assertEquals(BigDecimal.ONE, original.numericValue());
        assertEquals(BigDecimal.TEN, replacement.numericValue());
        assertNotEquals(original, replacement);
    }

    private static SimulationTrustedReadingContract.ReadingEvidence fixture(String id, BigDecimal numeric, String text,
            Boolean flag, String trust, boolean missingMetadata) {
        return new SimulationTrustedReadingContract.ReadingEvidence(id, "original-reading", "point-2", numeric, text, flag,
                missingMetadata ? null : "raw-unit", "quality-code", trust, SOURCE, TRUSTED,
                "assessment-3", missingMetadata ? null : "asset-type", missingMetadata ? null : "asset-4",
                missingMetadata ? null : "asset-code", missingMetadata ? null : "snapshot-5",
                missingMetadata ? null : "batch-6");
    }

    private static final class Stub implements SimulationTrustedReadingContract {
        private Optional<SimulationTrustedReadingContract.ReadingEvidence> result = Optional.empty();
        private RuntimeException failure;
        private int calls;
        private String request;

        @Override
        public Optional<SimulationTrustedReadingContract.ReadingEvidence> resolve(String id) {
            calls++;
            request = id;
            if (failure != null) throw failure;
            return result;
        }
    }
}
