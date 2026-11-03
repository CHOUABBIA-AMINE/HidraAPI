/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineManifestChecks
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.qualification
 *
 * @Description : Real draft-manifest assertion cases reusable by JUnit and an isolated Java harness.
 *
 */
package dz.sh.hidra.modules.topology.qualification;

import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.*;
import dz.sh.hidra.modules.topology.domain.service.EngineeringBaselineCompletenessPolicy;
import dz.sh.hidra.modules.topology.domain.service.EngineeringBaselineCompletenessPolicy.*;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringBaselineManifestCodec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

abstract class EngineeringBaselineManifestChecks {
    private static final Instant FROM = Instant.parse("2026-11-01T00:00:00Z");
    private static final Instant UNTIL = FROM.plusSeconds(60);
    private static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000605");
    private static final String SHA = "a".repeat(64);
    private static final Pin NETWORK = new Pin(Kind.NETWORK_PUBLICATION,"network","network","revision",SHA);
    private static final Pin PRODUCT = new Pin(Kind.PRODUCT_PUBLICATION,"product","catalog","revision",SHA);
    private static final Pin STATION = new Pin(Kind.STATION_CONFIGURATION,"station","station","revision",SHA);
    private static final Pin LINK = new Pin(Kind.CHARACTERISTIC_LINK,"unit","link","1",SHA);
    private static EngineeringBaselineManifest manifest(Pin... pins) {
        return new EngineeringBaselineManifest(ID,"company","pipeline",1,FROM,UNTIL,List.of(pins));
    }
    private static EngineeringBaselineManifestCodec codec() { return new EngineeringBaselineManifestCodec(16,16_384); }
    private static SourceFacts facts(Pin pin, Requirement... needs) {
        return new SourceFacts(pin,FROM,null,Set.of(needs),List.of());
    }
    private static Completeness inspect(EngineeringBaselineManifest m,SourceFacts... facts) {
        return new EngineeringBaselineCompletenessPolicy().inspect(m,List.of(facts));
    }
    private static void require(boolean condition) { if(!condition)throw new AssertionError("Contract violated"); }
    private static void eq(Object expected,Object actual) { if(!Objects.equals(expected,actual))throw new AssertionError("Unexpected result"); }
    private static void fails(Class<? extends Throwable> type,Runnable action) {
        try { action.run(); } catch(Throwable failure) {
            if(type.isInstance(failure))return;
            throw new AssertionError("Unexpected exception type",failure);
        }
        throw new AssertionError("Expected rejection");
    }
    static void checkCanonicalOrderIsIndependentOfRequestOrder() {
        eq(manifest(NETWORK,PRODUCT),manifest(PRODUCT,NETWORK));
        require(Arrays.equals(codec().encode(manifest(NETWORK,PRODUCT)),codec().encode(manifest(PRODUCT,NETWORK))));
    }
    static void checkCallerMutationCannotAlterTheDraft() {
        var source = new ArrayList<>(List.of(NETWORK));
        var draft = new EngineeringBaselineManifest(ID,"company","pipeline",1,FROM,UNTIL,source);
        source.clear(); eq(List.of(NETWORK),draft.pins());
        fails(UnsupportedOperationException.class,()->draft.pins().clear());
    }
    static void checkDuplicateSourceSelectionsAreRejected() {
        var otherRevision = new Pin(NETWORK.kind(),NETWORK.ownerId(),NETWORK.sourceId(),"other",SHA);
        fails(IllegalArgumentException.class,()->manifest(NETWORK,otherRevision));
    }
    static void checkMultipleNetworkPublicationsAreRejected() {
        fails(IllegalArgumentException.class,()->manifest(NETWORK,new Pin(NETWORK.kind(),"other","other","revision",SHA)));
    }
    static void checkMalformedOrPaddedIdentitiesAreRejectedWithoutNormalization() {
        for(String value:List.of(" company","company ","bad\u0000id","\ud800","a".repeat(81)))
            fails(IllegalArgumentException.class,()->new EngineeringBaselineManifest(ID,value,"pipeline",1,FROM,null,List.of()));
        eq("\ud83d\ude80",EngineeringBaselineManifest.identity("\ud83d\ude80"));
        eq("e\u0301",EngineeringBaselineManifest.identity("e\u0301"));
        require(!EngineeringBaselineManifest.identity("e\u0301").equals(EngineeringBaselineManifest.identity("\u00e9")));
        var unicode=new EngineeringBaselineManifest(ID,"\u00e9".repeat(80),"pipeline",1,FROM,null,List.of());
        eq(unicode,codec().decode(codec().encode(unicode)));
    }
    static void checkInvalidPrecisionAndIntervalsAreRejected() {
        fails(IllegalArgumentException.class,()->new EngineeringBaselineManifest(ID,"company","pipeline",1,FROM.plusNanos(1),null,List.of()));
        fails(IllegalArgumentException.class,()->new EngineeringBaselineManifest(ID,"company","pipeline",1,FROM,FROM,List.of()));
        fails(IllegalArgumentException.class,()->new EngineeringBaselineManifest(ID,"company","pipeline",0,FROM,null,List.of()));
    }
    static void checkReplacementPreservesHistoricalValuesAndChecksExpectedVersion() {
        var old = manifest(NETWORK); var next = old.replace(1,FROM,UNTIL,List.of(PRODUCT));
        eq(1L,old.version()); eq(List.of(NETWORK),old.pins()); eq(2L,next.version());
        eq(ID,next.manifestId()); eq("company",next.companyPartyId());
        fails(IllegalArgumentException.class,()->old.replace(0,FROM,UNTIL,List.of()));
    }
    static void checkVersionOverflowCannotWrapAround() {
        var old = new EngineeringBaselineManifest(ID,"company","pipeline",Long.MAX_VALUE,FROM,null,List.of());
        fails(ArithmeticException.class,()->old.replace(Long.MAX_VALUE,FROM,null,List.of()));
    }
    static void checkOpenSourceIntervalsMustCoverTheWholeDraftInterval() {
        require(manifest().coveredBy(FROM,null));
        require(!manifest().coveredBy(FROM.plusSeconds(1),null));
        require(!manifest().coveredBy(FROM,UNTIL.minusSeconds(1)));
        var open = new EngineeringBaselineManifest(ID,"company","pipeline",1,FROM,null,List.of());
        require(!open.coveredBy(FROM,UNTIL));
    }
    static void checkEmptyDraftExplicitlyReportsMissingRoots() {
        var report=inspect(manifest()); require(!report.complete());
        eq(Set.of(Finding.NETWORK_NOT_SELECTED,Finding.PRODUCT_NOT_SELECTED),
                new HashSet<>(report.diagnostics().stream().map(Diagnostic::finding).toList()));
    }
    static void checkCompleteDraftNeverBecomesQualifiedFrozenOrAuthoritative() {
        var draft=manifest(NETWORK,PRODUCT); var report=inspect(draft,facts(NETWORK),facts(PRODUCT));
        require(report.complete());
        require(!report.engineeringQualified()&&!report.authoritative()&&!report.frozen());
        require(!draft.engineeringQualified()&&!draft.authoritative()&&!draft.frozen());
    }
    static void checkOwnerDerivedMissingStationIsDiagnosed() {
        var report=inspect(manifest(NETWORK,PRODUCT),facts(NETWORK,new Requirement(Kind.STATION_CONFIGURATION,"station")),facts(PRODUCT));
        eq(List.of(new Diagnostic(Finding.STATION_CONFIGURATION_NOT_SELECTED,Kind.STATION_CONFIGURATION,"station")),report.diagnostics());
    }
    static void checkStationDerivedMissingCharacteristicIsDiagnosed() {
        var report=inspect(manifest(NETWORK,PRODUCT,STATION),
                facts(NETWORK,new Requirement(Kind.STATION_CONFIGURATION,"station")),facts(PRODUCT),
                facts(STATION,new Requirement(Kind.CHARACTERISTIC_LINK,"unit")));
        eq(List.of(new Diagnostic(Finding.CHARACTERISTIC_NOT_SELECTED,Kind.CHARACTERISTIC_LINK,"unit")),report.diagnostics());
    }
    static void checkUnrelatedSourceCannotBeAddedToACompleteManifest() {
        fails(IllegalArgumentException.class,()->inspect(manifest(NETWORK,PRODUCT,LINK),facts(NETWORK),facts(PRODUCT),facts(LINK)));
    }
    static void checkWrongRevisionOrDigestCannotSatisfyASelectedPin() {
        var altered=new Pin(NETWORK.kind(),NETWORK.ownerId(),NETWORK.sourceId(),NETWORK.revisionId(),"b".repeat(64));
        fails(IllegalArgumentException.class,()->inspect(manifest(NETWORK,PRODUCT),facts(altered),facts(PRODUCT)));
        fails(IllegalArgumentException.class,()->inspect(manifest(NETWORK,PRODUCT),facts(NETWORK)));
    }
    static void checkDuplicateOrInvalidOwnerRequirementsAreRejected() {
        fails(IllegalArgumentException.class,()->inspect(manifest(NETWORK),facts(NETWORK),facts(NETWORK)));
        fails(IllegalArgumentException.class,()->facts(PRODUCT,new Requirement(Kind.STATION_CONFIGURATION,"station")));
    }
    static void checkSourceCoverageFailureRemainsAnExplicitFinding() {
        var report=inspect(manifest(NETWORK,PRODUCT),
                new SourceFacts(NETWORK,FROM.plusSeconds(1),null,Set.of(),List.of()),facts(PRODUCT));
        eq(List.of(new Diagnostic(Finding.SOURCE_INTERVAL_NOT_COVERED,Kind.NETWORK_PUBLICATION,"network")),report.diagnostics());
    }
    static void checkSourceMethodAndApprovalGapsAreNeverInventedAway() {
        var report=inspect(manifest(NETWORK,PRODUCT),facts(NETWORK),
                new SourceFacts(PRODUCT,FROM,null,Set.of(),List.of(Finding.METHOD_NOT_SUPPLIED,Finding.APPROVAL_NOT_ESTABLISHED)));
        require(!report.complete()); eq(2,report.diagnostics().size());
    }

