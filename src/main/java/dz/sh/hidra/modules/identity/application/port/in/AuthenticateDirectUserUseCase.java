/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticateDirectUserUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.in
 *
 * @Description : Defines the application boundary for explicit LOCAL and LDAP/Active Directory login.
 *
 */
package dz.sh.hidra.modules.identity.application.port.in;

import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationResult;

/**
 * Inbound use case for provider-selected direct authentication.
 */
public interface AuthenticateDirectUserUseCase {

    DirectAuthenticationResult authenticate(DirectAuthenticationCommand command);
}
