/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationGraphPublicationHead
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Defines one immutable Topology-owned published station head.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.util.Objects;
import java.util.UUID;

public record StationGraphPublicationHead(String facilityId,String revisionId,long epoch,State state,UUID lastEventId) {
    public enum State { ACTIVE, WITHDRAWN }
    public StationGraphPublicationHead {
        facilityId=identity(facilityId);revisionId=identity(revisionId);
        if(epoch<=0 || state==null || lastEventId==null)throw new IllegalArgumentException("Invalid publication head");
    }
    public static String identity(String value) {
        if(value==null || value.isBlank() || !value.equals(value.strip()))throw new IllegalArgumentException("Exact station revision identity required");
        return value;
    }
    public StationGraphPublicationHead transitioned(String revision,State target,UUID eventId) {
        if(epoch==Long.MAX_VALUE || target==null || eventId==null)throw new IllegalArgumentException("Publication epoch exhausted");
        revision=identity(revision);
        if(target==State.WITHDRAWN && (state!=State.ACTIVE || !revision.equals(revisionId)))
            throw new IllegalArgumentException("Invalid station withdrawal");
        if(target==State.ACTIVE && state==State.WITHDRAWN && !revision.equals(revisionId))
            throw new IllegalArgumentException("Reactivation cannot change revision");
        if(target==State.ACTIVE && state==State.ACTIVE && revision.equals(revisionId))
            throw new IllegalArgumentException("Publication requires a new revision");
        return new StationGraphPublicationHead(facilityId,revision,epoch+1,target,eventId);
    }
}
