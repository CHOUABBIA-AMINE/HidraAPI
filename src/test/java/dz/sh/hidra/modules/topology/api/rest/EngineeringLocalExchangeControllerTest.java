/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : API Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.controller.EngineeringLocalExchangeController;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringLocalExchangeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringLocalExchangeUseCase.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringLocalExchange.Editor;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringLocalExchangeCodecTest;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure;
import dz.sh.hidra.platform.permissions.*;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
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

class EngineeringLocalExchangeControllerTest {
    private final UUID context=UUID.randomUUID(),draft=UUID.randomUUID();
    private EngineeringLocalExchangeUseCase owner;private MockMvc mvc;
    private String url(Editor e,String tail){return "/api/v1/topology/engineering-networks/"+draft+"/exchanges/"+e+tail+"?companyPartyId=company&pipelineSystemId=network"+(e==Editor.SURVEY?"&branchId=pipe":"");}
    private String body(Editor e){return "{\"expectedVersion\":3,\"package\":"+JsonMapper.builder().build().writeValueAsString(EngineeringLocalExchangeCodecTest.pack(e,context,draft))+"}";}
    @BeforeEach void setup(){
        owner=mock(EngineeringLocalExchangeUseCase.class);
        when(owner.execute(any())).thenAnswer(i->{var c=(Command)i.getArgument(0);return new Result(c.operation(),c.editor(),3,
            c.operation()==Operation.IMPORT?4L:null,c.operation()==Operation.EXPORT?EngineeringLocalExchangeCodecTest.pack(c.editor(),context,draft):c.bundle(),
            List.of("PHYSICAL_QUALIFICATION_NOT_ESTABLISHED"),"AUTHORED",null,null);});
        var interceptor=new HidraRouteAuthorizationInterceptor(mock(HidraEffectivePermissionResolver.class),new HidraRoutePermissionNaming(),true,"jwt");
        mvc=MockMvcBuilders.standaloneSetup(new EngineeringLocalExchangeController(owner)).setControllerAdvice(new EngineeringLocalExchangeApiExceptionHandler()).addInterceptors(interceptor).build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("transport-only",null,List.of()));
    }
    @AfterEach void clear(){SecurityContextHolder.clearContext();}
    @Test void mapsFourExactRoutesAndThreeEditorsToTypedInputs() throws Exception {
        for(var e:Editor.values()){
            mvc.perform(get(url(e,"/export"))).andExpect(status().isOk());
            mvc.perform(post(url(e,"/dry-runs")).contentType(MediaType.APPLICATION_JSON).content(body(e))).andExpect(status().isOk());
            mvc.perform(post(url(e,"/imports")).contentType(MediaType.APPLICATION_JSON).content(body(e))).andExpect(status().isOk());
            mvc.perform(get(url(e,"/capabilities"))).andExpect(status().isOk());
        }
        var capture=org.mockito.ArgumentCaptor.forClass(Command.class);verify(owner,times(12)).execute(capture.capture());
        assertEquals(List.of(Operation.EXPORT,Operation.DRY_RUN,Operation.IMPORT,Operation.CAPABILITIES),capture.getAllValues().subList(0,4).stream().map(Command::operation).toList());
        assertEquals("pipe",capture.getAllValues().get(10).branchId());
    }
    @Test void rejectsDuplicateUnknownCoercedAndOversizedBodiesBeforeOwnerDispatch() throws Exception {
        String b=body(Editor.METADATA);
        for(String invalid:List.of(b.replace("\"expectedVersion\":3","\"expectedVersion\":3,\"expectedVersion\":3"),
                b.replace("\"expectedVersion\":3","\"expectedVersion\":\"3\""),b.replace("\"expectedVersion\":3","\"expectedVersion\":3.0"),
                b.replace("\"expectedVersion\":3","\"expectedVersion\":9223372036854775808"),
                b.replace("\"package\":","\"actor\":\"forged\",\"package\":"),b+" {}"))
            mvc.perform(post(url(Editor.METADATA,"/imports")).contentType(MediaType.APPLICATION_JSON).content(invalid)).andExpect(status().isBadRequest());
        mvc.perform(post(url(Editor.METADATA,"/imports")).contentType(MediaType.APPLICATION_JSON)
            .content(new byte[EngineeringLocalExchangeApiExceptionHandler.MAX_BODY_BYTES+1])).andExpect(status().isBadRequest());
        verifyNoInteractions(owner);
    }
    @Test void preservesScalarEnvelopesAndDetachedResults() throws Exception {
        mvc.perform(get(url(Editor.METADATA,"/export"))).andExpect(status().isOk())
            .andExpect(jsonPath("$.engineeringQualified").value(false)).andExpect(jsonPath("$.writeAuthorization").value("NOT_EVALUATED"))
            .andExpect(jsonPath("$.package.contextId").value(context.toString())).andExpect(jsonPath("$.package.payload").value(EngineeringLocalExchangeCodecTest.pack(Editor.METADATA,context,draft).payload()));
    }
    @Test void requiresExactScopeVersionAndSurveyBranchSelectors() throws Exception {
        mvc.perform(get(url(Editor.METADATA,"/export")+"&version=1")).andExpect(status().isBadRequest());
        mvc.perform(get(url(Editor.METADATA,"/capabilities")+"&branchId=pipe")).andExpect(status().isBadRequest());
        mvc.perform(get(url(Editor.SURVEY,"/export").replace("&branchId=pipe",""))).andExpect(status().isBadRequest());
        mvc.perform(post(url(Editor.METADATA,"/imports")).contentType(MediaType.APPLICATION_JSON).content(body(Editor.METADATA).replace("\"expectedVersion\":3","\"expectedVersion\":0"))).andExpect(status().isBadRequest());
        verifyNoInteractions(owner);
    }
    @Test void anonymousForeignAndSiblingRoutesCannotDelegate() throws Exception {
        SecurityContextHolder.clearContext();mvc.perform(get(url(Editor.METADATA,"/export"))).andExpect(status().isUnauthorized());verifyNoInteractions(owner);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("transport-only",null,List.of()));
        doThrow(new EngineeringNetworkDraftFailure(EngineeringNetworkDraftFailure.Reason.DENIED)).when(owner).execute(any());
        mvc.perform(get(url(Editor.METADATA,"/export").replace("companyPartyId=company","companyPartyId=foreign"))).andExpect(status().isForbidden())
            .andExpect(jsonPath("$.instance").value("urn:hidra:engineering-local-exchange"))
            .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("foreign"))));
        clearInvocations(owner);mvc.perform(get(url(Editor.METADATA,"/sibling"))).andExpect(status().isNotFound());verifyNoInteractions(owner);
    }
}
