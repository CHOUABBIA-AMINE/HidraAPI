/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionApplicationMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.mapper
 *
 * @Description : Maps leak detection domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.mapper;

import dz.sh.hidra.modules.leakdetection.application.dto.LeakCandidateSummaryDto;
import dz.sh.hidra.modules.leakdetection.application.dto.LeakCaseSummaryDto;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakCandidate;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionCase;

/**
 * Maps leak detection domain models to DTOs.
 */
public final class LeakDetectionApplicationMapper {

    private LeakDetectionApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static LeakCandidateSummaryDto toSummary(LeakCandidate candidate) {
        return new LeakCandidateSummaryDto(
                candidate.id(),
                candidate.candidateNumber(),
                candidate.topologyAssetType(),
                candidate.topologyAssetId(),
                candidate.topologyAssetCode(),
                candidate.confidenceScore(),
                candidate.severityLevel(),
                candidate.status(),
                candidate.suspectedAt()
        );
    }

    public static LeakCaseSummaryDto toSummary(LeakDetectionCase leakCase) {
        return new LeakCaseSummaryDto(
                leakCase.id(),
                leakCase.caseNumber(),
                leakCase.primaryCandidateId(),
                leakCase.topologyAssetType(),
                leakCase.topologyAssetId(),
                leakCase.status(),
                leakCase.severityLevel(),
                leakCase.confidenceScore(),
                leakCase.openedAt(),
                leakCase.closedAt()
        );
    }
}
