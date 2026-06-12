/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AggregateRoot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.model
 *
 * @Description : Defines the minimal generic aggregate-root contract.
 *
 */
package dz.sh.hidra.kernel.domain.model;

/**
 * Minimal aggregate-root contract exposing a stable identifier.
 *
 * @param <ID> stable identifier type
 */
public interface AggregateRoot<ID> {

    /**
     * Returns the stable identifier of the aggregate root.
     *
     * @return stable identifier
     */
    ID id();
}
