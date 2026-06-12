/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionProfileRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.out
 *
 * @Description : Repository port for LeakDetectionProfile.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.port.out;

import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionProfile;

import java.util.Optional;

/**
 * Repository port for LeakDetectionProfile.
 */
public interface LeakDetectionProfileRepositoryPort {

    LeakDetectionProfile save(LeakDetectionProfile model);

    Optional<LeakDetectionProfile> findById(String id);
}
