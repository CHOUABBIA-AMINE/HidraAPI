/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningModule
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning
 *
 * @Description : Defines planning module constants.
 *
 */
package dz.sh.hidra.modules.planning;

/**
 * Planning module constants.
 */
public final class PlanningModule {

    public static final String MODULE_NAME = "planning";
    public static final String TABLE_PREFIX = "hidra_planning_";

    private PlanningModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
