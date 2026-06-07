/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Service
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Application service for telemetry ingestion batch use cases.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.application.command.CompleteTelemetryIngestionBatchCommand;
import dz.sh.hidra.modules.telemetry.application.command.FailTelemetryIngestionBatchCommand;
import dz.sh.hidra.modules.telemetry.application.command.MarkTelemetryIngestionBatchProcessingCommand;
import dz.sh.hidra.modules.telemetry.application.command.StartTelemetryIngestionBatchCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryIngestionBatchDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.port.in.CompleteTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.FailTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryIngestionBatchesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.MarkTelemetryIngestionBatchProcessingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.StartTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryIngestionBatchRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetrySourceRepositoryPort;
import dz.sh.hidra.modules.telemetry.application.query.GetTelemetryIngestionBatchByIdQuery;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryIngestionBatchesQuery;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryIngestionBatch;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryIngestionDomainService;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;

/**
 * Application service for telemetry ingestion batch use cases.
 */
public final class TelemetryIngestionApplicationService implements
        StartTelemetryIngestionBatchUseCase,
        MarkTelemetryIngestionBatchProcessingUseCase,
        CompleteTelemetryIngestionBatchUseCase,
        FailTelemetryIngestionBatchUseCase,
        GetTelemetryIngestionBatchUseCase,
        ListTelemetryIngestionBatchesUseCase {

    private final TelemetrySourceRepositoryPort sourceRepository;
    private final TelemetryIngestionBatchRepositoryPort batchRepository;
    private final TelemetryIngestionDomainService ingestionDomainService;

    public TelemetryIngestionApplicationService(
            TelemetrySourceRepositoryPort sourceRepository,
            TelemetryIngestionBatchRepositoryPort batchRepository,
            TelemetryIngestionDomainService ingestionDomainService) {

        this.sourceRepository = Objects.requireNonNull(sourceRepository, "TelemetrySourceRepositoryPort must not be null.");
        this.batchRepository = Objects.requireNonNull(batchRepository, "TelemetryIngestionBatchRepositoryPort must not be null.");
        this.ingestionDomainService = Objects.requireNonNull(ingestionDomainService, "TelemetryIngestionDomainService must not be null.");
    }

    @Override
    public TelemetryIngestionBatchDto startTelemetryIngestionBatch(StartTelemetryIngestionBatchCommand command) {
        Objects.requireNonNull(command, "StartTelemetryIngestionBatchCommand must not be null.");
        TelemetrySource source = findSource(command.sourceId());
        return toDto(batchRepository.save(ingestionDomainService.startBatch(source, command.correlationId())));
    }

    @Override
    public TelemetryIngestionBatchDto markTelemetryIngestionBatchProcessing(MarkTelemetryIngestionBatchProcessingCommand command) {
        TelemetryIngestionBatch batch = findBatch(command.batchId());
        return toDto(batchRepository.save(ingestionDomainService.markProcessing(batch)));
    }

    @Override
    public TelemetryIngestionBatchDto completeTelemetryIngestionBatch(CompleteTelemetryIngestionBatchCommand command) {
        TelemetryIngestionBatch batch = findBatch(command.batchId());
        return toDto(batchRepository.save(ingestionDomainService.completeBatch(
                batch,
                command.receivedCount(),
                command.acceptedCount(),
                command.rejectedCount(),
                command.duplicateCount(),
                command.quarantinedCount())));
    }

    @Override
    public TelemetryIngestionBatchDto failTelemetryIngestionBatch(FailTelemetryIngestionBatchCommand command) {
        TelemetryIngestionBatch batch = findBatch(command.batchId());
        return toDto(batchRepository.save(ingestionDomainService.failBatch(batch, command.reason())));
    }

    @Override
    public TelemetryIngestionBatchDto getTelemetryIngestionBatch(GetTelemetryIngestionBatchByIdQuery query) {
        Objects.requireNonNull(query, "GetTelemetryIngestionBatchByIdQuery must not be null.");
        return toDto(findBatch(query.batchId()));
    }

    @Override
    public TelemetryPageDto<TelemetryIngestionBatchDto> listTelemetryIngestionBatches(ListTelemetryIngestionBatchesQuery query) {
        Objects.requireNonNull(query, "ListTelemetryIngestionBatchesQuery must not be null.");

        PageResult<TelemetryIngestionBatch> page = batchRepository.findAll(
                query.sourceId(),
                query.status(),
                query.startedFrom(),
                query.startedTo(),
                query.pageRequest());

        return new TelemetryPageDto<>(
                page.items().stream().map(this::toDto).toList(),
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages());
    }

    private TelemetrySource findSource(TelemetrySourceId sourceId) {
        return sourceRepository.findById(sourceId)
                .orElseThrow(() -> new BusinessRuleViolationException("Telemetry source not found: " + sourceId.value()));
    }

    private TelemetryIngestionBatch findBatch(TelemetryIngestionBatchId batchId) {
        return batchRepository.findById(batchId)
                .orElseThrow(() -> new BusinessRuleViolationException("Telemetry ingestion batch not found: " + batchId.value()));
    }

    private TelemetryIngestionBatchDto toDto(TelemetryIngestionBatch batch) {
        return new TelemetryIngestionBatchDto(
                batch.id().value(),
                batch.sourceId().value(),
                batch.correlationId() == null ? null : batch.correlationId().value(),
                batch.status().name(),
                batch.receivedCount(),
                batch.acceptedCount(),
                batch.rejectedCount(),
                batch.duplicateCount(),
                batch.quarantinedCount(),
                batch.startedAt(),
                batch.completedAt(),
                batch.failureReason());
    }
}
