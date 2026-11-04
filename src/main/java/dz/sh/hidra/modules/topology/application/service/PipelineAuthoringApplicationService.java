/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAuthoringApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Owns the bounded canonical Pipeline and persisted graph association contract.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.*;
import dz.sh.hidra.modules.topology.application.port.out.AuthoredTopologyAuthorizationPort;
import org.springframework.stereotype.Service;
@Service
public final class PipelineAuthoringApplicationService implements PipelineAuthoringUseCase,AuthoredTopologyViewUseCase {
    private final EngineeringGraphDraftUseCase graphOwner;
    private final AuthoredTopologyAuthorizationPort authentication;
    public PipelineAuthoringApplicationService(EngineeringGraphDraftUseCase graphOwner,AuthoredTopologyAuthorizationPort authentication){this.graphOwner=java.util.Objects.requireNonNull(graphOwner);this.authentication=java.util.Objects.requireNonNull(authentication);}
    @Override public void requireGlobalMapAccess(){authentication.requireLegacyMapAdministrator();}
    @Override public View write(Write command){authentication.requireNativeAuthentication();return graphOwner.executePipeline(command);}
    @Override public View view(Scope scope,Long version){authentication.requireNativeAuthentication();return graphOwner.executeAuthoredView(scope,version);}
    @Override public Discovery discover(Scope scope,int offset,int limit){authentication.requireNativeAuthentication();if(offset<0||offset>100000||limit<1||limit>200)throw new IllegalArgumentException("Bounded discovery required");return graphOwner.executeAuthoredDiscovery(scope,offset,limit);}
}
