/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationStateBoundaryInputTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Verifies synthetic state knowledge, boundary provenance, horizon coverage and immutability.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.Origin;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.SourceKind;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.Knowledge;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.Quantity;
import dz.sh.hidra.modules.simulation.domain.model.SimulationStateQuantityInput.TargetKind;
import dz.sh.hidra.modules.simulation.domain.model.SimulationBoundarySeriesInput.Interpolation;
import dz.sh.hidra.modules.simulation.domain.model.SimulationBoundarySeriesInput.Point;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationStateBoundaryInputTest {
    private static final Instant START = Instant.parse("2026-10-09T12:00:00Z");
    private static final Instant RECORDED = START.plusSeconds(10);
    private static final Instant END = START.plusSeconds(60);

    @Test
    void retainsMeasuredEstimatedSyntheticAndUnknownInitialKnowledge() {
        var quantities = List.of(known(TargetKind.NODE, "measured", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.MEASURED, "100000"),
                known(TargetKind.NODE, "estimated", Quantity.TEMPERATURE_KELVIN, Knowledge.ESTIMATED, "300"),
                known(TargetKind.PIPE, "synthetic", Quantity.PIPE_MASS_FLOW_KILOGRAMS_PER_SECOND, Knowledge.SYNTHETIC, "-2"),
                unknown(TargetKind.NODE, "unknown", Quantity.PRESSURE_PASCALS_ABSOLUTE));
        var state = state(quantities);
        assertEquals(quantities, state.quantities());
        assertNull(state.quantities().get(3).value());
        assertNull(state.quantities().get(3).valueAt());
        assertNull(state.quantities().get(3).evidenceRecordedAt());
    }

    @Test
    void acceptsEveryLegalTargetAndPhysicalBoundaryIncludingSignedFlows() {
        for (TargetKind target : List.of(TargetKind.NODE, TargetKind.PIPE)) {
            assertEquals(decimal("1"), known(target, "x", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.SYNTHETIC, "1").value());
            assertEquals(decimal("1"), known(target, "x", Quantity.TEMPERATURE_KELVIN, Knowledge.SYNTHETIC, "1").value());
        }
        for (String value : List.of("-1", "0", "1")) {
            assertEquals(decimal(value), known(TargetKind.PIPE, "p", Quantity.PIPE_MASS_FLOW_KILOGRAMS_PER_SECOND, Knowledge.SYNTHETIC, value).value());
            assertEquals(decimal(value), known(TargetKind.NODE, "n", Quantity.NODE_MASS_INJECTION_KILOGRAMS_PER_SECOND, Knowledge.SYNTHETIC, value).value());
        }
        assertEquals(BigDecimal.ZERO, known(TargetKind.EQUIPMENT, "c", Quantity.COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE, Knowledge.MEASURED, "0").value());
        for (String opening : List.of("0", "1.00")) {
            assertEquals(decimal(opening), known(TargetKind.EQUIPMENT, "v", Quantity.VALVE_OPENING_FRACTION, Knowledge.SYNTHETIC, opening).value());
        }
    }

    @Test
    void rejectsWrongTargetsEvenForUnknownValues() {
        for (Quantity quantity : Quantity.values()) {
            for (TargetKind target : TargetKind.values()) {
                boolean allowed = switch (quantity) {
                    case PRESSURE_PASCALS_ABSOLUTE, TEMPERATURE_KELVIN -> target != TargetKind.EQUIPMENT;
                    case PIPE_MASS_FLOW_KILOGRAMS_PER_SECOND -> target == TargetKind.PIPE;
                    case NODE_MASS_INJECTION_KILOGRAMS_PER_SECOND -> target == TargetKind.NODE;
                    case COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE, VALVE_OPENING_FRACTION -> target == TargetKind.EQUIPMENT;
                };
                if (!allowed) {
                    invalid(() -> known(target, "x", quantity, Knowledge.SYNTHETIC, "1"));
                    invalid(() -> unknown(target, "x", quantity));
                }
            }
        }
    }

    @Test
    void requiresIdentityEvidenceAndAllEnumsAndNormalizesReferences() {
        for (String missing : Arrays.asList(null, "", " ")) {
            invalid(() -> new SimulationStateQuantityInput(TargetKind.NODE, missing, Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.MEASURED, BigDecimal.ONE, START, RECORDED, "e"));
            invalid(() -> new SimulationStateQuantityInput(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.UNKNOWN, null, null, null, missing));
        }
        invalid(() -> new SimulationStateQuantityInput(null, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.UNKNOWN, null, null, null, "e"));
        invalid(() -> new SimulationStateQuantityInput(TargetKind.NODE, "n", null, Knowledge.UNKNOWN, null, null, null, "e"));
        invalid(() -> new SimulationStateQuantityInput(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, null, null, null, null, "e"));
        var value = new SimulationStateQuantityInput(TargetKind.NODE, " N ", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.MEASURED, BigDecimal.ONE, START, RECORDED, " evidence ");
        assertEquals("N", value.targetId());
        assertEquals("evidence", value.evidenceReference());
    }

    @Test
    void requiresKnownValueTimesAndForbidsUnknownValueOrTimes() {
        for (Knowledge knowledge : List.of(Knowledge.MEASURED, Knowledge.ESTIMATED, Knowledge.SYNTHETIC)) {
            invalid(() -> new SimulationStateQuantityInput(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, knowledge, null, START, RECORDED, "e"));
            invalid(() -> new SimulationStateQuantityInput(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, knowledge, BigDecimal.ONE, null, RECORDED, "e"));
            invalid(() -> new SimulationStateQuantityInput(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, knowledge, BigDecimal.ONE, START, null, "e"));
            invalid(() -> new SimulationStateQuantityInput(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, knowledge, BigDecimal.ONE, RECORDED, START, "e"));
        }
        invalid(() -> new SimulationStateQuantityInput(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.UNKNOWN, BigDecimal.ONE, null, null, "e"));
        invalid(() -> new SimulationStateQuantityInput(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.UNKNOWN, null, START, null, "e"));
        invalid(() -> new SimulationStateQuantityInput(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.UNKNOWN, null, null, RECORDED, "e"));
    }

    @Test
    void rejectsInvalidQuantityRangesWithoutInventingOperatingLimits() {
        for (String value : List.of("-1", "0")) {
            invalid(() -> known(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.SYNTHETIC, value));
            invalid(() -> known(TargetKind.PIPE, "p", Quantity.TEMPERATURE_KELVIN, Knowledge.SYNTHETIC, value));
        }
        invalid(() -> known(TargetKind.EQUIPMENT, "c", Quantity.COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE, Knowledge.SYNTHETIC, "-1"));
        invalid(() -> known(TargetKind.EQUIPMENT, "v", Quantity.VALVE_OPENING_FRACTION, Knowledge.SYNTHETIC, "-0.1"));
        invalid(() -> known(TargetKind.EQUIPMENT, "v", Quantity.VALVE_OPENING_FRACTION, Knowledge.SYNTHETIC, "1.01"));
    }

    @Test
    void validatesInitialSourceKindIdentityAndEffectiveIntervalAtStateTime() {
        var quantities = List.of(unknown(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE));
        for (String missing : Arrays.asList(null, "", " ")) {
            invalid(() -> new SimulationInitialStateInput(missing, stateSource("r"), START, quantities));
        }
        invalid(() -> new SimulationInitialStateInput("s", null, START, quantities));
        invalid(() -> new SimulationInitialStateInput("s", source(SourceKind.FLUID_MODEL, "r", RECORDED, START, null), START, quantities));
        invalid(() -> new SimulationInitialStateInput("s", stateSource("r"), null, quantities));
        invalid(() -> new SimulationInitialStateInput("s", source(SourceKind.OPERATING_STATE, "r", RECORDED, START.plusNanos(1), null), START, quantities));
        invalid(() -> new SimulationInitialStateInput("s", source(SourceKind.OPERATING_STATE, "r", RECORDED, START.minusSeconds(1), START), START, quantities));
        invalid(() -> new SimulationInitialStateInput("s", source(SourceKind.OPERATING_STATE, "r", START.minusNanos(1), START, null), START, quantities));
        assertEquals("s", new SimulationInitialStateInput(" s ", stateSource("r"), START, quantities).id());
    }

    @Test
    void rejectsFutureInitialValuesOrEvidenceButRetainsDelayedEvidenceAndUnknowns() {
        var future = new SimulationStateQuantityInput(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.ESTIMATED, BigDecimal.ONE, START.plusNanos(1), RECORDED, "e");
        invalid(() -> state(List.of(future)));
        var late = new SimulationStateQuantityInput(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.MEASURED, BigDecimal.ONE, START, RECORDED.plusNanos(1), "e");
        invalid(() -> state(List.of(late)));
        var delayed = known(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.MEASURED, "1");
        assertEquals(RECORDED, state(List.of(delayed)).quantities().get(0).evidenceRecordedAt());
        assertNull(state(List.of(unknown(TargetKind.PIPE, "p", Quantity.PIPE_MASS_FLOW_KILOGRAMS_PER_SECOND))).quantities().get(0).value());
    }

    @Test
    void rejectsMissingInitialListsAndDuplicateNormalizedStructuralKeys() {
        invalid(() -> state(null));
        invalid(() -> state(List.of()));
        var value = unknown(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE);
        invalid(() -> state(Arrays.asList(value, null)));
        invalid(() -> state(List.of(value, unknown(TargetKind.NODE, " n ", Quantity.PRESSURE_PASCALS_ABSOLUTE))));
        assertEquals(3, state(List.of(value, unknown(TargetKind.NODE, "n", Quantity.TEMPERATURE_KELVIN),
                unknown(TargetKind.PIPE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE))).quantities().size());
    }

    @Test
    void acceptsStepAndLinearSchedulesWithExplicitHistoryScenarioForecastAndSyntheticOrigins() {
        for (Interpolation interpolation : Interpolation.values()) {
            var history = point(START, "100000", Origin.TRUSTED_TELEMETRY, START);
            var scenario = point(START.plusSeconds(20), "110000", Origin.SCENARIO, START);
            var forecast = point(START.plusSeconds(40), "105000", Origin.FORECAST, START);
            var synthetic = point(END, "100000", Origin.SYNTHETIC, START);
            var series = series("boundary", TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, interpolation,
                    List.of(history, scenario, forecast, synthetic));
            var schedule = schedule(List.of(series));
            assertEquals(interpolation, schedule.series().get(0).interpolation());
            assertEquals(Origin.FORECAST, schedule.series().get(0).points().get(2).origin());
            assertEquals(END, schedule.endsAt());
        }
    }

    @Test
    void requiresBoundaryIdentityTargetQuantityInterpolationAndAllowedTargets() {
        for (String missing : Arrays.asList(null, "", " ")) {
            invalid(() -> series(missing, TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, Interpolation.LINEAR, boundaryPoints()));
            invalid(() -> series("b", TargetKind.NODE, missing, Quantity.PRESSURE_PASCALS_ABSOLUTE, Interpolation.LINEAR, boundaryPoints()));
        }
        invalid(() -> series("b", null, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, Interpolation.LINEAR, boundaryPoints()));
        invalid(() -> series("b", TargetKind.NODE, "n", null, Interpolation.LINEAR, boundaryPoints()));
        invalid(() -> series("b", TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, null, boundaryPoints()));
        invalid(() -> series("b", TargetKind.PIPE, "p", Quantity.PIPE_MASS_FLOW_KILOGRAMS_PER_SECOND, Interpolation.LINEAR, boundaryPoints()));
        invalid(() -> series("b", TargetKind.NODE, "n", Quantity.COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE, Interpolation.LINEAR, boundaryPoints()));
        var series = series(" b ", TargetKind.NODE, " N ", Quantity.PRESSURE_PASCALS_ABSOLUTE, Interpolation.LINEAR, boundaryPoints());
        assertEquals("b", series.id()); assertEquals("N", series.targetId());
    }

    @Test
    void validatesBoundaryQuantityRangesIncludingControlAndInjectionEdges() {
        for (String pressure : List.of("0", "-1")) {
            invalid(() -> boundary("b", "n", List.of(point(START, pressure, Origin.SYNTHETIC, START), point(END, "1", Origin.SYNTHETIC, START))));
        }
        var signed = List.of(point(START, "-1", Origin.SCENARIO, START), point(END, "0", Origin.FORECAST, START));
        assertEquals(decimal("-1"), series("b", TargetKind.NODE, "n", Quantity.NODE_MASS_INJECTION_KILOGRAMS_PER_SECOND, Interpolation.LINEAR, signed).points().get(0).value());
        var controls = List.of(point(START, "0", Origin.SCENARIO, START), point(END, "1", Origin.SCENARIO, START));
        assertEquals(2, series("v", TargetKind.EQUIPMENT, "v", Quantity.VALVE_OPENING_FRACTION, Interpolation.STEP_PREVIOUS, controls).points().size());
        assertEquals(2, series("c", TargetKind.EQUIPMENT, "c", Quantity.COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE, Interpolation.LINEAR, controls).points().size());
        invalid(() -> series("v", TargetKind.EQUIPMENT, "v", Quantity.VALVE_OPENING_FRACTION, Interpolation.LINEAR,
                List.of(point(START, "1.01", Origin.SCENARIO, START), controls.get(1))));
    }

    @Test
    void rejectsMissingPointFieldsUnsupportedOriginsAndFutureObservations() {
        invalid(() -> point(null, "1", Origin.SCENARIO, START));
        invalid(() -> new Point(START, null, Origin.SCENARIO, START, "e"));
        invalid(() -> point(START, "1", null, START));
        invalid(() -> point(START, "1", Origin.SCENARIO, null));
        for (String missing : Arrays.asList(null, "", " ")) {
            invalid(() -> new Point(START, BigDecimal.ONE, Origin.SCENARIO, START, missing));
        }
        for (Origin origin : List.of(Origin.ESTIMATED, Origin.APPROVED_PARAMETER)) {
            invalid(() -> point(START, "1", origin, START));
        }
        invalid(() -> point(END, "1", Origin.TRUSTED_TELEMETRY, START));
        assertEquals("e", new Point(END, BigDecimal.ONE, Origin.FORECAST, START, " e ").evidenceReference());
    }

    @Test
    void rejectsMissingUndersizedNullDuplicateOrDescendingBoundaryPoints() {
        invalid(() -> boundary("b", "n", null));
        invalid(() -> boundary("b", "n", List.of()));
        invalid(() -> boundary("b", "n", List.of(boundaryPoints().get(0))));
        invalid(() -> boundary("b", "n", Arrays.asList(boundaryPoints().get(0), null)));
        invalid(() -> boundary("b", "n", List.of(boundaryPoints().get(0), boundaryPoints().get(0))));
        invalid(() -> boundary("b", "n", List.of(boundaryPoints().get(1), boundaryPoints().get(0))));
    }

    @Test
    void requiresScheduleIdentitySourceOrderedHorizonAndEffectiveSelection() {
        var series = List.of(boundary("b", "n", boundaryPoints()));
        for (String missing : Arrays.asList(null, "", " ")) {
            invalid(() -> new SimulationBoundaryScheduleInput(missing, scheduleSource("r"), START, END, series));
        }
        invalid(() -> new SimulationBoundaryScheduleInput("s", null, START, END, series));
        invalid(() -> new SimulationBoundaryScheduleInput("s", stateSource("r"), START, END, series));
        invalid(() -> new SimulationBoundaryScheduleInput("s", scheduleSource("r"), null, END, series));
        invalid(() -> new SimulationBoundaryScheduleInput("s", scheduleSource("r"), START, null, series));
        invalid(() -> new SimulationBoundaryScheduleInput("s", scheduleSource("r"), START, START, series));
        invalid(() -> new SimulationBoundaryScheduleInput("s", scheduleSource("r"), END, START, series));
        invalid(() -> new SimulationBoundaryScheduleInput("s", source(SourceKind.BOUNDARY_SCHEDULE, "r", RECORDED, START.plusNanos(1), null), START, END, series));
        invalid(() -> new SimulationBoundaryScheduleInput("s", source(SourceKind.BOUNDARY_SCHEDULE, "r", RECORDED, START.minusSeconds(1), START), START, END, series));
        var selected = new SimulationBoundaryScheduleInput(" s ", source(SourceKind.BOUNDARY_SCHEDULE, "r", RECORDED, START, START.plusSeconds(1)), START, END, series);
        assertEquals("s", selected.id());
        assertEquals(END, selected.endsAt());
    }

    @Test
    void rejectsMissingScheduleSeriesDuplicateIdsAndDuplicateStructuralKeys() {
        invalid(() -> schedule(null)); invalid(() -> schedule(List.of()));
        var boundary = boundary("b", "n", boundaryPoints());
        invalid(() -> schedule(Arrays.asList(boundary, null)));
        invalid(() -> schedule(List.of(boundary, boundary(" b ", "other", boundaryPoints()))));
        invalid(() -> schedule(List.of(boundary, boundary("other", " n ", boundaryPoints()))));
        var temperature = series("temperature", TargetKind.NODE, "n", Quantity.TEMPERATURE_KELVIN, Interpolation.LINEAR,
                List.of(point(START, "300", Origin.SCENARIO, START), point(END, "301", Origin.FORECAST, START)));
        assertEquals(2, schedule(List.of(boundary, temperature)).series().size());
    }

    @Test
    void rejectsMissingHorizonAnchorsAndEvidenceAfterFrozenSourceRevision() {
        invalid(() -> schedule(List.of(boundary("b", "n", List.of(point(START.plusNanos(1), "1", Origin.SCENARIO, START), point(END, "1", Origin.SCENARIO, START))))));
        invalid(() -> schedule(List.of(boundary("b", "n", List.of(point(START, "1", Origin.SCENARIO, START), point(END.minusNanos(1), "1", Origin.SCENARIO, START))))));
        invalid(() -> schedule(List.of(boundary("b", "n", List.of(point(START.minusNanos(1), "1", Origin.SCENARIO, START), point(END, "1", Origin.SCENARIO, START))))));
        invalid(() -> schedule(List.of(boundary("b", "n", List.of(point(START, "1", Origin.SCENARIO, START), point(END.plusNanos(1), "1", Origin.SCENARIO, START))))));
        invalid(() -> schedule(List.of(boundary("b", "n", List.of(point(START, "1", Origin.SCENARIO, START), point(END, "1", Origin.FORECAST, RECORDED.plusNanos(1)))))));
        var history = boundary("b", "n", List.of(point(START, "1", Origin.TRUSTED_TELEMETRY, START), point(END, "1", Origin.TRUSTED_TELEMETRY, END)));
        invalid(() -> schedule(List.of(history)));
        assertEquals(Origin.TRUSTED_TELEMETRY, new SimulationBoundaryScheduleInput("history", source(SourceKind.BOUNDARY_SCHEDULE, "history-r", END, START, null), START, END, List.of(history)).series().get(0).points().get(1).origin());
    }

    @Test
    void defensivelyCopiesInitialQuantitiesBoundaryPointsAndScheduleSeries() {
        var quantities = new ArrayList<>(List.of(unknown(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE)));
        var initial = state(quantities);
        var points = new ArrayList<>(boundaryPoints());
        var boundary = boundary("b", "n", points);
        var series = new ArrayList<>(List.of(boundary));
        var schedule = schedule(series);
        quantities.clear(); points.clear(); series.clear();
        assertEquals(1, initial.quantities().size());
        assertEquals(2, boundary.points().size());
        assertEquals(1, schedule.series().size());
        assertThrows(UnsupportedOperationException.class, () -> initial.quantities().clear());
        assertThrows(UnsupportedOperationException.class, () -> boundary.points().clear());
        assertThrows(UnsupportedOperationException.class, () -> schedule.series().clear());
    }

    @Test
    void replacementRevisionsPreservePriorValuesKnowledgeOriginsAndTimes() {
        var firstState = state(List.of(known(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.MEASURED, "100000")));
        var nextState = new SimulationInitialStateInput("state", stateSource("state-r2"), START,
                List.of(known(TargetKind.NODE, "n", Quantity.PRESSURE_PASCALS_ABSOLUTE, Knowledge.ESTIMATED, "110000")));
        var firstSchedule = schedule(List.of(boundary("b", "n", boundaryPoints())));
        var nextSchedule = new SimulationBoundaryScheduleInput("schedule", scheduleSource("schedule-r2"), START, END,
                List.of(boundary("b", "n", List.of(point(START, "110000", Origin.SCENARIO, START), point(END, "120000", Origin.FORECAST, START)))));
        assertEquals("state-r1", firstState.sourceVersion().revisionId());
        assertEquals("state-r2", nextState.sourceVersion().revisionId());
        assertEquals(Knowledge.MEASURED, firstState.quantities().get(0).knowledge());
        assertEquals(decimal("100000"), firstState.quantities().get(0).value());
        assertEquals(Knowledge.ESTIMATED, nextState.quantities().get(0).knowledge());
        assertEquals("schedule-r1", firstSchedule.sourceVersion().revisionId());
        assertEquals("schedule-r2", nextSchedule.sourceVersion().revisionId());
        assertEquals(Origin.SYNTHETIC, firstSchedule.series().get(0).points().get(1).origin());
        assertEquals(decimal("100000"), firstSchedule.series().get(0).points().get(1).value());
        assertEquals(END, firstSchedule.series().get(0).points().get(1).at());
        assertEquals(Origin.FORECAST, nextSchedule.series().get(0).points().get(1).origin());
    }

    private static BigDecimal decimal(String value) { return new BigDecimal(value); }
    private static SimulationStateQuantityInput known(TargetKind target, String id, Quantity quantity, Knowledge knowledge, String value) {
        return new SimulationStateQuantityInput(target, id, quantity, knowledge, decimal(value), START, RECORDED, "synthetic-evidence");
    }
    private static SimulationStateQuantityInput unknown(TargetKind target, String id, Quantity quantity) {
        return new SimulationStateQuantityInput(target, id, quantity, Knowledge.UNKNOWN, null, null, null, "synthetic-missing-evidence");
    }
    private static SimulationInitialStateInput state(List<SimulationStateQuantityInput> quantities) {
        return new SimulationInitialStateInput("state", stateSource("state-r1"), START, quantities);
    }
    private static Point point(Instant at, String value, Origin origin, Instant recorded) {
        return new Point(at, decimal(value), origin, recorded, "synthetic-evidence");
    }
    private static List<Point> boundaryPoints() { return List.of(point(START, "100000", Origin.SYNTHETIC, START), point(END, "100000", Origin.SYNTHETIC, START)); }
    private static SimulationBoundarySeriesInput boundary(String id, String target, List<Point> points) {
        return series(id, TargetKind.NODE, target, Quantity.PRESSURE_PASCALS_ABSOLUTE, Interpolation.STEP_PREVIOUS, points);
    }
    private static SimulationBoundarySeriesInput series(String id, TargetKind target, String targetId, Quantity quantity, Interpolation interpolation, List<Point> points) {
        return new SimulationBoundarySeriesInput(id, target, targetId, quantity, interpolation, points);
    }
    private static SimulationBoundaryScheduleInput schedule(List<SimulationBoundarySeriesInput> series) {
        return new SimulationBoundaryScheduleInput("schedule", scheduleSource("schedule-r1"), START, END, series);
    }
    private static SimulationInputSourceVersion stateSource(String revision) { return source(SourceKind.OPERATING_STATE, revision, RECORDED, START, null); }
    private static SimulationInputSourceVersion scheduleSource(String revision) { return source(SourceKind.BOUNDARY_SCHEDULE, revision, RECORDED, START, null); }
    private static SimulationInputSourceVersion source(SourceKind kind, String revision, Instant recorded, Instant from, Instant until) {
        return new SimulationInputSourceVersion(kind, "synthetic-owner", "synthetic-source", revision, "a".repeat(64), recorded, from, until, Origin.SYNTHETIC, "synthetic-evidence");
    }
    private static void invalid(org.junit.jupiter.api.function.Executable operation) { assertThrows(InvalidSimulationValueException.class, operation); }
}
