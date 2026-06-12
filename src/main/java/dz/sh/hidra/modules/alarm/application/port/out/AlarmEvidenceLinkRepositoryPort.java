/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEvidenceLinkRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.out
 *
 * @Description : Repository port for AlarmEvidenceLink.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.out;

import dz.sh.hidra.modules.alarm.domain.model.AlarmEvidenceLink;

import java.util.Optional;

/**
 * Repository port for AlarmEvidenceLink.
 */
public interface AlarmEvidenceLinkRepositoryPort {

    AlarmEvidenceLink save(AlarmEvidenceLink model);

    Optional<AlarmEvidenceLink> findById(String id);
}
