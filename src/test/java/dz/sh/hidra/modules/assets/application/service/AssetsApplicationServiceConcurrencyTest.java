/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsApplicationServiceConcurrencyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.service
 *
 * @Description : Verifies authoritative maintainable asset concurrency semantics.
 *
 */
package dz.sh.hidra.modules.assets.application.service;

import dz.sh.hidra.modules.assets.application.port.in.UpdateMaintainableAssetUseCase;
import dz.sh.hidra.modules.assets.application.port.out.AssetConditionRecordRepositoryPort;
import dz.sh.hidra.modules.assets.application.port.out.MaintainableAssetRepositoryPort;
import dz.sh.hidra.modules.assets.application.port.out.MaintenanceWorkOrderRepositoryPort;
import dz.sh.hidra.modules.assets.domain.exception.MaintainableAssetConflictException;
import dz.sh.hidra.modules.assets.domain.model.MaintainableAsset;
import dz.sh.hidra.modules.assets.domain.value.AssetLifecycleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AssetsApplicationServiceConcurrencyTest {

    private static final Instant TOKEN = Instant.parse("2026-09-12T10:00:00Z");

    private MaintainableAssetRepositoryPort assetRepository;
    private AssetsApplicationService service;

    @BeforeEach
    void setUp() {
        assetRepository = mock(MaintainableAssetRepositoryPort.class);
        MaintenanceWorkOrderRepositoryPort workOrderRepository = mock(MaintenanceWorkOrderRepositoryPort.class);
        AssetConditionRecordRepositoryPort conditionRepository = mock(AssetConditionRecordRepositoryPort.class);
        service = new AssetsApplicationService(assetRepository, workOrderRepository, conditionRepository);
    }

    @Test
    void renamesAssetWhenExpectedTokenMatchesAndPreservesOtherFields() {
        MaintainableAsset existing = asset(TOKEN);
        when(assetRepository.findByIdForUpdate("ASSET-1")).thenReturn(Optional.of(existing));
        when(assetRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.updateMaintainableAsset(
                "ASSET-1",
                new UpdateMaintainableAssetUseCase.Command(TOKEN, "North Pump Renamed")
        );

        assertEquals("North Pump Renamed", result.assetName());
        assertEquals("CRITICAL", result.criticalityId());
        assertEquals(AssetLifecycleStatus.ACTIVE, result.status());
        assertEquals("TOPO-1", result.topologyAssetId());
        assertTrue(result.updatedAt().isAfter(TOKEN));
        verify(assetRepository).findByIdForUpdate("ASSET-1");
        verify(assetRepository).save(any(MaintainableAsset.class));
    }

    @Test
    void rejectsStaleExpectedTokenWithoutSaving() {
        MaintainableAsset existing = asset(TOKEN);
        when(assetRepository.findByIdForUpdate("ASSET-1")).thenReturn(Optional.of(existing));

        MaintainableAssetConflictException error = assertThrows(
                MaintainableAssetConflictException.class,
                () -> service.updateMaintainableAsset(
                        "ASSET-1",
                        new UpdateMaintainableAssetUseCase.Command(TOKEN.minusSeconds(1), "Stale Rename")
                )
        );

        assertTrue(error.getMessage().contains("Refetch the asset"));
        verify(assetRepository, never()).save(any());
    }

    private static MaintainableAsset asset(Instant updatedAt) {
        return new MaintainableAsset(
                "ASSET-1",
                "MA-001",
                "PUMP-001",
                "North Pump",
                "PUMP",
                "PUMP",
                "TOPO-1",
                "TP-001",
                "Topology Pump",
                null,
                AssetLifecycleStatus.ACTIVE,
                "CRITICAL",
                "ORG-1",
                "Maintenance",
                "PARTY-1",
                "Manufacturer",
                "MODEL-1",
                "SERIAL-1",
                Instant.parse("2026-09-01T08:00:00Z"),
                Instant.parse("2026-09-02T08:00:00Z"),
                Instant.parse("2026-09-03T08:00:00Z"),
                null,
                "ACTOR-1",
                Instant.parse("2026-09-01T08:00:00Z"),
                updatedAt
        );
    }
}
