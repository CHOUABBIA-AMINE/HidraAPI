/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcProtectedAuthorizationEvidenceReaderTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Verifies fenced authorization evidence and conservative rejection within the registered G scope.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyOperationAuthenticationContract.Evidence;
import dz.sh.hidra.modules.identity.domain.policy.*;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.*;

import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.*;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.nio.file.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;

class JdbcProtectedAuthorizationEvidenceReaderTest {
    @Test void structuralAppointmentRequestsCannotSelectQualifiedOrganizationFamily() {
        for (var action : dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Action.values()) {
            for (var kind : dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.ResourceKind.values()) {
                if (action == dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Action.READ_POLICY
                        && kind == dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.ResourceKind.EMPLOYEE) continue;
                var scope = kind == dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.ResourceKind.UNIT
                        ? ScopeType.ORGANIZATION_UNIT : ScopeType.CUSTOM;
                var request = new AuthorizationEvaluationRequest(USER,
                        dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.permissionCode(action, kind),
                        kind.resourceType(), "exact", new AuthorizationScope(scope, "exact", null));
                assertThatThrownBy(() -> reader.load(identity, request, 7, 11)).isInstanceOf(RuntimeException.class);
            }
        }
    }

    @Test void organizationReaderRejectsMissingNativeOwnerBeforeAnySql() {
        var request = new dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request(
                dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Action.READ_POLICY,
                List.of(new dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.CompanyUnit("company", "unit")), null);
        clearInvocations(jdbc);
        assertThatThrownBy(() -> reader.loadQualifiedOrganizationGroup(null, request)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> reader.recheckOrganizationGroup(null, request)).isInstanceOf(RuntimeException.class);
        verifyNoInteractions(jdbc);
    }
    private static final Instant AT = Instant.parse("2026-10-15T10:00:00Z");
    private static final String USER = "user", PROVIDER = "provider", PERMISSION = "permission";
    private final JdbcTemplate jdbc = mock(JdbcTemplate.class);
    private final JdbcProtectedAuthorizationEvidenceReader reader =
            new JdbcProtectedAuthorizationEvidenceReader(jdbc);
    private final Map<String, List<Map<String, Object>>> tables = new HashMap<>();
    private boolean coordinates = true, physical = true;
    private List<String> fences;
    private final List<String> sqls = new ArrayList<>();
    private Evidence identity;
    private AuthorizationEvaluationRequest request;

    private static Map<String, Object> row(Object... fields) {
        Map<String, Object> out = new LinkedHashMap<>();
        for (int i = 0; i < fields.length; i += 2) out.put((String) fields[i], fields[i + 1]);
        return out;
    }

