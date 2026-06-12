/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CathodicProtectionMeasurementRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.out
 *
 * @Description : Repository port for CathodicProtectionMeasurement.
 *
 */
package dz.sh.hidra.modules.integrity.application.port.out;

import dz.sh.hidra.modules.integrity.domain.model.CathodicProtectionMeasurement;

import java.util.Optional;

/**
 * Repository port for CathodicProtectionMeasurement.
 */
public interface CathodicProtectionMeasurementRepositoryPort {

    CathodicProtectionMeasurement save(CathodicProtectionMeasurement model);

    Optional<CathodicProtectionMeasurement> findById(String id);
}
