/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UsernameTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Tests identity username validation.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests identity username validation.
 *
 * <p>Business role: verifies the login username format accepted by the identity module.</p>
 *
 * <p>Architecture role: domain test for the username value object only. It does not
 * depend on Spring, JPA, platform security, or organization modules.</p>
 *
 * <p>Validation responsibility: covers normalization, length rules, start-character
 * rules, allowed separators, and blank rejection.</p>
 *
 * <p>Usage: executed by the identity domain test suite.</p>
 */
class UsernameTest {

    @Test
    void ofShouldNormalizeUsernameToLowercase() {
        Username username = Username.of("  Abir.Medjerab_01  ");

        assertEquals("abir.medjerab_01", username.value());
    }

    @Test
    void ofShouldRejectTooShortUsername() {
        assertThrows(InvalidValueObjectException.class, () -> Username.of("ab"));
    }

    @Test
    void ofShouldRejectUsernameStartingWithDigit() {
        assertThrows(InvalidValueObjectException.class, () -> Username.of("1abir"));
    }

    @Test
    void ofShouldRejectUnsupportedCharacters() {
        assertThrows(InvalidValueObjectException.class, () -> Username.of("abir medjerab"));
    }

    @Test
    void ofShouldRejectBlankUsername() {
        assertThrows(InvalidValueObjectException.class, () -> Username.of("   "));
    }
}
