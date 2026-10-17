/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * TrustedAuthenticationVerificationPort @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-17 @Type :
 * Interface @Layer : Application @Module : identity @Package :
 * dz.sh.hidra.modules.identity.application.port.out @Description : Consumes private producer
 * verification once and defines transaction-bound attestation and after-commit publication.
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.application.model.*;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;

/** Consumption and attestation implementations are private actual-producer objects. */
public interface TrustedAuthenticationVerificationPort {
    interface Verification {
        AuthenticationVerificationSnapshot snapshot();

        /** Existing genuine no-capture implementations preserve their old protocol. */
        default boolean prepareSourceClaims() {
            return false;
        }

        default java.util.Optional<
                        dz.sh.hidra.modules.identity.domain.value.BoundSourceClaimEvidence>
                attestSourceClaims(OperationAuthenticationBinding binding) {
            return java.util.Optional.empty();
        }

        OperationAuthenticationBinding attest(
                String bindingId,
                String sessionId,
                AccessTokenIssuerSettings settings,
                AccessTokenIssuanceContext context);

        void publishAfterCommit(OperationAuthenticationBinding binding);
    }

    Verification consume(AuthenticatedPrincipalInput input);
}
