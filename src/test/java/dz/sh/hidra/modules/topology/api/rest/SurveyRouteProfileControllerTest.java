/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SurveyRouteProfileControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Tests strict survey transport, decimal preservation and safe errors.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.controller.EngineeringNetworkDraftController;
import dz.sh.hidra.modules.topology.application.port.in.*;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure;
import java.util.*;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
class SurveyRouteProfileControllerTest {
    private SurveyRouteProfileUseCase owner;private MockMvc mvc;private final UUID id=UUID.randomUUID();
    private String url(){return "/api/v1/topology/engineering-networks/"+id+"/survey/parallel-a?companyPartyId=c&pipelineSystemId=n";}
    private String body(){return """
{"expectedVersion":2,"profile":{"branchId":"parallel-a","horizontalCrs":{"authority":"SYNTHETIC","code":"crs","axes":[{"name":"north","unitIdentifier":"metre"},{"name":"east","unitIdentifier":"metre"}]},"chainageOriginReference":"origin","direction":"FROM_TO","evidenceReference":"evidence","points":[{"id":"a","firstCoordinate":"1.000","secondCoordinate":"2.00","chainageMetres":"0.00","coordinatePrecision":"0.01","chainagePrecisionMetres":"0.001"},{"id":"b","firstCoordinate":"1.000","secondCoordinate":"2.00","chainageMetres":"10.000","coordinatePrecision":"0.01","chainagePrecisionMetres":"0.001"}],"sections":[]}}
        """;}
    @BeforeEach void setup(){owner=mock(SurveyRouteProfileUseCase.class);
        mvc=MockMvcBuilders.standaloneSetup(new EngineeringNetworkDraftController(mock(EngineeringNetworkDraftUseCase.class),mock(EngineeringGraphDraftUseCase.class),owner))
            .setControllerAdvice(new EngineeringNetworkDraftApiExceptionHandler()).build();
    }
    @Test void mapsStrictSurveyTransportWithoutDomainSerialization() throws Exception {
        when(owner.executeSurvey(any())).thenAnswer(i->{SurveyRouteProfileUseCase.Command c=i.getArgument(0);return new SurveyRouteProfileUseCase.Result(id,"parallel-a",3,3L,"a".repeat(64),2L,"b".repeat(64),c.profile(),List.of());});
        mvc.perform(put(url()).contentType(MediaType.APPLICATION_JSON).content(body())).andExpect(status().isOk()).andExpect(jsonPath("$.engineeringComplete").value(false)).andExpect(jsonPath("$.state").value("DRAFT")).andExpect(jsonPath("$.profile.points[0].firstCoordinate").value("1.000"));
        var capture=org.mockito.ArgumentCaptor.forClass(SurveyRouteProfileUseCase.Command.class);verify(owner).executeSurvey(capture.capture());assertEquals(2L,capture.getValue().expectedVersion());assertEquals(3,capture.getValue().profile().points().get(0).firstCoordinate().scale());
    }
    @Test void rejectsNestedUnknownFieldsAndScalarCoercion() throws Exception {
        for(String b:List.of(body().replace("\"expectedVersion\":2","\"expectedVersion\":\"2\""),body().replace("\"expectedVersion\":2","\"expectedVersion\":2.0"),body().replace("\"expectedVersion\":2","\"expectedVersion\":9223372036854775808"),body().replace("\"name\":\"north\"","\"extra\":true,\"name\":\"north\""),body().replace("\"firstCoordinate\":\"1.000\"","\"firstCoordinate\":1.000"),body().replace("\"id\":\"a\"","\"unknown\":1,\"id\":\"a\""),body().replace("\"points\":[{","\"points\":[null,{")))
            mvc.perform(put(url()).contentType(MediaType.APPLICATION_JSON).content(b)).andExpect(status().isBadRequest());verifyNoInteractions(owner);
    }
    @Test void preservesDecimalStringsAndRejectsInvalidOrOversizedValues() throws Exception {
        for(String s:List.of("NaN","Infinity"," 1.0","x","1".repeat(4097)))mvc.perform(put(url()).contentType(MediaType.APPLICATION_JSON).content(body().replace("1.000",s))).andExpect(status().isBadRequest());verifyNoInteractions(owner);
        assertEquals(new java.math.BigDecimal("1E+100"),dz.sh.hidra.modules.topology.api.rest.request.SurveyRouteProfileRequests.decimal("1E+100"));
    }
    @Test void rejectsBadScopeVersionsAndBranchMismatch() throws Exception {
        mvc.perform(put(url()).contentType(MediaType.APPLICATION_JSON).content(body().replace("parallel-a","other"))).andExpect(status().isBadRequest());
        mvc.perform(get(url()+"&version=0")).andExpect(status().isBadRequest());
        mvc.perform(get(url().replace("companyPartyId=c&",""))).andExpect(status().isBadRequest());
        mvc.perform(get(url().replace(id.toString(),"not-a-uuid"))).andExpect(status().isBadRequest());verifyNoInteractions(owner);
    }
    @Test void returnsRegisteredNonDisclosingErrors() throws Exception {
        for(var reason:EngineeringNetworkDraftFailure.Reason.values()){
            doThrow(new EngineeringNetworkDraftFailure(reason)).when(owner).executeSurvey(any());int code=switch(reason){case DENIED->403;case NOT_FOUND->404;case CONFLICT->409;case UNAVAILABLE,INDETERMINATE->503;};
            mvc.perform(get(url())).andExpect(status().is(code)).andExpect(jsonPath("$.code").value(reason.name()));
        }
    }
}
