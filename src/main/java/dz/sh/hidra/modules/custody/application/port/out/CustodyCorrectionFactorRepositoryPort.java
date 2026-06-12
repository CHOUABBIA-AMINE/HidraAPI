/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyCorrectionFactorRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Repository port for CustodyCorrectionFactor.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.domain.model.CustodyCorrectionFactor;

import java.util.Optional;

/**
 * Repository port for CustodyCorrectionFactor.
 */
public interface CustodyCorrectionFactorRepositoryPort {

    CustodyCorrectionFactor save(CustodyCorrectionFactor model);

    Optional<CustodyCorrectionFactor> findById(String id);
}
