/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TransactionalUseCaseExecutor
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence.transaction
 *
 * @Description : Executes generic application work inside a technical transaction boundary.
 *
 */
package dz.sh.hidra.platform.persistence.transaction;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class TransactionalUseCaseExecutor {

    private final TransactionTemplate transactionTemplate;

    public TransactionalUseCaseExecutor(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public <T> T execute(Supplier<T> useCaseWork) {
        if (useCaseWork == null) {
            throw new IllegalArgumentException("Use-case work must not be null.");
        }
        return transactionTemplate.execute(status -> useCaseWork.get());
    }

    public void executeWithoutResult(Runnable useCaseWork) {
        if (useCaseWork == null) {
            throw new IllegalArgumentException("Use-case work must not be null.");
        }
        transactionTemplate.executeWithoutResult(status -> useCaseWork.run());
    }
}
