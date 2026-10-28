/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductPublicationSourceQueryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.integration
 *
 * @Description : Preserves governed product publication evidence under exact owner admission.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.integration;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import dz.sh.hidra.modules.custody.application.contract.publication.ProductPublicationSourceContract.*;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase;
import java.util.*;
class ProductPublicationSourceQueryAdapterTest {
    private final ProductRevisionPublicationUseCase owner=mock(ProductRevisionPublicationUseCase.class);
    private final ProductPublicationSourceQueryAdapter adapter=new ProductPublicationSourceQueryAdapter(owner);
    @Test void exactHistoricalExportPreservesBytesVersionsOriginalLexemesAndNullEmpty(){var original=sample(false);when(owner.execute(any())).thenReturn(original);var s=adapter.read(new Query(Mode.EXACT_HISTORICAL,"company",draft,publication,null)).orElseThrow();assertThat(s.currentlyEligible()).isFalse();assertThat(s.engineeringQualified()).isFalse();assertThat(s.envelope().supplied().base64()).isEqualTo(original.candidate().supplied().base64());assertThat(s.authoredMetadata().name()).isEqualTo("Exact é");assertThat(s.authoredFacts()).contains(new ScalarFact("supplied.composition.components[0].fraction","1.0000"),new ScalarFact("supplied.properties",null),new ScalarFact("supplied.methods.size","0"));verify(owner).execute(argThat(c->c.kind()==ProductRevisionPublicationUseCase.Kind.READ_EXACT&&c.companyPartyId().equals("company")&&c.publicationId().equals(publication)));}
    @Test void eligibleModesDelegateFreshProtectedQueriesWithExactAsOf(){when(owner.execute(any())).thenReturn(sample(false));assertThat(adapter.read(new Query(Mode.ELIGIBLE_CURRENT,"company",draft,null,null)).orElseThrow().currentlyEligible()).isTrue();assertThat(adapter.read(new Query(Mode.ELIGIBLE_EFFECTIVE,"company",draft,null,at))).isPresent();verify(owner).execute(argThat(c->c.kind()==ProductRevisionPublicationUseCase.Kind.ELIGIBLE_EFFECTIVE&&at.equals(c.asOf())));}
    @Test void withdrawnOrMissingSelectionNeverFallsBack(){var s=sample(false);when(owner.execute(any())).thenReturn(new ProductRevisionPublicationUseCase.Result(null,null,null,null,null,null,List.of(),null,false,false));assertThat(adapter.read(new Query(Mode.ELIGIBLE_CURRENT,"company",draft,null,null))).isEmpty();when(owner.execute(any())).thenReturn(new ProductRevisionPublicationUseCase.Result(s.candidate(),digest,s.publication(),s.observedHead(),s.selectedHead(),null,List.of(),null,false,false));assertThat(adapter.read(new Query(Mode.ELIGIBLE_EFFECTIVE,"company",draft,null,at))).isEmpty();}
    @Test void ownerDenialAndUnavailabilityCannotBecomeHistoricalFallback(){for(var reason:List.of(ProductRevisionPublicationUseCase.Reason.DENIED,ProductRevisionPublicationUseCase.Reason.UNAVAILABLE)){doThrow(new ProductRevisionPublicationUseCase.Failure(reason)).when(owner).execute(any());assertThatThrownBy(()->adapter.read(new Query(Mode.EXACT_HISTORICAL,"company",draft,publication,null))).isInstanceOf(ProductRevisionPublicationUseCase.Failure.class);}}
    @Test void malformedMixedSelectorsNeverReachOwner(){assertThatThrownBy(()->new Query(Mode.EXACT_HISTORICAL,"company",draft,null,null)).isInstanceOf(IllegalArgumentException.class);assertThatThrownBy(()->new Query(Mode.ELIGIBLE_CURRENT,"company",draft,publication,null)).isInstanceOf(IllegalArgumentException.class);assertThatThrownBy(()->new Query(Mode.ELIGIBLE_EFFECTIVE,"company",draft,null,at.plusNanos(1))).isInstanceOf(IllegalArgumentException.class);verifyNoInteractions(owner);}
    private final UUID draft=UUID.randomUUID(),publication=UUID.randomUUID();
    private final String digest="a".repeat(64);
    private final java.time.Instant at=java.time.Instant.parse("2026-10-28T00:00:00Z");
    private ProductRevisionPublicationUseCase.Result sample(boolean replay){
        var scope=new dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Scope(draft,"company","catalog");
        var md=new dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Metadata("Exact é",null,null,dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Classification.GAS);
        var f=new dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.Facts(new dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.Composition(dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.Basis.MOLE_FRACTION,List.of(new dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.Component("A",new dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.Decimal("1.0000"))),null,null),null,List.of(),null,null);
        var m=dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Component.of(1,"HIDRA_PRODUCT_METADATA_V1",new dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.ProductEngineeringDraftMetadataCodec().encode(md));
        var s=dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Component.of(2,"HIDRA_SUPPLIED_FLUID_V1",new dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.SuppliedFluidPropertyRevisionCodec().encode(f));
        var e=new dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Envelope(scope,m,s,1,"GAS",List.of("INCOMPLETE"),List.of(),new dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Policy("def",1,"type","purpose"));
        var a=new dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Approval(digest,"instance","task","action","history","human","Human Reviewer",at);
        var p=new dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication(publication,e,a);
        var h=new dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Head(scope,publication,1,dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.State.ACTIVE,UUID.randomUUID(),at);
        return new ProductRevisionPublicationUseCase.Result(e,digest,p,h,h,null,List.of(),null,replay,true);
    }
}
