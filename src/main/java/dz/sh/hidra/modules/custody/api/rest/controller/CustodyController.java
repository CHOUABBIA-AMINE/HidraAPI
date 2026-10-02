/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.controller
 *
 * @Description : Framework-neutral custody controller contract.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.controller;
import dz.sh.hidra.modules.custody.api.rest.request.*;
import dz.sh.hidra.modules.custody.api.rest.response.*;

/**
 * Framework-neutral custody controller contract.
 */
public interface CustodyController {
    CustodyTransferTicketResponse createTransferTicket(CreateCustodyTransferTicketRequest request);
    CustodyDiscrepancyResponse openDiscrepancy(OpenCustodyDiscrepancyRequest request);
    CustodyMeasurementPeriodResponse openMeasurementPeriod(OpenCustodyMeasurementPeriodRequest request);
}
