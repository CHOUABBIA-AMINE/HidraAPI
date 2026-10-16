/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalMappingGenerationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-16
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Qualifies a bounded read-only mapping-name selector against PostgreSQL 16 and fresh Flyway schema.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.port.out.ClaimNameCatalogPort;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Only synthetic Identity catalog rows are written. This selector neither proves
 * verified LDAP/OIDC source coverage nor participates in a protected PERMIT decision.
 */
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = HidraApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class ExternalMappingGenerationPostgresIntegrationTest {
    private static final String PREFIX = "public.hidra_identity_";
    private static final String UNAVAILABLE = "External claim-name catalog unavailable.";

    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("hidra_claim_name_test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        registry.add("spring.flyway.enabled", () -> "true");
        registry.add("spring.flyway.validate-on-migrate", () -> "true");
    }

    @Autowired JdbcTemplate jdbc;
    @Autowired Flyway flyway;
    @Autowired ClaimNameCatalogPort catalog;

    private String provider;
    private String group;
    private String role;
    private String permission;

    @BeforeEach
    void freshFixture() {
        flyway.validate();
        assertThat(flyway.info().pending()).isEmpty();
        provider = key();
        group = key();
        role = key();
        permission = key();
        jdbc.update(
                "INSERT INTO " + PREFIX + "provider "
                        + "(id,code,name,provider_type,issuer_uri,sync_enabled,"
                        + "just_in_time_provisioning_enabled,status,created_at,updated_at)"
                        + " VALUES (?,?,'Synthetic OIDC provider','OIDC',?,false,false,'ACTIVE',now(),now())",
                provider, provider, "https://synthetic." + provider + ".invalid");
        jdbc.update(
                "INSERT INTO " + PREFIX + "group "
                        + "(id,code,group_type,status,created_at,updated_at)"
                        + " VALUES (?,?,'EXTERNAL_MAPPED','ACTIVE',now(),now())",
                group, group);
        jdbc.update(
                "INSERT INTO " + PREFIX + "role "
                        + "(id,code,role_type,status,created_at,updated_at)"
                        + " VALUES (?,?,'EXTERNAL_MAPPED','ACTIVE',now(),now())",
                role, role);
        jdbc.update(
                "INSERT INTO " + PREFIX + "permission "
                        + "(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)"
                        + " VALUES (?,?,'TOPOLOGY','TOPOLOGY','read',false,'ACTIVE',now(),now())",
                permission, "identity:catalog:" + permission);
    }

    private static String key() {
        return UUID.randomUUID().toString();
    }

    private void groupMapping(String claim, String mode, String status) {
        jdbc.update(
                "INSERT INTO " + PREFIX + "external_group_mapping "
                        + "(id,identity_provider_id,group_id,external_group_name,claim_name,"
                        + "mapping_mode,auto_create_membership,status,created_at,updated_at)"
                        + " VALUES (?,?,?,'Synthetic dispatch',?,?,false,?,now(),now())",
                key(), provider, group, claim, mode, status);
    }

    private void roleMapping(String claim, String mode, String status) {
        jdbc.update(
                "INSERT INTO " + PREFIX + "external_role_mapping "
                        + "(id,identity_provider_id,role_id,external_role_code,claim_name,"
                        + "mapping_mode,status,created_at,updated_at)"
                        + " VALUES (?,?,?,'operator',?,?,?,now(),now())",
                key(), provider, role, claim, mode, status);
    }

    private void permissionMapping(String claim, String mode, String status) {
        jdbc.update(
                "INSERT INTO " + PREFIX + "external_permission_mapping "
                        + "(id,identity_provider_id,permission_id,external_permission_code,"
                        + "claim_name,mapping_mode,effect,status,created_at,updated_at)"
                        + " VALUES (?,?,?,'read',?,?,'DENY',?,now(),now())",
                key(), provider, permission, claim, mode, status);
    }

    private void denied(String providerId) {
        assertThatThrownBy(() -> catalog.select(providerId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(UNAVAILABLE)
                .satisfies(e -> assertThat(e.getCause()).isNull());
    }


    private long generation(String id) {
        return jdbc.queryForObject("SELECT generation FROM public.hidra_identity_external_mapping_generation WHERE provider_id=?",Long.class,id);
    }

    @Test
    void generationInitializedAndChangedByAllThreeFamilies() {
        assertThat(generation(provider)).isEqualTo(1);
        groupMapping("groups","ASSERTION_ONLY","ACTIVE");
        roleMapping("roles","DIRECT_GRANT","ACTIVE");
        permissionMapping("permissions","DIRECT_GRANT","ACTIVE");
        assertThat(generation(provider)).isEqualTo(4);
        jdbc.update("UPDATE " + PREFIX + "external_role_mapping SET claim_name=claim_name WHERE identity_provider_id=?",provider);
        assertThat(generation(provider)).isEqualTo(5);
    }

    @Test
    void generationAbaInsertDeleteDoesNotReuseOldRevision() {
        roleMapping("roles","DIRECT_GRANT","ACTIVE");
        jdbc.update("DELETE FROM " + PREFIX + "external_role_mapping WHERE identity_provider_id=?",provider);
        assertThat(catalog.select(provider).names()).isEmpty();
        assertThat(generation(provider)).isEqualTo(3);
    }

    @Test
    void generationProviderReassignmentIncrementsBoth() {
        String second=key();
        jdbc.update("INSERT INTO " + PREFIX + "provider "
           + "(id,code,name,provider_type,issuer_uri,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at)"
           + " VALUES (?,?,'Synthetic second provider','OIDC',?,false,false,'ACTIVE',now(),now())",
           second,second,"https://synthetic."+second+".invalid");
        roleMapping("roles","DIRECT_GRANT","ACTIVE");
        jdbc.update("UPDATE " + PREFIX + "external_role_mapping SET identity_provider_id=? WHERE identity_provider_id=?",
            second,provider);
        assertThat(generation(provider)).isEqualTo(3);
        assertThat(generation(second)).isEqualTo(2);
    }

    @Test
    void generationOverflowAndTruncateAreFailClosed() {
        assertThatThrownBy(() -> jdbc.execute("TRUNCATE " + PREFIX + "external_role_mapping")).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> jdbc.execute("TRUNCATE public.hidra_identity_external_mapping_generation")).isInstanceOf(RuntimeException.class);
        jdbc.update("UPDATE public.hidra_identity_external_mapping_generation SET generation=9223372036854775807 WHERE provider_id=?",provider);
        assertThatThrownBy(() -> roleMapping("roles","DIRECT_GRANT","ACTIVE")).isInstanceOf(RuntimeException.class);
        assertThat(generation(provider)).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    void generationRejectsElevatedDefaultRuntimeReader() {
        assertThatThrownBy(() -> catalog.selectVersioned(provider))
            .isInstanceOf(IllegalStateException.class).hasMessage(UNAVAILABLE);
        assertThat(catalog.select(provider).names()).isEmpty();
    }

    @Test
    void generationNonOwnerRoleCannotResetButCanSelectAtomicSnapshotAndWrite() {
        String user="b2_"+key().replace("-","");
        jdbc.execute("CREATE ROLE "+user+" LOGIN PASSWORD 'fixture_only_b2'");
        try {
            jdbc.execute("GRANT USAGE ON SCHEMA public TO "+user);
            jdbc.execute("GRANT SELECT ON "+PREFIX+"provider, "+PREFIX+"external_group_mapping, "
                +PREFIX+"external_role_mapping, "+PREFIX+"external_permission_mapping, "
                +"public.hidra_identity_external_mapping_generation TO "+user);
            jdbc.execute("GRANT INSERT, UPDATE, DELETE ON "+PREFIX+"external_group_mapping, "
                +PREFIX+"external_role_mapping, "+PREFIX+"external_permission_mapping TO "+user);
            JdbcTemplate ordinary=new JdbcTemplate(new DriverManagerDataSource(POSTGRES.getJdbcUrl(),user,"fixture_only_b2"));
            assertThatThrownBy(() -> ordinary.update("UPDATE public.hidra_identity_external_mapping_generation SET generation=1 WHERE provider_id=?",provider))
                .isInstanceOf(RuntimeException.class);
            var selector=new dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcClaimNameCatalogAdapter(ordinary);
            assertThat(selector.selectVersioned(provider).generation()).isEqualTo(1);
            String mapping=key();
            ordinary.update("INSERT INTO "+PREFIX+"external_role_mapping "
                +"(id,identity_provider_id,role_id,external_role_code,claim_name,mapping_mode,status,created_at,updated_at)"
                +" VALUES (?,?,?,'operator','roles','DIRECT_GRANT','ACTIVE',now(),now())",mapping,provider,role);
            var snap=selector.selectVersioned(provider);
            assertThat(snap.generation()).isEqualTo(2);
            assertThat(snap.selection().names()).containsExactly("roles");
            ordinary.update("DELETE FROM "+PREFIX+"external_role_mapping WHERE id=?",mapping);
            assertThat(generation(provider)).isEqualTo(3);
        } finally {
            jdbc.execute("DROP OWNED BY "+user);
            jdbc.execute("DROP ROLE "+user);
        }
    }

    @Test
    void freshFlywayChainAndSelectedAdapterExist() {
        flyway.validate();
        assertThat(flyway.info().pending()).isEmpty();
        assertThat(catalog)
                .isInstanceOf(
                        dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
                                .JdbcClaimNameCatalogAdapter.class);
        assertThat(catalog.select(provider).names()).isEmpty();
    }

    @Test
    void activeGroupRoleAndPermissionProvideOnlyNames() {
        groupMapping("groups", "ASSERTION_ONLY", "ACTIVE");
        roleMapping("roles", "DIRECT_GRANT", "ACTIVE");
        permissionMapping("permissions", "DIRECT_GRANT", "ACTIVE");
        var selected = catalog.select(provider);
        assertThat(selected.names()).containsExactly("groups", "permissions", "roles");
        assertThat(selected.toString()).contains("authority=none")
                .doesNotContain("groups", "roles", "permissions");
    }

    @Test
    void exactCaseAndDeduplicatedNamesAreSortedAndImmutable() {
        groupMapping("roles", "ASSERTION_ONLY", "ACTIVE");
        roleMapping("roles", "DIRECT_GRANT", "ACTIVE");
        permissionMapping("Roles", "DIRECT_GRANT", "ACTIVE");
        var selection = catalog.select(provider);
        assertThat(selection.names()).containsExactly("Roles", "roles");
        assertThatThrownBy(() -> selection.names().add("extra"))
                .isInstanceOf(UnsupportedOperationException.class);
        var source = new ArrayList<>(List.of("b", "a"));
        var copied = new ClaimNameCatalogPort.Selection(source);
        source.clear();
        assertThat(copied.names()).containsExactly("a", "b");
        assertThatThrownBy(
                () -> new ClaimNameCatalogPort.Selection(List.of("dup", "dup")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void inactiveAndNonClaimDependentMappingsRemainIrrelevant() {
        groupMapping(null, "SYNC_MEMBERSHIP", "ACTIVE");
        groupMapping(null, "MANUAL_APPROVAL", "ACTIVE");
        groupMapping(null, "DISABLED", "ACTIVE");
        roleMapping(null, "MANUAL_APPROVAL", "ACTIVE");
        roleMapping(null, "REQUIRES_LOCAL_APPROVAL", "ACTIVE");
        permissionMapping(null, "DISABLED", "ACTIVE");
        permissionMapping("ignore-inactive", "DIRECT_GRANT", "INACTIVE");
        assertThat(catalog.select(provider).names()).isEmpty();
    }

    @Test
    void missingAndInactiveProvidersFailClosedIdentically() {
        denied(key());
        jdbc.update(
                "UPDATE " + PREFIX + "provider SET status='INACTIVE' WHERE id=?", provider);
        denied(provider);
    }

    @Test
    void activeClaimDependentMappingWithoutClaimNameFailsClosed() {
        groupMapping(null, "ASSERTION_ONLY", "ACTIVE");
        denied(provider);
    }

    @Test
    void blankClaimNameFailsClosedWithoutLoggingTheValue() {
        permissionMapping(" ", "DIRECT_GRANT", "ACTIVE");
        denied(provider);
    }

    @Test
    void conflictStatusFailsClosedEvenIfAnotherNameIsValid() {
        roleMapping("valid", "DIRECT_GRANT", "ACTIVE");
        groupMapping("conflict", "ASSERTION_ONLY", "CONFLICT");
        denied(provider);
    }

    @Test
    void unsupportedFamilyModeFailsClosed() {
        groupMapping("group", "DIRECT_GRANT", "ACTIVE");
        denied(provider);
    }

    @Test
    void unsupportedStatusAndModeFailClosed() {
        roleMapping("roles", "DIRECT_GRANT", "ACTIVE");
        jdbc.update(
                "UPDATE " + PREFIX + "external_role_mapping SET status='UNKNOWN' "
                        + "WHERE identity_provider_id=?", provider);
        denied(provider);
        jdbc.update(
                "UPDATE " + PREFIX + "external_role_mapping SET status='ACTIVE',"
                        + " mapping_mode='SOMETHING_ELSE' WHERE identity_provider_id=?", provider);
        denied(provider);
    }

    @Test
    void rawRowLimitRejectsDuplicatesRatherThanTruncating() {
        jdbc.update(
                "INSERT INTO " + PREFIX + "external_role_mapping "
                        + "(id,identity_provider_id,role_id,external_role_code,claim_name,"
                        + "mapping_mode,status,created_at,updated_at)"
                        + " SELECT concat(?, n::text), ?, ?, 'operator', 'roles',"
                        + "'DIRECT_GRANT','ACTIVE',now(),now()"
                        + " FROM generate_series(1,1025) AS n",
                key(), provider, role);
        denied(provider);
    }

    @Test
    void distinctNameLimitRejectsOver64Names() {
        jdbc.update(
                "INSERT INTO " + PREFIX + "external_role_mapping "
                        + "(id,identity_provider_id,role_id,external_role_code,claim_name,"
                        + "mapping_mode,status,created_at,updated_at)"
                        + " SELECT concat(?, n::text), ?, ?, 'operator',"
                        + " concat('claim-', n::text), 'DIRECT_GRANT','ACTIVE',now(),now()"
                        + " FROM generate_series(1,65) AS n",
                key(), provider, role);
        denied(provider);
    }

    @Test
    void aggregateNameBytesCannotBypassTheDomainEnvelope() {
        // Every individual name fits varchar(120) and the 480-byte name limit,
        // but forty distinct names exceed ClaimCoverage's 16384-byte total envelope.
        String prefix = new String(Character.toChars(0x1F642)).repeat(118);
        for (int i = 0; i < 40; i++)
            roleMapping(prefix + String.format("%02d", i), "DIRECT_GRANT", "ACTIVE");
        denied(provider);
    }

    @Test
    void committedMappingChangeProducesDifferentNonAuthoritativeSelection() {
        roleMapping("before", "DIRECT_GRANT", "ACTIVE");
        var previous = catalog.select(provider);
        jdbc.update("UPDATE " + PREFIX + "external_role_mapping "
                + "SET claim_name='after',updated_at=now() WHERE identity_provider_id=?", provider);
        var later = catalog.select(provider);
        assertThat(previous.names()).containsExactly("before");
        assertThat(later.names()).containsExactly("after");
        assertThat(previous.names()).containsExactly("before");
    }

    @Test
    void noValuesTokensOrAuthorizationAreReturned() {
        permissionMapping("sensitive-name", "DIRECT_GRANT", "ACTIVE");
        var selected = catalog.select(provider);
        assertThat(selected.names()).containsExactly("sensitive-name");
        assertThat(selected.toString()).doesNotContain("sensitive-name");
        assertThat(ClaimNameCatalogPort.Selection.class.getRecordComponents())
                .extracting(java.lang.reflect.RecordComponent::getName)
                .containsExactly("names");
        assertThat(
                java.util.Arrays.stream(ClaimNameCatalogPort.Selection.class.getDeclaredMethods())
                        .map(java.lang.reflect.Method::getName))
                .doesNotContain("permit", "grant", "authenticate", "attest");
    }
}
