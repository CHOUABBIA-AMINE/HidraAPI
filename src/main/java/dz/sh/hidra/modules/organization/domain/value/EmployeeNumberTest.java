/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeNumberTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Unit tests for EmployeeNumber validation.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;

/**
 * Tests the employee number value object.
 *
 * <p>Business role:
 * Verifies that organization employees use stable, normalized, and valid business numbers.
 *
 * <p>Architecture role:
 * This is a domain unit test and does not load Spring, JPA, persistence, identity, topology, or API
 * infrastructure.
 */
class EmployeeNumberTest {

    @Test
    void shouldNormalizeEmployeeNumber() {
        EmployeeNumber employeeNumber = EmployeeNumber.of(" emp-000123 ");

        assertEquals("EMP-000123", employeeNumber.value());
    }

    @Test
    void shouldRejectBlankEmployeeNumber() {
        assertThrows(InvalidValueObjectException.class, () -> EmployeeNumber.of("   "));
    }

    @Test
    void shouldRejectInvalidEmployeeNumberCharacters() {
        assertThrows(InvalidValueObjectException.class, () -> EmployeeNumber.of("EMP.000123"));
    }
}
