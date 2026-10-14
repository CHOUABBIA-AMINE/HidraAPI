/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationAuthenticationBindingRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Append-only authenticated binding storage and exact native token lookup.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;

import java.util.Optional;

/**
 * Outbound persistence protocol. An existing identical tuple is read evidence only; it never
 * authorizes receipt renewal or token issuance.
 */
public interface OperationAuthenticationBindingRepositoryPort {

    /** Append one immutable binding, with database constraints and ownership validation. */
    void append(OperationAuthenticationBinding binding);

    /** Exact issuer and JTI lookup; absence is not proof of successful verification. */
    Optional<OperationAuthenticationBinding> findByNativeIssuerAndJti(
            String nativeIssuer, String jti);
}
