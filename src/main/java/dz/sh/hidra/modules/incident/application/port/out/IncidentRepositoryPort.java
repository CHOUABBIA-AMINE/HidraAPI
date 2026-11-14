/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.out
 *
 * @Description : Repository port for Incident.
 *
 */
package dz.sh.hidra.modules.incident.application.port.out;

import dz.sh.hidra.modules.incident.domain.model.Incident;
import java.util.List;
import java.util.Optional;

/**
 * Repository port for Incident.
 */
public interface IncidentRepositoryPort {

    Incident save(Incident model);

    Optional<Incident> findById(String id);

    default Optional<Incident> findByIdForUpdate(String id) { throw new UnsupportedOperationException("Locked Incident lookup required."); }

    List<Incident> findAll(int page, int size);

    long count();

    record AuthorizedPage(List<Incident> content, long total) {}
    AuthorizedPage findAuthorized(List<IncidentEscalationAuthorizationPort.AssetRef> assets,
            List<String> sourceDeniedIncidentIds, int page, int size);
    default void flush() { throw new UnsupportedOperationException("Incident flush required."); }
}
