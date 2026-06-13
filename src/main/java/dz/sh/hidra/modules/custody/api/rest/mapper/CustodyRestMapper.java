/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.mapper
 *
 * @Description : Maps custody REST models to application models.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.mapper;
import dz.sh.hidra.modules.custody.api.rest.request.CreateCustodyTransferTicketRequest;
import dz.sh.hidra.modules.custody.api.rest.request.OpenCustodyDiscrepancyRequest;
import dz.sh.hidra.modules.custody.api.rest.request.OpenCustodyMeasurementPeriodRequest;
import dz.sh.hidra.modules.custody.api.rest.response.CustodyDiscrepancyResponse;
import dz.sh.hidra.modules.custody.api.rest.response.CustodyMeasurementPeriodResponse;
import dz.sh.hidra.modules.custody.api.rest.response.CustodyTransferTicketResponse;
import dz.sh.hidra.modules.custody.application.command.CreateCustodyTransferTicketCommand;
import dz.sh.hidra.modules.custody.application.command.OpenCustodyDiscrepancyCommand;
import dz.sh.hidra.modules.custody.application.command.OpenCustodyMeasurementPeriodCommand;
import dz.sh.hidra.modules.custody.application.dto.CustodyDiscrepancySummaryDto;
import dz.sh.hidra.modules.custody.application.dto.CustodyMeasurementPeriodSummaryDto;
import dz.sh.hidra.modules.custody.application.dto.CustodyTransferTicketSummaryDto;

/**
 * Maps custody REST models to application models.
 */
public final class CustodyRestMapper {

    private CustodyRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateCustodyTransferTicketCommand toCommand(CreateCustodyTransferTicketRequest request) {
        return new CreateCustodyTransferTicketCommand(
                request.ticketNumber(),
                request.measurementPeriodId(),
                request.agreementId(),
                request.transferPointId(),
                request.batchId(),
                request.quantityCalculationId(),
                request.ticketDate(),
                request.issuedByActorId(),
                request.workflowInstanceId()
        );
    }

    public static OpenCustodyDiscrepancyCommand toCommand(OpenCustodyDiscrepancyRequest request) {
        return new OpenCustodyDiscrepancyCommand(
                request.discrepancyNumber(),
                request.reconciliationId(),
                request.discrepancyTypeId(),
                request.differenceQuantity(),
                request.quantityUnitId(),
                request.description(),
                request.assignedActorId(),
                request.openedAt()
        );
    }

    public static OpenCustodyMeasurementPeriodCommand toCommand(OpenCustodyMeasurementPeriodRequest request) {
        return new OpenCustodyMeasurementPeriodCommand(
                request.periodCode(),
                request.agreementId(),
                request.transferPointId(),
                request.periodStart(),
                request.periodEnd()
        );
    }

    public static CustodyTransferTicketResponse toResponse(CustodyTransferTicketSummaryDto dto) {
        return new CustodyTransferTicketResponse(
                dto.id(),
                dto.ticketNumber(),
                dto.measurementPeriodId(),
                dto.agreementId(),
                dto.transferPointId(),
                dto.status(),
                dto.ticketDate(),
                dto.approvedAt()
        );
    }

    public static CustodyDiscrepancyResponse toResponse(CustodyDiscrepancySummaryDto dto) {
        return new CustodyDiscrepancyResponse(
                dto.id(),
                dto.discrepancyNumber(),
                dto.reconciliationId(),
                dto.discrepancyTypeId(),
                dto.status(),
                dto.differenceQuantity(),
                dto.openedAt()
        );
    }

    public static CustodyMeasurementPeriodResponse toResponse(CustodyMeasurementPeriodSummaryDto dto) {
        return new CustodyMeasurementPeriodResponse(
                dto.id(),
                dto.periodCode(),
                dto.agreementId(),
                dto.transferPointId(),
                dto.periodStart(),
                dto.periodEnd(),
                dto.status()
        );
    }
}
