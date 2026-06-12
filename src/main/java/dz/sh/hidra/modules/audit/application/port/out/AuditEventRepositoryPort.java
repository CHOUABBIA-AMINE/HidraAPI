/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditEventRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.out
 *
 * @Description : Repository port for AuditEvent.
 *
 */
package dz.sh.hidra.modules.audit.application.port.out;

import dz.sh.hidra.modules.audit.domain.model.AuditEvent;

import java.util.Optional;

/**
 * Repository port for AuditEvent.
 */
public interface AuditEventRepositoryPort {

    AuditEvent save(AuditEvent model);

    Optional<AuditEvent> findById(String id);
}
