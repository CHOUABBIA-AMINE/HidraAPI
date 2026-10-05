/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingAccessAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.reporting
 *
 * @Description : Identity-owned authorization decision contract exported to Reporting.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.reporting;

public interface ReportingAccessAuthorizationContract {

    boolean permitted(AccessRequest request);

    record AccessRequest(
            String actorId,
            String permissionCode,
            String reportDefinitionId,
            String scopeType,
            String scopeReferenceId
    ) { }
}
