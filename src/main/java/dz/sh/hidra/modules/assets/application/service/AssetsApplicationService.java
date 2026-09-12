/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.service
 *
 * @Description : Application service for assets and maintenance workflows.
 *
 */
package dz.sh.hidra.modules.assets.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dz.sh.hidra.modules.assets.application.command.CreateMaintenanceWorkOrderCommand;
import dz.sh.hidra.modules.assets.application.command.RecordAssetConditionCommand;
import dz.sh.hidra.modules.assets.application.command.RegisterMaintainableAssetCommand;
import dz.sh.hidra.modules.assets.application.dto.AssetConditionSummaryDto;
import dz.sh.hidra.modules.assets.application.dto.MaintainableAssetSummaryDto;
import dz.sh.hidra.modules.assets.application.dto.MaintenanceWorkOrderSummaryDto;
import dz.sh.hidra.modules.assets.application.mapper.AssetsApplicationMapper;
import dz.sh.hidra.modules.assets.application.port.in.CreateMaintenanceWorkOrderUseCase;
import dz.sh.hidra.modules.assets.application.port.in.RecordAssetConditionUseCase;
import dz.sh.hidra.modules.assets.application.port.in.RegisterMaintainableAssetUseCase;
import dz.sh.hidra.modules.assets.application.port.in.UpdateMaintainableAssetUseCase;
import dz.sh.hidra.modules.assets.application.port.out.AssetConditionRecordRepositoryPort;
import dz.sh.hidra.modules.assets.application.port.out.MaintainableAssetRepositoryPort;
import dz.sh.hidra.modules.assets.application.port.out.MaintenanceWorkOrderRepositoryPort;
import dz.sh.hidra.modules.assets.domain.exception.MaintainableAssetConflictException;
import dz.sh.hidra.modules.assets.domain.model.AssetConditionRecord;
import dz.sh.hidra.modules.assets.domain.model.MaintainableAsset;
import dz.sh.hidra.modules.assets.domain.model.MaintenanceWorkOrder;
import dz.sh.hidra.modules.assets.domain.value.AssetConditionStatus;
import dz.sh.hidra.modules.assets.domain.value.AssetLifecycleStatus;
import dz.sh.hidra.modules.assets.domain.value.AssetsId;
import dz.sh.hidra.modules.assets.domain.value.MaintenanceWorkOrderStatus;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Application service for assets and maintenance workflows.
 */
@Service
public final class AssetsApplicationService implements RegisterMaintainableAssetUseCase, CreateMaintenanceWorkOrderUseCase, RecordAssetConditionUseCase, UpdateMaintainableAssetUseCase {

    private final MaintainableAssetRepositoryPort maintainableAssetRepositoryPort;
    private final MaintenanceWorkOrderRepositoryPort workOrderRepositoryPort;
    private final AssetConditionRecordRepositoryPort conditionRecordRepositoryPort;

    public AssetsApplicationService(
            MaintainableAssetRepositoryPort maintainableAssetRepositoryPort,
            MaintenanceWorkOrderRepositoryPort workOrderRepositoryPort,
            AssetConditionRecordRepositoryPort conditionRecordRepositoryPort
    ) {
        this.maintainableAssetRepositoryPort = Objects.requireNonNull(maintainableAssetRepositoryPort, "Maintainable asset repository port must not be null.");
        this.workOrderRepositoryPort = Objects.requireNonNull(workOrderRepositoryPort, "Maintenance work order repository port must not be null.");
        this.conditionRecordRepositoryPort = Objects.requireNonNull(conditionRecordRepositoryPort, "Asset condition record repository port must not be null.");
    }

    @Override
    public MaintainableAssetSummaryDto registerMaintainableAsset(RegisterMaintainableAssetCommand command) {
        Objects.requireNonNull(command, "Register maintainable asset command must not be null.");
        Instant now = Instant.now();
        MaintainableAsset asset = new MaintainableAsset(
                AssetsId.newId().value(),
                command.assetNumber(),
                command.assetCode(),
                command.assetName(),
                command.assetTypeId(),
                command.topologyAssetTypeCode(),
                command.topologyAssetId(),
                command.topologyAssetCodeSnapshot(),
                command.topologyAssetNameSnapshot(),
                null,
                AssetLifecycleStatus.ACTIVE,
                command.criticalityId(),
                command.ownerOrganizationUnitId(),
                command.ownerOrganizationUnitNameSnapshot(),
                command.manufacturerPartyId(),
                command.manufacturerNameSnapshot(),
                command.modelId(),
                command.serialIdentityId(),
                now,
                command.installedAt(),
                command.commissionedAt(),
                null,
                command.createdByActorId(),
                now,
                now
        );
        return AssetsApplicationMapper.toSummary(maintainableAssetRepositoryPort.save(asset));
    }

