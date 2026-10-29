/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest
 *
 * @Description : Verifies all eight exact REST routes and strict protected transport boundaries.
 *
 */
package dz.sh.hidra.modules.topology.api.rest;

import dz.sh.hidra.modules.topology.api.rest.controller.InstalledUnitLifecycleController;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.*;
import dz.sh.hidra.modules.topology.application.model.InstalledUnitLifecycleFailure;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnit;
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

class InstalledUnitLifecycleControllerTest {
    private MockMvc mvc;
    private InstalledUnitLifecycleUseCase useCase;
    private static final String BASE="/api/v1/topology/installed-units/unit";
    private static final String WHEN="2026-10-29T10:00:00Z";
    private static final String UNKNOWN="{\"verification\":\"UNKNOWN\"}";
    private static final String REGISTER="{\"requestId\":\"request\",\"companyPartyId\":\"company\",\"equipmentKindId\":\"kind\",\"manufacturerParty\":"+UNKNOWN+",\"manufacturerModel\":"+UNKNOWN+",\"serialNumber\":"+UNKNOWN+"}";
    private static final String RECORD="{\"requestId\":\"request\",\"companyPartyId\":\"company\",\"installationId\":\"inst\",\"positionId\":\"pos\",\"facilityId\":\"facility\",\"effectiveFrom\":\""+WHEN+"\",\"evidenceReference\":\"source\"}";
    private static final String CLOSE="{\"requestId\":\"request\",\"companyPartyId\":\"company\",\"positionId\":\"pos\",\"facilityId\":\"facility\",\"effectiveUntil\":\""+WHEN+"\",\"expectedRevision\":1}";
    private static final String RELOCATE="{\"requestId\":\"request\",\"companyPartyId\":\"company\",\"replacementInstallationId\":\"new\",\"sourcePositionId\":\"pos\",\"sourceFacilityId\":\"facility\",\"destinationPositionId\":\"other\",\"destinationFacilityId\":\"facility2\",\"effectiveAt\":\""+WHEN+"\",\"evidenceReference\":\"source\",\"expectedRevision\":1}";
    private static final String REPLACE="{\"requestId\":\"request\",\"companyPartyId\":\"company\",\"replacementUnitId\":\"unit2\",\"replacementInstallationId\":\"new\",\"positionId\":\"pos\",\"facilityId\":\"facility\",\"effectiveAt\":\""+WHEN+"\",\"evidenceReference\":\"source\",\"expectedRevision\":1}";
    private static final String READ="?companyPartyId=company&positionId=pos&facilityId=facility";
    @BeforeEach void prepare(){
        useCase=mock(InstalledUnitLifecycleUseCase.class);
        var unit=new InstalledUnit("unit","company","kind",InstalledUnit.Evidence.unknown(),
                InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),null);
        when(useCase.execute(any())).thenReturn(new Result(unit,null,null,List.of(),false,null));
        mvc=MockMvcBuilders.standaloneSetup(new InstalledUnitLifecycleController(useCase))
                .setControllerAdvice(new InstalledUnitLifecycleApiExceptionHandler()).build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("transport-only",null,List.of()));
    }
    @AfterEach void clear(){SecurityContextHolder.clearContext();}
    @Test void allEightRoutesBindExactTypedOperationsWithoutAlternativeWriter() throws Exception {
        mvc.perform(put(BASE).contentType(MediaType.APPLICATION_JSON).content(REGISTER)).andExpect(status().isOk());
        mvc.perform(get(BASE+"?companyPartyId=company")).andExpect(status().isOk());
        mvc.perform(post(BASE+"/installations").contentType(MediaType.APPLICATION_JSON).content(RECORD)).andExpect(status().isOk());
        mvc.perform(post(BASE+"/installations/inst/close").contentType(MediaType.APPLICATION_JSON).content(CLOSE)).andExpect(status().isOk());
        mvc.perform(post(BASE+"/installations/inst/relocate").contentType(MediaType.APPLICATION_JSON).content(RELOCATE)).andExpect(status().isOk());
        mvc.perform(post(BASE+"/installations/inst/replace").contentType(MediaType.APPLICATION_JSON).content(REPLACE)).andExpect(status().isOk());
        mvc.perform(get(BASE+"/installations/inst"+READ)).andExpect(status().isOk());
        mvc.perform(get(BASE+"/installations/inst/history"+READ)).andExpect(status().isOk());
        var captured=org.mockito.ArgumentCaptor.forClass(Command.class);
        verify(useCase,times(8)).execute(captured.capture());
        assertEquals(List.of(Operation.REGISTER_UNIT,Operation.READ_UNIT,Operation.RECORD_INSTALLATION,
                Operation.CLOSE_INSTALLATION,Operation.RELOCATE,Operation.REPLACE,Operation.READ_INSTALLATION,Operation.READ_HISTORY),
                captured.getAllValues().stream().map(Command::operation).toList());
        assertEquals("unit2",captured.getAllValues().get(5).replacementUnitId());
        assertEquals(captured.getAllValues().get(5).source(),captured.getAllValues().get(5).destination());
        assertNull(captured.getAllValues().get(4).replacementUnitId());
    }
    @Test void malformedUnknownCoercedAndCallerClaimFieldsRejectBeforeUseCase() throws Exception {
        for(String body:List.of(
                REGISTER.replace("}",",\"actorUserId\":\"foreign\"}"),
                REGISTER.replace("\"equipmentKindId\":\"kind\"","\"equipmentKindId\":77"),
                REGISTER.replace("\"verification\":\"UNKNOWN\"","\"verification\":\"VERIFIED\""),
                REGISTER.replace("\"requestId\":\"request\"","\"requestId\":\" request\""),
                REGISTER.replace("\"manufacturerModel\":"+UNKNOWN,"\"manufacturerModel\":{\"verification\":\"VERIFIED\",\"value\":\"x\",\"sourceReference\":\"invented\"}"),
                REGISTER.replace("\"requestId\":\"request\"","\"requestId\":\"request\",\"nativeClaim\":\"forge\""))) {
            mvc.perform(put(BASE).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isBadRequest());
        }
        for(String body:List.of(REPLACE.replace("\"expectedRevision\":1","\"expectedRevision\":\"1\""),
                REPLACE.replace("\"effectiveAt\":\""+WHEN+"\"","\"effectiveAt\":\"2026-10-29T10:00:00.123456789Z\""),
                REPLACE.replace("\"replacementUnitId\":\"unit2\"","\"replacementUnitId\":\"unit\""),
                REPLACE.replace("\"requestId\":\"request\"","\"requestId\":\"request\",\"role\":\"admin\""))) {
            mvc.perform(post(BASE+"/installations/inst/replace").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isBadRequest());
        }
        verifyNoInteractions(useCase);
    }
    @Test void anonymousAndUntrustedPathNeverDiscloseProtectedUnit() throws Exception {
        SecurityContextHolder.clearContext();
        mvc.perform(get(BASE+"?companyPartyId=company")).andExpect(status().isUnauthorized());
        verifyNoInteractions(useCase);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("transport-only",null,List.of()));
        mvc.perform(get(BASE+"/installations/inst?companyPartyId=company&positionId=pos")).andExpect(status().isBadRequest());
        mvc.perform(get(BASE).param("companyPartyId"," company")).andExpect(status().isBadRequest());
        verifyNoInteractions(useCase);
    }
    @Test void deniedAndIndeterminateOutcomesRevealNoPrivateEvidence() throws Exception {
        for(var kind:List.of(InstalledUnitLifecycleFailure.Kind.DENIED,InstalledUnitLifecycleFailure.Kind.INDETERMINATE)) {
            doThrow(new InstalledUnitLifecycleFailure(kind,new IllegalStateException("private native token sqlstate")))
                    .when(useCase).execute(any());
            mvc.perform(get(BASE+"?companyPartyId=company"))
                    .andExpect(status().is(kind==InstalledUnitLifecycleFailure.Kind.DENIED?403:503))
                    .andExpect(jsonPath("$.code").value(kind.name()))
                    .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("private native token"))));
        }
    }
    @Test void mutationRequiresBodyAndExplicitCompanyWithoutAuthoritySubstitution() throws Exception {
        mvc.perform(put(BASE).contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest());
        mvc.perform(post(BASE+"/installations").contentType(MediaType.APPLICATION_JSON).content(RECORD.replace("\"companyPartyId\":\"company\",",""))).andExpect(status().isBadRequest());
        mvc.perform(post(BASE+"/installations/inst/close").contentType(MediaType.APPLICATION_JSON).content(CLOSE.replace("\"expectedRevision\":1","\"expectedRevision\":0"))).andExpect(status().isBadRequest());
        verifyNoInteractions(useCase);
    }
}
