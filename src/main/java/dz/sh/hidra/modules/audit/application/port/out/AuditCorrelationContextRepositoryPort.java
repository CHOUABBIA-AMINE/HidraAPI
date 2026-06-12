/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditCorrelationContextRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.out
 *
 * @Description : Repository port for AuditCorrelationContext.
 *
 */
package dz.sh.hidra.modules.audit.application.port.out;

import dz.sh.hidra.modules.audit.domain.model.AuditCorrelationContext;

import java.util.Optional;

/**
 * Repository port for AuditCorrelationContext.
 */
public interface AuditCorrelationContextRepositoryPort {

    AuditCorrelationContext save(AuditCorrelationContext model);

    Optional<AuditCorrelationContext> findById(String id);
}
