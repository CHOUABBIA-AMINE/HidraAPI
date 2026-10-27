/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SuppliedFluidPropertyRevisionCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Preserves exact authored fluid facts independently of numerical qualification.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.*;
import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.security.*;
import java.util.*;

/** Versioned canonical binary envelope; exact decimal strings and null/empty survive. */
public final class SuppliedFluidPropertyRevisionCodec {
    public static final String FORMAT="HIDRA_SUPPLIED_FLUID_V1";
    public static final int MAX_BYTES=1048576;
    @FunctionalInterface private interface Writer<T>{void put(DataOutputStream out,T value)throws IOException;}
    @FunctionalInterface private interface Reader<T>{T get(DataInputStream in)throws IOException;}
    private static void text(DataOutputStream o,String s)throws IOException {if(s==null){o.writeInt(-1);return;}var e=StandardCharsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);var b=e.encode(CharBuffer.wrap(s));o.writeInt(b.remaining());o.write(b.array(),b.position(),b.remaining());}
    private static String text(DataInputStream in)throws IOException{int n=in.readInt();if(n==-1)return null;if(n<0 || n>MAX_BYTES || n>in.available())throw bad();byte[] b=in.readNBytes(n);return StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(b)).toString();}
    private static <T> void optional(DataOutputStream o,T x,Writer<T> w)throws IOException{o.writeBoolean(x!=null);if(x!=null)w.put(o,x);}
    private static <T> T optional(DataInputStream i,Reader<T> r)throws IOException{int tag=i.readUnsignedByte();if(tag>1)throw bad();return tag==0?null:r.get(i);}
    private static <T> void list(DataOutputStream o,List<T> xs,Writer<T> w)throws IOException{if(xs==null){o.writeInt(-1);return;}o.writeInt(xs.size());for(var x:xs)w.put(o,x);}
    private static <T> List<T> list(DataInputStream i,int max,Reader<T> r)throws IOException{int n=i.readInt();if(n==-1)return null;if(n<0 || n>max)throw bad();var out=new ArrayList<T>();for(int j=0;j<n;j++)out.add(r.get(i));return List.copyOf(out);}
    private static void decimal(DataOutputStream o,Decimal d)throws IOException{text(o,d.lexeme());}
    private static Decimal decimal(DataInputStream i)throws IOException{return new Decimal(text(i));}
    private static void source(DataOutputStream o,Source s)throws IOException{text(o,s.origin().name());text(o,s.reference());text(o,s.evidenceReference());}
    private static Source source(DataInputStream i)throws IOException{return new Source(Origin.valueOf(text(i)),text(i),text(i));}
    private static void key(DataOutputStream o,MethodKey k)throws IOException{text(o,k.reference());text(o,k.revision());}
    private static MethodKey key(DataInputStream i)throws IOException{return new MethodKey(text(i),text(i));}
    private static void condition(DataOutputStream o,Condition c)throws IOException{decimal(o,c.value());text(o,c.unitId());}
    private static Condition condition(DataInputStream i)throws IOException{return new Condition(decimal(i),text(i));}
    private static void range(DataOutputStream o,Range c)throws IOException{decimal(o,c.minimum());decimal(o,c.maximum());text(o,c.unitId());}
    private static Range range(DataInputStream i)throws IOException{return new Range(decimal(i),decimal(i),text(i));}
    public byte[] encode(Facts facts) {
        Objects.requireNonNull(facts);
        try{var bytes=new ByteArrayOutputStream();var o=new DataOutputStream(bytes);o.writeInt(1);
            optional(o,facts.composition(),(s,c)->{text(s,c.basis().name());list(s,c.components(),(a,v)->{text(a,v.reference());decimal(a,v.fraction());});optional(s,c.method(),SuppliedFluidPropertyRevisionCodec::key);optional(s,c.source(),SuppliedFluidPropertyRevisionCodec::source);});
            list(o,facts.properties(),(s,p)->{text(s,p.observationId());text(s,p.name());text(s,p.property().name());decimal(s,p.value());text(s,p.unitId());condition(s,p.absolutePressure());condition(s,p.absoluteTemperature());optional(s,p.method(),SuppliedFluidPropertyRevisionCodec::key);optional(s,p.source(),SuppliedFluidPropertyRevisionCodec::source);});
            list(o,facts.methods(),(s,m)->{key(s,m.key());text(s,m.origin().name());text(s,m.basis().name());list(s,m.components(),SuppliedFluidPropertyRevisionCodec::text);list(s,m.properties(),(a,v)->text(a,v.name()));range(s,m.absolutePressure());range(s,m.absoluteTemperature());text(s,m.evidenceReference());});
            optional(o,facts.source(),SuppliedFluidPropertyRevisionCodec::source);text(o,facts.description());o.flush();byte[] b=bytes.toByteArray();if(b.length>MAX_BYTES)throw bad();return b;
        }catch(IOException failure){throw new IllegalArgumentException("Invalid fluid encoding",failure);}
    }
    public Facts decode(byte[] bytes) {
        if(bytes==null || bytes.length>MAX_BYTES)throw bad();
        try{var i=new DataInputStream(new ByteArrayInputStream(bytes));if(i.readInt()!=1)throw bad();
            var c=optional(i,s->new Composition(Basis.valueOf(text(s)),list(s,64,a->new Component(text(a),decimal(a))),optional(s,SuppliedFluidPropertyRevisionCodec::key),optional(s,SuppliedFluidPropertyRevisionCodec::source)));
            var p=list(i,32,s->new Observation(text(s),text(s),Property.valueOf(text(s)),decimal(s),text(s),condition(s),condition(s),optional(s,SuppliedFluidPropertyRevisionCodec::key),optional(s,SuppliedFluidPropertyRevisionCodec::source)));
            var m=list(i,16,s->new Method(key(s),Origin.valueOf(text(s)),Basis.valueOf(text(s)),list(s,64,SuppliedFluidPropertyRevisionCodec::text),list(s,3,a->Property.valueOf(text(a))),range(s),range(s),text(s)));
            var f=new Facts(c,p,m,optional(i,SuppliedFluidPropertyRevisionCodec::source),text(i));
            if(i.available()!=0 || !Arrays.equals(bytes,encode(f)))throw bad();return f;
        }catch(IOException|NullPointerException failure){throw new IllegalArgumentException("Invalid fluid payload",failure);}
    }
    public String requestDigest(UUID product,UUID request,long expected,long metadata,Facts facts){
        try{var b=new ByteArrayOutputStream();var o=new DataOutputStream(b);text(o,product.toString());text(o,request.toString());o.writeLong(expected);o.writeLong(metadata);o.write(encode(facts));return digest(b.toByteArray());}catch(IOException impossible){throw bad();}
    }
    public static String digest(byte[] b){try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(b));}catch(NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}}
    private static IllegalArgumentException bad(){return new IllegalArgumentException("Invalid canonical supplied fluid payload");}
}