    static void checkEveryRequiredFamilyUsesExactOwnerDerivedMembership() {
        var all=manifest(NETWORK,PRODUCT,STATION,LINK);
        var networkFacts=facts(NETWORK,new Requirement(Kind.STATION_CONFIGURATION,"station"));
        var stationFacts=facts(STATION,new Requirement(Kind.CHARACTERISTIC_LINK,"unit"));
        var observed=inspect(all,networkFacts,facts(PRODUCT),stationFacts,facts(LINK));
        require(observed.complete());
        require(!observed.frozen()&&!observed.authoritative()&&!observed.engineeringQualified());
        eq(List.of(NETWORK,PRODUCT,STATION,LINK).stream()
                .sorted(Comparator.comparing(Pin::kind).thenComparing(Pin::ownerId)
                        .thenComparing(Pin::sourceId)).toList(),all.pins());
        eq(all,codec().decode(codec().encode(all)));
        var noStation=inspect(manifest(NETWORK,PRODUCT),networkFacts,facts(PRODUCT));
        require(!noStation.complete());
        eq(List.of(new Diagnostic(Finding.STATION_CONFIGURATION_NOT_SELECTED,
                Kind.STATION_CONFIGURATION,"station")),noStation.diagnostics());
        var noLink=inspect(manifest(NETWORK,PRODUCT,STATION),
                networkFacts,facts(PRODUCT),stationFacts);
        require(!noLink.complete());
        eq(List.of(new Diagnostic(Finding.CHARACTERISTIC_NOT_SELECTED,
                Kind.CHARACTERISTIC_LINK,"unit")),noLink.diagnostics());
    }
    static void checkComposedSourceWithdrawalAndEvidenceGapsNeverRewriteCapture() {
        var captured=manifest(NETWORK,PRODUCT,STATION,LINK);
        var ownerNetwork=facts(NETWORK,new Requirement(Kind.STATION_CONFIGURATION,"station"));
        var ownerStation=facts(STATION,new Requirement(Kind.CHARACTERISTIC_LINK,"unit"));
        require(inspect(captured,ownerNetwork,ownerStation,facts(PRODUCT),facts(LINK)).complete());
        var withdrawn=new SourceFacts(PRODUCT,FROM,null,Set.of(),
                List.of(Finding.SOURCE_NOT_CURRENTLY_ELIGIBLE,Finding.METHOD_NOT_SUPPLIED));
        var approvalLost=new SourceFacts(LINK,FROM,null,Set.of(),
                List.of(Finding.APPROVAL_NOT_ESTABLISHED));
        var current=inspect(captured,ownerNetwork,ownerStation,withdrawn,approvalLost);
        eq(Set.of(Finding.SOURCE_NOT_CURRENTLY_ELIGIBLE,Finding.METHOD_NOT_SUPPLIED,
                    Finding.APPROVAL_NOT_ESTABLISHED),
                new HashSet<>(current.diagnostics().stream().map(Diagnostic::finding).toList()));
        require(!current.complete());
        eq(captured,codec().decode(codec().encode(captured)));
        require(!captured.frozen()&&!captured.authoritative()&&!captured.engineeringQualified());
    }

