/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyModuleConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.configuration
 *
 * @Description : Custody infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.configuration;

/**
 * Custody infrastructure configuration.
 */
public record CustodyModuleConfiguration(
        boolean immutableSnapshotRequired,
        boolean ticketApprovalWorkflowEnabled,
        boolean reconciliationEnabled,
        boolean financeExportBlocked
) {

    public static CustodyModuleConfiguration defaults() {
        return new CustodyModuleConfiguration(true, true, true, true);
    }
}
