/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTransferLifecyclePolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.service
 * @Description : Guards immutable observed reconciliation and independent human ticket transitions.
 */
package dz.sh.hidra.modules.custody.domain.service;
import dz.sh.hidra.modules.custody.domain.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CustodyTransferLifecyclePolicyTest {
    private static CustodyReconciliationPolicy.Receipt receipt(String id,Instant start,Instant end,String unit,BigDecimal quantity){
        return new CustodyReconciliationPolicy.Receipt(id,"period","agreement","point","company",
            "run","product",unit,"OBSERVED",start,end,quantity);
    }
    @Test void exactObservedReconciliationRejectsDuplicatesOverlapMixedUnitAndOverflow(){
        var start=Instant.parse("2026-11-17T00:00:00Z");
        var one=receipt("m1",start,start.plusSeconds(60),"unit",new BigDecimal("15.500000"));
        var two=receipt("m2",start.plusSeconds(60),start.plusSeconds(120),"unit",new BigDecimal("25.100000"));
        var p=new CustodyReconciliationPolicy();
        var actual=p.reconcile(List.of(two,one),nullTicket(), "actor", start.plusSeconds(180));
        assertEquals(List.of("m1","m2"),actual.receiptIds());
        assertEquals(0,new BigDecimal("40.600000").compareTo(actual.observedTotal()));
        assertEquals(0,new BigDecimal("40.600000").compareTo(actual.ticketTotal()));
        assertEquals(0,actual.difference().signum());
        assertThrows(IllegalArgumentException.class,()->p.reconcile(List.of(),"0".equals("1")?null:BigDecimal.ZERO,"actor",start));
        assertThrows(IllegalArgumentException.class,()->p.reconcile(List.of(one,one),BigDecimal.ZERO,"actor",start));
        assertThrows(IllegalArgumentException.class,()->p.reconcile(List.of(one,
            receipt("m2",start.plusSeconds(30),start.plusSeconds(90),"unit",BigDecimal.ONE)),BigDecimal.ZERO,"actor",start));
        assertThrows(IllegalArgumentException.class,()->p.reconcile(List.of(one,
            receipt("m2",start.plusSeconds(60),start.plusSeconds(120),"different",BigDecimal.ONE)),BigDecimal.ZERO,"actor",start));
        assertThrows(IllegalArgumentException.class,()->receipt("m3",start,start.plusSeconds(20),"unit",
            new BigDecimal("1.0000001")));
        assertThrows(IllegalArgumentException.class,()->p.reconcile(List.of(
            receipt("m3",start,start.plusSeconds(10),"unit",new BigDecimal("999999999999.999999")),
            receipt("m4",start.plusSeconds(10),start.plusSeconds(20),"unit",new BigDecimal("0.000001"))),
            BigDecimal.ZERO,"actor",start));
    }
    private static BigDecimal nullTicket(){return new BigDecimal("40.600000");}
    @Test void finalizationNeverAcceptsSelfApprovalOrUnresolvedDifference(){
        var start=Instant.parse("2026-11-17T00:00:00Z");
        var one=receipt("m1",start,start.plusSeconds(60),"unit",new BigDecimal("25.000000"));
        var snapshot=new CustodyReconciliationPolicy().reconcile(List.of(one),new BigDecimal("24.000000"),
            "reconciler",start.plusSeconds(120));
        var service=new CustodyTicketFinalizationPolicy();
        var good=new CustodyTicketFinalizationPolicy.ApprovalEvidence("ticket","digest","instance","task",
            "action","reviewer",start.plusSeconds(180),true);
        assertThrows(IllegalStateException.class,()->service.requireFinalizable("ticket","digest",snapshot,
            "issuer","reconciler",true,false,good,start.plusSeconds(200)));
        assertDoesNotThrow(()->service.requireFinalizable("ticket","digest",snapshot,
            "issuer","reconciler",false,true,good,start.plusSeconds(200)));
        var self=new CustodyTicketFinalizationPolicy.ApprovalEvidence("ticket","digest","instance","task",
            "action","issuer",start.plusSeconds(180),true);
        assertThrows(IllegalStateException.class,()->service.requireFinalizable("ticket","digest",snapshot,
            "issuer","reconciler",false,true,self,start.plusSeconds(200)));
    }
    @Test void correctionRequiresFinalizedUniqueCurrentHeadAndLiveVersion(){
        var current=UUID.randomUUID();
        var c=new CustodyTicketCorrection("old","new",current,"reason","evidence","actor",2,Instant.now());
        var policy=new CustodyTicketCorrectionPolicy();
        assertDoesNotThrow(()->policy.requireCorrectable(c,"old",2,true,false,Set.of("old")));
        assertThrows(IllegalStateException.class,()->policy.requireCorrectable(c,"old",1,true,false,Set.of("old")));
        assertThrows(IllegalStateException.class,()->policy.requireCorrectable(c,"old",2,true,true,Set.of("old")));
        assertThrows(IllegalStateException.class,()->policy.requireCorrectable(c,"old",2,false,false,Set.of("old")));
        assertThrows(IllegalStateException.class,()->policy.requireCorrectable(c,"old",2,true,false,Set.of("old","new")));
    }
}
