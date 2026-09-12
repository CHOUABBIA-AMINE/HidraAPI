/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintainableAssetConcurrencyApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.service
 *
 * @Description : Verifies authoritative maintainable-asset stale-write semantics.
 *
 */
package dz.sh.hidra.modules.assets.application.service;

import dz.sh.hidra.modules.assets.application.port.in.UpdateMaintainableAssetUseCase;
import dz.sh.hidra.modules.assets.application.port.out.MaintainableAssetRepositoryPort;
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

class MaintainableAssetConcurrencyApplicationServiceTest {

    private static final Instant TOKEN = Instant.parse("2026-09-12T10:00:00Z");

    private MaintainableAssetRepositoryPort repository;
    private MaintainableAssetConcurrencyApplicationService service;

    @BeforeEach
    void setUp() {
        repository = mock(MaintainableAssetRepositoryPort.class);
        service = new MaintainableAssetConcurrencyApplicationService(repository);
    }

    @Test
    void readsAuthoritativeCurrentToken() {
        when(repository.findById("ASSET-1")).thenReturn(Optional.of(asset(TOKEN)));

        MaintainableAsset result = service.get("ASSET-1");

        assertEquals(TOKEN, result.updatedAt());
        assertEquals("North Pump", result.assetName());
        verify(repository).findById("ASSET-1");
    }

    @Test
    void updatesAssetNameWhenExpectedTokenMatchesAndReturnsRefreshedToken() {
        MaintainableAsset current = asset(TOKEN);
        when(repository.findByIdForUpdate("ASSET-1")).thenReturn(Optional.of(current));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        MaintainableAsset result = service.update("ASSET-1", new UpdateMaintainableAssetUseCase.Command(
                TOKEN, "North Pump A"
        ));

        assertEquals("North Pump A", result.assetName());
        assertTrue(result.updatedAt().isAfter(TOKEN));
        assertEquals(AssetLifecycleStatus.ACTIVE, result.status());
        assertEquals("TOPO-1", result.topologyAssetId());
        assertEquals("CRIT-HIGH", result.criticalityId());
        verify(repository).findByIdForUpdate("ASSET-1");
        verify(repository).save(any(MaintainableAsset.class));
    }

    @Test
    void rejectsStaleExpectedTokenWithoutSaving() {
        when(repository.findByIdForUpdate("ASSET-1")).thenReturn(Optional.of(asset(TOKEN)));

        MaintainableAssetConflictException error = assertThrows(
                MaintainableAssetConflictException.class,
                () -> service.update("ASSET-1", new UpdateMaintainableAssetUseCase.Command(
                        TOKEN.minusSeconds(1), "Stale name"
                ))
        );

        assertTrue(error.getMessage().contains("Refetch the asset"));
        verify(repository, never()).save(any());
    }

    private static MaintainableAsset asset(Instant updatedAt) {
        return new MaintainableAsset(
                "ASSET-1",
                "MA-001",
                "PUMP-001",
                "North Pump",
                "PUMP",
                "EQUIPMENT",
                "TOPO-1",
                "TP-001",
                "Topology Pump",
                null,
                AssetLifecycleStatus.ACTIVE,
                "CRIT-HIGH",
                "ORG-1",
                "Operations",
                "PARTY-1",
                "Manufacturer",
                "MODEL-1",
                "SERIAL-1",
                Instant.parse("2026-09-12T08:00:00Z"),
                Instant.parse("2026-09-12T08:30:00Z"),
                Instant.parse("2026-09-12T09:00:00Z"),
                null,
                "ACTOR-1",
                Instant.parse("2026-09-12T08:00:00Z"),
                updatedAt
        );
    }
}
