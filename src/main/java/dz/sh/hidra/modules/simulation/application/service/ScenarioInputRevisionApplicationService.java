/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScenarioInputRevisionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.service
 * @Description : Coordinates two independent scoped Identity decisions and the owner-protected historical Topology proof.
 */
package dz.sh.hidra.modules.simulation.application.service;


import dz.sh.hidra.modules.simulation.application.port.in.ScenarioInputRevisionUseCase;
import dz.sh.hidra.modules.simulation.application.port.out.ScenarioInputOwnerAccessPort;
import dz.sh.hidra.modules.simulation.application.port.out.ScenarioInputRevisionRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision.Baseline;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision.Inputs;
import dz.sh.hidra.modules.simulation.domain.model.ScenarioInputRevision.Scope;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

/** Two separate owner decisions; no atomic cross-transaction revocation claim. */
@Service
public final class ScenarioInputRevisionApplicationService implements ScenarioInputRevisionUseCase {
    private final ScenarioInputOwnerAccessPort owners;
    private final ScenarioInputRevisionRepositoryPort repository;
    private final TransactionTemplate transaction;
    public ScenarioInputRevisionApplicationService(ScenarioInputOwnerAccessPort owners,
            ScenarioInputRevisionRepositoryPort repository,PlatformTransactionManager manager) {
        this.owners=Objects.requireNonNull(owners);
        this.repository=Objects.requireNonNull(repository);
        this.transaction=new TransactionTemplate(Objects.requireNonNull(manager));
    }
    private <T> T within(Supplier<T> operation) {
        if(TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isSynchronizationActive())
            throw new IllegalStateException("Scenario original transaction must not be nested");
        return Objects.requireNonNull(transaction.execute(state->operation.get()));
    }
    private static void match(Baseline exact,Baseline recorded) {
        if(!exact.equals(recorded))throw missing();
    }
    private static void expected(Inputs input,Baseline actual) {
        if(!actual.digest().equals(input.expectedManifestDigest()))throw invalid();
    }
    @Override public Result create(Create cmd) {
        Objects.requireNonNull(cmd);Objects.requireNonNull(cmd.scope());Objects.requireNonNull(cmd.inputs());
        Scope scope=cmd.scope(); Inputs input=cmd.inputs();
        String original=within(()->owners.requireCurrentWrite(scope));
        Baseline proof=owners.exactBaseline(scope,input.baselineReceiptId());
        expected(input,proof);
        return within(()->{
            String actor=owners.requireCurrentWrite(scope);
            if(!actor.equals(original))throw missing();
            var old=repository.byRequest(scope,actor,input.requestKey());
            if(old.isPresent())return replayCreate(old.orElseThrow(),scope,actor,input,proof);
            ScenarioInputRevision proposed=ScenarioInputRevision.prepare(UUID.randomUUID(),1,scope,proof,input,actor,Instant.now());
            if(repository.append(proposed))return new Result(proposed,false);
            return repository.byRequest(scope,actor,input.requestKey())
                    .map(saved->replayCreate(saved,scope,actor,input,proof)).orElseThrow(Conflict::new);
        });
    }
    private Result replayCreate(ScenarioInputRevision row,Scope scope,String actor,Inputs in,Baseline proof) {
        if(row.revision()!=1 || !row.scope().equals(scope) || !row.actorId().equals(actor)
                || !row.baseline().equals(proof)
                || !row.inputDigest().equals(ScenarioInputRevision.computeInputDigest(scope,proof,in))
                || !row.inputs().requestKey().equals(in.requestKey()))
            throw new Conflict();
        return new Result(row,true);
    }
    @Override public Result append(Append cmd) {
        Objects.requireNonNull(cmd);Objects.requireNonNull(cmd.scope());
        Objects.requireNonNull(cmd.scenarioId());Objects.requireNonNull(cmd.inputs());
        Scope scope=cmd.scope(); Inputs input=cmd.inputs();
        String original=within(()->{
            String actor=owners.requireCurrentWrite(scope);
            repository.locate(scope,cmd.scenarioId(),cmd.expectedRevision()).orElseThrow(ScenarioInputRevisionApplicationService::missing);
            return actor;
        });
        Baseline proof=owners.exactBaseline(scope,input.baselineReceiptId());
        expected(input,proof);
        return within(()->{
            String actor=owners.requireCurrentWrite(scope);
            if(!actor.equals(original))throw missing();
            // Replay remains possible after later heads; the key is globally unique inside this scope and actor.
            var old=repository.byRequest(scope,actor,input.requestKey());
            if(old.isPresent())return replayAppend(old.orElseThrow(),cmd,proof,actor);
            var head=repository.head(scope,cmd.scenarioId()).orElseThrow(ScenarioInputRevisionApplicationService::missing);
            if(head.revision()!=cmd.expectedRevision())throw new Conflict();
            var proposed=ScenarioInputRevision.prepare(cmd.scenarioId(),cmd.expectedRevision()+1,
                    scope,proof,input,actor,Instant.now());
            if(repository.append(proposed))return new Result(proposed,false);
            return repository.byRequest(scope,actor,input.requestKey())
                    .map(row->replayAppend(row,cmd,proof,actor)).orElseThrow(Conflict::new);
        });
    }
    private Result replayAppend(ScenarioInputRevision row,Append cmd,Baseline proof,String actor) {
        if(!row.scenarioId().equals(cmd.scenarioId()) || row.expectedRevision()!=cmd.expectedRevision()
                || !row.actorId().equals(actor) || !row.baseline().equals(proof)
                || !row.inputs().requestKey().equals(cmd.inputs().requestKey())
                || !row.inputDigest().equals(ScenarioInputRevision.computeInputDigest(cmd.scope(),proof,cmd.inputs())))
            throw new Conflict();
        return new Result(row,true);
    }
    @Override public Result read(Read query) {
        Objects.requireNonNull(query);Objects.requireNonNull(query.scope());
        String original=within(()->{
            String actor=owners.requireCurrentRead(query.scope());
            repository.locate(query.scope(),query.scenarioId(),query.revision()).orElseThrow(ScenarioInputRevisionApplicationService::missing);
            return actor;
        });
        var locator=within(()->{
            owners.requireCurrentRead(query.scope());
            return repository.locate(query.scope(),query.scenarioId(),query.revision()).orElseThrow(ScenarioInputRevisionApplicationService::missing);
        });
        Baseline proof=owners.exactBaseline(query.scope(),locator.baselineReceiptId());
        if(!proof.digest().equals(locator.baselineDigest()))throw missing();
        return within(()->{
            String actor=owners.requireCurrentRead(query.scope());
            if(!actor.equals(original))throw missing();
            var persisted=repository.find(query.scope(),query.scenarioId(),query.revision())
                    .orElseThrow(ScenarioInputRevisionApplicationService::missing);
            if(!persisted.baseline().equals(proof))throw missing();
            return new Result(persisted,false);
        });
    }
    @Override public Validation validate(Read query) {
        var revision=read(query).revision();
        return new Validation(revision.scenarioId(),revision.revision(),revision.policyId(),
                revision.validationStatus(),revision.numericalCapability(),
                revision.diagnosticCodes(),revision.inputDigest());
    }
    private static NoSuchElementException missing() {
        return new NoSuchElementException("Scoped scenario inputs unavailable");
    }
    private static IllegalArgumentException invalid() {
        return new IllegalArgumentException("Invalid baseline digest for scenario inputs");
    }
    public static final class Conflict extends RuntimeException {
        public Conflict(){super("Scenario revision conflict");}
    }
}
