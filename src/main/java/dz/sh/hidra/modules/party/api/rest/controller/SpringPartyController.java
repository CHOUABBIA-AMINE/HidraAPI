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
import dz.sh.hidra.modules.party.api.rest.request.RegisterPartyRequest;
import dz.sh.hidra.modules.party.api.rest.response.PartyResponse;
import dz.sh.hidra.modules.party.application.port.in.RegisterPartyUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing party REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/party")
public final class SpringPartyController implements PartyController {

    private final RegisterPartyUseCase registerPartyUseCase;

    public SpringPartyController(
            RegisterPartyUseCase registerPartyUseCase
    ) {
        this.registerPartyUseCase = Objects.requireNonNull(registerPartyUseCase, "RegisterPartyUseCase must not be null.");
    }


    @Override
    @PostMapping("/register-party")
    public PartyResponse registerParty(@Valid @RequestBody RegisterPartyRequest request) {
        Objects.requireNonNull(request, "RegisterPartyRequest must not be null.");
        return PartyRestMapper.toResponse(registerPartyUseCase.registerParty(PartyRestMapper.toCommand(request)));
    }

}
