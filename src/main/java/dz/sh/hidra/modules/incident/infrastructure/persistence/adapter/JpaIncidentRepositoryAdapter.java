/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for Incident.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.out.IncidentRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.Incident;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentJpaRepository;
import java.util.List;
import java.util.Objects;
import dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationAuthorizationPort;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * Database-backed repository adapter for Incident.
 */
@Component
public class JpaIncidentRepositoryAdapter implements IncidentRepositoryPort {

    private final IncidentJpaRepository repository;

    private final IncidentCatalogValidation catalogs;
    private final dz.sh.hidra.modules.incident.application.port.out.IncidentReferencePolicyPort references;
    public JpaIncidentRepositoryAdapter(IncidentJpaRepository repository, IncidentCatalogValidation catalogs,
            dz.sh.hidra.modules.incident.application.port.out.IncidentReferencePolicyPort references) {
        this.catalogs=Objects.requireNonNull(catalogs);this.references=Objects.requireNonNull(references);
        this.repository = Objects.requireNonNull(repository, "IncidentJpaRepository must not be null.");
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Incident save(Incident model) {
        var old=findByIdForUpdate(model.id()).orElse(null);
        if(model.status()==dz.sh.hidra.modules.incident.domain.value.IncidentStatus.CLOSED && (old==null || !old.equals(model)))
            throw new IllegalArgumentException("CLOSED state is owned by the atomic closure operation.");
        if(old!=null && old.status()==dz.sh.hidra.modules.incident.domain.value.IncidentStatus.CLOSED && !old.equals(model))
            throw new IllegalArgumentException("Closed Incident is immutable.");
        catalogs.require(model.classificationId(),"INCIDENT_CLASSIFICATION",old==null || !Objects.equals(old.classificationId(),model.classificationId()));
        catalogs.require(model.severityId(),"INCIDENT_SEVERITY",old==null || !Objects.equals(old.severityId(),model.severityId()));
        if(model.priorityId()!=null) catalogs.require(model.priorityId(),"INCIDENT_PRIORITY",old==null || !Objects.equals(old.priorityId(),model.priorityId()));
        references.validate(model,old);
        return IncidentPersistenceMapper.toDomain(repository.save(IncidentPersistenceMapper.toEntity(model)));
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Optional<Incident> findByIdForUpdate(String id) {
        return repository.findByIdForUpdate(id).map(IncidentPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Incident> findById(String id) {
        return repository.findById(id).map(IncidentPersistenceMapper::toDomain);
    }

    @Override
    public List<Incident> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt")))
                .stream()
                .map(IncidentPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public void flush() { repository.flush(); }

    @Override
    public AuthorizedPage findAuthorized(List<IncidentEscalationAuthorizationPort.AssetRef> assets,
            List<String> sourceDeniedIncidentIds,int page,int size) {
        if(assets==null || assets.isEmpty())return new AuthorizedPage(List.of(),0);
        Specification<dz.sh.hidra.modules.incident.infrastructure.persistence.entity.IncidentJpaEntity> spec=
          (root,query,cb)-> {
              Predicate authorizedAsset=cb.or(assets.stream()
                  .map(a->cb.and(cb.equal(root.get("topologyAssetTypeCode"),a.type()),
                                 cb.equal(root.get("topologyAssetId"),a.id())))
                  .toArray(Predicate[]::new));
              Predicate notDenied=sourceDeniedIncidentIds==null||sourceDeniedIncidentIds.isEmpty()
                   ?cb.conjunction():cb.not(root.get("id").in(sourceDeniedIncidentIds));
              return cb.and(authorizedAsset,notDenied);
          };
        var result=repository.findAll(spec,PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"updatedAt")));
        return new AuthorizedPage(result.getContent().stream()
            .map(IncidentPersistenceMapper::toDomain).toList(),result.getTotalElements());
    }

    @Override
    public long count() {
        return repository.count();
    }
}
