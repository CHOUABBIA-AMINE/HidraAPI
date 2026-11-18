/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.service
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.application.service;
import dz.sh.hidra.modules.reporting.application.port.in.ScopedReportUseCase;
import dz.sh.hidra.modules.reporting.application.port.out.ScopedReportOwnerPort;
import dz.sh.hidra.modules.reporting.application.port.out.ScopedReportOwnerPort.Operation;
import dz.sh.hidra.modules.reporting.application.port.out.ScopedReportRepositoryPort;
import dz.sh.hidra.modules.reporting.application.port.out.ScopedReportRepositoryPort.Row;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Phase;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Scope;
import dz.sh.hidra.modules.reporting.domain.service.AbsoluteDeviationReportJsonV1;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import static dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.bounded;

@Service
public class ScopedReportApplicationService implements ScopedReportUseCase {
    private final ScopedReportRepositoryPort records;
    private final ScopedReportOwnerPort owners;
    private final TransactionTemplate transaction;

    public ScopedReportApplicationService(ScopedReportRepositoryPort records,
            ScopedReportOwnerPort owners, PlatformTransactionManager manager) {
        this.records = records;
        this.owners = owners;
        transaction = new TransactionTemplate(manager);
        transaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Override
    public Receipt submit(Scope scope, Submit command) {
        String key = key(command.requestKey());
        String definition = key(command.definitionId());
        String version = key(command.templateVersionId());
        String metric = key(command.metricRunId());
        return tx(() -> {
            String actor = owners.require(scope, Operation.SUBMIT);
            records.lockAdmission(scope, key);
            var existing = records.submission(scope, key);
            if (existing.isPresent()) {
                Row row = existing.get();
                if (!actor.equals(row.actor())) throw hidden();
                if (!definition.equals(row.definitionId()) || !version.equals(row.templateVersionId())
                        || !metric.equals(row.metricRunId())) throw conflict();
                if (!row.sourceHash().equals(AbsoluteDeviationReportJsonV1.sha256(bytes(scope,metric))))
                    throw conflict();
                return receipt(row, true);
            }
            var template = records.template(definition, version);
            byte[] projection = bytes(scope, metric);
            Row row = new Row(scope, id(), key, actor, definition, version, template.hash(), metric,
                    AbsoluteDeviationReportJsonV1.sha256(projection), null, null, null, null, null,
                    Phase.SUBMITTED, null, null, null, null, null);
            records.submit(row, template.parameterId(), new String(projection, StandardCharsets.UTF_8));
            return receipt(row, false);
        });
    }

    @Override
    public Receipt render(Scope scope, String requestId, Render command) {
        key(requestId);
        key(command.requestKey());
        key(command.workflowInstanceId());
        key(command.workflowActionId());
        var admitted = tx(() -> {
            String actor = owners.require(scope, Operation.RENDER);
            Row row = records.request(scope, requestId, true).orElseThrow(ScopedReportApplicationService::hidden);
            if (!actor.equals(row.actor())) throw hidden();
            if (row.runId() != null) {
                if (!command.requestKey().equals(row.renderKey())
                        || !command.workflowActionId().equals(row.workflowActionId())
                        || !command.workflowInstanceId().equals(row.workflowInstanceId())) throw conflict();
                return receipt(row, true);
            }
            var approval = owners.approval(requestId, command.workflowInstanceId(), command.workflowActionId());
            if (actor.equals(approval.reviewerId())) throw hidden();
            recheck(row);
            owners.requireDraftAuthority(scope);
            records.queue(row, id(), command.requestKey(), approval);
            return receipt(records.request(scope, requestId, false).orElseThrow(), false);
        });
        if (!Phase.valueOf(admitted.status()).pending()) return admitted;
        String runId = admitted.runId();
        tx(() -> {
            owners.require(scope, Operation.RENDER);
            Row row = run(scope, runId, true);
            if (row.phase() == Phase.QUEUED) records.start(runId);
            return Boolean.TRUE;
        });
        try {
            return tx(() -> {
                owners.require(scope, Operation.RENDER);
                Row row = run(scope, runId, true);
                if (row.phase() != Phase.RUNNING) return receipt(row, true);
                byte[] bytes = recheck(row);
                owners.requireDraftAuthority(scope);
                var draft = owners.draft(scope, runId, row.actor(), bytes);
                if (draft.length() != bytes.length || !row.sourceHash().equals(draft.checksum())) throw conflict();
                records.rendered(runId, draft);
                return receipt(run(scope, runId, false), admitted.replayed());
            });
        } catch (ScopedReportOwnerPort.RenderFailure failed) {
            return tx(() -> {
                owners.require(scope, Operation.RENDER);
                Row row = run(scope, runId, true);
                if (row.phase() == Phase.RUNNING) records.failed(runId, "DOCUMENT_STORAGE_UNAVAILABLE");
                return receipt(run(scope, runId, false), admitted.replayed());
            });
        }
    }

    @Override
    public Receipt read(Scope scope, String runId) {
        return tx(() -> {
            owners.require(scope, Operation.READ);
            return receipt(run(scope, runId, false), false);
        });
    }

    @Override
    public Receipt seal(Scope scope, String runId, Seal command) {
        String key = key(command.requestKey());
        return tx(() -> {
            String custodian = owners.require(scope, Operation.SEAL);
            Row row = run(scope, runId, true);
            if (row.actor().equals(custodian) || Objects.equals(row.reviewerId(), custodian)) throw hidden();
            if (row.phase() == Phase.COMPLETED) {
                if (!key.equals(row.sealKey()) || !custodian.equals(row.custodianId())) throw conflict();
                owners.content(scope, runId, row.draft(), row.acceptanceId());
                return receipt(row, true);
            }
            row.phase().require(Phase.RENDERED);
            String acceptance = owners.seal(scope, runId, row.draft());
            records.complete(row, id(), key, custodian, acceptance);
            return receipt(run(scope, runId, false), false);
        });
    }

    @Override
    public Content content(Scope scope, String runId) {
        return tx(() -> {
            owners.require(scope, Operation.READ);
            Row row = run(scope, runId, false);
            if (row.phase() != Phase.COMPLETED) throw hidden();
            byte[] bytes = owners.content(scope, runId, row.draft(), row.acceptanceId());
            if (bytes.length != row.draft().length() || !row.draft().checksum()
                    .equals(AbsoluteDeviationReportJsonV1.sha256(bytes))) throw conflict();
            return new Content(bytes, row.draft().checksum());
        });
    }

    private Row run(Scope scope, String run, boolean lock) {
        return records.run(scope, key(run), lock).orElseThrow(ScopedReportApplicationService::hidden);
    }
    private byte[] bytes(Scope scope, String metric) {
        var projection = owners.metric(scope, metric);
        if (!metric.equals(projection.metricRunId())) throw hidden();
        return AbsoluteDeviationReportJsonV1.render(projection);
    }
    private byte[] recheck(Row row) {
        var template = records.template(row.definitionId(), row.templateVersionId());
        byte[] bytes = bytes(row.scope(), row.metricRunId());
        if (!template.hash().equals(row.templateHash())
                || !AbsoluteDeviationReportJsonV1.sha256(bytes).equals(row.sourceHash())) throw conflict();
        return bytes;
    }
    private static Receipt receipt(Row row, boolean replayed) {
        var draft = row.draft();
        boolean completed = row.phase() == Phase.COMPLETED;
        return new Receipt(row.requestId(), row.runId(), row.phase().name(), row.definitionId(),
                row.templateVersionId(), row.templateHash(), row.metricRunId(), row.sourceHash(),
                row.workflowInstanceId(), row.workflowActionId(), row.reviewerId(), row.custodianId(),
                completed ? draft.documentId() : null, completed ? draft.versionId() : null, row.acceptanceId(),
                completed ? draft.checksum() : null, completed ? draft.length() : null, row.failureCode(), replayed);
    }
    private <T> T tx(Supplier<T> work) { return Objects.requireNonNull(transaction.execute(status -> work.get())); }
    private static String key(String value) { return bounded(value, 80); }
    private static String id() { return UUID.randomUUID().toString(); }
    private static NoSuchElementException hidden() { return new NoSuchElementException("Protected report unavailable."); }
    private static IllegalStateException conflict() { return new IllegalStateException("Report state conflict."); }
}
