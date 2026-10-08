/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningProductReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.contract.planning
 *
 * @Description : Enforces owner-controlled Nomination reference integrity.
 *
 */
package dz.sh.hidra.modules.custody.application.contract.planning;

import java.util.Optional;
public interface PlanningProductReferenceContract {
    Optional<Product> resolve(String id);
    record Product(String id, String code, boolean active) { }
}
