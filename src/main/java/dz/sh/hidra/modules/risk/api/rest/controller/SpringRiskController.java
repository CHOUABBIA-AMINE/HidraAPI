/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringRiskController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing risk REST endpoints.
 *
 */
package dz.sh.hidra.modules.risk.api.rest.controller;
import dz.sh.hidra.modules.risk.api.rest.mapper.RiskRestMapper;
import dz.sh.hidra.modules.risk.api.rest.request.AddRiskEvidenceRequest;
import dz.sh.hidra.modules.risk.api.rest.request.CreateRiskAssessmentRequest;
import dz.sh.hidra.modules.risk.api.rest.request.CreateRiskRegisterRequest;
import dz.sh.hidra.modules.risk.api.rest.response.RiskAssessmentResponse;
import dz.sh.hidra.modules.risk.api.rest.response.RiskRegisterResponse;
import dz.sh.hidra.modules.risk.application.port.in.AddRiskEvidenceUseCase;
import dz.sh.hidra.modules.risk.application.port.in.CreateRiskAssessmentUseCase;
import dz.sh.hidra.modules.risk.application.port.in.CreateRiskRegisterUseCase;
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
 * Spring MVC adapter exposing risk REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/risk")
public class SpringRiskController implements RiskController {

    private final AddRiskEvidenceUseCase addRiskEvidenceUseCase;
    private final CreateRiskAssessmentUseCase createRiskAssessmentUseCase;
    private final CreateRiskRegisterUseCase createRiskRegisterUseCase;

    public SpringRiskController(
            AddRiskEvidenceUseCase addRiskEvidenceUseCase,
            CreateRiskAssessmentUseCase createRiskAssessmentUseCase,
            CreateRiskRegisterUseCase createRiskRegisterUseCase
    ) {
        this.addRiskEvidenceUseCase = Objects.requireNonNull(addRiskEvidenceUseCase, "AddRiskEvidenceUseCase must not be null.");
        this.createRiskAssessmentUseCase = Objects.requireNonNull(createRiskAssessmentUseCase, "CreateRiskAssessmentUseCase must not be null.");
        this.createRiskRegisterUseCase = Objects.requireNonNull(createRiskRegisterUseCase, "CreateRiskRegisterUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "risk",
                "mission", "Manage risk registers, assessments, and evidence for pipeline operational risk.",
                "objectives", List.of(
                "Create risk registers.",
                "Create risk assessments.",
                "Add risk evidence."
        ),
                "operations", List.of(
                "addRiskEvidence",
                "createRiskAssessment",
                "createRiskRegister"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/risk/evidence",
                "POST /api/v1/risk/assessments",
                "POST /api/v1/risk/registers"
        )
        );
    }

    @Override
    @PostMapping({"/add-risk-evidence", "/evidence"})
    public String addRiskEvidence(@Valid @RequestBody AddRiskEvidenceRequest request) {
        Objects.requireNonNull(request, "AddRiskEvidenceRequest must not be null.");
        return addRiskEvidenceUseCase.addRiskEvidence(RiskRestMapper.toCommand(request));
    }

    @Override
    @PostMapping({"/create-risk-assessment", "/assessments"})
    public RiskAssessmentResponse createRiskAssessment(@Valid @RequestBody CreateRiskAssessmentRequest request) {
        Objects.requireNonNull(request, "CreateRiskAssessmentRequest must not be null.");
        return RiskRestMapper.toResponse(createRiskAssessmentUseCase.createRiskAssessment(RiskRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/create-risk-register", "/registers"})
    public RiskRegisterResponse createRiskRegister(@Valid @RequestBody CreateRiskRegisterRequest request) {
        Objects.requireNonNull(request, "CreateRiskRegisterRequest must not be null.");
        return RiskRestMapper.toResponse(createRiskRegisterUseCase.createRiskRegister(RiskRestMapper.toCommand(request)));
    }

}
