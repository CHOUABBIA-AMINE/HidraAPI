/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionQueryApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.service
 *
 * @Description : Implements read-only leak candidate and case queries.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.service;

import dz.sh.hidra.modules.leakdetection.application.port.in.LeakDetectionQueryUseCase;
import dz.sh.hidra.modules.leakdetection.application.port.out.LeakCandidateRepositoryPort;
import dz.sh.hidra.modules.leakdetection.application.port.out.LeakDetectionCaseRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakCandidate;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionCase;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class LeakDetectionQueryApplicationService implements LeakDetectionQueryUseCase {

    private static final int MAX_PAGE_SIZE = 200;
    private final LeakCandidateRepositoryPort candidateRepositoryPort;
    private final LeakDetectionCaseRepositoryPort caseRepositoryPort;

    public LeakDetectionQueryApplicationService(
            LeakCandidateRepositoryPort candidateRepositoryPort,
            LeakDetectionCaseRepositoryPort caseRepositoryPort
    ) {
        this.candidateRepositoryPort = Objects.requireNonNull(candidateRepositoryPort, "LeakCandidateRepositoryPort must not be null.");
        this.caseRepositoryPort = Objects.requireNonNull(caseRepositoryPort, "LeakDetectionCaseRepositoryPort must not be null.");
    }

    @Override
    public Page<LeakCandidateView> candidates(int page, int size) {
        validatePage(page, size);
        List<LeakCandidateView> content = candidateRepositoryPort.findAll(page, size).stream().map(this::toCandidateView).toList();
        long total = candidateRepositoryPort.count();
        int totalPages = total == 0 ? 0 : (int) Math.ceil((double) total / size);
        return new Page<>(content, page, size, total, totalPages, page + 1 < totalPages);
    }

    @Override
    public LeakCandidateView candidate(String id) {
        String normalizedId = requireId(id, "candidate");
        return candidateRepositoryPort.findById(normalizedId)
                .map(this::toCandidateView)
                .orElseThrow(() -> new IllegalArgumentException("Unknown leak candidate: " + id));
    }

    @Override
    public Page<LeakCaseView> cases(int page, int size) {
        validatePage(page, size);
        List<LeakCaseView> content = caseRepositoryPort.findAll(page, size).stream().map(this::toCaseView).toList();
        long total = caseRepositoryPort.count();
        int totalPages = total == 0 ? 0 : (int) Math.ceil((double) total / size);
        return new Page<>(content, page, size, total, totalPages, page + 1 < totalPages);
    }

    @Override
    public LeakCaseView leakCase(String id) {
        String normalizedId = requireId(id, "case");
        return caseRepositoryPort.findById(normalizedId)
                .map(this::toCaseView)
                .orElseThrow(() -> new IllegalArgumentException("Unknown leak case: " + id));
    }

    private void validatePage(int page, int size) {
        if (page < 0) throw new IllegalArgumentException("page must be >= 0.");
        if (size < 1 || size > MAX_PAGE_SIZE) throw new IllegalArgumentException("size must be between 1 and 200.");
    }

    private String requireId(String id, String resource) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("leak " + resource + " id must not be blank.");
        return id.trim();
    }

    private LeakCandidateView toCandidateView(LeakCandidate candidate) {
        return new LeakCandidateView(
                candidate.id(), candidate.runId(), candidate.profileId(), candidate.candidateNumber(),
                candidate.topologyAssetType(), candidate.topologyAssetId(), candidate.topologyAssetCode(),
                candidate.topologyAssetNameSnapshot(), candidate.suspectedAt(), candidate.firstEvidenceAt(),
                candidate.confidenceScore(), candidate.severityLevel() == null ? null : candidate.severityLevel().name(),
                candidate.status() == null ? null : candidate.status().name(), candidate.summary(),
                candidate.correlationId(), candidate.createdAt(), candidate.updatedAt()
        );
    }

    private LeakCaseView toCaseView(LeakDetectionCase leakCase) {
        return new LeakCaseView(
                leakCase.id(), leakCase.caseNumber(), leakCase.primaryCandidateId(), leakCase.topologyAssetType(),
                leakCase.topologyAssetId(), leakCase.topologyAssetCode(), leakCase.owningOrganizationUnitId(),
                leakCase.status() == null ? null : leakCase.status().name(),
                leakCase.severityLevel() == null ? null : leakCase.severityLevel().name(),
                leakCase.confidenceScore(), leakCase.openedAt(), leakCase.closedAt(), leakCase.openedByActorId(),
                leakCase.closedByActorId(), leakCase.closureReasonId(), leakCase.correlationId(),
                leakCase.createdAt(), leakCase.updatedAt()
        );
    }
}