    private void table(String name, Map<String, Object>... rows) {
        tables.put(name, new ArrayList<>(Arrays.asList(rows)));
    }

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setup() {
        TransactionSynchronizationManager.setActualTransactionActive(true);
        identity =
                new Evidence(
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        USER,
                        PROVIDER,
                        "LOCAL",
                        1,
                        null,
                        null,
                        "credential",
                        1L,
                        "hidra-api",
                        UUID.randomUUID().toString(),
                        AT,
                        AT.plusSeconds(60),
                        null);
        request =
                new AuthorizationEvaluationRequest(
                        USER,
                        "topology:station-revision:author",
                        "STATION_GRAPH_REVISION",
                        "facility",
                        new AuthorizationScope(ScopeType.FACILITY, "facility", null));
        fences =
                List.of(
                                "attribute_definition",
                                "authorization_policy",
                                "authorization_policy_rule",
                                "authorization_policy_version",
                                "external_group_mapping",
                                "external_identity",
                                "external_permission_mapping",
                                "external_role_mapping",
                                "group",
                                "group_role_grant",
                                "local_credential",
                                "login_session",
                                "operation_binding",
                                "permission",
                                "provider",
                                "role",
                                "role_permission_grant",
                                "subject_security_attribute",
                                "user",
                                "user_group_membership",
                                "user_permission_grant",
                                "user_role_grant")
                        .stream()
                        .map(v -> "public.hidra_identity_" + v)
                        .toList();
        when(jdbc.queryForObject(anyString(), eq(Boolean.class), any(Object[].class)))
                .thenAnswer(a -> coordinates && physical);
        when(jdbc.queryForList(anyString(), eq(String.class))).thenAnswer(a -> fences);
        when(jdbc.query(anyString(), any(RowMapper.class), any(Object[].class)))
                .thenAnswer(
                        a -> {
                            String sql = a.getArgument(0);
                            sqls.add(sql);
                            String table =
                                    sql.split(" FROM public.hidra_identity_")[1]
                                            .split(" WHERE ")[0];
                            List<Object> result = new ArrayList<>();
                            RowMapper<Object> mapper = a.getArgument(1);
                            List<Map<String, Object>> rows = tables.getOrDefault(table, List.of());
                            // The bound provider is fetched separately from optional provenance
                            // providers.
                            if (table.equals("provider") && sql.contains("WHERE (false)"))
                                rows = List.of();
                            for (var fields : rows) {
                                var rs = mock(ResultSet.class);
                                var meta = mock(ResultSetMetaData.class);
                                List<String> keys = new ArrayList<>(fields.keySet());
                                when(rs.getMetaData()).thenReturn(meta);
                                when(meta.getColumnCount()).thenReturn(keys.size());
                                when(meta.getColumnLabel(anyInt()))
                                        .thenAnswer(v -> keys.get((int) v.getArgument(0) - 1));
                                when(rs.getObject(anyInt()))
                                        .thenAnswer(
                                                v ->
                                                        fields.get(
                                                                keys.get(
                                                                        (int) v.getArgument(0)
                                                                                - 1)));
                                result.add(mapper.mapRow(rs, result.size()));
                            }
                            return result;
                        });
        table("user", row("id", USER, "user_type", "HUMAN", "status", "ACTIVE"));
        table("provider", row("id", PROVIDER, "status", "ACTIVE"));
        table(
                "permission",
                row(
                        "id",
                        PERMISSION,
                        "code",
                        request.permissionCode(),
                        "permission_domain",
                        "TOPOLOGY",
                        "resource_type",
                        request.resourceType(),
                        "action",
                        "AUTHOR",
                        "status",
                        "ACTIVE"));
        table(
                "user_permission_grant",
                direct("grant", "GRANT", AT.minusSeconds(1), null, "GLOBAL", null));
    }

    @AfterEach
    void clear() {
        TransactionSynchronizationManager.clear();
    }

