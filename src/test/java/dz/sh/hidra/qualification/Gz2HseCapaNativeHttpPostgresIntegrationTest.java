/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : Gz2HseCapaNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Test
 * @Module      : hse
 * @Package     : dz.sh.hidra.qualification
 * @Description : Signed GZ2 fresh-schema negative access and exact T17 route discovery across runtime restart.
 */
package dz.sh.hidra.qualification;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.junit.jupiter.api.Assertions.*;

class Gz2HseCapaNativeHttpPostgresIntegrationTest {
    private static String route(Gz2SignedWorkflowFixture f){
        return "/api/v1/hse/companies/"+f.company+"/facilities/"+f.asset+"/cases";
    }
    private static void denied(int status){
        assertTrue(status==403||status==404,"HSE protected resource must not be disclosed: "+status);
    }
    @Test void signedCompanyAndFacilityReadsConcealNonexistentCasesAndRejectWrongScope() throws Exception {
        try(var postgres=new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("hidra_gz2_t17").withCommand("postgres","-c","autovacuum=off")){
            postgres.start();
            var f=new Gz2SignedWorkflowFixture(postgres);
            // Direct current permits at both registered owner scopes; no role name inference.
            f.permit(f.author,"hse:capa:read","HSE_CAPA","CUSTOM","documents-company:"+f.company);
            f.permit(f.author,"hse:capa:read","HSE_CAPA","FACILITY",f.asset);
            f.permit(f.foreign,"hse:capa:read","HSE_CAPA","CUSTOM","documents-company:"+f.foreignCompany);
            f.permit(f.foreign,"hse:capa:read","HSE_CAPA","FACILITY",f.asset);
            String missing=Gz2SignedWorkflowFixture.id();
            try(var runtime=f.start()){
                var author=runtime.login(f.author),foreign=runtime.login(f.foreign);
                denied(runtime.send("GET",route(f)+"/"+missing,author,null).statusCode());
                denied(runtime.send("GET",route(f)+"/"+missing,foreign,null).statusCode());
                denied(runtime.send("GET",route(f),foreign,null).statusCode());
                denied(runtime.send("GET",route(f)+"/"+missing+"/history",foreign,null).statusCode());
                int code=runtime.send("GET",route(f)+"/"+missing,null,null).statusCode();
                assertTrue(code==401||code==403);
            }
            // Restart must not create shadow records for denied/nonexistent resources.
            try(var restarted=f.start()){
                var author=restarted.login(f.author);
                denied(restarted.send("GET",route(f)+"/"+missing,author,null).statusCode());
                assertEquals(0L,f.sql.queryForObject("SELECT count(*) FROM hidra_hse_t17_case_scope",
                        Long.class));
            }
        }
    }
}
