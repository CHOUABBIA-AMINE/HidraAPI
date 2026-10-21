/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentLifecyclePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
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
    private static final List<String> TABLES = List.of("binding_revision", "binding_head", "policy_revision", "policy_head",
            "configuration_event", "lock_key", "event", "receipt");
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16");
    private String partyType, unitType;
    private String upgradeCompany, upgradeUnit, upgradeBinding, upgradePolicy;
    private long originalConfigurationChecksum;

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
        assertEquals(0,count("SELECT count(*) FROM public.hidra_org_appointment_event"));
        assertEquals(0,count("SELECT count(*) FROM public.hidra_org_appointment_receipt"));
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
}
