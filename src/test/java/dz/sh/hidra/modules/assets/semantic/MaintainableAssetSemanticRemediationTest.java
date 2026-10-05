/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintainableAssetSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Assets Test
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.semantic
 *
 * @Description : Verifies HMR-045 owner and same-module MaintainableAsset integrity.
 *
 */
package dz.sh.hidra.modules.assets.semantic;

import dz.sh.hidra.modules.assets.application.command.RegisterMaintainableAssetCommand;
import dz.sh.hidra.modules.assets.application.port.out.AssetConditionRecordRepositoryPort;
import dz.sh.hidra.modules.assets.application.port.out.MaintainableAssetRepositoryPort;
import dz.sh.hidra.modules.assets.application.port.out.MaintenanceWorkOrderRepositoryPort;
import dz.sh.hidra.modules.assets.application.service.AssetsApplicationService;
import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class MaintainableAssetSemanticRemediationTest {

    @Test
    void registrationFailsClosedWhenTypedTopologyReferenceDoesNotResolve() {
        AssetsApplicationService service = new AssetsApplicationService(
                mock(MaintainableAssetRepositoryPort.class),
                mock(MaintenanceWorkOrderRepositoryPort.class),
                mock(AssetConditionRecordRepositoryPort.class),
                (type, id) -> java.util.Optional.empty(),
                id -> true,
                id -> true
        );

        RegisterMaintainableAssetCommand command = new RegisterMaintainableAssetCommand(
                "A-1",
                "ASSET-1",
                "Asset 1",
                "asset-type-1",
                "EQUIPMENT",
                "missing-equipment",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "actor-1"
        );

        assertThatThrownBy(() -> service.registerMaintainableAsset(command))
                .isInstanceOf(InvalidAssetsValueException.class)
                .hasMessageContaining("Topology owner boundary");
    }

    @Test
    void migrationProtectsOnlySameModuleReferences() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_045__hmr_045_assets_maintainable_asset.sql"
        ));

        assertThat(sql)
                .contains("FOREIGN KEY (parent_asset_id)")
                .contains("REFERENCES hidra_asset_maintainable_asset (id)")
                .contains("FOREIGN KEY (model_id)")
                .contains("REFERENCES hidra_asset_model (id)")
                .contains("FOREIGN KEY (serial_identity_id)")
                .contains("REFERENCES hidra_asset_serial_identity (id)")
                .doesNotContain("owner_organization_unit_id)")
                .doesNotContain("manufacturer_party_id)")
                .doesNotContain("topology_asset_id)");
    }
}
