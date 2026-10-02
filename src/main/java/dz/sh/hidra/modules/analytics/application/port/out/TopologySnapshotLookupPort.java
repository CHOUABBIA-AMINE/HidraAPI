/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologySnapshotLookupPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Outbound analytics port TopologySnapshotLookupPort.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

/**
 * Outbound analytics port returning read models or snapshots.
 */
public interface TopologySnapshotLookupPort {

    boolean available(String referenceId);
}
