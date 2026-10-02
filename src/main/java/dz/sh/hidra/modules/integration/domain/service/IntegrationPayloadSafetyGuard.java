/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationPayloadSafetyGuard
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.service
 *
 * @Description : Guards sanitized integration metadata and payload references.
 *
 */
package dz.sh.hidra.modules.integration.domain.service;

import dz.sh.hidra.modules.integration.domain.exception.IntegrationBoundaryViolationException;
import dz.sh.hidra.modules.integration.domain.policy.IntegrationBoundaryPolicy;

/**
 * Guards sanitized integration metadata and payload references.
 */
public class IntegrationPayloadSafetyGuard {

    public void ensureNoSecretMaterial(String value) {
        if (IntegrationBoundaryPolicy.isForbiddenSecretMaterial(value)) {
            throw new IntegrationBoundaryViolationException("Integration metadata must not store passwords, tokens, private keys, or secret values.");
        }
    }

    public void ensureNoOtActuation(String operationName) {
        if (IntegrationBoundaryPolicy.isForbiddenOtActuation(operationName)) {
            throw new IntegrationBoundaryViolationException("Integration must not execute OT actuation or SCADA control commands.");
        }
    }
}
