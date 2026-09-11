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

    public JpaIncidentRepositoryAdapter(IncidentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IncidentJpaRepository must not be null.");
    }

    @Override
    public Incident save(Incident model) {
        return IncidentPersistenceMapper.toDomain(repository.save(IncidentPersistenceMapper.toEntity(model)));
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
    public long count() {
        return repository.count();
    }
}
