/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationGraphPublicationEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Captures immutable synthetic-operation provenance without granting authority.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.util.UUID;

public record StationGraphPublicationEvent(
    UUID eventId,String facilityId,Kind kind,Long resultingEpoch,
    String priorRevisionId,String resultingRevisionId,
    String actorBindingId,String actorSessionId,String actorUserId,String companyPartyId,
    long partyGeneration,long facilityGeneration,Instant sampledAt,Instant validUntil,
    int backendPid,long transactionId) {
    public enum Kind { AUTHOR,PUBLISH,WITHDRAW,REACTIVATE }
    public StationGraphPublicationEvent {
        if(eventId==null || kind==null)throw new IllegalArgumentException("Event kind/id required");
        facilityId=StationGraphPublicationHead.identity(facilityId);
        resultingRevisionId=StationGraphPublicationHead.identity(resultingRevisionId);
        if(priorRevisionId!=null)priorRevisionId=StationGraphPublicationHead.identity(priorRevisionId);
        actorBindingId=StationGraphPublicationHead.identity(actorBindingId);
        actorSessionId=StationGraphPublicationHead.identity(actorSessionId);
        actorUserId=StationGraphPublicationHead.identity(actorUserId);
        companyPartyId=StationGraphPublicationHead.identity(companyPartyId);
        if((kind==Kind.AUTHOR)!=(resultingEpoch==null) ||
            (resultingEpoch!=null && resultingEpoch<=0) ||
            partyGeneration<=0 || facilityGeneration<=0 || backendPid<=0 || transactionId<=0 ||
            sampledAt==null || validUntil==null || !sampledAt.isBefore(validUntil))
            throw new IllegalArgumentException("Invalid immutable station operation provenance");
    }
}
