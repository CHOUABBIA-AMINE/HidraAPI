/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductRevisionPublicationControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.controller
 *
 * @Description : Preserves governed product publication evidence under exact owner admission.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.controller;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import java.util.*;
class ProductRevisionPublicationControllerTest {
    private ProductRevisionPublicationUseCase owner;private MockMvc http;
    @BeforeEach void setup(){owner=mock(ProductRevisionPublicationUseCase.class);http=MockMvcBuilders.standaloneSetup(new ProductRevisionPublicationController(owner)).build();}
    private String url(){return "/api/v1/custody/engineering-products/"+draft+"/publications";}
    private String prepare(){return "{\"requestId\":\""+UUID.randomUUID()+"\",\"metadataVersion\":1,\"suppliedVersion\":2}";}
    private ResultActions postBody(String suffix,String body)throws Exception{return http.perform(post(url()+suffix).param("companyPartyId","company").contentType(MediaType.APPLICATION_JSON).content(body));}
    @Test void prepareCreationAndExactReplayPreserveOriginalEnvelopeBytes()throws Exception{var first=sample(false);when(owner.execute(any())).thenReturn(first,sample(true));postBody("/candidates",prepare()).andExpect(status().isCreated()).andExpect(jsonPath("$.candidate.supplied.version").value(2)).andExpect(jsonPath("$.candidate.supplied.base64").value(first.candidate().supplied().base64())).andExpect(jsonPath("$.candidate.engineeringQualified").value(false));postBody("/candidates",prepare()).andExpect(status().isOk()).andExpect(jsonPath("$.replayed").value(true));}
    @Test void unknownServerFieldsAndCoercedVersionsCannotReachOwner()throws Exception{for(String body:List.of(prepare().replace("\"metadataVersion\":1","\"metadataVersion\":\"1\""),prepare().replace("\"metadataVersion\":1","\"metadataVersion\":1.0"),prepare().replace("\"metadataVersion\":1","\"metadataVersion\":0"),prepare().replace("}",",\"actor\":\"forged\"}"),prepare().replace("}",",\"companyPartyId\":\"other\"}")))postBody("/candidates",body).andExpect(status().isBadRequest());verifyNoInteractions(owner);}
    @Test void duplicateAndExtraQueryScopesAreRejected()throws Exception{http.perform(get(url()+"/current").param("companyPartyId","company","other")).andExpect(status().isBadRequest());http.perform(get(url()+"/current").param("companyPartyId","company").param("actor","forged")).andExpect(status().isBadRequest());verifyNoInteractions(owner);}
    @Test void allReadSelectorsBindExplicitCompanyProductAndTemporalBounds()throws Exception{when(owner.execute(any())).thenReturn(sample(false));for(String suffix:List.of("/"+publication,"/current","/candidates/"+digest,"/requests/"+UUID.randomUUID()))http.perform(get(url()+suffix).param("companyPartyId","company")).andExpect(status().isOk());http.perform(get(url()+"/effective").param("companyPartyId","company").param("asOf",at.toString())).andExpect(status().isOk());http.perform(get(url()+"/history").param("companyPartyId","company").param("asOf",at.toString()).param("limit","100")).andExpect(status().isOk());verify(owner,times(6)).execute(argThat(c->c.companyPartyId().equals("company")&&c.productDraftId().equals(draft)));}
    @Test void historyLimitsMicrosecondsCursorAndDigestsRejectBeforeOwner()throws Exception{for(String n:List.of("0","101","1.0","01","-1"))http.perform(get(url()+"/history").param("companyPartyId","company").param("asOf",at.toString()).param("limit",n)).andExpect(status().isBadRequest());http.perform(get(url()+"/effective").param("companyPartyId","company").param("asOf","2026-10-28T00:00:00.000000001Z")).andExpect(status().isBadRequest());http.perform(get(url()+"/candidates/"+digest.toUpperCase(Locale.ROOT)).param("companyPartyId","company")).andExpect(status().isBadRequest());verifyNoInteractions(owner);}
    @Test void publishAndTransitionsCarryOnlySelectorsAndExpectedHead()throws Exception{when(owner.execute(any())).thenReturn(sample(false));String approval="{\"instanceId\":\"instance\",\"taskId\":\"task\",\"actionId\":\"action\"}";String prefix="{\"requestId\":\""+UUID.randomUUID()+"\",\"expectedEpoch\":1,\"expectedPublicationId\":\""+publication+"\"";postBody("",prefix+",\"candidateDigest\":\""+digest+"\",\"approval\":"+approval+"}").andExpect(status().isCreated());postBody("/"+publication+"/withdraw",prefix+"}").andExpect(status().isOk());postBody("/"+publication+"/reactivate",prefix+",\"approval\":"+approval+"}").andExpect(status().isOk());}
    @Test void typedFailuresNeverDiscloseSqlOrRetryAdvice()throws Exception{for(var reason:ProductRevisionPublicationUseCase.Reason.values()){doThrow(new ProductRevisionPublicationUseCase.Failure(reason,new RuntimeException("SQL private details"))).when(owner).execute(any());int code=switch(reason){case INVALID_REQUEST->400;case DENIED->403;case CONFLICT->409;default->503;};var out=http.perform(get(url()+"/current").param("companyPartyId","company")).andExpect(status().is(code)).andReturn().getResponse().getContentAsString();assertThat(out).doesNotContain("SQL private", "stackTrace");}}
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
