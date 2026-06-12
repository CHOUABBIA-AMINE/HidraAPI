/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CorrosionFeatureRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.out
 *
 * @Description : Repository port for CorrosionFeature.
 *
 */
package dz.sh.hidra.modules.integrity.application.port.out;

import dz.sh.hidra.modules.integrity.domain.model.CorrosionFeature;

import java.util.Optional;

/**
 * Repository port for CorrosionFeature.
 */
public interface CorrosionFeatureRepositoryPort {

    CorrosionFeature save(CorrosionFeature model);

    Optional<CorrosionFeature> findById(String id);
}
