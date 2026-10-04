/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsTopologyScopeContractAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.reference
 *
 * @Description : Topology-owned persistence adapter for Analytics scope existence lookup.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.reference;

import dz.sh.hidra.modules.topology.application.contract.analytics.AnalyticsTopologyScopeContract;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.EquipmentJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.FacilityJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSegmentJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSystemJpaRepository;
import java.util.Locale;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 * Topology-owned Analytics scope resolver.
 */
@Component
public class JpaAnalyticsTopologyScopeContractAdapter implements AnalyticsTopologyScopeContract {

    private final PipelineSystemJpaRepository pipelineSystemRepository;
    private final PipelineJpaRepository pipelineRepository;
    private final PipelineSegmentJpaRepository pipelineSegmentRepository;
    private final FacilityJpaRepository facilityRepository;
    private final EquipmentJpaRepository equipmentRepository;

    public JpaAnalyticsTopologyScopeContractAdapter(
            PipelineSystemJpaRepository pipelineSystemRepository,
            PipelineJpaRepository pipelineRepository,
            PipelineSegmentJpaRepository pipelineSegmentRepository,
            FacilityJpaRepository facilityRepository,
            EquipmentJpaRepository equipmentRepository
    ) {
        this.pipelineSystemRepository = Objects.requireNonNull(pipelineSystemRepository);
        this.pipelineRepository = Objects.requireNonNull(pipelineRepository);
        this.pipelineSegmentRepository = Objects.requireNonNull(pipelineSegmentRepository);
        this.facilityRepository = Objects.requireNonNull(facilityRepository);
        this.equipmentRepository = Objects.requireNonNull(equipmentRepository);
    }

    @Override
    public Resolution resolve(String scopeType, String scopeId) {
        if (scopeType == null || scopeType.isBlank()) {
            return Resolution.unsupported();
        }
        String type = scopeType.trim().toUpperCase(Locale.ROOT);
        if (!supported(type)) {
            return Resolution.unsupported();
        }
        if (scopeId == null || scopeId.isBlank()) {
            return Resolution.supported(false);
        }
        String id = scopeId.trim();
        return Resolution.supported(switch (type) {
            case "PIPELINE_SYSTEM" -> pipelineSystemRepository.existsById(id);
            case "PIPELINE" -> pipelineRepository.existsById(id);
            case "PIPELINE_SEGMENT" -> pipelineSegmentRepository.existsById(id);
            case "FACILITY" -> facilityRepository.existsById(id);
            case "EQUIPMENT" -> equipmentRepository.existsById(id);
            default -> false;
        });
    }

    private static boolean supported(String type) {
        return "PIPELINE_SYSTEM".equals(type)
                || "PIPELINE".equals(type)
                || "PIPELINE_SEGMENT".equals(type)
                || "FACILITY".equals(type)
                || "EQUIPMENT".equals(type);
    }
}
