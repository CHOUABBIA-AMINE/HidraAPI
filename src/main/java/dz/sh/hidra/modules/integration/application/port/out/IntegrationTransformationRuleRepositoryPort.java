/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationTransformationRuleRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Repository port for IntegrationTransformationRule.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

import dz.sh.hidra.modules.integration.domain.model.IntegrationTransformationRule;

import java.util.Optional;

/**
 * Repository port for IntegrationTransformationRule.
 */
public interface IntegrationTransformationRuleRepositoryPort {

    IntegrationTransformationRule save(IntegrationTransformationRule model);

    Optional<IntegrationTransformationRule> findById(String id);
}
