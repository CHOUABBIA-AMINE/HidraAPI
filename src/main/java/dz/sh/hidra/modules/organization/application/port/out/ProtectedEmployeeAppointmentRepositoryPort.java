/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedEmployeeAppointmentRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Restricts protected appointment storage to owner-issued admission and typed scoped operations.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract;
import dz.sh.hidra.modules.party.application.contract.organization.OrganizationProtectedPartyEligibilityContract;
import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult;
import dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery;
import java.util.List;
import java.util.Objects;

/**
 * Repository boundary, NOT a generic JPA CRUD port. The owning adapter must
 * validate actual Identity/Party issuer tickets, lock order, current/pinned
 * Organization binding and policy, complete 1024-row conflict snapshot,
 * database principal/transaction/time, immutable event/receipt and final guard.
 * No marker, record or preview is independently an attestation or permission.
 *
 * This interface is deliberately not wired to a writer or HTTP route until
 * the migration, owner coordinator and PostgreSQL proofs are accepted.
 */
public interface ProtectedEmployeeAppointmentRepositoryPort {

    /** Issued only by the actual protected storage adapter; caller implementations are rejected. */
    interface Admission { }

    /**
     * Selector-only, internal, bounded preview before native Identity admission.
     * It is NEVER a complete/authoritative owner lookup or proof of permission.
     * A real adapter must re-derive the entire descriptor closure inside locks.
     */
    record DescriptorPreview(
            List<OrganizationProtectedAppointmentAuthorizationContract.CompanyUnit> companyUnits,
            String employeeId, String assignmentId, boolean overflow) {
        public DescriptorPreview {
            companyUnits = List.copyOf(Objects.requireNonNull(companyUnits, "Company/unit preview required"));
            new OrganizationProtectedAppointmentAuthorizationContract.Request(
                    OrganizationProtectedAppointmentAuthorizationContract.Action.READ_HISTORY,
                    companyUnits, employeeId);
            if (assignmentId != null) {
                if (assignmentId.isBlank() || assignmentId.length() > 80
                        || !assignmentId.equals(assignmentId.strip())
                        || assignmentId.codePoints().anyMatch(c -> Character.isISOControl(c)
                                || Character.getType(c) == Character.SURROGATE)) {
                    throw new IllegalArgumentException("Exact bounded assignment selector required");
                }
            }
        }

        /** Overflow must be rejected without returning the candidate descriptors. */
        public void requireBounded() {
            if (overflow) throw new IllegalArgumentException("Descriptor closure exceeds its supported bound");
        }
    }

    /**
     * SELECT-only discovery; may not lock owner rows, reveal results or decide
     * authority. The coordinator must compare this with freshly locked facts.
     * Legacy unresolved ownership is never silently replaced with an active head.
     */
    DescriptorPreview preview(EmployeeAppointmentCommand command);

    DescriptorPreview preview(EmployeeAppointmentQuery query);

    /**
     * Acquires Organization's exact owner fences only after genuine Identity then
     * Party fences. No implementation may accept a fabricated or stale ticket,
     * cross-transaction holder, non-owning database principal or incomplete set
     * of company/unit descriptors. Recheck Party only for fresh new references;
     * preserved historical pins remain historical evidence.
     */
    Admission acquire(
            OrganizationProtectedAppointmentAuthorizationContract.Admission identityAdmission,
            List<OrganizationProtectedPartyEligibilityContract.Admission> partyAdmissions,
            DescriptorPreview preview);

    /** Recheck all issuer/physical/owner evidence and fail closed before any row is exposed. */
    void requireCurrent(Admission admission);

    /**
     * One guarded atomic lifecycle operation under the original request key,
     * exact source CAS and pinned revisions. CREATE has no source, END and
     * CORRECT one successor version, TRANSFER two records. Implementer must
     * atomically append complete event/receipt evidence and reject unknown commit.
     * A historical identical replay still requires fresh current authorization.
     */
    EmployeeAppointmentResult.MutationReceipt mutate(Admission admission,
                                                      EmployeeAppointmentCommand command);

    /** Complete scope authorization is required BEFORE any count/cursor/payload. */
    EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PrivateHistoryEvent>
            privateHistory(Admission admission, EmployeeAppointmentQuery.History query);

    /** This distinct shape prevents the repository returning employee identity on occupant reads. */
    EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PositionHistoryEvent>
            positionHistory(Admission admission, EmployeeAppointmentQuery.History query);

    EmployeeAppointmentResult.PinnedVersion exactVersion(
            Admission admission, EmployeeAppointmentQuery.ExactVersion query);

    EmployeeAppointmentResult.PolicyView readPolicy(
            Admission admission, EmployeeAppointmentQuery.Policy query);
}
