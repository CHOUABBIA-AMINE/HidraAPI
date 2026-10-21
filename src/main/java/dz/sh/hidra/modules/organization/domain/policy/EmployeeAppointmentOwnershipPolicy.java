/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentOwnershipPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.policy
 *
 * @Description : Validates pinned appointment ownership across immutable lifecycle proposals.
 *
 */
package dz.sh.hidra.modules.organization.domain.policy;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.CompanyBoundEmployeeAppointment;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAppointmentChange;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnitCompanyBinding;

/**
 * Pure consistency checks on supplied facts. Current ownership, Party eligibility,
 * authorization, active overlap policy and transaction evidence remain runtime duties.
 */
public final class EmployeeAppointmentOwnershipPolicy {

    public void validateCreate(CompanyBoundEmployeeAppointment created) {
        require(created != null, "A bound appointment is required.");
        require(created.appointment().version() == 1, "A new appointment must start at version one.");
    }

    public void validateChange(
            EmployeeAppointmentChange change,
            CompanyBoundEmployeeAppointment before,
            CompanyBoundEmployeeAppointment after,
            CompanyBoundEmployeeAppointment successor
    ) {
        require(change != null && before != null && after != null,
                "Change and bound before/after snapshots are required.");
        require(change.before().equals(before.appointment())
                        && change.after().equals(after.appointment()),
                "Bound snapshots must exactly match the versioned proposal.");
        requireSameCompany(before, after);
        requireConsistentPin(before.binding(), after.binding());

        if (before.appointment().assignment().organizationUnitId()
                .equals(after.appointment().assignment().organizationUnitId())) {
            require(before.binding().equals(after.binding()),
                    "A source or same-unit change must retain its complete binding revision.");
        }

        if (change.kind() == EmployeeAppointmentChange.Kind.TRANSFER) {
            require(successor != null, "A transfer requires a bound successor.");
            require(change.successor().equals(successor.appointment()),
                    "Bound successor must exactly match the versioned proposal.");
            requireSameCompany(before, successor);
            requireConsistentPin(before.binding(), successor.binding());
            requireConsistentPin(after.binding(), successor.binding());
        } else {
            require(successor == null, "Only a transfer may carry a bound successor.");
        }
    }

    private static void requireSameCompany(
            CompanyBoundEmployeeAppointment source, CompanyBoundEmployeeAppointment target) {
        require(source.binding().companyPartyId().equals(target.binding().companyPartyId()),
                "Cross-company appointment changes are unsupported.");
    }

    private static void requireConsistentPin(
            OrganizationUnitCompanyBinding first, OrganizationUnitCompanyBinding second) {
        if (first.id().equals(second.id()) && first.revision() == second.revision()) {
            require(first.equals(second), "An immutable binding revision cannot describe different facts.");
        }
    }

    private static void require(boolean valid, String message) {
        if (!valid) {
            throw new InvalidOrganizationValueException(message);
        }
    }
}
