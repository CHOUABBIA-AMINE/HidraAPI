/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FoundationIdentityLifecycleUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.in
 *
 * @Description : Exposes the complete protected ordinary user access lifecycle.
 *
 */
package dz.sh.hidra.modules.identity.application.port.in;

import java.time.Instant;
import java.util.List;

public interface FoundationIdentityLifecycleUseCase {
    record UserState(String id, String status, Instant updatedAt) { }
    record CredentialState(String userId, long securityVersion, Instant changedAt) { }
    record GrantState(String id, String status, Instant revokedAt) { }
    enum GrantKind { USER_ROLE, USER_PERMISSION, ROLE_PERMISSION }

    UserState currentUser(String userId);
    CredentialState currentCredential(String userId);
    UserState activate(String userId, Instant expectedUpdatedAt, String reason);
    UserState deactivate(String userId, Instant expectedUpdatedAt, String reason);
    CredentialState credential(String userId, String password, long expectedSecurityVersion, String reason);
    GrantState revoke(GrantKind kind, String grantId, Instant expectedCreatedAt, String reason);
    List<String> capabilities(String companyId, String resourceType, String resourceId);
}
