/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SurveyRouteProfileCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Encodes bounded canonical survey values without coordinate conversion.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile;
import dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile.*;
import java.io.*;
import java.math.*;
import java.nio.*;
import java.nio.charset.*;
import java.security.*;
import java.util.*;

public final class SurveyRouteProfileCodec {
    public static final String FORMAT="HIDRA_ENGINEERING_SURVEY_DRAFT_V1";
    public static final int MAX_BYTES=16*1024*1024;
    private static final class Bounded extends ByteArrayOutputStream {
        @Override public synchronized void write(int b){if(count>=MAX_BYTES)throw new IllegalArgumentException("Survey byte limit");super.write(b);}
        @Override public synchronized void write(byte[] b,int off,int len){if(len>MAX_BYTES-count)throw new IllegalArgumentException("Survey byte limit");super.write(b,off,len);}
    }
    public byte[] encode(SurveyRouteProfile p) {
        Objects.requireNonNull(p);
        try {
            var bytes=new Bounded();var o=new DataOutputStream(bytes);
            text(o,FORMAT);text(o,p.branchId());text(o,p.horizontalCrs().authority());text(o,p.horizontalCrs().code());
            for(var a:p.horizontalCrs().axes()){text(o,a.name());text(o,a.unitIdentifier());}
            o.writeBoolean(p.verticalReference()!=null);
            if(p.verticalReference()!=null){text(o,p.verticalReference().datumAuthority());text(o,p.verticalReference().datumCode());text(o,p.verticalReference().unitIdentifier());}
            text(o,p.chainageOriginReference());text(o,p.direction().name());text(o,p.evidenceReference());
            o.writeInt(p.points().size());for(var v:p.points()){
                text(o,v.id());decimal(o,v.firstCoordinate());decimal(o,v.secondCoordinate());decimal(o,v.chainageMetres());
                nullable(o,v.elevation());decimal(o,v.coordinatePrecision());decimal(o,v.chainagePrecisionMetres());nullable(o,v.elevationPrecision());
            }
            o.writeInt(p.sections().size());for(var s:p.sections()){
                text(o,s.id());decimal(o,s.startChainageMetres());decimal(o,s.endChainageMetres());text(o,s.evidenceReference());
            }
            o.flush();return bytes.toByteArray();
        }catch(IOException e){throw new IllegalArgumentException("Survey encoding failed",e);}
    }
    public SurveyRouteProfile decode(byte[] bytes){
        if(bytes==null || bytes.length<16 || bytes.length>MAX_BYTES)throw new IllegalArgumentException("Survey byte bounds");
        try {
            var i=new DataInputStream(new ByteArrayInputStream(bytes));
            if(!FORMAT.equals(text(i)))throw new IllegalArgumentException("Survey format");
            String branch=text(i),authority=text(i),code=text(i);
            var crs=new Crs(authority,code,List.of(new Axis(text(i),text(i)),new Axis(text(i),text(i))));
            VerticalReference vertical=marker(i)?new VerticalReference(text(i),text(i),text(i)):null;
            String origin=text(i);Direction direction=Direction.valueOf(text(i));String evidence=text(i);
            int n=count(i,SurveyRouteProfile.MAX_POINTS,32);var points=new ArrayList<Point>(n);
            for(int x=0;x<n;x++)points.add(new Point(text(i),decimal(i),decimal(i),decimal(i),nullable(i),decimal(i),decimal(i),nullable(i)));
            n=count(i,SurveyRouteProfile.MAX_SECTIONS,20);var sections=new ArrayList<Section>(n);
            for(int x=0;x<n;x++)sections.add(new Section(text(i),decimal(i),decimal(i),text(i)));
            if(i.available()!=0)throw new IllegalArgumentException("Trailing survey bytes");
            var p=new SurveyRouteProfile(branch,crs,vertical,origin,direction,evidence,points,sections);
            if(!Arrays.equals(bytes,encode(p)))throw new IllegalArgumentException("Noncanonical survey bytes");return p;
        }catch(IOException|ArithmeticException e){throw new IllegalArgumentException("Invalid survey bytes",e);}
    }
    private static int count(DataInputStream i,int max,int minimum)throws IOException{
        int n=i.readInt();if(n<0 || n>max || n>i.available()/minimum)throw new IllegalArgumentException("Survey count");return n;
    }
    private static void text(DataOutputStream o,String s)throws IOException{byte[] b=s.getBytes(StandardCharsets.UTF_8);o.writeInt(b.length);o.write(b);}
    private static String text(DataInputStream i)throws IOException{
        int n=i.readInt();if(n<0 || n>8192 || n>i.available())throw new IllegalArgumentException("Survey text length");
        return StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT)
            .decode(ByteBuffer.wrap(i.readNBytes(n))).toString();
    }
    private static void decimal(DataOutputStream o,BigDecimal d)throws IOException{byte[] b=d.unscaledValue().toByteArray();o.writeInt(d.scale());o.writeInt(b.length);o.write(b);}
    private static BigDecimal decimal(DataInputStream i)throws IOException{
        int scale=i.readInt(),n=i.readInt();if(n<1 || n>MAX_BYTES || n>i.available())throw new IllegalArgumentException("Decimal length");
        byte[] b=i.readNBytes(n);var value=new BigInteger(b);
        if(!Arrays.equals(b,value.toByteArray()))throw new IllegalArgumentException("Noncanonical decimal integer");return new BigDecimal(value,scale);
    }
    private static void nullable(DataOutputStream o,BigDecimal d)throws IOException{o.writeBoolean(d!=null);if(d!=null)decimal(o,d);}
    private static BigDecimal nullable(DataInputStream i)throws IOException{return marker(i)?decimal(i):null;}
    private static boolean marker(DataInputStream i)throws IOException{int b=i.readUnsignedByte();if(b>1)throw new IllegalArgumentException("Nullable marker");return b==1;}
    public static String sha256(byte[] bytes){try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));}catch(NoSuchAlgorithmException e){throw new IllegalStateException(e);}}
}
