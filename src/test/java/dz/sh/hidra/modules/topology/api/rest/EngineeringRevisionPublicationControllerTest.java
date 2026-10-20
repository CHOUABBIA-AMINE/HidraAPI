/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Exercises all publication routes and strict request and error transport without simulating native authority.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.controller.EngineeringRevisionPublicationController;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase.*;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure.Reason;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EngineeringRevisionPublicationControllerTest {
    private EngineeringRevisionPublicationUseCase useCase;
    private MockMvc mvc;
    private final UUID draft=UUID.randomUUID(),request=UUID.randomUUID(),revision=UUID.randomUUID();
    private String url(String suffix){return "/api/v1/topology/engineering-networks/"+draft+"/publications"+suffix+"?companyPartyId=company&pipelineSystemId=network";}
    private String publish(){return "{\"requestId\":\""+request+"\",\"draftVersion\":1,\"expectedEpoch\":0}";}
    private String transition(){return "{\"requestId\":\""+request+"\",\"expectedEpoch\":1,\"expectedRevisionId\":\""+revision+"\"}";}
    @BeforeEach void setup() {
        useCase=mock(EngineeringRevisionPublicationUseCase.class);
        mvc=MockMvcBuilders.standaloneSetup(new EngineeringRevisionPublicationController(useCase))
            .setControllerAdvice(new EngineeringRevisionPublicationApiExceptionHandler()).build();
        // Controller-only fixture. Genuine signed bearer/native producer tests live in PostgreSQL integration.
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("transport-fixture",null,List.of()));
        when(useCase.executePublication(any())).thenReturn(new Result(null,null,List.of(),null,null,false));
    }
    @AfterEach void clear(){SecurityContextHolder.clearContext();}
    @Test void mapsAllEightRegisteredRoutesToTypedCommands() throws Exception {
        mvc.perform(post(url("")).contentType(MediaType.APPLICATION_JSON).content(publish())).andExpect(status().isOk());
        mvc.perform(post(url("/withdraw")).contentType(MediaType.APPLICATION_JSON).content(transition())).andExpect(status().isOk());
        mvc.perform(post(url("/reactivate")).contentType(MediaType.APPLICATION_JSON).content(transition())).andExpect(status().isOk());
        mvc.perform(get(url("/revisions/"+revision))).andExpect(status().isOk());
        mvc.perform(get(url("/current"))).andExpect(status().isOk());
        mvc.perform(get(url("/effective")+"&asOf=2026-10-20T12:00:00.123456Z")).andExpect(status().isOk());
        mvc.perform(get(url("/history"))).andExpect(status().isOk());
        mvc.perform(get(url("/requests/"+request))).andExpect(status().isOk());
        var capture=org.mockito.ArgumentCaptor.forClass(Command.class);verify(useCase,times(8)).executePublication(capture.capture());
        assertEquals(List.of(Kind.PUBLISH,Kind.WITHDRAW,Kind.REACTIVATE,Kind.READ_EXACT,Kind.READ_CURRENT,Kind.READ_EFFECTIVE,Kind.LIST_HISTORY,Kind.READ_REQUEST),
                capture.getAllValues().stream().map(Command::kind).toList());
        assertEquals(revision,capture.getAllValues().get(3).revisionId());assertEquals(100,capture.getAllValues().get(6).limit());
    }
    @Test void rejectsUnknownFieldsCoercionAndCallerAuthority() throws Exception {
        for(String body:List.of(
            publish().replace("}",",\"engineeringQualified\":true}"),
            publish().replace("}",",\"actorUserId\":\"other\"}"),
            publish().replace("}",",\"effectiveFrom\":\"2026-10-20T00:00:00Z\"}"),
            publish().replace("\"draftVersion\":1","\"draftVersion\":\"1\""),
            publish().replace("\"draftVersion\":1","\"draftVersion\":1.0"),
            publish().replace("\"expectedEpoch\":0","\"expectedEpoch\":9223372036854775808"))) {
            mvc.perform(post(url("")).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isBadRequest());
        }
        verifyNoInteractions(useCase);
    }
    @Test void requiresNativeAuthenticationAndNonDisclosingScope() throws Exception {
        SecurityContextHolder.clearContext();
        mvc.perform(get(url("/current"))).andExpect(status().isUnauthorized());
        verifyNoInteractions(useCase);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("transport-fixture",null,List.of()));
        doThrow(new EngineeringRevisionPublicationFailure(Reason.DENIED)).when(useCase).executePublication(any());
        mvc.perform(get(url("/revisions/"+revision))).andExpect(status().isForbidden())
            .andExpect(jsonPath("$.instance").value("urn:hidra:engineering-publication"))
            .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString(revision.toString()))));
    }
    @Test void preservesExplicitUnqualifiedStatusAndMissingValues() throws Exception {
        var scope=new Scope(UUID.randomUUID(),"company","network");
        var snapshot=new Snapshot(scope,draft,1,Component.of(1,"HIDRA_ENGINEERING_METADATA_V1",new byte[]{1}),
            null,List.of(),List.of("GRAPH_NOT_AUTHORED",EngineeringRevisionPublication.UNQUALIFIED));
        when(useCase.executePublication(any())).thenReturn(new Result(null,new EngineeringRevisionPublication(revision,snapshot),List.of(),null,null,false));
        mvc.perform(get(url("/revisions/"+revision))).andExpect(status().isOk())
            .andExpect(jsonPath("$.snapshot.engineeringQualified").value(false))
            .andExpect(jsonPath("$.snapshot.version").value(1))
            .andExpect(jsonPath("$.snapshot.graph").doesNotExist())
            .andExpect(jsonPath("$.snapshot.diagnostics[1]").value(EngineeringRevisionPublication.UNQUALIFIED));
    }
    @Test void mapsStaleReplayAndOwnerHoldToConflict() throws Exception {
        doThrow(new EngineeringRevisionPublicationFailure(Reason.CONFLICT,new IllegalStateException("private owner generation"))).when(useCase).executePublication(any());
        mvc.perform(post(url("")).contentType(MediaType.APPLICATION_JSON).content(publish())).andExpect(status().isConflict())
            .andExpect(jsonPath("$.code").value("CONFLICT")).andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("private owner generation"))));
    }
    @Test void boundsHistoryAndBindsOpaqueCursor() throws Exception {
        for(String query:List.of("&limit=0","&limit=101","&cursor="+"a".repeat(4097),"&asOf=2026-10-20T12:00:00.123456789Z"))
            mvc.perform(get(url("/history")+query)).andExpect(status().isBadRequest());
        verifyNoInteractions(useCase);
        mvc.perform(get(url("/history")+"&limit=3&cursor=context-bound-window&asOf=2026-10-20T12:00:00Z")).andExpect(status().isOk());
        var capture=org.mockito.ArgumentCaptor.forClass(Command.class);verify(useCase).executePublication(capture.capture());
        assertEquals(3,capture.getValue().limit());assertEquals("context-bound-window",capture.getValue().cursor());
        assertEquals(java.time.Instant.parse("2026-10-20T12:00:00Z"),capture.getValue().asOf());
        assertThrows(IllegalArgumentException.class,()->EngineeringRevisionPublicationController.asOf("2026-10-20T12:00:00+00:00"));
    }
    @Test void requiresExpectedEpochPredecessorAndRequestId() throws Exception {
        for(String body:List.of("{}",publish().replace(",\"expectedEpoch\":0",""),
            publish().replace("\"expectedEpoch\":0","\"expectedEpoch\":1"),
            transition().replace("\"expectedEpoch\":1","\"expectedEpoch\":0"),
            publish().replace(request.toString(),"not-a-uuid"))) {
            mvc.perform(post(url("")).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isBadRequest());
        }
        mvc.perform(post(url("/withdraw")).contentType(MediaType.APPLICATION_JSON).content("{\"requestId\":\""+request+"\",\"expectedEpoch\":1}")).andExpect(status().isBadRequest());
        verifyNoInteractions(useCase);
    }
    @Test void keepsStorageAndIndeterminateDetailsPrivate() throws Exception {
        for(Reason reason:List.of(Reason.UNAVAILABLE,Reason.INDETERMINATE)) {
            doThrow(new EngineeringRevisionPublicationFailure(reason,new IllegalStateException("jdbc password native binding"))).when(useCase).executePublication(any());
            mvc.perform(get(url("/current"))).andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.code").value(reason.name()))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("jdbc password native binding"))));
        }
    }
}
