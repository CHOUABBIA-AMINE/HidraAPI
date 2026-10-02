/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionConflictException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.exception
 *
 * @Description : Raised when suppression creation or release conflicts with authoritative suppression state.
 *
 */
package dz.sh.hidra.modules.alarm.domain.exception;

public final class AlarmSuppressionConflictException extends AlarmDomainException {

    private static final long serialVersionUID = 8426705603946744238L;

    public AlarmSuppressionConflictException(String message) {
        super(message);
    }
}
