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
    private String company,otherCompany,facility,source,device,point,unit,quality,producer,foreign,producerUser;
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
        if(properAsset)producerUser=user;
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


    // T09: only a configured synthetic rule may assess; publication is a separate grant.
    private void t09Grants() {
        for(String code:List.of("telemetry:trusted-reading:qualify","telemetry:trusted-reading:read")) {
            String action=code.endsWith("qualify")?"qualify":"read";
            String perm=permission(code,action);
            grant(producerUser,perm,"CUSTOM","telemetry-company:"+company);
            grant(producerUser,perm,"CUSTOM","telemetry-source-point:"+source+":"+point);
        }
    }
    private void t09Mapping() {
        db.update("""
                INSERT INTO hidra_telemetry_external_tag_mapping
                  (id,source_id,device_id,point_id,external_tag_name,mapping_mode,active,
                   valid_from,created_at,updated_at)
                VALUES (?,?,?,?,'GZ2.QUALIFIED','DIRECT',true,
                        now()-interval '1 hour',now(),now())
                """,id(),source,device,point);
    }
    private void t09Rule(long maxPastSeconds,String acceptedQuality) {
        String type=catalog("VALIDATION_RULE_TYPE",null);
        String config="{\"qualityCodeId\":\""+acceptedQuality+"\",\"trustLevel\":\"MEDIUM\","
                +"\"maxPastAgeSeconds\":"+maxPastSeconds+",\"maxFutureSkewSeconds\":120}";
        db.update("""
                INSERT INTO hidra_telemetry_validation_rule
                  (id,code,name,scope_type,scope_reference_id,rule_type_id,severity,
                   action_on_failure,configuration_json,active,valid_from,created_at,updated_at)
                VALUES (?,'T09_RAW_QUALITY','Explicit synthetic quality contract','POINT',?,
                        ?,'ERROR','REJECT',CAST(? AS jsonb),true,
                        now()-interval '1 hour',now(),now())
                """,id(),point,type,config);
    }
    private String t09Raw(String sequence,long secondsAgo,String qualityCode) throws Exception {
        String payload=payload(sequence,"51.25",qualityCode,unit,
                Instant.now().minusSeconds(secondsAgo).toString());
        var result=http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isCreated()).andReturn();
        return JSON.readTree(result.getResponse().getContentAsString()).path("id").asText();
    }
    private String t09Prefix(String reading) {
        return "/api/v1/telemetry/points/"+point+"/readings/"+reading;
    }
    private String t09Command() {
        return "{\"companyPartyId\":\""+company+"\"}";
    }
    @Test void governedTrustAssessmentPublishReadReplayAndRevocation() throws Exception {
        t09Grants();t09Mapping();t09Rule(3600,quality);
        String reading=t09Raw("t09-seq-a",3,quality);
        String base=t09Prefix(reading);
        var assessed=http.perform(post(base+"/assessment")
                .header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(t09Command()))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.status").value("PASSED"))
                .andExpect(jsonPath("$.trustLevel").value("MEDIUM"))
                .andExpect(jsonPath("$.assessedByActorId").value(producerUser))
                .andReturn();
        String assessment=JSON.readTree(assessed.getResponse().getContentAsString()).path("id").asText();
        http.perform(post(base+"/assessment").header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(t09Command()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(assessment))
                .andExpect(jsonPath("$.replayed").value(true));
        var first=http.perform(post(base+"/trust").header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(t09Command()))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.assessmentId").value(assessment))
                .andExpect(jsonPath("$.unitId").value(unit))
                .andExpect(jsonPath("$.pointId").value(point))
                .andExpect(jsonPath("$.rawReadingId").value(reading))
                .andExpect(jsonPath("$.reasonCode").value("QUALITY_POLICY_PASSED"))
                .andReturn();
        JsonNode trusted=JSON.readTree(first.getResponse().getContentAsString());
        String trustedId=trusted.path("id").asText(),bindingId=trusted.path("bindingId").asText();
        assertFalse(bindingId.isBlank());
        assertEquals(facility,trusted.path("assetId").asText());
        assertFalse(trusted.path("receivedAt").asText().isBlank());
        assertFalse(trusted.path("assessedAt").asText().isBlank());
        http.perform(post(base+"/trust").header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(t09Command()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(trustedId))
                .andExpect(jsonPath("$.replayed").value(true));
        assertEquals(1L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_trusted_reading WHERE reading_id=?",
                Long.class,reading));
        assertEquals(bindingId,db.queryForObject(
                "SELECT binding_id FROM hidra_telemetry_trusted_reading WHERE reading_id=?",
                String.class,reading));
        String read="/api/v1/telemetry/points/"+point+"/trusted-readings";
        http.perform(get(base+"/assessment").header("Authorization","Bearer "+producer)
                .param("companyPartyId",company)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(assessment));
        http.perform(get(read+"/"+trustedId).header("Authorization","Bearer "+producer)
                .param("companyPartyId",company)).andExpect(status().isOk())
                .andExpect(jsonPath("$.bindingId").value(bindingId));
        http.perform(get(read).header("Authorization","Bearer "+producer)
                .param("companyPartyId",company)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trustedId));
        http.perform(get(read+"/"+trustedId).header("Authorization","Bearer "+foreign)
                .param("companyPartyId",otherCompany)).andExpect(status().isForbidden());
        String readPerm=permission("telemetry:trusted-reading:read","read");
        db.update("""
                UPDATE hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now()
                WHERE user_id=? AND permission_id=? AND scope_reference_id=?
                """,producerUser,readPerm,"telemetry-source-point:"+source+":"+point);
        http.perform(get(read+"/"+trustedId).header("Authorization","Bearer "+producer)
                .param("companyPartyId",company)).andExpect(status().isForbidden());
        http.perform(get(read).header("Authorization","Bearer "+producer)
                .param("companyPartyId",company)).andExpect(status().isForbidden());
    }

    @Test void missingPolicyAndNonpassingQualityNeverPublishTrustedEvidence() throws Exception {
        t09Grants();t09Mapping();
        String unconfigured=t09Raw("t09-no-rule",3,quality);
        http.perform(post(t09Prefix(unconfigured)+"/assessment")
                .header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(t09Command()))
                .andExpect(status().isConflict());
        assertEquals(0L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_quality_assessment WHERE reading_id=?",
                Long.class,unconfigured));
        t09Rule(30,quality);
        String stale=t09Raw("t09-stale",90,quality);
        http.perform(post(t09Prefix(stale)+"/assessment")
                .header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(t09Command()))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.status").value("FAILED"))
                .andExpect(jsonPath("$.reasonCode").value("SOURCE_TIME_NOT_ELIGIBLE"));
        http.perform(post(t09Prefix(stale)+"/trust")
                .header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(t09Command()))
                .andExpect(status().isBadRequest());
        String other=catalog("QUALITY_CODE",null);
        String bad=t09Raw("t09-bad-quality",2,other);
        http.perform(post(t09Prefix(bad)+"/assessment")
                .header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(t09Command()))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.status").value("FAILED"))
                .andExpect(jsonPath("$.reasonCode").value("QUALITY_NOT_ELIGIBLE"));
        http.perform(post(t09Prefix(bad)+"/trust").header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(t09Command()))
                .andExpect(status().isBadRequest());
        assertEquals(0L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_trusted_reading WHERE reading_id IN (?,?)",
                Long.class,stale,bad));
    }

    @Test void concurrentQualifyingPublicationLocksOneRawAndOneTrustedRow() throws Exception {
        t09Grants();t09Mapping();t09Rule(300,quality);
        String reading=t09Raw("t09-concurrent",3,quality);
        String base=t09Prefix(reading);
        http.perform(post(base+"/assessment").header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(t09Command()))
                .andExpect(status().isCreated());
        try(var pool=Executors.newFixedThreadPool(2)) {
            List<Callable<Integer>> tasks=List.of(
                    ()->http.perform(post(base+"/trust").header("Authorization","Bearer "+producer)
                            .contentType(MediaType.APPLICATION_JSON).content(t09Command()))
                            .andReturn().getResponse().getStatus(),
                    ()->http.perform(post(base+"/trust").header("Authorization","Bearer "+producer)
                            .contentType(MediaType.APPLICATION_JSON).content(t09Command()))
                            .andReturn().getResponse().getStatus());
            var results=pool.invokeAll(tasks);
            for(var future:results)
                assertTrue(List.of(200,201).contains(future.get(60,TimeUnit.SECONDS)));
        }
        assertEquals(1L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_trusted_reading WHERE reading_id=?",
                Long.class,reading));
        assertEquals(1L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_quality_assessment WHERE reading_id=?",
                Long.class,reading));
    }

    @Test void nativeCommissioningCreatesScopedSourcePointMappingAndEffectiveBinding() throws Exception {
        // A source cannot become ingested by merely posting a DRAFT source or PLANNED point.
        // T08 commissions all ACTIVE prerequisites together under Topology and native Identity.
        String management=permission("telemetry:commission:manage","manage");
        grant(producerUser,management,"CUSTOM","telemetry-company:"+company);
        String sourceType=catalog("SOURCE_TYPE",null),protocol=catalog("PROTOCOL",null);
        String deviceType=catalog("DEVICE_TYPE",null),pointType=catalog("POINT_TYPE",null);
        String signalType=catalog("SIGNAL_TYPE","NUMERIC"),role=catalog("BINDING_ROLE",null);
        Instant from=Instant.now().minusSeconds(8)
                .truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        UUID request=UUID.randomUUID();
        String enroll="""
                {"requestId":"%s","companyPartyId":"%s","assetType":"FACILITY","assetId":"%s",
                 "sourceCode":"T08-S-%s","sourceNameFr":"Source GZ2",
                 "sourceTypeId":"%s","protocolId":"%s",
                 "deviceCode":"T08-D-%s","deviceNameFr":"Appareil GZ2","deviceTypeId":"%s",
                 "pointCode":"T08-P-%s","pointNameFr":"Pression GZ2",
                 "pointTypeId":"%s","signalTypeId":"%s","unitId":"%s",
                 "bindingRoleId":"%s","externalTagName":"GZ2.PRESSURE",
                 "validFrom":"%s"}
                """.formatted(request,company,facility,request,sourceType,protocol,request,
                deviceType,request,pointType,signalType,unit,role,from);
        String base="/api/v1/telemetry/commissioning";
        var created=http.perform(post(base).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(enroll))
                .andExpect(status().isOk()).andReturn();
        var initial=JSON.readTree(created.getResponse().getContentAsString());
        String newSource=initial.path("sourceId").asText(),newPoint=initial.path("pointId").asText();
        assertFalse(initial.path("bindingId").asText().isBlank());
        assertFalse(initial.path("mappingId").asText().isBlank());
        assertEquals(facility,initial.path("assetId").asText());
        http.perform(post(base).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(enroll))
                .andExpect(status().isOk()).andExpect(jsonPath("$.pointId").value(newPoint));
        assertEquals(1L,db.queryForObject("SELECT count(*) FROM hidra_telemetry_point WHERE id=?",
                Long.class,newPoint));
        for(String action:List.of("read","ingest")) {
            String p=permission("telemetry:raw-reading:"+action,action);
            grant(producerUser,p,"CUSTOM","telemetry-source-point:"+newSource+":"+newPoint);
        }
        grant(producerUser,management,"CUSTOM","telemetry-source-point:"+newSource+":"+newPoint);
        http.perform(get(base+"/points/"+newPoint+"/effective")
                .header("Authorization","Bearer "+producer)
                .param("companyPartyId",company).param("asOf",from.toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.sourceId").value(newSource));
        http.perform(get(base+"/points/"+newPoint+"/effective")
                .header("Authorization","Bearer "+foreign)
                .param("companyPartyId",company).param("asOf",from.toString()))
                .andExpect(status().isForbidden());
        String raw="""
                {"sourceId":"%s","sourceSequenceNumber":"t08-seq-a",
                 "numericValue":51.25,"qualityCodeId":"%s","unitId":"%s",
                 "sourceTimestamp":"%s"}
                """.formatted(newSource,quality,unit,Instant.now().minusSeconds(2));
        String route="/api/v1/telemetry/points/"+newPoint+"/readings";
        http.perform(post(route).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(raw)).andExpect(status().isCreated());
        http.perform(post(route).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(raw))
                .andExpect(status().isOk()).andExpect(jsonPath("$.replayed").value(true));
        http.perform(post(route).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(raw.replace(
                        "\"unitId\":\""+unit+"\"","\"unitId\":\""+id()+"\"")))
                .andExpect(status().isForbidden());
        http.perform(post(route).header("Authorization","Bearer "+foreign)
                .contentType(MediaType.APPLICATION_JSON).content(raw))
                .andExpect(status().isForbidden());
        assertEquals(0L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_trusted_reading WHERE point_id=?",
                Long.class,newPoint));
        Instant switchAt=Instant.now().plusSeconds(25)
                .truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        String newRequestId=UUID.randomUUID().toString();
        String rebinding="""
                {"requestId":"%s","companyPartyId":"%s","assetType":"FACILITY",
                 "assetId":"%s","bindingRoleId":"%s",
                 "externalTagName":"GZ2.PRESSURE.REVISED","validFrom":"%s"}
                """.formatted(newRequestId,company,facility,role,switchAt);
        http.perform(post(base+"/points/"+newPoint+"/bindings")
                .header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(rebinding))
                .andExpect(status().isOk()).andExpect(jsonPath("$.externalTagName")
                        .value("GZ2.PRESSURE.REVISED"));
        // A second overlapping publication cannot create two effective bindings.
        http.perform(post(base+"/points/"+newPoint+"/bindings")
                .header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(rebinding.replace(newRequestId,UUID.randomUUID().toString())))
                .andExpect(status().isConflict());
        assertEquals(2L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_point_binding WHERE point_id=?",
                Long.class,newPoint));
        http.perform(get(base+"/points/"+newPoint+"/effective")
                .header("Authorization","Bearer "+producer)
                .param("companyPartyId",company).param("asOf",from.toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.externalTagName")
                        .value("GZ2.PRESSURE"));
        http.perform(get(base+"/points/"+newPoint+"/effective")
                .header("Authorization","Bearer "+producer)
                .param("companyPartyId",company).param("asOf",switchAt.toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.externalTagName")
                        .value("GZ2.PRESSURE.REVISED"));
        http.perform(get(base+"/points/"+newPoint+"/history")
                .header("Authorization","Bearer "+producer)
                .param("companyPartyId",company).param("limit","10"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.items.length()").value(2));
        // No historic or aggregate count disclosure after current scoped READ revocation.
        db.update("UPDATE hidra_identity_user_permission_grant SET status='REVOKED',"
                +"revoked_at=now() WHERE user_id=? AND scope_reference_id=?",
                producerUser,"telemetry-source-point:"+newSource+":"+newPoint);
        http.perform(get(base+"/points/"+newPoint+"/history")
                .header("Authorization","Bearer "+producer)
                .param("companyPartyId",company)).andExpect(status().isForbidden());
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
    @Test void nativeTextAndBooleanReadingsPreserveTypedValuesWithoutTrustPublication() throws Exception {
        String stamp=Instant.now().minusSeconds(4)
                .truncatedTo(java.time.temporal.ChronoUnit.MICROS).toString();
        db.update("UPDATE hidra_telemetry_type_catalog SET value_shape='TEXT',updated_at=now() WHERE id=(SELECT signal_type_id FROM hidra_telemetry_point WHERE id=?)",point);
        String textual="{\"sourceId\":\""+source+"\",\"sourceSequenceNumber\":\"seq-text\","
                +"\"textValue\":\"stable\",\"unitId\":\""+unit+"\",\"qualityCodeId\":\""
                +quality+"\",\"sourceTimestamp\":\""+stamp+"\"}";
        JsonNode text=JSON.readTree(http.perform(post(route())
                .header("Authorization","Bearer "+producer).contentType(MediaType.APPLICATION_JSON)
                .content(textual)).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString());
        assertEquals("stable",text.path("textValue").asText());
        assertEquals("RECEIVED",text.path("state").asText());
        assertFalse(text.path("replayed").asBoolean());

        db.update("UPDATE hidra_telemetry_type_catalog SET value_shape='BOOLEAN',updated_at=now() WHERE id=(SELECT signal_type_id FROM hidra_telemetry_point WHERE id=?)",point);
        String booleanBody="{\"sourceId\":\""+source+"\",\"sourceSequenceNumber\":\"seq-bool\","
                +"\"booleanValue\":true,\"unitId\":\""+unit+"\",\"qualityCodeId\":\""
                +quality+"\",\"sourceTimestamp\":\""+stamp+"\"}";
        JsonNode bool=JSON.readTree(http.perform(post(route())
                .header("Authorization","Bearer "+producer).contentType(MediaType.APPLICATION_JSON)
                .content(booleanBody)).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString());
        assertTrue(bool.path("booleanValue").asBoolean());
        assertEquals("RECEIVED",bool.path("state").asText());
        assertEquals(2L,countReadings());
        assertEquals(2L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_reading_ingest_receipt WHERE point_id=?",
                Long.class,point));
        http.perform(get(route()).header("Authorization","Bearer "+producer))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2));
        assertEquals(0L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_trusted_reading WHERE point_id=?",
                Long.class,point));
    }

    @Test void protectedReadsAndInvalidNativeObservationsFailClosedWithoutAppend() throws Exception {
        String stamp=Instant.now().minusSeconds(5).toString();
        String valid=payload("negative-baseline","41.25",quality,unit,stamp);
        http.perform(get(route()).header("Authorization","Bearer "+foreign))
                .andExpect(status().isForbidden());
        http.perform(get(route()+"/latest").header("Authorization","Bearer "+foreign))
                .andExpect(status().isForbidden());
        http.perform(get("/api/v1/telemetry/points/"+point+"/trend")
                .header("Authorization","Bearer "+foreign)).andExpect(status().isForbidden());
        http.perform(get(route())).andExpect(status().isUnauthorized());
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valid.substring(0,valid.length()-1)+",\"state\":\"TRUSTED\"}"))
                .andExpect(status().isBadRequest());
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valid.substring(0,valid.length()-1)+",\"textValue\":\"extra\"}"))
                .andExpect(status().isBadRequest());
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sourceId\":\""+source+"\",\"sourceSequenceNumber\":\"empty\","
                        +"\"qualityCodeId\":\""+quality+"\",\"unitId\":\""+unit
                        +"\",\"sourceTimestamp\":\""+stamp+"\"}"))
                .andExpect(status().isBadRequest());
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload("too-old","5",quality,unit,
                        Instant.now().minusSeconds(90000).toString())))
                .andExpect(status().isBadRequest());
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload("bad-source","5",quality,unit,stamp)
                        .replace(source,id()))).andExpect(status().isForbidden());
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload("bad-quality","5",id(),unit,stamp)))
                .andExpect(status().isForbidden());
        db.update("UPDATE hidra_telemetry_type_catalog SET active=false WHERE id=?",quality);
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(valid))
                .andExpect(status().isForbidden());
        db.update("UPDATE hidra_telemetry_type_catalog SET active=true WHERE id=?",quality);
        db.update("UPDATE hidra_telemetry_point SET status='INACTIVE' WHERE id=?",point);
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(valid))
                .andExpect(status().isForbidden());
        db.update("UPDATE hidra_telemetry_point SET status='ACTIVE' WHERE id=?",point);
        db.update("UPDATE hidra_telemetry_source SET status='INACTIVE' WHERE id=?",source);
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(valid))
                .andExpect(status().isForbidden());
        db.update("UPDATE hidra_telemetry_source SET status='ACTIVE' WHERE id=?",source);
        db.update("UPDATE hidra_telemetry_point_binding SET active=false WHERE point_id=?",point);
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(valid))
                .andExpect(status().isForbidden());
        db.update("UPDATE hidra_telemetry_point_binding SET active=true WHERE point_id=?",point);
        db.update("UPDATE hidra_topology_facility SET owner_party_id=? WHERE id=?",otherCompany,facility);
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(valid))
                .andExpect(status().isForbidden());
        db.update("UPDATE hidra_topology_facility SET owner_party_id=? WHERE id=?",company,facility);
        db.update("INSERT INTO hidra_telemetry_external_tag_mapping("
                +"id,source_id,device_id,point_id,external_tag_name,mapping_mode,active,"
                +"valid_from,created_at,updated_at) "
                +"VALUES (?,?,?,?,'synthetic-alias','ALIASED',true,"
                +"now()-interval '1 day',now(),now())",id(),source,device,point);
        http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(valid))
                .andExpect(status().isForbidden());
        assertEquals(0L,countReadings());
        assertEquals(0L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_reading_ingest_receipt WHERE point_id=?",
                Long.class,point));
        assertEquals(0L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_trusted_reading WHERE point_id=?",
                Long.class,point));
    }

    @Test void concurrentConflictingSequencesCannotCommitTwoDifferentReadings() throws Exception {
        String stamp=Instant.now().minusSeconds(4).toString();
        String first=payload("seq-conflicting","51.25",quality,unit,stamp);
        String second=payload("seq-conflicting","52.75",quality,unit,stamp);
        try(var pool=Executors.newFixedThreadPool(2)) {
            Callable<Integer> writeFirst=()->http.perform(post(route())
                    .header("Authorization","Bearer "+producer)
                    .contentType(MediaType.APPLICATION_JSON).content(first))
                    .andReturn().getResponse().getStatus();
            Callable<Integer> writeSecond=()->http.perform(post(route())
                    .header("Authorization","Bearer "+producer)
                    .contentType(MediaType.APPLICATION_JSON).content(second))
                    .andReturn().getResponse().getStatus();
            var future1=pool.submit(writeFirst);
            var future2=pool.submit(writeSecond);
            int a=future1.get(30,TimeUnit.SECONDS);
            int b=future2.get(30,TimeUnit.SECONDS);
            assertTrue((a==201&&b==409)||(a==409&&b==201),
                    "Concurrent conflicting canonical observations must return 201 and 409.");
        }
        assertEquals(1L,countReadings());
        assertEquals(1L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_reading_ingest_receipt WHERE point_id=?",
                Long.class,point));
    }

    // Both real HTTP requests run in separate OS JVMs. The first process commits
    // the observation and stops; the second must independently resolve native
    // authority, persisted history, replay and revocation from the same PostgreSQL.
    // Spring's own @SpringBootTest context remains under Spring Test lifecycle control.
    @Test @Order(Integer.MAX_VALUE)
    void protectedReadingAndReplaySurviveSeparateApplicationJvmRestart() throws Exception {
        String stamp=Instant.now().minusSeconds(4)
                .truncatedTo(java.time.temporal.ChronoUnit.MICROS).toString();
        String originalBody=payload("seq-restart","41.75",quality,unit,stamp);
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
            awaitSeparateJvmReady(child,client,base,logfile);
            HttpResponse<String> first=restartedRequest(client,base,"POST",route(),originalBody);
            assertEquals(201,first.statusCode(),first.body());
            JsonNode committed=JSON.readTree(first.body());
            String reading=committed.path("id").asText();
            String receivedAt=committed.path("receivedAt").asText();
            assertFalse(reading.isBlank());
            assertFalse(receivedAt.isBlank());
            assertEquals(1L,countReadings());
            assertEquals(1L,db.queryForObject(
                    "SELECT count(*) FROM hidra_telemetry_reading_ingest_receipt WHERE reading_id=?",
                    Long.class,reading));

            // A truly independent restart, not another MockMvc or Spring context.
            long originalPid=child.pid();
            child.destroy();
            if(!child.waitFor(10,TimeUnit.SECONDS)) {
                child.destroyForcibly();
                assertTrue(child.waitFor(10,TimeUnit.SECONDS),"First OS JVM must terminate.");
            }
            assertFalse(child.isAlive());
            child=new ProcessBuilder(command).redirectErrorStream(true)
                    .redirectOutput(logfile.toFile()).start();
            assertNotEquals(originalPid,child.pid());
            assertNotEquals(ProcessHandle.current().pid(),child.pid());
            awaitSeparateJvmReady(child,client,base,logfile);

            HttpResponse<String> history=restartedRequest(client,base,"GET",route(),null);
            assertEquals(200,history.statusCode(),history.body());
            JsonNode saved=JSON.readTree(history.body()).path("content").path(0);
            assertEquals(reading,saved.path("id").asText());
            // PostgreSQL TIMESTAMPTZ retains microseconds rather than JVM nanoseconds.
            assertEquals(Instant.parse(receivedAt).truncatedTo(java.time.temporal.ChronoUnit.MICROS),
                    Instant.parse(saved.path("receivedAt").asText()));
            assertEquals(unit,saved.path("unitId").asText());
            assertEquals(Instant.parse(stamp),Instant.parse(saved.path("sourceTimestamp").asText()));
            HttpResponse<String> latest=restartedRequest(client,base,"GET",route()+"/latest",null);
            assertEquals(200,latest.statusCode(),latest.body());
            assertEquals(reading,JSON.readTree(latest.body()).path("id").asText());
            HttpResponse<String> trend=restartedRequest(client,base,"GET",
                    "/api/v1/telemetry/points/"+point+"/trend",null);
            assertEquals(200,trend.statusCode(),trend.body());
            assertEquals(reading,JSON.readTree(trend.body()).path(0).path("id").asText());

            HttpResponse<String> retry=restartedRequest(client,base,"POST",route(),originalBody);
            assertEquals(200,retry.statusCode(),retry.body());
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
            if(!child.waitFor(10,TimeUnit.SECONDS)) {
                child.destroyForcibly();
                child.waitFor(10,TimeUnit.SECONDS);
            }
            Files.deleteIfExists(logfile);
        }
    }

    private void awaitSeparateJvmReady(Process child,HttpClient client,String base,Path logfile)
            throws Exception {
        boolean started=false;
        long deadline=System.nanoTime()+TimeUnit.SECONDS.toNanos(75);
        while(System.nanoTime()<deadline&&child.isAlive()) {
            try {
                var health=client.send(HttpRequest.newBuilder(URI.create(base+"/actuator/health"))
                        .timeout(Duration.ofSeconds(2)).GET().build(),
                        HttpResponse.BodyHandlers.ofString());
                if(health.statusCode()==200){started=true;break;}
            } catch(java.io.IOException unavailable) {
                // Readiness is not inferred from a refused connection while the OS JVM starts.
            }
            Thread.sleep(350);
        }
        if(!started) {
            String diagnostics=Files.readString(logfile);
            fail("Separate JVM did not reach health (exit="
                    +(child.isAlive()?"running":child.exitValue())+"): "
                    +diagnostics.substring(Math.max(0,diagnostics.length()-3000)));
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

    @Test void t09NativeQualityTrustAndReadbackUseVerifiedPolicyAndCurrentScopedGrants() throws Exception {
        // This real HTTP/PostgreSQL fixture closes the T09 gap left by its migration-only test.
        String qualifier=permission("telemetry:trusted-reading:qualify","qualify");
        String reader=permission("telemetry:trusted-reading:read","read");
        for(String permission:List.of(qualifier,reader)) {
            grant(producerUser,permission,"CUSTOM","telemetry-company:"+company);
            grant(producerUser,permission,"CUSTOM","telemetry-source-point:"+source+":"+point);
        }
        String mapping=id();
        db.update("""
            INSERT INTO hidra_telemetry_external_tag_mapping
            (id,source_id,device_id,point_id,external_tag_name,mapping_mode,active,
             valid_from,created_at,updated_at)
            VALUES (?,?,?,?,'GZ2.SYNTHETIC.PRESSURE','DIRECT',true,now()-interval '1 day',now(),now())
            """,mapping,source,device,point);
        String rawJson=payload("t09-seq-one","51.25",quality,unit,
                Instant.now().minusSeconds(3).toString());
        var receipt=http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(rawJson))
                .andExpect(status().isCreated()).andReturn();
        String readingId=JSON.readTree(receipt.getResponse().getContentAsString()).path("id").asText();
        assertFalse(readingId.isBlank());
        String base=route()+"/"+readingId;
        String body="{\"companyPartyId\":\""+company+"\"}";
        // A configured/approved quality policy is mandatory; no implicit trust promotion.
        http.perform(post(base+"/assessment").header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isConflict());
        String ruleType=catalog("VALIDATION_RULE_TYPE",null),rule=id();
        String configuration="{\"qualityCodeId\":\""+quality
                +"\",\"trustLevel\":\"HIGH\",\"maxPastAgeSeconds\":86400,\"maxFutureSkewSeconds\":120}";
        db.update("""
            INSERT INTO hidra_telemetry_validation_rule
            (id,code,name,scope_type,scope_reference_id,rule_type_id,severity,
             action_on_failure,configuration_json,active,valid_from,created_at,updated_at)
            VALUES (?,'T09_RAW_QUALITY','Synthetic GZ2 policy','POINT',?,?,'ERROR',
                    'REJECT',CAST(? AS jsonb),true,now()-interval '1 day',now(),now())
            """,rule,point,ruleType,configuration);
        var qualified=http.perform(post(base+"/assessment").header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PASSED"))
                .andExpect(jsonPath("$.trustLevel").value("HIGH"))
                .andReturn();
        String assessedId=JSON.readTree(qualified.getResponse().getContentAsString()).path("id").asText();
        assertFalse(assessedId.isBlank());
        http.perform(post(base+"/assessment").header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(assessedId))
                .andExpect(jsonPath("$.replayed").value(true));
        var published=http.perform(post(base+"/trust").header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rawReadingId").value(readingId))
                .andExpect(jsonPath("$.assessmentId").value(assessedId))
                .andExpect(jsonPath("$.bindingId").isNotEmpty())
                .andExpect(jsonPath("$.unitId").value(unit))
                .andReturn();
        String trusted=JSON.readTree(published.getResponse().getContentAsString()).path("id").asText();
        http.perform(post(base+"/trust").header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(trusted))
                .andExpect(jsonPath("$.replayed").value(true));
        assertEquals(1L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_trusted_reading WHERE reading_id=?",
                Long.class,readingId));
        String trustedPath="/api/v1/telemetry/points/"+point+"/trusted-readings";
        http.perform(get(trustedPath+"/"+trusted).header("Authorization","Bearer "+producer)
                .param("companyPartyId",company))
                .andExpect(status().isOk()).andExpect(jsonPath("$.rawReadingId").value(readingId));
        http.perform(get(trustedPath).header("Authorization","Bearer "+producer)
                .param("companyPartyId",company).param("limit","10"))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(trusted));
        // An authorized but bad-quality raw receipt must never produce trusted evidence.
        String otherQuality=catalog("QUALITY_CODE",null);
        var bad=http.perform(post(route()).header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload("t09-seq-bad","50.00",otherQuality,unit,
                        Instant.now().minusSeconds(2).toString())))
                .andExpect(status().isCreated()).andReturn();
        String badId=JSON.readTree(bad.getResponse().getContentAsString()).path("id").asText();
        http.perform(post(route()+"/"+badId+"/assessment").header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("FAILED"));
        http.perform(post(route()+"/"+badId+"/trust").header("Authorization","Bearer "+producer)
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());
        assertEquals(0L,db.queryForObject(
                "SELECT count(*) FROM hidra_telemetry_trusted_reading WHERE reading_id=?",
                Long.class,badId));
        http.perform(get(trustedPath+"/"+trusted).header("Authorization","Bearer "+foreign)
                .param("companyPartyId",company)).andExpect(status().isForbidden());
        // No protected existence, history or values after exact source-point read revocation.
        db.update("""
            UPDATE hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now()
            WHERE user_id=? AND permission_id=? AND scope_reference_id=?
            """,producerUser,reader,"telemetry-source-point:"+source+":"+point);
        http.perform(get(trustedPath+"/"+trusted).header("Authorization","Bearer "+producer)
                .param("companyPartyId",company)).andExpect(status().isForbidden());
        http.perform(get(trustedPath).header("Authorization","Bearer "+producer)
                .param("companyPartyId",company)).andExpect(status().isForbidden());
    }
}