    @Test
    void c3QualifiedOverloadCannotBeReachedFromPublicStructuralEvidence() {
        assertThatThrownBy(() -> reader.loadQualified(null, request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Protected authorization evidence is unavailable or corrupt.");
        assertThat(
                        Arrays.stream(
                                        JdbcProtectedAuthorizationEvidenceReader.class
                                                .getDeclaredMethods())
                                .filter(m -> m.getName().equals("loadQualified"))
                                .toList())
                .hasSize(1)
                .allSatisfy(m -> assertThat(m.getParameterTypes()[0])
                        .isEqualTo(
                                dz.sh.hidra.modules.identity.infrastructure.security
                                        .ProtectedOperationIdentityParticipant.QualifiedClaimRead.class));
        assertThat(decision(load())).isEqualTo(AuthorizationDecisionValue.PERMIT);
    }

    private Map<String, Object> direct(
            String id, String effect, Instant from, Instant to, String scope, String ref) {
        return row(
                "id",
                id,
                "permission_id",
                PERMISSION,
                "effect",
                effect,
                "status",
                "ACTIVE",
                "valid_from",
                Timestamp.from(from),
                "valid_to",
                to == null ? null : Timestamp.from(to),
                "scope_type",
                scope,
                "scope_reference_id",
                ref);
    }

    private AuthorizationEvidence load() {
        return reader.load(identity, request, 7, 11);
    }

    @Test void qualifiedPhysicalFenceProbeNeverReusesAPreviousSuccess() {
        clearInvocations(jdbc);
        org.springframework.test.util.ReflectionTestUtils.invokeMethod(reader,"qualifiedPhysicalFences",7,11L);
        coordinates=false;
        assertThatThrownBy(()->org.springframework.test.util.ReflectionTestUtils.invokeMethod(reader,"qualifiedPhysicalFences",7,11L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Protected authorization evidence is unavailable or corrupt.").hasNoCause();
        verify(jdbc,times(2)).queryForObject(anyString(),eq(Boolean.class),eq(7),eq(11L));
        verifyNoMoreInteractions(jdbc);
    }

    @Test void qualifiedPhysicalFenceProbeRejectsUnknownOrUnavailableEvidence() {
        when(jdbc.queryForObject(anyString(),eq(Boolean.class),any(Object[].class))).thenReturn((Boolean)null);
        assertThatThrownBy(()->org.springframework.test.util.ReflectionTestUtils.invokeMethod(reader,"qualifiedPhysicalFences",7,11L))
                .isInstanceOf(IllegalStateException.class).hasMessage("Protected authorization evidence is unavailable or corrupt.");
        when(jdbc.queryForObject(anyString(),eq(Boolean.class),any(Object[].class))).thenThrow(new IllegalArgumentException("synthetic driver failure"));
        assertThatThrownBy(()->org.springframework.test.util.ReflectionTestUtils.invokeMethod(reader,"qualifiedPhysicalFences",7,11L))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("synthetic driver failure");
    }

    private AuthorizationDecisionValue decision(AuthorizationEvidence e) {
        return new AuthorizationPolicyEvaluator().evaluate(request, e, AT).decision();
    }

    private void rejects() {
        assertThatThrownBy(this::load)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Protected authorization evidence is unavailable or corrupt.")
                .hasNoCause();
    }

    @Test
    void requiresWritableReadCommittedParticipation() throws Exception {
        assertThat(
                        JdbcProtectedAuthorizationEvidenceReader.class
                                .getMethod(
                                        "load",
                                        Evidence.class,
                                        AuthorizationEvaluationRequest.class,
                                        int.class,
                                        long.class)
                                .getAnnotation(Transactional.class)
                                .propagation())
                .isEqualTo(Propagation.MANDATORY);
        TransactionSynchronizationManager.setActualTransactionActive(false);
        rejects();
        assertThat(sqls).isEmpty();
        TransactionSynchronizationManager.setActualTransactionActive(true);
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(true);
        rejects();
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(false);
        physical = false;
        rejects();
        physical = true;
        assertThat(decision(load())).isEqualTo(AuthorizationDecisionValue.PERMIT);
    }

    @Test
    void requiresMatchingPhysicalCoordinatesAndCompleteIdentityFences() {
        coordinates = false;
        rejects();
        assertThat(sqls).isEmpty();
        coordinates = true;
        var complete = fences;
        fences = fences.subList(0, 21);
        rejects();
        assertThat(sqls).isEmpty();
        fences = complete;
        assertThat(decision(load())).isEqualTo(AuthorizationDecisionValue.PERMIT);
        when(jdbc.queryForObject(anyString(), eq(Boolean.class), any(Object[].class)))
                .thenReturn(true, false);
        rejects();
    }

    @Test
    void rejectsCallerActorResourceAndScopeMismatches() {
        for (var bad :
                List.of(
                        new AuthorizationEvaluationRequest(
                                "other",
                                request.permissionCode(),
                                request.resourceType(),
                                "facility",
                                request.scope()),
                        new AuthorizationEvaluationRequest(
                                USER,
                                request.permissionCode(),
                                "OTHER",
                                "facility",
                                request.scope()),
                        new AuthorizationEvaluationRequest(
                                USER,
                                request.permissionCode(),
                                request.resourceType(),
                                "other",
                                request.scope()),
                        new AuthorizationEvaluationRequest(
                                USER,
                                request.permissionCode(),
                                request.resourceType(),
                                "facility",
                                AuthorizationScope.global()),
                        new AuthorizationEvaluationRequest(
                                USER,
                                "identity:admin:*",
                                request.resourceType(),
                                "facility",
                                request.scope()))) {
            assertThatThrownBy(() -> reader.load(identity, bad, 7, 11))
                    .isInstanceOf(IllegalStateException.class);
        }
        assertThat(sqls).isEmpty();
        var company =
                new AuthorizationEvaluationRequest(
                        USER,
                        request.permissionCode(),
                        request.resourceType(),
                        "facility",
                        new AuthorizationScope(ScopeType.CUSTOM, "topology-company:company", null));
        assertThat(reader.load(identity, company, 7, 11).failure()).isNull();
        var expired =
                new Evidence(
                        identity.bindingId(),
                        identity.sessionId(),
                        USER,
                        PROVIDER,
                        "LOCAL",
                        1,
                        null,
                        null,
                        "credential",
                        1L,
                        "hidra-api",
                        identity.jti(),
                        AT,
                        AT,
                        null);
        assertThatThrownBy(() -> reader.load(expired, company, 7, 11))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void preservesGrantOrderScopeWindowsAndDenyPrecedence() {
        table(
                "user_permission_grant",
                direct("permit", "GRANT", AT, null, "GLOBAL", null),
                direct("ended", "DENY", AT.minusSeconds(1), AT, "GLOBAL", null),
                direct("future", "DENY", AT.plusSeconds(1), null, "GLOBAL", null),
                direct("other", "DENY", AT, null, "FACILITY", "other"));
        assertThat(load().grants()).extracting(g -> g.ids().getFirst()).containsExactly("permit");
        table(
                "user_role_grant",
                row(
                        "id",
                        "ur",
                        "role_id",
                        "role",
                        "status",
                        "ACTIVE",
                        "valid_from",
                        Timestamp.from(AT)));
        table("role", row("id", "role", "status", "ACTIVE"));
        table(
                "role_permission_grant",
                row(
                        "id",
                        "rp",
                        "role_id",
                        "role",
                        "permission_id",
                        PERMISSION,
                        "status",
                        "ACTIVE",
                        "effect",
                        "GRANT",
                        "valid_from",
                        Timestamp.from(AT)));
        assertThat(load().grants())
                .extracting(AuthorizationEvidence.Grant::order)
                .containsExactly(0, 1);
        table(
                "user_group_membership",
                row(
                        "id",
                        "membership",
                        "group_id",
                        "group",
                        "membership_type",
                        "DIRECT",
                        "status",
                        "ACTIVE",
                        "valid_from",
                        Timestamp.from(AT)));
        table("group", row("id", "group", "status", "ACTIVE"));
        table(
                "group_role_grant",
                row(
                        "id",
                        "gr",
                        "group_id",
                        "group",
                        "role_id",
                        "role",
                        "status",
                        "ACTIVE",
                        "valid_from",
                        Timestamp.from(AT)));
        assertThat(load().grants())
                .extracting(AuthorizationEvidence.Grant::order)
                .containsExactly(0, 1, 2);
        tables.get("user_permission_grant")
                .add(direct("deny", "DENY", AT, null, "FACILITY", "facility"));
        assertThat(decision(load())).isEqualTo(AuthorizationDecisionValue.DENY);
    }

    @Test
    void rejectsUnknownClaimCoverageInsteadOfAssumingAbsence() {
        for (String name :
                List.of(
                        "external_group_mapping",
                        "external_role_mapping",
                        "external_permission_mapping")) {
            table(
                    name,
                    row(
                            "id",
                            "mapping",
                            "identity_provider_id",
                            PROVIDER,
                            "status",
                            "ACTIVE",
                            "mapping_mode",
                            name.contains("group") ? "ASSERTION_ONLY" : "DIRECT_GRANT"));
            assertThat(decision(load())).isEqualTo(AuthorizationDecisionValue.INDETERMINATE);
            tables.remove(name);
        }
        table(
                "external_role_mapping",
                row("id", "new-empty-claim", "status", "ACTIVE", "mapping_mode", "DIRECT_GRANT"));
        tables.get("user_permission_grant").add(direct("deny", "DENY", AT, null, "GLOBAL", null));
        assertThat(decision(load())).isEqualTo(AuthorizationDecisionValue.DENY);
    }

    @Test
    void preservesTypedAttributesAndConflicts() {
        table(
                "attribute_definition",
                row(
                        "id",
                        "definition",
                        "code",
                        "clearance",
                        "attribute_target",
                        "USER",
                        "data_type",
                        "JSON",
                        "multi_valued",
                        false,
                        "status",
                        "ACTIVE"));
        table(
                "subject_security_attribute",
                row(
                        "id",
                        "attr",
                        "subject_type",
                        "USER",
                        "subject_id",
                        USER,
                        "attribute_definition_id",
                        "definition",
                        "attribute_value",
                        "{\"levels\":[1,null,{\"ok\":true}]}",
                        "status",
                        "ACTIVE"));
        var e = load();
        assertThat(e.failure()).isNull();
        var value = (Map<?, ?>) e.attributes().get("subject.attributes.clearance");
        assertThatThrownBy(() -> ((List<?>) value.get("levels")).clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(value::clear).isInstanceOf(UnsupportedOperationException.class);
        tables.get("subject_security_attribute")
                .add(
                        row(
                                "id",
                                "conflict",
                                "subject_type",
                                "USER",
                                "subject_id",
                                USER,
                                "attribute_definition_id",
                                "definition",
                                "attribute_value",
                                "{}",
                                "status",
                                "ACTIVE"));
        assertThat(decision(load())).isEqualTo(AuthorizationDecisionValue.INDETERMINATE);
    }

    @Test
    void boundsRowsJsonAndUnsupportedEvidence() {
        var base = tables.get("user_permission_grant").getFirst();
        tables.put("user_permission_grant", Collections.nCopies(1025, base));
        rejects();
        table("user_permission_grant", base);
        table(
                "attribute_definition",
                row(
                        "id",
                        "d",
                        "code",
                        "x",
                        "attribute_target",
                        "USER",
                        "data_type",
                        "JSON",
                        "multi_valued",
                        false,
                        "status",
                        "ACTIVE"));
        table(
                "subject_security_attribute",
                row(
                        "id",
                        "a",
                        "subject_type",
                        "USER",
                        "subject_id",
                        USER,
                        "attribute_definition_id",
                        "d",
                        "attribute_value",
                        "[".repeat(17) + "0" + "]".repeat(17),
                        "status",
                        "ACTIVE"));
        assertThat(decision(load())).isEqualTo(AuthorizationDecisionValue.INDETERMINATE);
        table("authorization_policy", row("id", "policy", "status", "ACTIVE"));
        table(
                "authorization_policy_version",
                row(
                        "id",
                        "v",
                        "policy_id",
                        "policy",
                        "status",
                        "ACTIVE",
                        "effective_from",
                        Timestamp.from(AT)));
        table(
                "authorization_policy_rule",
                row(
                        "id",
                        "r",
                        "policy_version_id",
                        "v",
                        "priority",
                        1,
                        "effect",
                        "PERMIT",
                        "status",
                        "ACTIVE",
                        "context_expression",
                        "{\"op\":\"exists\",\"attribute\":\"context.evaluatedAt\"}"));
        assertThat(decision(load())).isEqualTo(AuthorizationDecisionValue.INDETERMINATE);
        when(jdbc.queryForObject(anyString(), eq(Boolean.class), any(Object[].class)))
                .thenThrow(new IllegalArgumentException("private SQL values"));
        rejects();
    }

    @Test
    void hasNoJpaCacheWallClockDecisionPersistenceOrAuthorityExport() throws Exception {
        assertThat(decision(load())).isEqualTo(AuthorizationDecisionValue.PERMIT);
        assertThat(sqls)
                .allSatisfy(
                        sql ->
                                assertThat(sql)
                                        .startsWith("SELECT ")
                                        .contains("ORDER BY id LIMIT 1025")
                                        .doesNotContain(
                                                "SELECT *", " OFFSET ", "password", "token"));
        assertThat(sqls).hasSizeLessThanOrEqualTo(22);
        String source =
                Files.readString(
                        Path.of(
                                "src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcProtectedAuthorizationEvidenceReader.java"));
        assertThat(source)
                .doesNotContain(
                        "Instant.now(",
                        "clock_timestamp(",
                        "EntityManager",
                        "REQUIRES_NEW",
                        "INSERT INTO",
                        "UPDATE public.",
                        "VerifiedAuthorizationAssertion",
                        "getConnection(");
        assertThat(
                        Arrays.stream(
                                        JdbcProtectedAuthorizationEvidenceReader.class
                                                .getDeclaredMethods())
                                .filter(m -> java.lang.reflect.Modifier.isPublic(m.getModifiers())))
                .extracting(java.lang.reflect.Method::getName)
                .containsExactlyInAnyOrder("load", "loadQualified", "loadQualifiedPair", "loadQualifiedDraftPair",
                        "loadQualifiedOrganizationGroup", "recheckOrganizationGroup");
        for (String name : List.of("loadQualifiedOrganizationGroup", "recheckOrganizationGroup")) {
            assertThat(Arrays.stream(JdbcProtectedAuthorizationEvidenceReader.class.getDeclaredMethods())
                    .filter(m -> m.getName().equals(name))).singleElement().satisfies(method -> {
                assertThat(method.getParameterTypes()).containsExactly(
                        dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant.OrganizationClaimGroup.class,
                        dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request.class);
                assertThat(method.getReturnType()).isEqualTo(JdbcProtectedAuthorizationEvidenceReader.OrganizationAuthorizationEvidence.class);
            });
        }
        assertThat(Arrays.stream(JdbcProtectedAuthorizationEvidenceReader.class.getDeclaredMethods())
                .filter(m -> m.getName().equals("loadQualifiedDraftPair")))
            .singleElement().satisfies(method -> {
                assertThat(method.getParameterTypes()).containsExactly(
                    dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant.DraftClaimPair.class,
                    dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Request.class);
                assertThat(method.getReturnType()).isEqualTo(JdbcProtectedAuthorizationEvidenceReader.PairedAuthorizationEvidence.class);
            });
        assertThat(Arrays.stream(JdbcProtectedAuthorizationEvidenceReader.class.getDeclaredMethods())
                .filter(m -> m.getName().equals("loadQualifiedPair")))
            .singleElement().satisfies(method -> {
                assertThat(method.getParameterTypes()).containsExactly(
                    dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant.QualifiedClaimPair.class,
                    dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Request.class);
                assertThat(method.getReturnType()).isEqualTo(
                    JdbcProtectedAuthorizationEvidenceReader.PairedAuthorizationEvidence.class);
            });
    }
    @Test void structuralDiagnosticEvidenceCannotSelectEngineeringDraftFamily(){
        var network=new AuthorizationEvaluationRequest(USER,"topology:engineering-draft:read","ENGINEERING_NETWORK_DRAFT","network",
            new AuthorizationScope(ScopeType.CUSTOM,"topology-network:network",null));
        assertThatThrownBy(()->reader.load(identity,network,7,11)).isInstanceOf(RuntimeException.class);
    }
    @Test void fakeDraftPairCannotBypassGenuineIssuerTransport(){
        var request=new dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Request(
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Action.READ,"company","network");
        assertThatThrownBy(()->reader.loadQualifiedDraftPair(null,request)).isInstanceOf(RuntimeException.class);
    }


    @Test void publicationDiagnosticsAndForgedPairsNeverSelectThePrivateQualifiedFamily() {
        for(String action:List.of("publish","withdraw","reactivate","read")) {
            var structural=new AuthorizationEvaluationRequest(USER,"topology:engineering-publication:"+action,"ENGINEERING_NETWORK_PUBLICATION","network",
                new AuthorizationScope(ScopeType.CUSTOM,"topology-network:network",null));
            assertThatThrownBy(()->reader.load(identity,structural,7,11)).isInstanceOf(RuntimeException.class);
            var request=new dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Request(
                dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Action.valueOf("PUBLICATION_"+action.toUpperCase(Locale.ROOT)),"company","network");
            assertThatThrownBy(()->reader.loadQualifiedDraftPair(null,request)).isInstanceOf(RuntimeException.class);
        }
    }

}
