/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationConfigurationSourceEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Defines bounded, lock-qualified source evidence without operation authority.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.List;

/**
 * Only an already-fenced physical transaction can sample the private sources.
 * Observations are not permissions, cannot be transferred and never authorize DML.
 * B3-S2 must independently obtain native authority and the actual source locks.
 */
public interface StationConfigurationSourceEvidencePort {
    interface Admission {}
    Admission acquire(StationInstalledConfiguration proposed);
    SourceObservation read(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);

    record SourceObservation(String facilityId,String companyPartyId,String graphRevisionId,
                             long graphEpoch,int positionCount,String sourceSha256,
                             int backendPid,long transactionId,String databaseRole) {
        public SourceObservation {
            if(facilityId==null || facilityId.isBlank() || companyPartyId==null || companyPartyId.isBlank()
                    || graphRevisionId==null || graphRevisionId.isBlank() || graphEpoch<1
                    || positionCount<0 || positionCount>StationInstalledConfiguration.MAX_POSITIONS
                    || sourceSha256==null || !sourceSha256.matches("[0-9a-f]{64}")
                    || backendPid<1 || transactionId<1 || databaseRole==null || databaseRole.isBlank())
                throw new IllegalArgumentException("Invalid source observation");
        }
        @Override public String toString(){return "SourceObservation[private fenced evidence]";}
    }
}
