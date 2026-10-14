/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DirectAuthenticationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Isolates provider-specific Spring Security authentication execution from the Identity application layer.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;

/**
 * Outbound port for provider-selected direct credential verification and principal normalization.
 */
public interface DirectAuthenticationPort {

    dz.sh.hidra.modules.identity.application.model.VerifiedDirectAuthenticationResult verify(
            ProviderType type, String principal, String credentials);

    void finishVerification();

    HidraPrincipal authenticate(ProviderType providerType, String principal, String credentials);
}
