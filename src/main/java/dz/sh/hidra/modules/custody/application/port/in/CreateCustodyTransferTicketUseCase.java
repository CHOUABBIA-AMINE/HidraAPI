/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateCustodyTransferTicketUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.in
 *
 * @Description : Use case for creating custody transfer tickets.
 *
 */
package dz.sh.hidra.modules.custody.application.port.in;

import dz.sh.hidra.modules.custody.application.command.CreateCustodyTransferTicketCommand;
import dz.sh.hidra.modules.custody.application.dto.CustodyTransferTicketSummaryDto;

/**
 * Use case for creating custody transfer tickets.
 */
public interface CreateCustodyTransferTicketUseCase {

    CustodyTransferTicketSummaryDto createTransferTicket(CreateCustodyTransferTicketCommand command);
}
