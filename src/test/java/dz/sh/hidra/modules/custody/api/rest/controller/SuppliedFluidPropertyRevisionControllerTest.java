/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SuppliedFluidPropertyRevisionControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.controller
 *
 * @Description : Provides strict protected supplied-fluid draft HTTP contracts.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase;
import dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase.*;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.*;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.json.JsonMapper;
import java.util.*;

class SuppliedFluidPropertyRevisionControllerTest {
    private static final String ROOT="/api/v1/custody/engineering-products/";
    private static final JsonMapper JSON=JsonMapper.builder().build();
    private SuppliedFluidPropertyRevisionUseCase useCase;private MockMvc http;private UUID id,request;
    @BeforeEach void setup(){useCase=mock(SuppliedFluidPropertyRevisionUseCase.class);http=MockMvcBuilders.standaloneSetup(new SuppliedFluidPropertyRevisionController(useCase)).build();id=UUID.randomUUID();request=UUID.fromString("aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaaa");}
    private String url(){return ROOT+id+"/properties";}
    private String body(){return "{\"requestId\":\""+request+"\",\"expectedVersion\":0,\"expectedProductMetadataVersion\":1}";}
    private String with(String fields){return body().substring(0,body().length()-1)+","+fields+"}";}
    private Result result(long version,boolean replay,Facts facts){return new Result(new SuppliedFluidPropertyRevision(id,"company","local","catalog",version,1,Classification.GAS,facts),replay,true);}
    private Facts absent(){return new Facts(null,null,null,null,null);}
    private ResultActions putBody(String body)throws Exception{return http.perform(put(url()).param("companyPartyId","company").contentType(MediaType.APPLICATION_JSON).content(body));}
    @Test void firstWriteSuccessorAndReplayHaveDistinctStatusAndIdentity()throws Exception {
        when(useCase.execute(any())).thenReturn(result(1,false,absent()),result(2,false,absent()),result(2,true,absent()));
        putBody(body()).andExpect(status().isCreated()).andExpect(jsonPath("$.productDraftId").value(id.toString())).andExpect(jsonPath("$.catalogProductId").value("catalog"));
        putBody(body().replace("\"expectedVersion\":0","\"expectedVersion\":1")).andExpect(status().isOk()).andExpect(jsonPath("$.version").value(2));
        putBody(body()).andExpect(status().isOk()).andExpect(jsonPath("$.replayed").value(true));
    }
    @Test void readBindsOnlyCurrentCompanyAndProductWithoutCallerVersions()throws Exception {
        when(useCase.execute(any())).thenReturn(result(1,false,absent()));http.perform(get(url()).param("companyPartyId","company")).andExpect(status().isOk());
        var c=ArgumentCaptor.forClass(Command.class);verify(useCase).execute(c.capture());assertThat(c.getValue()).isEqualTo(new Command(Kind.READ,"company",id,null,null,null,null));
    }
    @Test void versionsRequestIdsAndDecimalNumbersNeverCoerce()throws Exception {
        for(String body:List.of(body().replace("\"expectedVersion\":0","\"expectedVersion\":\"0\""),body().replace("\"expectedVersion\":0","\"expectedVersion\":0.0"),body().replace("\"expectedVersion\":0","\"expectedVersion\":-1"),body().replace("\"expectedVersion\":0","\"expectedVersion\":9223372036854775808"),body().replace("\"expectedProductMetadataVersion\":1","\"expectedProductMetadataVersion\":0"),body().replace(request.toString(),request.toString().toUpperCase(Locale.ROOT))))putBody(body).andExpect(status().isBadRequest());
        putBody(with("\"composition\":{\"basis\":\"MOLE_FRACTION\",\"components\":[{\"reference\":\"A\",\"fraction\":1}]}" )).andExpect(status().isBadRequest());verifyNoInteractions(useCase);
    }
    @Test void serverOwnedAndNestedUnknownFieldsCannotReachApplication()throws Exception {
        for(String key:List.of("actor","classification","catalogProductId","productDraftId","qualified","authoringComplete","backendPid","sha256","companyPartyId"))putBody(with("\""+key+"\":\"forged\"" )).andExpect(status().isBadRequest());
        for(String block:List.of("\"composition\":{\"basis\":\"MOLE_FRACTION\",\"components\":[{\"reference\":\"A\",\"fraction\":\"1\",\"certified\":true}]}","\"source\":{\"origin\":\"SYNTHETIC\",\"reference\":\"source\",\"approved\":true}"))putBody(with(block)).andExpect(status().isBadRequest());verifyNoInteractions(useCase);
    }
    @Test void exactFractionsAndBothBasesPreserveOriginalDecimalStrings()throws Exception {
        when(useCase.execute(any())).thenAnswer(i->{Command c=i.getArgument(0);return result(1,false,c.facts());});
        for(String basis:List.of("MOLE_FRACTION","MASS_FRACTION"))putBody(with("\"composition\":{\"basis\":\""+basis+"\",\"components\":[{\"reference\":\"A\",\"fraction\":\"0.2500\"},{\"reference\":\"B\",\"fraction\":\"0.750\"}]}"))
            .andExpect(status().isCreated()).andExpect(jsonPath("$.facts.composition.components[0].fraction").value("0.2500")).andExpect(jsonPath("$.facts.composition.basis").value(basis));
    }
    @Test void invalidCompositionShapesAndLexemesAreRejected()throws Exception {
        for(String fraction:List.of("0","1e0","+1","01","1.0000000000000000000"))putBody(with("\"composition\":{\"basis\":\"MOLE_FRACTION\",\"components\":[{\"reference\":\"A\",\"fraction\":\""+fraction+"\"}]}" )).andExpect(status().isBadRequest());
        for(String c:List.of("{\"basis\":\"VOLUME_FRACTION\",\"components\":[]}","{\"basis\":\"MOLE_FRACTION\",\"components\":[]}","{\"basis\":\"MASS_FRACTION\",\"components\":[{\"reference\":\"A\",\"fraction\":\"0.5\"},{\"reference\":\"A\",\"fraction\":\"0.5\"}]}"))putBody(with("\"composition\":"+c)).andExpect(status().isBadRequest());verifyNoInteractions(useCase);
    }
    @Test void suppliedMethodContradictionCannotBecomeIncompleteSuccess()throws Exception {
        putBody(with("\"composition\":{\"basis\":\"MASS_FRACTION\",\"components\":[{\"reference\":\"A\",\"fraction\":\"1\"}],\"method\":{\"reference\":\"m\",\"revision\":\"1\"}},\"methods\":[{\"key\":{\"reference\":\"m\",\"revision\":\"1\"},\"origin\":\"DECLARED_PARAMETER\",\"basis\":\"MOLE_FRACTION\",\"components\":[\"A\"],\"properties\":[],\"absolutePressure\":{\"minimum\":\"1\",\"maximum\":\"100\",\"unitId\":\"P\"},\"absoluteTemperature\":{\"minimum\":\"1\",\"maximum\":\"1000\",\"unitId\":\"T\"}}]" )).andExpect(status().isBadRequest());verifyNoInteractions(useCase);
    }
    @Test void boundsNullChildrenAndWrongCollectionTypesRejectBeforeOwners()throws Exception {
        for(String fields:List.of("\"properties\":[null]","\"methods\":{}","\"properties\":false","\"methods\":[null]"))putBody(with(fields)).andExpect(status().isBadRequest());
        String emptyMethod="{\"key\":{\"reference\":\"m\",\"revision\":\"1\"},\"origin\":\"SYNTHETIC\",\"basis\":\"MOLE_FRACTION\",\"components\":[],\"properties\":[],\"absolutePressure\":{\"minimum\":\"1\",\"maximum\":\"2\",\"unitId\":\"P\"},\"absoluteTemperature\":{\"minimum\":\"1\",\"maximum\":\"2\",\"unitId\":\"T\"}}";
        putBody(with("\"methods\":["+String.join(",",Collections.nCopies(17,emptyMethod))+"]")).andExpect(status().isBadRequest());verifyNoInteractions(useCase);
    }
    @Test void duplicateExtraOrMissingCompanyAndNoncanonicalPathsReject()throws Exception {
        http.perform(get(url()).param("companyPartyId","company","other")).andExpect(status().isBadRequest());
        http.perform(get(url()).param("companyPartyId","company").param("expectedVersion","1")).andExpect(status().isBadRequest());http.perform(get(url())).andExpect(status().isBadRequest());
        http.perform(get(ROOT+"not-uuid/properties").param("companyPartyId","company")).andExpect(status().isBadRequest());verifyNoInteractions(useCase);
    }
    @Test void responsesPreserveNullEmptyIncompleteAndStaleBindingWithoutQualification()throws Exception {
        var stale=new Result(new SuppliedFluidPropertyRevision(id,"company","local","catalog",3,1,Classification.GAS,new Facts(null,List.of(),null,null,null)),true,false);when(useCase.execute(any())).thenReturn(stale);
        var response=http.perform(get(url()).param("companyPartyId","company")).andExpect(status().isOk()).andReturn().getResponse();var n=JSON.readTree(response.getContentAsString());
        assertThat(n.get("facts").get("composition").isNull()).isTrue();assertThat(n.get("facts").get("properties").isArray()).isTrue();assertThat(n.get("facts").get("methods").isNull()).isTrue();assertThat(n.get("metadataBindingCurrent").asBoolean()).isFalse();assertThat(n.get("diagnostics").toString()).contains("METADATA_BINDING_STALE");assertThat(n.has("qualified")).isFalse();assertThat(n.get("authoringComplete").asBoolean()).isFalse();
    }
    @Test void ownerFailuresReturnOnlyStablePublicProblems()throws Exception {
        for(var reason:Reason.values()){reset(useCase);when(useCase.execute(any())).thenThrow(new Failure(reason,new IllegalStateException("private SQL actor")));int code=reason==Reason.INVALID_REQUEST?400:reason==Reason.DENIED?403:reason==Reason.CONFLICT?409:503;
            var response=putBody(body()).andExpect(status().is(code)).andExpect(jsonPath("$.code").value(reason.name())).andReturn().getResponse();assertThat(response.getContentAsString()).doesNotContain("private SQL actor","stackTrace","backendPid");}
    }
}
