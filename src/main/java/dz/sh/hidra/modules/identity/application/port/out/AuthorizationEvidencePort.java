/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Resolves bounded and explainable Identity authorization evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvidence;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import java.time.Instant;
public interface AuthorizationEvidencePort {
    AuthorizationEvidence load(AuthorizationEvaluationRequest request, Instant at, VerifiedAuthorizationAssertion assertion);
}
