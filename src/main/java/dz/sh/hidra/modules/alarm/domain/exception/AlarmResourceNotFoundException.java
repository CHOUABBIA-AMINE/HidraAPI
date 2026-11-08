/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Name        : AlarmResourceNotFoundException
 * @UpdatedOn   : 2026-11-08
 * @Type        : Exception
 * @Layer       : Exception
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.exception
 * @Description : Non-disclosing missing or inaccessible alarm resource.
 */
package dz.sh.hidra.modules.alarm.domain.exception;

public final class AlarmResourceNotFoundException extends RuntimeException {
    public AlarmResourceNotFoundException() { super("Resource not found."); }
}
