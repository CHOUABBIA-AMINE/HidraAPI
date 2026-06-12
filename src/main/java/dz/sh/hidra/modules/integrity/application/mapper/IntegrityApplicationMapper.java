/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.mapper
 *
 * @Description : Maps integrity domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.integrity.application.mapper;

import dz.sh.hidra.modules.integrity.application.dto.IntegrityAssessmentSummaryDto;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityCaseSummaryDto;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityProgramSummaryDto;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityAssessment;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityCase;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityProgram;

/**
 * Maps integrity domain models to DTOs.
 */
public final class IntegrityApplicationMapper {

    private IntegrityApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static IntegrityProgramSummaryDto toSummary(IntegrityProgram program) {
        return new IntegrityProgramSummaryDto(
                program.id(),
                program.code(),
                program.nameFr(),
                program.programTypeId(),
                program.status(),
                program.plannedStartAt(),
                program.plannedEndAt()
        );
    }

    public static IntegrityAssessmentSummaryDto toSummary(IntegrityAssessment assessment) {
        return new IntegrityAssessmentSummaryDto(
                assessment.id(),
                assessment.programId(),
                assessment.assessmentNumber(),
                assessment.title(),
                assessment.assessmentTypeId(),
                assessment.status(),
                assessment.assessmentDate()
        );
    }

    public static IntegrityCaseSummaryDto toSummary(IntegrityCase integrityCase) {
        return new IntegrityCaseSummaryDto(
                integrityCase.id(),
                integrityCase.caseNumber(),
                integrityCase.title(),
                integrityCase.caseTypeId(),
                integrityCase.status(),
                integrityCase.topologyAssetTypeCode(),
                integrityCase.topologyAssetId(),
                integrityCase.primaryDefectId(),
                integrityCase.openedAt(),
                integrityCase.closedAt()
        );
    }
}
