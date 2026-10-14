/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedJpaTransactionManager
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence
 *
 * @Description : Brackets physical provider commit and rollback while preserving dormant ordinary JPA semantics.
 *
 */
package dz.sh.hidra.platform.persistence;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.sql.DataSource;

/** Provider completion bridge only; no module entity or business authority. */
public class ProtectedJpaTransactionManager extends JpaTransactionManager {
    public ProtectedJpaTransactionManager(EntityManagerFactory factory, DataSource datasource) {
        super(factory);
        setDataSource(datasource);
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        var s = ProtectedOperationDataSource.CURRENT.get();
        if (s != null
                && (s.phase != ProtectedOperationDataSource.Phase.OPENING
                        || definition.isReadOnly()
                        || definition.getIsolationLevel()
                                != TransactionDefinition.ISOLATION_READ_COMMITTED)) {
            s.reject("nested or incompatible transaction begin");
            s.healthy();
        }
        super.doBegin(transaction, definition);
    }

    @Override
    protected Object doSuspend(Object transaction) {
        var s = ProtectedOperationDataSource.CURRENT.get();
        if (s != null) {
            s.reject("transaction suspension prohibited");
            s.healthy();
        }
        return super.doSuspend(transaction);
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) {
        var s = ProtectedOperationDataSource.CURRENT.get();
        if (s == null) {
            super.doCommit(status);
            return;
        }
        s.healthy();
        if (s.phase != ProtectedOperationDataSource.Phase.SEALED
                || s.guardExecutions != 1
                || s.openGuardResults != 0) {
            s.reject("missing final guard or open result");
            s.healthy();
        }
        s.phase = ProtectedOperationDataSource.Phase.COMPLETION;
        s.providerCommit = true;
        try {
            super.doCommit(status);
        } finally {
            s.providerCommit = false;
        }
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) {
        var s = ProtectedOperationDataSource.CURRENT.get();
        if (s == null) {
            super.doRollback(status);
            return;
        }
        s.providerRollback = true;
        try {
            super.doRollback(status);
        } finally {
            s.providerRollback = false;
        }
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        var s = ProtectedOperationDataSource.CURRENT.get();
        if (s == null) {
            super.doCleanupAfterCompletion(transaction);
            return;
        }
        s.cleanup = true;
        try {
            super.doCleanupAfterCompletion(transaction);
        } finally {
            s.cleanup = false;
        }
    }
}
