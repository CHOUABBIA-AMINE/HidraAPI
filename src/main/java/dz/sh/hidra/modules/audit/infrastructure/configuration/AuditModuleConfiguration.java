/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.configuration
 *
 * @Description : Audit infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.configuration;

/**
 * Audit infrastructure configuration.
 */
public record AuditModuleConfiguration(
        boolean appendOnlyEnforced,
        boolean sensitiveDataMaskingRequired,
        boolean integritySealEnabled,
        boolean exportApprovalWorkflowEnabled
) {

    public static AuditModuleConfiguration defaults() {
        return new AuditModuleConfiguration(true, true, true, true);
    }
}
