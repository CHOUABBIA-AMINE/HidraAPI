/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCommissioningConflictException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.exception
 *
 * @Description : Generic non-disclosing duplicate or interval conflict.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.exception;

public final class TelemetryCommissioningConflictException extends TelemetryDomainException {
    public TelemetryCommissioningConflictException() { super("Conflicting commissioning request."); }
}
