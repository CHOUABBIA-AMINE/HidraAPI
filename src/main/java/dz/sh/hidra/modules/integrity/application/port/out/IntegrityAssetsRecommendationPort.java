/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssetsRecommendationPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.out
 *
 * @Description : Outbound reference port IntegrityAssetsRecommendationPort.
 *
 */
package dz.sh.hidra.modules.integrity.application.port.out;

/**
 * Outbound reference port.
 */
public interface IntegrityAssetsRecommendationPort {

    boolean available(String referenceId);
}
