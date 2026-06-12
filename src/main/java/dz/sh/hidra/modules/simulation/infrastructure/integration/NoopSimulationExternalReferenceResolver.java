/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopSimulationExternalReferenceResolver
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : No-op simulation external reference resolver.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

/**
 * No-op simulation external reference resolver.
 */
public class NoopSimulationExternalReferenceResolver implements SimulationExternalReferenceResolver {

    @Override
    public boolean topologySnapshotAvailable(String topologySnapshotId) {
        return true;
    }

    @Override
    public boolean planningSnapshotAvailable(String planningReferenceId) {
        return true;
    }

    @Override
    public boolean monitoringContextAvailable(String monitoringContextId) {
        return true;
    }

    @Override
    public boolean workflowAvailable(String workflowReferenceId) {
        return true;
    }

    @Override
    public boolean documentReferenceAvailable(String documentReferenceId) {
        return true;
    }
}
