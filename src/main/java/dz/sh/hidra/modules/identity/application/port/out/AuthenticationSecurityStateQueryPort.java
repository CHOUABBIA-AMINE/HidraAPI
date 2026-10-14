/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationSecurityStateQueryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Defines fresh Identity-owned credential, provider and joined current-state queries for bound authentication.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.application.model.AuthenticationVerificationSnapshot;
import dz.sh.hidra.modules.identity.application.model.LocalCredentialVerificationState;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;

import java.time.Instant;

/** Exact owner SQL row images, independent of stale JPA entity generations. */
public interface AuthenticationSecurityStateQueryPort {
    record ProviderState(
            String id,
            String type,
            long version,
            String issuer,
            String directoryBaseDn,
            String userSearchBase) {}

    ProviderState provider(String id, String expectedType);

    LocalCredentialVerificationState credential(String userId);

    AuthenticationVerificationSnapshot external(
            ProviderState provider, String subject, String issuer, Instant sourceDeadline);

    boolean current(OperationAuthenticationBinding binding, Instant observedAt);
}
