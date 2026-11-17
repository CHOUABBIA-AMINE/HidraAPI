/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyReconciliationPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.service
 * @Description : Deterministically reconcile only coherent immutable observed intervals.
 */
package dz.sh.hidra.modules.custody.domain.service;
import dz.sh.hidra.modules.custody.domain.model.CustodyReconciliationSnapshot;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
public final class CustodyReconciliationPolicy {
    public record Receipt(String id,String periodId,String agreementId,String transferPointId,String companyId,
          String meterRunId,String productPublicationId,String unitId,String quantityBasis,
          Instant start,Instant end,BigDecimal observedQuantity){
        public Receipt {
            for(String s:Arrays.asList(id,periodId,agreementId,transferPointId,companyId,meterRunId,productPublicationId,unitId,quantityBasis))
                if(s==null||s.isBlank())throw new IllegalArgumentException("Missing observed evidence identity");
            Objects.requireNonNull(start);Objects.requireNonNull(end);Objects.requireNonNull(observedQuantity);
            if(!start.isBefore(end)||!"OBSERVED".equals(quantityBasis)||observedQuantity.signum()<0)
                throw new IllegalArgumentException("Unsupported observed interval");
            precision(observedQuantity);
        }
    }
    public static void precision(BigDecimal quantity) {
        if(quantity==null||quantity.scale()>6||quantity.setScale(6).unscaledValue().abs().toString().length()>18)
            throw new IllegalArgumentException("Quantity exceeds numeric(18,6) precision");
    }
    public CustodyReconciliationSnapshot reconcile(List<Receipt> input,BigDecimal ticketTotal,String actor,Instant at) {
        Objects.requireNonNull(input);Objects.requireNonNull(at);
        if(ticketTotal==null||ticketTotal.signum()<0||actor==null||actor.isBlank()||input.isEmpty())
            throw new IllegalArgumentException("Nonempty exact reconciliation required");
        precision(ticketTotal);
        List<Receipt> ordered=input.stream().sorted(Comparator.comparing(Receipt::start)
                  .thenComparing(Receipt::end).thenComparing(Receipt::id)).toList();
        Receipt first=ordered.getFirst();
        Set<String> ids=new HashSet<>();
        BigDecimal total=BigDecimal.ZERO;
        Instant previousEnd=null;
        for(Receipt r:ordered){
            if(!ids.add(r.id())||!first.periodId().equals(r.periodId())||!first.agreementId().equals(r.agreementId())
                ||!first.transferPointId().equals(r.transferPointId())||!first.companyId().equals(r.companyId())
                ||!first.meterRunId().equals(r.meterRunId())||!first.productPublicationId().equals(r.productPublicationId())
                ||!first.unitId().equals(r.unitId())||!first.quantityBasis().equals(r.quantityBasis())
                ||previousEnd!=null&&r.start().isBefore(previousEnd))
                throw new IllegalArgumentException("Mixed, duplicated or overlapping receipt evidence");
            total=total.add(r.observedQuantity());precision(total);previousEnd=r.end();
        }
        return new CustodyReconciliationSnapshot(first.periodId(),first.agreementId(),first.transferPointId(),
            first.companyId(),first.unitId(),"OBSERVED",ordered.stream().map(Receipt::id).toList(),
            total,ticketTotal,ticketTotal.subtract(total),"T21-OBSERVED-1",actor,at);
    }
}
