/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOperationalWorkbenchHttpExposureTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Verifies the Workbench HTTP/controller/serialization boundary cannot expose credential data.
 *
 */
package dz.sh.hidra.platform.workbench;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.LocalCredentialJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.platform.exception.HidraGlobalExceptionHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class HidraOperationalWorkbenchHttpExposureTest {

    private static final String HASH_MARKER = "$2a$10$HIDRA_P0_HTTP_PASSWORD_HASH_MARKER";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void discoverySerializesOnlyExplicitlyApprovedResources() throws Exception {
        EntityManager entityManager = mock(EntityManager.class);
        HidraOperationalWorkbenchService service = service(
                entityManager,
                "identity/users=id,username",
                UserJpaEntity.class,
                LocalCredentialJpaEntity.class
        );
        MockMvc mockMvc = mockMvc(service);

        MvcResult modules = mockMvc.perform(get("/api/v1/workbench/modules"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(modules.getResponse().getContentAsString())
                .contains("identity")
                .doesNotContain("local-credentials")
                .doesNotContain("passwordHash")
                .doesNotContain(HASH_MARKER);

        MvcResult resources = mockMvc.perform(get("/api/v1/workbench/identity/resources"))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode body = OBJECT_MAPPER.readTree(resources.getResponse().getContentAsString());

        assertThat(body).hasSize(1);
        assertThat(body.get(0).get("resource").asText()).isEqualTo("users");
        assertThat(resources.getResponse().getContentAsString())
                .doesNotContain("local-credentials")
                .doesNotContain("passwordHash")
                .doesNotContain(HASH_MARKER);
    }

    @Test
    void listHttpResponseContainsOnlyApprovedFields() throws Exception {
        EntityManager entityManager = mock(EntityManager.class);
        HidraOperationalWorkbenchService service = service(
                entityManager,
                "identity/users=id,username",
                UserJpaEntity.class,
                LocalCredentialJpaEntity.class
        );
        queryHarness(entityManager, List.of(user()), 1L);
        MockMvc mockMvc = mockMvc(service);

        MvcResult result = mockMvc.perform(get("/api/v1/workbench/identity/users"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode attributes = OBJECT_MAPPER.readTree(result.getResponse().getContentAsString())
                .get("items")
                .get(0)
                .get("attributes");

        assertThat(fieldNames(attributes)).containsExactlyInAnyOrder("id", "username");
        assertThat(result.getResponse().getContentAsString())
                .doesNotContain("emailAddress")
                .doesNotContain("displayName")
                .doesNotContain("passwordHash")
                .doesNotContain(HASH_MARKER);
    }

    @Test
    void detailHttpResponseContainsOnlyApprovedFields() throws Exception {
        EntityManager entityManager = mock(EntityManager.class);
        HidraOperationalWorkbenchService service = service(
                entityManager,
                "identity/users=id,username",
                UserJpaEntity.class,
                LocalCredentialJpaEntity.class
        );
        when(entityManager.find(UserJpaEntity.class, "user-1")).thenReturn(user());
        MockMvc mockMvc = mockMvc(service);

        MvcResult result = mockMvc.perform(get("/api/v1/workbench/identity/users/user-1"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode body = OBJECT_MAPPER.readTree(result.getResponse().getContentAsString());
        assertThat(body.get("id").asText()).isEqualTo("user-1");
        assertThat(fieldNames(body.get("attributes"))).containsExactlyInAnyOrder("id", "username");
        assertThat(result.getResponse().getContentAsString())
                .doesNotContain("emailAddress")
                .doesNotContain("displayName")
                .doesNotContain("passwordHash")
                .doesNotContain(HASH_MARKER);
    }

    @Test
    void searchHttpResponseCannotExposeOrQueryHiddenFields() throws Exception {
        EntityManager entityManager = mock(EntityManager.class);
        HidraOperationalWorkbenchService service = service(
                entityManager,
                "identity/users=id,username",
                UserJpaEntity.class,
                LocalCredentialJpaEntity.class
        );
        QueryHarness harness = queryHarness(entityManager, List.of(user()), 1L);
        stubStringSearch(harness);
        MockMvc mockMvc = mockMvc(service);

        MvcResult result = mockMvc.perform(post("/api/v1/workbench/identity/users/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "query": "alice",
                                  "filters": {
                                    "emailAddress": "hidden@example.com"
                                  },
                                  "page": 0,
                                  "size": 50,
                                  "sortBy": "emailAddress",
                                  "sortDirection": "asc"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode attributes = OBJECT_MAPPER.readTree(result.getResponse().getContentAsString())
                .get("items")
                .get(0)
                .get("attributes");

        assertThat(fieldNames(attributes)).containsExactlyInAnyOrder("id", "username");
        assertThat(result.getResponse().getContentAsString())
                .doesNotContain("hidden@example.com")
                .doesNotContain("passwordHash")
                .doesNotContain(HASH_MARKER);

        verify(harness.dataRoot(), never()).get("emailAddress");
        verify(harness.countRoot(), never()).get("emailAddress");
    }

    @Test
    void credentialResourceHttpRoutesAreDeniedBeforePersistenceAndCannotSerializeHashMarker() throws Exception {
        EntityManager entityManager = mock(EntityManager.class);
        HidraOperationalWorkbenchService service = service(
                entityManager,
                "identity/users=id,username",
                UserJpaEntity.class,
                LocalCredentialJpaEntity.class
        );
        when(entityManager.find(LocalCredentialJpaEntity.class, "credential-1"))
                .thenReturn(credentialWithHashMarker());
        MockMvc mockMvc = mockMvc(service);

        MvcResult list = mockMvc.perform(get("/api/v1/workbench/identity/local-credentials"))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertSensitiveMarkerAbsent(list);

        MvcResult detail = mockMvc.perform(get("/api/v1/workbench/identity/local-credentials/credential-1"))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertSensitiveMarkerAbsent(detail);

        MvcResult search = mockMvc.perform(post("/api/v1/workbench/identity/local-credentials/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "query": "credential-1",
                                  "filters": {},
                                  "page": 0,
                                  "size": 50
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertSensitiveMarkerAbsent(search);

        verify(entityManager, never()).find(LocalCredentialJpaEntity.class, "credential-1");
        verify(entityManager, never()).getCriteriaBuilder();
    }

    private static void assertSensitiveMarkerAbsent(MvcResult result) throws Exception {
        assertThat(result.getResponse().getContentAsString())
                .contains("Unsupported operational workbench resource")
                .doesNotContain("passwordHash")
                .doesNotContain(HASH_MARKER);
    }

    private static List<String> fieldNames(JsonNode node) {
        List<String> names = new ArrayList<>();
        node.fieldNames().forEachRemaining(names::add);
        return names;
    }

    private static MockMvc mockMvc(HidraOperationalWorkbenchService service) {
        return MockMvcBuilders
                .standaloneSetup(new HidraOperationalWorkbenchController(service))
                .setControllerAdvice(new HidraGlobalExceptionHandler(false))
                .build();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static HidraOperationalWorkbenchService service(
            EntityManager entityManager,
            String exposure,
            Class<?>... entityClasses
    ) {
        Metamodel metamodel = mock(Metamodel.class);
        Set<EntityType<?>> entityTypes = new LinkedHashSet<>();
        for (Class<?> entityClass : entityClasses) {
            EntityType entityType = mock(EntityType.class);
            when(entityType.getJavaType()).thenReturn(entityClass);
            when(entityType.getName()).thenReturn(entityClass.getSimpleName());
            entityTypes.add(entityType);
        }
        when(entityManager.getMetamodel()).thenReturn(metamodel);
        when(metamodel.getEntities()).thenReturn(entityTypes);

        return new HidraOperationalWorkbenchService(
                entityManager,
                new HidraOperationalWorkbenchExposurePolicy(exposure)
        );
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static QueryHarness queryHarness(
            EntityManager entityManager,
            List<Object> results,
            long count
    ) {
        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        CriteriaQuery<Object> dataQuery = mock(CriteriaQuery.class);
        CriteriaQuery<Long> countQuery = mock(CriteriaQuery.class);
        Root dataRoot = mock(Root.class);
        Root countRoot = mock(Root.class);
        Path dataPath = mock(Path.class);
        Path countPath = mock(Path.class);
        TypedQuery<Object> dataTypedQuery = mock(TypedQuery.class);
        TypedQuery<Long> countTypedQuery = mock(TypedQuery.class);
        Expression<Long> countExpression = mock(Expression.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(builder);
        when(builder.createQuery()).thenReturn(dataQuery);
        when(builder.createQuery(Long.class)).thenReturn(countQuery);
        when(dataQuery.from(UserJpaEntity.class)).thenReturn(dataRoot);
        when(countQuery.from(UserJpaEntity.class)).thenReturn(countRoot);
        when(builder.count(countRoot)).thenReturn(countExpression);
        when(dataRoot.get(anyString())).thenReturn(dataPath);
        when(countRoot.get(anyString())).thenReturn(countPath);

        when(entityManager.createQuery(dataQuery)).thenReturn(dataTypedQuery);
        when(dataTypedQuery.setFirstResult(0)).thenReturn(dataTypedQuery);
        when(dataTypedQuery.setMaxResults(50)).thenReturn(dataTypedQuery);
        when(dataTypedQuery.getResultList()).thenReturn(results);

        when(entityManager.createQuery(countQuery)).thenReturn(countTypedQuery);
        when(countTypedQuery.getSingleResult()).thenReturn(count);

        return new QueryHarness(builder, dataRoot, countRoot, dataPath, countPath);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void stubStringSearch(QueryHarness harness) {
        Expression<String> dataAsString = mock(Expression.class);
        Expression<String> countAsString = mock(Expression.class);
        Expression<String> dataLower = mock(Expression.class);
        Expression<String> countLower = mock(Expression.class);
        Predicate like = mock(Predicate.class);
        Predicate orPredicate = mock(Predicate.class);

        when(harness.dataPath().as(String.class)).thenReturn(dataAsString);
        when(harness.countPath().as(String.class)).thenReturn(countAsString);
        when(harness.builder().lower(dataAsString)).thenReturn(dataLower);
        when(harness.builder().lower(countAsString)).thenReturn(countLower);
        when(harness.builder().like(any(Expression.class), anyString())).thenReturn(like);
        when(harness.builder().or(any(Predicate[].class))).thenReturn(orPredicate);
    }

    private static UserJpaEntity user() {
        return new UserJpaEntity(
                "user-1",
                "alice",
                "alice@example.com",
                "Alice",
                null,
                null,
                null,
                null,
                0,
                null,
                Instant.EPOCH,
                null,
                null,
                null,
                Instant.EPOCH
        );
    }

    private static LocalCredentialJpaEntity credentialWithHashMarker() {
        return new LocalCredentialJpaEntity(
                "credential-1",
                "user-1",
                HASH_MARKER,
                "ACTIVE",
                Instant.EPOCH,
                Instant.EPOCH,
                Instant.EPOCH
        );
    }

    private record QueryHarness(
            CriteriaBuilder builder,
            Root<?> dataRoot,
            Root<?> countRoot,
            Path<?> dataPath,
            Path<?> countPath
    ) { }
}
