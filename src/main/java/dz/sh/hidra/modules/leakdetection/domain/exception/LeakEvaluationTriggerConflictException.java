/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakEvaluationTriggerConflictException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.exception
 * @Description : T13 governed source-linked suspected leak candidate delivery.
 */
package dz.sh.hidra.modules.leakdetection.domain.exception;

/** Same immutable Monitoring receipt was already used with a different declaration. */
public class LeakEvaluationTriggerConflictException extends RuntimeException {
    public LeakEvaluationTriggerConflictException() {
        super("The Monitoring evaluation already has a different Leak candidate declaration.");
    }
}
