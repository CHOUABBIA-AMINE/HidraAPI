/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyAgreementPartyRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Repository port for CustodyAgreementParty.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.domain.model.CustodyAgreementParty;

import java.util.Optional;

/**
 * Repository port for CustodyAgreementParty.
 */
public interface CustodyAgreementPartyRepositoryPort {

    CustodyAgreementParty save(CustodyAgreementParty model);

    Optional<CustodyAgreementParty> findById(String id);
}
