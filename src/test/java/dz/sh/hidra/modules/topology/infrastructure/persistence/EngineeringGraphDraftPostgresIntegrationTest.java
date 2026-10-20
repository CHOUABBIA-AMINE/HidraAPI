/**
 * HAPI-588B: independent fresh-schema PostgreSQL DDL/trigger contract verification.
 * This is NOT a substitute for the registered end-to-end principal/fence/concurrency tests.
 * It must actually execute with Docker to count as PostgreSQL evidence.
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodec;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HexFormat;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers // mandatory: fail rather than silently skip when Docker/PostgreSQL is absent
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EngineeringGraphDraftPostgresIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");

    private static final String MIGRATION =
        "db/migration/V20261019_002__p25_topology_engineering_graph_snapshots.sql";
    private final EngineeringGraphDraftCodec codec = new EngineeringGraphDraftCodec();

    private Connection connection() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }

    /** Minimal parent witness for DDL isolation; end-to-end tests MUST use genuine prior Flyway migrations. */
    @BeforeEach void applyFreshMigration() throws Exception {
        try (var c=connection();var q=c.createStatement()) {
            q.execute("DROP TABLE IF EXISTS public.hidra_topology_engineering_graph_snapshot CASCADE");
            q.execute("DROP TABLE IF EXISTS public.hidra_topology_engineering_draft_version CASCADE");
            q.execute("DROP FUNCTION IF EXISTS public.hidra_p25_graph_snapshot_same_transaction() CASCADE");
            q.execute("DROP FUNCTION IF EXISTS public.hidra_p25_draft_immutable() CASCADE");
            q.execute("CREATE TABLE public.hidra_topology_engineering_draft_version ("+
                "draft_id uuid NOT NULL, version bigint NOT NULL, payload_format varchar(40) NOT NULL, " +
                "canonical_payload bytea NOT NULL, sha256 varchar(64) NOT NULL, transaction_id bigint NOT NULL, " +
                "PRIMARY KEY(draft_id,version))");
            q.execute("CREATE FUNCTION public.hidra_p25_draft_immutable() RETURNS trigger " +
                "LANGUAGE plpgsql AS $$ BEGIN RAISE EXCEPTION 'Immutable parent'; END $$");
            try (var stream=Thread.currentThread().getContextClassLoader().getResourceAsStream(MIGRATION)) {
                assertNotNull(stream,"Registered additive Flyway SQL must exist on test classpath");
                q.execute(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
            }
        }
    }

    private byte[] graphBytes() {
        var graph=new EngineeringGraphDraft(
            java.util.List.of(new EngineeringGraphDraft.Node("a",EngineeringGraphDraft.NodeKind.JUNCTION,null),
                new EngineeringGraphDraft.Node("b",EngineeringGraphDraft.NodeKind.JUNCTION,null)),
            java.util.List.of(new EngineeringGraphDraft.Port("p1","a",EngineeringGraphDraft.PortDirection.OUTLET),
                new EngineeringGraphDraft.Port("p2","b",EngineeringGraphDraft.PortDirection.INLET)),
            java.util.List.of(new EngineeringGraphDraft.Branch("pipe",EngineeringGraphDraft.BranchKind.PIPE,"p1","p2")));
        return codec.encode(graph);
    }

    private void metadata(Connection c,UUID draftId,long version,long transactionId) throws SQLException {
        try(var p=c.prepareStatement("INSERT INTO public.hidra_topology_engineering_draft_version " +
            "(draft_id,version,payload_format,canonical_payload,sha256,transaction_id) " +
            "VALUES (?,?,'HIDRA_ENGINEERING_METADATA_V1',?, ?,?)")){
            byte[] payload="minimal-fixture".getBytes(StandardCharsets.UTF_8);
            p.setObject(1,draftId);p.setLong(2,version);p.setBytes(3,payload);
            p.setString(4,HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(payload)));
            p.setLong(5,transactionId);assertEquals(1,p.executeUpdate());
        } catch(java.security.NoSuchAlgorithmException impossible){throw new AssertionError(impossible);}
    }

    private long xid(Connection c) throws SQLException {
        try(var st=c.createStatement();var r=st.executeQuery("SELECT txid_current()")){
            assertTrue(r.next());return r.getLong(1);
        }
    }

    private void snapshot(Connection c,UUID draftId,long version,long transactionId,byte[] bytes,String sha) throws SQLException{
        try (PreparedStatement p=c.prepareStatement("INSERT INTO public.hidra_topology_engineering_graph_snapshot " +
            "(draft_id,version,payload_format,canonical_payload,sha256,transaction_id) VALUES (?,?,?,?,?,?)")) {
            p.setObject(1,draftId);p.setLong(2,version);p.setString(3,EngineeringGraphDraftCodec.FORMAT);
            p.setBytes(4,bytes);p.setString(5,sha);p.setLong(6,transactionId);
            assertEquals(1,p.executeUpdate());
        }
    }

    @Test void samePhysicalTransactionAppendsDigestCheckedImmutableSnapshot() throws Exception {
        UUID id=UUID.randomUUID();byte[] bytes=graphBytes();String hash=codec.sha256(bytes);
        try(var c=connection()){
            c.setAutoCommit(false);long transaction=xid(c);metadata(c,id,1,transaction);
            snapshot(c,id,1,transaction,bytes,hash);c.commit();
            try(var q=c.prepareStatement("SELECT payload_format,canonical_payload,sha256,transaction_id " +
                 "FROM public.hidra_topology_engineering_graph_snapshot WHERE draft_id=? AND version=1")){
                q.setObject(1,id);try(ResultSet rs=q.executeQuery()){
                    assertTrue(rs.next());assertEquals(EngineeringGraphDraftCodec.FORMAT,rs.getString(1));
                    assertArrayEquals(bytes,rs.getBytes(2));assertEquals(hash,rs.getString(3));
                    assertEquals(transaction,rs.getLong(4));assertFalse(rs.next());
                }
            }
            try(var q=c.createStatement()){
                assertThrows(SQLException.class,()->q.executeUpdate(
                    "UPDATE public.hidra_topology_engineering_graph_snapshot SET version=2"));
                c.rollback();
                assertThrows(SQLException.class,()->q.executeUpdate(
                    "DELETE FROM public.hidra_topology_engineering_graph_snapshot"));
                c.rollback();
                assertThrows(SQLException.class,()->q.executeUpdate(
                    "TRUNCATE public.hidra_topology_engineering_graph_snapshot"));
                c.rollback();
            }
        }
    }

    @Test void differentTransactionCannotClaimParentMetadataVersion() throws Exception {
        UUID id=UUID.randomUUID();byte[] bytes=graphBytes();String hash=codec.sha256(bytes);
        try(var c=connection()){
            c.setAutoCommit(false);long first=xid(c);metadata(c,id,1,first);c.commit();
            long second=xid(c);assertNotEquals(first,second);
            assertThrows(SQLException.class,()->snapshot(c,id,1,second,bytes,hash));c.rollback();
        }
    }

    @Test void invalidDigestAndAbsentMetadataAreRejected() throws Exception {
        UUID id=UUID.randomUUID();byte[] bytes=graphBytes();
        try(var c=connection()){
            c.setAutoCommit(false);long transaction=xid(c);metadata(c,id,1,transaction);
            assertThrows(SQLException.class,()->snapshot(c,id,1,transaction,bytes,"0".repeat(64)));c.rollback();
            assertThrows(SQLException.class,()->snapshot(c,UUID.randomUUID(),7,xid(c),bytes,codec.sha256(bytes)));
            c.rollback();
        }
    }


    @Test void actualCatalogConfirmsAllThreeReviewedTriggersAndImmutableSecurityDefiner() throws Exception {
        try(var c=connection();var q=c.createStatement();var rs=q.executeQuery("""
            SELECT count(*) AS n, bool_and(t.tgenabled='O' AND t.tgqual IS NULL
                AND t.tgnargs=0 AND t.tgparentid=0 AND t.tgconstraint=0)
            FROM pg_trigger t WHERE NOT t.tgisinternal
              AND t.tgrelid='public.hidra_topology_engineering_graph_snapshot'::regclass
            """)) {
            assertTrue(rs.next());assertEquals(3,rs.getInt(1));assertTrue(rs.getBoolean(2));
        }
        try(var c=connection();var q=c.createStatement();var rs=q.executeQuery("""
            SELECT p.prosecdef,p.pronargs=0,p.prorettype='trigger'::regtype,
                md5(p.prosrc),p.proconfig=ARRAY['search_path=pg_catalog, pg_temp']
            FROM pg_proc p WHERE p.oid='public.hidra_p25_graph_snapshot_same_transaction()'::regprocedure
            """)) {
            assertTrue(rs.next());assertTrue(rs.getBoolean(1));assertTrue(rs.getBoolean(2));
            assertTrue(rs.getBoolean(3));
            assertEquals("f21c1f96289a7e167165be8637d1d28d",rs.getString(4));
            assertTrue(rs.getBoolean(5));assertFalse(rs.next());
        }
    }

    @Test void disablingImmutabilityTriggerIsObservableAsCatalogTampering() throws Exception {
        try(var c=connection();var q=c.createStatement()) {
            q.execute("ALTER TABLE public.hidra_topology_engineering_graph_snapshot "
                +"DISABLE TRIGGER trg_p25_engineering_graph_immutable");
            try(var rs=q.executeQuery("""
                SELECT count(*)=3 AND bool_and(t.tgenabled='O')
                FROM pg_trigger t WHERE NOT t.tgisinternal
                  AND t.tgrelid='public.hidra_topology_engineering_graph_snapshot'::regclass
                """)) {
                assertTrue(rs.next());assertFalse(rs.getBoolean(1));
            }
        }
    }
}
