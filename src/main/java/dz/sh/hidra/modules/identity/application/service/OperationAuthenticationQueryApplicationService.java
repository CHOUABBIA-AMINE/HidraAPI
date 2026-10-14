/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationAuthenticationQueryApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Returns point-in-time B1 evidence from one fresh, read-only joined Identity owner-state image.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyOperationAuthenticationContract;
import dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

/** Current owner eligibility is separate from verified private producer origin. */
@Service
public class OperationAuthenticationQueryApplicationService {
    private final AuthenticationSecurityStateQueryPort states;

    public OperationAuthenticationQueryApplicationService(
            AuthenticationSecurityStateQueryPort states) {
        this.states = Objects.requireNonNull(states);
    }

    @org.springframework.transaction.annotation.Transactional(
            readOnly = true,
            isolation = org.springframework.transaction.annotation.Isolation.READ_COMMITTED,
            propagation = org.springframework.transaction.annotation.Propagation.REQUIRES_NEW)
    public TopologyOperationAuthenticationContract.Result query(
            OperationAuthenticationBinding b, Instant now) {
        if (!states.current(b, now)) return TopologyOperationAuthenticationContract.Result.denied();
        return TopologyOperationAuthenticationContract.Result.eligible(
                new TopologyOperationAuthenticationContract.Evidence(
                        b.bindingId(),
                        b.sessionId(),
                        b.userId(),
                        b.providerId(),
                        b.authenticationType(),
                        b.providerSecurityVersion(),
                        b.externalIdentityId(),
                        b.externalSecurityVersion(),
                        b.credentialId(),
                        b.credentialSecurityVersion(),
                        b.nativeIssuer(),
                        b.jti(),
                        now,
                        b.expiresAt(),
                        b.sourceExpiresAt()));
    }
}
