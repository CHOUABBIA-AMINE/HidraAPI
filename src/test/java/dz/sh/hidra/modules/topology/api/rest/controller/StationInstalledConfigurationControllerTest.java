/**
 * HidraAPI — Package C controller routing and authorization-before-disclosure tests.
 * Standalone HTTP tests are transport proof only; qualification is separate.
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.api.rest.StationInstalledConfigurationApiExceptionHandler;
import dz.sh.hidra.modules.topology.api.rest.mapper.StationInstalledConfigurationRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.StationInstalledConfigurationRequests.*;
import dz.sh.hidra.modules.topology.application.model.StationInstalledConfigurationFailure;
import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase;
import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.*;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.StationInternalGraphRevisionCodec;
import java.util.*;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.json.JsonMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StationInstalledConfigurationControllerTest {
    private StationInstalledConfigurationUseCase owner;
    private MockMvc mvc;
    private static final String PATH="/api/v1/topology/facilities/facility/installed-configuration";
    private static final String AT="2026-02-01T00:00:00Z";
    private Save save;
    private static Save sample(){
        var graph=new GraphRevision("facility","graph-r1",
            new Graph("facility","graph-r1","COMPLETE",
                List.of(new Header("suction","SUCTION","s"),new Header("discharge","DISCHARGE","d")),
                List.of(new Unit("position","facility","inlet","outlet")),
                List.of(new Edge("in","s","inlet"),new Edge("out","outlet","d"))),
            "SYNTHETIC",AT,AT,null,"synthetic:graph",null,"STRUCTURALLY_QUALIFIED");
        // Strict mapper itself recomputes the checksum; source test keeps the fixture independent.
        var model=new dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision("facility","graph-r1",
            new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph("facility","graph-r1",
                dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Status.COMPLETE,
                List.of(new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Header("suction",
                        dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.HeaderKind.SUCTION,"s"),
                    new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Header("discharge",
                        dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.HeaderKind.DISCHARGE,"d")),
                List.of(new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Unit(
                    "position","facility","inlet","outlet")),
                List.of(new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Edge("in","s","inlet"),
                    new dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.Edge("out","outlet","d"))),
            dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.SourceClassification.SYNTHETIC,
            java.time.Instant.parse(AT),java.time.Instant.parse(AT),null,"synthetic:graph",null,
            dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.Qualification.STRUCTURALLY_QUALIFIED);
        var codec=new StationInternalGraphRevisionCodec();
        return new Save("request","company","config-r1",0L,1L,graph,
            StationInternalGraphRevisionCodec.FORMAT,codec.sha256(codec.encode(model)),AT,null,
            "synthetic:configuration",List.of(new Position("position","CONNECTED","unit",
                "installation",1L,AT,null,"synthetic:installation")),List.of("in","out"));
    }
    private String json(){return JsonMapper.builder().build().writeValueAsString(save);}
    @BeforeEach void setup(){
        save=sample();
        owner=mock(StationInstalledConfigurationUseCase.class);
        var committed=StationInstalledConfigurationRestMapper.save("facility",save).declaration();
        when(owner.execute(any())).thenAnswer(inv->{
            Command c=inv.getArgument(0);
            return switch(c.operation()){
                case SAVE -> new Result(c.declaration(),List.of(),false);
                case READ_CURRENT,READ_REVISION -> new Result(committed,List.of(),false);
                case LIST_HISTORY -> new Result(null,List.of(committed),false);
            };
        });
        mvc=MockMvcBuilders.standaloneSetup(new StationInstalledConfigurationController(owner))
            .setControllerAdvice(new StationInstalledConfigurationApiExceptionHandler()).build();
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("transport-only",null,List.of()));
    }
    @AfterEach void clear(){SecurityContextHolder.clearContext();}
    @Test void allFourExactRoutesDelegateOnlyToProtectedOwner() throws Exception {
        mvc.perform(put(PATH).contentType(MediaType.APPLICATION_JSON).content(json()))
            .andExpect(status().isOk()).andExpect(jsonPath("$.snapshot.selectedEpoch").value(1))
            .andExpect(jsonPath("$.engineeringQualified").value(false))
            .andExpect(jsonPath("$.operatingStateValidated").value(false));
        mvc.perform(get(PATH).param("companyPartyId","company"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.snapshot.revisionId").value("config-r1"));
        mvc.perform(get(PATH+"/revisions/config-r1").param("companyPartyId","company"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.snapshot.graphHeadEpoch").value(1));
        mvc.perform(get(PATH+"/history").param("companyPartyId","company")
            .param("limit","1").param("cursor","2"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.history.length()").value(1));
        var capture=org.mockito.ArgumentCaptor.forClass(Command.class);
        verify(owner,times(4)).execute(capture.capture());
        assertEquals(List.of(Operation.SAVE,Operation.READ_CURRENT,Operation.READ_REVISION,
            Operation.LIST_HISTORY),capture.getAllValues().stream().map(Command::operation).toList());
        assertEquals(2L,capture.getAllValues().get(3).beforeEpoch());
    }
    @Test void anonymousDoesNotDispatchAnyProtectedSourceOperation() throws Exception {
        SecurityContextHolder.clearContext();
        mvc.perform(get(PATH).param("companyPartyId","company")).andExpect(status().isUnauthorized());
        mvc.perform(get(PATH+"/history").param("companyPartyId","company"))
            .andExpect(status().isUnauthorized());
        verify(owner,never()).execute(any());
    }
    @Test void forgedIdentityAndMalformedHistorySelectorsFailBeforeOwner() throws Exception {
        mvc.perform(put(PATH).contentType(MediaType.APPLICATION_JSON)
            .content(json().replace("\"requestId\":\"request\"",
                "\"requestId\":\"request\",\"actorUserId\":\"forged\"")))
            .andExpect(status().isBadRequest());
        mvc.perform(get(PATH+"/history").param("companyPartyId","company")
            .param("cursor","-1")).andExpect(status().isBadRequest());
        mvc.perform(get(PATH+"/history").param("companyPartyId","company")
            .param("limit","101")).andExpect(status().isBadRequest());
        mvc.perform(get(PATH)).andExpect(status().isBadRequest());
        verifyNoInteractions(owner);
    }
    @Test void nativeTypedDenialsAndUnknownCommitNeverDiscloseProtectedFacts() throws Exception {
        for(var kind:List.of(StationInstalledConfigurationFailure.Kind.DENIED,
            StationInstalledConfigurationFailure.Kind.CONFLICT,
            StationInstalledConfigurationFailure.Kind.INDETERMINATE)){
            doThrow(new StationInstalledConfigurationFailure(kind,
                new IllegalStateException("hidden private session and native SQL")))
                .when(owner).execute(any());
            int status=kind==StationInstalledConfigurationFailure.Kind.DENIED?403:
                kind==StationInstalledConfigurationFailure.Kind.CONFLICT?409:503;
            mvc.perform(get(PATH).param("companyPartyId","company"))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.code").value(kind.name()))
                .andExpect(content().string(org.hamcrest.Matchers.not(
                    org.hamcrest.Matchers.containsString("private session"))));
        }
    }
    @Test void onlyAuthorizedOwnerAbsenceCanTranslateTo404() throws Exception {
        doReturn(new Result(null,List.of(),false)).when(owner).execute(any());
        mvc.perform(get(PATH).param("companyPartyId","company")).andExpect(status().isNotFound());
        verify(owner).execute(any());
    }
}
