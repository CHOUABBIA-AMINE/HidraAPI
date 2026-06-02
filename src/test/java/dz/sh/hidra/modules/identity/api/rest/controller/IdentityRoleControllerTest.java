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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests identity role REST endpoints.
 *
 * <p>Business role: verifies role creation and permission grant endpoints exposed under
 * {@code /api/v1/identity/roles}.</p>
 *
 * <p>Architecture role: API adapter test using Spring MVC slice support. Controllers are
 * tested against mocked application inbound ports and the real REST mapper, without
 * repositories, JPA entities, platform security plumbing, or organization modules.</p>
 *
 * <p>Validation responsibility: covers successful JSON request/response mapping,
 * Bean Validation rejection, and explicit placeholder status for not-yet-created read
 * use-case ports.</p>
 *
 * <p>Usage: executed by the identity API test suite.</p>
 */
@WebMvcTest(IdentityRoleController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(IdentityRestMapper.class)
class IdentityRoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateRoleUseCase createRoleUseCase;

    @MockBean
    private GrantPermissionToRoleUseCase grantPermissionToRoleUseCase;

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
