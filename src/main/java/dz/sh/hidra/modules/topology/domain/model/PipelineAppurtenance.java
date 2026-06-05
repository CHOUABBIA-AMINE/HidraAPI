/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenance
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Pipeline appurtenance entity representing a physical point asset along a pipeline.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceId;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.PipelineKilometerPoint;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import dz.sh.hidra.modules.topology.domain.value.ValveType;

/**
 * Represents a physical point asset installed along a pipeline.
 *
 * <p>Business role:
 * A pipeline appurtenance models topology-relevant point assets such as valves, injection points,
 * extraction points, purge points, vent points, drain points, metering points, scraper launchers,
 * scraper receivers, hot tap points, bypass points, and connection points.
 *
 * <p>Architecture role:
 * This is a pure topology domain entity. It owns physical point-asset identity and location only;
 * operations, permits, telemetry values, hydraulic calculations, maintenance, risk, and workflow
 * behavior belong to future modules.
 *
 * <p>Validation:
 * Pipeline, node, code, name, appurtenance type, KP, status, creation instant, and update instant
 * are mandatory. Valve type is mandatory only for valve appurtenances and forbidden otherwise.
 */
public final class PipelineAppurtenance implements Entity<PipelineAppurtenanceId> {

    private final PipelineAppurtenanceId id;
    private final PipelineId pipelineId;
    private final TopologyNodeId nodeId;
    private final TopologyCode code;
    private final TopologyName name;
    private final PipelineAppurtenanceType appurtenanceType;
    private final ValveType valveType;
    private final PipelineKilometerPoint pipelineKilometerPoint;
    private final TopologyStatus status;
    private final GeoCoordinate coordinate;
    private final String description;
    private final Instant createdAt;
    private final Instant updatedAt;

    private PipelineAppurtenance(
            PipelineAppurtenanceId id,
            PipelineId pipelineId,
            TopologyNodeId nodeId,
            TopologyCode code,
            TopologyName name,
            PipelineAppurtenanceType appurtenanceType,
            ValveType valveType,
            PipelineKilometerPoint pipelineKilometerPoint,
            TopologyStatus status,
            GeoCoordinate coordinate,
            String description,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Pipeline appurtenance id must not be null.");
        this.pipelineId = Objects.requireNonNull(pipelineId, "Pipeline id must not be null.");
        this.nodeId = Objects.requireNonNull(nodeId, "Pipeline appurtenance node id must not be null.");
        this.code = Objects.requireNonNull(code, "Pipeline appurtenance code must not be null.");
        this.name = Objects.requireNonNull(name, "Pipeline appurtenance name must not be null.");
        this.appurtenanceType = Objects.requireNonNull(appurtenanceType, "Pipeline appurtenance type must not be null.");
        this.valveType = valveType;
        this.pipelineKilometerPoint = Objects.requireNonNull(pipelineKilometerPoint, "Pipeline kilometer point must not be null.");
        this.status = Objects.requireNonNull(status, "Pipeline appurtenance status must not be null.");
        this.coordinate = coordinate;
        this.description = normalizeOptionalText(description, 500, "Pipeline appurtenance description");
        this.createdAt = requireInstant(createdAt, "Pipeline appurtenance createdAt");
        this.updatedAt = requireInstant(updatedAt, "Pipeline appurtenance updatedAt");

        ensureValveTypeConsistency();
        ensureUpdatedAtIsValid(this.createdAt, this.updatedAt, "PipelineAppurtenance");
    }

    public static PipelineAppurtenance create(
            PipelineId pipelineId,
            TopologyNodeId nodeId,
            TopologyCode code,
            TopologyName name,
            PipelineAppurtenanceType appurtenanceType,
            ValveType valveType,
            PipelineKilometerPoint pipelineKilometerPoint,
            GeoCoordinate coordinate,
            String description) {

        Instant now = Instant.now();
        return new PipelineAppurtenance(
                PipelineAppurtenanceId.newId(),
                pipelineId,
                nodeId,
                code,
                name,
                appurtenanceType,
                valveType,
                pipelineKilometerPoint,
                TopologyStatus.PLANNED,
                coordinate,
                description,
                now,
                now);
    }

