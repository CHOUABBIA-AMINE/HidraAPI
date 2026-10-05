/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskTopologyScopeReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Resolves the currently owner-backed Topology scope types for Risk.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.contract.risk.RiskTopologyScopeReferenceContract;
import dz.sh.hidra.modules.topology.application.port.out.EquipmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class RiskTopologyScopeReferenceQueryService
        implements RiskTopologyScopeReferenceContract {

    private final PipelineSystemRepositoryPort systemRepository;
    private final PipelineRepositoryPort pipelineRepository;
    private final FacilityRepositoryPort facilityRepository;
    private final EquipmentRepositoryPort equipmentRepository;

    public RiskTopologyScopeReferenceQueryService(
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

    @Override
    public Optional<ScopeView> resolve(String scopeType, String scopeId) {
        if (scopeType == null || scopeType.isBlank() || scopeId == null || scopeId.isBlank()) {
            return Optional.empty();
        }
        String type = scopeType.trim().toUpperCase(Locale.ROOT);
        String id = scopeId.trim();

        return switch (type) {
            case "PIPELINE_SYSTEM" -> systemRepository.findById(id)
                    .map(v -> new ScopeView(v.id(), v.code(), label(v.nameFr(), v.nameEn(), v.nameAr())));
            case "PIPELINE" -> pipelineRepository.findById(id)
                    .map(v -> new ScopeView(v.id(), v.code(), label(v.nameFr(), v.nameEn(), v.nameAr())));
            case "FACILITY" -> facilityRepository.findById(id)
                    .map(v -> new ScopeView(v.id(), v.code(), label(v.nameFr(), v.nameEn(), v.nameAr())));
            case "EQUIPMENT" -> equipmentRepository.findById(id)
                    .map(v -> new ScopeView(v.id(), v.code(), v.name()));
            default -> Optional.empty();
        };
    }

    private static String label(String nameFr, String nameEn, String nameAr) {
        if (nameFr != null && !nameFr.isBlank()) return nameFr;
        if (nameEn != null && !nameEn.isBlank()) return nameEn;
        return nameAr;
    }
}
