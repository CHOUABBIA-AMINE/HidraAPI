/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAuthoringPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Verifies synthetic fresh-schema Pipeline association mechanics; configured authority is qualified separately.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import java.util.*;
import org.junit.jupiter.api.*;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
class PipelineAuthoringPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16");
    static JdbcTemplate jdbc;
    @BeforeAll static void migrate(){jdbc=database(PG);}
    static JdbcTemplate database(PostgreSQLContainer<?> pg){var ds=new DriverManagerDataSource(pg.getJdbcUrl(),pg.getUsername(),pg.getPassword());Flyway.configure().dataSource(ds).load().migrate();return new JdbcTemplate(ds);}
    static String id(){return UUID.randomUUID().toString();}
    record Fixture(String company,String system,String pipeline,String type) {}
    static Fixture seed(JdbcTemplate j){String partyType=id(),company=id(),systemType=id(),system=id(),type=id(),pipeline=id();
        j.update("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at) VALUES (?,?,'ACTIVE',now(),now())",partyType,partyType);
        j.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES (?,?,?,'Synthetic','DZ','ACTIVE',now(),now())",company,company,partyType);
        j.update("INSERT INTO public.hidra_topology_pipeline_system_type(id,code,active,created_at,updated_at) VALUES (?,?,true,now(),now())",systemType,systemType);
        j.update("INSERT INTO public.hidra_topology_pipeline_system(id,code,system_type_id,status,created_at,updated_at) VALUES (?,?,?,'DRAFT',now(),now())",system,system,systemType);
        j.update("INSERT INTO public.hidra_topology_pipeline_type(id,code,active,created_at,updated_at) VALUES (?,?,true,now(),now())",type,type);
        j.update("INSERT INTO public.hidra_topology_pipeline(id,pipeline_system_id,code,pipeline_type_id,name_en,status,created_at,updated_at) VALUES (?,?,?,?,'Original','DRAFT',now(),now())",pipeline,system,pipeline,type);
        return new Fixture(company,system,pipeline,type);
    }
    static void revision(JdbcTemplate j,Fixture f,long revision,String label){j.update("INSERT INTO public.hidra_topology_pipeline_authoring_revision(pipeline_id,revision,company_party_id,pipeline_system_id,code,pipeline_type_id,name_en,actor_user_id,recorded_at) VALUES (?,?,?,?,?,?,?,'synthetic-schema-only',now())",f.pipeline(),revision,f.company(),f.system(),f.pipeline(),f.type(),label);}
    @Test void actualMigrationRejectsRevisionGapsOwnershipChangesAndMutableHistory(){var f=seed(jdbc);revision(jdbc,f,1,"Original");
        assertThrows(org.springframework.dao.DataAccessException.class,()->revision(jdbc,f,3,"Original"));
        var other=seed(jdbc);assertThrows(org.springframework.dao.DataAccessException.class,()->revision(jdbc,new Fixture(other.company(),f.system(),f.pipeline(),f.type()),2,"Original"));
        assertThrows(org.springframework.dao.DataAccessException.class,()->jdbc.update("DELETE FROM public.hidra_topology_pipeline_authoring_revision WHERE pipeline_id=?",f.pipeline()));
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_pipeline_authoring_revision WHERE pipeline_id=?",Integer.class,f.pipeline()));
    }
    @Test void canonicalAndRevisionChangesRollbackTogether(){var f=seed(jdbc);revision(jdbc,f,1,"Original");var tx=new TransactionTemplate(new DataSourceTransactionManager(jdbc.getDataSource()));
        assertThrows(IllegalStateException.class,()->tx.executeWithoutResult(s->{jdbc.update("UPDATE public.hidra_topology_pipeline SET name_en='Revised' WHERE id=?",f.pipeline());revision(jdbc,f,2,"Revised");throw new IllegalStateException("Synthetic downstream failure");}));
        assertEquals("Original",jdbc.queryForObject("SELECT name_en FROM public.hidra_topology_pipeline WHERE id=?",String.class,f.pipeline()));assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM public.hidra_topology_pipeline_authoring_revision WHERE pipeline_id=?",Integer.class,f.pipeline()));
    }
    @Test void associationForeignKeysAndNoGraphPayloadDuplicationArePhysicalContracts(){
        assertEquals(3,jdbc.queryForObject("SELECT count(*) FROM pg_constraint WHERE conrelid='public.hidra_topology_pipeline_graph_binding'::regclass AND contype='f' AND convalidated",Integer.class));
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM information_schema.columns WHERE table_schema='public' AND table_name IN ('hidra_topology_pipeline_authoring_revision','hidra_topology_pipeline_graph_snapshot','hidra_topology_pipeline_graph_binding') AND column_name='canonical_payload'",Integer.class));
    }
}
