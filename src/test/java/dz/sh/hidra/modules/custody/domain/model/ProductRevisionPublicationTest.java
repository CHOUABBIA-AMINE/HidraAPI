/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductRevisionPublicationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Preserves governed product publication evidence under exact owner admission.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;
import dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.*;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.*;
import dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.*;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ProductRevisionPublicationTest {
    private final ProductRevisionPublicationCodec codec=new ProductRevisionPublicationCodec();
    private final Scope scope=new Scope(UUID.randomUUID(),"شركة😀","product");
    private final Instant at=Instant.parse("2026-10-27T12:00:00.123456Z");
    private Envelope envelope(Facts facts){
        var m=new ProductEngineeringDraft.Metadata("مزيج😀",null,null,ProductEngineeringDraft.Classification.GAS);
        return new Envelope(scope,ProductRevisionPublication.Component.of(4,ProductEngineeringDraftMetadataCodec.FORMAT,new ProductEngineeringDraftMetadataCodec().encode(m)),
            ProductRevisionPublication.Component.of(2,SuppliedFluidPropertyRevisionCodec.FORMAT,new SuppliedFluidPropertyRevisionCodec().encode(facts)),4,"GAS",java.util.stream.Stream.concat(m.diagnostics().stream(),facts.diagnostics().stream()).toList(),List.of(),new Policy("definition",3,"type","purpose"));
    }
    private Envelope incomplete(){return envelope(new Facts(null,null,null,null,null));}
    private Approval approval(){return new Approval(ProductRevisionPublication.hash(codec.encode(incomplete())),"instance","task","action","history","human","Human Reviewer",at);}
    private Event first(){return ProductRevisionPublication.transition(scope,null,0,null,Action.PUBLISH,UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),"a".repeat(64),at,approval());}
    @Test void incompleteSourceRemainsExplicitAndUnqualified(){var e=incomplete();assertTrue(e.diagnostics().contains("COMPOSITION_NOT_AUTHORED"));assertFalse(e.engineeringQualified());assertEquals(e,codec.decode(codec.encode(e)));}
    @Test void preservesUnicodeAndIndependentVersionNumbers(){var e=incomplete();var d=codec.decode(codec.encode(e));assertEquals(scope,d.scope());assertEquals(4,d.metadata().version());assertEquals(2,d.supplied().version());assertArrayEquals(e.metadata().bytes(),d.metadata().bytes());}
    @Test void nullAndEmptyFactListsHaveDifferentEnvelopes(){var n=incomplete();var e=envelope(new Facts(null,List.of(),List.of(),null,null));assertFalse(Arrays.equals(codec.encode(n),codec.encode(e)));assertEquals(e,codec.decode(codec.encode(e)));}
    @Test void originalDecimalSpellingSurvivesPublicationBytes(){var f=new Facts(new Composition(Basis.MOLE_FRACTION,List.of(new SuppliedFluidPropertyRevision.Component("A",new Decimal("0.2500")),new SuppliedFluidPropertyRevision.Component("B",new Decimal("0.7500"))),null,null),null,null,null,null);var e=envelope(f);var d=codec.decode(codec.encode(e));assertArrayEquals(e.supplied().bytes(),d.supplied().bytes());var actual=new SuppliedFluidPropertyRevisionCodec().decode(d.supplied().bytes());assertEquals("0.2500",actual.composition().components().get(0).fraction().lexeme());}
    @Test void rejectsSourceHashSubstitution(){var c=incomplete().metadata();assertThrows(IllegalArgumentException.class,()->new ProductRevisionPublication.Component(c.version(),c.format(),"0".repeat(64),c.base64()));}
    @Test void rejectsStaleSuppliedMetadataBinding(){var e=incomplete();assertThrows(IllegalArgumentException.class,()->new Envelope(scope,e.metadata(),e.supplied(),3,"GAS",e.diagnostics(),e.units(),e.policy()));}
    @Test void rejectsDiagnosticSubstitution(){var e=incomplete();var changed=new Envelope(scope,e.metadata(),e.supplied(),4,"GAS",List.of(),e.units(),e.policy());assertThrows(IllegalArgumentException.class,()->codec.encode(changed));}
    @Test void rejectsTrailingUnknownAndMalformedEncoding(){byte[] b=codec.encode(incomplete());assertThrows(IllegalArgumentException.class,()->codec.decode(Arrays.copyOf(b,b.length+1)));b[3]=2;assertThrows(IllegalArgumentException.class,()->codec.decode(b));}
    @Test void rejectsUnsortedOrDuplicateUnitFacts(){var e=incomplete();var u=new Unit("u","DENSITY","u","u","density",at);assertThrows(IllegalArgumentException.class,()->new Envelope(scope,e.metadata(),e.supplied(),4,"GAS",e.diagnostics(),List.of(u,u),e.policy()));}
    @Test void candidateDigestExcludesPublicationAndApprovalAudit(){var e=incomplete();String digest=ProductRevisionPublication.hash(codec.encode(e));var p=new ProductRevisionPublication(UUID.randomUUID(),e,approval());assertEquals(digest,ProductRevisionPublication.hash(codec.encode(p.envelope())));assertFalse(p.engineeringQualified());assertEquals(p,codec.publication(codec.encode(p)));}
    @Test void withdrawAndReactivateUseContiguousEpochs(){var first=first();var w=ProductRevisionPublication.transition(scope,first.head(),1,first.publicationId(),Action.WITHDRAW,first.publicationId(),UUID.randomUUID(),UUID.randomUUID(),"b".repeat(64),at.plusNanos(1000),null);assertEquals(State.WITHDRAWN,w.state());var r=ProductRevisionPublication.transition(scope,w.head(),2,w.publicationId(),Action.REACTIVATE,w.publicationId(),UUID.randomUUID(),UUID.randomUUID(),"c".repeat(64),at.plusNanos(2000),approval());assertEquals(3,r.epoch());assertEquals(r,codec.event(codec.encode(r)));}
    @Test void equalOrBackwardEventClocksReject(){var e=first();for(var bad:List.of(at,at.minusNanos(1000)))assertThrows(IllegalArgumentException.class,()->ProductRevisionPublication.transition(scope,e.head(),1,e.publicationId(),Action.WITHDRAW,e.publicationId(),UUID.randomUUID(),UUID.randomUUID(),"b".repeat(64),bad,null));}
    @Test void staleExpectedHeadCannotTransition(){var e=first();assertThrows(IllegalArgumentException.class,()->ProductRevisionPublication.transition(scope,e.head(),0,null,Action.PUBLISH,UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),"b".repeat(64),at.plusSeconds(1),approval()));}
    @Test void firstTransitionCannotWithdrawOrReactivate(){for(var a:List.of(Action.WITHDRAW,Action.REACTIVATE))assertThrows(IllegalArgumentException.class,()->ProductRevisionPublication.transition(scope,null,0,null,a,UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),"b".repeat(64),at,a==Action.WITHDRAW?null:approval()));}
    @Test void requestReplayRequiresOriginalActorScopeAndBody(){var r=new Receipt(scope,"actor",UUID.randomUUID(),"a".repeat(64),"PREPARE",approval().digest(),null,at);assertEquals(r,codec.receipt(codec.encode(r)));r.requireReplay(scope,"actor",r.requestId(),r.commandSha256());assertThrows(IllegalArgumentException.class,()->r.requireReplay(scope,"other",r.requestId(),r.commandSha256()));assertThrows(IllegalArgumentException.class,()->r.requireReplay(scope,"actor",r.requestId(),"b".repeat(64)));}
    @Test void cursorPreservesScopeAsOfAndHighWater(){var c=new Cursor(scope,at,12,10);assertEquals(c,codec.cursor(codec.cursor(c)));assertThrows(IllegalArgumentException.class,()->codec.cursor(codec.cursor(c)+"="));assertThrows(IllegalArgumentException.class,()->new Cursor(scope,at,9,11));}
    @Test void subMicrosecondTimeAndMalformedIdentityReject(){assertThrows(IllegalArgumentException.class,()->ProductRevisionPublication.microseconds(at.plusNanos(1)));assertThrows(IllegalArgumentException.class,()->new Scope(UUID.randomUUID(),"\uD800","product"));}
}
