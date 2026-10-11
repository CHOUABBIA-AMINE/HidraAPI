/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInternalGraphRevisionPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Verifies actual PostgreSQL immutable station history, integrity, races and Flyway upgrade.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import dz.sh.hidra.modules.topology.application.port.out.StationInternalGraphRevisionRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.TopologyPhysicalNetworkRevisionRepositoryPort;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.*;
import dz.sh.hidra.modules.topology.domain.model.TopologyPhysicalNetworkRevision;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcStationInternalGraphRevisionRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.StationInternalGraphRevisionCodec;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.dao.DataAccessException;
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
class StationInternalGraphRevisionPostgresIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("hidra_test");
    private static final String TABLE = "hidra_topology_station_graph_revision";
    private static final Path MIGRATION = Path.of(
            "src/main/resources/db/migration/V20261011_002__p25_topology_station_graph_revisions.sql");
    private final StationInternalGraphRevisionCodec codec = new StationInternalGraphRevisionCodec();
    private JdbcTemplate jdbc;
    private DataSourceTransactionManager transactions;
    private StationInternalGraphRevisionRepositoryPort repository;

    @BeforeEach
    void setup() throws Exception {
        var source = new DriverManagerDataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        source.setDriverClassName("org.postgresql.Driver");
        jdbc = new JdbcTemplate(source);
        transactions = new DataSourceTransactionManager(source);
        jdbc.execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        jdbc.execute(Files.readString(MIGRATION));
        repository = proxy(new JdbcStationInternalGraphRevisionRepositoryAdapter(jdbc), StationInternalGraphRevisionRepositoryPort.class);
    }

    @Test
    void serialAndParallelRevisionsPreserveAllSuppliedFieldsAndNanoseconds() {
        for (boolean parallel : List.of(false, true)) {
            var value = fixture("synthetic-station-a", parallel ? "parallel" : "serial", parallel);
            assertEquals(value, repository.append(value));
            var stored = repository.findStored(value.facilityId(), value.revisionId()).orElseThrow();
            assertEquals(value, stored.revision());
            assertEquals(StationInternalGraphRevisionCodec.FORMAT, stored.payloadFormat());
            assertEquals(codec.sha256(codec.encode(value)), stored.sha256());
            assertArrayEquals(codec.encode(value), payload(value.facilityId(), value.revisionId()));
        }
    }

    @Test
    void draftAndVerifiedDeclarationsRemainDeclarationsWithoutMasterDataOrPublication() {
        var graph = new StationInternalGraph("synthetic-unprovisioned", "draft", Status.DRAFT,
                List.of(), List.of(), List.of());
        var draft = new StationInternalGraphRevision(graph.facilityId(), graph.revisionId(), graph,
                SourceClassification.DECLARED, Instant.MIN, Instant.MIN, null, null, null, Qualification.DRAFT);
        assertEquals(draft, repository.append(draft));
        var supplied = fixture("synthetic-station-a", "verified", false);
        var verified = new StationInternalGraphRevision(supplied.facilityId(), supplied.revisionId(), supplied.graph(),
                SourceClassification.VERIFIED, supplied.recordedAt(), supplied.effectiveFrom(), supplied.effectiveUntil(),
                "supplied:reference-only", "absent-predecessor", supplied.qualification());
        assertEquals(verified, repository.append(verified));
        assertEquals("absent-predecessor", repository.find(verified.facilityId(), verified.revisionId())
                .orElseThrow().expectedPredecessorRevisionId());
    }

    @Test
    void canonicalPermutationReplaysWithoutMutatingExactHistoryOrAnotherStation() {
        var old = fixture("synthetic-station-a", "r1", true);
        repository.append(old);
        byte[] before = payload(old.facilityId(), "r1");
        var graph = old.graph();
        var permuted = new StationInternalGraph(graph.facilityId(), graph.revisionId(), graph.status(),
                reversed(graph.headers()), reversed(graph.units()), reversed(graph.edges()));
        var replay = new StationInternalGraphRevision(old.facilityId(), old.revisionId(), permuted,
                old.sourceClassification(), old.recordedAt(), old.effectiveFrom(), old.effectiveUntil(),
                old.evidenceReference(), old.expectedPredecessorRevisionId(), old.qualification());
        assertEquals(old, repository.append(replay));
        repository.append(fixture(old.facilityId(), "r2", false));
        var other = fixture("synthetic-station-b", "r1", false);
        assertEquals(other, repository.append(other));
        assertEquals(old, repository.find(" synthetic-station-a ", " r1 ").orElseThrow());
        assertArrayEquals(before, payload(old.facilityId(), "r1"));
        assertTrue(repository.find(old.facilityId(), "missing").isEmpty());
        assertTrue(repository.find("missing-station", "r1").isEmpty());
        assertEquals(3L, count());
        assertNotEquals(old.graph().units(), replay.graph().units());
    }

    @Test
    void contentConflictsAndOuterFailuresRollBackAtomically() {
        var old = fixture("synthetic-station-a", "r1", false);
        repository.append(old);
        assertThrows(InvalidTopologyValueException.class,
                () -> repository.append(fixture(old.facilityId(), "r1", true)));
        assertEquals(old, repository.find(old.facilityId(), "r1").orElseThrow());
        assertThrows(IllegalStateException.class, () -> new TransactionTemplate(transactions).execute(status -> {
            repository.append(fixture(old.facilityId(), "rollback", false));
            throw new IllegalStateException("Synthetic downstream failure");
        }));
        assertTrue(repository.find(old.facilityId(), "rollback").isEmpty());
        assertThrows(InvalidTopologyValueException.class, () -> new TransactionTemplate(transactions).execute(status -> {
            repository.append(fixture(old.facilityId(), "before-conflict", false));
            repository.append(fixture(old.facilityId(), "r1", true));
            return null;
        }));
        assertTrue(repository.find(old.facilityId(), "before-conflict").isEmpty());
        assertEquals(1L, count());
    }

    @Test
    void databaseFailuresPropagateAndRollBackPriorAppends() {
        assertThrows(DataAccessException.class, () -> new TransactionTemplate(transactions).execute(status -> {
            repository.append(fixture("synthetic-station-a", "rollback", false));
            jdbc.execute("SELECT 1/0");
            return null;
        }));
        assertEquals(0L, count());
    }

    @Test
    void concurrentIdenticalAppendsCommitExactlyOneRow() throws Exception {
        var value = fixture("synthetic-station-a", "r1", true);
        assertEquals(2, race(value, value));
        assertEquals(1L, count());
        assertEquals(value, repository.find(value.facilityId(), value.revisionId()).orElseThrow());
    }

    @Test
    void concurrentConflictingAppendsHaveOneWinner() throws Exception {
        var a = fixture("synthetic-station-a", "r1", false);
        var b = fixture("synthetic-station-a", "r1", true);
        assertEquals(1, race(a, b));
        assertEquals(1L, count());
        var actual = repository.find(a.facilityId(), a.revisionId()).orElseThrow();
        assertTrue(actual.equals(a) || actual.equals(b));
    }

    @Test
    void blankAndNullIdentitiesFailRatherThanReturnAbsence() {
        assertThrows(InvalidTopologyValueException.class, () -> repository.append(null));
        for (String identity : Arrays.asList(null, "", " ", "\t\n", "\u2003")) {
            assertThrows(InvalidTopologyValueException.class, () -> repository.find(identity, "r1"));
            assertThrows(InvalidTopologyValueException.class, () -> repository.find("station", identity));
            assertThrows(InvalidTopologyValueException.class, () -> repository.findStored(identity, "r1"));
        }
    }

    @Test
    void sqlRejectsInvalidKeysPayloadFormatDigestAndAllMutations() {
        var value = fixture("synthetic-station-a", "r1", false);
        repository.append(value);
        byte[] bytes = codec.encode(value); String digest = codec.sha256(bytes);
        assertThrows(DataAccessException.class, () -> insert(value.facilityId(), "r1", codec.FORMAT, bytes, digest));
        for (String identity : List.of("", " ", "\t\n", " station ", "\tstation\t")) {
            assertThrows(DataAccessException.class, () -> insert(identity, "bad", codec.FORMAT, bytes, digest));
            assertThrows(DataAccessException.class, () -> insert("station", identity, codec.FORMAT, bytes, digest));
        }
        assertThrows(DataAccessException.class, () -> insert(null, "bad", codec.FORMAT, bytes, digest));
        assertThrows(DataAccessException.class, () -> insert("station", "bad", "UNKNOWN", bytes, digest));
        assertThrows(DataAccessException.class, () -> insert("station", "bad", codec.FORMAT, bytes, "a".repeat(64)));
        assertThrows(DataAccessException.class, () -> insert("station", "bad", codec.FORMAT, bytes, digest.toUpperCase()));
        assertThrows(DataAccessException.class, () -> insert("station", "bad", codec.FORMAT, null, digest));
        assertThrows(DataAccessException.class, () -> insert("station", "bad", codec.FORMAT, new byte[0], codec.sha256(new byte[0])));
        byte[] oversized = new byte[codec.MAX_PAYLOAD_BYTES + 1];
        assertThrows(DataAccessException.class, () -> jdbc.update("INSERT INTO " + TABLE
                + " VALUES ('station','bad',?, ?,encode(sha256(?),'hex'))", codec.FORMAT, oversized, oversized));
        assertThrows(DataAccessException.class, () -> jdbc.update("UPDATE " + TABLE + " SET revision_id='changed'"));
        assertThrows(DataAccessException.class, () -> jdbc.update("DELETE FROM " + TABLE));
        assertThrows(DataAccessException.class, () -> jdbc.execute("TRUNCATE " + TABLE));
        assertEquals(value, repository.find(value.facilityId(), "r1").orElseThrow());
    }

    @Test
    void hashConsistentMalformedNoncanonicalAndWrongIdentityPayloadsFailOnRead() {
        var value = fixture("synthetic-station-a", "r1", false);
        byte[] bytes = codec.encode(value);
        insert(value.facilityId(), "wrong-key", codec.FORMAT, bytes, codec.sha256(bytes));
        assertThrows(InvalidTopologyValueException.class, () -> repository.findStored(value.facilityId(), "wrong-key"));
        insert("synthetic-station-b", "r1", codec.FORMAT, bytes, codec.sha256(bytes));
        assertThrows(InvalidTopologyValueException.class, () -> repository.find("synthetic-station-b", "r1"));
        insert("malformed", "r1", codec.FORMAT, new byte[]{1,2,3}, codec.sha256(new byte[]{1,2,3}));
        assertThrows(InvalidTopologyValueException.class, () -> repository.find("malformed", "r1"));
        // Canonical header order a,z changed to z,a without altering any list element.
        byte[] noncanonical = shuffledHeaders(bytes);
        insert(value.facilityId(), "r1", codec.FORMAT, noncanonical, codec.sha256(noncanonical));
        assertThrows(InvalidTopologyValueException.class, () -> repository.findStored(value.facilityId(), "r1"));
        assertThrows(InvalidTopologyValueException.class, () -> repository.append(value));
    }

    @Test
    void readsRejectPrivilegedDigestAndFormatConstraintBypass() {
        // Administrative bypass in a disposable test schema only.
        jdbc.execute("ALTER TABLE " + TABLE + " DROP CONSTRAINT ck_topology_station_graph_digest");
        var value = fixture("synthetic-station-a", "r1", false);
        insert(value.facilityId(), "r1", codec.FORMAT, codec.encode(value), "a".repeat(64));
        assertThrows(InvalidTopologyValueException.class, () -> repository.findStored(value.facilityId(), "r1"));
        jdbc.execute("ALTER TABLE " + TABLE + " DROP CONSTRAINT ck_topology_station_graph_format");
        var other = fixture("synthetic-station-b", "r1", false); byte[] bytes = codec.encode(other);
        insert(other.facilityId(), "r1", "UNKNOWN", bytes, codec.sha256(bytes));
        assertThrows(InvalidTopologyValueException.class, () -> repository.find(other.facilityId(), "r1"));
    }

    @Test
    void failedTransactionalMigrationLeavesNoPartialTableOrTriggers() throws Exception {
        jdbc.execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        jdbc.execute("CREATE FUNCTION hidra_topology_reject_station_graph_revision_mutation() RETURNS trigger"
                + " LANGUAGE plpgsql AS $$ BEGIN RETURN NEW; END; $$");
        String sql = Files.readString(MIGRATION);
        assertThrows(DataAccessException.class, () -> new TransactionTemplate(transactions).execute(status -> {
            jdbc.execute(sql); return null;
        }));
        assertNull(jdbc.queryForObject("SELECT to_regclass(?)::text", String.class, TABLE));
        assertEquals(0L, jdbc.queryForObject("SELECT count(*) FROM pg_trigger WHERE tgname LIKE 'topology_station_graph_revision_%'", Long.class));
        assertEquals(1L, jdbc.queryForObject("SELECT count(*) FROM pg_proc WHERE proname='hidra_topology_reject_station_graph_revision_mutation'", Long.class));
    }

    @Test
    void freshFullFlywayChainCreatesEmptyStoreWithValidatedHistory() throws Exception {
        jdbc.execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        var flyway = flyway(null); flyway.migrate(); flyway.validate();
        long sourceCount;
        try (var files = Files.list(MIGRATION.getParent())) { sourceCount = files.filter(p -> p.toString().endsWith(".sql")).count(); }
        assertEquals(sourceCount, jdbc.queryForObject("SELECT count(*) FROM flyway_schema_history WHERE success", Long.class));
        assertEquals(0, flyway.info().pending().length);
        assertEquals(0L, count());
        assertEquals(2L, jdbc.queryForObject("SELECT count(*) FROM pg_trigger WHERE tgrelid=?::regclass AND NOT tgisinternal", Long.class, TABLE));
        assertEquals(0L, jdbc.queryForObject("SELECT count(*) FROM pg_constraint WHERE conrelid=?::regclass AND contype='f'", Long.class, TABLE));
    }

    @Test
    void upgradeFromActualPredecessorPreservesPhysicalV1V2BytesAndChecksums() {
        jdbc.execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        var previous = flyway("20261011.001"); previous.migrate(); previous.validate();
        var history = jdbc.queryForList("SELECT installed_rank,version,script,checksum,success FROM flyway_schema_history ORDER BY installed_rank");
        assertEquals(144, history.size());
        var physical = proxy(new JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter(jdbc), TopologyPhysicalNetworkRevisionRepositoryPort.class);
        var v1 = physicalFixture("v1", TopologyPhysicalNetworkRevision.EquipmentKind.COMPRESSOR);
        var v2 = physicalFixture("v2", TopologyPhysicalNetworkRevision.EquipmentKind.REGULATOR);
        physical.append(v1); physical.append(v2);
        byte[] old1 = physicalPayload("v1"); byte[] old2 = physicalPayload("v2");
        var current = flyway(null); assertEquals(1, current.migrate().migrationsExecuted); current.validate();
        assertEquals(history, jdbc.queryForList("SELECT installed_rank,version,script,checksum,success FROM flyway_schema_history WHERE installed_rank<=144 ORDER BY installed_rank"));
        assertArrayEquals(old1, physicalPayload("v1")); assertArrayEquals(old2, physicalPayload("v2"));
        assertEquals(v1, physical.find("synthetic-source", "v1").orElseThrow());
        assertEquals(v2, physical.find("synthetic-source", "v2").orElseThrow());
        assertEquals(0L, count());
        assertEquals(fixture("synthetic-station-a", "r1", true), repository.append(fixture("synthetic-station-a", "r1", true)));
    }

    private Flyway flyway(String target) {
        var config = Flyway.configure().dataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())
                .locations("filesystem:src/main/resources/db/migration");
        if (target != null) config.target(target);
        return config.load();
    }

    private <T> T proxy(Object target, Class<T> contract) {
        var factory = new ProxyFactory(); factory.setTarget(target); factory.setInterfaces(contract);
        factory.addAdvice(new TransactionInterceptor(transactions, new AnnotationTransactionAttributeSource()));
        return contract.cast(factory.getProxy());
    }

    private int race(StationInternalGraphRevision a, StationInternalGraphRevision b) throws Exception {
        var pool = Executors.newFixedThreadPool(2); var ready = new CountDownLatch(2); var go = new CountDownLatch(1);
        try {
            var left = pool.submit(() -> compete(a, ready, go)); var right = pool.submit(() -> compete(b, ready, go));
            assertTrue(ready.await(10, TimeUnit.SECONDS)); go.countDown();
            return left.get(20, TimeUnit.SECONDS) + right.get(20, TimeUnit.SECONDS);
        } finally { go.countDown(); pool.shutdownNow(); }
    }

    private int compete(StationInternalGraphRevision value, CountDownLatch ready, CountDownLatch go) throws Exception {
        ready.countDown(); assertTrue(go.await(10, TimeUnit.SECONDS));
        assertEquals("read committed", jdbc.queryForObject("SHOW transaction_isolation", String.class));
        try { assertEquals(value, repository.append(value)); return 1; }
        catch (InvalidTopologyValueException conflict) { assertTrue(conflict.getMessage().contains("Conflicting")); return 0; }
    }

    private void insert(String facility, String revision, String format, byte[] bytes, String digest) {
        jdbc.update("INSERT INTO " + TABLE + " VALUES (?,?,?,?,?)", facility, revision, format, bytes, digest);
    }
    private long count() { return jdbc.queryForObject("SELECT count(*) FROM " + TABLE, Long.class); }
    private byte[] payload(String facility, String revision) {
        return jdbc.queryForObject("SELECT canonical_payload FROM " + TABLE + " WHERE facility_id=? AND revision_id=?", byte[].class, facility, revision);
    }
    private byte[] physicalPayload(String revision) {
        return jdbc.queryForObject("SELECT canonical_payload FROM hidra_topology_physical_network_revision WHERE source_id='synthetic-source' AND revision_id=?", byte[].class, revision);
    }
    private static <T> List<T> reversed(List<T> values) { var result = new ArrayList<>(values); Collections.reverse(result); return result; }

    private static StationInternalGraphRevision fixture(String facility, String revision, boolean parallel) {
        var graph = new StationInternalGraph(facility, revision, Status.COMPLETE,
                List.of(new Header("a", HeaderKind.SUCTION, "s"), new Header("z", HeaderKind.DISCHARGE, "d")),
                List.of(new Unit("u1", facility, "i1", "o1"), new Unit("u2", facility, "i2", "o2")),
                parallel ? List.of(new Edge("a", "s", "i1"), new Edge("b", "s", "i2"), new Edge("c", "o1", "d"), new Edge("d", "o2", "d"))
                         : List.of(new Edge("a", "s", "i1"), new Edge("b", "o1", "i2"), new Edge("c", "o2", "d")));
        return new StationInternalGraphRevision(facility, revision, graph, SourceClassification.SYNTHETIC,
                Instant.ofEpochSecond(-1, 123456789), Instant.ofEpochSecond(-2, 987654321), Instant.ofEpochSecond(1, 555555555),
                "synthetic:fixture", "absent-predecessor", Qualification.STRUCTURALLY_QUALIFIED);
    }

    private static TopologyPhysicalNetworkRevision physicalFixture(String revision, TopologyPhysicalNetworkRevision.EquipmentKind kind) {
        return new TopologyPhysicalNetworkRevision("synthetic-source", revision, TopologyPhysicalNetworkRevision.ScopeType.PIPELINE_SYSTEM,
                "synthetic-scope", Instant.EPOCH, Instant.EPOCH, null, TopologyPhysicalNetworkRevision.Origin.SYNTHETIC,
                "synthetic:fixture", List.of(new TopologyPhysicalNetworkRevision.Node("a", BigDecimal.ZERO), new TopologyPhysicalNetworkRevision.Node("b", BigDecimal.ONE)),
                List.of(new TopologyPhysicalNetworkRevision.PipeSegment("p", "a", "b", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO)),
                List.of(new TopologyPhysicalNetworkRevision.EquipmentLink("unit", "a", "b", kind)));
    }

    /** Swaps the first two wire headers without canonical re-encoding. */
    private static byte[] shuffledHeaders(byte[] original) {
        var in = ByteBuffer.wrap(original);
        for (int i = 0; i < 4; i++) skipString(in);
        in.position(in.position() + 24);
        if (in.get() == 1) in.position(in.position() + 12);
        if (in.get() == 1) skipString(in);
        if (in.get() == 1) skipString(in);
        for (int i = 0; i < 4; i++) skipString(in);
        assertEquals(2, in.getInt()); int first = in.position();
        for (int i = 0; i < 3; i++) skipString(in); int second = in.position();
        for (int i = 0; i < 3; i++) skipString(in); int end = in.position();
        byte[] result = original.clone();
        System.arraycopy(original, second, result, first, end - second);
        System.arraycopy(original, first, result, first + end - second, second - first);
        return result;
    }
    private static void skipString(ByteBuffer in) { int n = in.getInt(); in.position(in.position() + n); }
}
