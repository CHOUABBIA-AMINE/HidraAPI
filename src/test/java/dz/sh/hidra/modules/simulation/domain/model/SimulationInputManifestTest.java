/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationInputManifestTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Verifies immutable input provenance, validity and calculation-mode consistency.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.Origin;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.SourceKind;
import dz.sh.hidra.modules.simulation.domain.value.SimulationInputMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationInputManifestTest {
    private static final Instant STATE = Instant.parse("2026-10-09T12:00:00Z");
    private static final Instant CAPTURE = STATE.plusSeconds(10);
    private static final String HASH = "a".repeat(64);

    @Test
    void acceptsSyntheticSteadyStateAndTransientInputs() {
        var steady = manifest(SimulationInputMode.STEADY_STATE, null, base());
        assertTrue(steady.synthetic());
        var transientSources = new ArrayList<>(base());
        transientSources.add(source(SourceKind.BOUNDARY_SCHEDULE));
        var dynamic = manifest(SimulationInputMode.TRANSIENT, STATE.plusSeconds(300), transientSources);
        assertEquals(5, dynamic.sources().size());
        assertEquals(STATE.plusSeconds(300), dynamic.horizonEnd());
    }

    @Test
    void preservesDefensiveImmutableSelectionAcrossReplacementVersions() {
        var selected = new ArrayList<>(base());
        var first = manifest(SimulationInputMode.STEADY_STATE, null, selected);
        selected.set(0, version(SourceKind.TOPOLOGY_CONFIGURATION, "revision-2", STATE.minusSeconds(1), null,
                CAPTURE, Origin.SYNTHETIC));
        var second = manifest(SimulationInputMode.STEADY_STATE, null, selected);
        selected.clear();
        assertEquals("revision-1", first.sources().get(0).revisionId());
        assertEquals("revision-2", second.sources().get(0).revisionId());
        assertEquals(4, first.sources().size());
        assertThrows(UnsupportedOperationException.class, () -> first.sources().clear());
    }

    @Test
    void validatesEffectiveIntervalWithExclusiveEndAndOpenEndedVersion() {
        var bounded = version(SourceKind.FLUID_MODEL, "r", STATE, STATE.plusSeconds(1), CAPTURE, Origin.SYNTHETIC);
        assertTrue(bounded.effectiveAt(STATE));
        assertFalse(bounded.effectiveAt(STATE.minusNanos(1)));
        assertFalse(bounded.effectiveAt(STATE.plusSeconds(1)));
        assertTrue(source(SourceKind.FLUID_MODEL).effectiveAt(STATE.plusSeconds(1000)));
        invalid(() -> bounded.effectiveAt(null));
        invalid(() -> version(SourceKind.FLUID_MODEL, "r", STATE, STATE, CAPTURE, Origin.SYNTHETIC));
        invalid(() -> version(SourceKind.FLUID_MODEL, "r", STATE, STATE.minusSeconds(1), CAPTURE, Origin.SYNTHETIC));
    }

    @Test
    void rejectsSourcesNotEffectiveAtInitialState() {
        var sources = new ArrayList<>(base());
        sources.set(0, version(SourceKind.TOPOLOGY_CONFIGURATION, "r", STATE.plusNanos(1), null, CAPTURE, Origin.SYNTHETIC));
        invalid(() -> manifest(SimulationInputMode.STEADY_STATE, null, sources));
        sources.set(0, version(SourceKind.TOPOLOGY_CONFIGURATION, "r", STATE.minusSeconds(1), STATE, CAPTURE, Origin.SYNTHETIC));
        invalid(() -> manifest(SimulationInputMode.STEADY_STATE, null, sources));
    }

    @Test
    void rejectsSourceRevisionRecordedAfterCapture() {
        var sources = new ArrayList<>(base());
        sources.set(0, version(SourceKind.TOPOLOGY_CONFIGURATION, "r", STATE, null, CAPTURE.plusNanos(1), Origin.SYNTHETIC));
        invalid(() -> manifest(SimulationInputMode.STEADY_STATE, null, sources));
    }

    @Test
    void rejectsMissingAndDuplicateSourceKindsInsteadOfChoosingLatest() {
        invalid(() -> manifest(SimulationInputMode.STEADY_STATE, null, List.of()));
        for (int i = 0; i < base().size(); i++) {
            var missing = new ArrayList<>(base());
            missing.remove(i);
            invalid(() -> manifest(SimulationInputMode.STEADY_STATE, null, missing));
        }
        var duplicate = new ArrayList<>(base());
        duplicate.add(version(SourceKind.FLUID_MODEL, "revision-2", STATE, null, CAPTURE, Origin.SYNTHETIC));
        invalid(() -> manifest(SimulationInputMode.STEADY_STATE, null, duplicate));
    }

    @Test
    void rejectsNullSourceListAndNullEntry() {
        invalid(() -> manifest(SimulationInputMode.STEADY_STATE, null, null));
        var sources = new ArrayList<>(base());
        sources.set(0, null);
        invalid(() -> manifest(SimulationInputMode.STEADY_STATE, null, sources));
    }

    @Test
    void transientRequiresBothFutureHorizonAndBoundarySchedule() {
        invalid(() -> manifest(SimulationInputMode.TRANSIENT, STATE.plusSeconds(1), base()));
        var scheduled = new ArrayList<>(base());
        scheduled.add(source(SourceKind.BOUNDARY_SCHEDULE));
        invalid(() -> manifest(SimulationInputMode.TRANSIENT, null, scheduled));
        invalid(() -> manifest(SimulationInputMode.TRANSIENT, STATE, scheduled));
        invalid(() -> manifest(SimulationInputMode.TRANSIENT, STATE.minusNanos(1), scheduled));
    }

    @Test
    void steadyStateRejectsHorizonAndBoundarySchedule() {
        invalid(() -> manifest(SimulationInputMode.STEADY_STATE, STATE.plusSeconds(1), base()));
        var scheduled = new ArrayList<>(base());
        scheduled.add(source(SourceKind.BOUNDARY_SCHEDULE));
        invalid(() -> manifest(SimulationInputMode.STEADY_STATE, null, scheduled));
    }

    @Test
    void validatesInitialCaptureAndMeasurementWatermarkOrder() {
        invalid(() -> create("m", 1, "gas", "PIPELINE", "p", SimulationInputMode.STEADY_STATE,
                CAPTURE.plusNanos(1), CAPTURE, null, null, base()));
        invalid(() -> create("m", 1, "gas", "PIPELINE", "p", SimulationInputMode.STEADY_STATE,
                STATE, CAPTURE, STATE.plusNanos(1), null, base()));
        assertEquals(STATE, create("m", 1, "gas", "PIPELINE", "p", SimulationInputMode.STEADY_STATE,
                STATE, STATE, STATE, null, base()).measurementWatermark());
    }

    @Test
    void requiresManifestIdentitiesPositiveSchemaAndExplicitModeAndTimes() {
        invalid(() -> create(" ", 1, "gas", "PIPELINE", "p", SimulationInputMode.STEADY_STATE, STATE, CAPTURE, null, null, base()));
        invalid(() -> create("m", 0, "gas", "PIPELINE", "p", SimulationInputMode.STEADY_STATE, STATE, CAPTURE, null, null, base()));
        invalid(() -> create("m", -1, "gas", "PIPELINE", "p", SimulationInputMode.STEADY_STATE, STATE, CAPTURE, null, null, base()));
        invalid(() -> create("m", 1, null, "PIPELINE", "p", SimulationInputMode.STEADY_STATE, STATE, CAPTURE, null, null, base()));
        invalid(() -> create("m", 1, "gas", "UNKNOWN", "p", SimulationInputMode.STEADY_STATE, STATE, CAPTURE, null, null, base()));
        invalid(() -> create("m", 1, "gas", "PIPELINE", " ", SimulationInputMode.STEADY_STATE, STATE, CAPTURE, null, null, base()));
        invalid(() -> create("m", 1, "gas", "PIPELINE", "p", null, STATE, CAPTURE, null, null, base()));
        invalid(() -> create("m", 1, "gas", "PIPELINE", "p", SimulationInputMode.STEADY_STATE, null, CAPTURE, null, null, base()));
        invalid(() -> create("m", 1, "gas", "PIPELINE", "p", SimulationInputMode.STEADY_STATE, STATE, null, null, null, base()));
    }

    @Test
    void sourceRequiresIdentityEvidenceKindOriginAndTimestamps() {
        invalid(() -> sourceFields(null, "owner", "s", "r", HASH, CAPTURE, STATE, Origin.SYNTHETIC, "e"));
        invalid(() -> sourceFields(SourceKind.FLUID_MODEL, " ", "s", "r", HASH, CAPTURE, STATE, Origin.SYNTHETIC, "e"));
        invalid(() -> sourceFields(SourceKind.FLUID_MODEL, "owner", null, "r", HASH, CAPTURE, STATE, Origin.SYNTHETIC, "e"));
        invalid(() -> sourceFields(SourceKind.FLUID_MODEL, "owner", "s", " ", HASH, CAPTURE, STATE, Origin.SYNTHETIC, "e"));
        invalid(() -> sourceFields(SourceKind.FLUID_MODEL, "owner", "s", "r", HASH, null, STATE, Origin.SYNTHETIC, "e"));
        invalid(() -> sourceFields(SourceKind.FLUID_MODEL, "owner", "s", "r", HASH, CAPTURE, null, Origin.SYNTHETIC, "e"));
        invalid(() -> sourceFields(SourceKind.FLUID_MODEL, "owner", "s", "r", HASH, CAPTURE, STATE, null, "e"));
        invalid(() -> sourceFields(SourceKind.FLUID_MODEL, "owner", "s", "r", HASH, CAPTURE, STATE, Origin.SYNTHETIC, " "));
    }

    @Test
    void validatesAndNormalizesDigestReferenceWithoutClaimingPayloadVerification() {
        var normalized = sourceFields(SourceKind.FLUID_MODEL, " owner ", " s ", " r ", " " + HASH.toUpperCase(java.util.Locale.ROOT) + " ", CAPTURE, STATE, Origin.SYNTHETIC, " e ");
        assertEquals(HASH, normalized.payloadSha256());
        assertEquals("owner", normalized.owner());
        assertEquals("e", normalized.evidenceReference());
        for (String hash : List.of("", "a".repeat(63), "a".repeat(65), "g".repeat(64))) {
            invalid(() -> sourceFields(SourceKind.FLUID_MODEL, "o", "s", "r", hash, CAPTURE, STATE, Origin.SYNTHETIC, "e"));
        }
        invalid(() -> sourceFields(SourceKind.FLUID_MODEL, "o", "s", "r", null, CAPTURE, STATE, Origin.SYNTHETIC, "e"));
    }

    @Test
    void syntheticFlagDoesNotConflateDeclaredNonSyntheticOriginWithVerifiedReadiness() {
        var sources = new ArrayList<>(base());
        for (int i = 0; i < sources.size(); i++) {
            sources.set(i, version(sources.get(i).kind(), "r", STATE, null, CAPTURE, Origin.APPROVED_PARAMETER));
        }
        assertFalse(manifest(SimulationInputMode.STEADY_STATE, null, sources).synthetic());
        sources.set(2, source(SourceKind.EQUIPMENT_PARAMETERS));
        assertTrue(manifest(SimulationInputMode.STEADY_STATE, null, sources).synthetic());
    }

    private static SimulationInputManifest manifest(SimulationInputMode mode, Instant end, List<SimulationInputSourceVersion> sources) {
        return create("manifest-1", 1, "synthetic-gas", "PIPELINE", "synthetic-pipe", mode, STATE, CAPTURE, STATE, end, sources);
    }

    private static SimulationInputManifest create(String id, int schema, String product, String type, String scope,
            SimulationInputMode mode, Instant state, Instant capture, Instant watermark, Instant end, List<SimulationInputSourceVersion> sources) {
        return new SimulationInputManifest(id, schema, product, type, scope, mode, state, capture, watermark, end, sources);
    }

    private static List<SimulationInputSourceVersion> base() {
        return List.of(source(SourceKind.TOPOLOGY_CONFIGURATION), source(SourceKind.FLUID_MODEL),
                source(SourceKind.EQUIPMENT_PARAMETERS), source(SourceKind.OPERATING_STATE));
    }

    private static SimulationInputSourceVersion source(SourceKind kind) {
        return version(kind, "revision-1", STATE, null, STATE, Origin.SYNTHETIC);
    }

    private static SimulationInputSourceVersion version(SourceKind kind, String revision, Instant from, Instant until,
            Instant recorded, Origin origin) {
        return new SimulationInputSourceVersion(kind, "simulation", kind.name(), revision, HASH, recorded, from, until, origin, "synthetic-reference-case");
    }

    private static SimulationInputSourceVersion sourceFields(SourceKind kind, String owner, String source, String revision,
            String hash, Instant recorded, Instant from, Origin origin, String evidence) {
        return new SimulationInputSourceVersion(kind, owner, source, revision, hash, recorded, from, null, origin, evidence);
    }

    private static void invalid(org.junit.jupiter.api.function.Executable action) {
        assertThrows(InvalidSimulationValueException.class, action);
    }
}
