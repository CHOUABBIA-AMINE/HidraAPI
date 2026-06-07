/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Service
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service for telemetry reading use cases.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.application.command.AcceptTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.command.MarkTelemetryReadingDuplicateCommand;
import dz.sh.hidra.modules.telemetry.application.command.QuarantineTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.command.ReceiveTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.command.RejectTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryReadingDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryReadingValueDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryTypeReferenceDto;
import dz.sh.hidra.modules.telemetry.application.port.in.AcceptTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetLatestTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryReadingsUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.MarkTelemetryReadingDuplicateUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.QuarantineTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ReceiveTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RejectTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.query.GetLatestTelemetryReadingQuery;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryReadingByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryReadingsQuery;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryReadingDomainService;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryQualityCodeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingId;

/**
 * Application service for telemetry reading use cases.
 */
public final class TelemetryReadingApplicationService implements
        ReceiveTelemetryReadingUseCase,
        AcceptTelemetryReadingUseCase,
        RejectTelemetryReadingUseCase,
        QuarantineTelemetryReadingUseCase,
        MarkTelemetryReadingDuplicateUseCase,
        GetTelemetryReadingUseCase,
        GetLatestTelemetryReadingUseCase,
        ListTelemetryReadingsUseCase {

    private final TelemetryPointRepositoryPort pointRepository;
    private final TelemetryReadingRepositoryPort readingRepository;
    private final TelemetryReadingDomainService readingDomainService;

    public TelemetryReadingApplicationService(
            TelemetryPointRepositoryPort pointRepository,
            TelemetryReadingRepositoryPort readingRepository,
            TelemetryReadingDomainService readingDomainService) {

        this.pointRepository = Objects.requireNonNull(pointRepository, "TelemetryPointRepositoryPort must not be null.");
        this.readingRepository = Objects.requireNonNull(readingRepository, "TelemetryReadingRepositoryPort must not be null.");
        this.readingDomainService = Objects.requireNonNull(readingDomainService, "TelemetryReadingDomainService must not be null.");
    }

    @Override
    public TelemetryReadingDto receiveTelemetryReading(ReceiveTelemetryReadingCommand command) {
        Objects.requireNonNull(command, "ReceiveTelemetryReadingCommand must not be null.");

        TelemetryPoint point = findPoint(command.pointId());
        TelemetryReading reading = readingDomainService.receiveReading(
                point,
                command.value(),
                command.qualityCode(),
                command.sourceTimestamp(),
                command.ingestionBatchId(),
                command.correlationId());

        if (readingRepository.existsByPointIdAndSourceTimestamp(command.pointId(), command.sourceTimestamp())) {
            reading = reading.markDuplicate();
        }

        return toDto(readingRepository.save(reading));
    }

    @Override
    public TelemetryReadingDto acceptTelemetryReading(AcceptTelemetryReadingCommand command) {
        TelemetryReading reading = findReading(command.readingId());
        TelemetryPoint point = findPoint(reading.pointId());
        return toDto(readingRepository.save(readingDomainService.acceptReading(point, reading)));
    }

    @Override
    public TelemetryReadingDto rejectTelemetryReading(RejectTelemetryReadingCommand command) {
        TelemetryReading reading = findReading(command.readingId());
        TelemetryPoint point = findPoint(reading.pointId());
        return toDto(readingRepository.save(readingDomainService.rejectReading(point, reading, command.reason())));
    }

    @Override
    public TelemetryReadingDto quarantineTelemetryReading(QuarantineTelemetryReadingCommand command) {
        TelemetryReading reading = findReading(command.readingId());
        TelemetryPoint point = findPoint(reading.pointId());
        return toDto(readingRepository.save(readingDomainService.quarantineReading(point, reading, command.reason())));
    }

    @Override
    public TelemetryReadingDto markTelemetryReadingDuplicate(MarkTelemetryReadingDuplicateCommand command) {
        TelemetryReading reading = findReading(command.readingId());
        TelemetryPoint point = findPoint(reading.pointId());
        return toDto(readingRepository.save(readingDomainService.markDuplicate(point, reading)));
    }

    @Override
    public TelemetryReadingDto getTelemetryReading(GetTelemetryReadingByIdQuery query) {
        Objects.requireNonNull(query, "GetTelemetryReadingByIdQuery must not be null.");
        return toDto(findReading(query.readingId()));
    }

    @Override
    public TelemetryReadingDto getLatestTelemetryReading(GetLatestTelemetryReadingQuery query) {
        Objects.requireNonNull(query, "GetLatestTelemetryReadingQuery must not be null.");
        return readingRepository.findLatestByPointId(query.pointId())
                .map(this::toDto)
                .orElseThrow(() -> new BusinessRuleViolationException("No telemetry reading found for point: " + query.pointId().value()));
    }

    @Override
    public TelemetryPageDto<TelemetryReadingDto> listTelemetryReadings(ListTelemetryReadingsQuery query) {
        Objects.requireNonNull(query, "ListTelemetryReadingsQuery must not be null.");

        PageResult<TelemetryReading> page = readingRepository.findAll(
                query.pointId(),
                query.qualityCode(),
                query.state(),
                query.fromSourceTimestamp(),
                query.toSourceTimestamp(),
                query.ingestionBatchId(),
                query.pageRequest());

        return new TelemetryPageDto<>(
                page.items().stream().map(this::toDto).toList(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages());
    }

    private TelemetryPoint findPoint(TelemetryPointId pointId) {
        return pointRepository.findById(pointId)
                .orElseThrow(() -> new BusinessRuleViolationException("Telemetry point not found: " + pointId.value()));
    }

    private TelemetryReading findReading(TelemetryReadingId readingId) {
        return readingRepository.findById(readingId)
                .orElseThrow(() -> new BusinessRuleViolationException("Telemetry reading not found: " + readingId.value()));
    }

    private TelemetryReadingDto toDto(TelemetryReading reading) {
        return new TelemetryReadingDto(
                reading.id().value(),
                reading.pointId().value(),
                new TelemetryReadingValueDto(
                        reading.value().numericValue(),
                        reading.value().textValue(),
                        reading.value().booleanValue()),
                toTypeDto(reading.qualityCode()),
                reading.sourceTimestamp().value(),
                reading.receivedAt().value(),
                reading.state().name(),
                reading.ingestionBatchId() == null ? null : reading.ingestionBatchId().value(),
                reading.correlationId() == null ? null : reading.correlationId().value(),
                reading.rejectionReason());
    }

    private static TelemetryTypeReferenceDto toTypeDto(TelemetryQualityCodeReference reference) {
        return new TelemetryTypeReferenceDto(reference.id(), reference.name(), reference.name(), null);
    }
}
