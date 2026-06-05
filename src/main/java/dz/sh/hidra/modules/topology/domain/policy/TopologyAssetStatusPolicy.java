/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyAssetStatusPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.policy
 *
 * @Description : Policy validating topology asset status transitions.
 *
 */
package dz.sh.hidra.modules.topology.domain.policy;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

import dz.sh.hidra.modules.topology.domain.exception.TopologyValidationException;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;

/**
 * Validates lifecycle transitions for topology assets.
 *
 * <p>Business role:
 * This policy centralizes allowed status transitions for pipeline systems, pipelines, facilities,
 * nodes, pipeline segments, pipeline appurtenances, connections, and equipment references.
 *
 * <p>Architecture role:
 * This is a pure domain policy. It must not be annotated as a Spring bean and must not access
 * repositories, persistence adapters, REST DTOs, identity implementation, organization implementation,
 * measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * Retired and decommissioned assets are terminal. Decommissioned assets cannot transition to any
 * other status. Planned assets can become active, inactive, under maintenance, retired, or
 * decommissioned. Active assets can become inactive, under maintenance, retired, or decommissioned.
 *
 * <p>Usage:
 * Application services and domain services may call this policy before creating a new model instance
 * with a changed status.
 */
public final class TopologyAssetStatusPolicy {

    private static final Set<TopologyStatus> PLANNED_TARGETS = EnumSet.of(
            TopologyStatus.ACTIVE,
            TopologyStatus.INACTIVE,
            TopologyStatus.UNDER_MAINTENANCE,
            TopologyStatus.RETIRED,
            TopologyStatus.DECOMMISSIONED);

    private static final Set<TopologyStatus> ACTIVE_TARGETS = EnumSet.of(
            TopologyStatus.INACTIVE,
            TopologyStatus.UNDER_MAINTENANCE,
            TopologyStatus.RETIRED,
            TopologyStatus.DECOMMISSIONED);

    private static final Set<TopologyStatus> INACTIVE_TARGETS = EnumSet.of(
            TopologyStatus.ACTIVE,
            TopologyStatus.UNDER_MAINTENANCE,
            TopologyStatus.RETIRED,
            TopologyStatus.DECOMMISSIONED);

    private static final Set<TopologyStatus> MAINTENANCE_TARGETS = EnumSet.of(
            TopologyStatus.ACTIVE,
            TopologyStatus.INACTIVE,
            TopologyStatus.RETIRED,
            TopologyStatus.DECOMMISSIONED);

    private static final Set<TopologyStatus> RETIRED_TARGETS = EnumSet.of(
            TopologyStatus.DECOMMISSIONED);

    /**
     * Validates a lifecycle transition.
     *
     * @param currentStatus current status
     * @param requestedStatus requested status
     */
    public void validateTransition(TopologyStatus currentStatus, TopologyStatus requestedStatus) {
        Objects.requireNonNull(currentStatus, "Current topology status must not be null.");
        Objects.requireNonNull(requestedStatus, "Requested topology status must not be null.");

        if (currentStatus == requestedStatus) {
            return;
        }

        if (!allowedTargets(currentStatus).contains(requestedStatus)) {
            throw new TopologyValidationException(
                    "Topology asset status transition from " + currentStatus + " to " + requestedStatus + " is not allowed.");
        }
    }

    /**
     * Ensures a topology asset can be used for ordinary operations.
     *
     * @param status status to check
     */
    public void requireOperationallyUsable(TopologyStatus status) {
        Objects.requireNonNull(status, "Topology status must not be null.");

        if (!status.allowsOperationalUse()) {
            throw new TopologyValidationException("Topology asset must be ACTIVE to be used operationally.");
        }
    }

    /**
     * Ensures a topology asset can receive child/linked topology elements.
     *
     * @param status status to check
     */
    public void requireCanAttachTopologyChild(TopologyStatus status) {
        Objects.requireNonNull(status, "Topology status must not be null.");

        if (status.isTerminalLifecycleState()) {
            throw new TopologyValidationException("Topology child assets cannot be attached to retired or decommissioned assets.");
        }
    }

    private Set<TopologyStatus> allowedTargets(TopologyStatus currentStatus) {
        return switch (currentStatus) {
            case PLANNED -> PLANNED_TARGETS;
            case ACTIVE -> ACTIVE_TARGETS;
            case INACTIVE -> INACTIVE_TARGETS;
            case UNDER_MAINTENANCE -> MAINTENANCE_TARGETS;
            case RETIRED -> RETIRED_TARGETS;
            case DECOMMISSIONED -> Set.of();
        };
    }
}
