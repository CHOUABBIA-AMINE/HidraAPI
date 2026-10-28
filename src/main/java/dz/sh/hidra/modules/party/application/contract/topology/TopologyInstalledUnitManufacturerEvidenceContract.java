/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyInstalledUnitManufacturerEvidenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.contract.topology
 *
 * @Description : Exports fresh Party-owned manufacturer evidence without granting installed-unit authority.
 *
 */
package dz.sh.hidra.modules.party.application.contract.topology;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.List;

/**
 * Owner-only evidence from one actual physical transaction.
 * This is NOT a permission, manufacturer-kind mapping, serial attestation,
 * engineering qualification or transferable authorization token.
 */
public interface TopologyInstalledUnitManufacturerEvidenceContract {
    /** Acquire a single-use, issuer-bound ticket under the caller's active physical transaction. */
    Admission acquire(String manufacturerPartyId);

    /** Return only live Party-owned evidence; all required owner tables remain locked until commit. */
    Contribution read(Admission admission);

    /** Reviewed relation-fence requirements for a future trusted Topology coordinator. */
    List<ProtectedRelationFence> finalFences(Admission admission);

    /** No public constructor or transferable native proof. */
    interface Admission {}

    /**
     * Current manufacturer role and profile facts, never an operator or company grant.
     * Capability is a Party catalog category, NOT an equipment-kind compatibility decision.
     */
    record Contribution(
            String manufacturerPartyId, long partyGeneration,
            String roleCode, String roleAssignmentId,
            Instant validFrom, Instant validTo, boolean qualificationRequired,
            String manufacturerCapability, String qualificationStatus,
            Instant sampledAt, int backendPid, long transactionId) {
        public Contribution {
            if (manufacturerPartyId == null || manufacturerPartyId.isBlank()
                    || !manufacturerPartyId.equals(manufacturerPartyId.trim())
                    || roleCode == null || roleCode.isBlank()
                    || roleAssignmentId == null || roleAssignmentId.isBlank()
                    || manufacturerCapability == null || manufacturerCapability.isBlank()
                    || qualificationStatus == null || qualificationStatus.isBlank()
                    || validFrom == null || sampledAt == null
                    || validFrom.isAfter(sampledAt)
                    || (validTo != null && !sampledAt.isBefore(validTo))
                    || partyGeneration <= 0 || backendPid <= 0 || transactionId <= 0)
                throw new IllegalArgumentException("Invalid Party manufacturer evidence");
        }
        @Override public String toString() {
            return "Contribution[opaque Party manufacturer evidence]";
        }
    }
}
