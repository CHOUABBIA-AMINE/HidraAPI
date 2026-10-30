/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerCharacteristicRevisionCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Bounded canonical binary serialization of immutable supplier facts without legacy format changes.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision;
import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

/** The digest authenticates exact bytes, not manufacturer identity or the truth of supplier assertions. */
public final class ManufacturerCharacteristicRevisionCodec {
    public static final String FORMAT="HIDRA_MANUFACTURER_CHARACTERISTIC_SOURCE_V1";
    public static final int MAX_BYTES=1_048_576;
    private static final int MAX_STRING_BYTES=1_024;

    public byte[] encode(ManufacturerCharacteristicRevision value) {
        if(value==null) throw invalid("Missing manufacturer source revision.");
        try {
            var bytes=new ByteArrayOutputStream();var out=new DataOutputStream(bytes);
            string(out,FORMAT);
            string(out,value.sourceId());string(out,value.revisionId());instant(out,value.recordedAt());
            instant(out,value.effectiveFrom());optionalInstant(out,value.effectiveUntil());
            string(out,value.origin().name());string(out,value.manufacturerPartyId());
            string(out,value.manufacturerModelReference());string(out,value.documentReference());
            string(out,value.documentSha256());
            decimal(out,value.referenceConditions().pressurePascalsAbsolute());
            decimal(out,value.referenceConditions().temperatureKelvin());
            string(out,value.referenceConditions().methodReference());
            out.writeBoolean(value.certificate()!=null);
            if(value.certificate()!=null) certificate(out,value.certificate());
            string(out,value.family().kind().name());
            switch(value.family()) {
                case CompressorMap c -> {out.writeInt(c.speedLines().size());for(var line:c.speedLines()) {
                    decimal(out,line.speedRevolutionsPerMinute());out.writeInt(line.points().size());
                    for(var p:line.points()) {decimal(out,p.massFlowKilogramsPerSecond());
                        decimal(out,p.specificHeadJoulesPerKilogram());decimal(out,p.efficiencyFraction());}
                }}
                case ValveMap v -> {out.writeInt(v.openingLines().size());for(var line:v.openingLines()) {
                    decimal(out,line.openingFraction());out.writeInt(line.points().size());
                    for(var p:line.points()){decimal(out,p.differentialPressurePascals());decimal(out,p.massFlowKilogramsPerSecond());}
                }}
                case ParameterTable t -> {axes(out,t.axes());axes(out,t.outputs());out.writeInt(t.rows().size());
                    for(var row:t.rows()){decimals(out,row.coordinates());decimals(out,row.values());}}
                case OperatingLimits limits -> {out.writeInt(limits.limits().size());for(var l:limits.limits()){
                    axis(out,l.axis());decimal(out,l.minimumInclusive());decimal(out,l.maximumInclusive());
                    string(out,l.evidenceReference());}}
            }
            out.flush();
            if(bytes.size()>MAX_BYTES) throw invalid("Manufacturer payload exceeds canonical size limit.");
            return bytes.toByteArray();
        } catch(IOException e){throw invalid("Manufacturer payload encoding failed.");}
    }

