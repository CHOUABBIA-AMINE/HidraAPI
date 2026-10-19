/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft.Metadata;
import java.io.*;
import java.nio.charset.*;
import java.security.*;
import java.util.*;
/** Fixed length-prefixed UTF-8; null differs from supplied text. */
public final class EngineeringNetworkDraftCodec {
    public static final String FORMAT="HIDRA_ENGINEERING_METADATA_V1";
    public byte[] encode(Metadata m){
        try {var bytes=new ByteArrayOutputStream();var out=new DataOutputStream(bytes);out.writeInt(1);
            for(String s:new String[]{m.title(),m.description(),m.evidenceReference()}){
                if(s==null)out.writeInt(-1);else {byte[] b=s.getBytes(StandardCharsets.UTF_8);out.writeInt(b.length);out.write(b);}}
            byte[] result=bytes.toByteArray();if(result.length>32768)throw new IllegalArgumentException("Metadata encoding exceeds envelope");return result;
        }catch(IOException impossible){throw new IllegalStateException(impossible);}
    }
    public Metadata decode(byte[] bytes){
        if(bytes==null || bytes.length>32768)throw new IllegalArgumentException("Invalid metadata encoding");
        try {var in=new DataInputStream(new ByteArrayInputStream(bytes));if(in.readInt()!=1)throw new IOException();
            String[] values=new String[3];for(int i=0;i<3;i++){int n=in.readInt();if(n==-1)continue;
                if(n<0 || n>32768 || n>in.available())throw new IOException();
                values[i]=StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).decode(java.nio.ByteBuffer.wrap(in.readNBytes(n))).toString();}
            if(in.available()!=0)throw new IOException();var result=new Metadata(values[0],values[1],values[2]);
            if(!Arrays.equals(bytes,encode(result)))throw new IOException();return result;
        }catch(IOException e){throw new IllegalArgumentException("Invalid metadata encoding");}
    }
    public static String digest(byte[] bytes){try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));}
        catch(NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}}
}
