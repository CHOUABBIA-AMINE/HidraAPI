/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTicketCorrectionPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.service
 * @Description : Protect finalized ticket lineage against branches, cycles and stale revisions.
 */
package dz.sh.hidra.modules.custody.domain.service;
import dz.sh.hidra.modules.custody.domain.model.CustodyTicketCorrection;
import java.util.*;
public final class CustodyTicketCorrectionPolicy {
    public void requireCorrectable(CustodyTicketCorrection correction,String activeTicketId,long activeVersion,
          boolean originalFinalized,boolean successorExists,Set<String> lineage){
        Objects.requireNonNull(correction);Objects.requireNonNull(lineage);
        if(!originalFinalized||successorExists||!correction.originalTicketId().equals(activeTicketId)
           ||correction.expectedOriginalVersion()!=activeVersion||lineage.contains(correction.supersedingTicketId()))
            throw new IllegalStateException("Correction must extend the unique finalized active head");
    }
}
