/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyOperationalScopeTargetQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Resolves current topology-owned targets for operational-scope consumers.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.ResolveTopologyOperationalScopeTargetUseCase;
import dz.sh.hidra.modules.topology.application.port.out.EquipmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import dz.sh.hidra.modules.topology.domain.value.EquipmentStatus;
import dz.sh.hidra.modules.topology.domain.value.FacilityStatus;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Topology-owned implementation of the public scope-target read contract.
 *
 * <p>Only ACTIVE topology objects are assignable for new responsibilities.
 * Suspended, retired, archived, planned, draft, decommissioned and out-of-service
 * objects remain resolvable for history/reconciliation but are not assignable.</p>
 */
@Service
public final class TopologyOperationalScopeTargetQueryService
        implements ResolveTopologyOperationalScopeTargetUseCase {

    private final PipelineSystemRepositoryPort pipelineSystemRepositoryPort;
    private final PipelineRepositoryPort pipelineRepositoryPort;
    private final FacilityRepositoryPort facilityRepositoryPort;
    private final EquipmentRepositoryPort equipmentRepositoryPort;

    public TopologyOperationalScopeTargetQueryService(
            PipelineSystemRepositoryPort pipelineSystemRepositoryPort,
            PipelineRepositoryPort pipelineRepositoryPort,
            FacilityRepositoryPort facilityRepositoryPort,
            EquipmentRepositoryPort equipmentRepositoryPort
    ) {
        this.pipelineSystemRepositoryPort = Objects.requireNonNull(
                pipelineSystemRepositoryPort,
                "Pipeline system repository port must not be null."
        );
        this.pipelineRepositoryPort = Objects.requireNonNull(
                pipelineRepositoryPort,
                "Pipeline repository port must not be null."
        );
        this.facilityRepositoryPort = Objects.requireNonNull(
                facilityRepositoryPort,
                "Facility repository port must not be null."
        );
        this.equipmentRepositoryPort = Objects.requireNonNull(
                equipmentRepositoryPort,
                "Equipment repository port must not be null."
        );
    }

    @Override
    public Optional<TargetView> resolvePipelineSystem(String targetId) {
        String id = requireTargetId(targetId);
        return pipelineSystemRepositoryPort.findById(id)
                .map(model -> new TargetView(
                        model.id(),
                        model.code(),
                        currentName(model.nameEn(), model.nameFr(), model.nameAr()),
                        model.status() == TopologyStatus.ACTIVE
                ));
    }

    @Override
    public Optional<TargetView> resolvePipeline(String targetId) {
        String id = requireTargetId(targetId);
        return pipelineRepositoryPort.findById(id)
                .map(model -> new TargetView(
                        model.id(),
                        model.code(),
                        currentName(model.nameEn(), model.nameFr(), model.nameAr()),
                        model.status() == TopologyStatus.ACTIVE
                ));
    }

    @Override
    public Optional<TargetView> resolveFacility(String targetId) {
        String id = requireTargetId(targetId);
        return facilityRepositoryPort.findById(id)
                .map(model -> new TargetView(
                        model.id(),
                        model.code(),
                        currentName(model.nameEn(), model.nameFr(), model.nameAr()),
                        model.status() == FacilityStatus.ACTIVE
                ));
    }

    @Override
    public Optional<TargetView> resolveEquipment(String targetId) {
        String id = requireTargetId(targetId);
        return equipmentRepositoryPort.findById(id)
                .map(model -> new TargetView(
                        model.id(),
                        model.code(),
                        model.name(),
                        model.status() == EquipmentStatus.ACTIVE
                ));
    }

    private static String requireTargetId(String targetId) {
        if (targetId == null || targetId.isBlank()) {
            throw new IllegalArgumentException("Topology target ID must not be blank.");
        }
        return targetId.trim();
    }

    private static String currentName(
            String nameEn,
            String nameFr,
            String nameAr
    ) {
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
