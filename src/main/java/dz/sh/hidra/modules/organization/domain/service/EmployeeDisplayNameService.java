/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeDisplayNameService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.service
 *
 * @Description : Builds employee display names from structured names.
 *
 */
package dz.sh.hidra.modules.organization.domain.service;

import dz.sh.hidra.modules.organization.domain.model.Employee;

/**
 * Builds employee display names from structured names.
 */
public class EmployeeDisplayNameService {

    public String latinDisplayName(Employee employee) {
        if (employee == null) {
            return null;
        }
        if (employee.displayNameLt() != null) {
            return employee.displayNameLt();
        }
        return join(employee.firstNameLt(), employee.lastNameLt());
    }

    public String arabicDisplayName(Employee employee) {
        if (employee == null) {
            return null;
        }
        if (employee.displayNameAr() != null) {
            return employee.displayNameAr();
        }
        return join(employee.firstNameAr(), employee.lastNameAr());
    }

    private String join(String firstName, String lastName) {
        String first = firstName == null ? "" : firstName.trim();
        String last = lastName == null ? "" : lastName.trim();
        String joined = (first + " " + last).trim();
        return joined.isBlank() ? null : joined;
    }
}
