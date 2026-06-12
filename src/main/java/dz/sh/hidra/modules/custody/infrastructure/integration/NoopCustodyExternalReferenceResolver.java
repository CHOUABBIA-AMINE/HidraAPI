/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopCustodyExternalReferenceResolver
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.integration
 *
 * @Description : No-op custody external reference resolver.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.integration;

/**
 * No-op custody external reference resolver.
 */
public class NoopCustodyExternalReferenceResolver implements CustodyExternalReferenceResolver {

    @Override
    public boolean telemetrySnapshotAvailable(String referenceId) {
        return true;
    }

    @Override
    public boolean planningReferenceAvailable(String referenceId) {
        return true;
    }

    @Override
    public boolean topologyReferenceAvailable(String referenceId) {
        return true;
    }

    @Override
    public boolean partyReferenceAvailable(String referenceId) {
        return true;
    }

    @Override
    public boolean workflowReferenceAvailable(String referenceId) {
        return true;
    }

    @Override
    public boolean documentReferenceAvailable(String referenceId) {
        return true;
    }
}
