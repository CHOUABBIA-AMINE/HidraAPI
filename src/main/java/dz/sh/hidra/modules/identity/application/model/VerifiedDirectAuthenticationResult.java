/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : VerifiedDirectAuthenticationResult
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.model
 *
 * @Description : Carries a normalized principal and opaque private-verification handle inside the direct authentication invocation.
 *
 */
package dz.sh.hidra.modules.identity.application.model;

import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;

import java.util.Objects;

/** Private invocation transport; construction is not authentication evidence. */
public record VerifiedDirectAuthenticationResult(
        HidraPrincipal principal, String verificationHandle) {
    public VerifiedDirectAuthenticationResult {
        Objects.requireNonNull(principal);
    }

    @Override
    public String toString() {
        return "VerifiedDirectAuthenticationResult[REDACTED]";
    }
}
