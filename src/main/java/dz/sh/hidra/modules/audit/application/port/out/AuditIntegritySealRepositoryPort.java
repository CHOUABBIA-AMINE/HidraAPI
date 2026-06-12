/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditIntegritySealRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.out
 *
 * @Description : Repository port for AuditIntegritySeal.
 *
 */
package dz.sh.hidra.modules.audit.application.port.out;

import dz.sh.hidra.modules.audit.domain.model.AuditIntegritySeal;

import java.util.Optional;

/**
 * Repository port for AuditIntegritySeal.
 */
public interface AuditIntegritySealRepositoryPort {

    AuditIntegritySeal save(AuditIntegritySeal model);

    Optional<AuditIntegritySeal> findById(String id);
}
