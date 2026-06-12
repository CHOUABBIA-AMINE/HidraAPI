/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportCatalogTranslationRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.out
 *
 * @Description : Repository port for ReportCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.out;

import dz.sh.hidra.modules.reporting.domain.model.ReportCatalogTranslation;

import java.util.Optional;

/**
 * Repository port for ReportCatalogTranslation.
 */
public interface ReportCatalogTranslationRepositoryPort {

    ReportCatalogTranslation save(ReportCatalogTranslation model);

    Optional<ReportCatalogTranslation> findById(String id);
}
