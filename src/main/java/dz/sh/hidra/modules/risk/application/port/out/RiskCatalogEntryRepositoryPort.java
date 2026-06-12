/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskCatalogEntryRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.out
 *
 * @Description : Repository port for RiskCatalogEntry.
 *
 */
package dz.sh.hidra.modules.risk.application.port.out;

import dz.sh.hidra.modules.risk.domain.model.RiskCatalogEntry;

import java.util.Optional;

/**
 * Repository port for RiskCatalogEntry.
 */
public interface RiskCatalogEntryRepositoryPort {

    RiskCatalogEntry save(RiskCatalogEntry model);

    Optional<RiskCatalogEntry> findById(String id);
}
