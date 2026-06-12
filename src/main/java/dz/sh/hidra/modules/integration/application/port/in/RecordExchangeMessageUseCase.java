/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordExchangeMessageUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.in
 *
 * @Description : Use case for recording exchange messages.
 *
 */
package dz.sh.hidra.modules.integration.application.port.in;

import dz.sh.hidra.modules.integration.application.command.RecordExchangeMessageCommand;
import dz.sh.hidra.modules.integration.application.dto.IntegrationExchangeMessageSummaryDto;

/**
 * Use case for recording exchange messages.
 */
public interface RecordExchangeMessageUseCase {

    IntegrationExchangeMessageSummaryDto recordExchangeMessage(RecordExchangeMessageCommand command);
}
