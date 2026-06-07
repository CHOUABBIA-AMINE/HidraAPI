/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTopologyAssetLookupAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.topology
 *
 * @Description : Adapter implementing telemetry topology asset lookup through topology application ports.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.topology;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import dz.sh.hidra.modules.telemetry.application.dto.TopologyAssetReferenceDto;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTopologyAssetLookupPort;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.topology.application.dto.FacilityDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineAppurtenanceDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineDto;
import dz.sh.hidra.modules.topology.application.dto.PipelineSystemDto;
import dz.sh.hidra.modules.topology.application.dto.TopologyNodeDto;
import dz.sh.hidra.modules.topology.application.port.in.GetFacilityUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineAppurtenanceUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineSystemUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetTopologyNodeUseCase;
import dz.sh.hidra.modules.topology.application.query.GetFacilityByIdQuery;
import dz.sh.hidra.modules.topology.application.query.GetPipelineAppurtenanceByIdQuery;
import dz.sh.hidra.modules.topology.application.query.GetPipelineByIdQuery;
import dz.sh.hidra.modules.topology.application.query.GetPipelineSystemByIdQuery;
import dz.sh.hidra.modules.topology.application.query.GetTopologyNodeByIdQuery;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceId;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemId;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;

/**
 * Adapter implementing telemetry topology asset lookup through topology application ports.
 *
 * <p>Business role:
 * Allows telemetry point bindings to verify that a neutral topology asset reference points to an
 * existing topology asset and to return a stable snapshot containing asset type, id, code, and name.
 *
 * <p>Architecture role:
 * This is a telemetry infrastructure adapter. It depends on topology application read ports only.
 * It does not import topology persistence entities, topology repositories, topology REST controllers,
 * topology mappers, flow, risk, analytics, workflow, reporting, or notification code.
 *
 * <p>Supported asset type codes:
 * <code>PIPELINE_SYSTEM</code>, <code>PIPELINE</code>, <code>FACILITY</code>,
 * <code>TOPOLOGY_NODE</code>, and <code>PIPELINE_APPURTENANCE</code>.
 */
@Component
public class TelemetryTopologyAssetLookupAdapter implements TelemetryTopologyAssetLookupPort {

    private static final String PIPELINE_SYSTEM = "PIPELINE_SYSTEM";
    private static final String PIPELINE = "PIPELINE";
    private static final String FACILITY = "FACILITY";
    private static final String TOPOLOGY_NODE = "TOPOLOGY_NODE";
    private static final String PIPELINE_APPURTENANCE = "PIPELINE_APPURTENANCE";

    private final GetPipelineSystemUseCase getPipelineSystemUseCase;
    private final GetPipelineUseCase getPipelineUseCase;
    private final GetFacilityUseCase getFacilityUseCase;
    private final GetTopologyNodeUseCase getTopologyNodeUseCase;
    private final GetPipelineAppurtenanceUseCase getPipelineAppurtenanceUseCase;

    public TelemetryTopologyAssetLookupAdapter(
            GetPipelineSystemUseCase getPipelineSystemUseCase,
            GetPipelineUseCase getPipelineUseCase,
            GetFacilityUseCase getFacilityUseCase,
            GetTopologyNodeUseCase getTopologyNodeUseCase,
            GetPipelineAppurtenanceUseCase getPipelineAppurtenanceUseCase) {

        this.getPipelineSystemUseCase = Objects.requireNonNull(getPipelineSystemUseCase, "GetPipelineSystemUseCase must not be null.");
        this.getPipelineUseCase = Objects.requireNonNull(getPipelineUseCase, "GetPipelineUseCase must not be null.");
        this.getFacilityUseCase = Objects.requireNonNull(getFacilityUseCase, "GetFacilityUseCase must not be null.");
        this.getTopologyNodeUseCase = Objects.requireNonNull(getTopologyNodeUseCase, "GetTopologyNodeUseCase must not be null.");
        this.getPipelineAppurtenanceUseCase = Objects.requireNonNull(getPipelineAppurtenanceUseCase, "GetPipelineAppurtenanceUseCase must not be null.");
    }

