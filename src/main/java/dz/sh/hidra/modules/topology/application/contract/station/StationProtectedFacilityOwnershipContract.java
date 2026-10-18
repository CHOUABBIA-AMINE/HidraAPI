/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationProtectedFacilityOwnershipContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.contract.station
 *
 * @Description : Provides protected owner evidence in the actual physical transaction.
 *
 */
package dz.sh.hidra.modules.topology.application.contract.station;

/** Live owner data only; the trusted coordinator owns permissions, time and publication gates. */
public interface StationProtectedFacilityOwnershipContract {
    Admission acquire(String exactId);
    Contribution read(Admission admission);

    /** Marker only: fabricated implementations are never accepted by the configured adapter. */
    interface Admission {}

    /** Constructible scalar data is not a transferable attestation or permission. */
    record Contribution(String facilityId, String ownerPartyId, long generation, int backendPid, long transactionId) {
        public Contribution {
            identity(facilityId); identity(ownerPartyId);
            if (generation <= 0 || backendPid <= 0 || transactionId <= 0)
                throw new IllegalArgumentException("Invalid owner evidence coordinates");
        }
    }

    private static void identity(String id) {
        if (id == null || id.isBlank() || !id.equals(id.trim()) || id.length() > 80)
            throw new IllegalArgumentException("Exact owner identity required");
    }
}
