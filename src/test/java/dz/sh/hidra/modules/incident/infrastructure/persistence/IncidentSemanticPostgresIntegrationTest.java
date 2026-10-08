/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence;

import java.nio.file.*;
import java.sql.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
@Testcontainers(disabledWithoutDocker=true)
class IncidentSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    static final Path MIGRATIONS=Path.of("src/main/resources/db/migration");
    Connection connection() throws SQLException {return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String sql) throws SQLException {try(var c=connection();var s=c.createStatement()){s.execute(sql);}}
    void file(String name) throws Exception {
        try(var c=connection()){c.setAutoCommit(false);try(var s=c.createStatement()){s.execute(Files.readString(MIGRATIONS.resolve(name)));c.commit();}
            catch(Exception e){c.rollback();throw e;}}
    }
    void baseline() throws Exception {
        sql("DROP SCHEMA public CASCADE;CREATE SCHEMA public");file("V20260611_010__create_incident_tables.sql");
        sql("ALTER TABLE hidra_incident_related_incident ADD CONSTRAINT fk_hra111_incident_013 FOREIGN KEY(related_incident_id) REFERENCES hidra_incident_catalog_entry(id)");
        sql("INSERT INTO hidra_incident_catalog_entry VALUES ('classification','INCIDENT_CLASSIFICATION','C',true,0,false,now(),now()),('severity','INCIDENT_SEVERITY','S',true,0,false,now(),now()),('wrong','OTHER','W',true,0,false,now(),now()),('action','RESPONSE_ACTION_TYPE','A',true,0,false,now(),now()),('relationship','RELATED_INCIDENT_RELATIONSHIP_TYPE','R',true,0,false,now(),now())");
        file("V20261008_007__hmr_062_incident_reference_lifecycle_integrity.sql");
    }
    void incident(String id,String state) throws SQLException {
        sql("INSERT INTO hidra_incident(id,incident_number,title,classification_id,severity_id,status,source_type,detected_at,reported_at,current_escalation_level,created_by_actor_id,created_at,updated_at,responsible_actor_id,responsible_actor_name_snapshot,resolved_at) VALUES ('"+id+"','"+id+"','Incident','classification','severity','"+state+"','MANUAL',now(),now(),0,'actor',now(),now(),'actor','Actor',"+(state.equals("RESOLVED") ? "now()" : "null")+")");
    }
    @Test void catalogAndTimeGuardsRejectInvalidWrites() throws Exception {
        baseline();incident("incident","OPEN");
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_incident SET classification_id='wrong'"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_incident SET detected_at=reported_at+interval '1 second'"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_incident SET closed_at=now()"));
    }
    @Test void wrongLegacyCatalogRequiresReconciliation() throws Exception {
        baseline();sql("DROP TRIGGER tr_hmr062_incident_catalog ON hidra_incident");incident("incident","OPEN");sql("UPDATE hidra_incident SET classification_id='wrong'");
        sql("DROP FUNCTION hidra_incident_catalog_guard() CASCADE");
        assertThrows(SQLException.class,() -> file("V20261008_007__hmr_062_incident_reference_lifecycle_integrity.sql"));
    }

    void relationships() throws Exception {baseline();file("V20261008_008__hmr_091_incident_relationship_integrity.sql");incident("a","OPEN");incident("b","OPEN");}
    void link(String id,String from,String to) throws SQLException {
        sql("INSERT INTO hidra_incident_related_incident VALUES ('"+id+"','"+from+"','"+to+"','relationship',null,'actor',now())");
    }
    @Test void correctedFkAcceptsIncidentIdsAndRejectsCatalogIds() throws Exception {
        relationships();sql("INSERT INTO hidra_incident_relationship_policy VALUES ('relationship','SYMMETRIC',null,true)");link("link","a","b");
        assertThrows(SQLException.class,() -> link("bad","a","wrong"));
    }
    @Test void explicitPolicyAndNoSelfLinkAreRequired() throws Exception {
        relationships();assertThrows(SQLException.class,() -> link("missing-policy","a","b"));
        sql("INSERT INTO hidra_incident_relationship_policy VALUES ('relationship','SYMMETRIC',null,true)");
        assertThrows(SQLException.class,() -> link("self","a","a"));
    }
    @Test void symmetricInverseCannotCreateDuplicateEvidence() throws Exception {
        relationships();sql("INSERT INTO hidra_incident_relationship_policy VALUES ('relationship','SYMMETRIC',null,true)");link("first","b","a");
        assertThrows(SQLException.class,() -> link("inverse","a","b"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_incident_related_incident SET comment='rewrite'"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_incident_relationship_policy SET active=false"));
    }
    @Test void legacyRelatedCatalogIdBlocksMigrationWithoutRewritingIt() throws Exception {
        baseline();incident("a","OPEN");link("legacy","a","wrong");
        assertThrows(SQLException.class,() -> file("V20261008_008__hmr_091_incident_relationship_integrity.sql"));
    }
    @Test void concurrentSymmetricInverseOnlyCommitsOnce() throws Exception {
        relationships();sql("INSERT INTO hidra_incident_relationship_policy VALUES ('relationship','SYMMETRIC',null,true)");
        try(var first=connection();var second=connection()) {
            first.setAutoCommit(false);second.setAutoCommit(false);
            try(var statement=first.createStatement()) {statement.execute("INSERT INTO hidra_incident_related_incident VALUES ('first','a','b','relationship',null,'actor',now())");}
            var executor=java.util.concurrent.Executors.newSingleThreadExecutor();
            try {
                var future=executor.submit(() -> {try(var statement=second.createStatement()){statement.execute("SET LOCAL statement_timeout='5s'");statement.execute("INSERT INTO hidra_incident_related_incident VALUES ('second','b','a','relationship',null,'actor',now())");second.commit();return true;}catch(SQLException denied){second.rollback();return false;}});
                first.commit();assertFalse(future.get(10,java.util.concurrent.TimeUnit.SECONDS));
            } finally {executor.shutdownNow();}
        }
    }
}
