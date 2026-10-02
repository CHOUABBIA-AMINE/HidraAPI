/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringLeakdetectionController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing leakdetection REST endpoints.
 *
 */
package dz.sh.hidra.modules.leakdetection.api.rest.controller;
import dz.sh.hidra.modules.leakdetection.api.rest.mapper.LeakdetectionRestMapper;
import dz.sh.hidra.modules.leakdetection.api.rest.request.CreateLeakCandidateRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.request.EscalateLeakCaseRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.request.OpenLeakCaseRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakCandidateResponse;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakCaseResponse;
import dz.sh.hidra.modules.leakdetection.application.port.in.CreateLeakCandidateUseCase;
import dz.sh.hidra.modules.leakdetection.application.port.in.EscalateLeakCaseUseCase;
import dz.sh.hidra.modules.leakdetection.application.port.in.OpenLeakCaseUseCase;
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
 * Spring MVC adapter exposing leakdetection REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/leakdetection")
public class SpringLeakdetectionController implements LeakdetectionController {

    private final CreateLeakCandidateUseCase createLeakCandidateUseCase;
    private final EscalateLeakCaseUseCase escalateLeakCaseUseCase;
    private final OpenLeakCaseUseCase openLeakCaseUseCase;

    public SpringLeakdetectionController(
            CreateLeakCandidateUseCase createLeakCandidateUseCase,
            EscalateLeakCaseUseCase escalateLeakCaseUseCase,
            OpenLeakCaseUseCase openLeakCaseUseCase
    ) {
        this.createLeakCandidateUseCase = Objects.requireNonNull(createLeakCandidateUseCase, "CreateLeakCandidateUseCase must not be null.");
        this.escalateLeakCaseUseCase = Objects.requireNonNull(escalateLeakCaseUseCase, "EscalateLeakCaseUseCase must not be null.");
        this.openLeakCaseUseCase = Objects.requireNonNull(openLeakCaseUseCase, "OpenLeakCaseUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "leakdetection",
                "mission", "Manage leak candidates, leak cases, and escalation paths.",
                "objectives", List.of(
                "Create leak candidates from detection evidence.",
                "Open leak cases.",
                "Escalate leak cases into operational response."
        ),
                "operations", List.of(
                "createLeakCandidate",
                "escalateLeakCase",
                "openLeakCase"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/leakdetection/candidates",
                "POST /api/v1/leakdetection/cases/escalations",
                "POST /api/v1/leakdetection/cases"
        )
        );
    }

    @Override
    @PostMapping({"/create-leak-candidate", "/candidates"})
    public LeakCandidateResponse createLeakCandidate(@Valid @RequestBody CreateLeakCandidateRequest request) {
        Objects.requireNonNull(request, "CreateLeakCandidateRequest must not be null.");
        return LeakdetectionRestMapper.toResponse(createLeakCandidateUseCase.createLeakCandidate(LeakdetectionRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/escalate-leak-case", "/cases/escalations"})
    public String escalateLeakCase(@Valid @RequestBody EscalateLeakCaseRequest request) {
        Objects.requireNonNull(request, "EscalateLeakCaseRequest must not be null.");
        return escalateLeakCaseUseCase.escalateLeakCase(LeakdetectionRestMapper.toCommand(request));
    }

    @Override
    @PostMapping({"/open-leak-case", "/cases"})
    public LeakCaseResponse openLeakCase(@Valid @RequestBody OpenLeakCaseRequest request) {
        Objects.requireNonNull(request, "OpenLeakCaseRequest must not be null.");
        return LeakdetectionRestMapper.toResponse(openLeakCaseUseCase.openLeakCase(LeakdetectionRestMapper.toCommand(request)));
    }

}
