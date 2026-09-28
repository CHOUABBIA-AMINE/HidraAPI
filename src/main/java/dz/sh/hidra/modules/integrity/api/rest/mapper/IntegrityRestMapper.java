/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.api.rest.mapper
 *
 * @Description : Maps integrity REST models to application models.
 *
 */
package dz.sh.hidra.modules.integrity.api.rest.mapper;
import dz.sh.hidra.modules.integrity.api.rest.request.CreateIntegrityAssessmentRequest;
import dz.sh.hidra.modules.integrity.api.rest.request.CreateIntegrityProgramRequest;
import dz.sh.hidra.modules.integrity.api.rest.request.OpenIntegrityCaseRequest;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityAssessmentResponse;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityCaseResponse;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityProgramResponse;
import dz.sh.hidra.modules.integrity.application.command.CreateIntegrityAssessmentCommand;
import dz.sh.hidra.modules.integrity.application.command.CreateIntegrityProgramCommand;
import dz.sh.hidra.modules.integrity.application.command.OpenIntegrityCaseCommand;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityAssessmentSummaryDto;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityCaseSummaryDto;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityProgramSummaryDto;
import java.util.Objects;

/**
 * Maps integrity REST models to application models.
 */
public final class IntegrityRestMapper {

    private static final IntegrityGeneratedRestMapper GENERATED = IntegrityGeneratedRestMapper.INSTANCE;

    private IntegrityRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateIntegrityAssessmentCommand toCommand(CreateIntegrityAssessmentRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateIntegrityAssessmentRequest must not be null."));
    }

    public static CreateIntegrityProgramCommand toCommand(CreateIntegrityProgramRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateIntegrityProgramRequest must not be null."));
    }

    public static OpenIntegrityCaseCommand toCommand(OpenIntegrityCaseRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "OpenIntegrityCaseRequest must not be null."));
    }

    public static IntegrityAssessmentResponse toResponse(IntegrityAssessmentSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "IntegrityAssessmentSummaryDto must not be null."));
    }

    public static IntegrityProgramResponse toResponse(IntegrityProgramSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "IntegrityProgramSummaryDto must not be null."));
    }

    public static IntegrityCaseResponse toResponse(IntegrityCaseSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "IntegrityCaseSummaryDto must not be null."));
    }
}
