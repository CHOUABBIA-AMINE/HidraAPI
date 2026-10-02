/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssigneeTypeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Verifies governed responsibility assignee discriminator parsing.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ResponsibilityAssigneeTypeTest {

    @Test
    void parsesSupportedLegacyText() {
        assertEquals(
                ResponsibilityAssigneeType.EMPLOYEE,
                ResponsibilityAssigneeType.from(" employee ")
        );
        assertEquals(
                ResponsibilityAssigneeType.ORGANIZATION_UNIT,
                ResponsibilityAssigneeType.from("ORGANIZATION_UNIT")
        );
    }

    @Test
    void rejectsBlankAndUnsupportedText() {
        assertThrows(IllegalArgumentException.class, () -> ResponsibilityAssigneeType.from(" "));
        assertThrows(IllegalArgumentException.class, () -> ResponsibilityAssigneeType.from("TEAM"));
    }
}
