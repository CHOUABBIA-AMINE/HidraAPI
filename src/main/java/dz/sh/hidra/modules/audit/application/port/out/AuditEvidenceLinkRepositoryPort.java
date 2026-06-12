/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditEvidenceLinkRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.out
 *
 * @Description : Repository port for AuditEvidenceLink.
 *
 */
package dz.sh.hidra.modules.audit.application.port.out;

import dz.sh.hidra.modules.audit.domain.model.AuditEvidenceLink;

import java.util.Optional;

/**
 * Repository port for AuditEvidenceLink.
 */
public interface AuditEvidenceLinkRepositoryPort {

    AuditEvidenceLink save(AuditEvidenceLink model);

    Optional<AuditEvidenceLink> findById(String id);
}
