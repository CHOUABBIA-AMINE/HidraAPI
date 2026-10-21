/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentOwnershipPolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.policy
 *
 * @Description : Exercises pinned ownership invariants for create end transfer and correction proposals.
 *
 */
package dz.sh.hidra.modules.organization.domain.policy;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.CompanyBoundEmployeeAppointment;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAppointmentChange;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAppointmentChange.VersionedAssignment;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnitCompanyBinding;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeAppointmentOwnershipPolicyTest {
    private static final Instant START = Instant.parse("2026-10-01T00:00:00Z");
    private static final Instant CHANGE = START.plusSeconds(60);
    private final EmployeeAppointmentOwnershipPolicy policy = new EmployeeAppointmentOwnershipPolicy();

    private static VersionedAssignment source() {
        return new VersionedAssignment(7, new EmployeeAssignment("source", "employee", "unit", "position",
                AssignmentType.PRIMARY, START, null, AssignmentStatus.ACTIVE, START, START));
    }

    private static OrganizationUnitCompanyBinding binding(String id, long revision, String unit, String company) {
        return new OrganizationUnitCompanyBinding(id, revision, unit, company, START);
    }

    private static CompanyBoundEmployeeAppointment bound(VersionedAssignment snapshot) {
        return new CompanyBoundEmployeeAppointment(snapshot,
                binding("binding-" + snapshot.assignment().organizationUnitId(), 2,
                        snapshot.assignment().organizationUnitId(), "company"));
    }

    private static CompanyBoundEmployeeAppointment withBinding(VersionedAssignment snapshot,
            OrganizationUnitCompanyBinding binding) {
        return new CompanyBoundEmployeeAppointment(snapshot, binding);
    }

    private static EmployeeAppointmentChange transfer(String unit) {
        return EmployeeAppointmentChange.transfer(source(), "successor", unit, "new-position",
                AssignmentType.ACTING, CHANGE, null, CHANGE, "approved transfer");
    }

    private static EmployeeAppointmentChange correction(String unit) {
        return EmployeeAppointmentChange.correct(source(),
                new EmployeeAssignment("source", "employee", unit, "corrected-position",
                        AssignmentType.TEMPORARY, START, null, AssignmentStatus.ACTIVE, START, CHANGE),
                CHANGE, "approved correction");
    }

    private void validate(EmployeeAppointmentChange change) {
        policy.validateChange(change, bound(change.before()), bound(change.after()),
                change.successor() == null ? null : bound(change.successor()));
    }

    @Test void createRequiresVersionOneAndRetainsExplicitPin() {
        var created = bound(new VersionedAssignment(1, source().assignment()));
        assertDoesNotThrow(() -> policy.validateCreate(created));
        assertEquals(2, created.binding().revision());
        assertThrows(InvalidOrganizationValueException.class, () -> policy.validateCreate(bound(source())));
        assertThrows(InvalidOrganizationValueException.class, () -> policy.validateCreate(null));
    }

    @Test void endRetainsSourcePinAndOriginalHistory() {
        var change = EmployeeAppointmentChange.end(source(), CHANGE, CHANGE, "approved end");
        var before = bound(change.before());
        var after = withBinding(change.after(), before.binding());
        assertDoesNotThrow(() -> policy.validateChange(change, before, after, null));
        assertNull(before.appointment().assignment().validTo());
        assertEquals(7, before.appointment().version());
        assertEquals(CHANGE, after.appointment().assignment().validTo());
        assertSame(before.binding(), after.binding());
    }

    @Test void rejectsEndPinSubstitutionEvenWhenCompanyAndUnitMatch() {
        var change = EmployeeAppointmentChange.end(source(), CHANGE, CHANGE, "end");
        var before = bound(change.before());
        for (var pin : new OrganizationUnitCompanyBinding[] {
                binding("binding-unit", 3, "unit", "company"),
                binding("other-binding", 2, "unit", "company"),
                new OrganizationUnitCompanyBinding("binding-unit", 2, "unit", "company", CHANGE) }) {
            assertThrows(InvalidOrganizationValueException.class,
                    () -> policy.validateChange(change, before, withBinding(change.after(), pin), null));
        }
        assertEquals(2, before.binding().revision());
    }

    @Test void acceptsSameCompanyCrossUnitTransferWithExactSuccessorPin() {
        var change = transfer("new-unit");
        assertDoesNotThrow(() -> validate(change));
        assertEquals("unit", change.before().assignment().organizationUnitId());
        assertEquals("new-unit", change.successor().assignment().organizationUnitId());
        assertEquals(1, change.successor().version());
    }

    @Test void deniesCrossCompanyTransferDespiteMatchingUnitAndPositionLabels() {
        for (String targetUnit : new String[] { "unit", "new-unit" }) {
            var change = transfer(targetUnit);
            var foreign = withBinding(change.successor(),
                    binding("destination-binding", 2, targetUnit, "foreign-company"));
            assertThrows(InvalidOrganizationValueException.class,
                    () -> policy.validateChange(change, bound(change.before()), bound(change.after()), foreign));
        }
    }

    @Test void transferSourceCannotSwitchBindingRevision() {
        var change = transfer("new-unit");
        var substituted = withBinding(change.after(), binding("binding-unit", 3, "unit", "company"));
        assertThrows(InvalidOrganizationValueException.class,
                () -> policy.validateChange(change, bound(change.before()), substituted, bound(change.successor())));
    }

    @Test void sameUnitSuccessorCanPinItsExplicitNewBindingRevision() {
        var change = transfer("unit");
        var successor = withBinding(change.successor(), binding("binding-unit", 3, "unit", "company"));
        assertDoesNotThrow(() -> policy.validateChange(
                change, bound(change.before()), bound(change.after()), successor));
        assertEquals(2, bound(change.before()).binding().revision());
        assertEquals(3, successor.binding().revision());
    }

    @Test void acceptsSameUnitCorrectionWithoutRewritingOriginalPin() {
        var change = correction("unit");
        assertDoesNotThrow(() -> validate(change));
        assertEquals("position", change.before().assignment().positionId());
        assertEquals("corrected-position", change.after().assignment().positionId());
    }

    @Test void sameUnitCorrectionRejectsPinOrCompanySubstitution() {
        var change = correction("unit");
        for (var pin : new OrganizationUnitCompanyBinding[] {
                binding("binding-unit", 3, "unit", "company"),
                binding("other-binding", 2, "unit", "company"),
                binding("binding-unit", 2, "unit", "foreign-company") }) {
            assertThrows(InvalidOrganizationValueException.class, () -> policy.validateChange(
                    change, bound(change.before()), withBinding(change.after(), pin), null));
        }
    }

    @Test void acceptsUnitChangingCorrectionWithDestinationPinAndBeforeImage() {
        var change = correction("corrected-unit");
        var before = bound(change.before());
        var after = bound(change.after());
        assertDoesNotThrow(() -> policy.validateChange(change, before, after, null));
        assertEquals("unit", before.binding().organizationUnitId());
        assertEquals("corrected-unit", after.binding().organizationUnitId());
        assertEquals(7, before.appointment().version());
        assertEquals(8, after.appointment().version());
        assertNotEquals(before.binding().id(), after.binding().id());
    }

    @Test void rejectsCrossCompanyCorrectionAndContradictoryBindingIdentity() {
        var change = correction("corrected-unit");
        for (var pin : new OrganizationUnitCompanyBinding[] {
                binding("destination-binding", 2, "corrected-unit", "foreign-company"),
                binding("binding-unit", 2, "corrected-unit", "company") }) {
            assertThrows(InvalidOrganizationValueException.class, () -> policy.validateChange(
                    change, bound(change.before()), withBinding(change.after(), pin), null));
        }
    }

    @Test void rejectsContradictorySuccessorBindingFactsForTheSamePin() {
        var change = transfer("new-unit");
        var contradictory = withBinding(change.successor(), binding("binding-unit", 2, "new-unit", "company"));
        assertThrows(InvalidOrganizationValueException.class, () -> policy.validateChange(
                change, bound(change.before()), bound(change.after()), contradictory));
    }

    @Test void rejectsMissingChangeOrBeforeAfterSnapshots() {
        var change = correction("unit");
        assertThrows(InvalidOrganizationValueException.class,
                () -> policy.validateChange(null, bound(change.before()), bound(change.after()), null));
        assertThrows(InvalidOrganizationValueException.class,
                () -> policy.validateChange(change, null, bound(change.after()), null));
        assertThrows(InvalidOrganizationValueException.class,
                () -> policy.validateChange(change, bound(change.before()), null, null));
    }

    @Test void rejectsBeforeSnapshotVersionOrFieldSubstitution() {
        var change = correction("unit");
        var old = change.before().assignment();
        var otherFields = new EmployeeAssignment(old.id(), old.employeeId(), old.organizationUnitId(), "other-position",
                old.assignmentType(), old.validFrom(), old.validTo(), old.status(), old.createdAt(), old.updatedAt());
        for (var snapshot : new VersionedAssignment[] {
                new VersionedAssignment(6, old), new VersionedAssignment(7, otherFields) }) {
            assertThrows(InvalidOrganizationValueException.class,
                    () -> policy.validateChange(change, bound(snapshot), bound(change.after()), null));
        }
    }

    @Test void rejectsAfterSnapshotVersionOrFieldSubstitution() {
        var change = correction("unit");
        var changed = change.after().assignment();
        var otherFields = new EmployeeAssignment(changed.id(), changed.employeeId(), changed.organizationUnitId(),
                "other-position", changed.assignmentType(), changed.validFrom(), changed.validTo(), changed.status(),
                changed.createdAt(), changed.updatedAt());
        for (var snapshot : new VersionedAssignment[] {
                new VersionedAssignment(9, changed), new VersionedAssignment(8, otherFields) }) {
            assertThrows(InvalidOrganizationValueException.class,
                    () -> policy.validateChange(change, bound(change.before()), bound(snapshot), null));
        }
    }

    @Test void transferRejectsMissingOrSubstitutedSuccessorSnapshot() {
        var change = transfer("new-unit");
        assertThrows(InvalidOrganizationValueException.class,
                () -> policy.validateChange(change, bound(change.before()), bound(change.after()), null));
        var next = change.successor().assignment();
        var otherFields = new EmployeeAssignment("foreign-successor", next.employeeId(), next.organizationUnitId(),
                next.positionId(), next.assignmentType(), next.validFrom(), next.validTo(), next.status(),
                next.createdAt(), next.updatedAt());
        for (var snapshot : new VersionedAssignment[] {
                new VersionedAssignment(2, next), new VersionedAssignment(1, otherFields) }) {
            assertThrows(InvalidOrganizationValueException.class, () -> policy.validateChange(
                    change, bound(change.before()), bound(change.after()), bound(snapshot)));
        }
    }

    @Test void endAndCorrectionRejectExtraneousSuccessor() {
        for (var change : new EmployeeAppointmentChange[] {
                EmployeeAppointmentChange.end(source(), CHANGE, CHANGE, "end"), correction("unit") }) {
            assertThrows(InvalidOrganizationValueException.class, () -> policy.validateChange(
                    change, bound(change.before()), bound(change.after()), bound(transfer("new-unit").successor())));
        }
    }
}
