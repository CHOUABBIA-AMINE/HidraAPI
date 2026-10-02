/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingSubjectReferenceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Verifies governed reporting-subject reference validation.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReportingSubjectReferenceTest {

    @Test
    void acceptsOnlyGovernedOrganizationSubjectTypes() {
        assertEquals(
                ReportingSubjectType.EMPLOYEE,
                ReportingSubjectReference.from(" employee ", " emp-1 ").type()
        );
        assertEquals(
                ReportingSubjectType.POSITION,
                ReportingSubjectReference.from("POSITION", "pos-1").type()
        );
        assertEquals(
                ReportingSubjectType.ORGANIZATION_UNIT,
                ReportingSubjectReference.from("organization_unit", "unit-1").type()
        );
    }

    @Test
    void normalizesTargetIdAndRejectsUnsupportedDiscriminators() {
        var reference = ReportingSubjectReference.from("EMPLOYEE", " emp-1 ");

        assertEquals("emp-1", reference.targetId());
        assertThrows(
                InvalidOrganizationValueException.class,
                () -> ReportingSubjectReference.from("PIPELINE", "pl-1")
        );
        assertThrows(
                InvalidOrganizationValueException.class,
                () -> ReportingSubjectReference.from("EMPLOYEE", " ")
        );
    }
}
