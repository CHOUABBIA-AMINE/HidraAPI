/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductEngineeringDraft
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Provides company-owned catalog-linked product metadata drafts.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import java.util.*;

/** An authored metadata revision; classification is never numerical qualification. */
public record ProductEngineeringDraft(UUID id, String companyPartyId, String localCode,
        String catalogProductId, UUID creationRequestId, long version, Metadata metadata) {
    public enum Classification { GAS, LIQUID }
    public record Metadata(String name, String description, String evidenceReference, Classification classification) {
        public Metadata {
            text(name,240,false); text(description,4000,false); text(evidenceReference,1000,false);
        }
        public List<String> diagnostics() {
            var result=new ArrayList<String>();
            if(name==null) result.add("NAME_NOT_AUTHORED");
            if(evidenceReference==null) result.add("EVIDENCE_NOT_AUTHORED");
            if(classification==null) result.add("CLASSIFICATION_UNSPECIFIED");
            return List.copyOf(result);
        }
    }
    public ProductEngineeringDraft {
        Objects.requireNonNull(id); Objects.requireNonNull(creationRequestId); Objects.requireNonNull(metadata);
        text(companyPartyId,80,true); text(localCode,80,true); text(catalogProductId,80,true);
        if(version<=0) throw new IllegalArgumentException("Positive product draft version required");
    }
    public ProductEngineeringDraft revise(long expectedVersion,Metadata next) {
        if(expectedVersion!=version || version==Long.MAX_VALUE) throw new IllegalArgumentException("Stale product draft version");
        return new ProductEngineeringDraft(id,companyPartyId,localCode,catalogProductId,creationRequestId,
                version+1,Objects.requireNonNull(next));
    }
    /** Exact scalar text: reject invalid UTF-16, NUL, blanks and surrounding whitespace. */
    public static void text(String value,int maximum,boolean required) {
        if(value==null) {if(required)throw new IllegalArgumentException("Required product identity");return;}
        if(value.isBlank() || !value.equals(value.strip()) || value.indexOf(0)>=0
                || value.codePointCount(0,value.length())>maximum)
            throw new IllegalArgumentException("Invalid exact product text");
        for(int i=0;i<value.length();i++) {
            char c=value.charAt(i);
            if(Character.isHighSurrogate(c)) {
                if(++i>=value.length() || !Character.isLowSurrogate(value.charAt(i)))
                    throw new IllegalArgumentException("Invalid Unicode scalar");
            } else if(Character.isLowSurrogate(c)) throw new IllegalArgumentException("Invalid Unicode scalar");
        }
    }
}
