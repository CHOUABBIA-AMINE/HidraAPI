/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTransferTicketSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence
 *
 * @Description : Validates owner-controlled CustodyTransferTicket references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence;

import java.nio.file.*;
import java.sql.*;
import java.util.concurrent.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
/** Isolated real base tables plus the actual forward SQL; full Flyway baseline is verified by repository CI. */
@Testcontainers(disabledWithoutDocker=true)
class CustodyTransferTicketSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    Connection connection() throws SQLException {return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String text) throws SQLException {try(var c=connection();var statement=c.createStatement()) {statement.execute(text);}}
    void file(String name) throws Exception {
        try(var c=connection()) {c.setAutoCommit(false);try(var statement=c.createStatement()) {statement.execute(Files.readString(Path.of("src/main/resources/db/migration",name)));c.commit();}
            catch(Exception e) {c.rollback();throw e;}}
    }
    @BeforeEach void baseline() throws Exception {sql("DROP SCHEMA public CASCADE;CREATE SCHEMA public");file("V20260611_015__create_custody_tables.sql");}
    void migrate() throws Exception {file("V20261008_012__hmr_070_custody_custody_transfer_ticket.sql");}
    void child(String id) throws SQLException {sql("INSERT INTO hidra_custody_transfer_ticket(id,ticket_number,measurement_period_id,agreement_id,transfer_point_id,status,ticket_date,created_at,updated_at) VALUES(%s,'required','required','required','required','required',now(),now(),now())".replace("%s","'"+id+"'"));}
    void parents() throws SQLException {
        sql("INSERT INTO hidra_custody_batch(id,batch_number,measurement_period_id,agreement_id,product_type_id,status,created_at,updated_at) VALUES('local','required','required','required','required','required',now(),now())");
        sql("INSERT INTO hidra_custody_quantity_calculation(id,calculation_number,measurement_period_id,quantity_basis,quantity_unit_id,calculated_at,official,created_at) VALUES('local','required','required','required','required',now(),false,now())");
    }
    @Test void nullOptionalReferencesAndValidParentLinksAreAccepted() throws Exception {
        migrate();child("child");parents();sql("UPDATE hidra_custody_transfer_ticket SET batch_id='local',quantity_calculation_id='local' WHERE id='child'");
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_custody_batch WHERE id='local'"));
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_custody_quantity_calculation WHERE id='local'"));
    }
    @Test void nonexistentLocalParentsRejectWrites() throws Exception {migrate();child("child");
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_custody_transfer_ticket SET batch_id='missing'"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_custody_transfer_ticket SET quantity_calculation_id='missing'"));
    }
    @Test void legacyOrphanFailsMigrationWithoutPartialConstraints() throws Exception {
        child("legacy");sql("UPDATE hidra_custody_transfer_ticket SET batch_id='missing'");assertThrows(SQLException.class,this::migrate);
        try(var c=connection();var statement=c.createStatement();var rows=statement.executeQuery("SELECT count(*) FROM pg_constraint WHERE conname='fk_hmr070_batch_id'")) {assertTrue(rows.next());assertEquals(0,rows.getInt(1));}
        try(var c=connection();var statement=c.createStatement();var rows=statement.executeQuery("SELECT batch_id FROM hidra_custody_transfer_ticket WHERE id='legacy'")) {assertTrue(rows.next());assertEquals("missing",rows.getString(1));}
    }
    @Test void committedParentDeletionWinsAgainstConcurrentReferenceWrite() throws Exception {
        migrate();parents();child("child");var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var deleting=connection()) {
            deleting.setAutoCommit(false);try(var statement=deleting.createStatement()) {statement.execute("DELETE FROM hidra_custody_batch WHERE id='local'");}
            var write=executor.submit(() -> {try(var writing=connection();var statement=writing.createStatement()) {attempting.countDown();statement.execute("UPDATE hidra_custody_transfer_ticket SET batch_id='local' WHERE id='child'");return false;}
                catch(SQLException e) {if(!"23503".equals(e.getSQLState())) throw e;return true;}});
            assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> write.get(150,TimeUnit.MILLISECONDS));
            deleting.commit();assertTrue(write.get(10,TimeUnit.SECONDS));
        } finally {executor.shutdownNow();}
    }
}
