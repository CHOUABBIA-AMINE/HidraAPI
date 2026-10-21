/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CompanyBoundEmployeeAppointment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Pins a versioned appointment snapshot to its complete immutable unit binding.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAppointmentChange.VersionedAssignment;

/** Retains history without resolving a pin through a mutable current-binding pointer. */
public record CompanyBoundEmployeeAppointment(
        VersionedAssignment appointment,
        OrganizationUnitCompanyBinding binding
) {
    public CompanyBoundEmployeeAppointment {
        if (appointment == null || binding == null) {
            throw new InvalidOrganizationValueException("Appointment snapshot and binding are required.");
        }
        if (!appointment.assignment().organizationUnitId().equals(binding.organizationUnitId())) {
            throw new InvalidOrganizationValueException("Appointment must match its binding's exact unit.");
        }
    }
}
