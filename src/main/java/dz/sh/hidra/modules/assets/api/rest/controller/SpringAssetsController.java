/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringAssetsController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing assets REST endpoints.
 *
 */
package dz.sh.hidra.modules.assets.api.rest.controller;
import dz.sh.hidra.modules.assets.api.rest.mapper.AssetsRestMapper;
import dz.sh.hidra.modules.assets.api.rest.request.CreateMaintenanceWorkOrderRequest;
import dz.sh.hidra.modules.assets.api.rest.request.RecordAssetConditionRequest;
import dz.sh.hidra.modules.assets.api.rest.request.RegisterMaintainableAssetRequest;
import dz.sh.hidra.modules.assets.api.rest.response.AssetConditionResponse;
import dz.sh.hidra.modules.assets.api.rest.response.MaintainableAssetResponse;
import dz.sh.hidra.modules.assets.api.rest.response.MaintenanceWorkOrderResponse;
import dz.sh.hidra.modules.assets.application.port.in.CreateMaintenanceWorkOrderUseCase;
import dz.sh.hidra.modules.assets.application.port.in.RecordAssetConditionUseCase;
import dz.sh.hidra.modules.assets.application.port.in.RegisterMaintainableAssetUseCase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC adapter exposing assets REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/assets")
public class SpringAssetsController implements AssetsController {

    private final CreateMaintenanceWorkOrderUseCase createMaintenanceWorkOrderUseCase;
    private final RecordAssetConditionUseCase recordAssetConditionUseCase;
    private final RegisterMaintainableAssetUseCase registerMaintainableAssetUseCase;

    public SpringAssetsController(
            CreateMaintenanceWorkOrderUseCase createMaintenanceWorkOrderUseCase,
            RecordAssetConditionUseCase recordAssetConditionUseCase,
            RegisterMaintainableAssetUseCase registerMaintainableAssetUseCase
    ) {
        this.createMaintenanceWorkOrderUseCase = Objects.requireNonNull(createMaintenanceWorkOrderUseCase, "CreateMaintenanceWorkOrderUseCase must not be null.");
        this.recordAssetConditionUseCase = Objects.requireNonNull(recordAssetConditionUseCase, "RecordAssetConditionUseCase must not be null.");
        this.registerMaintainableAssetUseCase = Objects.requireNonNull(registerMaintainableAssetUseCase, "RegisterMaintainableAssetUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "assets",
                "mission", "Manage maintainable pipeline assets, their condition evidence, and maintenance work orders.",
                "objectives", List.of(
                "Register maintainable hydrocarbon transportation assets.",
                "Record asset condition evidence.",
                "Create maintenance work orders linked to operational reliability."
        ),
                "operations", List.of(
                "createMaintenanceWorkOrder",
                "recordAssetCondition",
                "registerMaintainableAsset"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/assets/maintenance-work-orders",
                "POST /api/v1/assets/asset-conditions",
                "POST /api/v1/assets/maintainable-assets"
        )
        );
    }

    @Override
    @PostMapping({"/create-maintenance-work-order", "/maintenance-work-orders"})
    public MaintenanceWorkOrderResponse createMaintenanceWorkOrder(@Valid @RequestBody CreateMaintenanceWorkOrderRequest request) {
        Objects.requireNonNull(request, "CreateMaintenanceWorkOrderRequest must not be null.");
        return AssetsRestMapper.toResponse(createMaintenanceWorkOrderUseCase.createMaintenanceWorkOrder(AssetsRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/record-asset-condition", "/asset-conditions"})
    public AssetConditionResponse recordAssetCondition(@Valid @RequestBody RecordAssetConditionRequest request) {
        Objects.requireNonNull(request, "RecordAssetConditionRequest must not be null.");
        return AssetsRestMapper.toResponse(recordAssetConditionUseCase.recordAssetCondition(AssetsRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/register-maintainable-asset", "/maintainable-assets"})
    public MaintainableAssetResponse registerMaintainableAsset(@Valid @RequestBody RegisterMaintainableAssetRequest request) {
        Objects.requireNonNull(request, "RegisterMaintainableAssetRequest must not be null.");
        return AssetsRestMapper.toResponse(registerMaintainableAssetUseCase.registerMaintainableAsset(AssetsRestMapper.toCommand(request)));
    }

}
