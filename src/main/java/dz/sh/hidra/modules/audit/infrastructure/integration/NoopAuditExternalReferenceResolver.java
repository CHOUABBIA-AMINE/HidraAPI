/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopAuditExternalReferenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.integration
 *
 * @Description : No-op audit external reference resolver.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.integration;

/**
 * No-op audit external reference resolver.
 */
public class NoopAuditExternalReferenceResolver implements AuditExternalReferenceResolver {

    @Override
    public boolean sourceEventAvailable(String sourceModule, String sourceEventId) {
        return true;
    }

    @Override
    public boolean targetAvailable(String targetModule, String targetType, String targetId) {
        return true;
    }

    @Override
    public boolean workflowReferenceAvailable(String workflowInstanceId) {
        return true;
    }

    @Override
    public boolean documentReferenceAvailable(String documentReferenceId) {
        return true;
    }
}
