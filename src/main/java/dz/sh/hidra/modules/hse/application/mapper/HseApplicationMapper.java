/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.mapper
 *
 * @Description : Maps HSE domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.hse.application.mapper;

import dz.sh.hidra.modules.hse.application.dto.HseCapaSummaryDto;
import dz.sh.hidra.modules.hse.application.dto.HseCaseSummaryDto;
import dz.sh.hidra.modules.hse.domain.model.HseCase;
import dz.sh.hidra.modules.hse.domain.model.HseCorrectivePreventiveAction;

/**
 * Maps HSE domain models to DTOs.
 */
public final class HseApplicationMapper {

    private HseApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static HseCaseSummaryDto toSummary(HseCase hseCase) {
        return new HseCaseSummaryDto(
                hseCase.id(),
                hseCase.caseNumber(),
                hseCase.title(),
                hseCase.caseTypeId(),
                hseCase.severityId(),
                hseCase.status(),
                hseCase.sourceType(),
                hseCase.incidentReferenceId(),
                hseCase.targetModule(),
                hseCase.targetTypeCode(),
                hseCase.targetId(),
                hseCase.reportedAt(),
                hseCase.closedAt()
        );
    }

    public static HseCapaSummaryDto toSummary(HseCorrectivePreventiveAction action) {
        return new HseCapaSummaryDto(
                action.id(),
                action.hseCaseId(),
                action.actionNumber(),
                action.title(),
                action.status(),
                action.targetDate(),
                action.completedAt()
        );
    }
}
