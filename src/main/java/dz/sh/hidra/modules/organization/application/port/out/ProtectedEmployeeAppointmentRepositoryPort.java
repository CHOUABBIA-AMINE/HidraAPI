/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedEmployeeAppointmentRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-23
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
import java.util.Set;

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

    /** Storage observation only; only its live issuing Admission can establish this classification. */
    enum ReferenceUse { CURRENT, RETAINED, REPLAY }

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
     * Party fences. Party acquisition retains fences without consuming selectability;
     * CURRENT writes consume that eligibility only after locked replay classification.
     * No implementation may accept a fabricated or stale ticket,
     * cross-transaction holder, non-owning database principal or incomplete set
     * of company/unit descriptors. Recheck Party only for fresh new references;
     * preserved historical pins remain historical evidence. For writes, acquire
     * may retain the not-yet-consumed Party ticket to preserve relation-lock order,
     * but that marker alone may never establish eligibility. Validate physical
     * Party fences independently; CURRENT reference use additionally requires the
     * genuine Party read/current checks before mutate or any protected result.
     */
    Admission acquire(
            OrganizationProtectedAppointmentAuthorizationContract.Admission identityAdmission,
            List<OrganizationProtectedPartyEligibilityContract.Admission> partyAdmissions,
            DescriptorPreview preview);

    /**
     * After locked descriptor equality, consume the genuine native contribution
     * once inside the storage issuer. The returned scalar is retained privately
     * by that issuer; callers cannot qualify storage by supplying a contribution.
     */
    OrganizationProtectedAppointmentAuthorizationContract.Contribution contribute(Admission admission);

    /** Recheck all issuer/physical/owner evidence and fail closed before any row is exposed. */
    void requireCurrent(Admission admission);

    /**
     * Under retained owner fences, authenticate this storage admission and its
     * genuine native issuer, match the single contribution and exact command,
     * and classify the scoped request BEFORE current projection CAS. REPLAY
     * requires an identical immutable receipt for the sampled actor, command and
     * complete result descriptors; missing/conflicting receipts are never replay.
     * RETAINED is only a new END or same-unit CORRECT retaining the source pin.
     * CURRENT means a fresh CREATE, TRANSFER or moved CORRECT. Repeated calls
     * must revalidate the same frozen classification, not silently reclassify it.
     * A scalar enum or caller-constructed contribution alone conveys no authority.
     * No mutation, receipt creation or protected result may be returned here.
     */
    ReferenceUse referenceUse(Admission admission, EmployeeAppointmentCommand command,
            OrganizationProtectedAppointmentAuthorizationContract.Contribution contribution);

    /**
     * Only after this issuer has frozen CURRENT classification, consume the
     * genuine configured Party issuer once and retain its observation privately.
     * Match company and physical coordinates to the original native sample.
     * No scalar supplied by the caller can qualify current-reference planning.
     * RETAINED/REPLAY must never consume current selectability here.
     */
    OrganizationProtectedPartyEligibilityContract.Contribution contributeParty(Admission admission);

    /**
     * Revalidate and freeze the complete SELECT-only Domain/policy/conflict plan.
     * CURRENT requires the storage-owned genuine Party contribution. This grants
     * no provisional permit, DML, result, receipt or observed-commit assertion.
     */
    void prepareMutation(Admission admission, EmployeeAppointmentCommand command);

    /** Freeze keys, row parameters and PostgreSQL canonical event bytes before sealing admission. */
    Set<String> prepareProvisional(Admission admission, EmployeeAppointmentCommand command);

    /** Only the compiled coordinator calls this once inside its executor's six-template block.
     * No owner query, evidence discovery, flush, receipt or protected result is permitted here. */
    void persistProvisional(Admission admission);

    /** After closing PROVISIONAL, verify the frozen post-write graph under the original owners.
     * This is not final proof/receipt acceptance or an observed successful commit. */
    void verifyProvisional(Admission admission);

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
