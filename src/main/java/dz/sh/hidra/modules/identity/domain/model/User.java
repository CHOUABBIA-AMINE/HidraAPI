/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : User
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Represents an application security identity.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

    /**
     * Represents an application security identity.
     *
         * @param id id
     * @param username username
     * @param emailAddress emailAddress
     * @param displayName displayName
     * @param userType userType
     * @param status status
     * @param employeeReferenceId employeeReferenceId
     * @param lastAuthenticatedAt lastAuthenticatedAt
     * @param failedLoginCount failedLoginCount
     * @param lockedUntil lockedUntil
     * @param createdAt createdAt
     * @param activatedAt activatedAt
     * @param suspendedAt suspendedAt
     * @param disabledAt disabledAt
     * @param updatedAt updatedAt
     */
    public record User(
            String id,
        String username,
        String emailAddress,
        String displayName,
        UserType userType,
        UserStatus status,
        String employeeReferenceId,
        Instant lastAuthenticatedAt,
        int failedLoginCount,
        Instant lockedUntil,
        Instant createdAt,
        Instant activatedAt,
        Instant suspendedAt,
        Instant disabledAt,
        Instant updatedAt
    ) {

        public User {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("User id must not be blank.");
        }
        // HRA-051 required: userType
        if (userType == null) {
            throw new InvalidIdentityValueException("User user type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("User status must not be null.");
        }

        if (username == null || username.isBlank()) {
            throw new InvalidIdentityValueException("User username must not be blank.");
        }
        id = normalize(id);
        username = normalize(username);
        emailAddress = normalize(emailAddress);
        displayName = normalize(displayName);
        employeeReferenceId = normalize(employeeReferenceId);
        }

public boolean active() {
    return status == UserStatus.ACTIVE;
}

public boolean locked(Instant at) {
    return status == UserStatus.LOCKED || (lockedUntil != null && at != null && at.isBefore(lockedUntil));
}

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
