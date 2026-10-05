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
 * @Description : Verifies default-deny exposure, approved projections, query checks and scoped reads.
 *
 */
package dz.sh.hidra.platform.workbench;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

class HidraOperationalWorkbenchServiceTest {
    private final WorkbenchResource resource = mock(WorkbenchResource.class);
    private final WorkbenchResource.ScopedReader reader = mock(WorkbenchResource.ScopedReader.class);

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    static WorkbenchResourceDefinition definition() {
        return new WorkbenchResourceDefinition("telemetry", "safe-points", "id", List.of("id", "label"),
                List.of("label"), List.of("id"), List.of("label"), "Test projection", "Test owner", "TEST-REVIEW");
    }

    static void authenticate(String... grants) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                "operator", "n/a", java.util.Arrays.stream(grants).map(SimpleGrantedAuthority::new).toList()));
    }

    private HidraOperationalWorkbenchService service() {
        when(resource.definition()).thenReturn(definition());
        when(resource.scope(any())).thenReturn(Optional.of(reader));
        return new HidraOperationalWorkbenchService(new WorkbenchResourceRegistry(List.of(resource)),
                new HidraEffectivePermissionResolver(List.of()));
    }

    @Test
    void springWiringWithoutAdapterBeansCreatesEmptyDefaultDenyRegistry() {
        try (var context = new AnnotationConfigApplicationContext()) {
            context.registerBean(HidraEffectivePermissionResolver.class,
                    () -> new HidraEffectivePermissionResolver(List.of()));
            context.register(WorkbenchResourceRegistry.class, HidraOperationalWorkbenchService.class);
            context.refresh();
            assertTrue(context.getBean(WorkbenchResourceRegistry.class).entries().isEmpty());
            authenticate("telemetry:safe-points:read");
            assertTrue(context.getBean(HidraOperationalWorkbenchService.class).listModules().isEmpty());
        }
    }

    @Test
    void validQueryIsSnapshottedAndPaginationIsBoundedBeforeDelegation() {
        var service = service();
        authenticate("telemetry:safe-points:search");
        when(reader.search(any())).thenReturn(new WorkbenchResource.Page(List.of(), 0));
        Map<String, Object> filters = new LinkedHashMap<>(Map.of("id", "1"));
        service.search("telemetry", "safe-points", new OperationalSearchRequest("station", filters, -1, 500, "label", "DESC"));
        var capture = org.mockito.ArgumentCaptor.forClass(OperationalSearchRequest.class);
        verify(reader).search(capture.capture());
        var validated = capture.getValue();
        assertEquals(0, validated.page()); assertEquals(200, validated.size());
        assertEquals("desc", validated.sortDirection());
        filters.put("secret", "TEST_MARKER");
        assertEquals(Map.of("id", "1"), validated.filters());
        assertThrows(UnsupportedOperationException.class, () -> validated.filters().put("id", "2"));
    }

    @Test
    void emptyRegistryDeniesEveryReadEvenForConfirmedAdministrator() {
        authenticate("ROLE_HIDRA_ADMIN");
        var service = new HidraOperationalWorkbenchService(new WorkbenchResourceRegistry(List.of()),
                new HidraEffectivePermissionResolver(List.of(name -> Set.of("*"))));
        assertEquals(List.of(), service.listModules());
        assertEquals(List.of(), service.listResources("identity"));
        for (String name : List.of("local-credentials", "users", "unknown")) {
            assertThrows(java.util.NoSuchElementException.class, () -> service.detail("identity", name, "any"));
            assertThrows(java.util.NoSuchElementException.class, () -> service.list("identity", name, 0, 50, null));
            assertThrows(java.util.NoSuchElementException.class, () -> service.search("identity", name, null));
        }
    }

    @Test
    void unrelatedAndTemplateGrantsCannotReachReadersOrDiscovery() {
        var service = service();
        for (String grant : List.of("other:safe-points:read", "telemetry:other:read",
                "dynamic-module:dynamic-resource:read", "dynamic-module:workbench:read")) {
            authenticate(grant);
            assertTrue(service.listModules().isEmpty());
            assertTrue(service.listResources("telemetry").isEmpty());
            assertThrows(java.util.NoSuchElementException.class,
                    () -> service.detail("telemetry", "safe-points", "1"));
        }
        verify(resource, never()).scope(any());
        verifyNoInteractions(reader);
    }

    @Test
    void anonymousAndUnauthenticatedCallsCannotRead() {
        var service = service();
        assertThrows(AccessDeniedException.class, service::listModules);
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken(
                "test", "anonymousUser", List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
        assertThrows(AccessDeniedException.class, () -> service.detail("telemetry", "safe-points", "1"));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("operator", "n/a"));
        assertThrows(AccessDeniedException.class, service::listModules);
        verifyNoInteractions(reader);
    }

    @Test
    void noRowScopeDeniesDataAndDiscoveryForAnOtherwiseAuthorizedCaller() {
        var service = service();
        authenticate("telemetry:safe-points:read");
        when(resource.scope(any())).thenReturn(Optional.empty());
        assertTrue(service.listModules().isEmpty());
        assertThrows(java.util.NoSuchElementException.class, () -> service.detail("telemetry", "safe-points", "1"));
        verifyNoInteractions(reader);
    }

    @Test
    void queryFieldsAreValidatedBeforeResolvingScopeOrReadingCounts() {
        var service = service();
        authenticate("telemetry:safe-points:search");
        for (var request : List.of(
                new OperationalSearchRequest(null, Map.of("passwordHash", "TEST_MARKER"), 0, 10, null, null),
                new OperationalSearchRequest(null, Map.of("newField", "x"), 0, 10, null, null),
                new OperationalSearchRequest(null, Map.of("label", "x"), 0, 10, null, null),
                new OperationalSearchRequest(null, Map.of("id", Map.of("secret", "TEST_MARKER")), 0, 10, null, null),
                new OperationalSearchRequest(null, Map.of(), 0, 10, "passwordHash", "asc"),
                new OperationalSearchRequest(null, Map.of(), 0, 10, "id", "asc"),
                new OperationalSearchRequest(null, Map.of(), 0, 10, "label", "invalid"),
                new OperationalSearchRequest(null, Map.of(), Integer.MAX_VALUE, 200, null, null))) {
            var exception = assertThrows(IllegalArgumentException.class,
                    () -> service.search("telemetry", "safe-points", request));
            assertEquals("Unsupported workbench query.", exception.getMessage());
        }
        verify(resource, never()).scope(any());
        verifyNoInteractions(reader);
    }

    @Test
    void listAndDetailUseOnlyApprovedProjectionAndSearchHasItsOwnPermission() {
        var service = service();
        authenticate("telemetry:safe-points:read");
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("id", "1"); row.put("label", "Station A");
        row.put("passwordHash", "TEST_MARKER"); row.put("newField", Map.of("secret", "TEST_MARKER"));
        when(reader.detail("1")).thenReturn(Optional.of(row));
        when(reader.search(any())).thenReturn(new WorkbenchResource.Page(List.of(row), 1));
        assertEquals(Map.of("id", "1", "label", "Station A"), service.detail("telemetry", "safe-points", "1").attributes());
        var page = service.list("telemetry", "safe-points", 0, 10, null);
        assertEquals(1, page.totalElements());
        assertEquals(1, page.totalPages());
        assertEquals(service.detail("telemetry", "safe-points", "1"), page.items().getFirst());
        assertThrows(java.util.NoSuchElementException.class, () -> service.search("telemetry", "safe-points", null));
        authenticate("telemetry:safe-points:search");
        assertEquals(page.items(), service.search("telemetry", "safe-points", null).items());
        assertThrows(java.util.NoSuchElementException.class, () -> service.detail("telemetry", "safe-points", "1"));
    }

    @Test
    void rawNestedOrArbitraryOutputIsRejectedWithoutStringifyingIt() {
        var service = service();
        authenticate("telemetry:safe-points:read");
        Object hostile = new Object() {
            @Override public String toString() { fail("Arbitrary output must not be stringified"); return ""; }
        };
        for (Object value : List.of(Map.of("secret", "TEST_MARKER"), List.of("TEST_MARKER"), hostile)) {
            when(reader.detail("1")).thenReturn(Optional.of(Map.of("id", "1", "label", value)));
            assertEquals("Invalid workbench projection.", assertThrows(IllegalStateException.class,
                    () -> service.detail("telemetry", "safe-points", "1")).getMessage());
        }
    }

    @Test
    void descriptorContainsOnlyPublicProjectionContract() {
        var service = service();
        authenticate("telemetry:safe-points:read");
        assertEquals(List.of("telemetry"), service.listModules());
        var descriptor = service.listResources("telemetry").getFirst();
        assertEquals(List.of("id", "label"), descriptor.outputFields());
        assertEquals(List.of("label"), descriptor.searchableFields());
        assertEquals(List.of("id"), descriptor.filterableFields());
        assertEquals("telemetry:safe-points:read", descriptor.readPermission());
        var fields = java.util.Arrays.stream(OperationalResourceDescriptor.class.getRecordComponents())
                .map(java.lang.reflect.RecordComponent::getName).toList();
        assertFalse(fields.contains("javaType")); assertFalse(fields.contains("tableName"));
        assertFalse(fields.contains("entityName"));
    }

    @Test
    void providerFailuresCannotExposeSensitiveMessagesOrCauses() {
        var service = service();
        authenticate("telemetry:safe-points:read");
        when(reader.detail("1")).thenThrow(new IllegalStateException("TEST_MARKER"));
        var exception = assertThrows(IllegalStateException.class, () -> service.detail("telemetry", "safe-points", "1"));
        assertEquals("Workbench read failed.", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void approvedScopedAdapterAppliesSamePrincipalScopeToRowsCountsAndDetail() {
        var adapter = new WorkbenchResource() {
            public WorkbenchResourceDefinition definition() { return HidraOperationalWorkbenchServiceTest.definition(); }
            public Optional<ScopedReader> scope(Authentication authentication) {
                List<Map<String, Object>> rows = "operator".equals(authentication.getName())
                        ? List.of(Map.of("id", "own", "label", "Own station")) : List.of();
                return Optional.of(new ScopedReader() {
                    public Page search(OperationalSearchRequest request) { return new Page(rows, rows.size()); }
                    public Optional<Map<String, Object>> detail(String id) {
                        return rows.stream().filter(row -> id.equals(row.get("id"))).findFirst();
                    }
                });
            }
        };
        var service = new HidraOperationalWorkbenchService(new WorkbenchResourceRegistry(List.of(adapter)),
                new HidraEffectivePermissionResolver(List.of()));
        authenticate("telemetry:safe-points:read", "telemetry:safe-points:search");
        assertEquals(1, service.list("telemetry", "safe-points", null, null, null).totalElements());
        assertEquals(1, service.search("telemetry", "safe-points", null).totalElements());
        assertEquals("own", service.detail("telemetry", "safe-points", "own").id());
        assertThrows(java.util.NoSuchElementException.class, () -> service.detail("telemetry", "safe-points", "other"));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("other", "n/a",
                List.of(new SimpleGrantedAuthority("telemetry:safe-points:read"))));
        assertEquals(0, service.list("telemetry", "safe-points", null, null, null).totalElements());
        assertThrows(java.util.NoSuchElementException.class, () -> service.detail("telemetry", "safe-points", "own"));
    }
}
