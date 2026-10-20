/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SurveyRouteProfileUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Defines typed protected per-branch survey draft commands.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft;
import java.util.List;
import java.util.UUID;

public interface SurveyRouteProfileUseCase {
    enum Kind { READ, UPDATE }
    record Command(Kind kind, String companyPartyId, String pipelineSystemId, UUID draftId,
            String branchId, Long expectedVersion, Long requestedVersion, SurveyRouteProfile profile) {
        public Command {
            if(kind==null || draftId==null)throw new IllegalArgumentException("Survey operation and draft required");
            EngineeringNetworkDraft.identity(companyPartyId);EngineeringNetworkDraft.identity(pipelineSystemId);
            branchId=EngineeringGraphDraft.identity(branchId);
            if(kind==Kind.UPDATE) {
                if(expectedVersion==null || expectedVersion<1 || requestedVersion!=null || profile==null
                    || !branchId.equals(profile.branchId()))throw new IllegalArgumentException("Invalid survey update");
            } else if(expectedVersion!=null || profile!=null || requestedVersion!=null && requestedVersion<1)
                throw new IllegalArgumentException("Invalid survey read");
        }
    }
    record Result(UUID draftId, String branchId, long version, Long surveyVersion, String surveySha256,
            Long boundGraphVersion, String boundGraphSha256, SurveyRouteProfile profile, List<String> diagnostics) {
        public Result {
            if(draftId==null || version<1)throw new IllegalArgumentException("Invalid survey result");
            EngineeringGraphDraft.identity(branchId);
            boolean absent=profile==null;
            if(absent != (surveyVersion==null) || absent != (surveySha256==null)
                || absent != (boundGraphVersion==null) || absent != (boundGraphSha256==null))
                throw new IllegalArgumentException("Incomplete survey provenance");
            if(!absent && (surveyVersion<1 || surveyVersion>version || boundGraphVersion<1
                || boundGraphVersion>=surveyVersion || !branchId.equals(profile.branchId())
                || !surveySha256.matches("[0-9a-f]{64}") || !boundGraphSha256.matches("[0-9a-f]{64}")))
                throw new IllegalArgumentException("Invalid survey provenance");
            diagnostics=List.copyOf(diagnostics);
        }
    }
    Result executeSurvey(Command command);
}
