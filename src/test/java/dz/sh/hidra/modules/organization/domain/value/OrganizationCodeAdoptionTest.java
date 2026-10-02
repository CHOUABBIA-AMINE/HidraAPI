/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationCodeAdoptionTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Verifies canonical OrganizationCode normalization across stable reference/master models.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.AdministrativeDistrict;
import dz.sh.hidra.modules.organization.domain.model.AdministrativeLocality;
import dz.sh.hidra.modules.organization.domain.model.AdministrativeState;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnitType;
import dz.sh.hidra.modules.organization.domain.model.Position;
import dz.sh.hidra.modules.organization.domain.model.Shift;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrganizationCodeAdoptionTest {

    private static final Instant NOW = Instant.parse("2026-09-27T16:00:00Z");

    @Test
    void normalizesStableBusinessCodesAndRejectsBlankValues() {
        assertEquals("OU_EAST-01", OrganizationCode.of(" ou_east-01 ").value());
        assertEquals("16", OrganizationCode.of(" 16 ").value());

        assertThrows(
                InvalidOrganizationValueException.class,
                () -> OrganizationCode.of(" ")
        );
        assertThrows(
                InvalidOrganizationValueException.class,
                () -> OrganizationCode.of(null)
        );
    }

    @Test
    void allSupportedReferenceAndMasterModelsApplyTheSameCodePolicy() {
        AdministrativeState state = new AdministrativeState(
                "state-1", " 16 ", null, null, "Algiers", true, NOW, NOW
        );
        AdministrativeDistrict district = new AdministrativeDistrict(
                "district-1", "state-1", " bir_mourad_raïs ",
                null, null, "Bir Mourad Rais", true, NOW, NOW
        );
        AdministrativeLocality locality = new AdministrativeLocality(
                "locality-1", "district-1", " hydra ",
                null, null, "Hydra", "16035", true, NOW, NOW
        );
        OrganizationUnitType unitType = new OrganizationUnitType(
                "type-1", " station_unit ", OrganizationUnitKind.STATION_UNIT,
                null, null, "Station", null, null, null, true, NOW, NOW
        );
        OrganizationUnit unit = new OrganizationUnit(
                "unit-1", " cs_east_01 ", null, null, "Station East",
                "type-1", null, OrganizationUnitStatus.ACTIVE,
                NOW, null, NOW, NOW
        );
        Position position = new Position(
                "position-1", " station_team_leader ", null, null, "Team Leader",
                PositionLevel.SUPERVISOR, null, null, null,
                PositionStatus.ACTIVE, NOW, NOW
        );
        Shift shift = new Shift(
                "shift-1", " day_shift ", null, null, "Day Shift",
                ShiftType.DAY, "08:00", "16:00", "Africa/Algiers",
                true, NOW, NOW
        );

        assertEquals("16", state.code());
        assertEquals("BIR_MOURAD_RAÏS", district.code());
        assertEquals("HYDRA", locality.code());
        assertEquals("STATION_UNIT", unitType.code());
        assertEquals("CS_EAST_01", unit.code());
        assertEquals("STATION_TEAM_LEADER", position.code());
        assertEquals("DAY_SHIFT", shift.code());
    }
}
