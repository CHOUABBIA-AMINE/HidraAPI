/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Resolves simulation external references without importing foreign domains.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

/**
 * Resolves simulation external references without importing foreign domain models.
 */
public interface SimulationExternalReferenceResolver {

    boolean topologySnapshotAvailable(String topologySnapshotId);

    boolean planningSnapshotAvailable(String planningReferenceId);

    boolean monitoringContextAvailable(String monitoringContextId);

    boolean workflowAvailable(String workflowReferenceId);

    boolean documentReferenceAvailable(String documentReferenceId);
}
