/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QualifiedOperatingConstraintReadUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Queries exact scoped qualified pressure restrictions at an explicit effective instant.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.Revision;
import dz.sh.hidra.modules.topology.domain.service.QualifiedPressureRestrictionResolver.Status;
import java.time.Instant;
import java.util.Objects;

public interface QualifiedOperatingConstraintReadUseCase {
    record Query(String companyPartyId, String pipelineId, String sectionId,
                 String conditionCode, Instant effectiveAt) {
        public Query {
            Objects.requireNonNull(companyPartyId);
            Objects.requireNonNull(pipelineId);
            Objects.requireNonNull(conditionCode);
            Objects.requireNonNull(effectiveAt);
        }
    }
    record Result(Status status, Revision restriction) {
        public Result {
            Objects.requireNonNull(status);
            if ((status == Status.QUALIFIED) != (restriction != null))
                throw new IllegalArgumentException("A non-qualified response cannot reveal a restriction");
        }
    }
    Result read(Query query);
}
