/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
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
    private final dz.sh.hidra.modules.organization.application.port.in.EmployeeAppointmentLifecycleUseCase appointments=mock(dz.sh.hidra.modules.organization.application.port.in.EmployeeAppointmentLifecycleUseCase.class);
    private final EmployeeAssignmentRepositoryPort assignments = mock(EmployeeAssignmentRepositoryPort.class);
    private final OrganizationUnitRepositoryPort units = mock(OrganizationUnitRepositoryPort.class);
    private final EmployeeAssignmentApplicationService service =
            new EmployeeAssignmentApplicationService(appointments);

    @ParameterizedTest
    @EnumSource(value = OrganizationUnitStatus.class, names = "ACTIVE", mode = EnumSource.Mode.EXCLUDE)
    void rejectsEveryNonActiveUnit(OrganizationUnitStatus status) {
        when(appointments.createLegacy(any())).thenThrow(new InvalidOrganizationValueException("Only an ACTIVE organization unit can receive an assignment: "+status));
        assertThatThrownBy(() -> service.assignEmployee(command("unit-1")))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("ACTIVE");
        verifyNoInteractions(assignments);
    }

    @Test
    void rejectsMissingUnit() {
        when(appointments.createLegacy(any())).thenThrow(new InvalidOrganizationValueException("Employee assignment requires an existing OrganizationUnit."));
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
        verifyNoInteractions(units, assignments, appointments);
    }

    @Test
    void savesAssignmentForActiveUnitWithNormalizedIdentity() {
        var receipt=mock(dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.MutationReceipt.class);
        var version=mock(dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.PinnedVersion.class);
        when(receipt.after()).thenReturn(version);when(version.assignmentId()).thenReturn("assignment");
        when(appointments.createLegacy(any())).thenReturn(receipt);
        assertThat(service.assignEmployee(command(" unit-1 "))).isEqualTo("assignment");
        var selector=org.mockito.ArgumentCaptor.forClass(dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand.LegacyCreate.class);
        verify(appointments).createLegacy(selector.capture());assertThat(selector.getValue().organizationUnitId()).isEqualTo("unit-1");
        verifyNoInteractions(assignments,units);
    }

    private static AssignEmployeeCommand command(String unit) {
        return new AssignEmployeeCommand("employee-1", unit, "position-1", null, NOW, null);
    }

    private static OrganizationUnit unit(OrganizationUnitStatus status) {
        return new OrganizationUnit("unit-1", "UNIT-001", null, "Unité", null, "type-1",
                null, status, NOW, null, NOW, NOW);
    }

    @Test void legacyJpaWriterRejectsBeforeIssuingSqlAndRepositoryExposesNoCrudMutators() {
        var repository=mock(dz.sh.hidra.modules.organization.infrastructure.persistence.repository.EmployeeAssignmentJpaRepository.class);
        var adapter=new dz.sh.hidra.modules.organization.infrastructure.persistence.adapter.JpaEmployeeAssignmentRepositoryAdapter(repository);
        assertThatThrownBy(()->adapter.save(null)).isInstanceOf(UnsupportedOperationException.class);verifyNoInteractions(repository);
        for(var method:dz.sh.hidra.modules.organization.infrastructure.persistence.repository.EmployeeAssignmentJpaRepository.class.getMethods())
            assertThat(method.getName()).doesNotStartWith("save").doesNotStartWith("delete").isNotEqualTo("flush");
    }
    @Test void legacyQueryAdapterDelegatesSqlPagingWithoutEntityManagerScanning() {
        var em=mock(jakarta.persistence.EntityManager.class);
        var read=new dz.sh.hidra.modules.organization.infrastructure.query.JpaOrganizationAdministrationQueryAdapter(em,appointments);
        var page=new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.CurrentPage(java.util.List.of(),0,10,0,0,false);
        when(appointments.currentAssignments(any())).thenReturn(page);
        assertThat(read.assignments("employee-1",null,null,0,10).totalElements()).isZero();
        verify(appointments).currentAssignments(new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Current(null,"employee-1",null,null,0,10,false));
        verifyNoInteractions(em);
    }
}
