/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateIntegrityAssessmentCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.command
 *
 * @Description : Command to create integrity assessment.
 *
 */
package dz.sh.hidra.modules.integrity.application.command;

import java.time.Instant;

/**
 * Command to create integrity assessment.
 */
public record CreateIntegrityAssessmentCommand(
        String programId,
        String assessmentNumber,
        String title,
        String description,
        String assessmentTypeId,
        String methodologyId,
        Instant assessmentDate,
        String assessedByActorId,
        String workflowInstanceId
) {
}
