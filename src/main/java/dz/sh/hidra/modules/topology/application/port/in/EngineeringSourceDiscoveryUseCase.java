/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringSourceDiscoveryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Defines a compact, source-owner authorized engineering revision selection contract.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** Readback and pre-selection only; never grants publication or engineering qualification. */
public interface EngineeringSourceDiscoveryUseCase {
    enum Kind { NETWORK_PUBLICATION }

    record Lookup(Kind kind, String companyPartyId, String pipelineSystemId,
                  UUID draftId, UUID contextId, UUID revisionId) {
        public Lookup {
            Objects.requireNonNull(kind, "Source kind required");
            companyPartyId=EngineeringRevisionPublication.scopeIdentity(companyPartyId);
            pipelineSystemId=EngineeringRevisionPublication.scopeIdentity(pipelineSystemId);
            Objects.requireNonNull(draftId, "Known source draft key required");
            if ((contextId==null)!=(revisionId==null))
                throw new IllegalArgumentException("Exact source requires context and revision together");
        }
    }
    /** This is immutable authored-source evidence, not a qualified operational baseline. */
    record Source(Kind kind, UUID ownerId, UUID sourceId, UUID revisionId,
                  long authoredVersion, String metadataSha256, String state,
                  Instant effectiveAt, boolean currentlyEligible, boolean engineeringQualified) {
        public Source {
            Objects.requireNonNull(kind);Objects.requireNonNull(ownerId);
            Objects.requireNonNull(sourceId);Objects.requireNonNull(revisionId);
            Objects.requireNonNull(metadataSha256);Objects.requireNonNull(state);
            if (authoredVersion<1 || !metadataSha256.matches("[0-9a-f]{64}") || engineeringQualified)
                throw new IllegalArgumentException("Source facts invalid or unqualified status hidden");
        }
    }

    Optional<Source> current(Lookup lookup);
    Source exact(Lookup lookup);
}
