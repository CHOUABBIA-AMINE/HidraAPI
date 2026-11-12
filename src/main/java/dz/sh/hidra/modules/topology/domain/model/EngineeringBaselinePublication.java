/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselinePublication
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Implements the exact engineering baseline publication contract.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

public final class EngineeringBaselinePublication {
    private EngineeringBaselinePublication() {}

    public record Submission(UUID id, EngineeringBaselineManifest manifest, String sha256,
                             String submittedBy, Instant submittedAt) {
        public Submission {
            Objects.requireNonNull(id);
            Objects.requireNonNull(manifest);
            digest(sha256);
            submittedBy = EngineeringBaselineManifest.identity(submittedBy);
            Objects.requireNonNull(submittedAt);
        }

        public String workflowLocator() {
            return "baseline-v1." + encode(manifest.companyPartyId()) + "." + encode(manifest.pipelineSystemId())
                    + "." + id;
        }

        private static String encode(String value) {
            return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
        }
    }

    public record Decision(String definitionId, int definitionVersion, String targetTypeId,
                           String purposeId, String instanceId, String taskId, String actionId) {
        public Decision {
            for (String id : new String[]{definitionId, targetTypeId, purposeId, instanceId, taskId, actionId}) {
                EngineeringBaselineManifest.identity(id);
            }
            if (definitionVersion < 1) throw new IllegalArgumentException("Positive definition version required");
        }
    }

    public record Approval(Decision decision, String reviewerId, Instant reviewedAt) {
        public Approval {
            Objects.requireNonNull(decision);
            reviewerId = EngineeringBaselineManifest.identity(reviewerId);
            Objects.requireNonNull(reviewedAt);
        }
    }

    public record Receipt(UUID id, Submission submission, long epoch, Approval approval,
                          String publishedBy, Instant publishedAt) {
        public Receipt {
            Objects.requireNonNull(id);
            Objects.requireNonNull(submission);
            Objects.requireNonNull(approval);
            Objects.requireNonNull(publishedAt);
            publishedBy = EngineeringBaselineManifest.identity(publishedBy);
            if (epoch < 1 || submission.submittedBy().equals(approval.reviewerId())
                    || approval.reviewedAt().isBefore(submission.submittedAt())
                    || publishedAt.isBefore(approval.reviewedAt())) {
                throw new IllegalArgumentException("Invalid publication lineage");
            }
        }
    }

    public static String digest(String value) {
        if (value == null || !value.matches("[0-9a-f]{64}"))
            throw new IllegalArgumentException("Exact canonical SHA-256 required");
        return value;
    }
}
