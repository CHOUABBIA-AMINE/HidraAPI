/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityLifecycleResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.response
 *
 * @Description : Defines safe identity lifecycle HTTP responses without secret material.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.response;

import dz.sh.hidra.modules.identity.application.port.in.FoundationIdentityLifecycleUseCase;
import java.time.Instant;

public final class IdentityLifecycleResponses {
    private IdentityLifecycleResponses() { }
    public record User(String id, String status, Instant updatedAt) {
        public static User of(FoundationIdentityLifecycleUseCase.UserState s) { return new User(s.id(), s.status(), s.updatedAt()); }
    }
    public record Credential(String userId, long securityVersion, Instant changedAt) {
        public static Credential of(FoundationIdentityLifecycleUseCase.CredentialState s) {
            return new Credential(s.userId(), s.securityVersion(), s.changedAt());
        }
    }
    public record Grant(String id, String status, Instant revokedAt) {
        public static Grant of(FoundationIdentityLifecycleUseCase.GrantState s) { return new Grant(s.id(), s.status(), s.revokedAt()); }
    }
}
