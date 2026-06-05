/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSegment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Pipeline segment entity representing a physical pipe section between nodes.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.topology.domain.value.DiameterInInches;
import dz.sh.hidra.modules.topology.domain.value.LengthInKilometers;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.PipelineSegmentId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Represents a physical pipe section between two topology nodes.
 *
 * <p>Business role:
 * A pipeline segment belongs to one pipeline and connects a from-node to a to-node.
 *
 * <p>Architecture role:
 * This is a pure topology domain entity and must not depend on Spring, JPA, REST DTOs, identity,
 * organization implementation, measurement, flow, risk, workflow, or infrastructure.
 *
 * <p>Validation:
 * Pipeline identifier, code, name, from-node, to-node, length, diameter, status, creation instant,
 * and update instant are mandatory. From-node and to-node must be different.
 */
public final class PipelineSegment implements Entity<PipelineSegmentId> {

    private final PipelineSegmentId id;
    private final PipelineId pipelineId;
    private final TopologyCode code;
    private final TopologyName name;
    private final TopologyNodeId fromNodeId;
    private final TopologyNodeId toNodeId;
    private final LengthInKilometers length;
    private final DiameterInInches diameter;
    private final TopologyStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;

    private PipelineSegment(
            PipelineSegmentId id,
            PipelineId pipelineId,
            TopologyCode code,
            TopologyName name,
            TopologyNodeId fromNodeId,
            TopologyNodeId toNodeId,
            LengthInKilometers length,
            DiameterInInches diameter,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Pipeline segment id must not be null.");
        this.pipelineId = Objects.requireNonNull(pipelineId, "Pipeline id must not be null.");
        this.code = Objects.requireNonNull(code, "Pipeline segment code must not be null.");
        this.name = Objects.requireNonNull(name, "Pipeline segment name must not be null.");
        this.fromNodeId = Objects.requireNonNull(fromNodeId, "Pipeline segment from node id must not be null.");
        this.toNodeId = Objects.requireNonNull(toNodeId, "Pipeline segment to node id must not be null.");
        this.length = Objects.requireNonNull(length, "Pipeline segment length must not be null.");
        this.diameter = Objects.requireNonNull(diameter, "Pipeline segment diameter must not be null.");
        this.status = Objects.requireNonNull(status, "Pipeline segment status must not be null.");
        this.createdAt = requireInstant(createdAt, "Pipeline segment createdAt");
        this.updatedAt = requireInstant(updatedAt, "Pipeline segment updatedAt");

        ensureDifferentNodes();
        ensureUpdatedAtIsValid(this.createdAt, this.updatedAt, "PipelineSegment");
    }

    public static PipelineSegment create(
            PipelineId pipelineId,
            TopologyCode code,
            TopologyName name,
            TopologyNodeId fromNodeId,
            TopologyNodeId toNodeId,
            LengthInKilometers length,
            DiameterInInches diameter) {

        Instant now = Instant.now();
        return new PipelineSegment(
                PipelineSegmentId.newId(),
                pipelineId,
                code,
                name,
                fromNodeId,
                toNodeId,
                length,
                diameter,
                TopologyStatus.PLANNED,
                now,
                now);
    }

    public static PipelineSegment restore(
            PipelineSegmentId id,
            PipelineId pipelineId,
            TopologyCode code,
            TopologyName name,
            TopologyNodeId fromNodeId,
            TopologyNodeId toNodeId,
            LengthInKilometers length,
            DiameterInInches diameter,
            TopologyStatus status,
            Instant createdAt,
            Instant updatedAt) {

        return new PipelineSegment(
                id,
                pipelineId,
                code,
                name,
                fromNodeId,
                toNodeId,
                length,
                diameter,
                status,
                createdAt,
                updatedAt);
    }

    @Override
    public PipelineSegmentId id() {
        return id;
    }

    public PipelineId pipelineId() {
        return pipelineId;
    }

    public TopologyCode code() {
        return code;
    }

    public TopologyName name() {
        return name;
    }

    public TopologyNodeId fromNodeId() {
        return fromNodeId;
    }

    public TopologyNodeId toNodeId() {
        return toNodeId;
    }

    public LengthInKilometers length() {
        return length;
    }

    public DiameterInInches diameter() {
        return diameter;
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


    /**
     * Activates this topology asset.
     *
     * @return active topology asset
     */
    public PipelineSegment activate() {
        return withStatus(TopologyStatus.ACTIVE);
    }

    /**
     * Deactivates this topology asset.
     *
     * @return inactive topology asset
     */
    public PipelineSegment deactivate() {
        return withStatus(TopologyStatus.INACTIVE);
    }

    /**
     * Marks this topology asset as under maintenance.
     *
     * @return topology asset under maintenance
     */
    public PipelineSegment markUnderMaintenance() {
        return withStatus(TopologyStatus.UNDER_MAINTENANCE);
    }

    /**
     * Retires this topology asset.
     *
     * @return retired topology asset
     */
    public PipelineSegment retire() {
        return withStatus(TopologyStatus.RETIRED);
    }

    /**
     * Decommissions this topology asset.
     *
     * @return decommissioned topology asset
     */
    public PipelineSegment decommission() {
        return withStatus(TopologyStatus.DECOMMISSIONED);
    }

    private PipelineSegment withStatus(TopologyStatus newStatus) {
        return new PipelineSegment(
                id,
                pipelineId,
                code,
                name,
                fromNodeId,
                toNodeId,
                length,
                diameter,
                Objects.requireNonNull(newStatus, "Pipeline segment status must not be null."),
                createdAt,
                Instant.now());
    }

    private void ensureDifferentNodes() {
        if (fromNodeId.equals(toNodeId)) {
            throw new BusinessRuleViolationException("PipelineSegment from node and to node must be different.");
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
