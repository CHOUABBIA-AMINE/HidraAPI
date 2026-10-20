/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraft
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.util.*;

/** Immutable metadata foundation; no authored graph or engineering qualification is inferred. */
public record EngineeringNetworkDraft(UUID contextId, String companyPartyId, String pipelineSystemId,
        UUID draftId, long version, Metadata metadata) {
    public EngineeringNetworkDraft {
        Objects.requireNonNull(contextId);identity(companyPartyId);identity(pipelineSystemId);
        Objects.requireNonNull(draftId);Objects.requireNonNull(metadata);
        if(version<1)throw new IllegalArgumentException("Positive version required");
    }
    public record Metadata(String title,String description,String evidenceReference) {
        public Metadata {title=bounded(title,255);description=bounded(description,16384);evidenceReference=bounded(evidenceReference,2048);}
        private static String bounded(String s,int n){
            if(s==null || s.isBlank())return null;
            if(s.codePointCount(0,s.length())>n || s.indexOf('\0')>=0)throw new IllegalArgumentException("Metadata bounds exceeded");
            // Reject malformed UTF-16 rather than letting UTF-8 replacement collapse request digests.
            for(int i=0;i<s.length();i++)if(Character.isSurrogate(s.charAt(i))){
                if(!Character.isHighSurrogate(s.charAt(i)) || i+1>=s.length() || !Character.isLowSurrogate(s.charAt(++i)))
                    throw new IllegalArgumentException("Malformed metadata text");
            }
            return s;
        }
    }
    public EngineeringNetworkDraft updated(Metadata next){
        return new EngineeringNetworkDraft(contextId,companyPartyId,pipelineSystemId,draftId,Math.addExact(version,1),next);
    }
    /** An authored graph advances the one aggregate version without replacing metadata. */
    public EngineeringNetworkDraft graphUpdated(){return updated(metadata);}
    public List<String> diagnostics(){
        var d=new ArrayList<String>();d.add("GRAPH_NOT_AUTHORED");
        if(metadata.title()==null)d.add("TITLE_NOT_SUPPLIED");
        if(metadata.evidenceReference()==null)d.add("EVIDENCE_NOT_SUPPLIED");return List.copyOf(d);
    }
    public static String identity(String s){
        if(s==null || s.isBlank() || !s.equals(s.trim()) || s.codePointCount(0,s.length())>80 || s.indexOf('\0')>=0)
            throw new IllegalArgumentException("Exact scope identity required");return s;
    }
    public static UUID uuid(String s){
        if(s==null)throw new IllegalArgumentException("UUID required");UUID u=UUID.fromString(s);if(!u.toString().equals(s))throw new IllegalArgumentException("Canonical UUID required");return u;
    }
}
