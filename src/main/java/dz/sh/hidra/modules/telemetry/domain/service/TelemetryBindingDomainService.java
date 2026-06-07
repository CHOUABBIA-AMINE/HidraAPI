/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryBindingDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.service
 *
 * @Description : Domain service for telemetry point topology binding workflows.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPointBinding;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryBindingPolicy;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryBindingRoleReference;
import dz.sh.hidra.modules.telemetry.domain.value.TopologyAssetReference;

/**
 * Domain service for telemetry point topology binding workflows.
 *
 * <p>Business role:
 * Creates and closes neutral bindings between telemetry points and topology assets while preventing
 * duplicate active bindings.
 *
 * <p>Architecture role:
 * Pure telemetry domain service. It receives neutral topology asset references and never imports
 * topology implementation classes.
 */
public final class TelemetryBindingDomainService {

    private final TelemetryBindingPolicy bindingPolicy;

    public TelemetryBindingDomainService(TelemetryBindingPolicy bindingPolicy) {
        this.bindingPolicy = Objects.requireNonNull(bindingPolicy, "TelemetryBindingPolicy must not be null.");
    }

    public TelemetryPointBinding bindPointToTopology(
            TelemetryPoint point,
            TopologyAssetReference topologyAssetReference,
            TelemetryBindingRoleReference bindingRole,
            List<TelemetryPointBinding> existingBindings,
            Instant validFrom) {

        bindingPolicy.requirePointCanBeBound(point, topologyAssetReference);

        TelemetryPointBinding binding = TelemetryPointBinding.create(
                point.id(),
                topologyAssetReference,
                bindingRole,
                validFrom);

        bindingPolicy.requireNoDuplicateActiveBinding(binding, existingBindings);

        return binding;
    }

    public TelemetryPointBinding closeBinding(TelemetryPointBinding binding, Instant closedAt) {
        bindingPolicy.requireBindingCanBeClosed(binding);
        return binding.close(closedAt);
    }
}
