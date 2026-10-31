/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerCharacteristicRevisionPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence
 *
 * @Description : Checks the synthetic manufacturer characteristic contract and denial cases.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
class ManufacturerCharacteristicRevisionPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16");
    private static JdbcTemplate jdbc;
    private static TransactionTemplate tx;
    private static final String TABLE="public.hidra_simulation_manufacturer_characteristic_revision";
    @BeforeAll static void schema(){
        var ds=new DriverManagerDataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword());
        Flyway.configure().dataSource(ds).load().migrate();
        jdbc=new JdbcTemplate(ds);
        tx=new TransactionTemplate(new DataSourceTransactionManager(ds));
        tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
    }
    private static String id(){return "m-"+UUID.randomUUID();}
    private static String digest(byte[] bytes){
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));}
        catch(java.security.NoSuchAlgorithmException fault){throw new AssertionError(fault);}
    }
    private static String insert(String identity,byte[] bytes,String sha,int pidOffset){
        String company=id(), source=id(),rev=id();
        tx.execute(status->{
            int pid=jdbc.queryForObject("SELECT pg_backend_pid()",Integer.class);
            long xid=jdbc.queryForObject("SELECT txid_current()",Long.class);
            String role=jdbc.queryForObject("SELECT current_user",String.class);
            jdbc.update("INSERT INTO "+TABLE+" (company_party_id,source_id,revision_id,manufacturer_party_id,"
                +"manufacturer_model_reference,payload_format,canonical_payload,payload_sha256,"
                +"workflow_target_digest,document_sha256,source_recorded_at,effective_from,source_origin,"
                +"creator_user_id,operation_binding_id,session_id,backend_pid,transaction_id,database_role,inserted_at)"
                +" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,'SYNTHETIC',?,?,?,?,?,?,?)",
                company,source,rev,id(),"model",
                "HIDRA_MANUFACTURER_CHARACTERISTIC_SOURCE_V1",bytes,sha,identity,"a".repeat(64),
                Timestamp.from(Instant.now().minusSeconds(10)),Timestamp.from(Instant.now().minusSeconds(10)),
                "user","binding","session",pid+pidOffset,xid,role,Timestamp.from(Instant.now()));
            return null;
        });
        return company+"|"+source+"|"+rev;
    }
    @Test void originalTransactionAndDatabaseShaMustMatch(){
        byte[] data="synthetic-documentary-input".getBytes(StandardCharsets.UTF_8);
        assertThrows(RuntimeException.class,()->insert("a".repeat(64),data,"b".repeat(64),0));
        assertThrows(RuntimeException.class,()->insert("b".repeat(64),data,digest(data),1));
        String key=insert("c".repeat(64),data,digest(data),0);
        String[] p=key.split("\\|");
        assertArrayEquals(data,jdbc.queryForObject(
                "SELECT canonical_payload FROM "+TABLE+" WHERE company_party_id=? AND source_id=? AND revision_id=?",
                byte[].class,p[0],p[1],p[2]));
        assertThrows(RuntimeException.class,()->jdbc.update("UPDATE "+TABLE+" SET creator_user_id='forged' WHERE company_party_id=? AND source_id=? AND revision_id=?",
                p[0],p[1],p[2]));
        assertThrows(RuntimeException.class,()->jdbc.update("DELETE FROM "+TABLE+" WHERE company_party_id=? AND source_id=? AND revision_id=?",
                p[0],p[1],p[2]));
    }
    @Test void schemaRejectsMissingSourceAndUnverifiedApprovalSubstitution(){
        assertThrows(RuntimeException.class,()->jdbc.update(
                "INSERT INTO public.hidra_simulation_manufacturer_characteristic_qualification"
                +" (qualification_id,company_party_id,source_id,revision_id,source_sha256,"
                +"workflow_target_digest,workflow_instance_id,workflow_task_id,workflow_action_id,"
                +"approver_id,approver_display_name,approved_at,qualified_at,qualifying_user_id,"
                +"operation_binding_id,backend_pid,transaction_id,database_role)"
                +" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                id(),id(),id(),id(),"a".repeat(64),"b".repeat(64),id(),id(),id(),
                id(),"Synthetic",Timestamp.from(Instant.now().minusSeconds(20)),
                Timestamp.from(Instant.now()),id(),id(),1,1,"forged"));
    }
}
