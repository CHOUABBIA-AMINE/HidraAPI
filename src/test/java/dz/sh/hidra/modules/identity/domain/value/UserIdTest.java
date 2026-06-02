/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserIdTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Tests identity user identifier validation.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests identity user identifier validation.
 *
 * <p>Business role: verifies that user identifiers cannot be missing or blank.</p>
 *
 * <p>Architecture role: domain test for the identity value object only. It does not
 * depend on Spring, JPA, platform security, or organization modules.</p>
 *
 * <p>Validation responsibility: covers accepted values, trimming, blank rejection, and
 * generated identifier creation.</p>
 *
 * <p>Usage: executed by the identity domain test suite.</p>
 */
class UserIdTest {

    @Test
    void ofShouldTrimValidIdentifier() {
        UserId userId = UserId.of("  user-123  ");

        assertEquals("user-123", userId.value());
    }

    @Test
    void ofShouldRejectBlankIdentifier() {
        assertThrows(InvalidValueObjectException.class, () -> UserId.of("   "));
    }

    @Test
    void ofShouldRejectNullIdentifier() {
        assertThrows(InvalidValueObjectException.class, () -> UserId.of(null));
    }

    @Test
    void newIdShouldCreateNonBlankIdentifier() {
        UserId userId = assertDoesNotThrow(UserId::newId);

        assertFalse(userId.value().isBlank());
    }
}
