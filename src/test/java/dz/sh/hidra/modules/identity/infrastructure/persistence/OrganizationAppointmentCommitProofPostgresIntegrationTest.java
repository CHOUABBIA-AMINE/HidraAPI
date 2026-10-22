/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationAppointmentCommitProofPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-22
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Authenticates transaction-bound appointment commit evidence within Identity.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

/** Real database/ACL/codec tests. Manual authority fields here do not prove native admission;
 * the existing native integration class separately exercises the actual issuer.
 * The synthetic guard/receipt below is not the future Organization lifecycle guard.
 */
@Testcontainers
class OrganizationAppointmentCommitProofPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16")
            .withCommand("postgres","-c","log_min_error_statement=panic","-c","log_error_verbosity=terse",
                    "-c","log_parameter_max_length_on_error=0");
    static final String ADMIN="proof_key_admin", PUBLISHER="proof_publisher", ORDINARY="proof_ordinary", CONFIG="proof_org_config";
    static final String PASSWORD="synthetic-proof-test-only";
    static final UUID AUDIENCE=UUID.fromString("11111111-1111-1111-1111-111111111111");
    static final String ADMIN_FN="public.hidra_identity_org_commit_admin(bigint,text,uuid,name,bytea)";
    static final String VERIFY_FN="public.hidra_identity_org_commit_verify_consume(bytea,bytea,jsonb)";
    static final String CONTEXT_FN="public.hidra_identity_org_commit_key_context(boolean)";
    static final String[] TABLES={"hidra_identity_org_commit_key_event","hidra_identity_org_commit_key_head","hidra_identity_org_commit_proof_use"};
    long revision;
    static byte[] key() { byte[] b=new byte[32]; for(int i=0;i<32;i++)b[i]=(byte)i; return b; }
    static Connection connection(String role) throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(),role,PASSWORD);
    }
    static Connection owner() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
    }
    static void sql(Connection c,String statement) throws SQLException { try(var s=c.createStatement()){s.execute(statement);} }
    static long number(Connection c,String statement) throws SQLException {
        try(var s=c.createStatement();var r=s.executeQuery(statement)){assertTrue(r.next());return r.getLong(1);}
    }
    @BeforeAll static void migrateAndSeparateRoles() throws Exception {
        Flyway.configure().dataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()).load().migrate();
        try(var c=owner()){
            sql(c,"REVOKE CREATE ON SCHEMA public FROM PUBLIC");
            for(var role:List.of(ADMIN,PUBLISHER,ORDINARY,CONFIG)){
                sql(c,"CREATE ROLE "+role+" LOGIN PASSWORD '"+PASSWORD+"'");
                sql(c,"GRANT USAGE ON SCHEMA public TO "+role);
            }
            // Native lock fixtures only, with no access to any of the three private relations.
            for(var fence:JdbcIdentityAuthorityFence.requiredRelationFences())
                sql(c,"GRANT SELECT,INSERT,UPDATE,DELETE ON "+fence.relation()+" TO "+PUBLISHER);
            sql(c,"GRANT EXECUTE ON FUNCTION "+ADMIN_FN+" TO "+ADMIN);
            sql(c,"GRANT EXECUTE ON FUNCTION "+CONTEXT_FN+" TO "+PUBLISHER);
            sql(c,"CREATE SCHEMA proof_fixture");
            sql(c,"CREATE TABLE proof_fixture.receipt(event_id uuid PRIMARY KEY)");
            sql(c,"CREATE FUNCTION proof_fixture.guard(f bytea,m bytea,e jsonb,fail_after boolean DEFAULT false)"
                +" RETURNS void LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog AS $body$ BEGIN "
                +" PERFORM public.hidra_identity_org_commit_verify_consume(f,m,e);"
                +" IF fail_after THEN RAISE EXCEPTION 'synthetic post-verifier fault'; END IF;"
                +" INSERT INTO proof_fixture.receipt VALUES((e->>'event')::uuid); END $body$");
            sql(c,"REVOKE ALL ON FUNCTION proof_fixture.guard(bytea,bytea,jsonb,boolean) FROM PUBLIC");
            sql(c,"GRANT USAGE ON SCHEMA proof_fixture TO "+PUBLISHER);
            sql(c,"GRANT EXECUTE ON FUNCTION proof_fixture.guard(bytea,bytea,jsonb,boolean) TO "+PUBLISHER);
        }
    }
    @BeforeEach void activateFreshGeneration() throws Exception {
        long old;
        try(var c=owner()){old=number(c,"SELECT coalesce(max(revision),0) FROM public."+TABLES[0]);}
        revision=activate(old,key());
    }
    long activate(long expected,byte[] secret) throws SQLException {
        try(var c=connection(ADMIN);var s=c.prepareStatement("SELECT public.hidra_identity_org_commit_admin(?,?::text,?::uuid,?::name,?)")){
            s.setLong(1,expected);s.setString(2,"test-key");s.setObject(3,AUDIENCE);s.setString(4,PUBLISHER);s.setBytes(5,secret);
            try(var r=s.executeQuery()){assertTrue(r.next());return r.getLong(1);}
        }
    }
    static void nativeLocks(Connection c) throws SQLException {
        for(var fence:JdbcIdentityAuthorityFence.requiredRelationFences())
            sql(c,"LOCK TABLE "+fence.relation()+" IN "+fence.mode().name().replace('_',' ')+" MODE NOWAIT");
    }
    static void context(Connection c,boolean acquire) throws SQLException {
        try(var s=c.prepareStatement("SELECT * FROM public.hidra_identity_org_commit_key_context(?)")){
            s.setBoolean(1,acquire);try(var r=s.executeQuery()){assertTrue(r.next());assertEquals(PUBLISHER,r.getString("publisher"));}
        }
    }
    static void text(DataOutputStream out,String s) throws IOException {var b=s.getBytes(StandardCharsets.UTF_8);out.writeInt(b.length);out.write(b);}
    static void uuid(DataOutputStream out,UUID id) throws IOException {out.writeLong(id.getMostSignificantBits());out.writeLong(id.getLeastSignificantBits());}
    record Proof(byte[] frame,byte[] mac,String expected,UUID event) {}
    // Independent Java test encoder: tests SQL against bytes from outside the SQL implementation.
    Proof proof(Connection c) throws Exception {
        long pid=number(c,"SELECT pg_backend_pid()"),xid=number(c,"SELECT txid_current()");
        long sample=number(c,"SELECT (EXTRACT(epoch FROM clock_timestamp())*1000000)::bigint")-1000;
        UUID event=UUID.randomUUID(),binding=UUID.randomUUID(),session=UUID.randomUUID();
        long until=sample+60_000_000,operation=sample+30_000_000;
        String e="{\"binding\":\""+binding+"\",\"session\":\""+session+"\",\"actor\":\"user\",\"action\":\"TRANSFER\","
                +"\"pairs\":[[\"company\",\"𐀀\"],[\"company\",\"\"]],\"employee\":\"employee\","
                +"\"sampled\":"+sample+",\"nativeUntil\":"+until+",\"operationUntil\":"+operation+","
                +"\"event\":\""+event+"\",\"command\":\""+ "ab".repeat(32)+"\",\"payload\":\""+ "cd".repeat(32)+"\"}";
        var bytes=new ByteArrayOutputStream();var out=new DataOutputStream(bytes);
        text(out,"HIDRA/IDENTITY/ORG-APPOINTMENT-COMMIT/1");out.writeInt(1);uuid(out,AUDIENCE);
        text(out,PUBLISHER);text(out,"test-key");out.writeLong(revision);
        byte[] nonce=new byte[32];new java.security.SecureRandom().nextBytes(nonce);out.write(nonce);
        uuid(out,binding);uuid(out,session);text(out,"user");text(out,"TRANSFER");out.writeInt(2);
        text(out,"company");text(out,"𐀀");text(out,"company");text(out,"");text(out,"employee");
        out.writeLong(sample);out.writeLong(until);out.writeLong(operation);out.writeInt((int)pid);out.writeLong(xid);uuid(out,event);
        out.write(HexFormat.of().parseHex("ab".repeat(32)+"cd".repeat(32)));
        var frame=bytes.toByteArray();var mac=Mac.getInstance("HmacSHA256");mac.init(new SecretKeySpec(key(),"HmacSHA256"));
        return new Proof(frame,mac.doFinal(frame),e,event);
    }
    static void guard(Connection c,Proof p,boolean failAfter) throws SQLException {
        try(var s=c.prepareStatement("SELECT proof_fixture.guard(?,?,?::jsonb,?)")){
            s.setBytes(1,p.frame());s.setBytes(2,p.mac());s.setString(3,p.expected());s.setBoolean(4,failAfter);s.execute();
        }
    }
    static void rejected(Connection c,org.junit.jupiter.api.function.Executable action) throws Exception {
        var save=c.setSavepoint();assertThrows(SQLException.class,action);c.rollback(save);c.releaseSavepoint(save);
    }
    long uses(UUID event) throws SQLException {try(var c=owner()){return number(c,"SELECT count(*) FROM public."+TABLES[2]+" WHERE event_id='"+event+"'");}}
    long receipts(UUID event) throws SQLException {try(var c=owner()){return number(c,"SELECT count(*) FROM proof_fixture.receipt WHERE event_id='"+event+"'");}}

    @Test void separatedLoginsCannotReadKeysChangeStateOrCallPrivateVerifier() throws Exception {
        for(var role:List.of(ADMIN,PUBLISHER,ORDINARY,CONFIG)){
            try(var c=connection(role)){
                assertEquals(1,number(c,"SELECT CASE WHEN session_user=current_user AND session_user='"+role+"' THEN 1 ELSE 0 END"));
                for(var table:TABLES){
                    assertThrows(SQLException.class,()->sql(c,"SELECT * FROM public."+table));
                    assertThrows(SQLException.class,()->sql(c,"DELETE FROM public."+table));
                    assertThrows(SQLException.class,()->sql(c,"TRUNCATE public."+table));
                }
                assertThrows(SQLException.class,()->sql(c,"SELECT public.hidra_identity_org_commit_verify_consume(NULL,NULL,NULL)"));
                assertThrows(SQLException.class,()->sql(c,"SELECT hidra_identity_crypto.hmac('x'::bytea,'x'::bytea,'sha256')"));
                assertThrows(SQLException.class,()->sql(c,"SET ROLE "+POSTGRES.getUsername()));
            }
        }
        for(var role:List.of(PUBLISHER,ORDINARY,CONFIG))
            try(var c=connection(role)){
                assertThrows(SQLException.class,()->sql(c,"SELECT public.hidra_identity_org_commit_admin(0,'x',gen_random_uuid(),'"+PUBLISHER+"',NULL)"));
            }
        try(var c=owner()){
            assertThrows(SQLException.class,()->sql(c,"SELECT * FROM public.hidra_identity_org_commit_key_context(true)"));
        }
    }
    @Test void metadataRequiresNativeFencesAndNeverDisclosesKey() throws Exception {
        try(var c=connection(PUBLISHER)){
            c.setAutoCommit(false);rejected(c,()->context(c,true));nativeLocks(c);context(c,true);context(c,false);
            try(var s=c.createStatement();var r=s.executeQuery("SELECT * FROM public.hidra_identity_org_commit_key_context(false)")){
                assertEquals(6,r.getMetaData().getColumnCount());
            }
            c.rollback();nativeLocks(c);rejected(c,()->context(c,false));c.rollback();
        }
    }
    @Test void verifierConsumesOnceAndSyntheticReceiptAppearsOnlyAfterCommit() throws Exception {
        Proof p;
        try(var c=connection(PUBLISHER)){
            c.setAutoCommit(false);nativeLocks(c);context(c,true);p=proof(c);guard(c,p,false);
            assertEquals(0,uses(p.event()));assertEquals(0,receipts(p.event()));
            rejected(c,()->guard(c,p,false));c.commit();
        }
        assertEquals(1,uses(p.event()));assertEquals(1,receipts(p.event()));
    }
    @Test void rollbackAndPostVerifierFailureLeaveNoOrphanConsumptionOrReceipt() throws Exception {
        try(var c=connection(PUBLISHER)){
            c.setAutoCommit(false);nativeLocks(c);context(c,true);var p=proof(c);
            rejected(c,()->guard(c,p,true));
            assertEquals(0,uses(p.event()));guard(c,p,false);c.rollback();
            assertEquals(0,uses(p.event()));assertEquals(0,receipts(p.event()));
        }
    }
    @Test void copiedProofCannotCrossPhysicalTransactionEvenAfterRollback() throws Exception {
        try(var c=connection(PUBLISHER)){
            c.setAutoCommit(false);nativeLocks(c);context(c,true);var p=proof(c);c.rollback();
            nativeLocks(c);context(c,true);rejected(c,()->guard(c,p,false));c.rollback();
            assertEquals(0,uses(p.event()));
        }
    }
    @Test void everyFrameByteAndEveryMacByteTamperRejects() throws Exception {
        try(var c=connection(PUBLISHER)){
            c.setAutoCommit(false);nativeLocks(c);context(c,true);var p=proof(c);
            for(int i=0;i<p.frame().length;i++){
                var changed=p.frame().clone();changed[i]^=1;
                rejected(c,()->guard(c,new Proof(changed,p.mac(),p.expected(),p.event()),false));
            }
            for(int i=0;i<32;i++){
                var changed=p.mac().clone();changed[i]^=1;
                rejected(c,()->guard(c,new Proof(p.frame(),changed,p.expected(),p.event()),false));
            }
            for(byte[] mac:List.of(new byte[0],new byte[31],new byte[33]))
                rejected(c,()->guard(c,new Proof(p.frame(),mac,p.expected(),p.event()),false));
            for(byte[] frame:List.of(new byte[0],Arrays.copyOf(p.frame(),p.frame().length-1),Arrays.copyOf(p.frame(),p.frame().length+1),new byte[4097]))
                rejected(c,()->guard(c,new Proof(frame,p.mac(),p.expected(),p.event()),false));
            guard(c,p,false);c.rollback();assertEquals(0,uses(p.event()));
        }
    }
    @Test void independentlyChangedRealExpectationCannotBorrowValidMac() throws Exception {
        try(var c=connection(PUBLISHER)){
            c.setAutoCommit(false);nativeLocks(c);context(c,true);var p=proof(c);
            for(String e:List.of(p.expected().replace("TRANSFER","CORRECT"),p.expected().replace("employee","other"),
                    p.expected().replace("user","another"),p.expected().replace("company","another"),
                    p.expected().replace("ab".repeat(32),"aa".repeat(32)),
                    p.expected().replace("cd".repeat(32),"cc".repeat(32)),
                    p.expected().replace(p.event().toString(),UUID.randomUUID().toString()),
                    p.expected().replace("[[\"company\",\"𐀀\"],[\"company\",\"\"]]","[[\"company\",\"\"],[\"company\",\"𐀀\"]]")))
                rejected(c,()->guard(c,new Proof(p.frame(),p.mac(),e,p.event()),false));
            guard(c,p,false);c.rollback();
        }
    }
    @Test void evenAuthenticMacForAnotherContextCannotMatchTrustedExpectation() throws Exception {
        try(var c=connection(PUBLISHER)){
            c.setAutoCommit(false);nativeLocks(c);context(c,true);var p=proof(c);
            int nonceStart=4+39+4+16+4+PUBLISHER.length()+4+8+8;
            for(int i=0;i<p.frame().length;i++){
                if(i>=nonceStart && i<nonceStart+32)continue;
                var changed=p.frame().clone();changed[i]^=1;
                var mac=Mac.getInstance("HmacSHA256");mac.init(new SecretKeySpec(key(),"HmacSHA256"));
                var authentic=new Proof(changed,mac.doFinal(changed),p.expected(),p.event());
                rejected(c,()->guard(c,authentic,false));
            }
            guard(c,p,false);c.rollback();
        }
    }
    @Test void keyCasRotationRevocationAndHistoryCannotResetOrRetainOldKey() throws Exception {
        assertThrows(SQLException.class,()->activate(revision-1,key()));
        assertEquals(revision+1,activate(revision,null));
        try(var c=connection(PUBLISHER)){
            c.setAutoCommit(false);nativeLocks(c);rejected(c,()->context(c,true));c.rollback();
        }
        assertThrows(SQLException.class,()->activate(revision,key()));
        assertEquals(revision+2,activate(revision+1,key()));
        try(var c=owner()){
            assertEquals(1,number(c,"SELECT count(*) FROM public."+TABLES[0]+" WHERE revision="+(revision+1)+" AND NOT active AND fingerprint IS NULL"));
            assertThrows(SQLException.class,()->sql(c,"DELETE FROM public."+TABLES[0]));
            assertThrows(SQLException.class,()->sql(c,"TRUNCATE public."+TABLES[1]));
            assertEquals(0,number(c,"SELECT count(*) FROM information_schema.columns WHERE table_name='"+TABLES[0]+"' AND column_name='key_bytes'"));
        }
    }
    @Test void retainedKeyFenceStopsConcurrentAdministrationAndOldGenerationCannotRedeem() throws Exception {
        Proof old;
        try(var c=connection(PUBLISHER)){
            c.setAutoCommit(false);nativeLocks(c);context(c,true);old=proof(c);
            var worker=Executors.newSingleThreadExecutor();
            try { assertThrows(ExecutionException.class,()->worker.submit(()->activate(revision,null)).get(5,TimeUnit.SECONDS)); }
            finally {worker.shutdownNow();}
            c.rollback();
        }
        activate(revision,key());
        try(var c=connection(PUBLISHER)){
            c.setAutoCommit(false);nativeLocks(c);context(c,true);
            rejected(c,()->guard(c,old,false));c.rollback();
        }
    }
    @Test void catalogTamperAndRoleSwitchBridgeFailClosed() throws Exception {
        try(var admin=owner();var c=connection(PUBLISHER)){
            sql(admin,"GRANT SELECT ON public."+TABLES[1]+" TO "+ORDINARY);
            c.setAutoCommit(false);nativeLocks(c);
            try {rejected(c,()->context(c,true));}
            finally {sql(admin,"REVOKE SELECT ON public."+TABLES[1]+" FROM "+ORDINARY);}
            context(c,true);c.rollback();
            sql(admin,"GRANT EXECUTE ON FUNCTION "+VERIFY_FN+" TO "+PUBLISHER);
            nativeLocks(c);
            try {rejected(c,()->context(c,true));}
            finally {sql(admin,"REVOKE EXECUTE ON FUNCTION "+VERIFY_FN+" FROM "+PUBLISHER);}
            c.rollback();
        }
    }
    @Test void frozenJavaSqlFrameHmacUuidUnicodeAndMicrosecondVectorAgree() throws Exception {
        String frame="0000002748494452412f4944454e544954592f4f52472d4150504f494e544d454e542d434f4d4d49542f310000000111111111111111111111111111111111000000097075626c697368657200000008746573742d6b65790000000000000007000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f22222222222222222222222222222222333333333333333333333333333333330000000475736572000000085452414e534645520000000200000007636f6d70616e7900000004f090808000000007636f6d70616e7900000003ee808000000008656d706c6f796565fffffffffff0bdbf0000000000112480000000000001e2400000007b00000000000001c844444444444444444444444444444444ababababababababababababababababababababababababababababababababcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcd";
        String expected="{\"binding\":\"22222222-2222-2222-2222-222222222222\",\"session\":\"33333333-3333-3333-3333-333333333333\","
                +"\"actor\":\"user\",\"action\":\"TRANSFER\",\"pairs\":[[\"company\",\"𐀀\"],[\"company\",\"\"]],"
                +"\"employee\":\"employee\",\"sampled\":-1000001,\"nativeUntil\":1123456,\"operationUntil\":123456,"
                +"\"event\":\"44444444-4444-4444-4444-444444444444\",\"command\":\""+ "ab".repeat(32)+"\",\"payload\":\""+ "cd".repeat(32)+"\"}";
        try(var c=owner();var s=c.prepareStatement("SELECT encode(public.hidra_identity_org_commit_frame("
                +"public.hidra_identity_org_commit_prefix(?::uuid,'publisher','test-key',7),?,?::jsonb,123,456),'hex'),"
                +"encode(hidra_identity_crypto.hmac(?,?, 'sha256'),'hex')")){
            s.setObject(1,AUDIENCE);s.setBytes(2,key());s.setString(3,expected);s.setBytes(4,HexFormat.of().parseHex(frame));s.setBytes(5,key());
            try(var r=s.executeQuery()){assertTrue(r.next());assertEquals(frame,r.getString(1));
                assertEquals("25045243507018a5ebec21c371204227eaefcb99d1ab6374b1a44ffc7c03eda7",r.getString(2));}
        }
    }

    private Proof resignExpectation(Connection transaction,Proof original,String expected,byte[] signingKey) throws Exception {
        byte[] encoded;
        try(var admin=owner();var s=admin.prepareStatement("SELECT public.hidra_identity_org_commit_frame("
                +"public.hidra_identity_org_commit_prefix(?::uuid,?::text,'test-key',?),?,?::jsonb,?,?)")){
            int prefixLength=4+39+4+16+4+PUBLISHER.length()+4+8+8;
            s.setObject(1,AUDIENCE);s.setString(2,PUBLISHER);s.setLong(3,revision);
            s.setBytes(4,Arrays.copyOfRange(original.frame(),prefixLength,prefixLength+32));s.setString(5,expected);
            s.setInt(6,(int)number(transaction,"SELECT pg_backend_pid()"));s.setLong(7,number(transaction,"SELECT txid_current()"));
            try(var r=s.executeQuery()){assertTrue(r.next());encoded=r.getBytes(1);}
        }
        var mac=Mac.getInstance("HmacSHA256");mac.init(new SecretKeySpec(signingKey,"HmacSHA256"));
        return new Proof(encoded,mac.doFinal(encoded),expected,original.event());
    }
    @Test void validMacStillRejectsExpiredWindowAndRuntimeKeyMismatch() throws Exception {
        try(var c=connection(PUBLISHER)){
            c.setAutoCommit(false);nativeLocks(c);context(c,true);var p=proof(c);
            var wrong=resignExpectation(c,p,p.expected(),new byte[32]);
            rejected(c,()->guard(c,wrong,false));
            String expired=p.expected().replaceAll("\"operationUntil\":[0-9]+","\"operationUntil\":1")
                    .replaceAll("\"sampled\":[0-9]+","\"sampled\":0");
            var stale=resignExpectation(c,p,expired,key());rejected(c,()->guard(c,stale,false));
            guard(c,p,false);c.rollback();
        }
    }
    @Test void unsafeTriggerExtensionAndRoutineCatalogCannotSupplyMetadata() throws Exception {
        try(var admin=owner();var c=connection(PUBLISHER)){
            c.setAutoCommit(false);nativeLocks(c);
            sql(admin,"ALTER TABLE public."+TABLES[0]+" DISABLE TRIGGER hidra_identity_org_key_event_immutable");
            try {rejected(c,()->context(c,true));}
            finally {sql(admin,"ALTER TABLE public."+TABLES[0]+" ENABLE TRIGGER hidra_identity_org_key_event_immutable");}
            sql(admin,"ALTER EXTENSION pgcrypto SET SCHEMA public");
            try {rejected(c,()->context(c,true));}
            finally {sql(admin,"ALTER EXTENSION pgcrypto SET SCHEMA hidra_identity_crypto");}
            sql(admin,"ALTER FUNCTION "+CONTEXT_FN+" SET search_path TO public");
            try {rejected(c,()->context(c,true));}
            finally {sql(admin,"ALTER FUNCTION "+CONTEXT_FN+" SET search_path TO pg_catalog");}
            context(c,true);c.rollback();
        }
    }

    private static String disposableMigrationDatabase() throws SQLException {
        String database="proof_migration_"+UUID.randomUUID().toString().replace("-","");
        try(var c=owner()){sql(c,"CREATE DATABASE "+database);}
        return POSTGRES.getJdbcUrl().replace("/"+POSTGRES.getDatabaseName(),"/"+database);
    }
    private static Connection migrationOwner(String url) throws SQLException {
        return DriverManager.getConnection(url,POSTGRES.getUsername(),POSTGRES.getPassword());
    }
    private static void dropMigrationDatabase(String url) throws SQLException {
        String database=url.substring(url.lastIndexOf('/')+1).split("\\?")[0];
        if(!database.matches("proof_migration_[0-9a-f]{32}"))throw new IllegalArgumentException("Unexpected fixture database");
        try(var c=owner()){sql(c,"DROP DATABASE "+database+" WITH (FORCE)");}
    }

    @Test void publicCleanRebuildReusesOnlyValidatedCryptoNamespaceAndExtension() throws Exception {
        String url=disposableMigrationDatabase();
        try {
            var flyway=Flyway.configure().dataSource(url,POSTGRES.getUsername(),POSTGRES.getPassword())
                    .schemas("public").cleanDisabled(false).load();
            flyway.migrate();
            long extension,cryptoSchema;
            try(var c=migrationOwner(url)){
                extension=number(c,"SELECT oid FROM pg_extension WHERE extname='pgcrypto'");
                cryptoSchema=number(c,"SELECT oid FROM pg_namespace WHERE nspname='hidra_identity_crypto'");
            }
            flyway.clean();
            try(var c=migrationOwner(url)){
                assertEquals(extension,number(c,"SELECT oid FROM pg_extension WHERE extname='pgcrypto'"));
                assertEquals(cryptoSchema,number(c,"SELECT oid FROM pg_namespace WHERE nspname='hidra_identity_crypto'"));
            }
            flyway.migrate();flyway.validate();
            try(var c=migrationOwner(url)){
                sql(c,"SELECT public.hidra_identity_org_commit_catalog()");
                assertEquals(extension,number(c,"SELECT oid FROM pg_extension WHERE extname='pgcrypto'"));
                assertEquals(0,number(c,"SELECT count(*) FROM public.hidra_identity_org_commit_key_head"));
                assertEquals(0,number(c,"SELECT count(*) FROM public.hidra_identity_org_commit_proof_use"));
            }
        } finally {dropMigrationDatabase(url);}
    }

    @Test void inheritedApplicationDefaultsCannotGrantPrivateProofObjectsAndRemainUnchanged() throws Exception {
        String url=disposableMigrationDatabase();
        try {
            Flyway.configure().dataSource(url,POSTGRES.getUsername(),POSTGRES.getPassword())
                    .target("20261022.002").load().migrate();
            try(var c=migrationOwner(url)){
                sql(c,"ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT,INSERT,UPDATE,DELETE ON TABLES TO "
                        +ORDINARY+","+PUBLISHER+","+CONFIG);
                sql(c,"ALTER DEFAULT PRIVILEGES GRANT EXECUTE ON FUNCTIONS TO "+ORDINARY+","+PUBLISHER+","+CONFIG);
            }
            var flyway=Flyway.configure().dataSource(url,POSTGRES.getUsername(),POSTGRES.getPassword()).load();
            flyway.migrate();flyway.validate();
            try(var c=migrationOwner(url)){
                sql(c,"SELECT public.hidra_identity_org_commit_catalog()");
                for(var role:List.of(ORDINARY,PUBLISHER,CONFIG)){
                    for(var table:TABLES)
                        assertEquals(0,number(c,"SELECT CASE WHEN has_table_privilege('"+role+"','public."+table
                                +"','SELECT,INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER') THEN 1 ELSE 0 END"));
                    for(var routine:List.of(ADMIN_FN,VERIFY_FN,CONTEXT_FN,
                            "hidra_identity_crypto.hmac(bytea,bytea,text)"))
                        assertEquals(0,number(c,"SELECT CASE WHEN has_function_privilege('"+role+"','"+routine
                                +"','EXECUTE') THEN 1 ELSE 0 END"));
                }
                // Only the new private object's inherited ACLs were removed, never the defaults.
                sql(c,"CREATE TABLE public.proof_default_acl_control(id integer)");
                sql(c,"CREATE FUNCTION public.proof_default_acl_control() RETURNS integer LANGUAGE sql AS 'SELECT 1'");
                assertEquals(1,number(c,"SELECT CASE WHEN has_table_privilege('"+ORDINARY
                        +"','public.proof_default_acl_control','SELECT') AND has_function_privilege('"+ORDINARY
                        +"','public.proof_default_acl_control()','EXECUTE') THEN 1 ELSE 0 END"));
            }
        } finally {dropMigrationDatabase(url);}
    }

    @Test void retainedCryptoNamespaceWithForeignAclOrObjectIsRejectedWithoutLaundering() throws Exception {
        String url=disposableMigrationDatabase();
        try {
            var flyway=Flyway.configure().dataSource(url,POSTGRES.getUsername(),POSTGRES.getPassword())
                    .schemas("public").cleanDisabled(false).load();
            flyway.migrate();flyway.clean();
            try(var c=migrationOwner(url)){sql(c,"GRANT USAGE ON SCHEMA hidra_identity_crypto TO PUBLIC");}
            assertThrows(org.flywaydb.core.api.FlywayException.class,flyway::migrate);
            try(var c=migrationOwner(url)){
                assertEquals(1,number(c,"SELECT count(*) FROM pg_namespace n,LATERAL aclexplode(n.nspacl) a "
                        +"WHERE n.nspname='hidra_identity_crypto' AND a.grantee=0 AND a.privilege_type='USAGE'"));
                sql(c,"REVOKE ALL ON SCHEMA hidra_identity_crypto FROM PUBLIC");
                sql(c,"CREATE FUNCTION hidra_identity_crypto.unexpected_fixture() RETURNS integer LANGUAGE sql AS 'SELECT 1'");
            }
            assertThrows(org.flywaydb.core.api.FlywayException.class,flyway::migrate);
            try(var c=migrationOwner(url)){
                assertEquals(1,number(c,"SELECT count(*) FROM pg_proc WHERE oid='hidra_identity_crypto.unexpected_fixture()'::regprocedure"));
                sql(c,"DROP FUNCTION hidra_identity_crypto.unexpected_fixture()");
            }
            flyway.migrate();flyway.validate();
        } finally {dropMigrationDatabase(url);}
    }
}


