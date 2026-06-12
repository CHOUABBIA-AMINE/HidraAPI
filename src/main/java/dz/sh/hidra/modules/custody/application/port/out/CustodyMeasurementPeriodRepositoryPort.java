/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeasurementPeriodRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Repository port for CustodyMeasurementPeriod.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.domain.model.CustodyMeasurementPeriod;

import java.util.Optional;

/**
 * Repository port for CustodyMeasurementPeriod.
 */
public interface CustodyMeasurementPeriodRepositoryPort {

    CustodyMeasurementPeriod save(CustodyMeasurementPeriod model);

    Optional<CustodyMeasurementPeriod> findById(String id);
}
