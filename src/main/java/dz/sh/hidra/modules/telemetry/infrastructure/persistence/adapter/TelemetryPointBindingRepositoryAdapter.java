/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointBindingRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter for telemetry point binding repository port.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryPointBindingRepositoryPort;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPointBinding;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryBindingRoleReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointBindingId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryPointBindingJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.mapper.TelemetryPersistenceMapper;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryPointBindingJpaRepository;

/**
 * Persistence adapter for telemetry point binding repository port.
 */
@Repository
public class TelemetryPointBindingRepositoryAdapter implements TelemetryPointBindingRepositoryPort {

    private final TelemetryPointBindingJpaRepository bindingRepository;
    private final TelemetryPersistenceMapper mapper;

    public TelemetryPointBindingRepositoryAdapter(
            TelemetryPointBindingJpaRepository bindingRepository,
            TelemetryPersistenceMapper mapper) {

        this.bindingRepository = Objects.requireNonNull(bindingRepository, "TelemetryPointBindingJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryPersistenceMapper must not be null.");
    }

    @Override
    public TelemetryPointBinding save(TelemetryPointBinding binding) {
        return mapper.toDomain(bindingRepository.save(mapper.toEntity(binding)));
    }

    @Override
    public Optional<TelemetryPointBinding> findById(TelemetryPointBindingId id) {
        return bindingRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<TelemetryPointBinding> findActiveByPointId(TelemetryPointId pointId) {
        return bindingRepository.findByPointIdAndActiveTrue(pointId.value())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public PageResult<TelemetryPointBinding> findAll(
            TelemetryPointId pointId,
            TelemetryCode assetTypeCode,
            String assetId,
            TelemetryBindingRoleReference bindingRole,
            Boolean active,
            PageRequest pageRequest) {

        Pageable pageable = TelemetrySpringPageables.from(pageRequest);
        Page<TelemetryPointBindingJpaEntity> page;

        if (pointId != null) {
            page = bindingRepository.findByPointId(pointId.value(), pageable);
        } else if (assetTypeCode != null && assetId != null) {
            page = bindingRepository.findByTopologyAssetTypeCodeAndTopologyAssetId(assetTypeCode.value(), assetId, pageable);
        } else if (bindingRole != null) {
            page = bindingRepository.findByBindingRoleId(bindingRole.id(), pageable);
        } else if (active != null) {
            page = bindingRepository.findByActive(active, pageable);
        } else {
            page = bindingRepository.findAll(pageable);
        }

        return TelemetrySpringPageables.toPageResult(page, mapper::toDomain);
    }
}
