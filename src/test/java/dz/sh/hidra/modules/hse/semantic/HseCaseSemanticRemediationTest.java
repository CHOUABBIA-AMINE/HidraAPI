/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.semantic
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.hse.semantic;

import dz.sh.hidra.modules.hse.application.service.HseApplicationService;
import dz.sh.hidra.modules.hse.application.command.CloseHseCaseCommand;
import dz.sh.hidra.modules.hse.application.port.out.*;
import dz.sh.hidra.modules.hse.domain.model.*;
import dz.sh.hidra.modules.hse.domain.service.HseCaseClosureGuard;
import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class HseCaseSemanticRemediationTest {
    static HseCase parent(HseCaseStatus status) {
        var at=Instant.parse("2026-10-08T10:00:00Z");
        return new HseCase("case","CASE","Title",null,"type","severity",null,status,HseCaseSourceType.MANUAL,
                "neutral",null,null,null,null,null,null,null,null,at,null,null,null,null,null,null,null,null,null,at,at);
    }
    @Test void existingGuardRejectsMissingClosedCancelledAndMissingAttestations() {
        var guard=new HseCaseClosureGuard();
        assertThrows(RuntimeException.class,() -> guard.ensureCanClose(null,true,true,true));
        for(var status:new HseCaseStatus[]{HseCaseStatus.CLOSED,HseCaseStatus.CANCELLED}) assertThrows(RuntimeException.class,() -> guard.ensureCanClose(parent(status),true,true,true));
        assertThrows(RuntimeException.class,() -> guard.ensureCanClose(parent(HseCaseStatus.OPEN),false,true,true));
        assertThrows(RuntimeException.class,() -> guard.ensureCanClose(parent(HseCaseStatus.OPEN),true,false,true));
        assertThrows(RuntimeException.class,() -> guard.ensureCanClose(parent(HseCaseStatus.OPEN),true,true,false));
        assertDoesNotThrow(() -> guard.ensureCanClose(parent(HseCaseStatus.OPEN),true,true,true));
    }
    @Test void unknownParentIsRejectedBeforeLifecyclePortIsCalled() {
        var cases=mock(HseCaseRepositoryPort.class);var lifecycle=mock(HseClosureLifecyclePort.class);
        when(cases.findByIdForUpdate("missing")).thenReturn(Optional.empty());
        var service=new HseApplicationService(cases,mock(HseCorrectivePreventiveActionRepositoryPort.class),lifecycle);
        assertThrows(RuntimeException.class,() -> service.closeHseCase(new CloseHseCaseCommand("missing","summary",true,true,true,false,"actor","supplied",null)));
        verify(lifecycle,never()).close(any());
    }
    @Test void closedCopyPreservesNeutralReferencesAndUnrelatedFields() {
        var parent=parent(HseCaseStatus.OPEN);var at=Instant.now();var closed=parent.closedAt(at);
        assertEquals(HseCaseStatus.CLOSED,closed.status());assertEquals(at,closed.closedAt());assertEquals(at,closed.updatedAt());
        assertEquals(parent.incidentReferenceId(),closed.incidentReferenceId());assertEquals(parent.reportedAt(),closed.reportedAt());
    }
}
