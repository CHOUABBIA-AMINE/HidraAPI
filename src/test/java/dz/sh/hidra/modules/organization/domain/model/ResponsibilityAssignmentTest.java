/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssignmentTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Tests canonical scope registry identity on responsibility assignments.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResponsibilityAssignmentTest {

    @Test
    void storesOnlyRegistryScopeId() {
        Instant now = Instant.parse("2026-09-27T07:00:00Z");

        ResponsibilityAssignment assignment = new ResponsibilityAssignment(
                "resp-1",
                ResponsibilityType.RESPONSIBLE,
                "ORGANIZATION_UNIT",
                "unit-1",
                42L,
                "Pipeline operations",
                now,
                null,
                AssignmentStatus.ACTIVE,
                now,
                now
        );

        assertEquals(42L, assignment.scopeId());
        assertEquals("unit-1", assignment.assigneeId());
    }

    @Test
    void rejectsNonPositiveRegistryId() {
        Instant now = Instant.parse("2026-09-27T07:00:00Z");

        assertThrows(InvalidOrganizationValueException.class, () -> new ResponsibilityAssignment(
                "resp-1",
                ResponsibilityType.RESPONSIBLE,
                "ORGANIZATION_UNIT",
                "unit-1",
                0L,
                null,
                now,
                null,
                AssignmentStatus.ACTIVE,
                now,
                now
        ));
    }

    @Test
    void allowsNullRegistryIdOnlyForTransitionalLegacyRows() {
        Instant now = Instant.parse("2026-09-27T07:00:00Z");

        ResponsibilityAssignment assignment = new ResponsibilityAssignment(
                "resp-legacy",
                ResponsibilityType.RESPONSIBLE,
                "ORGANIZATION_UNIT",
                "unit-1",
                null,
                null,
                now,
                null,
                AssignmentStatus.ACTIVE,
                now,
                now
        );

        assertNull(assignment.scopeId());
    }
}
