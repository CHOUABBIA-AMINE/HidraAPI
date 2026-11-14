/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCandidateRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.out
 *
 * @Description : Repository port for LeakCandidate.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.port.out;

import dz.sh.hidra.modules.leakdetection.domain.model.LeakCandidate;
import java.util.List;
import java.util.Optional;

/**
 * Repository port for LeakCandidate.
 */
public interface LeakCandidateRepositoryPort {

    record ProfileRef(String id, String assetType, String assetId, boolean active) { }
    record AuthorizedPage(List<LeakCandidate> content, long total) { }
    Optional<ProfileRef> profile(String profileId);
    AuthorizedPage findAuthorized(List<dz.sh.hidra.modules.leakdetection.application.port.out.LeakCandidateResourceAuthorizationPort.AssetRef> assets,
            int page, int size);

    void flush();

    LeakCandidate save(LeakCandidate model);

    Optional<LeakCandidate> findById(String id);

    List<LeakCandidate> findAll(int page, int size);

    long count();
}
