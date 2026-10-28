/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcInstalledUnitPositionOwnershipAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Checks structural rejection without asserting database authority.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.InstalledUnitPositionOwnershipPort;
import javax.sql.DataSource;
import java.sql.Connection;
import java.lang.reflect.Modifier;
import java.util.*;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JdbcInstalledUnitPositionOwnershipAdapterTest {
    private final DataSource source=mock(DataSource.class);
    private final JdbcTemplate jdbc=mock(JdbcTemplate.class);
    private JdbcInstalledUnitPositionOwnershipAdapter adapter;
    @BeforeEach void setup(){when(jdbc.getDataSource()).thenReturn(source);adapter=new JdbcInstalledUnitPositionOwnershipAdapter(jdbc);clearInvocations(jdbc);}
    @AfterEach void clear(){if(TransactionSynchronizationManager.hasResource(source))TransactionSynchronizationManager.unbindResource(source);TransactionSynchronizationManager.clear();}
    @Test void malformedIdentitiesNeverQueryOwnerRows(){
        for(String id:Arrays.asList(null,""," "," position","position ","x".repeat(81)))
            assertThrows(IllegalArgumentException.class,()->adapter.acquire(id));
        verifyNoInteractions(jdbc);
    }
    @Test void missingPhysicalTransactionRejectsBeforeCatalogOrLocks(){
        assertThrows(IllegalStateException.class,()->adapter.acquire("position"));verifyNoInteractions(jdbc);
    }
    @Test void readOnlyBoundTransactionRejectsBeforeSql(){
        TransactionSynchronizationManager.initSynchronization();TransactionSynchronizationManager.setActualTransactionActive(true);
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(true);
        var holder=new ConnectionHolder(mock(Connection.class));TransactionSynchronizationManager.bindResource(source,holder);
        assertThrows(IllegalStateException.class,()->adapter.acquire("position"));assertTrue(holder.isRollbackOnly());verifyNoInteractions(jdbc);
    }
    @Test void fabricatedTicketsCannotReachAnyOwnerLifecycleQuery(){
        TransactionSynchronizationManager.initSynchronization();TransactionSynchronizationManager.setActualTransactionActive(true);
        var holder=new ConnectionHolder(mock(Connection.class));TransactionSynchronizationManager.bindResource(source,holder);
        var fake=new InstalledUnitPositionOwnershipPort.Admission(){};
        assertThrows(IllegalStateException.class,()->adapter.read(fake));
        assertThrows(IllegalStateException.class,()->adapter.requireCurrent(fake));
        assertThrows(IllegalStateException.class,()->adapter.finalFences(fake));
        assertTrue(holder.isRollbackOnly());verifyNoInteractions(jdbc);
    }
    @Test void scalarObservationCannotBecomeAnAdmissionOrEligibilityBoolean(){
        var value=new InstalledUnitPositionOwnershipPort.Contribution("position","type","facility","company","PLANNED","SUSPENDED","STATION",1,2,3,"reader");
        assertFalse(InstalledUnitPositionOwnershipPort.Admission.class.isInstance(value));
        assertFalse(value.toString().contains("company"));
        assertThrows(IllegalArgumentException.class,()->new InstalledUnitPositionOwnershipPort.Contribution("p","t","f","c","ACTIVE","ACTIVE","STATION",0,2,3,"r"));
        assertTrue(Arrays.stream(InstalledUnitPositionOwnershipPort.Contribution.class.getDeclaredMethods()).noneMatch(m->m.getReturnType()==boolean.class && m.getName().startsWith("isEligible")));
    }
    @Test void privateTicketHasNoPublicFactoryOrConstructor(){
        var ticket=Arrays.stream(JdbcInstalledUnitPositionOwnershipAdapter.class.getDeclaredClasses()).filter(c->c.getSimpleName().equals("Ticket")).findFirst().orElseThrow();
        assertEquals(0,ticket.getConstructors().length);
        for(var constructor:ticket.getDeclaredConstructors())assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        for(var field:ticket.getDeclaredFields())if(!field.isSynthetic())assertTrue(Modifier.isPrivate(field.getModifiers()));
    }
}
