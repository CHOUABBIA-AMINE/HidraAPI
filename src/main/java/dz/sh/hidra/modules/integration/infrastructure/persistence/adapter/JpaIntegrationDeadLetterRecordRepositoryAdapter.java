/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationDeadLetterRecordRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationDeadLetterRecord.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationDeadLetterRecordRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationDeadLetterRecord;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationDeadLetterRecordJpaRepository;
import org.springframework.stereotype.Component;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.identity.application.contract.integration.IntegrationResolverContract;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationJobRunJpaRepository;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationExchangeMessageJpaRepository;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationInboundRecordJpaRepository;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationOutboundRecordJpaRepository;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import java.time.Instant;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationDeadLetterRecord.
 */
@Component
public class JpaIntegrationDeadLetterRecordRepositoryAdapter implements IntegrationDeadLetterRecordRepositoryPort {

    private final IntegrationDeadLetterRecordJpaRepository repository;
    private final IntegrationJobRunJpaRepository runs;
    private final IntegrationExchangeMessageJpaRepository messages;
    private final IntegrationInboundRecordJpaRepository inbound;
    private final IntegrationOutboundRecordJpaRepository outbound;
    private final IntegrationResolverContract resolvers;
    private final CurrentSecurityContext security;

    public JpaIntegrationDeadLetterRecordRepositoryAdapter(IntegrationDeadLetterRecordJpaRepository repository,
            IntegrationJobRunJpaRepository runs, IntegrationExchangeMessageJpaRepository messages,
            IntegrationInboundRecordJpaRepository inbound, IntegrationOutboundRecordJpaRepository outbound,
            IntegrationResolverContract resolvers, CurrentSecurityContext security) {
        this.runs=Objects.requireNonNull(runs);this.messages=Objects.requireNonNull(messages);
        this.inbound=Objects.requireNonNull(inbound);this.outbound=Objects.requireNonNull(outbound);
        this.resolvers=Objects.requireNonNull(resolvers);this.security=Objects.requireNonNull(security);
        this.repository = Objects.requireNonNull(repository, "IntegrationDeadLetterRecordJpaRepository must not be null.");
    }

    @Override
    @Transactional
    public IntegrationDeadLetterRecord save(IntegrationDeadLetterRecord model) {
        Objects.requireNonNull(model);
        if(model.jobRunId()!=null && !runs.existsById(model.jobRunId()))unknownReference();
        if(model.exchangeMessageId()!=null && !messages.existsById(model.exchangeMessageId()))unknownReference();
        if(model.inboundRecordId()!=null && !inbound.existsById(model.inboundRecordId()))unknownReference();
        if(model.outboundRecordId()!=null && !outbound.existsById(model.outboundRecordId()))unknownReference();
        var existing=repository.findById(model.id()).map(IntegrationPersistenceMapper::toDomain);
        if(existing.isPresent() && existing.orElseThrow().hasManualResolution()) {
            var previous=existing.orElseThrow();
            if(!Objects.equals(previous.resolvedByActorId(),model.resolvedByActorId())
                    || !Objects.equals(previous.resolvedAt(),model.resolvedAt())
                    || !Objects.equals(previous.resolutionComment(),model.resolutionComment()))
                throw new InvalidIntegrationValueException("Recorded manual-resolution provenance cannot be replaced or removed.");
        } else if(model.hasManualResolution()) {
            var principal=security.currentPrincipal().filter(p->p.authenticated())
                    .orElseThrow(()->new InvalidIntegrationValueException("New manual resolution requires authentication."));
            if(!model.resolvedByActorId().equals(principal.actorId().value())
                    || !resolvers.eligibleResolver(model.resolvedByActorId(),Instant.now()))
                throw new InvalidIntegrationValueException("Manual resolver must match an eligible authenticated Identity actor.");
        }
        return IntegrationPersistenceMapper.toDomain(repository.saveAndFlush(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationDeadLetterRecord> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
    private void unknownReference(){throw new InvalidIntegrationValueException("Integration dead-letter evidence reference is unknown.");}

}
