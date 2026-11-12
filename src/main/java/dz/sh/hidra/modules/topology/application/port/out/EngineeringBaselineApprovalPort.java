/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineApprovalPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Implements the exact engineering baseline publication contract.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselinePublication.*;
import java.time.Instant;

public interface EngineeringBaselineApprovalPort {
    Approval requireApproval(Submission submission, Decision decision, Instant evaluationAt);
}
