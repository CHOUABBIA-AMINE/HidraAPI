/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaFacilityRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for Facility.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.FacilityJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.Optional;
@Component
public class JpaFacilityRepositoryAdapter implements FacilityRepositoryPort {
    private final FacilityJpaRepository repository;
    private org.springframework.jdbc.core.JdbcTemplate jdbc;
    @org.springframework.beans.factory.annotation.Autowired
    public JpaFacilityRepositoryAdapter(FacilityJpaRepository repository,org.springframework.jdbc.core.JdbcTemplate jdbc){this(repository);this.jdbc=Objects.requireNonNull(jdbc);}

    public JpaFacilityRepositoryAdapter(FacilityJpaRepository repository) { this.repository = Objects.requireNonNull(repository, "FacilityJpaRepository must not be null."); }
    @org.springframework.transaction.annotation.Transactional
    public Facility save(Facility model) {
        if(jdbc==null || !Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_facility_type WHERE id=? AND facility_kind=? AND status='ACTIVE')",Boolean.class,model.facilityTypeId(),model.facilityKind().name())))
            throw new dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException("Active compatible Facility type required.");
        return TopologyPersistenceMapper.toDomain(repository.save(TopologyPersistenceMapper.toEntity(model))); }
    public Optional<Facility> findById(String id) { return repository.findById(id).map(TopologyPersistenceMapper::toDomain); }
}
