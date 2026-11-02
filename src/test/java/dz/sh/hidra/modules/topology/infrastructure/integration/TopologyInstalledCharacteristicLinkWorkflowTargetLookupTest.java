package dz.sh.hidra.modules.topology.infrastructure.integration;

import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase;
import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.Command;
import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.Result;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import dz.sh.hidra.modules.topology.domain.service.InstalledCharacteristicLinkApprovalTargetDigester;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup.ProtectedRead;
import java.time.Instant;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.security.access.AccessDeniedException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class TopologyInstalledCharacteristicLinkWorkflowTargetLookupTest {
    private static InstalledCharacteristicLink draft() {
        return new InstalledCharacteristicLink("link-1",7,"party-1","unit-1",
                "source-1","r3","a".repeat(64),InstalledCharacteristicLink.Kind.COMPRESSOR_MAP,
                Instant.parse("2026-11-01T12:00:00Z"),null,
                Instant.parse("2026-11-01T09:00:00Z"),"synthetic",null);
    }
    private final InstalledCharacteristicLinkUseCase nativeOwner=mock(InstalledCharacteristicLinkUseCase.class);
    // NOT_SUPPORTED does not open a JDBC connection; this manager exercises Spring's
    // real empty synchronization scaffold without Docker or database fixtures.
    private final DataSourceTransactionManager manager=
            new DataSourceTransactionManager(mock(DataSource.class));
    private final TopologyInstalledCharacteristicLinkWorkflowTargetLookup lookup=
            new TopologyInstalledCharacteristicLinkWorkflowTargetLookup(nativeOwner,manager);
    private final InstalledCharacteristicLink value=draft();
    private final String digest=InstalledCharacteristicLinkApprovalTargetDigester.digest(value);
    private final String locator=TopologyInstalledCharacteristicLinkWorkflowTargetLookup.locator(
            "party-1","unit-1","link-1",7);

    @Test void exactAuthorizedRevisionResolvesOnlyThroughNativeOwner() {
        when(nativeOwner.execute(any())).thenReturn(new Result(value,List.of(),false,null));
        var found=lookup.eligibleTarget(TopologyInstalledCharacteristicLinkWorkflowTargetLookup.TYPE,digest,locator).orElseThrow();
        assertEquals(digest,found.id());assertEquals(locator,found.code());
        verify(nativeOwner).execute(argThat(c->c.operation()==InstalledCharacteristicLinkUseCase.Operation.READ_REVISION
                && c.companyPartyId().equals("party-1") && c.installedUnitId().equals("unit-1")
                && c.linkId().equals("link-1") && c.revision()==7));
    }
    @Test void detachedNativeReadHasNoSpringSynchronizationAndDoesNotLeakOne() {
        when(nativeOwner.execute(any())).thenAnswer(inv->{
            assertFalse(TransactionSynchronizationManager.isActualTransactionActive());
            assertFalse(TransactionSynchronizationManager.isSynchronizationActive());
            return new Result(value,List.of(),false,null);
        });
        assertFalse(TransactionSynchronizationManager.isSynchronizationActive());
        assertTrue(lookup.eligibleTarget(
                TopologyInstalledCharacteristicLinkWorkflowTargetLookup.TYPE,digest,locator).isPresent());
        assertFalse(TransactionSynchronizationManager.isSynchronizationActive());
        verify(nativeOwner,times(1)).execute(any());
    }
    @Test void noDigestOnlyLookupOrUntrustedLocatorBypass() {
        assertTrue(lookup.eligibleTarget(TopologyInstalledCharacteristicLinkWorkflowTargetLookup.TYPE,digest).isEmpty());
        assertTrue(lookup.eligibleTarget(TopologyInstalledCharacteristicLinkWorkflowTargetLookup.TYPE,digest,"bad").isEmpty());
        assertTrue(lookup.eligibleTarget("WRONG",digest,locator).isEmpty());
        verifyNoInteractions(nativeOwner);
    }
    @Test void nativeDenialAndUnavailableSourceAreNonDisclosing() {
        when(nativeOwner.execute(any())).thenThrow(new AccessDeniedException("native owner denied"));
        assertTrue(lookup.eligibleTarget(TopologyInstalledCharacteristicLinkWorkflowTargetLookup.TYPE,digest,locator).isEmpty());
        assertFalse(lookup.protectedReadAllowed(TopologyInstalledCharacteristicLinkWorkflowTargetLookup.TYPE,digest,locator,ProtectedRead.REVISION));
    }
    @Test void wrongActualRevisionOrShaCannotResolve() {
        when(nativeOwner.execute(any())).thenReturn(new Result(value,List.of(),false,null));
        assertTrue(lookup.eligibleTarget(TopologyInstalledCharacteristicLinkWorkflowTargetLookup.TYPE,"b".repeat(64),locator).isEmpty());
        String other=TopologyInstalledCharacteristicLinkWorkflowTargetLookup.locator("party-1","unit-1","link-1",8);
        assertTrue(lookup.eligibleTarget(TopologyInstalledCharacteristicLinkWorkflowTargetLookup.TYPE,digest,other).isEmpty());
    }
    @Test void historyRequiresIndependentNativeHistoryAuthorization() {
        when(nativeOwner.execute(any())).thenAnswer(inv->{
            Command c=inv.getArgument(0);
            if(c.operation()==InstalledCharacteristicLinkUseCase.Operation.LIST_HISTORY)
                throw new AccessDeniedException("history denied");
            return new Result(value,List.of(),false,null);
        });
        assertTrue(lookup.protectedReadAllowed(TopologyInstalledCharacteristicLinkWorkflowTargetLookup.TYPE,
                digest,locator,ProtectedRead.REVISION));
        assertFalse(lookup.protectedReadAllowed(TopologyInstalledCharacteristicLinkWorkflowTargetLookup.TYPE,
                digest,locator,ProtectedRead.HISTORY));
    }
    @Test void canonicalLocatorDoesNotAliasMalformedOrAmbiguousInput() {
        when(nativeOwner.execute(any())).thenReturn(new Result(value,List.of(),false,null));
        assertTrue(lookup.eligibleTarget(TopologyInstalledCharacteristicLinkWorkflowTargetLookup.TYPE,digest,
                locator.replace("v1.","v2.")).isEmpty());
        assertTrue(lookup.eligibleTarget(TopologyInstalledCharacteristicLinkWorkflowTargetLookup.TYPE,digest,
                locator.replace(".7",".07")).isEmpty());
        assertNotEquals(locator,TopologyInstalledCharacteristicLinkWorkflowTargetLookup.locator(
                "party-1","unit-2","link-1",7));
    }
}
