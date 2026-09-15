/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalCredentialRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Defines persistence access for Identity-owned LOCAL credentials without exposing Spring Data.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.model.LocalCredential;
import java.util.Optional;

/**
 * Outbound persistence port for LOCAL credentials.
 */
public interface LocalCredentialRepositoryPort {

    LocalCredential save(LocalCredential credential);

    Optional<LocalCredential> findById(String id);

    Optional<LocalCredential> findByUserId(String userId);
}
