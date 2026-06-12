/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyDocumentReferenceRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.out
 *
 * @Description : Repository port for PartyDocumentReference.
 *
 */
package dz.sh.hidra.modules.party.application.port.out;

import dz.sh.hidra.modules.party.domain.model.PartyDocumentReference;

import java.util.Optional;

/**
 * Repository port for PartyDocumentReference.
 */
public interface PartyDocumentReferenceRepositoryPort {

    PartyDocumentReference save(PartyDocumentReference model);

    Optional<PartyDocumentReference> findById(String id);
}
