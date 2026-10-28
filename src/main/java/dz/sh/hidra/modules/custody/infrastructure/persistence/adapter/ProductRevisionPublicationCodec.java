/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductRevisionPublicationCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Preserves governed product publication evidence under exact owner admission.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;
import dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication;
import dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.*;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.Command;
import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.time.Instant;
import java.util.*;

/** Strict canonical versioned binary encodings; original source bytes remain untouched. */
public final class ProductRevisionPublicationCodec {
    public static final String FORMAT="HIDRA_PRODUCT_PUBLICATION_V1";
    public static final int MAX_BYTES=4194304;
    @FunctionalInterface private interface Writer<T>{void put(DataOutputStream o,T x)throws IOException;}
    @FunctionalInterface private interface Reader<T>{T get(DataInputStream i)throws IOException;}
    private static void text(DataOutputStream o,String s)throws IOException {
        if(s==null){o.writeInt(-1);return;}var b=StandardCharsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT)
            .onUnmappableCharacter(CodingErrorAction.REPORT).encode(CharBuffer.wrap(s));o.writeInt(b.remaining());o.write(b.array(),b.position(),b.remaining());
    }
    private static String text(DataInputStream i)throws IOException {
        int n=i.readInt();if(n==-1)return null;if(n<0 || n>MAX_BYTES || n>i.available())throw bad();
        return StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(i.readNBytes(n))).toString();
    }
    private static void uuid(DataOutputStream o,UUID id)throws IOException{text(o,id==null?null:id.toString());}
    private static UUID uuid(DataInputStream i)throws IOException{String s=text(i);return s==null?null:UUID.fromString(s);}
    private static void instant(DataOutputStream o,Instant at)throws IOException{text(o,at==null?null:at.toString());}
    private static Instant instant(DataInputStream i)throws IOException{String s=text(i);return s==null?null:Instant.parse(s);}
    private static void scope(DataOutputStream o,Scope s)throws IOException{uuid(o,s.productDraftId());text(o,s.companyPartyId());text(o,s.catalogProductId());}
    private static Scope scope(DataInputStream i)throws IOException{return new Scope(uuid(i),text(i),text(i));}
    private static void component(DataOutputStream o,Component c)throws IOException{o.writeLong(c.version());text(o,c.format());text(o,c.sha256());text(o,c.base64());}
    private static Component component(DataInputStream i)throws IOException{return new Component(i.readLong(),text(i),text(i),text(i));}
    private static void policy(DataOutputStream o,Policy p)throws IOException{text(o,p.definitionId());o.writeInt(p.definitionVersion());text(o,p.targetTypeId());text(o,p.purposeId());}
    private static Policy policy(DataInputStream i)throws IOException{return new Policy(text(i),i.readInt(),text(i),text(i));}
    private static void envelope(DataOutputStream o,Envelope e)throws IOException {
        scope(o,e.scope());component(o,e.metadata());component(o,e.supplied());o.writeLong(e.suppliedMetadataVersion());text(o,e.classification());
        o.writeInt(e.diagnostics().size());for(String d:e.diagnostics())text(o,d);
        o.writeInt(e.units().size());for(Unit u:e.units()){text(o,u.unitId());text(o,u.role());text(o,u.code());text(o,u.symbol());text(o,u.dimension());instant(o,u.updatedAt());}policy(o,e.policy());
    }
    private static int count(DataInputStream i,int max)throws IOException{int n=i.readInt();if(n<0 || n>max)throw bad();return n;}
    private static Envelope envelope(DataInputStream i)throws IOException {
        var s=scope(i);var m=component(i);var f=component(i);long mv=i.readLong();String c=text(i);
        var ds=new ArrayList<String>();for(int n=count(i,128);n>0;n--)ds.add(text(i));
        var us=new ArrayList<Unit>();for(int n=count(i,128);n>0;n--)us.add(new Unit(text(i),text(i),text(i),text(i),text(i),instant(i)));
        var e=new Envelope(s,m,f,mv,c,ds,us,policy(i));verifySource(e);return e;
    }
    public static void verifySource(Envelope e){
        if(!ProductEngineeringDraftMetadataCodec.FORMAT.equals(e.metadata().format()) || !SuppliedFluidPropertyRevisionCodec.FORMAT.equals(e.supplied().format()))throw bad();
        var m=new ProductEngineeringDraftMetadataCodec().decode(e.metadata().bytes());
        var f=new SuppliedFluidPropertyRevisionCodec().decode(e.supplied().bytes());
        if(m.classification()==null || !m.classification().name().equals(e.classification()))throw bad();
        f.validate(dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.Classification.valueOf(e.classification()));
        if(!diagnostics(e.metadata(),e.supplied()).equals(e.diagnostics()))throw bad();
        var needed=new TreeSet<String>();
        if(f.properties()!=null)for(var observation:f.properties()){
            needed.add(observation.property().name()+":"+observation.unitId());needed.add("ABSOLUTE_PRESSURE:"+observation.absolutePressure().unitId());needed.add("ABSOLUTE_TEMPERATURE:"+observation.absoluteTemperature().unitId());}
        if(f.methods()!=null)for(var method:f.methods()){needed.add("ABSOLUTE_PRESSURE:"+method.absolutePressure().unitId());needed.add("ABSOLUTE_TEMPERATURE:"+method.absoluteTemperature().unitId());}
        if(!List.copyOf(needed).equals(e.units().stream().map(Unit::key).toList()))throw bad();
    }
    public static List<String> diagnostics(Component metadata,Component supplied){
        var values=new ArrayList<String>(new ProductEngineeringDraftMetadataCodec().decode(metadata.bytes()).diagnostics());
        values.addAll(new SuppliedFluidPropertyRevisionCodec().decode(supplied.bytes()).diagnostics());return List.copyOf(values);
    }
    private static void approval(DataOutputStream o,Approval a)throws IOException {
        o.writeBoolean(a!=null);if(a==null)return;text(o,a.digest());text(o,a.instanceId());text(o,a.taskId());text(o,a.actionId());text(o,a.historyId());text(o,a.actorId());text(o,a.actorDisplayName());instant(o,a.actedAt());
    }
    private static Approval approval(DataInputStream i)throws IOException{int tag=i.readUnsignedByte();if(tag>1)throw bad();return tag==0?null:new Approval(text(i),text(i),text(i),text(i),text(i),text(i),text(i),instant(i));}
    private static void event(DataOutputStream o,Event e)throws IOException{
        o.writeBoolean(e!=null);if(e==null)return;uuid(o,e.eventId());scope(o,e.scope());o.writeLong(e.epoch());text(o,e.action().name());uuid(o,e.priorPublicationId());uuid(o,e.publicationId());text(o,e.state().name());uuid(o,e.requestId());text(o,e.commandSha256());instant(o,e.effectiveAt());approval(o,e.approval());
    }
    private static Event event(DataInputStream i)throws IOException{int tag=i.readUnsignedByte();if(tag>1)throw bad();return tag==0?null:new Event(uuid(i),scope(i),i.readLong(),Action.valueOf(text(i)),uuid(i),uuid(i),State.valueOf(text(i)),uuid(i),text(i),instant(i),approval(i));}
    private static <T> byte[] encode(int tag,T x,Writer<T> w){try{var b=new ByteArrayOutputStream();var o=new DataOutputStream(b);o.writeInt(1);o.writeInt(tag);w.put(o,x);o.flush();if(b.size()>MAX_BYTES)throw bad();return b.toByteArray();}catch(IOException e){throw new IllegalArgumentException("Invalid publication encoding",e);}}
    private static <T> T decode(int tag,byte[] b,Reader<T> r,Writer<T> w){if(b==null || b.length>MAX_BYTES)throw bad();try{var i=new DataInputStream(new ByteArrayInputStream(b));if(i.readInt()!=1 || i.readInt()!=tag)throw bad();T x=r.get(i);if(i.available()!=0 || !Arrays.equals(b,encode(tag,x,w)))throw bad();return x;}catch(IOException|NullPointerException e){throw new IllegalArgumentException("Invalid publication bytes",e);}}
    public byte[] encode(Envelope e){verifySource(e);return encode(1,e,ProductRevisionPublicationCodec::envelope);}
    public Envelope decode(byte[] b){return decode(1,b,ProductRevisionPublicationCodec::envelope,ProductRevisionPublicationCodec::envelope);}
    public byte[] encode(ProductRevisionPublication p){return encode(2,p,(o,v)->{uuid(o,v.publicationId());envelope(o,v.envelope());approval(o,v.approval());});}
    public ProductRevisionPublication publication(byte[] b){return decode(2,b,i->new ProductRevisionPublication(uuid(i),envelope(i),approval(i)),(o,v)->{uuid(o,v.publicationId());envelope(o,v.envelope());approval(o,v.approval());});}
    public byte[] encode(Event e){return encode(3,e,ProductRevisionPublicationCodec::event);}
    public Event event(byte[] b){return decode(3,b,ProductRevisionPublicationCodec::event,ProductRevisionPublicationCodec::event);}
    public byte[] encode(Receipt r){return encode(4,r,(o,v)->{scope(o,v.scope());text(o,v.actorId());uuid(o,v.requestId());text(o,v.commandSha256());text(o,v.kind());text(o,v.candidateDigest());event(o,v.event());instant(o,v.recordedAt());});}
    public Receipt receipt(byte[] b){return decode(4,b,i->new Receipt(scope(i),text(i),uuid(i),text(i),text(i),text(i),event(i),instant(i)),(o,v)->{scope(o,v.scope());text(o,v.actorId());uuid(o,v.requestId());text(o,v.commandSha256());text(o,v.kind());text(o,v.candidateDigest());event(o,v.event());instant(o,v.recordedAt());});}
    public String cursor(Cursor c){return Base64.getUrlEncoder().withoutPadding().encodeToString(encode(5,c,(o,v)->{scope(o,v.scope());instant(o,v.asOf());o.writeLong(v.highWaterEpoch());o.writeLong(v.beforeEpoch());}));}
    public Cursor cursor(String s){if(s==null || s.length()>4096)throw bad();var c=decode(5,Base64.getUrlDecoder().decode(s),i->new Cursor(scope(i),instant(i),i.readLong(),i.readLong()),(o,v)->{scope(o,v.scope());instant(o,v.asOf());o.writeLong(v.highWaterEpoch());o.writeLong(v.beforeEpoch());});if(!cursor(c).equals(s))throw bad();return c;}
    public String commandDigest(Command c){return ProductRevisionPublication.hash(encode(6,c,(o,v)->{
        text(o,v.kind().name());uuid(o,v.productDraftId());text(o,v.companyPartyId());uuid(o,v.requestId());o.writeLong(v.metadataVersion());o.writeLong(v.suppliedVersion());text(o,v.candidateDigest());uuid(o,v.publicationId());o.writeLong(v.expectedEpoch());uuid(o,v.expectedPublicationId());
        text(o,v.approval()==null?null:v.approval().instanceId());text(o,v.approval()==null?null:v.approval().taskId());text(o,v.approval()==null?null:v.approval().actionId());instant(o,v.asOf());o.writeInt(v.limit());text(o,v.cursor());}));}
    private static IllegalArgumentException bad(){return new IllegalArgumentException("Invalid canonical product publication payload");}
}
