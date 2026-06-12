/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeterRunSnapshotRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Repository port for CustodyMeterRunSnapshot.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.domain.model.CustodyMeterRunSnapshot;

import java.util.Optional;

/**
 * Repository port for CustodyMeterRunSnapshot.
 */
public interface CustodyMeterRunSnapshotRepositoryPort {

    CustodyMeterRunSnapshot save(CustodyMeterRunSnapshot model);

    Optional<CustodyMeterRunSnapshot> findById(String id);
}
