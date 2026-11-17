/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTicketCorrection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 * @Description : Immutable correction provenance and optimistic version boundary.
 */
package dz.sh.hidra.modules.custody.domain.model;
import java.time.Instant;
import java.util.*;
public record CustodyTicketCorrection(String originalTicketId,String supersedingTicketId,
        UUID commandId,String reason,String evidenceReferenceId,String actorId,long expectedOriginalVersion,Instant requestedAt){
    public CustodyTicketCorrection {
        for(String s:Arrays.asList(originalTicketId,supersedingTicketId,reason,evidenceReferenceId,actorId))
            if(s==null||s.isBlank()||!s.equals(s.strip()))throw new IllegalArgumentException("Exact correction provenance required");
        Objects.requireNonNull(commandId);Objects.requireNonNull(requestedAt);
        if(originalTicketId.equals(supersedingTicketId)||expectedOriginalVersion<1)
            throw new IllegalArgumentException("Invalid correction version or successor");
    }
}
