/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineTypeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Unit tests for ReportingLineType matrix reporting values.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests reporting line type values.
 *
 * <p>Business role:
 * Verifies that organization supports hierarchical and matrix reporting line types.
 *
 * <p>Architecture role:
 * This is a pure enum test with no infrastructure dependencies.
 */
class ReportingLineTypeTest {

    @Test
    void shouldSupportPrimaryLineReportingType() {
        assertTrue(ReportingLineType.valueOf("LINE") == ReportingLineType.LINE);
    }

    @Test
    void shouldSupportMatrixReportingTypes() {
        assertTrue(ReportingLineType.valueOf("OPERATIONAL") == ReportingLineType.OPERATIONAL);
        assertTrue(ReportingLineType.valueOf("FUNCTIONAL") == ReportingLineType.FUNCTIONAL);
        assertTrue(ReportingLineType.valueOf("DOTTED_LINE") == ReportingLineType.DOTTED_LINE);
    }

    @Test
    void shouldAllowMultipleFunctionalButNotLineReportingRelationships() {
        assertTrue(ReportingLineType.FUNCTIONAL.allowsMultipleActiveLines());
        assertFalse(ReportingLineType.LINE.allowsMultipleActiveLines());
    }
}
