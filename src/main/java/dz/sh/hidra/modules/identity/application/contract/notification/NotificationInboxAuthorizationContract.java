/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : NotificationInboxAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.notification
 * @Description : Only current signed LOCAL recipient grants for exact T27 Notification inbox actions.
 */
package dz.sh.hidra.modules.identity.application.contract.notification;

public interface NotificationInboxAuthorizationContract {
    String require(String permission,String companyId,String facilityId);
}
