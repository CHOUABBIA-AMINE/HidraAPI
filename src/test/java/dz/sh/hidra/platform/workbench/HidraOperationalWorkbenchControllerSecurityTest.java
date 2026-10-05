/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraOperationalWorkbenchControllerSecurityTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.workbench
 *
 * @Description : Verifies both workbench URL aliases enforce the same exposure and authorization policy.
 *
 */
package dz.sh.hidra.platform.workbench;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dz.sh.hidra.platform.exception.HidraGlobalExceptionHandler;
import dz.sh.hidra.platform.permissions.HidraRouteAuthorizationInterceptor;
import dz.sh.hidra.platform.permissions.HidraRoutePermissionNaming;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class HidraOperationalWorkbenchControllerSecurityTest {
    private final WorkbenchResource resource = mock(WorkbenchResource.class);
    private final WorkbenchResource.ScopedReader reader = mock(WorkbenchResource.ScopedReader.class);
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        when(resource.definition()).thenReturn(HidraOperationalWorkbenchServiceTest.definition());
        when(resource.scope(any())).thenReturn(Optional.of(reader));
        var resolver = new HidraEffectivePermissionResolver(List.of(name -> Set.of("*")));
        var service = new HidraOperationalWorkbenchService(new WorkbenchResourceRegistry(List.of(resource)), resolver);
        mvc = MockMvcBuilders.standaloneSetup(new HidraOperationalWorkbenchController(service))
                .setControllerAdvice(new WorkbenchExceptionHandler(), new HidraGlobalExceptionHandler(false))
                .addInterceptors(new HidraRouteAuthorizationInterceptor(resolver, new HidraRoutePermissionNaming(), true, "jwt"))
                .build();
    }

    @AfterEach
    void clearContext() { SecurityContextHolder.clearContext(); }

    @Test
    void administratorCannotDiscoverOrReadCredentialsOnEitherAlias() throws Exception {
        HidraOperationalWorkbenchServiceTest.authenticate("ROLE_HIDRA_ADMIN");
        for (String base : List.of("/api/v1/workbench/identity/local-credentials", "/api/v1/identity/workbench/local-credentials")) {
            mvc.perform(get(base)).andExpect(status().isNotFound());
            mvc.perform(get(base + "/any")).andExpect(status().isNotFound());
            mvc.perform(post(base + "/search").contentType(MediaType.APPLICATION_JSON)
                    .content("{\"filters\":{\"passwordHash\":\"TEST_MARKER\"}}"))
                    .andExpect(status().isNotFound()).andExpect(content().string(not(containsString("TEST_MARKER"))));
        }
        for (String url : List.of("/api/v1/workbench/identity/resources", "/api/v1/identity/workbench/resources")) {
            mvc.perform(get(url)).andExpect(status().isOk()).andExpect(content().json("[]"));
        }
        mvc.perform(get("/api/v1/workbench/modules")).andExpect(status().isOk())
                .andExpect(content().string(not(containsString("identity"))));
        verifyNoInteractions(reader);
    }

    @Test
    void concreteGrantAllowsBothAliasesButTemplateAndWrongResourceGrantsDoNot() throws Exception {
        when(reader.detail("1")).thenReturn(Optional.of(Map.of("id", "1", "label", "Station A")));
        when(reader.search(any())).thenReturn(new WorkbenchResource.Page(List.of(Map.of("id", "1", "label", "Station A")), 1));
        for (String base : List.of("/api/v1/workbench/telemetry/safe-points", "/api/v1/telemetry/workbench/safe-points")) {
            for (String grant : List.of("dynamic-module:dynamic-resource:read", "dynamic-module:workbench:read", "other:safe-points:read")) {
                HidraOperationalWorkbenchServiceTest.authenticate(grant);
                mvc.perform(get(base)).andExpect(status().isNotFound());
                mvc.perform(get(base + "/1")).andExpect(status().isNotFound());
            }
            HidraOperationalWorkbenchServiceTest.authenticate("telemetry:safe-points:read", "telemetry:safe-points:search");
            mvc.perform(get(base)).andExpect(status().isOk()).andExpect(jsonPath("$.totalElements").value(1));
            mvc.perform(get(base + "/1")).andExpect(status().isOk()).andExpect(jsonPath("$.attributes.label").value("Station A"));
            mvc.perform(post(base + "/search")).andExpect(status().isOk()).andExpect(jsonPath("$.totalElements").value(1));
        }
    }

    @Test
    void deniedSearchAndInvalidFieldsNeverReachRowsOrCountsOnEitherAlias() throws Exception {
        for (String base : List.of("/api/v1/workbench/telemetry/safe-points", "/api/v1/telemetry/workbench/safe-points")) {
            HidraOperationalWorkbenchServiceTest.authenticate("telemetry:safe-points:read");
            mvc.perform(post(base + "/search")).andExpect(status().isNotFound());
            HidraOperationalWorkbenchServiceTest.authenticate("telemetry:safe-points:search");
            mvc.perform(post(base + "/search").contentType(MediaType.APPLICATION_JSON)
                    .content("{\"sortBy\":\"passwordHash\",\"filters\":{\"id\":\"TEST_MARKER\"}}"))
                    .andExpect(status().isBadRequest()).andExpect(content().string(not(containsString("TEST_MARKER"))));
        }
        verify(resource, never()).scope(any());
        verifyNoInteractions(reader);
    }

    @Test
    void anonymousRequestsRemainDeniedAndErrorsDoNotExposeProviderData() throws Exception {
        for (String base : List.of("/api/v1/workbench/telemetry/safe-points", "/api/v1/telemetry/workbench/safe-points")) {
            mvc.perform(get(base)).andExpect(status().isForbidden());
            mvc.perform(get(base + "/1")).andExpect(status().isForbidden());
            mvc.perform(post(base + "/search")).andExpect(status().isForbidden());
        }
        mvc.perform(get("/api/v1/workbench/modules")).andExpect(status().isForbidden());
        HidraOperationalWorkbenchServiceTest.authenticate("telemetry:safe-points:read");
        when(reader.detail("1")).thenThrow(new IllegalStateException("TEST_MARKER"));
        mvc.perform(get("/api/v1/workbench/telemetry/safe-points/1"))
                .andExpect(status().isInternalServerError()).andExpect(content().string(not(containsString("TEST_MARKER"))));
    }

    @Test
    void malformedJsonAndPagingErrorsNeverEchoSubmittedValues() throws Exception {
        HidraOperationalWorkbenchServiceTest.authenticate("telemetry:safe-points:read", "telemetry:safe-points:search");
        for (String base : List.of("/api/v1/workbench/telemetry/safe-points", "/api/v1/telemetry/workbench/safe-points")) {
            mvc.perform(get(base).param("page", "TEST_MARKER"))
                    .andExpect(status().isBadRequest()).andExpect(content().string(not(containsString("TEST_MARKER"))));
            mvc.perform(post(base + "/search").contentType(MediaType.APPLICATION_JSON)
                    .content("{\"filters\":{\"id\":\"TEST_MARKER\"},\"sortBy\":"))
                    .andExpect(status().isBadRequest()).andExpect(content().string(not(containsString("TEST_MARKER"))));
        }
        when(reader.detail("TEST_MARKER")).thenThrow(new IllegalStateException("TEST_MARKER"));
        mvc.perform(get("/api/v1/workbench/telemetry/safe-points/TEST_MARKER"))
                .andExpect(status().isInternalServerError()).andExpect(content().string(not(containsString("TEST_MARKER"))));
        // Provider errors must not echo either their message or the requested identifier.
        verify(reader).detail("TEST_MARKER");
    }

    @Test
    void descriptorsAreCallerFilteredAndContainNoPersistenceInternals() throws Exception {
        HidraOperationalWorkbenchServiceTest.authenticate("other:safe-points:read");
        mvc.perform(get("/api/v1/workbench/telemetry/resources")).andExpect(content().json("[]"));
        HidraOperationalWorkbenchServiceTest.authenticate("telemetry:safe-points:read");
        for (String url : List.of("/api/v1/workbench/telemetry/resources", "/api/v1/telemetry/workbench/resources")) {
            mvc.perform(get(url)).andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].readPermission").value("telemetry:safe-points:read"))
                    .andExpect(jsonPath("$[0].javaType").doesNotExist())
                    .andExpect(jsonPath("$[0].entityName").doesNotExist())
                    .andExpect(jsonPath("$[0].tableName").doesNotExist());
        }
        verifyNoInteractions(reader);
    }
}
