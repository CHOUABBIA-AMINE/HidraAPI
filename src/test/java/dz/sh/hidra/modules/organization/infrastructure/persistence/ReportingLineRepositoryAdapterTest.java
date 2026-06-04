/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineRepositoryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Unit tests for employee-owned reporting line persistence mapping.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.ReportingLine;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.EmployeeJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ReportingLineJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.EmployeeJpaRepository;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.EmployeeRepositoryAdapter;

/**
 * Tests employee-owned reporting line persistence.
 *
 * <p>Business role:
 * Verifies that matrix reporting lines are persisted as part of the employee persistence aggregate
 * because ORG-013 does not introduce an independent reporting-line repository adapter.
 *
 * <p>Architecture role:
 * This is an infrastructure persistence unit test. It uses EmployeeRepositoryAdapter and a mocked
 * EmployeeJpaRepository to verify reporting-line persistence mapping without loading a database.
 *
 * <p>Validation:
 * The test verifies reporting line type, manager employee id, primary-line flag, effective dates,
 * and description round-trip through the persistence mapper.
 */
class ReportingLineRepositoryAdapterTest {

    private final OrganizationPersistenceMapper mapper = new OrganizationPersistenceMapper();

    @Test
    void shouldPersistEmployeeReportingLineThroughEmployeeRepositoryAdapter() {
        EmployeeJpaRepository jpaRepository = mock(EmployeeJpaRepository.class);
        EmployeeRepositoryAdapter adapter = new EmployeeRepositoryAdapter(jpaRepository, mapper);

        Employee employee = employee("EMP-RPT-PER-001");
        Employee manager = employee("EMP-RPT-PER-002");
        ReportingLine reportingLine = ReportingLine.create(
                employee.id(),
                manager.id(),
                ReportingLineType.LINE,
                true,
                LocalDate.now(),
                "Primary line reporting relationship");

        Employee employeeWithReportingLine = employee.activate().addReportingLine(reportingLine);

        when(jpaRepository.save(any(EmployeeJpaEntity.class))).thenAnswer(invocation -> {
            EmployeeJpaEntity entity = invocation.getArgument(0);
            populateReportingLineEmployeeIds(entity);
            return entity;
        });

        Employee savedEmployee = adapter.save(employeeWithReportingLine);

        assertEquals(employee.id(), savedEmployee.id());
        assertEquals(1, savedEmployee.reportingLines().size());
        assertEquals(ReportingLineType.LINE, savedEmployee.reportingLines().get(0).type());
        assertEquals(manager.id(), savedEmployee.reportingLines().get(0).managerEmployeeId());
        assertTrue(savedEmployee.reportingLines().get(0).primaryLine());
        assertEquals("Primary line reporting relationship", savedEmployee.reportingLines().get(0).optionalDescription().orElseThrow());
        verify(jpaRepository).save(any(EmployeeJpaEntity.class));
    }

    @Test
    void shouldMapReportingLineFieldsToPersistenceEntity() {
        Employee employee = employee("EMP-RPT-PER-003").activate();
        Employee manager = employee("EMP-RPT-PER-004");
        ReportingLine reportingLine = ReportingLine.create(
                employee.id(),
                manager.id(),
                ReportingLineType.FUNCTIONAL,
                false,
                LocalDate.now(),
                "Functional reporting relationship");

        EmployeeJpaEntity entity = mapper.toEntity(employee.addReportingLine(reportingLine));

        assertEquals(1, entity.getReportingLines().size());
        ReportingLineJpaEntity reportingLineEntity = entity.getReportingLines().get(0);
        assertEquals(manager.id().value(), reportingLineEntity.getManagerEmployeeId());
        assertEquals("FUNCTIONAL", reportingLineEntity.getReportingLineType());
        assertEquals(false, reportingLineEntity.isPrimaryLine());
        assertEquals("Functional reporting relationship", reportingLineEntity.getDescription());
    }

    private static Employee employee(String employeeNumber) {
        return Employee.register(
                EmployeeNumber.of(employeeNumber),
                EmployeeFullName.of("Abir MEDJERAB"),
                EmployeeEmail.of("abir.medjerab@example.com"),
                null);
    }

    private static void populateReportingLineEmployeeIds(EmployeeJpaEntity employeeEntity) throws ReflectiveOperationException {
        for (ReportingLineJpaEntity reportingLineEntity : employeeEntity.getReportingLines()) {
            Field employeeIdField = ReportingLineJpaEntity.class.getDeclaredField("employeeId");
            employeeIdField.setAccessible(true);
            employeeIdField.set(reportingLineEntity, employeeEntity.getId());
        }
    }
}
