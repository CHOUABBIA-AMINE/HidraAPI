/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenCustodyDiscrepancyCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.command
 *
 * @Description : Command to open custody discrepancy.
 *
 */
package dz.sh.hidra.modules.custody.application.command;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Command to open custody discrepancy.
 */
public record OpenCustodyDiscrepancyCommand(
        String discrepancyNumber,
        String reconciliationId,
        String discrepancyTypeId,
        BigDecimal differenceQuantity,
        String quantityUnitId,
        String description,
        String assignedActorId,
        Instant openedAt
) {
}
