/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : Strict operation-specific installed-unit JSON descriptors without caller authority.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import dz.sh.hidra.modules.topology.domain.model.InstalledUnit;
import io.swagger.v3.oas.annotations.media.Schema;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.*;
import tools.jackson.databind.annotation.JsonDeserialize;
import java.time.Instant;
import java.time.DateTimeException;
import java.util.Set;

/** Operation-specific JSON shapes. No actor, grant, native claim or server time may be supplied. */
public final class InstalledUnitLifecycleRequests {
    private InstalledUnitLifecycleRequests() {}

    @Schema(name="InstalledUnitRegisterRequest")
    @JsonDeserialize(using=RegisterDeserializer.class)
    public record Register(String requestId,String companyPartyId,String equipmentKindId,
            InstalledUnit.Evidence manufacturerParty,InstalledUnit.Evidence manufacturerModel,
            InstalledUnit.Evidence serialNumber,String maintainableAssetId) {}

    @Schema(name="InstalledUnitRecordInstallationRequest")
    @JsonDeserialize(using=RecordDeserializer.class)
    public record RecordInstallation(String requestId,String companyPartyId,String installationId,
            String positionId,String facilityId,Instant effectiveFrom,Instant effectiveUntil,String evidenceReference) {}

    @Schema(name="InstalledUnitCloseInstallationRequest")
    @JsonDeserialize(using=CloseDeserializer.class)
    public record Close(String requestId,String companyPartyId,String positionId,String facilityId,
            Instant effectiveUntil,long expectedRevision) {}

    @Schema(name="InstalledUnitRelocateRequest")
    @JsonDeserialize(using=RelocateDeserializer.class)
    public record Relocate(String requestId,String companyPartyId,String replacementInstallationId,
            String sourcePositionId,String sourceFacilityId,String destinationPositionId,
            String destinationFacilityId,Instant effectiveAt,String evidenceReference,long expectedRevision) {}

    @Schema(name="InstalledUnitReplaceRequest")
    @JsonDeserialize(using=ReplaceDeserializer.class)
    public record Replace(String requestId,String companyPartyId,String replacementUnitId,
            String replacementInstallationId,String positionId,String facilityId,
            Instant effectiveAt,String evidenceReference,long expectedRevision) {}

