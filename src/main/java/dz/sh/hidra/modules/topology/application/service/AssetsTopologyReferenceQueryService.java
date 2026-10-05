/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsTopologyReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Resolves typed Topology references for Assets without leaking owner internals.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.contract.assets.AssetsTopologyReferenceContract;
import dz.sh.hidra.modules.topology.application.port.out.EquipmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class AssetsTopologyReferenceQueryService
        implements AssetsTopologyReferenceContract {

    private final PipelineSystemRepositoryPort pipelineSystemRepository;
    private final PipelineRepositoryPort pipelineRepository;
    private final FacilityRepositoryPort facilityRepository;
    private final EquipmentRepositoryPort equipmentRepository;

    public AssetsTopologyReferenceQueryService(
            PipelineSystemRepositoryPort pipelineSystemRepository,
            PipelineRepositoryPort pipelineRepository,
            FacilityRepositoryPort facilityRepository,
            EquipmentRepositoryPort equipmentRepository
    ) {
        this.pipelineSystemRepository = Objects.requireNonNull(pipelineSystemRepository);
        this.pipelineRepository = Objects.requireNonNull(pipelineRepository);
        this.facilityRepository = Objects.requireNonNull(facilityRepository);
        this.equipmentRepository = Objects.requireNonNull(equipmentRepository);
    }

    @Override
    public Optional<ReferenceView> resolve(String topologyAssetTypeCode, String topologyAssetId) {
        if (topologyAssetTypeCode == null || topologyAssetTypeCode.isBlank()
                || topologyAssetId == null || topologyAssetId.isBlank()) {
            return Optional.empty();
        }

        String type = topologyAssetTypeCode.trim().toUpperCase(Locale.ROOT);
        String id = topologyAssetId.trim();

        return switch (type) {
            case "PIPELINE_SYSTEM" -> pipelineSystemRepository.findById(id)
                    .map(model -> new ReferenceView(
                            model.id(),
                            model.code(),
                            currentName(model.nameEn(), model.nameFr(), model.nameAr())
                    ));
            case "PIPELINE" -> pipelineRepository.findById(id)
                    .map(model -> new ReferenceView(
                            model.id(),
                            model.code(),
                            currentName(model.nameEn(), model.nameFr(), model.nameAr())
                    ));
            case "FACILITY" -> facilityRepository.findById(id)
                    .map(model -> new ReferenceView(
                            model.id(),
                            model.code(),
                            currentName(model.nameEn(), model.nameFr(), model.nameAr())
                    ));
            case "EQUIPMENT" -> equipmentRepository.findById(id)
                    .map(model -> new ReferenceView(model.id(), model.code(), model.name()));
            default -> Optional.empty();
        };
    }

    private static String currentName(String nameEn, String nameFr, String nameAr) {
        if (nameEn != null && !nameEn.isBlank()) {
            return nameEn.trim();
        }
        if (nameFr != null && !nameFr.isBlank()) {
            return nameFr.trim();
        }
        return nameAr == null || nameAr.isBlank() ? null : nameAr.trim();
    }
}
