/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductEngineeringDraftMetadataCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Provides company-owned catalog-linked product metadata drafts.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Metadata;
import dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Classification;
import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.security.*;
import java.util.*;

/** Versioned fixed-order length-prefixed strict UTF-8. Null has its own representation. */
public final class ProductEngineeringDraftMetadataCodec {
    public static final String FORMAT="HIDRA_PRODUCT_METADATA_V1";
    public byte[] encode(Metadata metadata) {
        Objects.requireNonNull(metadata);
        try {
            var bytes=new ByteArrayOutputStream();var out=new DataOutputStream(bytes);out.writeInt(1);
            for(String text:new String[]{metadata.name(),metadata.description(),metadata.evidenceReference(),
                    metadata.classification()==null?null:metadata.classification().name()}) {
                if(text==null) out.writeInt(-1);
                else {byte[] value=text.getBytes(StandardCharsets.UTF_8);out.writeInt(value.length);out.write(value);}
            }
            byte[] result=bytes.toByteArray();if(result.length>32768)throw new IllegalArgumentException("Metadata exceeds envelope");
            return result;
        } catch(IOException impossible){throw new IllegalStateException(impossible);}
    }
    public Metadata decode(byte[] bytes) {
        if(bytes==null || bytes.length<20 || bytes.length>32768)throw new IllegalArgumentException("Invalid product metadata");
        try {
            var input=new DataInputStream(new ByteArrayInputStream(bytes));if(input.readInt()!=1)throw new IOException();
            String[] values=new String[4];
            for(int i=0;i<4;i++) {
                int length=input.readInt();if(length==-1)continue;
                if(length<0 || length>input.available())throw new IOException();
                values[i]=StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
                        .onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(input.readNBytes(length))).toString();
            }
            if(input.available()!=0)throw new IOException();
            var metadata=new Metadata(values[0],values[1],values[2],values[3]==null?null:Classification.valueOf(values[3]));
            if(!Arrays.equals(bytes,encode(metadata)))throw new IOException();return metadata;
        } catch(IOException failure){throw new IllegalArgumentException("Invalid product metadata");}
    }
    public static String digest(byte[] bytes) {
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));}
        catch(NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    public String requestDigest(String company,String code,String catalog,UUID request,Metadata metadata) {
        try {
            var bytes=new ByteArrayOutputStream();var out=new DataOutputStream(bytes);
            for(String value:List.of(company,code,catalog,request.toString())) {
                byte[] encoded=value.getBytes(StandardCharsets.UTF_8);out.writeInt(encoded.length);out.write(encoded);
            }
            out.write(encode(metadata));return digest(bytes.toByteArray());
        }catch(IOException impossible){throw new IllegalStateException(impossible);}
    }
}
