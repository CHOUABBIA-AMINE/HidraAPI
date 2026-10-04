/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPeriodSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Planning Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.semantic
 *
 * @Description : Verifies HMR-006 PlanningPeriod semantic remediation.
 *
 */
package dz.sh.hidra.modules.planning.semantic;

import dz.sh.hidra.modules.planning.application.command.CreatePlanningPeriodCommand;
import dz.sh.hidra.modules.planning.application.port.out.PlanningPeriodRepositoryPort;
import dz.sh.hidra.modules.planning.application.service.PlanningPeriodApplicationService;
import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.model.PlanningPeriod;
import dz.sh.hidra.modules.planning.domain.value.PlanningPeriodStatus;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlanningPeriodSemanticRemediationTest {

    private static final Instant START = Instant.parse("2026-10-04T00:00:00Z");
    private static final Instant END = Instant.parse("2026-10-05T00:00:00Z");

    @Test
    void rejectsZeroLengthPeriod() {
        assertThatThrownBy(() -> period(START, START, "Africa/Algiers", PlanningPeriodStatus.OPEN))
                .isInstanceOf(InvalidPlanningValueException.class)
                .hasMessageContaining("before period end");
    }

    @Test
    void rejectsBlankFrenchName() {
        assertThatThrownBy(() -> new PlanningPeriod(
                "period-1", "P-1", null, " ", null, "period-type",
                START, END, "Africa/Algiers", PlanningPeriodStatus.OPEN,
                "actor-1", START, START
        ))
                .isInstanceOf(InvalidPlanningValueException.class)
                .hasMessageContaining("French name");
    }

    @Test
    void rejectsInvalidIanaTimeZone() {
        assertThatThrownBy(() -> period(START, END, "Not/AZone", PlanningPeriodStatus.OPEN))
                .isInstanceOf(InvalidPlanningValueException.class)
                .hasMessageContaining("IANA");
    }

    @Test
    void closedPeriodDoesNotAllowNewPlanRevisions() {
        assertThat(period(START, END, "Africa/Algiers", PlanningPeriodStatus.CLOSED)
                .allowsNewPlanRevisions()).isFalse();
        assertThat(period(START, END, "Africa/Algiers", PlanningPeriodStatus.OPEN)
                .allowsNewPlanRevisions()).isTrue();
    }

    @Test
    void rejectsDuplicateCodeBeforeSave() {
        PlanningPeriodRepositoryPort repository = mock(PlanningPeriodRepositoryPort.class);
        PlanningPeriodApplicationService service = new PlanningPeriodApplicationService(repository);
        CreatePlanningPeriodCommand command = command("P-1", "period-type", null);

        when(repository.existsByCode("P-1")).thenReturn(true);

        assertThatThrownBy(() -> service.createPlanningPeriod(command))
                .isInstanceOf(InvalidPlanningValueException.class)
                .hasMessageContaining("unique");

        verify(repository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void rejectsPeriodTypeOutsideActivePeriodTypeFamily() {
        PlanningPeriodRepositoryPort repository = mock(PlanningPeriodRepositoryPort.class);
        PlanningPeriodApplicationService service = new PlanningPeriodApplicationService(repository);
        CreatePlanningPeriodCommand command = command("P-1", "wrong-type", "Africa/Algiers");

        when(repository.existsByCode("P-1")).thenReturn(false);
        when(repository.isActivePeriodType("wrong-type")).thenReturn(false);

        assertThatThrownBy(() -> service.createPlanningPeriod(command))
                .isInstanceOf(InvalidPlanningValueException.class)
                .hasMessageContaining("PERIOD_TYPE");

        verify(repository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void defaultsBlankTimeZoneToAfricaAlgiers() {
        PlanningPeriodRepositoryPort repository = mock(PlanningPeriodRepositoryPort.class);
        PlanningPeriodApplicationService service = new PlanningPeriodApplicationService(repository);
        CreatePlanningPeriodCommand command = command("P-1", "period-type", " ");

        when(repository.existsByCode("P-1")).thenReturn(false);
        when(repository.isActivePeriodType("period-type")).thenReturn(true);
        when(repository.save(org.mockito.ArgumentMatchers.any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.createPlanningPeriod(command);

        assertThat(result.timeZone()).isEqualTo("Africa/Algiers");
    }

    @Test
    void migrationEnforcesUniqueCodeAndStrictInterval() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_006__hmr_006_planning_planning_period.sql"
        ));

        assertThat(sql).contains("CREATE UNIQUE INDEX uk_hmr006_planning_period_code");
        assertThat(sql).contains("CHECK (period_start < period_end)");
    }

    private static PlanningPeriod period(
            Instant start,
            Instant end,
            String timeZone,
            PlanningPeriodStatus status
    ) {
        return new PlanningPeriod(
                "period-1", "P-1", null, "Période", null, "period-type",
                start, end, timeZone, status, "actor-1", START, START
        );
    }

    private static CreatePlanningPeriodCommand command(String code, String periodTypeId, String timeZone) {
        return new CreatePlanningPeriodCommand(
                code, null, "Période", null, periodTypeId,
                START, END, timeZone, "actor-1"
        );
    }
}
