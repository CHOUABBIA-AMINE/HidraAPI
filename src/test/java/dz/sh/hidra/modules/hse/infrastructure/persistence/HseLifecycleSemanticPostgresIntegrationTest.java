/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseLifecycleSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence;

import java.nio.file.*;
import java.sql.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
/** Real PostgreSQL baseline and actual forward SQL; no reconstructed operational evidence. */
@Testcontainers(disabledWithoutDocker=true)
class HseLifecycleSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    Connection connection() throws SQLException {return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String text) throws SQLException {try(var c=connection();var s=c.createStatement()) {s.execute(text);}}
    void file(String name) throws Exception {try(var c=connection()) {c.setAutoCommit(false);try(var s=c.createStatement()) {s.execute(Files.readString(Path.of("src/main/resources/db/migration",name)));c.commit();}catch(Exception e) {c.rollback();throw e;}}}
    @BeforeEach void baseline() throws Exception {sql("DROP SCHEMA public CASCADE;CREATE SCHEMA public");file("V20260611_012__create_hse_tables.sql");}
    void migrate() throws Exception {file("V20261008_014__hmr_082_hse_case_lifecycle.sql");}
    void parent() throws SQLException {sql("INSERT INTO hidra_hse_case(id,case_number,title,case_type_id,severity_id,status,source_type,reported_at,created_at,updated_at) VALUES('case','CASE','Title','type','severity','OPEN','MANUAL',now(),now(),now())");}
    @Test void genericClosedInsertAndStandaloneClosureCannotCommit() throws Exception {
        migrate();parent();assertThrows(SQLException.class,() -> sql("UPDATE hidra_hse_case SET status='CLOSED',closed_at=now() WHERE id='case'"));
        assertThrows(SQLException.class,() -> sql("INSERT INTO hidra_hse_closure VALUES('closure','case','summary',true,true,true,false,'actor','Actor',now(),null)"));
    }
    @Test void incoherentLegacyLifecycleAbortsWithoutManufacturingEvidence() throws Exception {
        parent();sql("UPDATE hidra_hse_case SET status='CLOSED',closed_at=now()");assertThrows(SQLException.class,this::migrate);
        try(var c=connection();var s=c.createStatement();var rows=s.executeQuery("SELECT count(*) FROM hidra_hse_case_status_history")) {assertTrue(rows.next());assertEquals(0,rows.getInt(1));}
    }
    @Test void statusHistoryCannotBeOverwrittenDeletedOrTruncated() throws Exception {
        migrate();parent();sql("INSERT INTO hidra_hse_case_status_history(id,hse_case_id,new_status,changed_at) VALUES('history','case','OPEN',now())");
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_hse_case_status_history SET new_status='IN_PROGRESS'"));
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_hse_case_status_history"));assertThrows(SQLException.class,() -> sql("TRUNCATE hidra_hse_case_status_history"));
    }
}
