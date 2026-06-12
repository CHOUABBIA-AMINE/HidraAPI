/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyExternalReferenceResolver
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.integration
 *
 * @Description : Resolves custody external references without importing foreign domains.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.integration;

/**
 * Resolves custody external references without importing foreign domain models.
 */
public interface CustodyExternalReferenceResolver {

    boolean telemetrySnapshotAvailable(String referenceId);

    boolean planningReferenceAvailable(String referenceId);

    boolean topologyReferenceAvailable(String referenceId);

    boolean partyReferenceAvailable(String referenceId);

    boolean workflowReferenceAvailable(String referenceId);

    boolean documentReferenceAvailable(String referenceId);
}
