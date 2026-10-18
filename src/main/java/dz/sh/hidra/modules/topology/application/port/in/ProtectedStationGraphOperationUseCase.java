/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationGraphOperationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Declares private bounded station graph operations requiring live protected owner admission.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.*;
import java.util.List;
import java.util.UUID;

/** Internal use case; not a REST entry point, and caller values are never authority. */
public interface ProtectedStationGraphOperationUseCase {
    enum Kind { AUTHOR,PUBLISH,READ_CURRENT,READ_HISTORY,LIST_HISTORY,WITHDRAW,REACTIVATE,TRANSFER }
    record Command(Kind kind,String companyPartyId,String facilityId,String revisionId,
                   Long expectedEpoch,String expectedRevisionId,UUID eventId,
                   StationInternalGraphRevision authoredRevision,int limit) {
        public Command {
            if(kind==null)throw new IllegalArgumentException("Operation required");
            companyPartyId=StationGraphPublicationHead.identity(companyPartyId);
            facilityId=StationGraphPublicationHead.identity(facilityId);
            if(revisionId!=null)revisionId=StationGraphPublicationHead.identity(revisionId);
            if(expectedRevisionId!=null)expectedRevisionId=StationGraphPublicationHead.identity(expectedRevisionId);
            if(expectedEpoch!=null && expectedEpoch<0)throw new IllegalArgumentException("Invalid expected epoch");
            if(limit<0 || limit>100)throw new IllegalArgumentException("History limit exceeds maximum");
            if(authoredRevision!=null && (!facilityId.equals(authoredRevision.facilityId())
                    || !authoredRevision.revisionId().equals(revisionId)))
                throw new IllegalArgumentException("Mismatched authored revision");
        }
    }
    record Result(StationGraphPublicationHead head,StationInternalGraphRevision revision,
                  List<StationGraphPublicationEvent> history, boolean observedCommit) {
        public Result {
            history=List.copyOf(history);
        }
    }
    Result execute(Command command);
}
