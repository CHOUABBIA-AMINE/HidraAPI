package dz.sh.hidra.modules.simulation.api.rest.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/** Explicit V1 transport values: documentary claims, never native permissions. */
public final class ManufacturerCharacteristicRevisionRequests {
    private ManufacturerCharacteristicRevisionRequests() {}

    public record Save(@NotBlank String companyPartyId, @Valid @NotNull Revision revision) {}
    public record Qualify(@NotBlank String companyPartyId, @Valid @NotNull Approval approval) {}
    public record Approval(String definitionId, int definitionVersion, String targetTypeId,
            String purposeId, String instanceId, String taskId, String actionId) {}
    public record Revision(String sourceId, String revisionId, Instant recordedAt, Instant effectiveFrom,
            Instant effectiveUntil, ManufacturerCharacteristicRevision.Origin origin, String manufacturerPartyId,
            String manufacturerModelReference, String documentReference, String documentSha256,
            @Valid @NotNull ReferenceConditions referenceConditions, @Valid Certificate certificate,
            @Valid @NotNull Family family) {}
    public record ReferenceConditions(BigDecimal pressurePascalsAbsolute, BigDecimal temperatureKelvin,
            String methodReference) {}
    public record Certificate(String issuerReference, String certificateReference, String certificateSha256,
            String sourceId, String revisionId, String sourceDocumentSha256, String manufacturerPartyId,
            String manufacturerModelReference, ManufacturerCharacteristicRevision.Kind characteristicKind,
            Instant validFrom, Instant validUntil) {}
    public record Axis(ManufacturerCharacteristicRevision.Quantity quantity,
            ManufacturerCharacteristicRevision.Unit unit) {}
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
