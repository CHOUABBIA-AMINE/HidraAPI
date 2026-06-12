/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenCustodyMeasurementPeriodCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.command
 *
 * @Description : Command to open custody measurement period.
 *
 */
package dz.sh.hidra.modules.custody.application.command;

import java.time.Instant;

/**
 * Command to open custody measurement period.
 */
public record OpenCustodyMeasurementPeriodCommand(
        String periodCode,
        String agreementId,
        String transferPointId,
        Instant periodStart,
        Instant periodEnd
) {
}
