/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentLifecyclePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-22
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Verifies actual restricted configuration activation and immutable appointment storage from the full Flyway chain.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.CompanyUnit;
import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.adapter.JdbcProtectedEmployeeAppointmentRepositoryAdapter;
import java.time.Instant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.access.AccessDeniedException;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/** Actual PostgreSQL configuration proofs; native lifecycle writer tests remain pending. */
@Testcontainers(disabledWithoutDocker = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeAppointmentLifecyclePostgresIntegrationTest {
    private static final String CONFIG = "appointment_config", ORDINARY = "appointment_app", PUBLISHER = "appointment_publisher";
    private static final String PASSWORD = "disposable-synthetic-fixture";
    private static final String BIND = "public.hidra_org_activate_appointment_binding(text,text,text,bigint)";
    private static final String POLICY = "public.hidra_org_activate_appointment_policy(text,text,bigint,jsonb)";
    private static final String PARTY = "public.hidra_party_require_organization_configuration_reference(text)";
    private static final String MIGRATION = "src/main/resources/db/migration/V20261021_003__organization_appointment_ownership_lifecycle.sql";
    private static final String SUCCESSOR_MIGRATION = "src/main/resources/db/migration/V20261021_004__organization_appointment_configuration_successor.sql";
    private static final String PROJECTION_MIGRATION = "src/main/resources/db/migration/V20261022_001__organization_appointment_projection_integrity.sql";
    private static final String COMMAND_MIGRATION = "src/main/resources/db/migration/V20261022_002__organization_appointment_typed_command_integrity.sql";
    private static final List<String> TABLES = List.of("binding_revision", "binding_head", "policy_revision", "policy_head",
            "configuration_event", "lock_key", "event", "receipt");
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16");
    private String partyType, unitType;
    private String upgradeCompany, upgradeUnit, upgradeBinding, upgradePolicy;
    private long originalConfigurationChecksum;
    private String retainedLegacyId;
    private Projection retainedProjection;
    private UUID retainedEvent;
    private String retainedPayload;
    private long originalProjectionChecksum;

    @BeforeAll void migrateAndSeparatePrincipals() throws Exception {
        Flyway.configure().dataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())
                .target("20261021.003").load().migrate();
        originalConfigurationChecksum = count("SELECT checksum FROM public.flyway_schema_history WHERE script=?",
                Path.of(MIGRATION).getFileName().toString());
        for (String role : List.of(CONFIG, ORDINARY, PUBLISHER)) {
            admin("CREATE ROLE " + role + " LOGIN PASSWORD '" + PASSWORD + "'");
            admin("GRANT USAGE ON SCHEMA public TO " + role);
        }
        admin("GRANT EXECUTE ON FUNCTION " + BIND + "," + POLICY + "," + PARTY + " TO " + CONFIG);
        partyType = "party-type-" + UUID.randomUUID(); unitType = "unit-type-" + UUID.randomUUID();
        admin("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at) VALUES (?,?,'ACTIVE',now(),now())", partyType, partyType);
        admin("INSERT INTO public.hidra_org_unit_type(id,code,kind,active,created_at,updated_at) VALUES (?,?,'STATION',true,now(),now())", unitType, unitType);
        // Persist real revision-one facts under the already-applied003 routines.
        // The forward repair must preserve these rows, checksum and explicit ACLs.
        upgradeCompany = company("ACTIVE"); upgradeUnit = unit();
        upgradeBinding = "upgrade-binding-" + UUID.randomUUID();
        upgradePolicy = "upgrade-policy-" + UUID.randomUUID();
        try (var c = login(CONFIG)) {
            binding(c, upgradeBinding, upgradeUnit, upgradeCompany, 0);
            policy(c, upgradePolicy, upgradeCompany, 0, policyValue());
            c.commit();
        }
        String legacyEmployee = employee(), legacyPosition = position();
        retainedLegacyId = "legacy-" + UUID.randomUUID();
        admin("INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at) VALUES (?,?,?,?,'PRIMARY',now(),'ACTIVE',now(),now())",
                retainedLegacyId, legacyEmployee, upgradeUnit, legacyPosition);
        Flyway.configure().dataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())
                .target("20261022.001").load().migrate();
        originalProjectionChecksum = count("SELECT checksum FROM public.flyway_schema_history WHERE script=?",
                Path.of(PROJECTION_MIGRATION).getFileName().toString());
        retainedProjection = projection();
        try (var c = projectionTransaction()) {
            retainedEvent = UUID.randomUUID();
            insertProjection(c, retainedProjection, retainedEvent);
            String oldBody = payload(c, retainedProjection, "CREATE", "null", "null");
            oldBody = scalar(c, "SELECT jsonb_set(p,'{command}',jsonb_build_object('kind','CREATE','companyPartyId',p->'command'->'companyPartyId','requestId',p->'command'->'requestId'))::text FROM (SELECT ?::jsonb p) v", oldBody).toString();
            insertEvent(c, retainedProjection, retainedEvent, "CREATE", null, oldBody, false);
            insertReceipt(c, retainedEvent);
            retainedPayload = scalar(c, "SELECT encode(canonical_payload,'hex') FROM public.hidra_org_appointment_event WHERE event_id=?::uuid",
                    retainedEvent).toString();
            c.commit();
        }
        Flyway.configure().dataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword()).load().migrate();
        assertEquals(originalConfigurationChecksum, count("SELECT checksum FROM public.flyway_schema_history WHERE script=?",
                Path.of(MIGRATION).getFileName().toString()));
    }

    private Connection owner() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }
    private Connection login(String role) throws SQLException {
        var c = DriverManager.getConnection(POSTGRES.getJdbcUrl(), role, PASSWORD);
        c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); c.setAutoCommit(false); return c;
    }
    private static void execute(Connection c, String sql, Object... values) throws SQLException {
        try (var p = c.prepareStatement(sql)) { for (int i=0;i<values.length;i++) p.setObject(i+1,values[i]); p.execute(); }
    }
    private void admin(String sql, Object... values) throws SQLException { try (var c=owner()) { execute(c,sql,values); } }
    private static Object scalar(Connection c, String sql, Object... values) throws SQLException {
        try (var p=c.prepareStatement(sql)) {
            for (int i=0;i<values.length;i++) p.setObject(i+1,values[i]);
            try (var r=p.executeQuery()) { assertTrue(r.next()); Object value=r.getObject(1); assertFalse(r.next()); return value; }
        }
    }
    private Object catalog(String sql,Object... values) throws SQLException { try(var c=owner()) {return scalar(c,sql,values);} }
    private long count(String sql,Object... values) throws SQLException { return ((Number)catalog(sql,values)).longValue(); }
    private String company(String status) throws SQLException {
        String id="company-"+UUID.randomUUID();
        admin("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES (?,?,?,'Synthetic company','DZ',?,now(),now())",id,id,partyType,status);
        return id;
    }
    private String unit() throws SQLException {
        String id="unit-"+UUID.randomUUID();
        admin("INSERT INTO public.hidra_org_unit(id,code,unit_type_id,status,valid_from,created_at,updated_at) VALUES (?,?,?,'ACTIVE',now()-interval '1 day',now(),now())",id,id,unitType);
        return id;
    }
    private String employee() throws SQLException {
        String id = "employee-" + UUID.randomUUID();
        admin("INSERT INTO public.hidra_org_employee(id,employee_number,employee_type,status,created_at,updated_at) VALUES (?,?,'PERMANENT','ACTIVE',now(),now())", id, id);
        return id;
    }
    private String position() throws SQLException {
        String id = "position-" + UUID.randomUUID();
        admin("INSERT INTO public.hidra_org_position(id,code,level,status,created_at,updated_at) VALUES (?,?,'ENGINEER','ACTIVE',now(),now())", id, id);
        return id;
    }

    private record Projection(String id, String company, String unit, String employee, String position,
                              String binding, String policy) {}

    private Projection projection() throws SQLException {
        var f = new Projection("assignment-" + UUID.randomUUID(), company("ACTIVE"), unit(), employee(), position(),
                "binding-" + UUID.randomUUID(), "policy-" + UUID.randomUUID());
        try (var c = login(CONFIG)) {
            binding(c, f.binding(), f.unit(), f.company(), 0);
            policy(c, f.policy(), f.company(), 0, policyValue());
            c.commit();
        }
        return f;
    }

    private Connection projectionTransaction() throws SQLException {
        var c = owner(); c.setAutoCommit(false); return c;
    }

    private static void insertProjection(Connection c, Projection f, UUID event) throws SQLException {
        execute(c, "INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at,appointment_version,binding_id,binding_revision,policy_id,policy_revision,last_event_id) VALUES (?,?,?,?,'PRIMARY',transaction_timestamp()-interval '1 day','ACTIVE',transaction_timestamp(),transaction_timestamp(),1,?,1,?,1,?::uuid)",
                f.id(), f.employee(), f.unit(), f.position(), f.binding(), f.policy(), event);
    }

    private static String snapshot(Connection c, String id) throws SQLException {
        return scalar(c, "SELECT public.hidra_org_appointment_snapshot(a)::text FROM public.hidra_org_employee_assignment a WHERE id=?", id).toString();
    }

    private static String payload(Connection c, Projection f, String operation, String before, String successor) throws SQLException {
        return scalar(c, """
            WITH source AS (SELECT a, ?::text operation, ?::text company, ?::text request,
                                   ?::jsonb prior, ?::jsonb successor
                            FROM public.hidra_org_employee_assignment a WHERE id=?),
            shape AS (SELECT *,to_jsonb(a) after_row,prior->'assignment' before_row,
                                successor->'assignment' successor_row FROM source)
            SELECT jsonb_build_object('command',
                jsonb_build_object('kind',operation,'companyPartyId',company,'requestId',request) ||
                CASE operation
                WHEN 'CREATE' THEN jsonb_build_object(
                    'employeeId',after_row->'employee_id','organizationUnitId',after_row->'organization_unit_id',
                    'positionId',after_row->'position_id','assignmentType',after_row->'assignment_type',
                    'validFrom',after_row->'valid_from','validTo',after_row->'valid_to',
                    'expectedBindingRevision',after_row->'binding_revision','expectedPolicyRevision',after_row->'policy_revision')
                WHEN 'END' THEN jsonb_build_object(
                    'assignmentId',after_row->'id','expectedVersion',before_row->'appointment_version',
                    'endAt',after_row->'valid_to','reason','Synthetic change')
                WHEN 'TRANSFER' THEN jsonb_build_object(
                    'assignmentId',after_row->'id','expectedVersion',before_row->'appointment_version',
                    'destinationOrganizationUnitId',successor_row->'organization_unit_id','destinationPositionId',successor_row->'position_id',
                    'destinationType',successor_row->'assignment_type','effectiveAt',successor_row->'valid_from',
                    'successorValidTo',successor_row->'valid_to','expectedDestinationBindingRevision',successor_row->'binding_revision',
                    'expectedDestinationPolicyRevision',successor_row->'policy_revision','reason','Synthetic change')
                WHEN 'CORRECT' THEN jsonb_build_object(
                    'assignmentId',after_row->'id','expectedVersion',before_row->'appointment_version',
                    'correctedOrganizationUnitId',after_row->'organization_unit_id','correctedPositionId',after_row->'position_id',
                    'correctedType',after_row->'assignment_type','correctedStatus',after_row->'status',
                    'correctedValidFrom',after_row->'valid_from','correctedValidTo',after_row->'valid_to',
                    'expectedDestinationBindingRevision',after_row->'binding_revision',
                    'expectedDestinationPolicyRevision',after_row->'policy_revision','reason','Synthetic change')
                END,
                'before',prior,'after',public.hidra_org_appointment_snapshot(a),'successor',successor)::text FROM shape
            """, operation, f.company(), "request-" + UUID.randomUUID(), before, successor, f.id()).toString();
    }

    private static void insertEvent(Connection c, Projection f, UUID event, String operation, String successorId,
                                    String payload, boolean wrongDigest) throws SQLException {
        execute(c, "INSERT INTO public.hidra_org_appointment_event(event_id,operation,assignment_id,successor_id,employee_id,company_party_id,policy_id,policy_revision,canonical_payload,command_sha256,actor_binding_id,actor_session_id,actor_user_id,sampled_at,valid_until,backend_pid,transaction_id) SELECT ?::uuid,?::text,?::text,?::text,?::text,?::text,?::text,1,convert_to(p::text,'UTF8'),CASE WHEN ?::boolean THEN repeat('0',64) ELSE encode(sha256(convert_to((p->'command')::text,'UTF8')),'hex') END,?::uuid,?::uuid,'synthetic-actor',transaction_timestamp(),transaction_timestamp()+interval '1 hour',pg_backend_pid(),txid_current() FROM (SELECT ?::jsonb p) v",
                event, operation, f.id(), successorId, f.employee(), f.company(), f.policy(), wrongDigest,
                UUID.randomUUID(), UUID.randomUUID(), payload);
    }

    private static void insertReceipt(Connection c, UUID event) throws SQLException {
        execute(c, "INSERT INTO public.hidra_org_appointment_receipt(event_id,actor_user_id,scope_key,request_id,command_sha256,canonical_result,guarded_at,backend_pid,transaction_id) SELECT event_id,actor_user_id,repeat('a',64),(convert_from(canonical_payload,'UTF8')::jsonb)->'command'->>'requestId',command_sha256,canonical_payload,sampled_at,backend_pid,transaction_id FROM public.hidra_org_appointment_event WHERE event_id=?::uuid", event);
    }

    /** Owner-authored structural fixture only, never a native authorization proof. */
    private static UUID completeCreate(Connection c, Projection f) throws SQLException {
        UUID event = UUID.randomUUID();
        insertProjection(c, f, event);
        insertEvent(c, f, event, "CREATE", null, payload(c, f, "CREATE", "null", "null"), false);
        insertReceipt(c, event);
        execute(c, "SET CONSTRAINTS ALL IMMEDIATE");
        execute(c, "SET CONSTRAINTS ALL DEFERRED");
        return event;
    }
    private static Object binding(Connection c,String id,String unit,String company,long expected) throws SQLException {
        return scalar(c,"SELECT public.hidra_org_activate_appointment_binding(?::text,?::text,?::text,?::bigint)",id,unit,company,expected);
    }
    private static Object policy(Connection c,String id,String company,long expected,String value) throws SQLException {
        return scalar(c,"SELECT public.hidra_org_activate_appointment_policy(?::text,?::text,?::bigint,?::jsonb)",id,company,expected,value);
    }
    private static String policyValue() {
        return "{\"maxConcurrentPrimary\":1,\"positionCapacity\":\"SINGLE_HOLDER\",\"overlaps\":{" +
                "\"PRIMARY\":{\"PRIMARY\":false,\"SECONDARY\":false,\"TEMPORARY\":false,\"ACTING\":false}," +
                "\"SECONDARY\":{\"PRIMARY\":false,\"SECONDARY\":false,\"TEMPORARY\":false,\"ACTING\":false}," +
                "\"TEMPORARY\":{\"PRIMARY\":false,\"SECONDARY\":false,\"TEMPORARY\":false,\"ACTING\":false}," +
                "\"ACTING\":{\"PRIMARY\":false,\"SECONDARY\":false,\"TEMPORARY\":false,\"ACTING\":false}}}";
    }
    private static void state(String expected,org.junit.jupiter.api.function.Executable operation) {
        assertEquals(expected,assertThrows(SQLException.class,operation).getSQLState());
    }

    @Test void fullFreshFlywayInventoryHasEightOwnedTablesAndNoActivatedDefaults() throws Exception {
        Flyway.configure().dataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword()).load().validate();
        try(var files=Files.list(Path.of("src/main/resources/db/migration"))) {
            assertEquals(files.filter(p->p.toString().endsWith(".sql")).count(),
                    count("SELECT count(*) FROM public.flyway_schema_history WHERE success AND type='SQL'"));
        }
        for(String table:TABLES) assertEquals(1,count("SELECT count(*) FROM pg_class c JOIN pg_namespace n ON n.oid=c.relnamespace WHERE n.nspname='public' AND c.relname=? AND c.relkind='r' AND c.relpersistence='p' AND NOT c.relrowsecurity","hidra_org_appointment_"+table));
        // No configuration is silently created by the migration. Ignore test-created explicit facts.
        assertEquals(0,count("SELECT count(*) FROM public.hidra_org_appointment_configuration_event WHERE database_admin<>?",CONFIG));
        // Exactly one explicit pre-upgrade fixture is retained, never a migration default.
        assertEquals(1,count("SELECT count(*) FROM public.hidra_org_appointment_event"));
        assertEquals(1,count("SELECT count(*) FROM public.hidra_org_appointment_receipt"));
        assertEquals(1,count("SELECT count(*) FROM public.hidra_org_appointment_event WHERE event_id=?::uuid",retainedEvent));
    }

    @Test void restrictedActivationPinsActualAdminCompanyAndPhysicalCoordinates() throws Exception {
        String company=company("ACTIVE"), unit=unit(), id="binding-"+UUID.randomUUID(); Object event;
        try(var c=login(CONFIG)) {
            assertEquals(CONFIG,scalar(c,"SELECT session_user"));
            event=binding(c,id,unit,company,0); c.commit();
        }
        assertEquals(1,count("SELECT count(*) FROM public.hidra_org_appointment_binding_revision r JOIN public.hidra_org_appointment_binding_head h USING(binding_id,revision,organization_unit_id,company_party_id) JOIN public.hidra_org_appointment_configuration_event e ON e.event_id=h.last_event_id WHERE r.binding_id=? AND r.company_party_id=? AND r.database_admin=? AND e.event_id=?::uuid AND r.backend_pid=e.backend_pid AND r.transaction_id=e.transaction_id AND e.after_payload=to_jsonb(r) AND e.before_payload IS NULL",id,company,CONFIG,event));
    }

    @Test void policyActivationRequiresCompleteExplicitSymmetricMatrixAndRetainsDigest() throws Exception {
        String company=company("ACTIVE"), id="policy-"+UUID.randomUUID();
        try(var c=login(CONFIG)) {policy(c,id,company,0,policyValue());c.commit();}
        assertEquals(1,count("SELECT count(*) FROM public.hidra_org_appointment_policy_revision WHERE policy_id=? AND canonical_payload=convert_to(policy::text,'UTF8') AND sha256=encode(sha256(canonical_payload),'hex') AND revision=1 AND database_admin=?",id,CONFIG));
        for(String invalid:List.of("{}",policyValue().replace("\"maxConcurrentPrimary\":1","\"maxConcurrentPrimary\":0"),
                policyValue().replace("\"SINGLE_HOLDER\"","\"IMPLICIT_DEFAULT\""),
                policyValue().replaceFirst("\"ACTING\":false","\"ACTING\":true"),
                policyValue().replace("\"SECONDARY\":false,", ""))) {
            try(var c=login(CONFIG)) {state("23514",()->policy(c,id,company,1,invalid));c.rollback();}
        }
        assertEquals(1,count("SELECT count(*) FROM public.hidra_org_appointment_policy_revision WHERE policy_id=?",id));
    }

    @Test void bindingReactivationKeepsOriginalRevisionAndStrictSuccessorCas() throws Exception {
        String oldCompany=company("ACTIVE"), newCompany=company("ACTIVE"), unit=unit(), id="binding-"+UUID.randomUUID();
        try(var c=login(CONFIG)) {binding(c,id,unit,oldCompany,0);c.commit();}
        try(var c=login(CONFIG)) {binding(c,id,unit,newCompany,1);c.commit();}
        assertEquals(oldCompany,catalog("SELECT company_party_id FROM public.hidra_org_appointment_binding_revision WHERE binding_id=? AND revision=1",id));
        assertEquals(newCompany,catalog("SELECT company_party_id FROM public.hidra_org_appointment_binding_head WHERE binding_id=? AND revision=2 AND generation=2",id));
        try(var c=login(CONFIG)) {state("40001",()->binding(c,id,unit,oldCompany,0));c.rollback();}
        assertEquals(2,count("SELECT count(*) FROM public.hidra_org_appointment_configuration_event WHERE identity_id=?",id));
    }

    @Test void policyRevisionsAreAtomicAndCannotChangeTheirCompanyOrIdentity() throws Exception {
        String company=company("ACTIVE"), otherCompany=company("ACTIVE"), id="policy-"+UUID.randomUUID();
        try(var c=login(CONFIG)) {policy(c,id,company,0,policyValue());c.commit();}
        try(var c=login(CONFIG)) {policy(c,id,company,1,policyValue().replace("\"maxConcurrentPrimary\":1","\"maxConcurrentPrimary\":2"));c.commit();}
        try(var c=login(CONFIG)) {state("40001",()->policy(c,id,otherCompany,0,policyValue()));c.rollback();}
        try(var c=login(CONFIG)) {state("40001",()->policy(c,"another-policy",company,2,policyValue()));c.rollback();}
        assertEquals(2,count("SELECT count(*) FROM public.hidra_org_appointment_policy_revision WHERE policy_id=?",id));
    }

    @Test void ordinaryAndPublisherCannotActivateDiscoverOrMutateConfiguration() throws Exception {
        String company=company("ACTIVE"), unit=unit();
        for(String role:List.of(ORDINARY,PUBLISHER)) for(String selector:List.of(company,"missing-company")) {
            try(var c=login(role)) {state("42501",()->binding(c,"binding",unit,selector,0));c.rollback();}
            try(var c=login(role)) {state("42501",()->policy(c,"policy",selector,0,policyValue()));c.rollback();}
            for(String table:TABLES) try(var c=login(role)) {
                state("42501",()->scalar(c,"SELECT count(*) FROM public.hidra_org_appointment_"+table)); c.rollback();
            }
        }
    }

    @Test void configurationLoginHasNoDirectStorageWriteOrPartyTableAuthority() throws Exception {
        for(String table:TABLES) assertEquals(Boolean.FALSE,catalog("SELECT has_table_privilege(?,?,'INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')",CONFIG,"public.hidra_org_appointment_"+table));
        assertEquals(Boolean.FALSE,catalog("SELECT has_table_privilege(?,?,'SELECT,INSERT,UPDATE,DELETE')",CONFIG,"public.hidra_party_party"));
        try(var c=login(CONFIG)) {state("42501",()->execute(c,"UPDATE public.hidra_org_appointment_binding_head SET revision=revision WHERE false"));c.rollback();}
    }

    @Test void publicExecuteOrMisgrantedTableWriteCannotBecomeConfigurationAuthority() throws Exception {
        String company=company("ACTIVE"),unit=unit();
        admin("GRANT EXECUTE ON FUNCTION "+BIND+" TO PUBLIC");
        try {
            for(String role:List.of(CONFIG,ORDINARY,PUBLISHER)) try(var c=login(role)) {
                state("23514",()->binding(c,"binding",unit,company,0));c.rollback();
            }
        } finally {admin("REVOKE EXECUTE ON FUNCTION "+BIND+" FROM PUBLIC");}
        admin("GRANT UPDATE ON public.hidra_org_appointment_binding_head TO "+CONFIG);
        try(var c=login(CONFIG)) {state("23514",()->binding(c,"binding",unit,company,0));c.rollback();}
        finally {admin("REVOKE UPDATE ON public.hidra_org_appointment_binding_head FROM "+CONFIG);}
    }

    @Test void inactiveMissingOrMalformedCompanyAndUnitCannotCreateFacts() throws Exception {
        String inactive=company("SUSPENDED"),active=company("ACTIVE"),unit=unit();
        for(String company:List.of(inactive,"missing-company"," "+active,active+"\u00a0")) try(var c=login(CONFIG)) {
            state("23514",()->binding(c,"binding",unit,company,0));c.rollback();
        }
        try(var c=login(CONFIG)) {state("23514",()->binding(c,"binding","missing-unit",active,0));c.rollback();}
        admin("UPDATE public.hidra_org_unit SET valid_to=now()-interval '1 second' WHERE id=?",unit);
        try(var c=login(CONFIG)) {state("23514",()->binding(c,"binding",unit,active,0));c.rollback();}
    }

    @Test void transactionRollbackRemovesRevisionHeadAndAuditTogether() throws Exception {
        String company=company("ACTIVE"),unit=unit(),id="rollback-"+UUID.randomUUID();
        try(var c=login(CONFIG)) {
            binding(c,id,unit,company,0);
            state("23514",()->policy(c,"rollback-policy",company,0,"{}"));c.rollback();
        }
        assertEquals(0,count("SELECT count(*) FROM public.hidra_org_appointment_binding_revision WHERE binding_id=?",id));
        assertEquals(0,count("SELECT count(*) FROM public.hidra_org_appointment_binding_head WHERE binding_id=?",id));
        assertEquals(0,count("SELECT count(*) FROM public.hidra_org_appointment_configuration_event WHERE identity_id=?",id));
    }

    @Test void immutableRowsAndHeadsRejectZeroRowUpdatesDeletesAndTruncate() throws Exception {
        for(String table:List.of("binding_revision","policy_revision","configuration_event","lock_key","event","receipt")) {
            String column=table.equals("lock_key")?"key_one":table.endsWith("revision")?"revision":"event_id";
            for(String sql:List.of("UPDATE public.hidra_org_appointment_"+table+" SET "+column+"="+column+" WHERE false",
                    "DELETE FROM public.hidra_org_appointment_"+table+" WHERE false",
                    "TRUNCATE public.hidra_org_appointment_"+table+" CASCADE")) {
                try(var c=owner()) {state("23514",()->execute(c,sql));}
            }
        }
        for(String table:List.of("binding_head","policy_head")) {
            try(var c=owner()) {state("23514",()->execute(c,"DELETE FROM public.hidra_org_appointment_"+table+" WHERE false"));}
        }
    }

    @Test void disabledOrReplacedStorageGuardFailsConfigurationBeforePublication() throws Exception {
        String company=company("ACTIVE"),unit=unit();
        admin("ALTER TABLE public.hidra_org_appointment_binding_revision DISABLE TRIGGER appointment_binding_revision_immutable");
        try(var c=login(CONFIG)) {state("23514",()->binding(c,"binding",unit,company,0));c.rollback();}
        finally {admin("ALTER TABLE public.hidra_org_appointment_binding_revision ENABLE TRIGGER appointment_binding_revision_immutable");}
        String body=(String)catalog("SELECT prosrc FROM pg_proc WHERE oid='public.hidra_org_appointment_configuration_immutable()'::regprocedure");
        admin("CREATE OR REPLACE FUNCTION public.hidra_org_appointment_configuration_immutable() RETURNS trigger LANGUAGE plpgsql SET search_path=pg_catalog AS 'BEGIN RETURN NULL; END'");
        try(var c=login(CONFIG)) {state("23514",()->binding(c,"binding",unit,company,0));c.rollback();}
        finally {admin("CREATE OR REPLACE FUNCTION public.hidra_org_appointment_configuration_immutable() RETURNS trigger LANGUAGE plpgsql SET search_path=pg_catalog AS '"+body.replace("'","''")+"'");}
    }

    @Test void independentConnectionActivationContendsWithoutDeadlockAndThenRejectsStaleCas() throws Exception {
        String company=company("ACTIVE"),unit=unit(),id="race-"+UUID.randomUUID();
        try(var first=login(CONFIG);var second=login(CONFIG)) {
            binding(first,id,unit,company,0);
            state("55P03",()->binding(second,id,unit,company,0));second.rollback();
            first.commit();
            state("40001",()->binding(second,id,unit,company,0));second.rollback();
        }
        assertEquals(1,count("SELECT count(*) FROM public.hidra_org_appointment_configuration_event WHERE identity_id=?",id));
    }

    @Test void appliedRevisionOneConfigurationSurvivesForwardSuccessorRepair() throws Exception {
        assertEquals(originalConfigurationChecksum, count("SELECT checksum FROM public.flyway_schema_history WHERE script=?",
                Path.of(MIGRATION).getFileName().toString()));
        assertEquals(1, count("SELECT count(*) FROM public.flyway_schema_history WHERE script=? AND success",
                Path.of(SUCCESSOR_MIGRATION).getFileName().toString()));
        assertEquals(1, count("SELECT revision FROM public.hidra_org_appointment_binding_head WHERE binding_id=?", upgradeBinding));
        assertEquals(1, count("SELECT revision FROM public.hidra_org_appointment_policy_head WHERE policy_id=?", upgradePolicy));
        try (var c = login(CONFIG)) {
            binding(c, upgradeBinding, upgradeUnit, upgradeCompany, 1);
            policy(c, upgradePolicy, upgradeCompany, 1, policyValue());
            c.commit();
        }
        assertEquals(2, count("SELECT revision FROM public.hidra_org_appointment_binding_head WHERE binding_id=?", upgradeBinding));
        assertEquals(2, count("SELECT revision FROM public.hidra_org_appointment_policy_head WHERE policy_id=?", upgradePolicy));
        assertEquals(2, count("SELECT count(*) FROM public.hidra_org_appointment_binding_revision WHERE binding_id=?", upgradeBinding));
        assertEquals(2, count("SELECT count(*) FROM public.hidra_org_appointment_policy_revision WHERE policy_id=?", upgradePolicy));
        assertEquals(4, count("SELECT count(*) FROM public.hidra_org_appointment_configuration_event WHERE identity_id IN (?,?)",
                upgradeBinding, upgradePolicy));
        try (var c = login(CONFIG)) {
            state("40001", () -> binding(c, upgradeBinding, upgradeUnit, upgradeCompany, 1));
            c.rollback();
        }
    }

    @Test void projectionUpgradeRetainsLegacyRowsWithoutInventingPins() throws Exception {
        assertEquals(1, count("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE id=? AND appointment_version IS NULL AND binding_id IS NULL AND binding_revision IS NULL AND policy_id IS NULL AND policy_revision IS NULL AND last_event_id IS NULL", retainedLegacyId));
        assertEquals(1, count("SELECT count(*) FROM public.flyway_schema_history WHERE script=? AND success", Path.of(PROJECTION_MIGRATION).getFileName().toString()));
        try (var c = projectionTransaction()) {
            state("23514", () -> execute(c, "UPDATE public.hidra_org_employee_assignment SET appointment_version=1,binding_id=?,binding_revision=1,policy_id=?,policy_revision=1,last_event_id=?::uuid WHERE id=?",
                    upgradeBinding, upgradePolicy, UUID.randomUUID(), retainedLegacyId));
            c.rollback();
        }
    }

    @Test void projectionRejectsPartialMetadataAndNonInitialVersions() throws Exception {
        var f = projection();
        for (String column : List.of("appointment_version", "binding_id", "binding_revision", "policy_id", "policy_revision", "last_event_id")) {
            String value = column.endsWith("_id") ? column.equals("last_event_id") ? "gen_random_uuid()" : "'unresolved'" : "1";
            try (var c = projectionTransaction()) {
                state("23514", () -> execute(c, "INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at," + column + ") VALUES (?,?,?,?,'PRIMARY',now(),'ACTIVE',now(),now()," + value + ")", f.id(), f.employee(), f.unit(), f.position()));
                c.rollback();
            }
        }
        try (var c = projectionTransaction()) {
            state("23514", () -> execute(c, "INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at,appointment_version,binding_id,binding_revision,policy_id,policy_revision,last_event_id) VALUES (?,?,?,?,'PRIMARY',now(),'ACTIVE',now(),now(),2,?,1,?,1,?::uuid)", f.id(), f.employee(), f.unit(), f.position(), f.binding(), f.policy(), UUID.randomUUID()));
            c.rollback();
        }
    }

    @Test void legacyInsertWithOrdinaryLoginNeedsNoPrivateHelperExecutePrivilege() throws Exception {
        var f = projection();
        assertEquals(Boolean.FALSE, catalog("SELECT has_function_privilege(?, 'public.hidra_org_appointment_exact_id(text)','EXECUTE')", ORDINARY));
        admin("GRANT INSERT ON public.hidra_org_employee_assignment TO " + ORDINARY);
        try (var c = login(ORDINARY)) {
            execute(c, "INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at) VALUES (?,?,?,?,'PRIMARY',now(),'ACTIVE',now(),now())", f.id(), f.employee(), f.unit(), f.position());
            execute(c, "SET CONSTRAINTS ALL IMMEDIATE");
            c.rollback();
        } finally { admin("REVOKE INSERT ON public.hidra_org_employee_assignment FROM " + ORDINARY); }
        assertEquals(0, count("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE id=?", f.id()));
    }

    @Test void protectedProjectionNeedsRealRevisionReferencesAndMatchingUnit() throws Exception {
        var f = projection();
        for (String badColumn : List.of("binding_revision", "policy_revision")) try (var c = projectionTransaction()) {
            insertProjection(c, f, UUID.randomUUID());
            // A valid version transition still cannot refer to an absent immutable revision.
            state("23503", () -> execute(c, "UPDATE public.hidra_org_employee_assignment SET " + badColumn + "=999,appointment_version=2,last_event_id=?::uuid WHERE id=?", UUID.randomUUID(), f.id()));
            c.rollback();
        }
        try (var c = projectionTransaction()) {
            var wrongUnit = new Projection(f.id(), f.company(), unit(), f.employee(), f.position(), f.binding(), f.policy());
            UUID event = UUID.randomUUID();
            insertProjection(c, wrongUnit, event);
            insertEvent(c, wrongUnit, event, "CREATE", null, payload(c, wrongUnit, "CREATE", "null", "null"), false);
            insertReceipt(c, event);
            state("23514", () -> execute(c, "SET CONSTRAINTS ALL IMMEDIATE"));
            c.rollback();
        }
    }

    @Test void committingSourceWithoutEventRollsBackThePhysicalProjection() throws Exception {
        var f = projection();
        try (var c = projectionTransaction()) {
            insertProjection(c, f, UUID.randomUUID());
            assertTrue(assertThrows(SQLException.class, c::commit).getSQLState().startsWith("23"));
            c.rollback();
        }
        assertEquals(0, count("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE id=?", f.id()));
    }

    @Test void committingEventWithoutReceiptRollsBackSourceAndEvent() throws Exception {
        var f = projection(); UUID event = UUID.randomUUID();
        try (var c = projectionTransaction()) {
            insertProjection(c, f, event);
            insertEvent(c, f, event, "CREATE", null, payload(c, f, "CREATE", "null", "null"), false);
            state("23514", c::commit);
            c.rollback();
        }
        assertEquals(0, count("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE id=?", f.id()));
        assertEquals(0, count("SELECT count(*) FROM public.hidra_org_appointment_event WHERE event_id=?::uuid", event));
    }

    @Test void eventCannotSubstituteSnapshotCommandDigestOrPhysicalTransaction() throws Exception {
        var f = projection();
        for (String fault : List.of("snapshot", "digest", "transaction")) try (var c = projectionTransaction()) {
            UUID event = UUID.randomUUID(); insertProjection(c, f, event);
            String body = payload(c, f, "CREATE", "null", "null");
            if (fault.equals("snapshot")) body = scalar(c, "SELECT jsonb_set(?::jsonb,'{after,assignment,employee_id}',to_jsonb('substituted'::text))::text", body).toString();
            if (fault.equals("transaction")) {
                execute(c, "INSERT INTO public.hidra_org_appointment_event(event_id,operation,assignment_id,employee_id,company_party_id,policy_id,policy_revision,canonical_payload,command_sha256,actor_binding_id,actor_session_id,actor_user_id,sampled_at,valid_until,backend_pid,transaction_id) SELECT ?::uuid,'CREATE',?::text,?::text,?::text,?::text,1,convert_to(p::text,'UTF8'),encode(sha256(convert_to((p->'command')::text,'UTF8')),'hex'),gen_random_uuid(),gen_random_uuid(),'synthetic-actor',transaction_timestamp(),transaction_timestamp()+interval '1 hour',pg_backend_pid(),txid_current()+1 FROM (SELECT ?::jsonb p) v", event, f.id(), f.employee(), f.company(), f.policy(), body);
            } else insertEvent(c, f, event, "CREATE", null, body, fault.equals("digest"));
            insertReceipt(c, event);
            state("23514", () -> execute(c, "SET CONSTRAINTS ALL IMMEDIATE"));
            c.rollback();
            assertEquals(0, count("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE id=?", f.id()));
            assertEquals(0, count("SELECT count(*) FROM public.hidra_org_appointment_receipt WHERE event_id=?::uuid", event));
        }
    }

    @Test void receiptMustMatchActorResultAndExclusiveGuardWindow() throws Exception {
        var f = projection();
        for (String fault : List.of("actor", "result", "expiry")) try (var c = projectionTransaction()) {
            UUID event = UUID.randomUUID(); insertProjection(c, f, event);
            insertEvent(c, f, event, "CREATE", null, payload(c, f, "CREATE", "null", "null"), false);
            String actor = fault.equals("actor") ? "'another-actor'" : "actor_user_id";
            String result = fault.equals("result") ? "convert_to('{\"substituted\":true}','UTF8')" : "canonical_payload";
            String time = fault.equals("expiry") ? "valid_until" : "sampled_at";
            execute(c, "INSERT INTO public.hidra_org_appointment_receipt(event_id,actor_user_id,scope_key,request_id,command_sha256,canonical_result,guarded_at,backend_pid,transaction_id) SELECT event_id," + actor + ",repeat('a',64),(convert_from(canonical_payload,'UTF8')::jsonb)->'command'->>'requestId',command_sha256," + result + "," + time + ",backend_pid,transaction_id FROM public.hidra_org_appointment_event WHERE event_id=?::uuid", event);
            state("23514", () -> execute(c, "SET CONSTRAINTS ALL IMMEDIATE"));
            c.rollback();
        }
    }

    @Test void orphanEventForgedBeforeAndIncompleteTransferCannotCommit() throws Exception {
        var f = projection();
        try (var c = projectionTransaction()) {
            var savepoint = c.setSavepoint(); UUID event = UUID.randomUUID();
            insertProjection(c, f, event);
            String body = payload(c, f, "CREATE", "null", "null");
            c.rollback(savepoint);
            insertEvent(c, f, event, "CREATE", null, body, false); insertReceipt(c, event);
            state("23514", c::commit); c.rollback();
            assertEquals(0, count("SELECT count(*) FROM public.hidra_org_appointment_event WHERE event_id=?::uuid", event));
        }
        for (String operation : List.of("END", "TRANSFER")) try (var c = projectionTransaction()) {
            completeCreate(c, f);
            String before = snapshot(c, f.id()); UUID event = UUID.randomUUID();
            execute(c, "UPDATE public.hidra_org_employee_assignment SET appointment_version=2,last_event_id=?::uuid,valid_to=transaction_timestamp(),status='ENDED' WHERE id=?", event, f.id());
            if (operation.equals("END")) before = scalar(c, "SELECT jsonb_set(?::jsonb,'{assignment,employee_id}',to_jsonb('forged-before'::text))::text", before).toString();
            insertEvent(c, f, event, operation, operation.equals("TRANSFER") ? "missing-successor" : null,
                    payload(c, f, operation, before, "null"), false);
            insertReceipt(c, event);
            state("23514", c::commit); c.rollback();
            assertEquals(0, count("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE id=?", f.id()));
            assertEquals(0, count("SELECT count(*) FROM public.hidra_org_appointment_receipt WHERE event_id=?::uuid", event));
        }
    }

    @Test void protectedIdentityVersionAndOwnershipCannotBeResetThroughRawSql() throws Exception {
        var f = projection();
        for (String change : List.of("appointment_version=appointment_version", "appointment_version=appointment_version+2",
                "appointment_version=2,employee_id='substituted'", "appointment_version=2,id='substituted'",
                "appointment_version=2,created_at=created_at-interval '1 second'",
                "appointment_version=NULL,binding_id=NULL,binding_revision=NULL,policy_id=NULL,policy_revision=NULL,last_event_id=NULL")) {
            try (var c = projectionTransaction()) {
                completeCreate(c, f);
                state("23514", () -> execute(c, "UPDATE public.hidra_org_employee_assignment SET " + change + " WHERE id=?", f.id()));
                c.rollback();
            }
        }
        try (var c = projectionTransaction()) {
            completeCreate(c, f);
            state("23514", () -> execute(c, "DELETE FROM public.hidra_org_employee_assignment WHERE id=?", f.id()));
            c.rollback();
        }
        try (var c = projectionTransaction()) {
            state("23514", () -> execute(c, "TRUNCATE public.hidra_org_employee_assignment CASCADE"));
            c.rollback();
        }
    }

    @Test void completeCreateEndCorrectionAndTransferPassStructuralProofThenRollbackTogether() throws Exception {
        var f = projection();
        for (String operation : List.of("CREATE", "END", "CORRECT", "TRANSFER")) try (var c = projectionTransaction()) {
            UUID first = completeCreate(c, f), event = first;
            if (!operation.equals("CREATE")) {
                String before = snapshot(c, f.id()); event = UUID.randomUUID();
                String change = operation.equals("CORRECT") ? "assignment_type='ACTING'" : "valid_to=transaction_timestamp(),status='ENDED'";
                execute(c, "UPDATE public.hidra_org_employee_assignment SET appointment_version=2,last_event_id=?::uuid," + change + " WHERE id=?", event, f.id());
                String successorId = null, successor = "null";
                if (operation.equals("TRANSFER")) {
                    successorId = "successor-" + UUID.randomUUID();
                    execute(c, "INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,valid_to,status,created_at,updated_at,appointment_version,binding_id,binding_revision,policy_id,policy_revision,last_event_id) SELECT ?::varchar,employee_id,organization_unit_id,position_id,assignment_type,valid_to,NULL,'ACTIVE',updated_at,updated_at,1,binding_id,binding_revision,policy_id,policy_revision,last_event_id FROM public.hidra_org_employee_assignment WHERE id=?", successorId, f.id());
                    successor = snapshot(c, successorId);
                }
                insertEvent(c, f, event, operation, successorId, payload(c, f, operation, before, successor), false);
                insertReceipt(c, event);
                execute(c, "SET CONSTRAINTS ALL IMMEDIATE");
                assertEquals(2L, ((Number)scalar(c, "SELECT appointment_version FROM public.hidra_org_employee_assignment WHERE id=?", f.id())).longValue());
                assertEquals(before, scalar(c, "SELECT (convert_from(canonical_payload,'UTF8')::jsonb->'before')::text FROM public.hidra_org_appointment_event WHERE event_id=?::uuid", event));
            }
            c.rollback();
            assertEquals(0, count("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE id=? OR last_event_id=?::uuid", f.id(), event));
            assertEquals(0, count("SELECT count(*) FROM public.hidra_org_appointment_event WHERE assignment_id=?", f.id()));
            assertEquals(0, count("SELECT count(*) FROM public.hidra_org_appointment_receipt WHERE event_id=?::uuid OR event_id=?::uuid", first, event));
        }
    }

    @Test void sameUnitCorrectionRetainsItsPinAndCrossCompanyCorrectionFails() throws Exception {
        var f = projection(); var destination = projection();
        try (var c = login(CONFIG)) { binding(c, f.binding(), f.unit(), f.company(), 1); c.commit(); }
        for (boolean crossCompany : List.of(false, true)) try (var c = projectionTransaction()) {
            completeCreate(c, f);
            String before = snapshot(c, f.id()); UUID event = UUID.randomUUID();
            Projection result = f;
            if (crossCompany) {
                execute(c, "UPDATE public.hidra_org_employee_assignment SET appointment_version=2,last_event_id=?::uuid,organization_unit_id=?,position_id=?,binding_id=?,policy_id=? WHERE id=?",
                        event, destination.unit(), destination.position(), destination.binding(), destination.policy(), f.id());
                result = new Projection(f.id(), destination.company(), destination.unit(), f.employee(), destination.position(), destination.binding(), destination.policy());
            } else execute(c, "UPDATE public.hidra_org_employee_assignment SET appointment_version=2,last_event_id=?::uuid,binding_revision=2 WHERE id=?", event, f.id());
            insertEvent(c, result, event, "CORRECT", null, payload(c, result, "CORRECT", before, "null"), false);
            insertReceipt(c, event);
            state("23514", c::commit); c.rollback();
            assertEquals(0, count("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE id=?", f.id()));
        }
    }

    private record Proposal(UUID event, String successorId, String body) {}

    private static Proposal proposal(Connection c, Projection f, String operation) throws SQLException {
        if (operation.equals("CREATE")) {
            UUID event = UUID.randomUUID();
            insertProjection(c, f, event);
            return new Proposal(event, null, payload(c, f, operation, "null", "null"));
        }
        completeCreate(c, f);
        String before = snapshot(c, f.id());
        UUID event = UUID.randomUUID();
        String change = operation.equals("CORRECT") ? "assignment_type='ACTING'" : "valid_to=transaction_timestamp(),status='ENDED'";
        execute(c, "UPDATE public.hidra_org_employee_assignment SET appointment_version=2,last_event_id=?::uuid," + change + " WHERE id=?", event, f.id());
        String successorId = null, successor = "null";
        if (operation.equals("TRANSFER")) {
            successorId = "successor-" + UUID.randomUUID();
            execute(c, "INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,valid_to,status,created_at,updated_at,appointment_version,binding_id,binding_revision,policy_id,policy_revision,last_event_id) SELECT ?::varchar,employee_id,organization_unit_id,position_id,assignment_type,valid_to,NULL,'ACTIVE',updated_at,updated_at,1,binding_id,binding_revision,policy_id,policy_revision,last_event_id FROM public.hidra_org_employee_assignment WHERE id=?", successorId, f.id());
            successor = snapshot(c, successorId);
        }
        return new Proposal(event, successorId, payload(c, f, operation, before, successor));
    }

    private static String replaceCommand(Connection c, String body, String key, String json) throws SQLException {
        return scalar(c, "SELECT jsonb_set(?::jsonb,ARRAY['command',?::text],?::jsonb)::text", body, key, json).toString();
    }

    private void rejectsCommandValue(String operation, String key, String json) throws Exception {
        var f = projection();
        try (var c = projectionTransaction()) {
            var p = proposal(c, f, operation);
            String bad = replaceCommand(c, p.body(), key, json);
            // Recompute a valid digest and matching receipt: hashing a forged command
            // must not make it describe the real projection or immutable prior state.
            insertEvent(c, f, p.event(), operation, p.successorId(), bad, false);
            if (key.equals("requestId")) {
                // Probe the new typed-command check before the receipt's existing
                // varchar/exact-ID constraint could reject the same bad selector.
                state("23514", () -> execute(c, "SET CONSTRAINTS appointment_command_complete IMMEDIATE"));
            } else {
                insertReceipt(c, p.event());
                state("23514", c::commit);
            }
            c.rollback();
            assertEquals(0, count("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE id=? OR last_event_id=?::uuid", f.id(), p.event()));
            assertEquals(0, count("SELECT count(*) FROM public.hidra_org_appointment_event WHERE assignment_id=?", f.id()));
            assertEquals(0, count("SELECT count(*) FROM public.hidra_org_appointment_receipt WHERE event_id=?::uuid", p.event()));
        }
    }

    @Test void typedCommandUpgradePreservesPriorChecksumsAndImmutableHistoricalBytes() throws Exception {
        assertEquals(originalProjectionChecksum, count("SELECT checksum FROM public.flyway_schema_history WHERE script=?",
                Path.of(PROJECTION_MIGRATION).getFileName().toString()));
        assertEquals(1, count("SELECT count(*) FROM public.flyway_schema_history WHERE script=? AND success",
                Path.of(COMMAND_MIGRATION).getFileName().toString()));
        assertEquals(retainedPayload, catalog("SELECT encode(canonical_payload,'hex') FROM public.hidra_org_appointment_event WHERE event_id=?::uuid", retainedEvent));
        assertEquals(retainedPayload, catalog("SELECT encode(canonical_result,'hex') FROM public.hidra_org_appointment_receipt WHERE event_id=?::uuid", retainedEvent));
        assertEquals(3, count("SELECT count(*) FROM public.hidra_org_appointment_event e CROSS JOIN LATERAL jsonb_object_keys(convert_from(e.canonical_payload,'UTF8')::jsonb->'command') k WHERE event_id=?::uuid", retainedEvent));
        assertEquals(1, count("SELECT appointment_version FROM public.hidra_org_employee_assignment WHERE id=?", retainedProjection.id()));
    }

    @Test void createCommandCannotSubstituteEmployeeUnitPositionOrTypeEvenWithNewDigest() throws Exception {
        for (String key : List.of("employeeId", "organizationUnitId", "positionId", "assignmentType"))
            rejectsCommandValue("CREATE", key, "\"substituted\"");
    }

    @Test void typedCommandRequiresExactFieldMembershipAndNoMissingNullableFields() throws Exception {
        rejectsCommandValue("CREATE", "unexpected", "true");
        var f = projection();
        for (String key : List.of("validTo", "employeeId", "expectedPolicyRevision")) try (var c = projectionTransaction()) {
            var p = proposal(c, f, "CREATE");
            String bad = scalar(c, "SELECT (?::jsonb #- ARRAY['command',?::text])::text", p.body(), key).toString();
            insertEvent(c, f, p.event(), "CREATE", null, bad, false);
            insertReceipt(c, p.event());
            state("23514", c::commit);
            c.rollback();
        }
        assertEquals(0, count("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE id=?", f.id()));
    }

    @Test void createCommandPinsBindingAndPolicyRevisionsWithoutCoercion() throws Exception {
        for (String key : List.of("expectedBindingRevision", "expectedPolicyRevision"))
            for (String json : List.of("2", "\"1\"", "1.0", "null", "true", "9223372036854775808"))
                rejectsCommandValue("CREATE", key, json);
    }

    @Test void endCommandPinsTheObservedSourceIdentityVersionAndEndingBoundary() throws Exception {
        rejectsCommandValue("END", "assignmentId", "\"another-assignment\"");
        for (String json : List.of("2", "\"1\"", "1.0", "0", "null"))
            rejectsCommandValue("END", "expectedVersion", json);
        rejectsCommandValue("END", "endAt", "\"2000-01-01T00:00:00Z\"");
        rejectsCommandValue("END", "endAt", "null");
    }

    @Test void transferCommandPinsEveryDestinationValueAndVersion() throws Exception {
        for (String key : List.of("destinationOrganizationUnitId", "destinationPositionId", "destinationType"))
            rejectsCommandValue("TRANSFER", key, "\"substituted\"");
        for (String key : List.of("expectedVersion", "expectedDestinationBindingRevision", "expectedDestinationPolicyRevision"))
            rejectsCommandValue("TRANSFER", key, "2");
        rejectsCommandValue("TRANSFER", "effectiveAt", "\"2000-01-01T00:00:00Z\"");
        rejectsCommandValue("TRANSFER", "successorValidTo", "\"2100-01-01T00:00:00Z\"");
    }

    @Test void correctionCommandPinsTargetStatusIntervalAndRevisions() throws Exception {
        for (String key : List.of("assignmentId", "correctedOrganizationUnitId", "correctedPositionId", "correctedType", "correctedStatus"))
            rejectsCommandValue("CORRECT", key, "\"substituted\"");
        for (String key : List.of("expectedVersion", "expectedDestinationBindingRevision", "expectedDestinationPolicyRevision"))
            rejectsCommandValue("CORRECT", key, "2");
        rejectsCommandValue("CORRECT", "correctedValidFrom", "\"2000-01-01T00:00:00Z\"");
        rejectsCommandValue("CORRECT", "correctedValidTo", "\"2100-01-01T00:00:00Z\"");
    }

    @Test void commandReasonsRequireUncoercedNonblankBoundedPreservedText() throws Exception {
        for (String json : List.of("null", "42", "false", "\"\"", "\"   \"", "\"\\t\\n\"", "\"\\u2005\"",
                "\"bad\\rcontrol\"", "\"" + "x".repeat(2001) + "\"",
                "\"" + new String(Character.toChars(0x1f600)).repeat(1001) + "\""))
            rejectsCommandValue("CORRECT", "reason", json);
        var f = projection();
        try (var c = projectionTransaction()) {
            var p = proposal(c, f, "CORRECT");
            String body = replaceCommand(c, p.body(), "reason", "\" retained\\tline\\ntext \"");
            insertEvent(c, f, p.event(), "CORRECT", null, body, false); insertReceipt(c, p.event());
            execute(c, "SET CONSTRAINTS ALL IMMEDIATE");
            assertEquals(" retained\tline\ntext ", scalar(c, "SELECT convert_from(canonical_payload,'UTF8')::jsonb->'command'->>'reason' FROM public.hidra_org_appointment_event WHERE event_id=?::uuid", p.event()));
            c.rollback();
        }
    }

    @Test void commandTimesRejectImplicitZonesInvalidDatesAndSubmicrosecondPrecision() throws Exception {
        for (String json : List.of("null", "123", "\"infinity\"", "\"2026-10-22\"",
                "\"2026-10-22T10:00:00\"", "\"2026-02-30T10:00:00Z\"",
                "\"2026-10-22T10:00:00.0000001Z\"", "\"2026-10-22T10:00:00.0000000Z\""))
            rejectsCommandValue("CREATE", "validFrom", json);
        try (var c = owner()) {
            assertEquals(Boolean.TRUE, scalar(c, "SELECT public.hidra_org_appointment_command_time_matches(?::jsonb,?::jsonb)",
                    "\"2026-10-22T11:00:00+01:00\"", "\"2026-10-22T10:00:00+00:00\""));
            assertEquals(Boolean.TRUE, scalar(c, "SELECT public.hidra_org_appointment_command_time_matches('null'::jsonb,'null'::jsonb)"));
            assertEquals(Boolean.FALSE, scalar(c, "SELECT public.hidra_org_appointment_command_time_matches(NULL,'null'::jsonb)"));
        }
    }

    @Test void commandIdentifierLengthsUseJavaUtf16BoundsWithoutTrimmingOrAliases() throws Exception {
        rejectsCommandValue("CREATE", "requestId", "\" leading\"");
        rejectsCommandValue("CREATE", "requestId", "\"trailing \"");
        rejectsCommandValue("CREATE", "requestId", "\"" + "x".repeat(81) + "\"");
        // 41 supplementary characters fit SQL varchar(80) but exceed Java's 80 UTF-16 units.
        String supplementary = new String(Character.toChars(0x1f600));
        rejectsCommandValue("CREATE", "requestId", "\"" + supplementary.repeat(41) + "\"");
        var f = projection();
        try (var c = projectionTransaction()) {
            var p = proposal(c, f, "CREATE");
            String body = replaceCommand(c, p.body(), "requestId", "\"" + supplementary.repeat(40) + "\"");
            insertEvent(c, f, p.event(), "CREATE", null, body, false); insertReceipt(c, p.event());
            execute(c, "SET CONSTRAINTS ALL IMMEDIATE");
            c.rollback();
        }
    }

    @Test void typedCommandFunctionsAndDeferredTriggerMatchRestrictedPhysicalCatalog() throws Exception {
        String source = Files.readString(Path.of(COMMAND_MIGRATION));
        var tags = List.of("command_time", "typed_command");
        var signatures = List.of("public.hidra_org_appointment_command_time_matches(jsonb,jsonb)",
                "public.hidra_org_appointment_typed_command_complete()");
        for (int i = 0; i < tags.size(); i++) {
            String body = source.split(java.util.regex.Pattern.quote("AS $" + tags.get(i) + "$"), 2)[1]
                    .split(java.util.regex.Pattern.quote("$" + tags.get(i) + "$;"), 2)[0];
            assertEquals(body, catalog("SELECT prosrc FROM pg_proc WHERE oid=?::regprocedure", signatures.get(i)));
            assertEquals(Boolean.FALSE, catalog("SELECT prosecdef FROM pg_proc WHERE oid=?::regprocedure", signatures.get(i)));
            assertEquals(1, count("SELECT count(*) FROM pg_proc WHERE oid=?::regprocedure AND 'search_path=pg_catalog'=ANY(proconfig) AND EXISTS(SELECT 1 FROM unnest(proconfig) x WHERE lower(x)='timezone=utc')", signatures.get(i)));
            for (String role : List.of(ORDINARY, PUBLISHER, CONFIG))
                assertEquals(Boolean.FALSE, catalog("SELECT has_function_privilege(?,?,'EXECUTE')", role, signatures.get(i)));
            assertEquals(0, count("SELECT count(*) FROM pg_proc p CROSS JOIN LATERAL aclexplode(coalesce(p.proacl,acldefault('f',p.proowner))) a WHERE p.oid=?::regprocedure AND a.grantee=0 AND a.privilege_type='EXECUTE'", signatures.get(i)));
        }
        assertEquals(1, count("SELECT count(*) FROM pg_trigger WHERE tgrelid='public.hidra_org_appointment_event'::regclass AND tgname='appointment_command_complete' AND tgdeferrable AND tginitdeferred AND tgenabled='O' AND tgfoid='public.hidra_org_appointment_typed_command_complete()'::regprocedure AND tgqual IS NULL"));
    }

    @Test void projectionFunctionsConstraintsAndDeferredTriggersHaveActualCatalogShape() throws Exception {
        String source = Files.readString(Path.of(PROJECTION_MIGRATION));
        var tags = List.of("projection_transition", "projection_snapshot", "projection_complete", "ledger_complete");
        var signatures = List.of("hidra_org_appointment_projection_transition()", "hidra_org_appointment_snapshot(public.hidra_org_employee_assignment)",
                "hidra_org_appointment_projection_complete()", "hidra_org_appointment_ledger_complete()");
        for (int i = 0; i < tags.size(); i++) {
            String body = source.split(java.util.regex.Pattern.quote("AS $" + tags.get(i) + "$"), 2)[1].split(java.util.regex.Pattern.quote("$" + tags.get(i) + "$;"), 2)[0];
            assertEquals(body, catalog("SELECT prosrc FROM pg_proc WHERE oid=?::regprocedure", "public." + signatures.get(i)));
            assertEquals(0, count("SELECT count(*) FROM pg_proc p CROSS JOIN LATERAL aclexplode(coalesce(p.proacl,acldefault('f',p.proowner))) a WHERE p.oid=?::regprocedure AND a.grantee=0 AND a.privilege_type='EXECUTE'", "public." + signatures.get(i)));
        }
        assertEquals(3, count("SELECT count(*) FROM pg_trigger WHERE tgname IN ('appointment_projection_complete','appointment_event_complete','appointment_receipt_complete') AND tgdeferrable AND tginitdeferred AND tgenabled='O' AND tgqual IS NULL"));
        assertEquals(5, count("SELECT count(*) FROM pg_constraint WHERE conrelid='public.hidra_org_employee_assignment'::regclass AND conname IN ('appointment_metadata_complete','appointment_protected_values','appointment_binding_revision_fk','appointment_policy_revision_fk','appointment_last_event_fk') AND convalidated"));
        for (String role : List.of(ORDINARY, PUBLISHER)) {
            assertEquals(Boolean.FALSE, catalog("SELECT has_table_privilege(?, 'public.hidra_org_appointment_event','INSERT,UPDATE,DELETE,TRUNCATE')", role));
            assertEquals(Boolean.FALSE, catalog("SELECT has_table_privilege(?, 'public.hidra_org_appointment_receipt','INSERT,UPDATE,DELETE,TRUNCATE')", role));
        }
    }

    @Test void authoredRoutineBodiesAndCatalogPrivilegesMatchInstalledStorageContract() throws Exception {
        String source=Files.readString(Path.of(MIGRATION));
        String successorSource=Files.readString(Path.of(SUCCESSOR_MIGRATION));
        var tags=List.of("exact","policy","immutable","context","locks","binding","activate_policy","complete","head");
        var signatures=List.of("hidra_org_appointment_exact_id(text)","hidra_org_appointment_policy_valid(jsonb)",
                "hidra_org_appointment_configuration_immutable()","hidra_org_appointment_configuration_context(text)",
                "hidra_org_appointment_configuration_locks()","hidra_org_activate_appointment_binding(text,text,text,bigint)",
                "hidra_org_activate_appointment_policy(text,text,bigint,jsonb)",
                "hidra_org_appointment_configuration_complete()","hidra_org_appointment_head_transition()");
        for(int i=0;i<tags.size();i++) {
            String owningSource=List.of("context", "binding", "activate_policy").contains(tags.get(i)) ? successorSource : source;
            String body=owningSource.split(java.util.regex.Pattern.quote("AS $"+tags.get(i)+"$"),2)[1]
                    .split(java.util.regex.Pattern.quote("$"+tags.get(i)+"$;"),2)[0];
            String actual=(String)catalog("SELECT prosrc FROM pg_proc WHERE oid=?::regprocedure","public."+signatures.get(i));
            assertEquals(HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(body.getBytes(StandardCharsets.UTF_8))),
                    HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(actual.getBytes(StandardCharsets.UTF_8))));
            assertEquals(0,count("SELECT count(*) FROM pg_proc p CROSS JOIN LATERAL aclexplode(coalesce(p.proacl,acldefault('f',p.proowner))) a WHERE p.oid=?::regprocedure AND a.grantee=0 AND a.privilege_type='EXECUTE'","public."+signatures.get(i)));
        }
        assertEquals(0,count("SELECT count(*) FROM pg_trigger WHERE NOT tgisinternal AND tgrelid::regclass::text LIKE 'hidra_org_appointment_%' AND tgenabled<>'O'"));
        assertEquals(0,count("SELECT count(*) FROM public.hidra_org_appointment_configuration_event WHERE sha256<>encode(sha256(canonical_payload),'hex') OR transaction_id<=0 OR backend_pid<=0"));
    }

    @Test void jdbcMutationPreviewUsesImmutableSourceBindingAndCanonicalTargetSelectors() {
        var dataSource = new DriverManagerDataSource(
                POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        var adapter = new JdbcProtectedEmployeeAppointmentRepositoryAdapter(
                new JdbcTemplate(dataSource));
        var f = retainedProjection;
        var ending = new EmployeeAppointmentCommand.End(
                "request-preview", f.company(), f.id(), 1,
                Instant.parse("2026-10-23T10:00:00Z"), "synthetic end");
        var source = adapter.preview(ending);
        assertEquals(f.employee(), source.employeeId());
        assertEquals(f.id(), source.assignmentId());
        assertEquals(List.of(new CompanyUnit(f.company(), f.unit())), source.companyUnits());

        var transfer = new EmployeeAppointmentCommand.Transfer(
                "request-transfer", f.company(), f.id(), 1, "unverified-destination",
                f.position(), AssignmentType.ACTING,
                Instant.parse("2026-10-23T10:00:00Z"), null, 1, 1,
                "synthetic transfer");
        var proposed = adapter.preview(transfer);
        var expected = new java.util.ArrayList<>(List.of(
                new CompanyUnit(f.company(), f.unit()),
                new CompanyUnit(f.company(), "unverified-destination")));
        expected.sort(java.util.Comparator.comparing(CompanyUnit::companyPartyId)
                .thenComparing(CompanyUnit::organizationUnitId));
        assertEquals(expected, proposed.companyUnits());
        // Preview never asserts the destination's existence or its eligibility.
    }

    @Test void jdbcMutationPreviewRejectsLegacyAbsentAndForeignSourcesWithoutDisclosure() {
        var adapter = new JdbcProtectedEmployeeAppointmentRepositoryAdapter(
                new JdbcTemplate(new DriverManagerDataSource(
                        POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())));
        var f = retainedProjection;
        var at = Instant.parse("2026-10-23T10:00:00Z");
        for (String id : List.of(retainedLegacyId, "missing-synthetic-source")) {
            var denied = new EmployeeAppointmentCommand.End(
                    "request-denied", f.company(), id, 1, at, "synthetic reason");
            assertEquals("Protected appointment admission unavailable",
                    assertThrows(AccessDeniedException.class,
                            () -> adapter.preview(denied)).getMessage());
        }
        var foreign = new EmployeeAppointmentCommand.End(
                "request-foreign", "foreign-company", f.id(), 1, at, "synthetic reason");
        assertEquals("Protected appointment admission unavailable",
                assertThrows(AccessDeniedException.class,
                        () -> adapter.preview(foreign)).getMessage());
        var create = new EmployeeAppointmentCommand.Create(
                "request-create", f.company(), f.employee(), f.unit(), f.position(),
                AssignmentType.PRIMARY, at, null, 1, 1);
        var selectors = adapter.preview(create);
        assertEquals(List.of(new CompanyUnit(f.company(), f.unit())), selectors.companyUnits());
        assertNull(selectors.assignmentId());
        // A valid selector never grants current ownership or mutation access.
    }

    @Test void jdbcReadPreviewDerivesExactPinnedHistoryFromImmutableEventSnapshots() {
        var adapter = new JdbcProtectedEmployeeAppointmentRepositoryAdapter(
                new JdbcTemplate(new DriverManagerDataSource(
                        POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())));
        var f = retainedProjection;
        var pair = List.of(new CompanyUnit(f.company(), f.unit()));
        var byAssignment = adapter.preview(
                dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery
                        .History.assignment(f.company(), f.id(), 20, null));
        assertEquals(pair, byAssignment.companyUnits());
        assertEquals(f.employee(), byAssignment.employeeId());
        assertEquals(f.id(), byAssignment.assignmentId());
        var byEmployee = adapter.preview(
                dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery
                        .History.employee(f.company(), f.employee(), 20, null));
        assertEquals(pair, byEmployee.companyUnits());
        assertEquals(f.employee(), byEmployee.employeeId());
        assertNull(byEmployee.assignmentId());
        var exact = adapter.preview(
                new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery
                        .ExactVersion(f.company(), f.id(), 1));
        assertEquals(pair, exact.companyUnits());
        assertEquals(f.id(), exact.assignmentId());
        assertEquals(f.employee(), exact.employeeId());
        // This exact revision was committed before the typed-command upgrade;
        // retained historical bytes are not retroactively rewritten.
    }

    @Test void jdbcReadPreviewRejectsForeignMissingLegacyAndWrongRevisionWithoutDisclosure() {
        var adapter = new JdbcProtectedEmployeeAppointmentRepositoryAdapter(
                new JdbcTemplate(new DriverManagerDataSource(
                        POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())));
        var q = dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                .assignment(retainedProjection.company(), retainedLegacyId, 20, null);
        assertEquals("Protected appointment admission unavailable",
                assertThrows(AccessDeniedException.class, () -> adapter.preview(q)).getMessage());
        var other = dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                .assignment("foreign-company", retainedProjection.id(), 20, null);
        assertEquals("Protected appointment admission unavailable",
                assertThrows(AccessDeniedException.class, () -> adapter.preview(other)).getMessage());
        var missing = dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                .employee(retainedProjection.company(), "unknown-synthetic-employee", 20, null);
        assertEquals("Protected appointment admission unavailable",
                assertThrows(AccessDeniedException.class, () -> adapter.preview(missing)).getMessage());
        var wrongVersion = new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.ExactVersion(
                retainedProjection.company(), retainedProjection.id(), 2);
        assertEquals("Protected appointment admission unavailable",
                assertThrows(AccessDeniedException.class, () -> adapter.preview(wrongVersion)).getMessage());
        // Position and policy previews intentionally carry only caller selectors.
        var position = adapter.preview(
                dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                        .position("requested-company", "requested-unit", "requested-position", 20, null));
        assertEquals(List.of(new CompanyUnit("requested-company", "requested-unit")),
                position.companyUnits());
        assertNull(position.employeeId());
        var policy = adapter.preview(
                new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Policy(
                        "requested-company", "requested-unit"));
        assertEquals(List.of(new CompanyUnit("requested-company", "requested-unit")),
                policy.companyUnits());
        assertNull(policy.assignmentId());
    }
}
