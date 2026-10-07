/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditAccessRecordRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.out
 *
 * @Description : Repository port for AuditAccessRecord.
 *
 */
package dz.sh.hidra.modules.audit.application.port.out;

import dz.sh.hidra.modules.audit.domain.model.AuditAccessRecord;

import java.util.Optional;

/**
 * Repository port for AuditAccessRecord.
 */
public interface AuditAccessRecordRepositoryPort {

    /** Append a fresh ID; optional references, when supplied, must exist. */
    AuditAccessRecord save(AuditAccessRecord model);

    Optional<AuditAccessRecord> findById(String id);
}
