/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkApprovalTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Checks immutable link approval provenance, exact target identity and independent human actor invariants.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.service.InstalledCharacteristicLinkApprovalTargetDigester;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InstalledCharacteristicLinkApprovalTest {
    private static final Instant RECORDED = Instant.parse("2026-10-31T10:15:00Z");
    private static final Instant ACTION = Instant.parse("2026-11-01T12:40:00.123456Z");
    private static final Instant APPROVED = Instant.parse("2026-11-01T12:41:00.123456Z");

    private static InstalledCharacteristicLink draft(long revision) {
        return new InstalledCharacteristicLink("link-1", revision, "party-1", "unit-1",
                "source-1", "source-r3", "a".repeat(64),
                InstalledCharacteristicLink.Kind.COMPRESSOR_MAP,
                Instant.parse("2026-11-01T12:34:56.123456Z"), null, RECORDED,
                "synthetic:approval-source", null);
    }

    private static InstalledCharacteristicLinkApproval.WorkflowEvidence workflow(String actor, Instant actedAt) {
        return new InstalledCharacteristicLinkApproval.WorkflowEvidence("definition-1", 2,
                "target-id-1", InstalledCharacteristicLinkApproval.TARGET_TYPE_CODE,
                "purpose-id-1", InstalledCharacteristicLinkApproval.PURPOSE_CODE,
                "instance-1", "task-1", "action-1", actor, actedAt);
    }

    private static InstalledCharacteristicLinkApproval decision(InstalledCharacteristicLink target,
            String draftActor, String approver,
            InstalledCharacteristicLinkApproval.WorkflowEvidence evidence, Instant approvedAt) {
        return new InstalledCharacteristicLinkApproval(target, draftActor, approver,
                InstalledCharacteristicLinkApprovalTargetDigester.digest(target), evidence, approvedAt);
    }

    @Test void acceptsExactIndependentApprovalSnapshotWithoutClaimingOwnerAuthorization() {
        var target = draft(7);
        var value = decision(target, "drafter-1", "approver-2", workflow("approver-2", ACTION), APPROVED);
        assertTrue(InstalledCharacteristicLinkApproval.class.isRecord());
        assertEquals(target, value.target());
        assertEquals("drafter-1", value.draftActorUserId());
        assertEquals("approver-2", value.approvingActorUserId());
        assertEquals("action-1", value.workflow().actionId());
        assertEquals(2, value.workflow().definitionVersion());
        assertEquals(InstalledCharacteristicLinkApprovalTargetDigester.digest(target), value.targetSha256());
    }

    @Test void rejectsSelfApprovalEvenWithAWellFormedWorkflowReference() {
        assertThrows(InvalidTopologyValueException.class,
                () -> decision(draft(7), "same-user", "same-user", workflow("same-user", ACTION), APPROVED));
    }

    @Test void rejectsMismatchedWorkflowActorAndMissingEvidence() {
        assertThrows(InvalidTopologyValueException.class,
                () -> decision(draft(7), "drafter", "approver", workflow("other-user", ACTION), APPROVED));
        assertThrows(InvalidTopologyValueException.class,
                () -> decision(draft(7), "drafter", "approver", null, APPROVED));
    }

    @Test void rejectsWrongCanonicalDigestAndMalformedHex() {
        var target = draft(7);
        var evidence = workflow("approver", ACTION);
        for (String digest : new String[] { "b".repeat(64), "A".repeat(64), "a".repeat(63) }) {
            assertThrows(InvalidTopologyValueException.class,
                    () -> new InstalledCharacteristicLinkApproval(target, "drafter", "approver",
                            digest, evidence, APPROVED));
        }
    }

    @Test void rejectsIncompleteHumanOrWorkflowIdentity() {
        assertThrows(InvalidTopologyValueException.class,
                () -> decision(draft(7), " ", "approver", workflow("approver", ACTION), APPROVED));
        assertThrows(InvalidTopologyValueException.class,
                () -> decision(draft(7), "drafter", null, workflow("approver", ACTION), APPROVED));
        assertThrows(InvalidTopologyValueException.class,
                () -> workflow(" ", ACTION));
        assertThrows(InvalidTopologyValueException.class,
                () -> new InstalledCharacteristicLinkApproval.WorkflowEvidence("definition", 0,
                        "target", InstalledCharacteristicLinkApproval.TARGET_TYPE_CODE,
                        "purpose", InstalledCharacteristicLinkApproval.PURPOSE_CODE,
                        "instance", "task", "action", "approver", ACTION));
    }

    @Test void rejectsUnregisteredTargetAndPurposeCodes() {
        assertThrows(InvalidTopologyValueException.class,
                () -> new InstalledCharacteristicLinkApproval.WorkflowEvidence("definition", 1,
                        "target", "UNRELATED_TARGET", "purpose",
                        InstalledCharacteristicLinkApproval.PURPOSE_CODE,
                        "instance", "task", "action", "approver", ACTION));
        assertThrows(InvalidTopologyValueException.class,
                () -> new InstalledCharacteristicLinkApproval.WorkflowEvidence("definition", 1,
                        "target", InstalledCharacteristicLinkApproval.TARGET_TYPE_CODE,
                        "purpose", "UNRELATED_PURPOSE",
                        "instance", "task", "action", "approver", ACTION));
    }

    @Test void requiresOrderedMicrosecondLinkActionAndDecisionTimes() {
        var target = draft(7);
        assertThrows(InvalidTopologyValueException.class,
                () -> decision(target, "drafter", "approver", workflow("approver", RECORDED.minusSeconds(1)), APPROVED));
        assertThrows(InvalidTopologyValueException.class,
                () -> decision(target, "drafter", "approver", workflow("approver", ACTION), ACTION.minusSeconds(1)));
        assertThrows(InvalidTopologyValueException.class,
                () -> workflow("approver", ACTION.plusNanos(1)));
        assertThrows(InvalidTopologyValueException.class,
                () -> decision(target, "drafter", "approver", workflow("approver", ACTION), APPROVED.plusNanos(1)));
    }

    @Test void newerLinkRevisionCannotReusePriorApprovalTargetDigest() {
        var old = draft(7);
        var newer = draft(8);
        var oldDecision = decision(old, "drafter", "approver", workflow("approver", ACTION), APPROVED);
        assertNotEquals(oldDecision.targetSha256(), InstalledCharacteristicLinkApprovalTargetDigester.digest(newer));
        assertThrows(InvalidTopologyValueException.class,
                () -> new InstalledCharacteristicLinkApproval(newer, "drafter", "approver",
                        oldDecision.targetSha256(), workflow("approver", ACTION), APPROVED));
        assertEquals(7, oldDecision.target().linkRevision());
    }

    @Test void identityAndSourceEvidenceAreNotDisclosedByRecordStringification() {
        var value = decision(draft(7), "drafter", "approver", workflow("approver", ACTION), APPROVED);
        assertFalse(value.toString().contains("source-1"));
        assertFalse(value.toString().contains("drafter"));
        assertFalse(value.workflow().toString().contains("instance-1"));
    }

    @Test void exactHistoricalDecisionValuesAreStableAcrossReconstruction() {
        var one = decision(draft(7), "drafter", "approver", workflow("approver", ACTION), APPROVED);
        var two = decision(draft(7), "drafter", "approver", workflow("approver", ACTION), APPROVED);
        assertEquals(one, two);
        assertEquals(one.hashCode(), two.hashCode());
        assertNotEquals(one, decision(draft(8), "drafter", "approver", workflow("approver", ACTION), APPROVED));
    }
}
