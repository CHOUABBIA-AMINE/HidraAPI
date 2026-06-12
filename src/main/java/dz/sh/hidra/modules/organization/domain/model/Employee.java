/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Employee
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Operational employee/person, not identity user.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;

    /**
     * Operational employee/person, not identity user.
     *
         * @param id id
     * @param employeeNumber employeeNumber
     * @param firstNameAr firstNameAr
     * @param lastNameAr lastNameAr
     * @param firstNameLt firstNameLt
     * @param lastNameLt lastNameLt
     * @param displayNameAr displayNameAr
     * @param displayNameLt displayNameLt
     * @param emailAddress emailAddress
     * @param mobileNumber mobileNumber
     * @param employeeType employeeType
     * @param status status
     * @param identityUserReference identityUserReference
     * @param hiredAt hiredAt
     * @param terminatedAt terminatedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record Employee(
            String id,
        String employeeNumber,
        String firstNameAr,
        String lastNameAr,
        String firstNameLt,
        String lastNameLt,
        String displayNameAr,
        String displayNameLt,
        String emailAddress,
        String mobileNumber,
        EmployeeType employeeType,
        EmployeeStatus status,
        String identityUserReference,
        Instant hiredAt,
        Instant terminatedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public Employee {
        id = normalize(id);
        employeeNumber = normalize(employeeNumber);
        firstNameAr = normalize(firstNameAr);
        lastNameAr = normalize(lastNameAr);
        firstNameLt = normalize(firstNameLt);
        lastNameLt = normalize(lastNameLt);
        displayNameAr = normalize(displayNameAr);
        displayNameLt = normalize(displayNameLt);
        emailAddress = normalize(emailAddress);
        mobileNumber = normalize(mobileNumber);
        identityUserReference = normalize(identityUserReference);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
