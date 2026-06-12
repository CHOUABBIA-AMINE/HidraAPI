/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTicketLineRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Repository port for CustodyTicketLine.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.domain.model.CustodyTicketLine;

import java.util.Optional;

/**
 * Repository port for CustodyTicketLine.
 */
public interface CustodyTicketLineRepositoryPort {

    CustodyTicketLine save(CustodyTicketLine model);

    Optional<CustodyTicketLine> findById(String id);
}
