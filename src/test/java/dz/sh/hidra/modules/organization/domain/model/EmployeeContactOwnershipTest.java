/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeContactOwnershipTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Verifies canonical employee contact ownership and compatibility boundaries.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetReference;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetType;
import dz.sh.hidra.modules.organization.domain.value.ContactPointType;
import java.lang.reflect.RecordComponent;
import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class EmployeeContactOwnershipTest {

    @Test
    void employeeContactChannelsAreRepresentedByTypedOrganizationContactPoint() {
        OrganizationContactPoint email = new OrganizationContactPoint(
                "contact-1",
                ContactPointType.EMAIL,
                new ContactPointTargetReference(ContactPointTargetType.EMPLOYEE, "emp-1"),
                "Work email",
                "employee@example.com",
                true,
                false,
                true,
                Instant.parse("2026-09-28T00:00:00Z"),
                Instant.parse("2026-09-28T00:00:00Z")
        );

        assertThat(email.target().type()).isEqualTo(ContactPointTargetType.EMPLOYEE);
        assertThat(email.target().targetId()).isEqualTo("emp-1");
        assertThat(email.contactPointType()).isEqualTo(ContactPointType.EMAIL);
        assertThat(email.value()).isEqualTo("employee@example.com");
    }

    @Test
    void directEmployeeContactAccessorsRemainDeprecatedCompatibilityOnly() throws Exception {
        assertThat(Employee.class.getMethod("emailAddress").isAnnotationPresent(Deprecated.class))
                .isTrue();
        assertThat(Employee.class.getMethod("mobileNumber").isAnnotationPresent(Deprecated.class))
                .isTrue();
    }

    @Test
    void employeeDoesNotGainAnyAdditionalDirectContactComponents() {
        Set<String> components = Arrays.stream(Employee.class.getRecordComponents())
                .map(RecordComponent::getName)
                .collect(Collectors.toSet());

        assertThat(components)
                .contains("emailAddress", "mobileNumber")
                .doesNotContain(
                        "phoneNumber",
                        "officePhone",
                        "radioCallSign",
                        "emergencyPhone",
                        "secondaryEmail",
                        "secondaryMobile"
                );
    }
}
