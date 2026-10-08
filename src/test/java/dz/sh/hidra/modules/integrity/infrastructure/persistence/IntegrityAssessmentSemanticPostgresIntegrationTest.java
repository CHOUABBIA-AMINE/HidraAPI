/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessmentSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence
 *
 * @Description : Validates owner-controlled IntegrityAssessment references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence;

import java.nio.file.*;
import java.sql.*;
import java.util.concurrent.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
/** Isolated real base tables plus the actual forward SQL; full Flyway baseline is verified by repository CI. */
@Testcontainers(disabledWithoutDocker=true)
class IntegrityAssessmentSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    Connection connection() throws SQLException {return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String text) throws SQLException {try(var c=connection();var statement=c.createStatement()) {statement.execute(text);}}
    void file(String name) throws Exception {
        try(var c=connection()) {c.setAutoCommit(false);try(var statement=c.createStatement()) {statement.execute(Files.readString(Path.of("src/main/resources/db/migration",name)));c.commit();}
            catch(Exception e) {c.rollback();throw e;}}
    }
    @BeforeEach void baseline() throws Exception {sql("DROP SCHEMA public CASCADE;CREATE SCHEMA public");file("V20260611_013__create_integrity_tables.sql");}
    void migrate() throws Exception {file("V20261008_013__hmr_072_integrity_integrity_assessment.sql");}
    void child(String id) throws SQLException {sql("INSERT INTO hidra_integrity_assessment(id,assessment_number,title,assessment_type_id,status,assessment_date,created_at,updated_at) VALUES(%s,'required','required','required','required',now(),now(),now())".replace("%s","'"+id+"'"));}
    void parents() throws SQLException {
        sql("INSERT INTO hidra_integrity_program(id,code,name_fr,program_type_id,status,created_at,updated_at) VALUES('local','required','required','required','required',now(),now())");
    }
    @Test void nullOptionalReferencesAndValidParentLinksAreAccepted() throws Exception {
        migrate();child("child");parents();sql("UPDATE hidra_integrity_assessment SET program_id='local' WHERE id='child'");
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_integrity_program WHERE id='local'"));
    }
    @Test void nonexistentLocalParentsRejectWrites() throws Exception {migrate();child("child");
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_integrity_assessment SET program_id='missing'"));
    }
    @Test void legacyOrphanFailsMigrationWithoutPartialConstraints() throws Exception {
        child("legacy");sql("UPDATE hidra_integrity_assessment SET program_id='missing'");assertThrows(SQLException.class,this::migrate);
        try(var c=connection();var statement=c.createStatement();var rows=statement.executeQuery("SELECT count(*) FROM pg_constraint WHERE conname='fk_hmr072_program_id'")) {assertTrue(rows.next());assertEquals(0,rows.getInt(1));}
        try(var c=connection();var statement=c.createStatement();var rows=statement.executeQuery("SELECT program_id FROM hidra_integrity_assessment WHERE id='legacy'")) {assertTrue(rows.next());assertEquals("missing",rows.getString(1));}
    }
    @Test void committedParentDeletionWinsAgainstConcurrentReferenceWrite() throws Exception {
        migrate();parents();child("child");var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var deleting=connection()) {
            deleting.setAutoCommit(false);try(var statement=deleting.createStatement()) {statement.execute("DELETE FROM hidra_integrity_program WHERE id='local'");}
            var write=executor.submit(() -> {try(var writing=connection();var statement=writing.createStatement()) {attempting.countDown();statement.execute("UPDATE hidra_integrity_assessment SET program_id='local' WHERE id='child'");return false;}
                catch(SQLException e) {if(!"23503".equals(e.getSQLState())) throw e;return true;}});
            assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> write.get(150,TimeUnit.MILLISECONDS));
            deleting.commit();assertTrue(write.get(10,TimeUnit.SECONDS));
        } finally {executor.shutdownNow();}
    }
}
