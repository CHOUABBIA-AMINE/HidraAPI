/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentClosureSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.semantic
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.incident.semantic;

import dz.sh.hidra.modules.incident.domain.model.IncidentClosure;
import dz.sh.hidra.modules.incident.domain.value.IncidentStatus;
import dz.sh.hidra.modules.incident.domain.exception.InvalidIncidentValueException;
import dz.sh.hidra.modules.incident.application.port.out.IncidentClosureEvidencePort;
import dz.sh.hidra.modules.incident.infrastructure.persistence.adapter.JpaIncidentClosureRepositoryAdapter;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.identity.application.contract.incident.IncidentActorContract;
import dz.sh.hidra.modules.workflow.application.contract.incident.IncidentWorkflowContract;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class IncidentClosureSemanticRemediationTest {
    final IncidentClosureJpaRepository rows=mock(IncidentClosureJpaRepository.class);
    final IncidentJpaRepository parents=mock(IncidentJpaRepository.class);
    final IncidentClosureEvidencePort evidence=mock(IncidentClosureEvidencePort.class);
    final IncidentActorContract actors=mock(IncidentActorContract.class);
    final IncidentWorkflowContract workflow=mock(IncidentWorkflowContract.class);
    final EntityManager em=mock(EntityManager.class);
    final JpaIncidentClosureRepositoryAdapter adapter=new JpaIncidentClosureRepositoryAdapter(rows,parents,evidence,actors,workflow,em);
    IncidentClosure closure(String summary,boolean resolution,boolean reviewed) {return new IncidentClosure("closure","incident",summary,resolution,reviewed,false,false,"actor","Untrusted",Instant.now(),null);}
    void parent() {
        var at=IncidentSemanticRemediationTest.AT;
        var parent=IncidentSemanticRemediationTest.incident(IncidentStatus.RESOLVED,at,at,null,"actor","Actor");
        when(parents.findByIdForUpdate("incident")).thenReturn(Optional.of(IncidentPersistenceMapper.toEntity(parent)));
        when(actors.currentActor(any())).thenReturn(new IncidentActorContract.Actor("actor","Canonical Closer"));
        when(evidence.inspect(parent)).thenReturn(new IncidentClosureEvidencePort.Evidence(true,true,true,false,false,false,false,false));
    }
    @Test void requiredSummaryAndConfirmationsFailBeforePersistence() {
        assertThrows(InvalidIncidentValueException.class,() -> closure(" ",true,true));
        assertThrows(InvalidIncidentValueException.class,() -> closure("Closed",false,true));
        assertThrows(InvalidIncidentValueException.class,() -> closure("Closed",true,false));
    }
    @Test void missingParentAndDuplicateClosureAreDenied() {
        when(parents.findByIdForUpdate("incident")).thenReturn(Optional.empty());assertThrows(IllegalArgumentException.class,() -> adapter.save(closure("Closed",true,true)));
        parent();when(rows.existsByIncidentId("incident")).thenReturn(true);assertThrows(IllegalArgumentException.class,() -> adapter.save(closure("Closed",true,true)));verifyNoInteractions(em);
    }
    @Test void resolutionAndPolicyRequiredEvidenceAreRealPreconditions() {
        parent();when(evidence.inspect(any())).thenReturn(new IncidentClosureEvidencePort.Evidence(false,true,true,false,false,false,false,false));
        assertThrows(IllegalArgumentException.class,() -> adapter.save(closure("Closed",true,true)));
        when(evidence.inspect(any())).thenReturn(new IncidentClosureEvidencePort.Evidence(true,false,true,false,false,false,false,false));
        assertThrows(IllegalArgumentException.class,() -> adapter.save(closure("Closed",true,true)));verifyNoInteractions(em);
    }
    @Test void requiredWorkflowCannotBeSatisfiedByAnUnverifiedReference() {
        parent();when(evidence.inspect(any())).thenReturn(new IncidentClosureEvidencePort.Evidence(true,true,true,true,false,false,false,false));
        assertThrows(SecurityException.class,() -> adapter.save(closure("Closed",true,true)));verifyNoInteractions(em);
    }
    @Test void canonicalCloserAndServerTimeAreUsedForAtomicParentUpdate() {
        parent();var update=mock(Query.class);when(em.createNativeQuery(anyString())).thenReturn(update);when(update.setParameter(anyString(),any())).thenReturn(update);when(update.executeUpdate()).thenReturn(1);
        var result=adapter.save(closure("Closed",true,true));assertEquals("Canonical Closer",result.closedByActorNameSnapshot());
        verify(em).persist(any());verify(em).flush();verify(update).setParameter("at",result.closedAt());verify(update).setParameter("id","incident");verify(em).refresh(any());
    }
}
