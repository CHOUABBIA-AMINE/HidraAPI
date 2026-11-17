/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyReconciliationSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 * @Description : Immutable, exactly-scoped reconciliation evidence.
 */
package dz.sh.hidra.modules.custody.domain.model;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
public record CustodyReconciliationSnapshot(String periodId,String agreementId,String transferPointId,
        String companyId,String unitId,String quantityBasis,List<String> receiptIds,
        BigDecimal observedTotal,BigDecimal ticketTotal,BigDecimal difference,
        String selectionVersion,String actorId,Instant reconciledAt) {
    public CustodyReconciliationSnapshot {
        for(String s:Arrays.asList(periodId,agreementId,transferPointId,companyId,unitId,quantityBasis,selectionVersion,actorId))
            if(s==null||s.isBlank()||!s.equals(s.strip()))throw new IllegalArgumentException("Exact reconciliation scope required");
        receiptIds=List.copyOf(Objects.requireNonNull(receiptIds));
        if(receiptIds.isEmpty()||receiptIds.stream().anyMatch(s->s==null||s.isBlank())||
                receiptIds.stream().distinct().count()!=receiptIds.size())
            throw new IllegalArgumentException("Unique nonempty receipt selection required");
        Objects.requireNonNull(reconciledAt);
        Objects.requireNonNull(observedTotal);Objects.requireNonNull(ticketTotal);Objects.requireNonNull(difference);
        if(!"OBSERVED".equals(quantityBasis)||observedTotal.signum()<0||ticketTotal.signum()<0
                ||difference.compareTo(ticketTotal.subtract(observedTotal))!=0)
            throw new IllegalArgumentException("Invalid observed reconciliation");
    }
}
