/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentEvidenceLinkRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.out
 *
 * @Description : Repository port for IncidentEvidenceLink.
 *
 */
package dz.sh.hidra.modules.incident.application.port.out;

import dz.sh.hidra.modules.incident.domain.model.IncidentEvidenceLink;

import java.util.Optional;

/**
 * Repository port for IncidentEvidenceLink.
 */
public interface IncidentEvidenceLinkRepositoryPort {

    IncidentEvidenceLink save(IncidentEvidenceLink model);

    Optional<IncidentEvidenceLink> findById(String id);
}
