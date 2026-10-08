/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskGovernancePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence;

import dz.sh.hidra.modules.risk.application.command.*;
import dz.sh.hidra.modules.risk.application.port.out.RiskAssessmentRepositoryPort;
import dz.sh.hidra.modules.risk.infrastructure.persistence.adapter.JpaRiskAssessmentRepositoryAdapter;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.organization.application.contract.risk.RiskOrganizationReferenceContract;
import dz.sh.hidra.modules.topology.application.contract.risk.RiskTopologyScopeReferenceContract;
import dz.sh.hidra.modules.identity.application.contract.risk.RiskActorContract;
import dz.sh.hidra.modules.workflow.application.contract.risk.RiskAssessmentApprovalContract;
import dz.sh.hidra.modules.audit.application.contract.risk.RiskAssessmentAuditContract;
import dz.sh.hidra.modules.audit.infrastructure.integration.RiskAssessmentAuditContractAdapter;
import dz.sh.hidra.modules.audit.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.audit.application.service.*;
import dz.sh.hidra.modules.audit.application.port.out.*;
import jakarta.persistence.EntityManager;
import java.nio.file.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;

@Testcontainers(disabledWithoutDocker=true)
class RiskGovernancePostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    static final Path MIGRATIONS=Path.of("src/main/resources/db/migration");
    static final String EVIDENCE="V20261008_004__hmr_077_risk_evidence_identity_integrity.sql";
    static final String GOVERNANCE="V20261008_005__hmr_058_risk_assessment_governance.sql";
    static final String TAXONOMY="V20261008_006__provision_risk_assessment_audit_taxonomy.sql";
    Connection connection() throws SQLException {return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String text) throws SQLException {try(var c=connection();var s=c.createStatement()){s.execute(text);}}
    void file(String name) throws Exception {
        var text=Files.readString(MIGRATIONS.resolve(name));
        try(var c=connection()) {c.setAutoCommit(false);try(var s=c.createStatement()){s.execute(text);c.commit();}
            catch(SQLException | RuntimeException failure){try{c.rollback();}catch(SQLException e){failure.addSuppressed(e);}throw failure;}}
    }
    int count(String table) throws SQLException {try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT count(*) FROM "+table)){r.next();return r.getInt(1);}}
    void oldSchema() throws Exception {
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        file("V20260611_011__create_risk_tables.sql");file("V20260611_017__create_audit_tables.sql");
        // Preserve existing same-module association integrity in the isolated fixture.
        sql("ALTER TABLE hidra_risk_assessment_scope ADD FOREIGN KEY(risk_assessment_id) REFERENCES hidra_risk_assessment(id); "
                +"ALTER TABLE hidra_risk_evidence_link ADD FOREIGN KEY(risk_assessment_id) REFERENCES hidra_risk_assessment(id)");
    }
    void seed() throws SQLException {
        sql("INSERT INTO hidra_risk_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES "
                +"('type','RISK_ASSESSMENT_TYPE','TYPE',true,0,false,now(),now()),"
                +"('method','RISK_METHODLOGY','METHOD',true,0,false,now(),now()),"
                +"('l','RISK_LIKELIHOOD_LEVEL','L',true,0,false,now(),now()),"
                +"('c','RISK_CONSEQUENCE_LEVEL','C',true,0,false,now(),now()),"
                +"('r','RISK_RATING','R',true,0,false,now(),now()),"
                +"('wrong','OTHER','WRONG',true,0,false,now(),now())");
        sql("INSERT INTO hidra_risk_matrix(id,code,name_fr,matrix_type_id,version,status,created_at,updated_at) "
                +"VALUES ('matrix','M','Matrix','matrix-type','v1','ACTIVE',now(),now())");
        sql("INSERT INTO hidra_risk_matrix_cell(id,risk_matrix_id,likelihood_level_id,consequence_level_id,score_value,rating_id,"
                +"requires_treatment,requires_approval,requires_executive_acceptance,created_at,updated_at) VALUES "
                +"('cell','matrix','l','c',17.25,'r',false,false,false,now(),now())");
    }
    @BeforeEach void setup() throws Exception {oldSchema();file(EVIDENCE);file(GOVERNANCE);file(TAXONOMY);seed();}
    String parent(String id) {
        return "INSERT INTO hidra_risk_assessment(id,risk_register_id,assessment_number,title,assessment_type_id,methodology_id,"
                +"risk_scenario_id,status,assessment_date,assessed_by_actor_id,assessed_by_display_name_snapshot,created_at,updated_at) "
                +"VALUES ('"+id+"','register','"+id+"','Assessment','type','method','scenario','DRAFT',now(),'actor','Actor',now(),now())";
    }
    String scope(String id,String parent) {
        return "INSERT INTO hidra_risk_assessment_scope(id,risk_assessment_id,scope_type,scope_id,included,created_at) VALUES ('"
                +id+"','"+parent+"','PIPELINE','p',true,now())";
    }
    void aggregate(String id) throws SQLException {sql("BEGIN; "+parent(id)+"; "+scope("scope-"+id,id)+"; COMMIT");}
    void evidence(String id) throws SQLException {sql("INSERT INTO hidra_risk_evidence_link(id,risk_assessment_id,evidence_module,evidence_type,evidence_id,created_at) "
            +"VALUES ('e-"+id+"','"+id+"','alarm','Alarm','source',now())");}
    String approved(String id) {return "UPDATE hidra_risk_assessment SET status='APPROVED',reviewed_by_actor_id='reviewer',"
            +"reviewed_by_display_name_snapshot='Reviewer',approved_by_actor_id='actor',approved_by_display_name_snapshot='Actor',"
            +"approved_at=now(),workflow_reference_id='workflow',audit_reference_id='audit' WHERE id='"+id+"'";}
    @Test void scopeSetIsMandatoryAtCommitAndLastScopeDeletionReparentingFail() throws Exception {
        assertThrows(SQLException.class,()->sql(parent("missing")));assertEquals(0,count("hidra_risk_assessment"));
        aggregate("a");aggregate("b");
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_risk_assessment_scope WHERE id='scope-a'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_risk_assessment_scope SET risk_assessment_id='b' WHERE id='scope-a'"));
        assertEquals(2,count("hidra_risk_assessment_scope"));
        assertThrows(SQLException.class,()->sql("TRUNCATE hidra_risk_assessment_scope"));
    }
    @Test void wrongCatalogAndInactiveNewUseFailButCoherentHistoryRemains() throws Exception {
        aggregate("a");
        assertThrows(SQLException.class,()->sql("UPDATE hidra_risk_assessment SET assessment_type_id='wrong' WHERE id='a'"));
        sql("UPDATE hidra_risk_catalog_entry SET active=false WHERE id='type'");
        sql("UPDATE hidra_risk_assessment SET title='Historical draft' WHERE id='a'");
        assertThrows(SQLException.class,()->aggregate("new"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_risk_catalog_entry SET catalog_name='OTHER' WHERE id='method'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_risk_assessment SET confidence_level_id='wrong' WHERE id='a'"));
    }
    void score(String id) throws SQLException {
        sql("BEGIN; INSERT INTO hidra_risk_assessment_scoring(assessment_id,inherent_cell_id,inherent_matrix_id,inherent_matrix_version) "
                +"VALUES ('"+id+"','cell','matrix','v1'); UPDATE hidra_risk_assessment SET inherent_likelihood_id='l',inherent_consequence_id='c',"
                +"inherent_score=17.25,inherent_rating_id='r' WHERE id='"+id+"'; COMMIT");
    }
    @Test void scoreMustMatchExplicitCellAndApprovedProvenanceCannotBeChanged() throws Exception {
        aggregate("a");assertThrows(SQLException.class,()->sql("UPDATE hidra_risk_assessment SET inherent_score=99 WHERE id='a'"));
        score("a");assertThrows(SQLException.class,()->sql("UPDATE hidra_risk_assessment SET inherent_score=99 WHERE id='a'"));
        assertThrows(SQLException.class,()->sql(approved("a")));evidence("a");sql(approved("a"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_risk_assessment SET title='rewrite' WHERE id='a'"));
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_risk_assessment_scope WHERE id='scope-a'"));
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_risk_evidence_link WHERE id='e-a'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_risk_matrix_cell SET score_value=18 WHERE id='cell'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_risk_matrix SET version='v0' WHERE id='matrix'"));
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_risk_assessment_scoring WHERE assessment_id='a'"));
        assertThrows(SQLException.class,()->sql("TRUNCATE hidra_risk_assessment CASCADE"));
        assertThrows(SQLException.class,()->sql("TRUNCATE hidra_risk_evidence_link"));
    }
    @Test void legacyMissingScopeAndIncompleteEvidenceAbortWithoutRepair() throws Exception {
        oldSchema();seed();sql(parent("legacy"));assertThrows(SQLException.class,()->file(GOVERNANCE));
        assertEquals(1,count("hidra_risk_assessment"));assertEquals(0,count("hidra_risk_assessment_scope"));
        sql("INSERT INTO hidra_risk_evidence_link(id,risk_assessment_id,evidence_module,evidence_type,evidence_id,created_at) "
                +"VALUES ('invalid','legacy',' ','Alarm','source',now())");
        assertThrows(SQLException.class,()->file(EVIDENCE));assertEquals(1,count("hidra_risk_evidence_link"));
    }
    @Test void inactiveAuditTaxonomyIsNeverSilentlyReactivated() throws Exception {
        sql("UPDATE hidra_audit_catalog_entry SET active=false WHERE code='RISK_ASSESSMENT_APPROVED'");
        assertThrows(SQLException.class,()->file(TAXONOMY));
    }
    @Test void concurrentScopeDeletionsCannotCommitAnEmptyAggregate() throws Exception {
        aggregate("a");sql(scope("extra","a"));var start=new CountDownLatch(1);var pool=Executors.newFixedThreadPool(2);
        try {
            var first=pool.submit(()->attempt(start,"DELETE FROM hidra_risk_assessment_scope WHERE id='scope-a'"));
            var second=pool.submit(()->attempt(start,"DELETE FROM hidra_risk_assessment_scope WHERE id='extra'"));
            start.countDown();assertEquals(1,(first.get(15,TimeUnit.SECONDS)?1:0)+(second.get(15,TimeUnit.SECONDS)?1:0));
            assertEquals(1,count("hidra_risk_assessment_scope"));
        } finally {pool.shutdownNow();}
    }
    boolean attempt(CountDownLatch start,String command) throws Exception {
        start.await();try(var c=connection();var s=c.createStatement()) {c.setAutoCommit(false);s.execute("SET LOCAL lock_timeout='5s'");
            try{s.execute(command);c.commit();return true;}catch(SQLException denied){c.rollback();return false;}}
    }
    @Test void approvalLockExcludesConcurrentEvidenceMutation() throws Exception {
        aggregate("a");evidence("a");var pool=Executors.newSingleThreadExecutor();
        try(var first=connection();var statement=first.createStatement()) {
            first.setAutoCommit(false);statement.execute("SELECT id FROM hidra_risk_assessment WHERE id='a' FOR UPDATE");
            var entered=new CountDownLatch(1);
            var mutation=pool.submit(()->{entered.countDown();return attempt(new CountDownLatch(0),"DELETE FROM hidra_risk_evidence_link WHERE id='e-a'");});
            assertTrue(entered.await(5,TimeUnit.SECONDS));statement.execute(approved("a"));first.commit();
            assertFalse(mutation.get(15,TimeUnit.SECONDS));assertEquals(1,count("hidra_risk_evidence_link"));
        } finally {pool.shutdownNow();}
    }
    @Test void residualContextMustBelongToAssessmentAndCannotBeDeletedAfterSelection() throws Exception {
        aggregate("a");aggregate("b");score("a");
        sql("INSERT INTO hidra_risk_control(id,risk_assessment_id,control_code,control_name,control_type_id,verified,created_at,updated_at) "
                +"VALUES ('control','b','C','Control','control-type',false,now(),now())");
        String residual="BEGIN; UPDATE hidra_risk_assessment_scoring SET residual_cell_id='cell',residual_matrix_id='matrix',"
                +"residual_matrix_version='v1',residual_context_type='CONTROL',residual_context_id='control' WHERE assessment_id='a'; "
                +"UPDATE hidra_risk_assessment SET residual_likelihood_id='l',residual_consequence_id='c',residual_score=17.25,residual_rating_id='r' WHERE id='a'; COMMIT";
        assertThrows(SQLException.class,()->sql(residual));
        sql("UPDATE hidra_risk_control SET risk_assessment_id='a' WHERE id='control'");sql(residual);
        assertThrows(SQLException.class,()->sql("TRUNCATE hidra_risk_control"));
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_risk_control WHERE id='control'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_risk_control SET risk_assessment_id='b' WHERE id='control'"));
    }
    @Test void approvalProtectsSelectedCellAgainstWaitingMutation() throws Exception {
        aggregate("a");score("a");evidence("a");var pool=Executors.newSingleThreadExecutor();
        try(var first=connection();var statement=first.createStatement()) {
            first.setAutoCommit(false);statement.execute("SELECT id FROM hidra_risk_matrix_cell WHERE id='cell' FOR SHARE");
            var entered=new CountDownLatch(1);
            var mutation=pool.submit(()->{entered.countDown();return attempt(new CountDownLatch(0),"UPDATE hidra_risk_matrix_cell SET score_value=99 WHERE id='cell'");});
            assertTrue(entered.await(5,TimeUnit.SECONDS));statement.execute(approved("a"));first.commit();
            assertFalse(mutation.get(15,TimeUnit.SECONDS));
        } finally {pool.shutdownNow();}
    }
    @Test void realJpaAssessmentAndAuditRollbackTogetherIncludingWorkflowOwnerReceiptFixture() throws Exception {
        aggregate("a");evidence("a");sql("UPDATE hidra_risk_assessment SET status='UNDER_REVIEW' WHERE id='a'");
        sql("CREATE TABLE test_workflow_receipt(id varchar(80) PRIMARY KEY)");
        var ds=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        var factory=new LocalContainerEntityManagerFactoryBean();factory.setDataSource(ds);
        factory.setPackagesToScan("dz.sh.hidra.modules.risk.infrastructure.persistence.entity","dz.sh.hidra.modules.audit.infrastructure.persistence.entity");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());factory.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto","none"));factory.afterPropertiesSet();
        try {
            var emf=Objects.requireNonNull(factory.getObject());EntityManager em=SharedEntityManagerCreator.createSharedEntityManager(emf);
            var repos=new JpaRepositoryFactory(em);var auditCatalogs=repos.getRepository(AuditCatalogEntryJpaRepository.class);var policy=new AuditInputPolicy();
            var events=new JpaAuditEventRepositoryAdapter(repos.getRepository(AuditEventJpaRepository.class),em,new JpaAuditCatalogEligibilityAdapter(auditCatalogs),policy);
            var recorder=new AuditApplicationService(events,mock(AuditExportRequestRepositoryPort.class),mock(AuditAccessRecordRepositoryPort.class),policy);
            var auditOwner=new RiskAssessmentAuditContractAdapter(auditCatalogs,recorder,policy);var manager=new JpaTransactionManager(emf);
            RiskActorContract actors=at -> new RiskActorContract.Actor("actor","username","Actor");
            RiskTopologyScopeReferenceContract topology=(type,id) -> Optional.of(new RiskTopologyScopeReferenceContract.ScopeView(id,"P-1","Pipeline"));
            // Controlled Workflow-owner fixture isolates transaction enlistment. Configured execution is covered by Workflow owner tests.
            RiskAssessmentApprovalContract workflow=request -> {
                em.createNativeQuery("INSERT INTO test_workflow_receipt VALUES ('action')").executeUpdate();
                return new RiskAssessmentApprovalContract.Approval("w","t","action","review","reviewer","Reviewer","actor","username","Actor",Instant.now());
            };
            java.util.function.Function<RiskAssessmentAuditContract,RiskAssessmentRepositoryPort> port=owner -> {
                var target=new JpaRiskAssessmentRepositoryAdapter(repos.getRepository(RiskAssessmentJpaRepository.class),repos.getRepository(RiskAssessmentScopeJpaRepository.class),
                        repos.getRepository(RiskAssessmentScoringJpaRepository.class),repos.getRepository(RiskCatalogEntryJpaRepository.class),repos.getRepository(RiskMatrixCellJpaRepository.class),
                        repos.getRepository(RiskMatrixJpaRepository.class),repos.getRepository(RiskControlJpaRepository.class),repos.getRepository(RiskTreatmentPlanJpaRepository.class),
                        repos.getRepository(RiskEvidenceLinkJpaRepository.class),link -> link,mock(RiskOrganizationReferenceContract.class),topology,actors,workflow,owner,em);
                var interceptor=new TransactionInterceptor();interceptor.setTransactionManager(manager);interceptor.setTransactionAttributeSource(new AnnotationTransactionAttributeSource());interceptor.afterPropertiesSet();
                var proxy=new ProxyFactory(target);proxy.addAdvice(interceptor);return (RiskAssessmentRepositoryPort)proxy.getProxy();
            };
            var command=new ApproveRiskAssessmentCommand("a","w","t","transition","review",Instant.now(),null,null,null,null);
            var failing=port.apply(e -> {auditOwner.appendApproved(e);throw new IllegalStateException("After Audit flush");});
            assertThrows(RuntimeException.class,()->failing.approve(command));assertEquals(0,count("hidra_audit_event"));assertEquals(0,count("test_workflow_receipt"));
            try(var c=connection();var s=c.createStatement();var rows=s.executeQuery("SELECT status FROM hidra_risk_assessment WHERE id='a'")){rows.next();assertEquals("UNDER_REVIEW",rows.getString(1));}
            var saved=port.apply(auditOwner).approve(command);assertNotNull(saved.auditReferenceId());assertNotNull(saved.approvedAt());
            assertEquals(1,count("hidra_audit_event"));assertEquals(1,count("test_workflow_receipt"));
            assertThrows(RuntimeException.class,()->port.apply(auditOwner).approve(command));
        } finally {factory.destroy();}
    }
}
