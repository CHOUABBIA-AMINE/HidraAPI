/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Framework-neutral identity controller contract.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;
import dz.sh.hidra.modules.identity.api.rest.request.*;
import dz.sh.hidra.modules.identity.api.rest.response.*;

/**
 * Framework-neutral identity controller contract.
 */
public interface IdentityController {
    UserResponse createUser(CreateUserRequest request);
    PermissionDecisionResponse evaluate(EvaluatePermissionRequest request);
}
