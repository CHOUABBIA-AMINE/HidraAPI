/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcOrganizationProtectedPartyEligibilityAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Qualifies actual owner generations, admission, isolation and ordinary writers.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.contract.organization.OrganizationProtectedPartyEligibilityContract;
import java.sql.Connection;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JdbcOrganizationProtectedPartyEligibilityAdapterTest {
    private final DataSource source=mock(DataSource.class);
    private final JdbcTemplate jdbc=mock(JdbcTemplate.class);
    private JdbcOrganizationProtectedPartyEligibilityAdapter adapter;
    private ConnectionHolder holder;
    @BeforeEach void setup() {
        when(jdbc.getDataSource()).thenReturn(source);
        when(jdbc.queryForObject("SELECT pg_catalog.array_to_json(pg_catalog.current_schemas(true))::pg_catalog.text",String.class)).thenReturn("[\"pg_catalog\",\"public\"]");
        adapter=new JdbcOrganizationProtectedPartyEligibilityAdapter(jdbc);clearInvocations(jdbc);
    }
    @AfterEach void cleanup() {
        if(TransactionSynchronizationManager.hasResource(source)) TransactionSynchronizationManager.unbindResource(source);
        TransactionSynchronizationManager.clear();
    }
    private void transaction() {
        TransactionSynchronizationManager.setActualTransactionActive(true);
        TransactionSynchronizationManager.initSynchronization();
        holder=new ConnectionHolder(mock(Connection.class));
        TransactionSynchronizationManager.bindResource(source,holder);
    }
    @Test void invalidIdentitiesNeverQueryTheDatabase() {
        for(String id:new String[]{null,""," "," owner ","\u2003owner","own\u0000er","x".repeat(81)})
            assertThrows(IllegalArgumentException.class,()->adapter.acquire(id));
        verifyNoInteractions(jdbc);
    }
    @Test void acquisitionRequiresAnActualBoundTransaction() {
        assertThrows(IllegalStateException.class,()->adapter.acquire("owner"));verifyNoInteractions(jdbc);
    }
    @Test void readOnlyContextFailsBeforeAnyOwnerQuery() {
        transaction();TransactionSynchronizationManager.setCurrentTransactionReadOnly(true);
        assertThrows(IllegalStateException.class,()->adapter.acquire("owner"));
        assertTrue(holder.isRollbackOnly());verifyNoInteractions(jdbc);
    }
    @Test void fabricatedAdmissionCannotReachOwnerRowsAndMarksRollback() {
        transaction();
        assertThrows(IllegalStateException.class,()->adapter.read(new OrganizationProtectedPartyEligibilityContract.Admission() {}));
        assertTrue(holder.isRollbackOnly());verifyNoInteractions(jdbc);
    }
    @Test void nullAdmissionFailsClosed() {
        transaction();assertThrows(IllegalStateException.class,()->adapter.read(null));
        assertTrue(holder.isRollbackOnly());verifyNoInteractions(jdbc);
    }
    @Test void failedFirstFencePropagatesAndNeverAcquiresTheTail() {
        transaction();var failure=new DataAccessResourceFailureException("synthetic conflict");
        doThrow(failure).when(jdbc).execute("LOCK TABLE public.hidra_party_party IN SHARE ROW EXCLUSIVE MODE NOWAIT");
        assertSame(failure,assertThrows(DataAccessResourceFailureException.class,()->adapter.acquire("owner")));
        assertTrue(holder.isRollbackOnly());
        verify(jdbc,never()).execute("LOCK TABLE public.hidra_party_eligibility_generation IN ACCESS SHARE MODE NOWAIT");
    }
    @Test void unavailableCatalogAfterBothFencesRollsBackWithoutEvidence() {
        transaction();assertThrows(IllegalStateException.class,()->adapter.acquire("owner"));
        var order=inOrder(jdbc);
        order.verify(jdbc).execute("LOCK TABLE public.hidra_party_party IN SHARE ROW EXCLUSIVE MODE NOWAIT");
        order.verify(jdbc).execute("LOCK TABLE public.hidra_party_eligibility_generation IN ACCESS SHARE MODE NOWAIT");
        assertTrue(holder.isRollbackOnly());
    }
    @Test void scalarConstructionIsValidatedAndDoesNotMintAnAdmission() {
        assertNotNull(new OrganizationProtectedPartyEligibilityContract.Contribution("owner",1,10,20));
        assertThrows(IllegalArgumentException.class,()->new OrganizationProtectedPartyEligibilityContract.Contribution(" owner",1,10,20));
        assertFalse(OrganizationProtectedPartyEligibilityContract.Admission.class.isRecord());
    }
    @Test void finalFenceExportRejectsForgedAdmissionWithoutReadingOwnerRows() {
        transaction();
        assertThrows(IllegalStateException.class,()->adapter.finalFences(new OrganizationProtectedPartyEligibilityContract.Admission(){}));
        assertTrue(holder.isRollbackOnly());verifyNoInteractions(jdbc);
    }

    @Test void currentCheckRejectsFabricatedOrNullTicketsBeforeQueries() {
        for(var admission:new OrganizationProtectedPartyEligibilityContract.Admission[]{null,new OrganizationProtectedPartyEligibilityContract.Admission(){}}) {
            transaction();
            assertThrows(IllegalStateException.class,()->adapter.requireCurrent(admission));
            assertTrue(holder.isRollbackOnly());verifyNoInteractions(jdbc);
            TransactionSynchronizationManager.unbindResource(source);TransactionSynchronizationManager.clear();
        }
    }
    @Test void scalarCoordinatesNeverImplementThePrivateAdmissionType() {
        var scalar=new OrganizationProtectedPartyEligibilityContract.Contribution("owner",1,1,1);
        assertFalse(OrganizationProtectedPartyEligibilityContract.Admission.class.isInstance(scalar));
        for(long generation:new long[]{0,-1}) assertThrows(IllegalArgumentException.class,()->new OrganizationProtectedPartyEligibilityContract.Contribution("owner",generation,1,1));
        for(int pid:new int[]{0,-1}) assertThrows(IllegalArgumentException.class,()->new OrganizationProtectedPartyEligibilityContract.Contribution("owner",1,pid,1));
        assertThrows(IllegalArgumentException.class,()->new OrganizationProtectedPartyEligibilityContract.Contribution("owner",1,1,0));
        var ticket=java.util.Arrays.stream(JdbcOrganizationProtectedPartyEligibilityAdapter.class.getDeclaredClasses()).filter(c->c.getSimpleName().equals("Ticket")).findFirst().orElseThrow();
        assertTrue(java.util.Arrays.stream(ticket.getDeclaredConstructors()).allMatch(c->java.lang.reflect.Modifier.isPrivate(c.getModifiers())));
    }

}
