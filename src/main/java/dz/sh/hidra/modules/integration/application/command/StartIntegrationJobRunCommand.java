/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StartIntegrationJobRunCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.command
 *
 * @Description : Command to start an integration job run with persistence-owned run numbering.
 *
 */
package dz.sh.hidra.modules.integration.application.command;

import dz.sh.hidra.modules.integration.domain.value.JobTriggerType;

/**
 * Command to start an integration job run.
 */
public record StartIntegrationJobRunCommand(
        String jobDefinitionId,
        JobTriggerType triggerType,
        String triggeredByActorId,
        String correlationId
) {

    /**
     * Compatibility accessor for the pre-HMR-014 application service call shape.
     *
     * <p>Zero means unallocated. The repository/database boundary replaces it with the
     * authoritative per-job run number before persistence.</p>
     */
    public long runNumber() {
        return 0L;
    }
}
