/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCatalogTranslationRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Repository port for TelemetryCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetryCatalogTranslation;

import java.util.Optional;

/**
 * Repository port for TelemetryCatalogTranslation.
 */
public interface TelemetryCatalogTranslationRepositoryPort {

    TelemetryCatalogTranslation save(TelemetryCatalogTranslation model);

    Optional<TelemetryCatalogTranslation> findById(String id);
}
