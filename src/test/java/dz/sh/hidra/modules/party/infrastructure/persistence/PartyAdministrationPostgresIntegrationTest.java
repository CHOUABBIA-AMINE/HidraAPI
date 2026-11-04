/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyAdministrationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence
 *
 * @Description : Verifies the bounded Party foundation contract with synthetic evidence.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.party.application.port.in.PartyAdministrationUseCase;
import dz.sh.hidra.modules.party.application.contract.topology.TopologyPartyEligibilityContract;
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
class PartyAdministrationPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16").withDatabaseName("party_foundation");
    @DynamicPropertySource static void properties(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url",PG::getJdbcUrl);r.add("spring.datasource.username",PG::getUsername);r.add("spring.datasource.password",PG::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto",()->"validate");r.add("spring.flyway.enabled",()->"true");
        r.add("hidra.platform.security.jwt.token-issuer",()->"https://party.synthetic.invalid");
    }
    @Autowired JdbcTemplate jdbc;
    @Autowired PasswordEncoder passwords;
    @Autowired WebApplicationContext context;
    @Autowired PlatformTransactionManager manager;
    @Autowired PartyAdministrationUseCase service;
    @Autowired TopologyPartyEligibilityContract topology;
    private MockMvc http;
    private String admin,ordinary,type,role;
    private static final String BASE="/api/v1/party",PASSWORD="synthetic-party-foundation-password";
    private String id(){return UUID.randomUUID().toString();}
    @BeforeEach void fixture() throws Exception {
        http=MockMvcBuilders.webAppContextSetup(context).apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity()).build();
        jdbc.update("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        String provider=id();jdbc.update("INSERT INTO public.hidra_identity_provider(id,code,name,provider_type,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at) VALUES (?,?,'Synthetic','LOCAL',false,false,'ACTIVE',now(),now())",provider,provider);
        admin=loginFixture(true);ordinary=loginFixture(false);type=id();role=id();
        jdbc.update("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at) VALUES (?,?,'ACTIVE',now(),now())",type,"TYPE-"+type);
        jdbc.update("INSERT INTO public.hidra_party_type_translation(id,party_type_id,language_code,label,created_at,updated_at) VALUES (?,?,'fr','Entite synthetique',now(),now())",id(),type);
        jdbc.update("INSERT INTO public.hidra_party_role(id,code,qualification_required_by_default,status,created_at,updated_at) VALUES (?,?,false,'ACTIVE',now(),now())",role,"ROLE-"+role);
    }
    private String loginFixture(boolean administrator) throws Exception {
        String user=id();jdbc.update("INSERT INTO public.hidra_identity_user(id,username,user_type,status,failed_login_count,created_at,updated_at) VALUES (?,?,'HUMAN','ACTIVE',0,now(),now())",user,user);
        jdbc.update("INSERT INTO public.hidra_identity_local_credential(id,user_id,password_hash,credential_status,created_at,updated_at) VALUES (?,?,?,'ACTIVE',now(),now())",id(),user,passwords.encode(PASSWORD));
        if(administrator) {
            var ids=jdbc.queryForList("SELECT id FROM public.hidra_identity_role WHERE code='HIDRA_ADMIN'",String.class);String adminRole;
            if(ids.isEmpty()){adminRole=id();jdbc.update("INSERT INTO public.hidra_identity_role(id,code,role_type,status,created_at,updated_at) VALUES (?,'HIDRA_ADMIN','ADMIN','ACTIVE',now(),now())",adminRole);}else adminRole=ids.getFirst();
            jdbc.update("INSERT INTO public.hidra_identity_user_role_grant(id,user_id,role_id,scope_type,valid_from,status,created_at) VALUES (?,?,?,'GLOBAL',now()-interval '1 hour','ACTIVE',now())",id(),user,adminRole);
        }
        var result=http.perform(post("/api/v1/identity/authentication/login").contentType("application/json").content("{\"providerType\":\"LOCAL\",\"principal\":\""+user+"\",\"credentials\":\""+PASSWORD+"\"}")).andExpect(status().isOk());
        return value(result,"accessToken");
    }
    private ResultActions call(String method,String path,String token,String body) throws Exception {
        var r=request(org.springframework.http.HttpMethod.valueOf(method),path);if(token!=null)r.header("Authorization","Bearer "+token);if(body!=null)r.contentType("application/json").content(body);return http.perform(r);
    }
    private String value(ResultActions r,String field) throws Exception {return com.jayway.jsonpath.JsonPath.read(r.andReturn().getResponse().getContentAsString(),"$."+field);}
    private String registration(String code,String typeId){return "{\"code\":\""+code+"\",\"partyTypeId\":\""+typeId+"\",\"legalName\":\"Synthetic legal entity\",\"countryCode\":\"DZA\"}";}
    private String create() throws Exception {return value(call("POST",BASE+"/parties",admin,registration("P-"+id(),type)).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("DRAFT")),"id");}
    private String version(String party) throws Exception {return value(call("GET",BASE+"/parties/"+party,admin,null).andExpect(status().isOk()),"updatedAt");}
    private String transition(String party,String version,String reason){return "{\"expectedUpdatedAt\":\""+version+"\",\"reason\":\""+reason+"\"}";}
    private void activate(String party) throws Exception {call("POST",BASE+"/parties/"+party+"/activate",admin,transition(party,version(party),"synthetic activation")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("ACTIVE"));}

    @Test void nativeRegisterCatalogActivateRoleSelectMetadataAndSuspend() throws Exception {
        call("GET",BASE+"/reference/party-types?language=fr",admin,null).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString("Entite synthetique")));
        call("GET",BASE+"/reference/party-roles",admin,null).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString(role)));
        String party=create();assertFalse(topology.eligibleForNewReference(party));activate(party);assertTrue(topology.eligibleForNewReference(party));
        call("POST",BASE+"/roles/assignments",admin,"{\"partyId\":\""+party+"\",\"roleId\":\""+role+"\",\"qualificationRequired\":false}").andExpect(status().isOk());
        call("POST",BASE+"/roles/assignments",admin,"{\"partyId\":\""+party+"\",\"roleId\":\""+role+"\",\"qualificationRequired\":false}").andExpect(status().is(422));
        call("GET",BASE+"/parties?eligibleOnly=true&typeId="+type+"&roleId="+role,admin,null).andExpect(status().isOk()).andExpect(jsonPath("$.total").value(1)).andExpect(jsonPath("$.items[0].id").value(party));
        String code=jdbc.queryForObject("SELECT code FROM public.hidra_party_party WHERE id=?",String.class,party);
        call("PUT",BASE+"/parties/"+party,admin,"{\"expectedUpdatedAt\":\""+version(party)+"\",\"legalName\":\"Revised legal label\",\"reason\":\"supplied metadata correction\"}").andExpect(status().isOk()).andExpect(jsonPath("$.code").value(code));
        call("GET",BASE+"/parties/"+party+"/history",admin,null).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString("Synthetic legal entity"))).andExpect(content().string(org.hamcrest.Matchers.containsString("Revised legal label")));
        call("POST",BASE+"/parties/"+party+"/deactivate",admin,transition(party,version(party),"synthetic suspension")).andExpect(status().isOk());assertFalse(topology.eligibleForNewReference(party));
        call("GET",BASE+"/parties/"+party,admin,null).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("SUSPENDED"));
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM public.hidra_party_role_assignment WHERE party_id=?",Integer.class,party));
    }
    @Test void unauthorizedDiscoveryCountDetailHistoryAndMutationAreDeniedBeforeDisclosure() throws Exception {
        String party=create();
        String user=context.getBean("hidraJwtDecoder",org.springframework.security.oauth2.jwt.JwtDecoder.class).decode(ordinary).getSubject();
        for(String code:new String[]{"party:parties:read","party:reference:read","party:parties:execute"}) {
            var existing=jdbc.queryForList("SELECT id FROM public.hidra_identity_permission WHERE code=?",String.class,code);
            String permission;
            if(existing.isEmpty()) {
                permission=id();jdbc.update("INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES (?,?,'party','administration','read',false,'ACTIVE',now(),now())",permission,code);
            } else permission=existing.getFirst();
            jdbc.update("INSERT INTO public.hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES (?,?,?,'GRANT','GLOBAL','Synthetic route only',false,now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",id(),user,permission);
        }
        for(String path:new String[]{"/parties","/parties/"+party,"/parties/"+party+"/history","/reference/party-types","/reference/party-roles"})
            call("GET",BASE+path,ordinary,null).andExpect(status().isForbidden());
        call("GET",BASE+"/parties/nonexistent",ordinary,null).andExpect(status().isForbidden());
        call("POST",BASE+"/parties/"+party+"/activate",ordinary,transition(party,version(party),"forbidden")).andExpect(status().isForbidden());
        assertEquals("DRAFT",jdbc.queryForObject("SELECT status FROM public.hidra_party_party WHERE id=?",String.class,party));
    }
    @Test void duplicateInvalidInactiveTypeAndRoleKeepOwnerState() throws Exception {
        String code="DUP-"+id();call("POST",BASE+"/parties",admin,registration(code,type)).andExpect(status().isOk());
        call("POST",BASE+"/parties",admin,registration(code,type)).andExpect(status().is(422));
        call("POST",BASE+"/parties",admin,registration("missing-"+id(),"missing-type")).andExpect(status().isBadRequest());
        jdbc.update("UPDATE public.hidra_party_type SET status='INACTIVE' WHERE id=?",type);
        call("POST",BASE+"/parties",admin,registration("inactive-"+id(),type)).andExpect(status().isBadRequest());
        jdbc.update("UPDATE public.hidra_party_type SET status='ACTIVE' WHERE id=?",type);String party=create();activate(party);
        call("POST",BASE+"/roles/assignments",admin,"{\"partyId\":\""+party+"\",\"roleId\":\"missing-role\"}").andExpect(status().isBadRequest());
        jdbc.update("UPDATE public.hidra_party_role SET status='INACTIVE' WHERE id=?",role);
        call("POST",BASE+"/roles/assignments",admin,"{\"partyId\":\""+party+"\",\"roleId\":\""+role+"\"}").andExpect(status().isBadRequest());
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM public.hidra_party_role_assignment WHERE party_id=?",Integer.class,party));
    }
    @Test void staleAndForbiddenLifecycleTransitionsPreserveGeneration() throws Exception {
        String party=create(),stale=version(party);activate(party);
        long generation=jdbc.queryForObject("SELECT generation FROM public.hidra_party_eligibility_generation WHERE party_id=?",Long.class,party);
        call("POST",BASE+"/parties/"+party+"/deactivate",admin,transition(party,stale,"stale")).andExpect(status().isConflict());
        assertEquals(generation,jdbc.queryForObject("SELECT generation FROM public.hidra_party_eligibility_generation WHERE party_id=?",Long.class,party));
        call("POST",BASE+"/parties/"+party+"/activate",admin,transition(party,version(party),"already active")).andExpect(status().isConflict());
        jdbc.update("UPDATE public.hidra_party_party SET status='BLOCKED' WHERE id=?",party);
        call("POST",BASE+"/parties/"+party+"/activate",admin,transition(party,version(party),"blocked")).andExpect(status().isConflict());
    }
    @Test void rollbackRestoresPartyHistoryAndEligibilityGeneration() throws Exception {
        String party=create();String before=version(party);long generation=jdbc.queryForObject("SELECT generation FROM public.hidra_party_eligibility_generation WHERE party_id=?",Long.class,party);
        var tx=new TransactionTemplate(manager);tx.setIsolationLevel(org.springframework.transaction.TransactionDefinition.ISOLATION_READ_COMMITTED);
        var decoder=context.getBean("hidraJwtDecoder",org.springframework.security.oauth2.jwt.JwtDecoder.class);
        var jwt=decoder.decode(admin);var auth=context.getBean("hidraJwtAuthenticationConverter",org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter.class).convert(jwt);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);
        try {assertThrows(IllegalStateException.class,()->tx.execute(s->{service.transition(party,Instant.parse(before),true,"rollback");throw new IllegalStateException("Synthetic downstream rollback");}));}
        finally {org.springframework.security.core.context.SecurityContextHolder.clearContext();}
        assertEquals("DRAFT",jdbc.queryForObject("SELECT status FROM public.hidra_party_party WHERE id=?",String.class,party));
        assertEquals(generation,jdbc.queryForObject("SELECT generation FROM public.hidra_party_eligibility_generation WHERE party_id=?",Long.class,party));
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM public.hidra_party_status_history WHERE party_id=?",Integer.class,party));
    }
    @Test void competingActivationWritersCommitOneHistoryAndOneConflict() throws Exception {
        String party=create(),before=version(party);var start=new java.util.concurrent.CountDownLatch(1);var pool=java.util.concurrent.Executors.newFixedThreadPool(2);
        try {java.util.concurrent.Callable<Integer> writer=()->{assertTrue(start.await(5,java.util.concurrent.TimeUnit.SECONDS));return call("POST",BASE+"/parties/"+party+"/activate",admin,transition(party,before,"competing activation")).andReturn().getResponse().getStatus();};
            var first=pool.submit(writer);var second=pool.submit(writer);start.countDown();var outcomes=new java.util.ArrayList<>(java.util.List.of(first.get(30,java.util.concurrent.TimeUnit.SECONDS),second.get(30,java.util.concurrent.TimeUnit.SECONDS)));outcomes.sort(Integer::compareTo);assertEquals(java.util.List.of(200,409),outcomes);
        } finally {pool.shutdownNow();}
        assertEquals(2,jdbc.queryForObject("SELECT count(*) FROM public.hidra_party_status_history WHERE party_id=?",Integer.class,party));
    }
}
