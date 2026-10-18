/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationGraphProtectedPublicationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Defines owner-private guarded head and immutable ledger persistence.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.model.*;
import java.time.Instant;
import java.util.UUID;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/** Ticket origin and physical participation are always verified by the issuing adapter. */
public interface StationGraphProtectedPublicationPort {
    interface Admission {}
    Admission acquire(String facilityId);
    Optional<StationGraphPublicationHead> head(Admission ticket);
    Optional<StationInternalGraphRevision> revision(Admission ticket,String revisionId);
    List<StationGraphPublicationEvent> history(Admission ticket,int limit);
    /** Exact constant reviewed SQL texts, not caller-provided templates. */
    Set<String> mutationSql();
    void append(Admission ticket,StationInternalGraphRevision revision,
                StationGraphPublicationEvent event);
    StationGraphPublicationHead change(Admission ticket,StationGraphPublicationHead next,
        StationGraphPublicationEvent event,Long expectedEpoch,String expectedRevisionId);
    boolean finalGuard(Admission ticket,Instant sampledAt,Instant validUntil,String companyPartyId,
                       long partyGeneration,long facilityGeneration,StationGraphPublicationHead head,UUID eventId);
}
