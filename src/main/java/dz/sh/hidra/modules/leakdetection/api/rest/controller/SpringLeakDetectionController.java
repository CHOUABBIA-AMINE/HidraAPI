/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringLeakDetectionController
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

import dz.sh.hidra.modules.leakdetection.api.rest.mapper.LeakDetectionRestMapper;
import dz.sh.hidra.modules.leakdetection.api.rest.request.CreateLeakCandidateRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.request.OpenLeakCaseRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakCandidateResponse;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakCaseResponse;
import dz.sh.hidra.modules.leakdetection.application.port.in.CreateLeakCandidateUseCase;
import dz.sh.hidra.modules.leakdetection.application.port.in.OpenLeakCaseUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing leakdetection REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/leak-detection")
public class SpringLeakDetectionController implements LeakDetectionController {

    private final CreateLeakCandidateUseCase createLeakCandidateUseCase;
    private final OpenLeakCaseUseCase openLeakCaseUseCase;

    public SpringLeakDetectionController(
            CreateLeakCandidateUseCase createLeakCandidateUseCase,
            OpenLeakCaseUseCase openLeakCaseUseCase
    ) {
        this.createLeakCandidateUseCase = Objects.requireNonNull(createLeakCandidateUseCase, "CreateLeakCandidateUseCase must not be null.");
        this.openLeakCaseUseCase = Objects.requireNonNull(openLeakCaseUseCase, "OpenLeakCaseUseCase must not be null.");
    }


    @Override
    @PostMapping("/create-leak-candidate")
    public LeakCandidateResponse createLeakCandidate(@Valid @RequestBody CreateLeakCandidateRequest request) {
        Objects.requireNonNull(request, "CreateLeakCandidateRequest must not be null.");
        return LeakDetectionRestMapper.toResponse(createLeakCandidateUseCase.createLeakCandidate(LeakDetectionRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/open-leak-case")
    public LeakCaseResponse openLeakCase(@Valid @RequestBody OpenLeakCaseRequest request) {
        Objects.requireNonNull(request, "OpenLeakCaseRequest must not be null.");
        return LeakDetectionRestMapper.toResponse(openLeakCaseUseCase.openLeakCase(LeakDetectionRestMapper.toCommand(request)));
    }

}
