/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductRevisionPublicationRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.request
 *
 * @Description : Provides strict protected supplied-fluid draft HTTP contracts.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import tools.jackson.databind.JsonNode;
import java.time.Instant;
import java.util.*;

/** Only caller-owned selectors; no coercion, actor, scope or approval facts. */
public final class ProductRevisionPublicationRequests {
    private ProductRevisionPublicationRequests() {}
    @Schema(name="ProductPublicationApprovalSelection")
    public record Approval(String instanceId,String taskId,String actionId) {
        public Approval {for(String id:List.of(instanceId,taskId,actionId))ProductEngineeringDraftRequests.exact(id,80,true);}
    }
    @Schema(name="ProductPublicationPrepareRequest")
    public record Prepare(String requestId,Long metadataVersion,Long suppliedVersion) {
        public Prepare {ProductEngineeringDraftRequests.uuid(requestId);positive(metadataVersion);positive(suppliedVersion);}
        @JsonCreator(mode=JsonCreator.Mode.DELEGATING) public static Prepare from(JsonNode n){object(n,Set.of("requestId","metadataVersion","suppliedVersion"));return new Prepare(text(n,"requestId"),number(n,"metadataVersion"),number(n,"suppliedVersion"));}
    }
    @Schema(name="ProductPublicationPublishRequest")
    public record Publish(String requestId,String candidateDigest,Long expectedEpoch,String expectedPublicationId,Approval approval) {
        public Publish {ProductEngineeringDraftRequests.uuid(requestId);digest(candidateDigest);head(expectedEpoch,expectedPublicationId);Objects.requireNonNull(approval);}
        @JsonCreator(mode=JsonCreator.Mode.DELEGATING) public static Publish from(JsonNode n){object(n,Set.of("requestId","candidateDigest","expectedEpoch","expectedPublicationId","approval"));return new Publish(text(n,"requestId"),text(n,"candidateDigest"),number(n,"expectedEpoch"),text(n,"expectedPublicationId"),ProductRevisionPublicationRequests.approval(n.get("approval")));}
    }
    @Schema(name="ProductPublicationWithdrawRequest")
    public record Withdraw(String requestId,Long expectedEpoch,String expectedPublicationId) {
        public Withdraw {ProductEngineeringDraftRequests.uuid(requestId);head(expectedEpoch,expectedPublicationId);positive(expectedEpoch);}
        @JsonCreator(mode=JsonCreator.Mode.DELEGATING) public static Withdraw from(JsonNode n){object(n,Set.of("requestId","expectedEpoch","expectedPublicationId"));return new Withdraw(text(n,"requestId"),number(n,"expectedEpoch"),text(n,"expectedPublicationId"));}
    }
    @Schema(name="ProductPublicationReactivateRequest")
    public record Reactivate(String requestId,Long expectedEpoch,String expectedPublicationId,Approval approval) {
        public Reactivate {ProductEngineeringDraftRequests.uuid(requestId);head(expectedEpoch,expectedPublicationId);positive(expectedEpoch);Objects.requireNonNull(approval);}
        @JsonCreator(mode=JsonCreator.Mode.DELEGATING) public static Reactivate from(JsonNode n){object(n,Set.of("requestId","expectedEpoch","expectedPublicationId","approval"));return new Reactivate(text(n,"requestId"),number(n,"expectedEpoch"),text(n,"expectedPublicationId"),ProductRevisionPublicationRequests.approval(n.get("approval")));}
    }
    public static void digest(String s){if(s==null || !s.matches("[0-9a-f]{64}"))throw invalid();}
    public static Instant instant(String s){if(s==null || s.length()>40)throw invalid();var t=Instant.parse(s);if(t.getNano()%1000!=0)throw invalid();return t;}
    private static void positive(Long n){if(n==null || n<=0)throw invalid();}
    private static void head(Long n,String id){if(n==null || n<0 || n==Long.MAX_VALUE || (n==0)!=(id==null))throw invalid();if(id!=null)ProductEngineeringDraftRequests.uuid(id);}
    private static Approval approval(JsonNode n){object(n,Set.of("instanceId","taskId","actionId"));return new Approval(text(n,"instanceId"),text(n,"taskId"),text(n,"actionId"));}
    private static void object(JsonNode n,Set<String> fields){if(n==null || !n.isObject())throw invalid();for(String k:n.propertyNames())if(!fields.contains(k))throw invalid();}
    private static String text(JsonNode n,String key){var v=n.get(key);if(v==null || v.isNull())return null;if(!v.isTextual())throw invalid();return v.asText();}
    private static Long number(JsonNode n,String key){var v=n.get(key);if(v==null || !v.isIntegralNumber() || !v.canConvertToLong())throw invalid();return v.longValue();}
    private static IllegalArgumentException invalid(){return new IllegalArgumentException("Invalid product publication request");}
}
