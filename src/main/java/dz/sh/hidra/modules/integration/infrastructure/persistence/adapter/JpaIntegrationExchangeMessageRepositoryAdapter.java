/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationExchangeMessageRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationExchangeMessage.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationExchangeMessageRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationExchangeMessage;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationExchangeMessageJpaRepository;
import org.springframework.stereotype.Component;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationJobRunJpaRepository;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.ExternalEndpointJpaRepository;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationCatalogEntryJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationExchangeMessage.
 */
@Component
public class JpaIntegrationExchangeMessageRepositoryAdapter implements IntegrationExchangeMessageRepositoryPort {

    private final IntegrationExchangeMessageJpaRepository repository;
    private final IntegrationJobRunJpaRepository runs;
    private final ExternalEndpointJpaRepository endpoints;
    private final IntegrationCatalogEntryJpaRepository catalogs;

    public JpaIntegrationExchangeMessageRepositoryAdapter(IntegrationExchangeMessageJpaRepository repository,
            IntegrationJobRunJpaRepository runs, ExternalEndpointJpaRepository endpoints,
            IntegrationCatalogEntryJpaRepository catalogs) {
        this.runs=Objects.requireNonNull(runs);this.endpoints=Objects.requireNonNull(endpoints);
        this.catalogs=Objects.requireNonNull(catalogs);
        this.repository = Objects.requireNonNull(repository, "IntegrationExchangeMessageJpaRepository must not be null.");
    }

    @Override
    @Transactional
    public IntegrationExchangeMessage save(IntegrationExchangeMessage model) {
        Objects.requireNonNull(model);
        if(model.jobRunId()!=null && !runs.existsById(model.jobRunId()))
            throw new InvalidIntegrationValueException("Integration message job run is unknown.");
        if(model.endpointId()!=null) {
            var endpoint=endpoints.findById(model.endpointId())
                    .orElseThrow(()->new InvalidIntegrationValueException("Integration message endpoint is unknown."));
            if(!model.externalSystemId().equals(endpoint.externalSystemId()))
                throw new InvalidIntegrationValueException("Integration message endpoint belongs to another external system.");
        }
        requireCatalog(model.messageTypeId(),"MESSAGE_TYPE");
        requireCatalog(model.payloadFormatId(),"PAYLOAD_FORMAT");
        return IntegrationPersistenceMapper.toDomain(repository.saveAndFlush(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationExchangeMessage> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
    private void requireCatalog(String id,String family) {
        var entry=catalogs.findById(id).orElseThrow(()->new InvalidIntegrationValueException("Integration message catalog entry is unknown."));
        if(!entry.active() || !family.equals(entry.catalogName()))
            throw new InvalidIntegrationValueException("Integration message catalog entry is inactive or in the wrong family.");
    }

}
