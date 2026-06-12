/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditTargetReferenceRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.out
 *
 * @Description : Repository port for AuditTargetReference.
 *
 */
package dz.sh.hidra.modules.audit.application.port.out;

import dz.sh.hidra.modules.audit.domain.model.AuditTargetReference;

import java.util.Optional;

/**
 * Repository port for AuditTargetReference.
 */
public interface AuditTargetReferenceRepositoryPort {

    AuditTargetReference save(AuditTargetReference model);

    Optional<AuditTargetReference> findById(String id);
}
