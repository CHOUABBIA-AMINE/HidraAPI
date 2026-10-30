/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentConcurrencyPostgresIntegrationTest
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
class EmployeeAppointmentConcurrencyPostgresIntegrationTest extends EmployeeAppointmentPostgresFixture {
    @Test void actualProvisionalCreateEndCorrectionAndTransferMatchFrozenCanonicalEventsAndRollBack() throws Exception {
        for(int kind=0;kind<5;kind++){
            var f=fixture(kind!=0);
            EmployeeAppointmentCommand command=switch(kind){
                case 0 -> create(f,id());
                case 1 -> end(f,1);
                case 2 -> correction(f,id(),1,f.units().get(0));
                case 3 -> correction(f,id(),1,f.units().get(1));
                default -> new EmployeeAppointmentCommand.Transfer(id(),f.company(),f.assignment(),1,f.units().get(1),f.position(),
                        AssignmentType.PRIMARY,START.plusSeconds(3600),null,1,1,REASON);
            };
            try(var p=new ProvisionalSession(command)){
                p.s.tx.execute(status -> {
                    p.persist();
                    assertDoesNotThrow(() -> p.invoke("requireCurrentWrite",p.write));
                    assertEquals(command instanceof EmployeeAppointmentCommand.Transfer?2:1,p.s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",Integer.class,f.employee()));
                    assertEquals(1,p.s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=? AND actor_user_id=?",Integer.class,f.employee(),p.s.actor));
                    assertEquals(0,p.s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_appointment_receipt WHERE event_id IN (SELECT event_id FROM public.hidra_org_appointment_event WHERE employee_id=? AND actor_user_id=?)",Integer.class,f.employee(),p.s.actor));
                    verify(p.executor,times(1)).beginProvisional(any(),argThat(sql -> sql.size()==6));
                    verify(p.executor,times(1)).endProvisional(any());
                    status.setRollbackOnly();return null;
                });
            }
            try(var c=owner()){
                assertEquals(kind==0?"0":"1",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
                assertEquals(kind==0?"0":"1",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            }
        }
    }

    @Test void swallowedProvisionalFailureAndMissingFinalReceiptVetoPhysicalCommit() throws Exception {
        for(boolean failWrite:List.of(false,true)){
            var f=fixture(false);var command=create(f,id());
            try(var p=new ProvisionalSession(command)){
                if(failWrite)admin("REVOKE INSERT ON public.hidra_org_appointment_event FROM "+p.s.role);
                var failure=assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                    if(failWrite)assertThrows(AccessDeniedException.class,p::persist);else p.persist();
                    return null; // Deliberately swallow the write failure or omit final proof/receipt.
                }));
                assertTrue(failure instanceof AccessDeniedException || failure instanceof org.springframework.transaction.UnexpectedRollbackException);
            }
            try(var c=owner()){
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
            }
        }
    }

    @Test void sameTransactionPostWriteProjectionAndPeerSubstitutionCannotPassFrozenEvidence() throws Exception {
        for(boolean peer:List.of(false,true)){
            var f=fixture(false);var command=create(f,id());
            try(var p=new ProvisionalSession(command)){p.s.tx.execute(status -> {
                p.persist();
                if(peer){
                    // A legacy ACTIVE row in the same physical transaction must not evade the complete post-write conflict probe.
                    p.s.jdbc.update("INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at) VALUES (?,?,?,?,'PRIMARY',?,'ACTIVE',now(),now())",
                            id(),f.employee(),f.units().get(2),f.position(),java.sql.Timestamp.from(START));
                }else{
                    // The old plan remains a value, never permission to accept another source version/event.
                    p.s.jdbc.update("UPDATE public.hidra_org_employee_assignment SET appointment_version=appointment_version+1,last_event_id=?::uuid WHERE employee_id=?",id(),f.employee());
                }
                assertThrows(AccessDeniedException.class,() -> p.invoke("requireCurrentWrite",p.write));
                assertTrue(p.s.poisoned());status.setRollbackOnly();return null;
            });}
        }
    }

    @Test void directStorageCallCannotWriteAndReplayCannotCreateKeysOrEvents() throws Exception {
        var f=fixture(false);var command=create(f,id());
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=qualified(s);s.storage.contributeParty(ticket);s.storage.prepareMutation(ticket,command);
            var statements=s.storage.prepareProvisional(ticket,command);
            var hashes=statements.stream().map(sql -> {
                try{return java.util.HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(sql.getBytes(java.nio.charset.StandardCharsets.UTF_8)));}
                catch(java.security.NoSuchAlgorithmException failure){throw new AssertionError(failure);}
            }).collect(java.util.stream.Collectors.toSet());
            assertEquals(java.util.Set.of("516725eb3d2d0fa98f75e73c62186d730c22b44b15d648ed03b1f4e88cba1af4","0991e6595ce13ba5b556da137e0211bc72ecc982a10a01feac5bcb2f9294c896","9d331551beaa09d534325126e04d71625f123dd606e5ca99f79b5e37edbec4d5","e1e6cbcbc2281f495441c7d63ead7d54dda6babfa4c8b1ad1fadbafb250454ee","5a891a894c55c6ebd50cf78641cf19a3c6ad748b4e8b4365eeab6b446424f424","db6d602e42ead6338d10af03c3086056a4c5d50e4b1b978b355c749d4dc57bda"),hashes);
            assertThrows(AccessDeniedException.class,() -> s.storage.persistProvisional(ticket));
            assertEquals(0,s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",Integer.class,f.employee()));
            status.setRollbackOnly();return null;
        });}
        try(var p=new ProvisionalSession(create(baseline,baseline.createRequest()))){p.s.actor="synthetic-actor";p.s.tx.execute(status -> {
            p.write=p.invoke("acquireWrite",p.prepared,p.s.command);
            assertThrows(AccessDeniedException.class,() -> p.invoke("persistProvisional",p.write));
            verify(p.executor,never()).beginProvisional(any(),any());status.setRollbackOnly();return null;
        });}
    }

    @Test void transferSuccessorOrEventFailureRollsBackTheSourceCloseAndEveryProvisionalKey() throws Exception {
        for(boolean successorFailure:List.of(true,false)){
            var f=fixture();
            var command=new EmployeeAppointmentCommand.Transfer(id(),f.company(),f.assignment(),1,f.units().get(1),f.position(),
                    AssignmentType.PRIMARY,START.plusSeconds(3600),null,1,1,REASON);
            try(var p=new ProvisionalSession(command)){
                admin("REVOKE INSERT ON public."+(successorFailure?"hidra_org_employee_assignment":"hidra_org_appointment_event")+" FROM "+p.s.role);
                var failure=assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                    assertThrows(AccessDeniedException.class,p::persist);return null;
                }));
                assertTrue(failure instanceof AccessDeniedException || failure instanceof org.springframework.transaction.UnexpectedRollbackException);
            }
            try(var c=owner()){
                assertEquals("1",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
                assertEquals("1",scalar(c,"SELECT appointment_version FROM public.hidra_org_employee_assignment WHERE id=? AND valid_to IS NULL",f.assignment()));
                assertEquals("1",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
            }
        }
    }

    @Test void combinedWriteCallbacksReturnDetachedReceiptsOnlyAfterActualCommitForEveryLifecycleShape() throws Exception {
        for(int kind=0;kind<5;kind++) {
            var f=fixture(kind!=0);
            EmployeeAppointmentCommand command=switch(kind) {
                case 0 -> create(f,id());case 1 -> end(f,1);
                case 2 -> correction(f,id(),1,f.units().get(0));case 3 -> correction(f,id(),1,f.units().get(1));
                default -> new EmployeeAppointmentCommand.Transfer(id(),f.company(),f.assignment(),1,f.units().get(1),f.position(),
                        AssignmentType.PRIMARY,START.plusSeconds(3600),null,1,1,REASON);
            };
            try(var p=new ProvisionalSession(command)) {
                enableJavaFinalGuard(p,false);
                var result=p.s.tx.execute(status->{
                    p.write=p.invoke("acquireWrite",p.prepared,command);
                    var detached=p.invoke("apply",p.write);
                    p.invoke("prepareFinal",p.write);
                    clearInvocations(p.s.identity,p.s.party);
                    p.invoke("finalizeOperation",p.write);verifyNoInteractions(p.s.identity,p.s.party);
                    return (dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.MutationReceipt)detached;
                });
                assertNotNull(result);assertEquals(command.kind(),result.kind());assertEquals(command.requestId(),result.requestId());
                assertFalse(result.historicalReplay());assertEquals(f.employee(),result.after().snapshot().appointment().assignment().employeeId());
                assertEquals(kind==0?1:2,result.after().appointmentVersion());
                assertEquals(kind==4,result.successor()!=null);
                try(var c=owner()) {
                    assertEquals("1",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_receipt WHERE event_id=?::uuid",result.eventId()));
                }
            }
        }
    }

    @Test void combinedWriteResultRemainsInternalWhenFinalAuthenticationFails() throws Exception {
        var f=fixture(false);
        try(var p=new ProvisionalSession(create(f,id()))) {
            enableJavaFinalGuard(p,true);
            assertThrows(RuntimeException.class,()->p.s.tx.execute(status->{
                p.write=p.invoke("acquireWrite",p.prepared,p.s.command);
                assertNotNull(p.invoke("apply",p.write));p.invoke("prepareFinal",p.write);
                p.invoke("finalizeOperation",p.write);return null;
            }));
        }
        try(var c=owner()) {
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
        }
    }

    @Test void combinedReplayUsesActualReadGuardAndCommitWithoutCreatingAnotherEventOrReceipt() throws Exception {
        var command=create(baseline,baseline.createRequest());
        try(var p=new ProvisionalSession(command)) {
            p.s.actor="synthetic-actor";
            admin("GRANT EXECUTE ON FUNCTION public.hidra_org_appointment_read_guard(jsonb,timestamptz,timestamptz,integer,bigint,timestamptz) TO "+p.s.role);
            var result=p.s.tx.execute(status->{
                p.write=p.invoke("acquireWrite",p.prepared,command);var detached=p.invoke("apply",p.write);
                p.invoke("prepareFinal",p.write);clearInvocations(p.s.identity,p.s.party);
                p.invoke("finalizeOperation",p.write);verifyNoInteractions(p.s.identity,p.s.party);
                return (dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.MutationReceipt)detached;
            });
            assertNotNull(result);assertTrue(result.historicalReplay());assertEquals(baseline.assignment(),result.after().assignmentId());
            verify(p.executor,never()).beginProvisional(any(),any());verify(p.s.identity,never()).issueCommitProof(any(),any());
        }
        try(var c=owner()) {
            assertEquals("1",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",baseline.employee()));
            assertEquals("1",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_receipt r JOIN public.hidra_org_appointment_event e USING(event_id) WHERE e.employee_id=?",baseline.employee()));
        }
    }

    @Test void javaFinalGuardCommitsExactCreateEndCorrectionAndTransferWithReceiptAndNonce() throws Exception {
        for(int kind=0;kind<5;kind++){
            var f=fixture(kind!=0);
            EmployeeAppointmentCommand command=switch(kind){
                case 0 -> create(f,id());case 1 -> end(f,1);
                case 2 -> correction(f,id(),1,f.units().get(0));case 3 -> correction(f,id(),1,f.units().get(1));
                default -> new EmployeeAppointmentCommand.Transfer(id(),f.company(),f.assignment(),1,f.units().get(1),f.position(),
                        AssignmentType.PRIMARY,START.plusSeconds(3600),null,1,1,REASON);
            };
            try(var p=new ProvisionalSession(command)){
                enableJavaFinalGuard(p,false);
                p.s.tx.execute(status -> {
                    p.persist();p.invoke("prepareCommitProof",p.write);p.invoke("prepareFinalWrite",p.write);
                    clearInvocations(p.s.identity,p.s.party);
                    p.invoke("finalizeWrite",p.write);verifyNoInteractions(p.s.identity,p.s.party);
                    return null; // No owner SQL after GUARD; actual transaction commit runs both lifetime vetoes.
                });
            }
            assertEquals("1",finalCount("hidra_org_appointment_receipt",f.employee()));
            assertEquals("1",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
            try(var c=owner()){
                assertEquals(kind==4?"2":"1",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
                assertEquals(kind==0?"1":"2",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            }
        }
    }

    @Test void javaFinalGuardRollbackRemovesReceiptNonceEventProjectionAndKeys() throws Exception {
        var f=fixture(false);
        try(var p=new ProvisionalSession(create(f,id()))){
            enableJavaFinalGuard(p,false);p.s.tx.execute(status -> {
                p.persist();p.invoke("prepareCommitProof",p.write);p.invoke("prepareFinalWrite",p.write);
                p.invoke("finalizeWrite",p.write);status.setRollbackOnly();return null;
            });
        }
        assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        try(var c=owner()){
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
        }
    }

    @Test void javaFinalGuardRejectsForgedMacAndSwallowedFailureCannotCommitOrRetry() throws Exception {
        var f=fixture(false);
        try(var p=new ProvisionalSession(create(f,id()))){
            enableJavaFinalGuard(p,true);
            assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                p.persist();p.invoke("prepareCommitProof",p.write);p.invoke("prepareFinalWrite",p.write);
                assertThrows(AccessDeniedException.class,() -> p.invoke("finalizeWrite",p.write));
                assertTrue(p.s.poisoned());assertThrows(AccessDeniedException.class,() -> p.invoke("finalizeWrite",p.write));
                return null;
            }));
        }
        assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        try(var c=owner()){assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));}
    }

    @Test void preparedFinalWriteWithoutGuardStillVetoesPhysicalCommit() throws Exception {
        var f=fixture(false);
        try(var p=new ProvisionalSession(create(f,id()))){
            enableJavaFinalGuard(p,false);
            assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                p.persist();p.invoke("prepareCommitProof",p.write);p.invoke("prepareFinalWrite",p.write);
                return null; // A prepared argument image is never a successful database guard.
            }));
        }
        assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        try(var c=owner()){assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));}
    }

    @Test void javaFinalGuardRejectsProjectionOrLegacyPeerMutationAfterFinalPreparation() throws Exception {
        for(boolean peer:List.of(false,true)){
            var f=fixture(false);
            try(var p=new ProvisionalSession(create(f,id()))){
                enableJavaFinalGuard(p,false);p.s.tx.execute(status -> {
                    p.persist();p.invoke("prepareCommitProof",p.write);p.invoke("prepareFinalWrite",p.write);
                    if(peer)p.s.jdbc.update("INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at) VALUES (?,?,?,?,'PRIMARY',?,'ACTIVE',now(),now())",
                            id(),f.employee(),f.units().get(2),f.position(),java.sql.Timestamp.from(START));
                    else p.s.jdbc.update("UPDATE public.hidra_org_employee_assignment SET appointment_version=2,last_event_id=?::uuid WHERE employee_id=?",id(),f.employee());
                    assertThrows(AccessDeniedException.class,() -> p.invoke("finalizeWrite",p.write));
                    assertTrue(p.s.poisoned());status.setRollbackOnly();return null;
                });
            }
            assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
            assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        }
    }

    @Test void javaFinalPreparationRejectsRevokedAuthorityAndPostGuardAuthenticationSwapVetoesCommit() throws Exception {
        for(boolean revoke:List.of(false,true)){
            var f=fixture(false);
            try(var p=new ProvisionalSession(create(f,id()))){
                enableJavaFinalGuard(p,false);
                assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                    p.persist();p.invoke("prepareCommitProof",p.write);
                    if(revoke){
                        doThrow(new AccessDeniedException("revoked")).when(p.s.identity).requireCurrent(p.s.nativeTicket);
                        assertThrows(AccessDeniedException.class,() -> p.invoke("prepareFinalWrite",p.write));
                    }else{
                        p.invoke("prepareFinalWrite",p.write);p.invoke("finalizeWrite",p.write);
                        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("replacement",null,List.of()));
                    }
                    return null;
                }));
            }
            assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
            assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        }
    }

    @Test void finalWriteStorageRejectsDirectCallsAndPinsTheExactScalarStatement() throws Exception {
        for(boolean guard:List.of(false,true)){
            var f=fixture(false);
            try(var s=new Session(create(f,id()))){s.tx.execute(status -> {
                var admission=qualified(s);
                assertThrows(AccessDeniedException.class,() -> {if(guard)s.storage.finalizeWrite(admission);else s.storage.prepareFinalWrite(admission);});
                assertTrue(s.poisoned());status.setRollbackOnly();return null;
            });}
        }
        var field=JdbcProtectedEmployeeAppointmentRepositoryAdapter.class.getDeclaredField("WRITE_GUARD");field.setAccessible(true);
        assertEquals(FINAL_SQL,field.get(null));
    }

    @Test void storageDerivesCommitIntentFromExactPersistedUtf8EventForEveryFreshCommand() throws Exception {
        for(int kind=0;kind<5;kind++){
            var f=fixture(kind!=0);
            EmployeeAppointmentCommand command=switch(kind){
                case 0 -> create(f,id());
                case 1 -> end(f,1);
                case 2 -> correction(f,id(),1,f.units().get(0));
                case 3 -> correction(f,id(),1,f.units().get(1));
                default -> new EmployeeAppointmentCommand.Transfer(id(),f.company(),f.assignment(),1,f.units().get(1),f.position(),
                        AssignmentType.PRIMARY,START.plusSeconds(3600),null,1,1,REASON);
            };
            try(var p=new ProvisionalSession(command)){
                var captured=new java.util.concurrent.atomic.AtomicReference<CommitIntent>();
                when(p.s.identity.issueCommitProof(eq(p.s.nativeTicket),any())).thenAnswer(call -> {
                    var intent=call.getArgument(1,CommitIntent.class);captured.set(intent);
                    var expected=p.s.jdbc.queryForObject("SELECT event_id::text,command_sha256,encode(sha256(canonical_payload),'hex'),valid_until FROM public.hidra_org_appointment_event WHERE employee_id=? AND actor_user_id=?",
                            (r,i) -> new CommitIntent(UUID.fromString(r.getString(1)),r.getString(2),r.getString(3),r.getTimestamp(4).toInstant()),f.employee(),p.s.actor);
                    assertEquals(expected,intent);return syntheticProof();
                });
                p.s.tx.execute(status -> {
                    p.persist();assertNull(p.invoke("prepareCommitProof",p.write));assertNotNull(captured.get());
                    verify(p.s.identity,times(1)).issueCommitProof(eq(p.s.nativeTicket),any());
                    verify(p.s.identity,times(1)).contribute(p.s.nativeTicket);
                    var order=inOrder(p.executor,p.s.identity);
                    order.verify(p.executor).endProvisional(any());
                    order.verify(p.s.identity).issueCommitProof(eq(p.s.nativeTicket),eq(captured.get()));
                    assertEquals(0,p.s.jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_appointment_receipt WHERE event_id=?::uuid",Integer.class,captured.get().eventId().toString()));
                    status.setRollbackOnly();return null;
                });
            }
            try(var c=owner()){
                assertEquals(kind==0?"0":"1",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
                assertEquals(kind==0?"0":"1",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            }
        }
    }

    @Test void boundedProofTransportAloneNeverAuthorizesReceiptOrPhysicalCommit() throws Exception {
        var f=fixture(false);
        try(var p=new ProvisionalSession(create(f,id()))){
            when(p.s.identity.issueCommitProof(eq(p.s.nativeTicket),any())).thenReturn(syntheticProof());
            assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                p.persist();p.invoke("prepareCommitProof",p.write);return null;
            }));
        }
        try(var c=owner()){
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
        }
    }

    @Test void missingOrFailedNativeProofPoisonsOriginalTransactionWithoutAnotherIssuerAttempt() throws Exception {
        for(boolean throwsFailure:List.of(false,true)){
            var f=fixture(false);
            try(var p=new ProvisionalSession(create(f,id()))){
                if(throwsFailure)when(p.s.identity.issueCommitProof(any(),any())).thenThrow(new IllegalStateException("native proof unavailable"));
                assertThrows(RuntimeException.class,() -> p.s.tx.execute(status -> {
                    p.persist();
                    assertThrows(AccessDeniedException.class,() -> p.invoke("prepareCommitProof",p.write));
                    assertTrue(p.s.poisoned());
                    assertThrows(AccessDeniedException.class,() -> p.invoke("prepareCommitProof",p.write));
                    verify(p.s.identity,times(1)).issueCommitProof(eq(p.s.nativeTicket),any());
                    return null; // Deliberately swallow; beforeCommit must still veto.
                }));
            }
            try(var c=owner()){
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            }
        }
    }

    @Test void nativeProofCallbackCannotSubstitutePostWriteStateAuthenticationOrAuthority() throws Exception {
        for(int variant=0;variant<3;variant++){
            var f=fixture(false);final int changed=variant;
            try(var p=new ProvisionalSession(create(f,id()))){
                when(p.s.identity.issueCommitProof(eq(p.s.nativeTicket),any())).thenAnswer(call -> {
                    if(changed==0)p.s.jdbc.update("UPDATE public.hidra_org_employee_assignment SET appointment_version=appointment_version+1,last_event_id=?::uuid WHERE employee_id=?",id(),f.employee());
                    if(changed==1)SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("replacement",null,List.of()));
                    if(changed==2)doThrow(new AccessDeniedException("revoked")).when(p.s.identity).requireCurrent(p.s.nativeTicket);
                    return syntheticProof();
                });
                p.s.tx.execute(status -> {
                    p.persist();
                    assertThrows(AccessDeniedException.class,() -> p.invoke("prepareCommitProof",p.write));
                    assertTrue(p.s.poisoned());
                    verify(p.s.identity,times(1)).issueCommitProof(eq(p.s.nativeTicket),any());
                    status.setRollbackOnly();return null;
                });
            }
            try(var c=owner()){
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
            }
        }
    }

    @Test void proofPreparationRejectsDirectEarlyAndRepeatedStorageUse() throws Exception {
        var f=fixture(false);
        try(var s=new Session(create(f,id()))){s.tx.execute(status -> {
            var ticket=qualified(s);
            assertThrows(AccessDeniedException.class,() -> s.storage.prepareCommitProof(ticket));
            verify(s.identity,never()).issueCommitProof(any(),any());assertTrue(s.poisoned());
            status.setRollbackOnly();return null;
        });}
        try(var p=new ProvisionalSession(create(f,id()))){p.s.tx.execute(status -> {
            p.write=p.invoke("acquireWrite",p.prepared,p.s.command);
            assertThrows(AccessDeniedException.class,() -> p.invoke("prepareCommitProof",p.write));
            verify(p.s.identity,never()).issueCommitProof(any(),any());
            status.setRollbackOnly();return null;
        });}
        try(var p=new ProvisionalSession(create(f,id()))){
            when(p.s.identity.issueCommitProof(eq(p.s.nativeTicket),any())).thenReturn(syntheticProof());
            p.s.tx.execute(status -> {
                p.persist();p.invoke("prepareCommitProof",p.write);
                assertThrows(AccessDeniedException.class,() -> p.invoke("prepareCommitProof",p.write));
                verify(p.s.identity,times(1)).issueCommitProof(eq(p.s.nativeTicket),any());assertTrue(p.s.poisoned());
                status.setRollbackOnly();return null;
            });
        }
    }

    @Test void realFinalSqlGuardAuthenticatesAndCommitsReceiptAndNonceAtomically() throws Exception {
        var f=fixture(false);
        try(var g=new FinalSqlSession(f,false,false)){
            g.guard(g.proof());
            assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
            assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
            g.c.commit();
        }
        assertEquals("1",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("1",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        assertEquals("1",finalCount("hidra_org_employee_assignment",f.employee()));
    }

    @Test void realFinalSqlGuardRollbackRemovesProjectionKeysEventReceiptAndNonce() throws Exception {
        var f=fixture(false);
        try(var g=new FinalSqlSession(f,false,false)){g.guard(g.proof());g.c.rollback();}
        for(String table:List.of("hidra_org_employee_assignment","hidra_org_appointment_event","hidra_org_appointment_receipt","hidra_identity_org_commit_proof_use"))assertEquals("0",finalCount(table,f.employee()));
        try(var c=owner()){assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));}
    }

    @Test void realFinalSqlGuardRejectsForgedMacAndValidMacForForeignScopeOrPayload() throws Exception {
        for(int variant=0;variant<3;variant++){
            var f=fixture(false);
            try(var g=new FinalSqlSession(f,false,false)){
                var proof=variant==1?g.proof(f.units().get(1),g.payloadDigest):variant==2?g.proof(f.units().get(0),"00".repeat(32)):g.proof();
                if(variant==0)proof.mac()[0]^=1;
                assertThrows(SQLException.class,() -> g.guard(proof));g.c.rollback();
            }
            assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
        }
    }

    @Test void realFinalSqlGuardRejectsChangedRequestAndExpiredNativeWindow() throws Exception {
        for(boolean expired:List.of(false,true)){
            var f=fixture(false);
            try(var g=new FinalSqlSession(f,expired,false)){
                var proof=g.proof();assertThrows(SQLException.class,() -> g.guard(proof,expired?g.request:"other-request"));g.c.rollback();
            }
            assertEquals("0",finalCount("hidra_org_employee_assignment",f.employee()));
        }
    }

    @Test void realFinalSqlGuardDeferredTypedFailureRollsBackProofUseAndReceipt() throws Exception {
        var f=fixture(false);
        try(var g=new FinalSqlSession(f,false,true)){
            var proof=g.proof();assertThrows(SQLException.class,() -> g.guard(proof));g.c.rollback();
        }
        assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
    }

    @Test void realFinalSqlGuardCannotRedeemEventTwiceEvenInSameTransaction() throws Exception {
        var f=fixture(false);
        try(var g=new FinalSqlSession(f,false,false)){
            var proof=g.proof();g.guard(proof);var save=g.c.setSavepoint();
            assertThrows(SQLException.class,() -> g.guard(proof));g.c.rollback(save);g.c.releaseSavepoint(save);g.c.commit();
        }
        assertEquals("1",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("1",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
    }

    @Test void realFinalSqlGuardRejectsSameTransactionProjectionAndLegacyPeerSubstitution() throws Exception {
        for(boolean peer:List.of(false,true)){
            var f=fixture(false);
            try(var g=new FinalSqlSession(f,false,false)){
                var proof=g.proof();
                if(peer)execute(g.c,"INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at) VALUES (?,?,?,?,'PRIMARY',?::timestamptz,'ACTIVE',now(),now())",id(),f.employee(),f.units().get(2),f.position(),START.toString());
                else execute(g.c,"UPDATE public.hidra_org_employee_assignment SET appointment_version=2,last_event_id=?::uuid WHERE id=?",id(),g.assignment);
                assertThrows(SQLException.class,() -> g.guard(proof));g.c.rollback();
            }
            assertEquals("0",finalCount("hidra_org_employee_assignment",f.employee()));
        }
    }

    @Test void finalSqlFunctionsHaveClosedAclAndNoOrdinaryReceiptOrPrivateVerifierAccess() throws Exception {
        var f=fixture(false);
        try(var s=new Session(create(f,id()));var c=s.source.getConnection()){
            for(String signature:List.of(FINAL_GUARD,"public.hidra_org_appointment_final_context()","public.hidra_org_appointment_final_fingerprint(regclass)","public.hidra_identity_org_commit_verify_consume(bytea,bytea,jsonb)"))
                assertEquals("f",scalar(c,"SELECT has_function_privilege(current_user,?,'EXECUTE')",signature));
            assertEquals("f",scalar(c,"SELECT has_table_privilege(current_user,'public.hidra_org_appointment_receipt','INSERT')"));
        }
        try(var c=owner()){
            assertEquals("3",scalar(c,"SELECT count(*) FROM pg_proc p JOIN pg_namespace n ON n.oid=p.pronamespace WHERE n.nspname='public' AND p.proname IN ('hidra_org_appointment_final_guard','hidra_org_appointment_final_context','hidra_org_appointment_final_fingerprint') AND NOT EXISTS(SELECT 1 FROM aclexplode(coalesce(p.proacl,acldefault('f',p.proowner))) a WHERE a.grantee=0 AND a.privilege_type='EXECUTE')"));
        }
        assertEquals("4691e7803cd1b3619472ddc29872a9f893adb5decbadbfe05e94779196e4a01b",java.util.HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(FINAL_SQL.getBytes(java.nio.charset.StandardCharsets.UTF_8))));
    }

    @Test void realFinalSqlGuardRejectsReceiptWritePrivilegeAndMissingOwnerFence() throws Exception {
        for(int attack:List.of(1,2)){
            var f=fixture(false);
            try(var g=new FinalSqlSession(f,false,false,attack,START)){
                var proof=g.proof();assertThrows(SQLException.class,() -> g.guard(proof));g.c.rollback();
            }
            assertEquals("0",finalCount("hidra_org_appointment_receipt",f.employee()));
            assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        }
    }

    @Test void realFinalSqlGuardIndependentlyRejectsEmployeeOverlapAndPositionCapacityConflict() throws Exception {
        for(boolean otherEmployee:List.of(false,true)){
            var seeded=fixture();var target=seeded;
            if(otherEmployee){
                String employee=id();
                admin("INSERT INTO public.hidra_org_employee(id,employee_number,employee_type,status,created_at,updated_at) VALUES (?,?,'PERMANENT','ACTIVE',now(),now())",employee,employee);
                target=new Fixture(seeded.company(),employee,id(),seeded.position(),seeded.units(),seeded.policy(),id());
            }
            try(var g=new FinalSqlSession(target,false,false)){
                var proof=g.proof();assertThrows(SQLException.class,() -> g.guard(proof));g.c.rollback();
            }
            assertEquals("0",finalCount("hidra_org_appointment_receipt",target.employee()));
            assertEquals("0",finalCount("hidra_identity_org_commit_proof_use",target.employee()));
        }
    }

    @Test void realFinalSqlGuardAllowsExactHalfOpenAdjacencyWithoutFalsePrimaryPeak() throws Exception {
        var f=fixture(false);var boundary=START.plusSeconds(3600);seedCreate(f,boundary);
        try(var g=new FinalSqlSession(f,false,false,0,boundary)){g.guard(g.proof());g.c.commit();}
        assertEquals("1",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("1",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
        assertEquals("2",finalCount("hidra_org_employee_assignment",f.employee()));
    }

    @Test void archivedFinalGuardReproducesAmbiguousSweepColumnWhileForwardGuardCommits() throws Exception {
        // Test-only diagnostic clone of immutable001, with no redacting handler: expose only SQLSTATE.
        // The installed guard keeps generic diagnostics; no production function/ACL is weakened.
        String archived;
        try(var in=getClass().getResourceAsStream("/db/migration/V20261023_001__organization_appointment_final_write_guard.sql")){
            assertNotNull(in);archived=new String(in.readAllBytes(),java.nio.charset.StandardCharsets.UTF_8);
        }
        String currentContext;
        try(var in=getClass().getResourceAsStream("/db/migration/V20261023_003__organization_appointment_fingerprint_visibility.sql")){
            assertNotNull(in);currentContext=new String(in.readAllBytes(),java.nio.charset.StandardCharsets.UTF_8);
        }
        int contextStart=currentContext.indexOf("CREATE OR REPLACE FUNCTION public.hidra_org_appointment_final_context()");
        int contextEnd=currentContext.indexOf("$context$;",contextStart)+"$context$;".length();
        String context=markFixtureRejections(currentContext.substring(contextStart,contextEnd),"C")
                .replace("CREATE OR REPLACE FUNCTION public.hidra_org_appointment_final_context()","CREATE FUNCTION public.fixture_final_context()");
        context=context.replace("PERFORM public.hidra_identity_org_commit_caller();",
                "BEGIN PERFORM public.hidra_identity_org_commit_caller(); EXCEPTION WHEN OTHERS THEN RAISE EXCEPTION 'Fixture invariant C08'; END;")
                .replace("PERFORM public.hidra_identity_org_commit_locks();",
                "BEGIN PERFORM public.hidra_identity_org_commit_locks(); EXCEPTION WHEN OTHERS THEN RAISE EXCEPTION 'Fixture invariant C09'; END;");
        admin(context);
        admin("REVOKE ALL ON FUNCTION public.fixture_final_context() FROM PUBLIC");
        int start=archived.indexOf("CREATE FUNCTION public.hidra_org_appointment_final_guard(");
        int end=archived.indexOf("$guard$;",start)+"$guard$;".length();
        String diagnostic=archived.substring(start,end)
                .replace("CREATE FUNCTION public.hidra_org_appointment_final_guard(","CREATE FUNCTION public.fixture_ambiguous_final_guard(")
                .replace("EXCEPTION WHEN OTHERS THEN\n RAISE EXCEPTION USING ERRCODE='P0001',MESSAGE='Appointment finalization unavailable';\n","");
        assertFalse(diagnostic.contains("EXCEPTION WHEN OTHERS"));
        diagnostic=markFixtureRejections(diagnostic,"G").replace(
                "PERFORM public.hidra_org_appointment_final_context();","PERFORM public.fixture_final_context();");
        admin(diagnostic);
        admin("REVOKE ALL ON FUNCTION public.fixture_ambiguous_final_guard(uuid,uuid,uuid,text,text,text,text,timestamptz,timestamptz,integer,bigint,bytea,bytea,timestamptz) FROM PUBLIC");
        var f=fixture(false);
        try(var g=new FinalSqlSession(f,false,false)){
            admin("GRANT EXECUTE ON FUNCTION public.fixture_ambiguous_final_guard(uuid,uuid,uuid,text,text,text,text,timestamptz,timestamptz,integer,bigint,bytea,bytea,timestamptz) TO "+g.login.role);
            var proof=g.proof();var save=g.c.setSavepoint();
            var failure=assertThrows(SQLException.class,() -> g.guardSql(proof,g.request,
                    FINAL_SQL.replace("public.hidra_org_appointment_final_guard(","public.fixture_ambiguous_final_guard(")));
            assertEquals("42702",failure.getSQLState(),safeFixtureMarker(failure)); // No protected values in assertion diagnostics.
            g.c.rollback(save);g.c.releaseSavepoint(save);
            g.guard(proof);g.c.commit();
        }
        assertEquals("1",finalCount("hidra_org_appointment_receipt",f.employee()));
        assertEquals("1",finalCount("hidra_identity_org_commit_proof_use",f.employee()));
    }

    @Test void finalFingerprintPreservesAcceptedPinsWithCanonicalPublicVisibilityAndClosedAcl() throws Exception {
        String archived;
        try(var in=getClass().getResourceAsStream("/db/migration/V20261023_001__organization_appointment_final_write_guard.sql")){
            assertNotNull(in);archived=new String(in.readAllBytes(),java.nio.charset.StandardCharsets.UTF_8);
        }
        int start=archived.indexOf("CREATE FUNCTION public.hidra_org_appointment_final_fingerprint(");
        int end=archived.indexOf("$fingerprint$;",start)+"$fingerprint$;".length();
        admin(archived.substring(start,end).replace("CREATE FUNCTION public.hidra_org_appointment_final_fingerprint(","CREATE FUNCTION public.fixture_old_fingerprint("));
        admin("REVOKE ALL ON FUNCTION public.fixture_old_fingerprint(regclass) FROM PUBLIC");
        String relation="public.hidra_org_employee_assignment";
        String accepted="32d8113ab0f7d677837cd66c471351efd25ca3b0c05f76aacaeda9aab4b8c406";
        try(var c=owner()){
            assertNotEquals(accepted,scalar(c,"SELECT public.fixture_old_fingerprint(?::regclass)",relation));
            assertEquals(accepted,scalar(c,"SELECT public.hidra_org_appointment_final_fingerprint(?::regclass)",relation));
            assertEquals("search_path=pg_catalog, public",scalar(c,"SELECT proconfig[1] FROM pg_proc WHERE oid='public.hidra_org_appointment_final_fingerprint(regclass)'::regprocedure"));
        }
        var f=fixture(false);
        try(var login=new Session(create(f,id()));var c=login.source.getConnection()){
            assertEquals("f",scalar(c,"SELECT has_schema_privilege(current_user,'public','CREATE')"));
            assertEquals("f",scalar(c,"SELECT has_function_privilege(current_user,'public.hidra_org_appointment_final_fingerprint(regclass)','EXECUTE')"));
        }
    }

    @Test void committedCreateReceiptReplaysOnlyForItsOriginalActorAndExactCommand() throws Exception {
        var command=create(baseline,baseline.createRequest());classify(command,ReferenceUse.REPLAY);
        try(var s=new Session(command)){s.actor="another-actor";s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            assertEquals(ReferenceUse.CURRENT,s.storage.referenceUse(ticket,command,sample));status.setRollbackOnly();return null;
        });}
    }

    @Test void originalCorrectionReplaysFromHistoricalPinsAfterLaterMoveRebindingAndPartySuspension() throws Exception {
        try(var s=new Session(originalCorrection)){s.tx.execute(status -> {
            var preview=s.storage.preview(originalCorrection);
            assertEquals(2,preview.companyUnits().size());
            assertTrue(preview.companyUnits().contains(new CompanyUnit(historical.company(),historical.units().get(0))));
            assertTrue(preview.companyUnits().contains(new CompanyUnit(historical.company(),historical.units().get(1))));
            assertFalse(preview.companyUnits().contains(new CompanyUnit(historical.company(),historical.units().get(2))));
            assertEquals(3L,s.jdbc.queryForObject("SELECT appointment_version FROM public.hidra_org_employee_assignment WHERE id=?",Long.class,historical.assignment()));
            assertNotEquals(historical.company(),s.jdbc.queryForObject("SELECT company_party_id FROM public.hidra_org_appointment_binding_head WHERE organization_unit_id=?",String.class,historical.units().get(0)));
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            assertEquals(ReferenceUse.REPLAY,s.storage.referenceUse(ticket,originalCorrection,sample));
            status.setRollbackOnly();return null;
        });}
    }

    @Test void receiptKeyRejectsChangedReasonVersionNullableBoundaryAndPolicyRevision() throws Exception {
        var c=originalCorrection;
        for(var changed:List.of(
                new EmployeeAppointmentCommand.Correct(c.requestId(),c.companyPartyId(),c.assignmentId(),1,c.correctedOrganizationUnitId(),c.correctedPositionId(),c.correctedType(),c.correctedStatus(),START,null,1,1,"Changed reason"),
                new EmployeeAppointmentCommand.Correct(c.requestId(),c.companyPartyId(),c.assignmentId(),3,c.correctedOrganizationUnitId(),c.correctedPositionId(),c.correctedType(),c.correctedStatus(),START,null,1,1,REASON),
                new EmployeeAppointmentCommand.Correct(c.requestId(),c.companyPartyId(),c.assignmentId(),1,c.correctedOrganizationUnitId(),c.correctedPositionId(),c.correctedType(),c.correctedStatus(),START,START.plusSeconds(3600),1,1,REASON),
                new EmployeeAppointmentCommand.Correct(c.requestId(),c.companyPartyId(),c.assignmentId(),1,c.correctedOrganizationUnitId(),c.correctedPositionId(),c.correctedType(),c.correctedStatus(),START,null,1,2,REASON))) {
            try(var s=new Session(changed)){s.tx.execute(status -> {
                var ticket=s.acquire();var sample=s.storage.contribute(ticket);
                assertEquals("Protected appointment admission unavailable",assertThrows(AccessDeniedException.class,
                        () -> s.storage.referenceUse(ticket,changed,sample)).getMessage());
                assertTrue(s.poisoned());status.setRollbackOnly();return null;
            });}
        }
    }

    @Test void freshMutationRejectsStaleCasAndRetainedBindingRevisionSubstitution() throws Exception {
        for(var command:List.of(end(baseline,2),new EmployeeAppointmentCommand.Correct(id(),baseline.company(),baseline.assignment(),1,
                baseline.units().get(0),baseline.position(),AssignmentType.PRIMARY,AssignmentStatus.ACTIVE,START,null,2,1,REASON))) {
            try(var s=new Session(command)){s.tx.execute(status -> {
                var ticket=s.acquire();var sample=s.storage.contribute(ticket);
                assertThrows(AccessDeniedException.class,() -> s.storage.referenceUse(ticket,command,sample));
                assertTrue(s.poisoned());status.setRollbackOnly();return null;
            });}
        }
    }

    @Test void scalarContributionCopyCannotQualifyClassificationOrRestoreAPoisonedTicket() throws Exception {
        var command=end(baseline,1);
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            var copy=new Contribution(sample.request(),sample.decisions(),sample.bindingId(),sample.sessionId(),sample.userId(),
                    sample.sampledAt(),sample.validUntil(),sample.backendPid(),sample.transactionId());
            assertEquals(sample,copy);assertNotSame(sample,copy);
            assertThrows(AccessDeniedException.class,() -> s.storage.referenceUse(ticket,command,copy));
            assertTrue(s.poisoned());assertThrows(AccessDeniedException.class,() -> s.storage.referenceUse(ticket,command,sample));
            status.setRollbackOnly();return null;
        });}
    }

    @Test void replayRefusesAnIncompleteHistoricalOwnershipClosure() throws Exception {
        try(var s=new Session(originalCorrection)){
            s.previewOverride=new ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                    List.of(new CompanyUnit(historical.company(),historical.units().get(1))),historical.employee(),historical.assignment(),false);
            s.tx.execute(status -> {
                var ticket=s.acquire();var sample=s.storage.contribute(ticket);
                assertThrows(AccessDeniedException.class,() -> s.storage.referenceUse(ticket,originalCorrection,sample));
                assertTrue(s.poisoned());status.setRollbackOnly();return null;
            });
        }
    }

    @Test void classificationIsFrozenToTheExactCommandAndRechecksNativeRevocation() throws Exception {
        var command=end(baseline,1);
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            assertEquals(ReferenceUse.RETAINED,s.storage.referenceUse(ticket,command,sample));
            assertThrows(AccessDeniedException.class,() -> s.storage.referenceUse(ticket,end(baseline,1),sample));
            assertTrue(s.poisoned());status.setRollbackOnly();return null;
        });}
        try(var s=new Session(originalCorrection)){s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            assertEquals(ReferenceUse.REPLAY,s.storage.referenceUse(ticket,originalCorrection,sample));
            doThrow(new IllegalStateException("issuer revoked")).when(s.identity).requireCurrent(s.nativeTicket);
            assertThrows(AccessDeniedException.class,() -> s.storage.referenceUse(ticket,originalCorrection,sample));
            assertTrue(s.poisoned());status.setRollbackOnly();return null;
        });}
    }

    @Test void everyFreshKindBuildsStablePrivatePlansWithoutMutatingTheDatabase() throws Exception {
        var empty=fixture(false);prepare(create(empty,id()),true);
        var f=fixture();prepare(end(f,1),true);
        prepare(correction(f,id(),1,f.units().get(0)),true);prepare(correction(f,id(),1,f.units().get(1)),true);
        prepare(new EmployeeAppointmentCommand.Transfer(id(),f.company(),f.assignment(),1,f.units().get(1),f.position(),
                AssignmentType.ACTING,START.plusSeconds(3600),null,1,1,REASON),true);
        try(var c=owner()){assertEquals("1",scalar(c,"SELECT appointment_version FROM public.hidra_org_employee_assignment WHERE id=?",f.assignment()));}
    }

    @Test void retainedPlanningKeepsTheOriginalCompanyPinAfterRebindAndPartySuspension() throws Exception {
        var f=fixture();var other=fixture(false);
        try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),CONFIG,PASSWORD)){
            scalar(c,"SELECT public.hidra_org_activate_appointment_binding(?,?,?,1)",f.units().get(0),f.units().get(0),other.company());
        }
        admin("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",f.company());
        prepare(end(f,1),true);prepare(correction(f,id(),1,f.units().get(0)),true);
    }

    @Test void completeEmployeeConflictsIncludeProtectedPeersFromAnotherCompany() throws Exception {
        var selected=fixture(false);var other=fixture(false);
        seedCreate(new Fixture(other.company(),selected.employee(),id(),other.position(),other.units(),other.policy(),id()),null);
        prepare(create(selected,id()),false);
    }

    @Test void explicitPositionCapacityControlsOtherEmployeesWithoutInferringADefault() throws Exception {
        var f=fixture();var other=fixture(false);
        var occupant=new Fixture(f.company(),other.employee(),id(),f.position(),f.units(),f.policy(),id());
        prepare(create(occupant,id()),false);
        activatePolicy(f,policy().replace("SINGLE_HOLDER","UNRESTRICTED"));
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),other.employee(),f.units().get(0),f.position(),
                AssignmentType.PRIMARY,START,null,1,2),true);
    }

    @Test void completeTypeMatrixAndPrimaryLimitAreIndependentlyEnforced() throws Exception {
        var f=fixture();
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),
                AssignmentType.SECONDARY,START,null,1,1),false);
        activatePolicy(f,policy().replace("false","true").replace("SINGLE_HOLDER","UNRESTRICTED"));
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),
                AssignmentType.SECONDARY,START,null,1,2),true);
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),
                AssignmentType.PRIMARY,START,null,1,2),false);
    }

    @Test void unboundLegacyConflictCannotDisappearThroughAnInnerJoinOrInventedPin() throws Exception {
        var f=fixture(false);
        admin("INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at) VALUES (?,?,?,?,'SECONDARY',?::timestamptz,'ACTIVE',now(),now())",id(),f.employee(),f.units().get(0),f.position(),START.toString());
        prepare(create(f,id()),false);
    }

    @Test void activeHalfOpenIntervalsPermitAnExactlyAdjacentAppointment() throws Exception {
        var f=fixture(false);Instant boundary=Instant.now().plusSeconds(3600).truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        seedCreate(f,boundary);
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),
                AssignmentType.PRIMARY,boundary,null,1,1),true);
    }

    @Test void completeConflictBoundAccepts1024PeersAndRejectsThe1025thWithoutTruncation() throws Exception {
        var f=fixture(false);
        activatePolicy(f,policy().replace("false","true").replace("SINGLE_HOLDER","UNRESTRICTED")
                .replace("\"maxConcurrentPrimary\":1","\"maxConcurrentPrimary\":10000"));
        seedMany(f,1024);
        var command=new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),AssignmentType.PRIMARY,START,null,1,2);
        prepare(command,true);seedMany(f,1);prepare(command,false);
        try(var c=owner()) {
            var storage=new JdbcProtectedEmployeeAppointmentRepositoryAdapter(new JdbcTemplate(
                    new org.springframework.jdbc.datasource.SingleConnectionDataSource(c,true)));
            assertThrows(AccessDeniedException.class,()->storage.preview(runtimeCurrent(f.employee(),null,0,1)));
        }
    }

    @Test void scheduledEmployeeExpiryNarrowsThePhysicalWindowWithoutWaitingOrAnotherWriter() throws Exception {
        var f=fixture(false);var command=create(f,id());
        admin("UPDATE public.hidra_org_employee SET terminated_at=clock_timestamp()-interval '30 seconds' WHERE id=?",f.employee());
        try(var s=new Session(command)){
            s.backdateSeconds=60; // Mocked native sample only: deterministic physical deadline mechanics.
            s.tx.execute(status -> {
                var ticket=s.acquire();var sample=s.storage.contribute(ticket);
                assertEquals(ReferenceUse.CURRENT,s.storage.referenceUse(ticket,command,sample));
                s.storage.contributeParty(ticket);
                assertThrows(AccessDeniedException.class,() -> s.storage.prepareMutation(ticket,command));
                assertTrue(s.poisoned());assertThrows(AccessDeniedException.class,() -> s.storage.requireCurrent(ticket));
                status.setRollbackOnly();return null;
            });
        }
    }

    @Test void committedReplayDoesNotReevaluatePresentMutationPolicyOrOrganizationEligibility() throws Exception {
        var f=fixture();var command=correction(f,id(),1,f.units().get(1));commitCorrection(f,command);
        activatePolicy(f,policy());
        admin("UPDATE public.hidra_org_employee SET status='SUSPENDED' WHERE id=?",f.employee());
        admin("UPDATE public.hidra_org_position SET status='INACTIVE' WHERE id=?",f.position());
        prepare(command,true);
    }

    @Test void fabricatedForeignIssuerAndWrongCompanyPartyTicketsCannotQualifyStorage() throws Exception {
        var f=fixture(false);var command=create(f,id());
        for(int variant=0;variant<3;variant++)try(var s=new Session(command)){
            if(variant==0)s.partyTicketOverride=new OrganizationProtectedPartyEligibilityContract.Admission(){};
            if(variant==1)s.ticketOwnerOverride=new JdbcOrganizationProtectedPartyEligibilityAdapter(s.jdbc);
            if(variant==2)s.partyIdOverride=baseline.company();
            s.tx.execute(status -> {
                var ticket=qualified(s);
                assertEquals("Protected appointment admission unavailable",assertThrows(AccessDeniedException.class,
                        () -> s.storage.contributeParty(ticket)).getMessage());
                assertTrue(s.poisoned());status.setRollbackOnly();return null;
            });
        }
    }

    @Test void foreignThreadPartyConsumptionPoisonsTheOriginalPhysicalHolder() throws Exception {
        var f=fixture(false);var command=create(f,id());
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=qualified(s);var worker=java.util.concurrent.Executors.newSingleThreadExecutor();
            try {
                var failed=worker.submit(() -> assertThrows(AccessDeniedException.class,() -> s.storage.contributeParty(ticket)));
                assertNotNull(failed.get(5,java.util.concurrent.TimeUnit.SECONDS));
            }catch(Exception unexpected){throw new AssertionError(unexpected);}finally{worker.shutdownNow();}
            assertTrue(s.poisoned());verify(s.party,never()).read(any());status.setRollbackOnly();return null;
        });}
    }

    @Test void callerConsumedPartyScalarCannotReplaceTheStorageOwnedOneUseRead() throws Exception {
        var f=fixture(false);var command=create(f,id());
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=qualified(s);var scalar=s.party.read(s.partyTicket);
            assertEquals(f.company(),scalar.partyId());
            assertThrows(AccessDeniedException.class,() -> s.storage.contributeParty(ticket));
            assertTrue(s.poisoned());status.setRollbackOnly();return null;
        });}
    }

    @Test void independentSourceWriterCannotPassTheRetainedClassificationFence() throws Exception {
        var command=end(baseline,1);
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            assertEquals(ReferenceUse.RETAINED,s.storage.referenceUse(ticket,command,sample));
            try(var writer=owner()) {
                writer.setAutoCommit(false);
                assertEquals("55P03",assertThrows(SQLException.class,() -> execute(writer,
                        "LOCK TABLE public.hidra_org_employee_assignment IN ROW EXCLUSIVE MODE NOWAIT")).getSQLState());
                writer.rollback();
            } catch(SQLException failure){throw new AssertionError(failure);}
            assertEquals(ReferenceUse.RETAINED,s.storage.referenceUse(ticket,command,sample));
            status.setRollbackOnly();return null;
        });}
    }
}
