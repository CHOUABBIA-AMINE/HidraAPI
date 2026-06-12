/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetReliabilityLookupPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Outbound analytics port AssetReliabilityLookupPort.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

/**
 * Outbound analytics port returning read models or snapshots.
 */
public interface AssetReliabilityLookupPort {

    boolean available(String referenceId);
}
