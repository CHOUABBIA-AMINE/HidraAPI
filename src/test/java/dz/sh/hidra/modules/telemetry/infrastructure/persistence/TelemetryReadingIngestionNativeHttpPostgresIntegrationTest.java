/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingIngestionNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence
 *
 * @Description : Qualifies JWT-backed raw observation write, native grants and PostgreSQL replay uniqueness.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers(disabledWithoutDocker=true)
@SpringBootTest(classes=HidraApplication.class,webEnvironment=SpringBootTest.WebEnvironment.MOCK,
    properties={
        "hidra.platform.security.jwt.hmac-secret=synthetic-hapi933-secure-local-native-key-32-bytes",
        "hidra.platform.security.jwt.token-issuer=https://identity.hidra.invalid/telemetry",
        "hidra.platform.security.jwt.audience=hidra-api"
    })
@ActiveProfiles("test")
@org.springframework.test.annotation.DirtiesContext(
        classMode=org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS)
class TelemetryReadingIngestionNativeHttpPostgresIntegrationTest {
    private static final String PASSWORD="synthetic-hapi933-local-secret";
    private static final ObjectMapper JSON=new ObjectMapper();
    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("hidra_telemetry_hapi933");
    @DynamicPropertySource
    static void datasource(DynamicPropertyRegistry registry) {
        if(!PG.isRunning())PG.start();
        Flyway.configure().dataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword()).load().migrate();
        registry.add("spring.datasource.url",PG::getJdbcUrl);
        registry.add("spring.datasource.username",PG::getUsername);
        registry.add("spring.datasource.password",PG::getPassword);
        registry.add("spring.flyway.url",PG::getJdbcUrl);
        registry.add("spring.flyway.user",PG::getUsername);
        registry.add("spring.flyway.password",PG::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto",()->"validate");
    }
    @Autowired WebApplicationContext context;
    @Autowired AuthenticateDirectUserUseCase authentication;
    @Autowired PasswordEncoder passwords;
    private JdbcTemplate db;
    private MockMvc http;
    private String company,otherCompany,facility,source,device,point,unit,quality,producer,foreign;
    private static String id() { return UUID.randomUUID().toString(); }
    private String route() { return "/api/v1/telemetry/points/"+point+"/readings"; }
    @BeforeEach void fixture() {
        SecurityContextHolder.clearContext();
        db=new JdbcTemplate(new DriverManagerDataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword()));
        http=MockMvcBuilders.webAppContextSetup(context)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                .build();
        String partyType=id(),facilityType=id();
        company=id();otherCompany=id();facility=id();
        db.update("INSERT INTO hidra_party_type(id,code,status,created_at,updated_at) VALUES (?,?,'ACTIVE',now(),now())",
                partyType,partyType);
        for(var c:List.of(company,otherCompany))
            db.update("INSERT INTO hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES (?,?,?,'Synthetic gas authority','DZ','ACTIVE',now(),now())",
                    c,c,partyType);
        db.update("INSERT INTO hidra_topology_facility_type(id,code,name,facility_kind,status,created_at,updated_at) VALUES (?,?,'Synthetic gas station','COMPRESSOR_STATION','ACTIVE',now(),now())",
                facilityType,facilityType);
        db.update("INSERT INTO hidra_topology_facility(id,code,name_fr,facility_type_id,facility_kind,owner_party_id,status,created_at,updated_at) VALUES (?,?,?,?,'COMPRESSOR_STATION',?,'ACTIVE',now(),now())",
                facility,facility,"Synthetic GZ2",facilityType,company);
        String sourceType=catalog("SOURCE_TYPE",null), protocol=catalog("PROTOCOL",null);
        String deviceType=catalog("DEVICE_TYPE",null),pointType=catalog("POINT_TYPE",null);
        String signal=catalog("SIGNAL_TYPE","NUMERIC");
        quality=catalog("QUALITY_CODE",null);
        String bindingRole=catalog("BINDING_ROLE",null);
        unit=id();source=id();device=id();point=id();
        db.update("INSERT INTO hidra_telemetry_unit(id,code,symbol,dimension,active,system_defined,created_at,updated_at) VALUES (?,?,?,'PRESSURE',true,false,now(),now())",
                unit,unit,"bar");
        db.update("INSERT INTO hidra_telemetry_source(id,code,name_fr,source_type_id,protocol_id,status,created_at,updated_at) VALUES (?,?,?, ?,?,'ACTIVE',now(),now())",
                source,source,"Synthetic source",sourceType,protocol);
        db.update("INSERT INTO hidra_telemetry_device(id,source_id,code,name_fr,device_type_id,status,created_at,updated_at) VALUES (?,?,?, ?,?,'ACTIVE',now(),now())",
                device,source,device,"Synthetic device",deviceType);
        db.update("INSERT INTO hidra_telemetry_point(id,device_id,code,name_fr,point_type_id,signal_type_id,unit_id,status,created_at,updated_at) VALUES (?,?,?, ?,?,?,?,'ACTIVE',now(),now())",
                point,device,point,"Synthetic pressure",pointType,signal,unit);
        db.update("INSERT INTO hidra_telemetry_point_binding(id,point_id,topology_asset_type_code,topology_asset_id,topology_asset_code,binding_role_id,active,valid_from,created_at,updated_at) VALUES (?,?,?,?,?,?,true,now()-interval '1 day',now(),now())",
                id(),point,"FACILITY",facility,facility,bindingRole);
        db.update("UPDATE hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        String provider=id();
        db.update("INSERT INTO hidra_identity_provider(id,code,name,provider_type,status,sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at) VALUES (?,?,'Synthetic HAPI933 provider','LOCAL','ACTIVE',false,false,now(),now())",
                provider,provider);
        producer=actor(company,true);
        foreign=actor(otherCompany,false);
    }
    private String catalog(String name,String shape) {
        String key=id();
        db.update("INSERT INTO hidra_telemetry_type_catalog(id,catalog_name,code,active,value_shape,numeric_unit_exempt,sort_order,system_defined,created_at,updated_at) VALUES (?,?,?,true,?,false,0,false,now(),now())",
                key,name,key,shape);
        return key;
    }
    private String permission(String code,String action) {
        List<String> ids=db.queryForList("SELECT id FROM hidra_identity_permission WHERE code=?",String.class,code);
        if(!ids.isEmpty())return ids.getFirst();
        String permission=id();
        db.update("INSERT INTO hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES (?,?,'telemetry','TELEMETRY_READING',?,false,'ACTIVE',now(),now())",
                permission,code,action);
        return permission;
    }
    private void grant(String actor,String permission,String scope,String target) {
        db.update("INSERT INTO hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES (?,?,?,'GRANT',?,?,'synthetic HAPI933',false,now()-interval '1 hour',now()+interval '2 hours','ACTIVE',now())",
                id(),actor,permission,scope,target);
    }
    private String actor(String party,boolean properAsset) {
        String user=id();
        db.update("INSERT INTO hidra_identity_user(id,username,display_name,user_type,status,failed_login_count,created_at,updated_at,activated_at) VALUES (?,?,'Synthetic telemetry producer','HUMAN','ACTIVE',0,now(),now(),now())",
                user,user);
        db.update("INSERT INTO hidra_identity_local_credential(id,user_id,password_hash,credential_status,created_at,updated_at) VALUES (?,?,?,'ACTIVE',now(),now())",
                id(),user,passwords.encode(PASSWORD));
        for(String code:List.of("telemetry:points:execute","telemetry:points:read")){
            grant(user,permission(code,code.endsWith("read")?"read":"execute"),"GLOBAL",null);
        }
        for(String code:List.of("telemetry:raw-reading:ingest","telemetry:raw-reading:read")){
            String perm=permission(code,code.endsWith("read")?"read":"ingest");
            grant(user,perm,"CUSTOM","telemetry-company:"+party);
            grant(user,perm,"CUSTOM","telemetry-source-point:"+source+":"+(properAsset?point:id()));
        }
        String jwt=authentication.authenticate(new DirectAuthenticationCommand(
                ProviderType.LOCAL,user,PASSWORD,"synthetic-ip","synthetic-hapi933","synthetic-login")).accessToken();
        SecurityContextHolder.clearContext();
        return jwt;
    }
    private String payload(String seq,String number,String q,String u,String time) {
        return "{\"sourceId\":\""+source+"\",\"sourceSequenceNumber\":\""+seq+"\","
                +"\"numericValue\":"+number+",\"qualityCodeId\":\""+q+"\","
                +"\"unitId\":\""+u+"\",\"sourceTimestamp\":\""+time+"\"}";
    }
    @Test void localJwtNativeScopedWriteReplayAndProtectedReadbackArePostgresDurable() throws Exception {
        var stamp=Instant.now().minusSeconds(4).toString();
        String json=payload("seq-a","51.25",quality,unit,stamp);
        var result=http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated()).andReturn();
        JsonNode first=JSON.readTree(result.getResponse().getContentAsString());
        String reading=first.path("id").asText();
        assertFalse(reading.isBlank());
        assertEquals("RECEIVED",first.path("state").asText());
        assertEquals(1L,countReadings());
        assertEquals(1L,db.queryForObject("SELECT count(*) FROM hidra_telemetry_reading_ingest_receipt WHERE reading_id=?",Long.class,reading));
        http.perform(get(route()).header("Authorization","Bearer "+producer))
                .andExpect(status().isOk()).andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].id").value(reading))
                .andExpect(jsonPath("$.content[0].unitId").value(unit));
        http.perform(get(route()+"/latest").header("Authorization","Bearer "+producer))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(reading));
        http.perform(get("/api/v1/telemetry/points/"+point+"/trend").header("Authorization","Bearer "+producer))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(reading));
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(reading))
                .andExpect(jsonPath("$.replayed").value(true));
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload("seq-a","52.25",quality,unit,stamp))).andExpect(status().isConflict());
        assertEquals(1L,countReadings());
        http.perform(get(route()).header("Authorization","Bearer "+foreign)).andExpect(status().isForbidden());
        http.perform(post(route()).header("Authorization","Bearer "+foreign)
                .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isForbidden());
        http.perform(post(route()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(payload("bad-unit","5",quality,id(),stamp)))
                .andExpect(status().isForbidden());
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(payload("bad-time","5",quality,unit,
                        Instant.now().plusSeconds(7200).toString()))).andExpect(status().isBadRequest());
        assertEquals(1L,countReadings());
        assertEquals(0L,db.queryForObject("SELECT count(*) FROM hidra_telemetry_trusted_reading WHERE point_id=?",Long.class,point));
        db.update("UPDATE hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now() WHERE scope_reference_id=?",
                "telemetry-source-point:"+source+":"+point);
        http.perform(get(route()).header("Authorization","Bearer "+producer))
                .andExpect(status().isForbidden());
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());
        assertEquals(1L,countReadings());
    }
    @Test void concurrentDuplicateSequencesCannotAppendTwoRawReadings() throws Exception {
        String json=payload("seq-parallel","51.25",quality,unit,Instant.now().minusSeconds(4).toString());
        try(var pool=Executors.newFixedThreadPool(2)) {
            Callable<Integer> invoke=()->http.perform(post(route())
                    .header("Authorization","Bearer "+producer)
                    .contentType(MediaType.APPLICATION_JSON).content(json))
                    .andReturn().getResponse().getStatus();
            var futures=pool.invokeAll(List.of(invoke,invoke));
            for(var future:futures)assertTrue(List.of(200,201).contains(future.get(30,TimeUnit.SECONDS)));
        }
        assertEquals(1L,countReadings());
        assertEquals(1L,db.queryForObject("SELECT count(*) FROM hidra_telemetry_reading_ingest_receipt WHERE point_id=?",Long.class,point));
    }
    // Same HAPI-933 acceptance: close the original application context, then require
    // a NEW OS JVM (not a second MockMvc or JDBC connection) to serve the original
    // authorized history, latest, trend and replay from the same committed PostgreSQL.
    @Test @Order(Integer.MAX_VALUE)
    void protectedReadingAndReplaySurviveSeparateApplicationJvmRestart() throws Exception {
        String stamp=Instant.now().minusSeconds(4).toString();
        String originalBody=payload("seq-restart","41.75",quality,unit,stamp);
        JsonNode committed=JSON.readTree(http.perform(post(route())
                .header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(originalBody))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString());
        String reading=committed.path("id").asText();
        String receivedAt=committed.path("receivedAt").asText();
        assertFalse(reading.isBlank());
        assertFalse(receivedAt.isBlank());
        assertEquals(1L,countReadings());

        ((org.springframework.context.ConfigurableApplicationContext)context).close();
        int port;
        try(ServerSocket socket=new ServerSocket(0,0,InetAddress.getLoopbackAddress())) {
            port=socket.getLocalPort();
        }
        String classpath=System.getProperty("surefire.test.class.path");
        if(classpath==null||classpath.isBlank())classpath=System.getProperty("java.class.path");
        assertNotNull(classpath,"A child JVM requires the current test runtime classpath.");
        Path logfile=Files.createTempFile("hapi933-restarted-jvm-", ".log");
        var command=new ArrayList<>(List.of(
                Path.of(System.getProperty("java.home"),"bin","java").toString(),
                "-cp",classpath,RestartedRuntime.class.getName(),
                "--spring.profiles.active=test",
                "--server.address=127.0.0.1",
                "--server.port="+port,
                "--management.server.port="+port,
                "--spring.datasource.url="+PG.getJdbcUrl(),
                "--spring.datasource.username="+PG.getUsername(),
                "--spring.datasource.password="+PG.getPassword(),
                "--spring.flyway.url="+PG.getJdbcUrl(),
                "--spring.flyway.user="+PG.getUsername(),
                "--spring.flyway.password="+PG.getPassword(),
                "--spring.jpa.hibernate.ddl-auto=validate",
                "--hidra.platform.security.jwt.hmac-secret=synthetic-hapi933-secure-local-native-key-32-bytes",
                "--hidra.platform.security.jwt.token-issuer=https://identity.hidra.invalid/telemetry",
                "--hidra.platform.security.jwt.audience=hidra-api"));
        Process child=new ProcessBuilder(command).redirectErrorStream(true)
                .redirectOutput(logfile.toFile()).start();
        try {
            assertNotEquals(ProcessHandle.current().pid(),child.pid());
            var client=HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
            String base="http://127.0.0.1:"+port;
            boolean started=false;
            long deadline=System.nanoTime()+TimeUnit.SECONDS.toNanos(75);
            while(System.nanoTime()<deadline&&child.isAlive()) {
                try {
                    var health=client.send(HttpRequest.newBuilder(URI.create(base+"/actuator/health"))
                            .timeout(Duration.ofSeconds(2)).GET().build(),
                            HttpResponse.BodyHandlers.ofString());
                    if(health.statusCode()==200){started=true;break;}
                } catch(java.io.IOException unavailable) {
                    // Another process is still starting. No HTTP result is inferred.
                }
                Thread.sleep(350);
            }
            if(!started) {
                String diagnostics=Files.readString(logfile);
                fail("Separate JVM did not reach health (exit="
                        +(child.isAlive()?"running":child.exitValue())+"): "
                        +diagnostics.substring(Math.max(0,diagnostics.length()-3000)));
            }
            HttpResponse<String> history=restartedRequest(client,base,"GET",route(),null);
            assertEquals(200,history.statusCode());
            JsonNode saved=JSON.readTree(history.body()).path("content").path(0);
            assertEquals(reading,saved.path("id").asText());
            assertEquals(receivedAt,saved.path("receivedAt").asText());
            assertEquals(unit,saved.path("unitId").asText());
            assertEquals(stamp,saved.path("sourceTimestamp").asText());
            HttpResponse<String> latest=restartedRequest(client,base,"GET",route()+"/latest",null);
            assertEquals(200,latest.statusCode());
            assertEquals(reading,JSON.readTree(latest.body()).path("id").asText());
            HttpResponse<String> trend=restartedRequest(client,base,"GET",
                    "/api/v1/telemetry/points/"+point+"/trend",null);
            assertEquals(200,trend.statusCode());
            assertEquals(reading,JSON.readTree(trend.body()).path(0).path("id").asText());

            HttpResponse<String> retry=restartedRequest(client,base,"POST",route(),originalBody);
            assertEquals(200,retry.statusCode());
            assertEquals(reading,JSON.readTree(retry.body()).path("id").asText());
            assertTrue(JSON.readTree(retry.body()).path("replayed").asBoolean());
            assertEquals(409,restartedRequest(client,base,"POST",route(),
                    payload("seq-restart","43.75",quality,unit,stamp)).statusCode());
            assertEquals(1L,countReadings());
            assertEquals(1L,db.queryForObject(
                    "SELECT count(*) FROM hidra_telemetry_reading_ingest_receipt WHERE reading_id=?",
                    Long.class,reading));

            db.update("UPDATE hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now() WHERE scope_reference_id=?",
                    "telemetry-source-point:"+source+":"+point);
            assertEquals(403,restartedRequest(client,base,"GET",route(),null).statusCode());
            assertEquals(403,restartedRequest(client,base,"POST",route(),originalBody).statusCode());
            assertEquals(1L,countReadings());
        } finally {
            child.destroy();
            if(!child.waitFor(10,TimeUnit.SECONDS))child.destroyForcibly();
            Files.deleteIfExists(logfile);
        }
    }

    private HttpResponse<String> restartedRequest(HttpClient client,String base,String method,
                                                   String path,String body) throws Exception {
        var request=HttpRequest.newBuilder(URI.create(base+path)).timeout(Duration.ofSeconds(10))
                .header("Authorization","Bearer "+producer);
        if("POST".equals(method))request.header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body));
        else request.GET();
        return client.send(request.build(),HttpResponse.BodyHandlers.ofString());
    }

    public static final class RestartedRuntime {
        private RestartedRuntime() { }
        public static void main(String[] args) {
            org.springframework.boot.SpringApplication.run(HidraApplication.class,args);
        }
    }
    private long countReadings() {
        return db.queryForObject("SELECT count(*) FROM hidra_telemetry_reading WHERE point_id=?",Long.class,point);
    }
}
