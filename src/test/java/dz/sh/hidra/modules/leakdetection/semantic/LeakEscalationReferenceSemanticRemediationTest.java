/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakEscalationReferenceSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Leak Detection Test
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.semantic
 *
 * @Description : Verifies optional candidate provenance before escalation persistence.
 *
 */
package dz.sh.hidra.modules.leakdetection.semantic;

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakEscalationReference;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakEscalationStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakEscalationTargetType;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter.JpaLeakEscalationReferenceRepositoryAdapter;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity.LeakEscalationReferenceJpaEntity;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakCandidateJpaRepository;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakEscalationReferenceJpaRepository;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class LeakEscalationReferenceSemanticRemediationTest {
    private final LeakEscalationReferenceJpaRepository repository = mock(LeakEscalationReferenceJpaRepository.class);
    private final LeakCandidateJpaRepository candidates = mock(LeakCandidateJpaRepository.class);
    private final JpaLeakEscalationReferenceRepositoryAdapter adapter =
            new JpaLeakEscalationReferenceRepositoryAdapter(repository, candidates);

    @Test
    void rejectsDanglingCandidateBeforeSaving() {
        assertThatThrownBy(() -> adapter.save(model("missing")))
                .isInstanceOf(InvalidLeakDetectionValueException.class);
        verifyNoInteractions(repository);
    }

    @Test
    void preservesOptionalCandidateAndNeutralExternalTarget() {
        when(repository.save(any(LeakEscalationReferenceJpaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        var saved = adapter.save(model(null));
        assertThat(saved.candidateId()).isNull();
        assertThat(saved.targetReferenceId()).isEqualTo("external-reference");
        verifyNoInteractions(candidates);
    }

    @Test
    void acceptsAnyExistingCandidateWithoutInventingCasePrimaryEquality() {
        when(candidates.existsById("candidate-other")).thenReturn(true);
        when(repository.save(any(LeakEscalationReferenceJpaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        assertThat(adapter.save(model(" candidate-other ")).candidateId()).isEqualTo("candidate-other");
    }

    private static LeakEscalationReference model(String candidate) {
        return new LeakEscalationReference("escalation-1", "case-1", candidate,
                LeakEscalationTargetType.MANUAL_REFERENCE, "external-reference", null, null,
                LeakEscalationStatus.REQUESTED, null, Instant.parse("2026-10-06T00:00:00Z"), null, null);
    }
}
