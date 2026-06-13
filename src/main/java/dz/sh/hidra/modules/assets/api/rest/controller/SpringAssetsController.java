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
import dz.sh.hidra.modules.assets.api.rest.request.RegisterMaintainableAssetRequest;
import dz.sh.hidra.modules.assets.api.rest.response.MaintainableAssetResponse;
import dz.sh.hidra.modules.assets.api.rest.response.MaintenanceWorkOrderResponse;
import dz.sh.hidra.modules.assets.application.port.in.CreateMaintenanceWorkOrderUseCase;
import dz.sh.hidra.modules.assets.application.port.in.RegisterMaintainableAssetUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing assets REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/assets")
public class SpringAssetsController implements AssetsController {

    private final RegisterMaintainableAssetUseCase registerMaintainableAssetUseCase;
    private final CreateMaintenanceWorkOrderUseCase createMaintenanceWorkOrderUseCase;

    public SpringAssetsController(
            RegisterMaintainableAssetUseCase registerMaintainableAssetUseCase,
            CreateMaintenanceWorkOrderUseCase createMaintenanceWorkOrderUseCase
    ) {
        this.registerMaintainableAssetUseCase = Objects.requireNonNull(registerMaintainableAssetUseCase, "RegisterMaintainableAssetUseCase must not be null.");
        this.createMaintenanceWorkOrderUseCase = Objects.requireNonNull(createMaintenanceWorkOrderUseCase, "CreateMaintenanceWorkOrderUseCase must not be null.");
    }


    @Override
    @PostMapping("/register-maintainable-asset")
    public MaintainableAssetResponse registerMaintainableAsset(@Valid @RequestBody RegisterMaintainableAssetRequest request) {
        Objects.requireNonNull(request, "RegisterMaintainableAssetRequest must not be null.");
        return AssetsRestMapper.toResponse(registerMaintainableAssetUseCase.registerMaintainableAsset(AssetsRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/create-maintenance-work-order")
    public MaintenanceWorkOrderResponse createMaintenanceWorkOrder(@Valid @RequestBody CreateMaintenanceWorkOrderRequest request) {
        Objects.requireNonNull(request, "CreateMaintenanceWorkOrderRequest must not be null.");
        return AssetsRestMapper.toResponse(createMaintenanceWorkOrderUseCase.createMaintenanceWorkOrder(AssetsRestMapper.toCommand(request)));
    }

}
