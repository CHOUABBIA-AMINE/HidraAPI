/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyOperationAuthenticationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.topology
 *
 * @Description : Exports argument-free point-in-time bound authentication evidence without permission or source-assertion authority.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.topology;

import java.time.Instant;

/** B1 point-in-time evidence only; no permission, source assertion or operation fence. */
public interface TopologyOperationAuthenticationContract {
    record Evidence(
            String bindingId,
            String sessionId,
            String userId,
            String providerId,
            String authenticationType,
            long providerGeneration,
            String externalIdentityId,
            Long externalGeneration,
            String credentialId,
            Long credentialGeneration,
            String issuer,
            String jti,
            Instant observedAt,
            Instant expiresAt,
            Instant sourceExpiresAt) {}

    record Result(String status, Evidence evidence) {
        public Result {
            if (!("ELIGIBLE".equals(status) && evidence != null)
                    && !("ACCESS_DENIED".equals(status) && evidence == null))
                throw new IllegalArgumentException("Invalid bound authentication result.");
        }

        public static Result denied() {
            return new Result("ACCESS_DENIED", null);
        }

        public static Result eligible(Evidence evidence) {
            return new Result("ELIGIBLE", evidence);
        }
    }

    Result queryCurrentBoundAuthentication();
}
