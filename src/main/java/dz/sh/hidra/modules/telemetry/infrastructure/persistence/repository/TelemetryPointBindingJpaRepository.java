/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointBindingJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for telemetry point bindings.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryPointBindingJpaEntity;

/**
 * Spring Data repository for telemetry point bindings.
 */
public interface TelemetryPointBindingJpaRepository extends JpaRepository<TelemetryPointBindingJpaEntity, String> {

    List<TelemetryPointBindingJpaEntity> findByPointIdAndActiveTrue(String pointId);

    Page<TelemetryPointBindingJpaEntity> findByPointId(String pointId, Pageable pageable);

    Page<TelemetryPointBindingJpaEntity> findByTopologyAssetTypeCodeAndTopologyAssetId(
            String topologyAssetTypeCode,
            String topologyAssetId,
            Pageable pageable);

    Page<TelemetryPointBindingJpaEntity> findByBindingRoleId(String bindingRoleId, Pageable pageable);

    Page<TelemetryPointBindingJpaEntity> findByActive(Boolean active, Pageable pageable);
}
