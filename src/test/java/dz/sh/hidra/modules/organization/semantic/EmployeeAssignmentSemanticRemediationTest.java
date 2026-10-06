/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.semantic
 *
 * @Description : Verifies employee assignment rejects missing or non-active units.
 *
 */
package dz.sh.hidra.modules.organization.semantic;

import dz.sh.hidra.modules.organization.application.command.AssignEmployeeCommand;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.application.service.EmployeeAssignmentApplicationService;
import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class EmployeeAssignmentSemanticRemediationTest {
    private static final Instant NOW = Instant.parse("2026-10-06T00:00:00Z");
    private final EmployeeAssignmentRepositoryPort assignments = mock(EmployeeAssignmentRepositoryPort.class);
    private final OrganizationUnitRepositoryPort units = mock(OrganizationUnitRepositoryPort.class);
    private final EmployeeAssignmentApplicationService service =
            new EmployeeAssignmentApplicationService(assignments, units);

    @ParameterizedTest
    @EnumSource(value = OrganizationUnitStatus.class, names = "ACTIVE", mode = EnumSource.Mode.EXCLUDE)
    void rejectsEveryNonActiveUnit(OrganizationUnitStatus status) {
        when(units.findById("unit-1")).thenReturn(Optional.of(unit(status)));
        assertThatThrownBy(() -> service.assignEmployee(command("unit-1")))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("ACTIVE");
        verifyNoInteractions(assignments);
    }

    @Test
    void rejectsMissingUnit() {
        when(units.findById("unit-1")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.assignEmployee(command("unit-1")))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("existing OrganizationUnit");
        verifyNoInteractions(assignments);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void rejectsAbsentUnitBeforeLookup(String id) {
        assertThatThrownBy(() -> service.assignEmployee(command(id)))
                .isInstanceOf(InvalidOrganizationValueException.class);
        verifyNoInteractions(units, assignments);
    }

    @Test
    void savesAssignmentForActiveUnitWithNormalizedIdentity() {
        when(units.findById("unit-1")).thenReturn(Optional.of(unit(OrganizationUnitStatus.ACTIVE)));
        when(assignments.save(any(EmployeeAssignment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        assertThat(service.assignEmployee(command(" unit-1 "))).isNotBlank();
        verify(assignments).save(any(EmployeeAssignment.class));
    }

    private static AssignEmployeeCommand command(String unit) {
        return new AssignEmployeeCommand("employee-1", unit, "position-1", null, NOW, null);
    }

    private static OrganizationUnit unit(OrganizationUnitStatus status) {
        return new OrganizationUnit("unit-1", "UNIT-001", null, "Unité", null, "type-1",
                null, status, NOW, null, NOW, NOW);
    }
}
