/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TransactionExecutor
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence
 *
 * @Description : Defines a framework-neutral transaction execution contract.
 *
 */
package dz.sh.hidra.platform.persistence;

import java.util.function.Supplier;

/**
 * Framework-neutral transaction execution contract.
 */
public interface TransactionExecutor {

    <T> T execute(Supplier<T> action);

    void execute(Runnable action);
}
