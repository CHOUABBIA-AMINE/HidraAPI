/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationAdministrationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence
 *
 * @Description : Verifies the whole typed configuration foundation with synthetic evidence.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.configuration.application.port.in.ConfigurationAdministrationUseCase;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
@Testcontainers(disabledWithoutDocker=true)
@SpringBootTest(classes=HidraApplication.class,webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class ConfigurationAdministrationPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16").withDatabaseName("configuration_foundation");
    @DynamicPropertySource static void properties(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url",PG::getJdbcUrl);r.add("spring.datasource.username",PG::getUsername);r.add("spring.datasource.password",PG::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto",()->"validate");r.add("spring.flyway.enabled",()->"true");
        r.add("hidra.platform.security.jwt.token-issuer",()->"https://configuration.synthetic.invalid");
    }
    @Autowired JdbcTemplate jdbc;
    @Autowired PasswordEncoder passwords;
    @Autowired WebApplicationContext context;
    @Autowired PlatformTransactionManager manager;
    @Autowired ConfigurationAdministrationUseCase service;
    @Autowired dz.sh.hidra.modules.configuration.application.port.out.ConfigurationAdministrationPersistencePort store;
    @Autowired dz.sh.hidra.modules.configuration.application.port.out.ConfigurationAdministrationAuthorizationPort authority;
    private MockMvc http;
    private String admin,ordinary,namespace,adminUser;
    private static final String BASE="/api/v1/configuration",PASSWORD="synthetic-configuration-foundation-password";
    private String id(){return UUID.randomUUID().toString();}
    @BeforeEach void fixture() throws Exception {
        http=MockMvcBuilders.webAppContextSetup(context).apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity()).build();
        jdbc.update("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        String provider=id();jdbc.update("INSERT INTO public.hidra_identity_provider(id,code,name,provider_type,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at) VALUES (?,?,'Synthetic','LOCAL',false,false,'ACTIVE',now(),now())",provider,provider);
        admin=loginFixture(true);ordinary=loginFixture(false);namespace=id();
        adminUser=context.getBean("hidraJwtDecoder",org.springframework.security.oauth2.jwt.JwtDecoder.class).decode(admin).getSubject();
        jdbc.update("INSERT INTO public.hidra_configuration_namespace(id,code,name_fr,status,created_at,updated_at) VALUES (?,?,'Synthetic','ACTIVE',now(),now())",namespace,namespace);
    }
    private String loginFixture(boolean administrator) throws Exception {
        String user=id();jdbc.update("INSERT INTO public.hidra_identity_user(id,username,user_type,status,failed_login_count,created_at,updated_at) VALUES (?,?,'HUMAN','ACTIVE',0,now(),now())",user,user);
        jdbc.update("INSERT INTO public.hidra_identity_local_credential(id,user_id,password_hash,credential_status,created_at,updated_at) VALUES (?,?,?,'ACTIVE',now(),now())",id(),user,passwords.encode(PASSWORD));
        if(administrator) {
            var ids=jdbc.queryForList("SELECT id FROM public.hidra_identity_role WHERE code='HIDRA_ADMIN'",String.class);String role;
            if(ids.isEmpty()){role=id();jdbc.update("INSERT INTO public.hidra_identity_role(id,code,role_type,status,created_at,updated_at) VALUES (?,'HIDRA_ADMIN','ADMIN','ACTIVE',now(),now())",role);}else role=ids.getFirst();
            jdbc.update("INSERT INTO public.hidra_identity_user_role_grant(id,user_id,role_id,scope_type,valid_from,status,created_at) VALUES (?,?,?,'GLOBAL',now()-interval '1 hour','ACTIVE',now())",id(),user,role);
        }
        return value(http.perform(post("/api/v1/identity/authentication/login").contentType("application/json").content(json("providerType","LOCAL","principal",user,"credentials",PASSWORD))).andExpect(status().isOk()),"accessToken");
    }
    private ResultActions call(String method,String path,String token,String body) throws Exception {
        var r=request(org.springframework.http.HttpMethod.valueOf(method),path);if(token!=null)r.header("Authorization","Bearer "+token);if(body!=null)r.contentType("application/json").content(body);return http.perform(r);
    }
    private String value(ResultActions r,String field) throws Exception {return com.jayway.jsonpath.JsonPath.read(r.andReturn().getResponse().getContentAsString(),"$."+field);}
    private String json(Object... pairs) throws Exception {var map=new java.util.LinkedHashMap<String,Object>();for(int i=0;i<pairs.length;i+=2)map.put((String)pairs[i],pairs[i+1]);return tools.jackson.databind.json.JsonMapper.builder().build().writeValueAsString(map);}

    /**
     * Retain the exact HTTP 200 oracle while preserving the resolved server exception
     * in failing synthetic CI. Never log a request body, bearer or protected value.
     */
    private ResultActions expectedOk(ResultActions result,String operation) throws Exception {
        var mvc=result.andReturn();
        int actual=mvc.getResponse().getStatus();
        if(actual!=200) {
            var caught=mvc.getResolvedException();
            Throwable root=caught;
            for(int depth=0;root!=null&&root.getCause()!=null&&depth<8;depth++)root=root.getCause();
            throw new AssertionError("HAPI-927 "+operation+" expected HTTP 200 but received "+actual
                +"; resolvedType="+(caught==null?"none":caught.getClass().getName())
                +"; rootType="+(root==null?"none":root.getClass().getName())
                +"; rootMessage="+(root==null?"none":String.valueOf(root.getMessage())),caught);
        }
        return result.andExpect(status().isOk());
    }

    private String definition(String type,String sensitivity,Object defaultValue,boolean approval) throws Exception {
        return value(expectedOk(call("POST",BASE+"/definitions",admin,json("namespaceId",namespace,"key","runtime."+id(),"displayNameFr","Synthetic setting","valueType",type,"sensitivity",sensitivity,"scoped",false,"defaultValue",defaultValue,"requiresApproval",approval)),"create-definition"),"id");
    }
    private String version(String definition) throws Exception {return value(call("GET",BASE+"/definitions/"+definition,admin,null).andExpect(status().isOk()),"updatedAt");}
    private String activate(String definition) throws Exception {return value(call("POST",BASE+"/definitions/"+definition+"/activate",admin,json("expectedUpdatedAt",version(definition))).andExpect(status().isOk()),"activeVersionId");}
    private String override(String definition,String version,String raw,String from,String to,String actor) throws Exception {
        return json("definitionId",definition,"definitionVersionId",version,"environment","TEST","rawValue",raw,"effectiveFrom",from,"effectiveTo",to,"createdByActorId",actor);
    }
    private static final String FROM="2026-11-04T00:00:00Z",TO="2026-11-05T00:00:00Z";
    private String effective(String d,String instant) {return BASE+"/effective?definitionId="+d+"&environment=TEST&instant="+instant;}
    @Test void nativeDefinitionActivationTypedOverrideAndExactHalfOpenReload() throws Exception {
        call("GET",BASE+"/namespaces",admin,null).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString(namespace)));
        String d=definition("NUMBER","INTERNAL","12.500",false),v=activate(d);
        call("GET",effective(d,FROM),admin,null).andExpect(status().isOk()).andExpect(jsonPath("$.value").value("12.5")).andExpect(jsonPath("$.provenance").value("DEFAULT"));
        call("POST",BASE+"/values",admin,override(d,v,"42.250",FROM,TO,null)).andExpect(status().isOk());
        call("GET",effective(d,FROM),admin,null).andExpect(status().isOk()).andExpect(jsonPath("$.value").value("42.25")).andExpect(jsonPath("$.definitionVersionId").value(v));
        call("GET",effective(d,TO),admin,null).andExpect(status().isOk()).andExpect(jsonPath("$.value").value("12.5"));
        call("GET",BASE+"/values?definitionId="+d+"&environment=TEST",admin,null).andExpect(status().isOk()).andExpect(jsonPath("$[0].actorId").value(adminUser));
        call("GET",BASE+"/definitions?namespaceId="+namespace,admin,null).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString(d)));
    }
    @Test void nativeBooleanFlagActivationAndEnvironmentState() throws Exception {
        String f=value(expectedOk(call("POST",BASE+"/feature-flags",admin,json("code","F-"+id(),"nameFr","Synthetic","owningModule","configuration","evaluationStrategy","BOOLEAN","defaultEnabled",false)),"create-boolean-flag"),"id");
        var row=jdbc.queryForMap("SELECT updated_at FROM public.hidra_configuration_feature_flag WHERE id=?",f);String expected=((java.sql.Timestamp)row.get("updated_at")).toInstant().toString();
        String active=value(call("POST",BASE+"/feature-flags/"+f+"/activate",admin,json("expectedUpdatedAt",expected)).andExpect(status().isOk()),"updatedAt");
        call("GET",BASE+"/feature-flags/"+f+"/state?environment=TEST",admin,null).andExpect(status().isOk()).andExpect(jsonPath("$.enabled").value(false));
        String body=json("environment","TEST","enabled",true,"expectedUpdatedAt",active);
        call("PUT",BASE+"/feature-flags/"+f+"/state",admin,body).andExpect(status().isOk()).andExpect(jsonPath("$.enabled").value(true));
        call("PUT",BASE+"/feature-flags/"+f+"/state",admin,body).andExpect(status().isConflict());
        call("GET",BASE+"/feature-flags/"+f+"/state?environment=OTHER",admin,null).andExpect(status().isOk()).andExpect(jsonPath("$.enabled").value(false));
        assertEquals("HAPI-927:STATE:"+adminUser,jdbc.queryForObject("SELECT rule_name FROM public.hidra_configuration_feature_flag_rule WHERE feature_flag_id=? AND active",String.class,f));
    }
    @Test void unsupportedTypesApprovalScopesSecretsAndStrategiesRejectWithoutRows() throws Exception {
        for(String type:java.util.List.of("JSON","DATE","REFERENCE","LIST","DURATION"))
            call("POST",BASE+"/definitions",admin,json("namespaceId",namespace,"key","r."+id(),"displayNameFr","Synthetic","valueType",type,"sensitivity","INTERNAL","scoped",false,"requiresApproval",false)).andExpect(status().is4xxClientError());
        call("POST",BASE+"/definitions",admin,json("namespaceId",namespace,"key","r."+id(),"displayNameFr","Synthetic","valueType","STRING","sensitivity","INTERNAL","scoped",false,"requiresApproval",true)).andExpect(status().is4xxClientError());
        for(String secret:java.util.List.of("password=x","{\"token\":\"x\"}","Bearer abc.def.ghi"))
            call("POST",BASE+"/definitions",admin,json("namespaceId",namespace,"key","r."+id(),"displayNameFr","Synthetic","valueType","STRING","sensitivity","INTERNAL","scoped",false,"requiresApproval",false,"defaultValue",secret)).andExpect(status().is4xxClientError());
        call("POST",BASE+"/feature-flags",admin,json("code","F-"+id(),"nameFr","Synthetic","owningModule","configuration","evaluationStrategy","PERCENTAGE","defaultEnabled",false)).andExpect(status().is4xxClientError());
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM public.hidra_configuration_definition WHERE namespace_id=?",Integer.class,namespace));
    }
    @Test void staleWrongTypesWindowsVersionAndImpersonationNeverPersist() throws Exception {
        String d=definition("BOOLEAN","INTERNAL","false",false),v=activate(d);
        for(String body:java.util.List.of(override(d,id(),"true",FROM,TO,null),override(d,v,"TRUE",FROM,TO,null),override(d,v,"true",FROM,FROM,null),override(d,v,"true",FROM,TO,id()),json("definitionId",d,"definitionVersionId",v,"environment","TEST","rawValue","true","jsonValue","{\"token\":\"x\"}","effectiveFrom",FROM)))
            call("POST",BASE+"/values",admin,body).andExpect(status().is4xxClientError());
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM public.hidra_configuration_value WHERE definition_id=?",Integer.class,d));
        call("POST",BASE+"/definitions/"+d+"/activate",admin,json("expectedUpdatedAt",version(d))).andExpect(status().isConflict());
    }
    @Test void overlappingOverridesRejectWhileAdjacentWindowsSucceed() throws Exception {
        String d=definition("STRING","INTERNAL","default",false),v=activate(d);
        call("POST",BASE+"/values",admin,override(d,v,"first",FROM,TO,null)).andExpect(status().isOk());
        call("POST",BASE+"/values",admin,override(d,v,"overlap",FROM,null,null)).andExpect(status().isConflict());
        call("POST",BASE+"/values",admin,override(d,v,"second",TO,null,null)).andExpect(status().isOk());
        call("GET",effective(d,TO),admin,null).andExpect(status().isOk()).andExpect(jsonPath("$.value").value("second"));
        assertThrows(org.springframework.dao.DataAccessException.class,()->jdbc.update("INSERT INTO public.hidra_configuration_value(id,definition_id,definition_version_id,environment,raw_value,status,effective_from,created_by_actor_id,created_at,updated_at) VALUES (?,?,?,'TEST','forbidden','ACTIVE',?::timestamptz,?,now(),now())",id(),d,v,FROM,adminUser));
    }
    @Test void sensitiveLegacyDefaultsAndValuesAreRedactedBeforeSerialization() throws Exception {
        String d=definition("STRING","SECRET_REFERENCE_ONLY",null,false);
        jdbc.update("UPDATE public.hidra_configuration_definition SET default_value='password=synthetic-only' WHERE id=?",d);
        jdbc.update("INSERT INTO public.hidra_configuration_value(id,definition_id,environment,raw_value,status,created_at,updated_at) VALUES (?,?,'TEST','token=synthetic-only','ACTIVE',now(),now())",id(),d);
        for(String path:java.util.List.of(BASE+"/definitions/"+d,BASE+"/definitions?namespaceId="+namespace,BASE+"/values?definitionId="+d+"&environment=TEST",effective(d,FROM)))
            call("GET",path,admin,null).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("synthetic-only"))));
        call("POST",BASE+"/definitions/"+d+"/activate",admin,json("expectedUpdatedAt",version(d))).andExpect(status().is4xxClientError());
    }
    @Test void currentAdministratorRevocationDeniesKnownUnknownCountsAndWrites() throws Exception {
        String d=definition("STRING","INTERNAL","default",false);String before=version(d);
        jdbc.update("UPDATE public.hidra_identity_user_role_grant SET status='REVOKED' WHERE user_id=?",adminUser);
        for(String token:java.util.List.of(admin,ordinary)) {
            for(String path:java.util.List.of("/namespaces","/definitions?namespaceId="+namespace,"/definitions/"+d,"/definitions/missing","/values?definitionId="+d+"&environment=TEST","/feature-flags","/effective?definitionId="+d+"&environment=TEST&instant="+FROM))
                call("GET",BASE+path,token,null).andExpect(status().isForbidden());
            call("POST",BASE+"/definitions/"+d+"/activate",token,json("expectedUpdatedAt",before)).andExpect(status().isForbidden());
        }
        assertEquals("DRAFT",jdbc.queryForObject("SELECT status FROM public.hidra_configuration_definition WHERE id=?",String.class,d));
    }
    private void authenticateAdmin() {
        var jwt=context.getBean("hidraJwtDecoder",org.springframework.security.oauth2.jwt.JwtDecoder.class).decode(admin);
        var converter=context.getBean("hidraJwtAuthenticationConverter",org.springframework.core.convert.converter.Converter.class);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication((org.springframework.security.core.Authentication)converter.convert(jwt));
    }
    @Test void downstreamFailureRollsBackActivationAndVersionTogether() throws Exception {
        String d=definition("BOOLEAN","INTERNAL","false",false);Instant expected=Instant.parse(version(d));authenticateAdmin();
        try {var tx=new TransactionTemplate(manager);assertThrows(IllegalStateException.class,()->tx.executeWithoutResult(s->{service.activateDefinition(d,expected);throw new IllegalStateException("Synthetic downstream failure");}));}
        finally {org.springframework.security.core.context.SecurityContextHolder.clearContext();}
        assertEquals("DRAFT",jdbc.queryForObject("SELECT status FROM public.hidra_configuration_definition WHERE id=?",String.class,d));
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM public.hidra_configuration_definition_version WHERE definition_id=?",Integer.class,d));
    }
    private java.util.List<Integer> competing(String path,String body) throws Exception {
        var pool=java.util.concurrent.Executors.newFixedThreadPool(2);var start=new java.util.concurrent.CountDownLatch(1);
        try {java.util.concurrent.Callable<Integer> request=()->{start.await();return call("POST",path,admin,body).andReturn().getResponse().getStatus();};var a=pool.submit(request);var b=pool.submit(request);start.countDown();
            var codes=new java.util.ArrayList<>(java.util.List.of(a.get(30,java.util.concurrent.TimeUnit.SECONDS),b.get(30,java.util.concurrent.TimeUnit.SECONDS)));java.util.Collections.sort(codes);return codes;
        } finally {pool.shutdownNow();}
    }
    @Test void competingActivationsCommitOneVersion() throws Exception {
        String d=definition("BOOLEAN","INTERNAL","false",false);assertEquals(java.util.List.of(200,409),competing(BASE+"/definitions/"+d+"/activate",json("expectedUpdatedAt",version(d))));
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM public.hidra_configuration_definition_version WHERE definition_id=?",Integer.class,d));
    }
    @Test void competingOverlappingWritesCommitOneValue() throws Exception {
        String d=definition("BOOLEAN","INTERNAL","false",false),v=activate(d);assertEquals(java.util.List.of(200,409),competing(BASE+"/values",override(d,v,"true",FROM,TO,null)));
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM public.hidra_configuration_value WHERE definition_id=?",Integer.class,d));
    }
    @Test void activeDefinitionAndVersionCannotBeRewritten() throws Exception {
        String d=definition("STRING","INTERNAL","default",false),v=activate(d);
        assertThrows(org.springframework.dao.DataAccessException.class,()->jdbc.update("UPDATE public.hidra_configuration_definition SET default_value='changed' WHERE id=?",d));
        assertThrows(org.springframework.dao.DataAccessException.class,()->jdbc.update("DELETE FROM public.hidra_configuration_definition_version WHERE id=?",v));
        call("GET",effective(d,FROM),admin,null).andExpect(status().isOk()).andExpect(jsonPath("$.value").value("default"));
    }
    @Test void finalCurrentAuthorityDenialRollsBackProvisionalActivation() throws Exception {
        String d=definition("STRING","INTERNAL","default",false);Instant expected=Instant.parse(version(d));authenticateAdmin();
        var calls=new java.util.concurrent.atomic.AtomicInteger();
        var guarded=new dz.sh.hidra.modules.configuration.application.service.ConfigurationAdministrationApplicationService(store,()->{
            if(calls.incrementAndGet()==2)jdbc.update("UPDATE public.hidra_identity_user_role_grant SET status='REVOKED' WHERE user_id=?",adminUser);
            return authority.administrator();
        });
        try {var tx=new TransactionTemplate(manager);assertThrows(org.springframework.security.access.AccessDeniedException.class,()->tx.executeWithoutResult(t->guarded.activateDefinition(d,expected)));}
        finally {org.springframework.security.core.context.SecurityContextHolder.clearContext();}
        assertEquals(2,calls.get());
        assertEquals("DRAFT",jdbc.queryForObject("SELECT status FROM public.hidra_configuration_definition WHERE id=?",String.class,d));
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM public.hidra_configuration_definition_version WHERE definition_id=?",Integer.class,d));
    }
}
