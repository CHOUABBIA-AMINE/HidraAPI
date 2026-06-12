/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskReviewRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.out
 *
 * @Description : Repository port for RiskReview.
 *
 */
package dz.sh.hidra.modules.risk.application.port.out;

import dz.sh.hidra.modules.risk.domain.model.RiskReview;

import java.util.Optional;

/**
 * Repository port for RiskReview.
 */
public interface RiskReviewRepositoryPort {

    RiskReview save(RiskReview model);

    Optional<RiskReview> findById(String id);
}
