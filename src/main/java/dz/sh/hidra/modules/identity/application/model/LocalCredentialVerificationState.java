/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalCredentialVerificationState
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.model
 *
 * @Description : Captures the exact LOCAL credential row used by the password verifier without diagnostic hash disclosure.
 *
 */
package dz.sh.hidra.modules.identity.application.model;

/** Atomic credential row image used by the actual password verifier. */
public record LocalCredentialVerificationState(
        String credentialId,
        String userId,
        String passwordHash,
        String status,
        long securityVersion) {
    @Override
    public String toString() {
        return "LocalCredentialVerificationState[REDACTED]";
    }
}
