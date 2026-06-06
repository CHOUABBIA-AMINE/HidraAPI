/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InMemoryTopologyRepositoryPorts
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : In-memory outbound port fakes for topology application service tests.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.port.out.EquipmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineAppurtenanceRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSegmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.TopologyConnectionRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.TopologyNodeRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.ListFacilitiesQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineAppurtenancesQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSegmentsQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSystemsQuery;
import dz.sh.hidra.modules.topology.application.query.ListPipelinesQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyConnectionsQuery;
import dz.sh.hidra.modules.topology.application.query.ListTopologyNodesQuery;
import dz.sh.hidra.modules.topology.domain.model.Equipment;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineAppurtenance;
import dz.sh.hidra.modules.topology.domain.model.PipelineSegment;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.value.EquipmentId;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceId;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.PipelineSegmentId;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyConnectionId;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;

/**
 * In-memory outbound port fakes for application service unit tests.
 *
 * <p>Business role:
 * Provides deterministic test storage for topology application service tests without touching a
 * database.
 *
 * <p>Architecture role:
 * Test-only fake implementations of application outbound ports. These classes are not production
 * adapters and must not be used outside tests.
 *
 * <p>Validation:
 * The fakes intentionally keep filtering simple because application services delegate filtering to
 * outbound ports and only map the returned page.
 */
final class InMemoryTopologyRepositoryPorts {

    private InMemoryTopologyRepositoryPorts() {
    }

    static final class PipelineSystems implements PipelineSystemRepositoryPort {

        private final Map<PipelineSystemId, PipelineSystem> items = new LinkedHashMap<>();

        @Override
        public PipelineSystem save(PipelineSystem pipelineSystem) {
            items.put(pipelineSystem.id(), pipelineSystem);
            return pipelineSystem;
        }

        @Override
        public Optional<PipelineSystem> findById(PipelineSystemId id) {
            return Optional.ofNullable(items.get(id));
        }

