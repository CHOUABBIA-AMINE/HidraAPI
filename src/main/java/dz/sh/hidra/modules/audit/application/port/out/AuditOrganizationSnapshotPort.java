/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditOrganizationSnapshotPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.out
 *
 * @Description : Outbound audit port AuditOrganizationSnapshotPort.
 *
 */
package dz.sh.hidra.modules.audit.application.port.out;

/**
 * Outbound audit port.
 */
public interface AuditOrganizationSnapshotPort {

    boolean available(String referenceId);
}
