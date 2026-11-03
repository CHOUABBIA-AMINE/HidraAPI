/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FoundationIdentityLifecyclePersistencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Coordinates locked Identity-owned persistence for lifecycle mutations.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.application.port.in.FoundationIdentityLifecycleUseCase.*;
import java.time.Instant;

public interface FoundationIdentityLifecyclePersistencePort {
    UserState readUser(String userId, String actorId);
    CredentialState readCredential(String userId, String actorId);
    UserState changeUser(String userId, Instant expectedUpdatedAt, boolean activate, String reason, String actorId);
    CredentialState setCredential(String userId, String passwordHash, long expectedSecurityVersion, String reason, String actorId);
    GrantState revokeGrant(GrantKind kind, String grantId, Instant expectedCreatedAt, String reason, String actorId);
}
