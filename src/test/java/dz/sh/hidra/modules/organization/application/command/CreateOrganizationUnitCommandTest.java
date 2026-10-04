/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateOrganizationUnitCommandTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
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
import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateOrganizationUnitCommandTest {

    private static final Instant VALID_FROM = Instant.parse("2026-10-04T00:00:00Z");

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
    void rejectsMissingValidFrom() {
        assertThatThrownBy(() -> new CreateOrganizationUnitCommand(
                OrganizationCode.of("UNIT-1"),
                null,
                null,
                null,
                "type-1",
                null,
                OrganizationUnitStatus.ACTIVE,
                null
        ))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("validFrom is required");
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
