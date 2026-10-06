/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaEquipmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for Equipment.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.EquipmentRepositoryPort;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.Equipment;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.EquipmentJpaRepository;
import dz.sh.hidra.modules.party.application.contract.topology.TopologyPartyReferenceContract;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.Optional;

@Component
public class JpaEquipmentRepositoryAdapter implements EquipmentRepositoryPort {
    private final EquipmentJpaRepository repository;
    private final TopologyPartyReferenceContract parties;
    public JpaEquipmentRepositoryAdapter(EquipmentJpaRepository repository, TopologyPartyReferenceContract parties) {
        this.repository = Objects.requireNonNull(repository);
        this.parties = Objects.requireNonNull(parties);
    }
    @Override
    @Transactional
    public Equipment save(Equipment model) {
        Objects.requireNonNull(model, "Equipment must not be null.");
        if (model.manufacturerPartyId() != null && !parties.exists(model.manufacturerPartyId())) {
            throw new InvalidTopologyValueException("Equipment manufacturer must reference an existing Party.");
        }
        return TopologyPersistenceMapper.toDomain(repository.saveAndFlush(TopologyPersistenceMapper.toEntity(model)));
    }
    @Override
    public Optional<Equipment> findById(String id) {
        return repository.findById(id).map(TopologyPersistenceMapper::toDomain);
    }
}
