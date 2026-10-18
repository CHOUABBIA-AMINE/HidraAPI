/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedAdmissionLifecycleTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence
 *
 * @Description : Verifies protected transaction state, resource boundaries and completion outcomes with simulated JDBC behavior.
 *
 */
package dz.sh.hidra.platform.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.persistence.*;

import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;
import org.springframework.orm.jpa.DefaultJpaDialect;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import javax.sql.DataSource;

/** Exercises public lifecycle behavior with a simulated JDBC/JPA provider; PostgreSQL proof is separate. */
class ProtectedAdmissionLifecycleTest {
    Connection raw;
    ProtectedOperationDataSource ds;
    ProtectedJpaTransactionManager manager;
    ProtectedTransactionExecutor executor;
    JdbcTemplate jdbc;
    AtomicBoolean active;
    AtomicInteger commits, rollbacks;
    EntityTransaction providerTransaction;
    AtomicReference<Connection> providerConnection;
    final AtomicLong databaseXid = new AtomicLong(2);
    final AtomicInteger databasePid = new AtomicInteger(1);

    @BeforeEach
    void provider() throws Exception {
        raw = mock(Connection.class);
        DataSource target = mock(DataSource.class);
        when(target.getConnection()).thenReturn(raw);
        when(raw.getAutoCommit()).thenReturn(false);
        when(raw.getTransactionIsolation()).thenReturn(Connection.TRANSACTION_READ_COMMITTED);
        when(raw.isReadOnly()).thenReturn(false);
        ds = new ProtectedOperationDataSource(target);
        when(raw.createStatement()).thenAnswer(i -> statement(false, null));
        when(raw.prepareStatement(anyString())).thenAnswer(i -> statement(true, i.getArgument(0)));
        active = new AtomicBoolean();
        commits = new AtomicInteger();
        rollbacks = new AtomicInteger();
        var entity = mock(EntityManager.class);
        var transaction = mock(EntityTransaction.class);
        providerTransaction = transaction;
        var factory = mock(EntityManagerFactory.class);
        when(factory.createEntityManager(anyMap())).thenReturn(entity);
        when(factory.createEntityManager()).thenReturn(entity);
        when(entity.getTransaction()).thenReturn(transaction);
        when(entity.isOpen()).thenReturn(true);
        when(transaction.isActive()).thenAnswer(i -> active.get());
        AtomicReference<Connection> connection = new AtomicReference<>();
        providerConnection = connection;
        doAnswer(
                        i -> {
                            active.set(true);
                            return null;
                        })
                .when(transaction)
                .begin();
        doAnswer(
                        i -> {
                            connection.get().commit();
                            active.set(false);
                            return null;
                        })
                .when(transaction)
                .commit();
        doAnswer(
                        i -> {
                            connection.get().rollback();
                            active.set(false);
                            return null;
                        })
                .when(transaction)
                .rollback();
        doAnswer(
                        i -> {
                            commits.incrementAndGet();
                            return null;
                        })
                .when(raw)
                .commit();
        doAnswer(
                        i -> {
                            rollbacks.incrementAndGet();
                            return null;
                        })
                .when(raw)
                .rollback();
        manager = new ProtectedJpaTransactionManager(factory, ds);
        manager.setJpaDialect(
                new DefaultJpaDialect() {
                    @Override
                    public Object beginTransaction(EntityManager em, TransactionDefinition d) {
                        em.getTransaction().begin();
                        return null;
                    }

                    @Override
                    public ConnectionHandle getJdbcConnection(EntityManager em, boolean ro)
                            throws SQLException {
                        var c = ds.getConnection();
                        connection.set(c);
                        return new SimpleConnectionHandle(c);
                    }
                });
        executor = new ProtectedTransactionExecutor(manager, ds);
        jdbc = new JdbcTemplate(ds);
    }

    Statement statement(boolean prepared, String sql) throws Exception {
        Statement s = prepared ? mock(PreparedStatement.class) : mock(Statement.class);
        when(s.getConnection()).thenReturn(raw);
        if (prepared) when(((PreparedStatement) s).executeQuery()).thenAnswer(i -> rows(sql));
        else when(s.executeQuery(anyString())).thenAnswer(i -> rows(i.getArgument(0)));
        return s;
    }

