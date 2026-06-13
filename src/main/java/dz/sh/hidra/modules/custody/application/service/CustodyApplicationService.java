/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.service
 *
 * @Description : Application service for custody periods, tickets, and discrepancies.
 *
 */
package dz.sh.hidra.modules.custody.application.service;

import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.custody.application.command.CreateCustodyTransferTicketCommand;
import dz.sh.hidra.modules.custody.application.command.OpenCustodyDiscrepancyCommand;
import dz.sh.hidra.modules.custody.application.command.OpenCustodyMeasurementPeriodCommand;
import dz.sh.hidra.modules.custody.application.dto.CustodyDiscrepancySummaryDto;
import dz.sh.hidra.modules.custody.application.dto.CustodyMeasurementPeriodSummaryDto;
import dz.sh.hidra.modules.custody.application.dto.CustodyTransferTicketSummaryDto;
import dz.sh.hidra.modules.custody.application.mapper.CustodyApplicationMapper;
import dz.sh.hidra.modules.custody.application.port.in.CreateCustodyTransferTicketUseCase;
import dz.sh.hidra.modules.custody.application.port.in.OpenCustodyDiscrepancyUseCase;
import dz.sh.hidra.modules.custody.application.port.in.OpenCustodyMeasurementPeriodUseCase;
import dz.sh.hidra.modules.custody.application.port.out.CustodyDiscrepancyRepositoryPort;
import dz.sh.hidra.modules.custody.application.port.out.CustodyMeasurementPeriodRepositoryPort;
import dz.sh.hidra.modules.custody.application.port.out.CustodyTransferTicketRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyDiscrepancy;
import dz.sh.hidra.modules.custody.domain.model.CustodyMeasurementPeriod;
import dz.sh.hidra.modules.custody.domain.model.CustodyTransferTicket;
import dz.sh.hidra.modules.custody.domain.value.CustodyDiscrepancyStatus;
import dz.sh.hidra.modules.custody.domain.value.CustodyId;
import dz.sh.hidra.modules.custody.domain.value.CustodyPeriodStatus;
import dz.sh.hidra.modules.custody.domain.value.CustodyTicketStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for custody periods, tickets, and discrepancies.
 */
@Service
public final class CustodyApplicationService implements OpenCustodyMeasurementPeriodUseCase, CreateCustodyTransferTicketUseCase, OpenCustodyDiscrepancyUseCase {

    private final CustodyMeasurementPeriodRepositoryPort periodRepositoryPort;
    private final CustodyTransferTicketRepositoryPort ticketRepositoryPort;
    private final CustodyDiscrepancyRepositoryPort discrepancyRepositoryPort;

    public CustodyApplicationService(
            CustodyMeasurementPeriodRepositoryPort periodRepositoryPort,
            CustodyTransferTicketRepositoryPort ticketRepositoryPort,
            CustodyDiscrepancyRepositoryPort discrepancyRepositoryPort
    ) {
        this.periodRepositoryPort = Objects.requireNonNull(periodRepositoryPort, "Custody measurement period repository port must not be null.");
        this.ticketRepositoryPort = Objects.requireNonNull(ticketRepositoryPort, "Custody transfer ticket repository port must not be null.");
        this.discrepancyRepositoryPort = Objects.requireNonNull(discrepancyRepositoryPort, "Custody discrepancy repository port must not be null.");
    }

    @Override
    public CustodyMeasurementPeriodSummaryDto openMeasurementPeriod(OpenCustodyMeasurementPeriodCommand command) {
        Objects.requireNonNull(command, "Open custody measurement period command must not be null.");
        Instant now = Instant.now();
        CustodyMeasurementPeriod period = new CustodyMeasurementPeriod(
                CustodyId.newId().value(),
                command.periodCode(),
                command.agreementId(),
                command.transferPointId(),
                command.periodStart(),
                command.periodEnd(),
                CustodyPeriodStatus.OPEN,
                null,
                null,
                null,
                null,
                now,
                now
        );
        return CustodyApplicationMapper.toSummary(periodRepositoryPort.save(period));
    }

    @Override
    public CustodyTransferTicketSummaryDto createTransferTicket(CreateCustodyTransferTicketCommand command) {
        Objects.requireNonNull(command, "Create custody transfer ticket command must not be null.");
        Instant now = Instant.now();
        CustodyTransferTicket ticket = new CustodyTransferTicket(
                CustodyId.newId().value(),
                command.ticketNumber(),
                command.measurementPeriodId(),
                command.agreementId(),
                command.transferPointId(),
                command.batchId(),
                command.quantityCalculationId(),
                CustodyTicketStatus.DRAFT,
                command.ticketDate() == null ? now : command.ticketDate(),
                command.issuedByActorId(),
                null,
                null,
                command.workflowInstanceId(),
                null,
                now,
                now
        );
        return CustodyApplicationMapper.toSummary(ticketRepositoryPort.save(ticket));
    }

    @Override
    public CustodyDiscrepancySummaryDto openDiscrepancy(OpenCustodyDiscrepancyCommand command) {
        Objects.requireNonNull(command, "Open custody discrepancy command must not be null.");
        Instant now = Instant.now();
        CustodyDiscrepancy discrepancy = new CustodyDiscrepancy(
                CustodyId.newId().value(),
                command.discrepancyNumber(),
                command.reconciliationId(),
                command.discrepancyTypeId(),
                CustodyDiscrepancyStatus.OPEN,
                command.differenceQuantity(),
                command.quantityUnitId(),
                command.description(),
                null,
                null,
                command.assignedActorId(),
                command.openedAt() == null ? now : command.openedAt(),
                null,
                null,
                now,
                now
        );
        return CustodyApplicationMapper.toSummary(discrepancyRepositoryPort.save(discrepancy));
    }
}
