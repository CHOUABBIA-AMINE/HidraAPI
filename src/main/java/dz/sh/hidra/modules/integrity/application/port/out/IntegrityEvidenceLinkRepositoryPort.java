/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityEvidenceLinkRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.out
 *
 * @Description : Repository port for IntegrityEvidenceLink.
 *
 */
package dz.sh.hidra.modules.integrity.application.port.out;

import dz.sh.hidra.modules.integrity.domain.model.IntegrityEvidenceLink;

import java.util.Optional;

/**
 * Repository port for IntegrityEvidenceLink.
 */
public interface IntegrityEvidenceLinkRepositoryPort {

    IntegrityEvidenceLink save(IntegrityEvidenceLink model);

    Optional<IntegrityEvidenceLink> findById(String id);
}
