/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedCredentialRoutingDataSource
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence
 *
 * @Description : Selects a private credential pool before a physical transaction and restores the ordinary route.
 *
 */
package dz.sh.hidra.platform.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dz.sh.hidra.platform.configuration.ProtectedPublicationDatabaseProperties;
import java.sql.*;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public final class ProtectedCredentialRoutingDataSource extends AbstractDataSource implements AutoCloseable {
    private static final ThreadLocal<Route> CURRENT=new ThreadLocal<>();
    public record Identity(String ordinaryUser,String publisherUser) {
        @Override public String toString(){return "Identity[database principals]";}
    }
    private final DataSource ordinary;
    private final ProtectedPublicationDatabaseProperties properties;
    private DataSource publisher;
    private boolean closed;
    public ProtectedCredentialRoutingDataSource(DataSource ordinary,ProtectedPublicationDatabaseProperties properties) {
        this.ordinary=Objects.requireNonNull(ordinary);this.properties=Objects.requireNonNull(properties);
    }
    ProtectedCredentialRoutingDataSource(DataSource ordinary,DataSource publisher,ProtectedPublicationDatabaseProperties properties) {
        this(ordinary,properties);this.publisher=Objects.requireNonNull(publisher);
    }
    private static IllegalStateException unavailable(){return new IllegalStateException("Protected database credential unavailable");}
    private static String principal(Connection c) throws SQLException {
        try(var s=c.createStatement();var r=s.executeQuery("SELECT session_user,current_user,current_database()")) {
            if(!r.next())throw unavailable();
            String session=r.getString(1);
            if(session==null || !session.equals(r.getString(2)) || r.getString(3)==null)throw unavailable();
            return session;
        }
    }
    private synchronized Identity verify() throws SQLException {
        if(closed || !properties.enabled())throw unavailable();
        try(var c=ordinary.getConnection()) {
            String normal=principal(c);
            if(normal.equals(properties.username()))throw unavailable();
            String url=c.getMetaData().getURL();
            if(url==null || !url.startsWith("jdbc:postgresql:")
                || url.matches("(?i).*([?&])(password|user|sslpassword)=.*"))throw unavailable();
            if(publisher==null) {
                var config=new HikariConfig();
                config.setJdbcUrl(url);config.setUsername(properties.username());config.setPassword(properties.password());
                config.setPoolName("hidra-protected-publication");config.setMaximumPoolSize(properties.maximumPoolSize());
                config.setMinimumIdle(0);config.setConnectionTimeout(10000);
                publisher=new HikariDataSource(config);
            }
            try(var p=publisher.getConnection()) {
                if(!url.equals(p.getMetaData().getURL()) || !properties.username().equals(principal(p)))throw unavailable();
                try(var s=p.prepareStatement("""
                    SELECT NOT EXISTS(SELECT 1 FROM pg_catalog.pg_roles r
                      WHERE (r.rolname=current_user OR pg_catalog.pg_has_role(current_user,r.oid,'MEMBER'))
                        AND (r.rolsuper OR r.rolbypassrls OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication
                            OR r.rolname IN ('pg_execute_server_program','pg_read_server_files','pg_write_server_files')))
                      AND NOT pg_catalog.pg_has_role(?::name,current_user,'MEMBER')
                    """)) {
                    s.setString(1,normal);
                    try(var r=s.executeQuery()){if(!r.next() || !r.getBoolean(1))throw unavailable();}
                }
                return new Identity(normal,properties.username());
            }
        }
    }
    Route open() {
        if(CURRENT.get()!=null || TransactionSynchronizationManager.isActualTransactionActive()
            || TransactionSynchronizationManager.isSynchronizationActive())throw unavailable();
        try {
            Identity identity=verify();
            Route route=new Route(this,identity);CURRENT.set(route);return route;
        }catch(SQLException error){throw new IllegalStateException("Protected database credential verification failed");}
    }
    Identity identity() {
        Route r=CURRENT.get();
        if(r==null || r.source!=this || !r.active || r.thread!=Thread.currentThread())throw unavailable();
        return r.identity;
    }
    private synchronized DataSource selected() throws SQLException {
        if(closed)throw new SQLException("Datasource closed");
        Route r=CURRENT.get();
        if(r==null)return ordinary;
        if(r.source!=this || !r.active || r.thread!=Thread.currentThread() || publisher==null)throw unavailable();
        return publisher;
    }
    @Override public Connection getConnection() throws SQLException {return selected().getConnection();}
    @Override public Connection getConnection(String user,String password) throws SQLException {
        if(CURRENT.get()!=null)throw unavailable();
        return selected().getConnection(user,password);
    }
    @Override public <T> T unwrap(Class<T> type) throws SQLException {
        if(type.isInstance(this))return type.cast(this);
        if(CURRENT.get()!=null)throw unavailable();
        if(type.isInstance(ordinary))return type.cast(ordinary);
        return ordinary.unwrap(type);
    }
    @Override public boolean isWrapperFor(Class<?> type) throws SQLException {
        return type.isInstance(this) || (CURRENT.get()==null && (type.isInstance(ordinary) || ordinary.isWrapperFor(type)));
    }
    @Override public synchronized void close() throws Exception {
        if(CURRENT.get()!=null)throw unavailable();
        if(closed)return;closed=true;
        try {if(publisher instanceof AutoCloseable c)c.close();}
        finally {
            DataSource target=ordinary;
            while(target instanceof DelegatingDataSource d && !(target instanceof AutoCloseable))target=d.getTargetDataSource();
            if(target instanceof AutoCloseable c)c.close();
        }
    }
    static final class Route implements AutoCloseable {
        private final ProtectedCredentialRoutingDataSource source;
        private final Identity identity;
        private final Thread thread=Thread.currentThread();
        private boolean active=true;
        private Route(ProtectedCredentialRoutingDataSource source,Identity identity){this.source=source;this.identity=identity;}
        @Override public void close() {
            if(thread!=Thread.currentThread() || CURRENT.get()!=this)throw unavailable();
            active=false;CURRENT.remove();
        }
    }
}
