/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaLifecyclePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Test
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence
 * @Description : Real PostgreSQL 16 Flyway migration and physical mutation denial gates.
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence;

import java.util.List;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.junit.jupiter.api.Assertions.*;

/** Fresh-schema synthetic-only test: no historical production-data assumptions. */
class HseCapaLifecyclePostgresIntegrationTest {
    @Test void forwardOnlyFlywayCreatesSixHseTablesAndNoPermits() throws Exception {
        try(var postgres=new PostgreSQLContainer<>("postgres:16-alpine")){
            postgres.start();
            Flyway flyway=Flyway.configure().dataSource(postgres.getJdbcUrl(),
                    postgres.getUsername(),postgres.getPassword()).load();
            flyway.migrate();
            flyway.validate();
            var db=new JdbcTemplate(new DriverManagerDataSource(
                    postgres.getJdbcUrl(),postgres.getUsername(),postgres.getPassword()));
            var names=List.of("hidra_hse_t17_case_scope","hidra_hse_t17_capa_state",
                    "hidra_hse_t17_capa_event","hidra_hse_t17_capa_evidence",
                    "hidra_hse_t17_capa_verification","hidra_hse_t17_case_close_receipt");
            for(var name:names){
                assertEquals(1L,db.queryForObject("SELECT count(*) FROM pg_tables WHERE schemaname='public' AND tablename=?",
                        Long.class,name));
                assertEquals(0L,db.queryForObject("SELECT count(*) FROM "+name,Long.class));
            }
            assertEquals(1L,db.queryForObject("SELECT count(*) FROM flyway_schema_history WHERE script=? AND success",
                    Long.class,"V20261118_002__t17_hse_capa_governed_lifecycle.sql"));
            assertEquals(1L, db.queryForObject("SELECT count(*) FROM flyway_schema_history WHERE script=? AND success",
                    Long.class, "V20261118_003__t17_hse_impact_assessment.sql"));
            assertEquals(1L, db.queryForObject("SELECT count(*) FROM pg_constraint WHERE conname=?",
                    Long.class, "fk_t17_close_scoped_assessment"));
            assertEquals(6L,db.queryForObject("SELECT count(*) FROM hidra_identity_permission "+
                    "WHERE code LIKE 'hse:capa:%'",Long.class));
            assertEquals(0L,db.queryForObject("SELECT count(*) FROM hidra_identity_user_permission_grant g "+
                    "JOIN hidra_identity_permission p ON p.id=g.permission_id WHERE p.code LIKE 'hse:capa:%'",
                    Long.class));
            assertThrows(org.springframework.dao.DataAccessException.class,()->
                    db.update("INSERT INTO hidra_hse_t17_capa_evidence(id,capa_id,document_id,version_id,"+
                    "acceptance_id,checksum_sha256,content_length,company_id,facility_id,actor_id,attached_at)"+
                    "VALUES('invalid','missing','document','version','decision',?,1,'company','facility','actor',now())",
                            "a".repeat(64)));
        }
    }
}
