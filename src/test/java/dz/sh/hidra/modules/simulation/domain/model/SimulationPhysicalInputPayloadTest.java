/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationPhysicalInputPayloadTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Verifies combined network connectivity and exact immutable physical payload assembly.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.Origin;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.SourceKind;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.Knowledge;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.Quantity;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.TargetKind;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentInput.Kind;
import dz.sh.hidra.modules.simulation.domain.model.SimulationBoundarySeriesInput.Interpolation;
import dz.sh.hidra.modules.simulation.domain.value.SimulationInputMode;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationPhysicalInputPayloadTest {
    private static final Instant STATE = Instant.parse("2026-10-09T12:00:00Z");
    private static final Instant RECORDED = STATE.plusSeconds(10);
    private static final Instant CAPTURE = STATE.plusSeconds(20);
    private static final Instant END = STATE.plusSeconds(60);

    @Test
    void acceptsPipeOnlyAndEquipmentJoinedGraphsWithoutWeakeningB1() {
        var f = fixture(SimulationInputMode.STEADY_STATE, true, "r1");
        invalid(() -> new SimulationPhysicalNetworkInput("pipe-only", "r1", f.network.nodes(), f.network.pipeSegments()));
        assertEquals(4, f.network.nodes().size());
        assertEquals(2, f.network.pipeSegments().size());
        var empty = new SimulationEquipmentModelInput("empty", f.network.equipmentModel().sourceVersion(), List.of(), List.of());
        var single = graph(f, nodes("a", "b"), List.of(pipe("p", "b", "a")), empty);
        assertEquals("b", single.pipeSegments().get(0).fromNodeId());
        assertEquals(2, new SimulationPhysicalNetworkInput("pipe-only", "r1", single.nodes(), single.pipeSegments()).nodes().size());
        var equipmentIncidence = graph(f, f.network.nodes(), List.of(f.network.pipeSegments().get(0)), f.network.equipmentModel());
        assertEquals(4, equipmentIncidence.nodes().size());
    }

    @Test
    void acceptsCyclesParallelCrossKindLinksAndRetainsOrientations() {
        var f = fixture(SimulationInputMode.STEADY_STATE, true, "r1");
        var pipes = new ArrayList<>(f.network.pipeSegments());
        pipes.add(pipe("parallel", "b", "a"));
        pipes.add(pipe("cross-kind", "b", "c"));
        var graph = graph(f, f.network.nodes(), pipes, f.network.equipmentModel());
        assertEquals(pipes, graph.pipeSegments());
        assertEquals("b", graph.equipmentModel().equipment().get(0).fromNodeId());
        assertEquals("c", graph.equipmentModel().equipment().get(0).toNodeId());
    }

    @Test
    void requiresGraphIdentityScopeSourceAndEquipmentModel() {
        var f = fixture(SimulationInputMode.STEADY_STATE, true, "r1");
        for (String missing : Arrays.asList(null, "", " ")) {
            invalid(() -> new SimulationConnectedNetworkInput(missing, "PIPELINE", "scope", f.network.sourceVersion(), f.network.nodes(), f.network.pipeSegments(), f.network.equipmentModel()));
            invalid(() -> new SimulationConnectedNetworkInput("n", missing, "scope", f.network.sourceVersion(), f.network.nodes(), f.network.pipeSegments(), f.network.equipmentModel()));
            invalid(() -> new SimulationConnectedNetworkInput("n", "PIPELINE", missing, f.network.sourceVersion(), f.network.nodes(), f.network.pipeSegments(), f.network.equipmentModel()));
        }
        invalid(() -> new SimulationConnectedNetworkInput("n", "UNKNOWN", "scope", f.network.sourceVersion(), f.network.nodes(), f.network.pipeSegments(), f.network.equipmentModel()));
        invalid(() -> new SimulationConnectedNetworkInput("n", "PIPELINE", "scope", null, f.network.nodes(), f.network.pipeSegments(), f.network.equipmentModel()));
        invalid(() -> new SimulationConnectedNetworkInput("n", "PIPELINE", "scope", f.fluid.sourceVersion(), f.network.nodes(), f.network.pipeSegments(), f.network.equipmentModel()));
        invalid(() -> graph(f, f.network.nodes(), f.network.pipeSegments(), null));
        var normalized = new SimulationConnectedNetworkInput(" n ", " PIPELINE ", " scope ", f.network.sourceVersion(), f.network.nodes(), f.network.pipeSegments(), f.network.equipmentModel());
        assertEquals("n", normalized.id()); assertEquals("PIPELINE", normalized.scopeType()); assertEquals("scope", normalized.scopeId());
    }

    @Test
    void rejectsMissingUndersizedAndNullGraphListsAndDuplicateIdentities() {
        var f = fixture(SimulationInputMode.STEADY_STATE, true, "r1");
        invalid(() -> graph(f, null, f.network.pipeSegments(), f.network.equipmentModel()));
        invalid(() -> graph(f, List.of(), f.network.pipeSegments(), f.network.equipmentModel()));
        invalid(() -> graph(f, nodes("a"), f.network.pipeSegments(), f.network.equipmentModel()));
        invalid(() -> graph(f, f.network.nodes(), null, f.network.equipmentModel()));
        invalid(() -> graph(f, f.network.nodes(), List.of(), f.network.equipmentModel()));
        invalid(() -> graph(f, Arrays.asList(f.network.nodes().get(0), null), f.network.pipeSegments(), f.network.equipmentModel()));
        invalid(() -> graph(f, f.network.nodes(), Arrays.asList(f.network.pipeSegments().get(0), null), f.network.equipmentModel()));
        var nodes = new ArrayList<>(f.network.nodes()); nodes.add(node(" a "));
        invalid(() -> graph(f, nodes, f.network.pipeSegments(), f.network.equipmentModel()));
        var pipes = new ArrayList<>(f.network.pipeSegments()); pipes.add(pipe(" p1 ", "b", "a"));
        invalid(() -> graph(f, f.network.nodes(), pipes, f.network.equipmentModel()));
        var equipment = new ArrayList<>(f.network.equipmentModel().equipment());
        var c = equipment.get(0); equipment.set(0, new SimulationEquipmentInput("p1", c.fromNodeId(), c.toNodeId(), c.kind(), c.compressor(), null));
        invalid(() -> graph(f, f.network.nodes(), f.network.pipeSegments(), model(f, equipment, f.network.equipmentModel().compressorCurves())));
    }

    @Test
    void rejectsEitherDanglingPipeOrEquipmentEndpointIsolationAndUnionDisconnection() {
        var f = fixture(SimulationInputMode.STEADY_STATE, true, "r1");
        for (boolean from : List.of(true, false)) {
            var pipes = new ArrayList<>(f.network.pipeSegments()); pipes.set(0, pipe("p1", from ? "missing" : "a", from ? "b" : "missing"));
            invalid(() -> graph(f, f.network.nodes(), pipes, f.network.equipmentModel()));
            var items = new ArrayList<>(f.network.equipmentModel().equipment()); var c = items.get(0);
            items.set(0, new SimulationEquipmentInput(c.id(), from ? "missing" : "b", from ? "c" : "missing", c.kind(), c.compressor(), null));
            invalid(() -> graph(f, f.network.nodes(), f.network.pipeSegments(), model(f, items, f.network.equipmentModel().compressorCurves())));
        }
        var nodes = new ArrayList<>(f.network.nodes()); nodes.add(node("isolated"));
        invalid(() -> graph(f, nodes, f.network.pipeSegments(), f.network.equipmentModel()));
        var empty = new SimulationEquipmentModelInput("empty", f.network.equipmentModel().sourceVersion(), List.of(), List.of());
        invalid(() -> graph(f, f.network.nodes(), f.network.pipeSegments(), empty));
    }

    @Test
    void assemblesCompleteSyntheticSteadyAndTransientInputsAndRetainsOptionalUnknowns() {
        for (SimulationInputMode mode : SimulationInputMode.values()) {
            var f = fixture(mode, true, "r1");
            var payload = f.payload(); assertTrue(payload.synthetic());
            assertEquals(mode, payload.manifest().mode());
            assertEquals(decimal("-2"), payload.initialState().quantities().stream().filter(q -> q.quantity() == Quantity.PIPE_MASS_FLOW_KILOGRAMS_PER_SECOND && q.targetId().equals("p1")).findFirst().orElseThrow().value());
            assertEquals(0, payload.initialState().quantities().stream().filter(q -> q.quantity() == Quantity.COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE).findFirst().orElseThrow().value().signum());
        }
        var f = fixture(SimulationInputMode.STEADY_STATE, true, "r1");
        var quantities = new ArrayList<>(f.initial.quantities()); quantities.add(unknown(TargetKind.PIPE, "p1", Quantity.PRESSURE_PASCALS_ABSOLUTE));
        assertEquals(Knowledge.UNKNOWN, assemble(f, initial(f, quantities), null).initialState().quantities().get(quantities.size() - 1).knowledge());
    }

    @Test
    void preservesImmediateScenarioChangesAndPureDeclaredRealMetadataWithoutClaimingTrust() {
        var f = fixture(SimulationInputMode.TRANSIENT, false, "r1");
        var payload = f.payload(); assertFalse(payload.synthetic());
        assertEquals(decimal("110000"), payload.boundarySchedule().series().get(0).points().get(0).value());
        assertEquals(decimal("100000"), payload.initialState().quantities().get(0).value());
        assertEquals(Origin.FORECAST, payload.boundarySchedule().series().get(0).points().get(1).origin());
    }

    @Test
    void requiresAllPayloadObjectsAndRejectsUnknownAssemblySchema() {
        var f = fixture(SimulationInputMode.STEADY_STATE, true, "r1");
        invalid(() -> new SimulationPhysicalInputPayload(null, f.network, f.fluid, f.initial, null));
        invalid(() -> new SimulationPhysicalInputPayload(f.manifest, null, f.fluid, f.initial, null));
        invalid(() -> new SimulationPhysicalInputPayload(f.manifest, f.network, null, f.initial, null));
        invalid(() -> new SimulationPhysicalInputPayload(f.manifest, f.network, f.fluid, null, null));
        var m = manifest(f.manifest, 2, f.manifest.productReference(), f.manifest.scopeType(), f.manifest.scopeId(), STATE, END, null, f.manifest.sources());
        invalid(() -> new SimulationPhysicalInputPayload(m, f.network, f.fluid, f.initial, null));
    }

    @Test
    void requiresEveryCompleteManifestSourceRecordRatherThanOnlyRevisionIdentity() {
        var f = fixture(SimulationInputMode.TRANSIENT, true, "r1");
        for (int kind = 0; kind < f.manifest.sources().size(); kind++) {
            var s = f.manifest.sources().get(kind);
            var variants = List.of(
                    new SimulationInputSourceVersion(s.kind(), "different-owner", s.sourceId(), s.revisionId(), s.payloadSha256(), s.recordedAt(), s.effectiveFrom(), s.effectiveUntil(), s.origin(), s.evidenceReference()),
                    new SimulationInputSourceVersion(s.kind(), s.owner(), "different-source", s.revisionId(), s.payloadSha256(), s.recordedAt(), s.effectiveFrom(), s.effectiveUntil(), s.origin(), s.evidenceReference()),
                    new SimulationInputSourceVersion(s.kind(), s.owner(), s.sourceId(), "different-revision", s.payloadSha256(), s.recordedAt(), s.effectiveFrom(), s.effectiveUntil(), s.origin(), s.evidenceReference()),
                    new SimulationInputSourceVersion(s.kind(), s.owner(), s.sourceId(), s.revisionId(), "b".repeat(64), s.recordedAt(), s.effectiveFrom(), s.effectiveUntil(), s.origin(), s.evidenceReference()),
                    new SimulationInputSourceVersion(s.kind(), s.owner(), s.sourceId(), s.revisionId(), s.payloadSha256(), RECORDED.plusSeconds(1), s.effectiveFrom(), s.effectiveUntil(), s.origin(), s.evidenceReference()),
                    new SimulationInputSourceVersion(s.kind(), s.owner(), s.sourceId(), s.revisionId(), s.payloadSha256(), s.recordedAt(), STATE.minusSeconds(1), s.effectiveUntil(), s.origin(), s.evidenceReference()),
                    new SimulationInputSourceVersion(s.kind(), s.owner(), s.sourceId(), s.revisionId(), s.payloadSha256(), s.recordedAt(), s.effectiveFrom(), END, s.origin(), s.evidenceReference()),
                    new SimulationInputSourceVersion(s.kind(), s.owner(), s.sourceId(), s.revisionId(), s.payloadSha256(), s.recordedAt(), s.effectiveFrom(), s.effectiveUntil(), Origin.SCENARIO, s.evidenceReference()),
                    new SimulationInputSourceVersion(s.kind(), s.owner(), s.sourceId(), s.revisionId(), s.payloadSha256(), s.recordedAt(), s.effectiveFrom(), s.effectiveUntil(), s.origin(), "different-evidence"));
            for (var replacement : variants) {
                var sources = new ArrayList<>(f.manifest.sources()); sources.set(kind, replacement);
                var m = manifest(f.manifest, 1, f.manifest.productReference(), f.manifest.scopeType(), f.manifest.scopeId(), STATE, END, null, sources);
                invalid(() -> new SimulationPhysicalInputPayload(m, f.network, f.fluid, f.initial, f.schedule));
            }
        }
    }

    @Test
    void rejectsScopeProductAndInitialStateTimeMismatch() {
        var f = fixture(SimulationInputMode.STEADY_STATE, true, "r1");
        for (var m : List.of(
                manifest(f.manifest, 1, "other-product", "PIPELINE", "scope", STATE, END, null, f.manifest.sources()),
                manifest(f.manifest, 1, "gas", "PIPELINE_SYSTEM", "scope", STATE, END, null, f.manifest.sources()),
                manifest(f.manifest, 1, "gas", "PIPELINE", "other-scope", STATE, END, null, f.manifest.sources()),
                manifest(f.manifest, 1, "gas", "PIPELINE", "scope", STATE.plusSeconds(1), END, null, f.manifest.sources()))) {
            invalid(() -> new SimulationPhysicalInputPayload(m, f.network, f.fluid, f.initial, null));
        }
    }

    @Test
    void rejectsCurveFluidIdentityRevisionAndUncapturedExpiredOrFutureVersions() {
        var f = fixture(SimulationInputMode.STEADY_STATE, true, "r1"); var old = f.network.equipmentModel().compressorCurves().get(0);
        invalid(() -> withCurve(f, curve("other-fluid", old.fluidRevisionId(), old.sourceVersion())).payload());
        invalid(() -> withCurve(f, curve(old.fluidInputId(), "other-fluid-revision", old.sourceVersion())).payload());
        for (var source : List.of(
                curveSource(old, CAPTURE.plusSeconds(1), STATE, null),
                curveSource(old, RECORDED.plusSeconds(1), STATE, null),
                curveSource(old, STATE.plusSeconds(5), STATE.plusNanos(1), null),
                curveSource(old, STATE.plusSeconds(5), STATE.minusSeconds(1), STATE))) {
            invalid(() -> withCurve(f, curve("fluid", old.fluidRevisionId(), source)).payload());
        }
        var unused = curve("other-fluid", old.fluidRevisionId(), old.sourceVersion());
        unused = new SimulationCompressorCurveInput("unused", unused.sourceVersion(), unused.fluidInputId(), unused.fluidRevisionId(), unused.headDefinitionReference(), unused.efficiencyDefinitionReference(), unused.interpolationMethodReference(), unused.referenceInletPressurePascalsAbsolute(), unused.referenceInletTemperatureKelvin(), unused.speedLines());
        var curves = new ArrayList<>(f.network.equipmentModel().compressorCurves()); curves.add(unused);
        var equipment = model(f, f.network.equipmentModel().equipment(), curves);
        var graph = graph(f, f.network.nodes(), f.network.pipeSegments(), equipment);
        invalid(() -> new SimulationPhysicalInputPayload(f.manifest, graph, f.fluid, f.initial, null));
    }

    @Test
    void rejectsDanglingInitialTargetsIncludingUnknownAndEquipmentKindMismatch() {
        var f = fixture(SimulationInputMode.STEADY_STATE, true, "r1");
        for (var q : List.of(unknown(TargetKind.NODE, "missing", Quantity.NODE_MASS_INJECTION_KILOGRAMS_PER_SECOND),
                unknown(TargetKind.PIPE, "missing", Quantity.PRESSURE_PASCALS_ABSOLUTE),
                unknown(TargetKind.EQUIPMENT, "missing", Quantity.VALVE_OPENING_FRACTION),
                unknown(TargetKind.EQUIPMENT, "compressor", Quantity.VALVE_OPENING_FRACTION),
                unknown(TargetKind.EQUIPMENT, "valve", Quantity.COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE))) {
            var quantities = new ArrayList<>(f.initial.quantities()); quantities.add(q);
            invalid(() -> assemble(f, initial(f, quantities), null));
        }
    }

    @Test
    void rejectsEachAbsentOrUnknownRequiredScalarForBothModes() {
        for (SimulationInputMode mode : SimulationInputMode.values()) {
            var f = fixture(mode, true, "r1");
            for (int index = 0; index < f.initial.quantities().size(); index++) {
                var missing = new ArrayList<>(f.initial.quantities()); missing.remove(index);
                invalid(() -> assemble(f, initial(f, missing), f.schedule));
                var unknown = new ArrayList<>(f.initial.quantities()); var q = unknown.get(index);
                unknown.set(index, unknown(q.targetKind(), q.targetId(), q.quantity()));
                invalid(() -> assemble(f, initial(f, unknown), f.schedule));
            }
        }
    }

    @Test
    void enforcesMeasuredWatermarkWithoutTreatingEstimatesAsMeasurements() {
        var real = fixture(SimulationInputMode.STEADY_STATE, false, "r1");
        var m = manifest(real.manifest, 1, "gas", "PIPELINE", "scope", STATE, END, STATE.minusNanos(1), real.manifest.sources());
        invalid(() -> new SimulationPhysicalInputPayload(m, real.network, real.fluid, real.initial, null));
        assertFalse(real.payload().synthetic());
        var quantities = real.initial.quantities().stream().map(q -> new SimulationStateQuantityInput(q.targetKind(), q.targetId(), q.quantity(), Knowledge.ESTIMATED, q.value(), q.valueAt(), q.evidenceRecordedAt(), q.evidenceReference())).toList();
        assertFalse(new SimulationPhysicalInputPayload(m, real.network, real.fluid, initial(real, quantities), null).synthetic());
    }

    @Test
    void rejectsMissingForbiddenAndMismatchedTransientScheduleHorizons() {
        var transientFixture = fixture(SimulationInputMode.TRANSIENT, true, "r1");
        invalid(() -> assemble(transientFixture, transientFixture.initial, null));
        var steady = fixture(SimulationInputMode.STEADY_STATE, true, "r1");
        invalid(() -> assemble(steady, steady.initial, transientFixture.schedule));
        var m = manifest(transientFixture.manifest, 1, "gas", "PIPELINE", "scope", STATE, END.plusSeconds(1), null, transientFixture.manifest.sources());
        invalid(() -> new SimulationPhysicalInputPayload(m, transientFixture.network, transientFixture.fluid, transientFixture.initial, transientFixture.schedule));
        var shifted = schedule(transientFixture, STATE.plusSeconds(1), END, "a", Quantity.PRESSURE_PASCALS_ABSOLUTE, TargetKind.NODE, Origin.SYNTHETIC);
        invalid(() -> assemble(transientFixture, transientFixture.initial, shifted));
    }

    @Test
    void rejectsDanglingScheduleTargetsAndCrossKindEquipmentControls() {
        var f = fixture(SimulationInputMode.TRANSIENT, true, "r1");
        invalid(() -> assemble(f, f.initial, schedule(f, STATE, END, "missing", Quantity.PRESSURE_PASCALS_ABSOLUTE, TargetKind.NODE, Origin.SYNTHETIC)));
        invalid(() -> assemble(f, f.initial, schedule(f, STATE, END, "missing", Quantity.VALVE_OPENING_FRACTION, TargetKind.EQUIPMENT, Origin.SYNTHETIC)));
        invalid(() -> assemble(f, f.initial, schedule(f, STATE, END, "compressor", Quantity.VALVE_OPENING_FRACTION, TargetKind.EQUIPMENT, Origin.SYNTHETIC)));
        invalid(() -> assemble(f, f.initial, schedule(f, STATE, END, "valve", Quantity.COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE, TargetKind.EQUIPMENT, Origin.SYNTHETIC)));
    }

    @Test
    void requiresKnownScheduledBaselineAndAllowsAnImmediateDifferentScenarioValue() {
        var f = fixture(SimulationInputMode.TRANSIENT, true, "r1");
        var schedule = schedule(f, STATE, END, "a", Quantity.NODE_MASS_INJECTION_KILOGRAMS_PER_SECOND, TargetKind.NODE, Origin.SCENARIO);
        invalid(() -> assemble(f, f.initial, schedule));
        var quantities = new ArrayList<>(f.initial.quantities()); quantities.add(unknown(TargetKind.NODE, "a", Quantity.NODE_MASS_INJECTION_KILOGRAMS_PER_SECOND));
        invalid(() -> assemble(f, initial(f, quantities), schedule));
        quantities.set(quantities.size() - 1, value(TargetKind.NODE, "a", Quantity.NODE_MASS_INJECTION_KILOGRAMS_PER_SECOND, "0", Knowledge.SYNTHETIC));
        assertEquals(decimal("110000"), assemble(f, initial(f, quantities), schedule).boundarySchedule().series().get(0).points().get(0).value());
    }

    @Test
    void rejectsHiddenSyntheticStateScheduleAndCurveEvenWhenAnotherSourceIsSynthetic() {
        var real = fixture(SimulationInputMode.TRANSIENT, false, "r1");
        for (boolean anotherSynthetic : List.of(false, true)) {
            var f = anotherSynthetic ? withSyntheticTopology(real) : real;
            var quantities = new ArrayList<>(f.initial.quantities()); var q = quantities.get(0);
            quantities.set(0, new SimulationStateQuantityInput(q.targetKind(), q.targetId(), q.quantity(), Knowledge.SYNTHETIC, q.value(), q.valueAt(), q.evidenceRecordedAt(), q.evidenceReference()));
            invalid(() -> assemble(f, initial(f, quantities), f.schedule));
            invalid(() -> assemble(f, f.initial, schedule(f, STATE, END, "a", Quantity.PRESSURE_PASCALS_ABSOLUTE, TargetKind.NODE, Origin.SYNTHETIC)));
            var c = f.network.equipmentModel().compressorCurves().get(0); var syntheticCurveSource = origin(c.sourceVersion(), Origin.SYNTHETIC);
            invalid(() -> withCurve(f, curve("fluid", c.fluidRevisionId(), syntheticCurveSource)).payload());
        }
    }

    @Test
    void defensivelyCopiesNetworkListsAndRetainsNestedImmutability() {
        var f = fixture(SimulationInputMode.STEADY_STATE, true, "r1");
        var nodes = new ArrayList<>(f.network.nodes()); var pipes = new ArrayList<>(f.network.pipeSegments());
        var graph = graph(f, nodes, pipes, f.network.equipmentModel()); nodes.clear(); pipes.clear();
        assertEquals(4, graph.nodes().size()); assertEquals(2, graph.pipeSegments().size());
        assertThrows(UnsupportedOperationException.class, () -> graph.nodes().clear());
        assertThrows(UnsupportedOperationException.class, () -> graph.pipeSegments().clear());
        assertThrows(UnsupportedOperationException.class, () -> graph.equipmentModel().equipment().clear());
        assertThrows(UnsupportedOperationException.class, () -> f.payload().initialState().quantities().clear());
    }

    @Test
    void replacementRevisionsLeaveEveryEarlierPayloadAndOrientationIntact() {
        var original = fixture(SimulationInputMode.TRANSIENT, true, "r1").payload();
        var next = fixture(SimulationInputMode.TRANSIENT, true, "r2");
        var changedNodes = new ArrayList<>(next.network.nodes());
        changedNodes.set(0, new SimulationNetworkNodeInput("a", decimal("20")));
        var changedPipes = new ArrayList<>(next.network.pipeSegments());
        changedPipes.set(0, new SimulationPipeSegmentInput("p1", "a", "b", decimal("200"), BigDecimal.ONE, BigDecimal.ZERO));
        var changedNetwork = graph(next, changedNodes, changedPipes, next.network.equipmentModel());
        var changedFluid = new SimulationGasFluidInput(next.fluid.id(), next.fluid.sourceVersion(), next.fluid.productReference(),
                next.fluid.propertyMethodReference(), next.fluid.propertyMethodRevisionId(), next.fluid.propertyMethodEvidenceReference(),
                List.of(new SimulationGasFluidInput.Component("declared-component", decimal("0.8")),
                        new SimulationGasFluidInput.Component("other-component", decimal("0.2"))));
        var changedQuantities = new ArrayList<>(next.initial.quantities());
        changedQuantities.set(0, value(TargetKind.NODE, "a", Quantity.PRESSURE_PASCALS_ABSOLUTE, "105000", Knowledge.SYNTHETIC));
        var replacement = new SimulationPhysicalInputPayload(next.manifest, changedNetwork, changedFluid,
                initial(next, changedQuantities), next.schedule);
        assertEquals("TOPOLOGY_CONFIGURATION-r1", original.network().sourceVersion().revisionId());
        assertEquals("TOPOLOGY_CONFIGURATION-r2", replacement.network().sourceVersion().revisionId());
        assertEquals("FLUID_MODEL-r1", original.fluid().sourceVersion().revisionId());
        assertEquals("FLUID_MODEL-r2", replacement.fluid().sourceVersion().revisionId());
        assertEquals("OPERATING_STATE-r1", original.initialState().sourceVersion().revisionId());
        assertEquals("BOUNDARY_SCHEDULE-r1", original.boundarySchedule().sourceVersion().revisionId());
        assertEquals("EQUIPMENT_PARAMETERS-r1", original.network().equipmentModel().sourceVersion().revisionId());
        assertEquals("a", original.network().pipeSegments().get(0).fromNodeId());
        assertEquals("b", original.network().pipeSegments().get(0).toNodeId());
        assertEquals(decimal("100000"), original.initialState().quantities().get(0).value());
        assertEquals(Origin.SYNTHETIC, original.boundarySchedule().series().get(0).points().get(1).origin());
        assertEquals(BigDecimal.ZERO, original.network().nodes().get(0).elevationMeters());
        assertEquals(decimal("20"), replacement.network().nodes().get(0).elevationMeters());
        assertEquals(decimal("100"), original.network().pipeSegments().get(0).lengthMeters());
        assertEquals(decimal("200"), replacement.network().pipeSegments().get(0).lengthMeters());
        assertEquals(BigDecimal.ONE, original.fluid().components().get(0).moleFraction());
        assertEquals(decimal("0.8"), replacement.fluid().components().get(0).moleFraction());
        assertEquals(decimal("105000"), replacement.initialState().quantities().get(0).value());
    }

    private record Fixture(SimulationInputManifest manifest, SimulationConnectedNetworkInput network,
            SimulationGasFluidInput fluid, SimulationInitialStateInput initial, SimulationBoundaryScheduleInput schedule) {
        SimulationPhysicalInputPayload payload() { return new SimulationPhysicalInputPayload(manifest, network, fluid, initial, schedule); }
    }
    private static BigDecimal decimal(String value) { return new BigDecimal(value); }
    private static SimulationNetworkNodeInput node(String id) { return new SimulationNetworkNodeInput(id, BigDecimal.ZERO); }
    private static List<SimulationNetworkNodeInput> nodes(String... ids) { return Arrays.stream(ids).map(SimulationPhysicalInputPayloadTest::node).toList(); }
    private static SimulationPipeSegmentInput pipe(String id, String from, String to) { return new SimulationPipeSegmentInput(id, from, to, decimal("100"), BigDecimal.ONE, BigDecimal.ZERO); }
    private static SimulationInputSourceVersion source(SourceKind kind, String revision, boolean synthetic) { return new SimulationInputSourceVersion(kind, "owner", "source-" + kind, kind + "-" + revision, "a".repeat(64), RECORDED, STATE, null, synthetic ? Origin.SYNTHETIC : Origin.APPROVED_PARAMETER, "declared-evidence"); }
    private static SimulationStateQuantityInput value(TargetKind target, String id, Quantity quantity, String value, Knowledge knowledge) { return new SimulationStateQuantityInput(target, id, quantity, knowledge, decimal(value), STATE, RECORDED, "declared-evidence"); }
    private static SimulationStateQuantityInput unknown(TargetKind target, String id, Quantity quantity) { return new SimulationStateQuantityInput(target, id, quantity, Knowledge.UNKNOWN, null, null, null, "declared-missingness"); }
    private static Fixture fixture(SimulationInputMode mode, boolean synthetic, String revision) {
        var topology = source(SourceKind.TOPOLOGY_CONFIGURATION, revision, synthetic); var fluidSource = source(SourceKind.FLUID_MODEL, revision, synthetic);
        var equipmentSource = source(SourceKind.EQUIPMENT_PARAMETERS, revision, synthetic); var stateSource = source(SourceKind.OPERATING_STATE, revision, synthetic);
        var scheduleSource = source(SourceKind.BOUNDARY_SCHEDULE, revision, synthetic);
        var fluid = new SimulationGasFluidInput("fluid", fluidSource, "gas", "declared-method", "method-r", "declared-evidence", List.of(new SimulationGasFluidInput.Component("declared-component", BigDecimal.ONE)));
        var curveSource = new SimulationInputSourceVersion(SourceKind.EQUIPMENT_PARAMETERS, "owner", "curve", "curve-" + revision, "a".repeat(64), STATE.plusSeconds(5), STATE, null, synthetic ? Origin.SYNTHETIC : Origin.APPROVED_PARAMETER, "declared-evidence");
        var curve = curve("fluid", fluidSource.revisionId(), curveSource);
        var compressor = new SimulationEquipmentInput("compressor", "b", "c", Kind.COMPRESSOR, new SimulationEquipmentInput.CompressorConfiguration("curve", curveSource.revisionId(), decimal("1000")), null);
        var valve = new SimulationEquipmentInput("valve", "a", "d", Kind.VALVE, null, new SimulationEquipmentInput.ValveConfiguration("characteristic", "r", "evidence", BigDecimal.ZERO));
        var equipment = new SimulationEquipmentModelInput("model", equipmentSource, List.of(compressor, valve), List.of(curve));
        var graph = new SimulationConnectedNetworkInput("network", "PIPELINE", "scope", topology, nodes("a", "b", "c", "d"), List.of(pipe("p1", "a", "b"), pipe("p2", "c", "d")), equipment);
        var knowledge = synthetic ? Knowledge.SYNTHETIC : Knowledge.MEASURED; var quantities = new ArrayList<SimulationStateQuantityInput>();
        for (var n : graph.nodes()) { quantities.add(value(TargetKind.NODE, n.id(), Quantity.PRESSURE_PASCALS_ABSOLUTE, "100000", knowledge)); quantities.add(value(TargetKind.NODE, n.id(), Quantity.TEMPERATURE_KELVIN, "300", knowledge)); }
        for (var p : graph.pipeSegments()) { quantities.add(value(TargetKind.PIPE, p.id(), Quantity.PIPE_MASS_FLOW_KILOGRAMS_PER_SECOND, p.id().equals("p1") ? "-2" : "0", knowledge));
            if (mode == SimulationInputMode.TRANSIENT) { quantities.add(value(TargetKind.PIPE, p.id(), Quantity.PRESSURE_PASCALS_ABSOLUTE, "100000", knowledge)); quantities.add(value(TargetKind.PIPE, p.id(), Quantity.TEMPERATURE_KELVIN, "300", knowledge)); } }
        quantities.add(value(TargetKind.EQUIPMENT, "compressor", Quantity.COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE, "0", knowledge)); quantities.add(value(TargetKind.EQUIPMENT, "valve", Quantity.VALVE_OPENING_FRACTION, "0", knowledge));
        var initial = new SimulationInitialStateInput("state", stateSource, STATE, quantities);
        var sources = new ArrayList<>(List.of(topology, fluidSource, equipmentSource, stateSource));
        SimulationBoundaryScheduleInput schedule = null;
        if (mode == SimulationInputMode.TRANSIENT) { sources.add(scheduleSource); schedule = new SimulationBoundaryScheduleInput("schedule", scheduleSource, STATE, END, List.of(boundary("a", Quantity.PRESSURE_PASCALS_ABSOLUTE, TargetKind.NODE, STATE, END, synthetic ? Origin.SYNTHETIC : Origin.FORECAST))); }
        var manifest = new SimulationInputManifest("manifest", 1, "gas", "PIPELINE", "scope", mode, STATE, CAPTURE, null, mode == SimulationInputMode.TRANSIENT ? END : null, sources);
        return new Fixture(manifest, graph, fluid, initial, schedule);
    }
    private static SimulationConnectedNetworkInput graph(Fixture f, List<SimulationNetworkNodeInput> nodes, List<SimulationPipeSegmentInput> pipes, SimulationEquipmentModelInput model) { return new SimulationConnectedNetworkInput(f.network.id(), f.network.scopeType(), f.network.scopeId(), f.network.sourceVersion(), nodes, pipes, model); }
    private static SimulationEquipmentModelInput model(Fixture f, List<SimulationEquipmentInput> items, List<SimulationCompressorCurveInput> curves) { return new SimulationEquipmentModelInput(f.network.equipmentModel().id(), f.network.equipmentModel().sourceVersion(), items, curves); }
    private static SimulationInitialStateInput initial(Fixture f, List<SimulationStateQuantityInput> quantities) { return new SimulationInitialStateInput(f.initial.id(), f.initial.sourceVersion(), STATE, quantities); }
    private static SimulationPhysicalInputPayload assemble(Fixture f, SimulationInitialStateInput state, SimulationBoundaryScheduleInput schedule) { return new SimulationPhysicalInputPayload(f.manifest, f.network, f.fluid, state, schedule); }
    private static SimulationInputManifest manifest(SimulationInputManifest m, int schema, String product, String scopeType, String scopeId, Instant state, Instant end, Instant watermark, List<SimulationInputSourceVersion> sources) { return new SimulationInputManifest(m.id(), schema, product, scopeType, scopeId, m.mode(), state, CAPTURE, watermark, m.mode() == SimulationInputMode.TRANSIENT ? end : null, sources); }
    private static SimulationCompressorCurveInput curve(String fluid, String revision, SimulationInputSourceVersion source) { return new SimulationCompressorCurveInput("curve", source, fluid, revision, "declared-head", "declared-efficiency", "declared-interpolation", decimal("100000"), decimal("300"), List.of(new SimulationCompressorCurveInput.SpeedLine(decimal("1000"), List.of(new SimulationCompressorCurveInput.Point(BigDecimal.ONE, BigDecimal.TEN, decimal("0.8")), new SimulationCompressorCurveInput.Point(decimal("2"), decimal("9"), decimal("0.75")))))); }
    private static SimulationInputSourceVersion curveSource(SimulationCompressorCurveInput c, Instant recorded, Instant from, Instant until) { var s = c.sourceVersion(); return new SimulationInputSourceVersion(s.kind(), s.owner(), s.sourceId(), s.revisionId(), s.payloadSha256(), recorded, from, until, s.origin(), s.evidenceReference()); }
    private static Fixture withCurve(Fixture f, SimulationCompressorCurveInput c) { var model = model(f, f.network.equipmentModel().equipment(), List.of(c)); return new Fixture(f.manifest, graph(f, f.network.nodes(), f.network.pipeSegments(), model), f.fluid, f.initial, f.schedule); }
    private static SimulationBoundarySeriesInput boundary(String id, Quantity quantity, TargetKind target, Instant start, Instant end, Origin origin) { String first = quantity == Quantity.VALVE_OPENING_FRACTION ? "0" : "110000"; String last = quantity == Quantity.VALVE_OPENING_FRACTION ? "1" : "120000"; return new SimulationBoundarySeriesInput("boundary", target, id, quantity, Interpolation.STEP_PREVIOUS, List.of(new SimulationBoundarySeriesInput.Point(start, decimal(first), origin, STATE, "declared-evidence"), new SimulationBoundarySeriesInput.Point(end, decimal(last), origin, STATE, "declared-evidence"))); }
    private static SimulationBoundaryScheduleInput schedule(Fixture f, Instant start, Instant end, String id, Quantity quantity, TargetKind target, Origin origin) { return new SimulationBoundaryScheduleInput(f.schedule.id(), f.schedule.sourceVersion(), start, end, List.of(boundary(id, quantity, target, start, end, origin))); }
    private static SimulationInputSourceVersion origin(SimulationInputSourceVersion s, Origin origin) { return new SimulationInputSourceVersion(s.kind(), s.owner(), s.sourceId(), s.revisionId(), s.payloadSha256(), s.recordedAt(), s.effectiveFrom(), s.effectiveUntil(), origin, s.evidenceReference()); }
    private static Fixture withSyntheticTopology(Fixture f) { var t = origin(f.network.sourceVersion(), Origin.SYNTHETIC); var sources = f.manifest.sources().stream().map(s -> s.kind() == SourceKind.TOPOLOGY_CONFIGURATION ? t : s).toList(); var m = manifest(f.manifest, 1, "gas", "PIPELINE", "scope", STATE, END, null, sources); var g = new SimulationConnectedNetworkInput(f.network.id(), f.network.scopeType(), f.network.scopeId(), t, f.network.nodes(), f.network.pipeSegments(), f.network.equipmentModel()); return new Fixture(m, g, f.fluid, f.initial, f.schedule); }
    private static void invalid(org.junit.jupiter.api.function.Executable operation) { assertThrows(InvalidSimulationValueException.class, operation); }
}
