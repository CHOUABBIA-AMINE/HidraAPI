/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedOperationDataSource
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence
 *
 * @Description : Seals admitted JDBC statements across actual protected Spring and JPA transaction completion.
 *
 */
package dz.sh.hidra.platform.persistence;

import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.*;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

/** Technical SQL lifecycle boundary; permission/fence/window/head meaning belongs to owners. */
public final class ProtectedOperationDataSource extends DelegatingDataSource
        implements AutoCloseable {
    static final ThreadLocal<Scope> CURRENT = new ThreadLocal<>();

    enum Phase {
        OPENING,
        WORKING,
        ADMITTING,
        OBSERVING,
        FLUSHING,
        GUARD,
        SEALED,
        COMPLETION,
        FINISHED
    }

    static final class Scope {
        final Thread owner = Thread.currentThread();
        Phase phase = Phase.OPENING;
        Connection physical;
        volatile boolean poisoned;
        boolean providerCommit, providerRollback, cleanup, commitAttempted, committed, rolledBack;
        int guardExecutions, guardPreparations, openGuardResults;
        long started, xid;
        long userStatements;
        boolean admission;
        Object executor;
        int pid;
        String reason;

        SQLException reject(String why) {
            if (reason == null) reason = why;
            poisoned = true;
            Scope current = CURRENT.get();
            if (current != null && current != this) {
                if (current.reason == null) current.reason = why;
                current.poisoned = true;
            }
            return new SQLException("Protected transaction rejected: " + why, "25000");
        }

        void handle(Connection c) throws SQLException {
            if (owner != Thread.currentThread() || CURRENT.get() != this || phase == Phase.FINISHED)
                throw reject("expired or foreign-thread handle");
            if (physical != c) throw reject("foreign physical connection");
        }

        void healthy() {
            if (poisoned)
                throw new IllegalStateException("Protected transaction poisoned: " + reason);
        }

        void deadline() {
            healthy();
            if (System.nanoTime() - started >= 500_000_000L) {
                poisoned = true;
                reason = "aggregate 500ms work budget";
                healthy();
            }
        }
    }

    private static String firstSqlWord(String sql) {
        int i = 0, n = sql.length();
        while (i < n) {
            if (Character.isWhitespace(sql.charAt(i))) {
                i++;
                continue;
            }
            if (sql.startsWith("--", i)) {
                int end = sql.indexOf('\n', i + 2);
                i = end < 0 ? n : end + 1;
                continue;
            }
            if (sql.startsWith("/*", i)) {
                int depth = 1;
                i += 2;
                while (i < n && depth > 0) {
                    if (sql.startsWith("/*", i)) {
                        depth++;
                        i += 2;
                    } else if (sql.startsWith("*/", i)) {
                        depth--;
                        i += 2;
                    } else i++;
                }
                if (depth != 0) return "";
                continue;
            }
            break;
        }
        int begin = i;
        while (i < n && (Character.isLetter(sql.charAt(i)) || sql.charAt(i) == '_')) i++;
        return sql.substring(begin, i).toUpperCase(Locale.ROOT);
    }

    static boolean admissionSql(String sql) {
        if(sql==null) return false;
        String first=firstSqlWord(sql);
        if(!first.equals("SELECT") && !first.equals("LOCK")) return false;
        // Inspect code tokens, never text inside quoted data or identifiers.
        StringBuilder code=new StringBuilder();
        for(int i=0;i<sql.length();) {
            char c=sql.charAt(i);
            if(c=='\'' || c=='"') {
                char quote=c;i++;
                while(i<sql.length()) { if(sql.charAt(i++)==quote) { if(i<sql.length() && sql.charAt(i)==quote) i++; else break; } }
                code.append(' ');
            } else if(sql.startsWith("--",i)) {
                int e=sql.indexOf('\n',i);i=e<0?sql.length():e+1;code.append(' ');
            } else if(sql.startsWith("/*",i)) {
                int depth=1;i+=2;
                while(i<sql.length() && depth>0) {
                    if(sql.startsWith("/*",i)) {depth++;i+=2;}
                    else if(sql.startsWith("*/",i)) {depth--;i+=2;} else i++;
                }
                if(depth!=0) return false;code.append(' ');
            } else {code.append(c);i++;}
        }
        String tokens=code.toString().toUpperCase(Locale.ROOT);
        return !java.util.regex.Pattern.compile("\\b(INTO|INSERT|UPDATE|DELETE|MERGE|CALL|DO|COPY|WITH|SET_CONFIG|PG_ADVISORY_LOCK)\\b").matcher(tokens).find()
            && !tokens.contains("$") && !tokens.contains(";");
    }

    public ProtectedOperationDataSource(DataSource target) {
        super(Objects.requireNonNull(target));
    }

    @Override
    public void setTargetDataSource(DataSource target) {
        Scope scope = CURRENT.get();
        if (scope != null) {
            scope.reject("datasource replacement");
            scope.healthy();
        }
        if (super.getTargetDataSource() != null)
            throw new IllegalStateException("Protected datasource delegate is immutable");
        super.setTargetDataSource(target);
    }

    @Override
    public DataSource getTargetDataSource() {
        Scope scope = CURRENT.get();
        if (scope != null) {
            scope.reject("raw datasource delegate access");
            scope.healthy();
        }
        return super.getTargetDataSource();
    }

    @Override
    protected DataSource obtainTargetDataSource() {
        return Objects.requireNonNull(super.getTargetDataSource());
    }

    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        Scope scope = CURRENT.get();
        if (scope != null) throw scope.reject("raw connection builder");
        return super.createConnectionBuilder();
    }

    @Override
    public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        Scope scope = CURRENT.get();
        if (scope != null) throw scope.reject("sharding builder");
        return super.createShardingKeyBuilder();
    }

    @Override
    public void close() throws Exception {
        Scope scope = CURRENT.get();
        if (scope != null) throw scope.reject("datasource close during protected work");
        DataSource target = getTargetDataSource();
        while (target instanceof DelegatingDataSource delegate
                && !(target instanceof AutoCloseable)) target = delegate.getTargetDataSource();
        if (target instanceof AutoCloseable closeable) closeable.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return obtain(null, null);
    }

    @Override
    public Connection getConnection(String u, String p) throws SQLException {
        return obtain(u, p);
    }

    private Connection obtain(String u, String p) throws SQLException {
        Scope s = CURRENT.get();
        if (s != null && s.physical != null) throw s.reject("additional datasource connection");
        Connection c;
        try {
            c = u == null ? super.getConnection() : super.getConnection(u, p);
        } catch (SQLException error) {
            if (s != null) s.reject("datasource acquisition failure");
            throw error;
        }
        if (s != null) s.physical = c;
        return new ConnectionBoundary(c, s).proxy();
    }

    @Override
    public <T> T unwrap(Class<T> type) throws SQLException {
        if (type.isInstance(this)) return type.cast(this);
        Scope s = CURRENT.get();
        if (s != null) throw s.reject("raw datasource unwrap");
        return super.unwrap(type);
    }

    @Override
    public boolean isWrapperFor(Class<?> type) throws SQLException {
        return type.isInstance(this) || super.isWrapperFor(type);
    }

    private static Object call(Object target, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(target, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static Object objectMethod(Object proxy, Method m, Object[] args) {
        return switch (m.getName()) {
            case "equals" -> proxy == args[0];
            case "hashCode" -> System.identityHashCode(proxy);
            default -> "Protected JDBC " + m.getDeclaringClass().getSimpleName();
        };
    }

    private static final class ConnectionBoundary implements InvocationHandler {
        final Connection physical;
        Scope bound;
        Connection proxy;

        ConnectionBoundary(Connection c, Scope s) {
            physical = c;
            bound = s;
        }

        Connection proxy() {
            proxy =
                    (Connection)
                            Proxy.newProxyInstance(
                                    Connection.class.getClassLoader(),
                                    new Class<?>[] {Connection.class},
                                    this);
            return proxy;
        }

        Scope scope() throws SQLException {
            Scope active = CURRENT.get();
            if (bound == null && active != null) bound = active;
            if (bound != null) bound.handle(physical);
            return bound;
        }

        void sql(Scope s, String text, boolean execution) throws SQLException {
            if (s == null) return;
            if (s.poisoned) throw s.reject("previous error");
            if (s.phase != Phase.OPENING
                    && s.phase != Phase.ADMITTING
                    && s.phase != Phase.OBSERVING
                    && s.phase != Phase.WORKING
                    && s.phase != Phase.FLUSHING
                    && s.phase != Phase.GUARD) throw s.reject("SQL after final guard");
            if (s.phase != Phase.OPENING
                    && (!TransactionSynchronizationManager.isActualTransactionActive()
                            || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                            || physical.getAutoCommit()
                            || physical.isReadOnly()
                            || physical.getTransactionIsolation()
                                    != Connection.TRANSACTION_READ_COMMITTED))
                throw s.reject("wrong transaction participation");
            if (s.phase != Phase.OPENING && s.pid != 0) {
                // Metadata probes occur BEFORE the admitted SQL, including the final guard.
                // Never probe after sealing; they are not owner authority/time observations.
                try (Statement probe = physical.createStatement();
                        ResultSet image =
                                probe.executeQuery(
                                        "SELECT"
                                            + " pg_backend_pid(),txid_current(),current_setting('transaction_isolation'),current_setting('transaction_read_only')")) {
                    if (!image.next()
                            || image.getInt(1) != s.pid
                            || image.getLong(2) != s.xid
                            || !"read committed".equals(image.getString(3))
                            || !"off".equals(image.getString(4)))
                        throw s.reject("backend/xid/server participation changed");
                } catch (SQLException error) {
                    s.reject("participation probe failed");
                    throw error;
                }
            }
            if(execution && s.phase!=Phase.OPENING) {
                s.userStatements++;
                if(s.admission && (!admissionSql(text)
                    || (s.phase!=Phase.ADMITTING && firstSqlWord(text).equals("LOCK"))))
                    throw s.reject("mutation or late lock during protected admission");
            }
            if (s.phase != Phase.OPENING
                    && text != null
                    && (!Set.of("SELECT", "WITH", "INSERT", "UPDATE", "DELETE", "LOCK", "VALUES")
                                    .contains(firstSqlWord(text))
                            || text.strip().replaceFirst(";$", "").contains(";")))
                throw s.reject("transaction/control or multiple SQL");
        }

        @Override
        public Object invoke(Object p, Method m, Object[] args) throws Throwable {
            if (m.getDeclaringClass() == Object.class) return objectMethod(p, m, args);
            Scope s = scope();
            String name = m.getName();
            if (name.equals("unwrap")) {
                Class<?> type = (Class<?>) args[0];
                if (type.isInstance(p)) return p;
                if (s != null) throw s.reject("raw connection unwrap");
            }
            if (name.equals("isWrapperFor") && ((Class<?>) args[0]).isInstance(p)) return true;
            if (s != null) {
                if (name.equals("commit")) {
                    if (!s.providerCommit || s.commitAttempted || s.rolledBack || s.poisoned)
                        throw s.reject("premature/double commit");
                    s.commitAttempted = true;
                    Object value = call(physical, m, args);
                    s.committed = true;
                    return value;
                }
                if (name.equals("rollback")) {
                    if (args != null && args.length > 0) throw s.reject("partial rollback");
                    if ((!s.providerRollback && !s.providerCommit) || s.rolledBack)
                        throw s.reject("premature/double rollback");
                    Object value = call(physical, m, args);
                    s.rolledBack = true;
                    return value;
                }
                if (Set.of("setSavepoint", "releaseSavepoint", "abort").contains(name))
                    throw s.reject("savepoint/abort prohibited");
                if (Set.of(
                                        "setAutoCommit",
                                        "setReadOnly",
                                        "setTransactionIsolation",
                                        "setSchema",
                                        "setCatalog",
                                        "setNetworkTimeout",
                                        "setClientInfo",
                                        "setHoldability",
                                        "setTypeMap")
                                .contains(name)
                        && s.phase != Phase.OPENING
                        && !s.cleanup
                        && !((s.providerCommit || s.providerRollback)
                                && (s.committed || s.rolledBack)))
                    throw s.reject("connection settings mutation");
                if (name.equals("close")
                        && !s.cleanup
                        && !s.providerRollback
                        && !(s.providerCommit && (s.committed || s.rolledBack)))
                    throw s.reject("premature connection close");
                if (Set.of(
                                        "getMetaData",
                                        "isValid",
                                        "getSchema",
                                        "getCatalog",
                                        "nativeSQL",
                                        "getTransactionIsolation")
                                .contains(name)
                        && (s.phase == Phase.SEALED || s.phase == Phase.COMPLETION)
                        && !s.cleanup) throw s.reject("late connection observation");
            }
            if (Set.of("prepareStatement", "prepareCall", "createStatement").contains(name)) {
                String text =
                        args != null && args.length > 0 && args[0] instanceof String t ? t : null;
                sql(s, text, false);
                if (s != null && name.equals("prepareCall"))
                    throw s.reject("callable statement unsupported");
                if (s != null && s.phase == Phase.GUARD && ++s.guardPreparations != 1)
                    throw s.reject("multiple final statement preparations");
                Statement statement = (Statement) call(physical, m, args);
                return new StatementBoundary(this, statement, text).proxy();
            }
            Object result = call(physical, m, args);
            if (result instanceof DatabaseMetaData metadata)
                return Proxy.newProxyInstance(
                        DatabaseMetaData.class.getClassLoader(),
                        new Class<?>[] {DatabaseMetaData.class},
                        (q, method, values) -> {
                            if (method.getDeclaringClass() == Object.class)
                                return objectMethod(q, method, values);
                            Scope current = scope();
                            if (method.getName().equals("getConnection")) return proxy;
                            if (current != null && method.getName().equals("unwrap"))
                                throw current.reject("raw metadata unwrap");
                            sql(current, null);
                            Object v = call(metadata, method, values);
                            if (current != null && v instanceof ResultSet)
                                throw current.reject("metadata cursor unsupported");
                            return v;
                        });
            return result;
        }
    }

    private static final class StatementBoundary implements InvocationHandler {
        final ConnectionBoundary connection;
        final Statement statement;
        final String sql;
        final List<ResultBoundary> results = new ArrayList<>();
        Statement proxy;

        StatementBoundary(ConnectionBoundary c, Statement s, String text) {
            connection = c;
            statement = s;
            sql = text;
        }

        Statement proxy() {
            Class<?> type =
                    statement instanceof CallableStatement
                            ? CallableStatement.class
                            : statement instanceof PreparedStatement
                                    ? PreparedStatement.class
                                    : Statement.class;
            proxy =
                    (Statement)
                            Proxy.newProxyInstance(
                                    type.getClassLoader(), new Class<?>[] {type}, this);
            return proxy;
        }

        @Override
        public Object invoke(Object p, Method m, Object[] args) throws Throwable {
            if (m.getDeclaringClass() == Object.class) return objectMethod(p, m, args);
            Scope s = connection.scope();
            String name = m.getName();
            if (name.equals("getConnection")) return connection.proxy;
            if (name.equals("unwrap")) {
                Class<?> type = (Class<?>) args[0];
                if (type.isInstance(p)) return p;
                if (s != null) throw s.reject("raw statement unwrap");
            }
            if (name.equals("isWrapperFor") && ((Class<?>) args[0]).isInstance(p)) return true;
            if (s != null
                    && (name.contains("Batch")
                            || name.equals("getMoreResults")
                            || (name.equals("setFetchSize") && (int) args[0] > 0)))
                throw s.reject("batch/stream/multiple results unsupported");
            boolean execution = name.startsWith("execute");
            boolean guard = s != null && s.phase == Phase.GUARD;
            if (execution) {
                String text =
                        args != null && args.length > 0 && args[0] instanceof String t ? t : sql;
                connection.sql(s, text, true);
                if(s!=null && s.admission && firstSqlWord(text).equals("SELECT") && !name.equals("executeQuery"))
                    throw s.reject("admission SELECT requires executeQuery");
                if (guard) {
                    if (!name.equals("executeQuery"))
                        throw s.reject("final guard must be scalar query");
                    if (++s.guardExecutions != 1) throw s.reject("multiple guard executions");
                    statement.setMaxRows(2);
                    statement.setFetchSize(0);
                }
            }
            try {
                Object value = call(statement, m, args);
                if (guard && execution) s.phase = Phase.SEALED;
                if (name.equals("close")) for (ResultBoundary result : results) result.closed();
                if (value instanceof ResultSet rs) {
                    ResultBoundary wrapped = new ResultBoundary(this, rs, guard);
                    results.add(wrapped);
                    return wrapped.proxy();
                }
                return value;
            } catch (Throwable error) {
                if (s != null && (execution || error instanceof SQLException))
                    s.reject("SQL/statement failure");
                throw error;
            }
        }
    }

    private static final class ResultBoundary implements InvocationHandler {
        final StatementBoundary statement;
        final ResultSet result;
        final Scope scope;
        final boolean guard;
        boolean closed;
        int rows;

        ResultBoundary(StatementBoundary statement, ResultSet result, boolean guard) {
            this.statement = statement;
            this.result = result;
            this.scope = statement.connection.bound;
            this.guard = guard;
            if (guard) scope.openGuardResults++;
        }

        Object proxy() {
            return Proxy.newProxyInstance(
                    ResultSet.class.getClassLoader(), new Class<?>[] {ResultSet.class}, this);
        }

        void closed() {
            if (!closed) {
                closed = true;
                if (guard) {
                    scope.openGuardResults--;
                    if (rows != 1) scope.reject("final scalar row missing");
                }
            }
        }

        @Override
        public Object invoke(Object p, Method m, Object[] args) throws Throwable {
            if (m.getDeclaringClass() == Object.class) return objectMethod(p, m, args);
            Scope s = statement.connection.scope();
            String name = m.getName();
            if (name.equals("getStatement")) return statement.proxy;
            if (name.equals("unwrap")) {
                Class<?> type = (Class<?>) args[0];
                if (type.isInstance(p)) return p;
                if (s != null) throw s.reject("raw result unwrap");
            }
            if (name.equals("isWrapperFor") && ((Class<?>) args[0]).isInstance(p)) return true;
            if (s != null
                    && !guard
                    && (s.phase == Phase.SEALED || s.phase == Phase.COMPLETION)
                    && !Set.of("close", "getStatement").contains(name))
                throw s.reject("work cursor used after guard");
            if (s != null
                    && (name.startsWith("update")
                            || Set.of(
                                            "insertRow",
                                            "deleteRow",
                                            "moveToInsertRow",
                                            "refreshRow",
                                            "previous",
                                            "absolute",
                                            "relative",
                                            "first",
                                            "last",
                                            "beforeFirst",
                                            "afterLast")
                                    .contains(name)))
                throw s.reject("mutable/scrolling result unsupported");
            try {
                Object value = call(result, m, args);
                if (name.equals("close")) closed();
                if (value instanceof ResultSetMetaData metadata)
                    return Proxy.newProxyInstance(
                            ResultSetMetaData.class.getClassLoader(),
                            new Class<?>[] {ResultSetMetaData.class},
                            (q, method, values) -> {
                                if (method.getDeclaringClass() == Object.class)
                                    return objectMethod(q, method, values);
                                Scope current = statement.connection.scope();
                                if (method.getName().equals("unwrap")) {
                                    Class<?> type = (Class<?>) values[0];
                                    if (type.isInstance(q)) return q;
                                    if (current != null)
                                        throw current.reject("raw result metadata unwrap");
                                }
                                return call(metadata, method, values);
                            });
                if (s != null
                        && (value instanceof InputStream
                                || value instanceof Reader
                                || value instanceof Blob
                                || value instanceof Clob
                                || value instanceof SQLXML
                                || value instanceof java.sql.Array))
                    throw s.reject("stream/lob resource unsupported");
                if (guard && name.equals("next") && Boolean.TRUE.equals(value) && ++rows > 1)
                    throw s.reject("non-scalar final guard");
                return value;
            } catch (Throwable error) {
                if (s != null && error instanceof SQLException) s.reject("result failure");
                throw error;
            }
        }
    }
}
