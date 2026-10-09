/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationFluidEquipmentInputTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Verifies synthetic gas and equipment payload validity, references and nested immutability.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationGasFluidInput.Component;
import dz.sh.hidra.modules.simulation.domain.model.SimulationCompressorCurveInput.Point;
import dz.sh.hidra.modules.simulation.domain.model.SimulationCompressorCurveInput.SpeedLine;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentInput.CompressorConfiguration;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentInput.ValveConfiguration;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentInput.Kind;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.Origin;
import dz.sh.hidra.modules.simulation.domain.model.SimulationInputSourceVersion.SourceKind;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationFluidEquipmentInputTest {
    private static final Instant TIME = Instant.parse("2026-10-09T12:00:00Z");

    @Test
    void acceptsCompleteSingleAndMultipleComponentMoleFractionsWithDifferentScales() {
        var pure = fluid("fluid-r1", List.of(component("synthetic-A", "1.00")));
        assertEquals(new BigDecimal("1.00"), pure.components().get(0).moleFraction());
        var mixture = fluid("fluid-r1", List.of(component("synthetic-A", "0.750"), component("synthetic-B", "0.25")));
        assertEquals(2, mixture.components().size());
        assertEquals(Origin.SYNTHETIC, mixture.sourceVersion().origin());
    }

    @Test
    void normalizesFluidIdentityMethodAndComponentReferences() {
        var input = fluidFields(new String[]{" fluid ", " product ", " method ", " method-r1 ", " evidence "},
                source(SourceKind.FLUID_MODEL, "fluid-r1"), List.of(component(" A ", "1")));
        assertEquals("fluid", input.id());
        assertEquals("product", input.productReference());
        assertEquals("method", input.propertyMethodReference());
        assertEquals("method-r1", input.propertyMethodRevisionId());
        assertEquals("evidence", input.propertyMethodEvidenceReference());
        assertEquals("A", input.components().get(0).componentReference());
    }

    @Test
    void rejectsMissingFluidIdentityMethodEvidenceOrSource() {
        for (int position = 0; position < 5; position++) {
            for (String missing : Arrays.asList(null, "", " ")) {
                var fields = new String[]{"fluid", "product", "method", "method-r1", "evidence"};
                fields[position] = missing;
                invalid(() -> fluidFields(fields, source(SourceKind.FLUID_MODEL, "fluid-r1"), List.of(component("A", "1"))));
            }
        }
        invalid(() -> fluidFields(fluidFields(), null, List.of(component("A", "1"))));
        invalid(() -> fluidFields(fluidFields(), source(SourceKind.EQUIPMENT_PARAMETERS, "r"), List.of(component("A", "1"))));
    }

    @Test
    void rejectsMissingDuplicateOrIncompleteCompositionWithoutNormalizing() {
        invalid(() -> fluid("r", null));
        invalid(() -> fluid("r", List.of()));
        invalid(() -> fluid("r", Arrays.asList(component("A", "1"), null)));
        invalid(() -> fluid("r", List.of(component("A", "0.5"), component(" A ", "0.5"))));
        invalid(() -> fluid("r", List.of(component("A", "0.99999"))));
        invalid(() -> fluid("r", List.of(component("A", "0.5"), component("B", "0.50001"))));
        for (String id : Arrays.asList(null, "", " ")) {
            invalid(() -> new Component(id, BigDecimal.ONE));
        }
        for (BigDecimal fraction : Arrays.asList(null, decimal("-0.1"), BigDecimal.ZERO, decimal("1.00001"))) {
            invalid(() -> new Component("A", fraction));
        }
    }

    @Test
    void acceptsMultiSpeedMapsWithNonmonotonicHeadAndExplicitBases() {
        var points = List.of(point("1", "10", "0.75"), point("2", "12", "0.80"), point("3", "0", "1.0"));
        var lines = List.of(new SpeedLine(decimal("1000"), points), new SpeedLine(decimal("2000"), points));
        var curve = curve("curve", "curve-r1", lines);
        assertEquals(lines, curve.speedLines());
        assertEquals(decimal("12"), curve.speedLines().get(0).points().get(1).specificHeadJoulesPerKilogram());
        assertEquals("synthetic-head-definition", curve.headDefinitionReference());
        assertEquals("synthetic-efficiency-definition", curve.efficiencyDefinitionReference());
        assertEquals("synthetic-interpolation", curve.interpolationMethodReference());
        assertEquals(decimal("100000"), curve.referenceInletPressurePascalsAbsolute());
        assertEquals(decimal("300"), curve.referenceInletTemperatureKelvin());
    }

    @Test
    void rejectsMissingCurveReferencesSourcesAndWrongSourceKinds() {
        for (int position = 0; position < 6; position++) {
            for (String missing : Arrays.asList(null, "", " ")) {
                var fields = curveFields();
                fields[position] = missing;
                invalid(() -> curveFields(fields, source(SourceKind.EQUIPMENT_PARAMETERS, "curve-r1"),
                        decimal("100000"), decimal("300"), lines()));
            }
        }
        invalid(() -> curveFields(curveFields(), null, decimal("100000"), decimal("300"), lines()));
        invalid(() -> curveFields(curveFields(), source(SourceKind.FLUID_MODEL, "r"), decimal("100000"), decimal("300"), lines()));
        var fields = Arrays.stream(curveFields()).map(value -> " " + value + " ").toArray(String[]::new);
        var curve = curveFields(fields, source(SourceKind.EQUIPMENT_PARAMETERS, "curve-r1"),
                decimal("100000"), decimal("300"), lines());
        assertEquals("curve", curve.id());
        assertEquals("fluid", curve.fluidInputId());
        assertEquals("fluid-r1", curve.fluidRevisionId());
        assertEquals("synthetic-head-definition", curve.headDefinitionReference());
        assertEquals("synthetic-efficiency-definition", curve.efficiencyDefinitionReference());
        assertEquals("synthetic-interpolation", curve.interpolationMethodReference());
    }

    @Test
    void rejectsMissingNonpositiveReferencePressureAndTemperature() {
        for (BigDecimal value : Arrays.asList(null, BigDecimal.ZERO, decimal("-1"))) {
            invalid(() -> curveFields(curveFields(), source(SourceKind.EQUIPMENT_PARAMETERS, "r"), value, decimal("300"), lines()));
            invalid(() -> curveFields(curveFields(), source(SourceKind.EQUIPMENT_PARAMETERS, "r"), decimal("100000"), value, lines()));
        }
    }

    @Test
    void rejectsMissingEmptyNullDuplicateAndDescendingSpeedLines() {
        invalid(() -> curve("curve", "r", null));
        invalid(() -> curve("curve", "r", List.of()));
        invalid(() -> curve("curve", "r", Arrays.asList(lines().get(0), null)));
        invalid(() -> curve("curve", "r", List.of(line("1000"), line("1000.00"))));
        invalid(() -> curve("curve", "r", List.of(line("2000"), line("1000"))));
        for (BigDecimal speed : Arrays.asList(null, BigDecimal.ZERO, decimal("-1"))) {
            invalid(() -> new SpeedLine(speed, points()));
        }
    }

    @Test
    void rejectsMissingUndersizedNullDuplicateAndDescendingCurvePoints() {
        invalid(() -> new SpeedLine(decimal("1000"), null));
        invalid(() -> new SpeedLine(decimal("1000"), List.of()));
        invalid(() -> new SpeedLine(decimal("1000"), List.of(point("1", "10", "0.8"))));
        invalid(() -> new SpeedLine(decimal("1000"), Arrays.asList(points().get(0), null)));
        invalid(() -> new SpeedLine(decimal("1000"), List.of(point("1", "10", "0.8"), point("1.00", "9", "0.8"))));
        invalid(() -> new SpeedLine(decimal("1000"), List.of(point("2", "9", "0.8"), point("1", "10", "0.8"))));
    }

    @Test
    void rejectsInvalidPointQuantitiesAndAcceptsHeadAndEfficiencyBoundaries() {
        for (BigDecimal value : Arrays.asList(null, BigDecimal.ZERO, decimal("-1"))) {
            invalid(() -> new Point(value, decimal("10"), decimal("0.8")));
        }
        invalid(() -> new Point(BigDecimal.ONE, null, decimal("0.8")));
        invalid(() -> new Point(BigDecimal.ONE, decimal("-1"), decimal("0.8")));
        for (BigDecimal value : Arrays.asList(null, BigDecimal.ZERO, decimal("-0.1"), decimal("1.0001"))) {
            invalid(() -> new Point(BigDecimal.ONE, BigDecimal.TEN, value));
        }
        assertEquals(BigDecimal.ZERO, point("1", "0", "1").specificHeadJoulesPerKilogram());
        assertEquals(BigDecimal.ONE, point("1", "0", "1").efficiencyFraction());
    }

    @Test
    void acceptsBothEquipmentKindsAndFullyOpenAndClosedValveConfigurations() {
        var compressor = compressor("compressor", "curve", "curve-r1");
        assertEquals(Kind.COMPRESSOR, compressor.kind());
        assertEquals(decimal("1000"), compressor.compressor().configuredSpeedRevolutionsPerMinute());
        assertEquals(BigDecimal.ZERO, valve("closed", "0").valve().configuredOpeningFraction());
        assertEquals(BigDecimal.ONE, valve("open", "1").valve().configuredOpeningFraction());
        assertEquals("b", valve("valve", "0.5").toNodeId());
    }

    @Test
    void requiresExclusiveConfigurationAndDistinctNormalizedEndpoints() {
        var compressor = configuration("curve", "curve-r1");
        var valve = valveConfiguration("0.5");
        invalid(() -> new SimulationEquipmentInput("e", "a", "b", null, compressor, null));
        for (Kind kind : Kind.values()) {
            invalid(() -> new SimulationEquipmentInput("e", "a", "b", kind, null, null));
            invalid(() -> new SimulationEquipmentInput("e", "a", "b", kind, compressor, valve));
        }
        invalid(() -> new SimulationEquipmentInput("e", "a", "b", Kind.COMPRESSOR, null, valve));
        invalid(() -> new SimulationEquipmentInput("e", "a", "b", Kind.VALVE, compressor, null));
        invalid(() -> new SimulationEquipmentInput("e", " a ", "a", Kind.COMPRESSOR, compressor, null));
    }

    @Test
    void rejectsMissingEquipmentIdentitiesAndConfigurationReferences() {
        for (String missing : Arrays.asList(null, "", " ")) {
            invalid(() -> new SimulationEquipmentInput(missing, "a", "b", Kind.COMPRESSOR, configuration("curve", "r"), null));
            invalid(() -> new SimulationEquipmentInput("e", missing, "b", Kind.COMPRESSOR, configuration("curve", "r"), null));
            invalid(() -> new SimulationEquipmentInput("e", "a", missing, Kind.COMPRESSOR, configuration("curve", "r"), null));
            invalid(() -> configuration(missing, "r"));
            invalid(() -> configuration("curve", missing));
            invalid(() -> new ValveConfiguration(missing, "r", "evidence", BigDecimal.ZERO));
            invalid(() -> new ValveConfiguration("characteristic", missing, "evidence", BigDecimal.ZERO));
            invalid(() -> new ValveConfiguration("characteristic", "r", missing, BigDecimal.ZERO));
        }
        var equipment = new SimulationEquipmentInput(" e ", " a ", " b ", Kind.COMPRESSOR, configuration(" curve ", " r "), null);
        assertEquals("e", equipment.id());
        assertEquals("a", equipment.fromNodeId());
        assertEquals("b", equipment.toNodeId());
        assertEquals("curve", equipment.compressor().curveId());
        assertEquals("r", equipment.compressor().curveRevisionId());
        var v = new ValveConfiguration(" characteristic ", " r ", " evidence ", BigDecimal.ZERO);
        assertEquals("characteristic", v.characteristicReference());
        assertEquals("r", v.characteristicRevisionId());
        assertEquals("evidence", v.characteristicEvidenceReference());
    }

    @Test
    void rejectsMissingAndOutOfRangeConfigurationValues() {
        for (BigDecimal speed : Arrays.asList(null, BigDecimal.ZERO, decimal("-1"))) {
            invalid(() -> new CompressorConfiguration("curve", "r", speed));
        }
        for (BigDecimal opening : Arrays.asList(null, decimal("-0.001"), decimal("1.001"))) {
            invalid(() -> new ValveConfiguration("characteristic", "r", "evidence", opening));
        }
    }

    @Test
    void acceptsExplicitEmptyModelSharedCurveAndUnusedDeclaredCurve() {
        var empty = model("model-r1", List.of(), List.of());
        assertTrue(empty.equipment().isEmpty());
        assertTrue(empty.compressorCurves().isEmpty());
        var curves = List.of(curve("curve", "curve-r1", lines()), curve("unused", "other-r1", lines()));
        var model = model("model-r1", List.of(compressor("c1", "curve", "curve-r1"),
                compressor("c2", "curve", "curve-r1"), valve("v", "0.5")), curves);
        assertEquals(3, model.equipment().size());
        assertEquals(2, model.compressorCurves().size());
        assertTrue(model("r", List.of(valve("v", "1")), List.of()).compressorCurves().isEmpty());
    }

    @Test
    void rejectsMissingModelIdentitySourceListsAndNullEntries() {
        for (String missing : Arrays.asList(null, "", " ")) {
            invalid(() -> new SimulationEquipmentModelInput(missing, source(SourceKind.EQUIPMENT_PARAMETERS, "r"), List.of(), List.of()));
        }
        invalid(() -> new SimulationEquipmentModelInput("model", null, List.of(), List.of()));
        invalid(() -> new SimulationEquipmentModelInput("model", source(SourceKind.FLUID_MODEL, "r"), List.of(), List.of()));
        invalid(() -> model("r", null, List.of()));
        invalid(() -> model("r", List.of(), null));
        invalid(() -> model("r", Arrays.asList(valve("v", "0"), null), List.of()));
        invalid(() -> model("r", List.of(valve("v", "0")), Arrays.asList(curve("curve", "r", lines()), null)));
        invalid(() -> model("r", List.of(), List.of(curve("curve", "r", lines()))));
        assertEquals("model", new SimulationEquipmentModelInput(" model ", source(SourceKind.EQUIPMENT_PARAMETERS, "r"), List.of(), List.of()).id());
    }

    @Test
    void rejectsDuplicateEquipmentOrCurveIdsAndMissingOrRevisedCurveReferences() {
        invalid(() -> model("r", List.of(valve("v", "0"), valve(" v ", "1")), List.of()));
        invalid(() -> model("r", List.of(valve("v", "0")), List.of(curve("curve", "r1", lines()), curve(" curve ", "r2", lines()))));
        invalid(() -> model("r", List.of(compressor("c", "missing", "r")), List.of(curve("curve", "r", lines()))));
        invalid(() -> model("r", List.of(compressor("c", "curve", "old-r")), List.of(curve("curve", "new-r", lines()))));
    }

    @Test
    void defensivelyCopiesEveryNestedListAndExposesUnmodifiableLists() {
        var components = new ArrayList<>(List.of(component("A", "1")));
        var fluid = fluid("r", components);
        var points = new ArrayList<>(points());
        var line = new SpeedLine(decimal("1000"), points);
        var speedLines = new ArrayList<>(List.of(line));
        var curve = curve("curve", "r", speedLines);
        var equipment = new ArrayList<>(List.of(compressor("c", "curve", "r")));
        var curves = new ArrayList<>(List.of(curve));
        var model = model("r", equipment, curves);
        components.clear(); points.clear(); speedLines.clear(); equipment.clear(); curves.clear();
        assertEquals(1, fluid.components().size());
        assertEquals(2, line.points().size());
        assertEquals(1, curve.speedLines().size());
        assertEquals(1, model.equipment().size());
        assertEquals(1, model.compressorCurves().size());
        assertThrows(UnsupportedOperationException.class, () -> fluid.components().clear());
        assertThrows(UnsupportedOperationException.class, () -> line.points().clear());
        assertThrows(UnsupportedOperationException.class, () -> curve.speedLines().clear());
        assertThrows(UnsupportedOperationException.class, () -> model.equipment().clear());
        assertThrows(UnsupportedOperationException.class, () -> model.compressorCurves().clear());
    }

    @Test
    void replacementRevisionsPreserveEarlierCompositionCurveAndEquipment() {
        var firstFluid = fluid("fluid-r1", List.of(component("A", "1")));
        var secondFluid = fluid("fluid-r2", List.of(component("A", "0.8"), component("B", "0.2")));
        var firstCurve = curve("curve", "curve-r1", lines());
        var secondCurve = curveFields(new String[]{"curve", "fluid", "fluid-r2", "synthetic-head-definition",
                "synthetic-efficiency-definition", "synthetic-interpolation"}, source(SourceKind.EQUIPMENT_PARAMETERS, "curve-r2"),
                decimal("200000"), decimal("310"), List.of(new SpeedLine(decimal("2000"), points())));
        var first = model("model-r1", List.of(compressor("c", "curve", "curve-r1")), List.of(firstCurve));
        var second = model("model-r2", List.of(compressor("c", "curve", "curve-r2")), List.of(secondCurve));
        assertEquals("fluid-r1", firstFluid.sourceVersion().revisionId());
        assertEquals(BigDecimal.ONE, firstFluid.components().get(0).moleFraction());
        assertEquals("fluid-r2", secondFluid.sourceVersion().revisionId());
        assertEquals("model-r1", first.sourceVersion().revisionId());
        assertEquals("model-r2", second.sourceVersion().revisionId());
        assertEquals("curve-r1", first.equipment().get(0).compressor().curveRevisionId());
        assertEquals("curve-r2", second.equipment().get(0).compressor().curveRevisionId());
        assertEquals(decimal("100000"), first.compressorCurves().get(0).referenceInletPressurePascalsAbsolute());
        assertEquals(decimal("200000"), second.compressorCurves().get(0).referenceInletPressurePascalsAbsolute());
    }

    private static BigDecimal decimal(String value) { return new BigDecimal(value); }
    private static Component component(String id, String fraction) { return new Component(id, decimal(fraction)); }
    private static String[] fluidFields() { return new String[]{"fluid", "synthetic-product", "synthetic-method", "method-r1", "synthetic-method-evidence"}; }
    private static SimulationGasFluidInput fluid(String revision, List<Component> components) {
        return fluidFields(fluidFields(), source(SourceKind.FLUID_MODEL, revision), components);
    }
    private static SimulationGasFluidInput fluidFields(String[] fields, SimulationInputSourceVersion source, List<Component> components) {
        return new SimulationGasFluidInput(fields[0], source, fields[1], fields[2], fields[3], fields[4], components);
    }
    private static Point point(String flow, String head, String efficiency) { return new Point(decimal(flow), decimal(head), decimal(efficiency)); }
    private static List<Point> points() { return List.of(point("1", "10", "0.8"), point("2", "9", "0.75")); }
    private static SpeedLine line(String speed) { return new SpeedLine(decimal(speed), points()); }
    private static List<SpeedLine> lines() { return List.of(line("1000")); }
    private static String[] curveFields() { return new String[]{"curve", "fluid", "fluid-r1", "synthetic-head-definition", "synthetic-efficiency-definition", "synthetic-interpolation"}; }
    private static SimulationCompressorCurveInput curve(String id, String revision, List<SpeedLine> lines) {
        var fields = curveFields(); fields[0] = id;
        return curveFields(fields, source(SourceKind.EQUIPMENT_PARAMETERS, revision), decimal("100000"), decimal("300"), lines);
    }
    private static SimulationCompressorCurveInput curveFields(String[] fields, SimulationInputSourceVersion source,
            BigDecimal pressure, BigDecimal temperature, List<SpeedLine> lines) {
        return new SimulationCompressorCurveInput(fields[0], source, fields[1], fields[2], fields[3], fields[4], fields[5], pressure, temperature, lines);
    }
    private static CompressorConfiguration configuration(String curve, String revision) { return new CompressorConfiguration(curve, revision, decimal("1000")); }
    private static SimulationEquipmentInput compressor(String id, String curve, String revision) {
        return new SimulationEquipmentInput(id, "a", "b", Kind.COMPRESSOR, configuration(curve, revision), null);
    }
    private static ValveConfiguration valveConfiguration(String opening) { return new ValveConfiguration("synthetic-characteristic", "characteristic-r1", "synthetic-evidence", decimal(opening)); }
    private static SimulationEquipmentInput valve(String id, String opening) {
        return new SimulationEquipmentInput(id, "a", "b", Kind.VALVE, null, valveConfiguration(opening));
    }
    private static SimulationEquipmentModelInput model(String revision, List<SimulationEquipmentInput> equipment, List<SimulationCompressorCurveInput> curves) {
        return new SimulationEquipmentModelInput("model", source(SourceKind.EQUIPMENT_PARAMETERS, revision), equipment, curves);
    }
    private static SimulationInputSourceVersion source(SourceKind kind, String revision) {
        return new SimulationInputSourceVersion(kind, "synthetic-owner", "synthetic-source", revision, "a".repeat(64),
                TIME, TIME, null, Origin.SYNTHETIC, "synthetic-evidence");
    }
    private static void invalid(org.junit.jupiter.api.function.Executable operation) { assertThrows(InvalidSimulationValueException.class, operation); }
}
