/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationPersistenceMapperEmployeeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.mapper
 *
 * @Description : Verifies Employee persistence mapping preserves canonical personal state.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.EmployeeJpaEntity;
import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class OrganizationPersistenceMapperEmployeeTest {

    @Test
    void employeeRoundTripPreservesBirthAndCompatibilityState() {
        Instant hiredAt = Instant.parse("2015-01-10T08:00:00Z");
        Instant createdAt = Instant.parse("2026-09-28T08:00:00Z");
        Instant updatedAt = Instant.parse("2026-09-28T09:00:00Z");

        Employee employee = new Employee(
                "employee-1",
                "E-0001",
                "أمين",
                "مثال",
                "Amine",
                "Example",
                "أمين مثال",
                "Amine Example",
                LocalDate.of(1990, 5, 3),
                "locality-16-001",
                "الجزائر",
                "Alger",
                "Algiers",
                "amine@example.test",
                "+213555000001",
                EmployeeType.PERMANENT,
                EmployeeStatus.ACTIVE,
                "identity-user-1",
                hiredAt,
                null,
                createdAt,
                updatedAt
        );

        EmployeeJpaEntity entity = OrganizationPersistenceMapper.toEntity(employee);

        assertThat(entity.dateOfBirth()).isEqualTo(employee.dateOfBirth());
        assertThat(entity.birthLocalityId()).isEqualTo(employee.birthLocalityId());
        assertThat(entity.birthPlaceAr()).isEqualTo(employee.birthPlaceAr());
        assertThat(entity.birthPlaceFr()).isEqualTo(employee.birthPlaceFr());
        assertThat(entity.birthPlaceEn()).isEqualTo(employee.birthPlaceEn());

        Employee restored = OrganizationPersistenceMapper.toDomain(entity);

        assertThat(restored).isEqualTo(employee);
    }
}
