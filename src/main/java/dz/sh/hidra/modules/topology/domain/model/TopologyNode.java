/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Topology node entity representing a physical graph vertex.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.NodeTypeReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Represents a physical connection point in the topology graph.
 *
 * <p>Business role:
 * A topology node is a graph vertex such as a facility inlet, facility outlet, pipeline junction,
 * valve point, injection point, extraction point, purge point, vent point, drain point, metering
 * point, sampling point, receipt point, or delivery point.
 *
 * <p>Architecture role:
 * Node classification is a catalog reference so multilingual labels and configurable node taxonomies
 * can be resolved outside this entity.
 *
 * <p>Validation:
 * Identifier, code, name, node type reference, status, creation instant, and update instant are
 * mandatory.
 */
public final class TopologyNode implements Entity<TopologyNodeId> {

    private final TopologyNodeId id;
    private final TopologyCode code;
    private final TopologyName name;
    private final NodeTypeReference nodeType;
    private final FacilityId facilityId;
    private final PipelineAppurtenanceId pipelineAppurtenanceId;
    private final GeoCoordinate coordinate;
    private final BigDecimal elevationMeters;
    private final TopologyStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;

    private TopologyNode(
            TopologyNodeId id,
            TopologyCode code,
            TopologyName name,
            NodeTypeReference nodeType,
            FacilityId facilityId,
            PipelineAppurtenanceId pipelineAppurtenanceId,
            GeoCoordinate coordinate,
            BigDecimal elevationMeters,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Topology node id must not be null.");
        this.code = Objects.requireNonNull(code, "Topology node code must not be null.");
        this.name = Objects.requireNonNull(name, "Topology node name must not be null.");
        this.nodeType = Objects.requireNonNull(nodeType, "Topology node type reference must not be null.");
        this.facilityId = facilityId;
        this.pipelineAppurtenanceId = pipelineAppurtenanceId;
        this.coordinate = coordinate;
        this.elevationMeters = elevationMeters;
        this.status = Objects.requireNonNull(status, "Topology node status must not be null.");
        this.createdAt = requireInstant(createdAt, "Topology node createdAt");
        this.updatedAt = requireInstant(updatedAt, "Topology node updatedAt");

        ensureUpdatedAtIsValid(this.createdAt, this.updatedAt, "TopologyNode");
    }

    public static TopologyNode create(
            TopologyCode code,
            TopologyName name,
            NodeTypeReference nodeType,
            FacilityId facilityId,
            PipelineAppurtenanceId pipelineAppurtenanceId,
            GeoCoordinate coordinate,
            BigDecimal elevationMeters) {

        Instant now = Instant.now();
        return new TopologyNode(TopologyNodeId.newId(), code, name, nodeType, facilityId, pipelineAppurtenanceId, coordinate, elevationMeters, TopologyStatus.PLANNED, now, now);
    }

    @Deprecated(forRemoval = true)
    public static TopologyNode create(
            TopologyCode code,
            TopologyName name,
            NodeType nodeType,
            FacilityId facilityId,
            PipelineAppurtenanceId pipelineAppurtenanceId,
            GeoCoordinate coordinate,
            BigDecimal elevationMeters) {

        return create(code, name, NodeTypeReference.from(nodeType), facilityId, pipelineAppurtenanceId, coordinate, elevationMeters);
    }

    public static TopologyNode restore(
            TopologyNodeId id,
            TopologyCode code,
            TopologyName name,
            NodeTypeReference nodeType,
            FacilityId facilityId,
            PipelineAppurtenanceId pipelineAppurtenanceId,
            GeoCoordinate coordinate,
            BigDecimal elevationMeters,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        return new TopologyNode(id, code, name, nodeType, facilityId, pipelineAppurtenanceId, coordinate, elevationMeters, status, createdAt, updatedAt);
    }

    @Deprecated(forRemoval = true)
    public static TopologyNode restore(
            TopologyNodeId id,
            TopologyCode code,
            TopologyName name,
            NodeType nodeType,
            FacilityId facilityId,
            PipelineAppurtenanceId pipelineAppurtenanceId,
            GeoCoordinate coordinate,
            BigDecimal elevationMeters,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        return restore(id, code, name, NodeTypeReference.from(nodeType), facilityId, pipelineAppurtenanceId, coordinate, elevationMeters, status, createdAt, updatedAt);
    }

    @Override
    public TopologyNodeId id() {
        return id;
    }

    public TopologyCode code() {
        return code;
    }

    public TopologyName name() {
        return name;
    }

    public NodeTypeReference nodeType() {
        return nodeType;
    }

    public FacilityId facilityId() {
        return facilityId;
    }

    public PipelineAppurtenanceId pipelineAppurtenanceId() {
        return pipelineAppurtenanceId;
    }

    public GeoCoordinate coordinate() {
        return coordinate;
    }

    public BigDecimal elevationMeters() {
        return elevationMeters;
    }

    public TopologyStatus status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public TopologyNode activate() {
        return withStatus(TopologyStatus.ACTIVE);
    }

    public TopologyNode deactivate() {
        return withStatus(TopologyStatus.INACTIVE);
    }

    public TopologyNode markUnderMaintenance() {
        return withStatus(TopologyStatus.UNDER_MAINTENANCE);
    }

    public TopologyNode retire() {
        return withStatus(TopologyStatus.RETIRED);
    }

    public TopologyNode decommission() {
        return withStatus(TopologyStatus.DECOMMISSIONED);
    }

    private TopologyNode withStatus(TopologyStatus newStatus) {
        return new TopologyNode(id, code, name, nodeType, facilityId, pipelineAppurtenanceId, coordinate, elevationMeters, Objects.requireNonNull(newStatus, "Topology node status must not be null."), createdAt, Instant.now());
    }

    private static Instant requireInstant(Instant value, String fieldName) {
        return Objects.requireNonNull(value, fieldName + " must not be null.");
    }

    private static void ensureUpdatedAtIsValid(Instant createdAt, Instant updatedAt, String modelName) {
        if (updatedAt.isBefore(createdAt)) {
            throw new BusinessRuleViolationException(modelName + " updatedAt must not be before createdAt.");
        }
    }
}
