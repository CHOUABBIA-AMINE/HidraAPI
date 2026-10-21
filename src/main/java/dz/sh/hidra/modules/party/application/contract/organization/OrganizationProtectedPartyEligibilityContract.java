/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationProtectedPartyEligibilityContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.contract.organization
 *
 * @Description : Exports technical current Party eligibility for protected Organization admission.
 *
 */
package dz.sh.hidra.modules.party.application.contract.organization;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.List;

/** Technical new-reference eligibility only; no legal-employer, company-ownership or permission assertion. */
public interface OrganizationProtectedPartyEligibilityContract {
    Admission acquire(String exactId);
    Contribution read(Admission admission);

    /** Recheck the exact sampled generation and physical transaction; never authorizes an operation. */
    void requireCurrent(Admission admission);

    /** Requires a consumed, still-current issuing ticket; returned fences are structural data. */
    List<ProtectedRelationFence> finalFences(Admission admission);

    /** Marker only: fabricated implementations are rejected by the configured owner. */
    interface Admission {}

    /** Constructible scalar data is not a transferable attestation or permission. */
    record Contribution(String partyId, long generation, int backendPid, long transactionId) {
        public Contribution {
            identity(partyId);
            if (generation <= 0 || backendPid <= 0 || transactionId <= 0)
                throw new IllegalArgumentException("Invalid owner evidence coordinates");
        }
    }

    private static void identity(String id) {
        if (id == null || id.isBlank() || !id.equals(id.trim()) || id.length() > 80 || !id.equals(id.strip())
                || id.chars().anyMatch(Character::isISOControl))
            throw new IllegalArgumentException("Exact owner identity required");
    }
}
