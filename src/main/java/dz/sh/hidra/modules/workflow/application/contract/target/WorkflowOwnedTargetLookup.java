/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowOwnedTargetLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.target
 *
 * @Description : Enforces owner-validated Workflow execution and immutable evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.target;

import java.util.Optional;
import java.util.Set;
public interface WorkflowOwnedTargetLookup {
    record Target(String id, String code, String label) {}
    String module();
    Set<String> targetTypeCodes();
    Optional<Target> eligibleTarget(String typeCode, String targetId);
}
