/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityApplicationServiceRegistry
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application
 *
 * @Description : Groups identity application use cases for composition.
 *
 */
package dz.sh.hidra.modules.identity.application;

import dz.sh.hidra.modules.identity.application.port.in.CreateUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;

/**
 * Groups identity application use cases for composition.
 *
 * @param createUserUseCase create user use case
 * @param evaluatePermissionUseCase permission evaluation use case
 */
public record IdentityApplicationServiceRegistry(
        CreateUserUseCase createUserUseCase,
        EvaluatePermissionUseCase evaluatePermissionUseCase
) {
}
