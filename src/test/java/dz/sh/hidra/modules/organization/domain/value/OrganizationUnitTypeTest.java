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
 * @Description : Unit tests for OrganizationUnitType station support.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests organization unit type values.
 *
 * <p>Business role:
 * Verifies that station-as-organization-unit is explicitly supported while physical station assets
 * remain outside the organization module.
 *
 * <p>Architecture role:
 * This is a pure enum test with no infrastructure dependencies.
 */
class OrganizationUnitTypeTest {

    @Test
    void shouldSupportStationAsOrganizationUnit() {
        assertTrue(OrganizationUnitType.STATION.isStationOrganizationUnit());
    }

    @Test
    void shouldNotTreatNonStationTypeAsStationOrganizationUnit() {
        assertFalse(OrganizationUnitType.REGION.isStationOrganizationUnit());
    }

    @Test
    void shouldContainOperationalOrganizationTypes() {
        assertTrue(OrganizationUnitType.valueOf("DIVISION") == OrganizationUnitType.DIVISION);
        assertTrue(OrganizationUnitType.valueOf("DEPARTMENT") == OrganizationUnitType.DEPARTMENT);
        assertTrue(OrganizationUnitType.valueOf("TEAM") == OrganizationUnitType.TEAM);
    }
}
