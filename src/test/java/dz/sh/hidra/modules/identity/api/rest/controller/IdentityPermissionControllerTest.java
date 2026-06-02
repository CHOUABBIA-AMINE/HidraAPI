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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IdentityPermissionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private EvaluatePermissionUseCase evaluatePermissionUseCase;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        evaluatePermissionUseCase = mock(EvaluatePermissionUseCase.class);

        IdentityPermissionController controller = new IdentityPermissionController(
                evaluatePermissionUseCase,
                new IdentityRestMapper()
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(validator())
                .build();
    }

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

    private static LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        return validator;
    }
}
