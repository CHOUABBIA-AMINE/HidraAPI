/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseEvidenceLinkRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.out
 *
 * @Description : Repository port for HseCaseEvidenceLink.
 *
 */
package dz.sh.hidra.modules.hse.application.port.out;

import dz.sh.hidra.modules.hse.domain.model.HseCaseEvidenceLink;

import java.util.Optional;

/**
 * Repository port for HseCaseEvidenceLink.
 */
public interface HseCaseEvidenceLinkRepositoryPort {

    HseCaseEvidenceLink save(HseCaseEvidenceLink model);

    Optional<HseCaseEvidenceLink> findById(String id);
}
