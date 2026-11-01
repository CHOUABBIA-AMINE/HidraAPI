package dz.sh.hidra.modules.workflow.infrastructure.query;

import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import dz.sh.hidra.modules.workflow.application.service.WorkflowExecutionOwnership;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowCatalogEntryJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowInstanceJpaEntity;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaWorkflowTopologyProtectedReadTest {
    private static final String TYPE="INSTALLED_CHARACTERISTIC_LINK_REVISION";
    private static final String DIGEST="a".repeat(64);
    private static final String LOCATOR="v1.opaque.locator";
    private static final Instant AT=Instant.parse("2026-11-01T10:00:00Z");
    private final EntityManager entityManager=mock(EntityManager.class);
    private final WorkflowOwnedTargetLookup owner=mock(WorkflowOwnedTargetLookup.class);
    private final JpaWorkflowQueryAdapter query=new JpaWorkflowQueryAdapter(entityManager,
            mock(WorkflowExecutionOwnership.class),List.of(owner),mock(JdbcTemplate.class));
    private void fixture() {
        when(entityManager.find(WorkflowInstanceJpaEntity.class,"instance")).thenReturn(
            new WorkflowInstanceJpaEntity("instance","def",1,"purpose","topology","type",
                DIGEST,LOCATOR,"Synthetic Topology",WorkflowInstanceStatus.COMPLETED,
                null,"actor","actor","Synthetic Actor",null,
                AT.minusSeconds(5),AT,null,null,AT.minusSeconds(5),AT));
        when(entityManager.find(WorkflowCatalogEntryJpaEntity.class,"type")).thenReturn(
            new WorkflowCatalogEntryJpaEntity("type","WORKFLOW_TARGET_TYPE",TYPE,
                true,0,true,AT,AT));
        when(owner.module()).thenReturn("topology");
        when(owner.targetTypeCodes()).thenReturn(Set.of(TYPE));
    }
    @Test void neverDisclosesTopologyInstanceWithoutOwnerAuthorization() {
        fixture();
        assertThrows(NoSuchElementException.class,()->query.instance("instance"));
        verify(owner).protectedReadAllowed(TYPE,DIGEST,LOCATOR,WorkflowOwnedTargetLookup.ProtectedRead.REVISION);
    }
    @Test void nativeDenialOnHistoryIsNotConvertedToAHistoryLeak() {
        fixture();
        when(owner.protectedReadAllowed(TYPE,DIGEST,LOCATOR,
                WorkflowOwnedTargetLookup.ProtectedRead.HISTORY)).thenThrow(new IllegalStateException("source denied"));
        assertThrows(NoSuchElementException.class,()->query.timeline("instance"));
    }
    @Test void nullOrWrongTargetCatalogCannotBypassTheOwnerGuard() {
        fixture();
        when(entityManager.find(WorkflowCatalogEntryJpaEntity.class,"type")).thenReturn(null);
        assertThrows(NoSuchElementException.class,()->query.instance("instance"));
        verify(owner,never()).protectedReadAllowed(any(),any(),any(),any());
    }
}
