/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityTemporalEventCollectorPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Proves bounded real Identity event collection and fail-closed transaction behavior.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationTemporalBoundaryCompiler.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityTemporalEventCollector;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityTemporalEventCollector.Candidate;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import jakarta.persistence.EntityManager;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/** Zero-tag fixtures prove SQL-trigger structure only, never private producer origin or permission. */
@Testcontainers(disabledWithoutDocker=true)
@SpringBootTest(classes=HidraApplication.class,webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class IdentityTemporalEventCollectorPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16");
    @Autowired JdbcTemplate jdbc;
    @Autowired EntityManager em;
    @Autowired Flyway flyway;
    @Autowired JdbcIdentityTemporalEventCollector collector;
    @Autowired ProtectedTransactionExecutor executor;
    @Autowired PlatformTransactionManager manager;
    private final Deque<Cleanup> cleanup=new ArrayDeque<>();
    private final AtomicInteger receiptIds=new AtomicInteger();
    private OperationAuthenticationBinding binding;
    private String schema;
    private record Cleanup(String table,String id) {}
    @DynamicPropertySource static void properties(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url",POSTGRES::getJdbcUrl);
        r.add("spring.datasource.username",POSTGRES::getUsername);
        r.add("spring.datasource.password",POSTGRES::getPassword);
        r.add("spring.flyway.enabled",()->"true");
        r.add("spring.flyway.validate-on-migrate",()->"true");
        r.add("spring.jpa.hibernate.ddl-auto",()->"validate");
    }
    @BeforeEach void warmAndSeed() {
        flyway.validate();
        assertThat(flyway.info().pending()).isEmpty();
        schema="hpr051d_"+id().replace("-","");
        jdbc.execute("CREATE SCHEMA "+schema);
        jdbc.execute("CREATE TABLE "+receipt()+"(id integer PRIMARY KEY)");
        binding=fixture("LOCAL",600);
        TransactionTemplate warm=new TransactionTemplate(manager);
        warm.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        Candidate c=warm.execute(status->collector.collect(binding.bindingId()));
        assertThat(c).isNotNull();
        assertThat(c.temporalInputs()).isPositive().isLessThanOrEqualTo(1024);
        assertThat(executor.execute(()->"warm",em::flush,
                ()->jdbc.queryForObject("SELECT 1",Integer.class))).isEqualTo("warm");
    }
    @AfterEach void cleanupFixtureRows() {
        // Delete only this test's ordinary grant/policy/attribute fixtures in FK order.
        // Retain all guarded User/Provider/Credential/Link/Session/Binding identities.
        for(Cleanup c:cleanup) jdbc.update("DELETE FROM "+table(c.table())+" WHERE id=?",c.id());
        if(schema!=null) jdbc.execute("DROP SCHEMA "+schema+" CASCADE");
    }
    private String receipt(){return schema+".receipt";}
    private Candidate run(String id) {
        AtomicReference<Candidate> captured=new AtomicReference<>();
        return executor.execute(()->{var c=collector.collect(id);captured.set(c);return c;},em::flush,
                ()->guard(captured.get()));
    }
    private void guard(Candidate c) {
        assertThat(c).isNotNull();
        Integer inserted=jdbc.queryForObject("WITH observed AS MATERIALIZED (SELECT clock_timestamp() AS t),"
                +" made AS (INSERT INTO "+receipt()+" SELECT ? FROM observed WHERE t>=? AND t<? RETURNING id)"
                +" SELECT count(*) FROM made",Integer.class,receiptIds.incrementAndGet(),
                Timestamp.from(c.sampledAt()),Timestamp.from(c.earliestFuture()));
        assertThat(inserted).isEqualTo(1);
    }
    private void rejected(Runnable action) {
        assertThatThrownBy(action::run).isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
            .satisfies(e->assertThat(((ProtectedTransactionExecutor.ExecutionFailure)e).outcome())
                .isEqualTo(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT));
    }
    @Test void completeTimedEstateIncludesInactiveFutureDeny() {
        String permission=permission(),grant=id();
        Instant from=dbNow().plusSeconds(5),to=from.plusSeconds(100);
        seed("user_permission_grant",grant,
                "id,user_id,permission_id,effect,grant_reason,emergency_access,valid_from,valid_to,status,created_at",
                "?,?,?,'DENY','future unmatched synthetic',false,?,?,'SUSPENDED',clock_timestamp()",
                grant,binding.userId(),permission,Timestamp.from(from),Timestamp.from(to));
        Candidate c=run(binding.bindingId());
        assertThat(c.earliestFuture()).isEqualTo(from);
        assertThat(c.contributors()).contains(new Contributor("user_permission_grant:"+grant,Kind.DIRECT_GRANT,Endpoint.FROM));
        assertThat(c.temporalInputs()).isGreaterThan(3);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+receipt(),Integer.class)).isEqualTo(1);
    }
    @Test void roleMembershipPolicyAndAttributeFutureStartsAreRetained() {
        String role=role(),group=group(),permission=permission(),definition=attributeDefinition();
        Instant from=dbNow().plusSeconds(5),to=from.plusSeconds(100);
        List<Contributor> expected=new ArrayList<>();
        String ur=id(); seed("user_role_grant",ur,"id,user_id,role_id,valid_from,valid_to,status,created_at",
                "?,?,?, ?,?,'SUSPENDED',clock_timestamp()",ur,binding.userId(),role,Timestamp.from(from),Timestamp.from(to));
        expected.add(new Contributor("user_role_grant:"+ur,Kind.GRANT_OR_MEMBERSHIP,Endpoint.FROM));
        String gr=id();seed("group_role_grant",gr,"id,group_id,role_id,valid_from,valid_to,status,created_at",
                "?,?,?, ?,?,'SUSPENDED',clock_timestamp()",gr,group,role,Timestamp.from(from),Timestamp.from(to));
        expected.add(new Contributor("group_role_grant:"+gr,Kind.GRANT_OR_MEMBERSHIP,Endpoint.FROM));
        String rp=id();seed("role_permission_grant",rp,"id,role_id,permission_id,effect,condition_expression,valid_from,valid_to,status,created_at",
                "?,?,?,'DENY','{\"op\":\"exists\",\"attribute\":\"subject.id\"}'::jsonb,?,?,'SUSPENDED',clock_timestamp()",
                rp,role,permission,Timestamp.from(from),Timestamp.from(to));
        expected.add(new Contributor("role_permission_grant:"+rp,Kind.GRANT_OR_MEMBERSHIP,Endpoint.FROM));
        String member=id();seed("user_group_membership",member,"id,user_id,group_id,membership_type,valid_from,valid_to,status,created_at,updated_at",
                "?,?,?,'DIRECT',?,?,'SUSPENDED',clock_timestamp(),clock_timestamp()",member,binding.userId(),group,Timestamp.from(from),Timestamp.from(to));
        expected.add(new Contributor("user_group_membership:"+member,Kind.GRANT_OR_MEMBERSHIP,Endpoint.FROM));
        String version=policyVersion(from,to);
        expected.add(new Contributor("authorization_policy_version:"+version,Kind.POLICY_VERSION,Endpoint.FROM));
        String attr=id();seed("subject_security_attribute",attr,"id,subject_type,subject_id,attribute_definition_id,attribute_value,valid_from,valid_to,status,created_at,updated_at",
                "?,'USER',?,?,'synthetic',?,?,'INACTIVE',clock_timestamp(),clock_timestamp()",attr,binding.userId(),definition,Timestamp.from(from),Timestamp.from(to));
        expected.add(new Contributor("subject_security_attribute:"+attr,Kind.ATTRIBUTE,Endpoint.FROM));
        Candidate c=run(binding.bindingId());
        assertThat(c.earliestFuture()).isEqualTo(from);
        assertThat(c.contributors()).containsAll(expected);
        assertThat(c.referencedAttributes()).contains("subject.id");
        assertThat(c.expressionRows()).isPositive();
        assertThatThrownBy(()->c.contributors().clear()).isInstanceOf(UnsupportedOperationException.class);
    }
    @Test void selectedBindingAndSessionExpiryBoundCandidate() {
        Candidate before=run(binding.bindingId());
        OperationAuthenticationBinding oidc=fixture("OIDC",60);
        Candidate c=run(oidc.bindingId());
        assertThat(c.earliestFuture()).isBeforeOrEqualTo(oidc.expiresAt());
        assertThat(c.temporalInputs()).isEqualTo(before.temporalInputs()+5);
        assertThat(c.earliestFuture()).isBeforeOrEqualTo(oidc.sourceExpiresAt());
        // The stored native/session tuple is exact; it still has an intentionally invalid producer tag.
        assertThat(jdbc.queryForObject("SELECT expires_at FROM "+table("login_session")+" WHERE id=?",
                Timestamp.class,oidc.sessionId()).toInstant()).isEqualTo(oidc.expiresAt());
        assertThat(oidc.producerAuthenticator()).containsOnly((byte)0);
    }
    @Test void staticExpressionGateRejectsHiddenClockAndUnclassifiedAttribute() {
        String role=role(),permission=permission(),grant=id();
        seed("role_permission_grant",grant,"id,role_id,permission_id,effect,condition_expression,valid_from,valid_to,status,created_at",
                "?,?,?,'DENY',?::jsonb,clock_timestamp()-interval '1 hour',clock_timestamp()+interval '1 hour','SUSPENDED',clock_timestamp()",
                grant,role,permission,"{\"op\":\"any\",\"args\":[{\"op\":\"exists\",\"attribute\":\"subject.id\"},{\"op\":\"exists\",\"attribute\":\"context.evaluatedAt\"}]}");
        rejected(()->run(binding.bindingId()));
        jdbc.update("UPDATE "+table("role_permission_grant")+" SET condition_expression=?::jsonb WHERE id=?",
                "{\"op\":\"not\",\"arg\":{\"op\":\"exists\",\"attribute\":\"subject.attributes.rank\"}}",grant);
        rejected(()->run(binding.bindingId()));
        jdbc.update("UPDATE "+table("role_permission_grant")+" SET condition_expression=NULL WHERE id=?",grant);
        String version=policyVersion(dbNow().minusSeconds(10),dbNow().plusSeconds(100));
        String rule=id();seed("authorization_policy_rule",rule,
                "id,policy_version_id,rule_code,effect,priority,obligation_expression,status,created_at",
                "?,?,?,'DENY',1,'{}'::jsonb,'DISABLED',clock_timestamp()",rule,version,rule);
        rejected(()->run(binding.bindingId()));
        jdbc.update("UPDATE "+table("authorization_policy_rule")+" SET obligation_expression=NULL WHERE id=?",rule);
        assertThat(run(binding.bindingId())).isNotNull();
    }
    @Test void databaseSampleAndCandidateUseSameBackend() {
        AtomicReference<Candidate> captured=new AtomicReference<>();
        Candidate c=executor.execute(()->{
            Candidate candidate=collector.collect(binding.bindingId());captured.set(candidate);
            assertThat(jdbc.queryForObject("SELECT pg_backend_pid()",Integer.class)).isEqualTo(candidate.backendPid());
            assertThat(jdbc.queryForObject("SELECT txid_current()",Long.class)).isEqualTo(candidate.transactionId());
            assertThat(jdbc.queryForObject("SELECT clock_timestamp()>=?",Boolean.class,Timestamp.from(candidate.sampledAt()))).isTrue();
            assertThat(candidate.sampledAt().getNano()%1000).isZero();
            return candidate;
        },em::flush,()->guard(captured.get()));
        assertThat(c).isNotNull();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+receipt(),Integer.class)).isEqualTo(1);
    }
    @Test void nativeWriterCannotChangeCensusBeforeCommit() throws Exception {
        String permission=permission(),grant=id();
        String insert="INSERT INTO "+table("user_permission_grant")
                +"(id,user_id,permission_id,effect,grant_reason,emergency_access,valid_from,valid_to,status,created_at)"
                +" VALUES ('"+grant+"','"+binding.userId()+"','"+permission+"','DENY','synthetic',false,"
                +"clock_timestamp()+interval '5 minutes',clock_timestamp()+interval '10 minutes','SUSPENDED',clock_timestamp())";
        AtomicReference<Candidate> captured=new AtomicReference<>();
        try(Writer writer=new Writer(c->sql(c,insert))) {
            Candidate result=executor.execute(()->{
                Candidate c=collector.collect(binding.bindingId());captured.set(c);
                writer.start();observeWait(writer.pid,c.backendPid());
                assertThat(writer.result()).isEqualTo("55P03");
                assertThat(jdbc.queryForObject("SELECT count(*) FROM "+table("user_permission_grant")+" WHERE id=?",Integer.class,grant)).isZero();
                return c;
            },em::flush,()->guard(captured.get()));
            assertThat(result).isNotNull();
            writer.retryAndCommit();
        }
        cleanup.addFirst(new Cleanup("user_permission_grant",grant));
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+table("user_permission_grant")+" WHERE id=?",Integer.class,grant)).isEqualTo(1);
    }
    @Test void missingBindingOrExpiredB1Rejects() {
        rejected(()->run(id()));
        OperationAuthenticationBinding shortLived=fixture("LOCAL",4);
        long deadline=System.nanoTime()+TimeUnit.SECONDS.toNanos(5);
        // Observe a REAL expiration outside protected work; no sleep or mocked database clock.
        while(dbNow().isBefore(shortLived.expiresAt())) {
            if(System.nanoTime()>=deadline) fail("Real expiry was not reached within bounded observation");
        }
        rejected(()->run(shortLived.bindingId()));
        assertThat(run(binding.bindingId())).isNotNull();
    }
    @Test void oversizedCensusRollsBackWithoutPartialResult() {
        String permission=permission(),prefix=id();AtomicBoolean guarded=new AtomicBoolean();
        rejected(()->executor.execute(()->{
            jdbc.update("INSERT INTO "+table("user_permission_grant")
                +"(id,user_id,permission_id,effect,grant_reason,emergency_access,valid_from,valid_to,status,created_at)"
                +" SELECT ?||'-'||g,?,?,'DENY','synthetic overflow',false,clock_timestamp(),clock_timestamp()+interval '1 hour','SUSPENDED',clock_timestamp()"
                +" FROM generate_series(1,1025) AS g",prefix,binding.userId(),permission);
            jdbc.update("INSERT INTO "+receipt()+" VALUES(99)");
            try { return collector.collect(binding.bindingId()); }
            catch(IllegalStateException e) {
                assertThat(e).hasMessage("Temporal census exceeds reviewed bound");
                throw e;
            }
        },em::flush,()->{guarded.set(true);jdbc.queryForObject("SELECT 1",Integer.class);}));
        assertThat(guarded).isFalse();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+table("user_permission_grant")+" WHERE id LIKE ?",Integer.class,prefix+"%")).isZero();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+receipt(),Integer.class)).isZero();
        assertThat(run(binding.bindingId())).isNotNull();
    }
    @Test void readOnlyAndNoTransactionCallsReject() {
        assertThatThrownBy(()->collector.collect(binding.bindingId())).isInstanceOf(RuntimeException.class);
        TransactionTemplate readOnly=new TransactionTemplate(manager);readOnly.setReadOnly(true);
        assertThatThrownBy(()->readOnly.execute(status->collector.collect(binding.bindingId()))).isInstanceOf(RuntimeException.class);
        TransactionTemplate repeatable=new TransactionTemplate(manager);
        repeatable.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        assertThatThrownBy(()->repeatable.execute(status->collector.collect(binding.bindingId()))).isInstanceOf(RuntimeException.class);
        assertThat(run(binding.bindingId())).isNotNull();
    }
    @Test void lateRelationLockFailureRollsBackAndCannotRecover() throws Exception {
        AtomicBoolean delivered=new AtomicBoolean(),guarded=new AtomicBoolean();
        CountDownLatch entering=new CountDownLatch(1);AtomicInteger waitingPid=new AtomicInteger();
        try(var holder=connection();var observer=connection();var pool=Executors.newSingleThreadExecutor()) {
            holder.setAutoCommit(false);
            sql(holder,"LOCK TABLE "+table("user_role_grant")+" IN ROW EXCLUSIVE MODE");
            int holderPid=pid(holder);
            Future<?> proof=pool.submit(()->{await(entering);observeWait(observer,waitingPid.get(),holderPid);});
            try {
                rejected(()->{
                    executor.execute(()->{
                        UserJpaEntity user=em.find(UserJpaEntity.class,binding.userId());
                        ReflectionTestUtils.setField(user,"displayName","provisional collector collision");
                        em.flush();jdbc.update("INSERT INTO "+receipt()+" VALUES(99)");
                        waitingPid.set(jdbc.queryForObject("SELECT pg_backend_pid()",Integer.class));entering.countDown();
                        try { collector.collect(binding.bindingId()); fail("Collector unexpectedly acquired blocked final relation"); }
                        catch(RuntimeException e) {
                            assertThat(List.of("55P03","40P01")).contains(sqlState(e));
                            assertThatThrownBy(()->jdbc.queryForObject("SELECT 1",Integer.class)).isInstanceOf(RuntimeException.class);
                        }
                        return "must not be delivered";
                    },em::flush,()->{guarded.set(true);jdbc.queryForObject("SELECT 1",Integer.class);});
                    delivered.set(true);
                });
                joined(proof);
            } finally {holder.rollback();}
        }
        assertThat(delivered).isFalse();assertThat(guarded).isFalse();
        assertThat(jdbc.queryForObject("SELECT display_name FROM "+table("user")+" WHERE id=?",String.class,binding.userId())).isNull();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM "+receipt(),Integer.class)).isZero();
        assertThat(run(binding.bindingId())).isNotNull();
    }
    private static String table(String name){return "public.hidra_identity_"+name;}
    private static String id(){return UUID.randomUUID().toString();}
    private Instant dbNow(){return jdbc.queryForObject("SELECT clock_timestamp()",Timestamp.class).toInstant();}
    private void seed(String table,String id,String columns,String values,Object...args) {
        jdbc.update("INSERT INTO "+table(table)+"("+columns+") VALUES ("+values+")",args);
        cleanup.addFirst(new Cleanup(table,id));
    }
    private String permission() {
        String id=id();seed("permission",id,"id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at",
                "?,?,'TOPOLOGY','FACILITY','READ',false,'ACTIVE',clock_timestamp(),clock_timestamp()",id,"topology:facility:read-"+id);return id;
    }
    private String role() {
        String id=id();seed("role",id,"id,code,role_type,status,created_at,updated_at",
                "?,?,'BUSINESS','ACTIVE',clock_timestamp(),clock_timestamp()",id,id);return id;
    }
    private String group() {
        String id=id();seed("group",id,"id,code,group_type,status,created_at,updated_at",
                "?,?,'LOCAL','ACTIVE',clock_timestamp(),clock_timestamp()",id,id);return id;
    }
    private String attributeDefinition() {
        String id=id();seed("attribute_definition",id,"id,code,name,attribute_target,data_type,multi_valued,sensitive,status,created_at,updated_at",
                "?,?,'Synthetic','USER','STRING',false,false,'ACTIVE',clock_timestamp(),clock_timestamp()",id,id);return id;
    }
    private String policyVersion(Instant from,Instant to) {
        String policy=id(),version=id();
        seed("authorization_policy",policy,"id,code,name,policy_domain,status,created_at,updated_at",
                "?,?,'Synthetic','TOPOLOGY','DISABLED',clock_timestamp(),clock_timestamp()",policy,policy);
        seed("authorization_policy_version",version,"id,policy_id,version_number,status,effective_from,effective_to,created_at",
                "?,?,1,'DRAFT',?,?,clock_timestamp()",version,policy,Timestamp.from(from),Timestamp.from(to));return version;
    }
    private OperationAuthenticationBinding fixture(String type,long duration) {
        if(type.equals("LOCAL"))jdbc.update("UPDATE "+table("provider")+" SET status='INACTIVE' WHERE provider_type='LOCAL' AND status='ACTIVE'");
        String user=id(),provider=id(),credential=type.equals("LOCAL")?id():null,external=type.equals("LOCAL")?null:id();
        Instant issued=dbNow().truncatedTo(ChronoUnit.SECONDS).minusSeconds(1),expires=issued.plusSeconds(duration);
        String issuer=type.equals("OIDC")?"https://"+provider+".synthetic.invalid":null,subject=external==null?null:"subject-"+external;
        jdbc.update("INSERT INTO "+table("user")+"(id,username,user_type,status,failed_login_count,created_at,updated_at)"
                +" VALUES (?,?,'HUMAN','ACTIVE',0,?,?)",user,user,Timestamp.from(issued),Timestamp.from(issued));
        jdbc.update("INSERT INTO "+table("provider")+"(id,code,name,provider_type,issuer_uri,status,sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at)"
                +" VALUES (?,?,'Synthetic',?,?,'ACTIVE',false,false,?,?)",provider,provider,type,issuer,Timestamp.from(issued),Timestamp.from(issued));
        if(credential!=null)jdbc.update("INSERT INTO "+table("local_credential")+"(id,user_id,password_hash,credential_status,created_at,updated_at)"
                +" VALUES (?,?,'synthetic','ACTIVE',?,?)",credential,user,Timestamp.from(issued),Timestamp.from(issued));
        else jdbc.update("INSERT INTO "+table("external_identity")+"(id,user_id,identity_provider_id,external_subject,status,created_at,updated_at)"
                +" VALUES (?,?,?,?,'LINKED',?,?)",external,user,provider,subject,Timestamp.from(issued),Timestamp.from(issued));
        String session=id();
        jdbc.update("INSERT INTO "+table("login_session")+"(id,user_id,identity_provider_id,external_identity_id,started_at,expires_at,status,session_type)"
                +" VALUES (?,?,?,?,?,?,'ACTIVE',?)",session,user,provider,external,Timestamp.from(issued),Timestamp.from(expires),type);
        OperationAuthenticationBinding b=new OperationAuthenticationBinding(id(),(short)1,"hidra-api","hidra-api",id(),session,user,
                type,type,provider,1,external,subject,external==null?null:1L,issuer,issuer==null?null:expires.plusSeconds(1),
                credential,credential==null?null:1L,issued,expires,new byte[32]);
        var values=new LinkedHashMap<String,Object>();
        for(var component:OperationAuthenticationBinding.class.getRecordComponents())try{
            String column=component.getName().replaceAll("([a-z])([A-Z])","$1_$2").toLowerCase();
            Object value=component.getAccessor().invoke(b);
            values.put(column,value instanceof Instant instant?Timestamp.from(instant):value);
        }catch(ReflectiveOperationException e){throw new AssertionError(e);}
        String parameters=values.keySet().stream().map(c->"?::"+(c.endsWith("_at")?"timestamptz":
                c.endsWith("_version")&&!c.equals("format_version")?"bigint":c.equals("format_version")?"smallint":
                c.equals("producer_authenticator")?"bytea":"text")).collect(java.util.stream.Collectors.joining(","));
        jdbc.update("INSERT INTO "+table("operation_binding")+"("+String.join(",",values.keySet())+") VALUES ("+parameters+")",
                values.values().toArray());
        return b;
    }
    private Connection connection() throws SQLException {
        Connection c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        c.setNetworkTimeout(Runnable::run,1500);return c;
    }
    private static void sql(Connection c,String sql) throws SQLException {
        try(var p=c.createStatement()){p.execute(sql);}
    }
    private static int pid(Connection c) throws SQLException {
        try(var p=c.createStatement();var r=p.executeQuery("SELECT pg_backend_pid()")){r.next();return r.getInt(1);}
    }
    private void observeWait(int waiting,int holding) {
        long deadline=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(200);
        while(System.nanoTime()<deadline)if(Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT ? = ANY(pg_blocking_pids(?))",Boolean.class,holding,waiting)))return;
        fail("Actual writer wait was not observed");
    }
    private static void observeWait(Connection c,int waiting,int holding) {
        long deadline=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(200);
        try(var p=c.prepareStatement("SELECT ? = ANY(pg_blocking_pids(?))")) {
            p.setInt(1,holding);p.setInt(2,waiting);
            while(System.nanoTime()<deadline)try(var r=p.executeQuery()){r.next();if(r.getBoolean(1))return;}
            fail("Actual collector wait was not observed");
        }catch(SQLException e){throw new AssertionError(e);}
    }
    private static String sqlState(Throwable e) {
        for(Throwable c=e;c!=null;c=c.getCause())if(c instanceof SQLException sql)return sql.getSQLState();
        return null;
    }
    private static void await(CountDownLatch gate) {
        try{assertThat(gate.await(2,TimeUnit.SECONDS)).isTrue();}
        catch(InterruptedException e){Thread.currentThread().interrupt();throw new AssertionError(e);}
    }
    private static <T>T joined(Future<T> future) {
        try{return future.get(2,TimeUnit.SECONDS);}
        catch(Exception e){throw new AssertionError(e);}
    }
    @FunctionalInterface interface Mutation{void run(Connection c)throws SQLException;}
    private final class Writer implements AutoCloseable {
        final Connection c;final int pid;final Mutation mutation;
        final ExecutorService pool=Executors.newSingleThreadExecutor();
        final CountDownLatch start=new CountDownLatch(1),ready=new CountDownLatch(1);final Future<String> future;
        Writer(Mutation mutation)throws SQLException {
            this.mutation=mutation;c=connection();c.setAutoCommit(false);pid=pid(c);
            sql(c,"SET LOCAL lock_timeout='250ms'");sql(c,"SET LOCAL statement_timeout='750ms'");
            future=pool.submit(()->{ready.countDown();await(start);try{mutation.run(c);c.commit();return "committed";}
                catch(SQLException e){c.rollback();return e.getSQLState();}});
            await(ready);
        }
        void start(){start.countDown();}
        String result(){return joined(future);}
        void retryAndCommit()throws SQLException {
            assertThat(future.isDone()).isTrue();sql(c,"SET LOCAL lock_timeout='250ms'");
            sql(c,"SET LOCAL statement_timeout='750ms'");mutation.run(c);c.commit();
        }
        public void close()throws SQLException {
            start.countDown();pool.shutdownNow();
            try{assertThat(pool.awaitTermination(2,TimeUnit.SECONDS)).isTrue();}
            catch(InterruptedException e){Thread.currentThread().interrupt();throw new AssertionError(e);}
            finally{c.close();}
        }
    }
}
