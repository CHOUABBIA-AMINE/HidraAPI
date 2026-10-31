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

    /** Genuine canonical-source fixture; the supplier metadata column may be deliberately forged. */
    private static void appendCanonical(String company,
            dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision source,
            String manufacturerColumn) {
        var codec=new dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter.ManufacturerCharacteristicRevisionCodec();
        byte[] payload=codec.encode(source);
        int pid=jdbc.queryForObject("SELECT pg_backend_pid()",Integer.class);
        long xid=jdbc.queryForObject("SELECT txid_current()",Long.class);
        String role=jdbc.queryForObject("SELECT current_user",String.class);
        String target=digest(("target-"+UUID.randomUUID()).getBytes(StandardCharsets.UTF_8));
        jdbc.update("INSERT INTO "+TABLE+" (company_party_id,source_id,revision_id,manufacturer_party_id,"
            +"manufacturer_model_reference,payload_format,canonical_payload,payload_sha256,"
            +"workflow_target_digest,document_sha256,source_recorded_at,effective_from,source_origin,"
            +"creator_user_id,operation_binding_id,session_id,backend_pid,transaction_id,database_role,inserted_at)"
            +" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,'SYNTHETIC',?,?,?,?,?,?,?)",
            company,source.sourceId(),source.revisionId(),manufacturerColumn,source.manufacturerModelReference(),
            "HIDRA_MANUFACTURER_CHARACTERISTIC_SOURCE_V1",payload,codec.sha256(payload),
            target,source.documentSha256(),Timestamp.from(source.recordedAt()),
            Timestamp.from(source.effectiveFrom()),"synthetic-user","binding","session",pid,xid,role,
            Timestamp.from(Instant.now()));
    }
    private static dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision
            withModel(String revision,String model) {
        var source=dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevisionTest.fixture(
            revision,dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision.Origin.SYNTHETIC,
            dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevisionTest.limits());
        return new dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision(
            source.sourceId(),source.revisionId(),source.recordedAt(),source.effectiveFrom(),
            source.effectiveUntil(),source.origin(),source.manufacturerPartyId(),model,
            source.documentReference(),source.documentSha256(),source.referenceConditions(),
            null,source.family());
    }
    @Test void scansBeyondLegacy256RevisionsWithOnlyBoundedPageAndCompleteSubjectProof(){
        String company=id();
        tx.execute(status->{
            for(int i=0;i<265;i++)appendCanonical(company,withModel("v"+String.format("%04d",i),"model-x"),"party-1");
            return null;
        });
        var adapter=new dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter.JdbcManufacturerCharacteristicRevisionRepositoryAdapter(jdbc);
        var scan=tx.execute(status->{
            var admission=adapter.acquire(null);
            var result=adapter.scanHistory(admission,company,"m-source",20,null,64);
            adapter.prepareFinal(admission,adapter.finalFences(admission),
                    Instant.now().minusSeconds(10),Instant.now().plusSeconds(60));
            assertTrue(adapter.finalGuard(admission));
            return result;
        });
        assertNotNull(scan);
        assertEquals(1,scan.principals().size());
        assertEquals(20,scan.summaries().size());
        assertTrue(scan.hasNext());
        assertEquals("v0000",scan.summaries().getFirst().revisionId());
        var after=new dz.sh.hidra.modules.simulation.application.port.out
                .ManufacturerCharacteristicHistoryCursorPort.Position(
                    scan.summaries().getLast().recordedAt(),scan.summaries().getLast().revisionId());
        var next=tx.execute(status->{
            var admission=adapter.acquire(null);
            var result=adapter.scanHistory(admission,company,"m-source",20,after,64);
            adapter.prepareFinal(admission,adapter.finalFences(admission),
                    Instant.now().minusSeconds(10),Instant.now().plusSeconds(60));
            assertTrue(adapter.finalGuard(admission));
            return result;
        });
        assertNotNull(next);
        assertEquals("v0020",next.summaries().getFirst().revisionId());
    }
    @Test void canonicalSourceManufacturerMismatchFailsBeforeAnyHistoryPage(){
        String company=id();
        tx.execute(status->{appendCanonical(company,withModel("v1","actual-model"),"forged-party");return null;});
        var adapter=new dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter.JdbcManufacturerCharacteristicRevisionRepositoryAdapter(jdbc);
        assertThrows(RuntimeException.class,()->tx.execute(status->{
            var admission=adapter.acquire(null);
            return adapter.scanHistory(admission,company,"m-source",5,null,64);
        }));
    }
    @Test void sixtyFifthDistinctCanonicalManufacturerModelFailsClosed(){
        String company=id();
        tx.execute(status->{
            for(int i=0;i<65;i++)appendCanonical(company,withModel("v"+i,"model-"+i),"party-1");
            return null;
        });
        var adapter=new dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter.JdbcManufacturerCharacteristicRevisionRepositoryAdapter(jdbc);
        assertThrows(RuntimeException.class,()->tx.execute(status->{
            var admission=adapter.acquire(null);
            return adapter.scanHistory(admission,company,"m-source",5,null,64);
        }));
    }
}
