/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionQueryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.controller
 *
 * @Description : Exposes leak candidate and case list/detail reads.
 *
 */
package dz.sh.hidra.modules.leakdetection.api.rest.controller;

import dz.sh.hidra.modules.leakdetection.application.port.in.LeakDetectionQueryUseCase;
import dz.sh.hidra.modules.leakdetection.application.port.in.LeakDetectionQueryUseCase.LeakCandidateView;
import dz.sh.hidra.modules.leakdetection.application.port.in.LeakDetectionQueryUseCase.LeakCaseView;
import dz.sh.hidra.modules.leakdetection.application.port.in.LeakDetectionQueryUseCase.Page;
import java.util.Objects;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/leakdetection")
public class LeakDetectionQueryController {

    private final LeakDetectionQueryUseCase queryUseCase;

    public LeakDetectionQueryController(LeakDetectionQueryUseCase queryUseCase) {
        this.queryUseCase = Objects.requireNonNull(queryUseCase, "LeakDetectionQueryUseCase must not be null.");
    }

    @NativeProtectedOperation
    @GetMapping("/candidates")
    public Page<LeakCandidateView> candidates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return queryUseCase.candidates(page, size);
    }

    @NativeProtectedOperation
    @GetMapping("/candidates/{id}")
    public LeakCandidateView candidate(@PathVariable String id) {
        return queryUseCase.candidate(id);
    }

    @ExceptionHandler(java.util.NoSuchElementException.class)
    ProblemDetail hidden(java.util.NoSuchElementException ignored) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,"Protected Leak candidate unavailable.");
    }

    @GetMapping("/cases")
    public Page<LeakCaseView> cases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return queryUseCase.cases(page, size);
    }

    @GetMapping("/cases/{id}")
    public LeakCaseView leakCase(@PathVariable String id) {
        return queryUseCase.leakCase(id);
    }
}
