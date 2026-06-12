/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationPayloadGuard
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.service
 *
 * @Description : Guards notification payloads and provider references.
 *
 */
package dz.sh.hidra.modules.notification.domain.service;

import dz.sh.hidra.modules.notification.domain.exception.NotificationBoundaryViolationException;
import dz.sh.hidra.modules.notification.domain.policy.NotificationBoundaryPolicy;

/**
 * Guards notification payloads and provider references.
 */
public class NotificationPayloadGuard {

    public void ensureNoProviderSecret(String value) {
        if (NotificationBoundaryPolicy.containsProviderSecret(value)) {
            throw new NotificationBoundaryViolationException("Notification metadata must not store provider credentials, tokens, passwords, or secret values.");
        }
    }

    public void ensureNoBusinessOwnership(String operationName) {
        if (NotificationBoundaryPolicy.isForbiddenBusinessOwnership(operationName)) {
            throw new NotificationBoundaryViolationException("Notification must not own or mutate source business state.");
        }
    }
}
