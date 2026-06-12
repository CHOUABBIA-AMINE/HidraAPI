/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditRetentionPolicyRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.out
 *
 * @Description : Repository port for AuditRetentionPolicy.
 *
 */
package dz.sh.hidra.modules.audit.application.port.out;

import dz.sh.hidra.modules.audit.domain.model.AuditRetentionPolicy;

import java.util.Optional;

/**
 * Repository port for AuditRetentionPolicy.
 */
public interface AuditRetentionPolicyRepositoryPort {

    AuditRetentionPolicy save(AuditRetentionPolicy model);

    Optional<AuditRetentionPolicy> findById(String id);
}
