/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionCodeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Unit tests for PositionCode validation.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;

/**
 * Tests the position code value object.
 *
 * <p>Business role:
 * Verifies that operational positions and functions use stable normalized business codes.
 *
 * <p>Architecture role:
 * This test is limited to the domain value object.
 */
class PositionCodeTest {

    @Test
    void shouldNormalizePositionCode() {
        PositionCode code = PositionCode.of(" station_team_leader ");

        assertEquals("STATION_TEAM_LEADER", code.value());
    }

    @Test
    void shouldRejectBlankPositionCode() {
        assertThrows(InvalidValueObjectException.class, () -> PositionCode.of(" "));
    }

    @Test
    void shouldRejectInvalidPositionCodeCharacters() {
        assertThrows(InvalidValueObjectException.class, () -> PositionCode.of("STATION TEAM LEADER"));
    }
}
