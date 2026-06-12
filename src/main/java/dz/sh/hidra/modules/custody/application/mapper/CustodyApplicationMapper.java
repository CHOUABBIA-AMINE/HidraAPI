/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyApplicationMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.mapper
 *
 * @Description : Maps custody domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.custody.application.mapper;

import dz.sh.hidra.modules.custody.application.dto.CustodyDiscrepancySummaryDto;
import dz.sh.hidra.modules.custody.application.dto.CustodyMeasurementPeriodSummaryDto;
import dz.sh.hidra.modules.custody.application.dto.CustodyTransferTicketSummaryDto;
import dz.sh.hidra.modules.custody.domain.model.CustodyDiscrepancy;
import dz.sh.hidra.modules.custody.domain.model.CustodyMeasurementPeriod;
import dz.sh.hidra.modules.custody.domain.model.CustodyTransferTicket;

/**
 * Maps custody domain models to DTOs.
 */
public final class CustodyApplicationMapper {

    private CustodyApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CustodyMeasurementPeriodSummaryDto toSummary(CustodyMeasurementPeriod period) {
        return new CustodyMeasurementPeriodSummaryDto(
                period.id(),
                period.periodCode(),
                period.agreementId(),
                period.transferPointId(),
                period.periodStart(),
                period.periodEnd(),
                period.status()
        );
    }

    public static CustodyTransferTicketSummaryDto toSummary(CustodyTransferTicket ticket) {
        return new CustodyTransferTicketSummaryDto(
                ticket.id(),
                ticket.ticketNumber(),
                ticket.measurementPeriodId(),
                ticket.agreementId(),
                ticket.transferPointId(),
                ticket.status(),
                ticket.ticketDate(),
                ticket.approvedAt()
        );
    }

    public static CustodyDiscrepancySummaryDto toSummary(CustodyDiscrepancy discrepancy) {
        return new CustodyDiscrepancySummaryDto(
                discrepancy.id(),
                discrepancy.discrepancyNumber(),
                discrepancy.reconciliationId(),
                discrepancy.discrepancyTypeId(),
                discrepancy.status(),
                discrepancy.differenceQuantity(),
                discrepancy.openedAt()
        );
    }
}