    public ManufacturerCharacteristicRevision decode(byte[] payload) {
        if(payload==null || payload.length==0 || payload.length>MAX_BYTES)
            throw invalid("Missing or oversized manufacturer payload.");
        try {
            var in=new DataInputStream(new ByteArrayInputStream(payload));
            if(!FORMAT.equals(string(in))) throw invalid("Unsupported manufacturer payload format.");
            String id=string(in),rev=string(in);Instant recorded=instant(in),from=instant(in),until=optionalInstant(in);
            Origin origin=Origin.valueOf(string(in));String party=string(in),model=string(in),doc=string(in),digest=string(in);
            ReferenceConditions refs=new ReferenceConditions(decimal(in),decimal(in),string(in));
            int presence=in.readUnsignedByte();if(presence>1)throw invalid("Invalid certificate marker.");
            CertificateEvidence cert=presence==1?certificate(in):null;
            Kind kind=Kind.valueOf(string(in));Family family=switch(kind){
                case COMPRESSOR_MAP -> {var lines=new ArrayList<SpeedLine>();int count=count(in,1,128);
                    for(int k=0;k<count;k++){BigDecimal speed=decimal(in);var pts=new ArrayList<CompressorPoint>();
                        int n=count(in,2,1024);for(int j=0;j<n;j++)pts.add(new CompressorPoint(decimal(in),decimal(in),decimal(in)));
                        lines.add(new SpeedLine(speed,pts));}yield new CompressorMap(lines);}
                case VALVE_MAP -> {var lines=new ArrayList<OpeningLine>();int count=count(in,1,128);
                    for(int k=0;k<count;k++){BigDecimal opening=decimal(in);var pts=new ArrayList<ValvePoint>();
                        int n=count(in,2,1024);for(int j=0;j<n;j++)pts.add(new ValvePoint(decimal(in),decimal(in)));
                        lines.add(new OpeningLine(opening,pts));}yield new ValveMap(lines);}
                case PARAMETER_TABLE -> {List<Axis> a=axes(in),outputs=axes(in);int count=count(in,1,4096);
                    var rows=new ArrayList<TableRow>();for(int k=0;k<count;k++)rows.add(new TableRow(decimals(in,1,16),decimals(in,1,16)));
                    yield new ParameterTable(a,outputs,rows);}
                case OPERATING_LIMITS -> {int count=count(in,1,32);var limits=new ArrayList<Limit>();
                    for(int k=0;k<count;k++)limits.add(new Limit(axis(in),decimal(in),decimal(in),string(in)));
                    yield new OperatingLimits(limits);}
            };
            ManufacturerCharacteristicRevision value=new ManufacturerCharacteristicRevision(id,rev,recorded,from,until,
                origin,party,model,doc,digest,refs,cert,family);
            if(in.available()!=0 || !Arrays.equals(payload,encode(value)))
                throw invalid("Noncanonical or trailing manufacturer bytes.");
            return value;
        }catch(IOException|IllegalArgumentException e){throw invalid("Malformed manufacturer source payload.");}
    }
    public String sha256(byte[] canonicalPayload) {
        if(canonicalPayload==null||canonicalPayload.length>MAX_BYTES)throw invalid("Invalid payload digest input.");
        try {return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(canonicalPayload));}
        catch(NoSuchAlgorithmException e){throw new IllegalStateException("SHA-256 unavailable.",e);}
    }
    public String sha256(ManufacturerCharacteristicRevision value){return sha256(encode(value));}

    private static void certificate(DataOutputStream out,CertificateEvidence c)throws IOException{
        string(out,c.issuerReference());string(out,c.certificateReference());string(out,c.certificateSha256());
        string(out,c.sourceId());string(out,c.revisionId());string(out,c.sourceDocumentSha256());
        string(out,c.manufacturerPartyId());string(out,c.manufacturerModelReference());
        string(out,c.characteristicKind().name());instant(out,c.validFrom());optionalInstant(out,c.validUntil());
    }
    private static CertificateEvidence certificate(DataInputStream in)throws IOException{
        return new CertificateEvidence(string(in),string(in),string(in),string(in),string(in),string(in),
                string(in),string(in),Kind.valueOf(string(in)),instant(in),optionalInstant(in));
    }
    private static void axes(DataOutputStream out,List<Axis> a)throws IOException{
        out.writeInt(a.size());for(Axis axis:a)axis(out,axis);
    }
    private static List<Axis> axes(DataInputStream in)throws IOException{
        int n=count(in,1,16);var result=new ArrayList<Axis>();for(int i=0;i<n;i++)result.add(axis(in));return result;
    }
    private static void axis(DataOutputStream out,Axis a)throws IOException{
        string(out,a.quantity().name());string(out,a.unit().name());
    }
    private static Axis axis(DataInputStream in)throws IOException{
        return new Axis(Quantity.valueOf(string(in)),Unit.valueOf(string(in)));
    }
    private static void decimals(DataOutputStream out,List<BigDecimal> ds)throws IOException{
        out.writeInt(ds.size());for(BigDecimal d:ds)decimal(out,d);
    }
    private static List<BigDecimal> decimals(DataInputStream in,int min,int max)throws IOException{
        int n=count(in,min,max);var ds=new ArrayList<BigDecimal>();for(int i=0;i<n;i++)ds.add(decimal(in));return ds;
    }
    private static void decimal(DataOutputStream out,BigDecimal d)throws IOException{
        if(d==null || d.scale()<0 || d.scale()>12 || d.precision()>40)
            throw invalid("Unbounded manufacturer decimal.");
        string(out,d.toPlainString());out.writeInt(d.scale());
    }
    private static BigDecimal decimal(DataInputStream in)throws IOException{
        String s=string(in);int scale=in.readInt();
        if(!s.matches("-?(0|[1-9][0-9]*)(\\.[0-9]+)?") || scale<0 || scale>12)
            throw invalid("Unsupported manufacturer decimal notation.");
        BigDecimal d=new BigDecimal(s);
        if(d.scale()!=scale || !d.toPlainString().equals(s) || d.precision()>40)
            throw invalid("Noncanonical manufacturer decimal.");
        return d;
    }
    private static void instant(DataOutputStream out,Instant i)throws IOException{
        out.writeLong(i.getEpochSecond());out.writeInt(i.getNano());
    }
    private static Instant instant(DataInputStream in)throws IOException{
        long sec=in.readLong();int nano=in.readInt();if(nano<0||nano>999_999_999)throw invalid("Invalid instant.");
        return Instant.ofEpochSecond(sec,nano);
    }
    private static void optionalInstant(DataOutputStream out,Instant i)throws IOException{
        out.writeByte(i==null?0:1);if(i!=null)instant(out,i);
    }
    private static Instant optionalInstant(DataInputStream in)throws IOException{
        int marker=in.readUnsignedByte();if(marker>1)throw invalid("Invalid timestamp marker.");
        return marker==0?null:instant(in);
    }
    private static void string(DataOutputStream out,String text)throws IOException{
        byte[] bytes=text.getBytes(StandardCharsets.UTF_8);
        if(bytes.length==0 || bytes.length>MAX_STRING_BYTES)throw invalid("Source text exceeds byte limit.");
        out.writeInt(bytes.length);out.write(bytes);
    }
    private static String string(DataInputStream in)throws IOException{
        int size=in.readInt();if(size<1||size>MAX_STRING_BYTES||size>in.available())
            throw invalid("Invalid supplied string byte count.");
        byte[] bytes=new byte[size];in.readFully(bytes);
        try {return StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(bytes)).toString();}
        catch(CharacterCodingException e){throw invalid("Malformed UTF-8 manufacturer text.");}
    }
    private static int count(DataInputStream in,int min,int max)throws IOException{
        int n=in.readInt();if(n<min||n>max||n>in.available()/4)
            throw invalid("Invalid supplied count or insufficient bytes.");return n;
    }
    private static InvalidSimulationValueException invalid(String message){return new InvalidSimulationValueException(message);}
}
