/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyProductPublicationWorkflowTargetLookupTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.integration
 *
 * @Description : Preserves governed product publication evidence under exact owner admission.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.integration;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.*;
import dz.sh.hidra.modules.custody.application.service.*;
import dz.sh.hidra.modules.custody.application.port.out.ProductRevisionPublicationRepositoryPort;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class CustodyProductPublicationWorkflowTargetLookupTest {
    private final ProductRevisionPublicationUseCase owner=mock(ProductRevisionPublicationUseCase.class);
    private final CustodyProductPublicationWorkflowTargetLookup lookup=new CustodyProductPublicationWorkflowTargetLookup(owner);
    private final String digest="a".repeat(64);
    @Test void advertisesOnlyExactProductTarget(){assertEquals("custody",lookup.module());assertEquals(Set.of("PRODUCT_SOURCE_PUBLICATION"),lookup.targetTypeCodes());}
    @Test void unsupportedMalformedAndNullTargetsNeverReachOwner(){assertTrue(lookup.eligibleTarget("GAS_FLUID_REVISION",digest).isEmpty());assertTrue(lookup.eligibleTarget("PRODUCT_SOURCE_PUBLICATION",null).isEmpty());assertTrue(lookup.eligibleTarget("PRODUCT_SOURCE_PUBLICATION","A".repeat(64)).isEmpty());verifyNoInteractions(owner);}
    @Test void delegatesAuthorizationAndReturnsOnlyBoundDigestLabel(){when(owner.candidateForWorkflow(digest)).thenReturn(Optional.of(digest));var t=lookup.eligibleTarget("PRODUCT_SOURCE_PUBLICATION",digest).orElseThrow();assertEquals(digest,t.id());assertEquals(digest,t.code());assertEquals("Product source publication",t.label());verify(owner).candidateForWorkflow(digest);}
    @Test void ownerDenialCannotBecomeExistenceAndSubstitutionRejects(){when(owner.candidateForWorkflow(digest)).thenReturn(Optional.empty());assertTrue(lookup.eligibleTarget("PRODUCT_SOURCE_PUBLICATION",digest).isEmpty());when(owner.candidateForWorkflow(digest)).thenReturn(Optional.of("b".repeat(64)));assertThrows(IllegalStateException.class,()->lookup.eligibleTarget("PRODUCT_SOURCE_PUBLICATION",digest));}
    private ProductRevisionPublicationApplicationService service(ProtectedTransactionExecutor executor,PlatformTransactionManager manager){return spy(new ProductRevisionPublicationApplicationService(mock(ProtectedProductPublicationAdmissionCoordinator.class),mock(ProductRevisionPublicationRepositoryPort.class),executor,manager));}
    @Test void ordinaryWorkflowTransactionIsSuspendedForProtectedRead(){
        var manager=new LocalManager();var executor=mock(ProtectedTransactionExecutor.class);var service=service(executor,manager);
        doAnswer(i->{assertFalse(TransactionSynchronizationManager.isActualTransactionActive());assertEquals(Kind.READ_CANDIDATE_FOR_WORKFLOW,((Command)i.getArgument(0)).kind());return new Result(null,digest,null,null,null,null,List.of(),null,false,false);}).when(service).execute(any());
        new TransactionTemplate(manager).executeWithoutResult(status->{assertTrue(TransactionSynchronizationManager.isActualTransactionActive());assertEquals(Optional.of(digest),service.candidateForWorkflow(digest));assertTrue(TransactionSynchronizationManager.isActualTransactionActive());});
        verify(executor).requireNoProtectedContext();
    }
    @Test void nestingRejectionOccursBeforeSuspensionOrOwnerRead(){
        var executor=mock(ProtectedTransactionExecutor.class);var manager=mock(PlatformTransactionManager.class);var service=service(executor,manager);
        doThrow(new IllegalStateException("protected nesting")).when(executor).requireNoProtectedContext();assertThrows(IllegalStateException.class,()->service.candidateForWorkflow(digest));verifyNoInteractions(manager);verify(service,never()).execute(any());
    }
    static class LocalManager extends AbstractPlatformTransactionManager {
        private final ThreadLocal<Object> active=new ThreadLocal<>();
        protected Object doGetTransaction(){return Optional.ofNullable(active.get()).orElseGet(Object::new);}
        protected boolean isExistingTransaction(Object tx){return active.get()==tx;}
        protected void doBegin(Object tx,TransactionDefinition d){active.set(tx);}
        protected Object doSuspend(Object tx){active.remove();return tx;}
        protected void doResume(Object tx,Object suspended){active.set(suspended);}
        protected void doCommit(DefaultTransactionStatus s){}
        protected void doRollback(DefaultTransactionStatus s){}
        protected void doCleanupAfterCompletion(Object tx){active.remove();}
    }
}
