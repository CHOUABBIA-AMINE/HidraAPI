/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseClosureSemanticRemediationTest
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

import dz.sh.hidra.modules.hse.application.port.out.HseClosureLifecyclePort;
import dz.sh.hidra.modules.hse.domain.model.HseClosure;
import dz.sh.hidra.modules.hse.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.hse.infrastructure.persistence.entity.*;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.hse.domain.value.HseCaseStatus;
import dz.sh.hidra.modules.identity.application.contract.hse.HseActorContract;
import dz.sh.hidra.modules.workflow.application.contract.hse.HseWorkflowReferenceContract;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class HseClosureSemanticRemediationTest {
    final HseCaseJpaRepository cases=mock(HseCaseJpaRepository.class);
    final HseClosureJpaRepository closures=mock(HseClosureJpaRepository.class);
    final HseCaseStatusHistoryJpaRepository histories=mock(HseCaseStatusHistoryJpaRepository.class);
    final HseActorContract actors=mock(HseActorContract.class);
    final HseWorkflowReferenceContract workflows=mock(HseWorkflowReferenceContract.class);
    final EntityManager em=mock(EntityManager.class);
    final JpaHseClosureLifecycleAdapter lifecycle=new JpaHseClosureLifecycleAdapter(cases,closures,histories,actors,workflows,em);
    HseClosure request(String actor,String workflow) {return new HseClosure("closure","case","summary",true,true,true,false,actor,"untrusted",Instant.EPOCH,workflow);}
    void valid() {
        when(cases.findByIdForUpdate("case")).thenReturn(Optional.of(HsePersistenceMapper.toEntity(HseCaseSemanticRemediationTest.parent(HseCaseStatus.OPEN))));
        when(actors.currentActor(any())).thenReturn(new HseActorContract.Actor("actor","Canonical Actor"));
    }
    @Test void standaloneRepositoryDelegatesToAuthoritativeLifecycle() {
        var port=mock(HseClosureLifecyclePort.class);var requested=request("actor",null);when(port.close(requested)).thenReturn(requested);
        var adapter=new JpaHseClosureRepositoryAdapter(closures,port);assertEquals(requested,adapter.save(requested));verify(closures,never()).save(any());
    }
    @Test void successfulClosureUsesCanonicalActorAndServerTimestampAndPreservesOptionalRegulatoryReview() {
        valid();var result=lifecycle.close(request("actor",null));assertEquals("Canonical Actor",result.closedByDisplayNameSnapshot());assertFalse(result.regulatoryReviewed());
        assertTrue(result.closedAt().isAfter(Instant.EPOCH));assertEquals(0,result.closedAt().getNano()%1000);
        verify(em).persist(any(HseClosureJpaEntity.class));verify(em).persist(any(HseCaseStatusHistoryJpaEntity.class));verify(cases).saveAndFlush(any());
    }
    @Test void actorSpoofingAndWorkflowMismatchCannotWriteEvidence() {
        valid();assertThrows(SecurityException.class,() -> lifecycle.close(request("spoof",null)));verify(em,never()).persist(any());
        assertThrows(IllegalArgumentException.class,() -> lifecycle.close(request("actor","wrong")));verify(em,never()).persist(any());
    }
    @Test void replayedEvidenceIdCannotBeMerged() {
        valid();when(closures.existsById("closure")).thenReturn(true);assertThrows(IllegalArgumentException.class,() -> lifecycle.close(request("actor",null)));verify(em,never()).persist(any());
    }
}
