/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.mapper
 *
 * @Description : Maps leak detection REST models to application models.
 *
 */
package dz.sh.hidra.modules.leakdetection.api.rest.mapper;

import dz.sh.hidra.modules.leakdetection.api.rest.request.CreateLeakCandidateRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.request.OpenLeakCaseRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakCandidateResponse;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakCaseResponse;
import dz.sh.hidra.modules.leakdetection.application.command.CreateLeakCandidateCommand;
import dz.sh.hidra.modules.leakdetection.application.command.OpenLeakCaseCommand;
import dz.sh.hidra.modules.leakdetection.application.dto.LeakCandidateSummaryDto;
import dz.sh.hidra.modules.leakdetection.application.dto.LeakCaseSummaryDto;

/**
 * Maps leak detection REST models to application models.
 */
public final class LeakDetectionRestMapper {

    private LeakDetectionRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateLeakCandidateCommand toCommand(CreateLeakCandidateRequest request) {
        return new CreateLeakCandidateCommand(
                request.runId(),
                request.profileId(),
                request.candidateNumber(),
                request.topologyAssetType(),
                request.topologyAssetId(),
                request.topologyAssetCode(),
                request.topologyAssetNameSnapshot(),
                request.suspectedAt(),
                request.firstEvidenceAt(),
                request.confidenceScore(),
                request.summary(),
                request.correlationId()
        );
    }

    public static OpenLeakCaseCommand toCommand(OpenLeakCaseRequest request) {
        return new OpenLeakCaseCommand(
                request.caseNumber(),
                request.primaryCandidateId(),
                request.topologyAssetType(),
                request.topologyAssetId(),
                request.topologyAssetCode(),
                request.owningOrganizationUnitId(),
                request.confidenceScore(),
                request.openedByActorId(),
                request.correlationId()
        );
    }

    public static LeakCandidateResponse toResponse(LeakCandidateSummaryDto dto) {
        return new LeakCandidateResponse(
                dto.id(),
                dto.candidateNumber(),
                dto.topologyAssetType(),
                dto.topologyAssetId(),
                dto.topologyAssetCode(),
                dto.confidenceScore(),
                dto.severityLevel(),
                dto.status(),
                dto.suspectedAt()
        );
    }

    public static LeakCaseResponse toResponse(LeakCaseSummaryDto dto) {
        return new LeakCaseResponse(
                dto.id(),
                dto.caseNumber(),
                dto.primaryCandidateId(),
                dto.topologyAssetType(),
                dto.topologyAssetId(),
                dto.status(),
                dto.severityLevel(),
                dto.confidenceScore(),
                dto.openedAt(),
                dto.closedAt()
        );
    }
}
