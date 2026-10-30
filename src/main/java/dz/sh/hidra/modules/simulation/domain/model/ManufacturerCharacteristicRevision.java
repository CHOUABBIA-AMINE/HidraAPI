/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerCharacteristicRevision
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Immutable manufacturer-supplied characteristic facts, not certified or approved decisions.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.math.BigDecimal;
import java.time.Instant;
import java.text.Normalizer;
import java.util.*;

/** Documentary source assertions only; a certificate reference is not independent verification. */
public record ManufacturerCharacteristicRevision(
        String sourceId, String revisionId, Instant recordedAt, Instant effectiveFrom, Instant effectiveUntil,
        Origin origin, String manufacturerPartyId, String manufacturerModelReference,
        String documentReference, String documentSha256, ReferenceConditions referenceConditions,
        CertificateEvidence certificate, Family family) {
    public enum Origin { SUPPLIER_DECLARED, SYNTHETIC }
    public enum Kind { COMPRESSOR_MAP, VALVE_MAP, PARAMETER_TABLE, OPERATING_LIMITS }
    public enum Unit { KILOGRAM_PER_SECOND, REVOLUTION_PER_MINUTE, JOULE_PER_KILOGRAM,
        FRACTION, PASCAL_ABSOLUTE, PASCAL_DIFFERENTIAL, KELVIN }
    public enum Quantity {
        MASS_FLOW(Unit.KILOGRAM_PER_SECOND), ROTATIONAL_SPEED(Unit.REVOLUTION_PER_MINUTE),
        SPECIFIC_HEAD(Unit.JOULE_PER_KILOGRAM), EFFICIENCY(Unit.FRACTION),
        OPENING(Unit.FRACTION), ABSOLUTE_PRESSURE(Unit.PASCAL_ABSOLUTE),
        DIFFERENTIAL_PRESSURE(Unit.PASCAL_DIFFERENTIAL), TEMPERATURE(Unit.KELVIN);
        private final Unit unit;
        Quantity(Unit unit) { this.unit = unit; }
        public Unit unit() { return unit; }
    }
    public record Axis(Quantity quantity, Unit unit) {
        public Axis { require(quantity != null && unit == quantity.unit(), "Quantity and SI unit mismatch."); }
    }
    public record ReferenceConditions(BigDecimal pressurePascalsAbsolute, BigDecimal temperatureKelvin,
            String methodReference) {
        public ReferenceConditions {
            positive(pressurePascalsAbsolute); positive(temperatureKelvin);
            methodReference = required(methodReference, 240);
        }
    }
    /** Unverified reference to a manufacturer certificate; its authenticity is assessed outside Package A. */
    public record CertificateEvidence(String issuerReference, String certificateReference, String certificateSha256,
            String sourceId, String revisionId, String sourceDocumentSha256,
            String manufacturerPartyId, String manufacturerModelReference, Kind characteristicKind,
            Instant validFrom, Instant validUntil) {
        public CertificateEvidence {
            issuerReference = required(issuerReference, 240);
            certificateReference = required(certificateReference, 240);
            certificateSha256 = digest(certificateSha256);
            sourceId = required(sourceId, 80); revisionId = required(revisionId, 80);
            sourceDocumentSha256 = digest(sourceDocumentSha256);
            manufacturerPartyId = required(manufacturerPartyId, 80);
            manufacturerModelReference = required(manufacturerModelReference, 240);
            require(characteristicKind != null && validFrom != null
                    && (validUntil == null || validUntil.isAfter(validFrom)), "Certificate scope or dates invalid.");
        }
    }
    public sealed interface Family permits CompressorMap, ValveMap, ParameterTable, OperatingLimits {
        Kind kind();
    }
    public record CompressorMap(List<SpeedLine> speedLines) implements Family {
        public CompressorMap {
            speedLines = items(speedLines, 1, 128);
            BigDecimal last = null;
            for (SpeedLine line : speedLines) {
                ascending(last, line.speedRevolutionsPerMinute());
                last = line.speedRevolutionsPerMinute();
            }
        }
        @Override public Kind kind() { return Kind.COMPRESSOR_MAP; }
    }
    public record SpeedLine(BigDecimal speedRevolutionsPerMinute, List<CompressorPoint> points) {
        public SpeedLine {
            positive(speedRevolutionsPerMinute);
            points = items(points, 2, 1024);
            BigDecimal last = null;
            for (CompressorPoint point : points) {
                ascending(last, point.massFlowKilogramsPerSecond());
                last = point.massFlowKilogramsPerSecond();
            }
        }
    }
    public record CompressorPoint(BigDecimal massFlowKilogramsPerSecond,
            BigDecimal specificHeadJoulesPerKilogram, BigDecimal efficiencyFraction) {
        public CompressorPoint {
            positive(massFlowKilogramsPerSecond); nonnegative(specificHeadJoulesPerKilogram);
            fraction(efficiencyFraction, true);
        }
    }
    public record ValveMap(List<OpeningLine> openingLines) implements Family {
        public ValveMap {
            openingLines = items(openingLines, 1, 128);
            BigDecimal last = null;
            for (OpeningLine line : openingLines) {
                ascending(last, line.openingFraction()); last = line.openingFraction();
            }
        }
        @Override public Kind kind() { return Kind.VALVE_MAP; }
    }
    public record OpeningLine(BigDecimal openingFraction, List<ValvePoint> points) {
        public OpeningLine {
            fraction(openingFraction, false);
            points = items(points, 2, 1024);
            BigDecimal last = null;
            for (ValvePoint point : points) {
                ascending(last, point.differentialPressurePascals());
                last = point.differentialPressurePascals();
            }
        }
    }
    public record ValvePoint(BigDecimal differentialPressurePascals, BigDecimal massFlowKilogramsPerSecond) {
        public ValvePoint { nonnegative(differentialPressurePascals); nonnegative(massFlowKilogramsPerSecond); }
    }
    public record ParameterTable(List<Axis> axes, List<Axis> outputs, List<TableRow> rows) implements Family {
        public ParameterTable {
            axes = items(axes, 1, 16); outputs = items(outputs, 1, 16); rows = items(rows, 1, 4096);
            var seen = new HashSet<Quantity>();
            for (Axis axis : axes) require(seen.add(axis.quantity()), "Duplicate table axis.");
            for (Axis output : outputs) require(seen.add(output.quantity()), "Duplicate table quantity.");
            List<BigDecimal> previous = null;
            for (TableRow row : rows) {
                require(row.coordinates().size() == axes.size() && row.values().size() == outputs.size(),
                        "Table row dimensions differ from declared axes and outputs.");
                for (int i=0;i<axes.size();i++) check(axes.get(i).quantity(),row.coordinates().get(i));
                for (int i=0;i<outputs.size();i++) check(outputs.get(i).quantity(),row.values().get(i));
                require(previous == null || compare(previous,row.coordinates()) < 0,
                        "Table coordinate tuples must be strictly ordered and unique.");
                previous = row.coordinates();
            }
        }
        @Override public Kind kind() { return Kind.PARAMETER_TABLE; }
    }
    public record TableRow(List<BigDecimal> coordinates, List<BigDecimal> values) {
        public TableRow {
            coordinates = items(coordinates, 1, 16); values = items(values, 1, 16);
            for (BigDecimal n : coordinates) number(n);
            for (BigDecimal n : values) number(n);
        }
    }
    public record OperatingLimits(List<Limit> limits) implements Family {
        public OperatingLimits {
            limits = items(limits, 1, 32);
            Quantity previous = null;
            for (Limit limit : limits) {
                require(previous == null || previous.ordinal() < limit.axis().quantity().ordinal(),
                        "Limits must use unique ordered quantity types.");
                previous = limit.axis().quantity();
            }
        }
        @Override public Kind kind() { return Kind.OPERATING_LIMITS; }
    }
    public record Limit(Axis axis, BigDecimal minimumInclusive, BigDecimal maximumInclusive,
            String evidenceReference) {
        public Limit {
            require(axis != null, "Limit axis required.");
            check(axis.quantity(), minimumInclusive); check(axis.quantity(), maximumInclusive);
            require(minimumInclusive.compareTo(maximumInclusive) <= 0, "Limit range invalid.");
            evidenceReference = required(evidenceReference, 240);
        }
    }
    public ManufacturerCharacteristicRevision {
        sourceId = required(sourceId, 80); revisionId = required(revisionId, 80);
        interval(recordedAt, effectiveFrom, effectiveUntil);
        require(origin != null, "Explicit source origin required.");
        manufacturerPartyId = required(manufacturerPartyId, 80);
        manufacturerModelReference = required(manufacturerModelReference, 240);
        documentReference = required(documentReference, 240);
        documentSha256 = digest(documentSha256);
        require(referenceConditions != null && family != null, "Reference conditions and family required.");
        if (certificate != null) {
            require(origin == Origin.SUPPLIER_DECLARED, "Synthetic source cannot assert a certificate.");
            require(sourceId.equals(certificate.sourceId()) && revisionId.equals(certificate.revisionId())
                    && documentSha256.equals(certificate.sourceDocumentSha256())
                    && manufacturerPartyId.equals(certificate.manufacturerPartyId())
                    && manufacturerModelReference.equals(certificate.manufacturerModelReference())
                    && family.kind() == certificate.characteristicKind(), "Certificate does not cover exact supplied revision.");
            require(!effectiveFrom.isBefore(certificate.validFrom())
                    && (certificate.validUntil() == null || (effectiveUntil != null
                            && !effectiveUntil.isAfter(certificate.validUntil()))),
                    "Certificate validity does not cover the source effective interval.");
        }
    }
    public boolean effectiveAt(Instant at) {
        return at != null && !at.isBefore(recordedAt) && !at.isBefore(effectiveFrom)
                && (effectiveUntil == null || at.isBefore(effectiveUntil));
    }
    private static int compare(List<BigDecimal> a, List<BigDecimal> b) {
        for (int i=0;i<a.size();i++) { int v=a.get(i).compareTo(b.get(i)); if (v!=0) return v; }
        return 0;
    }
    private static void check(Quantity q, BigDecimal n) {
        switch(q) {
            case MASS_FLOW, SPECIFIC_HEAD, DIFFERENTIAL_PRESSURE -> nonnegative(n);
            case ROTATIONAL_SPEED, ABSOLUTE_PRESSURE, TEMPERATURE -> positive(n);
            case EFFICIENCY -> fraction(n,true);
            case OPENING -> fraction(n,false);
        }
    }
    private static void interval(Instant recorded, Instant from, Instant until) {
        require(recorded!=null && from!=null && (until==null || until.isAfter(from)), "Invalid source time interval.");
    }
    private static String required(String v,int max) {
        require(v!=null && !v.isBlank() && v.equals(v.strip()) && !v.isEmpty()
                && v.codePointCount(0,v.length())<=max && Normalizer.isNormalized(v,Normalizer.Form.NFC)
                && v.codePoints().noneMatch(cp->Character.isISOControl(cp) || (cp>=0xD800 && cp<=0xDFFF)),
                "Noncanonical or unbounded source reference.");
        return v;
    }
    private static String digest(String v) {
        require(v!=null && v.matches("[0-9a-f]{64}"), "Exact lowercase SHA-256 evidence digest required.");
        return v;
    }
    private static <T> List<T> items(List<T> value,int min,int max) {
        require(value!=null && value.size()>=min && value.size()<=max
                && value.stream().noneMatch(Objects::isNull), "Missing, null or oversized supplied collection.");
        return List.copyOf(value);
    }
    private static void number(BigDecimal n) {
        require(n!=null && n.scale()>=0 && n.scale()<=12 && n.precision()<=40,
                "Supplied decimal scale or precision unsupported.");
    }
    private static void positive(BigDecimal n) { number(n); require(n.signum()>0, "Positive supplied value required."); }
    private static void nonnegative(BigDecimal n) { number(n); require(n.signum()>=0, "Nonnegative supplied value required."); }
    private static void fraction(BigDecimal n,boolean strictlyPositive) {
        nonnegative(n); require(n.compareTo(BigDecimal.ONE)<=0 && (!strictlyPositive || n.signum()>0),
                "Supplied fraction out of range.");
    }
    private static void ascending(BigDecimal prior,BigDecimal current) {
        require(prior==null || prior.compareTo(current)<0,"Duplicate or unordered supplied samples.");
    }
    private static void require(boolean condition,String msg) {
        if(!condition) throw new InvalidSimulationValueException(msg);
    }
}
