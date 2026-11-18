/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaDecisionContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.hse
 * @Description : Exact independently executed Workflow APPROVE action and typed HSE target evidence.
 */
package dz.sh.hidra.modules.workflow.application.contract.hse;

import java.util.Optional;
public interface HseCapaDecisionContract {
    record Decision(String actionId,String instanceId,String taskId,String targetId,
                    String approverId,String digest){}
    Optional<Decision> approved(String actionId,String targetType,String targetId);
}
