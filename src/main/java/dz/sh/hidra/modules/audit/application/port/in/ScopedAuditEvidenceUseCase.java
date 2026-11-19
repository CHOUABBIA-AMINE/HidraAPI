/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedAuditEvidenceUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.in
 *
 * @Description : Defines exact T28 governed scoped Audit evidence.
 *
 */
package dz.sh.hidra.modules.audit.application.port.in;

import dz.sh.hidra.modules.audit.domain.model.ScopedAuditEvidence.*;
import java.util.UUID;

/** Four native T28 operations. Content failure is a value so receipts can commit before HTTP. */
public interface ScopedAuditEvidenceUseCase {
    Page search(Scope scope,Window window,Integer limit,String cursor);
    ExportResult createExport(Criteria criteria);
    Manifest exportManifest(Scope scope,UUID exportId);
    ContentResult download(Scope scope,UUID exportId);
}
