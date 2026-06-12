/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StartIntegrationJobRunCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.command
 *
 * @Description : Command to start an integration job run.
 *
 */
package dz.sh.hidra.modules.integration.application.command;

import dz.sh.hidra.modules.integration.domain.value.JobTriggerType;

/**
 * Command to start an integration job run.
 */
public record StartIntegrationJobRunCommand(
        String jobDefinitionId,
        long runNumber,
        JobTriggerType triggerType,
        String triggeredByActorId,
        String correlationId
) {
}
