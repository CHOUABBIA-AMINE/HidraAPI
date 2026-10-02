package dz.sh.hidra.modules.leakdetection.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakCandidateRepositoryPort;
import dz.sh.hidra.modules.leakdetection.application.port.out.LeakDetectionCaseRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakCandidate;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionCase;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakCandidateStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakDetectionCaseStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakSeverityLevel;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LeakDetectionQueryApplicationServiceTest {

    @Mock
    private LeakCandidateRepositoryPort candidateRepository;

    @Mock
    private LeakDetectionCaseRepositoryPort caseRepository;

    @Test
    void returnsPagedCandidatesAndCandidateDetail() {
        LeakCandidate candidate = candidate("cand-1");
        when(candidateRepository.findAll(0, 50)).thenReturn(List.of(candidate));
        when(candidateRepository.count()).thenReturn(1L);
        when(candidateRepository.findById("cand-1")).thenReturn(Optional.of(candidate));

        var service = new LeakDetectionQueryApplicationService(candidateRepository, caseRepository);
        var page = service.candidates(0, 50);

        assertThat(page.content()).hasSize(1);
        assertThat(page.content().getFirst().candidateNumber()).isEqualTo("LC-001");
        assertThat(page.content().getFirst().status()).isEqualTo("VERIFIED");
        assertThat(page.totalElements()).isEqualTo(1);
        assertThat(page.hasNext()).isFalse();
        assertThat(service.candidate("cand-1").severityLevel()).isEqualTo("HIGH");
    }

    @Test
    void returnsPagedCasesAndCaseDetailAndRejectsUnknownIds() {
        LeakDetectionCase leakCase = leakCase("case-1");
        when(caseRepository.findAll(0, 50)).thenReturn(List.of(leakCase));
        when(caseRepository.count()).thenReturn(1L);
        when(caseRepository.findById("case-1")).thenReturn(Optional.of(leakCase));
        when(candidateRepository.findById("missing")).thenReturn(Optional.empty());
        when(caseRepository.findById("missing")).thenReturn(Optional.empty());

        var service = new LeakDetectionQueryApplicationService(candidateRepository, caseRepository);
        var page = service.cases(0, 50);

        assertThat(page.content()).hasSize(1);
        assertThat(page.content().getFirst().caseNumber()).isEqualTo("CASE-001");
        assertThat(page.content().getFirst().status()).isEqualTo("UNDER_INVESTIGATION");
        assertThat(service.leakCase("case-1").primaryCandidateId()).isEqualTo("cand-1");
        assertThatThrownBy(() -> service.candidate("missing")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Unknown leak candidate");
        assertThatThrownBy(() -> service.leakCase("missing")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Unknown leak case");
    }

    @Test
    void rejectsInvalidPaging() {
        var service = new LeakDetectionQueryApplicationService(candidateRepository, caseRepository);
        assertThatThrownBy(() -> service.candidates(-1, 50)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> service.cases(0, 201)).isInstanceOf(IllegalArgumentException.class);
    }

    private LeakCandidate candidate(String id) {
        Instant now = Instant.parse("2026-09-11T20:00:00Z");
        return new LeakCandidate(
                id, "run-1", "profile-1", "LC-001", "PIPELINE", "pipe-1", "PL-01", "Pipeline 01",
                now, now, new BigDecimal("0.91"), LeakSeverityLevel.HIGH, LeakCandidateStatus.VERIFIED,
                "Leak evidence detected", "corr-1", now, now
        );
    }

    private LeakDetectionCase leakCase(String id) {
        Instant now = Instant.parse("2026-09-11T20:00:00Z");
        return new LeakDetectionCase(
                id, "CASE-001", "cand-1", "PIPELINE", "pipe-1", "PL-01", "org-1",
                LeakDetectionCaseStatus.UNDER_INVESTIGATION, LeakSeverityLevel.HIGH, new BigDecimal("0.91"),
                now, null, "actor-1", null, null, "corr-1", now, now
        );
    }
}
