/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationManufacturerEvidencePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence
 *
 * @Description : Checks the synthetic manufacturer characteristic contract and denial cases.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
class SimulationManufacturerEvidencePostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16");
    private static JdbcTemplate jdbc;
    private static final String TABLE="public.hidra_party_manufacturer_source_authorization";
    @BeforeAll static void migrate(){
        var ds=new DriverManagerDataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword());
        Flyway.configure().dataSource(ds).load().migrate();
        jdbc=new JdbcTemplate(ds);
    }
    private static String id(){return "m-"+UUID.randomUUID();}
    @Test void missingCompanyAndManufacturerNeverAutoGrant(){
        String absentCompany=id();
        assertEquals(0L,jdbc.queryForObject("SELECT count(*) FROM "+TABLE+
                " WHERE company_party_id=?",Long.class,absentCompany));
        assertThrows(RuntimeException.class,()->jdbc.update(
                "INSERT INTO "+TABLE+" (authorization_id,company_party_id,manufacturer_party_id,"
                +"manufacturer_model_reference,authorization_status,valid_from,generation,created_at,updated_at)"
                +" VALUES (?,?,?,'synthetic-model','ACTIVE',now(),1,now(),now())",id(),id(),id()));
    }
    @Test void revocationDoesNotRewriteScopeOrReopenExpiredGrant(){
        String type=id(),company=id(),manufacturer=id(),grant=id();
        jdbc.update("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at)"
                +" VALUES (?,?,'ACTIVE',now(),now())",type,type);
        for(String party:new String[]{company,manufacturer})
            jdbc.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at)"
                    +" VALUES (?,?,?,'Synthetic Party','DZ','ACTIVE',now(),now())",party,party,type);
        jdbc.update("INSERT INTO "+TABLE+" (authorization_id,company_party_id,manufacturer_party_id,"
                +"manufacturer_model_reference,authorization_status,valid_from,generation,created_at,updated_at)"
                +" VALUES (?,?,?,'synthetic-model','ACTIVE',?,1,now(),now())",
                grant,company,manufacturer,Timestamp.from(Instant.now().minusSeconds(60)));
        assertEquals("ACTIVE",jdbc.queryForObject("SELECT authorization_status FROM "+TABLE
                +" WHERE authorization_id=?",String.class,grant));
        assertThrows(RuntimeException.class,()->jdbc.update("UPDATE "+TABLE
                +" SET company_party_id=?,generation=2,updated_at=now() WHERE authorization_id=?",id(),grant));
        jdbc.update("UPDATE "+TABLE+" SET authorization_status='REVOKED',"
                +" revoked_at=now(),generation=2,updated_at=now() WHERE authorization_id=?",grant);
        assertThrows(RuntimeException.class,()->jdbc.update("UPDATE "+TABLE
                +" SET authorization_status='ACTIVE',revoked_at=NULL,generation=3,updated_at=now() WHERE authorization_id=?",grant));
        assertThrows(RuntimeException.class,()->jdbc.update("DELETE FROM "+TABLE+" WHERE authorization_id=?",grant));
    }
}
