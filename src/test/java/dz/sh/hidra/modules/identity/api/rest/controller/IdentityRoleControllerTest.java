/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityRoleControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity API Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Tests identity role REST endpoints.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.modules.identity.api.rest.mapper.IdentityRestMapper;
import dz.sh.hidra.modules.identity.api.rest.request.CreateRoleRequest;
import dz.sh.hidra.modules.identity.api.rest.request.GrantPermissionToRoleRequest;
import dz.sh.hidra.modules.identity.application.dto.PermissionDto;
import dz.sh.hidra.modules.identity.application.dto.RoleDto;
import dz.sh.hidra.modules.identity.application.port.in.CreateRoleUseCase;
import dz.sh.hidra.modules.identity.application.port.in.GrantPermissionToRoleUseCase;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.domain.value.PermissionName;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
import dz.sh.hidra.modules.identity.domain.value.RoleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IdentityRoleControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private CreateRoleUseCase createRoleUseCase;
    private GrantPermissionToRoleUseCase grantPermissionToRoleUseCase;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        createRoleUseCase = mock(CreateRoleUseCase.class);
        grantPermissionToRoleUseCase = mock(GrantPermissionToRoleUseCase.class);

        IdentityRoleController controller = new IdentityRoleController(
                createRoleUseCase,
                grantPermissionToRoleUseCase,
                new IdentityRestMapper()
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(validator())
                .build();
    }

    @Test
    void createRoleShouldReturnCreatedRole() throws Exception {
        when(createRoleUseCase.createRole(any())).thenReturn(roleDto(List.of()));

        CreateRoleRequest request = new CreateRoleRequest(
                "IDENTITY_ADMIN",
                "Identity Administrator"
        );

        mockMvc.perform(post("/api/v1/identity/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("role-1"))
                .andExpect(jsonPath("$.code").value("IDENTITY_ADMIN"))
                .andExpect(jsonPath("$.name").value("Identity Administrator"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void createRoleShouldRejectInvalidRequestBody() throws Exception {
        CreateRoleRequest request = new CreateRoleRequest("!", "");

        mockMvc.perform(post("/api/v1/identity/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void grantPermissionToRoleShouldReturnRoleWithGrantedPermission() throws Exception {
        PermissionDto permissionDto = permissionDto();
        when(grantPermissionToRoleUseCase.grantPermissionToRole(any()))
                .thenReturn(roleDto(List.of(permissionDto)));

        GrantPermissionToRoleRequest request = new GrantPermissionToRoleRequest("identity:user:create");

        mockMvc.perform(post("/api/v1/identity/roles/role-1/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("role-1"))
                .andExpect(jsonPath("$.permissions[0].id").value("permission-1"))
                .andExpect(jsonPath("$.permissions[0].code").value("identity:user:create"));
    }

    @Test
    void listRolesShouldReturnNotImplementedUntilReadPortExists() throws Exception {
        mockMvc.perform(get("/api/v1/identity/roles"))
                .andExpect(status().isNotImplemented());
    }

    private static LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        return validator;
    }

    private static RoleDto roleDto(List<PermissionDto> permissions) {
        return new RoleDto(
                RoleId.of("role-1"),
                RoleCode.of("IDENTITY_ADMIN"),
                RoleName.of("Identity Administrator"),
                RoleStatus.ACTIVE,
                permissions
        );
    }

    private static PermissionDto permissionDto() {
        return new PermissionDto(
                PermissionId.of("permission-1"),
                PermissionCode.of("identity:user:create"),
                PermissionName.of("Create identity user"),
                "Allows creating identity users."
        );
    }
}
