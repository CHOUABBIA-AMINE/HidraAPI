/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductEngineeringDraftRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.request
 *
 * @Description : Provides company-owned catalog-linked product metadata drafts.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import tools.jackson.databind.JsonNode;
import java.util.*;

/** Explicit wire shapes; no coercion of caller identities, metadata or expected version. */
public final class ProductEngineeringDraftRequests {
    private ProductEngineeringDraftRequests() {}
    @Schema(name="ProductEngineeringDraftClassification",description="Authored metadata label only; no physical-property or engine qualification.")
    public enum Classification { GAS, LIQUID }

    @Schema(name="ProductEngineeringDraftCreateRequest")
    public record Create(
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,maxLength=80,description="Exact owner company Party identifier.") String companyPartyId,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,maxLength=80,description="Immutable company-local case-sensitive code, measured in Unicode scalar values.") String localCode,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,maxLength=80,description="Distinct admitted shared Custody catalog product identifier.") String catalogProductId,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,format="uuid",description="Canonical lowercase UUID; exact original requests replay the current draft.") String creationRequestId,
            @Schema(maxLength=240,nullable=true) String name,
            @Schema(maxLength=4000,nullable=true) String description,
            @Schema(maxLength=1000,nullable=true) String evidenceReference,
            @Schema(nullable=true,description="Absent classification remains unspecified.") Classification classification) {
        public Create {
            exact(companyPartyId,80,true);exact(localCode,80,true);exact(catalogProductId,80,true);uuid(creationRequestId);
            exact(name,240,false);exact(description,4000,false);exact(evidenceReference,1000,false);
        }
        @JsonCreator(mode=JsonCreator.Mode.DELEGATING)
        public static Create from(JsonNode node) {
            object(node,Set.of("companyPartyId","localCode","catalogProductId","creationRequestId","name","description","evidenceReference","classification"));
            return new Create(text(node,"companyPartyId"),text(node,"localCode"),text(node,"catalogProductId"),text(node,"creationRequestId"),
                    text(node,"name"),text(node,"description"),text(node,"evidenceReference"),label(node));
        }
    }
    @Schema(name="ProductEngineeringDraftUpdateRequest")
    public record Update(
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,minimum="1",format="int64",description="Positive integral current metadata version; conflicts do not append.") Long expectedVersion,
            @Schema(maxLength=240,nullable=true) String name,
            @Schema(maxLength=4000,nullable=true) String description,
            @Schema(maxLength=1000,nullable=true) String evidenceReference,
            @Schema(nullable=true) Classification classification) {
        public Update {
            if(expectedVersion==null || expectedVersion<=0)throw invalid();
            exact(name,240,false);exact(description,4000,false);exact(evidenceReference,1000,false);
        }
        @JsonCreator(mode=JsonCreator.Mode.DELEGATING)
        public static Update from(JsonNode node) {
            object(node,Set.of("expectedVersion","name","description","evidenceReference","classification"));
            var version=node.get("expectedVersion");
            if(version==null || !version.isIntegralNumber() || !version.canConvertToLong())throw invalid();
            return new Update(version.longValue(),text(node,"name"),text(node,"description"),text(node,"evidenceReference"),label(node));
        }
    }
    private static void object(JsonNode node,Set<String> fields) {
        if(node==null || !node.isObject())throw invalid();
        for(String field:node.propertyNames())if(!fields.contains(field))throw invalid();
    }
    private static String text(JsonNode node,String field) {
        var value=node.get(field);if(value==null || value.isNull())return null;
        if(!value.isTextual())throw invalid();return value.asText();
    }
    private static Classification label(JsonNode node) {
        String value=text(node,"classification");return value==null?null:Classification.valueOf(value);
    }
    public static UUID uuid(String value) {
        if(value==null || !value.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"))throw invalid();
        return UUID.fromString(value);
    }
    public static void exact(String value,int maximum,boolean required) {
        if(value==null) {if(required)throw invalid();return;}
        if(value.isBlank() || !value.equals(value.strip()) || value.indexOf(0)>=0 || value.codePointCount(0,value.length())>maximum)throw invalid();
        for(int index=0;index<value.length();index++) {
            char character=value.charAt(index);
            if(Character.isHighSurrogate(character)) {
                if(++index>=value.length() || !Character.isLowSurrogate(value.charAt(index)))throw invalid();
            }else if(Character.isLowSurrogate(character))throw invalid();
        }
    }
    private static IllegalArgumentException invalid(){return new IllegalArgumentException("Invalid product draft request");}
}
