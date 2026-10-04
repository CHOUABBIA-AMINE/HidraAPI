/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ShiftSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.semantic
 *
 * @Description : Verifies HMR-021 Shift schedule requiredness alignment.
 *
 */
package dz.sh.hidra.modules.organization.semantic;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.Shift;
import dz.sh.hidra.modules.organization.domain.value.ShiftType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShiftSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void startTimeIsRequired() {
        assertThatThrownBy(() -> shift(" ", "16:00", "Africa/Algiers"))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("start time is required");
    }

    @Test
    void endTimeIsRequired() {
        assertThatThrownBy(() -> shift("08:00", null, "Africa/Algiers"))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("end time is required");
    }

    @Test
    void timezoneIsRequired() {
        assertThatThrownBy(() -> shift("08:00", "16:00", " "))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("timezone is required");
    }

    @Test
    void requiredScheduleTextIsTrimmedWithoutInventingFormatRules() {
        Shift shift = shift(" 08:00 ", " 16:00 ", " Africa/Algiers ");

        assertThat(shift.startTime()).isEqualTo("08:00");
        assertThat(shift.endTime()).isEqualTo("16:00");
        assertThat(shift.timezone()).isEqualTo("Africa/Algiers");
    }

    @Test
    void migrationRejectsBlankScheduleTextWithoutParsingIt() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_021__hmr_021_organization_shift.sql"
        ));

        assertThat(sql).contains("ck_hmr021_shift_start_time_nonblank");
        assertThat(sql).contains("ck_hmr021_shift_end_time_nonblank");
        assertThat(sql).contains("ck_hmr021_shift_timezone_nonblank");
        assertThat(sql).contains("btrim(start_time) <> ''");
        assertThat(sql).contains("btrim(end_time) <> ''");
        assertThat(sql).contains("btrim(timezone) <> ''");
        assertThat(sql).doesNotContain("to_timestamp");
        assertThat(sql).doesNotContain("AT TIME ZONE");
    }

    private static Shift shift(String startTime, String endTime, String timezone) {
        return new Shift(
                "shift-1",
                "DAY_SHIFT",
                null,
                "Quart de jour",
                "Day Shift",
                ShiftType.DAY,
                startTime,
                endTime,
                timezone,
                true,
                NOW,
                NOW
        );
    }
}
