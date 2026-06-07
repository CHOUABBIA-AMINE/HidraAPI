/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryBindingPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.policy
 *
 * @Description : Domain policy for telemetry point topology bindings.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.policy;

import java.util.List;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPointBinding;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TopologyAssetReference;

/**
 * Domain policy for telemetry point topology bindings.
 *
 * <p>Business role:
 * Ensures telemetry points are bound to topology assets through neutral references and prevents
 * duplicate active bindings for the same role.
 *
 * <p>Architecture role:
 * Pure domain policy. It validates references passed by the application layer and never imports
 * topology implementation classes.
 */
public final class TelemetryBindingPolicy {

    /**
     * Ensures a point can be bound to a topology asset.
     *
     * @param point telemetry point
     * @param topologyAssetReference neutral topology asset reference
     */
    public void requirePointCanBeBound(TelemetryPoint point, TopologyAssetReference topologyAssetReference) {
        Objects.requireNonNull(point, "Telemetry point must not be null.");
        Objects.requireNonNull(topologyAssetReference, "Topology asset reference must not be null.");

        if (TelemetryPointStatus.RETIRED.equals(point.status())) {
            throw new BusinessRuleViolationException("Retired telemetry point cannot be bound to topology.");
        }
    }

    /**
     * Ensures no active binding already exists for the same point, topology asset, and binding role.
     *
     * @param candidate candidate binding
     * @param existingBindings current bindings for the point
     */
    public void requireNoDuplicateActiveBinding(
            TelemetryPointBinding candidate,
            List<TelemetryPointBinding> existingBindings) {

        Objects.requireNonNull(candidate, "Telemetry point binding candidate must not be null.");

        if (!candidate.active()) {
            return;
        }

        List<TelemetryPointBinding> safeExistingBindings = existingBindings == null
                ? List.of()
                : List.copyOf(existingBindings);

        boolean duplicate = safeExistingBindings.stream()
                .filter(TelemetryPointBinding::active)
                .anyMatch(existing -> sameBindingIdentity(existing, candidate));

        if (duplicate) {
            throw new BusinessRuleViolationException(
                    "An active telemetry point binding already exists for the same point, topology asset, and role.");
        }
    }

    /**
     * Ensures a binding can be closed.
     *
     * @param binding telemetry point binding
     */
    public void requireBindingCanBeClosed(TelemetryPointBinding binding) {
        Objects.requireNonNull(binding, "Telemetry point binding must not be null.");

        if (!binding.active()) {
            throw new BusinessRuleViolationException("Only active telemetry point bindings can be closed.");
        }
    }

    private boolean sameBindingIdentity(TelemetryPointBinding left, TelemetryPointBinding right) {
        return left.pointId().equals(right.pointId())
                && left.bindingRole().equals(right.bindingRole())
                && left.topologyAssetReference().assetTypeCode().equals(right.topologyAssetReference().assetTypeCode())
                && left.topologyAssetReference().assetId().equals(right.topologyAssetReference().assetId());
    }
}
