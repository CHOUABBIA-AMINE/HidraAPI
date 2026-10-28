/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductRevisionPublication
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Preserves governed product publication evidence under exact owner admission.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import java.security.*;
import java.time.Instant;
import java.util.*;

/** Immutable authoring evidence; publication never establishes engineering qualification. */
public record ProductRevisionPublication(UUID publicationId, Envelope envelope, Approval approval) {
    public ProductRevisionPublication {Objects.requireNonNull(publicationId);Objects.requireNonNull(envelope);Objects.requireNonNull(approval);}
    public boolean engineeringQualified(){return false;}
    public record Scope(UUID productDraftId,String companyPartyId,String catalogProductId) {
        public Scope {Objects.requireNonNull(productDraftId);identity(companyPartyId);identity(catalogProductId);}
    }
    public record Component(long version,String format,String sha256,String base64) {
        public Component {
            if(version<1 || format==null || !format.matches("[A-Z0-9_]{1,80}") || base64==null || base64.length()>2800000)throw invalid();
            digest(sha256);byte[] b=Base64.getDecoder().decode(base64);
            if(!Base64.getEncoder().encodeToString(b).equals(base64) || !hash(b).equals(sha256))throw invalid();
        }
        public byte[] bytes(){return Base64.getDecoder().decode(base64);}
        public static Component of(long v,String f,byte[] b){return new Component(v,f,hash(b),Base64.getEncoder().encodeToString(b));}
    }
    public record Policy(String definitionId,int definitionVersion,String targetTypeId,String purposeId) {
        public Policy {identity(definitionId);identity(targetTypeId);identity(purposeId);if(definitionVersion<1)throw invalid();}
    }
    public record Unit(String unitId,String role,String code,String symbol,String dimension,Instant updatedAt) {
        public Unit {identity(unitId);identity(role);Objects.requireNonNull(code);Objects.requireNonNull(symbol);Objects.requireNonNull(dimension);microseconds(updatedAt);}
        public String key(){return role+":"+unitId;}
    }
    public record Envelope(Scope scope,Component metadata,Component supplied,long suppliedMetadataVersion,
            String classification,List<String> diagnostics,List<Unit> units,Policy policy) {
        public Envelope {
            Objects.requireNonNull(scope);Objects.requireNonNull(metadata);Objects.requireNonNull(supplied);Objects.requireNonNull(policy);
            if(suppliedMetadataVersion!=metadata.version() || !Set.of("GAS","LIQUID").contains(classification))throw invalid();
            diagnostics=List.copyOf(diagnostics);units=List.copyOf(units);
            if(diagnostics.size()>128 || new HashSet<>(diagnostics).size()!=diagnostics.size() || units.size()>128)throw invalid();
            diagnostics.forEach(x->{if(x==null || x.isEmpty() || x.length()>300)throw invalid();});
            String prior=null;for(var u:units){if(prior!=null && prior.compareTo(u.key())>=0)throw invalid();prior=u.key();}
        }
        public boolean engineeringQualified(){return false;}
    }
    public record Approval(String digest,String instanceId,String taskId,String actionId,String historyId,
            String actorId,String actorDisplayName,Instant actedAt) {
        public Approval {ProductRevisionPublication.digest(digest);identity(instanceId);identity(taskId);identity(actionId);identity(historyId);identity(actorId);
            if(actorDisplayName==null || actorDisplayName.isBlank())throw invalid();microseconds(actedAt);}
    }
    public enum Action { PUBLISH, WITHDRAW, REACTIVATE }
    public enum State { ACTIVE, WITHDRAWN }
    public record Head(Scope scope,UUID publicationId,long epoch,State state,UUID eventId,Instant effectiveAt) {
        public Head {Objects.requireNonNull(scope);Objects.requireNonNull(publicationId);Objects.requireNonNull(state);Objects.requireNonNull(eventId);microseconds(effectiveAt);if(epoch<1)throw invalid();}
    }
    public record Event(UUID eventId,Scope scope,long epoch,Action action,UUID priorPublicationId,
            UUID publicationId,State state,UUID requestId,String commandSha256,Instant effectiveAt,Approval approval) {
        public Event {Objects.requireNonNull(eventId);Objects.requireNonNull(scope);Objects.requireNonNull(action);Objects.requireNonNull(publicationId);Objects.requireNonNull(state);Objects.requireNonNull(requestId);
            digest(commandSha256);microseconds(effectiveAt);
            if(epoch<1 || (epoch==1)!=(priorPublicationId==null) || (action==Action.WITHDRAW)!=(state==State.WITHDRAWN)
                || (action==Action.WITHDRAW)!=(approval==null) || action!=Action.PUBLISH && !publicationId.equals(priorPublicationId)
                || action==Action.PUBLISH && publicationId.equals(priorPublicationId))throw invalid();}
        public Head head(){return new Head(scope,publicationId,epoch,state,eventId,effectiveAt);}
    }
    public static Event transition(Scope scope,Head current,long expectedEpoch,UUID expectedId,Action action,
            UUID publicationId,UUID eventId,UUID requestId,String commandDigest,Instant at,Approval approval) {
        microseconds(at);if(expectedEpoch<0 || (expectedEpoch==0)!=(expectedId==null))throw invalid();
        if(current==null){if(expectedEpoch!=0 || action!=Action.PUBLISH)throw invalid();}
        else if(!scope.equals(current.scope()) || expectedEpoch!=current.epoch() || !current.publicationId().equals(expectedId)
                || !at.isAfter(current.effectiveAt()) || action==Action.PUBLISH && publicationId.equals(expectedId)
                || action!=Action.PUBLISH && (!publicationId.equals(expectedId) || (action==Action.WITHDRAW)!=(current.state()==State.ACTIVE)))throw invalid();
        long epoch;try{epoch=Math.addExact(expectedEpoch,1);}catch(ArithmeticException e){throw invalid();}
        return new Event(eventId,scope,epoch,action,expectedId,publicationId,action==Action.WITHDRAW?State.WITHDRAWN:State.ACTIVE,requestId,commandDigest,at,approval);
    }
    public record Receipt(Scope scope,String actorId,UUID requestId,String commandSha256,String kind,
            String candidateDigest,Event event,Instant recordedAt) {
        public Receipt {Objects.requireNonNull(scope);identity(actorId);Objects.requireNonNull(requestId);digest(commandSha256);digest(candidateDigest);microseconds(recordedAt);
            if(!Set.of("PREPARE","PUBLISH","WITHDRAW","REACTIVATE").contains(kind) || kind.equals("PREPARE")!=(event==null)
                || event!=null && (!scope.equals(event.scope()) || !requestId.equals(event.requestId()) || !commandSha256.equals(event.commandSha256())))throw invalid();}
        public void requireReplay(Scope actual,String actor,UUID request,String digest){if(!scope.equals(actual) || !actorId.equals(actor) || !requestId.equals(request) || !commandSha256.equals(digest))throw invalid();}
    }
    public record Cursor(Scope scope,Instant asOf,long highWaterEpoch,long beforeEpoch) {
        public Cursor {Objects.requireNonNull(scope);microseconds(asOf);if(highWaterEpoch<0 || beforeEpoch<1 || beforeEpoch>highWaterEpoch+1)throw invalid();}
    }
    public static void identity(String value){ProductEngineeringDraft.text(value,80,true);}
    public static void digest(String value){if(value==null || !value.matches("[0-9a-f]{64}"))throw invalid();}
    public static Instant microseconds(Instant value){if(value==null || value.getNano()%1000!=0)throw invalid();return value;}
    public static String hash(byte[] bytes){try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));}catch(NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}}
    private static IllegalArgumentException invalid(){return new IllegalArgumentException("Invalid product publication evidence");}
}
