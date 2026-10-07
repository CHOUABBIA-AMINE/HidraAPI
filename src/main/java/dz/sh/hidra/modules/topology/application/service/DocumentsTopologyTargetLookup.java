/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsTopologyTargetLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.documents.application.contract.target.DocumentsOwnedTargetLookup;
import dz.sh.hidra.modules.topology.application.port.out.EquipmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class DocumentsTopologyTargetLookup
        implements DocumentsOwnedTargetLookup {

    private final PipelineSystemRepositoryPort systemRepository;
    private final PipelineRepositoryPort pipelineRepository;
    private final FacilityRepositoryPort facilityRepository;
    private final EquipmentRepositoryPort equipmentRepository;

    public DocumentsTopologyTargetLookup(
            PipelineSystemRepositoryPort systemRepository,
            PipelineRepositoryPort pipelineRepository,
            FacilityRepositoryPort facilityRepository,
            EquipmentRepositoryPort equipmentRepository
    ) {
        this.systemRepository = Objects.requireNonNull(systemRepository);
        this.pipelineRepository = Objects.requireNonNull(pipelineRepository);
        this.facilityRepository = Objects.requireNonNull(facilityRepository);
        this.equipmentRepository = Objects.requireNonNull(equipmentRepository);
    }

    public String module(){return "topology";}
    public java.util.Set<String> targetTypeCodes(){return java.util.Set.of("PIPELINE_SYSTEM","PIPELINE","FACILITY","EQUIPMENT");}

    @Override
    public Optional<Target> resolve(String scopeType, String scopeId) {
        if (scopeType == null || scopeType.isBlank() || scopeId == null || scopeId.isBlank()) {
            return Optional.empty();
        }
        String type = scopeType.trim();
        String id = scopeId.trim();

        return switch (type) {
            case "PIPELINE_SYSTEM" -> systemRepository.findById(id)
                    .map(v -> new Target(v.id(), v.code(), label(v.nameFr(), v.nameEn(), v.nameAr())));
            case "PIPELINE" -> pipelineRepository.findById(id)
                    .map(v -> new Target(v.id(), v.code(), label(v.nameFr(), v.nameEn(), v.nameAr())));
            case "FACILITY" -> facilityRepository.findById(id)
                    .map(v -> new Target(v.id(), v.code(), label(v.nameFr(), v.nameEn(), v.nameAr())));
            case "EQUIPMENT" -> equipmentRepository.findById(id)
                    .map(v -> new Target(v.id(), v.code(), v.name()));
            default -> Optional.empty();
        };
    }

    private static String label(String nameFr, String nameEn, String nameAr) {
        if (nameFr != null && !nameFr.isBlank()) return nameFr;
        if (nameEn != null && !nameEn.isBlank()) return nameEn;
        return nameAr;
    }
}
