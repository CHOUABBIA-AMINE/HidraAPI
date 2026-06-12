/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsSubjectAreaRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Repository port for AnalyticsSubjectArea.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

import dz.sh.hidra.modules.analytics.domain.model.AnalyticsSubjectArea;

import java.util.Optional;

/**
 * Repository port for AnalyticsSubjectArea.
 */
public interface AnalyticsSubjectAreaRepositoryPort {

    AnalyticsSubjectArea save(AnalyticsSubjectArea model);

    Optional<AnalyticsSubjectArea> findById(String id);
}
