/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure;
import dz.sh.hidra.modules.topology.api.rest.controller.EngineeringNetworkDraftController;
import java.util.*;
class EngineeringNetworkDraftControllerTest {
    EngineeringNetworkDraftUseCase useCase;MockMvc http;String url="/api/v1/topology/engineering-networks";UUID id=UUID.randomUUID();
    @BeforeEach void setup(){useCase=mock(EngineeringNetworkDraftUseCase.class);http=MockMvcBuilders.standaloneSetup(new EngineeringNetworkDraftController(useCase)).setControllerAdvice(new EngineeringNetworkDraftApiExceptionHandler()).build();}
    Result result(boolean replay){return new Result(new EngineeringNetworkDraft(UUID.randomUUID(),"c","n",id,1,new EngineeringNetworkDraft.Metadata(null,null,null)),replay,List.of());}
    @Test void createDistinguishesNewReceiptAndFreshlyAuthorizedReplay() throws Exception {
        when(useCase.execute(any())).thenReturn(result(false),result(true));String body="{\"companyPartyId\":\"c\",\"pipelineSystemId\":\"n\",\"creationRequestId\":\""+UUID.randomUUID()+"\"}";
        http.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated()).andExpect(jsonPath("$.engineeringComplete").value(false));
        http.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isOk()).andExpect(jsonPath("$.replayed").value(true));verify(useCase,times(2)).execute(any());
    }
    @Test void rejectsOwnershipGraphUnknownFieldsAndMissingVersion() throws Exception {
        http.perform(put(url+"/"+id+"?companyPartyId=c&pipelineSystemId=n").contentType(MediaType.APPLICATION_JSON).content("{\"expectedVersion\":1,\"graph\":{}}")).andExpect(status().isBadRequest());
        http.perform(put(url+"/"+id+"?companyPartyId=c&pipelineSystemId=n").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest());verifyNoInteractions(useCase);
    }
    @Test void recognizedFailuresHaveSafeNonDisclosingStatus() throws Exception {
        when(useCase.execute(any())).thenThrow(new EngineeringNetworkDraftFailure(EngineeringNetworkDraftFailure.Reason.INDETERMINATE));
        http.perform(get(url+"/"+id+"?companyPartyId=c&pipelineSystemId=n")).andExpect(status().isServiceUnavailable()).andExpect(jsonPath("$.code").value("INDETERMINATE"));
    }
}
