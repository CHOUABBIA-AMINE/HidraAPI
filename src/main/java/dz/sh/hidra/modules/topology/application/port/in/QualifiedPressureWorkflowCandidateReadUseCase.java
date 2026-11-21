/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QualifiedPressureWorkflowCandidateReadUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Verifies an exact proposed operating restriction as a possible Workflow review target.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import java.util.Optional;
import java.util.UUID;

public interface QualifiedPressureWorkflowCandidateReadUseCase {
    /**
     * Current owner-granted evidence and immutable candidate consistency only;
     * the result is never an approved operating pressure.
     */
    Optional<String> validatedLocator(UUID candidateId);
}
