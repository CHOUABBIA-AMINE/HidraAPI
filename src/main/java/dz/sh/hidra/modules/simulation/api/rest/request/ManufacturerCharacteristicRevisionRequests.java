package dz.sh.hidra.modules.simulation.api.rest.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/** Explicit V1 transport values: documentary claims, never native permissions. */
public final class ManufacturerCharacteristicRevisionRequests {
    private ManufacturerCharacteristicRevisionRequests() {}

    // Transport-owned vocabularies; conversion to domain happens only in the mapper.
    public enum Origin { SUPPLIER_DECLARED, SYNTHETIC }
    public enum Kind { COMPRESSOR_MAP, VALVE_MAP, PARAMETER_TABLE, OPERATING_LIMITS }
    public enum Unit { KILOGRAM_PER_SECOND, REVOLUTION_PER_MINUTE, JOULE_PER_KILOGRAM,
        FRACTION, PASCAL_ABSOLUTE, PASCAL_DIFFERENTIAL, KELVIN }
    public enum Quantity { MASS_FLOW, ROTATIONAL_SPEED, SPECIFIC_HEAD, EFFICIENCY,
        OPENING, ABSOLUTE_PRESSURE, DIFFERENTIAL_PRESSURE, TEMPERATURE }

    public record Save(@NotBlank String companyPartyId, @Valid @NotNull Revision revision) {}
    public record Qualify(@NotBlank String companyPartyId, @Valid @NotNull Approval approval) {}
    public record Approval(String definitionId, int definitionVersion, String targetTypeId,
            String purposeId, String instanceId, String taskId, String actionId) {}
    public record Revision(String sourceId, String revisionId, Instant recordedAt, Instant effectiveFrom,
            Instant effectiveUntil, Origin origin, String manufacturerPartyId,
            String manufacturerModelReference, String documentReference, String documentSha256,
            @Valid @NotNull ReferenceConditions referenceConditions, @Valid Certificate certificate,
            @Valid @NotNull Family family) {}
    public record ReferenceConditions(BigDecimal pressurePascalsAbsolute, BigDecimal temperatureKelvin,
            String methodReference) {}
    public record Certificate(String issuerReference, String certificateReference, String certificateSha256,
            String sourceId, String revisionId, String sourceDocumentSha256, String manufacturerPartyId,
            String manufacturerModelReference, Kind characteristicKind,
            Instant validFrom, Instant validUntil) {}
    public record Axis(Quantity quantity,
            Unit unit) {}
    public record SpeedLine(BigDecimal speedRevolutionsPerMinute, List<CompressorPoint> points) {}
    public record CompressorPoint(BigDecimal massFlowKilogramsPerSecond,
            BigDecimal specificHeadJoulesPerKilogram, BigDecimal efficiencyFraction) {}
    public record OpeningLine(BigDecimal openingFraction, List<ValvePoint> points) {}
    public record ValvePoint(BigDecimal differentialPressurePascals, BigDecimal massFlowKilogramsPerSecond) {}
    public record TableRow(List<BigDecimal> coordinates, List<BigDecimal> values) {}
    public record Limit(Axis axis, BigDecimal minimumInclusive, BigDecimal maximumInclusive,
            String evidenceReference) {}

    @JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="kind")
    @JsonSubTypes({
        @JsonSubTypes.Type(value=CompressorMap.class,name="COMPRESSOR_MAP"),
        @JsonSubTypes.Type(value=ValveMap.class,name="VALVE_MAP"),
        @JsonSubTypes.Type(value=ParameterTable.class,name="PARAMETER_TABLE"),
        @JsonSubTypes.Type(value=OperatingLimits.class,name="OPERATING_LIMITS")
    })
    public sealed interface Family permits CompressorMap, ValveMap, ParameterTable, OperatingLimits {}
    public record CompressorMap(List<SpeedLine> speedLines) implements Family {}
    public record ValveMap(List<OpeningLine> openingLines) implements Family {}
    public record ParameterTable(List<Axis> axes, List<Axis> outputs, List<TableRow> rows) implements Family {}
    public record OperatingLimits(List<Limit> limits) implements Family {}
}
