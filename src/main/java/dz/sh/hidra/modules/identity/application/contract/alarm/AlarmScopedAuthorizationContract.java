/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Name        : AlarmScopedAuthorizationContract
 * @UpdatedOn   : 2026-11-08
 * @Type        : Interface
 * @Layer       : Interface
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.alarm
 * @Description : Current native Identity decision requiring explicit company and asset grants.
 */
package dz.sh.hidra.modules.identity.application.contract.alarm;

public interface AlarmScopedAuthorizationContract {
    boolean permitted(String permissionCode,String companyPartyId,String assetType,String assetId);
}
