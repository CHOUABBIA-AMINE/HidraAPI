/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApprovalException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application
 *
 * @Description : Classifies deterministic planning approval failures for API translation.
 *
 */
package dz.sh.hidra.modules.planning.application;

public final class PlanningApprovalException extends RuntimeException {

    private final Kind kind;

    public PlanningApprovalException(Kind kind, String message) {
        super(message);
        this.kind = kind;
    }

    public Kind kind() {
        return kind;
    }

    public enum Kind {
        NOT_FOUND,
        BOUNDARY,
        DENIED,
        CONFLICT
    }
}
