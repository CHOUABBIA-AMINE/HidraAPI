/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateOrganizationUnitCommandTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Verifies OrganizationCode adoption at the active organization-unit creation boundary.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import dz.sh.hidra.modules.organization.domain.value.OrganizationCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateOrganizationUnitCommandTest {

    @Test
    void carriesCanonicalOrganizationCode() {
        CreateOrganizationUnitCommand command = new CreateOrganizationUnitCommand(
                OrganizationCode.of(" cs_east_01 "),
                null,
                null,
                "Station East 01",
                "type-station",
                null,
                OrganizationUnitStatus.ACTIVE,
                null
        );

        assertEquals("CS_EAST_01", command.code().value());
    }

    @Test
    @SuppressWarnings("removal")
    void textualCompatibilityConstructorDelegatesToOrganizationCode() {
        CreateOrganizationUnitCommand command = new CreateOrganizationUnitCommand(
                " region_center ",
                null,
                null,
                "Central Region",
                "type-region",
                null,
                OrganizationUnitStatus.ACTIVE,
                null
        );

        assertEquals("REGION_CENTER", command.code().value());
    }
}
