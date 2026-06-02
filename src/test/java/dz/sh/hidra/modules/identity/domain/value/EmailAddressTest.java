/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmailAddressTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Tests identity email address validation.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests identity email address validation.
 *
 * <p>Business role: verifies the email address accepted for identity user contact and
 * login information.</p>
 *
 * <p>Architecture role: domain test for the email value object only. It does not depend
 * on Spring, JPA, platform security, or organization modules.</p>
 *
 * <p>Validation responsibility: covers lowercase normalization, blank rejection, malformed
 * value rejection, and maximum length enforcement.</p>
 *
 * <p>Usage: executed by the identity domain test suite.</p>
 */
class EmailAddressTest {

    @Test
    void ofShouldNormalizeEmailAddressToLowercase() {
        EmailAddress emailAddress = EmailAddress.of("  Abir.Medjerab@Sonatrach.DZ  ");

        assertEquals("abir.medjerab@sonatrach.dz", emailAddress.value());
    }

    @Test
    void ofShouldRejectMalformedEmailAddress() {
        assertThrows(InvalidValueObjectException.class, () -> EmailAddress.of("abir-at-sonatrach"));
    }

    @Test
    void ofShouldRejectBlankEmailAddress() {
        assertThrows(InvalidValueObjectException.class, () -> EmailAddress.of("   "));
    }

    @Test
    void ofShouldRejectTooLongEmailAddress() {
        String localPart = "a".repeat(245);
        String tooLongEmail = localPart + "@sonatrach.dz";

        assertThrows(InvalidValueObjectException.class, () -> EmailAddress.of(tooLongEmail));
    }
}
