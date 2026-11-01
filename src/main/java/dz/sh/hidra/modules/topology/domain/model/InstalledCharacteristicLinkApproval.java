/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkApproval
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Captures an immutable characteristic-link approval decision with exact human and Workflow evidence identifiers.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.service.InstalledCharacteristicLinkApprovalTargetDigester;
import java.time.Instant;

/**
 * Immutable proposed publication decision, distinct from a manufacturer source
 * approval and from the original DRAFT link. A constructed value does NOT
 * authenticate Identity, resolve live Workflow or authorize SQL publication:
 * the later protected application/adapter stages must prove all of those
 * against original, same-transaction owner evidence before storing it.
 */
public record InstalledCharacteristicLinkApproval(
        InstalledCharacteristicLink target,
        String draftActorUserId,
        String approvingActorUserId,
        String targetSha256,
        WorkflowEvidence workflow,
        Instant approvedAt) {

    public static final String TARGET_TYPE_CODE = "INSTALLED_CHARACTERISTIC_LINK_REVISION";
    public static final String PURPOSE_CODE = "INSTALLED_CHARACTERISTIC_LINK_APPROVAL";

    /** Exact identifiers; these are not caller-authoritative approval claims. */
    public record WorkflowEvidence(
            String definitionId, int definitionVersion,
            String targetTypeId, String targetTypeCode,
            String purposeId, String purposeCode,
            String instanceId, String taskId, String actionId,
            String actorUserId, Instant actedAt) {
        public WorkflowEvidence {
            definitionId = id(definitionId, "Workflow definition");
            if (definitionVersion < 1)
                throw invalid("Positive Workflow definition version required.");
            targetTypeId = id(targetTypeId, "Workflow target type");
            purposeId = id(purposeId, "Workflow approval purpose");
            if (!TARGET_TYPE_CODE.equals(targetTypeCode) || !PURPOSE_CODE.equals(purposeCode))
                throw invalid("Exact characteristic-link Workflow target and purpose required.");
            instanceId = id(instanceId, "Workflow instance");
            taskId = id(taskId, "Workflow task");
            actionId = id(actionId, "Workflow action");
            actorUserId = id(actorUserId, "Workflow action actor");
            microsecond(actedAt, "Workflow action time");
        }
        @Override public String toString() { return "WorkflowEvidence[redacted]"; }
    }

    public InstalledCharacteristicLinkApproval {
        if (target == null) throw invalid("Immutable draft-link target required.");
        draftActorUserId = id(draftActorUserId, "Original draft actor");
        approvingActorUserId = id(approvingActorUserId, "Independent approving actor");
        if (draftActorUserId.equals(approvingActorUserId))
            throw invalid("Draft actor cannot approve the same link revision.");
        if (workflow == null || !approvingActorUserId.equals(workflow.actorUserId()))
            throw invalid("Actual Workflow action actor must match the independent approver.");
        if (targetSha256 == null || !targetSha256.matches("[0-9a-f]{64}")
                || !targetSha256.equals(InstalledCharacteristicLinkApprovalTargetDigester.digest(target)))
            throw invalid("Approval requires the exact canonical immutable target digest.");
        microsecond(approvedAt, "Approval record time");
        if (workflow.actedAt().isBefore(target.recordedAt())
                || approvedAt.isBefore(workflow.actedAt()))
            throw invalid("Original link, Workflow action and approval record must be ordered.");
    }

    private static String id(String value, String description) {
        if (value == null || value.isBlank() || !value.equals(value.strip())
                || value.codePointCount(0, value.length()) > 80
                || value.codePoints().anyMatch(c -> Character.isISOControl(c)
                    || c >= 0xd800 && c <= 0xdfff))
            throw invalid(description + " must be a bounded exact identifier.");
        return value;
    }

    private static void microsecond(Instant value, String description) {
        if (value == null || value.getNano() % 1_000 != 0)
            throw invalid(description + " requires explicit UTC microsecond precision.");
    }

    private static InvalidTopologyValueException invalid(String detail) {
        return new InvalidTopologyValueException(detail);
    }

    @Override public String toString() {
        return "InstalledCharacteristicLinkApproval[redacted]";
    }
}
