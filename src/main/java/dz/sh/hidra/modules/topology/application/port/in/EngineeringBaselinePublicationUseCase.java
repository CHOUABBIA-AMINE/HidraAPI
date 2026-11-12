/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselinePublicationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Implements the exact engineering baseline publication contract.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselinePublication;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselinePublication.*;
import java.util.Objects;
import java.util.UUID;

public interface EngineeringBaselinePublicationUseCase {
    enum Action { SUBMIT, REVIEW, TARGET, PUBLISH, RECEIPT }

    record Command(Action action, String company, String pipeline, UUID id, UUID manifestId,
                   long version, String sha256, UUID requestId, long expectedEpoch, Decision decision) {
        public Command {
            Objects.requireNonNull(action);
            company = EngineeringBaselineManifest.identity(company);
            pipeline = EngineeringBaselineManifest.identity(pipeline);
            Objects.requireNonNull(id);
            switch (action) {
                case SUBMIT -> {
                    Objects.requireNonNull(manifestId);
                    if (version < 1 || requestId != null || decision != null || expectedEpoch != 0)
                        throw new IllegalArgumentException("Invalid submission");
                    EngineeringBaselinePublication.digest(sha256);
                }
                case REVIEW, TARGET -> EngineeringBaselinePublication.digest(sha256);
                case PUBLISH -> {
                    Objects.requireNonNull(requestId);
                    Objects.requireNonNull(decision);
                    if (expectedEpoch < 0 || expectedEpoch == Long.MAX_VALUE)
                        throw new IllegalArgumentException("Invalid expected publication epoch");
                }
                case RECEIPT -> { }
            }
        }
    }

    record Result(Submission submission, Receipt receipt, boolean replayed) {}
    Result execute(Command command);
}
