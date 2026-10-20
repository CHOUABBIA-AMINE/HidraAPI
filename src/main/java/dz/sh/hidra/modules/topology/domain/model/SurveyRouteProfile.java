/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SurveyRouteProfile
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Preserves supplied survey coordinates, references, precision and section intervals without physical qualification.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/** Draft values only: descriptors are declarations, not resolved or qualified geodetic facts. */
public record SurveyRouteProfile(String branchId, Crs horizontalCrs, VerticalReference verticalReference,
        String chainageOriginReference, Direction direction, String evidenceReference,
        List<Point> points, List<Section> sections) {
    public static final int MAX_POINTS = 10000;
    public static final int MAX_SECTIONS = 10000;

    /** Increasing chainage follows this traversal against the graph branch's from/to orientation. */
    public enum Direction { FROM_TO, TO_FROM }

    public record Axis(String name, String unitIdentifier) {
        public Axis {
            name = EngineeringGraphDraft.identity(name);
            unitIdentifier = EngineeringGraphDraft.identity(unitIdentifier);
        }
    }

    /** Ordered axes and unit identifiers are preserved; no authority catalog is consulted. */
    public record Crs(String authority, String code, List<Axis> axes) {
        public Crs {
            authority = EngineeringGraphDraft.identity(authority);
            code = EngineeringGraphDraft.identity(code);
            axes = copy(axes, 2);
            if (axes.size() != 2 || axes.get(0).name().equals(axes.get(1).name()))
                throw new IllegalArgumentException("Exactly two distinct ordered axes required");
        }
    }

    public record VerticalReference(String datumAuthority, String datumCode, String unitIdentifier) {
        public VerticalReference {
            datumAuthority = EngineeringGraphDraft.identity(datumAuthority);
            datumCode = EngineeringGraphDraft.identity(datumCode);
            unitIdentifier = EngineeringGraphDraft.identity(unitIdentifier);
        }
    }

    /** Coordinates/elevation retain declared units; chainage and its precision are always metres. */
    public record Point(String id, BigDecimal firstCoordinate, BigDecimal secondCoordinate,
            BigDecimal chainageMetres, BigDecimal elevation, BigDecimal coordinatePrecision,
            BigDecimal chainagePrecisionMetres, BigDecimal elevationPrecision) {
        public Point {
            id = EngineeringGraphDraft.identity(id);
            required(firstCoordinate);
            required(secondCoordinate);
            nonnegative(chainageMetres);
            positive(coordinatePrecision);
            positive(chainagePrecisionMetres);
            if (elevation != null) positive(elevationPrecision);
            else if (elevationPrecision != null)
                throw new IllegalArgumentException("Elevation precision requires elevation");
        }
    }

    /** A declared interval, without inferred material, dimensions or hydraulic characteristics. */
    public record Section(String id, BigDecimal startChainageMetres, BigDecimal endChainageMetres,
            String evidenceReference) {
        public Section {
            id = EngineeringGraphDraft.identity(id);
            nonnegative(startChainageMetres);
            required(endChainageMetres);
            if (endChainageMetres.compareTo(startChainageMetres) <= 0)
                throw new IllegalArgumentException("Positive section interval required");
            evidenceReference = reference(evidenceReference);
        }
    }

    public SurveyRouteProfile {
        branchId = EngineeringGraphDraft.identity(branchId);
        required(horizontalCrs);
        chainageOriginReference = reference(chainageOriginReference);
        required(direction);
        evidenceReference = reference(evidenceReference);
        points = copy(points, MAX_POINTS);
        sections = copy(sections, MAX_SECTIONS);
        if (points.size() < 2) throw new IllegalArgumentException("At least two survey points required");

        var pointIds = new HashSet<String>();
        var chainages = new TreeSet<BigDecimal>();
        BigDecimal previous = null;
        for (Point point : points) {
            if (!pointIds.add(point.id())) throw new IllegalArgumentException("Duplicate point identity");
            if (previous != null && point.chainageMetres().compareTo(previous) <= 0)
                throw new IllegalArgumentException("Chainage must strictly increase in declared traversal");
            if (point.elevation() != null && verticalReference == null)
                throw new IllegalArgumentException("Elevation requires explicit vertical reference");
            previous = point.chainageMetres();
            chainages.add(previous);
        }

        var sectionIds = new HashSet<String>();
        BigDecimal previousEnd = null;
        for (Section section : sections) {
            if (!sectionIds.add(section.id())) throw new IllegalArgumentException("Duplicate section identity");
            // Numeric membership deliberately treats 1.0 and 1.00 as the same boundary,
            // while the original values and scales remain untouched in the records.
            if (!chainages.contains(section.startChainageMetres())
                    || !chainages.contains(section.endChainageMetres()))
                throw new IllegalArgumentException("Section boundaries must be retained survey chainages");
            if (previousEnd != null && section.startChainageMetres().compareTo(previousEnd) < 0)
                throw new IllegalArgumentException("Sections must be ordered without overlap");
            previousEnd = section.endChainageMetres();
        }
    }

    private static <T> List<T> copy(List<T> values, int limit) {
        if (values == null || values.size() > limit || values.stream().anyMatch(v -> v == null))
            throw new IllegalArgumentException("Required bounded list without null elements");
        return List.copyOf(values);
    }

    private static void required(Object value) {
        if (value == null) throw new IllegalArgumentException("Required survey value");
    }

    private static void positive(BigDecimal value) {
        required(value);
        if (value.signum() <= 0) throw new IllegalArgumentException("Positive supplied precision required");
    }

    private static void nonnegative(BigDecimal value) {
        required(value);
        if (value.signum() < 0) throw new IllegalArgumentException("Nonnegative chainage required");
    }

    /** Evidence/origin text is retained exactly, without trimming or Unicode normalization. */
    private static String reference(String value) {
        if (value == null || value.isBlank() || value.codePointCount(0, value.length()) > 2048
                || value.indexOf('\0') >= 0)
            throw new IllegalArgumentException("Explicit bounded reference required");
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (Character.isHighSurrogate(c)) {
                if (i + 1 >= value.length() || !Character.isLowSurrogate(value.charAt(++i)))
                    throw new IllegalArgumentException("Malformed reference text");
            } else if (Character.isLowSurrogate(c)) {
                throw new IllegalArgumentException("Malformed reference text");
            }
        }
        return value;
    }
}
