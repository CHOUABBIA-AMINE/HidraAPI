/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyGasFluidRevisionCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Encodes and strictly verifies versioned gas method, revision and qualification bytes.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision.*;
import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidRevisionRepositoryPort.Qualification;
import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.DateTimeException;
import java.util.*;

public final class CustodyGasFluidRevisionCodec {
    public static final String FORMAT = "HIDRA_CUSTODY_GAS_FLUID_V1";
    public static final String METHOD_FORMAT = "HIDRA_CUSTODY_GAS_METHOD_V1";
    public static final String QUALIFICATION_FORMAT = "HIDRA_CUSTODY_GAS_FLUID_QUALIFICATION_V1";
    private static void writeProductSnapshot(DataOutputStream out, ProductSnapshot v) throws IOException {
        string(out, v.id());
        string(out, v.catalogName());
        string(out, v.code());
        out.writeByte(v.active() ? 1 : 0);
        instant(out, v.createdAt());
        instant(out, v.updatedAt());
    }
    private static ProductSnapshot readProductSnapshot(DataInputStream in) throws IOException {
        String id = string(in);
        String catalogName = string(in);
        String code = string(in);
        boolean active = marker(in) == 1;
        Instant createdAt = instant(in);
        Instant updatedAt = instant(in);
        return new ProductSnapshot(id, catalogName, code, active, createdAt, updatedAt);
    }
    private static void writeGovernanceBinding(DataOutputStream out, GovernanceBinding v) throws IOException {
        string(out, v.definitionId());
        out.writeInt(v.definitionVersion());
        string(out, v.targetTypeId());
        string(out, v.purposeId());
    }
    private static GovernanceBinding readGovernanceBinding(DataInputStream in) throws IOException {
        String definitionId = string(in);
        int definitionVersion = in.readInt();
        String targetTypeId = string(in);
        String purposeId = string(in);
        return new GovernanceBinding(definitionId, definitionVersion, targetTypeId, purposeId);
    }
    private static void writeComponent(DataOutputStream out, Component v) throws IOException {
        string(out, v.componentReference());
        decimal(out, v.moleFraction());
    }
    private static Component readComponent(DataInputStream in) throws IOException {
        String componentReference = string(in);
        BigDecimal moleFraction = decimal(in);
        return new Component(componentReference, moleFraction);
    }
    private static void writeMethod(DataOutputStream out, Method v) throws IOException {
        string(out, v.reference());
        string(out, v.revisionId());
        instant(out, v.recordedAt());
        instant(out, v.effectiveFrom());
        out.writeByte(v.effectiveUntil() == null ? 0 : 1); if (v.effectiveUntil() != null) { instant(out, v.effectiveUntil()); }
        string(out, v.origin().name());
        string(out, v.evidenceReference());
        string(out, v.inputRepresentation().name());
        out.writeInt(v.allowedComponentReferences().size()); for (var item : v.allowedComponentReferences()) { string(out, item); }
        decimal(out, v.minimumPressurePascalsAbsolute());
        decimal(out, v.maximumPressurePascalsAbsolute());
        decimal(out, v.minimumTemperatureKelvin());
        decimal(out, v.maximumTemperatureKelvin());
        out.writeInt(v.supportedUses().size()); for (var item : v.supportedUses()) { string(out, item.name()); }
    }
    private static Method readMethod(DataInputStream in) throws IOException {
        String reference = string(in);
        String revisionId = string(in);
        Instant recordedAt = instant(in);
        Instant effectiveFrom = instant(in);
        Instant effectiveUntil = marker(in) == 0 ? null : instant(in);
        Origin origin = Origin.valueOf(string(in));
        String evidenceReference = string(in);
        InputRepresentation inputRepresentation = InputRepresentation.valueOf(string(in));
        int allowedComponentReferencesCount = count(in, 4); var allowedComponentReferences = new ArrayList<String>();
        for (int index = 0; index < allowedComponentReferencesCount; index++) allowedComponentReferences.add(string(in));
        BigDecimal minimumPressurePascalsAbsolute = decimal(in);
        BigDecimal maximumPressurePascalsAbsolute = decimal(in);
        BigDecimal minimumTemperatureKelvin = decimal(in);
        BigDecimal maximumTemperatureKelvin = decimal(in);
        int supportedUsesCount = count(in, 4); var supportedUses = new ArrayList<SupportedUse>();
        for (int index = 0; index < supportedUsesCount; index++) supportedUses.add(SupportedUse.valueOf(string(in)));
        return new Method(reference, revisionId, recordedAt, effectiveFrom, effectiveUntil, origin, evidenceReference, inputRepresentation, allowedComponentReferences, minimumPressurePascalsAbsolute, maximumPressurePascalsAbsolute, minimumTemperatureKelvin, maximumTemperatureKelvin, supportedUses);
    }
    private static void writeCustodyGasFluidRevision(DataOutputStream out, CustodyGasFluidRevision v) throws IOException {
        string(out, v.sourceId());
        string(out, v.revisionId());
        instant(out, v.recordedAt());
        instant(out, v.effectiveFrom());
        out.writeByte(v.effectiveUntil() == null ? 0 : 1); if (v.effectiveUntil() != null) { instant(out, v.effectiveUntil()); }
        string(out, v.origin().name());
        string(out, v.evidenceReference());
        writeProductSnapshot(out, v.productSnapshot());
        string(out, v.productKind().name());
        writeMethod(out, v.method());
        out.writeInt(v.components().size()); for (var item : v.components()) { writeComponent(out, item); }
        writeGovernanceBinding(out, v.governanceBinding());
    }
    private static CustodyGasFluidRevision readCustodyGasFluidRevision(DataInputStream in) throws IOException {
        String sourceId = string(in);
        String revisionId = string(in);
        Instant recordedAt = instant(in);
        Instant effectiveFrom = instant(in);
        Instant effectiveUntil = marker(in) == 0 ? null : instant(in);
        Origin origin = Origin.valueOf(string(in));
        String evidenceReference = string(in);
        ProductSnapshot productSnapshot = readProductSnapshot(in);
        ProductKind productKind = ProductKind.valueOf(string(in));
        Method method = readMethod(in);
        int componentsCount = count(in, 4); var components = new ArrayList<Component>();
        for (int index = 0; index < componentsCount; index++) components.add(readComponent(in));
        GovernanceBinding governanceBinding = readGovernanceBinding(in);
        return new CustodyGasFluidRevision(sourceId, revisionId, recordedAt, effectiveFrom, effectiveUntil, origin, evidenceReference, productSnapshot, productKind, method, components, governanceBinding);
    }
    private static void writeQualification(DataOutputStream out, Qualification v) throws IOException {
        string(out, v.qualificationId());
        string(out, v.sourceId());
        string(out, v.revisionId());
        string(out, v.payloadSha256());
        instant(out, v.qualifiedAt());
        string(out, v.workflowInstanceId());
        string(out, v.workflowTaskId());
        string(out, v.workflowActionId());
        string(out, v.approverId());
        string(out, v.approverDisplayName());
        instant(out, v.approvedAt());
        string(out, v.definitionId());
        out.writeInt(v.definitionVersion());
        string(out, v.targetTypeId());
        string(out, v.purposeId());
    }
    private static Qualification readQualification(DataInputStream in) throws IOException {
        String qualificationId = string(in);
        String sourceId = string(in);
        String revisionId = string(in);
        String payloadSha256 = string(in);
        Instant qualifiedAt = instant(in);
        String workflowInstanceId = string(in);
        String workflowTaskId = string(in);
        String workflowActionId = string(in);
        String approverId = string(in);
        String approverDisplayName = string(in);
        Instant approvedAt = instant(in);
        String definitionId = string(in);
        int definitionVersion = in.readInt();
        String targetTypeId = string(in);
        String purposeId = string(in);
        return new Qualification(qualificationId, sourceId, revisionId, payloadSha256, qualifiedAt, workflowInstanceId, workflowTaskId, workflowActionId, approverId, approverDisplayName, approvedAt, definitionId, definitionVersion, targetTypeId, purposeId);
    }
    public byte[] encode(CustodyGasFluidRevision value) {
        if (value == null) throw invalid("Missing canonical value.");
        try { var bytes = new ByteArrayOutputStream(); var out = new DataOutputStream(bytes);
            string(out, FORMAT); writeCustodyGasFluidRevision(out, value); out.flush(); return bytes.toByteArray();
        } catch (IOException failure) { throw invalid("Strict canonical encoding failed."); }
    }
    public CustodyGasFluidRevision decode(byte[] payload) {
        if (payload == null) throw invalid("Missing canonical bytes.");
        byte[] bytes = payload.clone();
        try { var in = new DataInputStream(new ByteArrayInputStream(bytes));
            if (!FORMAT.equals(string(in))) throw invalid("Unsupported canonical format.");
            var value = readCustodyGasFluidRevision(in);
            if (in.available() != 0 || !Arrays.equals(bytes, encode(value))) throw invalid("Noncanonical or trailing bytes.");
            return value;
        } catch (IOException | IllegalArgumentException | ArithmeticException | DateTimeException failure) {
            throw invalid("Malformed canonical payload.");
        }
    }
    public byte[] encodeMethod(Method value) {
        if (value == null) throw invalid("Missing canonical value.");
        try { var bytes = new ByteArrayOutputStream(); var out = new DataOutputStream(bytes);
            string(out, METHOD_FORMAT); writeMethod(out, value); out.flush(); return bytes.toByteArray();
        } catch (IOException failure) { throw invalid("Strict canonical encoding failed."); }
    }
    public Method decodeMethod(byte[] payload) {
        if (payload == null) throw invalid("Missing canonical bytes.");
        byte[] bytes = payload.clone();
        try { var in = new DataInputStream(new ByteArrayInputStream(bytes));
            if (!METHOD_FORMAT.equals(string(in))) throw invalid("Unsupported canonical format.");
            var value = readMethod(in);
            if (in.available() != 0 || !Arrays.equals(bytes, encodeMethod(value))) throw invalid("Noncanonical or trailing bytes.");
            return value;
        } catch (IOException | IllegalArgumentException | ArithmeticException | DateTimeException failure) {
            throw invalid("Malformed canonical payload.");
        }
    }
    public byte[] encodeQualification(Qualification value) {
        if (value == null) throw invalid("Missing canonical value.");
        try { var bytes = new ByteArrayOutputStream(); var out = new DataOutputStream(bytes);
            string(out, QUALIFICATION_FORMAT); writeQualification(out, value); out.flush(); return bytes.toByteArray();
        } catch (IOException failure) { throw invalid("Strict canonical encoding failed."); }
    }
    public Qualification decodeQualification(byte[] payload) {
        if (payload == null) throw invalid("Missing canonical bytes.");
        byte[] bytes = payload.clone();
        try { var in = new DataInputStream(new ByteArrayInputStream(bytes));
            if (!QUALIFICATION_FORMAT.equals(string(in))) throw invalid("Unsupported canonical format.");
            var value = readQualification(in);
            if (in.available() != 0 || !Arrays.equals(bytes, encodeQualification(value))) throw invalid("Noncanonical or trailing bytes.");
            return value;
        } catch (IOException | IllegalArgumentException | ArithmeticException | DateTimeException failure) {
            throw invalid("Malformed canonical payload.");
        }
    }
    public String sha256(byte[] payload) {
        if (payload == null) {
            throw invalid("Gas revision payload must be supplied.");
        }
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(payload.clone()));
        } catch (NoSuchAlgorithmException failure) {
            throw new IllegalStateException("Required SHA-256 implementation is unavailable.", failure);
        }
    }

    private static void string(DataOutputStream out, String value) throws IOException {
        ByteBuffer encoded = StandardCharsets.UTF_8.newEncoder()
                .onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT)
                .encode(CharBuffer.wrap(value));
        out.writeInt(encoded.remaining());
        byte[] bytes = new byte[encoded.remaining()];
        encoded.get(bytes);
        out.write(bytes);
    }

    private static String string(DataInputStream in) throws IOException {
        int length = in.readInt();
        if (length < 0 || length > in.available()) {
            throw invalid("String length exceeds remaining gas revision bytes.");
        }
        return StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT)
                .decode(ByteBuffer.wrap(in.readNBytes(length))).toString();
    }

    private static void decimal(DataOutputStream out, BigDecimal value) throws IOException {
        string(out, value.toPlainString());
        out.writeInt(value.scale());
    }

    private static BigDecimal decimal(DataInputStream in) throws IOException {
        String text = string(in);
        int scale = in.readInt();
        if (!text.matches("-?(0|[1-9][0-9]*)(\\.[0-9]+)?")) {
            throw invalid("Decimal text must use canonical plain notation.");
        }
        BigDecimal parsed = new BigDecimal(text);
        if (!text.equals(parsed.toPlainString())) {
            throw invalid("Decimal text is not canonical.");
        }
        if (scale >= 0) {
            // A canonical nonnegative scale is already explicit in the supplied text.
            // Never expand a short payload using an independently declared large scale.
            if (scale != parsed.scale()) {
                throw invalid("Decimal scale does not match supplied plain text.");
            }
            return parsed;
        }
        if (parsed.scale() != 0) {
            throw invalid("Negative decimal scale requires plain integer text.");
        }
        if (parsed.signum() != 0) {
            int trailingZeros = 0;
            for (int i = text.length() - 1; i >= 0 && text.charAt(i) == '0'; i--) {
                trailingZeros++;
            }
            if (-(long) scale > trailingZeros) {
                throw invalid("Negative decimal scale exceeds supplied trailing zeros.");
            }
        }
        return parsed.setScale(scale);
    }

    private static void instant(DataOutputStream out, Instant value) throws IOException {
        out.writeLong(value.getEpochSecond());
        out.writeInt(value.getNano());
    }

    private static Instant instant(DataInputStream in) throws IOException {
        long seconds = in.readLong();
        int nanos = in.readInt();
        if (nanos < 0 || nanos > 999_999_999) {
            throw invalid("Instant nanoseconds are invalid.");
        }
        return Instant.ofEpochSecond(seconds, nanos);
    }

    private static int count(DataInputStream in, int minimumBytesPerElement) throws IOException {
        int count = in.readInt();
        if (count < 0 || count > in.available() / minimumBytesPerElement) {
            throw invalid("List count exceeds remaining gas revision bytes.");
        }
        return count;
    }

    private static int marker(DataInputStream in) throws IOException {
        int marker = in.readUnsignedByte(); if (marker > 1) throw invalid("Invalid optional marker."); return marker;
    }
    private static InvalidCustodyValueException invalid(String message) { return new InvalidCustodyValueException(message); }
}
