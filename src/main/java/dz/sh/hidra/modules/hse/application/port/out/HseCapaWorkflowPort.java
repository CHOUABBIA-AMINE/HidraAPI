/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaWorkflowPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.out
 * @Description : Actual independent approved Workflow action, not merely a matching reference.
 */
package dz.sh.hidra.modules.hse.application.port.out;

import java.util.Optional;
public interface HseCapaWorkflowPort {
    record Decision(String actionId,String instanceId,String taskId,String targetId,
                    String approverId,String digest){}
    Optional<Decision> approved(String actionId,String targetType,String exactTargetId);
}