    public static PipelineAppurtenance restore(
            PipelineAppurtenanceId id,
            PipelineId pipelineId,
            TopologyNodeId nodeId,
            TopologyCode code,
            TopologyName name,
            PipelineAppurtenanceType appurtenanceType,
            ValveType valveType,
            PipelineKilometerPoint pipelineKilometerPoint,
            TopologyStatus status,
            GeoCoordinate coordinate,
            String description,
            Instant createdAt,
            Instant updatedAt) {

        return new PipelineAppurtenance(
                id,
                pipelineId,
                nodeId,
                code,
                name,
                appurtenanceType,
                valveType,
                pipelineKilometerPoint,
                status,
                coordinate,
                description,
                createdAt,
                updatedAt);
    }

    @Override
    public PipelineAppurtenanceId id() {
        return id;
    }

    public PipelineId pipelineId() {
        return pipelineId;
    }

    public TopologyNodeId nodeId() {
        return nodeId;
    }

    public TopologyCode code() {
        return code;
    }

    public TopologyName name() {
        return name;
    }

    public PipelineAppurtenanceType appurtenanceType() {
        return appurtenanceType;
    }

    public ValveType valveType() {
        return valveType;
    }

    public PipelineKilometerPoint pipelineKilometerPoint() {
        return pipelineKilometerPoint;
    }

    public TopologyStatus status() {
        return status;
    }

    public GeoCoordinate coordinate() {
        return coordinate;
    }

    public String description() {
        return description;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }


    /**
     * Activates this topology asset.
     *
     * @return active topology asset
     */
    public PipelineAppurtenance activate() {
        return withStatus(TopologyStatus.ACTIVE);
    }

    /**
     * Deactivates this topology asset.
     *
     * @return inactive topology asset
     */
    public PipelineAppurtenance deactivate() {
        return withStatus(TopologyStatus.INACTIVE);
    }

    /**
     * Marks this topology asset as under maintenance.
     *
     * @return topology asset under maintenance
     */
    public PipelineAppurtenance markUnderMaintenance() {
        return withStatus(TopologyStatus.UNDER_MAINTENANCE);
    }

    /**
     * Retires this topology asset.
     *
     * @return retired topology asset
     */
    public PipelineAppurtenance retire() {
        return withStatus(TopologyStatus.RETIRED);
    }

    /**
     * Decommissions this topology asset.
     *
     * @return decommissioned topology asset
     */
    public PipelineAppurtenance decommission() {
        return withStatus(TopologyStatus.DECOMMISSIONED);
    }

    private PipelineAppurtenance withStatus(TopologyStatus newStatus) {
        return new PipelineAppurtenance(
                id,
                pipelineId,
                nodeId,
                code,
                name,
                appurtenanceType,
                valveType,
                pipelineKilometerPoint,
                Objects.requireNonNull(newStatus, "Pipeline appurtenance status must not be null."),
                coordinate,
                description,
                createdAt,
                Instant.now());
    }

    private void ensureValveTypeConsistency() {
        if (appurtenanceType.isValve() && valveType == null) {
            throw new BusinessRuleViolationException("PipelineAppurtenance valve type is required when appurtenance type is VALVE.");
        }

        if (!appurtenanceType.isValve() && valveType != null) {
            throw new BusinessRuleViolationException("PipelineAppurtenance valve type must be null when appurtenance type is not VALVE.");
        }
    }

    private static String normalizeOptionalText(String value, int maxLength, String fieldName) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();

        if (normalized.length() > maxLength) {
            throw new BusinessRuleViolationException(fieldName + " length must not exceed " + maxLength + " characters.");
        }

        return normalized;
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
