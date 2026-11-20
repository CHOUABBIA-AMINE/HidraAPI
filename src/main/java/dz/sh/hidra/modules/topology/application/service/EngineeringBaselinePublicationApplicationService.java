/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselinePublicationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Implements the exact engineering baseline publication contract.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.model.EngineeringBaselineDraftFailure;
import dz.sh.hidra.modules.topology.application.contract.simulation.ScenarioBaselineReceiptContract;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselinePublicationUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineReadUseCase;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.sql.SQLException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public final class EngineeringBaselinePublicationApplicationService implements EngineeringBaselinePublicationUseCase, EngineeringBaselineReadUseCase, ScenarioBaselineReceiptContract {
    private static final int SCENARIO_READ_ATTEMPTS = 5;
    private final ProtectedEngineeringBaselinePublicationCoordinator coordinator;
    private final ProtectedTransactionExecutor executor;

    public EngineeringBaselinePublicationApplicationService(ProtectedEngineeringBaselinePublicationCoordinator coordinator,
                                                            ProtectedTransactionExecutor executor) {
        this.coordinator = coordinator;
        this.executor = executor;
    }

    @Override
    public EngineeringBaselinePublicationUseCase.Result execute(Command command) {
        var prepared = coordinator.prepare();
        var admission = new AtomicReference<ProtectedEngineeringBaselinePublicationCoordinator.Admission>();
        try {
            return executor.executePublication(() -> {
                var ticket = coordinator.acquire(command, prepared);
                admission.set(ticket);
                return coordinator.perform(ticket);
            }, () -> coordinator.prepareFinal(admission.get()), () -> coordinator.guard(admission.get()));
        } catch (ProtectedTransactionExecutor.ExecutionFailure failure) {
            if (failure.outcome() == ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT) {
                if (failure.getCause() instanceof AccessDeniedException denied) throw denied;
                if (failure.getCause() instanceof EngineeringBaselineDraftFailure business) throw business;
            }
            throw failure;
        }
    }
    @Override
    public EngineeringBaselineReadUseCase.Result read(EngineeringBaselineReadUseCase.Query query) {
        var prepared = coordinator.prepare();
        var admission = new AtomicReference<ProtectedEngineeringBaselinePublicationCoordinator.ReadAdmission>();
        try {
            return executor.executePublication(() -> {
                var ticket = coordinator.acquireRead(query, prepared);
                admission.set(ticket);
                return ticket.result();
            }, () -> coordinator.prepareReadFinal(admission.get()),
               () -> coordinator.guardRead(admission.get()));
        } catch (ProtectedTransactionExecutor.ExecutionFailure failure) {
            if (failure.outcome() == ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT) {
                if (failure.getCause() instanceof AccessDeniedException denied) throw denied;
                if (failure.getCause() instanceof EngineeringBaselineDraftFailure business) throw business;
                if (failure.getCause() instanceof IllegalArgumentException invalid) throw invalid;
            }
            throw failure;
        }
    }

    /** Exact T07 protected read; never bypass historical source authorization or select current. */
    @Override
    public ScenarioBaselineReceiptContract.Exact exact(String company, String pipeline, UUID receiptId) {
        var query = new EngineeringBaselineReadUseCase.Query(
                EngineeringBaselineReadUseCase.Action.EXACT, company, pipeline, receiptId,
                null, null, 0, null);
        var receipt = readScenarioBaseline(query).primary();
        var manifest = receipt.submission().manifest();
        if (!company.equals(manifest.companyPartyId())
                || !pipeline.equals(manifest.pipelineSystemId())
                || !receiptId.equals(receipt.id())) {
            throw new NoSuchElementException("Scenario baseline unavailable");
        }
        return new ScenarioBaselineReceiptContract.Exact(receipt.id(), manifest.manifestId(),
                receipt.epoch(), receipt.submission().sha256());
    }

    private EngineeringBaselineReadUseCase.Result readScenarioBaseline(EngineeringBaselineReadUseCase.Query query) {
        for (int attempt = 1; ; attempt++) {
            try {
                return read(query);
            } catch (EngineeringBaselineDraftFailure failure) {
                if (failure.kind() == EngineeringBaselineDraftFailure.Kind.NOT_FOUND) {
                    throw new NoSuchElementException("Scenario baseline unavailable");
                }
                throw failure;
            } catch (ProtectedTransactionExecutor.ExecutionFailure failure) {
                if (attempt == SCENARIO_READ_ATTEMPTS
                        || failure.outcome() != ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT
                        || !lockUnavailable(failure)) {
                    throw failure;
                }
                // Each retry starts a fresh protected read and obtains all owner proofs again.
                try {
                    Thread.sleep(Duration.ofMillis(50L << (attempt - 1)));
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    throw failure;
                }
            }
        }
    }

    private static boolean lockUnavailable(Throwable failure) {
        for (Throwable cause = failure; cause != null; cause = cause.getCause()) {
            if (cause instanceof SQLException sql && "55P03".equals(sql.getSQLState())) {
                return true;
            }
        }
        return false;
    }
}
