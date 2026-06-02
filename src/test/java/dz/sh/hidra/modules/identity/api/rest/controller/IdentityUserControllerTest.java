/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityUserControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity API Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Tests identity user REST endpoints.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sh.hidra.modules.identity.api.rest.mapper.IdentityRestMapper;
import dz.sh.hidra.modules.identity.api.rest.request.ActivateUserRequest;
import dz.sh.hidra.modules.identity.api.rest.request.AssignRoleToUserRequest;
import dz.sh.hidra.modules.identity.api.rest.request.RegisterUserRequest;
import dz.sh.hidra.modules.identity.application.dto.PermissionDto;
import dz.sh.hidra.modules.identity.application.dto.RoleDto;
import dz.sh.hidra.modules.identity.application.dto.UserDto;
import dz.sh.hidra.modules.identity.application.port.in.ActivateUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.AssignRoleToUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.GetUserPermissionsUseCase;
import dz.sh.hidra.modules.identity.application.port.in.RegisterUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.RevokeRoleFromUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.SuspendUserUseCase;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.domain.value.PermissionName;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
import dz.sh.hidra.modules.identity.domain.value.RoleStatus;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.Username;
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

/**
 * Tests identity user REST endpoints.
 *
 * <p>Business role: verifies user registration, activation, role assignment, and
 * effective permission endpoints exposed under {@code /api/v1/identity/users}.</p>
 *
 * <p>Architecture role: API adapter test using standalone MockMvc with mocked application
 * inbound ports and the real REST mapper. It avoids Spring Boot test slices and does not
 * access repositories, JPA entities, platform security plumbing, or organization modules.</p>
 *
 * <p>Validation responsibility: covers successful JSON request/response mapping and
 * Bean Validation rejection for invalid request bodies.</p>
 *
 * <p>Usage: executed by the identity API test suite.</p>
 */
class IdentityUserControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private RegisterUserUseCase registerUserUseCase;
    private ActivateUserUseCase activateUserUseCase;
    private AssignRoleToUserUseCase assignRoleToUserUseCase;
    private GetUserPermissionsUseCase getUserPermissionsUseCase;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        registerUserUseCase = mock(RegisterUserUseCase.class);
        activateUserUseCase = mock(ActivateUserUseCase.class);
        SuspendUserUseCase suspendUserUseCase = mock(SuspendUserUseCase.class);
        assignRoleToUserUseCase = mock(AssignRoleToUserUseCase.class);
        RevokeRoleFromUserUseCase revokeRoleFromUserUseCase = mock(RevokeRoleFromUserUseCase.class);
        getUserPermissionsUseCase = mock(GetUserPermissionsUseCase.class);

        IdentityUserController controller = new IdentityUserController(
                registerUserUseCase,
                activateUserUseCase,
                suspendUserUseCase,
                assignRoleToUserUseCase,
                revokeRoleFromUserUseCase,
                getUserPermissionsUseCase,
                new IdentityRestMapper()
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(validator())
                .build();
    }

    @Test
    void registerUserShouldReturnCreatedUser() throws Exception {
        when(registerUserUseCase.registerUser(any())).thenReturn(userDto(UserStatus.REGISTERED, List.of()));

        RegisterUserRequest request = new RegisterUserRequest(
                "abir.medjerab",
                "abir.medjerab@sonatrach.dz"
        );

        mockMvc.perform(post("/api/v1/identity/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("user-1"))
                .andExpect(jsonPath("$.username").value("abir.medjerab"))
                .andExpect(jsonPath("$.emailAddress").value("abir.medjerab@sonatrach.dz"))
                .andExpect(jsonPath("$.status").value("REGISTERED"));
    }

    @Test
    void registerUserShouldRejectInvalidRequestBody() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest("", "not-an-email");

        mockMvc.perform(post("/api/v1/identity/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void activateUserShouldReturnActivatedUser() throws Exception {
        when(activateUserUseCase.activate(any())).thenReturn(userDto(UserStatus.ACTIVE, List.of()));

        ActivateUserRequest request = new ActivateUserRequest("user-1");

        mockMvc.perform(post("/api/v1/identity/users/user-1/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("user-1"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void assignRoleToUserShouldReturnUserWithAssignedRole() throws Exception {
        RoleDto roleDto = roleDto(List.of());
        when(assignRoleToUserUseCase.assignRoleToUser(any()))
                .thenReturn(userDto(UserStatus.ACTIVE, List.of(roleDto)));

        AssignRoleToUserRequest request = new AssignRoleToUserRequest("role-1");

        mockMvc.perform(post("/api/v1/identity/users/user-1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles[0].id").value("role-1"))
                .andExpect(jsonPath("$.roles[0].code").value("IDENTITY_ADMIN"));
    }

    @Test
    void getUserPermissionsShouldReturnPermissionResponses() throws Exception {
        when(getUserPermissionsUseCase.getUserPermissions(any())).thenReturn(List.of(permissionDto()));

        mockMvc.perform(get("/api/v1/identity/users/user-1/permissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("permission-1"))
                .andExpect(jsonPath("$[0].code").value("identity:user:create"));
    }

    private static LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        return validator;
    }

    private static UserDto userDto(UserStatus status, List<RoleDto> roles) {
        return new UserDto(
                UserId.of("user-1"),
                Username.of("abir.medjerab"),
                EmailAddress.of("abir.medjerab@sonatrach.dz"),
                status,
                null,
                roles
        );
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
