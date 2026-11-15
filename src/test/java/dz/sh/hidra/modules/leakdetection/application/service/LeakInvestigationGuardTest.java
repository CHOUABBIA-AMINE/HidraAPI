/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakInvestigationGuardTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Test
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.service
 * @Description : Pure Leak suspected-case disposition and independent reviewer transition cases.
 */
package dz.sh.hidra.modules.leakdetection.application.service;

import dz.sh.hidra.modules.leakdetection.domain.service.LeakInvestigationGuard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LeakInvestigationGuardTest {
    final LeakInvestigationGuard subject=new LeakInvestigationGuard();
    @Test void humanInvestigatorCanSubmitEvidenceBackedSuspectedCase() {
        assertDoesNotThrow(()->subject.require("OPEN","NEW","ASSIGN",null,"operator",false,false));
        assertDoesNotThrow(()->subject.require("OPEN","ASSIGNED","EVIDENCE","operator","operator",false,false));
        assertDoesNotThrow(()->subject.require("UNDER_INVESTIGATION","INVESTIGATING","ACTION","operator","operator",true,false));
        assertDoesNotThrow(()->subject.require("UNDER_INVESTIGATION","INVESTIGATING","SUBMIT","operator","operator",true,true));
        assertDoesNotThrow(()->subject.require("UNDER_INVESTIGATION","SUBMITTED","REVIEW","operator","reviewer",true,true));
    }
    @Test void noSelfReviewOrEvidenceFreeDisposition() {
        assertThrows(IllegalStateException.class,()->subject.require("UNDER_INVESTIGATION","SUBMITTED","REVIEW","operator","operator",true,true));
        assertThrows(IllegalStateException.class,()->subject.require("UNDER_INVESTIGATION","INVESTIGATING","SUBMIT","operator","operator",true,false));
        assertThrows(IllegalStateException.class,()->subject.require("OPEN","ASSIGNED","ACTION","operator","reviewer",true,true));
    }
    @Test void terminalCaseIsImmutableAcrossEveryInvestigationOperation() {
        for(String parent:new String[]{"CLOSED","VERIFIED_LEAK","DISMISSED","ESCALATED"})
            assertThrows(IllegalStateException.class,()->subject.require(parent,"SUBMITTED","REVIEW","operator","reviewer",true,true));
        assertThrows(IllegalArgumentException.class,()->subject.require("OPEN","NEW","TRIGGER_ACTUATOR",null,"operator",false,false));
    }
}