    @Override
    @Transactional
    public MaintainableAssetSummaryDto updateMaintainableAsset(String assetId, UpdateMaintainableAssetUseCase.Command command) {
        if (assetId == null || assetId.isBlank()) {
            throw new IllegalArgumentException("assetId must not be null or blank.");
        }
        Objects.requireNonNull(command, "Maintainable asset update command must not be null.");

        String normalizedAssetId = assetId.trim();
        MaintainableAsset asset = maintainableAssetRepositoryPort.findByIdForUpdate(normalizedAssetId)
                .orElseThrow(() -> new NoSuchElementException("Unknown maintainable asset: " + normalizedAssetId));

        if (!command.expectedUpdatedAt().equals(asset.updatedAt())) {
            throw new MaintainableAssetConflictException(
                    "Maintainable asset changed after it was loaded. Refetch the asset before retrying."
            );
        }

        Instant updatedAt = Instant.now();
        if (!updatedAt.isAfter(asset.updatedAt())) {
            updatedAt = asset.updatedAt().plusNanos(1);
        }

        MaintainableAsset updated = new MaintainableAsset(
                asset.id(),
                asset.assetNumber(),
                asset.assetCode(),
                command.assetName(),
                asset.assetTypeId(),
                asset.topologyAssetTypeCode(),
                asset.topologyAssetId(),
                asset.topologyAssetCodeSnapshot(),
                asset.topologyAssetNameSnapshot(),
                asset.parentAssetId(),
                asset.status(),
                asset.criticalityId(),
                asset.ownerOrganizationUnitId(),
                asset.ownerOrganizationUnitNameSnapshot(),
                asset.manufacturerPartyId(),
                asset.manufacturerNameSnapshot(),
                asset.modelId(),
                asset.serialIdentityId(),
                asset.registeredAt(),
                asset.installedAt(),
                asset.commissionedAt(),
                asset.retiredAt(),
                asset.createdByActorId(),
                asset.createdAt(),
                updatedAt
        );

        return AssetsApplicationMapper.toSummary(maintainableAssetRepositoryPort.save(updated));
    }

    @Override
    public MaintenanceWorkOrderSummaryDto createMaintenanceWorkOrder(CreateMaintenanceWorkOrderCommand command) {
        Objects.requireNonNull(command, "Create maintenance work order command must not be null.");
        Instant now = Instant.now();
        MaintenanceWorkOrder workOrder = new MaintenanceWorkOrder(
                AssetsId.newId().value(),
                command.workOrderNumber(),
                command.maintainableAssetId(),
                command.maintenancePlanId(),
                command.sourceRecommendationId(),
                command.workOrderTypeId(),
                command.priorityId(),
                MaintenanceWorkOrderStatus.DRAFT,
                command.title(),
                command.description(),
                command.assignedOrganizationUnitId(),
                command.assignedActorId(),
                command.plannedStartAt(),
                command.plannedEndAt(),
                null,
                null,
                command.workflowInstanceId(),
                command.createdByActorId(),
                now,
                now
        );
        return AssetsApplicationMapper.toSummary(workOrderRepositoryPort.save(workOrder));
    }

    @Override
    public AssetConditionSummaryDto recordAssetCondition(RecordAssetConditionCommand command) {
        Objects.requireNonNull(command, "Record asset condition command must not be null.");
        Instant now = Instant.now();
        AssetConditionRecord conditionRecord = new AssetConditionRecord(
                AssetsId.newId().value(),
                command.maintainableAssetId(),
                command.conditionStatus() == null ? AssetConditionStatus.UNKNOWN : command.conditionStatus(),
                command.conditionTypeId(),
                command.sourceModule(),
                command.sourceReferenceId(),
                command.summary(),
                command.conditionScore(),
                command.observedAt() == null ? now : command.observedAt(),
                command.observedByActorId(),
                now
        );
        return AssetsApplicationMapper.toSummary(conditionRecordRepositoryPort.save(conditionRecord));
    }
}
