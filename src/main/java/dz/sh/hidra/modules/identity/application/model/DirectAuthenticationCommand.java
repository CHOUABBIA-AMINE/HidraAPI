/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DirectAuthenticationCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.model
 *
 * @Description : Carries direct-login provider selection, credentials, and request metadata through the application boundary.
 *
 */
package dz.sh.hidra.modules.identity.application.model;

import dz.sh.hidra.modules.identity.domain.value.ProviderType;

/**
 * Application command for direct LOCAL or LDAP/Active Directory authentication.
 */
public record DirectAuthenticationCommand(
        ProviderType providerType,
        String principal,
        String credentials,
        String clientIp,
        String userAgent,
        String correlationId
) {
}
