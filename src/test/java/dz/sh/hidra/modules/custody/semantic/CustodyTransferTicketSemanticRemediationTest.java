/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTransferTicketSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.semantic
 *
 * @Description : Validates owner-controlled CustodyTransferTicket references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.custody.semantic;

import dz.sh.hidra.modules.custody.domain.model.CustodyTransferTicket;
import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.identity.application.contract.custody.CustodyTransferTicketActorReferenceContract;
import dz.sh.hidra.modules.workflow.application.contract.custody.CustodyTransferTicketWorkflowReferenceContract;
import dz.sh.hidra.modules.audit.application.contract.custody.CustodyTicketAuditReferenceContract;
import java.time.Instant;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class CustodyTransferTicketSemanticRemediationTest {
    final CustodyBatchJpaRepository batchId=mock(CustodyBatchJpaRepository.class);
    final CustodyQuantityCalculationJpaRepository quantityCalculationId=mock(CustodyQuantityCalculationJpaRepository.class);
    CustodyTransferTicketReferenceValidation validation(CustodyTransferTicketActorReferenceContract actors,CustodyTransferTicketWorkflowReferenceContract workflow,CustodyTicketAuditReferenceContract audit) {return new CustodyTransferTicketReferenceValidation(actors,workflow,audit,batchId,quantityCalculationId);}
    CustodyTransferTicketReferenceValidation permissive() {return validation((id,at)->true,(id,target)->true,(id,target)->true);}
    static CustodyTransferTicket model(Map<String,String> references) {
        try {
            var components=CustodyTransferTicket.class.getRecordComponents(); var types=new Class<?>[components.length]; var values=new Object[components.length];
            for(int i=0;i<components.length;i++) {var c=components[i]; types[i]=c.getType();
                if(c.getType()==String.class) values[i]=references.containsKey(c.getName())?references.get(c.getName()):switch(c.getName()) {case "id","ticketNumber","measurementPeriodId","agreementId","transferPointId" -> c.getName().equals("id")?"target":"required"; case "title" -> "Title"; default -> null;};
                else if(c.getType()==Instant.class) values[i]=Instant.parse("2026-10-08T00:00:00Z");
                else if(c.getType().isEnum()) values[i]=Enum.valueOf(c.getType().asSubclass(Enum.class),"DRAFT");
            }
            return CustodyTransferTicket.class.getDeclaredConstructor(types).newInstance(values);
        } catch(InvocationTargetException e) {throw (RuntimeException)e.getCause();} catch(ReflectiveOperationException e) {throw new AssertionError(e);}
    }
    @Test void nullOptionalReferencesRemainAllowed() {assertDoesNotThrow(() -> permissive().validate(model(Map.of()),null));}
    @Test void missingLocalReferencesFailClosed() {
        assertThrows(InvalidCustodyValueException.class,() -> permissive().validate(model(Map.of("batchId","missing")),null));
        assertThrows(InvalidCustodyValueException.class,() -> permissive().validate(model(Map.of("quantityCalculationId","missing")),null));
    }
    @Test void populatedActorsMustResolveThroughIdentity() {
        assertThrows(InvalidCustodyValueException.class,() -> validation((id,at)->false,(id,target)->true,(id,target)->true).validate(model(Map.of("issuedByActorId","missing")),null));
        assertThrows(InvalidCustodyValueException.class,() -> validation((id,at)->false,(id,target)->true,(id,target)->true).validate(model(Map.of("approvedByActorId","missing")),null));
    }
    @Test void wrongWorkflowContextFailsClosed() {assertThrows(InvalidCustodyValueException.class,() -> validation((id,at)->true,(id,target)->false,(id,target)->true).validate(model(Map.of("workflowInstanceId","workflow")),null));}
    @Test void ownerFailurePropagates() {assertThrows(IllegalStateException.class,() -> validation((id,at)->{throw new IllegalStateException("owner unavailable");},(id,target)->true,(id,target)->true).validate(model(Map.of("issuedByActorId","actor")),null));}
    @Test void unchangedHistoricalActorAndWorkflowArePreserved() {
        var history=model(Map.of("issuedByActorId","historical","workflowInstanceId","historical-workflow"));
        assertDoesNotThrow(() -> validation((id,at)->false,(id,target)->false,(id,target)->true).validate(history,history));
    }
    @Test void changedHistoricalActorRequiresFreshEligibility() {
        assertThrows(InvalidCustodyValueException.class,() -> validation((id,at)->false,(id,target)->true,(id,target)->true).validate(model(Map.of("issuedByActorId","new-actor")),model(Map.of("issuedByActorId","historical"))));
    }
    @Test void adapterDeniesBeforePersisting() {
        var repository=mock(CustodyTransferTicketJpaRepository.class); when(repository.findByIdForUpdate("target")).thenReturn(Optional.empty());
        var adapter=new JpaCustodyTransferTicketRepositoryAdapter(repository,validation((id,at)->false,(id,target)->true,(id,target)->true));
        assertThrows(InvalidCustodyValueException.class,() -> adapter.save(model(Map.of("issuedByActorId","missing"))));
        verify(repository,never()).saveAndFlush(any());
    }
    @Test void auditMustBelongToExactTicket() {
        assertThrows(InvalidCustodyValueException.class,() -> validation((id,at)->true,(id,target)->true,(id,target)->false).validate(model(Map.of("auditReferenceId","audit")),null));
    }
    @Test void existingLocalReferenceIsAccepted() {when(batchId.existsById("local")).thenReturn(true);assertDoesNotThrow(() -> permissive().validate(model(Map.of("batchId","local")),null));}
}
