/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentNativeLifecyclePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Preserves isolated appointment qualification cases with the shared disposable PostgreSQL fixture.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.application.port.out.ProtectedEmployeeAppointmentRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse;
import dz.sh.hidra.modules.organization.application.service.ProtectedEmployeeAppointmentAdmissionCoordinator;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.adapter.JdbcProtectedEmployeeAppointmentRepositoryAdapter;
import dz.sh.hidra.modules.party.application.contract.organization.OrganizationProtectedPartyEligibilityContract;
import dz.sh.hidra.modules.party.infrastructure.persistence.adapter.JdbcOrganizationProtectedPartyEligibilityAdapter;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tools.jackson.databind.json.JsonMapper;

/** Each concrete class executes in its own JVM and disposable migrated PostgreSQL database. */
class EmployeeAppointmentNativeLifecyclePostgresIntegrationTest extends EmployeeAppointmentPostgresFixture {
    @Test void genuinePublicAliasesLifecycleCurrentReadsAndRedactedHistoryUseTheWiredProtectedOwner() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);
        var mvc=org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup(runtimeWeb)
                .alwaysDo(result->{
                    if(result.getResponse().getStatus()>=500 && result.getResolvedException()!=null)
                        throw new AssertionError("Synthetic appointment MVC request failed",result.getResolvedException());
                }).build();
        String root="/api/v1/organization";String key=id();
        String body="{\"employeeId\":\""+f.employee()+"\",\"organizationUnitId\":\""+f.units().get(0)+"\",\"positionId\":\""+f.position()+"\",\"validFrom\":\""+START+"\",\"requestId\":\""+key+"\"}";
        try {
            var create=mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(root+"/assign-employee")
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(body))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk()).andReturn();
            String assignment=create.getResponse().getContentAsString();assertFalse(assignment.isBlank());
            mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(root+"/employees/assignments")
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(body))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().string(assignment));
            String correction="{\"requestId\":\""+id()+"\",\"expectedVersion\":1,\"correctedOrganizationUnitId\":\""+f.units().get(0)+"\",\"correctedPositionId\":\""+f.position()+"\",\"correctedType\":\"PRIMARY\",\"correctedStatus\":\"ACTIVE\",\"correctedValidFrom\":\""+START+"\",\"expectedDestinationBindingRevision\":1,\"expectedDestinationPolicyRevision\":1,\"reason\":\"HTTP synthetic correction\"}";
            mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(root+"/assignments/"+assignment+"/corrections")
                    .param("companyPartyId",f.company()).contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(correction))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
            String transfer="{\"requestId\":\""+id()+"\",\"expectedVersion\":2,\"destinationOrganizationUnitId\":\""+f.units().get(1)+"\",\"destinationPositionId\":\""+f.position()+"\",\"destinationType\":\"PRIMARY\",\"effectiveAt\":\""+START.plusSeconds(3600)+"\",\"expectedDestinationBindingRevision\":1,\"expectedDestinationPolicyRevision\":1,\"reason\":\"HTTP synthetic transfer\"}";
            var transferred=mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(root+"/assignments/"+assignment+"/transfer")
                    .param("companyPartyId",f.company()).contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(transfer))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
            String successor=JSON.readTree(transferred).get("successor").get("id").stringValue();
            mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(root+"/assignments/"+successor+"/end")
                    .param("companyPartyId",f.company()).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .content("{\"requestId\":\""+id()+"\",\"expectedVersion\":1,\"endAt\":\""+START.plusSeconds(7200)+"\",\"reason\":\"HTTP synthetic end\"}"))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
            for(String path:List.of("/assignments/"+assignment+"/history","/assignments/"+assignment+"/versions/1",
                    "/employees/"+f.employee()+"/appointment-history","/units/"+f.units().get(0)+"/appointment-policy"))
                mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(root+path).param("companyPartyId",f.company()))
                        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
            String redacted=mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(root+"/units/"+f.units().get(0)+"/positions/"+f.position()+"/appointment-history")
                    .param("companyPartyId",f.company())).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString();
            assertFalse(redacted.contains(f.employee()));assertFalse(redacted.contains("employeeId"));
            String current=mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(root+"/assignments").param("employeeId",f.employee()))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
            assertEquals(2,JSON.readTree(current).get("totalElements").intValue());
            mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(root+"/employees/"+f.employee()+"/assignments"))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
            try(var c=owner()) {
                assertEquals("4",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event e JOIN public.hidra_org_appointment_receipt r USING(event_id) JOIN public.hidra_identity_org_commit_proof_use p USING(event_id) WHERE e.employee_id=? AND e.actor_user_id=?",f.employee(),actor));
            }
            String effects=runtimeEffects(f,actor);
            mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(root+"/assign-employee")
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(body.replace("\"employeeId\":","\"actor\":\"forged\",\"employeeId\":")))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isBadRequest());
            admin("DELETE FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor);
            var denied=mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(root+"/assignments").param("employeeId",f.employee()))
                    .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isForbidden()).andReturn().getResponse().getContentAsString();
            assertFalse(denied.contains(f.employee()));assertFalse(denied.contains("totalElements"));assertEquals(effects,runtimeEffects(f,actor));
        } finally {SecurityContextHolder.clearContext();}
    }

    @Test void genuineLocalLifecycleReplayAndScopedReadsUsePrivatePublisherAndObservedCommit() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var service=runtimeService(runtimeEntityManager);var command=create(f,id());
        var created=measuredRuntime("CREATE",()->service.create(command));String assignment=created.after().assignmentId();
        assertFalse(created.historicalReplay());assertEquals(1,created.after().appointmentVersion());
        var corrected=measuredRuntime("CORRECT",()->service.correct(new EmployeeAppointmentCommand.Correct(id(),f.company(),assignment,1,f.units().get(0),f.position(),AssignmentType.PRIMARY,AssignmentStatus.ACTIVE,START,null,1,1,REASON)));
        assertEquals(2,corrected.after().appointmentVersion());
        var transferred=measuredRuntime("TRANSFER",()->service.transfer(new EmployeeAppointmentCommand.Transfer(id(),f.company(),assignment,2,f.units().get(1),f.position(),AssignmentType.PRIMARY,START.plusSeconds(3600),null,1,1,REASON)));
        assertEquals(3,transferred.after().appointmentVersion());assertNotNull(transferred.successor());
        measuredRuntime("END",()->service.end(new EmployeeAppointmentCommand.End(id(),f.company(),transferred.successor().assignmentId(),1,START.plusSeconds(7200),REASON)));
        admin("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",f.company());
        runtimeAuthenticate(actor);var replay=measuredRuntime("REPLAY",()->service.create(command));
        assertTrue(replay.historicalReplay());assertEquals(created.eventId(),replay.eventId());assertEquals(created.after(),replay.after());
        assertEquals(4,service.employeeHistory(dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History.employee(f.company(),f.employee(),100,null)).entries().size());
        assertEquals(1,service.exactVersion(new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.ExactVersion(f.company(),assignment,1)).appointmentVersion());
        assertEquals(f.company(),service.readPolicy(new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Policy(f.company(),f.units().get(0))).companyPartyId());
        assertFalse(service.positionHistory(dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History.position(f.company(),f.units().get(0),f.position(),100,null)).entries().isEmpty());
        try(var c=owner()) {
            assertEquals("4",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            assertEquals("4",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=? AND actor_user_id=?",f.employee(),actor));
            assertEquals("4",scalar(c,"SELECT count(*) FROM public.hidra_identity_org_commit_proof_use u JOIN public.hidra_org_appointment_event e USING(event_id) WHERE e.employee_id=?",f.employee()));
            assertEquals("4",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_receipt r JOIN public.hidra_org_appointment_event e USING(event_id) WHERE e.employee_id=?",f.employee()));
        } finally {SecurityContextHolder.clearContext();}
        assertEquals(RUNTIME_APP,runtimeJdbc.queryForObject("SELECT session_user",String.class));
    }

    @Test void genuineLegacyCreateUsesNativeSampleAndOriginalReplayAfterConfigurationAdvance() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var service=runtimeService(runtimeEntityManager);
        var selectors=new EmployeeAppointmentCommand.LegacyCreate(id(),f.employee(),f.units().get(0),f.position(),null,null,null);
        try {
            var trace=new RuntimeTrace();var created=tracedRuntime("LEGACY_NATIVE_SAMPLE",trace,()->service.createLegacy(selectors));trace.committed();
            assertEquals(AssignmentType.PRIMARY,created.after().snapshot().appointment().assignment().assignmentType());
            assertEquals(created.sampledAt(),created.after().snapshot().appointment().assignment().validFrom());
            assertEquals(1,created.after().snapshot().binding().revision());assertEquals(1,created.after().policyRevision());
            assertFalse(created.historicalReplay());assertEquals(selectors.requestId(),created.requestId());
            activatePolicy(f,policy());
            try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),CONFIG,PASSWORD)) {scalar(c,"SELECT public.hidra_org_activate_appointment_binding(?,?,?,1)",f.units().get(0),f.units().get(0),f.company());}
            runtimeAuthenticate(actor);
            var replayTrace=new RuntimeTrace();var replay=tracedRuntime("LEGACY_ORIGINAL_REPLAY",replayTrace,()->service.createLegacy(selectors));replayTrace.committed();
            assertTrue(replay.historicalReplay());assertEquals(created.eventId(),replay.eventId());assertEquals(created.after(),replay.after());
            assertEquals(created.sampledAt(),replay.sampledAt());
            String effects=runtimeEffects(f,actor);
            var changed=new EmployeeAppointmentCommand.LegacyCreate(selectors.requestId(),f.employee(),f.units().get(0),f.position(),AssignmentType.SECONDARY,null,null);
            assertThrows(RuntimeException.class,()->service.createLegacy(changed));assertEquals(effects,runtimeEffects(f,actor));
            assertEquals(RUNTIME_APP,runtimeJdbc.queryForObject("SELECT session_user",String.class));
        } finally {SecurityContextHolder.clearContext();}
    }

    @Test void genuineLegacyCreateRollsBackOnManagedFlushAndDeniesRevokedReplay() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var service=runtimeService(runtimeEntityManager);
        var selectors=new EmployeeAppointmentCommand.LegacyCreate(id(),f.employee(),f.units().get(0),f.position(),null,null,null);
        try {
            String before=runtimeEffects(f,actor);var em=spy(runtimeEntityManager);
            doAnswer(call->{call.callRealMethod();throw new IllegalStateException("synthetic legacy post-flush fault");}).when(em).flush();
            var rejected=new RuntimeTrace();
            assertThrows(RuntimeException.class,()->tracedRuntime("LEGACY_FLUSH_ROLLBACK",rejected,()->runtimeService(em).createLegacy(selectors)));
            rejected.rejected();assertEquals(0,rejected.guardBegan);assertEquals(before,runtimeEffects(f,actor));
            service.createLegacy(selectors);String committed=runtimeEffects(f,actor);
            admin("DELETE FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor);
            var denied=new RuntimeTrace();assertThrows(RuntimeException.class,()->tracedRuntime("LEGACY_REVOKED_REPLAY",denied,()->service.createLegacy(selectors)));
            denied.rejected();assertEquals(0,denied.guardBegan);assertEquals(committed,runtimeEffects(f,actor));
        } finally {SecurityContextHolder.clearContext();}
    }

    @Test void genuineExternalOriginsResolveLegacyDefaultsThroughTheirBoundNativeIssuers() throws Exception {
        for(String type:List.of("LDAP","ACTIVE_DIRECTORY","OIDC")) {
            var f=fixture(false);var origin=runtimeActor(f,type);var service=runtimeService(runtimeEntityManager);
            var selectors=new EmployeeAppointmentCommand.LegacyCreate(id(),f.employee(),f.units().get(0),f.position(),null,null,null);
            try {
                var trace=new RuntimeTrace();var result=tracedRuntime(type+"_LEGACY_DEFAULTS",trace,()->service.createLegacy(selectors));trace.committed();
                assertEquals(result.sampledAt(),result.after().snapshot().appointment().assignment().validFrom());
                assertEquals(AssignmentType.PRIMARY,result.after().snapshot().appointment().assignment().assignmentType());
                try(var c=owner()) {
                    assertEquals("1",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event e JOIN public.hidra_identity_operation_binding b ON b.binding_id=e.actor_binding_id::text JOIN public.hidra_identity_bound_source_claim_evidence s ON s.binding_id=b.binding_id WHERE e.employee_id=? AND e.actor_user_id=? AND b.authentication_type=? AND b.external_identity_id=?",
                            f.employee(),origin.actor(),type,origin.link()));
                }
                runtimeAuthenticate(origin);var replay=service.createLegacy(selectors);
                assertTrue(replay.historicalReplay());assertEquals(result.eventId(),replay.eventId());
            } finally {SecurityContextHolder.clearContext();}
        }
    }

    @Test void genuineLdapLifecycleReplayRollbackAndLinkRevocationUsePrivatePublisher() throws Exception {
        runtimeExternalLifecycle("LDAP");
    }

    @Test void genuineActiveDirectoryLifecycleReplayRollbackAndLinkRevocationUsePrivatePublisher() throws Exception {
        runtimeExternalLifecycle("ACTIVE_DIRECTORY");
    }

    @Test void genuineOidcLifecycleReplayRollbackAndLinkRevocationUsePrivatePublisher() throws Exception {
        runtimeExternalLifecycle("OIDC");
    }
}
