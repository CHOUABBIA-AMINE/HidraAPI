/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * BoundSourceClaimEvidencePostgresIntegrationTest @CreatedOn : 2025-06-26 @UpdatedOn :
 * 2026-10-17 @Type : Class @Layer : Identity Test @Module : identity @Package :
 * dz.sh.hidra.modules.identity.infrastructure.persistence @Description : Verifies actual PostgreSQL
 * issuance companions, privilege separation and competing writer fences.
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;

import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.*;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.nio.file.*;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

/** Real PostgreSQL structural proof; fake fixture tags are never producer authority. */
@Testcontainers(disabledWithoutDocker = true)
class BoundSourceClaimEvidencePostgresIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16").withDatabaseName("hidra_claim_test");

    private static JdbcTemplate owner, app;
    private static DriverManagerDataSource appSource;
    private static Flyway flyway;
    private static final String P = "public.hidra_identity_";
    private static final String CLAIMS = P + "bound_source_claim_evidence";
    private static final String APP = "claim_issuance_app";
    private JdbcBoundSourceClaimIssuanceFence fence;
    private JdbcBoundSourceClaimEvidenceRepositoryAdapter claims;
    private JdbcOperationAuthenticationBindingRepositoryAdapter bindings;
    private ClaimNameCatalogPort catalog;
    private TransactionTemplate tx;
    private String user, provider, link, role, issuer;

    @BeforeAll
    static void migratedNonownerDatabase() {
        var ds =
                new DriverManagerDataSource(
                        POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        owner = new JdbcTemplate(ds);
        Flyway.configure().dataSource(ds).target("20261017.001").load().migrate();
        assertThat(
                        owner.queryForObject(
                                "SELECT"
                                    + " pg_get_functiondef('public.hidra_identity_bound_source_claim_guard()'::regprocedure)",
                                String.class))
                .contains("origin.authentication_type <> 'OIDC'");
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        owner.execute("CREATE ROLE " + APP + " LOGIN PASSWORD 'synthetic-claims-fixture-only'");
        owner.execute("REVOKE CREATE ON SCHEMA public FROM PUBLIC");
        owner.execute("GRANT USAGE ON SCHEMA public TO " + APP);
        owner.execute("GRANT SELECT ON ALL TABLES IN SCHEMA public TO " + APP);
        owner.execute("GRANT INSERT,UPDATE,DELETE ON ALL TABLES IN SCHEMA public TO " + APP);
        owner.execute("REVOKE ALL ON " + P + "external_mapping_generation FROM " + APP);
        owner.execute("GRANT SELECT ON " + P + "external_mapping_generation TO " + APP);
        appSource =
                new DriverManagerDataSource(
                        POSTGRES.getJdbcUrl(), APP, "synthetic-claims-fixture-only");
        app = new JdbcTemplate(appSource);
    }

    @BeforeEach
    void setup() {
        catalog = new JdbcClaimNameCatalogAdapter(app);
        fence = new JdbcBoundSourceClaimIssuanceFence(app, catalog);
        claims = new JdbcBoundSourceClaimEvidenceRepositoryAdapter(app);
        bindings = new JdbcOperationAuthenticationBindingRepositoryAdapter(app);
        tx = new TransactionTemplate(new DataSourceTransactionManager(appSource));
        tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        tx.setTimeout(10);
        user = id();
        provider = id();
        issuer = "https://source.invalid/" + provider;
        link = id();
        role = id();
        owner.update(
                "INSERT INTO "
                        + P
                        + "user (id,username,user_type,status,failed_login_count,created_at,updated_at)"
                        + " VALUES (?,?,'HUMAN','ACTIVE',0,now(),now())",
                user,
                user);
        owner.update(
                "INSERT INTO "
                        + P
                        + "provider"
                        + " (id,code,name,provider_type,issuer_uri,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at)"
                        + " VALUES (?,?,'Synthetic','OIDC',?,false,false,'ACTIVE',now(),now())",
                provider,
                provider,
                issuer);
        owner.update(
                "INSERT INTO "
                        + P
                        + "external_identity"
                        + " (id,user_id,identity_provider_id,external_subject,status,created_at,updated_at)"
                        + " VALUES (?,?,?,'synthetic-subject','LINKED',now(),now())",
                link,
                user,
                provider);
        owner.update(
                "INSERT INTO "
                        + P
                        + "role (id,code,role_type,status,created_at,updated_at)"
                        + " VALUES (?,?,'EXTERNAL_MAPPED','ACTIVE',now(),now())",
                role,
                role);
    }

    private OperationAuthenticationBinding bind() {
        return bind("OIDC");
    }

    private OperationAuthenticationBinding bind(String type) {
        var at = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS);
        var b =
                new OperationAuthenticationBinding(
                        id(),
                        (short) 1,
                        "hidra-api",
                        "hidra-api",
                        id(),
                        id(),
                        user,
                        type,
                        type.equals("OIDC") ? "OIDC" : "LDAP",
                        provider,
                        owner.queryForObject(
                                "SELECT security_version FROM " + P + "provider WHERE id=?",
                                Long.class,
                                provider),
                        link,
                        "synthetic-subject",
                        1L,
                        type.equals("OIDC") ? issuer : null,
                        type.equals("OIDC") ? at.plusSeconds(300) : null,
                        null,
                        null,
                        at,
                        at.plusSeconds(120),
                        new byte[32]);
        app.update(
                "INSERT INTO "
                        + P
                        + "login_session"
                        + " (id,user_id,identity_provider_id,external_identity_id,started_at,expires_at,status,session_type)"
                        + " VALUES (?,?,?,?,?,?,'ACTIVE',?)",
                b.sessionId(),
                user,
                provider,
                link,
                Timestamp.from(at),
                Timestamp.from(b.expiresAt()),
                type.equals("OIDC") ? "OIDC" : "LDAP");
        bindings.append(b);
        return b;
    }

    private BoundSourceClaimEvidence evidence(
            OperationAuthenticationBinding b, long xid, long generation) {
        return new BoundSourceClaimEvidence(
                b.bindingId(), (short) 1, generation, xid, ClaimCoverage.empty(), new byte[32]);
    }

    private static String id() {
        return UUID.randomUUID().toString();
    }

    private void mapping(String claim) {
        app.update(
                "INSERT INTO "
                        + P
                        + "external_role_mapping"
                        + " (id,identity_provider_id,role_id,external_role_code,claim_name,mapping_mode,status,created_at,updated_at)"
                        + " VALUES (?,?,?,'operator',?,'DIRECT_GRANT','ACTIVE',now(),now())",
                id(),
                provider,
                role,
                claim);
    }

    @Test
    void additiveGuardSupportsBothDirectoryTypesWithExistingFenceAndReplayRules() {
        for (String type : List.of("LDAP", "ACTIVE_DIRECTORY")) {
            owner.update(
                    "UPDATE "
                            + P
                            + "provider SET"
                            + " provider_type=?,issuer_uri=NULL,directory_base_dn='dc=synthetic',user_search_base='ou=people'"
                            + " WHERE id=?",
                    type,
                    provider);
            var selection = catalog.selectVersioned(provider);
            tx.executeWithoutResult(
                    status -> {
                        var stamp = fence.prepare(provider, selection);
                        var b = bind(type);
                        var e = evidence(b, stamp.transactionId(), selection.generation());
                        claims.append(e);
                        claims.append(e);
                        assertThat(claims.findByBindingId(b.bindingId())).contains(e);
                        assertThat(b.sourceExpiresAt()).isNull();
                    });
        }
        assertThat(
                        owner.queryForObject(
                                "SELECT NOT prosecdef AND provolatile='v' FROM pg_proc WHERE"
                                    + " oid='public.hidra_identity_bound_source_claim_guard()'::regprocedure",
                                Boolean.class))
                .isTrue();
        assertThat(
                        owner.queryForObject(
                                "SELECT has_function_privilege(?,"
                                        + " 'public.hidra_identity_bound_source_claim_guard()',"
                                        + " 'EXECUTE')",
                                Boolean.class,
                                APP))
                .isFalse();
        assertThat(
                        owner.queryForObject(
                                "SELECT count(*) FROM pg_trigger WHERE"
                                    + " tgrelid='public.hidra_identity_bound_source_claim_evidence'::regclass"
                                    + " AND NOT tgisinternal",
                                Integer.class))
                .isEqualTo(2);
    }

    @Test
    void freshSchemaAndNonownerEpochSelectOnlyAreRequired() {
        flyway.validate();
        assertThat(flyway.info().pending()).isEmpty();
        assertThat(
                        app.queryForObject(
                                "SELECT has_table_privilege(current_user,'"
                                        + P
                                        + "external_mapping_generation','UPDATE')",
                                Boolean.class))
                .isFalse();
        assertThat(catalog.selectVersioned(provider).generation()).isEqualTo(1);
        assertThatThrownBy(() -> new JdbcClaimNameCatalogAdapter(owner).selectVersioned(provider))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void sameTransactionAppendReadbackReplayAndAllMutationsAreGuarded() {
        var stored =
                tx.execute(
                        status -> {
                            var stamp = fence.prepare(provider, catalog.selectVersioned(provider));
                            var b = bind();
                            var e = evidence(b, stamp.transactionId(), 1);
                            claims.append(e);
                            return e;
                        });
        assertThat(claims.findByBindingId(stored.bindingId())).contains(stored);
        tx.executeWithoutResult(status -> claims.append(stored));
        for (String sql :
                List.of(
                        "UPDATE " + CLAIMS + " SET evidence_format=evidence_format WHERE false",
                        "DELETE FROM " + CLAIMS + " WHERE false",
                        "TRUNCATE " + CLAIMS))
            assertThatThrownBy(() -> app.execute(sql)).isInstanceOf(RuntimeException.class);
        assertThat(claims.findByBindingId(stored.bindingId())).contains(stored);
    }

    @Test
    void noFenceAndDelayedAttachmentRejectAndRollback() {
        assertThatThrownBy(
                        () ->
                                tx.execute(
                                        status -> {
                                            var b = bind();
                                            claims.append(
                                                    evidence(
                                                            b,
                                                            app.queryForObject(
                                                                    "SELECT txid_current()",
                                                                    Long.class),
                                                            1));
                                            return b;
                                        }))
                .isInstanceOf(IllegalStateException.class);
        assertThat(
                        app.queryForObject(
                                "SELECT count(*) FROM " + P + "login_session WHERE user_id=?",
                                Integer.class,
                                user))
                .isZero();
        var b = tx.execute(status -> bind());
        assertThatThrownBy(
                        () ->
                                tx.execute(
                                        status -> {
                                            var stamp =
                                                    fence.prepare(
                                                            provider,
                                                            catalog.selectVersioned(provider));
                                            claims.append(evidence(b, stamp.transactionId(), 1));
                                            return null;
                                        }))
                .isInstanceOf(IllegalStateException.class);
        assertThat(claims.findByBindingId(b.bindingId())).isEmpty();
    }

    @Test
    void changedCatalogAndSavepointBindingCannotBePromoted() {
        var before = catalog.selectVersioned(provider);
        mapping("groups");
        assertThatThrownBy(() -> tx.execute(status -> fence.prepare(provider, before)))
                .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(
                        () ->
                                tx.execute(
                                        status -> {
                                            var selected = catalog.selectVersioned(provider);
                                            var stamp = fence.prepare(provider, selected);
                                            var savepoint = status.createSavepoint();
                                            var b = bind();
                                            status.releaseSavepoint(savepoint);
                                            claims.append(
                                                    evidence(
                                                            b,
                                                            stamp.transactionId(),
                                                            selected.generation()));
                                            return null;
                                        }))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void preparedPrefixBlocksMappingWriterUntilPhysicalCommit() throws Exception {
        var executor = Executors.newSingleThreadExecutor();
        var started = new CountDownLatch(1);
        var result = new java.util.concurrent.atomic.AtomicReference<Future<?>>();
        try {
            tx.executeWithoutResult(
                    status -> {
                        fence.prepare(provider, catalog.selectVersioned(provider));
                        result.set(
                                executor.submit(
                                        () -> {
                                            started.countDown();
                                            mapping("groups");
                                        }));
                        try {
                            assertThat(started.await(2, TimeUnit.SECONDS)).isTrue();
                            assertThatThrownBy(() -> result.get().get(150, TimeUnit.MILLISECONDS))
                                    .isInstanceOf(TimeoutException.class);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            throw new IllegalStateException(e);
                        }
                        assertThat(
                                        owner.queryForObject(
                                                "SELECT EXISTS (SELECT 1 FROM pg_stat_activity"
                                                    + " WHERE wait_event_type='Lock' AND query LIKE"
                                                    + " 'INSERT INTO"
                                                    + " public.hidra_identity_external_role_mapping%')",
                                                Boolean.class))
                                .isTrue();
                    });
            result.get().get(5, TimeUnit.SECONDS);
            assertThat(catalog.selectVersioned(provider).generation()).isEqualTo(2);
        } finally {
            executor.shutdownNow();
        }
    }

    @Test
    void sameTransactionMappingMutationInvalidatesPreparedCaptureAndRollsBack() {
        assertThatThrownBy(
                        () ->
                                tx.execute(
                                        status -> {
                                            var stamp =
                                                    fence.prepare(
                                                            provider,
                                                            catalog.selectVersioned(provider));
                                            mapping("groups");
                                            fence.requireCurrent(stamp);
                                            return null;
                                        }))
                .isInstanceOf(IllegalStateException.class);
        assertThat(catalog.selectVersioned(provider).generation()).isEqualTo(1);
    }

    @Test
    void fixedFencePrefixMatchesExistingDiagnosticFence() throws Exception {
        String root =
                "src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/";
        String a = Files.readString(Path.of(root + "JdbcIdentityAuthorityFence.java"));
        String b = Files.readString(Path.of(root + "JdbcBoundSourceClaimIssuanceFence.java"));
        assertThat(prefix(b)).isEqualTo(prefix(a));
    }

    private String prefix(String s) {
        int start = s.indexOf("private static final List<String> RELATIONS");
        return s.substring(start, s.indexOf(";", start) + 1).replaceAll("\\s+", "");
    }
}
