/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterDocumentUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.in
 *
 * @Description : Use case for registering documents.
 *
 */
package dz.sh.hidra.modules.documents.application.port.in;

import dz.sh.hidra.modules.documents.application.command.RegisterDocumentCommand;
import dz.sh.hidra.modules.documents.application.dto.DocumentSummaryDto;

/**
 * Use case for registering documents.
 */
public interface RegisterDocumentUseCase {

    DocumentSummaryDto registerDocument(RegisterDocumentCommand command);
}
