/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeasurementSnapshotRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Repository port for CustodyMeasurementSnapshot.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.domain.model.CustodyMeasurementSnapshot;

import java.util.Optional;

/**
 * Repository port for CustodyMeasurementSnapshot.
 */
public interface CustodyMeasurementSnapshotRepositoryPort {

    CustodyMeasurementSnapshot save(CustodyMeasurementSnapshot model);

    Optional<CustodyMeasurementSnapshot> findById(String id);
}
