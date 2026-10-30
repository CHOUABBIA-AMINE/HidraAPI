/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentCurrentReadPostgresIntegrationTest
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
class EmployeeAppointmentCurrentReadPostgresIntegrationTest extends EmployeeAppointmentPostgresFixture {
    @Test void genuineNativeDenialDoesNotCreateProjectionEventReceiptOrKeys() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);
        admin("DELETE FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor);runtimeAuthenticate(actor);
        assertThrows(RuntimeException.class,()->runtimeService(runtimeEntityManager).create(create(f,id())));
        try(var c=owner()) {
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
        } finally {SecurityContextHolder.clearContext();}
    }

    @Test void genuineExecutionRollsBackProvisionalWritesWhenActualManagedFlushFails() throws Exception {
        var f=fixture(false);runtimeActor(f);var em=spy(runtimeEntityManager);
        doAnswer(call->{runtimeEntityManager.flush();throw new IllegalStateException("synthetic post-flush failure");}).when(em).flush();
        assertThrows(RuntimeException.class,()->runtimeService(em).create(create(f,id())));
        verify(em).flush();
        try(var c=owner()) {
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_employee_assignment WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",f.employee()));
            assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='EMPLOYEE' AND key_one=?",f.employee()));
        } finally {SecurityContextHolder.clearContext();}
    }

    @Test void genuineCompetingAppointmentRejectsWithoutPartialEffectsWhileWinnerCommits() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var authentication=SecurityContextHolder.getContext().getAuthentication();
        var winner=create(f,id());var loser=create(f,id());var winnerTrace=new RuntimeTrace();var loserTrace=new RuntimeTrace();
        var go=new java.util.concurrent.CountDownLatch(1);var ready=new java.util.concurrent.CountDownLatch(1);
        var worker=java.util.concurrent.Executors.newSingleThreadExecutor();
        try {
            var competitor=worker.submit(()->authenticated(authentication,()->{
                ready.countDown();assertTrue(go.await(30,java.util.concurrent.TimeUnit.SECONDS));
                var failure=assertThrows(ProtectedTransactionExecutor.ExecutionFailure.class,()->
                        tracedRuntime("COMPETING_CREATE",loserTrace,()->runtimeService(runtimeEntityManager).create(loser)));
                assertEquals(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT,failure.outcome());
                assertTrue(sqlState(failure,"55P03"));return failure.outcome();
            }));
            assertTrue(ready.await(5,java.util.concurrent.TimeUnit.SECONDS));
            var em=spy(runtimeEntityManager);
            doAnswer(call->{runtimeEntityManager.flush();go.countDown();
                assertEquals(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT,
                        competitor.get(650,java.util.concurrent.TimeUnit.MILLISECONDS));return null;}).when(em).flush();
            var receipt=tracedRuntime("WINNING_CREATE",winnerTrace,()->runtimeService(em).create(winner));
            assertEquals(winner.requestId(),receipt.requestId());winnerTrace.committed();loserTrace.rejected();verify(em).flush();
            assertEquals("1,1,1,1,3",runtimeEffects(f,actor));
            try(var c=owner()) {
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_lock_key WHERE kind='REQUEST' AND key_one=? AND key_three=?",actor,loser.requestId()));
                assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_receipt WHERE actor_user_id=? AND request_id=?",actor,loser.requestId()));
            }
        } finally {go.countDown();stopWorker(worker);}
    }

    @Test void genuineAuthorityFenceBoundsRevocationAttemptThenCommittedRevocationDeniesOldToken() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var trace=new RuntimeTrace();
        var go=new java.util.concurrent.CountDownLatch(1);var ready=new java.util.concurrent.CountDownLatch(1);
        var worker=java.util.concurrent.Executors.newSingleThreadExecutor();
        try {
            var writer=worker.submit(()->{
                try(var c=owner()) {
                    ready.countDown();assertTrue(go.await(30,java.util.concurrent.TimeUnit.SECONDS));c.setAutoCommit(false);
                    execute(c,"SET LOCAL lock_timeout='150ms'");execute(c,"SET LOCAL statement_timeout='500ms'");
                    long began=System.nanoTime();String state;
                    try {execute(c,"DELETE FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor);state="UNEXPECTED_SUCCESS";}
                    catch(SQLException failure){state=failure.getSQLState();if(!"55P03".equals(state))throw failure;}
                    finally {c.rollback();}
                    return new WriterObservation(state,System.nanoTime()-began);
                }
            });
            assertTrue(ready.await(5,java.util.concurrent.TimeUnit.SECONDS));
            var em=spy(runtimeEntityManager);
            doAnswer(call->{runtimeEntityManager.flush();go.countDown();var result=writer.get(650,java.util.concurrent.TimeUnit.MILLISECONDS);
                assertEquals("55P03",result.state());
                System.out.println("HAPI_778_CONTENTION_QUALIFICATION stage=REVOCATION_ATTEMPT sqlState=55P03 writerElapsedNs="+result.elapsedNs()+" operationallyAccepted=false");
                return null;}).when(em).flush();
            var receipt=tracedRuntime("CREATE_BEFORE_REVOCATION",trace,()->runtimeService(em).create(create(f,id())));
            trace.committed();verify(em).flush();assertEquals("1,1,1,1,3",runtimeEffects(f,actor));
            // Separate explicit action after known completion, never retry inside either operation.
            admin("DELETE FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor);
            try(var c=owner()){assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor));}
            var deniedTrace=new RuntimeTrace();String before=runtimeEffects(f,actor);var deniedEm=spy(runtimeEntityManager);
            // END is otherwise valid: a second CREATE could be rejected by overlap even with stale authority.
            var endCommand=new EmployeeAppointmentCommand.End(id(),f.company(),receipt.after().assignmentId(),1,START.plusSeconds(3600),REASON);
            String projection;
            try(var c=owner()){projection=scalar(c,"SELECT encode(sha256(convert_to(to_jsonb(a)::text,'UTF8')),'hex') FROM public.hidra_org_employee_assignment a WHERE id=?",receipt.after().assignmentId());}
            var failure=assertThrows(ProtectedTransactionExecutor.ExecutionFailure.class,()->tracedRuntime("OLD_TOKEN_AFTER_REVOCATION",deniedTrace,
                    ()->runtimeService(deniedEm).end(endCommand)));
            assertEquals(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT,failure.outcome());deniedTrace.rejected();
            verify(deniedEm,never()).flush();assertEquals(0,deniedTrace.guardBegan);assertFalse(sqlState(failure,"55P03"));
            assertTrue(deniedTrace.ended-deniedTrace.began<deniedTrace.workCeilingMillis*1_000_000L,"Denial must precede the work ceiling");
            assertEquals(before,runtimeEffects(f,actor));
            try(var c=owner()){assertEquals(projection,scalar(c,"SELECT encode(sha256(convert_to(to_jsonb(a)::text,'UTF8')),'hex') FROM public.hidra_org_employee_assignment a WHERE id=?",receipt.after().assignmentId()));}
        } finally {go.countDown();stopWorker(worker);}
    }

    @Test void genuineCommittedRevocationBeforeAdmissionRejectsWithoutAnyEffects() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var original=SecurityContextHolder.getContext().getAuthentication();
        admin("DELETE FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor);
        try(var c=owner()){assertEquals("0",scalar(c,"SELECT count(*) FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor));}
        var trace=new RuntimeTrace();var em=spy(runtimeEntityManager);assertEquals("0,0,0,0,0",runtimeEffects(f,actor));
        var failure=assertThrows(ProtectedTransactionExecutor.ExecutionFailure.class,()->tracedRuntime("REVOCATION_BEFORE_ADMISSION",trace,
                ()->runtimeService(em).create(create(f,id()))));
        assertSame(original,SecurityContextHolder.getContext().getAuthentication());
        assertEquals(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT,failure.outcome());trace.rejected();
        verify(em,never()).flush();assertEquals(0,trace.guardBegan);assertFalse(sqlState(failure,"55P03"));
        assertTrue(trace.ended-trace.began<trace.workCeilingMillis*1_000_000L,"Denial must precede the work ceiling");
        assertEquals("0,0,0,0,0",runtimeEffects(f,actor));
    }

    @Test void genuineNativeExpiryAtFinalDatabaseGuardRollsBackWhileWorkBudgetRemains() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var command=create(f,id());var trace=new RuntimeTrace(10000);
        Instant until=null;int grants=0;
        // One original persisted deadline for every actual scoped grant, before service preparation.
        try(var c=owner();var statement=c.prepareStatement("""
                WITH boundary AS MATERIALIZED (SELECT pg_catalog.clock_timestamp()+interval '8 seconds' AS u)
                UPDATE public.hidra_identity_user_permission_grant g SET valid_to=boundary.u
                  FROM boundary WHERE g.user_id=? RETURNING g.valid_to
                """)) {
            statement.setString(1,actor);
            try(var rows=statement.executeQuery()) {
                while(rows.next()){Instant value=rows.getTimestamp(1).toInstant();if(until==null)until=value;else assertEquals(until,value);grants++;}
            }
        }
        assertTrue(grants>0);assertNotNull(until);var fault=new NativeExpiryFault(until);var em=spy(runtimeEntityManager);
        assertNull(NATIVE_EXPIRY_FAULT.get());NATIVE_EXPIRY_FAULT.set(fault);
        try {
            var failure=assertThrows(ProtectedTransactionExecutor.ExecutionFailure.class,()->tracedRuntime("NATIVE_EXPIRY_FAULT",trace,
                    ()->runtimeService(em,runtimeExecutor).create(command)));
            assertEquals(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT,failure.outcome());trace.rejected();verify(em).flush();
            assertEquals(1,fault.guardAttempts);assertNotNull(fault.observedExpiry);assertEquals(until,fault.nativeUntil);
            assertFalse(fault.observedExpiry.isBefore(until));assertEquals("P0001",fault.guardSqlState);
            assertTrue(trace.guardBegan>=fault.dispatchedAt);assertEquals(0,trace.guardClosed);assertEquals(0,fault.postGuardStatements);
            assertTrue(trace.ended-trace.began<10_000_000_000L,"Complete failed call must leave work budget remaining");
            assertFalse(sqlState(failure,"57014"));assertFalse(sqlState(failure,"25P03"));
            System.out.println("HAPI_778_EXPIRY_QUALIFICATION nativeExpired=true guardSqlState=P0001 driverClockReads="+fault.clockReads
                    +" faultElapsedNs="+(fault.dispatchedAt-fault.began)
                    +" remainingWorkBudgetLowerBoundNs="+(10_000_000_000L-(fault.dispatchedAt-trace.began))
                    +" activeFault=true operationallyAccepted=false");
        } finally {NATIVE_EXPIRY_FAULT.remove();}
        assertEquals("0,0,0,0,0",runtimeEffects(f,actor));
    }

    @Test void genuineDevelopmentWorkCapRejectsAfterFlushWithoutWaitingForNativeExpiry() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var trace=new RuntimeTrace();var em=spy(runtimeEntityManager);
        doAnswer(call->{
            runtimeEntityManager.flush();assertTrue(trace.firstFence>0);
            long target=trace.firstFence+trace.workCeilingMillis*1_000_000L+50_000_000L;
            for(int attempt=0;attempt<400&&System.nanoTime()<target;attempt++)
                runtimeJdbc.queryForObject("SELECT true FROM pg_catalog.pg_sleep(0.025)",Boolean.class);
            assertTrue(System.nanoTime()>=target,"Bounded work-cap fault did not reach its target");
            assertTrue(Boolean.TRUE.equals(runtimeJdbc.queryForObject(
                    "SELECT valid_until>clock_timestamp() FROM public.hidra_org_appointment_event WHERE employee_id=?",Boolean.class,f.employee())),
                    "The actual provisional operation window must still be live at work-cap rejection");
            return null;
        }).when(em).flush();
        var failure=assertThrows(ProtectedTransactionExecutor.ExecutionFailure.class,()->tracedRuntime("DEVELOPMENT_WORK_CAP_FAULT",trace,
                ()->runtimeService(em).create(create(f,id()))));
        assertEquals(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT,failure.outcome());trace.rejected();verify(em).flush();
        assertInstanceOf(IllegalStateException.class,failure.getCause());
        assertTrue(failure.getCause().getMessage().contains("aggregate 7500ms work budget"));
        assertEquals(0,trace.guardBegan);assertEquals("0,0,0,0,0",runtimeEffects(f,actor));
        try(var c=owner()) {
            assertEquals("live",scalar(c,"SELECT CASE WHEN bool_and(valid_to>clock_timestamp()) THEN 'live' ELSE 'expired' END FROM public.hidra_identity_user_permission_grant WHERE user_id=?",actor));
        }
    }

    @Test void freshCommandsClassifyCurrentOrRetainedWithoutCreatingLedgerRows() throws Exception {
        classify(create(baseline,id()),ReferenceUse.CURRENT);classify(end(baseline,1),ReferenceUse.RETAINED);
        classify(correction(baseline,id(),1,baseline.units().get(0)),ReferenceUse.RETAINED);
        classify(correction(baseline,id(),1,baseline.units().get(1)),ReferenceUse.CURRENT);
        classify(new EmployeeAppointmentCommand.Transfer(id(),baseline.company(),baseline.assignment(),1,
                baseline.units().get(1),baseline.position(),AssignmentType.ACTING,START.plusSeconds(3600),null,1,1,REASON),ReferenceUse.CURRENT);
        try(var c=owner()){assertEquals("4",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event WHERE assignment_id IN (?,?)",baseline.assignment(),historical.assignment()));}
    }

    @Test void planningRequiresExactCurrentPolicyAndDestinationBindingRevisions() throws Exception {
        var f=fixture(false);activatePolicy(f,policy());prepare(create(f,id()),false);
        try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),CONFIG,PASSWORD)){
            scalar(c,"SELECT public.hidra_org_activate_appointment_binding(?,?,?,1)",f.units().get(0),f.units().get(0),f.company());
        }
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),AssignmentType.PRIMARY,START,null,1,2),false);
        prepare(new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),f.units().get(0),f.position(),AssignmentType.PRIMARY,START,null,2,2),true);
    }

    @Test void currentEmployeeUnitAndPositionEligibilityMustHoldAtTheNativeSample() throws Exception {
        for(int variant=0;variant<6;variant++){
            var f=fixture(false);
            switch(variant){
                case 0 -> admin("UPDATE public.hidra_org_employee SET status='SUSPENDED' WHERE id=?",f.employee());
                case 1 -> admin("UPDATE public.hidra_org_employee SET hired_at=clock_timestamp()+interval '1 hour' WHERE id=?",f.employee());
                case 2 -> admin("UPDATE public.hidra_org_employee SET terminated_at=clock_timestamp()-interval '1 minute' WHERE id=?",f.employee());
                case 3 -> admin("UPDATE public.hidra_org_unit SET status='INACTIVE' WHERE id=?",f.units().get(0));
                case 4 -> admin("UPDATE public.hidra_org_unit SET valid_from=clock_timestamp()+interval '1 hour' WHERE id=?",f.units().get(0));
                case 5 -> admin("UPDATE public.hidra_org_position SET status='INACTIVE' WHERE id=?",f.position());
                default -> throw new AssertionError();
            }
            prepare(create(f,id()),false);
        }
    }

    @Test void genuinePartyContributionIsConsumedOnceAndPrivatelyQualifiesCurrentPlanning() throws Exception {
        var f=fixture(false);var command=create(f,id());
        try(var s=new Session(command)){
            try(var reader=DriverManager.getConnection(POSTGRES.getJdbcUrl(),s.role,PASSWORD)){
                assertEquals("42501",assertThrows(SQLException.class,() -> scalar(reader,
                        "SELECT count(*) FROM public.hidra_identity_org_commit_key_head")).getSQLState());
            }
            s.tx.execute(status -> {
            var ticket=qualified(s);var eligibility=s.storage.contributeParty(ticket);
            assertEquals(f.company(),eligibility.partyId());
            assertEquals(s.jdbc.queryForObject("SELECT generation FROM public.hidra_party_eligibility_generation WHERE party_id=?",Long.class,f.company()),Long.valueOf(eligibility.generation()));
            assertEquals(s.jdbc.queryForObject("SELECT pg_backend_pid()",Integer.class),Integer.valueOf(eligibility.backendPid()));
            assertEquals(s.jdbc.queryForObject("SELECT txid_current()",Long.class),Long.valueOf(eligibility.transactionId()));
            assertDoesNotThrow(() -> s.storage.prepareMutation(ticket,command));
            assertDoesNotThrow(() -> s.storage.requireCurrent(ticket));
            verify(s.party,times(1)).read(s.partyTicket);
            assertThrows(AccessDeniedException.class,() -> s.storage.contributeParty(ticket));
            assertTrue(s.poisoned());assertThrows(AccessDeniedException.class,() -> s.storage.prepareMutation(ticket,command));
            status.setRollbackOnly();return null;
        });}
    }

    @Test void partyConsumptionRequiresFrozenCurrentClassificationAndPlanningRequiresItsPrivateSample() throws Exception {
        var f=fixture(false);var command=create(f,id());
        for(boolean classify:List.of(false,true))try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            if(classify){s.storage.referenceUse(ticket,command,sample);
                assertThrows(AccessDeniedException.class,() -> s.storage.prepareMutation(ticket,command));
            }else assertThrows(AccessDeniedException.class,() -> s.storage.contributeParty(ticket));
            verify(s.party,never()).read(any());assertTrue(s.poisoned());status.setRollbackOnly();return null;
        });}
    }

    @Test void actualSameTransactionPartyGenerationChangeInvalidatesTheRetainedCurrentSample() throws Exception {
        var f=fixture(false);var command=create(f,id());
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=qualified(s);var sample=s.storage.contributeParty(ticket);
            s.storage.prepareMutation(ticket,command);
            assertEquals(1,s.jdbc.update("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",f.company()));
            assertTrue(s.jdbc.queryForObject("SELECT generation FROM public.hidra_party_eligibility_generation WHERE party_id=?",Long.class,f.company())>sample.generation());
            assertThrows(AccessDeniedException.class,() -> s.storage.requireCurrent(ticket));
            assertTrue(s.poisoned());status.setRollbackOnly();return null;
        });}
    }

    @Test void retainedAndReplayPlanningNeverConsumesCurrentPartySelectability() throws Exception {
        for(var command:List.of(end(historical,3),originalCorrection))try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=qualified(s);assertDoesNotThrow(() -> s.storage.prepareMutation(ticket,command));
            verify(s.party,never()).read(any());verify(s.party,never()).requireCurrent(any());
            assertThrows(AccessDeniedException.class,() -> s.storage.contributeParty(ticket));
            verify(s.party,never()).read(any());assertTrue(s.poisoned());status.setRollbackOnly();return null;
        });}
    }

    @Test void genuineCurrentPagesUseCompleteNativeScopeSqlTiesCountsAndObservedReadCommit() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var service=runtimeService(runtimeEntityManager);
        activatePolicy(f,policy().replace("false","true").replace("SINGLE_HOLDER","UNRESTRICTED").replace("\"maxConcurrentPrimary\":1","\"maxConcurrentPrimary\":16"));
        var ids=new java.util.ArrayList<String>();
        for(int n=0;n<3;n++)ids.add(service.create(new EmployeeAppointmentCommand.Create(id(),f.company(),f.employee(),
                f.units().get(0),f.position(),AssignmentType.PRIMARY,START,null,1,2)).after().assignmentId());
        ids.sort(String::compareTo);String effects=runtimeEffects(f,actor);
        for(int page=0;page<3;page++) {
            var q=runtimeCurrent(f.employee(),null,page,1);var trace=new RuntimeTrace();
            var result=tracedRuntime("CURRENT_PAGE_"+page,trace,()->service.currentAssignments(q));trace.committed();
            assertEquals(3,result.totalElements());assertEquals(3,result.totalPages());assertEquals(page<2,result.hasNext());
            assertEquals(List.of(ids.get(page)),result.entries().stream().map(v->v.assignmentId()).toList());
        }
        var unit=service.currentAssignments(runtimeCurrent(null,f.units().get(0),0,100));assertEquals(3,unit.totalElements());
        assertEquals(ids,unit.entries().stream().map(v->v.assignmentId()).toList());
        var empty=new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Current(null,f.employee(),f.units().get(0),AssignmentStatus.ENDED,0,10,false);
        assertEquals(0,service.currentAssignments(empty).totalElements());
        var unscopedEmpty=new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Current(null,f.employee(),null,AssignmentStatus.ENDED,0,10,false);
        assertThrows(RuntimeException.class,()->service.currentAssignments(unscopedEmpty));
        assertEquals(effects,runtimeEffects(f,actor));assertEquals(RUNTIME_APP,runtimeJdbc.queryForObject("SELECT session_user",String.class));
    }

    @Test void genuineCurrentReadDeniesOutOfPageForeignOwnersBeforeReturningCounts() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var service=runtimeService(runtimeEntityManager);
        service.create(create(f,id()));var other=fixture(false);
        seedCreate(new Fixture(other.company(),f.employee(),id(),other.position(),other.units(),other.policy(),id()),null);
        String effects=runtimeEffects(f,actor);var trace=new RuntimeTrace();var em=spy(runtimeEntityManager);
        assertThrows(RuntimeException.class,()->tracedRuntime("CURRENT_FOREIGN_OUTSIDE_PAGE",trace,
                ()->runtimeService(em).currentAssignments(runtimeCurrent(f.employee(),null,0,1))));
        trace.rejected();verify(em,never()).flush();assertEquals(0,trace.guardBegan);assertEquals(effects,runtimeEffects(f,actor));
    }

    @Test void genuineCurrentPostFlushFailureRollsBackWithoutAnyReadResultOrEffects() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);runtimeService(runtimeEntityManager).create(create(f,id()));
        String effects=runtimeEffects(f,actor);var em=spy(runtimeEntityManager);var trace=new RuntimeTrace();
        doAnswer(call->{runtimeEntityManager.flush();throw new IllegalStateException("synthetic current-read post-flush fault");}).when(em).flush();
        assertThrows(RuntimeException.class,()->tracedRuntime("CURRENT_FLUSH_ROLLBACK",trace,
                ()->runtimeService(em).currentAssignments(runtimeCurrent(f.employee(),null,0,10))));
        trace.rejected();verify(em).flush();assertEquals(0,trace.guardBegan);assertEquals(effects,runtimeEffects(f,actor));
    }

    @Test void genuineCurrentNativeExpiryAtReadGuardRejectsWhileDevelopmentWorkTimeRemains() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);var service=runtimeService(runtimeEntityManager);service.create(create(f,id()));
        Instant until=null;int grants=0;
        try(var c=owner();var statement=c.prepareStatement("WITH t AS MATERIALIZED (SELECT clock_timestamp()+interval '5 seconds' u) UPDATE public.hidra_identity_user_permission_grant SET valid_to=t.u FROM t WHERE user_id=? RETURNING valid_to")) {
            statement.setString(1,actor);
            try(var rows=statement.executeQuery()) {
                while(rows.next()){Instant value=rows.getTimestamp(1).toInstant();if(until==null)until=value;else assertEquals(until,value);grants++;}
            }
        }
        assertNotNull(until);assertTrue(grants>0);
        // Retain the original real bearer; admission reads the actual shortened native grants.
        var fault=new NativeExpiryFault(until,true);var trace=new RuntimeTrace();String effects=runtimeEffects(f,actor);
        assertNull(NATIVE_EXPIRY_FAULT.get());NATIVE_EXPIRY_FAULT.set(fault);
        try {
            assertThrows(RuntimeException.class,()->tracedRuntime("CURRENT_NATIVE_EXPIRY",trace,
                    ()->service.currentAssignments(runtimeCurrent(f.employee(),null,0,10))));
        } finally {NATIVE_EXPIRY_FAULT.remove();}
        trace.rejected();assertEquals(1,fault.guardAttempts);assertEquals("P0001",fault.guardSqlState);
        assertNotNull(fault.observedExpiry);assertEquals(0,fault.postGuardStatements);
        assertTrue(fault.dispatchedAt-trace.began<7_500_000_000L);assertEquals(effects,runtimeEffects(f,actor));
    }

    @Test void currentPreviewRejectsUnboundRowsInsteadOfInventingOrHidingTheirCompany() throws Exception {
        var f=fixture(false);
        admin("INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at) VALUES (?,?,?,?,'PRIMARY',?::timestamptz,'ACTIVE',now(),now())",id(),f.employee(),f.units().get(0),f.position(),START.toString());
        try(var c=owner()) {
            var jdbc=new JdbcTemplate(new org.springframework.jdbc.datasource.SingleConnectionDataSource(c,true));
            var storage=new JdbcProtectedEmployeeAppointmentRepositoryAdapter(jdbc);
            for(String company:new String[]{null,f.company()}) {
                var q=new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Current(company,f.employee(),null,null,0,1,false);
                assertThrows(AccessDeniedException.class,()->storage.preview(q));
            }
        }
    }

    @Test void genuineCurrentEmployeeListOver100RequiresPaginationOnlyAfterCompleteAdmission() throws Exception {
        var f=fixture(false);String actor=runtimeActor(f);
        activatePolicy(f,policy().replace("false","true").replace("SINGLE_HOLDER","UNRESTRICTED")
                .replace("\"maxConcurrentPrimary\":1","\"maxConcurrentPrimary\":10000"));
        seedMany(f,101); // Owner-seeded SQL fixtures, not101 genuine native write calls.
        String effects=runtimeEffects(f,actor);var em=spy(runtimeEntityManager);var trace=new RuntimeTrace();
        var q=new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Current(null,f.employee(),null,null,0,100,true);
        var failure=assertThrows(ProtectedTransactionExecutor.ExecutionFailure.class,()->tracedRuntime("CURRENT_PAGINATION_REQUIRED",trace,
                ()->runtimeService(em).currentAssignments(q)));
        boolean typed=false;
        for(Throwable cause=failure;cause!=null;cause=cause.getCause())
            if(cause instanceof dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.PaginationRequired)typed=true;
        assertTrue(typed,"An admitted101-row list must report the safe pagination conflict");
        assertEquals(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT,failure.outcome());
        trace.rejected();verify(em,never()).flush();assertEquals(0,trace.guardBegan);assertEquals(effects,runtimeEffects(f,actor));
    }
}