        @Override
        public Optional<PipelineSystem> findByCode(TopologyCode code) {
            return items.values().stream().filter(item -> item.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(TopologyCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public PageResult<PipelineSystem> findAll(ListPipelineSystemsQuery query) {
            return page(new ArrayList<>(items.values()), query.pageRequest().page(), query.pageRequest().size());
        }
    }

    static final class Pipelines implements PipelineRepositoryPort {

        private final Map<PipelineId, Pipeline> items = new LinkedHashMap<>();

        @Override
        public Pipeline save(Pipeline pipeline) {
            items.put(pipeline.id(), pipeline);
            return pipeline;
        }

        @Override
        public Optional<Pipeline> findById(PipelineId id) {
            return Optional.ofNullable(items.get(id));
        }

        @Override
        public Optional<Pipeline> findByCode(TopologyCode code) {
            return items.values().stream().filter(item -> item.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(TopologyCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public PageResult<Pipeline> findAll(ListPipelinesQuery query) {
            return page(new ArrayList<>(items.values()), query.pageRequest().page(), query.pageRequest().size());
        }
    }

    static final class Facilities implements FacilityRepositoryPort {

        private final Map<FacilityId, Facility> items = new LinkedHashMap<>();

        @Override
        public Facility save(Facility facility) {
            items.put(facility.id(), facility);
            return facility;
        }

        @Override
        public Optional<Facility> findById(FacilityId id) {
            return Optional.ofNullable(items.get(id));
        }

        @Override
        public Optional<Facility> findByCode(TopologyCode code) {
            return items.values().stream().filter(item -> item.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(TopologyCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public PageResult<Facility> findAll(ListFacilitiesQuery query) {
            return page(new ArrayList<>(items.values()), query.pageRequest().page(), query.pageRequest().size());
        }
    }

    static final class TopologyNodes implements TopologyNodeRepositoryPort {

        private final Map<TopologyNodeId, TopologyNode> items = new LinkedHashMap<>();

        @Override
        public TopologyNode save(TopologyNode topologyNode) {
            items.put(topologyNode.id(), topologyNode);
            return topologyNode;
        }

        @Override
        public Optional<TopologyNode> findById(TopologyNodeId id) {
            return Optional.ofNullable(items.get(id));
        }

        @Override
        public Optional<TopologyNode> findByCode(TopologyCode code) {
            return items.values().stream().filter(item -> item.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(TopologyCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public PageResult<TopologyNode> findAll(ListTopologyNodesQuery query) {
            return page(new ArrayList<>(items.values()), query.pageRequest().page(), query.pageRequest().size());
        }
    }

    static final class PipelineSegments implements PipelineSegmentRepositoryPort {

        private final Map<PipelineSegmentId, PipelineSegment> items = new LinkedHashMap<>();

        @Override
        public PipelineSegment save(PipelineSegment pipelineSegment) {
            items.put(pipelineSegment.id(), pipelineSegment);
            return pipelineSegment;
        }

        @Override
        public Optional<PipelineSegment> findById(PipelineSegmentId id) {
            return Optional.ofNullable(items.get(id));
        }

        @Override
        public Optional<PipelineSegment> findByCode(TopologyCode code) {
            return items.values().stream().filter(item -> item.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(TopologyCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public PageResult<PipelineSegment> findAll(ListPipelineSegmentsQuery query) {
            return page(new ArrayList<>(items.values()), query.pageRequest().page(), query.pageRequest().size());
        }
    }

    static final class PipelineAppurtenances implements PipelineAppurtenanceRepositoryPort {

        private final Map<PipelineAppurtenanceId, PipelineAppurtenance> items = new LinkedHashMap<>();

        @Override
        public PipelineAppurtenance save(PipelineAppurtenance pipelineAppurtenance) {
            items.put(pipelineAppurtenance.id(), pipelineAppurtenance);
            return pipelineAppurtenance;
        }

        @Override
        public Optional<PipelineAppurtenance> findById(PipelineAppurtenanceId id) {
            return Optional.ofNullable(items.get(id));
        }

        @Override
        public Optional<PipelineAppurtenance> findByCode(TopologyCode code) {
            return items.values().stream().filter(item -> item.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(TopologyCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public PageResult<PipelineAppurtenance> findAll(ListPipelineAppurtenancesQuery query) {
            return page(new ArrayList<>(items.values()), query.pageRequest().page(), query.pageRequest().size());
        }
    }

    static final class TopologyConnections implements TopologyConnectionRepositoryPort {

        private final Map<TopologyConnectionId, TopologyConnection> items = new LinkedHashMap<>();

        @Override
        public TopologyConnection save(TopologyConnection topologyConnection) {
            items.put(topologyConnection.id(), topologyConnection);
            return topologyConnection;
        }

        @Override
        public Optional<TopologyConnection> findById(TopologyConnectionId id) {
            return Optional.ofNullable(items.get(id));
        }

        @Override
        public Optional<TopologyConnection> findByCode(TopologyCode code) {
            return items.values().stream().filter(item -> item.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(TopologyCode code) {
            return findByCode(code).isPresent();
        }

        @Override
        public PageResult<TopologyConnection> findAll(ListTopologyConnectionsQuery query) {
            return page(new ArrayList<>(items.values()), query.pageRequest().page(), query.pageRequest().size());
        }
    }

    static final class EquipmentItems implements EquipmentRepositoryPort {

        private final Map<EquipmentId, Equipment> items = new LinkedHashMap<>();

        @Override
        public Equipment save(Equipment equipment) {
            items.put(equipment.id(), equipment);
            return equipment;
        }

        @Override
        public Optional<Equipment> findById(EquipmentId id) {
            return Optional.ofNullable(items.get(id));
        }

        @Override
        public Optional<Equipment> findByCode(TopologyCode code) {
            return items.values().stream().filter(item -> item.code().equals(code)).findFirst();
        }

        @Override
        public boolean existsByCode(TopologyCode code) {
            return findByCode(code).isPresent();
        }
    }

    private static <T> PageResult<T> page(List<T> items, int page, int size) {
        int fromIndex = Math.min(page * size, items.size());
        int toIndex = Math.min(fromIndex + size, items.size());
        return PageResult.of(items.subList(fromIndex, toIndex), page, size, items.size());
    }
}
