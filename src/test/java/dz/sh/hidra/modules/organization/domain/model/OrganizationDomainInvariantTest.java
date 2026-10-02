/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationDomainInvariantTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Verifies constructor-level Organization domain invariants.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class OrganizationDomainInvariantTest {

    private static final Instant FROM = Instant.parse("2026-01-01T00:00:00Z");
    private static final Instant TO = Instant.parse("2026-02-01T00:00:00Z");

    @Test
    void rejectsMissingAdministrativeIdentifiersAndReferences() {
        assertThatThrownBy(() -> new AdministrativeState(
                " ", "16", null, null, "Algiers", true, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class);
        assertThatThrownBy(() -> new AdministrativeDistrict(
                "district-1", " ", "DIST", null, null, "District", true, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class);
        assertThatThrownBy(() -> new AdministrativeLocality(
                "locality-1", " ", "LOC", null, null, "Locality", null, true, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class);
    }

    @Test
    void rejectsInvalidOrganizationUnitShapeAndPeriod() {
        assertThatThrownBy(() -> new OrganizationUnit(
                "unit-1", "UNIT", null, null, "Unit", "type-1", "unit-1",
                OrganizationUnitStatus.ACTIVE, FROM, null, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class).hasMessageContaining("own parent");
        assertThatThrownBy(() -> new OrganizationUnit(
                "unit-1", "UNIT", null, null, "Unit", "type-1", null,
                OrganizationUnitStatus.ACTIVE, FROM, FROM, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class).hasMessageContaining("validTo");
    }

    @Test
    void rejectsMissingCatalogTypesAndStatuses() {
        assertThatThrownBy(() -> new OrganizationUnitType(
                "type-1", "TYPE", null, null, null, "Type",
                null, null, null, true, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class);
        assertThatThrownBy(() -> new Position(
                "pos-1", "POS", null, null, "Position", null,
                null, null, null, PositionStatus.ACTIVE, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class);
        assertThatThrownBy(() -> new Shift(
                "shift-1", "SHIFT", null, null, "Shift", null,
                null, null, null, true, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class);
    }

    @Test
    void rejectsInvalidEmployeeAddressAndAssignmentPeriods() {
        assertThatThrownBy(() -> new EmployeeAddress(
                "address-1", "emp-1", AddressType.HOME, "locality-1",
                null, null, null, true, FROM, FROM, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class);
        assertThatThrownBy(() -> new EmployeeAssignment(
                "assignment-1", "emp-1", "unit-1", "position-1",
                AssignmentType.PRIMARY, FROM, FROM, AssignmentStatus.ACTIVE, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class);
    }

    @Test
    void rejectsReportingSelfReferenceAndInvalidPeriod() {
        ReportingSubjectReference employee =
                new ReportingSubjectReference(ReportingSubjectType.EMPLOYEE, "emp-1");
        assertThatThrownBy(() -> new ReportingLine(
                "line-1", ReportingLineType.FUNCTIONAL, employee, employee,
                FROM, TO, true, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class).hasMessageContaining("different");
        assertThatThrownBy(() -> new ReportingLine(
                "line-1", ReportingLineType.FUNCTIONAL, employee,
                new ReportingSubjectReference(ReportingSubjectType.POSITION, "pos-1"),
                FROM, FROM, true, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class).hasMessageContaining("validTo");
    }

    @Test
    void rejectsIncompleteResponsibilityAndShiftAssignments() {
        assertThatThrownBy(() -> new ResponsibilityAssignment(
                "resp-1", ResponsibilityType.RESPONSIBLE, ResponsibilityAssigneeType.EMPLOYEE,
                " ", 1L, null, FROM, TO, AssignmentStatus.ACTIVE, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class);
        assertThatThrownBy(() -> new ShiftAssignment(
                "shift-assignment-1", "emp-1", "shift-1", "unit-1",
                FROM, FROM, ShiftAssignmentStatus.ACTIVE, FROM, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class);
    }

    @Test
    void rejectsSelfDelegationAndInvalidDelegationPeriod() {
        assertThatThrownBy(() -> new OrganizationDelegation(
                "delegation-1", "emp-1", "emp-1", "resp-1", null,
                FROM, TO, DelegationStatus.ACTIVE, FROM, null
        )).isInstanceOf(InvalidOrganizationValueException.class).hasMessageContaining("different");
        assertThatThrownBy(() -> new OrganizationDelegation(
                "delegation-1", "emp-1", "emp-2", "resp-1", null,
                FROM, FROM, DelegationStatus.ACTIVE, FROM, null
        )).isInstanceOf(InvalidOrganizationValueException.class).hasMessageContaining("validTo");
    }

    @Test
    void rejectsIncompleteHierarchySnapshotIdentity() {
        assertThatThrownBy(() -> new OrganizationHierarchySnapshot(
                "snapshot-1", "SNAPSHOT-1", null, "emp-1",
                HierarchySnapshotStatus.DRAFT, "{}", null, FROM
        )).isInstanceOf(InvalidOrganizationValueException.class).hasMessageContaining("capturedAt");
    }
}
