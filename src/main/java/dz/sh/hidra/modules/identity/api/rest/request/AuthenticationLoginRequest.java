/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationLoginRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.request
 *
 * @Description : Carries an explicit direct-authentication provider selection and submitted credentials.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.request;

import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Direct-login request for LOCAL or LDAP/Active Directory authentication.
 *
 * @param providerType explicitly selected provider type
 * @param principal submitted username or directory principal
 * @param credentials submitted password or direct credential
 */
public record AuthenticationLoginRequest(
        @NotNull ProviderType providerType,
        @NotBlank String principal,
        @NotBlank String credentials
) {
}
