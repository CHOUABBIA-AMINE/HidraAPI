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
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
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
    private final java.util.Set<UUID> historyFixtureEvents=new java.util.HashSet<>();

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
        insertProjection(c, f, event, 1);
    }

    private static void insertProjection(Connection c, Projection f, UUID event, long bindingRevision) throws SQLException {
        execute(c, "INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at,appointment_version,binding_id,binding_revision,policy_id,policy_revision,last_event_id) VALUES (?,?,?,?,'PRIMARY',transaction_timestamp()-interval '1 day','ACTIVE',transaction_timestamp(),transaction_timestamp(),1,?,?,?,1,?::uuid)",
                f.id(), f.employee(), f.unit(), f.position(), f.binding(), bindingRevision, f.policy(), event);
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
        return completeCreate(c, f, 1);
    }

    private static UUID completeCreate(Connection c, Projection f, long bindingRevision) throws SQLException {
        UUID event = UUID.randomUUID();
        insertProjection(c, f, event, bindingRevision);
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
        assertEquals(1+historyFixtureEvents.size(),count("SELECT count(*) FROM public.hidra_org_appointment_event"));
        assertEquals(1+historyFixtureEvents.size(),count("SELECT count(*) FROM public.hidra_org_appointment_receipt"));
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
        // Empty/unavailable position history now fails uniformly; policy is still selector-only.
        assertEquals("Protected appointment admission unavailable",
                assertThrows(AccessDeniedException.class, () -> adapter.preview(
                        dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                                .position("requested-company", "requested-unit", "requested-position", 20, null)))
                        .getMessage());
        var policy = adapter.preview(
                new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Policy(
                        "requested-company", "requested-unit"));
        assertEquals(List.of(new CompanyUnit("requested-company", "requested-unit")),
                policy.companyUnits());
        assertNull(policy.assignmentId());
    }

    private Projection historicalDestination(Projection source) throws Exception {
        var destination = new Projection("assignment-" + UUID.randomUUID(), source.company(), unit(),
                source.employee(), position(), "binding-" + UUID.randomUUID(), source.policy());
        try (var c = login(CONFIG)) {
            binding(c, destination.binding(), destination.unit(), destination.company(), 0);
            c.commit();
        }
        return destination;
    }

    private static JdbcProtectedEmployeeAppointmentRepositoryAdapter previewOn(Connection c) {
        return new JdbcProtectedEmployeeAppointmentRepositoryAdapter(
                new JdbcTemplate(new SingleConnectionDataSource(c, true)));
    }

    /** Real structural ledger fixture; always rolled back, never a native writer proof. */
    private static Proposal historicalMove(Connection c, Projection source, Projection destination,
                                           String operation) throws SQLException {
        completeCreate(c, source);
        String before = snapshot(c, source.id());
        UUID event = UUID.randomUUID();
        String successorId = null, successor = "null";
        Projection after = source;
        if (operation.equals("TRANSFER")) {
            execute(c, "UPDATE public.hidra_org_employee_assignment SET appointment_version=2,last_event_id=?::uuid,valid_to=transaction_timestamp(),status='ENDED' WHERE id=?",
                    event, source.id());
            successorId = destination.id();
            execute(c, "INSERT INTO public.hidra_org_employee_assignment(id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,created_at,updated_at,appointment_version,binding_id,binding_revision,policy_id,policy_revision,last_event_id) SELECT ?::varchar,employee_id,?::varchar,?::varchar,assignment_type,valid_to,'ACTIVE',updated_at,updated_at,1,?::varchar,1,policy_id,policy_revision,last_event_id FROM public.hidra_org_employee_assignment WHERE id=?",
                    successorId, destination.unit(), destination.position(), destination.binding(), source.id());
            successor = snapshot(c, successorId);
        } else {
            execute(c, "UPDATE public.hidra_org_employee_assignment SET appointment_version=2,last_event_id=?::uuid,organization_unit_id=?,position_id=?,binding_id=? WHERE id=?",
                    event, destination.unit(), destination.position(), destination.binding(), source.id());
            after = new Projection(source.id(), source.company(), destination.unit(), source.employee(),
                    destination.position(), destination.binding(), source.policy());
        }
        String body = payload(c, after, operation, before, successor);
        insertEvent(c, source, event, operation, successorId, body, false);
        insertReceipt(c, event);
        execute(c, "SET CONSTRAINTS ALL IMMEDIATE");
        execute(c, "SET CONSTRAINTS ALL DEFERRED");
        return new Proposal(event, successorId, body);
    }

    private static List<CompanyUnit> historicalPairs(Projection first, Projection second) {
        return java.util.stream.Stream.of(new CompanyUnit(first.company(), first.unit()),
                new CompanyUnit(second.company(), second.unit()))
                .distinct().sorted(java.util.Comparator.comparing(CompanyUnit::companyPartyId)
                        .thenComparing(CompanyUnit::organizationUnitId)).toList();
    }

    @Test void jdbcPositionHistoryClosesAllPinnedCompaniesWithoutEmployeeDisclosure() throws Exception {
        var original = projection();
        var other = projection();
        try (var config = login(CONFIG)) {
            binding(config, original.binding(), original.unit(), other.company(), 1);
            config.commit();
        }
        var laterOccupant = new Projection("assignment-" + UUID.randomUUID(), other.company(),
                original.unit(), other.employee(), original.position(), original.binding(), other.policy());
        try (var c = projectionTransaction()) {
            completeCreate(c, original);
            completeCreate(c, laterOccupant, 2);
            var adapter = previewOn(c);
            var history = dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                    .position(original.company(), original.unit(), original.position(), 1, "opaque-page");
            var owners = adapter.preview(history);
            assertEquals(historicalPairs(original, laterOccupant), owners.companyUnits());
            assertNull(owners.employeeId());
            assertNull(owners.assignmentId());
            var request = new dz.sh.hidra.modules.identity.application.contract.organization
                    .OrganizationProtectedAppointmentAuthorizationContract.Request(
                    dz.sh.hidra.modules.identity.application.contract.organization
                            .OrganizationProtectedAppointmentAuthorizationContract.Action.READ_HISTORY,
                    owners.companyUnits(), null);
            assertEquals(3, request.requirements().size());
            assertTrue(request.requirements().stream().noneMatch(r -> r.kind()
                    == dz.sh.hidra.modules.identity.application.contract.organization
                            .OrganizationProtectedAppointmentAuthorizationContract.ResourceKind.EMPLOYEE));
            assertEquals(owners, adapter.preview(
                    dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                            .position(other.company(), original.unit(), original.position(), 100, null)));
            assertEquals("Protected appointment admission unavailable",
                    assertThrows(AccessDeniedException.class, () -> adapter.preview(
                            dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                                    .position("foreign-company", original.unit(), original.position(), 20, null)))
                            .getMessage());
            c.rollback();
        }
    }

    @Test void jdbcTransferHistoryIncludesCounterpartUnitsFromEveryReturnedEventSlot() throws Exception {
        var source = projection();
        var destination = historicalDestination(source);
        try (var c = projectionTransaction()) {
            var event = historicalMove(c, source, destination, "TRANSFER");
            var adapter = previewOn(c);
            var expected = historicalPairs(source, destination);
            for (String assignment : List.of(source.id(), event.successorId())) {
                var owners = adapter.preview(
                        dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                                .assignment(source.company(), assignment, 1, "opaque-page"));
                assertEquals(expected, owners.companyUnits());
                assertEquals(source.employee(), owners.employeeId());
                assertEquals(assignment, owners.assignmentId());
            }
            assertEquals(expected, adapter.preview(
                    dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                            .employee(source.company(), source.employee(), 100, null)).companyUnits());
            for (var selected : List.of(source, destination)) {
                var owners = adapter.preview(
                        dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                                .position(source.company(), selected.unit(), selected.position(), 1, null));
                assertEquals(expected, owners.companyUnits());
                assertNull(owners.employeeId());
                assertNull(owners.assignmentId());
            }
            c.rollback();
        }
    }

    @Test void jdbcCorrectionHistoryFindsThePriorPositionAndRetainsBothOwnerPairs() throws Exception {
        var source = projection();
        var destination = historicalDestination(source);
        try (var c = projectionTransaction()) {
            historicalMove(c, source, destination, "CORRECT");
            var adapter = previewOn(c);
            for (var selected : List.of(source, destination)) {
                var owners = adapter.preview(
                        dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                                .position(source.company(), selected.unit(), selected.position(), 1, null));
                assertEquals(historicalPairs(source, destination), owners.companyUnits());
                assertNull(owners.employeeId());
            }
            assertEquals(historicalPairs(source, destination), adapter.preview(
                    dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                            .assignment(source.company(), source.id(), 100, null)).companyUnits());
            c.rollback();
        }
    }

    @Test void jdbcExactVersionKeepsOnlyItsPinAfterValidatingTheCompleteEvent() throws Exception {
        var source = projection();
        var destination = historicalDestination(source);
        try (var c = projectionTransaction()) {
            historicalMove(c, source, destination, "TRANSFER");
            var adapter = previewOn(c);
            var ending = adapter.preview(
                    new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.ExactVersion(
                            source.company(), source.id(), 2));
            assertEquals(List.of(new CompanyUnit(source.company(), source.unit())), ending.companyUnits());
            assertEquals(source.employee(), ending.employeeId());
            var successor = adapter.preview(
                    new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.ExactVersion(
                            source.company(), destination.id(), 1));
            assertEquals(List.of(new CompanyUnit(destination.company(), destination.unit())),
                    successor.companyUnits());
            assertEquals(source.employee(), successor.employeeId());
            c.rollback();
        }
    }

    @Test void jdbcHistoryRejectsAnInvalidEventInsteadOfDroppingItsSnapshot() throws Exception {
        var first = projection();
        var second = new Projection("assignment-" + UUID.randomUUID(), first.company(), first.unit(),
                first.employee(), first.position(), first.binding(), first.policy());
        try (var c = projectionTransaction()) {
            completeCreate(c, first);
            UUID event = completeCreate(c, second);
            var adapter = previewOn(c);
            var query = dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                    .employee(first.company(), first.employee(), 20, null);
            assertDoesNotThrow(() -> adapter.preview(query));
            // Administrator fault injection only, rolled back with the whole fixture.
            // Keep another valid event so an INNER JOIN omission would falsely succeed.
            execute(c, "ALTER TABLE public.hidra_org_appointment_event DISABLE TRIGGER USER");
            for (String invalid : List.of(
                    "jsonb_set(p,'{after,assignment,binding_revision}','999999'::jsonb)",
                    "jsonb_set(p,'{after,assignment,employee_id}',to_jsonb('foreign-employee'::text))",
                    "jsonb_set(p,'{after,binding,recorded_at}',to_jsonb('1900-01-01T00:00:00Z'::text))",
                    "jsonb_set(p,'{after}','null'::jsonb)")) {
                var savepoint = c.setSavepoint();
                execute(c, "UPDATE public.hidra_org_appointment_event e SET canonical_payload=convert_to(("
                        + invalid + ")::text,'UTF8') FROM (SELECT convert_from(canonical_payload,'UTF8')::jsonb p FROM public.hidra_org_appointment_event WHERE event_id=?::uuid) v WHERE e.event_id=?::uuid",
                        event, event);
                assertEquals("Protected appointment admission unavailable",
                        assertThrows(AccessDeniedException.class, () -> adapter.preview(query)).getMessage());
                c.rollback(savepoint);
                c.releaseSavepoint(savepoint);
            }
            c.rollback();
        }
        assertEquals(0, count("SELECT count(*) FROM public.hidra_org_employee_assignment WHERE id IN (?,?)",
                first.id(), second.id()));
    }

    @Test void jdbcHistoryRejectsEventOverflowRegardlessOfPageLimit() throws Exception {
        var fixture = projection();
        try (var c = projectionTransaction()) {
            execute(c, """
                    INSERT INTO public.hidra_org_employee_assignment
                        (id,employee_id,organization_unit_id,position_id,assignment_type,valid_from,status,
                         created_at,updated_at,appointment_version,binding_id,binding_revision,
                         policy_id,policy_revision,last_event_id)
                    SELECT 'history-bound-'||g,?::varchar,?::varchar,?::varchar,'PRIMARY',
                           transaction_timestamp()-interval '1 day','ACTIVE',transaction_timestamp(),
                           transaction_timestamp(),1,?::varchar,1,?::varchar,1,gen_random_uuid()
                      FROM generate_series(1,1025) g
                    """, fixture.employee(), fixture.unit(), fixture.position(), fixture.binding(), fixture.policy());
            execute(c, """
                    INSERT INTO public.hidra_org_appointment_event
                        (event_id,operation,assignment_id,employee_id,company_party_id,policy_id,policy_revision,
                         canonical_payload,command_sha256,actor_binding_id,actor_session_id,actor_user_id,
                         sampled_at,valid_until,backend_pid,transaction_id)
                    SELECT a.last_event_id,'CREATE',a.id,a.employee_id,?::varchar,a.policy_id,1,
                           convert_to(p.body::text,'UTF8'),encode(sha256(convert_to((p.body->'command')::text,'UTF8')),'hex'),
                           gen_random_uuid(),gen_random_uuid(),'synthetic-actor',transaction_timestamp(),
                           transaction_timestamp()+interval '1 hour',pg_backend_pid(),txid_current()
                      FROM public.hidra_org_employee_assignment a
                     CROSS JOIN LATERAL (SELECT jsonb_build_object(
                         'command',jsonb_build_object('kind','CREATE','companyPartyId',?::text,'requestId',a.id,
                             'employeeId',a.employee_id,'organizationUnitId',a.organization_unit_id,
                             'positionId',a.position_id,'assignmentType',a.assignment_type,
                             'validFrom',a.valid_from,'validTo',a.valid_to,
                             'expectedBindingRevision',a.binding_revision,'expectedPolicyRevision',a.policy_revision),
                         'before',NULL,'after',public.hidra_org_appointment_snapshot(a),'successor',NULL) body) p
                     WHERE a.employee_id=?
                    """, fixture.company(), fixture.company(), fixture.employee());
            execute(c, """
                    INSERT INTO public.hidra_org_appointment_receipt
                        (event_id,actor_user_id,scope_key,request_id,command_sha256,canonical_result,
                         guarded_at,backend_pid,transaction_id)
                    SELECT event_id,actor_user_id,repeat('a',64),
                           convert_from(canonical_payload,'UTF8')::jsonb->'command'->>'requestId',
                           command_sha256,canonical_payload,sampled_at,backend_pid,transaction_id
                      FROM public.hidra_org_appointment_event WHERE employee_id=?
                    """, fixture.employee());
            execute(c, "SET CONSTRAINTS ALL IMMEDIATE");
            var adapter = previewOn(c);
            assertEquals("Protected appointment admission unavailable",
                    assertThrows(AccessDeniedException.class, () -> adapter.preview(
                            dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                                    .employee(fixture.company(), fixture.employee(), 1, null))).getMessage());
            c.rollback();
        }
        assertEquals(0, count("SELECT count(*) FROM public.hidra_org_appointment_event WHERE employee_id=?",
                fixture.employee()));
    }

    private static final List<String> STORAGE_TABLES = List.of("employee","unit","position",
            "appointment_binding_revision","appointment_binding_head","appointment_policy_revision",
            "appointment_policy_head","appointment_configuration_event","appointment_lock_key",
            "employee_assignment","appointment_event","appointment_receipt");

    /**
     * Real restricted Organization storage/transactions; upstream Identity alone
     * is mocked. The test-only definer retains real native relation locks but is
     * explicitly NOT a native preparation, permission or cryptographic proof.
     */
    private final class StorageFixture implements AutoCloseable {
        final String role = "storage_" + UUID.randomUUID().toString().replace("-", "");
        final String lockFunction = "public.fixture_storage_fences_" + role;
        final DriverManagerDataSource dataSource;
        final JdbcTemplate jdbc;
        final org.springframework.transaction.support.TransactionTemplate tx;
        final dz.sh.hidra.modules.identity.application.contract.organization
                .OrganizationProtectedAppointmentAuthorizationContract identity =
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.application.contract.organization
                        .OrganizationProtectedAppointmentAuthorizationContract.class);
        final dz.sh.hidra.modules.identity.application.contract.organization
                .OrganizationProtectedAppointmentAuthorizationContract.Admission nativeTicket =
                org.mockito.Mockito.mock(dz.sh.hidra.modules.identity.application.contract.organization
                        .OrganizationProtectedAppointmentAuthorizationContract.Admission.class);
        final JdbcProtectedEmployeeAppointmentRepositoryAdapter storage;
        final dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Policy policyQuery;
        final dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery readQuery;
        final org.springframework.security.core.Authentication authentication =
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        "synthetic-storage-test", "unused", List.of());

        StorageFixture() throws Exception {
            this(null);
        }

        StorageFixture(dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery query)
                throws Exception {
            readQuery = query;
            policyQuery = query instanceof dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Policy p ? p : null;
            admin("CREATE ROLE " + role + " LOGIN PASSWORD '" + PASSWORD + "'");
            admin("GRANT USAGE ON SCHEMA public TO " + role);
            for (String table : STORAGE_TABLES)
                admin("GRANT SELECT ON public.hidra_org_" + table + " TO " + role);
            for (String table : List.of("employee","unit","position","appointment_lock_key","employee_assignment"))
                admin("GRANT UPDATE ON public.hidra_org_" + table + " TO " + role);
            for (String table : List.of("appointment_binding_head","appointment_policy_head"))
                admin("GRANT UPDATE ON public.hidra_org_" + table + " TO " + role);
            admin("CREATE FUNCTION " + lockFunction + "() RETURNS void LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog AS $fixture$ BEGIN LOCK TABLE public.hidra_identity_attribute_definition IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_authorization_policy IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_authorization_policy_rule IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_authorization_policy_version IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_external_group_mapping IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_external_identity IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_external_permission_mapping IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_external_role_mapping IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_group IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_group_role_grant IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_local_credential IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_login_session IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_operation_binding IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_permission IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_provider IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_role IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_role_permission_grant IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_subject_security_attribute IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_user IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_user_group_membership IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_user_permission_grant IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_user_role_grant IN SHARE ROW EXCLUSIVE MODE NOWAIT; LOCK TABLE public.hidra_identity_external_mapping_generation IN ACCESS SHARE MODE NOWAIT; LOCK TABLE public.hidra_identity_bound_source_claim_evidence IN SHARE MODE NOWAIT; END; $fixture$");
            admin("REVOKE ALL ON FUNCTION " + lockFunction + "() FROM PUBLIC");
            admin("GRANT EXECUTE ON FUNCTION " + lockFunction + "() TO " + role);
            dataSource = new DriverManagerDataSource(POSTGRES.getJdbcUrl(), role, PASSWORD);
            jdbc = new JdbcTemplate(dataSource);
            tx = new org.springframework.transaction.support.TransactionTemplate(
                    new org.springframework.jdbc.datasource.DataSourceTransactionManager(dataSource));
            tx.setIsolationLevel(java.sql.Connection.TRANSACTION_READ_COMMITTED);
            storage = new JdbcProtectedEmployeeAppointmentRepositoryAdapter(jdbc, identity);
            org.springframework.security.core.context.SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
            org.mockito.Mockito.when(identity.finalFences(nativeTicket)).thenReturn(
                    dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
                            .JdbcIdentityAuthorityFence.requiredRelationFences());
            org.mockito.Mockito.when(identity.contribute(nativeTicket)).thenAnswer(call -> {
                var f = retainedProjection;
                var descriptors=readQuery==null?null:storage.preview(readQuery);
                var request = new dz.sh.hidra.modules.identity.application.contract.organization
                        .OrganizationProtectedAppointmentAuthorizationContract.Request(
                        policyQuery != null ? dz.sh.hidra.modules.identity.application.contract.organization
                                .OrganizationProtectedAppointmentAuthorizationContract.Action.READ_POLICY
                                : readQuery instanceof dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.ExactVersion
                                ? dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Action.READ_CURRENT
                                : dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Action.READ_HISTORY,
                        descriptors==null ? List.of(new CompanyUnit(f.company(),f.unit())) : descriptors.companyUnits(),
                        descriptors==null ? f.employee() : descriptors.employeeId());
                return jdbc.queryForObject("SELECT pg_backend_pid(),txid_current(),clock_timestamp()",
                        (row,index) -> new dz.sh.hidra.modules.identity.application.contract.organization
                                .OrganizationProtectedAppointmentAuthorizationContract.Contribution(
                                request,request.requirements().stream().map(r ->
                                    new dz.sh.hidra.modules.identity.application.contract.organization
                                            .OrganizationProtectedAppointmentAuthorizationContract.ResourceDecision(
                                            r,dz.sh.hidra.modules.identity.application.contract.organization
                                                    .OrganizationProtectedAppointmentAuthorizationContract.Decision.PERMIT))
                                    .toList(),
                                "synthetic-binding","synthetic-session","synthetic-user",
                                row.getTimestamp(3).toInstant(),row.getTimestamp(3).toInstant().plusSeconds(60),
                                row.getInt(1),row.getLong(2)));
            });
        }

        dz.sh.hidra.modules.organization.application.port.out.ProtectedEmployeeAppointmentRepositoryPort.Admission
                acquire() {
            jdbc.execute("SELECT " + lockFunction + "()");
            var f = retainedProjection;
            if (readQuery != null)
                return storage.acquire(nativeTicket,List.of(),storage.preview(readQuery));
            return storage.acquire(nativeTicket, List.of(),
                    new dz.sh.hidra.modules.organization.application.port.out
                            .ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                            List.of(new CompanyUnit(f.company(),f.unit())),f.employee(),f.id(),false));
        }

        org.springframework.jdbc.datasource.ConnectionHolder holder() {
            return (org.springframework.jdbc.datasource.ConnectionHolder)
                    org.springframework.transaction.support.TransactionSynchronizationManager
                            .getResource(dataSource);
        }

        @Override public void close() {
            org.springframework.security.core.context.SecurityContextHolder.clearContext();
        }
    }

    @Test void storageAdmissionRetainsActualOwnerFencesAndConsumesItsNativeOwnerOnce() throws Exception {
        try (var f = new StorageFixture()) {
            f.tx.execute(status -> {
                var ticket = f.acquire();
                var sample = f.storage.contribute(ticket);
                assertEquals(retainedProjection.employee(),sample.request().employeeId());
                assertDoesNotThrow(() -> f.storage.requireCurrent(ticket));
                assertEquals(12, f.jdbc.queryForObject("""
                        SELECT count(DISTINCT c.relname) FROM pg_locks l
                        JOIN pg_class c ON c.oid=l.relation JOIN pg_namespace n ON n.oid=c.relnamespace
                        WHERE pid=pg_backend_pid() AND granted AND locktype='relation' AND n.nspname='public'
                          AND c.relname IN ('hidra_org_employee','hidra_org_unit','hidra_org_position',
                            'hidra_org_appointment_binding_revision','hidra_org_appointment_binding_head',
                            'hidra_org_appointment_policy_revision','hidra_org_appointment_policy_head',
                            'hidra_org_appointment_configuration_event','hidra_org_appointment_lock_key',
                            'hidra_org_employee_assignment','hidra_org_appointment_event','hidra_org_appointment_receipt')
                        """,Integer.class));
                assertEquals("StorageAdmission[opaque]",ticket.toString());
                assertThrows(AccessDeniedException.class,() -> f.storage.contribute(ticket));
                assertTrue(f.holder().isRollbackOnly());
                org.mockito.Mockito.verify(f.identity,org.mockito.Mockito.times(1)).contribute(f.nativeTicket);
                status.setRollbackOnly();
                return null;
            });
        }
    }

    @Test void storageAdmissionRejectsUnqualifiedForeignCompletedAndWrongThreadTickets() throws Exception {
        try (var f = new StorageFixture()) {
            var saved = new dz.sh.hidra.modules.organization.application.port.out
                    .ProtectedEmployeeAppointmentRepositoryPort.Admission[1];
            f.tx.execute(status -> {
                saved[0]=f.acquire();
                assertThrows(AccessDeniedException.class,() -> f.storage.requireCurrent(saved[0]));
                assertTrue(f.holder().isRollbackOnly());
                status.setRollbackOnly();return null;
            });
            f.tx.execute(status -> {
                assertThrows(AccessDeniedException.class,() -> f.storage.requireCurrent(saved[0]));
                assertTrue(f.holder().isRollbackOnly());
                status.setRollbackOnly();return null;
            });
            f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);
                var other=new JdbcProtectedEmployeeAppointmentRepositoryAdapter(f.jdbc,f.identity);
                assertThrows(AccessDeniedException.class,() -> other.requireCurrent(ticket));
                assertTrue(f.holder().isRollbackOnly());
                status.setRollbackOnly();return null;
            });
            f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);
                var worker=java.util.concurrent.Executors.newSingleThreadExecutor();
                try {
                    var failure=worker.submit(() -> assertThrows(AccessDeniedException.class,
                            () -> f.storage.requireCurrent(ticket)));
                    assertNotNull(failure.get(5,java.util.concurrent.TimeUnit.SECONDS));
                } catch(Exception unexpected) { throw new AssertionError(unexpected); }
                finally {worker.shutdownNow();}
                assertTrue(f.holder().isRollbackOnly());
                status.setRollbackOnly();return null;
            });
        }
    }

    @Test void storageAdmissionRejectsAuthenticationSwapSuspensionAndIssuerRevocation() throws Exception {
        try (var f = new StorageFixture()) {
            for (int variant=0;variant<3;variant++) {
                final int failure=variant;
                f.tx.execute(status -> {
                    var ticket=f.acquire();f.storage.contribute(ticket);
                    if(failure==0)
                        org.springframework.security.core.context.SecurityContextHolder.clearContext();
                    else if(failure==1)
                        org.springframework.transaction.support.TransactionSynchronizationManager
                                .getSynchronizations().forEach(s -> s.suspend());
                    else org.mockito.Mockito.doThrow(new IllegalStateException("native owner revoked"))
                            .when(f.identity).requireCurrent(f.nativeTicket);
                    assertThrows(AccessDeniedException.class,() -> f.storage.requireCurrent(ticket));
                    assertTrue(f.holder().isRollbackOnly());
                    status.setRollbackOnly();return null;
                });
                org.springframework.security.core.context.SecurityContextHolder.getContext()
                        .setAuthentication(f.authentication);
            }
        }
    }

    @Test void storageAdmissionCatalogPinsRejectDisabledTriggersAndRemovedConstraints() throws Exception {
        try (var f = new StorageFixture()) {
            admin("ALTER TABLE public.hidra_org_appointment_event DISABLE TRIGGER appointment_event_immutable");
            try {
                f.tx.execute(status -> {
                    assertThrows(AccessDeniedException.class,f::acquire);
                    assertTrue(f.holder().isRollbackOnly());
                    status.setRollbackOnly();return null;
                });
            } finally {
                admin("ALTER TABLE public.hidra_org_appointment_event ENABLE TRIGGER appointment_event_immutable");
            }
            String definition=catalog("SELECT pg_get_constraintdef(oid) FROM pg_constraint WHERE conrelid='public.hidra_org_employee_assignment'::regclass AND conname='appointment_metadata_complete'").toString();
            admin("ALTER TABLE public.hidra_org_employee_assignment DROP CONSTRAINT appointment_metadata_complete");
            try {
                f.tx.execute(status -> {
                    assertThrows(AccessDeniedException.class,f::acquire);
                    assertTrue(f.holder().isRollbackOnly());
                    status.setRollbackOnly();return null;
                });
            } finally {
                admin("ALTER TABLE public.hidra_org_employee_assignment ADD CONSTRAINT appointment_metadata_complete "+definition);
            }
            org.mockito.Mockito.verify(f.identity,org.mockito.Mockito.never()).contribute(f.nativeTicket);
        }
    }

    @Test void storageAdmissionRejectsSchemaCreationPrivilegeAndConfigurationWriteEscalation() throws Exception {
        try (var f = new StorageFixture()) {
            admin("GRANT CREATE ON SCHEMA public TO "+f.role);
            try {
                f.tx.execute(status -> {
                    assertThrows(AccessDeniedException.class,f::acquire);
                    assertTrue(f.holder().isRollbackOnly());
                    status.setRollbackOnly();return null;
                });
            } finally {admin("REVOKE CREATE ON SCHEMA public FROM "+f.role);}
            admin("GRANT INSERT ON public.hidra_org_appointment_binding_head TO "+f.role);
            try {
                f.tx.execute(status -> {
                    assertThrows(AccessDeniedException.class,f::acquire);
                    assertTrue(f.holder().isRollbackOnly());
                    status.setRollbackOnly();return null;
                });
            } finally {admin("REVOKE INSERT ON public.hidra_org_appointment_binding_head FROM "+f.role);}
            org.mockito.Mockito.verify(f.identity,org.mockito.Mockito.never()).contribute(f.nativeTicket);
        }
    }

    @Test void storageAdmissionCannotWaitPastAnEarlierOrganizationWriterFence() throws Exception {
        try (var f=new StorageFixture();var blocker=owner()) {
            blocker.setAutoCommit(false);
            execute(blocker,"LOCK TABLE public.hidra_org_employee IN SHARE ROW EXCLUSIVE MODE");
            f.tx.execute(status -> {
                assertThrows(AccessDeniedException.class,f::acquire);
                assertTrue(f.holder().isRollbackOnly());
                status.setRollbackOnly();return null;
            });
            blocker.rollback();
            org.mockito.Mockito.verify(f.identity,org.mockito.Mockito.never()).contribute(f.nativeTicket);
        }
    }


    @Test void storageLockPrivilegeCannotActivateAConfigurationWithoutItsAdministratorEvent() throws Exception {
        try(var f=new StorageFixture();var c=login(f.role)) {
            state("23514",() -> execute(c,
                    "UPDATE public.hidra_org_appointment_binding_head SET revision=revision+1,generation=generation+1 WHERE organization_unit_id=?",
                    retainedProjection.unit()));
            c.rollback();
            state("23514",() -> execute(c,
                    "UPDATE public.hidra_org_appointment_policy_head SET revision=revision+1,generation=generation+1 WHERE company_party_id=?",
                    retainedProjection.company()));
            c.rollback();
            state("42501",() -> execute(c,
                    "SELECT public.hidra_org_activate_appointment_binding(?::text,?::text,?::text,1::bigint)",
                    retainedProjection.binding(),retainedProjection.unit(),retainedProjection.company()));
            c.rollback();
        }
    }


    @Test void storageAdmissionRejectsNativeScopeAndPhysicalCoordinateSubstitution() throws Exception {
        try(var f=new StorageFixture()) {
            for(boolean wrongScope:List.of(true,false)) {
                f.tx.execute(status -> {
                    var ticket=f.acquire();
                    var reference=retainedProjection;
                    var request=new dz.sh.hidra.modules.identity.application.contract.organization
                            .OrganizationProtectedAppointmentAuthorizationContract.Request(
                            dz.sh.hidra.modules.identity.application.contract.organization
                                    .OrganizationProtectedAppointmentAuthorizationContract.Action.READ_HISTORY,
                            List.of(new CompanyUnit(reference.company(),
                                    wrongScope?"foreign-unit":reference.unit())),reference.employee());
                    var sample=f.jdbc.queryForObject("SELECT pg_backend_pid(),txid_current(),clock_timestamp()",
                            (row,index) -> new dz.sh.hidra.modules.identity.application.contract.organization
                                    .OrganizationProtectedAppointmentAuthorizationContract.Contribution(
                                    request,request.requirements().stream().map(r ->
                                        new dz.sh.hidra.modules.identity.application.contract.organization
                                                .OrganizationProtectedAppointmentAuthorizationContract.ResourceDecision(
                                                r,dz.sh.hidra.modules.identity.application.contract.organization
                                                        .OrganizationProtectedAppointmentAuthorizationContract.Decision.PERMIT))
                                        .toList(),
                                    "synthetic-binding","synthetic-session","synthetic-user",
                                    row.getTimestamp(3).toInstant(),row.getTimestamp(3).toInstant().plusSeconds(60),
                                    row.getInt(1)+(wrongScope?0:1),row.getLong(2)));
                    org.mockito.Mockito.when(f.identity.contribute(f.nativeTicket)).thenReturn(sample);
                    assertThrows(AccessDeniedException.class,() -> f.storage.contribute(ticket));
                    assertTrue(f.holder().isRollbackOnly());
                    status.setRollbackOnly();return null;
                });
            }
        }
    }

    private static dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Policy
            policyQuery(String company, String unit) {
        return new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Policy(company,unit);
    }

    @Test void storagePolicyReadMaterializesEveryExplicitCellUnderItsExactNativeScope() throws Exception {
        var p=projection();
        var query=policyQuery(p.company(),p.unit());
        try(var f=new StorageFixture(query)) {
            f.tx.execute(status -> {
                var ticket=f.acquire();var sample=f.storage.contribute(ticket);
                assertNull(sample.request().employeeId());
                assertEquals(2,sample.request().requirements().size());
                var result=f.storage.readPolicy(ticket,query);
                assertEquals(p.company(),result.companyPartyId());assertEquals(p.unit(),result.organizationUnitId());
                assertEquals(p.policy(),result.policyId());assertEquals(1,result.revision());
                assertEquals(1,result.policy().maxConcurrentPrimary());
                assertEquals(dz.sh.hidra.modules.organization.domain.policy.EmployeeAppointmentPolicy
                        .PositionCapacity.SINGLE_HOLDER,result.policy().positionCapacity());
                assertEquals(16,result.policy().overlapsAllowed().size());
                for(var a:AssignmentType.values()) for(var b:AssignmentType.values())
                    assertEquals(Boolean.FALSE,result.policy().overlapsAllowed().get(
                            new dz.sh.hidra.modules.organization.domain.policy.EmployeeAppointmentPolicy.TypePair(a,b)));
                assertThrows(UnsupportedOperationException.class,() -> result.policy().overlapsAllowed().clear());
                assertEquals(result,f.storage.readPolicy(ticket,query));
                org.mockito.Mockito.verify(f.identity,org.mockito.Mockito.times(1)).contribute(f.nativeTicket);
                status.setRollbackOnly();return null;
            });
        }
    }

    @Test void storagePolicyReadUsesTheActivatedSuccessorWithoutRewritingHistoricalRevision() throws Exception {
        var p=projection();
        String selected=policyValue().replace("\"maxConcurrentPrimary\":1","\"maxConcurrentPrimary\":7")
                .replace("SINGLE_HOLDER","UNRESTRICTED").replace("false","true");
        try(var c=login(CONFIG)) {policy(c,p.policy(),p.company(),1,selected);c.commit();}
        var query=policyQuery(p.company(),p.unit());
        try(var f=new StorageFixture(query)) {
            f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);
                var result=f.storage.readPolicy(ticket,query);
                assertEquals(2,result.revision());assertEquals(7,result.policy().maxConcurrentPrimary());
                assertEquals(dz.sh.hidra.modules.organization.domain.policy.EmployeeAppointmentPolicy
                        .PositionCapacity.UNRESTRICTED,result.policy().positionCapacity());
                assertTrue(result.policy().overlapsAllowed().values().stream().allMatch(Boolean.TRUE::equals));
                assertEquals(1,f.jdbc.queryForObject("SELECT (policy->>'maxConcurrentPrimary')::integer FROM public.hidra_org_appointment_policy_revision WHERE policy_id=? AND revision=1",Integer.class,p.policy()));
                status.setRollbackOnly();return null;
            });
        }
    }

    @Test void storagePolicyReadRequiresTheCurrentUnitCompanyBindingAfterReassignment() throws Exception {
        var p=projection();var other=projection();
        try(var c=login(CONFIG)) {binding(c,p.binding(),p.unit(),other.company(),1);c.commit();}
        for(String company:List.of(p.company(),other.company())) {
            var query=policyQuery(company,p.unit());
            try(var f=new StorageFixture(query)) {
                f.tx.execute(status -> {
                    var ticket=f.acquire();f.storage.contribute(ticket);
                    if(company.equals(p.company())) {
                        assertEquals("Protected appointment admission unavailable",assertThrows(AccessDeniedException.class,
                                () -> f.storage.readPolicy(ticket,query)).getMessage());
                        assertTrue(f.holder().isRollbackOnly());
                    } else assertEquals(other.policy(),f.storage.readPolicy(ticket,query).policyId());
                    status.setRollbackOnly();return null;
                });
            }
        }
    }

    @Test void storagePolicyReadRejectsMissingBindingOrPolicyWithoutFallback() throws Exception {
        var p=projection();String company=company("ACTIVE"),unit=unit();
        try(var c=login(CONFIG)) {binding(c,"binding-"+UUID.randomUUID(),unit,company,0);c.commit();}
        for(var query:List.of(policyQuery(company,unit),policyQuery(p.company(),unit()),
                policyQuery("unknown-company","unknown-unit"))) {
            try(var f=new StorageFixture(query)) {
                f.tx.execute(status -> {
                    var ticket=f.acquire();f.storage.contribute(ticket);
                    assertEquals("Protected appointment admission unavailable",assertThrows(AccessDeniedException.class,
                            () -> f.storage.readPolicy(ticket,query)).getMessage());
                    assertTrue(f.holder().isRollbackOnly());status.setRollbackOnly();return null;
                });
            }
        }
    }

    @Test void storagePolicyReadRejectsHistoryAuthorityUnqualifiedTicketsAndSelectorSubstitution() throws Exception {
        var query=policyQuery(retainedProjection.company(),retainedProjection.unit());
        try(var f=new StorageFixture()) {
            f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);
                assertThrows(AccessDeniedException.class,() -> f.storage.readPolicy(ticket,query));
                assertTrue(f.holder().isRollbackOnly());status.setRollbackOnly();return null;
            });
        }
        try(var f=new StorageFixture(query)) {
            f.tx.execute(status -> {
                var ticket=f.acquire();
                assertThrows(AccessDeniedException.class,() -> f.storage.readPolicy(ticket,query));
                assertTrue(f.holder().isRollbackOnly());status.setRollbackOnly();return null;
            });
            f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);f.storage.readPolicy(ticket,query);
                assertThrows(AccessDeniedException.class,() -> f.storage.readPolicy(ticket,
                        policyQuery(query.companyPartyId(),"another-unit")));
                assertTrue(f.holder().isRollbackOnly());status.setRollbackOnly();return null;
            });
        }
    }

    @Test void storagePolicyReadRetainsNativeRevocationChecksWithoutDemandingCurrentPartyEligibility() throws Exception {
        var p=projection();var query=policyQuery(p.company(),p.unit());
        admin("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",p.company());
        try(var f=new StorageFixture(query)) {
            f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);
                assertEquals(p.policy(),f.storage.readPolicy(ticket,query).policyId());
                org.mockito.Mockito.doThrow(new IllegalStateException("native issuer revoked"))
                        .when(f.identity).requireCurrent(f.nativeTicket);
                assertThrows(AccessDeniedException.class,() -> f.storage.readPolicy(ticket,query));
                assertTrue(f.holder().isRollbackOnly());status.setRollbackOnly();return null;
            });
        }
    }

    @Test void storageAdmissionBeforeCommitVetoIsSqlFreeAfterAuthenticationReplacement() throws Exception {
        try (var f=new StorageFixture()) {
            assertThrows(AccessDeniedException.class,() -> f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);
                org.springframework.security.core.context.SecurityContextHolder.clearContext();
                return null; // Actual Spring beforeCommit must veto this otherwise read-only fixture.
            }));
        }
    }

    private static final String READ_GUARD_SIGNATURE=
            "public.hidra_org_appointment_read_guard(jsonb,timestamptz,timestamptz,integer,bigint,timestamptz)";
    private static final String READ_GUARD_SQL="WITH observed AS MATERIALIZED (SELECT pg_catalog.clock_timestamp() AS t) SELECT public.hidra_org_appointment_read_guard(?::jsonb,?::timestamptz,?::timestamptz,?,?,t) FROM observed";

    /** Independent full-row expected image. It is a raw SQL fixture, never native authority. */
    private static String policyReadImage(JdbcTemplate jdbc,Projection p) {
        return jdbc.queryForObject("""
                SELECT jsonb_build_object('kind','POLICY','bindingHead',to_jsonb(bh),
                    'bindingRevision',to_jsonb(b),'bindingEvent',to_jsonb(be),'policyHead',to_jsonb(ph),
                    'policyRevision',to_jsonb(pr),'policyEvent',to_jsonb(pe))::text
                  FROM public.hidra_org_appointment_binding_head bh
                  JOIN public.hidra_org_appointment_binding_revision b ON b.binding_id=bh.binding_id AND b.revision=bh.revision
                  JOIN public.hidra_org_appointment_configuration_event be ON be.event_id=bh.last_event_id
                  JOIN public.hidra_org_appointment_policy_head ph ON ph.company_party_id=bh.company_party_id
                  JOIN public.hidra_org_appointment_policy_revision pr ON pr.policy_id=ph.policy_id AND pr.revision=ph.revision
                  JOIN public.hidra_org_appointment_configuration_event pe ON pe.event_id=ph.last_event_id
                 WHERE bh.company_party_id=? AND bh.organization_unit_id=?
                """,String.class,p.company(),p.unit());
    }

    private static Boolean policyReadGuard(JdbcTemplate jdbc,String image,Instant sample,Instant until,int pid,long xid) {
        return jdbc.queryForObject(READ_GUARD_SQL,Boolean.class,image,java.sql.Timestamp.from(sample),
                java.sql.Timestamp.from(until),pid,xid);
    }

    private void grantPolicyReadGuard(StorageFixture f) throws SQLException {
        admin("GRANT EXECUTE ON FUNCTION "+READ_GUARD_SIGNATURE+" TO "+f.role);
    }

    private static void organizationReadFences(JdbcTemplate jdbc) {
        for(String table:List.of("employee","unit","position","appointment_binding_revision","appointment_binding_head",
                "appointment_policy_revision","appointment_policy_head","appointment_configuration_event","appointment_lock_key",
                "employee_assignment","appointment_event","appointment_receipt")){
            boolean immutable=List.of("appointment_binding_revision","appointment_policy_revision","appointment_configuration_event",
                    "appointment_event","appointment_receipt").contains(table);
            jdbc.execute("LOCK TABLE public.hidra_org_"+table+" IN "+(immutable?"ACCESS SHARE":"SHARE ROW EXCLUSIVE")+" MODE NOWAIT");
        }
    }

    @Test void policyReadGuardAcceptsExactGraphWithoutPartyOrProofFencesAndNeverWrites() throws Exception {
        var p=projection();var query=policyQuery(p.company(),p.unit());
        admin("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",p.company());
        long events=count("SELECT count(*) FROM public.hidra_org_appointment_event");
        long receipts=count("SELECT count(*) FROM public.hidra_org_appointment_receipt");
        long uses=count("SELECT count(*) FROM public.hidra_identity_org_commit_proof_use");
        long keys=count("SELECT count(*) FROM public.hidra_org_appointment_lock_key");
        try(var f=new StorageFixture(query)){
            grantPolicyReadGuard(f);
            f.tx.execute(status -> {
                f.jdbc.execute("SET LOCAL TIME ZONE 'UTC'");
                var admission=f.acquire();var sample=f.storage.contribute(admission);
                assertEquals(p.policy(),f.storage.readPolicy(admission,query).policyId());
                String image=policyReadImage(f.jdbc,p);
                assertEquals(0,f.jdbc.queryForObject("""
                        SELECT count(*) FROM pg_locks WHERE pid=pg_backend_pid() AND granted
                          AND relation IN ('public.hidra_party_party'::regclass,
                            'public.hidra_party_eligibility_generation'::regclass,
                            'public.hidra_identity_org_commit_key_event'::regclass,
                            'public.hidra_identity_org_commit_key_head'::regclass,
                            'public.hidra_identity_org_commit_proof_use'::regclass)
                        """,Integer.class));
                assertEquals(Boolean.TRUE,policyReadGuard(f.jdbc,image,sample.sampledAt(),sample.validUntil(),sample.backendPid(),sample.transactionId()));
                return null; // No owner query after the final scalar. Native issuer remains mocked.
            });
        }
        assertEquals(events,count("SELECT count(*) FROM public.hidra_org_appointment_event"));
        assertEquals(receipts,count("SELECT count(*) FROM public.hidra_org_appointment_receipt"));
        assertEquals(uses,count("SELECT count(*) FROM public.hidra_identity_org_commit_proof_use"));
        assertEquals(keys,count("SELECT count(*) FROM public.hidra_org_appointment_lock_key"));
    }

    @Test void policyReadGuardRejectsChangedFullRowsMissingFieldsAndUnsupportedReadKinds() throws Exception {
        var p=projection();var query=policyQuery(p.company(),p.unit());
        for(int attack=0;attack<5;attack++)try(var f=new StorageFixture(query)){
            grantPolicyReadGuard(f);final int variant=attack;
            f.tx.execute(status -> {
                f.jdbc.execute("SET LOCAL TIME ZONE 'UTC'");var admission=f.acquire();var sample=f.storage.contribute(admission);
                String original=policyReadImage(f.jdbc,p);
                String changed=f.jdbc.queryForObject(switch(variant){
                    case 0 -> "SELECT jsonb_set(?::jsonb,'{policyRevision,sha256}',to_jsonb(repeat('0',64)))::text";
                    case 1 -> "SELECT jsonb_set(?::jsonb,'{bindingHead,generation}','999'::jsonb)::text";
                    case 2 -> "SELECT (?::jsonb - 'bindingEvent')::text";
                    case 3 -> "SELECT jsonb_set(?::jsonb,'{kind}','\"HISTORY\"'::jsonb)::text";
                    default -> "SELECT (?::jsonb || jsonb_build_object('unreviewed',true))::text";
                },String.class,original);
                assertThrows(org.springframework.dao.DataAccessException.class,() -> policyReadGuard(f.jdbc,changed,
                        sample.sampledAt(),sample.validUntil(),sample.backendPid(),sample.transactionId()));
                status.setRollbackOnly();return null;
            });
        }
    }

    @Test void policyReadGuardRejectsExpiredFutureAndDifferentPhysicalCoordinates() throws Exception {
        var p=projection();var query=policyQuery(p.company(),p.unit());
        for(int attack=0;attack<4;attack++)try(var f=new StorageFixture(query)){
            grantPolicyReadGuard(f);final int variant=attack;
            f.tx.execute(status -> {
                f.jdbc.execute("SET LOCAL TIME ZONE 'UTC'");var admission=f.acquire();var sample=f.storage.contribute(admission);
                String image=policyReadImage(f.jdbc,p);
                assertThrows(org.springframework.dao.DataAccessException.class,() -> policyReadGuard(f.jdbc,image,
                        variant==0?sample.sampledAt().minusSeconds(120):variant==1?sample.sampledAt().plusSeconds(120):sample.sampledAt(),
                        variant==0?sample.sampledAt().minusSeconds(60):variant==1?sample.validUntil().plusSeconds(120):sample.validUntil(),
                        variant==2?sample.backendPid()+1:sample.backendPid(),variant==3?sample.transactionId()+1:sample.transactionId()));
                status.setRollbackOnly();return null;
            });
        }
    }

    @Test void policyReadGuardRejectsSnapshotFromBeforePolicySuccessorOrCompanyRebinding() throws Exception {
        for(boolean rebind:List.of(false,true)){
            var p=projection();var query=policyQuery(p.company(),p.unit());
            try(var f=new StorageFixture(query)){
                grantPolicyReadGuard(f);
                String old=f.tx.execute(status -> {f.jdbc.execute("SET LOCAL TIME ZONE 'UTC'");return policyReadImage(f.jdbc,p);});
                String other=rebind?company("ACTIVE"):null;
                try(var c=login(CONFIG)){
                    if(rebind)binding(c,p.binding(),p.unit(),other,1);
                    else policy(c,p.policy(),p.company(),1,policyValue().replace("\"maxConcurrentPrimary\":1","\"maxConcurrentPrimary\":7"));
                    c.commit();
                }
                f.tx.execute(status -> {
                    f.jdbc.execute("SET LOCAL TIME ZONE 'UTC'");f.acquire();var sample=f.identity.contribute(f.nativeTicket);
                    assertThrows(org.springframework.dao.DataAccessException.class,() -> policyReadGuard(f.jdbc,old,
                            sample.sampledAt(),sample.validUntil(),sample.backendPid(),sample.transactionId()));
                    status.setRollbackOnly();return null;
                });
            }
        }
    }

    @Test void policyReadGuardIndependentlyRequiresNativeAndOrganizationFences() throws Exception {
        var p=projection();var query=policyQuery(p.company(),p.unit());
        for(boolean nativeOnly:List.of(false,true))try(var f=new StorageFixture(query)){
            grantPolicyReadGuard(f);f.tx.execute(status -> {
                f.jdbc.execute("SET LOCAL TIME ZONE 'UTC'");
                if(nativeOnly)f.jdbc.execute("SELECT "+f.lockFunction+"()");else organizationReadFences(f.jdbc);
                var sample=f.identity.contribute(f.nativeTicket);String image=policyReadImage(f.jdbc,p);
                assertThrows(org.springframework.dao.DataAccessException.class,() -> policyReadGuard(f.jdbc,image,
                        sample.sampledAt(),sample.validUntil(),sample.backendPid(),sample.transactionId()));
                status.setRollbackOnly();return null;
            });
        }
    }

    @Test void policyReadGuardRejectsReceiptWritePrivilegeAndRetainsClosedAclAndFixedPath() throws Exception {
        var p=projection();var query=policyQuery(p.company(),p.unit());
        try(var ordinary=login(ORDINARY)){
            assertEquals(Boolean.FALSE,scalar(ordinary,"SELECT has_function_privilege(session_user,?,'EXECUTE')",READ_GUARD_SIGNATURE));
        }
        assertEquals(0,count("""
                SELECT count(*) FROM pg_proc p,LATERAL aclexplode(coalesce(p.proacl,acldefault('f',p.proowner))) a
                 WHERE p.oid='public.hidra_org_appointment_read_guard(jsonb,timestamptz,timestamptz,integer,bigint,timestamptz)'::regprocedure
                   AND a.grantee=0 AND a.privilege_type='EXECUTE'
                """));
        assertEquals(Boolean.TRUE,catalog("""
                SELECT prosecdef AND cardinality(proconfig)=2 AND 'search_path=pg_catalog'=ANY(proconfig)
                   AND EXISTS(SELECT 1 FROM unnest(proconfig) setting WHERE lower(setting)='timezone=utc')
                  FROM pg_proc WHERE oid='public.hidra_org_appointment_read_guard(jsonb,timestamptz,timestamptz,integer,bigint,timestamptz)'::regprocedure
                """));
        try(var f=new StorageFixture(query)){
            grantPolicyReadGuard(f);admin("GRANT INSERT ON public.hidra_org_appointment_receipt TO "+f.role);
            f.tx.execute(status -> {
                f.jdbc.execute("SET LOCAL TIME ZONE 'UTC'");f.jdbc.execute("SELECT "+f.lockFunction+"()");organizationReadFences(f.jdbc);
                var sample=f.identity.contribute(f.nativeTicket);String image=policyReadImage(f.jdbc,p);
                assertThrows(org.springframework.dao.DataAccessException.class,() -> policyReadGuard(f.jdbc,image,
                        sample.sampledAt(),sample.validUntil(),sample.backendPid(),sample.transactionId()));
                status.setRollbackOnly();return null;
            });
        }
    }

    @Test void policyReadGuardTemplateRemainsTheRegisteredNonMutatingScalar() throws Exception {
        assertEquals("0b8a4baaef583bb8bccc878e0d255a295b2d63c7dc31d7d8af3a2e3b50261e51",
                HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(READ_GUARD_SQL.getBytes(StandardCharsets.UTF_8))));
    }

    private static dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History employeeHistory(
            Projection p,int limit,String cursor) {
        return dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                .employee(p.company(),p.employee(),limit,cursor);
    }

    private UUID commitCreate(Projection p) throws Exception {
        try(var c=projectionTransaction()){var id=completeCreate(c,p);c.commit();historyFixtureEvents.add(id);return id;}
    }

    private void commitMove(Projection p,Projection destination,String kind) throws Exception {
        try(var c=projectionTransaction()) {
            historicalMove(c,p,destination,kind);
            var ids=new java.util.ArrayList<UUID>();
            try(var statement=c.prepareStatement("SELECT event_id FROM public.hidra_org_appointment_event WHERE employee_id=?")) {
                statement.setString(1,p.employee());try(var rows=statement.executeQuery()) {
                    while(rows.next())ids.add(rows.getObject(1,UUID.class));
                }
            }
            c.commit();historyFixtureEvents.addAll(ids);
        }
    }

    private Projection peer(Projection p) {
        return new Projection("assignment-"+UUID.randomUUID(),p.company(),p.unit(),p.employee(),p.position(),p.binding(),p.policy());
    }

    private dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.HistoryPage<
            dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.PrivateHistoryEvent> historyPage(
            dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History query) throws Exception {
        try(var f=new StorageFixture(query)) {
            return f.tx.execute(status -> {
                f.jdbc.execute("SET LOCAL TIME ZONE 'UTC'");var ticket=f.acquire();f.storage.contribute(ticket);
                var result=f.storage.privateHistory(ticket,query);status.setRollbackOnly();return result;
            });
        }
    }

    @Test void protectedHistoryMaterializesPrivateAssignmentEmployeeAndRedactedPositionViews() throws Exception {
        var p=projection();var id=commitCreate(p);
        var employee=historyPage(employeeHistory(p,20,null));
        var assignment=historyPage(dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                .assignment(p.company(),p.id(),20,null));
        assertEquals(employee,assignment);assertEquals(1,employee.entries().size());
        assertEquals(id.toString(),employee.entries().get(0).eventId());
        assertEquals(p.employee(),employee.entries().get(0).after().snapshot().appointment().assignment().employeeId());
        var query=dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History
                .position(p.company(),p.unit(),p.position(),20,null);
        try(var f=new StorageFixture(query)) {
            f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);var page=f.storage.positionHistory(ticket,query);
                assertEquals(p.id(),page.entries().get(0).after().assignmentId());
                String output=tools.jackson.databind.json.JsonMapper.builder().build().writeValueAsString(page);
                assertFalse(output.contains(p.employee()));assertFalse(output.contains("actor_user_id"));
                assertFalse(output.contains("employeeId"));assertNull(page.nextCursor());
                assertThrows(UnsupportedOperationException.class,()->page.entries().clear());
                status.setRollbackOnly();return null;
            });
        }
    }

    @Test void protectedHistoryUsesStableHighWaterWithoutDuplicatesWhenNewEventsAppend() throws Exception {
        var p=projection();commitCreate(p);commitCreate(peer(p));commitCreate(peer(p));
        var initial=historyPage(employeeHistory(p,100,null));
        var first=historyPage(employeeHistory(p,1,null));assertTrue(first.hasNext());
        var appended=commitCreate(peer(p));
        var seen=new java.util.ArrayList<String>();seen.add(first.entries().get(0).eventId());
        String cursor=first.nextCursor();
        while(cursor!=null) {
            var next=historyPage(employeeHistory(p,1,cursor));
            assertEquals(1,next.entries().size());seen.add(next.entries().get(0).eventId());cursor=next.nextCursor();
            assertTrue(seen.size()<=3);
        }
        assertEquals(initial.entries().stream().map(e->e.eventId()).toList(),seen);
        assertFalse(seen.contains(appended.toString()));assertEquals(3,new java.util.HashSet<>(seen).size());
        assertEquals(4,historyPage(employeeHistory(p,100,null)).entries().size());
    }

    @Test void protectedHistoryCursorRejectsSelectorLimitHighWaterDigestAndNoncanonicalEncodingChanges() throws Exception {
        var p=projection();commitCreate(p);commitCreate(peer(p));
        String cursor=historyPage(employeeHistory(p,1,null)).nextCursor();
        var parts=new String(java.util.Base64.getUrlDecoder().decode(cursor),StandardCharsets.US_ASCII).split("\\|",-1);
        for(int variant=0;variant<5;variant++) {
            var changed=parts.clone();String forged;
            if(variant==0)changed[1]=UUID.randomUUID().toString();
            if(variant==1)changed[5]="0".repeat(64);
            if(variant==2)changed[0]="0".repeat(64);
            forged=variant==3?cursor+"=":java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(
                    String.join("|",changed).getBytes(StandardCharsets.US_ASCII));
            var query=employeeHistory(p,variant==4?2:1,forged);
            assertThrows(AccessDeniedException.class,()->historyPage(query));
        }
        var query=dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History.assignment(
                p.company(),p.id(),1,cursor);
        assertThrows(AccessDeniedException.class,()->historyPage(query));
    }

    @Test void protectedExactVersionRetainsBeforeAndSuccessorPinsAfterCorrectionAndTransfer() throws Exception {
        for(String kind:List.of("CORRECT","TRANSFER")) {
            var p=projection();var destination=historicalDestination(p);
            commitMove(p,destination,kind);
            for(long version:List.of(1L,2L)) {
                var query=new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.ExactVersion(p.company(),p.id(),version);
                try(var f=new StorageFixture(query)) {
                    f.tx.execute(status -> {
                        var ticket=f.acquire();f.storage.contribute(ticket);var value=f.storage.exactVersion(ticket,query);
                        assertEquals(version,value.appointmentVersion());assertEquals(p.id(),value.assignmentId());
                        assertEquals(version==1 || kind.equals("TRANSFER")?p.binding():destination.binding(),value.snapshot().binding().id());
                        status.setRollbackOnly();return null;
                    });
                }
            }
            var page=historyPage(employeeHistory(p,20,null));assertEquals(2,page.entries().size());
            if(kind.equals("TRANSFER")) {
                var transfer=page.entries().stream().filter(e->e.successor()!=null).findFirst().orElseThrow();
                assertEquals(destination.binding(),transfer.successor().snapshot().binding().id());
                var successor=new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.ExactVersion(
                        p.company(),destination.id(),1);
                try(var f=new StorageFixture(successor)) {
                    f.tx.execute(status -> {
                        var ticket=f.acquire();f.storage.contribute(ticket);var value=f.storage.exactVersion(ticket,successor);
                        assertEquals(destination.id(),value.assignmentId());assertEquals(destination.binding(),value.snapshot().binding().id());
                        status.setRollbackOnly();return null;
                    });
                }
            }
        }
    }

    @Test void protectedHistoryRequiresWholeClosureAndCannotBorrowPolicyOrExactAuthority() throws Exception {
        var p=projection();var destination=historicalDestination(p);
        commitMove(p,destination,"TRANSFER");
        var query=employeeHistory(p,1,null);
        try(var f=new StorageFixture(query)) {
            f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);
                assertEquals(historicalPairs(p,destination),f.storage.preview(query).companyUnits());
                assertThrows(AccessDeniedException.class,()->f.storage.positionHistory(ticket,
                        dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History.position(
                                p.company(),p.unit(),p.position(),1,null)));
                assertTrue(f.holder().isRollbackOnly());status.setRollbackOnly();return null;
            });
        }
        try(var f=new StorageFixture(policyQuery(p.company(),p.unit()))) {
            f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);
                assertThrows(AccessDeniedException.class,()->f.storage.privateHistory(ticket,query));
                status.setRollbackOnly();return null;
            });
        }
        var exact=new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.ExactVersion(p.company(),p.id(),1);
        try(var f=new StorageFixture(exact)) {
            f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);
                assertThrows(AccessDeniedException.class,()->f.storage.privateHistory(ticket,query));
                status.setRollbackOnly();return null;
            });
        }
    }

    @Test void protectedHistoryRetainsImmutableOwnershipAfterPartyRetirementAndCompanyRebinding() throws Exception {
        var p=projection();commitCreate(p);String other=company("ACTIVE");
        try(var c=login(CONFIG)){binding(c,p.binding(),p.unit(),other,1);c.commit();}
        admin("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",p.company());
        var page=historyPage(employeeHistory(p,20,null));assertEquals(p.company(),page.entries().get(0).after().companyPartyId());
        assertEquals(1,page.entries().get(0).after().snapshot().binding().revision());
        var foreign=dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.History.assignment(other,p.id(),1,null);
        assertThrows(AccessDeniedException.class,()->historyPage(foreign));
    }

    @Test void protectedHistoryRejectsRevokedOwnerAndChangedAuthenticationBeforeMaterialization() throws Exception {
        var p=projection();commitCreate(p);var query=employeeHistory(p,20,null);
        for(boolean revoke:List.of(false,true))try(var f=new StorageFixture(query)) {
            f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);
                if(revoke)org.mockito.Mockito.doThrow(new AccessDeniedException("synthetic revoke")).when(f.identity).requireCurrent(f.nativeTicket);
                else org.springframework.security.core.context.SecurityContextHolder.clearContext();
                assertThrows(AccessDeniedException.class,()->f.storage.privateHistory(ticket,query));
                assertTrue(f.holder().isRollbackOnly());status.setRollbackOnly();return null;
            });
        }
    }

    @Test void protectedHistoryWithoutItsOwnFinalGuardCannotPhysicallyCommit() throws Exception {
        var p=projection();commitCreate(p);var query=employeeHistory(p,20,null);
        try(var f=new StorageFixture(query)) {
            assertThrows(AccessDeniedException.class,()->f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);f.storage.privateHistory(ticket,query);return null;
            }));
        }
    }

    /** Real storage and commit; only native Identity and the protected executor are mocked. */
    private final class FinalPolicySession implements AutoCloseable {
        final StorageFixture f;
        final dz.sh.hidra.modules.organization.application.service.ProtectedEmployeeAppointmentAdmissionCoordinator coordinator;
        final dz.sh.hidra.modules.organization.application.service.ProtectedEmployeeAppointmentAdmissionCoordinator.Prepared prepared;
        Object read;
        FinalPolicySession(Projection p) throws Exception {
            f=new StorageFixture(policyQuery(p.company(),p.unit()));grantPolicyReadGuard(f);
            var port=org.mockito.Mockito.mock(dz.sh.hidra.modules.organization.application.port.out
                    .ProtectedEmployeeAppointmentRepositoryPort.class,call -> {
                try{return f.storage.getClass().getMethod(call.getMethod().getName(),call.getMethod().getParameterTypes())
                        .invoke(f.storage,call.getArguments());}
                catch(java.lang.reflect.InvocationTargetException failure){throw failure.getCause();}
            });
            org.mockito.Mockito.when(f.identity.prepare()).thenReturn(org.mockito.Mockito.mock(
                    dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Prepared.class));
            org.mockito.Mockito.when(f.identity.acquire(org.mockito.ArgumentMatchers.any(),org.mockito.ArgumentMatchers.any()))
                    .thenAnswer(call -> {f.jdbc.execute("SELECT "+f.lockFunction+"()");return f.nativeTicket;});
            coordinator=new dz.sh.hidra.modules.organization.application.service.ProtectedEmployeeAppointmentAdmissionCoordinator(
                    f.identity,org.mockito.Mockito.mock(dz.sh.hidra.modules.party.application.contract.organization
                            .OrganizationProtectedPartyEligibilityContract.class),port,
                    org.mockito.Mockito.mock(dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor.class));
            prepared=coordinator.prepare();
        }
        Object invoke(String name,Object... args) {
            try {
                var method=java.util.Arrays.stream(coordinator.getClass().getDeclaredMethods())
                        .filter(m -> m.getName().equals(name)).findFirst().orElseThrow();
                method.setAccessible(true);return method.invoke(coordinator,args);
            }catch(java.lang.reflect.InvocationTargetException failure){
                if(failure.getCause() instanceof RuntimeException runtime)throw runtime;
                throw new AssertionError(failure.getCause());
            }catch(ReflectiveOperationException failure){throw new AssertionError(failure);}
        }
        void prepare() {
            f.jdbc.execute("SET LOCAL TIME ZONE 'UTC'");
            read=invoke("acquireRead",prepared,f.policyQuery);invoke("prepareFinalRead",read);
        }
        @Override public void close(){f.close();}
    }

    @Test void javaFinalPolicyCallbacksCommitWithoutOwnerQueriesPartyOrProofWrites() throws Exception {
        var p=projection();admin("UPDATE public.hidra_party_party SET status='SUSPENDED' WHERE id=?",p.company());
        long events=count("SELECT count(*) FROM public.hidra_org_appointment_event");
        long receipts=count("SELECT count(*) FROM public.hidra_org_appointment_receipt");
        long uses=count("SELECT count(*) FROM public.hidra_identity_org_commit_proof_use");
        long keys=count("SELECT count(*) FROM public.hidra_org_appointment_lock_key");
        try(var session=new FinalPolicySession(p)) {
            session.f.tx.execute(status -> {
                session.prepare();org.mockito.Mockito.clearInvocations(session.f.identity);
                session.invoke("finalizeRead",session.read);
                org.mockito.Mockito.verifyNoInteractions(session.f.identity);
                return null; // Actual physical commit, no post-GUARD SQL.
            });
        }
        assertEquals(events,count("SELECT count(*) FROM public.hidra_org_appointment_event"));
        assertEquals(receipts,count("SELECT count(*) FROM public.hidra_org_appointment_receipt"));
        assertEquals(uses,count("SELECT count(*) FROM public.hidra_identity_org_commit_proof_use"));
        assertEquals(keys,count("SELECT count(*) FROM public.hidra_org_appointment_lock_key"));
    }

    @Test void javaFinalPolicyPreparationWithoutGuardVetoesPhysicalCommit() throws Exception {
        try(var session=new FinalPolicySession(projection())) {
            assertThrows(AccessDeniedException.class,() -> session.f.tx.execute(status -> {session.prepare();return null;}));
        }
    }

    @Test void javaFinalPolicyGuardRejectsUnsafePrivilegeGrantedAfterPreparationAndCannotRetry() throws Exception {
        try(var session=new FinalPolicySession(projection())) {
            session.f.tx.execute(status -> {
                session.prepare();
                try{admin("GRANT INSERT ON public.hidra_org_appointment_receipt TO "+session.f.role);}
                catch(SQLException failure){throw new AssertionError(failure);}
                assertThrows(AccessDeniedException.class,() -> session.invoke("finalizeRead",session.read));
                assertTrue(session.f.holder().isRollbackOnly());
                assertThrows(AccessDeniedException.class,() -> session.invoke("finalizeRead",session.read));
                status.setRollbackOnly();return null;
            });
        }
    }

    @Test void javaFinalPolicyGuardRejectsAuthenticationReplacementAndPostGuardQueries() throws Exception {
        for(boolean afterGuard:List.of(false,true))try(var session=new FinalPolicySession(projection())) {
            session.f.tx.execute(status -> {
                session.prepare();
                if(afterGuard) {
                    session.invoke("finalizeRead",session.read);
                    assertThrows(AccessDeniedException.class,() -> session.invoke("requireCurrentRead",session.read));
                } else {
                    org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(
                            new org.springframework.security.authentication.UsernamePasswordAuthenticationToken("replacement","unused",List.of()));
                    assertThrows(AccessDeniedException.class,() -> session.invoke("finalizeRead",session.read));
                }
                assertTrue(session.f.holder().isRollbackOnly());status.setRollbackOnly();return null;
            });
        }
    }

    @Test void javaFinalPolicyCallbacksRejectDirectStorageAndRepeatGuardInvocations() throws Exception {
        var p=projection();
        for(boolean guard:List.of(false,true))try(var f=new StorageFixture(policyQuery(p.company(),p.unit()))) {
            f.tx.execute(status -> {
                var ticket=f.acquire();f.storage.contribute(ticket);f.storage.readPolicy(ticket,f.policyQuery);
                assertThrows(AccessDeniedException.class,() -> {
                    if(guard)f.storage.finalizeRead(ticket);else f.storage.prepareFinalRead(ticket);
                });
                assertTrue(f.holder().isRollbackOnly());status.setRollbackOnly();return null;
            });
        }
        try(var session=new FinalPolicySession(p)) {
            session.f.tx.execute(status -> {
                session.prepare();session.invoke("finalizeRead",session.read);
                assertThrows(AccessDeniedException.class,() -> session.invoke("finalizeRead",session.read));
                assertTrue(session.f.holder().isRollbackOnly());status.setRollbackOnly();return null;
            });
        }
        var field=JdbcProtectedEmployeeAppointmentRepositoryAdapter.class.getDeclaredField("READ_GUARD");
        field.setAccessible(true);assertEquals(READ_GUARD_SQL,field.get(null));
    }

    @Test void javaFinalPolicyPreparationRejectsRevokedNativeOwnerBeforeFreezing() throws Exception {
        try(var session=new FinalPolicySession(projection())) {
            session.f.tx.execute(status -> {
                session.f.jdbc.execute("SET LOCAL TIME ZONE 'UTC'");
                session.read=session.invoke("acquireRead",session.prepared,session.f.policyQuery);
                org.mockito.Mockito.doThrow(new AccessDeniedException("synthetic revoked"))
                        .when(session.f.identity).requireCurrent(session.f.nativeTicket);
                assertThrows(AccessDeniedException.class,() -> session.invoke("prepareFinalRead",session.read));
                assertTrue(session.f.holder().isRollbackOnly());status.setRollbackOnly();return null;
            });
        }
    }
}