    private static JsonNode object(JsonParser parser,JsonNode node,Set<String> allowed) {
        if(node==null || !node.isObject())throw DatabindException.from(parser,"Installed-unit object required");
        for(String key:node.propertyNames())
            if(!allowed.contains(key))throw DatabindException.from(parser,"Unsupported installed-unit field");
        return node;
    }
    private static String string(JsonParser parser,JsonNode node,String key,boolean required,int maximum) {
        JsonNode value=node.get(key);
        if(value==null || value.isNull()) {
            if(required)throw DatabindException.from(parser,"Required installed-unit string absent");
            return null;
        }
        if(!value.isString())throw DatabindException.from(parser,"Installed-unit field must be text");
        String text=value.stringValue();
        if(text.isBlank() || !text.equals(text.strip()) || text.codePointCount(0,text.length())>maximum)
            throw DatabindException.from(parser,"Invalid installed-unit string");
        return text;
    }
    private static Instant instant(JsonParser parser,JsonNode node,String key,boolean required) {
        String value=string(parser,node,key,required,40);
        if(value==null)return null;
        try {
            Instant parsed=Instant.parse(value);
            if(!parsed.toString().equals(value) || parsed.getNano()%1000!=0)
                throw DatabindException.from(parser,"Canonical UTC microseconds required");
            return parsed;
        } catch(DateTimeException failure){throw DatabindException.from(parser,"Invalid installed-unit instant");}
    }
    private static long revision(JsonParser parser,JsonNode node) {
        JsonNode number=node.get("expectedRevision");
        if(number==null || !number.isIntegralNumber())
            throw DatabindException.from(parser,"Expected revision must be an integer");
        try {
            long value=number.bigIntegerValue().longValueExact();
            if(value<1 || value==Long.MAX_VALUE)throw new ArithmeticException();
            return value;
        } catch(ArithmeticException invalid){
            throw DatabindException.from(parser,"Expected revision outside bounds");
        }
    }
    private static InstalledUnit.Evidence evidence(JsonParser parser,JsonNode root,String key) {
        JsonNode value=object(parser,root.get(key),Set.of("verification","value","sourceReference"));
        String classification=string(parser,value,"verification",true,16);
        InstalledUnit.Verification verification;
        try {verification=InstalledUnit.Verification.valueOf(classification);}
        catch(IllegalArgumentException invalid){throw DatabindException.from(parser,"Unknown evidence classification");}
        String source=string(parser,value,"sourceReference",false,1024);
        String text=string(parser,value,"value",false,1024);
        if(verification==InstalledUnit.Verification.UNKNOWN ? (source!=null || text!=null) : (source==null || text==null))
            throw DatabindException.from(parser,"Evidence value/provenance mismatch");
        return new InstalledUnit.Evidence(text,verification,source);
    }
    private static JsonNode root(JsonParser parser,DeserializationContext context,Set<String> fields) {
        return object(parser,context.readTree(parser),fields);
    }
    public static final class RegisterDeserializer extends ValueDeserializer<Register> {
        @Override public Register deserialize(JsonParser p,DeserializationContext c) {
            var n=root(p,c,Set.of("requestId","companyPartyId","equipmentKindId","manufacturerParty",
                    "manufacturerModel","serialNumber","maintainableAssetId"));
            return new Register(string(p,n,"requestId",true,80),string(p,n,"companyPartyId",true,80),
                    string(p,n,"equipmentKindId",true,80),evidence(p,n,"manufacturerParty"),
                    evidence(p,n,"manufacturerModel"),evidence(p,n,"serialNumber"),
                    string(p,n,"maintainableAssetId",false,80));
        }
    }
    public static final class RecordDeserializer extends ValueDeserializer<RecordInstallation> {
        @Override public RecordInstallation deserialize(JsonParser p,DeserializationContext c) {
            var n=root(p,c,Set.of("requestId","companyPartyId","installationId","positionId",
                    "facilityId","effectiveFrom","effectiveUntil","evidenceReference"));
            return new RecordInstallation(string(p,n,"requestId",true,80),string(p,n,"companyPartyId",true,80),
                    string(p,n,"installationId",true,80),string(p,n,"positionId",true,80),
                    string(p,n,"facilityId",true,80),instant(p,n,"effectiveFrom",true),
                    instant(p,n,"effectiveUntil",false),string(p,n,"evidenceReference",true,65536));
        }
    }
    public static final class CloseDeserializer extends ValueDeserializer<Close> {
        @Override public Close deserialize(JsonParser p,DeserializationContext c) {
            var n=root(p,c,Set.of("requestId","companyPartyId","positionId","facilityId","effectiveUntil","expectedRevision"));
            return new Close(string(p,n,"requestId",true,80),string(p,n,"companyPartyId",true,80),
                    string(p,n,"positionId",true,80),string(p,n,"facilityId",true,80),
                    instant(p,n,"effectiveUntil",true),revision(p,n));
        }
    }
    public static final class RelocateDeserializer extends ValueDeserializer<Relocate> {
        @Override public Relocate deserialize(JsonParser p,DeserializationContext c) {
            var n=root(p,c,Set.of("requestId","companyPartyId","replacementInstallationId",
                    "sourcePositionId","sourceFacilityId","destinationPositionId",
                    "destinationFacilityId","effectiveAt","evidenceReference","expectedRevision"));
            return new Relocate(string(p,n,"requestId",true,80),string(p,n,"companyPartyId",true,80),
                    string(p,n,"replacementInstallationId",true,80),
                    string(p,n,"sourcePositionId",true,80),string(p,n,"sourceFacilityId",true,80),
                    string(p,n,"destinationPositionId",true,80),string(p,n,"destinationFacilityId",true,80),
                    instant(p,n,"effectiveAt",true),string(p,n,"evidenceReference",true,65536),revision(p,n));
        }
    }
    public static final class ReplaceDeserializer extends ValueDeserializer<Replace> {
        @Override public Replace deserialize(JsonParser p,DeserializationContext c) {
            var n=root(p,c,Set.of("requestId","companyPartyId","replacementUnitId",
                    "replacementInstallationId","positionId","facilityId",
                    "effectiveAt","evidenceReference","expectedRevision"));
            return new Replace(string(p,n,"requestId",true,80),string(p,n,"companyPartyId",true,80),
                    string(p,n,"replacementUnitId",true,80),string(p,n,"replacementInstallationId",true,80),
                    string(p,n,"positionId",true,80),string(p,n,"facilityId",true,80),
                    instant(p,n,"effectiveAt",true),string(p,n,"evidenceReference",true,65536),revision(p,n));
        }
    }
}
