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
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import jakarta.persistence.EntityManager;
import dz.sh.hidra.modules.incident.application.port.out.IncidentClosureRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.IncidentClosure;
import dz.sh.hidra.modules.incident.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.identity.application.contract.incident.IncidentActorContract;
import dz.sh.hidra.modules.workflow.application.contract.incident.IncidentWorkflowContract;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.support.TransactionTemplate;
import static org.mockito.Mockito.mock;

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

    void responses() throws Exception {relationships();file("V20261008_009__hmr_092_incident_response_action_integrity.sql");}
    void action(String id,String parent,String type,String description) throws SQLException {
        sql("INSERT INTO hidra_incident_response_action(id,incident_id,action_type_id,action_status,description,created_at,updated_at) VALUES ('"+id+"','"+parent+"','"+type+"','PLANNED','"+description+"',now(),now())");
    }
    @Test void actionCatalogAndDescriptionAreEnforced() throws Exception {
        responses();action("valid","a","action","Response");
        assertThrows(SQLException.class,() -> action("wrong","a","wrong","Response"));
        assertThrows(SQLException.class,() -> action("blank","a","action"," "));
    }
    @Test void terminalDraftAndMissingParentsRejectNewActions() throws Exception {
        responses();assertThrows(SQLException.class,() -> action("missing","missing","action","Response"));
        for(String state:new String[]{"DRAFT","CLOSED","CANCELLED","MERGED"}) {
            incident(state,state);assertThrows(SQLException.class,() -> action("action-"+state,state,"action","Response"));
        }
    }

    void closures() throws Exception {
        responses();file("V20261008_010__hmr_090_incident_closure_governance.sql");incident("resolved","RESOLVED");
        sql("INSERT INTO hidra_incident_catalog_entry VALUES ('resolution','RESOLUTION_TYPE','RESOLUTION',true,0,false,now(),now())");
        sql("INSERT INTO hidra_incident_closure_policy VALUES ('classification','severity',true,true,false,false,null,null)");
    }
    void resolutionAndEvidence() throws SQLException {
        sql("INSERT INTO hidra_incident_resolution(id,incident_id,resolution_type_id,resolution_summary,corrective_action_required,preventive_action_required,resolved_by_actor_id,resolved_at) SELECT 'resolution','resolved','resolution','Verified repair',false,false,'actor',resolved_at FROM hidra_incident WHERE id='resolved'");
        sql("INSERT INTO hidra_incident_evidence_link(id,incident_id,evidence_type,evidence_reference_id,attached_by_actor_id,attached_at) VALUES ('evidence','resolved','DOCUMENT','document','actor',now())");
    }
    String closureInsert(String id,String parent) {return "INSERT INTO hidra_incident_closure VALUES ('"+id+"','"+parent+"','Verified closure',true,true,false,false,'actor','Actor',now(),null)";}
    void pair(Connection c,String id) throws SQLException {
        try(var s=c.createStatement()) {s.execute(closureInsert(id,"resolved"));s.execute("UPDATE hidra_incident SET status='CLOSED',closed_at=(SELECT closed_at FROM hidra_incident_closure WHERE id='"+id+"'),updated_at=now() WHERE id='resolved'");}
    }
    int count(String table) throws SQLException {try(var c=connection();var s=c.createStatement();var rows=s.executeQuery("SELECT count(*) FROM "+table)){rows.next();return rows.getInt(1);}}
    @Test void closureRowAndClosedStateCannotCommitIndependently() throws Exception {
        closures();resolutionAndEvidence();assertThrows(SQLException.class,() -> sql(closureInsert("closure","resolved")));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_incident SET status='CLOSED',closed_at=now() WHERE id='resolved'"));
        assertEquals(0,count("hidra_incident_closure"));
    }
    @Test void closureRequiresResolutionAndPolicyRequiredEvidence() throws Exception {
        closures();try(var c=connection()){c.setAutoCommit(false);pair(c,"closure");assertThrows(SQLException.class,c::commit);c.rollback();}
        resolutionAndEvidence();sql("DELETE FROM hidra_incident_evidence_link");
        try(var c=connection()){c.setAutoCommit(false);pair(c,"closure");assertThrows(SQLException.class,c::commit);c.rollback();}
    }
    @Test void missingPolicyWrongStateAndFalseConfirmationsFailClosed() throws Exception {
        closures();resolutionAndEvidence();assertThrows(SQLException.class,() -> sql(closureInsert("wrong-state","a")));
        assertThrows(SQLException.class,() -> sql(closureInsert("false-confirmation","resolved").replace("true,true,false,false","false,true,false,false")));
        sql("DELETE FROM hidra_incident_closure_policy");try(var c=connection()){c.setAutoCommit(false);pair(c,"closure");assertThrows(SQLException.class,c::commit);c.rollback();}
    }
    @Test void closedAggregateAndEvidenceAreImmutable() throws Exception {
        closures();resolutionAndEvidence();try(var c=connection()){c.setAutoCommit(false);pair(c,"closure");c.commit();}
        assertEquals(1,count("hidra_incident_closure"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_incident SET title='rewrite' WHERE id='resolved'"));
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_incident_resolution"));
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_incident_evidence_link"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_incident_closure_policy SET evidence_required=false"));
        assertThrows(SQLException.class,() -> sql("TRUNCATE hidra_incident_closure CASCADE"));
        assertThrows(SQLException.class,() -> action("late","resolved","action","Response"));
    }
    @Test void rootCauseAndFollowUpFlagsDoNotReplacePersistedEvidence() throws Exception {
        closures();resolutionAndEvidence();sql("UPDATE hidra_incident_closure_policy SET root_cause_required=true");
        try(var c=connection()){c.setAutoCommit(false);pair(c,"closure");assertThrows(SQLException.class,c::commit);c.rollback();}
        sql("UPDATE hidra_incident_closure_policy SET root_cause_required=false,corrective_action_type_id='action'");sql("UPDATE hidra_incident_resolution SET corrective_action_required=true");
        try(var c=connection()){c.setAutoCommit(false);pair(c,"closure");assertThrows(SQLException.class,c::commit);c.rollback();}
    }
    @Test void concurrentCloseAndActionCannotCommitActionAfterClosure() throws Exception {
        closures();resolutionAndEvidence();try(var first=connection();var second=connection()) {
            first.setAutoCommit(false);second.setAutoCommit(false);pair(first,"closure");var pool=Executors.newSingleThreadExecutor();
            try {var future=pool.submit(() -> {try(var statement=second.createStatement()){statement.execute("SET LOCAL statement_timeout='5s'");statement.execute("INSERT INTO hidra_incident_response_action(id,incident_id,action_type_id,action_status,description,created_at,updated_at) VALUES ('late','resolved','action','PLANNED','Response',now(),now())");second.commit();return true;}catch(SQLException e){second.rollback();return false;}});first.commit();assertFalse(future.get(10,TimeUnit.SECONDS));}
            finally {pool.shutdownNow();}
        }
    }
    @Test void concurrentClosuresCannotProduceTwoDecisions() throws Exception {
        closures();resolutionAndEvidence();try(var first=connection();var second=connection()) {
            first.setAutoCommit(false);second.setAutoCommit(false);pair(first,"first");var pool=Executors.newSingleThreadExecutor();
            try {var future=pool.submit(() -> {try(var statement=second.createStatement()){statement.execute("SET LOCAL statement_timeout='5s'");pair(second,"second");second.commit();return true;}catch(SQLException e){second.rollback();return false;}});first.commit();assertFalse(future.get(10,TimeUnit.SECONDS));assertEquals(1,count("hidra_incident_closure"));}
            finally {pool.shutdownNow();}
        }
    }
    @Test void actualJpaClosureAndParentRollbackAndCommitTogether() throws Exception {
        closures();resolutionAndEvidence();var ds=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        var factory=new LocalContainerEntityManagerFactoryBean();factory.setDataSource(ds);factory.setPackagesToScan("dz.sh.hidra.modules.incident.infrastructure.persistence.entity");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());factory.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto","none"));factory.afterPropertiesSet();
        try {
            var emf=Objects.requireNonNull(factory.getObject());var em=SharedEntityManagerCreator.createSharedEntityManager(emf);var repositories=new JpaRepositoryFactory(em);
            var actors=new IncidentActorContract(){public Actor currentActor(Instant at){return new Actor("actor","Canonical Actor");}public Optional<Actor> eligibleActor(String id,Instant at){return Optional.of(new Actor(id,"Canonical Actor"));}};
            var adapter=new JpaIncidentClosureRepositoryAdapter(repositories.getRepository(IncidentClosureJpaRepository.class),repositories.getRepository(IncidentJpaRepository.class),new JpaIncidentClosureEvidenceAdapter(em),actors,mock(IncidentWorkflowContract.class),em);
            var manager=new JpaTransactionManager(emf);var proxy=new ProxyFactory(adapter);proxy.addAdvice(new TransactionInterceptor(manager,new AnnotationTransactionAttributeSource()));var port=(IncidentClosureRepositoryPort)proxy.getProxy();
            var template=new TransactionTemplate(manager);
            var closure=new IncidentClosure("closure","resolved","Verified closure",true,true,false,false,"actor","untrusted",Instant.now(),null);
            assertThrows(IllegalStateException.class,() -> template.execute(status -> {port.save(closure);throw new IllegalStateException("forced rollback");}));
            assertEquals(0,count("hidra_incident_closure"));
            var result=port.save(closure);assertEquals("Canonical Actor",result.closedByActorNameSnapshot());assertEquals(1,count("hidra_incident_closure"));
            try(var c=connection();var st=c.createStatement();var rows=st.executeQuery("SELECT status,closed_at FROM hidra_incident WHERE id='resolved'")){assertTrue(rows.next());assertEquals("CLOSED",rows.getString(1));assertEquals(rows.getTimestamp(2).toInstant(),result.closedAt().truncatedTo(java.time.temporal.ChronoUnit.MICROS));}
        } finally {factory.destroy();}
    }
}
