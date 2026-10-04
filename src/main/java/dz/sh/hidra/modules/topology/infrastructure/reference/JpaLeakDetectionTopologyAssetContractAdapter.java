/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakDetectionTopologyAssetContractAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.reference
 *
 * @Description : Topology-owned persistence adapter for Leak Detection typed asset resolution.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.reference;

import dz.sh.hidra.modules.topology.application.contract.leakdetection.LeakDetectionTopologyAssetContract;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.EquipmentJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.FacilityJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSegmentJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologyNodeJpaRepository;
import java.util.Locale;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Topology-owned Leak Detection asset resolver.
 */
@Component
public final class JpaLeakDetectionTopologyAssetContractAdapter
        implements LeakDetectionTopologyAssetContract {

    private final PipelineJpaRepository pipelineRepository;
    private final PipelineSegmentJpaRepository pipelineSegmentRepository;
    private final FacilityJpaRepository facilityRepository;
    private final TopologyNodeJpaRepository topologyNodeRepository;
    private final EquipmentJpaRepository equipmentRepository;

    public JpaLeakDetectionTopologyAssetContractAdapter(
            PipelineJpaRepository pipelineRepository,
            PipelineSegmentJpaRepository pipelineSegmentRepository,
            FacilityJpaRepository facilityRepository,
            TopologyNodeJpaRepository topologyNodeRepository,
            EquipmentJpaRepository equipmentRepository
    ) {
        this.pipelineRepository = Objects.requireNonNull(
                pipelineRepository,
                "PipelineJpaRepository must not be null."
        );
        this.pipelineSegmentRepository = Objects.requireNonNull(
                pipelineSegmentRepository,
                "PipelineSegmentJpaRepository must not be null."
        );
        this.facilityRepository = Objects.requireNonNull(
                facilityRepository,
                "FacilityJpaRepository must not be null."
        );
        this.topologyNodeRepository = Objects.requireNonNull(
                topologyNodeRepository,
                "TopologyNodeJpaRepository must not be null."
        );
        this.equipmentRepository = Objects.requireNonNull(
                equipmentRepository,
                "EquipmentJpaRepository must not be null."
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AssetResolution resolve(String assetType, String assetId) {
        if (assetType == null || assetType.isBlank()) {
            return AssetResolution.unsupported();
        }
        if (assetId == null || assetId.isBlank()) {
            return AssetResolution.missing();
        }

        String normalizedType = assetType.trim().toUpperCase(Locale.ROOT);
        String normalizedId = assetId.trim();

        return switch (normalizedType) {
            case "PIPELINE" -> pipelineRepository.findById(normalizedId)
                    .map(entity -> AssetResolution.resolved(
                            entity.id(),
                            entity.code(),
                            currentName(entity.nameEn(), entity.nameFr(), entity.nameAr())
                    ))
                    .orElseGet(AssetResolution::missing);
            case "PIPELINE_SEGMENT" -> pipelineSegmentRepository.findById(normalizedId)
                    .map(entity -> AssetResolution.resolved(entity.id(), entity.code(), null))
                    .orElseGet(AssetResolution::missing);
            case "FACILITY" -> facilityRepository.findById(normalizedId)
                    .map(entity -> AssetResolution.resolved(
                            entity.id(),
                            entity.code(),
                            currentName(entity.nameEn(), entity.nameFr(), entity.nameAr())
                    ))
                    .orElseGet(AssetResolution::missing);
            case "TOPOLOGY_NODE" -> topologyNodeRepository.findById(normalizedId)
                    .map(entity -> AssetResolution.resolved(entity.id(), entity.code(), entity.name()))
                    .orElseGet(AssetResolution::missing);
            case "EQUIPMENT" -> equipmentRepository.findById(normalizedId)
                    .map(entity -> AssetResolution.resolved(entity.id(), entity.code(), entity.name()))
                    .orElseGet(AssetResolution::missing);
            default -> AssetResolution.unsupported();
        };
    }

    private static String currentName(String nameEn, String nameFr, String nameAr) {
        if (nameEn != null && !nameEn.isBlank()) {
            return nameEn.trim();
        }
        if (nameFr != null && !nameFr.isBlank()) {
            return nameFr.trim();
        }
        if (nameAr != null && !nameAr.isBlank()) {
            return nameAr.trim();
        }
        return null;
    }
}
