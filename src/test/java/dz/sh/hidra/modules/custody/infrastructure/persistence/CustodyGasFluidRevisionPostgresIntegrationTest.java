/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyGasFluidRevisionPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence
 *
 * @Description : Exercises real gas persistence, qualification, replay, rollback, corruption and full-chain catalog evidence.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationGasFluidRevisionContract;
import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationProductCandidateContract;
import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidRevisionRepositoryPort;
import dz.sh.hidra.modules.custody.application.service.CustodyGasFluidQualificationService;
import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision.*;
import dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.CustodyGasFluidRevisionCodec;
import dz.sh.hidra.modules.workflow.application.service.WorkflowApplicationService;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.RevisionApprovalEvidencePostgresIntegrationTest;
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
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = HidraApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class CustodyGasFluidRevisionPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("hidra_test");
    @DynamicPropertySource static void database(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", POSTGRES::getJdbcUrl); r.add("spring.datasource.username", POSTGRES::getUsername);
        r.add("spring.datasource.password", POSTGRES::getPassword); r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        r.add("spring.flyway.enabled", () -> "true");
    }
    @Autowired JdbcTemplate jdbc;
    @Autowired PlatformTransactionManager transactions;
    @Autowired CustodyGasFluidRevisionRepositoryPort repository;
    @Autowired SimulationProductCandidateContract products;
    @Autowired SimulationGasFluidRevisionContract query;
    @Autowired CustodyGasFluidQualificationService qualifications;
    @Autowired WorkflowApplicationService workflow;
    @Autowired ExecuteWorkflowTransitionUseCase transitions;
    @Autowired WorkflowTaskRepositoryPort tasks;
    final CustodyGasFluidRevisionCodec codec = new CustodyGasFluidRevisionCodec();
    @AfterEach void clearSecurity() { SecurityContextHolder.clearContext(); }
    CustodyGasFluidRevision fixture(String revision) {
        String s = UUID.randomUUID().toString();
        return RevisionApprovalEvidencePostgresIntegrationTest.fluid(jdbc, products, revision, "def-" + s, "type-" + s, "purpose-" + s);
    }
    CustodyGasFluidRevision revision(CustodyGasFluidRevision v, String revision, Method method) {
        return new CustodyGasFluidRevision(v.sourceId(), revision, v.recordedAt(), v.effectiveFrom(), v.effectiveUntil(),
                v.origin(), v.evidenceReference(), v.productSnapshot(), v.productKind(), method, v.components(), v.governanceBinding());
    }
    @Test void persistsTwoRevisionsAndAtomicIdenticalReplayWithoutChangingOriginal() {
        var a = fixture("r1"); var b = revision(a, "r2", a.method());
        assertEquals(a, repository.append(a)); var stored = query.findStored(a.sourceId(), "r1").orElseThrow();
        assertEquals(b, repository.append(b)); assertEquals(a, repository.append(a));
        assertEquals(stored, query.findStored(a.sourceId(), "r1").orElseThrow());
        assertNotEquals(stored.sha256(), query.findStored(a.sourceId(), "r2").orElseThrow().sha256());
    }
    @Test void actualWorkflowQualifiesOnlyItsExactRevisionAndWithdrawalIsReattested() {
        var a = fixture("r1"); var b = revision(a, "r2", a.method()); repository.append(a); repository.append(b);
        var s = repository.findStored(a.sourceId(), "r1").orElseThrow(); var binding = a.governanceBinding();
        var approval = RevisionApprovalEvidencePostgresIntegrationTest.approve(jdbc, workflow, transitions, tasks, "custody",
                binding.definitionId(), binding.targetTypeId(), binding.purposeId(), s.sha256());
        String id = "q-" + UUID.randomUUID();
        var q = qualifications.qualify(id, a.sourceId(), "r1", approval.instanceId(), approval.taskId(), approval.actionId());
        var result = query.resolve(a.sourceId(), "r1", id, Instant.now(), SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE).orElseThrow();
        assertEquals(SimulationGasFluidRevisionContract.Origin.SYNTHETIC, result.stored().revision().origin());
        assertEquals(q.approverId(), result.qualification().approverId());
        assertTrue(query.resolve(a.sourceId(), "r2", id, Instant.now(), SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE).isEmpty());
        assertThrows(InvalidCustodyValueException.class, () -> qualifications.qualify("wrong-" + id, a.sourceId(), "r2", approval.instanceId(), approval.taskId(), approval.actionId()));
        jdbc.update("UPDATE hidra_workflow_definition_target_binding SET active=false WHERE id=?", binding.definitionId() + "-binding");
        assertTrue(query.resolve(a.sourceId(), "r1", id, Instant.now(), SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE).isEmpty());
    }
    @Test void conflictingMethodReuseRollsBackNewAggregateAndOuterRollbackLeavesNoRevision() {
        var a = fixture("r1"); repository.append(a); var m = a.method();
        var bad = new Method(m.reference(), m.revisionId(), m.recordedAt(), m.effectiveFrom(), null, m.origin(), m.evidenceReference(),
                m.inputRepresentation(), m.allowedComponentReferences(), m.minimumPressurePascalsAbsolute(), new BigDecimal("800000.00"),
                m.minimumTemperatureKelvin(), m.maximumTemperatureKelvin(), m.supportedUses());
        assertThrows(InvalidCustodyValueException.class, () -> repository.append(revision(a, "conflict", bad)));
        assertTrue(repository.findStored(a.sourceId(), "conflict").isEmpty());
        assertThrows(IllegalStateException.class, () -> new TransactionTemplate(transactions).execute(status -> {
            repository.append(revision(a, "rollback", m)); throw new IllegalStateException("synthetic downstream failure");
        }));
        assertTrue(repository.findStored(a.sourceId(), "rollback").isEmpty());
    }
    @Test void realConcurrentIdenticalReplayHasOneImmutableWinner() throws Exception {
        var a = fixture("race"); var ready = new CountDownLatch(2); var go = new CountDownLatch(1); var pool = Executors.newFixedThreadPool(2);
        try {
            var left = pool.submit(() -> { ready.countDown(); assertTrue(go.await(10, TimeUnit.SECONDS)); return repository.append(a); });
            var right = pool.submit(() -> { ready.countDown(); assertTrue(go.await(10, TimeUnit.SECONDS)); return repository.append(a); });
            assertTrue(ready.await(10, TimeUnit.SECONDS)); go.countDown();
            assertEquals(a, left.get(30, TimeUnit.SECONDS)); assertEquals(a, right.get(30, TimeUnit.SECONDS));
            assertEquals(1L, jdbc.queryForObject("SELECT count(*) FROM hidra_custody_gas_fluid_revision WHERE source_id=?", Long.class, a.sourceId()));
        } finally { go.countDown(); pool.shutdownNow(); }
    }
    @Test void mutationProtectionAndVerifiedCorruptionPreserveLiveProduct() {
        var a = fixture("mutations"); repository.append(a); var p = products.resolve(a.productSnapshot().id()).orElseThrow();
        var stored = repository.findStored(a.sourceId(), a.revisionId()).orElseThrow();
        String qid = "forged-" + UUID.randomUUID(); Instant now = Instant.now();
        repository.appendQualification(a.sourceId(), a.revisionId(), new CustodyGasFluidRevisionRepositoryPort.Qualification(qid,
                a.sourceId(), a.revisionId(), stored.sha256(), now, "missing-instance", "missing-task", "missing-action", "fake-actor", "Forged", now,
                a.governanceBinding().definitionId(), 1, a.governanceBinding().targetTypeId(), a.governanceBinding().purposeId()));
        assertTrue(query.resolve(a.sourceId(), a.revisionId(), qid, Instant.now(), SimulationGasFluidRevisionContract.SupportedUse.STEADY_STATE).isEmpty());
        for (String table : List.of("hidra_custody_gas_fluid_revision", "hidra_custody_gas_method_revision", "hidra_custody_gas_fluid_qualification")) {
            assertThrows(RuntimeException.class, () -> jdbc.update("UPDATE " + table + " SET payload_format=payload_format"));
            assertThrows(RuntimeException.class, () -> jdbc.update("DELETE FROM " + table));
            assertThrows(RuntimeException.class, () -> jdbc.execute("TRUNCATE " + table + " CASCADE"));
        }
        assertThrows(RuntimeException.class, () -> jdbc.update("UPDATE hidra_custody_gas_fluid_revision SET revision_id='forged'"));
        assertThrows(RuntimeException.class, () -> jdbc.update("DELETE FROM hidra_custody_gas_method_revision"));
        assertEquals(p, products.resolve(a.productSnapshot().id()).orElseThrow());
        byte[] bad = new byte[]{1,2,3}; String source = "corrupt-" + UUID.randomUUID();
        jdbc.update("INSERT INTO hidra_custody_gas_fluid_revision(source_id,revision_id,payload_format,canonical_payload,sha256) VALUES (?,?,?,?,?)",
                source, "bad", CustodyGasFluidRevisionCodec.FORMAT, bad, codec.sha256(bad));
        assertThrows(InvalidCustodyValueException.class, () -> repository.findStored(source, "bad"));
    }
    @Test void fullFlywayChainProvidesExactSourceCatalogForReviewedDictionaryRegeneration() throws Exception {
        Flyway.configure().dataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())
                .locations("filesystem:src/main/resources/db/migration").load().validate();
        assertEquals(0L, jdbc.queryForObject("SELECT count(*) FROM flyway_schema_history WHERE NOT success", Long.class));
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
        System.out.println("HIDRA_P25_C3C_CATALOG_VERIFICATION " + result.strip());
        if ("true".equals(System.getenv("GITHUB_ACTIONS"))) {
            // Transport compressed schema-only evidence in existing job logs; no workflow changes.
            var compressed = new ByteArrayOutputStream();
            try (var gzip = new GZIPOutputStream(compressed)) {
                gzip.write(Files.readAllBytes(directory.resolve("schema.json")));
            }
            String encoded = Base64.getEncoder().encodeToString(compressed.toByteArray());
            for (int start = 0, part = 0; start < encoded.length(); start += 4096, part++) {
                System.out.println("HIDRA_P25_C3C_CATALOG_PART " + part + " " + encoded.substring(start, Math.min(start+4096, encoded.length())));
            }
            System.out.println("HIDRA_P25_C3C_CATALOG_END");
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

}
