/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionCodePolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.policy
 *
 * @Description : Tests identity permission code format policy.
 *
 */
package dz.sh.hidra.modules.identity.domain.policy;

import dz.sh.hidra.modules.identity.domain.exception.InvalidPermissionCodeException;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests identity permission code format policy.
 *
 * <p>Business role: verifies the canonical identity permission code format enforced by
 * the domain policy.</p>
 *
 * <p>Architecture role: pure domain policy test with no Spring, JPA, platform security,
 * repository, or organization dependency.</p>
 *
 * <p>Validation responsibility: covers valid code acceptance, invalid code rejection,
 * boolean validity checks, and part extraction.</p>
 *
 * <p>Usage: executed by the identity domain test suite.</p>
 */
class PermissionCodePolicyTest {

    @Test
    void validateShouldReturnNormalizedPermissionCode() {
        PermissionCode permissionCode = PermissionCodePolicy.validate("  Identity:User:Create  ");

        assertEquals("identity:user:create", permissionCode.value());
    }

    @Test
    void validateShouldRejectInvalidFormat() {
        assertThrows(InvalidPermissionCodeException.class, () -> PermissionCodePolicy.validate("identity:user"));
    }

    @Test
    void isValidShouldReturnTrueForValidCode() {
        assertTrue(PermissionCodePolicy.isValid("identity:user:create"));
    }

    @Test
    void isValidShouldReturnFalseForInvalidCode() {
        assertFalse(PermissionCodePolicy.isValid("identity:user:create:extra"));
    }

    @Test
    void contextResourceAndActionShouldExtractCodeParts() {
        PermissionCode permissionCode = PermissionCode.of("identity:user:create");

        assertEquals("identity", PermissionCodePolicy.context(permissionCode));
        assertEquals("user", PermissionCodePolicy.resource(permissionCode));
        assertEquals("create", PermissionCodePolicy.action(permissionCode));
    }
}
