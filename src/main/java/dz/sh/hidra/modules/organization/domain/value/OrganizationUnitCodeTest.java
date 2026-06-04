/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitCodeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Unit tests for OrganizationUnitCode validation.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;

/**
 * Tests the organization unit code value object.
 *
 * <p>Business role:
 * Verifies that organization units such as regions, departments, teams, and station-as-organization
 * units use stable normalized business codes.
 *
 * <p>Architecture role:
 * This is a domain unit test and does not load infrastructure.
 */
class OrganizationUnitCodeTest {

    @Test
    void shouldNormalizeOrganizationUnitCode() {
        OrganizationUnitCode code = OrganizationUnitCode.of(" cs_east_01 ");

        assertEquals("CS_EAST_01", code.value());
    }

    @Test
    void shouldRejectBlankOrganizationUnitCode() {
        assertThrows(InvalidValueObjectException.class, () -> OrganizationUnitCode.of(" "));
    }

    @Test
    void shouldRejectInvalidOrganizationUnitCodeCharacters() {
        assertThrows(InvalidValueObjectException.class, () -> OrganizationUnitCode.of("CS.EAST.01"));
    }
}
