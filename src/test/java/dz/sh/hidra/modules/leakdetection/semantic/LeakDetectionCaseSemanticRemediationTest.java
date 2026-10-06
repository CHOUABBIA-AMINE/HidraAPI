/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionCaseSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Leak Detection Test
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.semantic
 *
 * @Description : Verifies HMR-051 owner validation and snapshot preservation.
 *
 */
package dz.sh.hidra.modules.leakdetection.semantic;

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionCase;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakDetectionCaseStatus;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter.JpaLeakDetectionCaseRepositoryAdapter;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity.LeakDetectionCaseJpaEntity;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakDetectionCaseJpaRepository;
import dz.sh.hidra.modules.organization.application.contract.leakdetection.LeakDetectionOrganizationUnitReferenceContract;
import dz.sh.hidra.modules.topology.application.contract.leakdetection.LeakDetectionTopologyAssetContract;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class LeakDetectionCaseSemanticRemediationTest {
    private static final Instant NOW = Instant.parse("2026-10-06T00:00:00Z");
    private final LeakDetectionCaseJpaRepository repository = mock(LeakDetectionCaseJpaRepository.class);
    private final LeakDetectionTopologyAssetContract topology = mock(LeakDetectionTopologyAssetContract.class);
    private final LeakDetectionOrganizationUnitReferenceContract organization =
            mock(LeakDetectionOrganizationUnitReferenceContract.class);
    private final JpaLeakDetectionCaseRepositoryAdapter adapter =
            new JpaLeakDetectionCaseRepositoryAdapter(repository, topology, organization);

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void requiresTypedTopologyIdentity(String type) {
        assertThatThrownBy(() -> model(type, null))
                .isInstanceOf(InvalidLeakDetectionValueException.class)
                .hasMessageContaining("asset type");
    }

    @Test
    void rejectsUnsupportedTypeBeforePersistence() {
        when(topology.resolve("UNKNOWN", "asset-1"))
                .thenReturn(LeakDetectionTopologyAssetContract.AssetResolution.unsupported());
        assertThatThrownBy(() -> adapter.save(model("UNKNOWN", null)))
                .isInstanceOf(InvalidLeakDetectionValueException.class);
        verifyNoInteractions(repository, organization);
    }

    @Test
    void rejectsMissingTopologyAssetBeforePersistence() {
        when(topology.resolve("PIPELINE", "asset-1"))
                .thenReturn(LeakDetectionTopologyAssetContract.AssetResolution.missing());
        assertThatThrownBy(() -> adapter.save(model("PIPELINE", null)))
                .isInstanceOf(InvalidLeakDetectionValueException.class);
        verifyNoInteractions(repository, organization);
    }

    @Test
    void rejectsMissingOrganizationBeforePersistence() {
        existingTopology();
        when(organization.exists("missing-org")).thenReturn(false);
        assertThatThrownBy(() -> adapter.save(model("PIPELINE", "missing-org")))
                .isInstanceOf(InvalidLeakDetectionValueException.class)
                .hasMessageContaining("OrganizationUnit");
        verifyNoInteractions(repository);
    }

    @Test
    void acceptsOwnerAndPreservesHistoricalSnapshot() {
        existingTopology();
        when(organization.exists("org-1")).thenReturn(true);
        when(repository.save(any(LeakDetectionCaseJpaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        var saved = adapter.save(model("PIPELINE", " org-1 "));
        assertThat(saved.topologyAssetCode()).isEqualTo("HISTORICAL-CODE");
        assertThat(saved.topologyAssetId()).isEqualTo("asset-1");
        assertThat(saved.owningOrganizationUnitId()).isEqualTo("org-1");
        verify(organization).exists("org-1");
    }

    @Test
    void acceptsAbsentOwnerWithoutMakingItMandatory() {
        existingTopology();
        when(repository.save(any(LeakDetectionCaseJpaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        assertThat(adapter.save(model("PIPELINE", "  ")).owningOrganizationUnitId()).isNull();
        verifyNoInteractions(organization);
    }

    @Test
    void ownerLookupFailureCannotPersist() {
        when(topology.resolve("PIPELINE", "asset-1"))
                .thenThrow(new IllegalStateException("Owner lookup unavailable"));
        assertThatThrownBy(() -> adapter.save(model("PIPELINE", null)))
                .isInstanceOf(IllegalStateException.class);
        verifyNoInteractions(repository);
    }

    private void existingTopology() {
        when(topology.resolve("PIPELINE", "asset-1"))
                .thenReturn(LeakDetectionTopologyAssetContract.AssetResolution.resolved(
                        "asset-1", "CURRENT-CODE", "Current name"));
    }

    private static LeakDetectionCase model(String type, String owner) {
        return new LeakDetectionCase("case-1", "CASE-001", "candidate-1", type, "asset-1",
                "HISTORICAL-CODE", owner, LeakDetectionCaseStatus.OPEN, null, null,
                NOW, null, null, null, null, null, NOW, NOW);
    }
}
