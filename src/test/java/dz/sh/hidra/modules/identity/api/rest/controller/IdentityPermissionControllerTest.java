/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityPermissionControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity API Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Tests identity permission REST endpoints.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.modules.identity.api.rest.mapper.IdentityRestMapper;
import dz.sh.hidra.modules.identity.api.rest.request.CheckPermissionRequest;
import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests identity permission REST endpoints.
 *
 * <p>Business role: verifies permission evaluation endpoints exposed under
 * {@code /api/v1/identity/permissions}.</p>
 *
 * <p>Architecture role: API adapter test using Spring MVC slice support. Controllers are
 * tested against mocked application inbound ports and the real REST mapper, without
 * repositories, JPA entities, platform security plumbing, or organization modules.</p>
 *
 * <p>Validation responsibility: covers successful JSON request/response mapping,
 * Bean Validation rejection, and explicit placeholder status for not-yet-created list
 * use-case port.</p>
 *
 * <p>Usage: executed by the identity API test suite.</p>
 */
@WebMvcTest(IdentityPermissionController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(IdentityRestMapper.class)
class IdentityPermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EvaluatePermissionUseCase evaluatePermissionUseCase;

    @Test
    void checkPermissionShouldReturnPermissionDecision() throws Exception {
        when(evaluatePermissionUseCase.evaluatePermission(any()))
                .thenReturn(PermissionDecisionDto.granted(
                        UserId.of("user-1"),
                        PermissionCode.of("identity:user:create")
                ));

        CheckPermissionRequest request = new CheckPermissionRequest(
                "user-1",
                "identity:user:create"
        );

        mockMvc.perform(post("/api/v1/identity/permissions/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user-1"))
                .andExpect(jsonPath("$.permissionCode").value("identity:user:create"))
                .andExpect(jsonPath("$.granted").value(true));
    }

    @Test
    void checkPermissionShouldRejectInvalidRequestBody() throws Exception {
        CheckPermissionRequest request = new CheckPermissionRequest("", "invalid");

        mockMvc.perform(post("/api/v1/identity/permissions/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listPermissionsShouldReturnNotImplementedUntilReadPortExists() throws Exception {
        mockMvc.perform(get("/api/v1/identity/permissions"))
                .andExpect(status().isNotImplemented());
    }
}
