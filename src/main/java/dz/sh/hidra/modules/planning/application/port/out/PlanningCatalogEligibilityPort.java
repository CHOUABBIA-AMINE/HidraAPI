/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningCatalogEligibilityPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.out
 *
 * @Description : Enforces Planning-owned semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.planning.application.port.out;

public interface PlanningCatalogEligibilityPort {
    void requireActive(String entryId, String family);
}
