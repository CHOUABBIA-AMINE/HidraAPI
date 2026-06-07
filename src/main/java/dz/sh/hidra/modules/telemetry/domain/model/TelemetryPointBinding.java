/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointBinding
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Telemetry point to topology asset binding domain entity.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryBindingRoleReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointBindingId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TopologyAssetReference;

/**
 * Telemetry point to topology asset binding domain entity.
 *
 * <p>Business role:
 * Binds a telemetry point to a topology asset such as pipeline, segment, facility, node,
 * appurtenance, or equipment using a neutral topology reference.
 *
 * <p>Architecture role:
 * Keeps telemetry independent from topology implementation classes.
 */
public final class TelemetryPointBinding implements Entity<TelemetryPointBindingId> {

    private final TelemetryPointBindingId id;
    private final TelemetryPointId pointId;
    private final TopologyAssetReference topologyAssetReference;
    private final TelemetryBindingRoleReference bindingRole;
    private final boolean active;
    private final Instant validFrom;
    private final Instant validTo;
    private final Instant createdAt;
    private final Instant updatedAt;

    private TelemetryPointBinding(
            TelemetryPointBindingId id,
            TelemetryPointId pointId,
            TopologyAssetReference topologyAssetReference,
            TelemetryBindingRoleReference bindingRole,
            boolean active,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Telemetry point binding id must not be null.");
        this.pointId = Objects.requireNonNull(pointId, "Telemetry point binding pointId must not be null.");
        this.topologyAssetReference = Objects.requireNonNull(topologyAssetReference, "Telemetry point binding topology reference must not be null.");
        this.bindingRole = Objects.requireNonNull(bindingRole, "Telemetry point binding role must not be null.");
        this.active = active;
        this.validFrom = Objects.requireNonNull(validFrom, "Telemetry point binding validFrom must not be null.");
        this.validTo = validTo;
        this.createdAt = Objects.requireNonNull(createdAt, "Telemetry point binding createdAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Telemetry point binding updatedAt must not be null.");

        if (validTo != null && !validTo.isAfter(validFrom)) {
            throw new BusinessRuleViolationException("Telemetry point binding validTo must be after validFrom.");
        }
        if (updatedAt.isBefore(createdAt)) {
            throw new BusinessRuleViolationException("Telemetry point binding updatedAt must not be before createdAt.");
        }
        if (active && validTo != null) {
            throw new BusinessRuleViolationException("Active telemetry point binding must not have validTo.");
        }
    }

    public static TelemetryPointBinding create(
            TelemetryPointId pointId,
            TopologyAssetReference topologyAssetReference,
            TelemetryBindingRoleReference bindingRole,
            Instant validFrom) {

        Instant now = Instant.now();
        return new TelemetryPointBinding(
                TelemetryPointBindingId.newId(),
                pointId,
                topologyAssetReference,
                bindingRole,
                true,
                validFrom == null ? now : validFrom,
                null,
                now,
                now);
    }

    public static TelemetryPointBinding restore(
            TelemetryPointBindingId id,
            TelemetryPointId pointId,
            TopologyAssetReference topologyAssetReference,
            TelemetryBindingRoleReference bindingRole,
            boolean active,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt) {

        return new TelemetryPointBinding(
                id,
                pointId,
                topologyAssetReference,
                bindingRole,
                active,
                validFrom,
                validTo,
                createdAt,
                updatedAt);
    }

    @Override
    public TelemetryPointBindingId id() {
        return id;
    }

    public TelemetryPointId pointId() {
        return pointId;
    }

    public TopologyAssetReference topologyAssetReference() {
        return topologyAssetReference;
    }

    public TelemetryBindingRoleReference bindingRole() {
        return bindingRole;
    }

    public boolean active() {
        return active;
    }

    public Instant validFrom() {
        return validFrom;
    }

    public Instant validTo() {
        return validTo;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public TelemetryPointBinding close(Instant closedAt) {
        Instant closeInstant = closedAt == null ? Instant.now() : closedAt;
        return new TelemetryPointBinding(
                id,
                pointId,
                topologyAssetReference,
                bindingRole,
                false,
                validFrom,
                closeInstant,
                createdAt,
                Instant.now());
    }
}
