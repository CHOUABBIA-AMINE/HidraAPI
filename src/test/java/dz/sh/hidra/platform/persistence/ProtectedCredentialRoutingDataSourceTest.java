/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedCredentialRoutingDataSourceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence
 *
 * @Description : Checks credential route confinement, restoration and denial without replacing runtime authorization.
 *
 */
package dz.sh.hidra.platform.persistence;
import dz.sh.hidra.platform.configuration.ProtectedPublicationDatabaseProperties;
import java.sql.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProtectedCredentialRoutingDataSourceTest {
    static class Pool extends AbstractDataSource implements AutoCloseable {
        final String user,url;final AtomicInteger connections=new AtomicInteger(),closed=new AtomicInteger();
        Pool(String user,String url){this.user=user;this.url=url;}
        @Override public Connection getConnection() throws SQLException {
            connections.incrementAndGet();
            var c=mock(Connection.class);var metadata=mock(DatabaseMetaData.class);
            when(metadata.getURL()).thenReturn(url);when(metadata.getUserName()).thenReturn(user);when(c.getMetaData()).thenReturn(metadata);
            var statement=mock(Statement.class);var row=mock(ResultSet.class);
            when(c.createStatement()).thenReturn(statement);when(statement.executeQuery(anyString())).thenReturn(row);
            when(row.next()).thenReturn(true,false);when(row.getString(1)).thenReturn(user);when(row.getString(2)).thenReturn(user);when(row.getString(3)).thenReturn("synthetic");
            var prepared=mock(PreparedStatement.class);var checked=mock(ResultSet.class);
            when(c.prepareStatement(anyString())).thenReturn(prepared);when(prepared.executeQuery()).thenReturn(checked);
            when(checked.next()).thenReturn(true,false);when(checked.getBoolean(1)).thenReturn(true);
            return c;
        }
        @Override public Connection getConnection(String u,String p) throws SQLException{return getConnection();}
        @Override public void close(){closed.incrementAndGet();}
    }
    final Pool ordinary=new Pool("ordinary","jdbc:postgresql://synthetic/db");
    final Pool publisher=new Pool("publisher","jdbc:postgresql://synthetic/db");
    ProtectedCredentialRoutingDataSource source(){return new ProtectedCredentialRoutingDataSource(ordinary,publisher,
        new ProtectedPublicationDatabaseProperties(true,"publisher","synthetic",2));}
    @Test void ordinaryAccessRetainsOriginalDataSource() throws Exception {
        try(var source=source();var c=source.getConnection()){assertEquals("ordinary",c.getMetaData().getUserName());}
        assertEquals(0,publisher.connections.get());
    }
    @Test void disabledPublisherRouteFailsBeforeConnection() throws Exception {
        try(var s=new ProtectedCredentialRoutingDataSource(ordinary,publisher,new ProtectedPublicationDatabaseProperties(false,null,null,2))){
            assertThrows(IllegalStateException.class,s::open);assertEquals(0,ordinary.connections.get());assertEquals(0,publisher.connections.get());
        }
    }
    @Test void publisherRouteUsesSeparatePrincipalAndSameEndpoint() throws Exception {
        try(var source=source()){
            try(var route=source.open();var c=source.getConnection()){
                assertEquals("publisher",c.getMetaData().getUserName());assertEquals("ordinary",source.identity().ordinaryUser());
            }
            try(var c=source.getConnection()){assertEquals("ordinary",c.getMetaData().getUserName());}
            var wrong=new Pool("publisher","jdbc:postgresql://other/db");
            try(var s=new ProtectedCredentialRoutingDataSource(ordinary,wrong,new ProtectedPublicationDatabaseProperties(true,"publisher","synthetic",2))){
                assertThrows(IllegalStateException.class,s::open);
            }
            try(var s=new ProtectedCredentialRoutingDataSource(ordinary,publisher,new ProtectedPublicationDatabaseProperties(true,"ordinary","synthetic",2))){
                assertThrows(IllegalStateException.class,s::open);
            }
        }
    }
    @Test void nestedOrAmbientRoutesAreRejected() throws Exception {
        try(var s=source()){
            try(var route=s.open()){assertThrows(IllegalStateException.class,s::open);}
            TransactionSynchronizationManager.setActualTransactionActive(true);
            try{assertThrows(IllegalStateException.class,s::open);}
            finally{TransactionSynchronizationManager.setActualTransactionActive(false);}
        }
    }
    @Test void routeIsThreadConfinedAndClearedOnAllOutcomes() throws Exception {
        try(var s=source()){
            var workers=Executors.newSingleThreadExecutor();
            try(var route=s.open()){
                assertEquals("ordinary",workers.submit(()->{try(var c=s.getConnection()){return c.getMetaData().getUserName();}}).get());
                assertThrows(ExecutionException.class,()->workers.submit(()->s.identity()).get());
            }finally{workers.shutdownNow();}
            assertThrows(IllegalStateException.class,()->{try(var route=s.open()){throw new IllegalStateException("synthetic");}});
            try(var c=s.getConnection()){assertEquals("ordinary",c.getMetaData().getUserName());}
        }
    }
    @Test void explicitCredentialAndUnwrapEscapesAreRejected() throws Exception {
        try(var s=source();var route=s.open()){
            assertThrows(IllegalStateException.class,()->s.getConnection("other","synthetic"));
            assertThrows(IllegalStateException.class,()->s.unwrap(Pool.class));
            assertSame(s,s.unwrap(ProtectedCredentialRoutingDataSource.class));
        }
    }
    @Test void closeReleasesBothOwnedPoolsExactlyOnce() throws Exception {
        var s=source();s.close();s.close();assertEquals(1,ordinary.closed.get());assertEquals(1,publisher.closed.get());
        assertThrows(SQLException.class,s::getConnection);
    }
}
