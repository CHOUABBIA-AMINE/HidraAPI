/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Verifies employee personal/birth data and lifecycle invariants.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeType;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    private static final Instant HIRED_AT = Instant.parse("2020-01-01T00:00:00Z");
    private static final Instant CREATED_AT = Instant.parse("2026-09-28T00:00:00Z");
    private static final Instant UPDATED_AT = Instant.parse("2026-09-28T01:00:00Z");

    @Test
    void preservesAndNormalizesBirthInformation() {
        Employee employee = employee(
                LocalDate.of(1990, 5, 17),
                " locality-1 ",
                " الجزائر ",
                " Alger ",
                " Algiers ",
                HIRED_AT,
                null
        );

        assertThat(employee.dateOfBirth()).isEqualTo(LocalDate.of(1990, 5, 17));
        assertThat(employee.birthLocalityId()).isEqualTo("locality-1");
        assertThat(employee.birthPlaceAr()).isEqualTo("الجزائر");
        assertThat(employee.birthPlaceFr()).isEqualTo("Alger");
        assertThat(employee.birthPlaceEn()).isEqualTo("Algiers");
        assertThat(employee.identityUserReference()).isNull();
    }

    @Test
    void acceptsFreeTextBirthplaceWithoutAdministrativeLocality() {
        Employee employee = employee(
                LocalDate.of(1985, 2, 10),
                null,
                null,
                "Paris",
                "Paris",
                HIRED_AT,
                null
        );

        assertThat(employee.birthLocalityId()).isNull();
        assertThat(employee.birthPlaceFr()).isEqualTo("Paris");
        assertThat(employee.birthPlaceEn()).isEqualTo("Paris");
    }

    @Test
    void rejectsMissingRequiredEmployeeIdentityAndLifecycleState() {
        assertThatThrownBy(() -> employeeWith(null, "EMP-1", EmployeeType.PERMANENT, EmployeeStatus.ACTIVE))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("Employee ID");

        assertThatThrownBy(() -> employeeWith("emp-1", " ", EmployeeType.PERMANENT, EmployeeStatus.ACTIVE))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("Employee number");

        assertThatThrownBy(() -> employeeWith("emp-1", "EMP-1", null, EmployeeStatus.ACTIVE))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("Employee type");

        assertThatThrownBy(() -> employeeWith("emp-1", "EMP-1", EmployeeType.PERMANENT, null))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("Employee status");
    }

    @Test
    void rejectsFutureBirthDate() {
        LocalDate future = LocalDate.now(ZoneOffset.UTC).plusDays(1);

        assertThatThrownBy(() -> employee(
                future,
                null,
                null,
                null,
                null,
                HIRED_AT,
                null
        ))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("date of birth");
    }

    @Test
    void enforcesHireTerminationChronology() {
        assertThatThrownBy(() -> employee(
                LocalDate.of(1990, 1, 1),
                null,
                null,
                null,
                null,
                null,
                Instant.parse("2025-01-01T00:00:00Z")
        ))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("requires a hire timestamp");

        assertThatThrownBy(() -> employee(
                LocalDate.of(1990, 1, 1),
                null,
                null,
                null,
                null,
                HIRED_AT,
                Instant.parse("2019-12-31T23:59:59Z")
        ))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("must not precede");
    }

    @Test
    void legacyConstructorRemainsCompatibleAndDoesNotInventBirthData() {
        @SuppressWarnings("removal")
        Employee employee = new Employee(
                "emp-1",
                "EMP-1",
                " أمين ",
                " شواعبية ",
                " Amine ",
                " Chouabbia ",
                null,
                null,
                null,
                null,
                EmployeeType.PERMANENT,
                EmployeeStatus.ACTIVE,
                " ",
                HIRED_AT,
                null,
                CREATED_AT,
                UPDATED_AT
        );

        assertThat(employee.id()).isEqualTo("emp-1");
        assertThat(employee.employeeNumber()).isEqualTo("EMP-1");
        assertThat(employee.firstNameAr()).isEqualTo("أمين");
        assertThat(employee.firstNameLt()).isEqualTo("Amine");
        assertThat(employee.dateOfBirth()).isNull();
        assertThat(employee.birthLocalityId()).isNull();
        assertThat(employee.birthPlaceAr()).isNull();
        assertThat(employee.birthPlaceFr()).isNull();
        assertThat(employee.birthPlaceEn()).isNull();
        assertThat(employee.identityUserReference()).isNull();
    }

    private static Employee employee(
            LocalDate dateOfBirth,
            String birthLocalityId,
            String birthPlaceAr,
            String birthPlaceFr,
            String birthPlaceEn,
            Instant hiredAt,
            Instant terminatedAt
    ) {
        return new Employee(
                " emp-1 ",
                " EMP-001 ",
                " أمين ",
                " شواعبية ",
                " Amine ",
                " Chouabbia ",
                null,
                null,
                dateOfBirth,
                birthLocalityId,
                birthPlaceAr,
                birthPlaceFr,
                birthPlaceEn,
                null,
                null,
                EmployeeType.PERMANENT,
                EmployeeStatus.ACTIVE,
                " ",
                hiredAt,
                terminatedAt,
                CREATED_AT,
                UPDATED_AT
        );
    }

    private static Employee employeeWith(
            String id,
            String employeeNumber,
            EmployeeType employeeType,
            EmployeeStatus status
    ) {
        return new Employee(
                id,
                employeeNumber,
                null,
                null,
                "Amine",
                "Chouabbia",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                employeeType,
                status,
                null,
                HIRED_AT,
                null,
                CREATED_AT,
                UPDATED_AT
        );
    }
}
