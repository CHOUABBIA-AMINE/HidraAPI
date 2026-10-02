/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringIntegrityController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing integrity REST endpoints.
 *
 */
package dz.sh.hidra.modules.integrity.api.rest.controller;
import dz.sh.hidra.modules.integrity.api.rest.mapper.IntegrityRestMapper;
import dz.sh.hidra.modules.integrity.api.rest.request.CreateIntegrityAssessmentRequest;
import dz.sh.hidra.modules.integrity.api.rest.request.CreateIntegrityProgramRequest;
import dz.sh.hidra.modules.integrity.api.rest.request.OpenIntegrityCaseRequest;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityAssessmentResponse;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityCaseResponse;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityProgramResponse;
import dz.sh.hidra.modules.integrity.application.port.in.CreateIntegrityAssessmentUseCase;
import dz.sh.hidra.modules.integrity.application.port.in.CreateIntegrityProgramUseCase;
import dz.sh.hidra.modules.integrity.application.port.in.OpenIntegrityCaseUseCase;
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
 * Spring MVC adapter exposing integrity REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/integrity")
public class SpringIntegrityController implements IntegrityController {

    private final CreateIntegrityAssessmentUseCase createIntegrityAssessmentUseCase;
    private final CreateIntegrityProgramUseCase createIntegrityProgramUseCase;
    private final OpenIntegrityCaseUseCase openIntegrityCaseUseCase;

    public SpringIntegrityController(
            CreateIntegrityAssessmentUseCase createIntegrityAssessmentUseCase,
            CreateIntegrityProgramUseCase createIntegrityProgramUseCase,
            OpenIntegrityCaseUseCase openIntegrityCaseUseCase
    ) {
        this.createIntegrityAssessmentUseCase = Objects.requireNonNull(createIntegrityAssessmentUseCase, "CreateIntegrityAssessmentUseCase must not be null.");
        this.createIntegrityProgramUseCase = Objects.requireNonNull(createIntegrityProgramUseCase, "CreateIntegrityProgramUseCase must not be null.");
        this.openIntegrityCaseUseCase = Objects.requireNonNull(openIntegrityCaseUseCase, "OpenIntegrityCaseUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "integrity",
                "mission", "Manage pipeline integrity programs, assessments, and integrity cases.",
                "objectives", List.of(
                "Create integrity programs.",
                "Create integrity assessments.",
                "Open integrity cases for follow-up."
        ),
                "operations", List.of(
                "createIntegrityAssessment",
                "createIntegrityProgram",
                "openIntegrityCase"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/integrity/assessments",
                "POST /api/v1/integrity/programs",
                "POST /api/v1/integrity/cases"
        )
        );
    }

    @Override
    @PostMapping({"/create-integrity-assessment", "/assessments"})
    public IntegrityAssessmentResponse createIntegrityAssessment(@Valid @RequestBody CreateIntegrityAssessmentRequest request) {
        Objects.requireNonNull(request, "CreateIntegrityAssessmentRequest must not be null.");
        return IntegrityRestMapper.toResponse(createIntegrityAssessmentUseCase.createIntegrityAssessment(IntegrityRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/create-integrity-program", "/programs"})
    public IntegrityProgramResponse createIntegrityProgram(@Valid @RequestBody CreateIntegrityProgramRequest request) {
        Objects.requireNonNull(request, "CreateIntegrityProgramRequest must not be null.");
        return IntegrityRestMapper.toResponse(createIntegrityProgramUseCase.createIntegrityProgram(IntegrityRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/open-integrity-case", "/cases"})
    public IntegrityCaseResponse openIntegrityCase(@Valid @RequestBody OpenIntegrityCaseRequest request) {
        Objects.requireNonNull(request, "OpenIntegrityCaseRequest must not be null.");
        return IntegrityRestMapper.toResponse(openIntegrityCaseUseCase.openIntegrityCase(IntegrityRestMapper.toCommand(request)));
    }

}
