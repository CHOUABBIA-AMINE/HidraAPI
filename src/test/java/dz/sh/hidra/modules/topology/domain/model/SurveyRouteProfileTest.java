/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SurveyRouteProfileTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Tests synthetic survey representation, precision, ordered chainage and immutable section boundaries.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SurveyRouteProfileTest {
    private static BigDecimal d(String s) { return new BigDecimal(s); }
    private static Crs crs() {
        return new Crs("SYNTHETIC", "test-crs", List.of(
                new Axis("northing", "metre"), new Axis("easting", "metre")));
    }
    private static VerticalReference datum() {
        return new VerticalReference("SYNTHETIC", "test-datum", "metre");
    }
    private static Point point(String id, String chainage) {
        return new Point(id, d("10.000"), d("20.0000"), d(chainage), null,
                d("0.01"), d("0.001"), null);
    }
    private static List<Point> points() {
        return List.of(point("p0", "10.00"), point("p1", "20.000"),
                point("p2", "30"), point("p3", "40.0"));
    }
    private static Section section(String id, String from, String to) {
        return new Section(id, d(from), d(to), "section-evidence");
    }
    private static SurveyRouteProfile profile(List<Point> points, List<Section> sections) {
        return new SurveyRouteProfile("pipe-1", crs(), null, "declared-origin", Direction.FROM_TO,
                "synthetic-survey", points, sections);
    }
    private static void invalid(Runnable work) {
        assertThrows(IllegalArgumentException.class, work::run);
    }

    @Test void preservesAxisOrderDecimalsAndIntermediatePoints() {
        var p = profile(points(), List.of(section("s1", "10.0", "20.0")));
        assertEquals(List.of("northing", "easting"), p.horizontalCrs().axes().stream().map(Axis::name).toList());
        assertEquals("metre", p.horizontalCrs().axes().get(0).unitIdentifier());
        assertEquals(points(), p.points());
        assertEquals(d("10.000"), p.points().get(1).firstCoordinate());
        assertEquals(d("20.0000"), p.points().get(1).secondCoordinate());
        assertEquals(d("20.000"), p.points().get(1).chainageMetres());
        assertEquals(d("0.01"), p.points().get(1).coordinatePrecision());
        assertEquals(d("0.001"), p.points().get(1).chainagePrecisionMetres());
        assertEquals(d("10.0"), p.sections().get(0).startChainageMetres());
        var declared = new Crs("UNRESOLVED", "custom-code", List.of(
                new Axis("longitude", "degree"), new Axis("latitude", "degree")));
        var geographic = new SurveyRouteProfile("pipe-1", declared, null, "origin", Direction.FROM_TO,
                "evidence", points(), List.of());
        assertEquals(declared, geographic.horizontalCrs());
        assertEquals(points(), geographic.points());
    }

    @Test void requiresExplicitCrsAxesAndUnits() {
        invalid(() -> new SurveyRouteProfile("pipe", null, null, "origin", Direction.FROM_TO,
                "evidence", points(), List.of()));
        invalid(() -> new Crs(null, "code", crs().axes()));
        invalid(() -> new Crs("authority", " ", crs().axes()));
        invalid(() -> new Crs("authority", "code", null));
        invalid(() -> new Crs("authority", "code", List.of()));
        invalid(() -> new Crs("authority", "code", List.of(new Axis("x", "metre"))));
        invalid(() -> new Crs("authority", "code", List.of(
                new Axis("x", "metre"), new Axis("x", "degree"))));
        invalid(() -> new Crs("authority", "code", List.of(
                new Axis("x", "metre"), new Axis("y", "metre"), new Axis("z", "metre"))));
        invalid(() -> new Crs("authority", "code", Arrays.asList(new Axis("x", "metre"), null)));
        invalid(() -> new Axis("x", null));
        invalid(() -> new Axis("", "metre"));
    }

    @Test void requiresDatumAndPrecisionWhenElevationSupplied() {
        var elevated = new Point("e", d("1"), d("2"), d("10"), d("5.000"),
                d("0.1"), d("0.01"), d("0.001"));
        invalid(() -> profile(List.of(elevated, point("b", "20")), List.of()));
        invalid(() -> new Point("e", d("1"), d("2"), d("10"), d("5"), d("0.1"), d("0.01"), null));
        invalid(() -> new VerticalReference(null, "code", "metre"));
        invalid(() -> new VerticalReference("authority", null, "metre"));
        invalid(() -> new VerticalReference("authority", "code", ""));
        var p = new SurveyRouteProfile("pipe", crs(), datum(), "origin", Direction.FROM_TO,
                "evidence", List.of(elevated, point("b", "20")), List.of());
        assertEquals(datum(), p.verticalReference());
        assertEquals(d("5.000"), p.points().get(0).elevation());
        assertEquals(d("0.001"), p.points().get(0).elevationPrecision());
    }

    @Test void allowsMissingAndNegativeElevationsWithoutDefaults() {
        var missing = profile(points(), List.of());
        assertNull(missing.verticalReference());
        assertTrue(missing.sections().isEmpty());
        assertTrue(missing.points().stream().allMatch(p -> p.elevation() == null && p.elevationPrecision() == null));
        var negative = new Point("a", d("1"), d("2"), d("10"), d("-50.000"),
                d("0.1"), d("0.01"), d("0.001"));
        var feet = new VerticalReference("SYNTHETIC", "local", "foot");
        var p = new SurveyRouteProfile("pipe", crs(), feet, "origin", Direction.TO_FROM,
                "evidence", List.of(negative, point("b", "20")), List.of());
        assertEquals(d("-50.000"), p.points().get(0).elevation());
        assertEquals("foot", p.verticalReference().unitIdentifier());
        assertNull(p.points().get(1).elevation());
        invalid(() -> new Point("a", d("1"), d("2"), d("0"), null, d("1"), d("1"), d("1")));
    }

    @Test void requiresOriginDirectionAndStrictlyIncreasingMetreChainage() {
        for (Direction direction : Direction.values()) {
            var p = new SurveyRouteProfile("pipe", crs(), null, "origin", direction, "evidence", points(), List.of());
            assertEquals(direction, p.direction());
            invalid(() -> new SurveyRouteProfile("pipe", crs(), null, "origin", direction, "evidence",
                    List.of(point("a", "10.0"), point("b", "10.00")), List.of()));
            invalid(() -> new SurveyRouteProfile("pipe", crs(), null, "origin", direction, "evidence",
                    List.of(point("a", "20"), point("b", "10")), List.of()));
        }
        invalid(() -> new SurveyRouteProfile("pipe", crs(), null, null, Direction.FROM_TO, "evidence", points(), List.of()));
        invalid(() -> new SurveyRouteProfile("pipe", crs(), null, "origin", null, "evidence", points(), List.of()));
        invalid(() -> point("a", "-0.1"));
        invalid(() -> profile(List.of(), List.of()));
        invalid(() -> profile(List.of(point("a", "0")), List.of()));
    }

    @Test void allowsNonzeroStartingChainageAndRepeatedCoordinates() {
        for (Direction direction : Direction.values()) {
            var p = new SurveyRouteProfile("pipe", crs(), null, "origin", direction, "evidence", points(), List.of());
            assertEquals(d("10.00"), p.points().get(0).chainageMetres());
            assertEquals(p.points().get(0).firstCoordinate(), p.points().get(1).firstCoordinate());
            assertEquals(p.points().get(0).secondCoordinate(), p.points().get(1).secondCoordinate());
            assertEquals(4, p.points().size());
        }
        var zero = profile(List.of(point("a", "0"), point("b", "1")), List.of());
        assertEquals(d("0"), zero.points().get(0).chainageMetres());
    }

    @Test void rejectsDuplicatePointAndSectionIdentities() {
        invalid(() -> profile(List.of(point("a", "10"), point("a", "20")), List.of()));
        invalid(() -> profile(points(), List.of(section("s", "10", "20"), section("s", "20", "30"))));
        assertEquals(2, profile(points(), List.of(section("s1", "10", "20"), section("s2", "20", "30"))).sections().size());
    }

    @Test void preservesTouchingSectionBoundariesAndPermitsGaps() {
        var touching = List.of(section("s1", "10.0", "20.00"), section("s2", "20.0000", "30.0"));
        assertEquals(touching, profile(points(), touching).sections());
        var gaps = List.of(section("s1", "10", "20"), section("s2", "30", "40"));
        var p = profile(points(), gaps);
        assertEquals(gaps, p.sections());
        assertEquals("section-evidence", p.sections().get(1).evidenceReference());
        assertEquals(4, p.points().size());
    }

    @Test void rejectsOverlappingReversedAndOutOfRangeSections() {
        invalid(() -> section("s", "20", "20.00"));
        invalid(() -> section("s", "20", "10"));
        invalid(() -> section("s", "-1", "20"));
        invalid(() -> profile(points(), List.of(section("s1", "10", "30"), section("s2", "20", "40"))));
        invalid(() -> profile(points(), List.of(section("s1", "30", "40"), section("s2", "10", "20"))));
        invalid(() -> profile(points(), List.of(section("s", "0", "20"))));
        invalid(() -> profile(points(), List.of(section("s", "20", "50"))));
    }

    @Test void requiresSectionBoundariesInRetainedPoints() {
        invalid(() -> profile(points(), List.of(section("s", "15", "20"))));
        invalid(() -> profile(points(), List.of(section("s", "10", "25"))));
        assertEquals(d("20.0000"), profile(points(), List.of(section("s", "10.000", "20.0000")))
                .sections().get(0).endChainageMetres());
    }

    @Test void rejectsMissingInvalidAndOversizedMetadata() {
        for (String bad : Arrays.asList(null, "", " ", " padded", "x\0y", "e\u0301", "\uD800", "\uDC00", "é".repeat(41))) {
            invalid(() -> new SurveyRouteProfile(bad, crs(), null, "origin", Direction.FROM_TO, "evidence", points(), List.of()));
            invalid(() -> new Axis(bad, "metre"));
            invalid(() -> new Axis("x", bad));
            invalid(() -> new Crs(bad, "code", crs().axes()));
            invalid(() -> new Crs("authority", bad, crs().axes()));
            invalid(() -> new VerticalReference(bad, "code", "metre"));
            invalid(() -> new VerticalReference("authority", bad, "metre"));
            invalid(() -> new VerticalReference("authority", "code", bad));
            invalid(() -> point(bad, "0"));
            invalid(() -> section(bad, "10", "20"));
        }
        assertEquals("é".repeat(40), new Axis("é".repeat(40), "metre").name());
        for (String bad : Arrays.asList(null, "", " ", "x\0y", "\uD800", "\uDC00", "a".repeat(2049), "😀".repeat(2049))) {
            invalid(() -> new SurveyRouteProfile("pipe", crs(), null, bad, Direction.FROM_TO, "evidence", points(), List.of()));
            invalid(() -> new SurveyRouteProfile("pipe", crs(), null, "origin", Direction.FROM_TO, bad, points(), List.of()));
            invalid(() -> new Section("s", d("10"), d("20"), bad));
        }
        String retained = " origin-e\u0301-😀 ";
        String limit = "😀".repeat(2048);
        var p = new SurveyRouteProfile("pipe", crs(), null, retained, Direction.FROM_TO, limit, points(),
                List.of(new Section("s", d("10"), d("20"), limit)));
        assertEquals(retained, p.chainageOriginReference());
        assertEquals(limit, p.evidenceReference());
        assertEquals(limit, p.sections().get(0).evidenceReference());
    }

    @Test void rejectsNullElementsAndExcessiveCollectionSizes() {
        invalid(() -> profile(null, List.of()));
        invalid(() -> profile(points(), null));
        invalid(() -> profile(Arrays.asList(point("a", "0"), null), List.of()));
        invalid(() -> profile(points(), Arrays.asList((Section) null)));
        invalid(() -> new Point("p", null, d("2"), d("0"), null, d("1"), d("1"), null));
        invalid(() -> new Point("p", d("1"), null, d("0"), null, d("1"), d("1"), null));
        invalid(() -> new Point("p", d("1"), d("2"), null, null, d("1"), d("1"), null));
        invalid(() -> new Section("s", null, d("20"), "evidence"));
        invalid(() -> new Section("s", d("10"), null, "evidence"));
        var atLimit = IntStream.range(0, SurveyRouteProfile.MAX_POINTS)
                .mapToObj(i -> point("p" + i, Integer.toString(i))).toList();
        var intervals = IntStream.range(0, SurveyRouteProfile.MAX_POINTS - 1)
                .mapToObj(i -> section("s" + i, Integer.toString(i), Integer.toString(i + 1))).toList();
        assertEquals(SurveyRouteProfile.MAX_POINTS, profile(atLimit, intervals).points().size());
        assertEquals(SurveyRouteProfile.MAX_POINTS - 1, profile(atLimit, intervals).sections().size());
        var tooMany = new ArrayList<>(atLimit);
        tooMany.add(point("last", "10000"));
        invalid(() -> profile(tooMany, List.of()));
        invalid(() -> profile(points(), Collections.nCopies(SurveyRouteProfile.MAX_SECTIONS + 1, section("s", "10", "20"))));
    }

    @Test void defensivelyCopiesAllCollections() {
        var axes = new ArrayList<>(crs().axes());
        var horizontal = new Crs("authority", "code", axes);
        var sourcePoints = new ArrayList<>(points());
        var sourceSections = new ArrayList<>(List.of(section("s", "10", "20")));
        var p = new SurveyRouteProfile("pipe", horizontal, null, "origin", Direction.FROM_TO,
                "evidence", sourcePoints, sourceSections);
        axes.clear(); sourcePoints.clear(); sourceSections.clear();
        assertEquals(2, p.horizontalCrs().axes().size());
        assertEquals(4, p.points().size());
        assertEquals(1, p.sections().size());
        assertThrows(UnsupportedOperationException.class, () -> p.points().clear());
        assertThrows(UnsupportedOperationException.class, () -> p.sections().clear());
        assertThrows(UnsupportedOperationException.class, () -> p.horizontalCrs().axes().clear());
    }

    @Test void rejectsZeroOrNegativeSuppliedPrecision() {
        for (BigDecimal bad : Arrays.asList(null, d("0"), d("-0.001"))) {
            invalid(() -> new Point("p", d("1"), d("2"), d("0"), null, bad, d("1"), null));
            invalid(() -> new Point("p", d("1"), d("2"), d("0"), null, d("1"), bad, null));
            invalid(() -> new Point("p", d("1"), d("2"), d("0"), d("3"), d("1"), d("1"), bad));
        }
    }
}
