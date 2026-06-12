/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditSourceEventConsumerPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.out
 *
 * @Description : Outbound audit port AuditSourceEventConsumerPort.
 *
 */
package dz.sh.hidra.modules.audit.application.port.out;

/**
 * Outbound audit port.
 */
public interface AuditSourceEventConsumerPort {

    boolean available(String referenceId);
}