    static void checkCanonicalRoundTripPreservesEveryField() {
        var draft=manifest(NETWORK,PRODUCT,STATION,LINK);
        eq(draft,codec().decode(codec().encode(draft)));
        eq("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad",
                codec().sha256("abc".getBytes(StandardCharsets.UTF_8)));
    }
    static void checkEveryTruncationAndTrailingDataAreRejected() {
        byte[] encoded=codec().encode(manifest(NETWORK,PRODUCT));
        for(int n=0;n<encoded.length;n++) {
            byte[] truncated=Arrays.copyOf(encoded,n);
            fails(IllegalArgumentException.class,()->codec().decode(truncated));
        }
        byte[] trailing=Arrays.copyOf(encoded,encoded.length+1);
        fails(IllegalArgumentException.class,()->codec().decode(trailing));
    }
    static void checkMalformedUtf8AndNoncanonicalMarkersAreRejected() {
        byte[] encoded=codec().encode(manifest(NETWORK));
        byte[] invalidUtf8=encoded.clone(); invalidUtf8[4]=(byte)0xc0;
        fails(IllegalArgumentException.class,()->codec().decode(invalidUtf8));
        int marker=4+EngineeringBaselineManifestCodec.FORMAT.length()+16+4+"company".length()+4+"pipeline".length()+8+12;
        byte[] invalidMarker=encoded.clone(); invalidMarker[marker]=2;
        fails(IllegalArgumentException.class,()->codec().decode(invalidMarker));
    }
    static void checkConfiguredEncodingBoundsAreEnforced() {
        fails(IllegalArgumentException.class,()->new EngineeringBaselineManifestCodec(0,1024));
        fails(IllegalArgumentException.class,()->new EngineeringBaselineManifestCodec(16,128).encode(manifest(NETWORK)));
        fails(IllegalArgumentException.class,()->new EngineeringBaselineManifestCodec(1,16384).encode(manifest(NETWORK,PRODUCT)));
        byte[] encoded=codec().encode(manifest(NETWORK,PRODUCT));
        fails(IllegalArgumentException.class,()->new EngineeringBaselineManifestCodec(1,16384).decode(encoded));
    }

    public static void main(String[] args) throws Exception {
        int count=0;
        for(var method:EngineeringBaselineManifestChecks.class.getDeclaredMethods()) {
            if(!java.lang.reflect.Modifier.isStatic(method.getModifiers()) || method.getParameterCount()!=0
                    || method.getReturnType()!=void.class || method.getName().startsWith("lambda$"))continue;
            method.invoke(null); count++;
        }
        if(count!=24)throw new AssertionError("Unexpected check inventory: "+count);
        System.out.println("PASS: "+count+" shared draft model, completeness and canonical codec checks");
    }
}
