/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationRevisionOwnerQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Resolves current exact Facility ownership through Party eligibility without granting access.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.party.application.contract.topology.TopologyPartyEligibilityContract;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Current owner observation only; no permission, publication binding or ownership epoch.
 * Facility and Party lookups are sequential and confer no authority for a later operation.
 */
@Service
public final class StationRevisionOwnerQueryService {
    private final FacilityRepositoryPort repository;
    private final TopologyPartyEligibilityContract partyEligibility;

    public StationRevisionOwnerQueryService(
            FacilityRepositoryPort repository, TopologyPartyEligibilityContract partyEligibility) {
        this.repository = Objects.requireNonNull(repository, "FacilityRepositoryPort must not be null.");
        this.partyEligibility = Objects.requireNonNull(
                partyEligibility, "TopologyPartyEligibilityContract must not be null.");
    }

    /** Returns only the live exact owner reference; dependency failures propagate. */
    public Optional<OwnerReference> resolve(String facilityId) {
        String identity = normalizedReference(facilityId);
        if (identity == null) {
            return Optional.empty();
        }
        return repository.findById(identity)
                .filter(facility -> identity.equals(facility.id()))
                .flatMap(facility -> {
                    String owner = normalizedReference(facility.ownerPartyId());
                    if (owner == null || !partyEligibility.eligibleForNewReference(owner)) {
                        return Optional.empty();
                    }
                    return Optional.of(new OwnerReference(identity, owner));
                });
    }

    /** Application-local observation, never an access grant or transferable authority. */
    public record OwnerReference(String facilityId, String companyPartyId) {
        public OwnerReference {
            facilityId = requiredReference(facilityId, "Facility identity");
            companyPartyId = requiredReference(companyPartyId, "Company Party identity");
        }
    }

    private static String requiredReference(String value, String label) {
        String reference = normalizedReference(value);
        if (reference == null) {
            throw new IllegalArgumentException(label + " must contain 1..80 code points after trimming.");
        }
        return reference;
    }

    private static String normalizedReference(String value) {
        if (value == null) {
            return null;
        }
        String reference = value.trim();
        return reference.isBlank() || reference.codePointCount(0, reference.length()) > 80
                ? null : reference;
    }
}
