/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CompanyBoundEmployeeAppointmentTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Verifies exact unit matching and retention of complete historical binding revisions.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAppointmentChange.VersionedAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompanyBoundEmployeeAppointmentTest {
    private static final Instant START = Instant.parse("2026-10-01T00:00:00Z");

    private static VersionedAssignment appointment() {
        return new VersionedAssignment(5, new EmployeeAssignment("assignment", "employee", "unit", "position",
                AssignmentType.PRIMARY, START, null, AssignmentStatus.ACTIVE, START, START));
    }

    private static OrganizationUnitCompanyBinding binding(long revision, String unit, String company) {
        return new OrganizationUnitCompanyBinding("binding", revision, unit, company, START);
    }

    @Test void retainsExactAppointmentAndBindingSnapshots() {
        var snapshot = appointment();
        var pin = binding(2, "unit", "company");
        var historical = new CompanyBoundEmployeeAppointment(snapshot, pin);
        var newer = new CompanyBoundEmployeeAppointment(snapshot, binding(3, "unit", "other-company"));
        assertSame(snapshot, historical.appointment());
        assertSame(pin, historical.binding());
        assertEquals(2, historical.binding().revision());
        assertEquals("company", historical.binding().companyPartyId());
        assertNotEquals(historical, newer);
        assertEquals(historical, new CompanyBoundEmployeeAppointment(appointment(), binding(2, "unit", "company")));
    }

    @Test void rejectsMissingAppointmentOrBinding() {
        assertThrows(InvalidOrganizationValueException.class,
                () -> new CompanyBoundEmployeeAppointment(null, binding(1, "unit", "company")));
        assertThrows(InvalidOrganizationValueException.class,
                () -> new CompanyBoundEmployeeAppointment(appointment(), null));
        assertThrows(InvalidOrganizationValueException.class,
                () -> new CompanyBoundEmployeeAppointment(null, null));
    }

    @Test void rejectsDifferentUnitEvenWithSameCompany() {
        assertThrows(InvalidOrganizationValueException.class,
                () -> new CompanyBoundEmployeeAppointment(appointment(), binding(1, "foreign-unit", "company")));
    }
}
