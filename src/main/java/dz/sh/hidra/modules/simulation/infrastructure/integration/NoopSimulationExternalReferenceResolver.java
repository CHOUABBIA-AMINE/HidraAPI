/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopSimulationExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
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

import dz.sh.hidra.modules.simulation.application.port.out.MonitoringContextLookupPort;
import dz.sh.hidra.modules.simulation.application.port.out.PlanningSnapshotLookupPort;
import dz.sh.hidra.modules.simulation.application.port.out.TopologySnapshotLookupPort;
import org.springframework.stereotype.Component;

/**
 * Fail-closed placeholder until owner-module reference adapters are wired.
 */
@Component
public class NoopSimulationExternalReferenceResolver implements
        SimulationExternalReferenceResolver,
        TopologySnapshotLookupPort,
        PlanningSnapshotLookupPort,
        MonitoringContextLookupPort {

    @Override
    public boolean topologySnapshotAvailable(String topologySnapshotId) {
        return false;
    }

    @Override
    public boolean planningSnapshotAvailable(String planningReferenceId) {
        return false;
    }

    @Override
    public boolean monitoringContextAvailable(String monitoringContextId) {
        return false;
    }

    @Override
    public boolean available(String referenceId) {
        return false;
    }

    @Override
    public boolean workflowAvailable(String workflowReferenceId) {
        return false;
    }

    @Override
    public boolean documentReferenceAvailable(String documentReferenceId) {
        return false;
    }
}
