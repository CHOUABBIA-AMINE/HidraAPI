/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScenarioInputAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.simulation
 * @Description : Exact paired signed-native scenario input grant decision in the current transaction.
 */
package dz.sh.hidra.modules.identity.application.contract.simulation;


public interface ScenarioInputAuthorizationContract {
    String READ = "simulation:scenario-inputs:read";
    String WRITE = "simulation:scenario-inputs:write";
    /** Returns the verified signed LOCAL actor, never a caller-supplied identity. */
    String require(String permission,String companyId,String pipelineSystemId);
}
