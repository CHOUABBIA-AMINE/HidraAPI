/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityRecommendationRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.out
 *
 * @Description : Repository port for IntegrityRecommendation.
 *
 */
package dz.sh.hidra.modules.integrity.application.port.out;

import dz.sh.hidra.modules.integrity.domain.model.IntegrityRecommendation;

import java.util.Optional;

/**
 * Repository port for IntegrityRecommendation.
 */
public interface IntegrityRecommendationRepositoryPort {

    IntegrityRecommendation save(IntegrityRecommendation model);

    Optional<IntegrityRecommendation> findById(String id);
}
