/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationTrustedReadingQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Application Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Verifies exact raw evidence transport and fail-closed identity lookup.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.contract.simulation.SimulationTrustedReadingContract;
import dz.sh.hidra.modules.telemetry.application.port.out.TrustedTelemetryReadingRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TrustedTelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.value.TrustLevel;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTrustedReadingQueryServiceTest {

    private static final Instant SOURCE = Instant.parse("2026-10-01T10:00:00Z");
    private static final Instant TRUSTED = Instant.parse("2026-10-01T10:00:07Z");

    @Test
    void copiesEveryDistinctEvidenceFieldWithoutConversion() {
        var stub = new Stub();
        stub.result = Optional.of(fixture("evidence-1", new BigDecimal("-12.3400"), "raw-text", false, TrustLevel.HIGH, false));
        var actual = new SimulationTrustedReadingQueryService(stub).resolve("  evidence-1  ").orElseThrow();
        var expected = new SimulationTrustedReadingContract.ReadingEvidence(
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
        var query = new SimulationTrustedReadingQueryService(stub);
        for (String id : new String[]{null, "", " ", "\t\n"}) assertTrue(query.resolve(id).isEmpty());
        assertEquals(0, stub.calls);
    }

    @Test
    void absenceAndWrongIdentityRemainAbsent() {
        var stub = new Stub();
        var query = new SimulationTrustedReadingQueryService(stub);
        assertTrue(query.resolve("evidence-1").isEmpty());
        stub.result = Optional.of(fixture("other", BigDecimal.ONE, null, null, TrustLevel.HIGH, false));
        assertTrue(query.resolve("evidence-1").isEmpty());
        assertEquals(2, stub.calls);
    }

    @Test
    void transportsEveryTrustLevelWithoutReclassification() {
        var stub = new Stub();
        var query = new SimulationTrustedReadingQueryService(stub);
        for (TrustLevel trust : TrustLevel.values()) {
            stub.result = Optional.of(fixture("evidence-1", BigDecimal.ZERO, null, null, trust, false));
            assertEquals(trust.name(), query.resolve("evidence-1").orElseThrow().trustLevel());
        }
    }

    @Test
    void preservesRawValueAlternativesAndMissingOptionalMetadata() {
        var stub = new Stub();
        var query = new SimulationTrustedReadingQueryService(stub);
        for (BigDecimal numeric : new BigDecimal[]{null, BigDecimal.ZERO, new BigDecimal("-1.25")}) {
            for (String text : new String[]{null, "text-value"}) {
                for (Boolean flag : new Boolean[]{null, false, true}) {
                    stub.result = Optional.of(fixture("evidence-1", numeric, text, flag, TrustLevel.HIGH, true));
                    var actual = query.resolve("evidence-1").orElseThrow();
                    assertEquals(new SimulationTrustedReadingContract.ReadingEvidence("evidence-1", "original-reading", "point-2",
                            numeric, text, flag, null, "quality-code", "HIGH", SOURCE, TRUSTED,
                            "assessment-3", null, null, null, null, null), actual);
                }
            }
        }
    }

    @Test
    void propagatesFailuresAndRejectsNullDependency() {
        assertThrows(NullPointerException.class, () -> new SimulationTrustedReadingQueryService(null));
        var stub = new Stub();
        stub.failure = new IllegalStateException("owner unavailable");
        var thrown = assertThrows(IllegalStateException.class,
                () -> new SimulationTrustedReadingQueryService(stub).resolve("evidence-1"));
        assertSame(stub.failure, thrown);
    }

    @Test
    void replacementEvidenceDoesNotModifyEarlierProjection() {
        var stub = new Stub();
        var query = new SimulationTrustedReadingQueryService(stub);
        stub.result = Optional.of(fixture("evidence-1", BigDecimal.ONE, null, null, TrustLevel.HIGH, false));
        var original = query.resolve("evidence-1").orElseThrow();
        stub.result = Optional.of(fixture("evidence-1", BigDecimal.TEN, null, null, TrustLevel.HIGH, false));
        var replacement = query.resolve("evidence-1").orElseThrow();
        assertEquals(BigDecimal.ONE, original.numericValue());
        assertEquals(BigDecimal.TEN, replacement.numericValue());
        assertNotEquals(original, replacement);
    }

    private static TrustedTelemetryReading fixture(String id, BigDecimal numeric, String text,
            Boolean flag, TrustLevel trust, boolean missingMetadata) {
        return new TrustedTelemetryReading(id, "original-reading", "point-2", numeric, text, flag,
                missingMetadata ? null : "raw-unit", "quality-code", trust, SOURCE, TRUSTED,
                "assessment-3", missingMetadata ? null : "asset-type", missingMetadata ? null : "asset-4",
                missingMetadata ? null : "asset-code", missingMetadata ? null : "snapshot-5",
                missingMetadata ? null : "batch-6");
    }

    private static final class Stub implements TrustedTelemetryReadingRepositoryPort {
        private Optional<TrustedTelemetryReading> result = Optional.empty();
        private RuntimeException failure;
        private int calls;
        private String request;

        @Override
        public Optional<TrustedTelemetryReading> findById(String id) {
            calls++;
            request = id;
            if (failure != null) throw failure;
            return result;
        }
        @Override
        public TrustedTelemetryReading save(TrustedTelemetryReading reading) {
            throw new AssertionError("Read-only query must not save.");
        }
    }
}
