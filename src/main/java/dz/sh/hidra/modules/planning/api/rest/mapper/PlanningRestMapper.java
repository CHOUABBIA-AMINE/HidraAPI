/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.mapper
 *
 * @Description : Maps planning REST models to application models.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.mapper;
import dz.sh.hidra.modules.planning.api.rest.request.CreateOperationalPlanRequest;
import dz.sh.hidra.modules.planning.api.rest.request.CreatePlanningPeriodRequest;
import dz.sh.hidra.modules.planning.api.rest.response.OperationalPlanResponse;
import dz.sh.hidra.modules.planning.api.rest.response.PlanningPeriodResponse;
import dz.sh.hidra.modules.planning.application.command.CreateOperationalPlanCommand;
import dz.sh.hidra.modules.planning.application.command.CreatePlanningPeriodCommand;
import dz.sh.hidra.modules.planning.application.dto.OperationalPlanSummaryDto;
import dz.sh.hidra.modules.planning.application.dto.PlanningPeriodSummaryDto;
import java.util.Objects;

/**
 * Maps planning REST models to application models.
 */
public final class PlanningRestMapper {

    private static final PlanningGeneratedRestMapper GENERATED = PlanningGeneratedRestMapper.INSTANCE;

    private PlanningRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateOperationalPlanCommand toCommand(CreateOperationalPlanRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateOperationalPlanRequest must not be null."));
    }

    public static CreatePlanningPeriodCommand toCommand(CreatePlanningPeriodRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreatePlanningPeriodRequest must not be null."));
    }

    public static OperationalPlanResponse toResponse(OperationalPlanSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "OperationalPlanSummaryDto must not be null."));
    }

    public static PlanningPeriodResponse toResponse(PlanningPeriodSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "PlanningPeriodSummaryDto must not be null."));
    }
}
