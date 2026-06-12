/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationAuthorityLookupPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.port.out
 *
 * @Description : Outbound notification port NotificationAuthorityLookupPort.
 *
 */
package dz.sh.hidra.modules.notification.application.port.out;

/**
 * Outbound notification port.
 */
public interface NotificationAuthorityLookupPort {

    boolean available(String referenceId);
}
