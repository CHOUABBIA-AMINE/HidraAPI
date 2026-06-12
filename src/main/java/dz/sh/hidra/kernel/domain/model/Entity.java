/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Entity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.model
 *
 * @Description : Defines the minimal generic domain entity contract.
 *
 */
package dz.sh.hidra.kernel.domain.model;

/**
 * Minimal domain entity contract exposing a stable identifier.
 *
 * @param <ID> stable identifier type
 */
public interface Entity<ID> {

    /**
     * Returns the stable identifier of the entity.
     *
     * @return stable identifier
     */
    ID id();
}
