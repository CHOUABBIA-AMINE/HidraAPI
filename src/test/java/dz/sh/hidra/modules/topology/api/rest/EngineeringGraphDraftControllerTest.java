package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.controller.EngineeringNetworkDraftController;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Controller semantics only. A separate PostgreSQL integration test must prove actual bearer authority. */
class EngineeringGraphDraftControllerTest {
    private EngineeringGraphDraftUseCase graph;
    private MockMvc mvc;
    private final UUID draftId=UUID.randomUUID();
    private String url(){return "/api/v1/topology/engineering-networks/"+draftId+"/graph?companyPartyId=owner&pipelineSystemId=network";}

    @BeforeEach void setup(){
        graph=mock(EngineeringGraphDraftUseCase.class);
        mvc=MockMvcBuilders.standaloneSetup(new EngineeringNetworkDraftController(mock(EngineeringNetworkDraftUseCase.class),graph))
                .setControllerAdvice(new EngineeringNetworkDraftApiExceptionHandler()).build();
    }

    @Test void graphReadDefaultsToCurrentAndSupportsExplicitHistoricalVersion() throws Exception {
        var result=new EngineeringGraphDraftUseCase.Result(draftId,3L,null,null,null,List.of("GRAPH_NOT_AUTHORED"));
        when(graph.executeGraph(any())).thenReturn(result);
        mvc.perform(get(url())).andExpect(status().isOk()).andExpect(jsonPath("$.version").value(3))
                .andExpect(jsonPath("$.graphVersion").doesNotExist());
        mvc.perform(get(url()+"&version=2")).andExpect(status().isOk());
        var capture=org.mockito.ArgumentCaptor.forClass(EngineeringGraphDraftUseCase.Command.class);
        verify(graph,times(2)).executeGraph(capture.capture());
        assertNull(capture.getAllValues().get(0).requestedVersion());
        assertEquals(2L,capture.getAllValues().get(1).requestedVersion());
        assertEquals(EngineeringGraphDraftUseCase.Kind.READ,capture.getAllValues().get(1).kind());
    }

    @Test void graphResponseSerializesExplicitNodePortBranchDtos() throws Exception {
        var g=new EngineeringGraphDraft(
            List.of(new EngineeringGraphDraft.Node("j",EngineeringGraphDraft.NodeKind.JUNCTION,null),
                new EngineeringGraphDraft.Node("s",EngineeringGraphDraft.NodeKind.STATION,"facility-1")),
            List.of(new EngineeringGraphDraft.Port("out","j",EngineeringGraphDraft.PortDirection.OUTLET),
                new EngineeringGraphDraft.Port("in","s",EngineeringGraphDraft.PortDirection.INLET)),
            List.of(new EngineeringGraphDraft.Branch("pipe-1",EngineeringGraphDraft.BranchKind.PIPE,"out","in")));
        when(graph.executeGraph(any())).thenReturn(new EngineeringGraphDraftUseCase.Result(
            draftId,4L,3L,"a".repeat(64),g,g.diagnostics()));
        mvc.perform(get(url()+"&version=4"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nodes[1].kind").value("STATION"))
           .andExpect(jsonPath("$.nodes[1].facilityId").value("facility-1"))
           .andExpect(jsonPath("$.ports[0].direction").value("INLET"))
           .andExpect(jsonPath("$.branches[0].kind").value("PIPE"))
           .andExpect(jsonPath("$.graphVersion").value(3))
           .andExpect(jsonPath("$.engineeringComplete").value(false));
    }

    @Test void updateRequiresExpectedVersionAndRejectsUnsupportedGraphShape() throws Exception {
        mvc.perform(put(url()).contentType(MediaType.APPLICATION_JSON)
                .content("{\"nodes\":[],\"ports\":[],\"branches\":[]}"))
                .andExpect(status().isBadRequest());
        mvc.perform(put(url()).contentType(MediaType.APPLICATION_JSON)
                .content("{\"expectedVersion\":1,\"nodes\":[],\"ports\":[],\"branches\":[],\"unknown\":true}"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(graph);
    }

    @Test void updatePassesImmutableTypedGraphAndReturnsDraftOnlyResult() throws Exception {
        var authored=new EngineeringGraphDraft(List.of(),List.of(),List.of());
        when(graph.executeGraph(any())).thenReturn(new EngineeringGraphDraftUseCase.Result(draftId,2,2L,
                "0".repeat(64),authored,authored.diagnostics()));
        mvc.perform(put(url()).contentType(MediaType.APPLICATION_JSON)
                .content("{\"expectedVersion\":1,\"nodes\":[],\"ports\":[],\"branches\":[]}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.engineeringComplete").value(false))
                .andExpect(jsonPath("$.version").value(2));
        var capture=org.mockito.ArgumentCaptor.forClass(EngineeringGraphDraftUseCase.Command.class);
        verify(graph).executeGraph(capture.capture());
        assertEquals(EngineeringGraphDraftUseCase.Kind.UPDATE,capture.getValue().kind());
        assertEquals(1L,capture.getValue().expectedVersion());
        assertEquals(authored,capture.getValue().graph());
    }


    @Test void graphUpdateRejectsScalarCoercionAndUnknownNestedProperties() throws Exception {
        for (String body : List.of(
                "{\"expectedVersion\":\"1\",\"nodes\":[],\"ports\":[],\"branches\":[]}",
                "{\"expectedVersion\":1.0,\"nodes\":[],\"ports\":[],\"branches\":[]}",
                "{\"expectedVersion\":true,\"nodes\":[],\"ports\":[],\"branches\":[]}",
                "{\"expectedVersion\":1,\"nodes\":[{\"id\":99,\"kind\":\"JUNCTION\"}],\"ports\":[],\"branches\":[]}",
                "{\"expectedVersion\":1,\"nodes\":[{\"id\":\"n\",\"kind\":\"JUNCTION\",\"extra\":true}],\"ports\":[],\"branches\":[]}"
        )) {
            mvc.perform(put(url()).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());
        }
        verifyNoInteractions(graph);
    }

    @Test void invalidRequestedVersionFailsBeforeGraphOwner() throws Exception {
        mvc.perform(get(url()+"&version=0")).andExpect(status().isBadRequest());
        verifyNoInteractions(graph);
    }

    @Test void nullNestedGraphElementsAreMalformedRatherThanAnUncaughtServerFailure() throws Exception {
        for (String body : List.of(
                "{\"expectedVersion\":1,\"nodes\":[null],\"ports\":[],\"branches\":[]}",
                "{\"expectedVersion\":1,\"nodes\":[],\"ports\":[null],\"branches\":[]}",
                "{\"expectedVersion\":1,\"nodes\":[],\"ports\":[],\"branches\":[null]}"
        )) {
            mvc.perform(put(url()).contentType(MediaType.APPLICATION_JSON).content(body))
                    .andExpect(status().isBadRequest());
        }
        verifyNoInteractions(graph);
    }
}
