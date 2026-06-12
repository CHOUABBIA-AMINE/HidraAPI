/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CathodicProtectionSurveyRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.out
 *
 * @Description : Repository port for CathodicProtectionSurvey.
 *
 */
package dz.sh.hidra.modules.integrity.application.port.out;

import dz.sh.hidra.modules.integrity.domain.model.CathodicProtectionSurvey;

import java.util.Optional;

/**
 * Repository port for CathodicProtectionSurvey.
 */
public interface CathodicProtectionSurveyRepositoryPort {

    CathodicProtectionSurvey save(CathodicProtectionSurvey model);

    Optional<CathodicProtectionSurvey> findById(String id);
}
