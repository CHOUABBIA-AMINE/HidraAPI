/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPhysicalNetworkRevisionPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Exercises actual PostgreSQL revision storage, transaction races and exact full-chain catalog evidence.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import dz.sh.hidra.modules.topology.application.contract.simulation.SimulationPhysicalNetworkRevisionContract;
import dz.sh.hidra.modules.topology.application.port.out.TopologyPhysicalNetworkRevisionRepositoryPort;
import dz.sh.hidra.modules.topology.application.service.TopologySimulationPhysicalNetworkRevisionQueryService;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision.*;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.TopologyPhysicalNetworkRevisionCodec;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
class TopologyPhysicalNetworkRevisionPostgresIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("hidra_test");
    private static final Path MIGRATION = Path.of("src/main/resources/db/migration/V20261009_001__p25_topology_physical_network_revisions.sql");
    private static final String TABLE = "hidra_topology_physical_network_revision";
    private JdbcTemplate jdbc;
    private DataSourceTransactionManager transactions;
    private TopologyPhysicalNetworkRevisionRepositoryPort repository;
    private SimulationPhysicalNetworkRevisionContract query;

    @BeforeEach
    void setup() throws Exception {
        var dataSource = new DriverManagerDataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        jdbc = new JdbcTemplate(dataSource);
        transactions = new DataSourceTransactionManager(dataSource);
        jdbc.execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        jdbc.execute("CREATE TABLE topology_test_sentinel(id text PRIMARY KEY); INSERT INTO topology_test_sentinel VALUES ('preserved')");
        jdbc.execute(Files.readString(MIGRATION));
        repository = proxy(new JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter(jdbc), TopologyPhysicalNetworkRevisionRepositoryPort.class);
        query = proxy(new TopologySimulationPhysicalNetworkRevisionQueryService(repository), SimulationPhysicalNetworkRevisionContract.class);
    }

    @Test
    void persistsTwoExactRevisionsAndExportsEveryOrderedFieldWithoutChangingOriginal() {
        var first = fixture("r1", "100.00");
        var second = fixture("r2", "120.000");
        assertEquals(first, repository.append(first));
        var before = query.find("source", "r1").orElseThrow();
        assertEquals(second, repository.append(second));
        assertExport(first, before);
        assertExport(second, query.find(" source ", " r2 ").orElseThrow());
        assertEquals(before, query.find("source", "r1").orElseThrow());
        assertEquals(first, repository.append(first));
        assertEquals(before, query.find("source", "r1").orElseThrow());
        assertNotEquals(before.sha256(), query.find("source", "r2").orElseThrow().sha256());
        assertTrue(query.find("source", "missing").isEmpty());
        assertTrue(query.find("other", "r1").isEmpty());
        assertEquals(2L, count());
    }

    @Test
    void rejectsConflictingContentAtomicallyAndRollsBackOuterTransaction() {
        var first = fixture("r1", "100.00");
        repository.append(first);
        assertThrows(InvalidTopologyValueException.class, () -> repository.append(fixture("r1", "101.00")));
        assertEquals(first, repository.find("source", "r1").orElseThrow());
        assertThrows(IllegalStateException.class, () -> new TransactionTemplate(transactions).execute(status -> {
            repository.append(fixture("rolled-back", "100.00"));
            throw new IllegalStateException("Synthetic downstream failure");
        }));
        assertTrue(repository.find("source", "rolled-back").isEmpty());
        assertEquals(1L, count());
    }

    @Test
    void concurrentIdenticalReplayCommitsOneRowThroughRealTransactionProxies() throws Exception {
        assertEquals(2, race(fixture("r1", "100.00"), fixture("r1", "100.00")));
        assertEquals(1L, count());
        assertEquals(fixture("r1", "100.00"), repository.find("source", "r1").orElseThrow());
    }

    @Test
    void concurrentConflictingReplayHasExactlyOneWinner() throws Exception {
        var a = fixture("r1", "100.00");
        var b = fixture("r1", "101.00");
        assertEquals(1, race(a, b));
        assertEquals(1L, count());
        var winner = repository.find("source", "r1").orElseThrow();
        assertTrue(winner.equals(a) || winner.equals(b));
    }

    @Test
    void databaseRejectsDuplicatesInvalidIdentityFormatDigestAndEveryMutation() {
        var value = fixture("r1", "100.00");
        repository.append(value);
        var codec = new TopologyPhysicalNetworkRevisionCodec();
        byte[] payload = codec.encode(value);
        String digest = codec.sha256(payload);
        assertThrows(RuntimeException.class, () -> insert("source", "r1", codec.FORMAT, payload, digest));
        for (String source : List.of("", " ", "\t\n", " source ", "\tsource\t")) {
            assertThrows(RuntimeException.class, () -> insert(source, "other", codec.FORMAT, payload, digest));
        }
        for (String revision : List.of("", " ", "\t\n", " revision ", "\trevision\t")) {
            assertThrows(RuntimeException.class, () -> insert("source", revision, codec.FORMAT, payload, digest));
        }
        assertThrows(RuntimeException.class, () -> insert("source", "r2", "UNKNOWN", payload, digest));
        assertThrows(RuntimeException.class, () -> insert("source", "r2", codec.FORMAT, payload, "a".repeat(64)));
        assertThrows(RuntimeException.class, () -> insert("source", "r2", codec.FORMAT, new byte[0], codec.sha256(new byte[0])));
        assertThrows(RuntimeException.class, () -> jdbc.update("UPDATE " + TABLE + " SET revision_id='changed'"));
        assertThrows(RuntimeException.class, () -> jdbc.update("DELETE FROM " + TABLE));
        assertThrows(RuntimeException.class, () -> jdbc.execute("TRUNCATE " + TABLE));
        assertEquals(value, repository.find("source", "r1").orElseThrow());
        assertEquals("preserved", jdbc.queryForObject("SELECT id FROM topology_test_sentinel", String.class));
    }

    @Test
    void ownerReadsRejectHashConsistentMalformedPayloadAndMismatchedRowIdentity() {
        var codec = new TopologyPhysicalNetworkRevisionCodec();
        byte[] malformed = new byte[]{1,2,3};
        insert("source", "bad", codec.FORMAT, malformed, codec.sha256(malformed));
        assertThrows(InvalidTopologyValueException.class, () -> repository.find("source", "bad"));
        assertThrows(InvalidTopologyValueException.class, () -> query.find("source", "bad"));
        byte[] bytes = codec.encode(fixture("actual", "100.00"));
        insert("source", "mismatched", codec.FORMAT, bytes, codec.sha256(bytes));
        assertThrows(InvalidTopologyValueException.class, () -> repository.findStored("source", "mismatched"));
        assertThrows(InvalidTopologyValueException.class, () -> repository.append(null));
        assertThrows(InvalidTopologyValueException.class, () -> repository.find(" ", "r1"));
        assertThrows(InvalidTopologyValueException.class, () -> repository.find("source", null));
    }

    @Test
    void ownerReadsVerifyDigestEvenWhenAdministrativeConstraintBypassHasOccurred() {
        var codec = new TopologyPhysicalNetworkRevisionCodec();
        // Disposable test schema only: demonstrate verified reads after privileged bypass.
        jdbc.execute("ALTER TABLE " + TABLE + " DROP CONSTRAINT ck_topology_physical_digest");
        insert("source", "r1", codec.FORMAT, codec.encode(fixture("r1", "100.00")), "a".repeat(64));
        assertThrows(InvalidTopologyValueException.class, () -> repository.findStored("source", "r1"));
        assertThrows(InvalidTopologyValueException.class, () -> query.find("source", "r1"));
    }

    @Test
    void forwardMigrationCreatesEmptyStoreAndPreservesExistingSentinel() {
        assertEquals(0L, count());
        assertEquals("preserved", jdbc.queryForObject("SELECT id FROM topology_test_sentinel", String.class));
        assertEquals(2L, jdbc.queryForObject("SELECT count(*) FROM pg_trigger WHERE tgrelid=?::regclass AND NOT tgisinternal", Long.class, TABLE));
    }

    @Test
    void fullFlywayChainProvidesExactSourceCatalogForReviewedDictionaryRegeneration() throws Exception {
        jdbc.execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        var flyway = Flyway.configure().dataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())
                .locations("filesystem:src/main/resources/db/migration").load();
        flyway.migrate();
        flyway.validate();
        assertEquals(0L, count());
        assertEquals(0L, jdbc.queryForObject("SELECT count(*) FROM flyway_schema_history WHERE NOT success", Long.class));
        try (var migrationFiles = Files.list(MIGRATION.getParent())) {
            assertEquals(migrationFiles.filter(p -> p.getFileName().toString().endsWith(".sql")).count(),
                    jdbc.queryForObject("SELECT count(*) FROM flyway_schema_history", Long.class).longValue());
        }
        repository.append(fixture("r1", "100.00"));
        assertExport(fixture("r1", "100.00"), query.find("source", "r1").orElseThrow());
        captureExactCatalog();
    }

    private void captureExactCatalog() throws Exception {
        // Reuse the existing generator's schema-only query and source-inventory validator.
        // This equivalent capture reads the actual fully migrated disposable PostgreSQL 16
        // catalog; it never exports business rows or substitutes generated schema facts.
        String sql = python("""
                import importlib.util
                spec=importlib.util.spec_from_file_location('dictionary','.github/scripts/generate_data_dictionary.py')
                g=importlib.util.module_from_spec(spec);spec.loader.exec_module(g)
                print(g.CATALOG_SQL.split(';',1)[1].rsplit('COMMIT;',1)[0])
                """);
        Path directory = Files.createTempDirectory("hidra-p25-catalog-");
        Path catalog = directory.resolve("catalog.json");
        try (var connection = DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setReadOnly(true);
            connection.setAutoCommit(false);
            try (var statement = connection.createStatement()) {
                statement.setQueryTimeout(60);
                try (var result = statement.executeQuery(sql)) {
                    assertTrue(result.next());
                    Files.writeString(catalog, result.getString(1), StandardCharsets.UTF_8);
                    assertFalse(result.next());
                }
            }
            connection.commit();
        }
        String result = python("""
                import importlib.util,json,subprocess,sys
                from pathlib import Path
                spec=importlib.util.spec_from_file_location('dictionary','.github/scripts/generate_data_dictionary.py')
                g=importlib.util.module_from_spec(spec);spec.loader.exec_module(g)
                root=Path('.').resolve();directory=Path(sys.argv[1])
                source=g.inventory(root);catalog=g.load_json(directory/'catalog.json')
                sha=subprocess.check_output(['git','rev-parse','HEAD'],text=True).strip()
                document={'format_version':g.VERSION,'source_sha':sha,'source':source,'catalog':catalog}
                g.validate_document(document,root)
                overrides=g.load_json('.github/database-dictionary-ownership.json')
                markdown=g.render(document,root,overrides,final=True)
                data=g.canonical(document)
                (directory/'schema.json').write_bytes(data)
                (directory/'DATA_DICTIONARY.md').write_text(markdown,encoding='utf-8')
                print(json.dumps({'source_sha':sha,'catalog_sha256':g.digest(data),'source_bundle_sha256':source['bundle_sha256'],'migrations':len(source['migrations']),'relations':len(catalog['relations']),'dictionary_sha256':g.digest(markdown.encode())},sort_keys=True))
                """, directory.toString());
        assertTrue(result.contains("\"catalog_sha256\""));
        System.out.println("HIDRA_P25_CATALOG_VERIFICATION " + result.strip());
        if ("true".equals(System.getenv("GITHUB_ACTIONS"))) {
            // Transport compressed schema-only evidence in existing job logs; no workflow changes.
            var compressed = new ByteArrayOutputStream();
            try (var gzip = new GZIPOutputStream(compressed)) {
                gzip.write(Files.readAllBytes(directory.resolve("schema.json")));
            }
            String encoded = Base64.getEncoder().encodeToString(compressed.toByteArray());
            for (int start = 0, part = 0; start < encoded.length(); start += 4096, part++) {
                System.out.println("HIDRA_P25_CATALOG_PART " + part + " " + encoded.substring(start, Math.min(start+4096, encoded.length())));
            }
            System.out.println("HIDRA_P25_CATALOG_END");
        }
    }

    private static String python(String script, String... arguments) throws Exception {
        var command = new java.util.ArrayList<>(List.of("python3", "-c", script));
        command.addAll(List.of(arguments));
        var process = new ProcessBuilder(command).redirectErrorStream(true).start();
        var output = new ByteArrayOutputStream();
        var reader = Executors.newSingleThreadExecutor();
        try {
            var read = reader.submit(() -> { process.getInputStream().transferTo(output); return 0; });
            assertTrue(process.waitFor(60, TimeUnit.SECONDS), "Catalog helper timeout");
            read.get(10, TimeUnit.SECONDS);
            String text = output.toString(StandardCharsets.UTF_8);
            assertEquals(0, process.exitValue(), text);
            return text;
        } finally {
            process.destroyForcibly();
            reader.shutdownNow();
        }
    }

    private <T> T proxy(Object target, Class<T> contract) {
        var factory = new ProxyFactory();
        factory.setTarget(target);
        factory.setInterfaces(contract);
        factory.addAdvice(new TransactionInterceptor(transactions, new AnnotationTransactionAttributeSource()));
        return contract.cast(factory.getProxy());
    }

    private int race(TopologyPhysicalNetworkRevision a, TopologyPhysicalNetworkRevision b) throws Exception {
        var pool = Executors.newFixedThreadPool(2);
        var ready = new CountDownLatch(2);
        var go = new CountDownLatch(1);
        try {
            var left = pool.submit(() -> compete(a, ready, go));
            var right = pool.submit(() -> compete(b, ready, go));
            assertTrue(ready.await(10, TimeUnit.SECONDS));
            go.countDown();
            return left.get(20, TimeUnit.SECONDS) + right.get(20, TimeUnit.SECONDS);
        } finally {
            go.countDown();
            pool.shutdownNow();
        }
    }

    private int compete(TopologyPhysicalNetworkRevision value, CountDownLatch ready, CountDownLatch go) throws Exception {
        ready.countDown();
        assertTrue(go.await(10, TimeUnit.SECONDS));
        try { assertEquals(value, repository.append(value)); return 1; }
        catch (InvalidTopologyValueException conflict) { assertTrue(conflict.getMessage().contains("Conflicting")); return 0; }
    }

    private void insert(String source, String revision, String format, byte[] bytes, String digest) {
        jdbc.update("INSERT INTO " + TABLE + " VALUES (?,?,?,?,?)", source, revision, format, bytes, digest);
    }

    private long count() { return jdbc.queryForObject("SELECT count(*) FROM " + TABLE, Long.class); }

    private static TopologyPhysicalNetworkRevision fixture(String revision, String length) {
        return new TopologyPhysicalNetworkRevision("source", revision, ScopeType.PIPELINE_SYSTEM, "synthetic-scope",
                Instant.ofEpochSecond(-1,123456789), Instant.ofEpochSecond(-2,987654321), null, Origin.SYNTHETIC,
                "synthetic-fixture", List.of(new Node("a",new BigDecimal("-1.00")),new Node("b",BigDecimal.ZERO),new Node("c",BigDecimal.ONE)),
                List.of(new PipeSegment("p","b","a",new BigDecimal(length),new BigDecimal("0.50"),BigDecimal.ZERO)),
                List.of(new EquipmentLink("e","c","b",EquipmentKind.COMPRESSOR)));
    }

    private static void assertExport(TopologyPhysicalNetworkRevision expected, SimulationPhysicalNetworkRevisionContract.Revision actual) {
        assertEquals(expected.sourceId(),actual.sourceId()); assertEquals(expected.revisionId(),actual.revisionId());
        assertEquals(expected.scopeType().name(),actual.scopeType()); assertEquals(expected.scopeId(),actual.scopeId());
        assertEquals(expected.recordedAt(),actual.recordedAt()); assertEquals(expected.effectiveFrom(),actual.effectiveFrom());
        assertEquals(expected.effectiveUntil(),actual.effectiveUntil()); assertEquals(expected.origin().name(),actual.origin());
        assertEquals(expected.evidenceReference(),actual.evidenceReference());
        assertEquals(expected.nodes().size(),actual.nodes().size());
        for(int i=0;i<expected.nodes().size();i++) {
            assertEquals(expected.nodes().get(i).id(),actual.nodes().get(i).id());
            assertEquals(expected.nodes().get(i).elevationMeters(),actual.nodes().get(i).elevationMeters());
        }
        assertEquals(expected.pipeSegments().size(),actual.pipeSegments().size());
        for(int i=0;i<expected.pipeSegments().size();i++) {
            var p=expected.pipeSegments().get(i);var a=actual.pipeSegments().get(i);
            assertEquals(p.id(),a.id());assertEquals(p.fromNodeId(),a.fromNodeId());assertEquals(p.toNodeId(),a.toNodeId());
            assertEquals(p.lengthMeters(),a.lengthMeters());assertEquals(p.internalDiameterMeters(),a.internalDiameterMeters());
            assertEquals(p.absoluteRoughnessMeters(),a.absoluteRoughnessMeters());
        }
        assertEquals(expected.equipmentLinks().size(),actual.equipmentLinks().size());
        for(int i=0;i<expected.equipmentLinks().size();i++) {
            var e=expected.equipmentLinks().get(i);var a=actual.equipmentLinks().get(i);
            assertEquals(e.id(),a.id());assertEquals(e.fromNodeId(),a.fromNodeId());assertEquals(e.toNodeId(),a.toNodeId());assertEquals(e.kind().name(),a.kind());
        }
        var codec=new TopologyPhysicalNetworkRevisionCodec();
        assertEquals(codec.FORMAT,actual.payloadFormat());assertEquals(codec.sha256(codec.encode(expected)),actual.sha256());
    }
}
