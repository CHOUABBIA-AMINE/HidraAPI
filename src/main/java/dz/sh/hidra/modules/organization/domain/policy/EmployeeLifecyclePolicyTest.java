/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeLifecyclePolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.policy
 *
 * @Description : Unit tests for EmployeeLifecyclePolicy rules.
 *
 */
package dz.sh.hidra.modules.organization.domain.policy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.organization.domain.exception.EmployeeLifecycleException;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;

/**
 * Tests employee lifecycle policy.
 *
 * <p>Business role:
 * Verifies allowed lifecycle transitions for real operational employees.
 *
 * <p>Architecture role:
 * This is a pure domain policy test with no infrastructure dependencies.
 */
class EmployeeLifecyclePolicyTest {

    private final EmployeeLifecyclePolicy policy = new EmployeeLifecyclePolicy();

    @Test
    void shouldAllowActivatingRegisteredEmployee() {
        assertDoesNotThrow(() -> policy.ensureCanActivate(registeredEmployee("EMP-LIFE-001")));
    }

    @Test
    void shouldRejectActivatingDisabledEmployee() {
        Employee disabledEmployee = registeredEmployee("EMP-LIFE-002").disable();

        assertThrows(EmployeeLifecycleException.class, () -> policy.ensureCanActivate(disabledEmployee));
    }

    @Test
    void shouldAllowSuspendingActiveEmployee() {
        Employee activeEmployee = registeredEmployee("EMP-LIFE-003").activate();

        assertDoesNotThrow(() -> policy.ensureCanSuspend(activeEmployee));
    }

    @Test
    void shouldRejectSuspendingRegisteredEmployee() {
        Employee registeredEmployee = registeredEmployee("EMP-LIFE-004");

        assertThrows(EmployeeLifecycleException.class, () -> policy.ensureCanSuspend(registeredEmployee));
    }

    @Test
    void shouldRejectDisablingAlreadyDisabledEmployee() {
        Employee disabledEmployee = registeredEmployee("EMP-LIFE-005").disable();

        assertThrows(EmployeeLifecycleException.class, () -> policy.ensureCanDisable(disabledEmployee));
    }

    private static Employee registeredEmployee(String employeeNumber) {
        return Employee.register(
                EmployeeNumber.of(employeeNumber),
                EmployeeFullName.of("Abir MEDJERAB"),
                EmployeeEmail.of("abir.medjerab@example.com"),
                null);
    }
}
