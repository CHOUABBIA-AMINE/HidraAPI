/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitTypeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Unit tests for organization unit type catalog references.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests organization unit type catalog references.
 */
class OrganizationUnitTypeTest {

    @Test
    void shouldSupportStationAsOrganizationUnit() {
        assertTrue(OrganizationUnitTypeReference.STATION.isStationOrganizationUnit());
    }

    @Test
    void shouldNotTreatNonStationTypeAsStationOrganizationUnit() {
        assertFalse(OrganizationUnitTypeReference.REGION.isStationOrganizationUnit());
    }

    @Test
    void shouldResolveSeededOrganizationUnitTypeIdsAndCodes() {
        assertEquals("organization-out-division", OrganizationUnitTypeReference.ofCode("division").id());
        assertEquals("DIVISION", OrganizationUnitTypeReference.ofId("organization-out-division").name());
        assertEquals(OrganizationUnitTypeReference.DIVISION, OrganizationUnitTypeReference.ofCode("DIVISION"));
    }
}
