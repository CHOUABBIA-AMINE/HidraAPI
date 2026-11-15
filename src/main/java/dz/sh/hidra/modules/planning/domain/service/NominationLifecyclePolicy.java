/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NominationLifecyclePolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.service
 *
 * @Description : Enforces the independent disposition and interval rules for governed nominations.
 *
 */
package dz.sh.hidra.modules.planning.domain.service;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.exception.PlanningRevisionConflictException;
import dz.sh.hidra.modules.planning.domain.model.Nomination;
import dz.sh.hidra.modules.planning.domain.value.NominationStatus;
import java.time.Instant;

public final class NominationLifecyclePolicy {
    public enum Decision {
        CONFIRM(NominationStatus.CONFIRMED), REJECT(NominationStatus.REJECTED);

        private final NominationStatus result;

        Decision(NominationStatus result) {
            this.result = result;
        }
    }

    private NominationLifecyclePolicy() { }

    public static void requireInterval(Instant start, Instant end, Instant periodStart, Instant periodEnd) {
        if (!start.isBefore(end) || start.isBefore(periodStart) || end.isAfter(periodEnd)) {
            throw new InvalidPlanningValueException("Nomination interval must fit within the planning period.");
        }
    }

    public static Nomination dispose(Nomination nomination, String author, String reviewer,
            Decision decision, Instant recordedAt) {
        if (author.equals(reviewer)) {
            throw new PlanningRevisionConflictException("Nomination disposition requires a separate reviewer.");
        }
        if (nomination.status() != NominationStatus.DRAFT) {
            throw new PlanningRevisionConflictException("Only a draft nomination can be dispositioned.");
        }
        return new Nomination(nomination.id(), nomination.revisionId(), nomination.scenarioId(),
                nomination.code(), nomination.nominationTypeId(), nomination.productTypeId(),
                nomination.quantity(), nomination.quantityUnitId(), nomination.rate(), nomination.rateUnitId(),
                nomination.sourceAssetType(), nomination.sourceAssetId(), nomination.sourceAssetCode(),
                nomination.destinationAssetType(), nomination.destinationAssetId(), nomination.destinationAssetCode(),
                nomination.shipperPartyId(), nomination.shipperPartyCodeSnapshot(), nomination.counterpartyId(),
                nomination.contractReferenceId(), nomination.priority(), decision.result, nomination.periodStart(),
                nomination.periodEnd(), nomination.createdAt(), recordedAt);
    }
}
