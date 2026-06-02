/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionDecisionDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.dto
 *
 * @Description : Application DTO representing an identity permission decision.
 *
 */
package dz.sh.hidra.modules.identity.application.dto;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.UserId;

/**
 * Application DTO representing a permission evaluation decision.
 *
 * <p>Business role: communicates whether a user has a requested business permission.</p>
 *
 * <p>Architecture role: immutable application-layer data transfer record for permission
 * evaluation use cases. It does not expose platform security internals or HTTP response
 * details.</p>
 *
 * <p>Validation responsibility: requires user identifier and permission code. Blank
 * decision reasons are normalized to {@code null}.</p>
 *
 * <p>Usage: return this DTO from the evaluate-permission use case.</p>
 */
public record PermissionDecisionDto(
        UserId userId,
        PermissionCode permissionCode,
        boolean granted,
        String reason
) {

    public PermissionDecisionDto {
        userId = requireNonNull(userId, "UserId");
        permissionCode = requireNonNull(permissionCode, "PermissionCode");
        reason = normalize(reason);
    }

    public static PermissionDecisionDto granted(UserId userId, PermissionCode permissionCode) {
        return new PermissionDecisionDto(userId, permissionCode, true, "Permission granted.");
    }

    public static PermissionDecisionDto denied(UserId userId, PermissionCode permissionCode, String reason) {
        return new PermissionDecisionDto(userId, permissionCode, false, reason);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
