/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakdetectionRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.mapper
 *
 * @Description : Maps leakdetection REST models to application models.
 *
 */
package dz.sh.hidra.modules.leakdetection.api.rest.mapper;
import dz.sh.hidra.modules.leakdetection.api.rest.request.CreateLeakCandidateRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.request.EscalateLeakCaseRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.request.OpenLeakCaseRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakCandidateResponse;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakCaseResponse;
import dz.sh.hidra.modules.leakdetection.application.command.CreateLeakCandidateCommand;
import dz.sh.hidra.modules.leakdetection.application.command.EscalateLeakCaseCommand;
import dz.sh.hidra.modules.leakdetection.application.command.OpenLeakCaseCommand;
import dz.sh.hidra.modules.leakdetection.application.dto.LeakCandidateSummaryDto;
import dz.sh.hidra.modules.leakdetection.application.dto.LeakCaseSummaryDto;
import java.util.Objects;

/**
 * Maps leakdetection REST models to application models.
 */
public final class LeakdetectionRestMapper {

    private static final LeakdetectionGeneratedRestMapper GENERATED = LeakdetectionGeneratedRestMapper.INSTANCE;

    private LeakdetectionRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateLeakCandidateCommand toCommand(CreateLeakCandidateRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateLeakCandidateRequest must not be null."));
    }

    public static EscalateLeakCaseCommand toCommand(EscalateLeakCaseRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "EscalateLeakCaseRequest must not be null."));
    }

    public static OpenLeakCaseCommand toCommand(OpenLeakCaseRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "OpenLeakCaseRequest must not be null."));
    }

    public static LeakCandidateResponse toResponse(LeakCandidateSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "LeakCandidateSummaryDto must not be null."));
    }

    public static LeakCaseResponse toResponse(LeakCaseSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "LeakCaseSummaryDto must not be null."));
    }
}
