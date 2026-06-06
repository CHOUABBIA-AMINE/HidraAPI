/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Topology connection entity representing explicit physical connectivity between nodes.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.topology.domain.value.ConnectionType;
import dz.sh.hidra.modules.topology.domain.value.ConnectionTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyConnectionId;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Represents explicit physical connectivity between two topology nodes.
 *
 * <p>Business role:
 * A topology connection is a graph edge that can represent a pipeline segment, facility internal
 * connection, valve connection, metering connection, junction connection, appurtenance connection,
 * or other physical connectivity.
 *
 * <p>Architecture role:
 * Connection classification is now a catalog reference. Linked asset type remains a technical asset
 * discriminator until a later roadmap decides otherwise.
 *
 * <p>Validation:
 * From-node and to-node must be different. Linked asset type and identifier are required.
 */
public final class TopologyConnection implements Entity<TopologyConnectionId> {

    private final TopologyConnectionId id;
    private final TopologyCode code;
    private final TopologyName name;
    private final TopologyNodeId fromNodeId;
    private final TopologyNodeId toNodeId;
    private final ConnectionTypeReference connectionType;
    private final TopologyAssetType linkedAssetType;
    private final String linkedAssetId;
    private final TopologyStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;

    private TopologyConnection(
            TopologyConnectionId id,
            TopologyCode code,
            TopologyName name,
            TopologyNodeId fromNodeId,
            TopologyNodeId toNodeId,
            ConnectionTypeReference connectionType,
            TopologyAssetType linkedAssetType,
            String linkedAssetId,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Topology connection id must not be null.");
        this.code = Objects.requireNonNull(code, "Topology connection code must not be null.");
        this.name = Objects.requireNonNull(name, "Topology connection name must not be null.");
        this.fromNodeId = Objects.requireNonNull(fromNodeId, "Topology connection from node id must not be null.");
        this.toNodeId = Objects.requireNonNull(toNodeId, "Topology connection to node id must not be null.");
        this.connectionType = Objects.requireNonNull(connectionType, "Topology connection type reference must not be null.");
        this.linkedAssetType = Objects.requireNonNull(linkedAssetType, "Topology connection linked asset type must not be null.");
        this.linkedAssetId = requireText(linkedAssetId, "Topology connection linked asset id");
        this.status = Objects.requireNonNull(status, "Topology connection status must not be null.");
        this.createdAt = requireInstant(createdAt, "Topology connection createdAt");
        this.updatedAt = requireInstant(updatedAt, "Topology connection updatedAt");

        ensureDifferentNodes();
        ensureUpdatedAtIsValid(this.createdAt, this.updatedAt, "TopologyConnection");
    }

    public static TopologyConnection create(
            TopologyCode code,
            TopologyName name,
            TopologyNodeId fromNodeId,
            TopologyNodeId toNodeId,
            ConnectionTypeReference connectionType,
            TopologyAssetType linkedAssetType,
            String linkedAssetId) {

        Instant now = Instant.now();
        return new TopologyConnection(TopologyConnectionId.newId(), code, name, fromNodeId, toNodeId, connectionType, linkedAssetType, linkedAssetId, TopologyStatus.PLANNED, now, now);
    }

    @Deprecated(forRemoval = true)
    public static TopologyConnection create(
            TopologyCode code,
            TopologyName name,
            TopologyNodeId fromNodeId,
            TopologyNodeId toNodeId,
            ConnectionType connectionType,
            TopologyAssetType linkedAssetType,
            String linkedAssetId) {

        return create(code, name, fromNodeId, toNodeId, ConnectionTypeReference.from(connectionType), linkedAssetType, linkedAssetId);
    }

    public static TopologyConnection restore(
            TopologyConnectionId id,
            TopologyCode code,
            TopologyName name,
            TopologyNodeId fromNodeId,
            TopologyNodeId toNodeId,
            ConnectionTypeReference connectionType,
            TopologyAssetType linkedAssetType,
            String linkedAssetId,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        return new TopologyConnection(id, code, name, fromNodeId, toNodeId, connectionType, linkedAssetType, linkedAssetId, status, createdAt, updatedAt);
    }

    @Deprecated(forRemoval = true)
    public static TopologyConnection restore(
            TopologyConnectionId id,
            TopologyCode code,
            TopologyName name,
            TopologyNodeId fromNodeId,
            TopologyNodeId toNodeId,
            ConnectionType connectionType,
            TopologyAssetType linkedAssetType,
            String linkedAssetId,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        return restore(id, code, name, fromNodeId, toNodeId, ConnectionTypeReference.from(connectionType), linkedAssetType, linkedAssetId, status, createdAt, updatedAt);
    }

    @Override
    public TopologyConnectionId id() { return id; }
    public TopologyCode code() { return code; }
    public TopologyName name() { return name; }
    public TopologyNodeId fromNodeId() { return fromNodeId; }
    public TopologyNodeId toNodeId() { return toNodeId; }
    public ConnectionTypeReference connectionType() { return connectionType; }
    public TopologyAssetType linkedAssetType() { return linkedAssetType; }
    public String linkedAssetId() { return linkedAssetId; }
    public TopologyStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }

    public TopologyConnection activate() { return withStatus(TopologyStatus.ACTIVE); }
    public TopologyConnection deactivate() { return withStatus(TopologyStatus.INACTIVE); }
    public TopologyConnection markUnderMaintenance() { return withStatus(TopologyStatus.UNDER_MAINTENANCE); }
    public TopologyConnection retire() { return withStatus(TopologyStatus.RETIRED); }
    public TopologyConnection decommission() { return withStatus(TopologyStatus.DECOMMISSIONED); }

    private TopologyConnection withStatus(TopologyStatus newStatus) {
        return new TopologyConnection(id, code, name, fromNodeId, toNodeId, connectionType, linkedAssetType, linkedAssetId, Objects.requireNonNull(newStatus, "Topology connection status must not be null."), createdAt, Instant.now());
    }

    private void ensureDifferentNodes() {
        if (fromNodeId.equals(toNodeId)) {
            throw new BusinessRuleViolationException("TopologyConnection fromNodeId and toNodeId must be different.");
        }
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) { throw new BusinessRuleViolationException(fieldName + " must not be null or blank."); }
        return value.trim();
    }

    private static Instant requireInstant(Instant value, String fieldName) { return Objects.requireNonNull(value, fieldName + " must not be null."); }

    private static void ensureUpdatedAtIsValid(Instant createdAt, Instant updatedAt, String modelName) {
        if (updatedAt.isBefore(createdAt)) { throw new BusinessRuleViolationException(modelName + " updatedAt must not be before createdAt."); }
    }
}
