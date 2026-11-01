/**
 * HidraAPI HAPI-602 — actual PostgreSQL 16 draft-link persistence constraints.
 * Synthetic schema-owner fixtures only; no native permission or industrial approval claim.
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
class InstalledCharacteristicLinkPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16").withCommand("postgres","-c","max_locks_per_transaction=256");
    private static volatile boolean migrated;
    private static final Instant START=Instant.parse("2026-01-01T00:00:00Z");
    private static final Instant MIDDLE=Instant.parse("2026-02-01T00:00:00Z");
    private static final Instant END=Instant.parse("2026-03-01T00:00:00Z");
    private static final String TABLE="public.hidra_topology_installed_characteristic_revision";

    private static synchronized void migrate() {
        if (!migrated) {
            Flyway.configure().dataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword())
                    .load().migrate();
            migrated=true;
        }
    }
    private JdbcTemplate jdbc() {
        migrate();
        var source=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        source.setDriverClassName("org.postgresql.Driver");
        return new JdbcTemplate(source);
    }
    private static String id() { return UUID.randomUUID().toString(); }
    private static String digest(byte[] bytes) {
        try {return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));}
        catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private record Fixture(String company,String unit,String link,String source,String revision,String sha) {}
    private Fixture fixture(JdbcTemplate db) {
        String company=id(),unit=id(),link=id(),sourceId=id(),sourceRevision=id();
        byte[] payload="synthetic-characteristic-v1".getBytes(StandardCharsets.UTF_8);
        String sha=digest(payload);
        db.update("""
            INSERT INTO public.hidra_topology_installed_unit(
              id,company_party_id,equipment_kind_id,manufacturer_party_verification,
              manufacturer_model_verification,serial_verification)
            VALUES(?,?,?,'UNKNOWN','UNKNOWN','UNKNOWN')
            """,unit,company,"COMPRESSOR");
        db.update("""
            INSERT INTO public.hidra_simulation_manufacturer_characteristic_revision(
              company_party_id,source_id,revision_id,manufacturer_party_id,manufacturer_model_reference,
              canonical_payload,payload_sha256,workflow_target_digest,document_sha256,
              source_recorded_at,effective_from,source_origin,creator_user_id,
              operation_binding_id,session_id,backend_pid,transaction_id,database_role,inserted_at)
            VALUES (?,?,?,?,?,?,?,?,?,?,?,'SYNTHETIC',
              'synthetic-creator','synthetic-binding','synthetic-session',
              pg_backend_pid(),txid_current(),session_user,clock_timestamp())
            """,company,sourceId,sourceRevision,id(),"synthetic-model",
            payload,sha,digest(("target:"+sourceId).getBytes(StandardCharsets.UTF_8)),
            digest(("document:"+sourceId).getBytes(StandardCharsets.UTF_8)),
            Timestamp.from(Instant.parse("2025-01-01T00:00:00Z")),
            Timestamp.from(Instant.parse("2025-01-01T00:00:00Z")));
        return new Fixture(company,unit,link,sourceId,sourceRevision,sha);
    }

    private void insert(JdbcTemplate db,Fixture f,long revision,String kind,
                        Instant from,Instant until,String sha) {
        db.update("""
            INSERT INTO public.hidra_topology_installed_characteristic_revision(
              company_party_id,installed_unit_id,link_id,link_revision,source_id,
              source_revision_id,source_sha256,source_kind,effective_from,effective_until,
              recorded_at,evidence_reference,operation_binding_id,actor_session_id,
              actor_user_id,backend_pid,transaction_id,database_role)
            VALUES(?,?,?,?,?,?,?,?,?,?,?,?,'synthetic-binding','synthetic-session',
              'synthetic-user',pg_backend_pid(),txid_current(),session_user)
            """,f.company(),f.unit(),f.link(),revision,f.source(),f.revision(),sha,kind,
            Timestamp.from(from),until==null?null:Timestamp.from(until),
            Timestamp.from(from),"synthetic:evidence");
    }

    @Test void freshFlywayInstallsThreeOwnerTablesAndPhysicalExclusion() {
        JdbcTemplate db=jdbc();
        for(String table:new String[]{"revision","head","request"})
            assertEquals(Boolean.TRUE,db.queryForObject(
                "SELECT to_regclass('public.hidra_topology_installed_characteristic_"+table+"') IS NOT NULL",
                Boolean.class));
        assertEquals(Boolean.TRUE,db.queryForObject("""
            SELECT EXISTS(SELECT 1 FROM pg_constraint
               WHERE conrelid='public.hidra_topology_installed_characteristic_revision'::regclass
               AND conname='ex_topology_characteristic_selection' AND contype='x')
            """,Boolean.class));
    }

    @Test void rejectsOverlappingSameCompanyUnitAndKindUnderRealExclusion() {
        JdbcTemplate db=jdbc();var f=fixture(db);
        insert(db,f,1,"COMPRESSOR_MAP",START,MIDDLE,f.sha());
        assertThrows(DataAccessException.class,()->insert(db,
                new Fixture(f.company(),f.unit(),id(),f.source(),f.revision(),f.sha()),
                1,"COMPRESSOR_MAP",START,END,f.sha()));
        assertEquals(1,db.queryForObject("SELECT count(*) FROM "+TABLE+
                " WHERE company_party_id=? AND installed_unit_id=?",Integer.class,f.company(),f.unit()));
    }

    @Test void permitsAdjacentPeriodsAndDistinctKinds() {
        JdbcTemplate db=jdbc();var f=fixture(db);
        insert(db,f,1,"COMPRESSOR_MAP",START,MIDDLE,f.sha());
        insert(db,new Fixture(f.company(),f.unit(),id(),f.source(),f.revision(),f.sha()),
                1,"COMPRESSOR_MAP",MIDDLE,END,f.sha());
        insert(db,new Fixture(f.company(),f.unit(),id(),f.source(),f.revision(),f.sha()),
                1,"OPERATING_LIMITS",START,END,f.sha());
        assertEquals(3,db.queryForObject("SELECT count(*) FROM "+TABLE+
                " WHERE company_party_id=? AND installed_unit_id=?",Integer.class,f.company(),f.unit()));
    }

    @Test void rejectsWrongManufacturerDigestAndMissingSource() {
        JdbcTemplate db=jdbc();var f=fixture(db);
        assertThrows(DataAccessException.class,()->insert(db,f,1,"VALVE_MAP",START,MIDDLE,"a".repeat(64)));
        var missing=new Fixture(f.company(),f.unit(),f.link(),id(),id(),f.sha());
        assertThrows(DataAccessException.class,()->insert(db,missing,1,"VALVE_MAP",START,MIDDLE,f.sha()));
    }

    @Test void rejectsForeignPhysicalUnitAndInvalidDraftState() {
        JdbcTemplate db=jdbc();var f=fixture(db);
        assertThrows(DataAccessException.class,()->insert(db,
                new Fixture(f.company(),id(),f.link(),f.source(),f.revision(),f.sha()),
                1,"COMPRESSOR_MAP",START,MIDDLE,f.sha()));
        assertThrows(DataAccessException.class,()->insert(db,f,1,"UNKNOWN_KIND",START,MIDDLE,f.sha()));
        assertThrows(DataAccessException.class,()->db.update("""
            INSERT INTO public.hidra_topology_installed_characteristic_head(
             company_party_id,installed_unit_id,link_id,link_revision,head_epoch)
            VALUES(?,?,?,?,?)
            """,f.company(),f.unit(),f.link(),1,1));
    }

    @Test void immutableDraftEvidenceRejectsUpdateDeleteAndTruncate() {
        JdbcTemplate db=jdbc();var f=fixture(db);
        insert(db,f,1,"VALVE_MAP",START,MIDDLE,f.sha());
        assertThrows(DataAccessException.class,()->db.update(
            "UPDATE "+TABLE+" SET source_sha256=? WHERE company_party_id=? AND link_id=?",
            "b".repeat(64),f.company(),f.link()));
        assertThrows(DataAccessException.class,()->db.update(
            "DELETE FROM "+TABLE+" WHERE company_party_id=? AND link_id=?",f.company(),f.link()));
        assertThrows(DataAccessException.class,()->db.execute("TRUNCATE "+TABLE));
        assertEquals(1,db.queryForObject(
            "SELECT count(*) FROM "+TABLE+" WHERE company_party_id=? AND link_id=?",
            Integer.class,f.company(),f.link()));
    }

    @Test void headRequiresConsecutiveRevisionAndRejectsStaleEpoch() {
        JdbcTemplate db=jdbc();var f=fixture(db);
        insert(db,f,1,"COMPRESSOR_MAP",START,MIDDLE,f.sha());
        db.update("""
            INSERT INTO public.hidra_topology_installed_characteristic_head(
              company_party_id,installed_unit_id,link_id,link_revision,head_epoch)
            VALUES(?,?,?,1,1)
            """,f.company(),f.unit(),f.link());
        assertThrows(DataAccessException.class,()->db.update("""
            UPDATE public.hidra_topology_installed_characteristic_head
            SET head_epoch=3,link_revision=3 WHERE company_party_id=? AND installed_unit_id=? AND link_id=?
            """,f.company(),f.unit(),f.link()));
        insert(db,f,2,"COMPRESSOR_MAP",MIDDLE,END,f.sha());
        db.update("""
            UPDATE public.hidra_topology_installed_characteristic_head
            SET head_epoch=2,link_revision=2 WHERE company_party_id=? AND installed_unit_id=? AND link_id=?
            """,f.company(),f.unit(),f.link());
        assertEquals(2L,db.queryForObject("""
            SELECT head_epoch FROM public.hidra_topology_installed_characteristic_head
            WHERE company_party_id=? AND installed_unit_id=? AND link_id=?
            """,Long.class,f.company(),f.unit(),f.link()));
    }

    @Test void requestReceiptRequiresRealRevisionAndIsAppendOnly() {
        JdbcTemplate db=jdbc();var f=fixture(db);
        insert(db,f,1,"PARAMETER_TABLE",START,MIDDLE,f.sha());
        String sql="""
            INSERT INTO public.hidra_topology_installed_characteristic_request(
             company_party_id,installed_unit_id,link_id,request_id,command_sha256,link_revision,
             actor_binding_id,actor_session_id,actor_user_id,backend_pid,transaction_id,database_role)
            VALUES(?,?,?,?,?,?,'synthetic-binding','synthetic-session','synthetic-user',
                   pg_backend_pid(),txid_current(),session_user)
            """;
        String request=id(),digest="c".repeat(64);
        db.update(sql,f.company(),f.unit(),f.link(),request,digest,1);
        assertThrows(DataAccessException.class,()->db.update(sql,f.company(),f.unit(),f.link(),request,digest,1));
        assertThrows(DataAccessException.class,()->db.update(
                "DELETE FROM public.hidra_topology_installed_characteristic_request WHERE request_id=?",request));
        assertThrows(DataAccessException.class,()->db.update(sql,f.company(),f.unit(),id(),id(),digest,2));
    }

    @Test void draftRelationsAndLockFunctionGrantNothingToPublic() {
        JdbcTemplate db=jdbc();
        for(String relation:new String[]{"revision","head","request"}) {
            assertEquals(Boolean.FALSE,db.queryForObject("""
                SELECT EXISTS(
                  SELECT 1 FROM pg_class c, LATERAL aclexplode(c.relacl) x
                  WHERE c.oid=?::regclass AND x.grantee=0
                    AND x.privilege_type IN ('SELECT','INSERT','UPDATE','DELETE'))
                """,Boolean.class,"public.hidra_topology_installed_characteristic_"+relation));
        }
        assertEquals(Boolean.FALSE,db.queryForObject("""
            SELECT EXISTS(
               SELECT 1 FROM pg_proc p,LATERAL aclexplode(p.proacl) x
               WHERE p.oid='public.hidra_topology_characteristic_lock_sources()'::regprocedure
                 AND x.grantee=0 AND x.privilege_type='EXECUTE')
            """,Boolean.class));
    }
}
