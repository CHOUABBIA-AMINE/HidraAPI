/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringIdentityController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing identity REST endpoints.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;
import dz.sh.hidra.modules.identity.api.rest.mapper.IdentityRestMapper;
import dz.sh.hidra.modules.identity.api.rest.request.CreateUserRequest;
import dz.sh.hidra.modules.identity.api.rest.request.EvaluatePermissionRequest;
import dz.sh.hidra.modules.identity.api.rest.response.PermissionDecisionResponse;
import dz.sh.hidra.modules.identity.api.rest.response.UserResponse;
import dz.sh.hidra.modules.identity.application.port.in.CreateUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC adapter exposing identity REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/identity")
public class SpringIdentityController implements IdentityController {

    private final CreateUserUseCase createUserUseCase;
    private final EvaluatePermissionUseCase evaluatePermissionUseCase;

    public SpringIdentityController(
            CreateUserUseCase createUserUseCase,
            EvaluatePermissionUseCase evaluatePermissionUseCase
    ) {
        this.createUserUseCase = Objects.requireNonNull(createUserUseCase, "CreateUserUseCase must not be null.");
        this.evaluatePermissionUseCase = Objects.requireNonNull(evaluatePermissionUseCase, "EvaluatePermissionUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "identity",
                "mission", "Provide identity and permission decision endpoints for Hidra access control.",
                "objectives", List.of(
                "Create users.",
                "Evaluate permissions for operational resources."
        ),
                "operations", List.of(
                "createUser",
                "evaluate"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/identity/users",
                "POST /api/v1/identity/permissions/evaluations"
        )
        );
    }

    @Override
    @PostMapping({"/create-user", "/users"})
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        Objects.requireNonNull(request, "CreateUserRequest must not be null.");
        return IdentityRestMapper.toResponse(createUserUseCase.createUser(IdentityRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/evaluate", "/evaluate-permission", "/permissions/evaluations"})
    public PermissionDecisionResponse evaluate(@Valid @RequestBody EvaluatePermissionRequest request) {
        Objects.requireNonNull(request, "EvaluatePermissionRequest must not be null.");
        return IdentityRestMapper.toResponse(evaluatePermissionUseCase.evaluate(IdentityRestMapper.toQuery(request)));
    }

}
