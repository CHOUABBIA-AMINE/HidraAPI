/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationFoundationAdministrationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Verifies the bounded Organization foundation contract with synthetic evidence.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.organization.application.port.in.OrganizationFoundationAdministrationUseCase;
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
class OrganizationFoundationAdministrationPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16").withDatabaseName("organization_foundation");
    @DynamicPropertySource static void properties(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url",PG::getJdbcUrl);r.add("spring.datasource.username",PG::getUsername);r.add("spring.datasource.password",PG::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto",()->"validate");r.add("spring.flyway.enabled",()->"true");
        r.add("hidra.platform.security.jwt.token-issuer",()->"https://organization.synthetic.invalid");
    }
    @Autowired JdbcTemplate jdbc;
    @Autowired PasswordEncoder passwords;
    @Autowired WebApplicationContext context;
    @Autowired PlatformTransactionManager manager;
    @Autowired OrganizationFoundationAdministrationUseCase service;
    private MockMvc http;
    private String admin,ordinary,type,position,adminUser;
    private static final String BASE="/api/v1/organization",PASSWORD="synthetic-organization-foundation-password";
    private String id(){return UUID.randomUUID().toString();}
    @BeforeEach void fixture() throws Exception {
        http=MockMvcBuilders.webAppContextSetup(context).apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity()).build();
        jdbc.update("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        String provider=id();jdbc.update("INSERT INTO public.hidra_identity_provider(id,code,name,provider_type,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at) VALUES (?,?,'Synthetic','LOCAL',false,false,'ACTIVE',now(),now())",provider,provider);
        admin=loginFixture(true);ordinary=loginFixture(false);type=id();position=id();
        adminUser=context.getBean("hidraJwtDecoder",org.springframework.security.oauth2.jwt.JwtDecoder.class).decode(admin).getSubject();
        jdbc.update("INSERT INTO public.hidra_org_unit_type(id,code,kind,name_en,active,created_at,updated_at) VALUES (?,?,'STATION_UNIT','Synthetic station',true,now(),now())",type,"TYPE-"+type);
        jdbc.update("INSERT INTO public.hidra_org_position(id,code,level,title_en,status,created_at,updated_at) VALUES (?,?,'ENGINEER','Synthetic engineer','ACTIVE',now(),now())",position,"POS-"+position);
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
    private String json(String... pairs) throws Exception {var map=new java.util.LinkedHashMap<String,String>();for(int i=0;i<pairs.length;i+=2)map.put(pairs[i],pairs[i+1]);return tools.jackson.databind.json.JsonMapper.builder().build().writeValueAsString(map);}
    private String employee() throws Exception {return value(call("POST",BASE+"/employees",admin,json("employeeNumber","EMP-"+id(),"firstNameLt","Synthetic","lastNameLt","Person","identityUserReference",adminUser)).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("REGISTERED")),"id");}
    private String version(String employee) throws Exception {return value(call("GET",BASE+"/employees/"+employee+"/lifecycle",admin,null).andExpect(status().isOk()),"updatedAt");}
    private String activation(String version) throws Exception{return json("expectedUpdatedAt",version,"reason","Synthetic activation");}
    private String unit(String parent) throws Exception {var map=new java.util.LinkedHashMap<String,String>();map.put("code","U-"+id());map.put("nameEn","Synthetic unit");map.put("unitTypeId",type);map.put("validFrom","2026-01-01T00:00:00Z");if(parent!=null)map.put("parentUnitId",parent);return value(call("POST",BASE+"/units",admin,tools.jackson.databind.json.JsonMapper.builder().build().writeValueAsString(map)).andExpect(status().isOk()),"id");}
    @Test void nativeHierarchyOwnerCatalogRegistrationActivationAndImmutableHistory() throws Exception {
        String parent=unit(null),child=unit(parent);
        call("GET",BASE+"/hierarchy",admin,null).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString(child)));
        call("GET",BASE+"/units/"+parent+"/children",admin,null).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(child));
        call("GET",BASE+"/reference/unit-types",admin,null).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString(type)));
        call("GET",BASE+"/reference/positions",admin,null).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString(position)));
        String employee=employee(),before=version(employee);
        call("POST",BASE+"/employees/"+employee+"/activate",admin,activation(before)).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("ACTIVE"));
        call("GET",BASE+"/employees/"+employee,admin,null).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("ACTIVE"));
        call("GET",BASE+"/employees/"+employee+"/lifecycle-history",admin,null).andExpect(status().isOk()).andExpect(jsonPath("$[0].actorId").value(adminUser)).andExpect(jsonPath("$[0].reason").value("Synthetic activation"));
        assertThrows(org.springframework.dao.DataAccessException.class,()->jdbc.update("DELETE FROM public.hidra_org_employee_lifecycle_event WHERE employee_id=?",employee));
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_employee_lifecycle_event WHERE employee_id=?",Integer.class,employee));
    }
    @Test void ordinaryRouteGrantsCannotDiscloseDirectoryHierarchyCountsCatalogsOrLifecycle() throws Exception {
        String employee=employee(),unit=unit(null);
        String user=context.getBean("hidraJwtDecoder",org.springframework.security.oauth2.jwt.JwtDecoder.class).decode(ordinary).getSubject();
        for(String code:java.util.List.of("organization:units:read","organization:employees:read","organization:hierarchy:read","organization:reference:read","organization:employees:execute","organization:responsibilities:read","organization:units:execute")) {
            var ids=jdbc.queryForList("SELECT id FROM public.hidra_identity_permission WHERE code=?",String.class,code);String permission;
            if(ids.isEmpty()){permission=id();jdbc.update("INSERT INTO public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES (?,?,'organization','administration','read',false,'ACTIVE',now(),now())",permission,code);}else permission=ids.getFirst();
            jdbc.update("INSERT INTO public.hidra_identity_user_permission_grant(id,user_id,permission_id,effect,scope_type,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES (?,?,?,'GRANT','GLOBAL','Synthetic route only',false,now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())",id(),user,permission);
        }
        for(String path:java.util.List.of("/units","/units/"+unit,"/units/missing","/units/"+unit+"/children","/hierarchy","/employees","/employees/"+employee,"/employees/missing","/employees/"+employee+"/lifecycle","/employees/"+employee+"/lifecycle-history","/reference/unit-types","/reference/positions","/responsibilities?scopeId=999"))
            call("GET",BASE+path,ordinary,null).andExpect(status().isForbidden());
        call("POST",BASE+"/employees/"+employee+"/activate",ordinary,activation(version(employee))).andExpect(status().isForbidden());
        call("POST",BASE+"/employees",ordinary,json("employeeNumber","DENIED-"+id())).andExpect(status().isForbidden());
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_employee_lifecycle_event WHERE employee_id=?",Integer.class,employee));
    }
    @Test void staleAndTerminalLifecycleRequestsNeverChangeOwnerStateOrHistory() throws Exception {
        for(String state:java.util.List.of("ACTIVE","SUSPENDED","RETIRED","TERMINATED")) {
            String e=employee();jdbc.update("UPDATE public.hidra_org_employee SET status=? WHERE id=?",state,e);String v=version(e);
            call("POST",BASE+"/employees/"+e+"/activate",admin,activation(v)).andExpect(status().isConflict());
            assertEquals(v,version(e));assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_employee_lifecycle_event WHERE employee_id=?",Integer.class,e));
        }
        String e=employee();Instant v=Instant.parse(version(e));
        call("POST",BASE+"/employees/"+e+"/activate",admin,activation(v.minusNanos(1000).toString())).andExpect(status().isConflict());
        call("POST",BASE+"/employees/"+e+"/activate",admin,activation(v.plusNanos(1).toString())).andExpect(status().isBadRequest());
        call("POST",BASE+"/employees/"+e+"/activate",admin,json("expectedUpdatedAt",v.toString(),"reason"," ")).andExpect(status().isBadRequest());
    }
    @Test void missingOrDisabledIdentityAndInactiveOwnerChoicesAreRejected() throws Exception {
        call("POST",BASE+"/employees",admin,json("employeeNumber","E-"+id(),"identityUserReference",id())).andExpect(status().isBadRequest());
        String e=employee();String linked=id();jdbc.update("INSERT INTO public.hidra_identity_user(id,username,user_type,status,failed_login_count,created_at,updated_at) VALUES (?,?,'HUMAN','DISABLED',0,now(),now())",linked,linked);
        jdbc.update("UPDATE public.hidra_org_employee SET identity_user_reference=? WHERE id=?",linked,e);
        call("POST",BASE+"/employees/"+e+"/activate",admin,activation(version(e))).andExpect(status().isConflict());
        jdbc.update("UPDATE public.hidra_org_unit_type SET active=false WHERE id=?",type);jdbc.update("UPDATE public.hidra_org_position SET status='INACTIVE' WHERE id=?",position);
        call("GET",BASE+"/reference/unit-types",admin,null).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString(type))));
        call("GET",BASE+"/reference/positions",admin,null).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString(position))));
    }
    @Test void rollbackRestoresEmployeeAndActivationHistoryTogether() throws Exception {
        String e=employee();Instant before=Instant.parse(version(e));
        authenticateAdmin();
        try {var tx=new TransactionTemplate(manager);tx.setIsolationLevel(org.springframework.transaction.TransactionDefinition.ISOLATION_READ_COMMITTED);
            assertThrows(IllegalStateException.class,()->tx.executeWithoutResult(s->{service.activate(e,before,"Synthetic rollback");throw new IllegalStateException("Downstream failure");}));
        } finally {org.springframework.security.core.context.SecurityContextHolder.clearContext();}
        assertEquals(before,Instant.parse(version(e)));assertEquals("REGISTERED",jdbc.queryForObject("SELECT status FROM public.hidra_org_employee WHERE id=?",String.class,e));
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_employee_lifecycle_event WHERE employee_id=?",Integer.class,e));
    }
    private void authenticateAdmin() {
        var jwt=context.getBean("hidraJwtDecoder",org.springframework.security.oauth2.jwt.JwtDecoder.class).decode(admin);
        var converter=context.getBean("hidraJwtAuthenticationConverter",org.springframework.core.convert.converter.Converter.class);
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication((org.springframework.security.core.Authentication)converter.convert(jwt));
    }
    @Test void competingNativeActivationRequestsCommitExactlyOneEvent() throws Exception {
        String e=employee(),body=activation(version(e));var pool=java.util.concurrent.Executors.newFixedThreadPool(2);var start=new java.util.concurrent.CountDownLatch(1);
        try {java.util.concurrent.Callable<Integer> request=()->{start.await();return call("POST",BASE+"/employees/"+e+"/activate",admin,body).andReturn().getResponse().getStatus();};
            var a=pool.submit(request);var b=pool.submit(request);start.countDown();var codes=new java.util.ArrayList<>(java.util.List.of(a.get(30,java.util.concurrent.TimeUnit.SECONDS),b.get(30,java.util.concurrent.TimeUnit.SECONDS)));java.util.Collections.sort(codes);assertEquals(java.util.List.of(200,409),codes);
        } finally {pool.shutdownNow();}
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM public.hidra_org_employee_lifecycle_event WHERE employee_id=?",Integer.class,e));
    }
}
