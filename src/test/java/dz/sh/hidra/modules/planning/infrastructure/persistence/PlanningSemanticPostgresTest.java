/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningSemanticPostgresTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence
 *
 * @Description : Enforces Planning-owned semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import java.nio.file.*;
import java.sql.*;
import java.util.concurrent.*;

@Testcontainers(disabledWithoutDocker=true)
class PlanningSemanticPostgresTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    static final Path MIGRATIONS=Path.of("src/main/resources/db/migration");
    Connection connection() throws SQLException {return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String value) throws SQLException {try(var c=connection();var s=c.createStatement()){s.execute(value);}}
    void emptySchema() throws Exception {
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        sql(Files.readString(MIGRATIONS.resolve("V20260611_006__create_planning_tables.sql")));
        sql("ALTER TABLE hidra_planning_plan_revision ADD CONSTRAINT parent_plan FOREIGN KEY(plan_id) REFERENCES hidra_planning_operational_plan(id)");
    }
    @BeforeEach void setup() throws Exception {
        emptySchema();
        sql(Files.readString(MIGRATIONS.resolve("V20261007_001__hmr_064_planning_plan_revision.sql")));
        sql(Files.readString(MIGRATIONS.resolve("V20261007_002__hmr_065_planning_operational_plan.sql")));
        catalog("reason","REVISION_REASON",true);catalog("type","PLAN_TYPE",true);catalog("wrong","PERIOD_TYPE",true);
        sql(plan("plan","P1"));sql(plan("other","P2"));
    }
    void catalog(String id,String family,boolean active) throws SQLException {
        sql("INSERT INTO hidra_planning_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES ('"+id+"','"+family+"','"+id+"',"+active+",0,false,now(),now())");
    }
    String plan(String id,String code) {return "INSERT INTO hidra_planning_operational_plan(id,period_id,code,name_fr,plan_type_id,topology_scope_type,topology_scope_id,topology_scope_code,status,created_by_actor_id,created_at,updated_at) VALUES ('"+id+"','period','"+code+"','Plan','type','PIPELINE','pipe','P','DRAFT','actor',now(),now())";}
    String revision(String id,String plan,int number,String status) {return "INSERT INTO hidra_planning_plan_revision(id,plan_id,revision_number,revision_code,status,created_at,updated_at) VALUES ('"+id+"','"+plan+"',"+number+",'R','"+status+"',now(),now())";}
    @Test void positiveUniqueNumbersAndNullableBaseLineage() throws Exception {
        assertThrows(SQLException.class,()->sql(revision("zero","plan",0,"DRAFT")));
        sql(revision("rev","plan",1,"DRAFT"));
        assertThrows(SQLException.class,()->sql(revision("dup","plan",1,"DRAFT")));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_planning_plan_revision SET base_revision_id='missing' WHERE id='rev'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_planning_plan_revision SET base_revision_id='rev' WHERE id='rev'"));
        sql(revision("next","plan",2,"DRAFT"));sql("UPDATE hidra_planning_plan_revision SET base_revision_id='rev' WHERE id='next'");
    }
    @Test void approvedEvidenceCannotBeChangedDeletedOrTruncated() throws Exception {
        sql(revision("approved","plan",1,"APPROVED"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_planning_plan_revision SET change_reason_text='edit' WHERE id='approved'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_planning_plan_revision SET status='DRAFT' WHERE id='approved'"));
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_planning_plan_revision WHERE id='approved'"));
        assertThrows(SQLException.class,()->sql("TRUNCATE hidra_planning_plan_revision CASCADE"));
        sql(revision("replacement","plan",2,"DRAFT"));
    }
    @Test void exactActiveReasonFamilyRequiredOnWrites() throws Exception {
        sql(revision("rev","plan",1,"DRAFT"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_planning_plan_revision SET change_reason_code_id='wrong' WHERE id='rev'"));
        sql("UPDATE hidra_planning_catalog_entry SET active=false WHERE id='reason'");
        assertThrows(SQLException.class,()->sql("UPDATE hidra_planning_plan_revision SET change_reason_code_id='reason' WHERE id='rev'"));
        sql("UPDATE hidra_planning_catalog_entry SET active=true WHERE id='reason'");
        sql("UPDATE hidra_planning_plan_revision SET change_reason_code_id='reason' WHERE id='rev'");
    }
    @Test void competingRevisionNumbersHaveExactlyOneWinner() throws Exception {
        var pool=Executors.newFixedThreadPool(2);var go=new CountDownLatch(1);
        try {
            var a=pool.submit(()->insertAfter(go,revision("a","plan",1,"DRAFT")));
            var b=pool.submit(()->insertAfter(go,revision("b","plan",1,"DRAFT")));
            go.countDown();assertEquals(1,a.get(20,TimeUnit.SECONDS)+b.get(20,TimeUnit.SECONDS));
        } finally {pool.shutdownNow();}
    }
    int insertAfter(CountDownLatch go,String query) throws Exception {
        go.await(10,TimeUnit.SECONDS);try{sql(query);return 1;}catch(SQLException e){assertEquals("23505",e.getSQLState());return 0;}
    }
    @Test void legacyInvalidNumbersAbortWithoutRenumbering() throws Exception {
        emptySchema();sql(plan("plan","P"));sql(revision("bad","plan",0,"DRAFT"));
        assertThrows(SQLException.class,()->sql(Files.readString(MIGRATIONS.resolve("V20261007_001__hmr_064_planning_plan_revision.sql"))));
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT revision_number FROM hidra_planning_plan_revision WHERE id='bad'")){assertTrue(r.next());assertEquals(0,r.getInt(1));}
    }

    @Test void planPointersMustExistAndBelongToSamePlan() throws Exception {
        sql(revision("rev","plan",1,"DRAFT"));sql(revision("other-rev","other",1,"APPROVED"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_planning_operational_plan SET current_revision_id='missing' WHERE id='plan'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_planning_operational_plan SET current_revision_id='other-rev' WHERE id='plan'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_planning_operational_plan SET approved_revision_id='other-rev' WHERE id='plan'"));
        sql("UPDATE hidra_planning_operational_plan SET current_revision_id='rev',approved_revision_id='rev' WHERE id='plan'");
        assertThrows(SQLException.class,()->sql("UPDATE hidra_planning_plan_revision SET plan_id='other',revision_number=2 WHERE id='rev'"));
        sql("UPDATE hidra_planning_operational_plan SET current_revision_id=null,approved_revision_id=null WHERE id='plan'");
    }
    @Test void planNameScopeTypeAndActiveTypeFamilyEnforced() throws Exception {
        assertThrows(SQLException.class,()->sql("UPDATE hidra_planning_operational_plan SET name_fr=' ' WHERE id='plan'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_planning_operational_plan SET topology_scope_type=' ' WHERE id='plan'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_planning_operational_plan SET plan_type_id='wrong' WHERE id='plan'"));
        sql("UPDATE hidra_planning_catalog_entry SET active=false WHERE id='type'");
        assertThrows(SQLException.class,()->sql(plan("new","P3")));
        sql("UPDATE hidra_planning_catalog_entry SET active=true WHERE id='type'");sql(plan("new","P3"));
    }
    @Test void competingPlanCodesHaveExactlyOneWinner() throws Exception {
        var pool=Executors.newFixedThreadPool(2);var go=new CountDownLatch(1);
        try {
            var a=pool.submit(()->insertAfter(go,plan("a","SHARED")));
            var b=pool.submit(()->insertAfter(go,plan("b","SHARED")));
            go.countDown();assertEquals(1,a.get(20,TimeUnit.SECONDS)+b.get(20,TimeUnit.SECONDS));
        } finally {pool.shutdownNow();}
    }
    @Test void legacyCrossPlanPointerAbortsWithoutReassignment() throws Exception {
        emptySchema();catalog("type","PLAN_TYPE",true);sql(plan("plan","P1"));sql(plan("other","P2"));
        sql(revision("foreign-rev","other",1,"DRAFT"));
        sql("UPDATE hidra_planning_operational_plan SET current_revision_id='foreign-rev' WHERE id='plan'");
        sql(Files.readString(MIGRATIONS.resolve("V20261007_001__hmr_064_planning_plan_revision.sql")));
        assertThrows(SQLException.class,()->sql(Files.readString(MIGRATIONS.resolve("V20261007_002__hmr_065_planning_operational_plan.sql"))));
        try(var c=connection();var s2=c.createStatement();var r=s2.executeQuery("SELECT current_revision_id FROM hidra_planning_operational_plan WHERE id='plan'")){assertTrue(r.next());assertEquals("foreign-rev",r.getString(1));}
    }
}
