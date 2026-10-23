/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentConcurrencyPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-23
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Proves locked command classification and committed receipt replay before current source CAS.
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
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.adapter.JdbcProtectedEmployeeAppointmentRepositoryAdapter;
import dz.sh.hidra.modules.party.application.contract.organization.OrganizationProtectedPartyEligibilityContract;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
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

/**
 * Genuine committed PostgreSQL ledger and separated login mechanics. Identity's
 * issuer is explicitly mocked; the closed fixture definer acquires actual locks
 * only. No native PERMIT, cryptographic commit proof or guarded writer is claimed.
 */
@Testcontainers(disabledWithoutDocker = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeAppointmentConcurrencyPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16");
    private static final String PASSWORD="disposable-classification-fixture", CONFIG="classification_config";
    private static final Instant START=Instant.parse("2026-10-20T10:00:00Z");
    private static final String REASON="Change é\t\n retained";
    private static final JsonMapper JSON=JsonMapper.builder().build();
    private static final List<ProtectedRelationFence> KEYS=List.of(
            new ProtectedRelationFence("public.hidra_identity_org_commit_key_event",ProtectedRelationFence.Mode.ACCESS_SHARE),
            new ProtectedRelationFence("public.hidra_identity_org_commit_key_head",ProtectedRelationFence.Mode.SHARE),
            new ProtectedRelationFence("public.hidra_identity_org_commit_proof_use",ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
    private String partyType,unitType;
    private Fixture baseline,historical;
    private EmployeeAppointmentCommand.Correct originalCorrection;

    private record Fixture(String company,String employee,String assignment,String position,List<String> units,
                           String policy,String createRequest) { }

    private Connection owner() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
    }
    private void admin(String sql,Object... args) throws SQLException {
        try(var c=owner()){execute(c,sql,args);}
    }
    private static void execute(Connection c,String sql,Object... args) throws SQLException {
        try(var p=c.prepareStatement(sql)){for(int i=0;i<args.length;i++)p.setObject(i+1,args[i]);p.execute();}
    }
    private static String scalar(Connection c,String sql,Object... args) throws SQLException {
        try(var p=c.prepareStatement(sql)){
            for(int i=0;i<args.length;i++)p.setObject(i+1,args[i]);
            try(var r=p.executeQuery()){assertTrue(r.next());String value=r.getString(1);assertFalse(r.next());return value;}
        }
    }
    private static String id(){return UUID.randomUUID().toString();}
    private static String policy(){
        var rows=new java.util.LinkedHashMap<String,Object>();
        for(var a:AssignmentType.values()){
            var row=new java.util.LinkedHashMap<String,Boolean>();
            for(var b:AssignmentType.values())row.put(b.name(),false);
            rows.put(a.name(),row);
        }
        return JSON.writeValueAsString(Map.of("maxConcurrentPrimary",1,"positionCapacity","SINGLE_HOLDER","overlaps",rows));
    }

    @BeforeAll void migrateAndCommitHistoricalFixtures() throws Exception {
        Flyway.configure().dataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()).load().migrate();
        admin("CREATE ROLE "+CONFIG+" LOGIN PASSWORD '"+PASSWORD+"'");
        admin("GRANT USAGE ON SCHEMA public TO "+CONFIG);
        admin("GRANT EXECUTE ON FUNCTION public.hidra_org_activate_appointment_binding(text,text,text,bigint),public.hidra_org_activate_appointment_policy(text,text,bigint,jsonb),public.hidra_party_require_organization_configuration_reference(text) TO "+CONFIG);
        partyType=id();unitType=id();
        admin("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at) VALUES (?,?,'ACTIVE',now(),now())",partyType,partyType);
        admin("INSERT INTO public.hidra_org_unit_type(id,code,kind,active,created_at,updated_at) VALUES (?,?,'STATION',true,now(),now())",unitType,unitType);
        baseline=fixture();historical=fixture();
        originalCorrection=correction(historical,"original-correction",1,historical.units().get(1));
        commitCorrection(historical,originalCorrection);
        commitCorrection(historical,correction(historical,"later-correction",2,historical.units().get(2)));
        String reboundCompany=id();
        admin("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES (?,?,?,'Synthetic rebound company','DZ','ACTIVE',now(),now())",reboundCompany,reboundCompany,partyType);
        try(var config=DriverManager.getConnection(POSTGRES.getJdbcUrl(),CONFIG,PASSWORD)){
            scalar(config,"SELECT public.hidra_org_activate_appointment_binding(?,?,?,1)",
                    historical.units().get(0),historical.units().get(0),reboundCompany);
        }
        admin("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",historical.company());
    }

    private Fixture fixture() throws Exception {
        var f=new Fixture(id(),id(),id(),id(),List.of(id(),id(),id()),id(),id());
        admin("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES (?,?,?,'Synthetic company','DZ','ACTIVE',now(),now())",f.company(),f.company(),partyType);
        admin("INSERT INTO public.hidra_org_employee(id,employee_number,employee_type,status,created_at,updated_at) VALUES (?,?,'PERMANENT','ACTIVE',now(),now())",f.employee(),f.employee());
        admin("INSERT INTO public.hidra_org_position(id,code,level,status,created_at,updated_at) VALUES (?,?,'ENGINEER','ACTIVE',now(),now())",f.position(),f.position());
        for(var unit:f.units())admin("INSERT INTO public.hidra_org_unit(id,code,unit_type_id,status,valid_from,created_at,updated_at) VALUES (?,?,?,'ACTIVE',now()-interval '1 day',now(),now())",unit,unit,unitType);
        try(var config=DriverManager.getConnection(POSTGRES.getJdbcUrl(),CONFIG,PASSWORD)){
            config.setAutoCommit(false);
            for(var unit:f.units())scalar(config,"SELECT public.hidra_org_activate_appointment_binding(?,?,?,0)",unit,unit,f.company());
            scalar(config,"SELECT public.hidra_org_activate_appointment_policy(?,?,0,?::jsonb)",f.policy(),f.company(),policy());config.commit();
        }
        try(var c=owner()){
            c.setAutoCommit(false);String event=id();
            execute(c,"INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at,appointment_version,binding_id,binding_revision,policy_id,policy_revision,last_event_id) VALUES (?,?,?,?,'PRIMARY',?::timestamptz,'ACTIVE',transaction_timestamp(),transaction_timestamp(),1,?,1,?,1,?::uuid)",f.assignment(),f.employee(),f.units().get(0),f.position(),START.toString(),f.units().get(0),f.policy(),event);
            String command=JSON.writeValueAsString(Map.ofEntries(
                    Map.entry("kind","CREATE"),Map.entry("companyPartyId",f.company()),Map.entry("requestId",f.createRequest()),
                    Map.entry("employeeId",f.employee()),Map.entry("organizationUnitId",f.units().get(0)),Map.entry("positionId",f.position()),
                    Map.entry("assignmentType","PRIMARY"),Map.entry("validFrom",START.toString()),
                    Map.entry("expectedBindingRevision",1),Map.entry("expectedPolicyRevision",1)));
            command=scalar(c,"SELECT ?::jsonb || jsonb_build_object('validTo',NULL)",command);
            ledger(c,f,event,"CREATE",command,"null");c.commit();
        }
        return f;
    }

    private static EmployeeAppointmentCommand.Correct correction(Fixture f,String request,long version,String unit){
        return new EmployeeAppointmentCommand.Correct(request,f.company(),f.assignment(),version,unit,f.position(),
                AssignmentType.PRIMARY,AssignmentStatus.ACTIVE,START,null,1,1,REASON);
    }
    private void commitCorrection(Fixture f,EmployeeAppointmentCommand.Correct command) throws Exception {
        try(var c=owner()){
            c.setAutoCommit(false);
            String before=scalar(c,"SELECT public.hidra_org_appointment_snapshot(a)::text FROM public.hidra_org_employee_assignment a WHERE id=?",f.assignment());
            String event=id();
            execute(c,"UPDATE public.hidra_org_employee_assignment SET organization_unit_id=?,binding_id=?,appointment_version=appointment_version+1,last_event_id=?::uuid,updated_at=transaction_timestamp() WHERE id=?",command.correctedOrganizationUnitId(),command.correctedOrganizationUnitId(),event,f.assignment());
            String json=JSON.writeValueAsString(Map.ofEntries(Map.entry("kind","CORRECT"),Map.entry("requestId",command.requestId()),
                    Map.entry("companyPartyId",f.company()),Map.entry("assignmentId",f.assignment()),Map.entry("expectedVersion",command.expectedVersion()),
                    Map.entry("correctedOrganizationUnitId",command.correctedOrganizationUnitId()),Map.entry("correctedPositionId",f.position()),
                    Map.entry("correctedType","PRIMARY"),Map.entry("correctedStatus","ACTIVE"),Map.entry("correctedValidFrom",START.toString()),
                    Map.entry("expectedDestinationBindingRevision",1),Map.entry("expectedDestinationPolicyRevision",1),Map.entry("reason",REASON)));
            json=scalar(c,"SELECT ?::jsonb || jsonb_build_object('correctedValidTo',NULL)",json);
            ledger(c,f,event,"CORRECT",json,before);c.commit();
        }
    }
    private static void ledger(Connection c,Fixture f,String event,String kind,String command,String before) throws Exception {
        String body=scalar(c,"SELECT jsonb_build_object('command',?::jsonb,'before',?::jsonb,'after',public.hidra_org_appointment_snapshot(a),'successor',NULL)::text FROM public.hidra_org_employee_assignment a WHERE id=?",command,before,f.assignment());
        execute(c,"INSERT INTO public.hidra_org_appointment_event(event_id,operation,assignment_id,employee_id,company_party_id,policy_id,policy_revision,canonical_payload,command_sha256,actor_binding_id,actor_session_id,actor_user_id,sampled_at,valid_until,backend_pid,transaction_id) SELECT ?::uuid,?,?,?,?,?,1,convert_to(p::text,'UTF8'),encode(sha256(convert_to((p->'command')::text,'UTF8')),'hex'),gen_random_uuid(),gen_random_uuid(),'synthetic-actor',transaction_timestamp(),transaction_timestamp()+interval '1 hour',pg_backend_pid(),txid_current() FROM (SELECT ?::jsonb p) v",event,kind,f.assignment(),f.employee(),f.company(),f.policy(),body);
        execute(c,"INSERT INTO public.hidra_org_appointment_receipt(event_id,actor_user_id,scope_key,request_id,command_sha256,canonical_result,guarded_at,backend_pid,transaction_id) SELECT event_id,actor_user_id,encode(sha256(convert_to(jsonb_build_object('family','organization-appointment-v1','companyPartyId',company_party_id)::text,'UTF8')),'hex'),convert_from(canonical_payload,'UTF8')::jsonb->'command'->>'requestId',command_sha256,canonical_payload,sampled_at,backend_pid,transaction_id FROM public.hidra_org_appointment_event WHERE event_id=?::uuid",event);
        execute(c,"SET CONSTRAINTS ALL IMMEDIATE");
    }

    private final class Session implements AutoCloseable {
        final String role="classification_"+id().replace("-","");
        final OrganizationProtectedAppointmentAuthorizationContract identity=mock(OrganizationProtectedAppointmentAuthorizationContract.class);
        final Admission nativeTicket=mock(Admission.class);
        final DriverManagerDataSource source;
        final JdbcTemplate jdbc;
        final TransactionTemplate tx;
        final JdbcProtectedEmployeeAppointmentRepositoryAdapter storage;
        final EmployeeAppointmentCommand command;
        String actor="synthetic-actor";
        ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview previewOverride;
        Session(EmployeeAppointmentCommand command) throws Exception {
            this.command=command;
            admin("CREATE ROLE "+role+" LOGIN PASSWORD '"+PASSWORD+"'");admin("GRANT USAGE ON SCHEMA public TO "+role);
            admin("GRANT SELECT ON ALL TABLES IN SCHEMA public TO "+role);
            for(var table:List.of("employee","unit","position","appointment_binding_head","appointment_policy_head","appointment_lock_key","employee_assignment"))
                admin("GRANT UPDATE ON public.hidra_org_"+table+" TO "+role);
            var fences=new ArrayList<>(JdbcIdentityAuthorityFence.requiredRelationFences());fences.addAll(KEYS);
            fences.add(new ProtectedRelationFence("public.hidra_party_party",ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
            fences.add(new ProtectedRelationFence("public.hidra_party_eligibility_generation",ProtectedRelationFence.Mode.ACCESS_SHARE));
            StringBuilder locks=new StringBuilder("BEGIN ");
            for(var fence:fences)locks.append("LOCK TABLE ").append(fence.relation()).append(" IN ")
                    .append(switch(fence.mode()){case SHARE_ROW_EXCLUSIVE -> "SHARE ROW EXCLUSIVE";case ACCESS_SHARE -> "ACCESS SHARE";case SHARE -> "SHARE";})
                    .append(" MODE NOWAIT; ");
            admin("CREATE FUNCTION public.fixture_"+role+"() RETURNS void LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog AS $fixture$ "+locks+" END; $fixture$");
            admin("REVOKE ALL ON FUNCTION public.fixture_"+role+"() FROM PUBLIC");admin("GRANT EXECUTE ON FUNCTION public.fixture_"+role+"() TO "+role);
            source=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),role,PASSWORD);jdbc=new JdbcTemplate(source);
            tx=new TransactionTemplate(new DataSourceTransactionManager(source));tx.setIsolationLevel(Connection.TRANSACTION_READ_COMMITTED);
            storage=new JdbcProtectedEmployeeAppointmentRepositoryAdapter(jdbc,identity);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("synthetic",null,List.of()));
            when(identity.finalFences(nativeTicket)).thenReturn(JdbcIdentityAuthorityFence.requiredRelationFences());
            when(identity.commitProofFences(nativeTicket)).thenReturn(KEYS);
            when(identity.contribute(nativeTicket)).thenAnswer(call -> {
                var preview=previewOverride==null?storage.preview(command):previewOverride;
                var request=new Request(Action.valueOf(command.kind().name()),preview.companyUnits(),preview.employeeId());
                return jdbc.queryForObject("SELECT pg_backend_pid(),txid_current(),clock_timestamp()",(r,i) ->
                        new Contribution(request,request.requirements().stream().map(q -> new ResourceDecision(q,Decision.PERMIT)).toList(),
                                "synthetic-binding","synthetic-session",actor,r.getTimestamp(3).toInstant(),r.getTimestamp(3).toInstant().plusSeconds(60),r.getInt(1),r.getLong(2)));
            });
        }
        ProtectedEmployeeAppointmentRepositoryPort.Admission acquire(){
            jdbc.execute("SELECT public.fixture_"+role+"()");
            return storage.acquire(nativeTicket,List.of(mock(OrganizationProtectedPartyEligibilityContract.Admission.class)),
                    previewOverride==null?storage.preview(command):previewOverride);
        }
        boolean poisoned(){return ((ConnectionHolder)TransactionSynchronizationManager.getResource(source)).isRollbackOnly();}
        @Override public void close(){SecurityContextHolder.clearContext();}
    }

    private EmployeeAppointmentCommand.Create create(Fixture f,String request){
        return new EmployeeAppointmentCommand.Create(request,f.company(),f.employee(),f.units().get(0),f.position(),AssignmentType.PRIMARY,START,null,1,1);
    }
    private EmployeeAppointmentCommand.End end(Fixture f,long version){
        return new EmployeeAppointmentCommand.End(id(),f.company(),f.assignment(),version,START.plusSeconds(3600),REASON);
    }
    private void classify(EmployeeAppointmentCommand command,ReferenceUse expected) throws Exception {
        try(var s=new Session(command)){s.tx.execute(status -> {
            var ticket=s.acquire();var sample=s.storage.contribute(ticket);
            assertEquals(expected,s.storage.referenceUse(ticket,command,sample));
            assertEquals(expected,s.storage.referenceUse(ticket,command,sample));
            verify(s.identity,times(1)).contribute(s.nativeTicket);status.setRollbackOnly();return null;
        });}
    }

    @Test void freshCommandsClassifyCurrentOrRetainedWithoutCreatingLedgerRows() throws Exception {
        classify(create(baseline,id()),ReferenceUse.CURRENT);classify(end(baseline,1),ReferenceUse.RETAINED);
        classify(correction(baseline,id(),1,baseline.units().get(0)),ReferenceUse.RETAINED);
        classify(correction(baseline,id(),1,baseline.units().get(1)),ReferenceUse.CURRENT);
        classify(new EmployeeAppointmentCommand.Transfer(id(),baseline.company(),baseline.assignment(),1,
                baseline.units().get(1),baseline.position(),AssignmentType.ACTING,START.plusSeconds(3600),null,1,1,REASON),ReferenceUse.CURRENT);
        try(var c=owner()){assertEquals("4",scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_event"));}
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
