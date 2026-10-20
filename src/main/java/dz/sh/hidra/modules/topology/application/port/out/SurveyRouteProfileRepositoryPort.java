/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SurveyRouteProfileRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Defines immutable survey snapshots within the existing draft transaction.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile;
import java.util.UUID;
public interface SurveyRouteProfileRepositoryPort {
    record Snapshot(long version,long graphVersion,String graphSha256,String sha256,SurveyRouteProfile profile) {
        public Snapshot {
            if(version<1 || graphVersion<1 || graphVersion>=version || profile==null
               || graphSha256==null || !graphSha256.matches("[0-9a-f]{64}")
               || sha256==null || !sha256.matches("[0-9a-f]{64}"))throw new IllegalArgumentException("Invalid survey snapshot");
        }
    }
    void validateProfile(SurveyRouteProfile profile);
    Snapshot asOf(UUID draftId,String branchId,long selectedVersion);
    Snapshot append(UUID draftId,long newVersion,long graphVersion,String graphSha256,SurveyRouteProfile profile,long transactionId);
}
