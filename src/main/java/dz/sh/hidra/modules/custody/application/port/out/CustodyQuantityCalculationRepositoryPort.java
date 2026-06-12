/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyQuantityCalculationRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Repository port for CustodyQuantityCalculation.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.domain.model.CustodyQuantityCalculation;

import java.util.Optional;

/**
 * Repository port for CustodyQuantityCalculation.
 */
public interface CustodyQuantityCalculationRepositoryPort {

    CustodyQuantityCalculation save(CustodyQuantityCalculation model);

    Optional<CustodyQuantityCalculation> findById(String id);
}
