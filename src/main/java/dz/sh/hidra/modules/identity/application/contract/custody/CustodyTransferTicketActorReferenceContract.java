/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTransferTicketActorReferenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.custody
 *
 * @Description : Validates owner-controlled CustodyTransferTicket references and preserves historical evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.custody;

import java.time.Instant;
/** Real actor eligibility only; caller names and snapshots are not credentials. */
public interface CustodyTransferTicketActorReferenceContract { boolean eligible(String actorId, Instant at); }
