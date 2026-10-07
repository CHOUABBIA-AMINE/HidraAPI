/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningTopologyScopeQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Enforces Planning-owned semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.contract.planning.PlanningTopologyScopeContract;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class PlanningTopologyScopeQueryService
        implements PlanningTopologyScopeContract {

    private final PipelineSystemRepositoryPort systemRepository;
    private final PipelineRepositoryPort pipelineRepository;
    private final FacilityRepositoryPort facilityRepository;

    public PlanningTopologyScopeQueryService(
            PipelineSystemRepositoryPort systemRepository,
            PipelineRepositoryPort pipelineRepository,
            FacilityRepositoryPort facilityRepository
    ) {
        this.systemRepository = Objects.requireNonNull(systemRepository);
        this.pipelineRepository = Objects.requireNonNull(pipelineRepository);
        this.facilityRepository = Objects.requireNonNull(facilityRepository);
    }

    @Override
    public Optional<Scope> resolve(String scopeType, String scopeId) {
        if (scopeType == null || scopeType.isBlank() || scopeId == null || scopeId.isBlank()) {
            return Optional.empty();
        }
        String type = scopeType.trim().toUpperCase(Locale.ROOT);
        String id = scopeId.trim();

        return switch (type) {
            case "PIPELINE_SYSTEM" -> systemRepository.findById(id)
                    .map(v -> new Scope(v.id(), v.code(), label(v.nameFr(), v.nameEn(), v.nameAr())));
            case "PIPELINE" -> pipelineRepository.findById(id)
                    .map(v -> new Scope(v.id(), v.code(), label(v.nameFr(), v.nameEn(), v.nameAr())));
            case "FACILITY" -> facilityRepository.findById(id)
                    .map(v -> new Scope(v.id(), v.code(), label(v.nameFr(), v.nameEn(), v.nameAr())));
            default -> Optional.empty();
        };
    }

    private static String label(String nameFr, String nameEn, String nameAr) {
        if (nameFr != null && !nameFr.isBlank()) return nameFr;
        if (nameEn != null && !nameEn.isBlank()) return nameEn;
        return nameAr;
    }
}
