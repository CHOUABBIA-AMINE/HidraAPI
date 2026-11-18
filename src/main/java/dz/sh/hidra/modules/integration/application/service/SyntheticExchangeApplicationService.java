/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : SyntheticExchangeApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.service
 * @Description : Govern scoped synthetic Integration exchange admission, isolated receiver execution and manual replay.
 */
package dz.sh.hidra.modules.integration.application.service;

import dz.sh.hidra.modules.integration.application.port.in.SyntheticExchangeUseCase;
import dz.sh.hidra.modules.integration.application.port.out.SyntheticExchangeOwnerPort;
import dz.sh.hidra.modules.integration.application.port.out.SyntheticExchangeRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Attempt;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Phase;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Scope;
import dz.sh.hidra.modules.integration.domain.service.SyntheticDocumentDraftMappingV1;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public final class SyntheticExchangeApplicationService implements SyntheticExchangeUseCase {
    private final SyntheticExchangeRepositoryPort records;
    private final SyntheticExchangeOwnerPort owners;
    private final TransactionTemplate transaction;

    public SyntheticExchangeApplicationService(SyntheticExchangeRepositoryPort records,
            SyntheticExchangeOwnerPort owners, PlatformTransactionManager manager) {
        this.records=Objects.requireNonNull(records);
        this.owners=Objects.requireNonNull(owners);
        this.transaction=new TransactionTemplate(Objects.requireNonNull(manager));
        transaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Override public Mapping mapping(Scope scope,String profileId) {
        String id=bounded(profileId,80);
        return tx(() -> {
            owners.require(scope,SyntheticExchangeOwnerPort.Operation.READ);
            var binding=records.activeBinding(scope,id);
            return new Mapping(binding.profileId(),binding.schemaVersionId(),binding.schemaChecksum(),
                    binding.mappingDigest(),binding.documentTypeId(),binding.classificationId());
        });
    }

    @Override public Exchange submit(Scope scope,Submit command) {
        Objects.requireNonNull(command);
        String key=bounded(command.requestKey(),80);
        String profile=bounded(command.mappingProfileId(),80);
        String schema=bounded(command.schemaVersionId(),80);
        String title=SyntheticDocumentDraftMappingV1.title(command.titleFr());
        String projection=SyntheticDocumentDraftMappingV1.projection(title);
        String payloadHash=SyntheticDocumentDraftMappingV1.sha256(projection);
        var admission=tx(() -> {
            String actor=owners.require(scope,SyntheticExchangeOwnerPort.Operation.EXECUTE);
            owners.requireDraft(scope);
            records.lockKey(scope,key);
            var old=records.byKey(scope,key);
            if (old.isPresent()) {
                var prior=old.orElseThrow();
                if (!actor.equals(prior.submitter())) throw hidden();
                if (!profile.equals(prior.profileId()) || !schema.equals(prior.schemaVersionId())
                        || !payloadHash.equals(prior.payloadDigest())) throw conflict();
                return new Admission(prior.id(),true);
            }
            var binding=records.activeBinding(scope,profile);
            if (!schema.equals(binding.schemaVersionId())) throw conflict();
            Instant now=Instant.now();
            String id=id();
            var exchange=new SyntheticExchange(id,scope,key,actor,binding.id(),profile,schema,
                    binding.schemaChecksum(),binding.mappingDigest(),payloadHash,projection,title,
                    binding.documentTypeId(),binding.classificationId(),binding.jobDefinitionId(),
                    binding.externalSystemId(),binding.messageTypeId(),binding.payloadFormatId(),
                    Phase.CLAIMED,1,null,now);
            records.insert(exchange,attempt(id,1,actor,key,null,now));
            return new Admission(id,false);
        });
        return execute(scope,admission.id(),SyntheticExchangeOwnerPort.Operation.EXECUTE,admission.replayed());
    }

    @Override public Exchange detail(Scope scope,String exchangeId) {
        String id=bounded(exchangeId,80);
        return tx(() -> {
            owners.require(scope,SyntheticExchangeOwnerPort.Operation.READ);
            return view(records.byId(scope,id,false).orElseThrow(SyntheticExchangeApplicationService::hidden),false);
        });
    }

    @Override public List<AttemptView> attempts(Scope scope,String exchangeId) {
        String id=bounded(exchangeId,80);
        return tx(() -> {
            owners.require(scope,SyntheticExchangeOwnerPort.Operation.READ);
            var exchange=records.byId(scope,id,false).orElseThrow(SyntheticExchangeApplicationService::hidden);
            return records.attempts(exchange.id()).stream().map(a->new AttemptView(a.ordinal(),
                    a.phase().name(),a.actor(),a.runId(),a.messageId(),a.inboundId(),
                    a.deadLetterId(),a.failureCode(),a.reason(),a.claimedAt(),a.finishedAt())).toList();
        });
    }

    @Override public Exchange replay(Scope scope,String exchangeId,Replay command) {
        Objects.requireNonNull(command);
        String id=bounded(exchangeId,80);
        String key=bounded(command.requestKey(),80);
        String reason=bounded(command.reason(),500);
        if (command.expectedAttempt()<1 || command.expectedAttempt()>2)
            throw new IllegalArgumentException("Invalid expected attempt.");
        var admitted=tx(() -> {
            String actor=owners.require(scope,SyntheticExchangeOwnerPort.Operation.REPLAY);
            owners.requireDraft(scope);
            var exchange=records.byId(scope,id,true).orElseThrow(SyntheticExchangeApplicationService::hidden);
            var attempts=records.attempts(exchange.id());
            if (attempts.isEmpty()) throw conflict();
            Attempt latest=attempts.getLast();
            if (latest.ordinal()==2 && actor.equals(latest.actor())
                    && key.equals(latest.requestKey()) && reason.equals(latest.reason())
                    && command.expectedAttempt()==1) {
                return new Admission(exchange.id(),true);
            }
            if (exchange.phase()==Phase.CLAIMED && exchange.latestAttempt()==1
                    && latest.expiresAt().isBefore(Instant.now())) {
                records.abandon(exchange,latest,"CLAIM_LEASE_EXPIRED");
                exchange=records.byId(scope,id,true).orElseThrow();
                latest=records.attempts(exchange.id()).getLast();
            }
            exchange.requireReplay(actor,command.expectedAttempt());
            if (latest.phase()!=Phase.FAILED && latest.phase()!=Phase.ABANDONED) throw conflict();
            records.requireCurrentBinding(exchange);
            records.insertReplay(exchange,attempt(exchange.id(),2,actor,key,reason,Instant.now()));
            return new Admission(exchange.id(),false);
        });
        return execute(scope,admitted.id(),SyntheticExchangeOwnerPort.Operation.REPLAY,admitted.replayed());
    }

    private Exchange execute(Scope scope,String exchangeId,
            SyntheticExchangeOwnerPort.Operation operation,boolean replayed) {
        try {
            return tx(() -> {
                String actor=owners.require(scope,operation);
                var exchange=records.byId(scope,exchangeId,true)
                        .orElseThrow(SyntheticExchangeApplicationService::hidden);
                var latest=records.attempts(exchange.id()).getLast();
                if (exchange.phase()!=Phase.CLAIMED)
                    return view(exchange,replayed);
                if (!actor.equals(latest.actor())) throw hidden();
                if (latest.expiresAt().isBefore(Instant.now())) {
                    // An expired original claim cannot overwrite a later claimant.
                    // The fenced latest ordinal is checked again under this row lock.
                    if (exchange.latestAttempt()!=latest.ordinal()) throw conflict();
                }
                records.requireCurrentBinding(exchange);
                owners.requireDraft(scope);
                var draft=owners.createDraft(scope,exchange.id(),exchange.titleFr(),
                        exchange.documentTypeId(),exchange.classificationId());
                if (draft.documentId()==null || draft.documentId().isBlank()) throw conflict();
                records.succeeded(exchange,latest,draft.documentId());
                return view(records.byId(scope,exchange.id(),false).orElseThrow(),replayed);
            });
        } catch (SyntheticExchangeOwnerPort.TransientReceiverFailure transientFailure) {
            // The receiving transaction has rolled back.  Only a safe typed terminal
            // failure is written in this second transaction; no exception text is stored.
            return tx(() -> {
                owners.require(scope,operation);
                var exchange=records.byId(scope,exchangeId,true)
                        .orElseThrow(SyntheticExchangeApplicationService::hidden);
                var latest=records.attempts(exchange.id()).getLast();
                if (exchange.phase()==Phase.CLAIMED) {
                    records.failed(exchange,latest,"RECEIVER_TRANSIENT_UNAVAILABLE");
                }
                return view(records.byId(scope,exchangeId,false).orElseThrow(),replayed);
            });
        }
    }
    private Exchange view(SyntheticExchange exchange,boolean replayed) {
        var current=records.attempts(exchange.id()).getLast();
        return new Exchange(exchange.id(),exchange.phase().name(),exchange.profileId(),
                exchange.schemaVersionId(),exchange.schemaChecksum(),exchange.mappingDigest(),
                exchange.payloadDigest(),exchange.latestAttempt(),current.failureCode(),
                exchange.phase()==Phase.SUCCEEDED?exchange.documentId():null,replayed);
    }
    private static Attempt attempt(String exchange,int ordinal,String actor,String key,String reason,Instant now) {
        return new Attempt(id(),exchange,ordinal,actor,key,reason,id(),id(),id(),
                null,Phase.CLAIMED,null,null,now,now.plusSeconds(120),null);
    }
    private <T> T tx(Supplier<T> action) {
        return Objects.requireNonNull(transaction.execute(status->action.get()));
    }
    private record Admission(String id,boolean replayed) {}
    private static String id() { return UUID.randomUUID().toString(); }
    private static String bounded(String input,int limit) {
        return SyntheticExchange.bounded(input,limit);
    }
    private static NoSuchElementException hidden() {
        return new NoSuchElementException("Protected exchange unavailable.");
    }
    private static IllegalStateException conflict() {
        return new IllegalStateException("Synthetic exchange state conflict.");
    }
}
