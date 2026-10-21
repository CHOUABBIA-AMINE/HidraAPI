/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationConfigurationReferencePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence
 *
 * @Description : Proves the administrative SQL owner export with actual isolated logins and transactions.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers(disabledWithoutDocker = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrganizationConfigurationReferencePostgresIntegrationTest {
    private static final String FUNCTION = "public.hidra_party_require_organization_configuration_reference";
    private static final String SIGNATURE = FUNCTION + "(text)";
    private static final String ROOT = "public.hidra_party_party";
    private static final String GEN = "public.hidra_party_eligibility_generation";
    private static final String CONFIG = "cfg_reference", ORDINARY = "cfg_ordinary", PUBLISHER = "cfg_publisher";
    private static final String WRITER = "cfg_party_writer", PASSWORD = "synthetic-disposable-fixture";
    private static final String DENIED = "Organization configuration reference unavailable";
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16");
    private String partyType;

    record Observation(String partyId, long generation, int backendPid, long transactionId) {}

    @BeforeAll void migrateAndSeparateActualLogins() throws Exception {
        // Exercise the additive upgrade from the published 001 version, retaining its history/checksum.
        Flyway.configure().dataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())
                .target("20261021.001").load().migrate();
        long originalChecksum = number(catalog("SELECT checksum FROM public.flyway_schema_history WHERE script='V20261021_001__party_organization_configuration_reference.sql'"));
        var upgrade = Flyway.configure().dataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword()).load().migrate();
        assertTrue(upgrade.migrationsExecuted >= 1);
        assertEquals(1, number(catalog("SELECT count(*) FROM public.flyway_schema_history WHERE success AND script='V20261021_002__party_configuration_reference_collation_guard.sql'")));
        assertEquals(originalChecksum, number(catalog("SELECT checksum FROM public.flyway_schema_history WHERE script='V20261021_001__party_organization_configuration_reference.sql'")));
        for (String role : List.of(CONFIG, ORDINARY, PUBLISHER, WRITER)) {
            admin("CREATE ROLE " + role + " LOGIN PASSWORD '" + PASSWORD + "'");
            admin("GRANT USAGE ON SCHEMA public TO " + role);
        }
        // Sole explicit configuration grant. Ordinary/publisher roles never receive it.
        admin("GRANT EXECUTE ON FUNCTION " + SIGNATURE + " TO " + CONFIG);
        admin("GRANT SELECT,UPDATE,DELETE ON " + ROOT + " TO " + WRITER);
        partyType = "cfg-type-" + UUID.randomUUID();
        adminUpdate("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at) VALUES (?,?,'ACTIVE',now(),now())", partyType, partyType);
    }

    private Connection administrator() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }
    private Connection login(String role) throws SQLException {
        Connection connection = DriverManager.getConnection(POSTGRES.getJdbcUrl(), role, PASSWORD);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        connection.setAutoCommit(false);
        return connection;
    }
    private static void execute(Connection connection, String sql) throws SQLException {
        try (var statement = connection.createStatement()) { statement.execute(sql); }
    }
    private void admin(String sql) throws SQLException { try (Connection connection = administrator()) { execute(connection, sql); } }
    private static void parameters(PreparedStatement statement, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) statement.setObject(i + 1, values[i]);
    }
    private int adminUpdate(String sql, Object... values) throws SQLException {
        try (Connection connection = administrator(); var statement = connection.prepareStatement(sql)) {
            parameters(statement, values); return statement.executeUpdate();
        }
    }
    private static Object scalar(Connection connection, String sql, Object... values) throws SQLException {
        try (var statement = connection.prepareStatement(sql)) {
            parameters(statement, values);
            try (var rows = statement.executeQuery()) {
                assertTrue(rows.next()); Object result = rows.getObject(1); assertFalse(rows.next()); return result;
            }
        }
    }
    private Object catalog(String sql, Object... values) throws SQLException {
        try (Connection connection = administrator()) { return scalar(connection, sql, values); }
    }
    private static long number(Object value) { return ((Number) value).longValue(); }
    private String fixture(String status) throws SQLException {
        String id = "cfg-" + UUID.randomUUID(); insert(id, status); return id;
    }
    private void insert(String id, String status) throws SQLException {
        adminUpdate("INSERT INTO " + ROOT + " (id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES (?,?,?,'Synthetic reference','DZ',?,now(),now())", id, id, partyType, status);
    }
    private long generation(String id) throws SQLException { return number(catalog("SELECT generation FROM " + GEN + " WHERE party_id=?", id)); }
    private static Observation call(Connection connection, String id) throws SQLException { return call(connection, FUNCTION, id); }
    private static Observation call(Connection connection, String function, String id) throws SQLException {
        try (var statement = connection.prepareStatement("SELECT party_id,generation,backend_pid,transaction_id FROM " + function + "(?::text)")) {
            statement.setString(1, id);
            try (ResultSet rows = statement.executeQuery()) {
                assertTrue(rows.next());
                var result = new Observation(rows.getString(1), rows.getLong(2), rows.getInt(3), rows.getLong(4));
                assertFalse(rows.next()); return result;
            }
        }
    }
    private Observation observe(String id) throws SQLException {
        try (Connection connection = login(CONFIG)) { var result = call(connection, id); connection.commit(); return result; }
    }
    private static SQLException rejected(Connection connection, String id) {
        var failure = assertThrows(SQLException.class, () -> call(connection, id));
        assertEquals("23514", failure.getSQLState()); assertTrue(failure.getMessage().contains(DENIED)); return failure;
    }
    private void reject(String id) throws SQLException {
        try (Connection connection = login(CONFIG)) { rejected(connection, id); connection.rollback(); }
    }
    private static String quote(String id) { return "\"" + id.replace("\"", "\"\"") + "\""; }

    @Test void completeFreshChainAndExactRestrictedFunctionCatalog() throws Exception {
        var flyway = Flyway.configure().dataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword()).load();
        flyway.validate();
        try (var scripts = Files.list(Path.of("src/main/resources/db/migration"))) {
            long count = scripts.filter(p -> p.toString().endsWith(".sql")).count();
            assertEquals(count, number(catalog("SELECT count(*) FROM public.flyway_schema_history WHERE success AND type='SQL'")));
        }
        assertEquals(Boolean.TRUE, catalog("""
            SELECT prokind='f' AND pronargs=1 AND proargtypes::text='25' AND prorettype='record'::regtype
              AND proretset AND prosecdef AND NOT proisstrict AND NOT proleakproof
              AND provolatile='v' AND proparallel='u' AND prolang=(SELECT oid FROM pg_language WHERE lanname='plpgsql')
              AND proconfig=ARRAY['search_path=pg_catalog, pg_temp']
              AND proowner=(SELECT relowner FROM pg_class WHERE oid='public.hidra_party_party'::regclass)
              AND proowner=(SELECT relowner FROM pg_class WHERE oid='public.hidra_party_eligibility_generation'::regclass)
            FROM pg_proc WHERE oid=?::regprocedure
            """, SIGNATURE));
        assertEquals(0, number(catalog("SELECT count(*) FROM pg_proc p CROSS JOIN LATERAL aclexplode(coalesce(p.proacl,acldefault('f',p.proowner))) a WHERE p.oid=?::regprocedure AND a.grantee=0 AND a.privilege_type='EXECUTE'", SIGNATURE)));
        for (String role : List.of(ORDINARY, PUBLISHER)) assertEquals(Boolean.FALSE, catalog("SELECT has_function_privilege(?,?::regprocedure,'EXECUTE')", role, SIGNATURE));
        assertEquals(Boolean.TRUE, catalog("SELECT has_function_privilege(?,?::regprocedure,'EXECUTE')", CONFIG, SIGNATURE));
    }

    @Test void originalPartyTriggerDefinitionsAndBodiesArePreserved() throws Exception {
        String migration = Files.readString(Path.of("src/main/resources/db/migration/V20261018_001__p25_party_eligibility_generations.sql"));
        var matcher = Pattern.compile("AS \\$owner\\$(.*?)\\$owner\\$;", Pattern.DOTALL).matcher(migration);
        var names = List.of("public.hidra_p25_party_eligibility_write()", "public.hidra_p25_party_eligibility_truncate()");
        for (String name : names) {
            assertTrue(matcher.find());
            assertEquals(matcher.group(1), catalog("SELECT prosrc FROM pg_proc WHERE oid=?::regprocedure", name));
        }
        assertFalse(matcher.find());
        assertEquals(3, number(catalog("SELECT count(*) FROM pg_trigger WHERE tgrelid IN ('public.hidra_party_party'::regclass,'public.hidra_party_eligibility_generation'::regclass) AND NOT tgisinternal")));
        assertEquals(Boolean.TRUE, catalog("SELECT bool_and(tgenabled='O') FROM pg_trigger WHERE tgrelid IN ('public.hidra_party_party'::regclass,'public.hidra_party_eligibility_generation'::regclass) AND NOT tgisinternal"));
        // Compare the whole new routine body independently to its actual additive migration.
        String source = Files.readString(Path.of("src/main/resources/db/migration/V20261021_002__party_configuration_reference_collation_guard.sql"));
        String body = source.split("AS \\$configuration_reference\\$", 2)[1].split("\\$configuration_reference\\$;", 2)[0];
        String actual = (String) catalog("SELECT prosrc FROM pg_proc WHERE oid=?::regprocedure", SIGNATURE);
        assertEquals(HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(body.getBytes(StandardCharsets.UTF_8))),
                HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(actual.getBytes(StandardCharsets.UTF_8))));
    }

    @Test void activeReferenceReturnsExactOneRowShapeAndPhysicalCoordinates() throws Exception {
        String id = fixture("ACTIVE"); long before = generation(id);
        try (Connection connection = login(CONFIG); var statement = connection.prepareStatement("SELECT * FROM " + FUNCTION + "(?::text)")) {
            assertEquals(CONFIG, scalar(connection, "SELECT session_user"));
            statement.setString(1, id);
            try (var rows = statement.executeQuery()) {
                assertEquals(4, rows.getMetaData().getColumnCount());
                var labels = new ArrayList<String>(); var types = new ArrayList<String>();
                for (int i = 1; i <= 4; i++) { labels.add(rows.getMetaData().getColumnLabel(i)); types.add(rows.getMetaData().getColumnTypeName(i)); }
                assertEquals(List.of("party_id", "generation", "backend_pid", "transaction_id"), labels);
                assertEquals(List.of("varchar", "int8", "int4", "int8"), types);
                assertTrue(rows.next()); assertEquals(id, rows.getString(1)); assertEquals(before, rows.getLong(2));
                int pid = rows.getInt(3); long xid = rows.getLong(4); assertTrue(pid > 0); assertTrue(xid > 0);
                assertEquals(pid, number(scalar(connection, "SELECT pg_backend_pid()")));
                assertEquals(xid, number(scalar(connection, "SELECT txid_current()"))); assertFalse(rows.next());
            }
            connection.commit();
        }
        assertEquals(before, generation(id));
    }

    @Test void explicitConfigurationGrantNeedsNoDirectPartyTablePrivileges() throws Exception {
        String id = fixture("ACTIVE");
        for (String table : List.of(ROOT, GEN)) assertEquals(Boolean.FALSE, catalog("SELECT has_table_privilege(?,?,'SELECT,INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')", CONFIG, table));
        assertEquals(id, observe(id).partyId());
        try (Connection connection = login(CONFIG)) {
            assertEquals("42501", assertThrows(SQLException.class, () -> scalar(connection, "SELECT count(*) FROM " + GEN)).getSQLState());
            connection.rollback();
        }
    }

    @Test void ordinaryPublisherAndPublicExecutionAreDeniedBeforeSelectorDisclosure() throws Exception {
        String id = fixture("ACTIVE");
        for (String role : List.of(ORDINARY, PUBLISHER)) {
            for (String selector : List.of(id, "cfg-missing")) try (Connection connection = login(role)) {
                assertEquals(role, scalar(connection, "SELECT session_user"));
                assertEquals("42501", assertThrows(SQLException.class, () -> call(connection, selector)).getSQLState());
                connection.rollback();
            }
        }
    }

    @Test void publicExecuteGrantCannotCreateConfigurationAuthority() throws Exception {
        String id = fixture("ACTIVE");
        admin("GRANT EXECUTE ON FUNCTION " + SIGNATURE + " TO PUBLIC");
        try {
            for (String role : List.of(CONFIG, ORDINARY, PUBLISHER)) try (Connection connection = login(role)) {
                rejected(connection, id); connection.rollback();
            }
        } finally { admin("REVOKE EXECUTE ON FUNCTION " + SIGNATURE + " FROM PUBLIC"); }
        assertEquals(id, observe(id).partyId());
    }

    @Test void missingInactiveAndMalformedIdentifiersRejectUniformly() throws Exception {
        reject("cfg-missing"); reject(fixture("SUSPENDED"));
        String active = fixture("ACTIVE"); reject(active.toUpperCase(java.util.Locale.ROOT));
        assertEquals(active, observe(active).partyId());
        for (String id : new String[] {null, "", " ", " cfg", "cfg ", "cfg\n", "cfg\u0085", "\u00a0cfg", "cfg\u2007", "cfg\u202f", "x".repeat(81)}) reject(id);
        String exact = "cfg-" + "x".repeat(76); assertEquals(80, exact.length()); insert(exact, "ACTIVE");
        assertEquals(exact, observe(exact).partyId());
    }

    @Test void nullMissingAndNonliveGenerationCannotProduceAnObservation() throws Exception {
        String absent = fixture("ACTIVE"), nonlive = fixture("ACTIVE");
        adminUpdate("DELETE FROM " + GEN + " WHERE party_id=?", absent);
        adminUpdate("UPDATE " + GEN + " SET present=false WHERE party_id=?", nonlive);
        reject(absent); reject(nonlive);
        assertEquals("23514", assertThrows(SQLException.class, () -> adminUpdate("UPDATE " + GEN + " SET generation=0 WHERE party_id=?", nonlive)).getSQLState());
        assertEquals("23502", assertThrows(SQLException.class, () -> adminUpdate("UPDATE " + GEN + " SET generation=NULL WHERE party_id=?", nonlive)).getSQLState());
        assertEquals(Boolean.FALSE, catalog("SELECT attnotnull=false FROM pg_attribute WHERE attrelid='public.hidra_party_eligibility_generation'::regclass AND attname='generation'"));
    }

    @Test void readOnlyAndOtherIsolationLevelsReject() throws Exception {
        String id = fixture("ACTIVE");
        try (Connection connection = login(CONFIG)) { connection.setReadOnly(true); rejected(connection, id); connection.rollback(); }
        for (int isolation : new int[] {Connection.TRANSACTION_REPEATABLE_READ, Connection.TRANSACTION_SERIALIZABLE}) {
            try (Connection connection = login(CONFIG)) { connection.setTransactionIsolation(isolation); rejected(connection, id); connection.rollback(); }
        }
    }

    @Test void unsafeOwnerMembershipAndElevatedLoginsReject() throws Exception {
        String id = fixture("ACTIVE");
        try (Connection connection = administrator()) {
            connection.setAutoCommit(false); rejected(connection, id); connection.rollback();
        }
        admin("GRANT " + quote(POSTGRES.getUsername()) + " TO " + CONFIG);
        try { reject(id); } finally { admin("REVOKE " + quote(POSTGRES.getUsername()) + " FROM " + CONFIG); }
        for (String attribute : List.of("CREATEDB", "CREATEROLE", "BYPASSRLS", "REPLICATION")) {
            admin("ALTER ROLE " + CONFIG + " " + attribute);
            try { reject(id); } finally { admin("ALTER ROLE " + CONFIG + " NO" + attribute); }
        }
        assertEquals(id, observe(id).partyId());
    }

    @Test void unsafeSchemaAndInheritedDataPrivilegesReject() throws Exception {
        String id = fixture("ACTIVE");
        admin("GRANT CREATE ON SCHEMA public TO " + CONFIG);
        try { reject(id); } finally { admin("REVOKE CREATE ON SCHEMA public FROM " + CONFIG); }
        admin("CREATE ROLE cfg_reference_mutator");
        admin("GRANT UPDATE(generation) ON " + GEN + " TO cfg_reference_mutator");
        admin("GRANT cfg_reference_mutator TO " + CONFIG);
        try { reject(id); } finally { admin("REVOKE cfg_reference_mutator FROM " + CONFIG); }
        admin("GRANT UPDATE(status) ON " + ROOT + " TO " + CONFIG);
        try { reject(id); } finally { admin("REVOKE UPDATE(status) ON " + ROOT + " FROM " + CONFIG); }
        assertEquals(id, observe(id).partyId());
    }

    @Test void sessionRoleSwitchCannotMasqueradeAsOriginalConfigurationLogin() throws Exception {
        String id = fixture("ACTIVE"); admin("CREATE ROLE cfg_reference_alternate");
        // Let execution reach the body: otherwise SET ROLE loses EXECUTE and fails earlier with 42501.
        admin("GRANT USAGE ON SCHEMA public TO cfg_reference_alternate");
        admin("GRANT EXECUTE ON FUNCTION " + SIGNATURE + " TO cfg_reference_alternate");
        admin("GRANT cfg_reference_alternate TO " + CONFIG);
        assertEquals(id, observe(id).partyId());
        try (Connection connection = login(CONFIG)) {
            execute(connection, "SET LOCAL ROLE cfg_reference_alternate");
            assertEquals(CONFIG, scalar(connection, "SELECT session_user"));
            assertEquals("cfg_reference_alternate", scalar(connection, "SELECT current_user"));
            assertEquals(Boolean.TRUE, scalar(connection, "SELECT has_function_privilege(current_user,?::regprocedure,'EXECUTE')", SIGNATURE));
            rejected(connection, id); connection.rollback();
        } finally {
            admin("REVOKE cfg_reference_alternate FROM " + CONFIG);
            admin("REVOKE EXECUTE ON FUNCTION " + SIGNATURE + " FROM cfg_reference_alternate");
        }
        assertEquals(id, observe(id).partyId());
    }

    @Test void securityDefinerWrapperCannotLaunderUnauthorizedOriginalLogin() throws Exception {
        String id = fixture("ACTIVE"); String wrapper = "public.cfg_reference_wrapper";
        admin("CREATE FUNCTION " + wrapper + "(text) RETURNS TABLE(party_id varchar,generation bigint,backend_pid integer,transaction_id bigint) LANGUAGE sql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS 'SELECT * FROM " + FUNCTION + "($1)'");
        admin("REVOKE ALL ON FUNCTION " + wrapper + "(text) FROM PUBLIC");
        admin("GRANT EXECUTE ON FUNCTION " + wrapper + "(text) TO " + ORDINARY + "," + PUBLISHER + "," + CONFIG);
        try {
            for (String role : List.of(ORDINARY, PUBLISHER)) try (Connection connection = login(role)) {
                var failure = assertThrows(SQLException.class, () -> call(connection, wrapper, id));
                assertEquals("23514", failure.getSQLState()); assertTrue(failure.getMessage().contains(DENIED)); connection.rollback();
            }
            // Real intended use: an authorized login through an owner procedure still works.
            try (Connection connection = login(CONFIG)) {
                var result = call(connection, wrapper, id); assertEquals(id, result.partyId());
                assertEquals(number(scalar(connection, "SELECT txid_current()")), result.transactionId()); connection.commit();
            }
        } finally { admin("DROP FUNCTION " + wrapper + "(text)"); }
    }

    @Test void withdrawnExecuteGrantCannotReuseAnEarlierObservation() throws Exception {
        String id = fixture("ACTIVE"); var prior = observe(id);
        admin("REVOKE EXECUTE ON FUNCTION " + SIGNATURE + " FROM " + CONFIG);
        try (Connection connection = login(CONFIG)) {
            assertEquals("42501", assertThrows(SQLException.class, () -> call(connection, prior.partyId())).getSQLState());
            connection.rollback();
        } finally { admin("GRANT EXECUTE ON FUNCTION " + SIGNATURE + " TO " + CONFIG); }
        assertEquals(prior.generation(), observe(id).generation());
    }

    @Test void committedStatusAndDeleteReinsertAbaAdvanceFreshObservations() throws Exception {
        String id = fixture("ACTIVE"); var before = observe(id);
        adminUpdate("UPDATE " + ROOT + " SET status='SUSPENDED' WHERE id=?", id);
        adminUpdate("UPDATE " + ROOT + " SET status='ACTIVE' WHERE id=?", id);
        var afterStatus = observe(id); assertEquals(before.generation() + 2, afterStatus.generation());
        assertNotEquals(before.transactionId(), afterStatus.transactionId());
        adminUpdate("DELETE FROM " + ROOT + " WHERE id=?", id); insert(id, "ACTIVE");
        var afterReplacement = observe(id); assertEquals(afterStatus.generation() + 2, afterReplacement.generation());
        assertNotEquals(afterStatus.transactionId(), afterReplacement.transactionId());
        // Each result came from a fresh actual invocation; no scalar result is an input credential.
        assertEquals(before.partyId(), afterReplacement.partyId());
    }

    @Test void repeatedCallsKeepPhysicalCoordinatesWithoutWritingPartyOrGeneration() throws Exception {
        String id = fixture("ACTIVE"); long before = generation(id);
        try (Connection connection = login(CONFIG)) {
            var first = call(connection, id); assertEquals(first, call(connection, id)); connection.rollback();
        }
        assertEquals(before, generation(id)); assertEquals("ACTIVE", catalog("SELECT status FROM " + ROOT + " WHERE id=?", id));
        assertEquals(before, observe(id).generation());
    }

    @Test void retainedOwnerLocksExcludeIndependentStatusAndDeleteWritersUntilRollback() throws Exception {
        String id = fixture("ACTIVE"); long before = generation(id);
        try (Connection connection = login(CONFIG)) {
            var result = call(connection, id);
            assertEquals(2, number(scalar(connection, "SELECT count(*) FROM pg_locks WHERE pid=pg_backend_pid() AND granted AND locktype='relation' AND ((relation='public.hidra_party_party'::regclass AND mode='ShareRowExclusiveLock') OR (relation='public.hidra_party_eligibility_generation'::regclass AND mode='AccessShareLock'))")));
            for (String sql : List.of("UPDATE " + ROOT + " SET status='SUSPENDED' WHERE id=?", "DELETE FROM " + ROOT + " WHERE id=?")) {
                try (Connection writer = login(WRITER); var statement = writer.prepareStatement(sql)) {
                    execute(writer, "SET LOCAL lock_timeout='250ms'"); statement.setString(1, id);
                    assertEquals("55P03", assertThrows(SQLException.class, statement::executeUpdate).getSQLState()); writer.rollback();
                }
            }
            assertEquals(result, call(connection, id)); connection.rollback();
        }
        assertEquals(before, generation(id));
        try (Connection writer = login(WRITER); var statement = writer.prepareStatement("UPDATE " + ROOT + " SET status='SUSPENDED' WHERE id=?")) {
            statement.setString(1, id); assertEquals(1, statement.executeUpdate()); writer.commit();
        }
        assertEquals(before + 1, generation(id)); reject(id);
    }

    @Test void writerFirstPreventsAdmissionForExistingAndAbsentSelectors() throws Exception {
        String id = fixture("ACTIVE"); long before = generation(id);
        try (Connection writer = login(WRITER); var statement = writer.prepareStatement("UPDATE " + ROOT + " SET status='ACTIVE' WHERE id=?")) {
            statement.setString(1, id); assertEquals(1, statement.executeUpdate());
            reject(id); reject("cfg-absent-lock-probe"); writer.rollback();
        }
        assertEquals(before, observe(id).generation());
    }

    @Test void disabledTriggerAndChangedGuardBodyAreRejected() throws Exception {
        String id = fixture("ACTIVE");
        admin("ALTER TABLE " + ROOT + " DISABLE TRIGGER trg_p25_party_eligibility_write");
        try { reject(id); } finally { admin("ALTER TABLE " + ROOT + " ENABLE TRIGGER trg_p25_party_eligibility_write"); }
        String definition = (String) catalog("SELECT pg_get_functiondef('public.hidra_p25_party_eligibility_write()'::regprocedure)");
        admin("CREATE OR REPLACE FUNCTION public.hidra_p25_party_eligibility_write() RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $$ BEGIN RETURN NULL; END; $$");
        try { reject(id); } finally { admin(definition); }
        assertEquals(id, observe(id).partyId());
    }

    @Test void rlsWrongColumnShapeAndMissingPositiveConstraintAreRejected() throws Exception {
        String id = fixture("ACTIVE");
        for (String table : List.of(ROOT, GEN)) {
            admin("ALTER TABLE " + table + " ENABLE ROW LEVEL SECURITY");
            try { reject(id); } finally { admin("ALTER TABLE " + table + " DISABLE ROW LEVEL SECURITY"); }
        }
        admin("ALTER TABLE " + GEN + " ALTER COLUMN party_id TYPE varchar(81)");
        try { reject(id); } finally { admin("ALTER TABLE " + GEN + " ALTER COLUMN party_id TYPE varchar(80)"); }
        admin("ALTER TABLE " + GEN + " ALTER COLUMN party_id TYPE varchar(80) COLLATE pg_catalog.\"C\"");
        try { reject(id); } finally { admin("ALTER TABLE " + GEN + " ALTER COLUMN party_id TYPE varchar(80) COLLATE pg_catalog.\"default\""); }
        admin("ALTER TABLE " + GEN + " DROP CONSTRAINT ck_p25_party_eligibility_positive");
        try { reject(id); } finally { admin("ALTER TABLE " + GEN + " ADD CONSTRAINT ck_p25_party_eligibility_positive CHECK (generation>0)"); }
        assertEquals(id, observe(id).partyId());
    }

    @Test void changedTableOwnerAndReplicaModeAreRejected() throws Exception {
        String id = fixture("ACTIVE"); admin("CREATE ROLE cfg_reference_other_owner");
        String oldOwner = (String) catalog("SELECT pg_get_userbyid(relowner) FROM pg_class WHERE oid='public.hidra_party_eligibility_generation'::regclass");
        admin("ALTER TABLE " + GEN + " OWNER TO cfg_reference_other_owner");
        try { reject(id); } finally { admin("ALTER TABLE " + GEN + " OWNER TO " + quote(oldOwner)); }
        admin("GRANT SET ON PARAMETER session_replication_role TO " + CONFIG);
        try (Connection connection = login(CONFIG)) {
            execute(connection, "SET LOCAL session_replication_role=replica"); rejected(connection, id); connection.rollback();
        } finally { admin("REVOKE SET ON PARAMETER session_replication_role FROM " + CONFIG); }
        assertEquals(id, observe(id).partyId());
    }
}