    ResultSet rows(String sql) throws Exception {
        ResultSet rs = mock(ResultSet.class);
        ResultSetMetaData meta = mock(ResultSetMetaData.class);
        when(rs.getMetaData()).thenReturn(meta);
        when(rs.next()).thenReturn(true, false);
        if (sql.contains("empty")) when(rs.next()).thenReturn(false);
        if (sql.contains("pg_backend_pid")) {
            when(meta.getColumnCount()).thenReturn(4);
            when(rs.getInt(1)).thenAnswer(i -> databasePid.get());
            when(rs.getLong(2)).thenAnswer(i -> databaseXid.get());
            when(rs.getString(3)).thenReturn("read committed");
            when(rs.getString(4)).thenReturn("off");
            String[] names = {"pid", "xid", "isolation", "readonly"};
            Object[] values = {1, 2L, "read committed", "off"};
            for (int i = 0; i < 4; i++) {
                when(meta.getColumnLabel(i + 1)).thenReturn(names[i]);
                when(rs.getObject(i + 1)).thenReturn(values[i]);
            }
        } else {
            when(meta.getColumnCount()).thenReturn(1);
            when(meta.getColumnLabel(1)).thenReturn("value");
            when(rs.getString(1)).thenReturn("ok");
            when(rs.getObject(1)).thenReturn("ok");
        }
        return rs;
    }

    String run(java.util.function.Supplier<String> work, Runnable flush, Runnable guard) {
        return executor.execute(work, flush, guard);
    }

    void guard() {
        jdbc.queryForObject("SELECT 'ok'", String.class);
    }

    void rejected(Runnable work) {
        assertThatThrownBy(work::run)
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        assertThat(commits).hasValue(0);
    }

    @Test void admissionMustBeFirstWorkAction() {
        rejected(()->run(()->{jdbc.queryForObject("SELECT 'ok'",String.class);executor.beginAdmission();return "bad";},()->{},this::guard));
    }
    @Test void admissionRejectsDmlCteSelectIntoAndBatchBeforeDispatch() {
        for(String sql:List.of("INSERT INTO x VALUES(1)","WITH x AS (DELETE FROM y RETURNING 1) SELECT 1","SELECT 1 INTO x","SELECT 1 /* comment */ INTO x"))
            assertThat(ProtectedOperationDataSource.admissionSql(sql)).isFalse();
        rejected(()->run(()->{executor.beginAdmission();jdbc.update("UPDATE x SET a=1");return "bad";},()->{},this::guard));
    }
    @Test void admissionRejectsDirtyJpaFlush() {
        rejected(()->run(()->{executor.beginAdmission();executor.sealAdmission();return "ok";},()->jdbc.update("INSERT INTO x VALUES(1)"),this::guard));
    }
    @Test void admissionCannotBeNestedReopenedOrSealedTwice() {
        rejected(()->run(()->{executor.beginAdmission();executor.beginAdmission();return "bad";},()->{},this::guard));
    }
    @Test void caughtJavaAdmissionFailurePoisonsPhysicalAttempt() {
        rejected(()->run(()->{executor.beginAdmission();executor.rejectAdmission();return "bad";},()->{},this::guard));
    }
    @Test void ordinaryProtectedCallbacksRetainExistingBehavior() {
        assertThat(run(()->{jdbc.update("INSERT INTO x VALUES(1)");return "ok";},()->{},this::guard)).isEqualTo("ok");assertThat(commits).hasValue(1);
    }
    @Test void admissionCannotEscapeThreadConnectionOrTransaction() throws Exception {
        rejected(()->run(()->{executor.beginAdmission();databaseXid.incrementAndGet();jdbc.queryForObject("SELECT 'ok'",String.class);return "bad";},()->{},this::guard));
    }
    @Test void readAdmissionRetainsFinalGuardAndCommitRules() {
        assertThat(run(()->{executor.beginAdmission();jdbc.queryForObject("SELECT 'ok'",String.class);executor.sealAdmission();executor.requireAdmission();return "ok";},()->{},this::guard)).isEqualTo("ok");assertThat(commits).hasValue(1);
    }
    @Test void unusedStatementPreparationDoesNotCountAsExecutedUserSql() {
        assertThat(run(()->{
            try(var statement=providerConnection.get().prepareStatement("SELECT 'ok'")) {
                executor.beginAdmission();
                try(var result=statement.executeQuery()) {assertThat(result.next()).isTrue();}
                executor.sealAdmission();executor.requireAdmission();return "ok";
            } catch(SQLException failure) {throw new IllegalStateException(failure);}
        },()->{},this::guard)).isEqualTo("ok");
        assertThat(commits).hasValue(1);
    }
    @Test void previouslyPreparedDmlStillPoisonsWhenExecutedAfterAdmission() {
        rejected(()->run(()->{
            try(var statement=providerConnection.get().prepareStatement("UPDATE x SET a=1")) {
                executor.beginAdmission();
                try {statement.executeUpdate();}catch(SQLException expected) { /* Poison must survive a caught refusal. */ }
                executor.sealAdmission();return "bad";
            } catch(SQLException failure) {throw new IllegalStateException(failure);}
        },()->{},this::guard));
        assertThat(commits).hasValue(0);assertThat(rollbacks).hasValue(1);
    }
}
