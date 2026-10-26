/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyProtectedPartyEligibilityContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-26
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.contract.custody
 *
 * @Description : Provides protected owner evidence in the actual physical transaction.
 *
 */
package dz.sh.hidra.modules.party.application.contract.custody;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.List;

/** Live owner data only; the trusted coordinator owns permissions, time and publication gates. */
public interface CustodyProtectedPartyEligibilityContract {
    Admission acquire(String exactId);
    Contribution read(Admission admission);

    /** Marker only: fabricated implementations are never accepted by the configured adapter. */
    /** Technical requirements from the issuing owner; data alone grants no authority. */
    List<ProtectedRelationFence> finalFences(Admission admission);

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
        if (id == null || id.isBlank() || !id.equals(id.trim()) || id.length() > 80)
            throw new IllegalArgumentException("Exact owner identity required");
    }
}
