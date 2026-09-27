/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ContactPointTargetReferenceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Verifies governed contact-point target reference validation.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContactPointTargetReferenceTest {

    @Test
    void acceptsEmployeeAndOrganizationUnitTargets() {
        assertEquals(
                ContactPointTargetType.EMPLOYEE,
                ContactPointTargetReference.from(" employee ", " emp-1 ").type()
        );
        assertEquals(
                ContactPointTargetType.ORGANIZATION_UNIT,
                ContactPointTargetReference.from("ORGANIZATION_UNIT", "unit-1").type()
        );
    }

    @Test
    void normalizesIdAndRejectsUnsupportedTargets() {
        var target = ContactPointTargetReference.from("EMPLOYEE", " emp-1 ");

        assertEquals("emp-1", target.targetId());
        assertThrows(
                InvalidOrganizationValueException.class,
                () -> ContactPointTargetReference.from("POSITION", "pos-1")
        );
        assertThrows(
                InvalidOrganizationValueException.class,
                () -> ContactPointTargetReference.from("PIPELINE", "pl-1")
        );
        assertThrows(
                InvalidOrganizationValueException.class,
                () -> ContactPointTargetReference.from("EMPLOYEE", " ")
        );
    }
}
