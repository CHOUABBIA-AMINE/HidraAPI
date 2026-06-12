/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EvaluatePermissionUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.in
 *
 * @Description : Inbound port for permission evaluation.
 *
 */
package dz.sh.hidra.modules.identity.application.port.in;

import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;

/**
 * Inbound use case for evaluating permissions.
 */
public interface EvaluatePermissionUseCase {

    PermissionDecisionDto evaluate(EvaluatePermissionQuery query);
}
