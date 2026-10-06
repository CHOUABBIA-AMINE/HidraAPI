/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowOrganizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.contract.workflow
 *
 * @Description : Enforces owner-validated Workflow execution and immutable evidence.
 *
 */
package dz.sh.hidra.modules.organization.application.contract.workflow;

import java.time.Instant;
import java.util.Optional;
public interface WorkflowOrganizationContract {
    record Unit(String id, String name) {}
    Optional<Unit> availableUnit(String unitId, Instant at);
    boolean eligibleMember(String employeeId, String unitId, Instant at);
}
