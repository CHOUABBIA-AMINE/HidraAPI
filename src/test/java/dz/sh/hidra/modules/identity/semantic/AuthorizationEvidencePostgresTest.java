/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationEvidencePostgresTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Resolves bounded and explainable Identity authorization evidence.
 *
 */
package dz.sh.hidra.modules.identity.semantic;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import jakarta.persistence.EntityManager;
import java.nio.file.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import dz.sh.hidra.modules.identity.domain.policy.*;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JpaAuthorizationEvidenceAdapter;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.*;

@Testcontainers(disabledWithoutDocker=true)
class AuthorizationEvidencePostgresTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    private static SessionFactory factory;
    private final Instant at=Instant.parse("2026-10-06T12:00:00Z");
    private final String start="'2026-10-01T00:00:00Z'",end="'2026-11-01T00:00:00Z'";
    @BeforeAll static void factory() {
        var cfg=new Configuration().setProperty("hibernate.connection.url",POSTGRES.getJdbcUrl())
                .setProperty("hibernate.connection.username",POSTGRES.getUsername()).setProperty("hibernate.connection.password",POSTGRES.getPassword())
                .setProperty("hibernate.hbm2ddl.auto","none");
        for(Class<?> type:List.of(UserJpaEntity.class,PermissionJpaEntity.class,RoleJpaEntity.class,GroupJpaEntity.class,
                UserPermissionGrantJpaEntity.class,UserRoleGrantJpaEntity.class,RolePermissionGrantJpaEntity.class,
                UserGroupMembershipJpaEntity.class,GroupRoleGrantJpaEntity.class,AuthorizationPolicyJpaEntity.class,
                AuthorizationPolicyVersionJpaEntity.class,AuthorizationPolicyRuleJpaEntity.class,SubjectSecurityAttributeJpaEntity.class,
                AttributeDefinitionJpaEntity.class,ExternalIdentityJpaEntity.class,IdentityProviderJpaEntity.class,
                ExternalGroupMappingJpaEntity.class,ExternalRoleMappingJpaEntity.class,ExternalPermissionMappingJpaEntity.class,
                AuthorizationDecisionJpaEntity.class)) cfg.addAnnotatedClass(type);
        factory=cfg.buildSessionFactory();
    }
    @AfterAll static void close() { if(factory!=null) factory.close(); }
    private void sql(String statement) throws Exception {
        try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());var s=c.createStatement()) { s.execute(statement); }
    }
    @BeforeEach void setup() throws Exception {
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        sql(Files.readString(Path.of("src/main/resources/db/migration/V20260611_001__create_identity_tables.sql")));
        for(String migration:List.of("V20261004_007__hmr_007_identity_role.sql","V20261004_010__hmr_010_identity_identity_provider.sql",
                "V20261004_011__hmr_011_identity_permission.sql","V20261006_010__hmr_063_identity_user_uniqueness.sql",
                "V20261006_013__hmr_088_direct_permission_bounds.sql"))
            sql(Files.readString(Path.of("src/main/resources/db/migration",migration)));

        sql("INSERT INTO hidra_identity_user (id,username,user_type,status,failed_login_count,created_at,updated_at) VALUES ('u','user','HUMAN','ACTIVE',0,now(),now())");
        sql("INSERT INTO hidra_identity_permission (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) VALUES ('p','pipeline:pipeline:read','pipeline','PIPELINE','read',false,'ACTIVE',now(),now())");
        sql("INSERT INTO hidra_identity_role (id,code,role_type,status,created_at,updated_at) VALUES ('role','operator','BUSINESS','ACTIVE',now(),now())");
        sql("INSERT INTO hidra_identity_group (id,code,group_type,status,created_at,updated_at) VALUES ('group','operators','LOCAL','ACTIVE',now(),now())");
        sql("INSERT INTO hidra_identity_role_permission_grant (id,role_id,permission_id,effect,valid_from,status,created_at) VALUES ('rp','role','p','GRANT',"+start+",'ACTIVE',now())");
    }
    private AuthorizationEvidence evidence(AuthorizationScope scope,VerifiedAuthorizationAssertion assertion) {
        try(EntityManager em=factory.createEntityManager()) {
            em.getTransaction().begin();
            var e=new JpaAuthorizationEvidenceAdapter(em).load(new AuthorizationEvaluationRequest("u","pipeline:pipeline:read","PIPELINE","pipeline-1",scope),at,assertion);
            em.getTransaction().commit(); return e;
        }
    }
    private dz.sh.hidra.modules.identity.domain.model.AuthorizationDecision decision(AuthorizationScope scope,VerifiedAuthorizationAssertion assertion) {
        return new AuthorizationPolicyEvaluator().evaluate(new AuthorizationEvaluationRequest("u","pipeline:pipeline:read","PIPELINE","pipeline-1",scope),evidence(scope,assertion),at);
    }
    private void direct(String effect) throws Exception {
        sql("INSERT INTO hidra_identity_user_permission_grant (id,user_id,permission_id,effect,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES ('direct','u','p','"+effect+"','test',false,"+start+","+end+",'ACTIVE',now())");
    }
    private void inherited() throws Exception {
        sql("INSERT INTO hidra_identity_user_group_membership (id,user_id,group_id,membership_type,valid_from,status,created_at,updated_at) VALUES ('membership','u','group','DIRECT',"+start+",'ACTIVE',now(),now())");
        sql("INSERT INTO hidra_identity_group_role_grant (id,group_id,role_id,scope_type,scope_reference_id,valid_from,status,created_at) VALUES ('gr','group','role','PIPELINE','pipeline-1',"+start+",'ACTIVE',now())");
    }
    private VerifiedAuthorizationAssertion external() throws Exception {
        sql("INSERT INTO hidra_identity_provider (id,code,name,provider_type,issuer_uri,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at) VALUES ('provider','oidc','OIDC','OIDC','https://issuer.example.invalid',false,false,'ACTIVE',now(),now())");
        sql("INSERT INTO hidra_identity_external_identity (id,user_id,identity_provider_id,external_subject,status,created_at,updated_at) VALUES ('external','u','provider','subject','LINKED',now(),now())");
        return new VerifiedAuthorizationAssertion("u","provider","external","subject",at.plusSeconds(60),Map.of("roles",Set.of("operator"),"groups",Set.of("operators"),"permissions",Set.of("read")));
    }
    @Test void inheritedChainRespectsScopeAndStatuses() throws Exception {
        inherited(); var scope=new AuthorizationScope(ScopeType.PIPELINE,"pipeline-1","untrusted-label");
        var d=decision(scope,null);
        assertEquals(AuthorizationDecisionValue.PERMIT,d.decision());
        assertEquals(List.of("gr","membership","rp"),AuthorizationJson.parse(d.matchedGrantIds()));
        assertEquals(AuthorizationDecisionValue.DENY,decision(new AuthorizationScope(ScopeType.PIPELINE,"pipeline-2","same-label"),null).decision());
        sql("UPDATE hidra_identity_group SET status='INACTIVE'");
        assertEquals(AuthorizationDecisionValue.DENY,decision(scope,null).decision());
    }
    @Test void directRolesAndHalfOpenTimeBounds() throws Exception {
        sql("INSERT INTO hidra_identity_user_role_grant (id,user_id,role_id,valid_from,valid_to,status,created_at) VALUES ('ur','u','role','2026-10-06T12:00:00Z',"+end+",'ACTIVE',now())");
        assertEquals(AuthorizationDecisionValue.PERMIT,decision(AuthorizationScope.global(),null).decision());
        sql("UPDATE hidra_identity_user_role_grant SET valid_to='2026-10-06T12:00:00Z'");
        assertEquals(AuthorizationDecisionValue.DENY,decision(AuthorizationScope.global(),null).decision());
        sql("UPDATE hidra_identity_user_role_grant SET valid_to=NULL,valid_from='2026-10-06T12:00:01Z'");
        assertEquals(AuthorizationDecisionValue.DENY,decision(AuthorizationScope.global(),null).decision());
    }
    @Test void directDenialOverridesInheritedGrant() throws Exception {
        inherited(); direct("DENY");
        assertEquals("EXPLICIT_DENY",decision(new AuthorizationScope(ScopeType.PIPELINE,"pipeline-1",null),null).reasonCode());
        sql("UPDATE hidra_identity_user SET locked_until='2026-10-07T00:00:00Z'");
        assertEquals("USER_INELIGIBLE",decision(AuthorizationScope.global(),null).reasonCode());
    }
    @Test void verifiedExternalMappingsExplainAuthorityAndRequireEligibleMode() throws Exception {
        var assertion=external();
        sql("INSERT INTO hidra_identity_external_permission_mapping (id,identity_provider_id,permission_id,external_permission_code,claim_name,mapping_mode,effect,status,created_at,updated_at) VALUES ('mapping','provider','p','read','permissions','DIRECT_GRANT','GRANT','ACTIVE',now(),now())");
        var d=decision(AuthorizationScope.global(),assertion);
        assertEquals(AuthorizationDecisionValue.PERMIT,d.decision());
        assertTrue(d.externalClaimsUsed().contains("mappingId")); assertFalse(d.externalClaimsUsed().contains("\"read\""));
        assertEquals(AuthorizationDecisionValue.DENY,decision(AuthorizationScope.global(),null).decision());
        for(String mode:List.of("REQUIRES_LOCAL_APPROVAL","DISABLED")) {
            sql("UPDATE hidra_identity_external_permission_mapping SET mapping_mode='"+mode+"'");
            assertEquals(AuthorizationDecisionValue.DENY,decision(AuthorizationScope.global(),assertion).decision());
        }
        sql("UPDATE hidra_identity_provider SET status='INACTIVE'");
        assertEquals("EXTERNAL_IDENTITY_INELIGIBLE",decision(AuthorizationScope.global(),assertion).reasonCode());
    }
    @Test void externalGroupAndRoleMappingsUseVerifiedClaims() throws Exception {
        var assertion=external();
        sql("INSERT INTO hidra_identity_group_role_grant (id,group_id,role_id,valid_from,status,created_at) VALUES ('gr','group','role',"+start+",'ACTIVE',now())");
        sql("INSERT INTO hidra_identity_external_group_mapping (id,identity_provider_id,group_id,external_group_name,claim_name,mapping_mode,auto_create_membership,status,created_at,updated_at) VALUES ('gm','provider','group','operators','groups','ASSERTION_ONLY',false,'ACTIVE',now(),now())");
        assertEquals(List.of("gm","gr","rp"),AuthorizationJson.parse(decision(AuthorizationScope.global(),assertion).matchedGrantIds()));
        sql("UPDATE hidra_identity_external_group_mapping SET mapping_mode='MANUAL_APPROVAL'");
        assertEquals(AuthorizationDecisionValue.DENY,decision(AuthorizationScope.global(),assertion).decision());
        sql("INSERT INTO hidra_identity_external_role_mapping (id,identity_provider_id,role_id,external_role_code,claim_name,mapping_mode,status,created_at,updated_at) VALUES ('rm','provider','role','operator','roles','DIRECT_GRANT','ACTIVE',now(),now())");
        assertEquals(List.of("rm","rp"),AuthorizationJson.parse(decision(AuthorizationScope.global(),assertion).matchedGrantIds()));
    }
    @Test void synchronizedMembershipRequiresActiveProviderAndMapping() throws Exception {
        external();
        sql("INSERT INTO hidra_identity_external_group_mapping (id,identity_provider_id,group_id,external_group_name,mapping_mode,auto_create_membership,status,created_at,updated_at) VALUES ('sync','provider','group','operators','SYNC_MEMBERSHIP',false,'ACTIVE',now(),now())");
        sql("INSERT INTO hidra_identity_user_group_membership (id,user_id,group_id,membership_type,source_provider_id,source_mapping_id,valid_from,status,created_at,updated_at) VALUES ('member','u','group','EXTERNAL_SYNC','provider','sync',"+start+",'ACTIVE',now(),now())");
        sql("INSERT INTO hidra_identity_group_role_grant (id,group_id,role_id,valid_from,status,created_at) VALUES ('gr','group','role',"+start+",'ACTIVE',now())");
        var d=decision(AuthorizationScope.global(),null);
        assertEquals(AuthorizationDecisionValue.PERMIT,d.decision());
        assertTrue(d.externalClaimsUsed().contains("SYNC_MEMBERSHIP"));
        sql("UPDATE hidra_identity_external_group_mapping SET status='INACTIVE'");
        assertEquals(AuthorizationDecisionValue.DENY,decision(AuthorizationScope.global(),null).decision());
    }
    @Test void staleOrMismatchedExternalAssertionsFailClosed() throws Exception {
        var a=external();
        var stale=new VerifiedAuthorizationAssertion(a.userId(),a.providerId(),a.externalIdentityId(),a.subject(),at,a.claims());
        assertEquals("EXTERNAL_IDENTITY_INELIGIBLE",decision(AuthorizationScope.global(),stale).reasonCode());
        var wrong=new VerifiedAuthorizationAssertion(a.userId(),a.providerId(),a.externalIdentityId(),"wrong",at.plusSeconds(60),a.claims());
        assertEquals("EXTERNAL_IDENTITY_INELIGIBLE",decision(AuthorizationScope.global(),wrong).reasonCode());
    }
    @Test void policiesUseTypedSubjectAttributesAndRejectOverlappingVersions() throws Exception {
        sql("INSERT INTO hidra_identity_attribute_definition (id,code,name,attribute_target,data_type,multi_valued,sensitive,status,created_at,updated_at) VALUES ('attr','clearance','Clearance','USER','NUMBER',false,true,'ACTIVE',now(),now())");
        sql("INSERT INTO hidra_identity_subject_security_attribute (id,subject_type,subject_id,attribute_definition_id,attribute_value,valid_from,status,created_at,updated_at) VALUES ('value','USER','u','attr','2',"+start+",'ACTIVE',now(),now())");
        sql("INSERT INTO hidra_identity_authorization_policy (id,code,name,policy_domain,status,created_at,updated_at) VALUES ('policy','policy','Policy','pipeline','ACTIVE',now(),now())");
        sql("INSERT INTO hidra_identity_authorization_policy_version (id,policy_id,version_number,status,effective_from,created_at) VALUES ('version','policy',1,'ACTIVE',"+start+",now())");
        sql("INSERT INTO hidra_identity_authorization_policy_rule (id,policy_version_id,rule_code,effect,priority,subject_expression,status,created_at) VALUES ('rule','version','rule','PERMIT',1,'{\"op\":\"eq\",\"attribute\":\"subject.attributes.clearance\",\"type\":\"NUMBER\",\"value\":2}','ACTIVE',now())");
        assertEquals(AuthorizationDecisionValue.PERMIT,decision(AuthorizationScope.global(),null).decision());
        sql("INSERT INTO hidra_identity_authorization_policy_version (id,policy_id,version_number,status,effective_from,created_at) VALUES ('overlap','policy',2,'ACTIVE',"+start+",now())");
        assertEquals(AuthorizationDecisionValue.INDETERMINATE,decision(AuthorizationScope.global(),null).decision());
    }
    @Test void decisionEvidenceIsStoredAsJsonArraysRatherThanQuotedJsonStrings() throws Exception {
        direct("GRANT"); var d=decision(AuthorizationScope.global(),null);
        try(EntityManager em=factory.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(new AuthorizationDecisionJpaEntity(d.id(),d.userId(),d.permissionCode(),d.resourceType(),d.resourceReferenceId(),ScopeType.GLOBAL,null,null,d.decision(),d.reasonCode(),d.reasonMessage(),d.matchedGrantIds(),d.matchedPolicyRuleIds(),d.externalClaimsUsed(),d.evaluatedAt(),null,null));
            em.getTransaction().commit();
        }
        try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());var s=c.createStatement();
            var rs=s.executeQuery("SELECT jsonb_typeof(matched_grant_ids),matched_grant_ids->>0,jsonb_typeof(matched_policy_rule_ids),jsonb_typeof(external_claims_used) FROM hidra_identity_authorization_decision")) {
            assertTrue(rs.next()); assertEquals("array",rs.getString(1)); assertEquals("direct",rs.getString(2)); assertEquals("array",rs.getString(3)); assertEquals("array",rs.getString(4));
        }
    }
}
