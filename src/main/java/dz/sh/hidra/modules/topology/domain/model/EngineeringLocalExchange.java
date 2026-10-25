/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchange
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.util.*;
import java.security.*;

/** Portable origin fields are unverified user data, never an authority ticket. */
public record EngineeringLocalExchange(String format, Editor editor, String companyPartyId,
        String pipelineSystemId, UUID contextId, UUID draftId, long sourceVersion,
        long componentVersion, String componentFormat, String sha256, String payload,
        String branchId, Long boundGraphVersion, String boundGraphSha256) {
    public static final String FORMAT="HIDRA_ENGINEERING_LOCAL_EXCHANGE_V1";
    public enum Editor {
        METADATA("HIDRA_ENGINEERING_METADATA_V1",32768),
        GRAPH("HIDRA_ENGINEERING_GRAPH_DRAFT_V1",1048576),
        SURVEY("HIDRA_ENGINEERING_SURVEY_DRAFT_V1",16777216);
        private final String format;private final int maximum;
        Editor(String format,int maximum){this.format=format;this.maximum=maximum;}
        public String componentFormat(){return format;}
        public int maximumBytes(){return maximum;}
        public int maximumBase64Characters(){return 4*((maximum+2)/3);}
    }
    public EngineeringLocalExchange {
        if(!FORMAT.equals(format) || editor==null)throw new IllegalArgumentException("Unsupported exchange format/editor");
        EngineeringNetworkDraft.identity(companyPartyId);EngineeringNetworkDraft.identity(pipelineSystemId);
        Objects.requireNonNull(contextId);Objects.requireNonNull(draftId);
        if(sourceVersion<1 || componentVersion<1 || componentVersion>sourceVersion
                || editor==Editor.METADATA && componentVersion!=sourceVersion)
            throw new IllegalArgumentException("Invalid source version");
        if(!editor.componentFormat().equals(componentFormat) || sha256==null || !sha256.matches("[0-9a-f]{64}")
                || payload==null || payload.length()>editor.maximumBase64Characters())
            throw new IllegalArgumentException("Invalid component envelope");
        if(editor==Editor.SURVEY){
            EngineeringGraphDraft.identity(branchId);
            if(boundGraphVersion==null || boundGraphVersion<1 || boundGraphVersion>=componentVersion
                    || boundGraphSha256==null || !boundGraphSha256.matches("[0-9a-f]{64}"))
                throw new IllegalArgumentException("Invalid survey binding provenance");
        }else if(branchId!=null || boundGraphVersion!=null || boundGraphSha256!=null)
            throw new IllegalArgumentException("Survey provenance on another editor");
        byte[] bytes=Base64.getDecoder().decode(payload);
        if(bytes.length==0 || bytes.length>editor.maximumBytes()
                || !Base64.getEncoder().encodeToString(bytes).equals(payload) || !digest(bytes).equals(sha256))
            throw new IllegalArgumentException("Noncanonical or altered component");
    }
    public byte[] bytes(){return Base64.getDecoder().decode(payload);}
    public static String digest(byte[] bytes){
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));}
        catch(NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
}
