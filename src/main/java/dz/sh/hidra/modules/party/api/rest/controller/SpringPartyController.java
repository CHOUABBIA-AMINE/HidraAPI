/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringPartyController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing party REST endpoints.
 *
 */
package dz.sh.hidra.modules.party.api.rest.controller;
import dz.sh.hidra.modules.party.api.rest.mapper.PartyRestMapper;
import dz.sh.hidra.modules.party.api.rest.request.AssignPartyRoleRequest;
import dz.sh.hidra.modules.party.api.rest.request.RegisterPartyRequest;
import dz.sh.hidra.modules.party.api.rest.response.PartyResponse;
import dz.sh.hidra.modules.party.application.port.in.AssignPartyRoleUseCase;
import dz.sh.hidra.modules.party.application.port.in.RegisterPartyUseCase;
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
 * Spring MVC adapter exposing party REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/party")
public class SpringPartyController implements PartyController {

    private final AssignPartyRoleUseCase assignPartyRoleUseCase;
    private final RegisterPartyUseCase registerPartyUseCase;

    public SpringPartyController(
            AssignPartyRoleUseCase assignPartyRoleUseCase,
            RegisterPartyUseCase registerPartyUseCase
    ) {
        this.assignPartyRoleUseCase = Objects.requireNonNull(assignPartyRoleUseCase, "AssignPartyRoleUseCase must not be null.");
        this.registerPartyUseCase = Objects.requireNonNull(registerPartyUseCase, "RegisterPartyUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "party",
                "mission", "Manage external and internal parties and their roles in Hidra processes.",
                "objectives", List.of(
                "Register parties.",
                "Assign roles to parties."
        ),
                "operations", List.of(
                "assignRole",
                "registerParty"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/party/roles/assignments",
                "POST /api/v1/party/parties"
        )
        );
    }

    @Override
    @PostMapping({"/assign-role", "/roles/assignments"})
    public String assignRole(@Valid @RequestBody AssignPartyRoleRequest request) {
        Objects.requireNonNull(request, "AssignPartyRoleRequest must not be null.");
        return assignPartyRoleUseCase.assignRole(PartyRestMapper.toCommand(request));
    }

    @Override
    @PostMapping({"/register-party", "/parties"})
    public PartyResponse registerParty(@Valid @RequestBody RegisterPartyRequest request) {
        Objects.requireNonNull(request, "RegisterPartyRequest must not be null.");
        return PartyRestMapper.toResponse(registerPartyUseCase.registerParty(PartyRestMapper.toCommand(request)));
    }

}
