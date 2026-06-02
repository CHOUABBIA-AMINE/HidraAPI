/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionCodeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Tests identity permission code validation.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.modules.identity.domain.exception.InvalidPermissionCodeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests identity permission code validation.
 *
 * <p>Business role: verifies the canonical context:resource:action permission format.</p>
 *
 * <p>Architecture role: domain test for the permission code value object only. It does
 * not depend on Spring, JPA, platform security, or organization modules.</p>
 *
 * <p>Validation responsibility: covers lowercase normalization, part extraction, missing
 * part rejection, and unsupported format rejection.</p>
 *
 * <p>Usage: executed by the identity domain test suite.</p>
 */
class PermissionCodeTest {

    @Test
    void ofShouldNormalizePermissionCodeToLowercase() {
        PermissionCode permissionCode = PermissionCode.of("  Identity:User:Create  ");

        assertEquals("identity:user:create", permissionCode.value());
        assertEquals("identity", permissionCode.context());
        assertEquals("user", permissionCode.resource());
        assertEquals("create", permissionCode.action());
    }

    @Test
    void ofShouldRejectMissingAction() {
        assertThrows(InvalidPermissionCodeException.class, () -> PermissionCode.of("identity:user"));
    }

    @Test
    void ofShouldRejectMissingResource() {
        assertThrows(InvalidPermissionCodeException.class, () -> PermissionCode.of("identity::create"));
    }

    @Test
    void ofShouldRejectUnsupportedCharacters() {
        assertThrows(InvalidPermissionCodeException.class, () -> PermissionCode.of("identity:user:create!"));
    }

    @Test
    void ofShouldRejectBlankPermissionCode() {
        assertThrows(InvalidPermissionCodeException.class, () -> PermissionCode.of("   "));
    }
}
