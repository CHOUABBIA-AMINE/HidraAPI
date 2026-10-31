/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationManufacturerEvidenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.contract.simulation
 *
 * @Description : Party-owned current manufacturer-to-company and model authorization evidence.
 *
 */
package dz.sh.hidra.modules.party.application.contract.simulation;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/** A Party-owned ticket never grants Identity permissions and is never a transferable assertion. */
public interface SimulationManufacturerEvidenceContract {
    record Requirement(String companyPartyId, String manufacturerPartyId, String manufacturerModelReference) {
        public Requirement {
            exact(companyPartyId,80); exact(manufacturerPartyId,80); exact(manufacturerModelReference,240);
        }
        @Override public String toString() { return "ManufacturerRequirement[redacted]"; }
    }
    record Contribution(String authorizationId, String companyPartyId, String manufacturerPartyId,
                        String manufacturerModelReference, long partyGeneration, Instant observedAt,
                        Instant validUntil, int backendPid, long transactionId, String databaseRole) {
        public Contribution {
            exact(authorizationId,80); exact(companyPartyId,80); exact(manufacturerPartyId,80);
            exact(manufacturerModelReference,240); exact(databaseRole,80);
            Objects.requireNonNull(observedAt);
            if ((validUntil != null && !observedAt.isBefore(validUntil)) || partyGeneration <= 0 || backendPid <= 0 || transactionId <= 0)
                throw new IllegalArgumentException("Invalid original-transaction manufacturer evidence");
        }
        @Override public String toString() { return "ManufacturerContribution[opaque Party evidence]"; }
    }
    interface Admission {}
    Admission acquire(Requirement requirement);
    Contribution read(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    private static void exact(String s,int limit) {
        if (s == null || s.isBlank() || !s.equals(s.strip()) || s.codePointCount(0,s.length()) > limit
                || s.codePoints().anyMatch(c -> Character.isISOControl(c) || c >= 0xd800 && c <= 0xdfff))
            throw new IllegalArgumentException("Noncanonical Party evidence identity");
    }
}