    @Override
    public Optional<TopologyAssetReferenceDto> findTopologyAssetReference(
            TelemetryCode assetTypeCode,
            String assetId) {

        Objects.requireNonNull(assetTypeCode, "Telemetry topology assetTypeCode must not be null.");

        if (assetId == null || assetId.isBlank()) {
            return Optional.empty();
        }

        String normalizedAssetTypeCode = normalizeAssetTypeCode(assetTypeCode);
        String normalizedAssetId = assetId.trim();

        try {
            return switch (normalizedAssetTypeCode) {
                case PIPELINE_SYSTEM -> findPipelineSystem(normalizedAssetId);
                case PIPELINE -> findPipeline(normalizedAssetId);
                case FACILITY -> findFacility(normalizedAssetId);
                case TOPOLOGY_NODE -> findTopologyNode(normalizedAssetId);
                case PIPELINE_APPURTENANCE -> findPipelineAppurtenance(normalizedAssetId);
                default -> Optional.empty();
            };
        } catch (RuntimeException ignored) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsTopologyAsset(TelemetryCode assetTypeCode, String assetId) {
        return findTopologyAssetReference(assetTypeCode, assetId).isPresent();
    }

    private Optional<TopologyAssetReferenceDto> findPipelineSystem(String assetId) {
        PipelineSystemDto dto = getPipelineSystemUseCase.getPipelineSystem(
                new GetPipelineSystemByIdQuery(PipelineSystemId.of(assetId)));

        return Optional.of(new TopologyAssetReferenceDto(
                PIPELINE_SYSTEM,
                dto.pipelineSystemId(),
                dto.code(),
                dto.name()));
    }

    private Optional<TopologyAssetReferenceDto> findPipeline(String assetId) {
        PipelineDto dto = getPipelineUseCase.getPipeline(
                new GetPipelineByIdQuery(PipelineId.of(assetId)));

        return Optional.of(new TopologyAssetReferenceDto(
                PIPELINE,
                dto.pipelineId(),
                dto.code(),
                firstNonBlank(dto.nameFr(), dto.nameEn(), dto.nameAr())));
    }

    private Optional<TopologyAssetReferenceDto> findFacility(String assetId) {
        FacilityDto dto = getFacilityUseCase.getFacility(
                new GetFacilityByIdQuery(FacilityId.of(assetId)));

        return Optional.of(new TopologyAssetReferenceDto(
                FACILITY,
                dto.facilityId(),
                dto.code(),
                dto.name()));
    }

    private Optional<TopologyAssetReferenceDto> findTopologyNode(String assetId) {
        TopologyNodeDto dto = getTopologyNodeUseCase.getTopologyNode(
                new GetTopologyNodeByIdQuery(TopologyNodeId.of(assetId)));

        return Optional.of(new TopologyAssetReferenceDto(
                TOPOLOGY_NODE,
                dto.topologyNodeId(),
                dto.code(),
                dto.name()));
    }

    private Optional<TopologyAssetReferenceDto> findPipelineAppurtenance(String assetId) {
        PipelineAppurtenanceDto dto = getPipelineAppurtenanceUseCase.getPipelineAppurtenance(
                new GetPipelineAppurtenanceByIdQuery(PipelineAppurtenanceId.of(assetId)));

        return Optional.of(new TopologyAssetReferenceDto(
                PIPELINE_APPURTENANCE,
                dto.pipelineAppurtenanceId(),
                dto.code(),
                dto.name()));
    }

    private static String normalizeAssetTypeCode(TelemetryCode assetTypeCode) {
        return assetTypeCode.value()
                .trim()
                .replace('-', '_')
                .replace(' ', '_')
                .toUpperCase(Locale.ROOT);
    }

    private static String firstNonBlank(String first, String second, String third) {
        if (first != null && !first.isBlank()) {
            return first;
        }
        if (second != null && !second.isBlank()) {
            return second;
        }
        if (third != null && !third.isBlank()) {
            return third;
        }
        return null;
    }
}
