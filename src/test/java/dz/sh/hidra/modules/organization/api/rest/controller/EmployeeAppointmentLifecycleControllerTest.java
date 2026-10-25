/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentLifecycleControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : Verifies appointment transport routing, strict bodies, legacy aliases and redacted history.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;


import dz.sh.hidra.modules.organization.api.rest.EmployeeAppointmentApiExceptionHandler;
import dz.sh.hidra.modules.organization.application.command.*;
import dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult;
import dz.sh.hidra.modules.organization.application.port.in.*;
import dz.sh.hidra.modules.organization.domain.model.*;
import dz.sh.hidra.modules.organization.domain.value.*;
import dz.sh.hidra.platform.permissions.*;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class EmployeeAppointmentLifecycleControllerTest {
    private static final Instant AT=Instant.parse("2026-10-25T10:00:00Z");
    private final EmployeeAppointmentLifecycleUseCase useCase=mock(EmployeeAppointmentLifecycleUseCase.class);
    private final AssignEmployeeUseCase legacy=mock(AssignEmployeeUseCase.class);
    private final OrganizationAdministrationQueryUseCase query=mock(OrganizationAdministrationQueryUseCase.class);
    private final HidraEffectivePermissionResolver permissions=mock(HidraEffectivePermissionResolver.class);
    private final MockMvc mvc=MockMvcBuilders.standaloneSetup(new EmployeeAppointmentLifecycleController(useCase),
            new SpringOrganizationController(legacy,mock(CreateOrganizationUnitUseCase.class),mock(RegisterEmployeeUseCase.class)),
            new OrganizationAdministrationQueryController(query))
            .setControllerAdvice(new EmployeeAppointmentApiExceptionHandler())
            .addInterceptors(new HidraRouteAuthorizationInterceptor(permissions,new HidraRoutePermissionNaming(),true,"jwt")).build();
    @BeforeEach void authenticated(){SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("synthetic",null,List.of()));}
    @AfterEach void clear(){SecurityContextHolder.clearContext();}
    private static EmployeeAppointmentResult.PinnedVersion version(long version) {
        var a=new EmployeeAssignment("assignment","employee","unit","position",AssignmentType.PRIMARY,AT,null,AssignmentStatus.ACTIVE,AT,AT);
        var b=new OrganizationUnitCompanyBinding("binding",1,"unit","company",AT);
        return new EmployeeAppointmentResult.PinnedVersion(new CompanyBoundEmployeeAppointment(new EmployeeAppointmentChange.VersionedAssignment(version,a),b),"policy",1);
    }
    private static EmployeeAppointmentResult.MutationReceipt receipt(EmployeeAppointmentCommand.Kind kind) {
        return new EmployeeAppointmentResult.MutationReceipt(kind,"key","event",AT,false,
                kind==EmployeeAppointmentCommand.Kind.CREATE?null:version(1),version(2),kind==EmployeeAppointmentCommand.Kind.TRANSFER?version(1):null);
    }
    @Test void endTransferAndCorrectionDispatchExactTypedCommandsAndDetachedResponses() throws Exception {
        when(useCase.end(any())).thenReturn(receipt(EmployeeAppointmentCommand.Kind.END));
        when(useCase.transfer(any())).thenReturn(receipt(EmployeeAppointmentCommand.Kind.TRANSFER));
        when(useCase.correct(any())).thenReturn(receipt(EmployeeAppointmentCommand.Kind.CORRECT));
        mvc.perform(post("/api/v1/organization/assignments/assignment/end").param("companyPartyId","company").contentType(MediaType.APPLICATION_JSON)
            .content("{\"requestId\":\"key\",\"expectedVersion\":1,\"endAt\":\"2026-10-25T10:00:00Z\",\"reason\":\"reason\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.after.employeeId").value("employee"));
        mvc.perform(post("/api/v1/organization/assignments/assignment/transfer").param("companyPartyId","company").contentType(MediaType.APPLICATION_JSON)
            .content("{\"requestId\":\"key\",\"expectedVersion\":1,\"destinationOrganizationUnitId\":\"destination\",\"destinationPositionId\":\"position\",\"destinationType\":\"ACTING\",\"effectiveAt\":\"2026-10-25T10:00:00Z\",\"expectedDestinationBindingRevision\":2,\"expectedDestinationPolicyRevision\":3,\"reason\":\"reason\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.successor").exists());
        mvc.perform(post("/api/v1/organization/assignments/assignment/corrections").param("companyPartyId","company").contentType(MediaType.APPLICATION_JSON)
            .content("{\"requestId\":\"key\",\"expectedVersion\":1,\"correctedOrganizationUnitId\":\"unit\",\"correctedPositionId\":\"position\",\"correctedType\":\"PRIMARY\",\"correctedStatus\":\"ACTIVE\",\"correctedValidFrom\":\"2026-10-25T10:00:00Z\",\"expectedDestinationBindingRevision\":1,\"expectedDestinationPolicyRevision\":1,\"reason\":\"reason\"}"))
            .andExpect(status().isOk());
        verify(useCase).end(new EmployeeAppointmentCommand.End("key","company","assignment",1,AT,"reason"));
        verify(useCase).transfer(new EmployeeAppointmentCommand.Transfer("key","company","assignment",1,"destination","position",AssignmentType.ACTING,AT,null,2,3,"reason"));
        verify(useCase).correct(new EmployeeAppointmentCommand.Correct("key","company","assignment",1,"unit","position",AssignmentType.PRIMARY,AssignmentStatus.ACTIVE,AT,null,1,1,"reason"));
        verifyNoInteractions(permissions);
    }
    @Test void unknownDuplicateCoercedOversizedAndSubmicrosecondBodiesNeverReachTheOwner() throws Exception {
        String valid="{\"requestId\":\"key\",\"expectedVersion\":1,\"endAt\":\"2026-10-25T10:00:00Z\",\"reason\":\"reason\"}";
        for(String body:List.of(valid.replace("1,","1.0,"),valid.replace("1,","\"1\","),
                valid.replace("\"key\"","4"),valid.replace("\"key\"","\"key\",\"requestId\":\"other\""),
                valid.replace("10:00:00Z","10:00:00.000000001Z"),valid.replace("\"reason\":","\"actor\":\"forged\",\"reason\":"),
                valid+" {}", "[]", valid.replace("reason\"}","x".repeat(17000)+"\"}"))) {
            mvc.perform(post("/api/v1/organization/assignments/assignment/end").param("companyPartyId","company")
                .contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isBadRequest());
        }
        verifyNoInteractions(useCase);
    }
    @Test void privateHistoryAndOccupancyHistoryRemainDistinctAndExactVersionIsIndependent() throws Exception {
        var event=new EmployeeAppointmentResult.PrivateHistoryEvent("event",EmployeeAppointmentCommand.Kind.CREATE,AT,null,version(1),null);
        var redacted=new EmployeeAppointmentResult.PositionHistoryEvent("event",EmployeeAppointmentCommand.Kind.CREATE,AT,null,EmployeeAppointmentResult.PositionVersion.redact(version(1)),null);
        when(useCase.assignmentHistory(any())).thenReturn(new EmployeeAppointmentResult.HistoryPage<>(List.of(event),null,false));
        when(useCase.employeeHistory(any())).thenReturn(new EmployeeAppointmentResult.HistoryPage<>(List.of(event),null,false));
        when(useCase.positionHistory(any())).thenReturn(new EmployeeAppointmentResult.HistoryPage<>(List.of(redacted),null,false));
        when(useCase.exactVersion(any())).thenReturn(version(1));
        mvc.perform(get("/api/v1/organization/assignments/assignment/history").param("companyPartyId","company")).andExpect(status().isOk()).andExpect(jsonPath("$.entries[0].after.employeeId").value("employee"));
        mvc.perform(get("/api/v1/organization/employees/employee/appointment-history").param("companyPartyId","company")).andExpect(status().isOk());
        var body=mvc.perform(get("/api/v1/organization/units/unit/positions/position/appointment-history").param("companyPartyId","company"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertFalse(body.contains("employeeId"));assertFalse(body.contains("employee"));assertTrue(body.contains("bindingRevision"));
        mvc.perform(get("/api/v1/organization/assignments/assignment/versions/1").param("companyPartyId","company")).andExpect(status().isOk()).andExpect(jsonPath("$.version").value(1));
        mvc.perform(get("/api/v1/organization/assignments/assignment/history").param("companyPartyId","company").param("limit","101")).andExpect(status().isBadRequest());
    }
    @Test void bothLegacyAliasesKeepTheirStringResponseAndCarryTheOptionalExactRequestKey() throws Exception {
        when(legacy.assignEmployee(any())).thenReturn("assignment");
        for(String route:List.of("/assign-employee","/employees/assignments"))
            mvc.perform(post("/api/v1/organization"+route).contentType(MediaType.APPLICATION_JSON)
                    .content("{\"employeeId\":\"employee\",\"organizationUnitId\":\"unit\",\"positionId\":\"position\",\"requestId\":\"key\"}"))
                    .andExpect(status().isOk()).andExpect(content().string("assignment"));
        verify(legacy,times(2)).assignEmployee(new AssignEmployeeCommand("employee","unit","position",null,null,null,"key"));
        mvc.perform(post("/api/v1/organization/assign-employee").contentType(MediaType.APPLICATION_JSON)
                .content("{\"employeeId\":\"employee\",\"organizationUnitId\":\"unit\",\"positionId\":\"position\",\"authority\":true}"))
                .andExpect(status().isBadRequest());
    }
    @Test void legacyReadsRequireBoundedSelectorsAndUnauthenticatedAndSiblingRoutesCannotDelegate() throws Exception {
        when(query.assignments("employee",null,null,0,50)).thenReturn(new OrganizationAdministrationQueryUseCase.Page<>(List.of(),0,50,0,0,false));
        when(query.employeeAssignments("employee")).thenReturn(List.of());
        mvc.perform(get("/api/v1/organization/assignments").param("employeeId","employee")).andExpect(status().isOk()).andExpect(jsonPath("$.totalElements").value(0));
        mvc.perform(get("/api/v1/organization/employees/employee/assignments")).andExpect(status().isOk()).andExpect(content().json("[]"));
        mvc.perform(get("/api/v1/organization/assignments")).andExpect(status().isBadRequest());
        mvc.perform(get("/api/v1/organization/assignments").param("employeeId","employee").param("size","101")).andExpect(status().isBadRequest());
        mvc.perform(get("/api/v1/organization/units")).andExpect(status().isForbidden());
        SecurityContextHolder.clearContext();
        mvc.perform(get("/api/v1/organization/assignments").param("employeeId","employee")).andExpect(status().isUnauthorized());
        verifyNoInteractions(useCase);
    }
}
