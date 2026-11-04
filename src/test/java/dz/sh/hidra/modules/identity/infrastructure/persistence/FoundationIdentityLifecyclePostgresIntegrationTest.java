/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FoundationIdentityLifecyclePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Verifies real fresh-schema PostgreSQL Identity lifecycle CAS, revocation, evidence and rollback.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.port.in.FoundationIdentityLifecycleUseCase.GrantKind;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcFoundationIdentityLifecycleAdapter;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.transaction.TransactionDefinition.ISOLATION_READ_COMMITTED;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes=HidraApplication.class, webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@org.junit.jupiter.api.TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
class FoundationIdentityLifecyclePostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("hidra_test");
    @DynamicPropertySource static void properties(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url",PG::getJdbcUrl);
        r.add("spring.datasource.username",PG::getUsername);
        r.add("spring.datasource.password",PG::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto",() -> "validate");
        r.add("spring.flyway.enabled",() -> "true");
        r.add("spring.flyway.validate-on-migrate",() -> "true");
        r.add("hidra.platform.security.jwt.token-issuer",() -> "https://foundation.synthetic.invalid");
    }

    @Autowired JdbcTemplate jdbc;
    @Autowired JdbcFoundationIdentityLifecycleAdapter adapter;
    @Autowired PlatformTransactionManager manager;
    @Autowired org.springframework.web.context.WebApplicationContext context;
    @Autowired org.springframework.security.crypto.password.PasswordEncoder passwords;
    @Autowired org.springframework.security.oauth2.jwt.JwtEncoder encoder;

    private static final String PASSWORD = "synthetic-foundation-password";
    private static final String API = "/api/v1/identity";
    private static final String BUSINESS = "/api/v1/organization/employees";
    private org.springframework.test.web.servlet.MockMvc http;

    @org.junit.jupiter.api.BeforeEach void configuredHttp() {
        http = org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup(context)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    private org.springframework.test.web.servlet.ResultActions request(String method,String path,
            String token,String body) throws Exception {
        var builder = org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request(
                org.springframework.http.HttpMethod.valueOf(method),path);
        if (token != null) builder.header("Authorization","Bearer "+token);
        if (body != null) builder.contentType(org.springframework.http.MediaType.APPLICATION_JSON).content(body);
        return http.perform(builder);
    }

    private String value(org.springframework.test.web.servlet.ResultActions result,String field) throws Exception {
        return com.jayway.jsonpath.JsonPath.read(result.andReturn().getResponse().getContentAsString(),"$."+field);
    }

    private String login(String username,String password) throws Exception {
        var result=request("POST",API+"/authentication/login",null,
                "{\"providerType\":\"LOCAL\",\"principal\":\""+username+"\",\"credentials\":\""+password+"\"}");
        result.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
        return value(result,"accessToken");
    }

    private String nativeAdministrator() throws Exception {
        jdbc.update("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        String provider=id();
        jdbc.update("INSERT INTO public.hidra_identity_provider"
                +"(id,code,name,provider_type,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at)"
                +" VALUES (?,?,'Synthetic foundation LOCAL','LOCAL',false,false,'ACTIVE',clock_timestamp(),clock_timestamp())",
                provider,provider);
        String actor=admin();
        jdbc.update("INSERT INTO public.hidra_identity_local_credential"
                +"(id,user_id,password_hash,credential_status,created_at,updated_at)"
                +" VALUES (?,?,?,'ACTIVE',clock_timestamp(),clock_timestamp())",id(),actor,passwords.encode(PASSWORD));
        return login("fixture-"+actor,PASSWORD);
    }

    private String onboard(String administrator,String username) throws Exception {
        var created=request("POST",API+"/users",administrator,"{\"username\":\""+username
                +"\",\"emailAddress\":\""+username+"@synthetic.invalid\",\"displayName\":\"Synthetic operator\",\"userType\":\"HUMAN\"}")
                .andDo(result -> {
                    var failure=result.getResolvedException();
                    if(failure!=null) {
                        String frames=java.util.Arrays.stream(failure.getStackTrace()).limit(6)
                                .map(StackTraceElement::toString).collect(java.util.stream.Collectors.joining("; "));
                        fail("Registration exception type="+failure.getClass().getName()+"; frames="+frames);
                    }
                })
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.status").value("REGISTERED"));
        String user=value(created,"id");
        String before=value(request("GET",API+"/users/"+user+"/lifecycle",administrator,null)
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk()),"updatedAt");
        var credential=request("PUT",API+"/users/"+user+"/local-credential",administrator,
                "{\"password\":\""+PASSWORD+"\",\"expectedSecurityVersion\":0,\"reason\":\"initial onboarding\"}")
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.securityVersion").value(1));
        assertFalse(credential.andReturn().getResponse().getContentAsString().contains(PASSWORD));
        assertFalse(credential.andReturn().getResponse().getContentAsString().contains("$2"));
        request("POST",API+"/users/"+user+"/activate",administrator,
                "{\"expectedUpdatedAt\":\""+before+"\",\"reason\":\"activate operator\"}")
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.status").value("ACTIVE"));
        return user;
    }

    private String routeGrant(String user,String code) {
        var permissions=jdbc.queryForList("SELECT id FROM public.hidra_identity_permission WHERE code=?",String.class,code);
        String permission;
        if(permissions.isEmpty()) {
            permission=id();
            jdbc.update("INSERT INTO public.hidra_identity_permission"
                    +"(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)"
                    +" VALUES (?,?,'foundation','route','read',false,'ACTIVE',clock_timestamp(),clock_timestamp())",permission,code);
        } else permission=permissions.getFirst();
        String grant=id();
        jdbc.update("INSERT INTO public.hidra_identity_user_permission_grant"
                +"(id,user_id,permission_id,effect,scope_type,grant_reason,emergency_access,valid_from,valid_to,status,created_at)"
                +" VALUES (?,?,?,'GRANT','GLOBAL','Synthetic foundation route',false,clock_timestamp()-interval '1 hour',"
                +"clock_timestamp()+interval '1 hour','ACTIVE',clock_timestamp())",grant,user,permission);
        return grant;
    }

    @Test @org.junit.jupiter.api.Order(1)
    void nativeLastAdministratorCannotBeDisabledOrHaveItsGrantRevoked() throws Exception {
        String token=nativeAdministrator();
        String actor=value(request("GET",API+"/me",token,null)
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk()),"userId");
        String version=value(request("GET",API+"/users/"+actor+"/lifecycle",token,null),"updatedAt");
        request("POST",API+"/users/"+actor+"/deactivate",token,
                "{\"expectedUpdatedAt\":\""+version+"\",\"reason\":\"last administrator regression\"}")
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isConflict());
        var grant=jdbc.queryForMap("SELECT id,created_at FROM public.hidra_identity_user_role_grant WHERE user_id=?",actor);
        request("POST",API+"/users/role-grants/"+grant.get("id")+"/revoke",token,
                "{\"expectedCreatedAt\":\""+((Timestamp)grant.get("created_at")).toInstant()+"\",\"reason\":\"last administrator regression\"}")
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isConflict());
        assertEquals("ACTIVE",jdbc.queryForObject("SELECT status FROM public.hidra_identity_user WHERE id=?",String.class,actor));
        assertEquals("ACTIVE",jdbc.queryForObject("SELECT status FROM public.hidra_identity_user_role_grant WHERE id=?",String.class,grant.get("id")));
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_lifecycle_event WHERE actor_id=?",Integer.class,actor));
        request("GET",API+"/me",token,null).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
    }

    @Test @org.junit.jupiter.api.Order(2)
    void nativeTwoUserOnboardingCurrentPermissionsRevocationAndAdvisoryIsolation() throws Exception {
        String administrator=nativeAdministrator(),firstName="first-"+id(),secondName="second-"+id();
        String first=onboard(administrator,firstName),second=onboard(administrator,secondName);
        String grant=routeGrant(first,"organization:employees:read");
        routeGrant(second,"identity:me:read");
        String firstToken=login(firstName,PASSWORD),secondToken=login(secondName,PASSWORD);
        request("GET",API+"/me",firstToken,null)
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.userId").value(first));
        request("GET",API+"/me/permissions",firstToken,null)
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("organization:employees:read")));
        request("GET",BUSINESS,firstToken,null).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
        request("GET",BUSINESS,secondToken,null).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isForbidden());
        request("GET",API+"/me/operation-capabilities?companyId=foreign-company&resourceType=PIPELINE&resourceId=hidden",secondToken,null)
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("[\"VIEW_OWN_PROFILE\"]"));
        request("GET",BUSINESS,secondToken,null).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isForbidden());
        request("POST",API+"/users/"+first+"/deactivate",secondToken,
                "{\"expectedUpdatedAt\":\"2000-01-01T00:00:00Z\",\"reason\":\"forbidden administrator\"}")
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isForbidden());
        Instant created=jdbc.queryForObject("SELECT created_at FROM public.hidra_identity_user_permission_grant WHERE id=?",
                (rs,n)->rs.getTimestamp(1).toInstant(),grant);
        request("POST",API+"/users/permission-grants/"+grant+"/revoke",administrator,
                "{\"expectedCreatedAt\":\""+created+"\",\"reason\":\"remove business access\"}")
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
        request("GET",BUSINESS,firstToken,null).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isForbidden());
        request("GET",API+"/me/permissions",firstToken,null)
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("organization:employees:read"))));
    }

    @Test @org.junit.jupiter.api.Order(3)
    void nativeCredentialResetDeactivationStaleVersionAndExpiredBearerAreDenied() throws Exception {
        String administrator=nativeAdministrator(),username="reset-"+id();
        String user=onboard(administrator,username),token=login(username,PASSWORD);
        request("POST",API+"/authentication/login",null,"{\"providerType\":\"LOCAL\",\"principal\":\""+username
                +"\",\"credentials\":\"wrong-synthetic-password\"}")
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isUnauthorized());
        String current=value(request("GET",API+"/users/"+user+"/lifecycle",administrator,null),"updatedAt");
        request("POST",API+"/users/"+user+"/deactivate",administrator,
                "{\"expectedUpdatedAt\":\"2000-01-01T00:00:00Z\",\"reason\":\"stale write\"}")
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isConflict());
        request("PUT",API+"/users/"+user+"/local-credential",administrator,
                "{\"password\":\"replacement-synthetic-password\",\"expectedSecurityVersion\":1,\"reason\":\"reset\"}")
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
        request("GET",API+"/me",token,null).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isForbidden());
        String renewed=login(username,"replacement-synthetic-password");
        String afterLogin=value(request("GET",API+"/users/"+user+"/lifecycle",administrator,null)
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk()),"updatedAt");
        assertNotEquals(current,afterLogin,"Successful LOCAL login must update its owner version.");
        request("POST",API+"/users/"+user+"/deactivate",administrator,
                "{\"expectedUpdatedAt\":\""+current+"\",\"reason\":\"pre-login version is stale\"}")
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isConflict());
        request("POST",API+"/users/"+user+"/deactivate",administrator,
                "{\"expectedUpdatedAt\":\""+afterLogin+"\",\"reason\":\"disable operator\"}")
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
        request("GET",API+"/me",renewed,null).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isForbidden());
        request("POST",API+"/authentication/login",null,"{\"providerType\":\"LOCAL\",\"principal\":\""+username
                +"\",\"credentials\":\"replacement-synthetic-password\"}")
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isUnauthorized());
        var claims=org.springframework.security.oauth2.jwt.JwtClaimsSet.builder()
                .issuer("https://foundation.synthetic.invalid").subject(user).audience(java.util.List.of("hidra-api"))
                .issuedAt(Instant.now().minusSeconds(7200)).expiresAt(Instant.now().minusSeconds(3600)).id(id()).build();
        String expired=encoder.encode(org.springframework.security.oauth2.jwt.JwtEncoderParameters.from(
                org.springframework.security.oauth2.jwt.JwsHeader.with(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256).build(),claims)).getTokenValue();
        request("GET",API+"/me",expired,null).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test @org.junit.jupiter.api.Order(4)
    void competingNativeLifecycleWritesCommitExactlyOneAuditEvent() throws Exception {
        String administrator=nativeAdministrator(),username="concurrent-"+id();
        String user=onboard(administrator,username);
        String version=value(request("GET",API+"/users/"+user+"/lifecycle",administrator,null),"updatedAt");
        var start=new java.util.concurrent.CountDownLatch(1);
        var executor=java.util.concurrent.Executors.newFixedThreadPool(2);
        try {
            java.util.concurrent.Callable<Integer> writer=() -> {
                assertTrue(start.await(5,java.util.concurrent.TimeUnit.SECONDS));
                return request("POST",API+"/users/"+user+"/deactivate",administrator,
                        "{\"expectedUpdatedAt\":\""+version+"\",\"reason\":\"competing writer\"}")
                        .andReturn().getResponse().getStatus();
            };
            var first=executor.submit(writer);
            var second=executor.submit(writer);
            start.countDown();
            var outcomes=new java.util.ArrayList<>(java.util.List.of(
                    first.get(30,java.util.concurrent.TimeUnit.SECONDS),
                    second.get(30,java.util.concurrent.TimeUnit.SECONDS)));
            outcomes.sort(Integer::compareTo);
            assertEquals(java.util.List.of(200,409),outcomes);
            assertEquals("DISABLED",jdbc.queryForObject("SELECT status FROM public.hidra_identity_user WHERE id=?",String.class,user));
            assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_lifecycle_event"
                    +" WHERE target_id=? AND action='DEACTIVATE'",Integer.class,user));
        } finally { executor.shutdownNow(); }
    }

    private String id() {return UUID.randomUUID().toString();}
    private TransactionTemplate tx() {
        var t = new TransactionTemplate(manager);
        t.setIsolationLevel(ISOLATION_READ_COMMITTED);
        return t;
    }

    private String createUser(String status) {
        String user=id();
        jdbc.update("INSERT INTO public.hidra_identity_user"
                +"(id,username,user_type,status,failed_login_count,created_at,updated_at)"
                +" VALUES (?,?,'HUMAN',?,0,clock_timestamp(),clock_timestamp())",
                user,"fixture-"+user,status);
        return user;
    }

    private String role() {
        var ids=jdbc.queryForList("SELECT id FROM public.hidra_identity_role WHERE code='HIDRA_ADMIN'",String.class);
        if (!ids.isEmpty())return ids.getFirst();
        String role=id();
        jdbc.update("INSERT INTO public.hidra_identity_role"
                +"(id,code,role_type,status,created_at,updated_at)"
                +" VALUES (?,'HIDRA_ADMIN','ADMIN','ACTIVE',clock_timestamp(),clock_timestamp())",role);
        return role;
    }

    private String adminGrant(String user, String role) {
        String grant=id();
        jdbc.update("INSERT INTO public.hidra_identity_user_role_grant"
                +"(id,user_id,role_id,scope_type,valid_from,status,created_at)"
                +" VALUES (?,?,?,'GLOBAL',clock_timestamp()-interval '1 hour','ACTIVE',clock_timestamp())",
                grant,user,role);
        return grant;
    }

    private String admin() {
        var user=createUser("ACTIVE");
        adminGrant(user,role());
        return user;
    }

    @Test void realCredentialActivationResetSessionRevocationAndImmutableAudit() {
        String actor=admin(), ordinary=createUser("REGISTERED");
        var before=tx().execute(s -> adapter.readUser(ordinary,actor));
        assertTranslatedRejection("An ACTIVE LOCAL credential is required.",() -> tx().execute(
                s -> adapter.changeUser(ordinary,before.updatedAt(),true,"activate",actor)));
        assertEquals(before,tx().execute(s -> adapter.readUser(ordinary,actor)));
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_lifecycle_event"
                +" WHERE target_id=?",Integer.class,ordinary));
        var first=tx().execute(s -> adapter.setCredential(ordinary,
                "$2a$10$synthetic-credential-hash-material-for-tests",0,"initial issue",actor));
        assertEquals(1L,first.securityVersion());
        var active=tx().execute(s -> adapter.changeUser(ordinary,before.updatedAt(),true,"activate",actor));
        assertEquals("ACTIVE",active.status());
        assertTranslatedRejection("Stale lifecycle version.",() -> tx().execute(
                s -> adapter.changeUser(ordinary,before.updatedAt(),false,"stale",actor)));
        assertEquals(active,tx().execute(s -> adapter.readUser(ordinary,actor)));
        String session=id();
        jdbc.update("INSERT INTO public.hidra_identity_login_session"
                +"(id,user_id,started_at,last_seen_at,expires_at,status,session_type)"
                +" VALUES (?,?,clock_timestamp(),clock_timestamp(),clock_timestamp()+interval '1 hour','ACTIVE','LOCAL')",
                session,ordinary);
        var reset=tx().execute(s -> adapter.setCredential(ordinary,
                "$2a$10$updated-synthetic-credential-hash-material",1,"recovery",actor));
        assertEquals(2L,reset.securityVersion());
        assertEquals("REVOKED",jdbc.queryForObject(
                "SELECT status FROM public.hidra_identity_login_session WHERE id=?",String.class,session));
        var disabled=tx().execute(s -> adapter.changeUser(ordinary,active.updatedAt(),false,"deactivate",actor));
        assertEquals("DISABLED",disabled.status());
        assertEquals(4,jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_lifecycle_event"
                +" WHERE actor_id=? AND target_id=?",Integer.class,actor,ordinary).intValue());
        assertThrows(org.springframework.dao.DataAccessException.class, () -> jdbc.update(
                "UPDATE public.hidra_identity_lifecycle_event SET reason='rewrite' WHERE target_id=?",ordinary));
    }

    @Test void historicalGrantRevocationAndReplay() {
        String actor=admin(), second=createUser("ACTIVE"), grant=adminGrant(second,role());
        Instant grantCreated=jdbc.queryForObject("SELECT created_at FROM public.hidra_identity_user_role_grant"
                +" WHERE id=?", (rs,n)->rs.getTimestamp(1).toInstant(),grant);
        var revoked=tx().execute(s -> adapter.revokeGrant(GrantKind.USER_ROLE,grant,grantCreated,
                "removal of secondary administrator",actor));
        assertEquals("REVOKED",revoked.status());
        assertEquals("removal of secondary administrator",jdbc.queryForObject(
                "SELECT revoked_reason FROM public.hidra_identity_user_role_grant WHERE id=?",String.class,grant));
        assertTranslatedRejection("Only ACTIVE grants may be revoked.",() -> tx().execute(
                s -> adapter.revokeGrant(GrantKind.USER_ROLE,grant,grantCreated,"replay",actor)));
        assertEquals(revoked.revokedAt(),jdbc.queryForObject(
                "SELECT revoked_at FROM public.hidra_identity_user_role_grant WHERE id=?",
                (rs,n) -> rs.getTimestamp(1).toInstant(),grant));
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_lifecycle_event"
                +" WHERE target_id=?",Integer.class,grant));
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_user_role_grant"
                +" WHERE id=?",Integer.class,grant));
    }

    private static void assertTranslatedRejection(String message,
            org.junit.jupiter.api.function.Executable operation) {
        var failure = assertThrows(org.springframework.dao.InvalidDataAccessApiUsageException.class,operation);
        var cause = assertInstanceOf(IllegalStateException.class,failure.getCause());
        assertEquals(message,cause.getMessage());
    }

    @Test void rollbackRestoresStatusAndAuditAtomically() {
        String actor=admin(),ordinary=createUser("REGISTERED");
        var before=tx().execute(s -> adapter.readUser(ordinary,actor));
        tx().execute(s -> adapter.setCredential(ordinary,
                "$2a$10$synthetic-credential-hash-material-for-tests",0,"initial",actor));
        int baseline=jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_lifecycle_event"
                +" WHERE target_id=?",Integer.class,ordinary);
        assertThrows(IllegalStateException.class, () -> tx().execute(s -> {
            adapter.changeUser(ordinary,before.updatedAt(),true,"activation",actor);
            throw new IllegalStateException("Synthetic downstream failure");
        }));
        assertEquals("REGISTERED",jdbc.queryForObject(
                "SELECT status FROM public.hidra_identity_user WHERE id=?",String.class,ordinary));
        assertEquals(baseline,jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_lifecycle_event"
                +" WHERE target_id=?",Integer.class,ordinary));
    }
}
