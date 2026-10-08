/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessmentSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.semantic
 *
 * @Description : Validates owner-controlled IntegrityAssessment references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.integrity.semantic;

import dz.sh.hidra.modules.integrity.domain.model.IntegrityAssessment;
import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.identity.application.contract.integrity.IntegrityAssessmentActorReferenceContract;
import dz.sh.hidra.modules.workflow.application.contract.integrity.IntegrityAssessmentWorkflowReferenceContract;
import java.time.Instant;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class IntegrityAssessmentSemanticRemediationTest {
    final IntegrityProgramJpaRepository programId=mock(IntegrityProgramJpaRepository.class);
    IntegrityAssessmentReferenceValidation validation(IntegrityAssessmentActorReferenceContract actors,IntegrityAssessmentWorkflowReferenceContract workflow) {return new IntegrityAssessmentReferenceValidation(actors,workflow,programId);}
    IntegrityAssessmentReferenceValidation permissive() {return validation((id,at)->true,(id,target)->true);}
    static IntegrityAssessment model(Map<String,String> references) {
        try {
            var components=IntegrityAssessment.class.getRecordComponents(); var types=new Class<?>[components.length]; var values=new Object[components.length];
            for(int i=0;i<components.length;i++) {var c=components[i]; types[i]=c.getType();
                if(c.getType()==String.class) values[i]=references.containsKey(c.getName())?references.get(c.getName()):switch(c.getName()) {case "id","assessmentNumber","assessmentTypeId" -> c.getName().equals("id")?"target":"required"; case "title" -> "Title"; default -> null;};
                else if(c.getType()==Instant.class) values[i]=Instant.parse("2026-10-08T00:00:00Z");
                else if(c.getType().isEnum()) values[i]=Enum.valueOf(c.getType().asSubclass(Enum.class),"DRAFT");
            }
            return IntegrityAssessment.class.getDeclaredConstructor(types).newInstance(values);
        } catch(InvocationTargetException e) {throw (RuntimeException)e.getCause();} catch(ReflectiveOperationException e) {throw new AssertionError(e);}
    }
    @Test void nullOptionalReferencesRemainAllowed() {assertDoesNotThrow(() -> permissive().validate(model(Map.of()),null));}
    @Test void missingLocalReferencesFailClosed() {
        assertThrows(InvalidIntegrityValueException.class,() -> permissive().validate(model(Map.of("programId","missing")),null));
    }
    @Test void populatedActorsMustResolveThroughIdentity() {
        assertThrows(InvalidIntegrityValueException.class,() -> validation((id,at)->false,(id,target)->true).validate(model(Map.of("assessedByActorId","missing")),null));
        assertThrows(InvalidIntegrityValueException.class,() -> validation((id,at)->false,(id,target)->true).validate(model(Map.of("reviewedByActorId","missing")),null));
        assertThrows(InvalidIntegrityValueException.class,() -> validation((id,at)->false,(id,target)->true).validate(model(Map.of("approvedByActorId","missing")),null));
    }
    @Test void wrongWorkflowContextFailsClosed() {assertThrows(InvalidIntegrityValueException.class,() -> validation((id,at)->true,(id,target)->false).validate(model(Map.of("workflowInstanceId","workflow")),null));}
    @Test void ownerFailurePropagates() {assertThrows(IllegalStateException.class,() -> validation((id,at)->{throw new IllegalStateException("owner unavailable");},(id,target)->true).validate(model(Map.of("assessedByActorId","actor")),null));}
    @Test void unchangedHistoricalActorAndWorkflowArePreserved() {
        var history=model(Map.of("assessedByActorId","historical","workflowInstanceId","historical-workflow"));
        assertDoesNotThrow(() -> validation((id,at)->false,(id,target)->false).validate(history,history));
    }
    @Test void changedHistoricalActorRequiresFreshEligibility() {
        assertThrows(InvalidIntegrityValueException.class,() -> validation((id,at)->false,(id,target)->true).validate(model(Map.of("assessedByActorId","new-actor")),model(Map.of("assessedByActorId","historical"))));
    }
    @Test void adapterDeniesBeforePersisting() {
        var repository=mock(IntegrityAssessmentJpaRepository.class); when(repository.findByIdForUpdate("target")).thenReturn(Optional.empty());
        var adapter=new JpaIntegrityAssessmentRepositoryAdapter(repository,validation((id,at)->false,(id,target)->true));
        assertThrows(InvalidIntegrityValueException.class,() -> adapter.save(model(Map.of("assessedByActorId","missing"))));
        verify(repository,never()).saveAndFlush(any());
    }
    @Test void existingLocalReferenceIsAccepted() {when(programId.existsById("local")).thenReturn(true);assertDoesNotThrow(() -> permissive().validate(model(Map.of("programId","local")),null));}
}
