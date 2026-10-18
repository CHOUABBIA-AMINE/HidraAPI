/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcStationProtectedFacilityOwnershipAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Qualifies actual owner generations, admission, isolation and ordinary writers.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.contract.station.StationProtectedFacilityOwnershipContract;
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

class JdbcStationProtectedFacilityOwnershipAdapterTest {
    private final DataSource source=mock(DataSource.class);
    private final JdbcTemplate jdbc=mock(JdbcTemplate.class);
    private JdbcStationProtectedFacilityOwnershipAdapter adapter;
    private ConnectionHolder holder;
    @BeforeEach void setup() {
        when(jdbc.getDataSource()).thenReturn(source);
        when(jdbc.queryForObject("SELECT pg_catalog.array_to_json(pg_catalog.current_schemas(true))::pg_catalog.text",String.class)).thenReturn("[\"pg_catalog\",\"public\"]");
        adapter=new JdbcStationProtectedFacilityOwnershipAdapter(jdbc);clearInvocations(jdbc);
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
        for(String id:new String[]{null,""," ","x".repeat(81)})
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
        assertThrows(IllegalStateException.class,()->adapter.read(new StationProtectedFacilityOwnershipContract.Admission() {}));
        assertTrue(holder.isRollbackOnly());verifyNoInteractions(jdbc);
    }
    @Test void nullAdmissionFailsClosed() {
        transaction();assertThrows(IllegalStateException.class,()->adapter.read(null));
        assertTrue(holder.isRollbackOnly());verifyNoInteractions(jdbc);
    }
    @Test void failedFirstFencePropagatesAndNeverAcquiresTheTail() {
        transaction();var failure=new DataAccessResourceFailureException("synthetic conflict");
        doThrow(failure).when(jdbc).execute("LOCK TABLE public.hidra_topology_facility IN SHARE ROW EXCLUSIVE MODE NOWAIT");
        assertSame(failure,assertThrows(DataAccessResourceFailureException.class,()->adapter.acquire("owner")));
        assertTrue(holder.isRollbackOnly());
        verify(jdbc,never()).execute("LOCK TABLE public.hidra_topology_facility_owner_generation IN ACCESS SHARE MODE NOWAIT");
    }
    @Test void unavailableCatalogAfterBothFencesRollsBackWithoutEvidence() {
        transaction();assertThrows(IllegalStateException.class,()->adapter.acquire(" owner "));
        var order=inOrder(jdbc);
        order.verify(jdbc).execute("LOCK TABLE public.hidra_topology_facility IN SHARE ROW EXCLUSIVE MODE NOWAIT");
        order.verify(jdbc).execute("LOCK TABLE public.hidra_topology_facility_owner_generation IN ACCESS SHARE MODE NOWAIT");
        assertTrue(holder.isRollbackOnly());
    }
    @Test void scalarConstructionIsValidatedAndDoesNotMintAnAdmission() {
        assertNotNull(new StationProtectedFacilityOwnershipContract.Contribution("facility","owner",1,10,20));
        assertThrows(IllegalArgumentException.class,()->new StationProtectedFacilityOwnershipContract.Contribution("facility"," owner",1,10,20));
        assertFalse(StationProtectedFacilityOwnershipContract.Admission.class.isRecord());
    }
}
