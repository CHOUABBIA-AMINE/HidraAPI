/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScenarioInputRevision
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 * @Description : Immutable and canonical engine-neutral scenario preparation with no numerical execution.
 */
package dz.sh.hidra.modules.simulation.domain.model;


import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** An exact receipt is input provenance, not a live/topology node or executable model. */
public record ScenarioInputRevision(UUID scenarioId, long revision, long expectedRevision,
                                    Scope scope, Baseline baseline, Inputs inputs,
                                    String actorId, Instant recordedAt,
                                    String inputDigest, String fingerprint) {
    public static final String POLICY = "T31_INPUT_V1";
    public static final String NUMERICAL_CAPABILITY = "NOT_AVAILABLE";
    public enum Mode { STEADY, TRANSIENT }
    public enum Slot { SUPPLY_PRESSURE, DELIVERY_MASS_FLOW }
    public enum Unit { PA, BAR_ABSOLUTE, KG_PER_S }

    public record Scope(String companyId, String pipelineSystemId) {
        public Scope { companyId=identifier(companyId,80); pipelineSystemId=identifier(pipelineSystemId,80); }
    }
    /** The actual owner digest and epoch are taken only from an authorized EXACT Topology result. */
    public record Baseline(UUID receiptId,UUID manifestId,long epoch,String digest) {
        public Baseline {
            Objects.requireNonNull(receiptId); Objects.requireNonNull(manifestId);
            if(epoch<1)throw invalid();
            digest=ScenarioInputRevision.digest(digest);
        }
    }
    public record Boundary(Slot slot, BigDecimal value, Unit unit) {
        public Boundary {
            Objects.requireNonNull(slot); Objects.requireNonNull(unit); Objects.requireNonNull(value);
            if(value.precision()>18 || value.scale()>6 || value.scale()< -17)throw invalid();
            if(slot==Slot.SUPPLY_PRESSURE) {
                if(unit!=Unit.PA && unit!=Unit.BAR_ABSOLUTE || value.signum()<=0)throw invalid();
            } else if(slot==Slot.DELIVERY_MASS_FLOW) {
                if(unit!=Unit.KG_PER_S || value.signum()<0)throw invalid();
            } else throw invalid();
        }
        public BigDecimal si() {
            return slot==Slot.SUPPLY_PRESSURE && unit==Unit.BAR_ABSOLUTE
                    ? value.multiply(new BigDecimal("100000")).stripTrailingZeros()
                    : value.stripTrailingZeros();
        }
        public String canonicalValue(){return value.stripTrailingZeros().toPlainString();}
    }
    public record Inputs(String name,Mode mode,UUID baselineReceiptId,String expectedManifestDigest,
                         List<Boundary> boundaries,Integer durationSeconds,Integer stepSeconds,String requestKey) {
        public Inputs {
            name=identifier(name,120);
            Objects.requireNonNull(mode); Objects.requireNonNull(baselineReceiptId);
            expectedManifestDigest=digest(expectedManifestDigest);
            requestKey=identifier(requestKey,80);
            if(boundaries==null || boundaries.size()!=2)throw invalid();
            if(boundaries.stream().anyMatch(Objects::isNull))throw invalid();
            boundaries=boundaries.stream().sorted(
                Comparator.comparingInt(item->item.slot()==Slot.SUPPLY_PRESSURE?0:1)).toList();
            if(boundaries.get(0).slot()!=Slot.SUPPLY_PRESSURE
                    ||boundaries.get(1).slot()!=Slot.DELIVERY_MASS_FLOW)throw invalid();
            if(mode==Mode.STEADY) {
                if(durationSeconds!=null||stepSeconds!=null)throw invalid();
            } else {
                if(durationSeconds==null||stepSeconds==null||durationSeconds<=0||stepSeconds<=0
                        ||durationSeconds%stepSeconds!=0||durationSeconds/stepSeconds>10000)throw invalid();
            }
        }
        public Boundary pressure(){return boundaries.get(0);}
        public Boundary delivery(){return boundaries.get(1);}
    }
    public ScenarioInputRevision {
        Objects.requireNonNull(scenarioId); Objects.requireNonNull(scope);
        Objects.requireNonNull(baseline); Objects.requireNonNull(inputs);
        actorId=identifier(actorId,80); Objects.requireNonNull(recordedAt);
        if(revision<1 || expectedRevision!=revision-1
                || !baseline.receiptId().equals(inputs.baselineReceiptId())
                || !baseline.digest().equals(inputs.expectedManifestDigest()))throw invalid();
        inputDigest=digest(inputDigest); fingerprint=digest(fingerprint);
        if(!inputDigest.equals(computeInputDigest(scope,baseline,inputs))
                || !fingerprint.equals(computeFingerprint(scenarioId,expectedRevision,actorId,inputs.requestKey(),inputDigest)))
            throw invalid();
    }
    public static ScenarioInputRevision prepare(UUID scenarioId,long revision,Scope scope,Baseline baseline,
                                               Inputs inputs,String actorId,Instant now) {
        String input=computeInputDigest(scope,baseline,inputs);
        return new ScenarioInputRevision(scenarioId,revision,revision-1,scope,baseline,inputs,actorId,now,input,
                computeFingerprint(scenarioId,revision-1,actorId,inputs.requestKey(),input));
    }
    public String policyId(){return POLICY;}
    public String validationStatus(){return "VALIDATED_INPUTS";}
    public String numericalCapability(){return NUMERICAL_CAPABILITY;}
    public List<String> diagnosticCodes(){return List.of();}
    public static String computeInputDigest(Scope s,Baseline b,Inputs in) {
        return sha("T31_INPUT_V1\n"+part(s.companyId())+part(s.pipelineSystemId())
                +part(b.receiptId().toString())+part(b.manifestId().toString())+part(Long.toString(b.epoch()))
                +part(b.digest())+part(in.name())+part(in.mode().name())
                +part(in.pressure().slot().name())+part(in.pressure().canonicalValue())
                +part(in.pressure().unit().name())+part(in.delivery().slot().name())
                +part(in.delivery().canonicalValue())+part(in.delivery().unit().name())
                +part(in.durationSeconds()==null?"NONE":in.durationSeconds().toString())
                +part(in.stepSeconds()==null?"NONE":in.stepSeconds().toString()));
    }
    /** Length-prefixed UTF-8 fields prevent ambiguous concatenation and platform-locale drift. */
    private static String part(String v){return v.getBytes(StandardCharsets.UTF_8).length+":"+v+"\n";}
    public static String computeFingerprint(UUID id,long expected,String actor,String key,String digest) {
        return sha("T31_REPLAY_V1\n"+part(id.toString())+part(Long.toString(expected))
                +part(actor)+part(key)+part(digest));
    }
    public static String createReplayFingerprint(Scope scope,Inputs inputs,String actor,Baseline baseline) {
        return sha("T31_CREATE_V1\n"+part(actor)+part(inputs.requestKey())
                +part(computeInputDigest(scope,baseline,inputs)));
    }
    public static String sha(String input) {
        try {return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(input.getBytes(StandardCharsets.UTF_8)));}
        catch(NoSuchAlgorithmException e){throw new IllegalStateException(e);}
    }
    private static String digest(String value){
        if(value==null||!value.matches("[0-9a-f]{64}"))throw invalid();
        return value;
    }
    private static String identifier(String value,int max){
        if(value==null||value.isBlank()||!value.equals(value.strip())||value.length()>max
                ||value.codePoints().anyMatch(c->Character.isISOControl(c)))throw invalid();
        return value;
    }
    private static IllegalArgumentException invalid(){return new IllegalArgumentException("Invalid T31 scenario input");}
    @Override public String toString(){return "ScenarioInputRevision[redacted]";}
}
