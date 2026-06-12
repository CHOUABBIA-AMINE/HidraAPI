/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WallThicknessMeasurementRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.out
 *
 * @Description : Repository port for WallThicknessMeasurement.
 *
 */
package dz.sh.hidra.modules.integrity.application.port.out;

import dz.sh.hidra.modules.integrity.domain.model.WallThicknessMeasurement;

import java.util.Optional;

/**
 * Repository port for WallThicknessMeasurement.
 */
public interface WallThicknessMeasurementRepositoryPort {

    WallThicknessMeasurement save(WallThicknessMeasurement model);

    Optional<WallThicknessMeasurement> findById(String id);
}
