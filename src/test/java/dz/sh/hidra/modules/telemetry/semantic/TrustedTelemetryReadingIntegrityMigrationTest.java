/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrustedTelemetryReadingIntegrityMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Telemetry Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.semantic
 *
 * @Description : Exercises trusted-creation gates, provenance and snapshot preservation on PostgreSQL 16.
 *
 */
package dz.sh.hidra.modules.telemetry.semantic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
class TrustedTelemetryReadingIntegrityMigrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
    @BeforeEach
    void baseline() throws Exception {
        execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public; "
            + "CREATE TABLE hidra_telemetry_unit(id text PRIMARY KEY); "
            + "CREATE TABLE hidra_telemetry_ingestion_batch(id text PRIMARY KEY); "
            + "CREATE TABLE hidra_telemetry_type_catalog(id text PRIMARY KEY,catalog_name text,active boolean); "
            + "CREATE TABLE hidra_telemetry_point(id text PRIMARY KEY,status text,unit_id text); "
            + "CREATE TABLE hidra_telemetry_reading(id text PRIMARY KEY,point_id text,numeric_value numeric, "
            + "text_value text,boolean_value boolean,source_timestamp timestamptz,ingestion_batch_id text); "
            + "CREATE TABLE hidra_telemetry_quality_assessment(id text PRIMARY KEY,reading_id text,point_id text, "
            + "resolved_quality_code_id text,trust_level text,assessment_status text); "
            + "CREATE TABLE hidra_telemetry_point_binding(id text PRIMARY KEY,point_id text,active boolean, "
            + "valid_from timestamptz,valid_to timestamptz,topology_asset_type_code text,topology_asset_id text, "
            + "topology_asset_code text,topology_snapshot_id text); "
            + "CREATE TABLE hidra_telemetry_trusted_reading(id text PRIMARY KEY,reading_id text,point_id text, "
            + "numeric_value numeric,text_value text,boolean_value boolean,unit_id text,quality_code_id text, "
            + "trust_level text,source_timestamp timestamptz,trusted_at timestamptz,quality_assessment_id text, "
            + "topology_asset_type_code text,topology_asset_id text,topology_asset_code text,topology_snapshot_id text,ingestion_batch_id text); "
            + "INSERT INTO hidra_telemetry_unit VALUES('unit'); INSERT INTO hidra_telemetry_ingestion_batch VALUES('batch'); "
            + "INSERT INTO hidra_telemetry_type_catalog VALUES('quality','QUALITY_CODE',true); "
            + "INSERT INTO hidra_telemetry_point VALUES('point','ACTIVE','unit'),('other','ACTIVE',null); "
            + "INSERT INTO hidra_telemetry_reading VALUES('reading','point',42,null,null,'2026-10-06T00:00:00Z','batch'); "
            + "INSERT INTO hidra_telemetry_quality_assessment VALUES('assessment','reading','point','quality','MEDIUM','PASSED')");
    }
    @Test
    void rejectsNonPassingUntrustedInactiveAndWrongFamilyEvidence() throws Exception {
        migrate();
        execute("UPDATE hidra_telemetry_quality_assessment SET assessment_status='WARNING'");
        rejects("warning");
        execute("UPDATE hidra_telemetry_quality_assessment SET assessment_status='PASSED',trust_level='LOW'");
        rejects("low");
        execute("UPDATE hidra_telemetry_quality_assessment SET trust_level='MEDIUM'; UPDATE hidra_telemetry_point SET status='MAINTENANCE' WHERE id='point'");
        rejects("inactive");
        execute("UPDATE hidra_telemetry_point SET status='ACTIVE' WHERE id='point'; UPDATE hidra_telemetry_type_catalog SET catalog_name='SIGNAL_TYPE'");
        rejects("wrong-family");
        execute("UPDATE hidra_telemetry_type_catalog SET catalog_name='QUALITY_CODE',active=false");
        rejects("inactive-quality");
        execute("UPDATE hidra_telemetry_type_catalog SET active=true");
        execute(trustedSql("valid","null,null,null,null"));
        assertThat(scalar("SELECT trust_level FROM hidra_telemetry_trusted_reading WHERE id='valid'")).isEqualTo("MEDIUM");
    }
    @Test
    void rejectsIdentityAndOptionalReferenceMismatch() throws Exception {
        migrate();
        execute("UPDATE hidra_telemetry_quality_assessment SET point_id='other'");
        rejects("mismatch");
        execute("UPDATE hidra_telemetry_quality_assessment SET point_id='point'; UPDATE hidra_telemetry_point SET unit_id='missing' WHERE id='point'");
        rejects("missing-unit");
        execute("UPDATE hidra_telemetry_point SET unit_id='unit' WHERE id='point'; UPDATE hidra_telemetry_reading SET ingestion_batch_id='missing'");
        rejects("missing-batch");
        execute("UPDATE hidra_telemetry_reading SET ingestion_batch_id=null; UPDATE hidra_telemetry_point SET unit_id=null WHERE id='point'");
        execute(trustedSql("optional","null,null,null,null"));
        assertThat(scalar("SELECT count(*) FROM hidra_telemetry_trusted_reading WHERE unit_id IS NULL AND ingestion_batch_id IS NULL")).isEqualTo("1");
    }
    @Test
    void copiesApplicableSnapshotAndPreservesItAfterRebindingOrRetirement() throws Exception {
        migrate();
        execute("INSERT INTO hidra_telemetry_point_binding VALUES('binding','point',true,'2026-10-01T00:00:00Z',null,'PIPELINE','asset','ASSET-1','snapshot')");
        rejects("missing-snapshot");
        assertThatThrownBy(() -> execute(trustedSql("wrong-snapshot","'PIPELINE','other','ASSET-1','snapshot'")))
            .isInstanceOf(SQLException.class);
        execute(trustedSql("captured","'PIPELINE','asset','ASSET-1','snapshot'"));
        execute("UPDATE hidra_telemetry_point_binding SET active=false,valid_to='2026-10-07T00:00:00Z'; "
            + "UPDATE hidra_telemetry_point SET status='RETIRED' WHERE id='point'; "
            + "UPDATE hidra_telemetry_trusted_reading SET id=id WHERE id='captured'");
        assertThat(scalar("SELECT topology_asset_id FROM hidra_telemetry_trusted_reading WHERE id='captured'")).isEqualTo("asset");
        assertThatThrownBy(() -> execute("UPDATE hidra_telemetry_trusted_reading SET topology_asset_id='rewritten'"))
            .isInstanceOf(SQLException.class).hasMessageContaining("cannot be rewritten");
        assertThatThrownBy(() -> execute("UPDATE hidra_telemetry_quality_assessment SET point_id='other'"))
            .isInstanceOf(SQLException.class);
    }
    @Test
    void refusesInconsistentLegacyEvidenceWithoutRewritingRows() throws Exception {
        execute(trustedSql("legacy","null,null,null,null"));
        execute("UPDATE hidra_telemetry_trusted_reading SET point_id='other'");
        assertThatThrownBy(this::migrate).isInstanceOf(SQLException.class).hasMessageContaining("HMR-053 preflight failed");
        assertThat(scalar("SELECT point_id FROM hidra_telemetry_trusted_reading WHERE id='legacy'")).isEqualTo("other");
    }
    @Test
    void preservesLegitimateHistoricalSnapshotWhenPointIsNoLongerActive() throws Exception {
        execute(trustedSql("historical","'PIPELINE','historical-asset','OLD',null"));
        execute("UPDATE hidra_telemetry_point SET status='RETIRED' WHERE id='point'");
        migrate();
        assertThat(scalar("SELECT topology_asset_id FROM hidra_telemetry_trusted_reading WHERE id='historical'")).isEqualTo("historical-asset");
    }
    private void rejects(String id) {
        assertThatThrownBy(() -> execute(trustedSql(id,"null,null,null,null"))).isInstanceOf(SQLException.class);
    }
    private String trustedSql(String id, String snapshot) {
        return "INSERT INTO hidra_telemetry_trusted_reading SELECT '"+id+"',r.id,r.point_id,r.numeric_value,r.text_value,"
            + "r.boolean_value,p.unit_id,a.resolved_quality_code_id,a.trust_level,r.source_timestamp,"
            + "'2026-10-06T01:00:00Z',a.id,"+snapshot+",r.ingestion_batch_id FROM hidra_telemetry_reading r "
            + "JOIN hidra_telemetry_point p ON p.id=r.point_id JOIN hidra_telemetry_quality_assessment a ON a.id='assessment'";
    }
    private void migrate() throws Exception {
        String sql = Files.readString(Path.of("src/main/resources/db/migration/V20261006_008__hmr_053_trusted_telemetry_gate.sql"));
        try (var c = connection(); var s = c.createStatement()) {
            c.setAutoCommit(false);
            try { s.execute(sql); c.commit(); }
            catch (SQLException failure) { c.rollback(); throw failure; }
        }
    }
    private void execute(String sql) throws SQLException {
        try (var c = connection(); var s = c.createStatement()) { s.execute(sql); }
    }
    private String scalar(String sql) throws SQLException {
        try (var c = connection(); var s = c.createStatement(); var r = s.executeQuery(sql)) {
            assertThat(r.next()).isTrue(); return r.getString(1);
        }
    }
    private Connection connection() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }
}
