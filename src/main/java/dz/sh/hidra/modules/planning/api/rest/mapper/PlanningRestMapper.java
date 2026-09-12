/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
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
import dz.sh.hidra.modules.planning.api.rest.request.UpdateOperationalPlanRequest;
import dz.sh.hidra.modules.planning.api.rest.response.OperationalPlanResponse;
import dz.sh.hidra.modules.planning.api.rest.response.OperationalPlanUpdateResponse;
import dz.sh.hidra.modules.planning.api.rest.response.PlanningPeriodResponse;
import dz.sh.hidra.modules.planning.application.command.CreateOperationalPlanCommand;
import dz.sh.hidra.modules.planning.application.command.CreatePlanningPeriodCommand;
import dz.sh.hidra.modules.planning.application.command.UpdateOperationalPlanCommand;
import dz.sh.hidra.modules.planning.application.dto.OperationalPlanSummaryDto;
import dz.sh.hidra.modules.planning.application.dto.PlanningPeriodSummaryDto;
import dz.sh.hidra.modules.planning.application.port.in.UpdateOperationalPlanUseCase.OperationalPlanUpdateResult;

/**
 * Maps planning REST models to application models.
 */
public final class PlanningRestMapper {

    private PlanningRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateOperationalPlanCommand toCommand(CreateOperationalPlanRequest request) {
        return new CreateOperationalPlanCommand(
                request.periodId(),
                request.code(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.planTypeId(),
                request.productTypeId(),
                request.topologyScopeType(),
                request.topologyScopeId(),
                request.topologyScopeCode(),
                request.topologyScopeNameSnapshot(),
                request.responsibleOrganizationUnitId(),
                request.createdByActorId()
        );
    }

    public static CreatePlanningPeriodCommand toCommand(CreatePlanningPeriodRequest request) {
        return new CreatePlanningPeriodCommand(
                request.code(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.periodTypeId(),
                request.periodStart(),
                request.periodEnd(),
                request.timeZone(),
                request.createdByActorId()
        );
    }

    public static UpdateOperationalPlanCommand toCommand(String id, UpdateOperationalPlanRequest request) {
        return new UpdateOperationalPlanCommand(
                id,
                request.expectedUpdatedAt(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.responsibleOrganizationUnitId()
        );
    }

    public static OperationalPlanResponse toResponse(OperationalPlanSummaryDto dto) {
        return new OperationalPlanResponse(
                dto.id(),
                dto.periodId(),
                dto.code(),
                dto.nameFr(),
                dto.topologyScopeType(),
                dto.topologyScopeId(),
                dto.status(),
                dto.approvedRevisionId()
        );
    }

    public static OperationalPlanUpdateResponse toResponse(OperationalPlanUpdateResult result) {
        return new OperationalPlanUpdateResponse(
                result.id(),
                result.nameAr(),
                result.nameFr(),
                result.nameEn(),
                result.responsibleOrganizationUnitId(),
                result.updatedAt()
        );
    }

    public static PlanningPeriodResponse toResponse(PlanningPeriodSummaryDto dto) {
        return new PlanningPeriodResponse(
                dto.id(),
                dto.code(),
                dto.nameFr(),
                dto.periodStart(),
                dto.periodEnd(),
                dto.timeZone(),
                dto.status()
        );
    }
}
