/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOperationalWorkbenchServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Verifies Workbench list, detail, and search paths cannot bypass approved exposure fields.
 *
 */
package dz.sh.hidra.platform.workbench;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.LocalCredentialJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HidraOperationalWorkbenchServiceTest {

    @Test
    void metamodelPresenceAloneDoesNotExposeResources() {
        EntityManager entityManager = mock(EntityManager.class);
        HidraOperationalWorkbenchService service = new HidraOperationalWorkbenchService(
                entityManager,
                new HidraOperationalWorkbenchExposurePolicy("")
        );

        assertThat(service.listModules()).isEmpty();
        verifyNoInteractions(entityManager);
    }

    @Test
    void resourceListingIncludesOnlyExplicitlyApprovedMetamodelResource() {
        EntityManager entityManager = mock(EntityManager.class);
        HidraOperationalWorkbenchService service = service(
                entityManager,
                "identity/users=id,username",
                UserJpaEntity.class,
                LocalCredentialJpaEntity.class
        );

        List<OperationalResourceDescriptor> resources = service.listResources("identity");

        assertThat(resources).hasSize(1);
        assertThat(resources.getFirst().resource()).isEqualTo("users");
        assertThat(resources.getFirst().searchableFields()).containsExactly("id", "username");
        assertThat(resources).noneMatch(resource -> "local-credentials".equals(resource.resource()));
    }

    @Test
    void unknownConfiguredFieldFailsClosedDuringResourceIndexing() {
        EntityManager entityManager = mock(EntityManager.class);
        HidraOperationalWorkbenchService service = service(
                entityManager,
                "identity/users=id,missingField",
                UserJpaEntity.class
        );

        assertThatThrownBy(() -> service.listResources("identity"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("references unknown field missingField");
    }

    @Test
    void configuredResourceMustExplicitlyExposeIdentifierField() {
        EntityManager entityManager = mock(EntityManager.class);
        HidraOperationalWorkbenchService service = service(
                entityManager,
                "identity/users=username",
                UserJpaEntity.class
        );

        assertThatThrownBy(() -> service.listResources("identity"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("must explicitly include id field id");
    }

    @Test
    void detailResponseContainsOnlyApprovedAttributesAndNeverPasswordHash() {
        EntityManager entityManager = mock(EntityManager.class);
        HidraOperationalWorkbenchService service = service(
                entityManager,
                "identity/users=id,username",
                UserJpaEntity.class,
                LocalCredentialJpaEntity.class
        );
        UserJpaEntity user = user();
        when(entityManager.find(UserJpaEntity.class, "user-1")).thenReturn(user);

        OperationalRecordResponse response = service.detail("identity", "users", "user-1");

        assertThat(response.id()).isEqualTo("user-1");
        assertThat(response.attributes())
                .containsEntry("id", "user-1")
                .containsEntry("username", "alice")
                .containsOnlyKeys("id", "username")
                .doesNotContainKey("passwordHash");
    }

    @Test
    void listResponseContainsOnlyApprovedAttributes() {
        EntityManager entityManager = mock(EntityManager.class);
        HidraOperationalWorkbenchService service = service(
                entityManager,
                "identity/users=id,username",
                UserJpaEntity.class
        );
        QueryHarness harness = queryHarness(entityManager, List.of(user()), 1L);

        OperationalPageResponse response = service.list("identity", "users", 0, 50, null);

        assertThat(response.items()).hasSize(1);
        assertThat(response.items().getFirst().attributes())
                .containsOnlyKeys("id", "username")
                .doesNotContainKeys("emailAddress", "displayName", "passwordHash");
        verifyNoInteractions(harness.dataPath(), harness.countPath());
    }

    @Test
    void searchIgnoresNonApprovedFilterAndSortFieldsAndSearchesOnlyApprovedStrings() {
        EntityManager entityManager = mock(EntityManager.class);
        HidraOperationalWorkbenchService service = service(
                entityManager,
                "identity/users=id,username",
                UserJpaEntity.class
        );
        QueryHarness harness = queryHarness(entityManager, List.of(user()), 1L);
        stubStringSearch(harness);

        OperationalPageResponse response = service.search(
                "identity",
                "users",
                new OperationalSearchRequest(
                        "alice",
                        Map.of("emailAddress", "hidden@example.com"),
                        0,
                        50,
                        "emailAddress",
                        "asc"
                )
        );

        assertThat(response.items()).hasSize(1);
        assertThat(response.items().getFirst().attributes())
                .containsOnlyKeys("id", "username")
                .doesNotContainKeys("emailAddress", "passwordHash");

        verify(harness.dataRoot(), never()).get("emailAddress");
        verify(harness.countRoot(), never()).get("emailAddress");
        verify(harness.dataRoot()).get("id");
        verify(harness.dataRoot()).get("username");
        verify(harness.countRoot()).get("id");
        verify(harness.countRoot()).get("username");
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

    private record QueryHarness(
            CriteriaBuilder builder,
            Root<?> dataRoot,
            Root<?> countRoot,
            Path<?> dataPath,
            Path<?> countPath
    ) { }
}
