/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleCodeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Tests identity role code validation.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests identity role code validation.
 *
 * <p>Business role: verifies stable role codes used by identity role administration.</p>
 *
 * <p>Architecture role: domain test for the role code value object only. It does not
 * depend on Spring, JPA, platform security, or organization modules.</p>
 *
 * <p>Validation responsibility: covers uppercase normalization, length rules, allowed
 * characters, and start-character rules.</p>
 *
 * <p>Usage: executed by the identity domain test suite.</p>
 */
class RoleCodeTest {

    @Test
    void ofShouldNormalizeRoleCodeToUppercase() {
        RoleCode roleCode = RoleCode.of("  identity_admin  ");

        assertEquals("IDENTITY_ADMIN", roleCode.value());
    }

    @Test
    void ofShouldRejectTooShortRoleCode() {
        assertThrows(InvalidValueObjectException.class, () -> RoleCode.of("ab"));
    }

    @Test
    void ofShouldRejectRoleCodeStartingWithDigit() {
        assertThrows(InvalidValueObjectException.class, () -> RoleCode.of("1_ADMIN"));
    }

    @Test
    void ofShouldRejectUnsupportedCharacters() {
        assertThrows(InvalidValueObjectException.class, () -> RoleCode.of("IDENTITY-ADMIN"));
    }

    @Test
    void ofShouldRejectBlankRoleCode() {
        assertThrows(InvalidValueObjectException.class, () -> RoleCode.of("   "));
    }
}
