/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Entity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.model
 *
 * @Description : Minimal domain entity contract exposing a stable identifier.
 *
 */
package dz.sh.hidra.kernel.domain.model;

public interface Entity<ID> {

    ID id();
}
