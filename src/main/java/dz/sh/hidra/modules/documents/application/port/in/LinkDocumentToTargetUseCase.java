/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LinkDocumentToTargetUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.in
 *
 * @Description : Use case for linking documents to business targets.
 *
 */
package dz.sh.hidra.modules.documents.application.port.in;

import dz.sh.hidra.modules.documents.application.command.LinkDocumentToTargetCommand;
import dz.sh.hidra.modules.documents.application.dto.DocumentTargetLinkSummaryDto;

/**
 * Use case for linking documents to business targets.
 */
public interface LinkDocumentToTargetUseCase {

    DocumentTargetLinkSummaryDto linkDocumentToTarget(LinkDocumentToTargetCommand command);
}
