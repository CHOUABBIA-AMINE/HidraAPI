/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaLifecycleGuardTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Test
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.service
 * @Description : T17 finite-state and independent-review negative and positive invariants.
 */
package dz.sh.hidra.modules.hse.domain.service;

import dz.sh.hidra.modules.hse.domain.value.CapaStatus;
import dz.sh.hidra.modules.hse.domain.model.HseCapaVerification;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class HseCapaLifecycleGuardTest {
    private final HseCapaLifecycleGuard guard=new HseCapaLifecycleGuard();
    @Test void requiresRealWorkflowApprovalBeforeProgress(){
        assertThrows(IllegalStateException.class,()->guard.next(CapaStatus.PROPOSED,"APPROVE",false,
                false,false,null));
        assertEquals(CapaStatus.APPROVED,guard.next(CapaStatus.PROPOSED,"APPROVE",false,true,false,null));
        assertEquals(CapaStatus.IN_PROGRESS,guard.next(CapaStatus.APPROVED,"ASSIGN",false,false,false,null));
        assertThrows(IllegalStateException.class,()->guard.next(CapaStatus.IN_PROGRESS,"COMPLETE",false,false,false,null));
    }
    @Test void rejectsSelfAndOwnerReview(){
        assertThrows(SecurityException.class,()->guard.independent("proposer","owner","proposer"));
        assertThrows(SecurityException.class,()->guard.independent("proposer","owner","owner"));
        assertDoesNotThrow(()->guard.independent("proposer","owner","reviewer"));
        assertThrows(IllegalStateException.class,()->guard.next(CapaStatus.COMPLETED,"VERIFY",true,
                false,false,HseCapaVerification.Finding.EFFECTIVE));
    }
    @Test void onlyEffectiveFindingsCanVerify(){
        assertEquals(CapaStatus.COMPLETED,guard.next(CapaStatus.COMPLETED,"VERIFY",true,false,
                true,HseCapaVerification.Finding.INDETERMINATE));
        assertEquals(CapaStatus.COMPLETED,guard.next(CapaStatus.COMPLETED,"VERIFY",true,false,
                true,HseCapaVerification.Finding.INEFFECTIVE));
        assertEquals(CapaStatus.VERIFIED,guard.next(CapaStatus.COMPLETED,"VERIFY",true,false,
                true,HseCapaVerification.Finding.EFFECTIVE));
    }
    @Test void missingVerifiedCapasOrOwnerApprovalFailClosure(){
        assertThrows(IllegalStateException.class,()->guard.mayCloseCase(List.of(),true,true,true));
        assertThrows(IllegalStateException.class,()->guard.mayCloseCase(List.of(CapaStatus.COMPLETED),true,true,true));
        assertThrows(IllegalStateException.class,()->guard.mayCloseCase(List.of(CapaStatus.VERIFIED),false,true,true));
        assertThrows(IllegalStateException.class,()->guard.mayCloseCase(List.of(CapaStatus.VERIFIED),true,true,false));
        assertDoesNotThrow(()->guard.mayCloseCase(List.of(CapaStatus.VERIFIED),true,true,true));
    }
    @Test void malformedTransitionsCannotBecomeTerminal(){
        assertThrows(IllegalStateException.class,()->guard.next(CapaStatus.PROPOSED,"CLOSE",
                true,true,true,HseCapaVerification.Finding.EFFECTIVE));
        assertThrows(IllegalArgumentException.class,()->guard.next(CapaStatus.VERIFIED,"UNKNOWN",
                true,true,true,HseCapaVerification.Finding.EFFECTIVE));
    }
}
