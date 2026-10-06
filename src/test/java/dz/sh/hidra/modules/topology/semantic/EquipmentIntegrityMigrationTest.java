/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentIntegrityMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.semantic
 *
 * @Description : Exercises catalog migration and nullable Equipment attachments on PostgreSQL 16.
 *
 */
package dz.sh.hidra.modules.topology.semantic;

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
class EquipmentIntegrityMigrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
    @BeforeEach
    void baseline() throws Exception {
        execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public; "
            + "CREATE TABLE hidra_topology_equipment_type(id text PRIMARY KEY,code text,equipment_kind text NOT NULL); "
            + "CREATE TABLE hidra_topology_facility(id text PRIMARY KEY); "
            + "CREATE TABLE hidra_topology_node(id text PRIMARY KEY); "
            + "CREATE TABLE hidra_topology_pipeline_segment(id text PRIMARY KEY); "
            + "CREATE TABLE hidra_topology_equipment(id text PRIMARY KEY,equipment_type_id text REFERENCES hidra_topology_equipment_type(id), "
            + "equipment_kind text NOT NULL,facility_id text,node_id text,pipeline_segment_id text); "
            + "INSERT INTO hidra_topology_equipment_type VALUES('type','PUMP_TYPE','PUMP'); "
            + "INSERT INTO hidra_topology_facility VALUES('facility'); INSERT INTO hidra_topology_node VALUES('node'); "
            + "INSERT INTO hidra_topology_pipeline_segment VALUES('segment'); "
            + "INSERT INTO hidra_topology_equipment VALUES('legacy','type','PUMP','facility','node','segment')");
    }
    @Test
    void preservesLegacyValuesAndAllowsNewCatalogCodesWithoutFixedKinds() throws Exception {
        migrate();
        assertThat(scalar("SELECT legacy_equipment_kind FROM hidra_topology_equipment WHERE id='legacy'")).isEqualTo("PUMP");
        assertThat(scalar("SELECT legacy_equipment_kind FROM hidra_topology_equipment_type WHERE id='type'")).isEqualTo("PUMP");
        execute("INSERT INTO hidra_topology_equipment_type(id,code) VALUES('custom','NEW_ENTERPRISE_CLASSIFICATION'); "
            + "INSERT INTO hidra_topology_equipment(id,equipment_type_id) VALUES('new','custom')");
        assertThat(scalar("SELECT count(*) FROM hidra_topology_equipment WHERE id='new' AND legacy_equipment_kind IS NULL")).isEqualTo("1");
        assertThatThrownBy(() -> execute("INSERT INTO hidra_topology_equipment(id,equipment_type_id) VALUES('bad','missing')"))
            .isInstanceOf(SQLException.class);
    }
    @Test
    void rejectsOrphanAttachmentsOnInsertUpdateAndParentDelete() throws Exception {
        migrate();
        for (String column : new String[]{"facility_id","node_id","pipeline_segment_id"}) {
            assertThatThrownBy(() -> execute("UPDATE hidra_topology_equipment SET "+column+"='missing' WHERE id='legacy'"))
                .isInstanceOf(SQLException.class);
            assertThatThrownBy(() -> execute("INSERT INTO hidra_topology_equipment(id,equipment_type_id,"+column+") VALUES('bad','type','missing')"))
                .isInstanceOf(SQLException.class);
        }
        for (String table : new String[]{"hidra_topology_facility","hidra_topology_node","hidra_topology_pipeline_segment"}) {
            assertThatThrownBy(() -> execute("DELETE FROM "+table)).isInstanceOf(SQLException.class);
        }
        execute("INSERT INTO hidra_topology_equipment(id,equipment_type_id) VALUES('unattached','type')");
    }
    @Test
    void conflictingLegacyTaxonomyAbortsWithoutInventingClassification() throws Exception {
        execute("UPDATE hidra_topology_equipment SET equipment_kind='VALVE' WHERE id='legacy'");
        assertThatThrownBy(this::migrate).isInstanceOf(SQLException.class).hasMessageContaining("conflicting legacy equipment classification");
        assertThat(scalar("SELECT equipment_kind FROM hidra_topology_equipment WHERE id='legacy'")).isEqualTo("VALVE");
    }
    @Test
    void legacyOrphanAttachmentAbortsWithoutRewritingIt() throws Exception {
        execute("UPDATE hidra_topology_equipment SET facility_id='missing' WHERE id='legacy'");
        assertThatThrownBy(this::migrate).isInstanceOf(SQLException.class).hasMessageContaining("orphan equipment attachment");
        assertThat(scalar("SELECT facility_id FROM hidra_topology_equipment WHERE id='legacy'")).isEqualTo("missing");
    }
    private void migrate() throws Exception {
        String sql = Files.readString(Path.of("src/main/resources/db/migration/V20261006_009__hmr_054_equipment_catalog_and_attachments.sql"));
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
