/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentParameterRevisionCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision;
import dz.sh.hidra.modules.simulation.domain.model.SimulationEquipmentParameterRevision.*;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationEquipmentParameterRevisionRepositoryPort.Qualification;
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
public final class SimulationEquipmentParameterRevisionCodec {
    public static final String FORMAT="HIDRA_SIMULATION_EQUIPMENT_PARAMETERS_V1";
    public static final String CURVE_FORMAT="HIDRA_SIMULATION_COMPRESSOR_CURVE_V1";
    public static final String VALVE_FORMAT="HIDRA_SIMULATION_VALVE_CHARACTERISTIC_V1";
    public static final String QUALIFICATION_FORMAT="HIDRA_SIMULATION_EQUIPMENT_QUALIFICATION_V1";
    private static void writeGovernanceBinding(DataOutputStream out,GovernanceBinding v) throws IOException {
        string(out,v.definitionId());
        out.writeInt(v.definitionVersion());
        string(out,v.targetTypeId());
        string(out,v.purposeId());
    }
    private static GovernanceBinding readGovernanceBinding(DataInputStream in) throws IOException {
        String definitionId=string(in);
        int definitionVersion=in.readInt();
        String targetTypeId=string(in);
        String purposeId=string(in);
        return new GovernanceBinding(definitionId,definitionVersion,targetTypeId,purposeId);
    }
    private static void writeEquipment(DataOutputStream out,Equipment v) throws IOException {
        string(out,v.id());
        string(out,v.fromNodeId());
        string(out,v.toNodeId());
        string(out,v.kind().name());
        out.writeByte(v.curveId() == null ? 0 : 1); if (v.curveId() != null) { string(out,v.curveId()); }
        out.writeByte(v.curveRevisionId() == null ? 0 : 1); if (v.curveRevisionId() != null) { string(out,v.curveRevisionId()); }
        out.writeByte(v.configuredSpeedRevolutionsPerMinute() == null ? 0 : 1); if (v.configuredSpeedRevolutionsPerMinute() != null) { decimal(out,v.configuredSpeedRevolutionsPerMinute()); }
        out.writeByte(v.characteristicId() == null ? 0 : 1); if (v.characteristicId() != null) { string(out,v.characteristicId()); }
        out.writeByte(v.characteristicRevisionId() == null ? 0 : 1); if (v.characteristicRevisionId() != null) { string(out,v.characteristicRevisionId()); }
        out.writeByte(v.configuredOpeningFraction() == null ? 0 : 1); if (v.configuredOpeningFraction() != null) { decimal(out,v.configuredOpeningFraction()); }
    }
    private static Equipment readEquipment(DataInputStream in) throws IOException {
        String id=string(in);
        String fromNodeId=string(in);
        String toNodeId=string(in);
        Kind kind=Kind.valueOf(string(in));
        String curveId=marker(in) == 0 ? null : string(in);
        String curveRevisionId=marker(in) == 0 ? null : string(in);
        BigDecimal configuredSpeedRevolutionsPerMinute=marker(in) == 0 ? null : decimal(in);
        String characteristicId=marker(in) == 0 ? null : string(in);
        String characteristicRevisionId=marker(in) == 0 ? null : string(in);
        BigDecimal configuredOpeningFraction=marker(in) == 0 ? null : decimal(in);
        return new Equipment(id,fromNodeId,toNodeId,kind,curveId,curveRevisionId,configuredSpeedRevolutionsPerMinute,characteristicId,characteristicRevisionId,configuredOpeningFraction);
    }
    private static void writeCompressorPoint(DataOutputStream out,CompressorPoint v) throws IOException {
        decimal(out,v.massFlowKilogramsPerSecond());
        decimal(out,v.specificHeadJoulesPerKilogram());
        decimal(out,v.efficiencyFraction());
    }
    private static CompressorPoint readCompressorPoint(DataInputStream in) throws IOException {
        BigDecimal massFlowKilogramsPerSecond=decimal(in);
        BigDecimal specificHeadJoulesPerKilogram=decimal(in);
        BigDecimal efficiencyFraction=decimal(in);
        return new CompressorPoint(massFlowKilogramsPerSecond,specificHeadJoulesPerKilogram,efficiencyFraction);
    }
    private static void writeSpeedLine(DataOutputStream out,SpeedLine v) throws IOException {
        decimal(out,v.rotationalSpeedRevolutionsPerMinute());
        out.writeInt(v.points().size()); for(var item : v.points()) { writeCompressorPoint(out,item); }
    }
    private static SpeedLine readSpeedLine(DataInputStream in) throws IOException {
        BigDecimal rotationalSpeedRevolutionsPerMinute=decimal(in);
        int pointsCount=count(in,4);var points=new ArrayList<CompressorPoint>();for(int i=0;i<pointsCount;i++) points.add(readCompressorPoint(in));
        return new SpeedLine(rotationalSpeedRevolutionsPerMinute,points);
    }
    private static void writeValvePoint(DataOutputStream out,ValvePoint v) throws IOException {
        decimal(out,v.differentialPressurePascals());
        decimal(out,v.massFlowKilogramsPerSecond());
    }
    private static ValvePoint readValvePoint(DataInputStream in) throws IOException {
        BigDecimal differentialPressurePascals=decimal(in);
        BigDecimal massFlowKilogramsPerSecond=decimal(in);
        return new ValvePoint(differentialPressurePascals,massFlowKilogramsPerSecond);
    }
    private static void writeOpeningLine(DataOutputStream out,OpeningLine v) throws IOException {
        decimal(out,v.openingFraction());
        out.writeInt(v.points().size()); for(var item : v.points()) { writeValvePoint(out,item); }
    }
    private static OpeningLine readOpeningLine(DataInputStream in) throws IOException {
        BigDecimal openingFraction=decimal(in);
        int pointsCount=count(in,4);var points=new ArrayList<ValvePoint>();for(int i=0;i<pointsCount;i++) points.add(readValvePoint(in));
        return new OpeningLine(openingFraction,points);
    }
    private static void writeGovernedLimit(DataOutputStream out,GovernedLimit v) throws IOException {
        string(out,v.equipmentId());
        string(out,v.quantity().name());
        decimal(out,v.minimumInclusive());
        decimal(out,v.maximumInclusive());
        string(out,v.evidenceReference());
    }
    private static GovernedLimit readGovernedLimit(DataInputStream in) throws IOException {
        String equipmentId=string(in);
        Quantity quantity=Quantity.valueOf(string(in));
        BigDecimal minimumInclusive=decimal(in);
        BigDecimal maximumInclusive=decimal(in);
        String evidenceReference=string(in);
        return new GovernedLimit(equipmentId,quantity,minimumInclusive,maximumInclusive,evidenceReference);
    }
    private static void writeCompressorCurve(DataOutputStream out,CompressorCurve v) throws IOException {
        string(out,v.id());
        string(out,v.revisionId());
        instant(out,v.recordedAt());
        instant(out,v.effectiveFrom());
        out.writeByte(v.effectiveUntil() == null ? 0 : 1); if (v.effectiveUntil() != null) { instant(out,v.effectiveUntil()); }
        string(out,v.origin().name());
        string(out,v.evidenceReference());
        string(out,v.fluidSourceId());
        string(out,v.fluidRevisionId());
        string(out,v.fluidSha256());
        string(out,v.headDefinitionReference());
        string(out,v.efficiencyDefinitionReference());
        string(out,v.interpolationMethodReference());
        decimal(out,v.referenceInletPressurePascalsAbsolute());
        decimal(out,v.referenceInletTemperatureKelvin());
        out.writeInt(v.speedLines().size()); for(var item : v.speedLines()) { writeSpeedLine(out,item); }
    }
    private static CompressorCurve readCompressorCurve(DataInputStream in) throws IOException {
        String id=string(in);
        String revisionId=string(in);
        Instant recordedAt=instant(in);
        Instant effectiveFrom=instant(in);
        Instant effectiveUntil=marker(in) == 0 ? null : instant(in);
        Origin origin=Origin.valueOf(string(in));
        String evidenceReference=string(in);
        String fluidSourceId=string(in);
        String fluidRevisionId=string(in);
        String fluidSha256=string(in);
        String headDefinitionReference=string(in);
        String efficiencyDefinitionReference=string(in);
        String interpolationMethodReference=string(in);
        BigDecimal referenceInletPressurePascalsAbsolute=decimal(in);
        BigDecimal referenceInletTemperatureKelvin=decimal(in);
        int speedLinesCount=count(in,4);var speedLines=new ArrayList<SpeedLine>();for(int i=0;i<speedLinesCount;i++) speedLines.add(readSpeedLine(in));
        return new CompressorCurve(id,revisionId,recordedAt,effectiveFrom,effectiveUntil,origin,evidenceReference,fluidSourceId,fluidRevisionId,fluidSha256,headDefinitionReference,efficiencyDefinitionReference,interpolationMethodReference,referenceInletPressurePascalsAbsolute,referenceInletTemperatureKelvin,speedLines);
    }
    private static void writeValveCharacteristic(DataOutputStream out,ValveCharacteristic v) throws IOException {
        string(out,v.id());
        string(out,v.revisionId());
        instant(out,v.recordedAt());
        instant(out,v.effectiveFrom());
        out.writeByte(v.effectiveUntil() == null ? 0 : 1); if (v.effectiveUntil() != null) { instant(out,v.effectiveUntil()); }
        string(out,v.origin().name());
        string(out,v.evidenceReference());
        string(out,v.fluidSourceId());
        string(out,v.fluidRevisionId());
        string(out,v.fluidSha256());
        decimal(out,v.referenceTemperatureKelvin());
        out.writeInt(v.openingLines().size()); for(var item : v.openingLines()) { writeOpeningLine(out,item); }
    }
    private static ValveCharacteristic readValveCharacteristic(DataInputStream in) throws IOException {
        String id=string(in);
        String revisionId=string(in);
        Instant recordedAt=instant(in);
        Instant effectiveFrom=instant(in);
        Instant effectiveUntil=marker(in) == 0 ? null : instant(in);
        Origin origin=Origin.valueOf(string(in));
        String evidenceReference=string(in);
        String fluidSourceId=string(in);
        String fluidRevisionId=string(in);
        String fluidSha256=string(in);
        BigDecimal referenceTemperatureKelvin=decimal(in);
        int openingLinesCount=count(in,4);var openingLines=new ArrayList<OpeningLine>();for(int i=0;i<openingLinesCount;i++) openingLines.add(readOpeningLine(in));
        return new ValveCharacteristic(id,revisionId,recordedAt,effectiveFrom,effectiveUntil,origin,evidenceReference,fluidSourceId,fluidRevisionId,fluidSha256,referenceTemperatureKelvin,openingLines);
    }
    private static void writeSimulationEquipmentParameterRevision(DataOutputStream out,SimulationEquipmentParameterRevision v) throws IOException {
        string(out,v.sourceId());
        string(out,v.revisionId());
        instant(out,v.recordedAt());
        instant(out,v.effectiveFrom());
        out.writeByte(v.effectiveUntil() == null ? 0 : 1); if (v.effectiveUntil() != null) { instant(out,v.effectiveUntil()); }
        string(out,v.origin().name());
        string(out,v.evidenceReference());
        string(out,v.networkSourceId());
        string(out,v.networkRevisionId());
        string(out,v.networkSha256());
        string(out,v.fluidSourceId());
        string(out,v.fluidRevisionId());
        string(out,v.fluidSha256());
        string(out,v.fluidQualificationId());
        out.writeInt(v.equipment().size()); for(var item : v.equipment()) { writeEquipment(out,item); }
        out.writeInt(v.compressorCurves().size()); for(var item : v.compressorCurves()) { writeCompressorCurve(out,item); }
        out.writeInt(v.valveCharacteristics().size()); for(var item : v.valveCharacteristics()) { writeValveCharacteristic(out,item); }
        out.writeInt(v.governedLimits().size()); for(var item : v.governedLimits()) { writeGovernedLimit(out,item); }
        writeGovernanceBinding(out,v.governanceBinding());
    }
    private static SimulationEquipmentParameterRevision readSimulationEquipmentParameterRevision(DataInputStream in) throws IOException {
        String sourceId=string(in);
        String revisionId=string(in);
        Instant recordedAt=instant(in);
        Instant effectiveFrom=instant(in);
        Instant effectiveUntil=marker(in) == 0 ? null : instant(in);
        Origin origin=Origin.valueOf(string(in));
        String evidenceReference=string(in);
        String networkSourceId=string(in);
        String networkRevisionId=string(in);
        String networkSha256=string(in);
        String fluidSourceId=string(in);
        String fluidRevisionId=string(in);
        String fluidSha256=string(in);
        String fluidQualificationId=string(in);
        int equipmentCount=count(in,4);var equipment=new ArrayList<Equipment>();for(int i=0;i<equipmentCount;i++) equipment.add(readEquipment(in));
        int compressorCurvesCount=count(in,4);var compressorCurves=new ArrayList<CompressorCurve>();for(int i=0;i<compressorCurvesCount;i++) compressorCurves.add(readCompressorCurve(in));
        int valveCharacteristicsCount=count(in,4);var valveCharacteristics=new ArrayList<ValveCharacteristic>();for(int i=0;i<valveCharacteristicsCount;i++) valveCharacteristics.add(readValveCharacteristic(in));
        int governedLimitsCount=count(in,4);var governedLimits=new ArrayList<GovernedLimit>();for(int i=0;i<governedLimitsCount;i++) governedLimits.add(readGovernedLimit(in));
        GovernanceBinding governanceBinding=readGovernanceBinding(in);
        return new SimulationEquipmentParameterRevision(sourceId,revisionId,recordedAt,effectiveFrom,effectiveUntil,origin,evidenceReference,networkSourceId,networkRevisionId,networkSha256,fluidSourceId,fluidRevisionId,fluidSha256,fluidQualificationId,equipment,compressorCurves,valveCharacteristics,governedLimits,governanceBinding);
    }
    private static void writeQualification(DataOutputStream out,Qualification v) throws IOException {
        string(out,v.qualificationId());
        string(out,v.sourceId());
        string(out,v.revisionId());
        string(out,v.payloadSha256());
        instant(out,v.qualifiedAt());
        string(out,v.workflowInstanceId());
        string(out,v.workflowTaskId());
        string(out,v.workflowActionId());
        string(out,v.approverId());
        string(out,v.approverDisplayName());
        instant(out,v.approvedAt());
        string(out,v.definitionId());
        out.writeInt(v.definitionVersion());
        string(out,v.targetTypeId());
        string(out,v.purposeId());
    }
    private static Qualification readQualification(DataInputStream in) throws IOException {
        String qualificationId=string(in);
        String sourceId=string(in);
        String revisionId=string(in);
        String payloadSha256=string(in);
        Instant qualifiedAt=instant(in);
        String workflowInstanceId=string(in);
        String workflowTaskId=string(in);
        String workflowActionId=string(in);
        String approverId=string(in);
        String approverDisplayName=string(in);
        Instant approvedAt=instant(in);
        String definitionId=string(in);
        int definitionVersion=in.readInt();
        String targetTypeId=string(in);
        String purposeId=string(in);
        return new Qualification(qualificationId,sourceId,revisionId,payloadSha256,qualifiedAt,workflowInstanceId,workflowTaskId,workflowActionId,approverId,approverDisplayName,approvedAt,definitionId,definitionVersion,targetTypeId,purposeId);
    }
    public byte[] encode(SimulationEquipmentParameterRevision value) {
        if(value==null)throw invalid("Missing canonical value.");
        try {var bytes=new ByteArrayOutputStream();var out=new DataOutputStream(bytes);string(out,FORMAT);writeSimulationEquipmentParameterRevision(out,value);out.flush();return bytes.toByteArray();}
        catch(IOException failure){throw invalid("Strict canonical encoding failed.");}
    }
    public SimulationEquipmentParameterRevision decode(byte[] payload) {
        if(payload==null)throw invalid("Missing canonical bytes.");byte[] bytes=payload.clone();
        try {var in=new DataInputStream(new ByteArrayInputStream(bytes));if(!FORMAT.equals(string(in)))throw invalid("Unsupported canonical format.");
            var value=readSimulationEquipmentParameterRevision(in);if(in.available()!=0||!Arrays.equals(bytes,encode(value)))throw invalid("Noncanonical or trailing bytes.");return value;}
        catch(IOException | IllegalArgumentException | ArithmeticException | DateTimeException failure){throw invalid("Malformed canonical payload.");}
    }
    public byte[] encodeCompressorCurve(CompressorCurve value) {
        if(value==null)throw invalid("Missing canonical value.");
        try {var bytes=new ByteArrayOutputStream();var out=new DataOutputStream(bytes);string(out,CURVE_FORMAT);writeCompressorCurve(out,value);out.flush();return bytes.toByteArray();}
        catch(IOException failure){throw invalid("Strict canonical encoding failed.");}
    }
    public CompressorCurve decodeCompressorCurve(byte[] payload) {
        if(payload==null)throw invalid("Missing canonical bytes.");byte[] bytes=payload.clone();
        try {var in=new DataInputStream(new ByteArrayInputStream(bytes));if(!CURVE_FORMAT.equals(string(in)))throw invalid("Unsupported canonical format.");
            var value=readCompressorCurve(in);if(in.available()!=0||!Arrays.equals(bytes,encodeCompressorCurve(value)))throw invalid("Noncanonical or trailing bytes.");return value;}
        catch(IOException | IllegalArgumentException | ArithmeticException | DateTimeException failure){throw invalid("Malformed canonical payload.");}
    }
    public byte[] encodeValveCharacteristic(ValveCharacteristic value) {
        if(value==null)throw invalid("Missing canonical value.");
        try {var bytes=new ByteArrayOutputStream();var out=new DataOutputStream(bytes);string(out,VALVE_FORMAT);writeValveCharacteristic(out,value);out.flush();return bytes.toByteArray();}
        catch(IOException failure){throw invalid("Strict canonical encoding failed.");}
    }
    public ValveCharacteristic decodeValveCharacteristic(byte[] payload) {
        if(payload==null)throw invalid("Missing canonical bytes.");byte[] bytes=payload.clone();
        try {var in=new DataInputStream(new ByteArrayInputStream(bytes));if(!VALVE_FORMAT.equals(string(in)))throw invalid("Unsupported canonical format.");
            var value=readValveCharacteristic(in);if(in.available()!=0||!Arrays.equals(bytes,encodeValveCharacteristic(value)))throw invalid("Noncanonical or trailing bytes.");return value;}
        catch(IOException | IllegalArgumentException | ArithmeticException | DateTimeException failure){throw invalid("Malformed canonical payload.");}
    }
    public byte[] encodeQualification(Qualification value) {
        if(value==null)throw invalid("Missing canonical value.");
        try {var bytes=new ByteArrayOutputStream();var out=new DataOutputStream(bytes);string(out,QUALIFICATION_FORMAT);writeQualification(out,value);out.flush();return bytes.toByteArray();}
        catch(IOException failure){throw invalid("Strict canonical encoding failed.");}
    }
    public Qualification decodeQualification(byte[] payload) {
        if(payload==null)throw invalid("Missing canonical bytes.");byte[] bytes=payload.clone();
        try {var in=new DataInputStream(new ByteArrayInputStream(bytes));if(!QUALIFICATION_FORMAT.equals(string(in)))throw invalid("Unsupported canonical format.");
            var value=readQualification(in);if(in.available()!=0||!Arrays.equals(bytes,encodeQualification(value)))throw invalid("Noncanonical or trailing bytes.");return value;}
        catch(IOException | IllegalArgumentException | ArithmeticException | DateTimeException failure){throw invalid("Malformed canonical payload.");}
    }
    public String sha256(byte[] payload) {
        if (payload == null) {
            throw invalid("Equipment revision payload must be supplied.");
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
            throw invalid("String length exceeds remaining equipment revision bytes.");
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
            throw invalid("List count exceeds remaining equipment revision bytes.");
        }
        return count;
    }

    private static int marker(DataInputStream in) throws IOException {
        int marker = in.readUnsignedByte(); if (marker > 1) throw invalid("Invalid optional marker."); return marker;
    }
    private static InvalidSimulationValueException invalid(String message) { return new InvalidSimulationValueException(message); }
}
