/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentInvestigationGuardTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Test
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.service
 * @Description : T16 domain Incident investigator/reviewer and terminal guards.
 */
package dz.sh.hidra.modules.incident.application.service;

import dz.sh.hidra.modules.incident.domain.service.IncidentInvestigationGuard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IncidentInvestigationGuardTest {
    final IncidentInvestigationGuard subject=new IncidentInvestigationGuard();
    @Test void validIndependentHumanJourneyAdvancesOnlyAnEligibleOpenIncident() {
        assertDoesNotThrow(()->subject.require("OPEN","NEW","ASSIGN",null,"operator",false,false));
        assertDoesNotThrow(()->subject.require("ASSIGNED","ASSIGNED","EVIDENCE","operator","operator",false,false));
        assertDoesNotThrow(()->subject.require("IN_PROGRESS","INVESTIGATING","ACTION","operator","operator",true,false));
        assertDoesNotThrow(()->subject.require("IN_PROGRESS","INVESTIGATING","SUBMIT","operator","operator",true,true));
        assertDoesNotThrow(()->subject.require("IN_PROGRESS","SUBMITTED","REVIEW","operator","reviewer",true,true));
        assertDoesNotThrow(()->subject.require("RESOLVED","APPROVED","CLOSE","operator","reviewer",true,true));
    }
    @Test void sameActorCannotSelfReviewOrCloseAndMissingEvidenceBlocksResolution() {
        assertThrows(IllegalStateException.class,()->subject.require("IN_PROGRESS","SUBMITTED","REVIEW","operator","operator",true,true));
        assertThrows(IllegalStateException.class,()->subject.require("IN_PROGRESS","INVESTIGATING","SUBMIT","operator","operator",false,true));
        assertThrows(IllegalStateException.class,()->subject.require("RESOLVED","APPROVED","CLOSE","operator","operator",true,true));
    }
    @Test void terminalAndStaleStatesCannotBeMutatedOrRelabelled() {
        for(String state:new String[]{"CLOSED","CANCELLED","MERGED","DRAFT"})
            assertThrows(IllegalStateException.class,()->subject.require(state,"ASSIGNED","ACTION","operator","operator",true,true));
        assertThrows(IllegalStateException.class,()->subject.require("OPEN","SUBMITTED","ASSIGN","operator","reviewer",true,true));
        assertThrows(IllegalArgumentException.class,()->subject.require("OPEN","NEW","ADMIN_OVERRIDE",null,"operator",false,false));
    }
}
