/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductEngineeringDraftControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.controller
 *
 * @Description : Provides company-owned catalog-linked product metadata drafts.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase;
import dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.*;
import dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.json.JsonMapper;
import java.util.*;

class ProductEngineeringDraftControllerTest {
    private static final String URL="/api/v1/custody/engineering-products";
    private static final JsonMapper JSON=JsonMapper.builder().build();
    private ProductEngineeringDraftUseCase useCase;private MockMvc http;
    private UUID id,request;
    @BeforeEach void setup(){useCase=mock(ProductEngineeringDraftUseCase.class);http=MockMvcBuilders.standaloneSetup(new ProductEngineeringDraftController(useCase)).build();id=UUID.randomUUID();request=UUID.randomUUID();}
    private String body() {return "{\"companyPartyId\":\"company\",\"localCode\":\"local\",\"catalogProductId\":\"catalog\",\"creationRequestId\":\""+request+"\"}";}
    private Result result(boolean replay) {
        return new Result(new ProductEngineeringDraft(id,"company","local","catalog",request,1,new ProductEngineeringDraft.Metadata(null,null,null,null)),replay);
    }
    @Test void createReturns201AndFreshReplayReturns200WithDistinctIdentities() throws Exception {
        when(useCase.execute(any())).thenReturn(result(false),result(true));
        http.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body())).andExpect(status().isCreated())
            .andExpect(jsonPath("$.draftId").value(id.toString())).andExpect(jsonPath("$.catalogProductId").value("catalog"));
        http.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body())).andExpect(status().isOk())
            .andExpect(jsonPath("$.replayed").value(true));
        var capture=ArgumentCaptor.forClass(Command.class);verify(useCase,times(2)).execute(capture.capture());
        for(var command:capture.getAllValues()){assertThat(command.kind()).isEqualTo(Kind.CREATE);assertThat(command.creationRequestId()).isEqualTo(request);assertThat(command.catalogProductId()).isEqualTo("catalog");}
    }
    @Test void readAndUpdateBindCompanyAndExpectedVersionToTypedCommands() throws Exception {
        when(useCase.execute(any())).thenReturn(result(false));
        http.perform(get(URL+"/"+id).param("companyPartyId","company")).andExpect(status().isOk());
        http.perform(put(URL+"/"+id).param("companyPartyId","company").contentType(MediaType.APPLICATION_JSON)
            .content("{\"expectedVersion\":1,\"name\":\"Changed\",\"classification\":\"GAS\"}")).andExpect(status().isOk());
        var capture=ArgumentCaptor.forClass(Command.class);verify(useCase,times(2)).execute(capture.capture());
        assertThat(capture.getAllValues().get(0).kind()).isEqualTo(Kind.READ);
        var update=capture.getAllValues().get(1);assertThat(update.companyPartyId()).isEqualTo("company");assertThat(update.draftId()).isEqualTo(id);
        assertThat(update.expectedVersion()).isEqualTo(1);assertThat(update.catalogProductId()).isNull();assertThat(update.metadata().classification()).isEqualTo(ProductEngineeringDraft.Classification.GAS);
    }
    @Test void unknownServerOwnedAndImmutableUpdateFieldsNeverReachUseCase() throws Exception {
        for(String field:List.of("actor","grants","metadataComplete","version","sourceBindingId"))
            http.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body().replace("}",",\""+field+"\":\"forged\"}"))).andExpect(status().isBadRequest());
        for(String field:List.of("companyPartyId","localCode","catalogProductId","creationRequestId","draftId"))
            http.perform(put(URL+"/"+id).param("companyPartyId","company").contentType(MediaType.APPLICATION_JSON)
                .content("{\"expectedVersion\":1,\""+field+"\":\"forged\"}")).andExpect(status().isBadRequest());
        verifyNoInteractions(useCase);
    }
    @Test void numericTextCoercionFractionalOverflowAndUnknownLabelsReject() throws Exception {
        for(String version:List.of("0","-1","1.5","\"1\"","true","null","9223372036854775808"))
            http.perform(put(URL+"/"+id).param("companyPartyId","company").contentType(MediaType.APPLICATION_JSON)
                .content("{\"expectedVersion\":"+version+"}")).andExpect(status().isBadRequest());
        for(String extra:List.of(",\"name\":123",",\"classification\":true",",\"classification\":\"gas\"",",\"classification\":\"H2\""))
            http.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body().replace("}",extra+"}"))).andExpect(status().isBadRequest());
        http.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body().replace("\"local\"","123"))).andExpect(status().isBadRequest());
        verifyNoInteractions(useCase);
    }
    @Test void exactUnicodeCodePointBoundsAndExplicitNullsRoundTrip() throws Exception {
        when(useCase.execute(any())).thenReturn(result(false));String scalar="\uD83D\uDE80";
        var value=JSON.readTree(body()).deepCopy();((tools.jackson.databind.node.ObjectNode)value).put("localCode",scalar.repeat(80));
        http.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(JSON.writeValueAsString(value))).andExpect(status().isCreated());
        ((tools.jackson.databind.node.ObjectNode)value).put("localCode",scalar.repeat(81));
        http.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(JSON.writeValueAsString(value))).andExpect(status().isBadRequest());
        verify(useCase,times(1)).execute(any());
    }
    @Test void blankNulMalformedSurrogatesAndOversizeMetadataReject() throws Exception {
        for(String encoded:List.of("\" \"","\" name\"","\"name \"","\"name\\u0000\"","\"\\ud800\"","\""+"x".repeat(241)+"\"")) {
            http.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body().replace("}",",\"name\":"+encoded+"}"))).andExpect(status().isBadRequest());
        }
        verifyNoInteractions(useCase);
    }
    @Test void malformedShapeUuidMissingAndDuplicateScopeRejectWithoutDisclosure() throws Exception {
        for(String content:List.of("{}","null","[]","{",body().replace(request.toString(),"1-1-1-1-1")))
            http.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isBadRequest());
        http.perform(get(URL+"/"+id)).andExpect(status().isBadRequest());
        http.perform(get(URL+"/"+id).param("companyPartyId","company","other")).andExpect(status().isBadRequest());
        http.perform(get(URL+"/"+id).param("companyPartyId","company").param("actor","forged")).andExpect(status().isBadRequest());
        http.perform(get(URL+"/1-1-1-1-1").param("companyPartyId","company")).andExpect(status().isBadRequest());
        verifyNoInteractions(useCase);
    }
    @Test void responseUsesExplicitMetadataNullsAndNoAuthorityOrPersistenceFields() throws Exception {
        when(useCase.execute(any())).thenReturn(result(false));
        var response=http.perform(get(URL+"/"+id).param("companyPartyId","company")).andExpect(status().isOk()).andReturn().getResponse();
        var json=JSON.readTree(response.getContentAsString());assertThat(json.get("metadata").has("classification")).isTrue();assertThat(json.get("metadata").get("classification").isNull()).isTrue();
        assertThat(json.get("metadataComplete").asBoolean()).isFalse();assertThat(json.get("diagnostics").size()).isEqualTo(3);
        assertThat(response.getContentAsString()).doesNotContain("actor_binding","transaction_id","bindingId","permissions","@class");
    }
    @Test void classifiedErrorsHideRetainedCauseAndNeverInviteAutomaticRetry() throws Exception {
        for(Reason reason:Reason.values()) {
            reset(useCase);when(useCase.execute(any())).thenThrow(new Failure(reason,new IllegalStateException("private SQL metadata")));
            int code=switch(reason){case DENIED->403;case CONFLICT->409;case UNAVAILABLE,INDETERMINATE->503;};
            var response=http.perform(get(URL+"/"+id).param("companyPartyId","company")).andExpect(status().is(code))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON)).andExpect(jsonPath("$.code").value(reason.name())).andReturn().getResponse();
            assertThat(response.getContentAsString()).doesNotContain("private SQL metadata","IllegalStateException","stackTrace");assertThat(response.getHeader("Retry-After")).isNull();
        }
    }
}
