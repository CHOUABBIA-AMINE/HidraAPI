/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityPermissionController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : REST controller for identity permission endpoints.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import dz.sh.hidra.modules.identity.api.rest.mapper.IdentityRestMapper;
import dz.sh.hidra.modules.identity.api.rest.request.CheckPermissionRequest;
import dz.sh.hidra.modules.identity.api.rest.response.PermissionDecisionResponse;
import dz.sh.hidra.modules.identity.api.rest.response.PermissionResponse;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST controller for identity permission endpoints.
 *
 * <p>Business role: exposes permission catalog and permission check endpoints under the
 * identity API path.</p>
 *
 * <p>Architecture role: API adapter that depends only on application inbound ports and
 * {@link IdentityRestMapper}. It does not access repositories, JPA entities, platform
 * security plumbing, or organization structures.</p>
 *
 * <p>Validation responsibility: uses {@link Valid} request bodies for permission
 * decision requests.</p>
 *
 * <p>Usage: discovered by Spring MVC as part of the identity REST API.</p>
 */
@RestController
@RequestMapping("/api/v1/identity/permissions")
public class IdentityPermissionController {

    private final EvaluatePermissionUseCase evaluatePermissionUseCase;
    private final IdentityRestMapper mapper;

    public IdentityPermissionController(
            EvaluatePermissionUseCase evaluatePermissionUseCase,
            IdentityRestMapper mapper
    ) {
        this.evaluatePermissionUseCase = evaluatePermissionUseCase;
        this.mapper = mapper;
    }

    @GetMapping
    public List<PermissionResponse> listPermissions() {
        throw new ResponseStatusException(
                HttpStatus.NOT_IMPLEMENTED,
                "List permissions requires a dedicated application port not created before ID-015."
        );
    }

    @PostMapping("/check")
    public PermissionDecisionResponse checkPermission(
            @Valid @RequestBody CheckPermissionRequest request
    ) {
        return mapper.toPermissionDecisionResponse(
                evaluatePermissionUseCase.evaluatePermission(mapper.toCheckPermissionQuery(request))
        );
    }
}
